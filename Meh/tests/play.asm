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

merge:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 280
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
														;$t13 = seq 0 $p6
	mov    r11, 0
	cmp    r11, qword [rbp+(-8)]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-256)], r11
														;br $t13 %if_true %if_false
	cmp    qword [rbp+(-256)], 0
	jnz    merge_if_true_2
	jz     merge_if_false_3
														;%if_true
merge_if_true_2:
														;ret $p7
	mov    rax, qword [rbp+(-16)]
	leave
	ret
														;jump %exit
	jmp    merge_exit_11
														;jump %if_merge
	jmp    merge_if_merge_4
														;%if_false
merge_if_false_3:
														;jump %if_merge
	jmp    merge_if_merge_4
														;%if_merge
merge_if_merge_4:
														;$t14 = seq 0 $p7
	mov    r11, 0
	cmp    r11, qword [rbp+(-16)]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t14 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    merge_if_true_5
	jz     merge_if_false_6
														;%if_true
merge_if_true_5:
														;ret $p6
	mov    rax, qword [rbp+(-8)]
	leave
	ret
														;jump %exit
	jmp    merge_exit_11
														;jump %if_merge
	jmp    merge_if_merge_7
														;%if_false
merge_if_false_6:
														;jump %if_merge
	jmp    merge_if_merge_7
														;%if_merge
merge_if_merge_7:
														;$t16 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-232)], r11
														;$t15 = add $g5(w) $t16
	mov    r11, qword [rel GV_w]
	add    r11, qword [rbp+(-232)]
	mov    qword [rbp+(-168)], r11
														;$t17 = load 8 $t15 0
	mov    r11, qword [rbp+(-168)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-128)], r12
														;$t19 = mul $p7 8
	mov    r11, qword [rbp+(-16)]
	imul   r11, 8
	mov    qword [rbp+(-264)], r11
														;$t18 = add $g5(w) $t19
	mov    r11, qword [rel GV_w]
	add    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-112)], r11
														;$t20 = load 8 $t18 0
	mov    r11, qword [rbp+(-112)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-96)], r12
														;$t21 = slt $t17 $t20
	mov    r11, qword [rbp+(-128)]
	cmp    r11, qword [rbp+(-96)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-120)], r11
														;br $t21 %if_true %if_false
	cmp    qword [rbp+(-120)], 0
	jnz    merge_if_true_8
	jz     merge_if_false_9
														;%if_true
merge_if_true_8:
														;$t8 = move $p6
	mov    r11, qword [rbp+(-8)]
	mov    qword [rbp+(-192)], r11
														;$p6 = move $p7
	mov    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-8)], r11
														;$p7 = move $t8
	mov    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-16)], r11
														;jump %if_merge
	jmp    merge_if_merge_10
														;%if_false
merge_if_false_9:
														;jump %if_merge
	jmp    merge_if_merge_10
														;%if_merge
merge_if_merge_10:
														;$t23 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-104)], r11
														;$t22 = add $g4(r) $t23
	mov    r11, qword [rel GV_r]
	add    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-152)], r11
														;$t25 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-176)], r11
														;$t24 = add $g4(r) $t25
	mov    r11, qword [rel GV_r]
	add    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-88)], r11
														;$t26 = load 8 $t24 0
	mov    r11, qword [rbp+(-88)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-136)], r12
														;$t27 = call merge $t26 $p7
	mov    rdi, qword [rbp+(-136)]
	mov    rsi, qword [rbp+(-16)]
	call   merge
	mov    qword [rbp+(-160)], rax
														;store 8 $t22 $t27 0
	mov    r11, qword [rbp+(-152)]
	add    r11, 0
	mov    rax, qword [rbp+(-160)]
	mov    qword [r11], rax
														;$t29 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t28 = add $g3(l) $t29
	mov    r11, qword [rel GV_l]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-248)], r11
														;$t30 = load 8 $t28 0
	mov    r11, qword [rbp+(-248)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-208)], r12
														;$t9 = move $t30
	mov    r11, qword [rbp+(-208)]
	mov    qword [rbp+(-216)], r11
														;$t32 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-240)], r11
														;$t31 = add $g3(l) $t32
	mov    r11, qword [rel GV_l]
	add    r11, qword [rbp+(-240)]
	mov    qword [rbp+(-184)], r11
														;$t34 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-224)], r11
														;$t33 = add $g4(r) $t34
	mov    r11, qword [rel GV_r]
	add    r11, qword [rbp+(-224)]
	mov    qword [rbp+(-200)], r11
														;$t35 = load 8 $t33 0
	mov    r11, qword [rbp+(-200)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-144)], r12
														;store 8 $t31 $t35 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, qword [rbp+(-144)]
	mov    qword [r11], rax
														;$t37 = mul $p6 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t36 = add $g4(r) $t37
	mov    r11, qword [rel GV_r]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-272)], r11
														;store 8 $t36 $t9 0
	mov    r11, qword [rbp+(-272)]
	add    r11, 0
	mov    rax, qword [rbp+(-216)]
	mov    qword [r11], rax
														;ret $p6
	mov    rax, qword [rbp+(-8)]
	leave
	ret
														;jump %exit
	jmp    merge_exit_11
														;jump %exit
	jmp    merge_exit_11
														;%exit
