package com.nbcb.thinkingInJava.concurrency.simulation;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

/**
 *
 * 这个代码综合了Concurrency的各种技术，涉及：
 * 1.BlockingQueue 保存各种Car队列；
 * 2.CyclicBarrier 用于协调各个异步线程；
 * 3.wait()/notifyAll() 用于Robot异步线程的状态激活、断电
 *
 * @业务场景
 * 这个代码的业务场景就是汽车组装
 * 组装流程如下：
 * 1.通过ChasisBuilder创建一个Car队列:chasisQueue，
 *   chasisQueue队列中的Car仅仅只有底盘，未开始组装；
 * 2.Assembler从chasisQueue队列中获取待组装的Car对象
 *   然后从RobotPool中获取三个Robot实例，
 *   分别组装Car的各个部件(引擎、驱动装置和轮胎)
 *   将组装完毕的Car放到finishedQueue队列
 * 3.Reporter从finishedQueue队列获取那些组装完毕的汽车，
 *   打印这些汽车的信息
 */


/**
 * java bean of Car
 */
class Car{

    private int id; // 标识Car

    public Car(int id) {
        this.id = id;
    }

    public Car() {
        id = -1;
    }

    private boolean engine = false; // 引擎是否安装
    private boolean driveTrain = false; // 底盘是否安装
    private boolean wheel = false; // 轮胎是否安装

    // getter
    public int getId() {
        return id;
    }

    // setter1 安装引擎
    public synchronized void addEngine(){
        this.engine = true;
    }

    // setter2 安装底盘
    public synchronized void addDriveTrain(){
        this.driveTrain = true;
    }

    // setter1 安装轮胎
    public synchronized void addWheel(){
        this.wheel = true;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", engine=" + engine +
                ", driveTrain=" + driveTrain +
                ", wheel=" + wheel +
                '}';
    }
}


class CarQueue<Car> extends LinkedBlockingQueue<Car>{}

/**
 * ChasisBuilder组装底盘
 * 异步线程做的事情：
 * 1.创建新的Car对象
 * 2.造好车的底盘
 */
class ChasisBuilder implements Runnable{

    /**
     * 这个队列，维护是那些新被创建出来的Car对象实例
     * 这些Car对象只有底盘，没有组装其他部件
     */
    private CarQueue<Car> carQueue;

    private int counter = 0;

    private Random random = new Random(47);

    public ChasisBuilder(CarQueue<Car> carQueue) {
        this.carQueue = carQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                // sleep一段时间，模拟创建一个新的Car对象实例
                Thread.sleep(500);
                System.out.println("new car created ...");
                Car car = new Car(counter++);
                carQueue.add(car);
            }
        } catch (InterruptedException e) {
            System.out.println("ChasisBuilder Interrupted ...");
        }
        System.out.println("ChasisBuilder off ...");
    }

}

/**
 * Assembler组装汽车
 * 异步线程做的事情：
 * 1.从chasisQueue队列中获取组装好底盘(待组装)的汽车
 * 2.调用各种机器人，组装各种部件
 * 3.把组装好的机器人放到finishQueue队列中
 *
 * 备注：Assembler虽然是一个独立的异步线程，但是，在同一时刻只能够处理一个Car对象
 * 这是如何做到的呢？这是通过CylicBarrier实现的：
 * CylicBarrier保证必须等到Assembler/EngineRobot/DriveTrainRobot/WheelRobot
 * 这四个异步线程全部完成自己的工作之后，流程才往下走。
 * 具体CylicBarrier参考下面的注释
 */
class Assembler implements Runnable{

    /**
     * 队列1 这个队列是由ChasisBuilder负责创建的，
     * 队列中包含仅安装了底盘的Car对象
     */
    private CarQueue<Car> chassisQueue = null;

    /**
     * 队列2 包含由Assembler组装完毕后的Car对象
     */
    private CarQueue<Car> finishedQueue = null;

    /**
     * Assembler当前正在组装处理的Car对象
     */
    private Car car;

    public Car getCar() {
        return car;
    }

    private RobotPool robotPool = null;

