package BackEnd.Translator.NASM;

/**
 * Created by tan on 5/23/17.
 */
public class NASMRegister extends PhysicalRegistor {
    //RAX RCX RDX RBX RSP RBP RSI RDI
    //EAX ECX EDX EBX ESP EBP ESI EDI
    public static PhysicalRegistor rax = new NASMRegister(1, "rax", false);
    public static PhysicalRegistor rcx = new NASMRegister(2, "rcx", false);
    public static PhysicalRegistor rdx = new NASMRegister(3, "rdx", false);
    public static PhysicalRegistor rbx = new NASMRegister(4, "rbx", true);
    public static PhysicalRegistor rsp = new NASMRegister(5, "rsp", true);
    public static PhysicalRegistor rbp = new NASMRegister(6, "rbp", true);
    public static PhysicalRegistor rsi = new NASMRegister(7, "rsi", false);
    public static PhysicalRegistor rdi = new NASMRegister(8, "rdi", false);
    public static PhysicalRegistor r8 = new NASMRegister(9, "r8", false);
    public static PhysicalRegistor r9 = new NASMRegister(10, "r9", false);
    public static PhysicalRegistor r10 = new NASMRegister(11, "r10", false);
    public static PhysicalRegistor r11 = new NASMRegister(12, "r11", false);
    public static PhysicalRegistor r12 = new NASMRegister(13, "r12", true);
    public static PhysicalRegistor r13 = new NASMRegister(14, "r13", true);
    public static PhysicalRegistor r14 = new NASMRegister(15, "r14", true);
    public static PhysicalRegistor r15 = new NASMRegister(16, "r15", true);

    public static int size() {
        return 8;
    }

    public NASMRegister(int identity, String name, boolean isCalleeSaved) {
        super(identity, name, isCalleeSaved);
    }
}
