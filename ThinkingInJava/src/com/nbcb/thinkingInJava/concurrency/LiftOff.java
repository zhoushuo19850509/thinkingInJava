package com.nbcb.thinkingInJava.concurrency;

public class LiftOff implements Runnable{
	
	
	protected int countDown = 10;   // default value is 10
	private static int taskCount = 0;  
	private final int id = taskCount++; // 每个线程的标识ID

	// empty constructor
	public LiftOff(){
		
	}

	// constructor
	public LiftOff(int countDown){
		this.countDown = countDown;
	}


	public String status(){
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!" ) + ")" ;
	}

	public void run() {
		
		// 在run()中，定义这个线程做的事情
		while(countDown-- > 0){
			System.out.println(status());
			Thread.yield();    // 说明当前的线程事情暂时做完了，资源可以留给其他线程了

		}
	}
	
	public static void main(String[] args){
		// 把LiftOff当做普通类来跑
//		LiftOff lift = new LiftOff();
//		lift.run();
		
		// 只起一个线程来跑LiftOff
//		Thread t = new Thread(new LiftOff());
//		t.run();
		
		// 启动多个线程来运行LiftOff
		for(int i = 0; i < 5 ; i++){
			new Thread(new LiftOff()).start();
			
		}
		System.out.println("All the threads has started!!!");
	}

}
