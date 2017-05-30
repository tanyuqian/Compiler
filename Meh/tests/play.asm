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

func:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 248
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp + (-192)], r12
func_enter_0:
														;jump %entry
	jmp    func_entry_1
func_entry_1:
														;$t11 = add $p0 $p1
	mov    r10, qword [rbp+(-8)]
	mov    r11, qword [rbp+(-16)]
	mov    r12, r10
	add    r12, r11
														;$t12 = add $t11 $p2
	mov    r11, qword [rbp+(-24)]
	add    r12, r11
														;$t13 = and $t12 1073741823
	mov    r11, 1073741823
	and    r12, r11
														;ret $t13
	mov    rax, r12
	jmp    func_exit_2
														;jump %exit
	jmp    func_exit_2
func_exit_2:
	mov    r12, qword [rbp + (-192)]
	leave
	ret
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 1440
	mov    qword [rbp + (-1312)], rbx
	mov    qword [rbp + (-1400)], r14
	mov    qword [rbp + (-1384)], r12
	mov    qword [rbp + (-1392)], r13
	mov    qword [rbp + (-1408)], r15
main_enter_0:
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t14 = call __builtin_getInt
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	call   __builtin_getInt
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    rsi, rax
														;$t3 = move $t14
	mov    rsi, rsi
														;$t16 = mul $t3 8
	mov    r11, 8
	mov    r12, rsi
	imul    r12, r11
														;$t16 = add $t16 8
	mov    r11, 8
	add    r12, r11
														;$t15 = alloc $t16
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   malloc
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    rcx, rax
														;store 8 $t15 $t3 0
	mov    r11, rcx
	mov    qword [r11], rsi
														;$t15 = add $t15 8
	mov    r11, 8
	add    rcx, r11
														;$t17 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %new_condition
	jmp    main_new_condition_2
main_new_condition_2:
														;$t18 = slt $t17 $t3
	mov    r12, r15
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t18 %new_body %new_exit
	cmp    r12, 0
	jnz    main_new_body_3
	jz     main_new_exit_5
main_new_body_3:
														;$t20 = mul $t3 8
	mov    r11, 8
	mov    r12, rsi
	imul    r12, r11
														;$t20 = add $t20 8
	mov    r11, 8
	add    r12, r11
														;$t19 = alloc $t20
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   malloc
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r14, rax
														;store 8 $t19 $t3 0
	mov    r11, r14
	mov    qword [r11], rsi
														;$t19 = add $t19 8
	mov    r11, 8
	add    r14, r11
														;$t21 = mul $t17 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t22 = add $t15 $t21
	mov    r13, rcx
	add    r13, r12
														;store 8 $t22 $t19 0
	mov    r11, r13
	mov    qword [r11], r14
														;jump %new_loop
	jmp    main_new_loop_4
main_new_loop_4:
														;$t17 = add $t17 1
	mov    r11, 1
	add    r15, r11
														;jump %new_condition
	jmp    main_new_condition_2
main_new_exit_5:
														;$t4 = move $t15
	mov    rcx, rcx
														;$t24 = mul $t3 8
	mov    r11, 8
	mov    r12, rsi
	imul    r12, r11
														;$t24 = add $t24 8
	mov    r11, 8
	add    r12, r11
														;$t23 = alloc $t24
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   malloc
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r15, rax
														;store 8 $t23 $t3 0
	mov    r11, r15
	mov    qword [r11], rsi
														;$t23 = add $t23 8
	mov    r11, 8
	add    r15, r11
														;$t25 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %new_condition
	jmp    main_new_condition_6
main_new_condition_6:
														;$t26 = slt $t25 $t3
	mov    r12, rbx
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t26 %new_body %new_exit
	cmp    r12, 0
	jnz    main_new_body_7
	jz     main_new_exit_9
