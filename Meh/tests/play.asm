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

check:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 272
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
check_enter_0:
														;jump %entry
	jmp    check_entry_1
														;%entry
check_entry_1:
														;$t8 = move 0
	mov    r11, 0
	mov    qword [rbp+(-112)], r11
														;jump %for_condition
	jmp    check_for_condition_2
														;%for_condition
check_for_condition_2:
														;$t26 = slt $t8 $g5(m)
	mov    r11, qword [rbp+(-112)]
	cmp    r11, qword [rel GV_m]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-256)], r11
														;br $t26 %for_body %for_after
	cmp    qword [rbp+(-256)], 0
	jnz    check_for_body_3
	jz     check_for_after_24
														;%for_body
check_for_body_3:
														;$t10 = move 0
	mov    r11, 0
	mov    qword [rbp+(-144)], r11
														;$t9 = move 0
	mov    r11, 0
	mov    qword [rbp+(-80)], r11
														;jump %for_condition
	jmp    check_for_condition_4
														;%for_condition
check_for_condition_4:
														;$t27 = slt $t9 3
	mov    r11, qword [rbp+(-80)]
	cmp    r11, 3
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-104)], r11
														;br $t27 %for_body %for_after
	cmp    qword [rbp+(-104)], 0
	jnz    check_for_body_5
	jz     check_for_after_19
														;%for_body
check_for_body_5:
														;$t29 = mul $t8 8
	mov    r11, qword [rbp+(-112)]
	imul   r11, 8
	mov    qword [rbp+(-168)], r11
														;$t28 = add $g0(sat) $t29
	mov    r11, qword [rel GV_sat]
	add    r11, qword [rbp+(-168)]
	mov    qword [rbp+(-208)], r11
														;$t30 = load 8 $t28 0
	mov    r11, qword [rbp+(-208)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-224)], rax
														;$t32 = mul $t9 8
	mov    r11, qword [rbp+(-80)]
	imul   r11, 8
	mov    qword [rbp+(-232)], r11
														;$t31 = add $t30 $t32
	mov    r11, qword [rbp+(-224)]
	add    r11, qword [rbp+(-232)]
	mov    qword [rbp+(-176)], r11
														;$t33 = load 8 $t31 0
	mov    r11, qword [rbp+(-176)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-192)], rax
														;$t11 = move $t33
	mov    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-96)], r11
														;$t35 = sgt $t11 0
	mov    r11, qword [rbp+(-96)]
	cmp    r11, 0
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-216)], r11
														;br $t35 %logical_true %logical_false
	cmp    qword [rbp+(-216)], 0
	jnz    check_logical_true_6
	jz     check_logical_false_7
														;%logical_true
check_logical_true_6:
														;$t37 = mul $t11 8
	mov    r11, qword [rbp+(-96)]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t36 = add $g1(assignment) $t37
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-128)], r11
														;$t38 = load 8 $t36 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-152)], rax
														;$t39 = seq $t38 1
	mov    r11, qword [rbp+(-152)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;$t34 = move $t39
	mov    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-88)], r11
														;jump %logical_merge
	jmp    check_logical_merge_8
														;%logical_false
check_logical_false_7:
														;$t34 = move 0
	mov    r11, 0
	mov    qword [rbp+(-88)], r11
														;jump %logical_merge
	jmp    check_logical_merge_8
														;%logical_merge
check_logical_merge_8:
														;br $t34 %if_true %if_false
	cmp    qword [rbp+(-88)], 0
	jnz    check_if_true_9
	jz     check_if_false_10
														;%if_true
check_if_true_9:
														;$t10 = move 1
	mov    r11, 1
	mov    qword [rbp+(-144)], r11
														;jump %if_merge
	jmp    check_if_merge_11
														;%if_false
check_if_false_10:
														;jump %if_merge
	jmp    check_if_merge_11
														;%if_merge
check_if_merge_11:
														;$t41 = slt $t11 0
	mov    r11, qword [rbp+(-96)]
	cmp    r11, 0
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t41 %logical_true %logical_false
	cmp    qword [rbp+(-136)], 0
	jnz    check_logical_true_12
	jz     check_logical_false_13
														;%logical_true
