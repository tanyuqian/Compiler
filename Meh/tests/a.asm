





default rel

global main

extern printf


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-10H], -5
        mov     qword [rbp-8H], 3
        mov     rax, qword [rbp-10H]
        cqo
        idiv    qword [rbp-8H]
        mov     rax, rdx
        mov     rsi, rax
        mov     edi, L_001
        mov     eax, 0
        call    printf
        mov     eax, 0
        leave
        ret



SECTION .data   


SECTION .bss    


SECTION .rodata 

L_001:
        db 25H, 64H, 0AH, 00H