main_new_body_7:
														;$t28 = mul $t3 8
	mov    r11, 8
	mov    r12, rsi
	imul    r12, r11
														;$t28 = add $t28 8
	mov    r11, 8
	add    r12, r11
														;$t27 = alloc $t28
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   malloc
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r13, rax
														;store 8 $t27 $t3 0
	mov    r11, r13
	mov    qword [r11], rsi
														;$t27 = add $t27 8
	mov    r11, 8
	add    r13, r11
														;$t29 = mul $t25 8
	mov    r11, 8
	mov    r14, rbx
	imul    r14, r11
														;$t30 = add $t23 $t29
	mov    r12, r15
	add    r12, r14
														;store 8 $t30 $t27 0
	mov    r11, r12
	mov    qword [r11], r13
														;jump %new_loop
	jmp    main_new_loop_8
main_new_loop_8:
														;$t25 = add $t25 1
	mov    r11, 1
	add    rbx, r11
														;jump %new_condition
	jmp    main_new_condition_6
main_new_exit_9:
														;$t5 = move $t23
	mov    qword [rbp+(-64)], r15
														;$t32 = mul $t3 8
	mov    r11, 8
	mov    r12, rsi
	imul    r12, r11
														;$t32 = add $t32 8
	mov    r11, 8
	add    r12, r11
														;$t31 = alloc $t32
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   malloc
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    rbx, rax
														;store 8 $t31 $t3 0
	mov    r11, rbx
	mov    qword [r11], rsi
														;$t31 = add $t31 8
	mov    r11, 8
	add    rbx, r11
														;$t33 = move 0
	mov    rax, 0
	mov    r13, rax
														;jump %new_condition
	jmp    main_new_condition_10
main_new_condition_10:
														;$t34 = slt $t33 $t3
	mov    r12, r13
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t34 %new_body %new_exit
	cmp    r12, 0
	jnz    main_new_body_11
	jz     main_new_exit_13
main_new_body_11:
														;$t36 = mul $t3 8
	mov    r11, 8
	mov    r12, rsi
	imul    r12, r11
														;$t36 = add $t36 8
	mov    r11, 8
	add    r12, r11
														;$t35 = alloc $t36
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   malloc
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r15, rax
														;store 8 $t35 $t3 0
	mov    r11, r15
	mov    qword [r11], rsi
														;$t35 = add $t35 8
	mov    r11, 8
	add    r15, r11
														;$t37 = mul $t33 8
	mov    r11, 8
	mov    r12, r13
	imul    r12, r11
														;$t38 = add $t31 $t37
	mov    r14, rbx
	add    r14, r12
														;store 8 $t38 $t35 0
	mov    r11, r14
	mov    qword [r11], r15
														;jump %new_loop
	jmp    main_new_loop_12
main_new_loop_12:
														;$t33 = add $t33 1
	mov    r11, 1
	add    r13, r11
														;jump %new_condition
	jmp    main_new_condition_10
main_new_exit_13:
														;$t6 = move $t31
	mov    rbx, rbx
														;$t7 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    main_for_condition_14
main_for_condition_14:
														;$t39 = slt $t7 $t3
	mov    r12, r15
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t39 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_15
	jz     main_for_after_21
main_for_body_15:
														;$t8 = move 0
	mov    rax, 0
	mov    r14, rax
														;jump %for_condition
	jmp    main_for_condition_16
main_for_condition_16:
														;$t40 = slt $t8 $t3
	mov    r12, r14
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t40 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_17
	jz     main_for_after_19
main_for_body_17:
														;$t42 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t41 = add $t4 $t42
	mov    r13, rcx
	add    r13, r12
														;$t43 = load 8 $t41 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t45 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t44 = add $t43 $t45
	add    r13, r12
														;$t46 = add $t7 $t8
	mov    r12, r15
	add    r12, r14
														;store 8 $t44 $t46 0
	mov    r11, r13
	mov    qword [r11], r12
														;jump %for_loop
	jmp    main_for_loop_18
main_for_loop_18:
														;$t8 = add $t8 1
	mov    r11, 1
	add    r14, r11
														;jump %for_condition
	jmp    main_for_condition_16
main_for_after_19:
														;jump %for_loop
	jmp    main_for_loop_20
main_for_loop_20:
														;$t7 = add $t7 1
	mov    r11, 1
	add    r15, r11
														;jump %for_condition
	jmp    main_for_condition_14
main_for_after_21:
														;$t7 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    main_for_condition_22
main_for_condition_22:
														;$t47 = slt $t7 $t3
	mov    r12, r15
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t47 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_23
	jz     main_for_after_36