    /**
     * 我们要重点解释一下这个CyclicBarrier
     * CyclicBarrier啥意思我们之前的HorseRace.java中已经说明了
     * CyclicBarrier对象在初始化的时候会指定一个参数：parties
     * 意思就是n个任务(任务数量n和parties一样，每个任务都是一个异步线程)，
     * 这些任务完成后调用CyclicBarrier.await()，
     * 这样就必须等待n个任务全部执行完毕后，程序才会走下去。
     * 备注：CyclicBarrier功能和之前我们常用的CountDown类似，
     * CyclicBarrier和CountDown的区别我们已经在HorseRace.java中解释过了
     *
     * 那么，在当前场景中，这个CyclicBarrier对象控制着整个Assembler以及各个Robot实例
     * 这里的4代表4个异步线程：3个Robot对象异步线程
     * (EngineRobot、DriveTrainRobot、WheelRobot)，以及Assembler对象。
     * 对于一辆车来说，必须等待这4个异步线程全部完成后，才能够放到finishedQueue队列中去
     * 这个CyclicBarrier可以说是整个CarBuilder的核心，
     * 因为它管控着整个组装器Assembler涉及的各个异步线程
     * 后续如果我们碰到有类似的需要同步各个异步线程的场景，也可以用CyclicBarrier
     *
     */
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public Assembler(CarQueue<Car> chassisQueue,
                     CarQueue<Car> finishedQueue,
                     RobotPool robotPool) {
        this.chassisQueue = chassisQueue;
        this.finishedQueue = finishedQueue;
        this.robotPool = robotPool;
    }

    /**
     * 组装器Assembler作为一个异步线程，要做的事情如下：
     */
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                // 从新建Car队列中取出一个仅安装了底盘的Car对象
                car = this.chassisQueue.take();

                // 由EngineRobot负责组装引擎
                this.robotPool.hire(EngineRobot.class,this);

                // 由DriveTrainRobot负责安装传动装置
                this.robotPool.hire(DriveTrainRobot.class,this);

                // 由WheelRobot负责安装轮胎
                this.robotPool.hire(WheelRobot.class,this);

                this.cyclicBarrier.await();

                // 等整辆车组装完毕以后，把当前car对象放到finishQueue队列中去
                finishedQueue.add(car);
            }
        } catch (InterruptedException e) {
            System.out.println("assembler Interrupted ... ");
            System.out.println(this.car +
                    "is in assembler while the thread pool shutdown ...");
            printChsisQueue();
        } catch (BrokenBarrierException e) {
            System.out.println("assembler BarrierException broken ... ");
            System.out.println(this.car +
                    "is in assembler while assembler BarrierException broken ...");
            printChsisQueue();
        }

        System.out.println("assembler finish ...");
    }

    /**
     * 这个方法用于打印那些处于chasisQueue队列的待组装的Car实例
     * 主要用于了解流水线关闭后，还有哪些Car实例等待组装
     */
    private void printChsisQueue(){
        System.out.println("start print cars in chasis queue : ");
        for(Car car : chassisQueue){
            System.out.println(car);
        }
    }

}

/**
 * Reporter：汇总报告
 * 异步线程做的事情：遍历一下finishQueue队列
 * 打印一下各个组装完毕的汽车
 */
class Reporter implements Runnable{

    /**
     * 这个队列，维护是那些已经完成组装的Car对象实例
     */
    private CarQueue<Car> carQueue;

    public Reporter(CarQueue<Car> carQueue) {
        this.carQueue = carQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Car car = carQueue.take();
                System.out.println("[Reporter: ]" + car);
            }

        } catch (InterruptedException e) {
            System.out.println("Reporter Interrupted ...");
        }
        System.out.println("Reporter off ...");
    }
}

/**
 * Robot抽象类(父类)
 */
abstract class Robot implements Runnable{


    private RobotPool pool;

    protected Assembler assembler;

    private boolean engage = false;

    protected Random random = new Random(47);

    public synchronized void engate(){
        this.engage = true;
        notifyAll();
    }
    /**
     * constructor
     * 指定当前Robot对象属于哪个RobotPool
     * @param pool
     */
    public Robot(RobotPool pool) {
        this.pool = pool;
    }

    /**
     * 指定当前Robot对象交给哪个assembler，实施组装工作
     * @param assembler
     */
    public void assignAssembler(Assembler assembler){
        this.assembler = assembler;
    }



    // 这个抽象方法，交给个子类去实现
    abstract void performService();

    /**
     * robot抽象类作为一个独立线程，做的事情如下：
     *
     */
    public void run(){

        try {
            powerDown();
            while(!Thread.interrupted()){

                /**
                 * 处理一种特殊情况，就是我们关闭线程池之后，Assembler会为null
                 * 这时Robot如果还去执行performService()的话，就会抛出异常
                 * 所以这里就处理一下，避免出现异常
                 */
                if(null == this.assembler){

                    System.out.println(
                            "assembler shutdown while robot is about to performService ...");
                    throw new InterruptedException();
                }

                /**
                 * 先调用Robot子类中实现的performService()方法，
                 * 就是具体某种robot做的事情：安装引擎、安装传动装置、安装轮胎
                 */
                performService();

                this.assembler.getCyclicBarrier().await();

                powerDown();

            }

        } catch (InterruptedException e) {
            System.out.println("existing robot " + this + " Interrupted ... ");
        } catch (BrokenBarrierException e) {
            System.out.println("Robot " + this + "BarrierException broken ... ");
        }


        System.out.println(this + " off");

    }

