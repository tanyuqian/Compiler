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
    mov     rdi, 400
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
    sub     rsp, 48

    mov     qword[rbp-24], rdi
    mov     qword[rbp-16], rsi

    mov     rdi, 400
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

random:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 128
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
random_enter_0:
														;jump %entry
	jmp    random_entry_1
														;%entry
random_entry_1:
														;$t26 = rem $g8(seed) $g6(Q)
	mov    r11, qword [rel GV_seed]
	mov    rax, qword [rel GV_seed]
	cqo
	mov    r11, qword [rel GV_Q]
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-64)], r11
														;$t27 = mul $g4(A) $t26
	mov    r11, qword [rel GV_A]
	imul   r11, qword [rbp+(-64)]
	mov    qword [rbp+(-72)], r11
														;$t28 = div $g8(seed) $g6(Q)
	mov    r11, qword [rel GV_seed]
	mov    rax, qword [rel GV_seed]
	cqo
	mov    r11, qword [rel GV_Q]
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-96)], r11
														;$t29 = mul $g7(R) $t28
	mov    r11, qword [rel GV_R]
	imul   r11, qword [rbp+(-96)]
	mov    qword [rbp+(-120)], r11
														;$t30 = sub $t27 $t29
	mov    r11, qword [rbp+(-72)]
	sub    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-88)], r11
														;$t9 = move $t30
	mov    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-112)], r11
														;$t31 = sge $t9 0
	mov    r11, qword [rbp+(-112)]
	cmp    r11, 0
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t31 %if_true %if_false
	cmp    qword [rbp+(-80)], 0
	jnz    random_if_true_2
	jz     random_if_false_3
														;%if_true
random_if_true_2:
														;$g8(seed) = move $t9
	mov    r11, qword [rbp+(-112)]
	mov    qword [rel GV_seed], r11
														;jump %if_merge
	jmp    random_if_merge_4
														;%if_false
random_if_false_3:
														;$t32 = add $t9 $g5(M)
	mov    r11, qword [rbp+(-112)]
	add    r11, qword [rel GV_M]
	mov    qword [rbp+(-104)], r11
														;$g8(seed) = move $t32
	mov    r11, qword [rbp+(-104)]
	mov    qword [rel GV_seed], r11
														;jump %if_merge
	jmp    random_if_merge_4
														;%if_merge
random_if_merge_4:
														;ret $g8(seed)
	mov    rax, qword [rel GV_seed]
	leave
	ret
														;jump %exit
	jmp    random_exit_5
														;jump %exit
	jmp    random_exit_5
														;%exit
random_exit_5:
	leave
	ret


initialize:
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
initialize_enter_0:
														;jump %entry
	jmp    initialize_entry_1
														;%entry
initialize_entry_1:
														;$g8(seed) = move $p10
	mov    r11, qword [rbp+(-8)]
	mov    qword [rel GV_seed], r11
														;jump %exit
	jmp    initialize_exit_2
														;%exit
initialize_exit_2:
	leave
	ret


swap:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 152
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
swap_enter_0:
														;jump %entry
	jmp    swap_entry_1
														;%entry
swap_entry_1:
														;$t34 = mul $p11 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-112)], r11
														;$t33 = add $g3(a) $t34
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-80)], r11
														;$t35 = load 8 $t33 0
	mov    r11, qword [rbp+(-80)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-144)], r12
														;$t13 = move $t35
	mov    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-96)], r11
														;$t37 = mul $p11 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-128)], r11
														;$t36 = add $g3(a) $t37
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-88)], r11
														;$t39 = mul $p12 8
	mov    r11, qword [rbp+(-16)]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t38 = add $g3(a) $t39
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-72)], r11
														;$t40 = load 8 $t38 0
	mov    r11, qword [rbp+(-72)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-64)], r12
														;store 8 $t36 $t40 0
	mov    r11, qword [rbp+(-88)]
	add    r11, 0
	mov    rax, qword [rbp+(-64)]
	mov    qword [r11], rax
														;$t42 = mul $p12 8
	mov    r11, qword [rbp+(-16)]
	imul   r11, 8
	mov    qword [rbp+(-136)], r11
														;$t41 = add $g3(a) $t42
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-136)]
	mov    qword [rbp+(-104)], r11
														;store 8 $t41 $t13 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, qword [rbp+(-96)]
	mov    qword [r11], rax
														;jump %exit
	jmp    swap_exit_2
														;%exit
swap_exit_2:
	leave
	ret


pd:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 104
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
pd_enter_0:
														;jump %entry
	jmp    pd_entry_1
														;%entry
pd_entry_1:
														;jump %for_condition
	jmp    pd_for_condition_2
														;%for_condition
pd_for_condition_2:
														;$t43 = sle $g1(h) $p14
	mov    r11, qword [rel GV_h]
	cmp    r11, qword [rbp+(-8)]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-88)], r11
														;br $t43 %for_body %for_after
	cmp    qword [rbp+(-88)], 0
	jnz    pd_for_body_3
	jz     pd_for_after_8
														;%for_body