main_for_body_23:
														;$t8 = move 0
	mov    rax, 0
	mov    r14, rax
														;jump %for_condition
	jmp    main_for_condition_24
main_for_condition_24:
														;$t48 = slt $t8 $t3
	mov    r12, r14
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t48 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_25
	jz     main_for_after_34
main_for_body_25:
														;$t9 = move 0
	mov    rax, 0
	mov    qword [rbp+(-296)], rax
														;jump %for_condition
	jmp    main_for_condition_26
main_for_condition_26:
														;$t49 = slt $t9 $t3
	mov    r10, qword [rbp+(-296)]
	mov    r12, r10
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t49 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_27
	jz     main_for_after_32
main_for_body_27:
														;$t50 = sge $t8 $t7
	mov    r12, r14
	cmp    r12, r15
	setge   al
	movzx    r12, al
														;br $t50 %if_true %if_false
	cmp    r12, 0
	jnz    main_if_true_28
	jz     main_if_false_29
main_if_true_28:
														;$t52 = mul $t7 8
	mov    r11, 8
	mov    r13, r15
	imul    r13, r11
														;$t51 = add $t5 $t52
	mov    r10, qword [rbp+(-64)]
	mov    r12, r10
	add    r12, r13
														;$t53 = load 8 $t51 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t55 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t54 = add $t53 $t55
	mov    r8, r13
	add    r8, r12
														;$t57 = mul $t7 8
	mov    r11, 8
	mov    r13, r15
	imul    r13, r11
														;$t56 = add $t5 $t57
	mov    r10, qword [rbp+(-64)]
	mov    r12, r10
	add    r12, r13
														;$t58 = load 8 $t56 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t60 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t59 = add $t58 $t60
	add    r13, r12
														;$t61 = load 8 $t59 0
	mov    r11, r13
	mov    r9, qword [r11]
														;$t63 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t62 = add $t4 $t63
	mov    r13, rcx
	add    r13, r12
														;$t64 = load 8 $t62 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t66 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t65 = add $t64 $t66
	add    r13, r12
														;$t67 = load 8 $t65 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t69 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t68 = add $t4 $t69
	mov    rdi, rcx
	add    rdi, r13
														;$t70 = load 8 $t68 0
	mov    r11, rdi
	mov    rdi, qword [r11]
														;$t72 = mul $t8 8
	mov    r11, 8
	mov    r13, r14
	imul    r13, r11
														;$t71 = add $t70 $t72
	add    rdi, r13
														;$t73 = load 8 $t71 0
	mov    r11, rdi
	mov    r13, qword [r11]
														;$t74 = call func $t61 $t67 $t73
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, qword[rbp + (-1360)]
	mov    rsi, r12
	mov    rdx, r13
	call   func
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r12, rax
														;store 8 $t54 $t74 0
	mov    r11, r8
	mov    qword [r11], r12
														;$t76 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t75 = add $t6 $t76
	mov    r13, rbx
	add    r13, r12
														;$t77 = load 8 $t75 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t79 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t78 = add $t77 $t79
	mov    rdi, r13
	add    rdi, r12
														;$t81 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t80 = add $t5 $t81
	mov    r10, qword [rbp+(-64)]
	mov    r13, r10
	add    r13, r12
														;$t82 = load 8 $t80 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t84 = mul $t8 8
	mov    r11, 8
	mov    r13, r14
	imul    r13, r11
														;$t83 = add $t82 $t84
	add    r12, r13
														;$t85 = load 8 $t83 0
	mov    r11, r12
	mov    r8, qword [r11]
														;$t87 = mul $t7 8
	mov    r11, 8
	mov    r13, r15
	imul    r13, r11
														;$t86 = add $t4 $t87
	mov    r12, rcx
	add    r12, r13
														;$t88 = load 8 $t86 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t90 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t89 = add $t88 $t90
	add    r12, r13
														;$t91 = load 8 $t89 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t93 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t92 = add $t4 $t93
	mov    r9, rcx
	add    r9, r12
														;$t94 = load 8 $t92 0
	mov    r11, r9
	mov    r9, qword [r11]
														;$t96 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t95 = add $t94 $t96
	add    r9, r12
														;$t97 = load 8 $t95 0
	mov    r11, r9
	mov    r12, qword [r11]
														;$t98 = call func $t85 $t91 $t97
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, qword[rbp + (-1352)]
	mov    rsi, r13
	mov    rdx, r12
	call   func
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r12, rax
														;store 8 $t78 $t98 0
	mov    r11, rdi
	mov    qword [r11], r12
														;$t100 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t99 = add $t6 $t100
	mov    r13, rbx
	add    r13, r12
														;$t101 = load 8 $t99 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t103 = mul $t8 8
	mov    r11, 8
	mov    rdi, r14
	imul    rdi, r11
														;$t102 = add $t101 $t103
	mov    r13, r12
	add    r13, rdi
														;$t105 = mul $t7 8
	mov    r11, 8
	mov    rdi, r15
	imul    rdi, r11
														;$t104 = add $t5 $t105
	mov    r10, qword [rbp+(-64)]
	mov    r12, r10
	add    r12, rdi
														;$t106 = load 8 $t104 0
	mov    r11, r12
	mov    rdi, qword [r11]
														;$t108 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t107 = add $t106 $t108
	add    rdi, r12
														;$t109 = load 8 $t107 0
	mov    r11, rdi
	mov    r8, qword [r11]
														;$t111 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t110 = add $t4 $t111
	mov    rdi, rcx
	add    rdi, r12
														;$t112 = load 8 $t110 0
	mov    r11, rdi
	mov    rdi, qword [r11]
														;$t114 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t113 = add $t112 $t114
	add    rdi, r12
														;$t115 = load 8 $t113 0
	mov    r11, rdi
	mov    rdi, qword [r11]
														;$t117 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r9, r10
	imul    r9, r11
														;$t116 = add $t4 $t117
	mov    r12, rcx
	add    r12, r9
														;$t118 = load 8 $t116 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t120 = mul $t8 8
	mov    r11, 8
	mov    r9, r14
	imul    r9, r11
														;$t119 = add $t118 $t120
	add    r12, r9
														;$t121 = load 8 $t119 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t122 = call func $t109 $t115 $t121
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, qword[rbp + (-1352)]
	mov    rsi, qword[rbp + (-1344)]
	mov    rdx, r12
	call   func
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r12, rax
														;store 8 $t102 $t122 0
	mov    r11, r13
	mov    qword [r11], r12
														;$t124 = mul $t7 8
	mov    r11, 8
	mov    r13, r15
	imul    r13, r11
														;$t123 = add $t6 $t124
	mov    r12, rbx
	add    r12, r13
														;$t125 = load 8 $t123 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t127 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t126 = add $t125 $t127
	add    r13, r12
														;$t129 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t128 = add $t5 $t129
	mov    r10, qword [rbp+(-64)]
	mov    rdi, r10
	add    rdi, r12
														;$t130 = load 8 $t128 0
	mov    r11, rdi
	mov    r12, qword [r11]
														;$t132 = mul $t8 8
	mov    r11, 8
	mov    rdi, r14
	imul    rdi, r11
														;$t131 = add $t130 $t132
	add    r12, rdi
														;$t133 = load 8 $t131 0
	mov    r11, r12
	mov    rdi, qword [r11]
														;$t135 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t134 = add $t4 $t135
	mov    r8, rcx
	add    r8, r12
														;$t136 = load 8 $t134 0
	mov    r11, r8
	mov    r8, qword [r11]
														;$t138 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t137 = add $t136 $t138
	add    r8, r12
														;$t139 = load 8 $t137 0
	mov    r11, r8
	mov    r8, qword [r11]
														;$t141 = mul $t9 8
	mov    r10, qword [rbp+(-296)]
	mov    r11, 8
	mov    r9, r10
	imul    r9, r11
														;$t140 = add $t4 $t141
	mov    r12, rcx
	add    r12, r9
														;$t142 = load 8 $t140 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t144 = mul $t8 8
	mov    r11, 8
	mov    r9, r14
	imul    r9, r11
														;$t143 = add $t142 $t144
	add    r12, r9
														;$t145 = load 8 $t143 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t146 = call func $t133 $t139 $t145
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, qword[rbp + (-1344)]
	mov    rsi, qword[rbp + (-1352)]
	mov    rdx, r12
	call   func
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r12, rax
														;store 8 $t126 $t146 0
	mov    r11, r13
	mov    qword [r11], r12
														;jump %if_merge
	jmp    main_if_merge_30
