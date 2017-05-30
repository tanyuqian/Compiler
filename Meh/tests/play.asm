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
	sub    rsp, 672
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-616)], r12
	mov    qword [rbp + (-568)], rsi
	mov    qword [rbp + (-624)], r13
	mov    qword [rbp + (-544)], rbx
	mov    qword [rbp + (-592)], r9
	mov    qword [rbp + (-584)], r8
	mov    qword [rbp + (-576)], rdi
main_enter_0:
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t7 = move 800
	mov    rax, 800
	mov    rbx, rax
														;$t7 = add $t7 8
	mov    r11, 8
	mov    r10, rbx
	mov    r11, r11
	add    r10, r11
	mov    rbx, r10
														;$t6 = alloc $t7
	mov    qword [rbp + (-616)], r12
	mov    qword [rbp + (-568)], rsi
	mov    qword [rbp + (-624)], r13
	mov    qword [rbp + (-544)], rbx
	mov    qword [rbp + (-592)], r9
	mov    qword [rbp + (-584)], r8
	mov    qword [rbp + (-576)], rdi
	mov    rdi, rbx
	call   malloc
	mov    r12, qword [rbp + (-616)]
	mov    rsi, qword [rbp + (-568)]
	mov    r13, qword [rbp + (-624)]
	mov    rbx, qword [rbp + (-544)]
	mov    r9, qword [rbp + (-592)]
	mov    r8, qword [rbp + (-584)]
	mov    rdi, qword [rbp + (-576)]
	mov    rsi, rax
														;store 8 $t6 100 0
	mov    rax, 100
	mov    r11, rsi
	add    r11, 0
	mov    qword [r11], rax
														;$t6 = add $t6 8
	mov    r11, 8
	mov    r10, rsi
	mov    r11, r11
	add    r10, r11
	mov    rsi, r10
														;$t0 = move $t6
	mov    rsi, rsi
														;$t1 = move 0
	mov    rax, 0
	mov    rdi, rax
														;jump %for_condition
	jmp    main_for_condition_2
main_for_condition_2:
														;$t8 = slt $t1 100
	mov    r11, 100
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t8 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_3
	jz     main_for_after_5
main_for_body_3:
														;$t10 = mul $t1 8
	mov    r11, 8
	mov    r10, rdi
	mov    r11, r11
	imul    r10, r11
	mov    r8, r10
														;$t9 = add $t0 $t10
	mov    r10, rsi
	mov    r11, r8
	add    r10, r11
	mov    rbx, r10
														;$t12 = move 800
	mov    rax, 800
	mov    r8, rax
														;$t12 = add $t12 8
	mov    r11, 8
	mov    r10, r8
	mov    r11, r11
	add    r10, r11
	mov    r8, r10
														;$t11 = alloc $t12
	mov    qword [rbp + (-616)], r12
	mov    qword [rbp + (-568)], rsi
	mov    qword [rbp + (-624)], r13
	mov    qword [rbp + (-544)], rbx
	mov    qword [rbp + (-592)], r9
	mov    qword [rbp + (-584)], r8
	mov    qword [rbp + (-576)], rdi
	mov    rdi, r8
	call   malloc
	mov    r12, qword [rbp + (-616)]
	mov    rsi, qword [rbp + (-568)]
	mov    r13, qword [rbp + (-624)]
	mov    rbx, qword [rbp + (-544)]
	mov    r9, qword [rbp + (-592)]
	mov    r8, qword [rbp + (-584)]
	mov    rdi, qword [rbp + (-576)]
	mov    r8, rax
														;store 8 $t11 100 0
	mov    rax, 100
	mov    r11, r8
	add    r11, 0
	mov    qword [r11], rax
														;$t11 = add $t11 8
	mov    r11, 8
	mov    r10, r8
	mov    r11, r11
	add    r10, r11
	mov    r8, r10
														;store 8 $t9 $t11 0
	mov    r11, rbx
	add    r11, 0
	mov    qword [r11], r8
														;jump %for_loop
	jmp    main_for_loop_4