pd_for_body_3:
														;$t44 = add $g1(h) 1
	mov    r11, qword [rel GV_h]
	add    r11, 1
	mov    qword [rbp+(-96)], r11
														;$t45 = mul $g1(h) $t44
	mov    r11, qword [rel GV_h]
	imul   r11, qword [rbp+(-96)]
	mov    qword [rbp+(-64)], r11
														;$t46 = div $t45 2
	mov    r11, qword [rbp+(-64)]
	mov    rax, qword [rbp+(-64)]
	cqo
	mov    r11, 2
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-72)], r11
														;$t47 = seq $p14 $t46
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rbp+(-72)]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t47 %if_true %if_false
	cmp    qword [rbp+(-80)], 0
	jnz    pd_if_true_4
	jz     pd_if_false_5
														;%if_true
pd_if_true_4:
														;ret 1
	mov    rax, 1
	leave
	ret
														;jump %exit
	jmp    pd_exit_9
														;jump %if_merge
	jmp    pd_if_merge_6
														;%if_false
pd_if_false_5:
														;jump %if_merge
	jmp    pd_if_merge_6
														;%if_merge
pd_if_merge_6:
														;jump %for_loop
	jmp    pd_for_loop_7
														;%for_loop
pd_for_loop_7:
														;$g1(h) = add $g1(h) 1
	mov    r11, qword [rel GV_h]
	add    r11, 1
	mov    qword [rel GV_h], r11
														;jump %for_condition
	jmp    pd_for_condition_2
														;%for_after
pd_for_after_8:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    pd_exit_9
														;jump %exit
	jmp    pd_exit_9
														;%exit
pd_exit_9:
	leave
	ret


show:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 120
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
show_enter_0:
														;jump %entry
	jmp    show_entry_1
														;%entry
show_entry_1:
														;$t15 = move 0
	mov    r11, 0
	mov    qword [rbp+(-72)], r11
														;jump %for_condition
	jmp    show_for_condition_2
														;%for_condition
show_for_condition_2:
														;$t48 = slt $t15 $g2(now)
	mov    r11, qword [rbp+(-72)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t48 %for_body %for_after
	cmp    qword [rbp+(-80)], 0
	jnz    show_for_body_3
	jz     show_for_after_5
														;%for_body
show_for_body_3:
														;$t50 = mul $t15 8
	mov    r11, qword [rbp+(-72)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t49 = add $g3(a) $t50
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-64)], r11
														;$t51 = load 8 $t49 0
	mov    r11, qword [rbp+(-64)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-112)], r12
														;$t52 = call __builtin_toString $t51
	mov    rdi, qword [rbp+(-112)]
	call   __builtin_toString
	mov    qword [rbp+(-96)], rax
														;$t54 = call __builtin_string_concat $t52 $53
	mov    rdi, qword [rbp+(-96)]
	mov    rsi, CONST_STRING_53
	call   __builtin_string_concat
	mov    qword [rbp+(-104)], rax
														;call __builtin_print $t54
	mov    rdi, qword [rbp+(-104)]
	call   __builtin_print
														;jump %for_loop
	jmp    show_for_loop_4
														;%for_loop
show_for_loop_4:
														;$t15 = add $t15 1
	mov    r11, qword [rbp+(-72)]
	add    r11, 1
	mov    qword [rbp+(-72)], r11
														;jump %for_condition
	jmp    show_for_condition_2
														;%for_after
show_for_after_5:
														;call __builtin_println $55
	mov    rdi, CONST_STRING_55
	call   __builtin_println
														;jump %exit
	jmp    show_exit_6
														;%exit
show_exit_6:
	leave
	ret


win:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 384
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
win_enter_0:
														;jump %entry
	jmp    win_entry_1
														;%entry
win_entry_1:
														;$t57 = move 800
	mov    r11, 800
	mov    qword [rbp+(-64)], r11
														;$t57 = add $t57 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    qword [rbp+(-64)], r11
														;$t56 = alloc $t57
	mov    rdi, qword [rbp+(-64)]
	call   malloc
	mov    qword [rbp+(-184)], rax
														;store 8 $t56 100 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t56 = add $t56 8
	mov    r11, qword [rbp+(-184)]
	add    r11, 8
	mov    qword [rbp+(-184)], r11
														;$t18 = move $t56
	mov    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-200)], r11
														;$t58 = sne $g2(now) $g1(h)
	mov    r11, qword [rel GV_now]
	cmp    r11, qword [rel GV_h]
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-296)], r11
														;br $t58 %if_true %if_false
	cmp    qword [rbp+(-296)], 0
	jnz    win_if_true_2
	jz     win_if_false_3
														;%if_true
win_if_true_2:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    win_exit_27
														;jump %if_merge
	jmp    win_if_merge_4
														;%if_false
