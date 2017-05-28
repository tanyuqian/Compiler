





default rel

global main

extern __stack_chk_fail
extern printf
extern scanf


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 32


        mov     rax, qword [fs:abs 28H]
        mov     qword [rbp-8H], rax
        xor     eax, eax
        mov     qword [rbp-20H], 5
        mov     qword [rbp-18H], 3
        lea     rdx, [rbp-18H]
        lea     rax, [rbp-20H]
        mov     rsi, rax
        mov     edi, L_002
        mov     eax, 0
        call    scanf
        mov     rdx, qword [rbp-20H]
        mov     rax, qword [rbp-18H]
        cmp     rdx, rax
        setg    al
        movzx   eax, al
        mov     qword [rbp-10H], rax
        mov     rax, qword [rbp-10H]
        mov     rsi, rax
        mov     edi, L_003
        mov     eax, 0
        call    printf
        mov     eax, 0
        mov     rcx, qword [rbp-8H]


        xor     rcx, qword [fs:abs 28H]
        jz      L_001
        call    __stack_chk_fail
L_001:  leave
        ret



SECTION .data   


SECTION .bss    


SECTION .rodata 

L_002:
        db 25H, 6CH, 6CH, 64H, 25H, 6CH, 6CH, 64H
        db 00H

L_003:
        db 25H, 6CH, 6CH, 64H, 0AH, 00H


