package com.MRK.alanparsons2.helpers;

import bsh.EvalError;
import bsh.Interpreter;

public class PathInterpreter {

	private Interpreter interpreter;
	
	public boolean evaluate(String command) {
		try {
			interpreter.eval(command);
		} catch (EvalError e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public int getInt(String value) {
		int ret = 0;
		try {
			ret = (Integer)interpreter.get(value);
		} catch (EvalError eval) {
			eval.printStackTrace();
		} catch (ClassCastException cast) {
			cast.printStackTrace();
		}
		return ret;
	}
}
