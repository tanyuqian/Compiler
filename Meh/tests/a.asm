





default rel

global main

extern printf


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     dword [rbp-8H], 5
        mov     dword [rbp-4H], 3
        mov     edx, dword [rbp-8H]
        mov     eax, dword [rbp-4H]
        add     eax, edx
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