main_for_loop_4:
														;$t13 = move $t1
	mov    rbx, rdi
														;$t1 = add $t1 1
	mov    r11, 1
	mov    r10, rdi
	mov    r11, r11
	add    r10, r11
	mov    rdi, r10
														;jump %for_condition
	jmp    main_for_condition_2
main_for_after_5:
														;$t3 = move 0
	mov    rax, 0
	mov    r8, rax
														;$t1 = move 0
	mov    rax, 0
	mov    rdi, rax
														;jump %for_condition
	jmp    main_for_condition_6
main_for_condition_6:
														;$t14 = slt $t1 100
	mov    r11, 100
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t14 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_7
	jz     main_for_after_13
main_for_body_7:
														;$t2 = move 0
	mov    rax, 0
	mov    r9, rax
														;jump %for_condition
	jmp    main_for_condition_8
main_for_condition_8:
														;$t15 = slt $t2 100
	mov    r11, 100
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t15 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_9
	jz     main_for_after_11
main_for_body_9:
														;$t17 = mul $t1 8
	mov    r11, 8
	mov    r10, rdi
	mov    r11, r11
	imul    r10, r11
	mov    rbx, r10
														;$t16 = add $t0 $t17
	mov    r10, rsi
	mov    r11, rbx
	add    r10, r11
	mov    r12, r10
														;$t18 = load 8 $t16 0
	mov    r11, r12
	add    r11, 0
	mov    r12, qword [r11]
														;$t20 = mul $t2 8
	mov    r11, 8
	mov    r10, r9
	mov    r11, r11
	imul    r10, r11
	mov    rbx, r10
														;$t19 = add $t18 $t20
	mov    r10, r12
	mov    r11, rbx
	add    r10, r11
	mov    r12, r10
														;store 8 $t19 0 0
	mov    rax, 0
	mov    r11, r12
	add    r11, 0
	mov    qword [r11], rax
														;jump %for_loop
	jmp    main_for_loop_10
main_for_loop_10:
														;$t21 = move $t2
	mov    rbx, r9
														;$t2 = add $t2 1
	mov    r11, 1
	mov    r10, r9
	mov    r11, r11
	add    r10, r11
	mov    r9, r10
														;jump %for_condition
	jmp    main_for_condition_8
main_for_after_11:
														;jump %for_loop
	jmp    main_for_loop_12
main_for_loop_12:
														;$t22 = move $t1
	mov    rbx, rdi
														;$t1 = add $t1 1
	mov    r11, 1
	mov    r10, rdi
	mov    r11, r11
	add    r10, r11
	mov    rdi, r10
														;jump %for_condition
	jmp    main_for_condition_6
main_for_after_13:
														;$t1 = move 0
	mov    rax, 0
	mov    rdi, rax
														;jump %for_condition
	jmp    main_for_condition_14
main_for_condition_14:
														;$t23 = slt $t1 100
	mov    r11, 100
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t23 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_15
	jz     main_for_after_33
main_for_body_15:
														;$t25 = sgt $t1 20
	mov    r11, 20
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setg   al
	movzx    r10, al
	mov    rbx, r10
														;br $t25 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_16
	jz     main_logical_false_17
main_logical_true_16:
														;$t26 = slt $t1 80
	mov    r11, 80
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;$t24 = move $t26
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_18
main_logical_merge_18:
														;br $t24 %if_true %if_false
	cmp    rbx, 0
	jnz    main_if_true_19
	jz     main_if_false_30
main_if_true_19:
														;$t2 = move 0
	mov    rax, 0
	mov    r9, rax
														;jump %for_condition
	jmp    main_for_condition_20
main_for_condition_20:
														;$t27 = slt $t2 100
	mov    r11, 100
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t27 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_21
	jz     main_for_after_29
main_for_body_21:
														;$t29 = sgt $t2 5
	mov    r11, 5
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setg   al
	movzx    r10, al
	mov    rbx, r10
														;br $t29 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_23
	jz     main_logical_false_22
main_logical_true_23:
														;$t28 = move 1
	mov    rax, 1
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_24
main_logical_merge_24:
														;br $t28 %if_true %if_false
	cmp    rbx, 0
	jnz    main_if_true_25
	jz     main_if_false_26
