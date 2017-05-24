package BackEnd.Translator.NASM;

import BackEnd.Allocator.PhysicalRegistor;

/**
 * Created by tan on 5/23/17.
 */
public class NASMRegister extends PhysicalRegistor {
    //RAX RCX RDX RBX RSP RBP RSI RDI
    //EAX ECX EDX EBX ESP EBP ESI EDI
    public static PhysicalRegistor rax = new NASMRegister(0, "eax");
    public static PhysicalRegistor rcx = new NASMRegister(1, "ecx");
    public static PhysicalRegistor rdx = new NASMRegister(1, "edx");
    public static PhysicalRegistor rbx = new NASMRegister(1, "ebx");
    public static PhysicalRegistor rsp = new NASMRegister(1, "esp");
    public static PhysicalRegistor rbp = new NASMRegister(1, "ebp");
    public static PhysicalRegistor rsi = new NASMRegister(1, "esi");
    public static PhysicalRegistor rdi = new NASMRegister(1, "edi");
    public static PhysicalRegistor r8 = new NASMRegister(1, "r8d");
    public static PhysicalRegistor r9 = new NASMRegister(1, "r9d");
    public static PhysicalRegistor r10 = new NASMRegister(1, "r10d");
    public static PhysicalRegistor r11 = new NASMRegister(1, "r11d");
    public static PhysicalRegistor r12 = new NASMRegister(1, "r12d");
    public static PhysicalRegistor r13 = new NASMRegister(1, "r13d");
    public static PhysicalRegistor r14 = new NASMRegister(1, "r14d");
    public static PhysicalRegistor r15 = new NASMRegister(1, "r15d");

    public static int size() {
        return 4;
    }

    public NASMRegister(int identity, String name) {
        super(identity, name);
    }
}
