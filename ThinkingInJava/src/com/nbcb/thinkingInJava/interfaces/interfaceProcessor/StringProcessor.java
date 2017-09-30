package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

import java.util.Arrays;

// 这个是一个抽象类，集成了Processor的接口
// 同时也是base class
public abstract class StringProcessor implements Processor{

	public String name() {
		return getClass().getSimpleName();
	}

	public abstract String process(Object input);
	
}

//extends the base class 
//and cast the input string to uppercase
class Upcast extends StringProcessor{
	public String process(Object input){
		return ((String)input).toUpperCase();
	}	
}


//extends the base class 
//and cast the input string to lowercase
class Downcast extends StringProcessor{
	public String process(Object input){
		return ((String)input).toLowerCase();
	}	
}

//extends the base class 
//and split the input string to arrays
class Splitter extends StringProcessor{
	public String process(Object input){
		return Arrays.toString(((String)input).split(" "));
	}	
}
