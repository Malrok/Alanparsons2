package com.MRK.alanparsons2.helpers;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class PathInterpreter {

	private Calculable calc;
	
	public boolean evaluate(String command, String[] vars, float[] values) {
		ExpressionBuilder expr;
		try {
			expr = new ExpressionBuilder(command);
			for  (int num = 0; num < vars.length; num++)
				expr.withVariable(vars[num], values[num]);
			calc = expr.build();
		} catch (UnknownFunctionException e) {
			e.printStackTrace();
			return false;
		} catch (UnparsableExpressionException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public float getInt() {
		return (float) calc.calculate();
	}
}
