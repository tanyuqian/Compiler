





default rel

global main

extern puts


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-10H], 15926
        mov     qword [rbp-8H], 3
        mov     edi, L_001
        call    puts
        mov     eax, 0
        leave
        ret



SECTION .data   


SECTION .bss    


SECTION .rodata 

L_001:
        db 20H, 00H


