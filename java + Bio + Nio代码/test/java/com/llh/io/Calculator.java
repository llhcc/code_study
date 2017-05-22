package com.llh.io;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
public final class Calculator {  
	
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    public static Object cal(String expression) throws ScriptException{
        return jse.eval(expression);
    }
    
    public static void main(String args[]){
    	Calculator cal = new Calculator();
    	try {
			System.out.println(cal.cal("2*3-1*8+8/2"));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
    }
}