main_if_merge_30:
														;jump %for_loop
	jmp    main_for_loop_31
main_for_loop_31:
														;$t9 = add $t9 1
	mov    r10, qword [rbp+(-296)]
	mov    r11, 1
	mov    rax, r10
	add    rax, r11
	mov    qword [rbp+(-296)], rax
														;jump %for_condition
	jmp    main_for_condition_26
main_if_false_29:
														;jump %if_merge
	jmp    main_if_merge_30
main_for_after_32:
														;jump %for_loop
	jmp    main_for_loop_33
main_for_loop_33:
														;$t8 = add $t8 1
	mov    r11, 1
	add    r14, r11
														;jump %for_condition
	jmp    main_for_condition_24
main_for_after_34:
														;jump %for_loop
	jmp    main_for_loop_35
main_for_loop_35:
														;$t7 = add $t7 1
	mov    r11, 1
	add    r15, r11
														;jump %for_condition
	jmp    main_for_condition_22
main_for_after_36:
														;$t10 = move 0
	mov    rax, 0
	mov    rbx, rax
														;$t7 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    main_for_condition_37
main_for_condition_37:
														;$t147 = slt $t7 $t3
	mov    r12, r15
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t147 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_38
	jz     main_for_after_44
main_for_body_38:
														;$t8 = move 0
	mov    rax, 0
	mov    r14, rax
														;jump %for_condition
	jmp    main_for_condition_39
