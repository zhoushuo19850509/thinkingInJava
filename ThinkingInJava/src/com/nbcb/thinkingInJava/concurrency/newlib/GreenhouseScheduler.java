package com.nbcb.thinkingInJava.concurrency.newlib;


import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 * 这个代码的核心就是ScheduledThreadPoolExecutor
 * ScheduledThreadPoolExecutor能够实现两个功能：
 * 1.启动一个异步线程
 *   schedule(task, delay, TimeUnit.MILLISECONDS)
 *
 * 2.定期轮训一个异步线程
 *   scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
 *
 * @业务场景
 * 这个代码的业务场景是这样的，一个阳光房，定期实现：
 * 1.定期打开、关闭灯光
 * 2.定期打开、关闭水源
 * 3.定期调整恒温模式
 * 4.定期打铃
 * 5.定期采集数据
 *
 */

public class GreenhouseScheduler {


    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day"; // 恒温器

    // getter()/setter() of thermostat
    public synchronized String getThermostat() {
        return thermostat;
    }

    public synchronized void setThermostat(String thermostat) {
        this.thermostat = thermostat;
    }

    /**
     * 异步线程1 这个线程做的事情就是打开灯(light置为true)
     */
    class LightOn implements Runnable{

        @Override
        public void run() {
            System.out.println("turning light on ...");
            light = true;
        }
    }

    /**
     * 异步线程2 这个线程做的事情就是关闭灯(light置为false)
     */
    class LightOff implements Runnable{

        @Override
        public void run() {
            System.out.println("turning light off ...");
            light = false;
        }
    }

    /**
     * 异步线程3 这个线程做的事情就是打开水(water置为true)
     */
    class WatherOn implements Runnable{

        @Override
        public void run() {
            System.out.println("turning water on ...");
            water = true;
        }
    }

    /**
     * 异步线程4 这个线程做的事情就是关闭水(water置为false)
     */
    class WatherOff implements Runnable{

        @Override
        public void run() {
            System.out.println("turning water off ...");
            water = false;
        }
    }

    /**
     * 异步线程5 这个线程做的事情就是将恒温器模式调为白天模式
     */
    class ThermostatDay implements Runnable{

        @Override
        public void run() {
            System.out.println("turning Thermostat Day ...");
            setThermostat("Day");
        }
    }

    /**
     * 异步线程6 这个线程做的事情就是将恒温器模式调为夜晚模式
     */
    class ThermostatNight implements Runnable{

        @Override
        public void run() {
            System.out.println("turning Thermostat Night ...");
            setThermostat("Night");
        }
    }

    /**
     * 异步线程7 这个线程做的事情就是响铃
     */
    class Bell implements Runnable{

        @Override
        public void run() {
            System.out.println("BingBingBing ...");
        }
    }

    /**
     * 异步线程8 这个线程是关闭green house，做的事情包括：
     * 1.关闭scheduler
     * 2.打印采集的数据
     */
    class Terminate implements Runnable{

        @Override
        public void run() {
            // 关闭scheduler
            scheduler.shutdownNow();


            /**
             * 这里为啥要在Terminate中单独再启动一个异步线程呢？
             * 因为主线程中的scheduler已经提前关闭了
             */
            new Thread(){
                @Override
                public void run() {
                    for(DataPoint dataPoint : dataList){
                        System.out.println(dataPoint);
                    }
                }
            }.start();

        }
    }

    /**
     * 采集的数据点
     */
    static class DataPoint{
        final Calendar time;     // 数据采集时间
        final float temperature; // 温度
        final float humidity;    // 湿度

        public DataPoint(Calendar time, float temperature, float humidity) {
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return time.getTime() +
                    String.format("  temperature :[%1$.1f] humidity :[%2$.2f]",
                            temperature,humidity);
        }
    }

    /**
     * 配置一个Calendar实例，用于CollectData采集数据的时候记录时间点
     *
     */
    private Calendar lastTime = Calendar.getInstance();
    {
        /**
         * 这两句代码的意思是把时间调整到半点：
         * 比如现在时间为： Thu Jan 19 22:39:00 CST 2023
         * 那么经过调整后的时间为： Thu Jan 19 22:30:00 CST 2023
         */
        lastTime.set(Calendar.MINUTE,30);
        lastTime.set(Calendar.SECOND,00);
    }


    /**
     * 这个list保存的是采集的各个数据点
     */
    List<DataPoint> dataList =
            Collections.synchronizedList(new ArrayList<>());

    /**
     * CollectData负责模拟采集各个数据点
     * 数据点包含时间、温度、湿度三个元素，CollectData会模拟采集这些元素
     */
    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    Random random = new Random(47);
    class CollectData implements Runnable{


        @Override
        public void run() {

            System.out.println("start CollectData ... ");
            synchronized (GreenhouseScheduler.this){

                // 模拟每隔30分钟采集一次数据
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);

                // 1/5概率调整依稀tempDirection的值，保证采集的温度有一定的随机性
                if(random.nextInt(5) == 4){
                    tempDirection = -tempDirection;
                }
                lastTemp = lastTemp + tempDirection * (1.0f + random.nextFloat());

                // 1/5概率调整依稀tempDirection的值，保证采集的温度有一定的随机性
                if(random.nextInt(5) == 4){
                    humidityDirection = -humidityDirection;
                }
                lastHumidity = lastHumidity + humidityDirection * random.nextFloat();

                // 数据模拟完毕后，采集起来放到dataList中
                dataList.add(new DataPoint((Calendar) lastTime.clone(),
                        lastTemp, lastHumidity));
            }
        }
    }


    /**
     * 引入本代码的核心：ScheduledThreadPoolExecutor
     * 其中参数10是初始化线程池中的线程数
     */
    ScheduledThreadPoolExecutor scheduler =
            new ScheduledThreadPoolExecutor(10);

    /**
     * 这个方法启动一次线程
     */

    /**
     * 这个方法启动一次线程
     * @param task 要启动的异步线程
     * @param delay delay多少时间才启动异步线程 时间单位是ms
     */
    public void schedule(Runnable task , long delay){
        scheduler.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 这个方法定期轮询某个异步线程
     * @param task 要轮询的异步线程
     * @param initialDelay 一开始delay多少时间才启动异步线程
     * @param period 每个多久轮询一次
     */
    public void repeat(Runnable task , long initialDelay , long period){
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {

        GreenhouseScheduler gh = new GreenhouseScheduler();

        // 5s之后，停止scheduler并打印采集的数据
        gh.schedule(gh.new Terminate(), 5000);
        // 下面开始就是定期启动一下各个异步线程
        // 比如，每隔1s打一次铃
        gh.repeat(gh.new Bell(), 0, 1000);
        gh.repeat(gh.new ThermostatNight(), 0, 2000);
        gh.repeat(gh.new ThermostatDay(), 0, 1400);
        gh.repeat(gh.new LightOn(), 0, 200);
        gh.repeat(gh.new WatherOn(), 0, 600);
        gh.repeat(gh.new WatherOff(), 0, 800);
        gh.repeat(gh.new CollectData(), 500, 500);


    }
}
