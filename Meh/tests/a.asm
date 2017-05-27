





default rel

global main

extern printf


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-10H], 15926
        mov     qword [rbp-8H], 3
        mov     rcx, qword [rbp-10H]
        mov     rdx, qword 7A7F4841139E6293H
        mov     rax, rcx
        imul    rdx
        sar     rdx, 8
        mov     rax, rcx
        sar     rax, 63
        sub     rdx, rax
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