main_for_condition_39:
														;$t148 = slt $t8 $t3
	mov    r12, r14
	cmp    r12, rsi
	setl   al
	movzx    r12, al
														;br $t148 %for_body %for_after
	cmp    r12, 0
	jnz    main_for_body_40
	jz     main_for_after_42
main_for_body_40:
														;$t150 = mul $t7 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t149 = add $t5 $t150
	mov    r10, qword [rbp+(-64)]
	mov    r13, r10
	add    r13, r12
														;$t151 = load 8 $t149 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t153 = mul $t8 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t152 = add $t151 $t153
	add    r13, r12
														;$t154 = load 8 $t152 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t155 = add $t10 $t154
	mov    r12, rbx
	add    r12, r13
														;$t156 = and $t155 1073741823
	mov    r11, 1073741823
	and    r12, r11
														;$t10 = move $t156
	mov    rbx, r12
														;jump %for_loop
	jmp    main_for_loop_41
main_for_loop_41:
														;$t8 = add $t8 1
	mov    r11, 1
	add    r14, r11
														;jump %for_condition
	jmp    main_for_condition_39
main_for_after_42:
														;jump %for_loop
	jmp    main_for_loop_43
main_for_loop_43:
														;$t7 = add $t7 1
	mov    r11, 1
	add    r15, r11
														;jump %for_condition
	jmp    main_for_condition_37
main_for_after_44:
														;$t157 = call __builtin_toString $t10
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, rbx
	call   __builtin_toString
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
	mov    r12, rax
														;call __builtin_print $t157
	mov    qword [rbp + (-1296)], rcx
	mov    qword [rbp + (-1344)], rdi
	mov    qword [rbp + (-1360)], r9
	mov    qword [rbp + (-1352)], r8
	mov    qword [rbp + (-1336)], rsi
	mov    rdi, r12
	call   __builtin_print
	mov    rcx, qword [rbp + (-1296)]
	mov    rdi, qword [rbp + (-1344)]
	mov    r9, qword [rbp + (-1360)]
	mov    r8, qword [rbp + (-1352)]
	mov    rsi, qword [rbp + (-1336)]
														;ret 0
	mov    rax, 0
	jmp    main_exit_45
														;jump %exit
	jmp    main_exit_45
main_exit_45:
	mov    rbx, qword [rbp + (-1312)]
	mov    r14, qword [rbp + (-1400)]
	mov    r12, qword [rbp + (-1384)]
	mov    r13, qword [rbp + (-1392)]
	mov    r15, qword [rbp + (-1408)]
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

