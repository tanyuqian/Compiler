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
														;%enter
main_enter_0:
														;$t6 = move 240
	mov    r11, 240
	mov    qword [rbp+(-80)], r11
														;$t6 = add $t6 8
	mov    r11, qword [rbp+(-80)]
	add    r11, 8
	mov    qword [rbp+(-80)], r11
														;$t5 = alloc $t6
	mov    rdi, qword [rbp+(-80)]
	call   malloc
	mov    qword [rbp+(-144)], rax
														;store 8 $t5 30 0
	mov    r11, qword [rbp+(-144)]
	add    r11, 0
	mov    rax, 30
	mov    qword [r11], rax
														;$t5 = add $t5 8
	mov    r11, qword [rbp+(-144)]
	add    r11, 8
	mov    qword [rbp+(-144)], r11
														;$t7 = move 0
	mov    r11, 0
	mov    qword [rbp+(-248)], r11
														;jump %new_condition
	jmp    main_new_condition_1
														;%new_condition
main_new_condition_1:
														;$t8 = slt $t7 30
	mov    r11, qword [rbp+(-248)]
	cmp    r11, 30
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-256)], r11
														;br $t8 %new_body %new_exit
	cmp    qword [rbp+(-256)], 0
	jnz    main_new_body_2
	jz     main_new_exit_4
														;%new_body
main_new_body_2:
														;$t10 = move 240
	mov    r11, 240
	mov    qword [rbp+(-344)], r11
														;$t10 = add $t10 8
	mov    r11, qword [rbp+(-344)]
	add    r11, 8
	mov    qword [rbp+(-344)], r11
														;$t9 = alloc $t10
	mov    rdi, qword [rbp+(-344)]
	call   malloc
	mov    qword [rbp+(-264)], rax
														;store 8 $t9 30 0
	mov    r11, qword [rbp+(-264)]
	add    r11, 0
	mov    rax, 30
	mov    qword [r11], rax
														;$t9 = add $t9 8
	mov    r11, qword [rbp+(-264)]
	add    r11, 8
	mov    qword [rbp+(-264)], r11
														;$t11 = mul $t7 8
	mov    r11, qword [rbp+(-248)]
	imul   r11, 8
	mov    qword [rbp+(-208)], r11
														;$t12 = add $t5 $t11
	mov    r11, qword [rbp+(-144)]
	add    r11, qword [rbp+(-208)]
	mov    qword [rbp+(-392)], r11
														;store 8 $t12 $t9 0
	mov    r11, qword [rbp+(-392)]
	add    r11, 0
	mov    rax, qword [rbp+(-264)]
	mov    qword [r11], rax
														;jump %new_loop
	jmp    main_new_loop_3
														;%new_loop
main_new_loop_3:
														;$t7 = add $t7 1
	mov    r11, qword [rbp+(-248)]
	add    r11, 1
	mov    qword [rbp+(-248)], r11
														;jump %new_condition
	jmp    main_new_condition_1
														;jump %new_exit
	jmp    main_new_exit_4
														;%new_exit
main_new_exit_4:
														;$g0(a) = move $t5
	mov    r11, qword [rbp+(-144)]
	mov    qword [rel GV_a], r11
														;$t14 = move 240
	mov    r11, 240
	mov    qword [rbp+(-272)], r11
														;$t14 = add $t14 8
	mov    r11, qword [rbp+(-272)]
	add    r11, 8
	mov    qword [rbp+(-272)], r11
														;$t13 = alloc $t14
	mov    rdi, qword [rbp+(-272)]
	call   malloc
	mov    qword [rbp+(-304)], rax
														;store 8 $t13 30 0
	mov    r11, qword [rbp+(-304)]
	add    r11, 0
	mov    rax, 30
	mov    qword [r11], rax
														;$t13 = add $t13 8
	mov    r11, qword [rbp+(-304)]
	add    r11, 8
	mov    qword [rbp+(-304)], r11
														;$g1(str) = move $t13
	mov    r11, qword [rbp+(-304)]
	mov    qword [rel GV_str], r11
														;jump %entry
	jmp    main_entry_5
														;%entry
main_entry_5:
														;$t2 = move 0
	mov    r11, 0
	mov    qword [rbp+(-280)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_condition
main_for_condition_6:
														;$t15 = sle $t2 29
	mov    r11, qword [rbp+(-280)]
	cmp    r11, 29
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-288)], r11
														;br $t15 %for_body %for_after
	cmp    qword [rbp+(-288)], 0
	jnz    main_for_body_7
	jz     main_for_after_19
														;%for_body
main_for_body_7:
														;$t4 = move 0
	mov    r11, 0
	mov    qword [rbp+(-104)], r11
														;$t17 = mul $t2 8
	mov    r11, qword [rbp+(-280)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t16 = add $g1(str) $t17
	mov    r11, qword [rel GV_str]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-64)], r11
														;$t19 = mul $t2 8
	mov    r11, qword [rbp+(-280)]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t18 = add $g0(a) $t19
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-360)], r11
														;$t20 = load 8 $t18 0
	mov    r11, qword [rbp+(-360)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-296)], r12
														;$t22 = move 0
	mov    r11, 0
	mov    qword [rbp+(-328)], r11
														;$t21 = add $t20 $t22
	mov    r11, qword [rbp+(-296)]
	add    r11, qword [rbp+(-328)]
	mov    qword [rbp+(-232)], r11
														;$t23 = load 8 $t21 0
	mov    r11, qword [rbp+(-232)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-384)], r12
														;$t24 = call __builtin_toString $t23
	mov    rdi, qword [rbp+(-384)]
	call   __builtin_toString
	mov    qword [rbp+(-312)], rax
														;store 8 $t16 $t24 0
	mov    r11, qword [rbp+(-64)]
	add    r11, 0
	mov    rax, qword [rbp+(-312)]
	mov    qword [r11], rax
														;$t3 = move 0
	mov    r11, 0
	mov    qword [rbp+(-152)], r11
														;jump %for_condition
	jmp    main_for_condition_8
														;%for_condition
