package com.MRK.alanparsons2.helpers;

import com.MRK.alanparsons2.Alanparsons2;

public class Debug {

	public static void out(String message) {
		if (Alanparsons2.DEBUG)
			System.out.println(message);
	}
	
}