check_logical_true_12:
														;$t42 = neg $t11
	mov    r11, qword [rbp+(-96)]
	neg    r11
	mov    qword [rbp+(-184)], r11
														;$t44 = mul $t42 8
	mov    r11, qword [rbp+(-184)]
	imul   r11, 8
	mov    qword [rbp+(-248)], r11
														;$t43 = add $g1(assignment) $t44
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-248)]
	mov    qword [rbp+(-240)], r11
														;$t45 = load 8 $t43 0
	mov    r11, qword [rbp+(-240)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-72)], rax
														;$t46 = seq $t45 0
	mov    r11, qword [rbp+(-72)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-264)], r11
														;$t40 = move $t46
	mov    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-200)], r11
														;jump %logical_merge
	jmp    check_logical_merge_14
														;%logical_false
check_logical_false_13:
														;$t40 = move 0
	mov    r11, 0
	mov    qword [rbp+(-200)], r11
														;jump %logical_merge
	jmp    check_logical_merge_14
														;%logical_merge
check_logical_merge_14:
														;br $t40 %if_true %if_false
	cmp    qword [rbp+(-200)], 0
	jnz    check_if_true_15
	jz     check_if_false_16
														;%if_true
check_if_true_15:
														;$t10 = move 1
	mov    r11, 1
	mov    qword [rbp+(-144)], r11
														;jump %if_merge
	jmp    check_if_merge_17
														;%if_false
check_if_false_16:
														;jump %if_merge
	jmp    check_if_merge_17
														;%if_merge
check_if_merge_17:
														;jump %for_loop
	jmp    check_for_loop_18
														;%for_loop
check_for_loop_18:
														;$t9 = add $t9 1
	mov    r11, qword [rbp+(-80)]
	add    r11, 1
	mov    qword [rbp+(-80)], r11
														;jump %for_condition
	jmp    check_for_condition_4
														;%for_after
check_for_after_19:
														;$t47 = seq $t10 0
	mov    r11, qword [rbp+(-144)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-160)], r11
														;br $t47 %if_true %if_false
	cmp    qword [rbp+(-160)], 0
	jnz    check_if_true_20
	jz     check_if_false_21
														;%if_true
check_if_true_20:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    check_exit_25
														;jump %if_merge
	jmp    check_if_merge_22
														;%if_false
check_if_false_21:
														;jump %if_merge
	jmp    check_if_merge_22
														;%if_merge
check_if_merge_22:
														;jump %for_loop
	jmp    check_for_loop_23
														;%for_loop
check_for_loop_23:
														;$t8 = add $t8 1
	mov    r11, qword [rbp+(-112)]
	add    r11, 1
	mov    qword [rbp+(-112)], r11
														;jump %for_condition
	jmp    check_for_condition_2
														;%for_after
check_for_after_24:
														;ret 1
	mov    rax, 1
	leave
	ret
														;jump %exit
	jmp    check_exit_25
														;jump %exit
	jmp    check_exit_25
														;%exit
check_exit_25:
	leave
	ret


myprint:
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
myprint_enter_0:
														;jump %entry
	jmp    myprint_entry_1
														;%entry
myprint_entry_1:
														;$t13 = move 0
	mov    r11, 0
	mov    qword [rbp+(-96)], r11
														;jump %for_condition
	jmp    myprint_for_condition_2
														;%for_condition
myprint_for_condition_2:
														;$t48 = call __builtin_getStringLength $p12
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getStringLength
	mov    qword [rbp+(-88)], rax
														;$t49 = slt $t13 $t48
	mov    r11, qword [rbp+(-96)]
	cmp    r11, qword [rbp+(-88)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t49 %for_body %for_after
	cmp    qword [rbp+(-64)], 0
	jnz    myprint_for_body_3
	jz     myprint_for_after_5
														;%for_body
myprint_for_body_3:
														;$t50 = call __builtin_ord $p12 $t13
	mov    rdi, qword [rbp+(-8)]
	mov    rsi, qword [rbp+(-96)]
	call   __builtin_ord
	mov    qword [rbp+(-80)], rax
														;$t51 = add $g7(myHash) $t50
	mov    r11, qword [rel GV_myHash]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-72)], r11
														;$g7(myHash) = move $t51
	mov    r11, qword [rbp+(-72)]
	mov    qword [rel GV_myHash], r11
														;jump %for_loop
	jmp    myprint_for_loop_4
														;%for_loop
