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

fibo:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 432
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-304)], rbx
	mov    qword [rbp + (-328)], rsi
fibo_enter_0:
														;jump %entry
	jmp    fibo_entry_1
fibo_entry_1:
														;$t3 = slt $p0 2
	mov    r10, qword [rbp+(-8)]
	mov    r11, 2
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t3 %if_true %if_false
	cmp    rbx, 0
	jnz    fibo_if_true_2
	jz     fibo_if_false_3
fibo_if_true_2:
														;ret $p0
	mov    rax, qword [rbp+(-8)]
	mov    rax, rax
	jmp    fibo_exit_5
														;jump %exit
	jmp    fibo_exit_5
fibo_if_false_3:
														;jump %if_merge
	jmp    fibo_if_merge_4
fibo_if_merge_4:
														;$t4 = sub $p0 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	mov    r10, r10
	mov    r11, r11
	sub    r10, r11
	mov    rbx, r10
														;$t5 = call fibo $t4
	mov    qword [rbp + (-144)], rbx
	mov    qword [rbp + (-168)], rsi
	mov    rdi, rbx
	call   fibo
	mov    rbx, qword [rbp + (-144)]
	mov    rsi, qword [rbp + (-168)]
	mov    rsi, rax
														;$t6 = sub $p0 2
	mov    r10, qword [rbp+(-8)]
	mov    r11, 2
	mov    r10, r10
	mov    r11, r11
	sub    r10, r11
	mov    rbx, r10
														;$t7 = call fibo $t6
	mov    qword [rbp + (-144)], rbx
	mov    qword [rbp + (-168)], rsi
	mov    rdi, rbx
	call   fibo
	mov    rbx, qword [rbp + (-144)]
	mov    rsi, qword [rbp + (-168)]
	mov    rbx, rax
														;$t8 = add $t5 $t7
	mov    r10, rsi
	mov    r11, rbx
	add    r10, r11
	mov    rbx, r10
														;ret $t8
	mov    rax, rbx
	jmp    fibo_exit_5
														;jump %exit
	jmp    fibo_exit_5
fibo_exit_5:
	mov    rbx, qword [rbp + (-304)]
	mov    rsi, qword [rbp + (-328)]
	leave
	ret
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 440
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-312)], rbx
	mov    qword [rbp + (-336)], rsi
main_enter_0:
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t1 = move 32
	mov    rax, 32
	mov    rbx, rax
														;$t9 = call fibo $t1
	mov    qword [rbp + (-152)], rbx
	mov    qword [rbp + (-176)], rsi
	mov    rdi, rbx
	call   fibo
	mov    rbx, qword [rbp + (-152)]
	mov    rsi, qword [rbp + (-176)]
	mov    rbx, rax
														;$t10 = call __builtin_toString $t9
	mov    qword [rbp + (-152)], rbx
	mov    qword [rbp + (-176)], rsi
	mov    rdi, rbx
	call   __builtin_toString
	mov    rbx, qword [rbp + (-152)]
	mov    rsi, qword [rbp + (-176)]
	mov    rbx, rax
														;call __builtin_println $t10
	mov    qword [rbp + (-152)], rbx
	mov    qword [rbp + (-176)], rsi
	mov    rdi, rbx
	call   __builtin_println
	mov    rbx, qword [rbp + (-152)]
	mov    rsi, qword [rbp + (-176)]
														;$t2 = move 0
	mov    rax, 0
	mov    rsi, rax
														;jump %for_condition
	jmp    main_for_condition_2
main_for_condition_2:
														;$t11 = sle $t2 100
	mov    r11, 100
	mov    r10, rsi
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t11 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_3
	jz     main_for_after_5
main_for_body_3:
														;$t12 = call fibo 30
	mov    qword [rbp + (-152)], rbx
	mov    qword [rbp + (-176)], rsi
	mov    rax, 30
	mov    rdi, rax
	call   fibo
	mov    rbx, qword [rbp + (-152)]
	mov    rsi, qword [rbp + (-176)]
	mov    rbx, rax
														;$t13 = call __builtin_toString $t12
	mov    qword [rbp + (-152)], rbx
	mov    qword [rbp + (-176)], rsi
	mov    rdi, rbx
	call   __builtin_toString
	mov    rbx, qword [rbp + (-152)]
	mov    rsi, qword [rbp + (-176)]
	mov    rbx, rax
														;call __builtin_println $t13
	mov    qword [rbp + (-152)], rbx
	mov    qword [rbp + (-176)], rsi
	mov    rdi, rbx
	call   __builtin_println
	mov    rbx, qword [rbp + (-152)]
	mov    rsi, qword [rbp + (-176)]
														;jump %for_loop
	jmp    main_for_loop_4
main_for_loop_4:
														;$t2 = add $t2 1
	mov    r11, 1
	mov    r10, rsi
	mov    r11, r11
	add    r10, r11
	mov    rsi, r10
														;jump %for_condition
	jmp    main_for_condition_2
main_for_after_5:
														;ret 0
	mov    rax, 0
	mov    rax, rax
	jmp    main_exit_6
														;jump %exit
	jmp    main_exit_6
main_exit_6:
	mov    rbx, qword [rbp + (-312)]
	mov    rsi, qword [rbp + (-336)]
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

