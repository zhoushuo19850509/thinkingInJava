package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

import com.nbcb.thinkingInJava.interfaces.filters.*;
import com.nbcb.thinkingInJava.interfaces.Waveform;


/**
 * 
 * @author 080776
 *
 *������Ŀ��ǳ���ȷ��֮ǰ���Ǵ�����һ��Process�ӿڣ��ܹ�ͨ��Apply.process()����ֱ�ӵ��á�
 *
 *����������ͨ��Apply.process()ֱ�ӵ���Filter�Լ���Ӧ������
 *��ô���أ����Ǵ���һ��Adpater�࣬���Apdater��ʵ��Process�ӿڵķ���
 *ͬʱ����constructor�У���Filter�ഫ������
 *
 *���գ�ͨ�����FilterAdapter�࣬ʵ��Apply.process()��ͳһ��ڵ���
 */
class FilterAdapter implements Processor{

	Filter filter;
	
	public FilterAdapter(Filter filter){
		this.filter = filter;
	}
	
	public String name() {
		return filter.name();
	}

	public Waveform process(Object input) {
		// TODO Auto-generated method stub
		return filter.process((Waveform)input);
	}
	
}


public class FilterProcessor {
	public static void main(String[] args){
		Waveform w = new Waveform();
		Apply.process(new FilterAdapter(new LowPass(2.0)), w);
		Apply.process(new FilterAdapter(new HighPass(3.0)), w);
		Apply.process(new FilterAdapter(new BandPass(4.0,5.0)), w);
//		Apply.process(new FilterAdapter(new Filter()), w);
	}

}
