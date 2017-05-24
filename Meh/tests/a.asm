





default rel

global main
global d


SECTION .text   

main:
        push    rbp
        mov     rbp, rsp
        mov     dword [rbp-10H], 5
        mov     dword [rbp-0CH], 1
        mov     dword [rbp-8H], 2
        mov     eax, dword [rbp-10H]
        cmp     eax, dword [rbp-0CH]
        sete    al
        movzx   eax, al
        mov     dword [rbp-4H], eax
        mov     eax, 0
        pop     rbp
        ret



SECTION .data   align=4

d:
        dd 00000002H


SECTION .bss    


