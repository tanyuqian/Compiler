	global main
	extern malloc
	extern printf
	extern puts

SECTION .text
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 48
	mov    dword [rbp-4H], edi
	mov    dword [rbp-8H], esi
	mov    dword [rbp-0CH], edx
	mov    dword [rbp-10H], ecx
	mov    dword [rbp-14H], r8d
	mov    dword [rbp-18H], r9d
main_enter_0:
	mov    r11d, 2
	mov    dword [rel d], r11d
	jmp    main_entry_1
main_entry_1:
	mov    r11d, 5
	mov    dword [rbp+(-40)], r11d
	mov    r11d, 1
	mov    dword [rbp+(-36)], r11d
	mov    r11d, 2
	mov    dword [rbp+(-32)], r11d
	mov    r11d, dword [rbp+(-40)]
	cmp    r11d, dword [rbp+(-36)]
	setg   al
	movzx  r11d, al
	mov    dword [rbp+(-44)], r11d
	cmp    dword [rbp+(-44)], 0
	jnz    main_if_true_2
	jz     main_if_false_3
main_if_true_2:
	mov    r11d, dword [rbp+(-40)]
	mov    dword [rbp+(-32)], r11d
	jmp    main_if_merge_4
main_if_false_3:
	mov    r11d, dword [rbp+(-36)]
	mov    dword [rbp+(-32)], r11d
	jmp    main_if_merge_4
main_if_merge_4:

        mov     eax, dword [rbp-32]
        mov     esi, eax
        mov     edi, L_003
        mov     eax, 0
        call    printf
        mov     eax, 0

	mov    eax, 0
	leave
	ret
	jmp    main_exit_5
	jmp    main_exit_5
main_exit_5:
	leave
	ret


SECTION .data
d: dd 0

L_003:
        db 25H, 64H, 0AH, 00H


