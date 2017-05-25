





default rel

global _Z4funcii
global main

extern printf


SECTION .text   

_Z4funcii:
        push    rbp
        mov     rbp, rsp
        mov     dword [rbp-4H], edi
        mov     dword [rbp-8H], esi
        mov     edx, dword [rbp-4H]
        mov     eax, edx
        shl     eax, 2
        add     eax, edx
        add     eax, eax
        mov     edx, eax
        mov     eax, dword [rbp-8H]
        add     eax, edx
        pop     rbp
        ret


main:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     dword [rbp-0CH], 5
        mov     dword [rbp-8H], 3
        mov     edx, dword [rbp-8H]
        mov     eax, dword [rbp-0CH]
        mov     esi, edx
        mov     edi, eax
        call    _Z4funcii
        mov     dword [rbp-4H], eax
        mov     eax, dword [rbp-4H]
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


