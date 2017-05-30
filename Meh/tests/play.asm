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
	sub    rsp, 1200
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-1160)], r14
	mov    qword [rbp + (-1112)], r8
	mov    qword [rbp + (-1072)], rbx
	mov    qword [rbp + (-1120)], r9
main_enter_0:
														;$g1(h) = move 99
	mov    rax, 99
	mov    qword [rel GV_h], rax
														;$g2(i) = move 100
	mov    rax, 100
	mov    qword [rel GV_i], rax
														;$g3(j) = move 101
	mov    rax, 101
	mov    qword [rel GV_j], rax
														;$g4(k) = move 102
	mov    rax, 102
	mov    qword [rel GV_k], rax
														;$g5(total) = move 0
	mov    rax, 0
	mov    qword [rel GV_total], rax
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t12 = call __builtin_getInt
	mov    qword [rbp + (-1000)], r14
	mov    qword [rbp + (-952)], r8
	mov    qword [rbp + (-912)], rbx
	mov    qword [rbp + (-960)], r9
	call   __builtin_getInt
	mov    r14, qword [rbp + (-1000)]
	mov    r8, qword [rbp + (-952)]
	mov    rbx, qword [rbp + (-912)]
	mov    r9, qword [rbp + (-960)]
	mov    r8, rax
														;$g0(N) = move $t12
	mov    qword [rel GV_N], r8
														;$t6 = move 1
	mov    rax, 1
	mov    qword [rbp+(-128)], rax
														;jump %for_condition
	jmp    main_for_condition_2
main_for_condition_2:
														;$t13 = sle $t6 $g0(N)
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rel GV_N]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t13 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_3
	jz     main_for_after_148
main_for_body_3:
														;$t7 = move 1
	mov    rax, 1
	mov    qword [rbp+(-216)], rax
														;jump %for_condition
	jmp    main_for_condition_4
main_for_condition_4:
														;$t14 = sle $t7 $g0(N)
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rel GV_N]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t14 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_5
	jz     main_for_after_146
main_for_body_5:
														;$t8 = move 1
	mov    rax, 1
	mov    qword [rbp+(-72)], rax
														;jump %for_condition
	jmp    main_for_condition_6
main_for_condition_6:
														;$t15 = sle $t8 $g0(N)
	mov    r10, qword [rbp+(-72)]
	mov    r11, qword [rel GV_N]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t15 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_7
	jz     main_for_after_144
main_for_body_7:
														;$t9 = move 1
	mov    rax, 1
	mov    r14, rax
														;jump %for_condition
	jmp    main_for_condition_8
main_for_condition_8:
														;$t16 = sle $t9 $g0(N)
	mov    r11, qword [rel GV_N]
	mov    r10, r14
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t16 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_9
	jz     main_for_after_142
main_for_body_9:
														;$t10 = move 1
	mov    rax, 1
	mov    qword [rbp+(-88)], rax
														;jump %for_condition
	jmp    main_for_condition_10
main_for_condition_10:
														;$t17 = sle $t10 $g0(N)
	mov    r10, qword [rbp+(-88)]
	mov    r11, qword [rel GV_N]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t17 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_11
	jz     main_for_after_140
main_for_body_11:
														;$t11 = move 1
	mov    rax, 1
	mov    r9, rax
														;jump %for_condition
	jmp    main_for_condition_12
main_for_condition_12:
														;$t18 = sle $t11 $g0(N)
	mov    r11, qword [rel GV_N]
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setle   al
	movzx    r10, al
	mov    rbx, r10
														;br $t18 %for_body %for_after
	cmp    rbx, 0
	jnz    main_for_body_13
	jz     main_for_after_138
main_for_body_13:
														;$t20 = sne $t6 $t7
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rbp+(-216)]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t20 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_14
	jz     main_logical_false_132
main_logical_true_14:
														;$t22 = sne $t6 $t8
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rbp+(-72)]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t22 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_15
	jz     main_logical_false_130
main_logical_true_15:
														;$t24 = sne $t6 $t9
	mov    r10, qword [rbp+(-128)]
	mov    r10, r10
	mov    r11, r14
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t24 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_16
	jz     main_logical_false_128
main_logical_true_16:
														;$t26 = sne $t6 $t10
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rbp+(-88)]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t26 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_17
	jz     main_logical_false_126
