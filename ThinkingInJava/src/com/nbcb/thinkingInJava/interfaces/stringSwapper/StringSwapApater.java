package com.nbcb.thinkingInJava.interfaces.stringSwapper;

import com.nbcb.thinkingInJava.interfaces.interfaceProcessor.Processor;

/**
 * Excercise 11
 * 这是一个Adapter类，主要目的是使得StringSwapProcessor类能够适配Apply.process()
 * @author 080776
 *
 */
public class StringSwapApater implements Processor{
	StringSwapProcessor swapper;
	
	// constructor
	public StringSwapApater(StringSwapProcessor swapper){
		this.swapper = swapper;
	}

	public String name() {
		// TODO Auto-generated method stub
		return swapper.getClass().getSimpleName();
	}

	public Object process(Object input) {
		// TODO Auto-generated method stub
		return swapper.process((String)input);
	}

}