win_if_false_3:
														;jump %if_merge
	jmp    win_if_merge_4
														;%if_merge
win_if_merge_4:
														;$t17 = move 0
	mov    r11, 0
	mov    qword [rbp+(-352)], r11
														;jump %for_condition
	jmp    win_for_condition_5
														;%for_condition
win_for_condition_5:
														;$t59 = slt $t17 $g2(now)
	mov    r11, qword [rbp+(-352)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-176)], r11
														;br $t59 %for_body %for_after
	cmp    qword [rbp+(-176)], 0
	jnz    win_for_body_6
	jz     win_for_after_8
														;%for_body
win_for_body_6:
														;$t61 = mul $t17 8
	mov    r11, qword [rbp+(-352)]
	imul   r11, 8
	mov    qword [rbp+(-336)], r11
														;$t60 = add $t18 $t61
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-336)]
	mov    qword [rbp+(-280)], r11
														;$t63 = mul $t17 8
	mov    r11, qword [rbp+(-352)]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t62 = add $g3(a) $t63
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-360)], r11
														;$t64 = load 8 $t62 0
	mov    r11, qword [rbp+(-360)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-376)], r12
														;store 8 $t60 $t64 0
	mov    r11, qword [rbp+(-280)]
	add    r11, 0
	mov    rax, qword [rbp+(-376)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    win_for_loop_7
														;%for_loop
win_for_loop_7:
														;$t17 = add $t17 1
	mov    r11, qword [rbp+(-352)]
	add    r11, 1
	mov    qword [rbp+(-352)], r11
														;jump %for_condition
	jmp    win_for_condition_5
														;%for_after
win_for_after_8:
														;$t16 = move 0
	mov    r11, 0
	mov    qword [rbp+(-240)], r11
														;jump %for_condition
	jmp    win_for_condition_9
														;%for_condition
win_for_condition_9:
														;$t65 = sub $g2(now) 1
	mov    r11, qword [rel GV_now]
	sub    r11, 1
	mov    qword [rbp+(-208)], r11
														;$t66 = slt $t16 $t65
	mov    r11, qword [rbp+(-240)]
	cmp    r11, qword [rbp+(-208)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-368)], r11
														;br $t66 %for_body %for_after
	cmp    qword [rbp+(-368)], 0
	jnz    win_for_body_10
	jz     win_for_after_19
														;%for_body
win_for_body_10:
														;$t67 = add $t16 1
	mov    r11, qword [rbp+(-240)]
	add    r11, 1
	mov    qword [rbp+(-320)], r11
														;$t17 = move $t67
	mov    r11, qword [rbp+(-320)]
	mov    qword [rbp+(-352)], r11
														;jump %for_condition
	jmp    win_for_condition_11
														;%for_condition
win_for_condition_11:
														;$t68 = slt $t17 $g2(now)
	mov    r11, qword [rbp+(-352)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-168)], r11
														;br $t68 %for_body %for_after
	cmp    qword [rbp+(-168)], 0
	jnz    win_for_body_12
	jz     win_for_after_17
														;%for_body
win_for_body_12:
														;$t70 = mul $t16 8
	mov    r11, qword [rbp+(-240)]
	imul   r11, 8
	mov    qword [rbp+(-272)], r11
														;$t69 = add $t18 $t70
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-272)]
	mov    qword [rbp+(-288)], r11
														;$t71 = load 8 $t69 0
	mov    r11, qword [rbp+(-288)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-152)], r12
														;$t73 = mul $t17 8
	mov    r11, qword [rbp+(-352)]
	imul   r11, 8
	mov    qword [rbp+(-248)], r11
														;$t72 = add $t18 $t73
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-248)]
	mov    qword [rbp+(-216)], r11
														;$t74 = load 8 $t72 0
	mov    r11, qword [rbp+(-216)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-312)], r12
														;$t75 = sgt $t71 $t74
	mov    r11, qword [rbp+(-152)]
	cmp    r11, qword [rbp+(-312)]
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t75 %if_true %if_false
	cmp    qword [rbp+(-136)], 0
	jnz    win_if_true_13
	jz     win_if_false_14
														;%if_true
win_if_true_13:
														;$t77 = mul $t16 8
	mov    r11, qword [rbp+(-240)]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t76 = add $t18 $t77
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-224)], r11
														;$t78 = load 8 $t76 0
	mov    r11, qword [rbp+(-224)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-344)], r12
														;$t19 = move $t78
	mov    r11, qword [rbp+(-344)]
	mov    qword [rbp+(-304)], r11
														;$t80 = mul $t16 8
	mov    r11, qword [rbp+(-240)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t79 = add $t18 $t80
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-104)], r11
														;$t82 = mul $t17 8
	mov    r11, qword [rbp+(-352)]
	imul   r11, 8
	mov    qword [rbp+(-112)], r11
														;$t81 = add $t18 $t82
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-144)], r11
														;$t83 = load 8 $t81 0
	mov    r11, qword [rbp+(-144)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-232)], r12
														;store 8 $t79 $t83 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, qword [rbp+(-232)]
	mov    qword [r11], rax
														;$t85 = mul $t17 8
	mov    r11, qword [rbp+(-352)]
	imul   r11, 8
	mov    qword [rbp+(-328)], r11
														;$t84 = add $t18 $t85
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-328)]
	mov    qword [rbp+(-128)], r11
														;store 8 $t84 $t19 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    rax, qword [rbp+(-304)]
	mov    qword [r11], rax
														;jump %if_merge
	jmp    win_if_merge_15
														;%if_false
