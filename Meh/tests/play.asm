	global main
	extern malloc
	extern printf
	extern puts

SECTION .text
func:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 80
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
func_enter_0:
	jmp    func_entry_1
func_entry_1:
	mov    r11, qword [rbp+(-8)]
	imul   r11, 10
	mov    qword [rbp+(-72)], r11
	mov    r11, qword [rbp+(-72)]
	add    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-64)], r11
	mov    rax, qword [rbp+(-64)]
	leave
	ret
	jmp    func_exit_2
	jmp    func_exit_2
func_exit_2:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 96
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
main_enter_0:
	jmp    main_entry_1
main_entry_1:
	mov    r11, 5
	mov    qword [rbp+(-80)], r11
	mov    r11, 3
	mov    qword [rbp+(-88)], r11
	mov    rdi, qword [rbp+(-80)]
	mov    rsi, qword [rbp+(-88)]
	call   func
	mov    qword [rbp+(-72)], rax
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-64)], r11

	mov     rax, qword [rbp-64]
            mov     rsi, rax
            mov     rdi, L_001
            mov     rax, 0
            call    printf

	mov    rax, 0
	leave
	ret
	jmp    main_exit_2
	jmp    main_exit_2
main_exit_2:
	leave
	ret


SECTION .data
L_001:
        db 25H, 64H, 0AH, 00H


