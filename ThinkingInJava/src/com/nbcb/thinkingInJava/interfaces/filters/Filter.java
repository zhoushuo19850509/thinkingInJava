package com.nbcb.thinkingInJava.interfaces.filters;

import com.nbcb.thinkingInJava.interfaces.Waveform;

public class Filter {
	public String name(){
		return getClass().getSimpleName();
	}
	
	public Waveform process(Waveform input){
		return input;
	}
}
