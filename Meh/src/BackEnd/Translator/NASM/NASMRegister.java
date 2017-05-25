package BackEnd.Translator.NASM;

import BackEnd.Allocator.PhysicalRegistor;

/**
 * Created by tan on 5/23/17.
 */
public class NASMRegister extends PhysicalRegistor {
    //RAX RCX RDX RBX RSP RBP RSI RDI
    //EAX ECX EDX EBX ESP EBP ESI EDI
    public static PhysicalRegistor rax = new NASMRegister(0, "rax");
    public static PhysicalRegistor rcx = new NASMRegister(1, "rcx");
    public static PhysicalRegistor rdx = new NASMRegister(1, "rdx");
    public static PhysicalRegistor rbx = new NASMRegister(1, "rbx");
    public static PhysicalRegistor rsp = new NASMRegister(1, "rsp");
    public static PhysicalRegistor rbp = new NASMRegister(1, "rbp");
    public static PhysicalRegistor rsi = new NASMRegister(1, "rsi");
    public static PhysicalRegistor rdi = new NASMRegister(1, "rdi");
    public static PhysicalRegistor r8 = new NASMRegister(1, "r8");
    public static PhysicalRegistor r9 = new NASMRegister(1, "r9");
    public static PhysicalRegistor r10 = new NASMRegister(1, "r10");
    public static PhysicalRegistor r11 = new NASMRegister(1, "r11");
    public static PhysicalRegistor r12 = new NASMRegister(1, "r12");
    public static PhysicalRegistor r13 = new NASMRegister(1, "r13");
    public static PhysicalRegistor r14 = new NASMRegister(1, "r14");
    public static PhysicalRegistor r15 = new NASMRegister(1, "r15");

    public static int size() {
        return 8;
    }

    public NASMRegister(int identity, String name) {
        super(identity, name);
    }
}