main_if_true_25:
														;$t31 = mul $t2 4
	mov    r11, 4
	mov    r10, r9
	mov    r11, r11
	imul    r10, r11
	mov    rbx, r10
														;$t32 = div $t31 100
	mov    r11, 100
	mov    r10, rbx
	mov    r11, r11
	mov    rax, r10
	cqo
	idiv   r11
	mov    r10, rax
	mov    r12, r10
														;$t4 = move $t32
	mov    r12, r12
														;$t33 = mul $t2 4
	mov    r11, 4
	mov    r10, r9
	mov    r11, r11
	imul    r10, r11
	mov    rbx, r10
														;$t34 = rem $t33 100
	mov    r11, 100
	mov    r10, rbx
	mov    r11, r11
	mov    rax, r10
	cqo
	idiv   r11
	mov    r10, rdx
	mov    rbx, r10
														;$t5 = move $t34
	mov    r13, rbx
														;$t35 = add $t1 $t4
	mov    r10, rdi
	mov    r11, r12
	add    r10, r11
	mov    rbx, r10
														;$t37 = mul $t35 8
	mov    r11, 8
	mov    r10, rbx
	mov    r11, r11
	imul    r10, r11
	mov    r12, r10
														;$t36 = add $t0 $t37
	mov    r10, rsi
	mov    r11, r12
	add    r10, r11
	mov    rbx, r10
														;$t38 = load 8 $t36 0
	mov    r11, rbx
	add    r11, 0
	mov    r12, qword [r11]
														;$t40 = mul $t5 8
	mov    r11, 8
	mov    r10, r13
	mov    r11, r11
	imul    r10, r11
	mov    rbx, r10
														;$t39 = add $t38 $t40
	mov    r10, r12
	mov    r11, rbx
	add    r10, r11
	mov    r12, r10
														;$t41 = add $t2 50
	mov    r11, 50
	mov    r10, r9
	mov    r11, r11
	add    r10, r11
	mov    rbx, r10
														;store 8 $t39 $t41 0
	mov    r11, r12
	add    r11, 0
	mov    qword [r11], rbx
														;jump %if_merge
	jmp    main_if_merge_27
main_if_merge_27:
														;jump %for_loop
	jmp    main_for_loop_28
main_for_loop_28:
														;$t42 = move $t2
	mov    rbx, r9
														;$t2 = add $t2 1
	mov    r11, 1
	mov    r10, r9
	mov    r11, r11
	add    r10, r11
	mov    r9, r10
														;jump %for_condition
	jmp    main_for_condition_20
main_if_false_26:
														;jump %if_merge
	jmp    main_if_merge_27
main_logical_false_22:
														;$t30 = slt $t1 90
	mov    r11, 90
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;$t28 = move $t30
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_24
main_for_after_29:
														;jump %if_merge
	jmp    main_if_merge_31
main_if_merge_31:
														;jump %for_loop
	jmp    main_for_loop_32
main_for_loop_32:
														;$t43 = move $t1
	mov    rbx, rdi
														;$t1 = add $t1 1
	mov    r11, 1
	mov    r10, rdi
	mov    r11, r11
	add    r10, r11
	mov    rdi, r10
														;jump %for_condition
	jmp    main_for_condition_14
main_if_false_30:
														;jump %if_merge
	jmp    main_if_merge_31
main_logical_false_17:
														;$t24 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_18
main_for_after_33:
														;$t1 = move 0
	mov    rax, 0
	mov    rdi, rax
														;jump %for_condition
	jmp    main_for_condition_34
main_for_condition_34:
														;$t44 = slt $t1 100
	mov    r11, 100
	mov    r10, rdi
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t44 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_35
	jz     main_for_after_41
main_for_body_35:
														;$t2 = move 0
	mov    rax, 0
	mov    r9, rax
														;jump %for_condition
	jmp    main_for_condition_36
main_for_condition_36:
														;$t45 = slt $t2 100
	mov    r11, 100
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setl   al
	movzx    r10, al
	mov    rbx, r10
														;br $t45 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_37
	jz     main_for_after_39