main_logical_true_17:
														;$t28 = sne $t6 $t11
	mov    r10, qword [rbp+(-128)]
	mov    r10, r10
	mov    r11, r9
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t28 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_18
	jz     main_logical_false_124
main_logical_true_18:
														;$t30 = sne $t6 $g1(h)
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rel GV_h]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t30 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_19
	jz     main_logical_false_122
main_logical_true_19:
														;$t32 = sne $t6 $g2(i)
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rel GV_i]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t32 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_20
	jz     main_logical_false_120
main_logical_true_20:
														;$t34 = sne $t6 $g3(j)
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rel GV_j]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t34 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_21
	jz     main_logical_false_118
main_logical_true_21:
														;$t36 = sne $t6 $g4(k)
	mov    r10, qword [rbp+(-128)]
	mov    r11, qword [rel GV_k]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t36 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_22
	jz     main_logical_false_116
main_logical_true_22:
														;$t38 = sne $t7 $t8
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rbp+(-72)]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t38 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_23
	jz     main_logical_false_114
main_logical_true_23:
														;$t40 = sne $t7 $t9
	mov    r10, qword [rbp+(-216)]
	mov    r10, r10
	mov    r11, r14
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t40 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_24
	jz     main_logical_false_112
main_logical_true_24:
														;$t42 = sne $t7 $t10
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rbp+(-88)]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t42 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_25
	jz     main_logical_false_110
main_logical_true_25:
														;$t44 = sne $t7 $t11
	mov    r10, qword [rbp+(-216)]
	mov    r10, r10
	mov    r11, r9
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t44 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_26
	jz     main_logical_false_108
main_logical_true_26:
														;$t46 = sne $t7 $g1(h)
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rel GV_h]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t46 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_27
	jz     main_logical_false_106
main_logical_true_27:
														;$t48 = sne $t7 $g2(i)
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rel GV_i]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t48 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_28
	jz     main_logical_false_104
main_logical_true_28:
														;$t50 = sne $t7 $g3(j)
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rel GV_j]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t50 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_29
	jz     main_logical_false_102
main_logical_true_29:
														;$t52 = sne $t7 $g4(k)
	mov    r10, qword [rbp+(-216)]
	mov    r11, qword [rel GV_k]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t52 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_30
	jz     main_logical_false_100
main_logical_true_30:
														;$t54 = sne $t8 $t9
	mov    r10, qword [rbp+(-72)]
	mov    r10, r10
	mov    r11, r14
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t54 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_31
	jz     main_logical_false_98
main_logical_true_31:
														;$t56 = sne $t8 $t10
	mov    r10, qword [rbp+(-72)]
	mov    r11, qword [rbp+(-88)]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t56 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_32
	jz     main_logical_false_96
main_logical_true_32:
														;$t58 = sne $t8 $t11
	mov    r10, qword [rbp+(-72)]
	mov    r10, r10
	mov    r11, r9
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t58 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_33
	jz     main_logical_false_94
main_logical_true_33:
														;$t60 = sne $t8 $g1(h)
	mov    r10, qword [rbp+(-72)]
	mov    r11, qword [rel GV_h]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t60 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_34
	jz     main_logical_false_92
main_logical_true_34:
														;$t62 = sne $t8 $g2(i)
	mov    r10, qword [rbp+(-72)]
	mov    r11, qword [rel GV_i]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t62 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_35
	jz     main_logical_false_90
main_logical_true_35:
														;$t64 = sne $t8 $g3(j)
	mov    r10, qword [rbp+(-72)]
	mov    r11, qword [rel GV_j]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t64 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_36
	jz     main_logical_false_88
main_logical_true_36:
														;$t66 = sne $t8 $g4(k)
	mov    r10, qword [rbp+(-72)]
	mov    r11, qword [rel GV_k]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t66 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_37
	jz     main_logical_false_86
main_logical_true_37:
														;$t68 = sne $t9 $t10
	mov    r11, qword [rbp+(-88)]
	mov    r10, r14
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t68 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_38
	jz     main_logical_false_84
main_logical_true_38:
														;$t70 = sne $t9 $t11
	mov    r10, r14
	mov    r11, r9
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t70 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_39
	jz     main_logical_false_82
main_logical_true_39:
														;$t72 = sne $t9 $g1(h)
	mov    r11, qword [rel GV_h]
	mov    r10, r14
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t72 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_40
	jz     main_logical_false_80
