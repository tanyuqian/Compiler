





default rel

global main

extern printf
extern _Znam
extern malloc


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     edi, 400
        call    malloc

        mov     qword [rbp-8H], rax
        mov     rax, qword [rbp-8H]
        add     rax, 396
        mov     dword [rax], 5
        mov     rax, qword [rbp-8H]
        add     rax, 396
        mov     eax, dword [rax]
        mov     esi, eax
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