win_if_false_14:
														;jump %if_merge
	jmp    win_if_merge_15
														;%if_merge
win_if_merge_15:
														;jump %for_loop
	jmp    win_for_loop_16
														;%for_loop
win_for_loop_16:
														;$t17 = add $t17 1
	mov    r11, qword [rbp+(-352)]
	add    r11, 1
	mov    qword [rbp+(-352)], r11
														;jump %for_condition
	jmp    win_for_condition_11
														;%for_after
win_for_after_17:
														;jump %for_loop
	jmp    win_for_loop_18
														;%for_loop
win_for_loop_18:
														;$t16 = add $t16 1
	mov    r11, qword [rbp+(-240)]
	add    r11, 1
	mov    qword [rbp+(-240)], r11
														;jump %for_condition
	jmp    win_for_condition_9
														;%for_after
win_for_after_19:
														;$t16 = move 0
	mov    r11, 0
	mov    qword [rbp+(-240)], r11
														;jump %for_condition
	jmp    win_for_condition_20
														;%for_condition
win_for_condition_20:
														;$t86 = slt $t16 $g2(now)
	mov    r11, qword [rbp+(-240)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t86 %for_body %for_after
	cmp    qword [rbp+(-80)], 0
	jnz    win_for_body_21
	jz     win_for_after_26
														;%for_body
win_for_body_21:
														;$t88 = mul $t16 8
	mov    r11, qword [rbp+(-240)]
	imul   r11, 8
	mov    qword [rbp+(-96)], r11
														;$t87 = add $t18 $t88
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-264)], r11
														;$t89 = load 8 $t87 0
	mov    r11, qword [rbp+(-264)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-192)], r12
														;$t90 = add $t16 1
	mov    r11, qword [rbp+(-240)]
	add    r11, 1
	mov    qword [rbp+(-160)], r11
														;$t91 = sne $t89 $t90
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rbp+(-160)]
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-256)], r11
														;br $t91 %if_true %if_false
	cmp    qword [rbp+(-256)], 0
	jnz    win_if_true_22
	jz     win_if_false_23
														;%if_true
win_if_true_22:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    win_exit_27
														;jump %if_merge
	jmp    win_if_merge_24
														;%if_false
win_if_false_23:
														;jump %if_merge
	jmp    win_if_merge_24
														;%if_merge
win_if_merge_24:
														;jump %for_loop
	jmp    win_for_loop_25
														;%for_loop
win_for_loop_25:
														;$t16 = add $t16 1
	mov    r11, qword [rbp+(-240)]
	add    r11, 1
	mov    qword [rbp+(-240)], r11
														;jump %for_condition
	jmp    win_for_condition_20
														;%for_after
win_for_after_26:
														;ret 1
	mov    rax, 1
	leave
	ret
														;jump %exit
	jmp    win_exit_27
														;jump %exit
	jmp    win_exit_27
														;%exit
win_exit_27:
	leave
	ret


merge:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 208
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
merge_enter_0:
														;jump %entry
	jmp    merge_entry_1
														;%entry
merge_entry_1:
														;$t20 = move 0
	mov    r11, 0
	mov    qword [rbp+(-192)], r11
														;jump %for_condition
	jmp    merge_for_condition_2
														;%for_condition
merge_for_condition_2:
														;$t92 = slt $t20 $g2(now)
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-96)], r11
														;br $t92 %for_body %for_after
	cmp    qword [rbp+(-96)], 0
	jnz    merge_for_body_3
	jz     merge_for_after_15
														;%for_body
merge_for_body_3:
														;$t94 = mul $t20 8
	mov    r11, qword [rbp+(-192)]
	imul   r11, 8
	mov    qword [rbp+(-184)], r11
														;$t93 = add $g3(a) $t94
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-64)], r11
														;$t95 = load 8 $t93 0
	mov    r11, qword [rbp+(-64)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-120)], r12
														;$t96 = seq $t95 0
	mov    r11, qword [rbp+(-120)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-144)], r11
														;br $t96 %if_true %if_false
	cmp    qword [rbp+(-144)], 0
	jnz    merge_if_true_4
	jz     merge_if_false_12
														;%if_true
