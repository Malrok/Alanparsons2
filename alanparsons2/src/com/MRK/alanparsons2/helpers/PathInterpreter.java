package com.MRK.alanparsons2.helpers;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class PathInterpreter {

	private Calculable calc;
	
	public boolean parse(String command, String vars) {
		ExpressionBuilder expr;
		try {
			expr = new ExpressionBuilder(command);
			expr.withVariableNames(vars);
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
	
	public void evaluate(String[] vars, float[] values) {
		for  (int num = 0; num < vars.length; num++)
			calc.setVariable(vars[num], values[num]);
	}
	
	public float getInt() {
		return (float) calc.calculate();
	}
}
