	global main
	extern malloc
	extern printf
	extern puts
	extern scanf
	extern gets
	extern strlen
	extern strcpy
	extern sscanf
	extern sprintf
	extern strcat
	extern strcmp

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

    mov     rdi, NEXT_LINE
    mov     rax, 0
    call    scanf

    mov     rdi, 400
    call    malloc
    mov     qword [rbp-8], rax
    mov     rdi, rax
    call    gets

    mov     rax, qword[rbp-8]

    leave
    ret

__builtin_getStringLength:
    mov rax, 0
    call    strlen
    ret

__builtin_getSubstring:
    push    rbp
    mov     rbp, rsp
    sub     rsp, 48

    mov     qword[rbp-8], r12
    mov     qword[rbp-16], r13
    mov     qword[rbp-24], r14
    mov     qword[rbp-32], r15

    mov     r15, rsi
    mov     r14, rdi


    mov     r12, rdx
    add     r12, 1
    sub     r12, rsi

    mov     rdi, 400
    call    malloc
    mov     r13, rax

    mov     rsi, r14
    add     rsi, r15

    mov     rdi, r13
    call    strcpy


    mov     r15, r13
    add     r13, r12
    mov     byte [r13], 0

    mov     rax, r15

    mov     r12, qword[rbp-8]
    mov     r13, qword[rbp-16]
    mov     r14, qword[rbp-24]
    mov     r15, qword[rbp-32]

    leave
    ret

__builtin_parseInt:
	push    rbp
	mov     rbp, rsp
	sub     rsp, 24

    lea     rdx, [rbp-8]
    mov     rsi, INTEGER_FORMAT
    mov     rax, 0
    call    sscanf

    mov     rax, qword [rbp-8]

	leave
	ret

__builtin_ord:
    add     rdi, rsi
    movzx   rax, byte [rdi]
    ret

__builtin_toString:

    push    rbp
    mov     rbp, rsp
    sub     rsp, 48

    mov     qword[rbp-8], r12
    mov     qword[rbp-16], r13
    mov     qword[rbp-24], r14
    mov     qword[rbp-32], r15

    mov     r12, rdi
    mov     rdi, 12
    call    malloc
    mov     r13, rax

    mov     rdx, r12
    mov     rsi, INTEGER_FORMAT
    mov     rdi, r13

    mov     rax, 0
    call    sprintf

    mov     rax, r13


    mov     r12, qword[rbp-8]
    mov     r13, qword[rbp-16]
    mov     r14, qword[rbp-24]
    mov     r15, qword[rbp-32]

    leave
    ret

__builtin_string_concat:
    push    rbp
    mov     rbp, rsp
    sub     rsp, 80

    mov     qword[rbp-24], rdi
    mov     qword[rbp-16], rsi

    mov     rdi, qword[rbp-24]
    call    __builtin_getStringLength
    mov     qword[rbp-32], rax

    mov     rdi, qword[rbp-16]
    call    __builtin_getStringLength
    add     qword[rbp-32], rax
    add     qword[rbp-32], 3

    mov     rdi, qword[rbp-32]
    call    malloc
    mov     qword [rbp-8], rax

    mov     rdi, rax
    mov     rsi, qword[rbp-24]
    call    strcpy

    mov     rdi, qword[rbp-8]
    mov     rsi, qword[rbp-16]
    call    strcat

    mov     rax, qword[rbp-8]

    leave
    ret

__builtin_string_equalTo:
    call   strcmp
    mov    r11, rax

    cmp    r11, 0
    jz     RETURN_1
    mov    rax, 0
    ret

__builtin_string_greaterThan:
    call   strcmp
    mov    r11, 0
    mov    r11d, eax

    cmp    r11d, 0
    jg     RETURN_1
    mov    rax, 0
    ret

__builtin_string_greaterThanOrEqualTo:
    call   strcmp
    mov    r11, 0
    mov    r11d, eax

    cmp    r11d, 0
    jge    RETURN_1
    mov    rax, 0
    ret

__builtin_string_lessThan:
    call   strcmp
    mov    r11, 0
    mov    r11d, eax

    cmp    r11d, 0
    jl     RETURN_1
    mov    rax, 0
    ret

__builtin_string_lessThanOrEqualTo:
    call   strcmp
    mov    r11, 0
    mov    r11d, eax

    cmp    r11d, 0
    jna    RETURN_1
    mov    rax, 0
    ret


RETURN_1:
    mov     rax, 1
    ret

__builtin_getArraySize:
    mov     rax, qword [rdi-8]
    ret

main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 296
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
main_enter_0:
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t3 = move 32
	mov    rax, 32
	mov    rbx, rax
														;$t3 = add $t3 8
	mov    r11, 8
	mov    rbx, rbx
	add    rbx, r11
														;$t2 = alloc $t3
	mov    rdi, rbx
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	call   malloc
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	mov    rbx, rax
														;store 8 $t2 4 0
	mov    rax, 4
	mov    r11, rbx
	add    r11, 0
	mov    qword [r11], rax
														;$t2 = add $t2 8
	mov    r11, 8
	mov    rbx, rbx
	add    rbx, r11
														;$t1 = move $t2
	mov    rsi, rbx
														;$t5 = move 16
	mov    rax, 16
	mov    rbx, rax
														;$t4 = add $t1 $t5
	mov    rdi, rsi
	add    rdi, rbx
														;store 8 $t4 2 0
	mov    rax, 2
	mov    r11, rdi
	add    r11, 0
	mov    qword [r11], rax
														;$g0(a) = move $t1
	mov    qword [rel GV_a], rsi
														;$t7 = move 16
	mov    rax, 16
	mov    rbx, rax
														;$t6 = add $g0(a) $t7
	mov    rax, qword [rel GV_a]
	mov    rsi, rax
	add    rsi, rbx
														;$t8 = load 8 $t6 0
	mov    r11, rsi
	add    r11, 0
	mov    rbx, qword [r11]
														;$t9 = call __builtin_toString $t8
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	mov    rdi, rbx
	call   __builtin_toString
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	mov    rbx, rax
														;call __builtin_println $t9
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	mov    rdi, rbx
	call   __builtin_println
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
														;ret 0
	mov    rax, 0
	mov    rax, rax
	jmp    main_exit_2
														;jump %exit
	jmp    main_exit_2
main_exit_2:
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	leave
	ret
SECTION .data
GV_a:
	dq 0
STRING_FORMAT:
	db "%s", 0
INTEGER_FORMAT_NEXT_LINE:
	db "%lld", 10, 0
INT_FORMAT_NEXT_LINE:
	db "%d", 10, 0
INTEGER_FORMAT:
	db "%lld", 0
CHAR_FORMAT:
	db "%c", 0
NEXT_LINE:
	db 10, 0

