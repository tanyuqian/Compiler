





default rel

global main

extern __stack_chk_fail
extern getchar


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        add     rsp, -128


        mov     rax, qword [fs:abs 28H]
        mov     qword [rbp-8H], rax
        xor     eax, eax
        mov     dword [rbp-74H], 0
L_001:  call    getchar
        mov     byte [rbp-75H], al
        mov     eax, dword [rbp-74H]
        cdqe
        movzx   edx, byte [rbp-75H]
        mov     byte [rbp+rax-70H], dl
        add     dword [rbp-74H], 1
        cmp     byte [rbp-75H], 10
        jz      L_002
        jmp     L_001

L_002:  nop
        mov     eax, 0
        mov     rcx, qword [rbp-8H]


        xor     rcx, qword [fs:abs 28H]
        jz      L_003
        call    __stack_chk_fail
L_003:  leave
        ret



SECTION .data   


SECTION .bss    


