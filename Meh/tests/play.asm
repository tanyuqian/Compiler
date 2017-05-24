	global main
	extern malloc
	extern printf
	extern puts

SECTION .text
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 60
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
	mov    dword [rbp+(-44)], r11d
	mov    r11d, 1
	mov    dword [rbp+(-40)], r11d
	mov    r11d, 2
	mov    dword [rbp+(-36)], r11d
	mov    r11d, dword [rbp+(-44)]
	mov    eax, dword [rbp+(-44)]
	cdq
	idiv   dword [rbp+(-40)]
	mov    r11d, eax
	mov    dword [rbp+(-56)], r11d
	mov    r11d, dword [rbp+(-36)]
	mov    eax, dword [rbp+(-36)]
	cdq
	idiv   dword [rel d]
	mov    r11d, eax
	mov    dword [rbp+(-52)], r11d
	mov    r11d, dword [rbp+(-56)]
	add    r11d, dword [rbp+(-52)]
	mov    dword [rbp+(-48)], r11d
	mov    r11d, dword [rbp+(-48)]
	mov    dword [rbp+(-32)], r11d
	mov    eax, 0

	mov     eax, dword [rbp-32]
            mov     esi, eax
            mov     edi, L_001
            mov     eax, 0
            call    printf

	leave
	ret
	jmp    main_exit_2
	jmp    main_exit_2
main_exit_2:
	leave
	ret


SECTION .data
d: dd 0

L_001: db "%d", 10, 0