merge_if_true_4:
														;$t97 = add $t20 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-176)], r11
														;$t21 = move $t97
	mov    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-80)], r11
														;jump %for_condition
	jmp    merge_for_condition_5
														;%for_condition
merge_for_condition_5:
														;$t98 = slt $t21 $g2(now)
	mov    r11, qword [rbp+(-80)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t98 %for_body %for_after
	cmp    qword [rbp+(-136)], 0
	jnz    merge_for_body_6
	jz     merge_for_after_11
														;%for_body
merge_for_body_6:
														;$t100 = mul $t21 8
	mov    r11, qword [rbp+(-80)]
	imul   r11, 8
	mov    qword [rbp+(-104)], r11
														;$t99 = add $g3(a) $t100
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-128)], r11
														;$t101 = load 8 $t99 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-160)], r12
														;$t102 = sne $t101 0
	mov    r11, qword [rbp+(-160)]
	cmp    r11, 0
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-200)], r11
														;br $t102 %if_true %if_false
	cmp    qword [rbp+(-200)], 0
	jnz    merge_if_true_7
	jz     merge_if_false_8
														;%if_true
merge_if_true_7:
														;call swap $t20 $t21
	mov    rdi, qword [rbp+(-192)]
	mov    rsi, qword [rbp+(-80)]
	call   swap
														;jump %for_after
	jmp    merge_for_after_11
														;jump %if_merge
	jmp    merge_if_merge_9
														;%if_false
merge_if_false_8:
														;jump %if_merge
	jmp    merge_if_merge_9
														;%if_merge
merge_if_merge_9:
														;jump %for_loop
	jmp    merge_for_loop_10
														;%for_loop
merge_for_loop_10:
														;$t21 = add $t21 1
	mov    r11, qword [rbp+(-80)]
	add    r11, 1
	mov    qword [rbp+(-80)], r11
														;jump %for_condition
	jmp    merge_for_condition_5
														;%for_after
merge_for_after_11:
														;jump %if_merge
	jmp    merge_if_merge_13
														;%if_false
merge_if_false_12:
														;jump %if_merge
	jmp    merge_if_merge_13
														;%if_merge
merge_if_merge_13:
														;jump %for_loop
	jmp    merge_for_loop_14
														;%for_loop
merge_for_loop_14:
														;$t20 = add $t20 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-192)], r11
														;jump %for_condition
	jmp    merge_for_condition_2
														;%for_after
merge_for_after_15:
														;$t20 = move 0
	mov    r11, 0
	mov    qword [rbp+(-192)], r11
														;jump %for_condition
	jmp    merge_for_condition_16
														;%for_condition
merge_for_condition_16:
														;$t103 = slt $t20 $g2(now)
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-168)], r11
														;br $t103 %for_body %for_after
	cmp    qword [rbp+(-168)], 0
	jnz    merge_for_body_17
	jz     merge_for_after_22
														;%for_body
merge_for_body_17:
														;$t105 = mul $t20 8
	mov    r11, qword [rbp+(-192)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t104 = add $g3(a) $t105
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-72)], r11
														;$t106 = load 8 $t104 0
	mov    r11, qword [rbp+(-72)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-112)], r12
														;$t107 = seq $t106 0
	mov    r11, qword [rbp+(-112)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-152)], r11
														;br $t107 %if_true %if_false
	cmp    qword [rbp+(-152)], 0
	jnz    merge_if_true_18
	jz     merge_if_false_19
														;%if_true
merge_if_true_18:
														;$g2(now) = move $t20
	mov    r11, qword [rbp+(-192)]
	mov    qword [rel GV_now], r11
														;jump %for_after
	jmp    merge_for_after_22
														;jump %if_merge
	jmp    merge_if_merge_20
														;%if_false
merge_if_false_19:
														;jump %if_merge
	jmp    merge_if_merge_20
														;%if_merge
merge_if_merge_20:
														;jump %for_loop
	jmp    merge_for_loop_21
														;%for_loop
merge_for_loop_21:
														;$t20 = add $t20 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-192)], r11
														;jump %for_condition
	jmp    merge_for_condition_16
														;%for_after
merge_for_after_22:
														;jump %exit
	jmp    merge_exit_23
														;%exit
merge_exit_23:
	leave
	ret


move:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 136
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
move_enter_0:
														;jump %entry
	jmp    move_entry_1
														;%entry
move_entry_1:
														;$t22 = move 0
	mov    r11, 0
	mov    qword [rbp+(-64)], r11
														;jump %for_condition
	jmp    move_for_condition_2
														;%for_condition
move_for_condition_2:
														;$t108 = slt $t22 $g2(now)
	mov    r11, qword [rbp+(-64)]
	cmp    r11, qword [rel GV_now]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-120)], r11
														;br $t108 %for_body %for_after
	cmp    qword [rbp+(-120)], 0
	jnz    move_for_body_3
	jz     move_for_after_5
														;%for_body
