package com.nbcb.thinkingInJava.interfaces.filters;

import com.nbcb.thinkingInJava.interfaces.Waveform;

public class LowPass extends Filter{
	double cutoff;
	public LowPass(double cutoff){
		this.cutoff = cutoff;
	}
	
	public Waveform process(Waveform input){
		return input;
	}
}