merge_exit_11:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 544
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
main_enter_0:
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t38 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-424)], rax
														;$g0(N) = move $t38
	mov    r11, qword [rbp+(-424)]
	mov    qword [rel GV_N], r11
														;$t39 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-376)], rax
														;$g1(M) = move $t39
	mov    r11, qword [rbp+(-376)]
	mov    qword [rel GV_M], r11
														;$t40 = call __builtin_getString
	call   __builtin_getString
	mov    qword [rbp+(-360)], rax
														;$g2(ch) = move $t40
	mov    r11, qword [rbp+(-360)]
	mov    qword [rel GV_ch], r11
														;$t41 = add $g0(N) $g1(M)
	mov    r11, qword [rel GV_N]
	add    r11, qword [rel GV_M]
	mov    qword [rbp+(-232)], r11
														;$t42 = add $t41 5
	mov    r11, qword [rbp+(-232)]
	add    r11, 5
	mov    qword [rbp+(-288)], r11
														;$t44 = mul $t42 8
	mov    r11, qword [rbp+(-288)]
	imul   r11, 8
	mov    qword [rbp+(-224)], r11
														;$t44 = add $t44 8
	mov    r11, qword [rbp+(-224)]
	add    r11, 8
	mov    qword [rbp+(-224)], r11
														;$t43 = alloc $t44
	mov    rdi, qword [rbp+(-224)]
	call   malloc
	mov    qword [rbp+(-200)], rax
														;store 8 $t43 $t42 0
	mov    r11, qword [rbp+(-200)]
	add    r11, 0
	mov    rax, qword [rbp+(-288)]
	mov    qword [r11], rax
														;$t43 = add $t43 8
	mov    r11, qword [rbp+(-200)]
	add    r11, 8
	mov    qword [rbp+(-200)], r11
														;$g3(l) = move $t43
	mov    r11, qword [rbp+(-200)]
	mov    qword [rel GV_l], r11
														;$t45 = add $g0(N) $g1(M)
	mov    r11, qword [rel GV_N]
	add    r11, qword [rel GV_M]
	mov    qword [rbp+(-416)], r11
														;$t46 = add $t45 5
	mov    r11, qword [rbp+(-416)]
	add    r11, 5
	mov    qword [rbp+(-128)], r11
														;$t48 = mul $t46 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-184)], r11
														;$t48 = add $t48 8
	mov    r11, qword [rbp+(-184)]
	add    r11, 8
	mov    qword [rbp+(-184)], r11
														;$t47 = alloc $t48
	mov    rdi, qword [rbp+(-184)]
	call   malloc
	mov    qword [rbp+(-512)], rax
														;store 8 $t47 $t46 0
	mov    r11, qword [rbp+(-512)]
	add    r11, 0
	mov    rax, qword [rbp+(-128)]
	mov    qword [r11], rax
														;$t47 = add $t47 8
	mov    r11, qword [rbp+(-512)]
	add    r11, 8
	mov    qword [rbp+(-512)], r11
														;$g4(r) = move $t47
	mov    r11, qword [rbp+(-512)]
	mov    qword [rel GV_r], r11
														;$t49 = add $g0(N) $g1(M)
	mov    r11, qword [rel GV_N]
	add    r11, qword [rel GV_M]
	mov    qword [rbp+(-352)], r11
														;$t50 = add $t49 5
	mov    r11, qword [rbp+(-352)]
	add    r11, 5
	mov    qword [rbp+(-344)], r11
														;$t52 = mul $t50 8
	mov    r11, qword [rbp+(-344)]
	imul   r11, 8
	mov    qword [rbp+(-528)], r11
														;$t52 = add $t52 8
	mov    r11, qword [rbp+(-528)]
	add    r11, 8
	mov    qword [rbp+(-528)], r11
														;$t51 = alloc $t52
	mov    rdi, qword [rbp+(-528)]
	call   malloc
	mov    qword [rbp+(-120)], rax
														;store 8 $t51 $t50 0
	mov    r11, qword [rbp+(-120)]
	add    r11, 0
	mov    rax, qword [rbp+(-344)]
	mov    qword [r11], rax
														;$t51 = add $t51 8
	mov    r11, qword [rbp+(-120)]
	add    r11, 8
	mov    qword [rbp+(-120)], r11
														;$g5(w) = move $t51
	mov    r11, qword [rbp+(-120)]
	mov    qword [rel GV_w], r11
														;$t10 = move 1
	mov    r11, 1
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_condition
main_for_condition_2:
														;$t53 = sle $t10 $g0(N)
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rel GV_N]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t53 %for_body %for_after
	cmp    qword [rbp+(-64)], 0
	jnz    main_for_body_3
	jz     main_for_after_5
														;%for_body