move_for_body_3:
														;$t110 = mul $t22 8
	mov    r11, qword [rbp+(-64)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t109 = add $g3(a) $t110
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-72)], r11
														;$t111 = load 8 $t109 0
	mov    r11, qword [rbp+(-72)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-128)], r12
														;$t111 = sub $t111 1
	mov    r11, qword [rbp+(-128)]
	sub    r11, 1
	mov    qword [rbp+(-128)], r11
														;store 8 $t109 $t111 0
	mov    r11, qword [rbp+(-72)]
	add    r11, 0
	mov    rax, qword [rbp+(-128)]
	mov    qword [r11], rax
														;$t112 = add $t22 1
	mov    r11, qword [rbp+(-64)]
	add    r11, 1
	mov    qword [rbp+(-104)], r11
														;$t22 = move $t112
	mov    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-64)], r11
														;jump %for_loop
	jmp    move_for_loop_4
														;%for_loop
move_for_loop_4:
														;jump %for_condition
	jmp    move_for_condition_2
														;%for_after
move_for_after_5:
														;$t114 = mul $g2(now) 8
	mov    r11, qword [rel GV_now]
	imul   r11, 8
	mov    qword [rbp+(-96)], r11
														;$t113 = add $g3(a) $t114
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-80)], r11
														;store 8 $t113 $g2(now) 0
	mov    r11, qword [rbp+(-80)]
	add    r11, 0
	mov    rax, qword [rel GV_now]
	mov    qword [r11], rax
														;$t115 = move $g2(now)
	mov    r11, qword [rel GV_now]
	mov    qword [rbp+(-112)], r11
														;$g2(now) = add $g2(now) 1
	mov    r11, qword [rel GV_now]
	add    r11, 1
	mov    qword [rel GV_now], r11
														;jump %exit
	jmp    move_exit_6
														;%exit
move_exit_6:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 432
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
main_enter_0:
														;$g4(A) = move 48271
	mov    r11, 48271
	mov    qword [rel GV_A], r11
														;$g5(M) = move 2147483647
	mov    r11, 2147483647
	mov    qword [rel GV_M], r11
														;$g8(seed) = move 1
	mov    r11, 1
	mov    qword [rel GV_seed], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t23 = move 0
	mov    r11, 0
	mov    qword [rbp+(-128)], r11
														;$t24 = move 0
	mov    r11, 0
	mov    qword [rbp+(-80)], r11
														;$t25 = move 0
	mov    r11, 0
	mov    qword [rbp+(-384)], r11
														;$g0(n) = move 210
	mov    r11, 210
	mov    qword [rel GV_n], r11
														;$g1(h) = move 0
	mov    r11, 0
	mov    qword [rel GV_h], r11
														;$t117 = move 800
	mov    r11, 800
	mov    qword [rbp+(-400)], r11
														;$t117 = add $t117 8
	mov    r11, qword [rbp+(-400)]
	add    r11, 8
	mov    qword [rbp+(-400)], r11
														;$t116 = alloc $t117
	mov    rdi, qword [rbp+(-400)]
	call   malloc
	mov    qword [rbp+(-376)], rax
														;store 8 $t116 100 0
	mov    r11, qword [rbp+(-376)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t116 = add $t116 8
	mov    r11, qword [rbp+(-376)]
	add    r11, 8
	mov    qword [rbp+(-376)], r11
														;$g3(a) = move $t116
	mov    r11, qword [rbp+(-376)]
	mov    qword [rel GV_a], r11
														;$t118 = div $g5(M) $g4(A)
	mov    r11, qword [rel GV_M]
	mov    rax, qword [rel GV_M]
	cqo
	mov    r11, qword [rel GV_A]
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-192)], r11
														;$g6(Q) = move $t118
	mov    r11, qword [rbp+(-192)]
	mov    qword [rel GV_Q], r11
														;$t119 = rem $g5(M) $g4(A)
	mov    r11, qword [rel GV_M]
	mov    rax, qword [rel GV_M]
	cqo
	mov    r11, qword [rel GV_A]
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-392)], r11
														;$g7(R) = move $t119
	mov    r11, qword [rbp+(-392)]
	mov    qword [rel GV_R], r11
														;$t120 = call pd $g0(n)
	mov    rdi, qword [rel GV_n]
	call   pd
	mov    qword [rbp+(-248)], rax
														;$t121 = xor $t120 1
	mov    r11, qword [rbp+(-248)]
	xor    r11, 1
	mov    qword [rbp+(-368)], r11
														;br $t121 %if_true %if_false
	cmp    qword [rbp+(-368)], 0
	jnz    main_if_true_2
	jz     main_if_false_3
														;%if_true
main_if_true_2:
														;call __builtin_println $122
	mov    rdi, CONST_STRING_122
	call   __builtin_println
														;ret 1
	mov    rax, 1
	leave
	ret
														;jump %exit
	jmp    main_exit_15
														;jump %if_merge
	jmp    main_if_merge_4
														;%if_false