myprint_for_loop_4:
														;$t13 = add $t13 1
	mov    r11, qword [rbp+(-96)]
	add    r11, 1
	mov    qword [rbp+(-96)], r11
														;jump %for_condition
	jmp    myprint_for_condition_2
														;%for_after
myprint_for_after_5:
														;jump %exit
	jmp    myprint_exit_6
														;%exit
myprint_exit_6:
	leave
	ret


payoff:
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
payoff_enter_0:
														;jump %entry
	jmp    payoff_entry_1
														;%entry
payoff_entry_1:
														;$t16 = move 0
	mov    r11, 0
	mov    qword [rbp+(-112)], r11
														;$t17 = move 0
	mov    r11, 0
	mov    qword [rbp+(-136)], r11
														;jump %for_condition
	jmp    payoff_for_condition_2
														;%for_condition
payoff_for_condition_2:
														;$t52 = slt $t17 3
	mov    r11, qword [rbp+(-136)]
	cmp    r11, 3
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-120)], r11
														;br $t52 %for_body %for_after
	cmp    qword [rbp+(-120)], 0
	jnz    payoff_for_body_3
	jz     payoff_for_after_8
														;%for_body
payoff_for_body_3:
														;$t54 = mul $t17 8
	mov    r11, qword [rbp+(-136)]
	imul   r11, 8
	mov    qword [rbp+(-64)], r11
														;$t53 = add $p14 $t54
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-88)], r11
														;$t55 = load 8 $t53 0
	mov    r11, qword [rbp+(-88)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-96)], rax
														;$t57 = mul $t17 8
	mov    r11, qword [rbp+(-136)]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t56 = add $p15 $t57
	mov    r11, qword [rbp+(-16)]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-104)], r11
														;$t58 = load 8 $t56 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-80)], rax
														;$t59 = sne $t55 $t58
	mov    r11, qword [rbp+(-96)]
	cmp    r11, qword [rbp+(-80)]
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-144)], r11
														;br $t59 %if_true %if_false
	cmp    qword [rbp+(-144)], 0
	jnz    payoff_if_true_4
	jz     payoff_if_false_5
														;%if_true
payoff_if_true_4:
														;$t60 = move $t16
	mov    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-128)], r11
														;$t16 = add $t16 1
	mov    r11, qword [rbp+(-112)]
	add    r11, 1
	mov    qword [rbp+(-112)], r11
														;jump %if_merge
	jmp    payoff_if_merge_6
														;%if_false
payoff_if_false_5:
														;jump %if_merge
	jmp    payoff_if_merge_6
														;%if_merge
payoff_if_merge_6:
														;jump %for_loop
	jmp    payoff_for_loop_7
														;%for_loop
payoff_for_loop_7:
														;$t17 = add $t17 1
	mov    r11, qword [rbp+(-136)]
	add    r11, 1
	mov    qword [rbp+(-136)], r11
														;jump %for_condition
	jmp    payoff_for_condition_2
														;%for_after
payoff_for_after_8:
														;ret $t16
	mov    rax, qword [rbp+(-112)]
	leave
	ret
														;jump %exit
	jmp    payoff_exit_9
														;jump %exit
	jmp    payoff_exit_9
														;%exit
payoff_exit_9:
	leave
	ret


print_cond:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 240
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
print_cond_enter_0:
														;jump %entry
	jmp    print_cond_entry_1
														;%entry
print_cond_entry_1:
														;$t61 = seq $p18 $g4(n)
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rel GV_n]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-112)], r11
														;br $t61 %if_true %if_false
	cmp    qword [rbp+(-112)], 0
	jnz    print_cond_if_true_2
	jz     print_cond_if_false_10
														;%if_true
print_cond_if_true_2:
														;$t63 = call payoff $g2(cond_ass) $g1(assignment)
	mov    rdi, qword [rel GV_cond_ass]
	mov    rsi, qword [rel GV_assignment]
	call   payoff
	mov    qword [rbp+(-184)], rax
														;$t64 = call __builtin_toString $t63
	mov    rdi, qword [rbp+(-184)]
	call   __builtin_toString
	mov    qword [rbp+(-120)], rax
														;$t65 = call __builtin_string_concat $62 $t64
	mov    rdi, CONST_STRING_62
	mov    rsi, qword [rbp+(-120)]
	call   __builtin_string_concat
	mov    qword [rbp+(-80)], rax
														;$t67 = call __builtin_string_concat $t65 $66
	mov    rdi, qword [rbp+(-80)]
	mov    rsi, CONST_STRING_66
	call   __builtin_string_concat
	mov    qword [rbp+(-208)], rax
														;call myprint $t67
	mov    rdi, qword [rbp+(-208)]
	call   myprint
														;$t19 = move 0
	mov    r11, 0
	mov    qword [rbp+(-88)], r11
														;jump %for_condition
	jmp    print_cond_for_condition_3
														;%for_condition