main_for_body_3:
														;$t55 = mul $t10 8
	mov    r11, qword [rbp+(-304)]
	imul   r11, 8
	mov    qword [rbp+(-320)], r11
														;$t54 = add $g5(w) $t55
	mov    r11, qword [rel GV_w]
	add    r11, qword [rbp+(-320)]
	mov    qword [rbp+(-152)], r11
														;$t56 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-328)], rax
														;store 8 $t54 $t56 0
	mov    r11, qword [rbp+(-152)]
	add    r11, 0
	mov    rax, qword [rbp+(-328)]
	mov    qword [r11], rax
														;$t58 = mul $t10 8
	mov    r11, qword [rbp+(-304)]
	imul   r11, 8
	mov    qword [rbp+(-384)], r11
														;$t57 = add $g3(l) $t58
	mov    r11, qword [rel GV_l]
	add    r11, qword [rbp+(-384)]
	mov    qword [rbp+(-168)], r11
														;store 8 $t57 0 0
	mov    r11, qword [rbp+(-168)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t60 = mul $t10 8
	mov    r11, qword [rbp+(-304)]
	imul   r11, 8
	mov    qword [rbp+(-176)], r11
														;$t59 = add $g4(r) $t60
	mov    r11, qword [rel GV_r]
	add    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-80)], r11
														;store 8 $t59 0 0
	mov    r11, qword [rbp+(-80)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;jump %for_loop
	jmp    main_for_loop_4
														;%for_loop
main_for_loop_4:
														;$t61 = move $t10
	mov    r11, qword [rbp+(-304)]
	mov    qword [rbp+(-520)], r11
														;$t10 = add $t10 1
	mov    r11, qword [rbp+(-304)]
	add    r11, 1
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_after
main_for_after_5:
														;$t10 = move 1
	mov    r11, 1
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_condition
main_for_condition_6:
														;$t62 = sle $t10 $g1(M)
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rel GV_M]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-368)], r11
														;br $t62 %for_body %for_after
	cmp    qword [rbp+(-368)], 0
	jnz    main_for_body_7
	jz     main_for_after_9
														;%for_body
