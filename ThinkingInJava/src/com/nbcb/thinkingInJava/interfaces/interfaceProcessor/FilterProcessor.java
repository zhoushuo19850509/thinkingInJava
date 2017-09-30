package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

import com.nbcb.thinkingInJava.interfaces.filters.*;
import com.nbcb.thinkingInJava.interfaces.Waveform;


/**
 * 
 * @author 080776
 *
 *这个类的目标非常明确，之前我们创建了一个Process接口，能够通过Apply.process()方法直接调用。
 *
 *现在我们想通过Apply.process()直接调用Filter以及对应的子类
 *怎么办呢？我们创建一个Adpater类，这个Apdater类实现Process接口的方法
 *同时，在constructor中，将Filter类传进来。
 *
 *最终，通过这个FilterAdapter类，实现Apply.process()的统一入口调用
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