print_cond_for_condition_3:
														;$t68 = slt $t19 $g4(n)
	mov    r11, qword [rbp+(-88)]
	cmp    r11, qword [rel GV_n]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t68 %for_body %for_after
	cmp    qword [rbp+(-72)], 0
	jnz    print_cond_for_body_4
	jz     print_cond_for_after_9
														;%for_body
print_cond_for_body_4:
														;$t69 = rem $t19 10
	mov    r11, qword [rbp+(-88)]
	mov    rax, qword [rbp+(-88)]
	cqo
	mov    r11, 10
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-128)], r11
														;$t70 = seq $t69 0
	mov    r11, qword [rbp+(-128)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-96)], r11
														;br $t70 %if_true %if_false
	cmp    qword [rbp+(-96)], 0
	jnz    print_cond_if_true_5
	jz     print_cond_if_false_6
														;%if_true
print_cond_if_true_5:
														;$t71 = add $t19 1
	mov    r11, qword [rbp+(-88)]
	add    r11, 1
	mov    qword [rbp+(-232)], r11
														;$t73 = mul $t71 8
	mov    r11, qword [rbp+(-232)]
	imul   r11, 8
	mov    qword [rbp+(-104)], r11
														;$t72 = add $g2(cond_ass) $t73
	mov    r11, qword [rel GV_cond_ass]
	add    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-192)], r11
														;$t74 = load 8 $t72 0
	mov    r11, qword [rbp+(-192)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-136)], rax
														;$t75 = call __builtin_toString $t74
	mov    rdi, qword [rbp+(-136)]
	call   __builtin_toString
	mov    qword [rbp+(-64)], rax
														;call myprint $t75
	mov    rdi, qword [rbp+(-64)]
	call   myprint
														;jump %if_merge
	jmp    print_cond_if_merge_7
														;%if_false
print_cond_if_false_6:
														;jump %if_merge
	jmp    print_cond_if_merge_7
														;%if_merge
print_cond_if_merge_7:
														;jump %for_loop
	jmp    print_cond_for_loop_8
														;%for_loop
print_cond_for_loop_8:
														;$t19 = add $t19 1
	mov    r11, qword [rbp+(-88)]
	add    r11, 1
	mov    qword [rbp+(-88)], r11
														;jump %for_condition
	jmp    print_cond_for_condition_3
														;%for_after
print_cond_for_after_9:
														;jump %exit
	jmp    print_cond_exit_12
														;jump %if_merge
	jmp    print_cond_if_merge_11
														;%if_false
print_cond_if_false_10:
														;jump %if_merge
	jmp    print_cond_if_merge_11
														;%if_merge
print_cond_if_merge_11:
														;$t76 = add $p18 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-224)], r11
														;$t78 = mul $t76 8
	mov    r11, qword [rbp+(-224)]
	imul   r11, 8
	mov    qword [rbp+(-176)], r11
														;$t77 = add $g2(cond_ass) $t78
	mov    r11, qword [rel GV_cond_ass]
	add    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-152)], r11
														;store 8 $t77 1 0
	mov    r11, qword [rbp+(-152)]
	add    r11, 0
	mov    rax, 1
	mov    qword [r11], rax
														;$t79 = add $p18 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-216)], r11
														;call print_cond $t79
	mov    rdi, qword [rbp+(-216)]
	call   print_cond
														;$t80 = add $p18 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-168)], r11
														;$t82 = mul $t80 8
	mov    r11, qword [rbp+(-168)]
	imul   r11, 8
	mov    qword [rbp+(-144)], r11
														;$t81 = add $g2(cond_ass) $t82
	mov    r11, qword [rel GV_cond_ass]
	add    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-160)], r11
														;store 8 $t81 0 0
	mov    r11, qword [rbp+(-160)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t83 = add $p18 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-200)], r11
														;call print_cond $t83
	mov    rdi, qword [rbp+(-200)]
	call   print_cond
														;jump %exit
	jmp    print_cond_exit_12
														;%exit