main_logical_true_40:
														;$t74 = sne $t9 $g2(i)
	mov    r11, qword [rel GV_i]
	mov    r10, r14
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t74 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_41
	jz     main_logical_false_78
main_logical_true_41:
														;$t76 = sne $t9 $g3(j)
	mov    r11, qword [rel GV_j]
	mov    r10, r14
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t76 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_42
	jz     main_logical_false_76
main_logical_true_42:
														;$t78 = sne $t9 $g4(k)
	mov    r11, qword [rel GV_k]
	mov    r10, r14
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t78 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_43
	jz     main_logical_false_74
main_logical_true_43:
														;$t80 = sne $t10 $t11
	mov    r10, qword [rbp+(-88)]
	mov    r10, r10
	mov    r11, r9
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t80 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_44
	jz     main_logical_false_72
main_logical_true_44:
														;$t82 = sne $t10 $g1(h)
	mov    r10, qword [rbp+(-88)]
	mov    r11, qword [rel GV_h]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t82 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_45
	jz     main_logical_false_70
main_logical_true_45:
														;$t84 = sne $t10 $g2(i)
	mov    r10, qword [rbp+(-88)]
	mov    r11, qword [rel GV_i]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t84 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_46
	jz     main_logical_false_68
main_logical_true_46:
														;$t86 = sne $t10 $g3(j)
	mov    r10, qword [rbp+(-88)]
	mov    r11, qword [rel GV_j]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t86 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_47
	jz     main_logical_false_66
main_logical_true_47:
														;$t88 = sne $t10 $g4(k)
	mov    r10, qword [rbp+(-88)]
	mov    r11, qword [rel GV_k]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t88 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_48
	jz     main_logical_false_64
main_logical_true_48:
														;$t90 = sne $t11 $g1(h)
	mov    r11, qword [rel GV_h]
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t90 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_49
	jz     main_logical_false_62
main_logical_true_49:
														;$t92 = sne $t11 $g2(i)
	mov    r11, qword [rel GV_i]
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t92 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_50
	jz     main_logical_false_60
main_logical_true_50:
														;$t94 = sne $t11 $g3(j)
	mov    r11, qword [rel GV_j]
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t94 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_51
	jz     main_logical_false_58
main_logical_true_51:
														;$t96 = sne $t11 $g4(k)
	mov    r11, qword [rel GV_k]
	mov    r10, r9
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t96 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_52
	jz     main_logical_false_56
main_logical_true_52:
														;$t98 = sne $g2(i) $g3(j)
	mov    r10, qword [rel GV_i]
	mov    r11, qword [rel GV_j]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;br $t98 %logical_true %logical_false
	cmp    rbx, 0
	jnz    main_logical_true_53
	jz     main_logical_false_54
main_logical_true_53:
														;$t99 = sne $g1(h) $g4(k)
	mov    r10, qword [rel GV_h]
	mov    r11, qword [rel GV_k]
	mov    r10, r10
	mov    r11, r11
	cmp    r10, r11
	setne   al
	movzx    r10, al
	mov    rbx, r10
														;$t97 = move $t99
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_55
main_logical_merge_55:
														;$t95 = move $t97
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_57
main_logical_merge_57:
														;$t93 = move $t95
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_59
main_logical_merge_59:
														;$t91 = move $t93
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_61
main_logical_merge_61:
														;$t89 = move $t91
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_63
main_logical_merge_63:
														;$t87 = move $t89
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_65
main_logical_merge_65:
														;$t85 = move $t87
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_67
main_logical_merge_67:
														;$t83 = move $t85
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_69
main_logical_merge_69:
														;$t81 = move $t83
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_71
main_logical_merge_71:
														;$t79 = move $t81
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_73
main_logical_merge_73:
														;$t77 = move $t79
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_75
main_logical_merge_75:
														;$t75 = move $t77
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_77
main_logical_merge_77:
														;$t73 = move $t75
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_79
main_logical_merge_79:
														;$t71 = move $t73
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_81
main_logical_merge_81:
														;$t69 = move $t71
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_83
main_logical_merge_83:
														;$t67 = move $t69
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_85
main_logical_merge_85:
														;$t65 = move $t67
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_87
main_logical_merge_87:
														;$t63 = move $t65
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_89
main_logical_merge_89:
														;$t61 = move $t63
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_91
main_logical_merge_91:
														;$t59 = move $t61
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_93
main_logical_merge_93:
														;$t57 = move $t59
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_95
main_logical_merge_95:
														;$t55 = move $t57
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_97
main_logical_merge_97:
														;$t53 = move $t55
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_99
main_logical_merge_99:
														;$t51 = move $t53
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_101
main_logical_merge_101:
														;$t49 = move $t51
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_103
main_logical_merge_103:
														;$t47 = move $t49
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_105
main_logical_merge_105:
														;$t45 = move $t47
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_107
main_logical_merge_107:
														;$t43 = move $t45
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_109
main_logical_merge_109:
														;$t41 = move $t43
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_111
main_logical_merge_111:
														;$t39 = move $t41
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_113
main_logical_merge_113:
														;$t37 = move $t39
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_115
main_logical_merge_115:
														;$t35 = move $t37
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_117
main_logical_merge_117:
														;$t33 = move $t35
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_119
main_logical_merge_119:
														;$t31 = move $t33
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_121
main_logical_merge_121:
														;$t29 = move $t31
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_123
main_logical_merge_123:
														;$t27 = move $t29
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_125
main_logical_merge_125:
														;$t25 = move $t27
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_127
main_logical_merge_127:
														;$t23 = move $t25
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_129
main_logical_merge_129:
														;$t21 = move $t23
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_131
main_logical_merge_131:
														;$t19 = move $t21
	mov    rbx, rbx
														;jump %logical_merge
	jmp    main_logical_merge_133
