





default rel

global printff
global main

extern printf


SECTION .text

printff:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 32
        mov     dword [rbp-4H], edi
        mov     dword [rbp-8H], esi
        mov     dword [rbp-0CH], edx
        mov     dword [rbp-10H], ecx
        mov     dword [rbp-14H], r8d
        mov     dword [rbp-18H], r9d
        mov     eax, dword [rbp-18H]
        mov     esi, eax
        mov     edi, L_001
        mov     eax, 0
        call    printf
        nop
        leave
        ret


main:
        push    rbp
        mov     rbp, rsp
        push    8
        push    7
        mov     r9d, 6
        mov     r8d, 5
        mov     ecx, 4
        mov     edx, 3
        mov     esi, 2
        mov     edi, 1
        call    printff
        add     rsp, 16
        mov     eax, 0
        leave
        ret



SECTION .data


SECTION .bss


SECTION .rodata

L_001:
        db 25H, 64H, 0AH, 00H