print_cond_exit_12:
	leave
	ret


dfs:
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
dfs_enter_0:
														;jump %entry
	jmp    dfs_entry_1
														;%entry
dfs_entry_1:
														;$t84 = seq $p20 $g4(n)
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rel GV_n]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t84 %if_true %if_false
	cmp    qword [rbp+(-136)], 0
	jnz    dfs_if_true_2
	jz     dfs_if_false_6
														;%if_true
dfs_if_true_2:
														;$t85 = call check
	call   check
	mov    qword [rbp+(-96)], rax
														;$t86 = seq $t85 1
	mov    r11, qword [rbp+(-96)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t86 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    dfs_if_true_3
	jz     dfs_if_false_4
														;%if_true
dfs_if_true_3:
														;call myprint $87
	mov    rdi, CONST_STRING_87
	call   myprint
														;call print_cond 0
	mov    rdi, 0
	call   print_cond
														;call myprint $88
	mov    rdi, CONST_STRING_88
	call   myprint
														;jump %if_merge
	jmp    dfs_if_merge_5
														;%if_false
dfs_if_false_4:
														;jump %if_merge
	jmp    dfs_if_merge_5
														;%if_merge
dfs_if_merge_5:
														;jump %exit
	jmp    dfs_exit_8
														;jump %if_merge
	jmp    dfs_if_merge_7
														;%if_false
dfs_if_false_6:
														;jump %if_merge
	jmp    dfs_if_merge_7
														;%if_merge
dfs_if_merge_7:
														;$t89 = add $p20 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-80)], r11
														;$t91 = mul $t89 8
	mov    r11, qword [rbp+(-80)]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t90 = add $g1(assignment) $t91
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-128)], r11
														;store 8 $t90 1 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    rax, 1
	mov    qword [r11], rax
														;$t92 = add $p20 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-104)], r11
														;call dfs $t92
	mov    rdi, qword [rbp+(-104)]
	call   dfs
														;$t93 = add $p20 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-144)], r11
														;$t95 = mul $t93 8
	mov    r11, qword [rbp+(-144)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t94 = add $g1(assignment) $t95
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-120)], r11
														;store 8 $t94 0 0
	mov    r11, qword [rbp+(-120)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t96 = add $p20 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-112)], r11
														;call dfs $t96
	mov    rdi, qword [rbp+(-112)]
	call   dfs
														;jump %exit
	jmp    dfs_exit_8
														;%exit
dfs_exit_8:
	leave
	ret


print_last_cond:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 248
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
print_last_cond_enter_0:
														;jump %entry
	jmp    print_last_cond_entry_1
														;%entry
print_last_cond_entry_1:
														;$t97 = seq $p21 $g4(n)
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rel GV_n]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t97 %if_true %if_false
	cmp    qword [rbp+(-136)], 0
	jnz    print_last_cond_if_true_2
	jz     print_last_cond_if_false_13
														;%if_true
print_last_cond_if_true_2:
														;call myprint $98
	mov    rdi, CONST_STRING_98
	call   myprint
														;$t22 = move 1
	mov    r11, 1
	mov    qword [rbp+(-184)], r11
														;$t23 = move 0
	mov    r11, 0
	mov    qword [rbp+(-192)], r11
														;jump %for_condition
	jmp    print_last_cond_for_condition_3
														;%for_condition
print_last_cond_for_condition_3:
														;$t99 = slt $t23 $g4(n)
	mov    r11, qword [rbp+(-192)]
	cmp    r11, qword [rel GV_n]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-240)], r11
														;br $t99 %for_body %for_after
	cmp    qword [rbp+(-240)], 0
	jnz    print_last_cond_for_body_4
	jz     print_last_cond_for_after_9
														;%for_body
print_last_cond_for_body_4:
														;$t100 = add $t23 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-216)], r11
														;$t102 = mul $t100 8
	mov    r11, qword [rbp+(-216)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t101 = add $g1(assignment) $t102
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-232)], r11
														;$t103 = load 8 $t101 0
	mov    r11, qword [rbp+(-232)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-208)], rax
														;$t104 = seq $t103 1
	mov    r11, qword [rbp+(-208)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-168)], r11
														;br $t104 %if_true %if_false
	cmp    qword [rbp+(-168)], 0
	jnz    print_last_cond_if_true_5
	jz     print_last_cond_if_false_6
														;%if_true
