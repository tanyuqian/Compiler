	global main
	extern malloc
	extern printf
	extern puts

SECTION .text

__builtin_print:
    mov     rsi, rdi
    mov     rdi, STRING_FORMAT_CUR_LINE
    mov     rax, 0
    call    printf
    ret

main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 64
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
main_enter_0:
														;$g0(a) = move $1
	mov    r11, CONST_STRING_1
	mov    qword [rel a], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;call __builtin_print $g0(a)
	mov    rdi, qword [rel a]
	call   __builtin_print
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_2
														;jump %exit
	jmp    main_exit_2
														;%exit
main_exit_2:
	leave
	ret


SECTION .data
CONST_STRING_1:
	db "aaaaaa", 0
a:
	dq 0
STRING_FORMAT_CUR_LINE:
	db "%s", 0