main_for_body_7:
														;$t63 = add $t10 $g0(N)
	mov    r11, qword [rbp+(-304)]
	add    r11, qword [rel GV_N]
	mov    qword [rbp+(-312)], r11
														;$t65 = mul $t63 8
	mov    r11, qword [rbp+(-312)]
	imul   r11, 8
	mov    qword [rbp+(-136)], r11
														;$t64 = add $g5(w) $t65
	mov    r11, qword [rel GV_w]
	add    r11, qword [rbp+(-136)]
	mov    qword [rbp+(-248)], r11
														;$t66 = sub $t10 1
	mov    r11, qword [rbp+(-304)]
	sub    r11, 1
	mov    qword [rbp+(-440)], r11
														;$t67 = call __builtin_ord $g2(ch) $t66
	mov    rdi, qword [rel GV_ch]
	mov    rsi, qword [rbp+(-440)]
	call   __builtin_ord
	mov    qword [rbp+(-144)], rax
														;store 8 $t64 $t67 0
	mov    r11, qword [rbp+(-248)]
	add    r11, 0
	mov    rax, qword [rbp+(-144)]
	mov    qword [r11], rax
														;$t68 = add $t10 $g0(N)
	mov    r11, qword [rbp+(-304)]
	add    r11, qword [rel GV_N]
	mov    qword [rbp+(-408)], r11
														;$t70 = mul $t68 8
	mov    r11, qword [rbp+(-408)]
	imul   r11, 8
	mov    qword [rbp+(-160)], r11
														;$t69 = add $g3(l) $t70
	mov    r11, qword [rel GV_l]
	add    r11, qword [rbp+(-160)]
	mov    qword [rbp+(-216)], r11
														;store 8 $t69 0 0
	mov    r11, qword [rbp+(-216)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t71 = add $t10 $g0(N)
	mov    r11, qword [rbp+(-304)]
	add    r11, qword [rel GV_N]
	mov    qword [rbp+(-400)], r11
														;$t73 = mul $t71 8
	mov    r11, qword [rbp+(-400)]
	imul   r11, 8
	mov    qword [rbp+(-192)], r11
														;$t72 = add $g4(r) $t73
	mov    r11, qword [rel GV_r]
	add    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-504)], r11
														;store 8 $t72 0 0
	mov    r11, qword [rbp+(-504)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;jump %for_loop
	jmp    main_for_loop_8
														;%for_loop
main_for_loop_8:
														;$t74 = move $t10
	mov    r11, qword [rbp+(-304)]
	mov    qword [rbp+(-336)], r11
														;$t10 = add $t10 1
	mov    r11, qword [rbp+(-304)]
	add    r11, 1
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_after
main_for_after_9:
														;$t11 = move 1
	mov    r11, 1
	mov    qword [rbp+(-496)], r11
														;$t75 = add $g0(N) 1
	mov    r11, qword [rel GV_N]
	add    r11, 1
	mov    qword [rbp+(-448)], r11
														;$t12 = move $t75
	mov    r11, qword [rbp+(-448)]
	mov    qword [rbp+(-264)], r11
														;$t10 = move 2
	mov    r11, 2
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_10
														;%for_condition
main_for_condition_10:
														;$t76 = sle $t10 $g0(N)
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rel GV_N]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-272)], r11
														;br $t76 %for_body %for_after
	cmp    qword [rbp+(-272)], 0
	jnz    main_for_body_11
	jz     main_for_after_13
														;%for_body
main_for_body_11:
														;$t77 = call merge $t11 $t10
	mov    rdi, qword [rbp+(-496)]
	mov    rsi, qword [rbp+(-304)]
	call   merge
	mov    qword [rbp+(-456)], rax
														;$t11 = move $t77
	mov    r11, qword [rbp+(-456)]
	mov    qword [rbp+(-496)], r11
														;jump %for_loop
	jmp    main_for_loop_12
														;%for_loop
main_for_loop_12:
														;$t78 = move $t10
	mov    r11, qword [rbp+(-304)]
	mov    qword [rbp+(-72)], r11
														;$t10 = add $t10 1
	mov    r11, qword [rbp+(-304)]
	add    r11, 1
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_10
														;%for_after
main_for_after_13:
														;$t79 = add $g0(N) 2
	mov    r11, qword [rel GV_N]
	add    r11, 2
	mov    qword [rbp+(-536)], r11
														;$t10 = move $t79
	mov    r11, qword [rbp+(-536)]
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_14
														;%for_condition