print_last_cond_if_true_5:
														;$t22 = move 0
	mov    r11, 0
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    print_last_cond_if_merge_7
														;%if_false
print_last_cond_if_false_6:
														;jump %if_merge
	jmp    print_last_cond_if_merge_7
														;%if_merge
print_last_cond_if_merge_7:
														;$t105 = add $t23 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-88)], r11
														;$t107 = mul $t105 8
	mov    r11, qword [rbp+(-88)]
	imul   r11, 8
	mov    qword [rbp+(-176)], r11
														;$t106 = add $g1(assignment) $t107
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-144)], r11
														;$t108 = load 8 $t106 0
	mov    r11, qword [rbp+(-144)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-96)], rax
														;$t109 = call __builtin_toString $t108
	mov    rdi, qword [rbp+(-96)]
	call   __builtin_toString
	mov    qword [rbp+(-64)], rax
														;call myprint $t109
	mov    rdi, qword [rbp+(-64)]
	call   myprint
														;jump %for_loop
	jmp    print_last_cond_for_loop_8
														;%for_loop
print_last_cond_for_loop_8:
														;$t23 = add $t23 1
	mov    r11, qword [rbp+(-192)]
	add    r11, 1
	mov    qword [rbp+(-192)], r11
														;jump %for_condition
	jmp    print_last_cond_for_condition_3
														;%for_after
print_last_cond_for_after_9:
														;$t110 = seq $t22 0
	mov    r11, qword [rbp+(-184)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-120)], r11
														;br $t110 %if_true %if_false
	cmp    qword [rbp+(-120)], 0
	jnz    print_last_cond_if_true_10
	jz     print_last_cond_if_false_11
														;%if_true
print_last_cond_if_true_10:
														;call myprint $111
	mov    rdi, CONST_STRING_111
	call   myprint
														;jump %if_merge
	jmp    print_last_cond_if_merge_12
														;%if_false
print_last_cond_if_false_11:
														;call myprint $112
	mov    rdi, CONST_STRING_112
	call   myprint
														;jump %if_merge
	jmp    print_last_cond_if_merge_12
														;%if_merge
print_last_cond_if_merge_12:
														;jump %exit
	jmp    print_last_cond_exit_15
														;jump %if_merge
	jmp    print_last_cond_if_merge_14
														;%if_false
print_last_cond_if_false_13:
														;jump %if_merge
	jmp    print_last_cond_if_merge_14
														;%if_merge
print_last_cond_if_merge_14:
														;$t113 = add $p21 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-152)], r11
														;$t115 = mul $t113 8
	mov    r11, qword [rbp+(-152)]
	imul   r11, 8
	mov    qword [rbp+(-128)], r11
														;$t114 = add $g1(assignment) $t115
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-104)], r11
														;store 8 $t114 1 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, 1
	mov    qword [r11], rax
														;$t116 = add $p21 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-112)], r11
														;call print_last_cond $t116
	mov    rdi, qword [rbp+(-112)]
	call   print_last_cond
														;$t117 = add $p21 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-72)], r11
														;$t119 = mul $t117 8
	mov    r11, qword [rbp+(-72)]
	imul   r11, 8
	mov    qword [rbp+(-224)], r11
														;$t118 = add $g1(assignment) $t119
	mov    r11, qword [rel GV_assignment]
	add    r11, qword [rbp+(-224)]
	mov    qword [rbp+(-160)], r11
														;store 8 $t118 0 0
	mov    r11, qword [rbp+(-160)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t120 = add $p21 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-200)], r11
														;call print_last_cond $t120
	mov    rdi, qword [rbp+(-200)]
	call   print_last_cond
														;jump %exit
	jmp    print_last_cond_exit_15
														;%exit
print_last_cond_exit_15:
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
														;%enter
