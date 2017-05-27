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
    mov     rdi, 400
    call    malloc
    mov     r13, rax

    mov     rdx, r12
    mov     rsi, INTEGER_FORMAT
    mov     rdi, r13

    mov     rax, 0
    call    sprintf

    mov     rax, r13

    leave
    ret

__builtin_string_concat:
    call    strcat
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

qsrt:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 360
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
qsrt_enter_0:
														;jump %entry
	jmp    qsrt_entry_1
														;%entry
qsrt_entry_1:
														;$t4 = move $p2
	mov    r11, qword [rbp+(-8)]
	mov    qword [rbp+(-192)], r11
														;$t5 = move $p3
	mov    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-200)], r11
														;$t9 = add $p2 $p3
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-120)], r11
														;$t10 = div $t9 2
	mov    r11, qword [rbp+(-120)]
	mov    rax, qword [rbp+(-120)]
	cqo
	mov    r11, 2
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-136)], r11
														;$t12 = mul $t10 8
	mov    r11, qword [rbp+(-136)]
	imul   r11, 8
	mov    qword [rbp+(-168)], r11
														;$t11 = add $g0(a) $t12
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-168)]
	mov    qword [rbp+(-248)], r11
														;$t13 = load 8 $t11 0
	mov    r11, qword [rbp+(-248)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-208)], r12
														;$t6 = move $t13
	mov    r11, qword [rbp+(-208)]
	mov    qword [rbp+(-176)], r11
														;jump %while_loop
	jmp    qsrt_while_loop_2
														;%while_loop
qsrt_while_loop_2:
														;$t14 = sle $t4 $t5
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rbp+(-200)]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-344)], r11
														;br $t14 %while_body %while_after
	cmp    qword [rbp+(-344)], 0
	jnz    qsrt_while_body_3
	jz     qsrt_while_after_13
														;%while_body
qsrt_while_body_3:
														;jump %while_loop
	jmp    qsrt_while_loop_4
														;%while_loop
qsrt_while_loop_4:
														;$t16 = mul $t4 8
	mov    r11, qword [rbp+(-192)]
	imul   r11, 8
	mov    qword [rbp+(-160)], r11
														;$t15 = add $g0(a) $t16
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-160)]
	mov    qword [rbp+(-288)], r11
														;$t17 = load 8 $t15 0
	mov    r11, qword [rbp+(-288)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-232)], r12
														;$t18 = slt $t17 $t6
	mov    r11, qword [rbp+(-232)]
	cmp    r11, qword [rbp+(-176)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t18 %while_body %while_after
	cmp    qword [rbp+(-72)], 0
	jnz    qsrt_while_body_5
	jz     qsrt_while_after_6
														;%while_body
qsrt_while_body_5:
														;$t19 = move $t4
	mov    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-88)], r11
														;$t4 = add $t4 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-192)], r11
														;jump %while_loop
	jmp    qsrt_while_loop_4
														;%while_after
qsrt_while_after_6:
														;jump %while_loop
	jmp    qsrt_while_loop_7
														;%while_loop
qsrt_while_loop_7:
														;$t21 = mul $t5 8
	mov    r11, qword [rbp+(-200)]
	imul   r11, 8
	mov    qword [rbp+(-272)], r11
														;$t20 = add $g0(a) $t21
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-272)]
	mov    qword [rbp+(-256)], r11
														;$t22 = load 8 $t20 0
	mov    r11, qword [rbp+(-256)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-304)], r12
														;$t23 = sgt $t22 $t6
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rbp+(-176)]
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-128)], r11
														;br $t23 %while_body %while_after
	cmp    qword [rbp+(-128)], 0
	jnz    qsrt_while_body_8
	jz     qsrt_while_after_9
														;%while_body
qsrt_while_body_8:
														;$t24 = move $t5
	mov    r11, qword [rbp+(-200)]
	mov    qword [rbp+(-240)], r11
														;$t5 = sub $t5 1
	mov    r11, qword [rbp+(-200)]
	sub    r11, 1
	mov    qword [rbp+(-200)], r11
														;jump %while_loop
	jmp    qsrt_while_loop_7
														;%while_after
