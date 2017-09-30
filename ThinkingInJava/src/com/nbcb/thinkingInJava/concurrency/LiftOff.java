package com.nbcb.thinkingInJava.concurrency;

public class LiftOff implements Runnable{
	
	
	protected int countDown = 10;   // default value is 10
	private static int taskCount = 0;  
	private final int id = taskCount++; // ÿ���̵߳ı�ʶID
	public LiftOff(){
		
	}
	
	public LiftOff(int countDown){
		this.countDown = countDown;
	}
	
	private String status(){
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!" ) + ")" ;
	}

	public void run() {
		
		// ��run()�У���������߳���������
		while(countDown-- > 0){
			System.out.println(status());
			Thread.yield();    // ˵����ǰ���߳�������ʱ�����ˣ���Դ�������������߳���
		}
	}
	
	public static void main(String[] args){
		// ��LiftOff������ͨ������
//		LiftOff lift = new LiftOff();
//		lift.run();
		
		// ֻ��һ���߳�����LiftOff
//		Thread t = new Thread(new LiftOff());
//		t.run();
		
		// ��������߳�������LiftOff
		for(int i = 0; i < 5 ; i++){
			new Thread(new LiftOff()).start();
			
		}
		System.out.println("waiting for thread to finish!!!");
	}

}