main_enter_0:
														;$g7(myHash) = move 0
	mov    r11, 0
	mov    qword [rel GV_myHash], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$g3(k) = move 5
	mov    r11, 5
	mov    qword [rel GV_k], r11
														;$t121 = mul $g3(k) 2
	mov    r11, qword [rel GV_k]
	imul   r11, 2
	mov    qword [rbp+(-64)], r11
														;$t122 = add $t121 1
	mov    r11, qword [rbp+(-64)]
	add    r11, 1
	mov    qword [rbp+(-224)], r11
														;$g4(n) = move $t122
	mov    r11, qword [rbp+(-224)]
	mov    qword [rel GV_n], r11
														;$g5(m) = move $g3(k)
	mov    r11, qword [rel GV_k]
	mov    qword [rel GV_m], r11
														;$g6(last) = move 1
	mov    r11, 1
	mov    qword [rel GV_last], r11
														;call myprint $123
	mov    rdi, CONST_STRING_123
	call   myprint
														;$t125 = mul $g5(m) 8
	mov    r11, qword [rel GV_m]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t125 = add $t125 8
	mov    r11, qword [rbp+(-80)]
	add    r11, 8
	mov    qword [rbp+(-80)], r11
														;$t124 = alloc $t125
	mov    rdi, qword [rbp+(-80)]
	call   malloc
	mov    qword [rbp+(-184)], rax
														;store 8 $t124 $g5(m) 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, qword [rel GV_m]
	mov    qword [r11], rax
														;$t124 = add $t124 8
	mov    r11, qword [rbp+(-184)]
	add    r11, 8
	mov    qword [rbp+(-184)], r11
														;$g0(sat) = move $t124
	mov    r11, qword [rbp+(-184)]
	mov    qword [rel GV_sat], r11
														;$t24 = move 0
	mov    r11, 0
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_condition
main_for_condition_2:
														;$t126 = slt $t24 $g5(m)
	mov    r11, qword [rbp+(-128)]
	cmp    r11, qword [rel GV_m]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-144)], r11
														;br $t126 %for_body %for_after
	cmp    qword [rbp+(-144)], 0
	jnz    main_for_body_3
	jz     main_for_after_5
														;%for_body
