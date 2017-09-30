package com.nbcb.thinkingInJava.interfaces.filters;

import com.nbcb.thinkingInJava.interfaces.Waveform;

public class BandPass extends Filter{
	double lowCut;
	double hightCut;
	public BandPass(double lowCut,double hightCut){
		this.lowCut = lowCut;
		this.hightCut = hightCut;
	}
	
	public Waveform process(Waveform input){
		return input;
	}
}