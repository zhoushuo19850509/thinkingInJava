package com.nbcb.thinkingInJava.interfaces.interfaceProcessor;

import java.util.Arrays;

// �����һ�������࣬������Processor�Ľӿ�
// ͬʱҲ��base class
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