main_for_body_3:
														;$t128 = move 24
	mov    r11, 24
	mov    qword [rbp+(-248)], r11
														;$t128 = add $t128 8
	mov    r11, qword [rbp+(-248)]
	add    r11, 8
	mov    qword [rbp+(-248)], r11
														;$t127 = alloc $t128
	mov    rdi, qword [rbp+(-248)]
	call   malloc
	mov    qword [rbp+(-88)], rax
														;store 8 $t127 3 0
	mov    r11, qword [rbp+(-88)]
	add    r11, 0
	mov    rax, 3
	mov    qword [r11], rax
														;$t127 = add $t127 8
	mov    r11, qword [rbp+(-88)]
	add    r11, 8
	mov    qword [rbp+(-88)], r11
														;$t25 = move $t127
	mov    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-96)], r11
														;$t130 = move 0
	mov    r11, 0
	mov    qword [rbp+(-256)], r11
														;$t129 = add $t25 $t130
	mov    r11, qword [rbp+(-96)]
	add    r11, qword [rbp+(-256)]
	mov    qword [rbp+(-232)], r11
														;store 8 $t129 $g6(last) 0
	mov    r11, qword [rbp+(-232)]
	add    r11, 0
	mov    rax, qword [rel GV_last]
	mov    qword [r11], rax
														;$t132 = move 8
	mov    r11, 8
	mov    qword [rbp+(-264)], r11
														;$t131 = add $t25 $t132
	mov    r11, qword [rbp+(-96)]
	add    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-168)], r11
														;$t133 = add $g6(last) 1
	mov    r11, qword [rel GV_last]
	add    r11, 1
	mov    qword [rbp+(-280)], r11
														;store 8 $t131 $t133 0
	mov    r11, qword [rbp+(-168)]
	add    r11, 0
	mov    rax, qword [rbp+(-280)]
	mov    qword [r11], rax
														;$t135 = move 16
	mov    r11, 16
	mov    qword [rbp+(-152)], r11
														;$t134 = add $t25 $t135
	mov    r11, qword [rbp+(-96)]
	add    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-272)], r11
														;$t136 = add $g6(last) 2
	mov    r11, qword [rel GV_last]
	add    r11, 2
	mov    qword [rbp+(-176)], r11
														;$t137 = neg $t136
	mov    r11, qword [rbp+(-176)]
	neg    r11
	mov    qword [rbp+(-112)], r11
														;store 8 $t134 $t137 0
	mov    r11, qword [rbp+(-272)]
	add    r11, 0
	mov    rax, qword [rbp+(-112)]
	mov    qword [r11], rax
														;$t138 = add $g6(last) 2
	mov    r11, qword [rel GV_last]
	add    r11, 2
	mov    qword [rbp+(-136)], r11
														;$g6(last) = move $t138
	mov    r11, qword [rbp+(-136)]
	mov    qword [rel GV_last], r11
														;$t140 = mul $t24 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t139 = add $g0(sat) $t140
	mov    r11, qword [rel GV_sat]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-216)], r11
														;store 8 $t139 $t25 0
	mov    r11, qword [rbp+(-216)]
	add    r11, 0
	mov    rax, qword [rbp+(-96)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    main_for_loop_4
														;%for_loop
main_for_loop_4:
														;$t24 = add $t24 1
	mov    r11, qword [rbp+(-128)]
	add    r11, 1
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_after
main_for_after_5:
														;$t141 = add $g4(n) 1
	mov    r11, qword [rel GV_n]
	add    r11, 1
	mov    qword [rbp+(-200)], r11
														;$t143 = mul $t141 8
	mov    r11, qword [rbp+(-200)]
	imul   r11, 8
	mov    qword [rbp+(-240)], r11
														;$t143 = add $t143 8
	mov    r11, qword [rbp+(-240)]
	add    r11, 8
	mov    qword [rbp+(-240)], r11
														;$t142 = alloc $t143
	mov    rdi, qword [rbp+(-240)]
	call   malloc
	mov    qword [rbp+(-120)], rax
														;store 8 $t142 $t141 0
	mov    r11, qword [rbp+(-120)]
	add    r11, 0
	mov    rax, qword [rbp+(-200)]
	mov    qword [r11], rax
														;$t142 = add $t142 8
	mov    r11, qword [rbp+(-120)]
	add    r11, 8
	mov    qword [rbp+(-120)], r11
														;$g1(assignment) = move $t142
	mov    r11, qword [rbp+(-120)]
	mov    qword [rel GV_assignment], r11
														;$t144 = add $g4(n) 1
	mov    r11, qword [rel GV_n]
	add    r11, 1
	mov    qword [rbp+(-104)], r11
														;$t146 = mul $t144 8
	mov    r11, qword [rbp+(-104)]
	imul   r11, 8
	mov    qword [rbp+(-160)], r11
														;$t146 = add $t146 8
	mov    r11, qword [rbp+(-160)]
	add    r11, 8
	mov    qword [rbp+(-160)], r11
														;$t145 = alloc $t146
	mov    rdi, qword [rbp+(-160)]
	call   malloc
	mov    qword [rbp+(-192)], rax
														;store 8 $t145 $t144 0
	mov    r11, qword [rbp+(-192)]
	add    r11, 0
	mov    rax, qword [rbp+(-104)]
	mov    qword [r11], rax
														;$t145 = add $t145 8
	mov    r11, qword [rbp+(-192)]
	add    r11, 8
	mov    qword [rbp+(-192)], r11
														;$g2(cond_ass) = move $t145
	mov    r11, qword [rbp+(-192)]
	mov    qword [rel GV_cond_ass], r11
														;call dfs 0
	mov    rdi, 0
	call   dfs
														;call print_last_cond 0
	mov    rdi, 0
	call   print_last_cond
														;$t147 = call __builtin_toString $g7(myHash)
	mov    rdi, qword [rel GV_myHash]
	call   __builtin_toString
	mov    qword [rbp+(-208)], rax
														;call __builtin_println $t147
	mov    rdi, qword [rbp+(-208)]
	call   __builtin_println
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_6
														;jump %exit
	jmp    main_exit_6
														;%exit
main_exit_6:
	leave
	ret


SECTION .data
CONST_STRING_62:
	db 45, 32, 0
GV_cond_ass:
	dq 0
CONST_STRING_88:
	db 32, 60, 61, 32, 48, 59, 10, 0
GV_myHash:
	dq 0
CONST_STRING_87:
	db 120, 122, 32, 0
CONST_STRING_98:
	db 120, 0
GV_last:
	dq 0
CONST_STRING_111:
	db 32, 43, 32, 0
CONST_STRING_123:
	db 109, 97, 120, 58, 32, 120, 122, 59, 10, 0
GV_k:
	dq 0
GV_sat:
	dq 0
CONST_STRING_112:
	db 32, 61, 32, 49, 59, 10, 0
CONST_STRING_66:
	db 42, 120, 0
GV_m:
	dq 0
GV_n:
	dq 0
GV_assignment:
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