qsrt_while_after_9:
														;$t25 = sle $t4 $t5
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rbp+(-200)]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-280)], r11
														;br $t25 %if_true %if_false
	cmp    qword [rbp+(-280)], 0
	jnz    qsrt_if_true_10
	jz     qsrt_if_false_11
														;%if_true
qsrt_if_true_10:
														;$t27 = mul $t4 8
	mov    r11, qword [rbp+(-192)]
	imul   r11, 8
	mov    qword [rbp+(-336)], r11
														;$t26 = add $g0(a) $t27
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-336)]
	mov    qword [rbp+(-352)], r11
														;$t28 = load 8 $t26 0
	mov    r11, qword [rbp+(-352)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-144)], r12
														;$t7 = move $t28
	mov    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-328)], r11
														;$t30 = mul $t4 8
	mov    r11, qword [rbp+(-192)]
	imul   r11, 8
	mov    qword [rbp+(-112)], r11
														;$t29 = add $g0(a) $t30
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-296)], r11
														;$t32 = mul $t5 8
	mov    r11, qword [rbp+(-200)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t31 = add $g0(a) $t32
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-64)], r11
														;$t33 = load 8 $t31 0
	mov    r11, qword [rbp+(-64)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-264)], r12
														;store 8 $t29 $t33 0
	mov    r11, qword [rbp+(-296)]
	add    r11, 0
	mov    rax, qword [rbp+(-264)]
	mov    qword [r11], rax
														;$t35 = mul $t5 8
	mov    r11, qword [rbp+(-200)]
	imul   r11, 8
	mov    qword [rbp+(-216)], r11
														;$t34 = add $g0(a) $t35
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-216)]
	mov    qword [rbp+(-320)], r11
														;store 8 $t34 $t7 0
	mov    r11, qword [rbp+(-320)]
	add    r11, 0
	mov    rax, qword [rbp+(-328)]
	mov    qword [r11], rax
														;$t36 = move $t4
	mov    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-104)], r11
														;$t4 = add $t4 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-192)], r11
														;$t37 = move $t5
	mov    r11, qword [rbp+(-200)]
	mov    qword [rbp+(-224)], r11
														;$t5 = sub $t5 1
	mov    r11, qword [rbp+(-200)]
	sub    r11, 1
	mov    qword [rbp+(-200)], r11
														;jump %if_merge
	jmp    qsrt_if_merge_12
														;%if_false
qsrt_if_false_11:
														;jump %if_merge
	jmp    qsrt_if_merge_12
														;%if_merge
qsrt_if_merge_12:
														;jump %while_loop
	jmp    qsrt_while_loop_2
														;%while_after
qsrt_while_after_13:
														;$t38 = slt $p2 $t5
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rbp+(-200)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-312)], r11
														;br $t38 %if_true %if_false
	cmp    qword [rbp+(-312)], 0
	jnz    qsrt_if_true_14
	jz     qsrt_if_false_15
														;%if_true
qsrt_if_true_14:
														;$t39 = call qsrt $p2 $t5
	mov    rdi, qword [rbp+(-8)]
	mov    rsi, qword [rbp+(-200)]
	call   qsrt
	mov    qword [rbp+(-96)], rax
														;jump %if_merge
	jmp    qsrt_if_merge_16
														;%if_false
qsrt_if_false_15:
														;jump %if_merge
	jmp    qsrt_if_merge_16
														;%if_merge
qsrt_if_merge_16:
														;$t40 = slt $t4 $p3
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rbp+(-16)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-184)], r11
														;br $t40 %if_true %if_false
	cmp    qword [rbp+(-184)], 0
	jnz    qsrt_if_true_17
	jz     qsrt_if_false_18
														;%if_true
qsrt_if_true_17:
														;$t41 = call qsrt $t4 $p3
	mov    rdi, qword [rbp+(-192)]
	mov    rsi, qword [rbp+(-16)]
	call   qsrt
	mov    qword [rbp+(-152)], rax
														;jump %if_merge
	jmp    qsrt_if_merge_19
														;%if_false
qsrt_if_false_18:
														;jump %if_merge
	jmp    qsrt_if_merge_19
														;%if_merge
qsrt_if_merge_19:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    qsrt_exit_20
														;jump %exit
	jmp    qsrt_exit_20
														;%exit
