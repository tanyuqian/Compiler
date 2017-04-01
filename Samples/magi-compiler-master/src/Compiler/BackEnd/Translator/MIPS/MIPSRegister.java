package Compiler.BackEnd.Translator.MIPS;

import Compiler.BackEnd.Allocator.PhysicalRegister;

public class MIPSRegister extends PhysicalRegister {
	public static PhysicalRegister zero = new MIPSRegister(0, "$0");
	public static PhysicalRegister at = new MIPSRegister(1, "$at");
	public static PhysicalRegister v0 = new MIPSRegister(2, "$v0");
	public static PhysicalRegister v1 = new MIPSRegister(3, "$v1");
	public static PhysicalRegister a0 = new MIPSRegister(4, "$a0");
	public static PhysicalRegister a1 = new MIPSRegister(5, "$a1");
	public static PhysicalRegister a2 = new MIPSRegister(6, "$a2");
	public static PhysicalRegister a3 = new MIPSRegister(7, "$a3");
	public static PhysicalRegister t0 = new MIPSRegister(8, "$t0");
	public static PhysicalRegister t1 = new MIPSRegister(9, "$t1");
	public static PhysicalRegister t2 = new MIPSRegister(10, "$t2");
	public static PhysicalRegister t3 = new MIPSRegister(11, "$t3");
	public static PhysicalRegister t4 = new MIPSRegister(12, "$t4");
	public static PhysicalRegister t5 = new MIPSRegister(13, "$t5");
	public static PhysicalRegister t6 = new MIPSRegister(14, "$t6");
	public static PhysicalRegister t7 = new MIPSRegister(15, "$t7");
	public static PhysicalRegister s0 = new MIPSRegister(16, "$s0");
	public static PhysicalRegister s1 = new MIPSRegister(17, "$s1");
	public static PhysicalRegister s2 = new MIPSRegister(18, "$s2");
	public static PhysicalRegister s3 = new MIPSRegister(19, "$s3");
	public static PhysicalRegister s4 = new MIPSRegister(20, "$s4");
	public static PhysicalRegister s5 = new MIPSRegister(21, "$s5");
	public static PhysicalRegister s6 = new MIPSRegister(22, "$s6");
	public static PhysicalRegister s7 = new MIPSRegister(23, "$s7");
	public static PhysicalRegister t8 = new MIPSRegister(24, "$t8");
	public static PhysicalRegister t9 = new MIPSRegister(25, "$t9");
	public static PhysicalRegister k0 = new MIPSRegister(26, "$k0");
	public static PhysicalRegister k1 = new MIPSRegister(27, "$k1");
	public static PhysicalRegister gp = new MIPSRegister(28, "$gp");
	public static PhysicalRegister sp = new MIPSRegister(29, "$sp");
	public static PhysicalRegister fp = new MIPSRegister(30, "$fp");
	public static PhysicalRegister ra = new MIPSRegister(31, "$ra");

	public static PhysicalRegister temporary1 = v1;
	public static PhysicalRegister temporary2 = a3;

	private MIPSRegister(int identity, String name) {
		super(identity, name);
	}

	public static int size() {
		return 4;
	}
}