main_if_false_3:
														;jump %if_merge
	jmp    main_if_merge_4
														;%if_merge
main_if_merge_4:
														;call __builtin_println $123
	mov    rdi, CONST_STRING_123
	call   __builtin_println
														;call initialize 3654898
	mov    rdi, 3654898
	call   initialize
														;$t124 = call random
	call   random
	mov    qword [rbp+(-296)], rax
														;$t125 = rem $t124 10
	mov    r11, qword [rbp+(-296)]
	mov    rax, qword [rbp+(-296)]
	cqo
	mov    r11, 10
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-184)], r11
														;$t126 = add $t125 1
	mov    r11, qword [rbp+(-184)]
	add    r11, 1
	mov    qword [rbp+(-88)], r11
														;$g2(now) = move $t126
	mov    r11, qword [rbp+(-88)]
	mov    qword [rel GV_now], r11
														;$t127 = call __builtin_toString $g2(now)
	mov    rdi, qword [rel GV_now]
	call   __builtin_toString
	mov    qword [rbp+(-64)], rax
														;call __builtin_println $t127
	mov    rdi, qword [rbp+(-64)]
	call   __builtin_println
														;jump %for_condition
	jmp    main_for_condition_5
														;%for_condition
main_for_condition_5:
														;$t128 = sub $g2(now) 1
	mov    r11, qword [rel GV_now]
	sub    r11, 1
	mov    qword [rbp+(-280)], r11
														;$t129 = slt $t23 $t128
	mov    r11, qword [rbp+(-128)]
	cmp    r11, qword [rbp+(-280)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-216)], r11
														;br $t129 %for_body %for_after
	cmp    qword [rbp+(-216)], 0
	jnz    main_for_body_6
	jz     main_for_after_11
														;%for_body
main_for_body_6:
														;$t131 = mul $t23 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-152)], r11
														;$t130 = add $g3(a) $t131
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-136)], r11
														;$t132 = call random
	call   random
	mov    qword [rbp+(-272)], rax
														;$t133 = rem $t132 10
	mov    r11, qword [rbp+(-272)]
	mov    rax, qword [rbp+(-272)]
	cqo
	mov    r11, 10
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-288)], r11
														;$t134 = add $t133 1
	mov    r11, qword [rbp+(-288)]
	add    r11, 1
	mov    qword [rbp+(-96)], r11
														;store 8 $t130 $t134 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    rax, qword [rbp+(-96)]
	mov    qword [r11], rax
														;jump %while_loop
	jmp    main_while_loop_7
														;%while_loop
main_while_loop_7:
														;$t136 = mul $t23 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-320)], r11
														;$t135 = add $g3(a) $t136
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-320)]
	mov    qword [rbp+(-240)], r11
														;$t137 = load 8 $t135 0
	mov    r11, qword [rbp+(-240)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-200)], r12
														;$t138 = add $t137 $t24
	mov    r11, qword [rbp+(-200)]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-360)], r11
														;$t139 = sgt $t138 $g0(n)
	mov    r11, qword [rbp+(-360)]
	cmp    r11, qword [rel GV_n]
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t139 %while_body %while_after
	cmp    qword [rbp+(-72)], 0
	jnz    main_while_body_8
	jz     main_while_after_9
														;%while_body
main_while_body_8:
														;$t141 = mul $t23 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-256)], r11
														;$t140 = add $g3(a) $t141
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-256)]
	mov    qword [rbp+(-312)], r11
														;$t142 = call random
	call   random
	mov    qword [rbp+(-232)], rax
														;$t143 = rem $t142 10
	mov    r11, qword [rbp+(-232)]
	mov    rax, qword [rbp+(-232)]
	cqo
	mov    r11, 10
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-104)], r11
														;$t144 = add $t143 1
	mov    r11, qword [rbp+(-104)]
	add    r11, 1
	mov    qword [rbp+(-160)], r11
														;store 8 $t140 $t144 0
	mov    r11, qword [rbp+(-312)]
	add    r11, 0
	mov    rax, qword [rbp+(-160)]
	mov    qword [r11], rax
														;jump %while_loop
	jmp    main_while_loop_7
														;%while_after
main_while_after_9:
														;$t146 = mul $t23 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-344)], r11
														;$t145 = add $g3(a) $t146
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-344)]
	mov    qword [rbp+(-112)], r11
														;$t147 = load 8 $t145 0
	mov    r11, qword [rbp+(-112)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-328)], r12
														;$t148 = add $t24 $t147
	mov    r11, qword [rbp+(-80)]
	add    r11, qword [rbp+(-328)]
	mov    qword [rbp+(-352)], r11
														;$t24 = move $t148
	mov    r11, qword [rbp+(-352)]
	mov    qword [rbp+(-80)], r11
														;jump %for_loop
	jmp    main_for_loop_10
														;%for_loop