main_logical_merge_133:
														;br $t19 %if_true %if_false
	cmp    rbx, 0
	jnz    main_if_true_134
	jz     main_if_false_135
main_if_true_134:
														;$t100 = move $g5(total)
	mov    rax, qword [rel GV_total]
	mov    rbx, rax
														;$g5(total) = add $g5(total) 1
	mov    r10, qword [rel GV_total]
	mov    r11, 1
	mov    r10, r10
	mov    r11, r11
	add    r10, r11
	mov    rax, r10
	mov    qword [rel GV_total], rax
														;jump %if_merge
	jmp    main_if_merge_136
main_if_merge_136:
														;jump %for_loop
	jmp    main_for_loop_137
main_for_loop_137:
														;$t101 = move $t11
	mov    rbx, r9
														;$t11 = add $t11 1
	mov    r11, 1
	mov    r10, r9
	mov    r11, r11
	add    r10, r11
	mov    r9, r10
														;jump %for_condition
	jmp    main_for_condition_12
main_if_false_135:
														;jump %if_merge
	jmp    main_if_merge_136
main_logical_false_54:
														;$t97 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_55
main_logical_false_56:
														;$t95 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_57
main_logical_false_58:
														;$t93 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_59
main_logical_false_60:
														;$t91 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_61
main_logical_false_62:
														;$t89 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_63
main_logical_false_64:
														;$t87 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_65
main_logical_false_66:
														;$t85 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_67
main_logical_false_68:
														;$t83 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_69
main_logical_false_70:
														;$t81 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_71
main_logical_false_72:
														;$t79 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_73
main_logical_false_74:
														;$t77 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_75
main_logical_false_76:
														;$t75 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_77
main_logical_false_78:
														;$t73 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_79
main_logical_false_80:
														;$t71 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_81
main_logical_false_82:
														;$t69 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_83
main_logical_false_84:
														;$t67 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_85
main_logical_false_86:
														;$t65 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_87
main_logical_false_88:
														;$t63 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_89
main_logical_false_90:
														;$t61 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_91
main_logical_false_92:
														;$t59 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_93
main_logical_false_94:
														;$t57 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_95
main_logical_false_96:
														;$t55 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_97
main_logical_false_98:
														;$t53 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_99
main_logical_false_100:
														;$t51 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_101
main_logical_false_102:
														;$t49 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_103
main_logical_false_104:
														;$t47 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_105
main_logical_false_106:
														;$t45 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_107
main_logical_false_108:
														;$t43 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_109
main_logical_false_110:
														;$t41 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_111
main_logical_false_112:
														;$t39 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_113
main_logical_false_114:
														;$t37 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_115
main_logical_false_116:
														;$t35 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_117
main_logical_false_118:
														;$t33 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_119
main_logical_false_120:
														;$t31 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_121
main_logical_false_122:
														;$t29 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_123
main_logical_false_124:
														;$t27 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_125
main_logical_false_126:
														;$t25 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_127
main_logical_false_128:
														;$t23 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_129
