





default rel

global main

extern strcpy
extern _Znam


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     edi, 100
        call    _Znam
        mov     qword [rbp-10H], rax
        mov     edi, 100
        call    _Znam
        mov     qword [rbp-8H], rax


        mov     rax, qword [rbp-8H]
        mov     byte [rax], 98
        mov     rax, qword [rbp-8H]
        add     rax, 1
        mov     byte [rax], 0
        mov     rdx, qword [rbp-8H]
        mov     rax, qword [rbp-10H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcpy
        mov     eax, 0
        leave
        ret



SECTION .data   


SECTION .bss    


