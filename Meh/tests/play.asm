	global main
	extern malloc
	extern printf
	extern puts
	extern scanf
	extern gets

SECTION .text

__builtin_print:
    mov     rsi, rdi
    mov     rdi, STRING_FORMAT
    mov     rax, 0
    call    printf
    ret

__builtin_println:
    call    puts
    ret

__builtin_printInt:
    mov     rsi, rdi
    mov     rdi, INTEGER_FORMAT_NEXT_LINE
    mov     rax, 0
    call    printf
    ret

__builtin_getInt:
	push    rbp
	mov     rbp, rsp
	sub     rsp, 24

    lea     rsi, [rbp-8]
    mov     rdi, INTEGER_FORMAT
    mov     rax, 0
    call    scanf

    mov     rax, qword [rbp-8]

	leave
	ret

__builtin_getChar:
	push    rbp
	mov     rbp, rsp
	sub     rsp, 24

    lea     rsi, [rbp-8]
    mov     rdi, CHAR_FORMAT
    mov     rax, 0
    call    scanf

    movzx     rax, byte [rbp-8]

	leave
	ret

__builtin_getString:
	push    rbp
	mov     rbp, rsp
	sub     rsp, 24



	leave
	ret

cc:
db "%s", 10, 0

main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 88
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
main_enter_0:
														;$g0(a) = move $2
	mov    r11, CONST_STRING_2
	mov    qword [rel a], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t1 = move 5
	mov    r11, 5
	mov    qword [rbp+(-72)], r11
														;jump %while_loop
	jmp    main_while_loop_2
														;%while_loop
main_while_loop_2:
														;$t3 = sgt $t1 0
	mov    r11, qword [rbp+(-72)]
	cmp    r11, 0
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t3 %while_body %while_after
	cmp    qword [rbp+(-64)], 0
	jnz    main_while_body_3
	jz     main_while_after_4
														;%while_body
main_while_body_3:
														;$t4 = sub $t1 1
	mov    r11, qword [rbp+(-72)]
	sub    r11, 1
	mov    qword [rbp+(-80)], r11
														;$t1 = move $t4
	mov    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-72)], r11
														;jump %while_loop
	jmp    main_while_loop_2
														;%while_after
main_while_after_4:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_5
														;jump %exit
	jmp    main_exit_5
														;%exit
main_exit_5:
	leave
	ret


SECTION .data
CONST_STRING_2:
	db "aaaaaa", 0
a:
	dq 0
STRING_FORMAT:
	db "%s", 0
INTEGER_FORMAT_NEXT_LINE:
	db "%lld", 10, 0
INTEGER_FORMAT:
	db "%lld", 0
CHAR_FORMAT:
	db "%c", 0