qsrt_exit_20:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 192
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
main_enter_0:
														;$t43 = move 80800
	mov    r11, 80800
	mov    qword [rbp+(-176)], r11
														;$t43 = add $t43 8
	mov    r11, qword [rbp+(-176)]
	add    r11, 8
	mov    qword [rbp+(-176)], r11
														;$t42 = alloc $t43
	mov    rdi, qword [rbp+(-176)]
	call   malloc
	mov    qword [rbp+(-168)], rax
														;store 8 $t42 10100 0
	mov    r11, qword [rbp+(-168)]
	add    r11, 0
	mov    rax, 10100
	mov    qword [r11], rax
														;$t42 = add $t42 8
	mov    r11, qword [rbp+(-168)]
	add    r11, 8
	mov    qword [rbp+(-168)], r11
														;$g0(a) = move $t42
	mov    r11, qword [rbp+(-168)]
	mov    qword [rel a], r11
														;$g1(n) = move 10
	mov    r11, 10
	mov    qword [rel n], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t8 = move 1
	mov    r11, 1
	mov    qword [rbp+(-120)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_condition
main_for_condition_2:
														;$t44 = sle $t8 $g1(n)
	mov    r11, qword [rbp+(-120)]
	cmp    r11, qword [rel n]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-144)], r11
														;br $t44 %for_body %for_after
	cmp    qword [rbp+(-144)], 0
	jnz    main_for_body_3
	jz     main_for_after_5
														;%for_body
main_for_body_3:
														;$t46 = mul $t8 8
	mov    r11, qword [rbp+(-120)]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t45 = add $g0(a) $t46
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-104)], r11
														;$t47 = add $g1(n) 1
	mov    r11, qword [rel n]
	add    r11, 1
	mov    qword [rbp+(-160)], r11
														;$t48 = sub $t47 $t8
	mov    r11, qword [rbp+(-160)]
	sub    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-64)], r11
														;store 8 $t45 $t48 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, qword [rbp+(-64)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    main_for_loop_4
														;%for_loop
main_for_loop_4:
														;$t49 = move $t8
	mov    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-80)], r11
														;$t8 = add $t8 1
	mov    r11, qword [rbp+(-120)]
	add    r11, 1
	mov    qword [rbp+(-120)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_after
main_for_after_5:
														;$t50 = call qsrt 1 $g1(n)
	mov    rdi, 1
	mov    rsi, qword [rel n]
	call   qsrt
	mov    qword [rbp+(-152)], rax
														;$t8 = move 1
	mov    r11, 1
	mov    qword [rbp+(-120)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_condition
main_for_condition_6:
														;$t51 = sle $t8 $g1(n)
	mov    r11, qword [rbp+(-120)]
	cmp    r11, qword [rel n]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t51 %for_body %for_after
	cmp    qword [rbp+(-136)], 0
	jnz    main_for_body_7
	jz     main_for_after_9
														;%for_body
main_for_body_7:
														;$t53 = mul $t8 8
	mov    r11, qword [rbp+(-120)]
	imul   r11, 8
	mov    qword [rbp+(-128)], r11
														;$t52 = add $g0(a) $t53
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-96)], r11
														;$t54 = load 8 $t52 0
	mov    r11, qword [rbp+(-96)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-184)], r12
														;$t55 = call __builtin_toString $t54
	mov    rdi, qword [rbp+(-184)]
	call   __builtin_toString
	mov    qword [rbp+(-88)], rax
														;call __builtin_print $t55
	mov    rdi, qword [rbp+(-88)]
	call   __builtin_print
														;call __builtin_print $56
	mov    rdi, CONST_STRING_56
	call   __builtin_print
														;jump %for_loop
	jmp    main_for_loop_8
														;%for_loop
main_for_loop_8:
														;$t57 = move $t8
	mov    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-112)], r11
														;$t8 = add $t8 1
	mov    r11, qword [rbp+(-120)]
	add    r11, 1
	mov    qword [rbp+(-120)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_after
main_for_after_9:
														;call __builtin_print $58
	mov    rdi, CONST_STRING_58
	call   __builtin_print
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_10
														;jump %exit
	jmp    main_exit_10
														;%exit
main_exit_10:
	leave
	ret


SECTION .data
a:
	dq 0
n:
	dq 0
CONST_STRING_56:
	db " ", 0
CONST_STRING_58:
	db "\n", 0
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