main_for_condition_14:
														;$t80 = add $g0(N) $g1(M)
	mov    r11, qword [rel GV_N]
	add    r11, qword [rel GV_M]
	mov    qword [rbp+(-88)], r11
														;$t81 = sle $t10 $t80
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rbp+(-88)]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-480)], r11
														;br $t81 %for_body %for_after
	cmp    qword [rbp+(-480)], 0
	jnz    main_for_body_15
	jz     main_for_after_17
														;%for_body
main_for_body_15:
														;$t82 = call merge $t12 $t10
	mov    rdi, qword [rbp+(-264)]
	mov    rsi, qword [rbp+(-304)]
	call   merge
	mov    qword [rbp+(-432)], rax
														;$t12 = move $t82
	mov    r11, qword [rbp+(-432)]
	mov    qword [rbp+(-264)], r11
														;jump %for_loop
	jmp    main_for_loop_16
														;%for_loop
main_for_loop_16:
														;$t83 = move $t10
	mov    r11, qword [rbp+(-304)]
	mov    qword [rbp+(-488)], r11
														;$t10 = add $t10 1
	mov    r11, qword [rbp+(-304)]
	add    r11, 1
	mov    qword [rbp+(-304)], r11
														;jump %for_condition
	jmp    main_for_condition_14
														;%for_after
main_for_after_17:
														;$t85 = mul $t11 8
	mov    r11, qword [rbp+(-496)]
	imul   r11, 8
	mov    qword [rbp+(-256)], r11
														;$t84 = add $g5(w) $t85
	mov    r11, qword [rel GV_w]
	add    r11, qword [rbp+(-256)]
	mov    qword [rbp+(-280)], r11
														;$t86 = load 8 $t84 0
	mov    r11, qword [rbp+(-280)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-112)], r12
														;$t87 = call __builtin_toString $t86
	mov    rdi, qword [rbp+(-112)]
	call   __builtin_toString
	mov    qword [rbp+(-392)], rax
														;call __builtin_print $t87
	mov    rdi, qword [rbp+(-392)]
	call   __builtin_print
														;call __builtin_print $88
	mov    rdi, CONST_STRING_88
	call   __builtin_print
														;$t89 = sub $t12 $g0(N)
	mov    r11, qword [rbp+(-264)]
	sub    r11, qword [rel GV_N]
	mov    qword [rbp+(-296)], r11
														;$t90 = sub $t89 1
	mov    r11, qword [rbp+(-296)]
	sub    r11, 1
	mov    qword [rbp+(-464)], r11
														;$t91 = sub $t12 $g0(N)
	mov    r11, qword [rbp+(-264)]
	sub    r11, qword [rel GV_N]
	mov    qword [rbp+(-472)], r11
														;$t92 = sub $t91 1
	mov    r11, qword [rbp+(-472)]
	sub    r11, 1
	mov    qword [rbp+(-208)], r11
														;$t93 = call __builtin_getSubstring $g2(ch) $t90 $t92
	mov    rdi, qword [rel GV_ch]
	mov    rsi, qword [rbp+(-464)]
	mov    rdx, qword [rbp+(-208)]
	call   __builtin_getSubstring
	mov    qword [rbp+(-96)], rax
														;call __builtin_print $t93
	mov    rdi, qword [rbp+(-96)]
	call   __builtin_print
														;call __builtin_print $94
	mov    rdi, CONST_STRING_94
	call   __builtin_print
														;$t95 = call merge $t11 $t12
	mov    rdi, qword [rbp+(-496)]
	mov    rsi, qword [rbp+(-264)]
	call   merge
	mov    qword [rbp+(-240)], rax
														;$t96 = call __builtin_toString $t95
	mov    rdi, qword [rbp+(-240)]
	call   __builtin_toString
	mov    qword [rbp+(-104)], rax
														;call __builtin_println $t96
	mov    rdi, qword [rbp+(-104)]
	call   __builtin_println
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_18
														;jump %exit
	jmp    main_exit_18
														;%exit
main_exit_18:
	leave
	ret


SECTION .data
GV_r:
	dq 0
GV_N:
	dq 0
GV_l:
	dq 0
GV_w:
	dq 0
CONST_STRING_88:
	db 32, 0
GV_ch:
	dq 0
GV_M:
	dq 0
CONST_STRING_94:
	db 10, 0
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