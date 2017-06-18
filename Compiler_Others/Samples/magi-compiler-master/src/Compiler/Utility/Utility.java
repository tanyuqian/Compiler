package Compiler.Utility;

import Compiler.BackEnd.Translator.MIPS.MIPSRegister;

import java.util.HashSet;
import java.util.Set;

public class Utility {
	public static Set<String> arguments = new HashSet<>();

	public static String getIndent(int indents) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < indents; ++i) {
			stringBuilder.append("\t");
		}
		return stringBuilder.toString();
	}

	public static int getLength(String string) {
		int length = 0;
		for (int i = 0; i < string.length(); ++length) {
			if (string.charAt(i) != '\\') {
				i += 1;
			} else {
				i += 2;
			}
		}
		return length;
	}

	public static int getAligned(int size) {
		return (size + MIPSRegister.size() - 1) / MIPSRegister.size() * MIPSRegister.size();
	}
}