main_for_body_37:
														;$t47 = mul $t1 8
	mov    r11, 8
	mov    r10, rdi
	mov    r11, r11
	imul    r10, r11
	mov    r12, r10
														;$t46 = add $t0 $t47
	mov    r10, rsi
	mov    r11, r12
	add    r10, r11
	mov    rbx, r10
														;$t48 = load 8 $t46 0
	mov    r11, rbx
	add    r11, 0
	mov    r12, qword [r11]
														;$t50 = mul $t2 8
	mov    r11, 8
	mov    r10, r9
	mov    r11, r11
	imul    r10, r11
	mov    rbx, r10
														;$t49 = add $t48 $t50
	mov    r10, r12
	mov    r11, rbx
	add    r10, r11
	mov    r12, r10
														;$t51 = load 8 $t49 0
	mov    r11, r12
	add    r11, 0
	mov    rbx, qword [r11]
														;$t52 = add $t3 $t51
	mov    r10, r8
	mov    r11, rbx
	add    r10, r11
	mov    r8, r10
														;$t3 = move $t52
	mov    r8, r8
														;jump %for_loop
	jmp    main_for_loop_38
main_for_loop_38:
														;$t53 = move $t2
	mov    rbx, r9
														;$t2 = add $t2 1
	mov    r11, 1
	mov    r10, r9
	mov    r11, r11
	add    r10, r11
	mov    r9, r10
														;jump %for_condition
	jmp    main_for_condition_36
main_for_after_39:
														;jump %for_loop
	jmp    main_for_loop_40
main_for_loop_40:
														;$t54 = move $t1
	mov    rbx, rdi
														;$t1 = add $t1 1
	mov    r11, 1
	mov    r10, rdi
	mov    r11, r11
	add    r10, r11
	mov    rdi, r10
														;jump %for_condition
	jmp    main_for_condition_34
main_for_after_41:
														;$t55 = call __builtin_toString $t3
	mov    qword [rbp + (-616)], r12
	mov    qword [rbp + (-568)], rsi
	mov    qword [rbp + (-624)], r13
	mov    qword [rbp + (-544)], rbx
	mov    qword [rbp + (-592)], r9
	mov    qword [rbp + (-584)], r8
	mov    qword [rbp + (-576)], rdi
	mov    rdi, qword[rbp + (-584)]
	call   __builtin_toString
	mov    r12, qword [rbp + (-616)]
	mov    rsi, qword [rbp + (-568)]
	mov    r13, qword [rbp + (-624)]
	mov    rbx, qword [rbp + (-544)]
	mov    r9, qword [rbp + (-592)]
	mov    r8, qword [rbp + (-584)]
	mov    rdi, qword [rbp + (-576)]
	mov    rbx, rax
														;call __builtin_println $t55
	mov    qword [rbp + (-616)], r12
	mov    qword [rbp + (-568)], rsi
	mov    qword [rbp + (-624)], r13
	mov    qword [rbp + (-544)], rbx
	mov    qword [rbp + (-592)], r9
	mov    qword [rbp + (-584)], r8
	mov    qword [rbp + (-576)], rdi
	mov    rdi, rbx
	call   __builtin_println
	mov    r12, qword [rbp + (-616)]
	mov    rsi, qword [rbp + (-568)]
	mov    r13, qword [rbp + (-624)]
	mov    rbx, qword [rbp + (-544)]
	mov    r9, qword [rbp + (-592)]
	mov    r8, qword [rbp + (-584)]
	mov    rdi, qword [rbp + (-576)]
														;ret 0
	mov    rax, 0
	mov    rax, rax
	jmp    main_exit_42
														;jump %exit
	jmp    main_exit_42
main_exit_42:
	mov    r12, qword [rbp + (-616)]
	mov    rsi, qword [rbp + (-568)]
	mov    r13, qword [rbp + (-624)]
	mov    rbx, qword [rbp + (-544)]
	mov    r9, qword [rbp + (-592)]
	mov    r8, qword [rbp + (-584)]
	mov    rdi, qword [rbp + (-576)]
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