    // robot抽象类断电
    private synchronized void powerDown(){
        try {
            this.engage = false;  // robot状态设置为空闲
            assembler = null; // 把robot和组装器断开
            pool.release(this);  // 把用完的robot放回robot pool
            while(engage == false){
                wait();
            }

        } catch (InterruptedException e) {
            System.out.println("Interrupted while waiting ..." + this );
        }
    }

    @Override
    public String toString() {
        return "Robot{ "+ getClass().getSimpleName() + "}";
    }
}

/**
 * 组装引擎的机器人
 */
class EngineRobot extends Robot{

    /**
     * constructor
     * 指定当前Robot对象属于哪个RobotPool
     *
     * @param pool
     */
    public EngineRobot(RobotPool pool) {
        super(pool);
    }

    @Override
    void performService() {
        System.out.println(this + " installing engine ...");
        // sleep a while to simulate the EngineRobot is installing engine ...
        try {
            Thread.sleep(this.random.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.assembler.getCar().addEngine();
    }
}

/**
 * Robot子类1 组装DriveTrain(传动系统)的机器人
 */
class DriveTrainRobot extends Robot{

    /**
     * constructor
     * 指定当前Robot对象属于哪个RobotPool
     *
     * @param pool
     */
    public DriveTrainRobot(RobotPool pool) {
        super(pool);
    }

    @Override
    void performService() {
        System.out.println(this + " installing DriveTrain ...");
        // sleep a while to simulate the DriveTrainRobot is installing DriveTrain ...
        try {
            Thread.sleep(this.random.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.assembler.getCar().addDriveTrain();
    }
}

/**
 * Robot子类2 组装轮胎的机器人
 */
class WheelRobot extends Robot{

    /**
     * constructor
     * 指定当前Robot对象属于哪个RobotPool
     *
     * @param pool
     */
    public WheelRobot(RobotPool pool) {
        super(pool);
    }

    @Override
    void performService() {
        System.out.println(this + " installing Wheel ...");
        // sleep a while to simulate the WheelRobot is installing Wheel ...
        try {
            Thread.sleep(this.random.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.assembler.getCar().addWheel();
    }
}


/**
 * Robot子类3 维护一个Robot pool
 * 里面存放的是可用的robot
 */
class RobotPool{
    // 这个set数据结构用来保存Robot pool中各个robot对象
    private Set<Robot> pool = new HashSet<Robot>();

    /**
     * 往Robot pool中添加可用的robot对象
     * @param robot
     */
    public synchronized void add(Robot robot){
        pool.add(robot);
        notifyAll();
    }

    /**
     * 从Robot pool中取出一个特定类型的、可用的robot对象，
     * 分配给assembler，实施组装工作
     * @param robotType 注意，这是指定的Robot类型，不是Robot实例
     * @param assembler 把取出来的Robot分配到哪个Assembler
     */
    public synchronized void
    hire(Class<? extends Robot> robotType, Assembler assembler){
        for(Robot robot : pool){
            // 一旦发现robot pool中有符合要求的robot实例
            if(robot.getClass().equals(robotType)){
                // 先把这个符合要求的robot从robot pool中取出来，避免其他线程抢占
                pool.remove(robot);
                // 然后把这个符合要求的robot分配给assembler
                robot.assignAssembler(assembler);
                // 启动robot
                robot.engate();
                return;
            }
        }
    }

    /**
     * 把一个用完的robot放回Robot pool
     * @param robot
     */
    public synchronized void release(Robot robot){
        add(robot);
    }
}

public class CarBuilder {

    public static void main(String[] args) {

        // 先创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 再创建若干队列
        // 队列1 仅仅安装了底盘，但是尚未组装的Car队列
        CarQueue<Car> chasisQueue = new CarQueue<>();

        // 队列2 组装完成的队列
        CarQueue<Car> finishedQueue = new CarQueue<>();

        // 创建一个(空的)RobotPool池
        RobotPool robotPool = new RobotPool();

        // 启动线程1 Robot1 EngineRobot，开始准备组装引擎
        executorService.execute(new EngineRobot(robotPool));
        // 启动线程2 Robot2 DriveTrainRobot，开始准备组装驱动装置
        executorService.execute(new DriveTrainRobot(robotPool));
        // 启动线程3 Robot3 WheelRobot，开始准备组装轮子
        executorService.execute(new WheelRobot(robotPool));

        // 启动线程4 Assembler线程开始准备组装
        executorService.execute(new Assembler(chasisQueue,finishedQueue,robotPool));

        // 启动线程5  打印那些已经完成组装的Car队列
        executorService.execute(new Reporter(finishedQueue));

        // 启动线程6 最后一个线程，ChasisBuilder开始新建Car实例(只有底盘，没有组装任何东西)
        executorService.execute(new ChasisBuilder(chasisQueue));

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdownNow();


    }
}
