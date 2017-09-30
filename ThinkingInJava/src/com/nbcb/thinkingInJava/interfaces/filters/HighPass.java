package com.nbcb.thinkingInJava.interfaces.filters;

import com.nbcb.thinkingInJava.interfaces.Waveform;


public class HighPass extends Filter{
	double cutoff;
	public HighPass(double cutoff){
		this.cutoff = cutoff;
	}
	
	public Waveform process(Waveform input){
		return input;
	}
}