main_for_condition_8:
														;$t25 = slt $t3 $t2
	mov    r11, qword [rbp+(-152)]
	cmp    r11, qword [rbp+(-280)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t25 %for_body %for_after
	cmp    qword [rbp+(-72)], 0
	jnz    main_for_body_9
	jz     main_for_after_17
														;%for_body
main_for_body_9:
														;$t26 = and $t3 1
	mov    r11, qword [rbp+(-152)]
	and    r11, 1
	mov    qword [rbp+(-400)], r11
														;$t27 = seq $t26 0
	mov    r11, qword [rbp+(-400)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-128)], r11
														;br $t27 %if_true %if_false
	cmp    qword [rbp+(-128)], 0
	jnz    main_if_true_10
	jz     main_if_false_11
														;%if_true
main_if_true_10:
														;$t29 = mul $t2 8
	mov    r11, qword [rbp+(-280)]
	imul   r11, 8
	mov    qword [rbp+(-184)], r11
														;$t28 = add $g0(a) $t29
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-200)], r11
														;$t30 = load 8 $t28 0
	mov    r11, qword [rbp+(-200)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-336)], r12
														;$t32 = move 0
	mov    r11, 0
	mov    qword [rbp+(-376)], r11
														;$t31 = add $t30 $t32
	mov    r11, qword [rbp+(-336)]
	add    r11, qword [rbp+(-376)]
	mov    qword [rbp+(-352)], r11
														;$t33 = load 8 $t31 0
	mov    r11, qword [rbp+(-352)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-320)], r12
														;$t34 = add $t4 $t33
	mov    r11, qword [rbp+(-104)]
	add    r11, qword [rbp+(-320)]
	mov    qword [rbp+(-216)], r11
														;$t4 = move $t34
	mov    r11, qword [rbp+(-216)]
	mov    qword [rbp+(-104)], r11
														;jump %if_merge
	jmp    main_if_merge_12
														;%if_false
main_if_false_11:
														;jump %if_merge
	jmp    main_if_merge_12
														;%if_merge
main_if_merge_12:
														;$t35 = and $t3 1
	mov    r11, qword [rbp+(-152)]
	and    r11, 1
	mov    qword [rbp+(-160)], r11
														;$t36 = seq $t35 1
	mov    r11, qword [rbp+(-160)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-224)], r11
														;br $t36 %if_true %if_false
	cmp    qword [rbp+(-224)], 0
	jnz    main_if_true_13
	jz     main_if_false_14
														;%if_true
main_if_true_13:
														;$t38 = mul $t2 8
	mov    r11, qword [rbp+(-280)]
	imul   r11, 8
	mov    qword [rbp+(-408)], r11
														;$t37 = add $g0(a) $t38
	mov    r11, qword [rel GV_a]
	add    r11, qword [rbp+(-408)]
	mov    qword [rbp+(-96)], r11
														;$t39 = load 8 $t37 0
	mov    r11, qword [rbp+(-96)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-368)], r12
														;$t41 = move 232
	mov    r11, 232
	mov    qword [rbp+(-176)], r11
														;$t40 = add $t39 $t41
	mov    r11, qword [rbp+(-368)]
	add    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-136)], r11
														;$t42 = load 8 $t40 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-192)], r12
														;$t43 = add $t4 $t42
	mov    r11, qword [rbp+(-104)]
	add    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-240)], r11
														;$t4 = move $t43
	mov    r11, qword [rbp+(-240)]
	mov    qword [rbp+(-104)], r11
														;jump %if_merge
	jmp    main_if_merge_15
														;%if_false
main_if_false_14:
														;jump %if_merge
	jmp    main_if_merge_15
														;%if_merge
main_if_merge_15:
														;jump %for_loop
	jmp    main_for_loop_16
														;%for_loop
main_for_loop_16:
														;$t44 = move $t3
	mov    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-168)], r11
														;$t3 = add $t3 1
	mov    r11, qword [rbp+(-152)]
	add    r11, 1
	mov    qword [rbp+(-152)], r11
														;jump %for_condition
	jmp    main_for_condition_8
														;%for_after
main_for_after_17:
														;call __builtin_println $45
	mov    rdi, CONST_STRING_45
	call   __builtin_println
														;jump %for_loop
	jmp    main_for_loop_18
														;%for_loop
main_for_loop_18:
														;$t46 = move $t2
	mov    r11, qword [rbp+(-280)]
	mov    qword [rbp+(-112)], r11
														;$t2 = add $t2 1
	mov    r11, qword [rbp+(-280)]
	add    r11, 1
	mov    qword [rbp+(-280)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_after
main_for_after_19:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_20
														;jump %exit
	jmp    main_exit_20
														;%exit
main_exit_20:
	leave
	ret


SECTION .data
CONST_STRING_45:
	db 115, 116, 114, 49, 115, 116, 114, 50, 115, 116, 114, 51, 115, 116, 114, 52, 115, 116, 114, 53, 115, 116, 114, 54, 115, 116, 114, 55, 115, 116, 114, 56, 115, 116, 114, 57, 115, 116, 114, 49, 48, 0
GV_a:
	dq 0
GV_str:
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