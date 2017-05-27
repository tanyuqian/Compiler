package BackEnd.Translator.NASM;

/**
 * Created by tan on 5/27/17.
 */
public class Template {
    public String templateStr;

    public Template() {
        templateStr = "\tglobal main\n" +
                "\textern malloc\n" +
                "\textern printf\n" +
                "\textern puts\n" +
                "\textern scanf\n" +
                "\textern gets\n" +
                "\textern strlen\n" +
                "\textern strcpy\n" +
                "\textern sscanf\n" +
                "\textern sprintf\n" +
                "\textern strcat\n" +
                "\textern strcmp\n" +
                "\n" +
                "SECTION .text\n" +
                "\n" +
                "__builtin_print:\n" +
                "    mov     rsi, rdi\n" +
                "    mov     rdi, STRING_FORMAT\n" +
                "    mov     rax, 0\n" +
                "    call    printf\n" +
                "    ret\n" +
                "\n" +
                "__builtin_println:\n" +
                "    call    puts\n" +
                "    ret\n" +
                "\n" +
                "__builtin_printInt:\n" +
                "    mov     rsi, rdi\n" +
                "    mov     rdi, INTEGER_FORMAT_NEXT_LINE\n" +
                "    mov     rax, 0\n" +
                "    call    printf\n" +
                "    ret\n" +
                "\n" +
                "__builtin_getInt:\n" +
                "\tpush    rbp\n" +
                "\tmov     rbp, rsp\n" +
                "\tsub     rsp, 24\n" +
                "\n" +
                "    lea     rsi, [rbp-8]\n" +
                "    mov     rdi, INTEGER_FORMAT\n" +
                "    mov     rax, 0\n" +
                "    call    scanf\n" +
                "\n" +
                "    mov     rax, qword [rbp-8]\n" +
                "\n" +
                "\tleave\n" +
                "\tret\n" +
                "\n" +
                "__builtin_getChar:\n" +
                "\tpush    rbp\n" +
                "\tmov     rbp, rsp\n" +
                "\tsub     rsp, 24\n" +
                "\n" +
                "    lea     rsi, [rbp-8]\n" +
                "    mov     rdi, CHAR_FORMAT\n" +
                "    mov     rax, 0\n" +
                "    call    scanf\n" +
                "\n" +
                "    movzx     rax, byte [rbp-8]\n" +
                "\n" +
                "\tleave\n" +
                "\tret\n" +
                "\n" +
                "__builtin_getString:\n" +
                "    push    rbp\n" +
                "    mov     rbp, rsp\n" +
                "    sub     rsp, 24\n" +
                "\n" +
                "    mov     rdi, 400\n" +
                "    call    malloc\n" +
                "    mov     qword [rbp-8], rax\n" +
                "    mov     rdi, rax\n" +
                "    call    gets\n" +
                "\n" +
                "    mov     rax, qword[rbp-8]\n" +
                "\n" +
                "    leave\n" +
                "    ret\n" +
                "\n" +
                "__builtin_getStringLength:\n" +
                "    mov rax, 0\n" +
                "    call    strlen\n" +
                "    ret\n" +
                "\n" +
                "__builtin_getSubstring:\n" +
                "    push    rbp\n" +
                "    mov     rbp, rsp\n" +
                "    sub     rsp, 48\n" +
                "\n" +
                "    mov     qword[rbp-8], r12\n" +
                "    mov     qword[rbp-16], r13\n" +
                "    mov     qword[rbp-24], r14\n" +
                "    mov     qword[rbp-32], r15\n" +
                "\n" +
                "    mov     r15, rsi\n" +
                "    mov     r14, rdi\n" +
                "\n" +
                "\n" +
                "    mov     r12, rdx\n" +
                "    add     r12, 1\n" +
                "    sub     r12, rsi\n" +
                "\n" +
                "    mov     rdi, 400\n" +
                "    call    malloc\n" +
                "    mov     r13, rax\n" +
                "\n" +
                "    mov     rsi, r14\n" +
                "    add     rsi, r15\n" +
                "\n" +
                "    mov     rdi, r13\n" +
                "    call    strcpy\n" +
                "\n" +
                "\n" +
                "    mov     r15, r13\n" +
                "    add     r13, r12\n" +
                "    mov     byte [r13], 0\n" +
                "\n" +
                "    mov     rax, r15\n" +
                "\n" +
                "    mov     r12, qword[rbp-8]\n" +
                "    mov     r13, qword[rbp-16]\n" +
                "    mov     r14, qword[rbp-24]\n" +
                "    mov     r15, qword[rbp-32]\n" +
                "\n" +
                "    leave\n" +
                "    ret\n" +
                "\n" +
                "__builtin_parseInt:\n" +
                "\tpush    rbp\n" +
                "\tmov     rbp, rsp\n" +
                "\tsub     rsp, 24\n" +
                "\n" +
                "    lea     rdx, [rbp-8]\n" +
                "    mov     rsi, INTEGER_FORMAT\n" +
                "    mov     rax, 0\n" +
                "    call    sscanf\n" +
                "\n" +
                "    mov     rax, qword [rbp-8]\n" +
                "\n" +
                "\tleave\n" +
                "\tret\n" +
                "\n" +
                "__builtin_ord:\n" +
                "    add     rdi, rsi\n" +
                "    movzx   rax, byte [rdi]\n" +
                "    ret\n" +
                "\n" +
                "__builtin_toString:\n" +
                "\n" +
                "    push    rbp\n" +
                "    mov     rbp, rsp\n" +
                "    sub     rsp, 48\n" +
                "\n" +
                "    mov     qword[rbp-8], r12\n" +
                "    mov     qword[rbp-16], r13\n" +
                "    mov     qword[rbp-24], r14\n" +
                "    mov     qword[rbp-32], r15\n" +
                "\n" +
                "    mov     r12, rdi\n" +
                "    mov     rdi, 400\n" +
                "    call    malloc\n" +
                "    mov     r13, rax\n" +
                "\n" +
                "    mov     rdx, r12\n" +
                "    mov     rsi, INTEGER_FORMAT\n" +
                "    mov     rdi, r13\n" +
                "\n" +
                "    mov     rax, 0\n" +
                "    call    sprintf\n" +
                "\n" +
                "    mov     rax, r13\n" +
                "\n" +
                "    leave\n" +
                "    ret\n" +
                "\n" +
                "__builtin_string_concat:\n" +
                "    call    strcat\n" +
                "    ret\n" +
                "\n" +
                "__builtin_string_equalTo:\n" +
                "    call   strcmp\n" +
                "    mov    r11, rax\n" +
                "\n" +
                "    cmp    r11, 0\n" +
                "    jz     RETURN_1\n" +
                "    mov    rax, 0\n" +
                "    ret\n" +
                "\n" +
                "__builtin_string_greaterThan:\n" +
                "    call   strcmp\n" +
                "    mov    r11, 0\n" +
                "    mov    r11d, eax\n" +
                "\n" +
                "    cmp    r11d, 0\n" +
                "    jg     RETURN_1\n" +
                "    mov    rax, 0\n" +
                "    ret\n" +
                "\n" +
                "__builtin_string_greaterThanOrEqualTo:\n" +
                "    call   strcmp\n" +
                "    mov    r11, 0\n" +
                "    mov    r11d, eax\n" +
                "\n" +
                "    cmp    r11d, 0\n" +
                "    jge    RETURN_1\n" +
                "    mov    rax, 0\n" +
                "    ret\n" +
                "\n" +
                "__builtin_string_lessThan:\n" +
                "    call   strcmp\n" +
                "    mov    r11, 0\n" +
                "    mov    r11d, eax\n" +
                "\n" +
                "    cmp    r11d, 0\n" +
                "    jl     RETURN_1\n" +
                "    mov    rax, 0\n" +
                "    ret\n" +
                "\n" +
                "__builtin_string_lessThanOrEqualTo:\n" +
                "    call   strcmp\n" +
                "    mov    r11, 0\n" +
                "    mov    r11d, eax\n" +
                "\n" +
                "    cmp    r11d, 0\n" +
                "    jna    RETURN_1\n" +
                "    mov    rax, 0\n" +
                "    ret\n" +
                "\n" +
                "\n" +
                "RETURN_1:\n" +
                "    mov     rax, 1\n" +
                "    ret\n" +
                "\n" +
                "__builtin_getArraySize:\n" +
                "    mov     rax, qword [rdi-8]\n" +
                "    ret\n" +
                "\n";
    }
}