main_for_loop_10:
														;$t23 = add $t23 1
	mov    r11, qword [rbp+(-128)]
	add    r11, 1
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_5
														;%for_after
main_for_after_11:
														;$t149 = sub $g2(now) 1
	mov    r11, qword [rel GV_now]
	sub    r11, 1
	mov    qword [rbp+(-208)], r11
														;$t151 = mul $t149 8
	mov    r11, qword [rbp+(-208)]
	imul   r11, 8
	mov    qword [rbp+(-424)], r11
														;$t150 = add $g3(a) $t151
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-424)]
	mov    qword [rbp+(-408)], r11
														;$t152 = sub $g0(n) $t24
	mov    r11, qword [rel GV_n]
	sub    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-416)], r11
														;store 8 $t150 $t152 0
	mov    r11, qword [rbp+(-408)]
	add    r11, 0
	mov    rax, qword [rbp+(-416)]
	mov    qword [r11], rax
														;call show
	call   show
														;call merge
	call   merge
														;jump %while_loop
	jmp    main_while_loop_12
														;%while_loop
main_while_loop_12:
														;$t153 = call win
	call   win
	mov    qword [rbp+(-168)], rax
														;$t154 = xor $t153 1
	mov    r11, qword [rbp+(-168)]
	xor    r11, 1
	mov    qword [rbp+(-224)], r11
														;br $t154 %while_body %while_after
	cmp    qword [rbp+(-224)], 0
	jnz    main_while_body_13
	jz     main_while_after_14
														;%while_body
main_while_body_13:
														;$t25 = add $t25 1
	mov    r11, qword [rbp+(-384)]
	add    r11, 1
	mov    qword [rbp+(-384)], r11
														;$t156 = call __builtin_toString $t25
	mov    rdi, qword [rbp+(-384)]
	call   __builtin_toString
	mov    qword [rbp+(-144)], rax
														;$t157 = call __builtin_string_concat $155 $t156
	mov    rdi, CONST_STRING_155
	mov    rsi, qword [rbp+(-144)]
	call   __builtin_string_concat
	mov    qword [rbp+(-304)], rax
														;$t159 = call __builtin_string_concat $t157 $158
	mov    rdi, qword [rbp+(-304)]
	mov    rsi, CONST_STRING_158
	call   __builtin_string_concat
	mov    qword [rbp+(-264)], rax
														;call __builtin_println $t159
	mov    rdi, qword [rbp+(-264)]
	call   __builtin_println
														;call move
	call   move
														;call merge
	call   merge
														;call show
	call   show
														;jump %while_loop
	jmp    main_while_loop_12
														;%while_after
main_while_after_14:
														;$t161 = call __builtin_toString $t25
	mov    rdi, qword [rbp+(-384)]
	call   __builtin_toString
	mov    qword [rbp+(-336)], rax
														;$t162 = call __builtin_string_concat $160 $t161
	mov    rdi, CONST_STRING_160
	mov    rsi, qword [rbp+(-336)]
	call   __builtin_string_concat
	mov    qword [rbp+(-120)], rax
														;$t164 = call __builtin_string_concat $t162 $163
	mov    rdi, qword [rbp+(-120)]
	mov    rsi, CONST_STRING_163
	call   __builtin_string_concat
	mov    qword [rbp+(-176)], rax
														;call __builtin_println $t164
	mov    rdi, qword [rbp+(-176)]
	call   __builtin_println
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_15
														;jump %exit
	jmp    main_exit_15
														;%exit
main_exit_15:
	leave
	ret


SECTION .data
CONST_STRING_155:
	db 115, 116, 101, 112, 32, 0
CONST_STRING_160:
	db 84, 111, 116, 97, 108, 58, 32, 0
GV_Q:
	dq 0
GV_h:
	dq 0
CONST_STRING_53:
	db 32, 0
GV_seed:
	dq 0
GV_R:
	dq 0
GV_a:
	dq 0
CONST_STRING_163:
	db 32, 115, 116, 101, 112, 40, 115, 41, 0
CONST_STRING_158:
	db 58, 0
GV_M:
	dq 0
CONST_STRING_122:
	db 83, 111, 114, 114, 121, 44, 32, 116, 104, 101, 32, 110, 117, 109, 98, 101, 114, 32, 110, 32, 109, 117, 115, 116, 32, 98, 101, 32, 97, 32, 110, 117, 109, 98, 101, 114, 32, 115, 46, 116, 46, 32, 116, 104, 101, 114, 101, 32, 101, 120, 105, 115, 116, 115, 32, 105, 32, 115, 97, 116, 105, 115, 102, 121, 105, 110, 103, 32, 110, 61, 49, 43, 50, 43, 46, 46, 46, 43, 105, 0
CONST_STRING_55:
	db 0
GV_now:
	dq 0
GV_n:
	dq 0
CONST_STRING_123:
	db 76, 101, 116, 39, 115, 32, 115, 116, 97, 114, 116, 33, 0
GV_A:
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

