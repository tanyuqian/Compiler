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
	sub    rsp, 416
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
main_enter_0:
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t0 = move 1000000
	mov    rax, 1000000
	mov    rdi, rax
														;call __builtin_print $4
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rax, CONST_STRING_4
	mov    rdi, rax
	call   __builtin_print
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
														;$t5 = mul $t0 2
	mov    r11, 2
	mov    rbx, rdi
	imul    rbx, r11
														;$t6 = add $t5 1
	mov    r11, 1
	mov    rbx, rbx
	add    rbx, r11
														;$t7 = call __builtin_toString $t6
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	call   __builtin_toString
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t9 = call __builtin_string_concat $t7 $8
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	mov    rax, CONST_STRING_8
	mov    rsi, rax
	call   __builtin_string_concat
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rsi, rax
														;$t10 = call __builtin_toString $t0
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rdi
	call   __builtin_toString
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t11 = call __builtin_string_concat $t9 $t10
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rsi
	mov    rsi, rbx
	call   __builtin_string_concat
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;call __builtin_println $t11
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	call   __builtin_println
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
														;$t2 = move 1
	mov    rax, 1
	mov    r9, rax
														;$t1 = move 0
	mov    rax, 0
	mov    rsi, rax
														;jump %for_condition
	jmp    main_for_condition_2
main_for_condition_2:
														;$t12 = slt $t1 $t0
	mov    rbx, rsi
	cmp    rsi, rdi
	setl   al
	movzx    rbx, al
														;br $t12 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_3
	jz     main_for_after_8
main_for_body_3:
														;$t13 = call __builtin_toString $t2
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, r9
	call   __builtin_toString
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t15 = call __builtin_string_concat $t13 $14
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	mov    rax, CONST_STRING_14
	mov    rsi, rax
	call   __builtin_string_concat
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t16 = add $t2 1
	mov    r11, 1
	mov    r8, r9
	add    r8, r11
														;$t17 = call __builtin_toString $t16
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, r8
	call   __builtin_toString
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    r8, rax
														;$t18 = call __builtin_string_concat $t15 $t17
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	mov    rsi, r8
	call   __builtin_string_concat
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t20 = call __builtin_string_concat $t18 $19
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	mov    rax, CONST_STRING_19
	mov    rsi, rax
	call   __builtin_string_concat
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    r8, rax
														;$t21 = add $t2 2
	mov    r11, 2
	mov    rbx, r9
	add    rbx, r11
														;$t22 = neg $t21
	mov    rbx, rbx
	neg    rbx
														;$t23 = call __builtin_toString $t22
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	call   __builtin_toString
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t24 = call __builtin_string_concat $t20 $t23
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, r8
	mov    rsi, rbx
	call   __builtin_string_concat
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	mov    rbx, rax
														;$t3 = move $t24
	mov    rbx, rbx
														;$t25 = rem $t1 100000
	mov    r11, 100000
	mov    r8, rsi
	mov    rax, r8
	cqo
	idiv   r11
	mov    r8, rdx
														;$t26 = seq $t25 0
	mov    r11, 0
	mov    r8, r8
	cmp    r8, r11
	sete   al
	movzx    r8, al
														;br $t26 %if_true %if_false
	cmp    r8, 0
	jnz    main_if_true_4
	jz     main_if_false_5
main_if_true_4:
														;call __builtin_println $t3
	mov    qword [rbp + (-328)], r8
	mov    qword [rbp + (-320)], rdi
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-312)], rsi
	mov    qword [rbp + (-336)], r9
	mov    rdi, rbx
	call   __builtin_println
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
														;jump %if_merge
	jmp    main_if_merge_6
main_if_merge_6:
														;$t27 = add $t2 2
	mov    r11, 2
	mov    rbx, r9
	add    rbx, r11
														;$t2 = move $t27
	mov    r9, rbx
														;jump %for_loop
	jmp    main_for_loop_7
main_for_loop_7:
														;$t1 = add $t1 1
	mov    r11, 1
	mov    rsi, rsi
	add    rsi, r11
														;jump %for_condition
	jmp    main_for_condition_2
main_if_false_5:
														;jump %if_merge
	jmp    main_if_merge_6
main_for_after_8:
														;ret 0
	mov    rax, 0
	mov    rax, rax
	mov    r8, qword [rbp + (-328)]
	mov    rdi, qword [rbp + (-320)]
	mov    rbx, qword [rbp + (-288)]
	mov    rsi, qword [rbp + (-312)]
	mov    r9, qword [rbp + (-336)]
	leave
	ret
														;jump %exit
	jmp    main_exit_9
main_exit_9:
SECTION .data
CONST_STRING_8:
	db 32, 0
CONST_STRING_14:
	db 32, 0
CONST_STRING_19:
	db 32, 0
CONST_STRING_4:
	db 112, 32, 99, 110, 102, 32, 0
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

