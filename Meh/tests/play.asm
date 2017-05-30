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

tak:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 296
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-208)], r8
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
tak_enter_0:
														;jump %entry
	jmp    tak_entry_1
tak_entry_1:
														;$t6 = slt $p1 $p0
	mov    rax, qword [rbp+(-16)]
	mov    r11, qword [rbp+(-8)]
	mov    rsi, rax
	cmp    rax, r11
	setl   al
	movzx    rsi, al
														;br $t6 %if_true %if_false
	cmp    rsi, 0
	jnz    tak_if_true_2
	jz     tak_if_false_3
tak_if_true_2:
														;$t7 = sub $p0 1
	mov    rax, qword [rbp+(-8)]
	mov    r11, 1
	mov    rsi, rax
	sub    rsi, r11
														;$t8 = call tak $t7 $p1 $p2
	mov    qword [rbp + (-208)], r8
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	mov    rdi, rsi
	mov    rax, qword [rbp+(-16)]
	mov    rsi, rax
	mov    rax, qword [rbp+(-24)]
	mov    rdx, rax
	call   tak
	mov    r8, qword [rbp + (-208)]
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	mov    r8, rax
														;$t9 = sub $p1 1
	mov    rax, qword [rbp+(-16)]
	mov    r11, 1
	mov    rsi, rax
	sub    rsi, r11
														;$t10 = call tak $t9 $p2 $p0
	mov    qword [rbp + (-208)], r8
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	mov    rdi, rsi
	mov    rax, qword [rbp+(-24)]
	mov    rsi, rax
	mov    rax, qword [rbp+(-8)]
	mov    rdx, rax
	call   tak
	mov    r8, qword [rbp + (-208)]
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	mov    rdi, rax
														;$t11 = sub $p2 1
	mov    rax, qword [rbp+(-24)]
	mov    r11, 1
	mov    rsi, rax
	sub    rsi, r11
														;$t12 = call tak $t11 $p0 $p1
	mov    qword [rbp + (-208)], r8
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	mov    rdi, rsi
	mov    rax, qword [rbp+(-8)]
	mov    rsi, rax
	mov    rax, qword [rbp+(-16)]
	mov    rdx, rax
	call   tak
	mov    r8, qword [rbp + (-208)]
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	mov    rbx, rax
														;$t13 = call tak $t8 $t10 $t12
	mov    qword [rbp + (-208)], r8
	mov    qword [rbp + (-168)], rbx
	mov    qword [rbp + (-200)], rdi
	mov    qword [rbp + (-192)], rsi
	mov    rdi, r8
	mov    rsi, rdi
	mov    rdx, rbx
	call   tak
	mov    r8, qword [rbp + (-208)]
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	mov    rbx, rax
														;$t14 = add $t13 1
	mov    r11, 1
	mov    rbx, rbx
	add    rbx, r11
														;ret $t14
	mov    rax, rbx
	jmp    tak_exit_5
														;jump %exit
	jmp    tak_exit_5
tak_if_false_3:
														;ret $p2
	mov    rax, qword [rbp+(-24)]
	mov    rax, rax
	jmp    tak_exit_5
														;jump %exit
	jmp    tak_exit_5
tak_exit_5:
	mov    r8, qword [rbp + (-208)]
	mov    rbx, qword [rbp + (-168)]
	mov    rdi, qword [rbp + (-200)]
	mov    rsi, qword [rbp + (-192)]
	leave
	ret
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 288
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
main_enter_0:
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t15 = call __builtin_getInt
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
	call   __builtin_getInt
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
	mov    rdi, rax
														;$t3 = move $t15
	mov    rdi, rdi
														;$t16 = call __builtin_getInt
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
	call   __builtin_getInt
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
	mov    rsi, rax
														;$t4 = move $t16
	mov    rsi, rsi
														;$t17 = call __builtin_getInt
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
	call   __builtin_getInt
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
	mov    rbx, rax
														;$t5 = move $t17
	mov    rbx, rbx
														;$t18 = call tak $t3 $t4 $t5
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
	mov    rdi, rdi
	mov    rsi, rsi
	mov    rdx, rbx
	call   tak
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
	mov    rbx, rax
														;$t19 = call __builtin_toString $t18
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
	mov    rdi, rbx
	call   __builtin_toString
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
	mov    rbx, rax
														;call __builtin_println $t19
	mov    qword [rbp + (-160)], rbx
	mov    qword [rbp + (-192)], rdi
	mov    qword [rbp + (-184)], rsi
	mov    rdi, rbx
	call   __builtin_println
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
														;ret 0
	mov    rax, 0
	mov    rax, rax
	jmp    main_exit_2
														;jump %exit
	jmp    main_exit_2
main_exit_2:
	mov    rbx, qword [rbp + (-160)]
	mov    rdi, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-184)]
	leave
	ret
SECTION .data
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