main_logical_false_130:
														;$t21 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_131
main_logical_false_132:
														;$t19 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %logical_merge
	jmp    main_logical_merge_133
main_for_after_138:
														;jump %for_loop
	jmp    main_for_loop_139
main_for_loop_139:
														;$t102 = move $t10
	mov    rax, qword [rbp+(-88)]
	mov    rbx, rax
														;$t10 = add $t10 1
	mov    r10, qword [rbp+(-88)]
	mov    r11, 1
	mov    r10, r10
	mov    r11, r11
	add    r10, r11
	mov    rax, r10
	mov    qword [rbp+(-88)], rax
														;jump %for_condition
	jmp    main_for_condition_10
main_for_after_140:
														;jump %for_loop
	jmp    main_for_loop_141
main_for_loop_141:
														;$t103 = move $t9
	mov    rbx, r14
														;$t9 = add $t9 1
	mov    r11, 1
	mov    r10, r14
	mov    r11, r11
	add    r10, r11
	mov    r14, r10
														;jump %for_condition
	jmp    main_for_condition_8
main_for_after_142:
														;jump %for_loop
	jmp    main_for_loop_143
main_for_loop_143:
														;$t104 = move $t8
	mov    rax, qword [rbp+(-72)]
	mov    rbx, rax
														;$t8 = add $t8 1
	mov    r10, qword [rbp+(-72)]
	mov    r11, 1
	mov    r10, r10
	mov    r11, r11
	add    r10, r11
	mov    rax, r10
	mov    qword [rbp+(-72)], rax
														;jump %for_condition
	jmp    main_for_condition_6
main_for_after_144:
														;jump %for_loop
	jmp    main_for_loop_145
main_for_loop_145:
														;$t105 = move $t7
	mov    rax, qword [rbp+(-216)]
	mov    rbx, rax
														;$t7 = add $t7 1
	mov    r10, qword [rbp+(-216)]
	mov    r11, 1
	mov    r10, r10
	mov    r11, r11
	add    r10, r11
	mov    rax, r10
	mov    qword [rbp+(-216)], rax
														;jump %for_condition
	jmp    main_for_condition_4
main_for_after_146:
														;jump %for_loop
	jmp    main_for_loop_147
main_for_loop_147:
														;$t106 = move $t6
	mov    rax, qword [rbp+(-128)]
	mov    rbx, rax
														;$t6 = add $t6 1
	mov    r10, qword [rbp+(-128)]
	mov    r11, 1
	mov    r10, r10
	mov    r11, r11
	add    r10, r11
	mov    rax, r10
	mov    qword [rbp+(-128)], rax
														;jump %for_condition
	jmp    main_for_condition_2
main_for_after_148:
														;$t107 = call __builtin_toString $g5(total)
	mov    qword [rbp + (-1000)], r14
	mov    qword [rbp + (-952)], r8
	mov    qword [rbp + (-912)], rbx
	mov    qword [rbp + (-960)], r9
	mov    rax, qword [rel GV_total]
	mov    rdi, rax
	call   __builtin_toString
	mov    r14, qword [rbp + (-1000)]
	mov    r8, qword [rbp + (-952)]
	mov    rbx, qword [rbp + (-912)]
	mov    r9, qword [rbp + (-960)]
	mov    rbx, rax
														;call __builtin_println $t107
	mov    qword [rbp + (-1000)], r14
	mov    qword [rbp + (-952)], r8
	mov    qword [rbp + (-912)], rbx
	mov    qword [rbp + (-960)], r9
	mov    rdi, rbx
	call   __builtin_println
	mov    r14, qword [rbp + (-1000)]
	mov    r8, qword [rbp + (-952)]
	mov    rbx, qword [rbp + (-912)]
	mov    r9, qword [rbp + (-960)]
														;ret 0
	mov    rax, 0
	mov    rax, rax
	jmp    main_exit_149
														;jump %exit
	jmp    main_exit_149
main_exit_149:
	mov    r14, qword [rbp + (-1160)]
	mov    r8, qword [rbp + (-1112)]
	mov    rbx, qword [rbp + (-1072)]
	mov    r9, qword [rbp + (-1120)]
	leave
	ret
SECTION .data
GV_i:
	dq 0
GV_h:
	dq 0
GV_total:
	dq 0
GV_N:
	dq 0
GV_k:
	dq 0
GV_j:
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

