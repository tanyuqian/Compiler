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

main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 296
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
main_enter_0:
														;$t4 = move 32
	mov    r11, 32
	mov    qword [rbp+(-160)], r11
														;$t4 = add $t4 8
	mov    r11, qword [rbp+(-160)]
	add    r11, 8
	mov    qword [rbp+(-160)], r11
														;$t3 = alloc $t4
	mov    rdi, qword [rbp+(-160)]
	call   malloc
	mov    qword [rbp+(-136)], rax
														;store 8 $t3 4 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    rax, 4
	mov    qword [r11], rax
														;$t3 = add $t3 8
	mov    r11, qword [rbp+(-136)]
	add    r11, 8
	mov    qword [rbp+(-136)], r11
														;$g0(a) = move $t3
	mov    r11, qword [rbp+(-136)]
	mov    qword [rel a], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t5 = call __builtin_getArraySize $g0(a)
	mov    rdi, qword [rel a]
	call   __builtin_getArraySize
	mov    qword [rbp+(-96)], rax
														;$t7 = mul $t5 8
	mov    r11, qword [rbp+(-96)]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t7 = add $t7 8
	mov    r11, qword [rbp+(-120)]
	add    r11, 8
	mov    qword [rbp+(-120)], r11
														;$t6 = alloc $t7
	mov    rdi, qword [rbp+(-120)]
	call   malloc
	mov    qword [rbp+(-112)], rax
														;store 8 $t6 $t5 0
	mov    r11, qword [rbp+(-112)]
	add    r11, 0
	mov    rax, qword [rbp+(-96)]
	mov    qword [r11], rax
														;$t6 = add $t6 8
	mov    r11, qword [rbp+(-112)]
	add    r11, 8
	mov    qword [rbp+(-112)], r11
														;$t1 = move $t6
	mov    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-232)], r11
														;$t2 = move 0
	mov    r11, 0
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_condition
main_for_condition_2:
														;$t8 = call __builtin_getArraySize $g0(a)
	mov    rdi, qword [rel a]
	call   __builtin_getArraySize
	mov    qword [rbp+(-264)], rax
														;$t9 = slt $t2 $t8
	mov    r11, qword [rbp+(-128)]
	cmp    r11, qword [rbp+(-264)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-192)], r11
														;br $t9 %for_body %for_after
	cmp    qword [rbp+(-192)], 0
	jnz    main_for_body_3
	jz     main_for_after_5
														;%for_body
main_for_body_3:
														;$t11 = mul $t2 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t10 = add $g0(a) $t11
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-168)], r11
														;store 8 $t10 0 0
	mov    r11, qword [rbp+(-168)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t13 = mul $t2 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-200)], r11
														;$t12 = add $t1 $t13
	mov    r11, qword [rbp+(-232)]
	add    r11, qword [rbp+(-200)]
	mov    qword [rbp+(-184)], r11
														;$t14 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-208)], rax
														;store 8 $t12 $t14 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, qword [rbp+(-208)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    main_for_loop_4
														;%for_loop
main_for_loop_4:
														;$t15 = move $t2
	mov    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-256)], r11
														;$t2 = add $t2 1
	mov    r11, qword [rbp+(-128)]
	add    r11, 1
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_after
main_for_after_5:
														;$t2 = move 0
	mov    r11, 0
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_condition
main_for_condition_6:
														;$t16 = call __builtin_getArraySize $g0(a)
	mov    rdi, qword [rel a]
	call   __builtin_getArraySize
	mov    qword [rbp+(-248)], rax
														;$t17 = slt $t2 $t16
	mov    r11, qword [rbp+(-128)]
	cmp    r11, qword [rbp+(-248)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-224)], r11
														;br $t17 %for_body %for_after
	cmp    qword [rbp+(-224)], 0
	jnz    main_for_body_7
	jz     main_for_after_9
														;%for_body
main_for_body_7:
														;$t19 = mul $t2 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-104)], r11
														;$t18 = add $g0(a) $t19
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-176)], r11
														;$t20 = load 8 $t18 0
	mov    r11, qword [rbp+(-176)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-280)], r12
														;$t21 = call __builtin_toString $t20
	mov    rdi, qword [rbp+(-280)]
	call   __builtin_toString
	mov    qword [rbp+(-72)], rax
														;call __builtin_print $t21
	mov    rdi, qword [rbp+(-72)]
	call   __builtin_print
														;jump %for_loop
	jmp    main_for_loop_8
														;%for_loop
main_for_loop_8:
														;$t22 = move $t2
	mov    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-216)], r11
														;$t2 = add $t2 1
	mov    r11, qword [rbp+(-128)]
	add    r11, 1
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_6
														;%for_after
main_for_after_9:
														;call __builtin_println $23
	mov    rdi, CONST_STRING_23
	call   __builtin_println
														;$g0(a) = move $t1
	mov    r11, qword [rbp+(-232)]
	mov    qword [rel a], r11
														;$t2 = move 0
	mov    r11, 0
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_10
														;%for_condition
main_for_condition_10:
														;$t24 = call __builtin_getArraySize $g0(a)
	mov    rdi, qword [rel a]
	call   __builtin_getArraySize
	mov    qword [rbp+(-240)], rax
														;$t25 = slt $t2 $t24
	mov    r11, qword [rbp+(-128)]
	cmp    r11, qword [rbp+(-240)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t25 %for_body %for_after
	cmp    qword [rbp+(-64)], 0
	jnz    main_for_body_11
	jz     main_for_after_13
														;%for_body
main_for_body_11:
														;$t27 = mul $t2 8
	mov    r11, qword [rbp+(-128)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t26 = add $g0(a) $t27
	mov    r11, qword [rel a]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-288)], r11
														;$t28 = load 8 $t26 0
	mov    r11, qword [rbp+(-288)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-272)], r12
														;$t29 = call __builtin_toString $t28
	mov    rdi, qword [rbp+(-272)]
	call   __builtin_toString
	mov    qword [rbp+(-144)], rax
														;call __builtin_print $t29
	mov    rdi, qword [rbp+(-144)]
	call   __builtin_print
														;jump %for_loop
	jmp    main_for_loop_12
														;%for_loop
main_for_loop_12:
														;$t30 = move $t2
	mov    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-152)], r11
														;$t2 = add $t2 1
	mov    r11, qword [rbp+(-128)]
	add    r11, 1
	mov    qword [rbp+(-128)], r11
														;jump %for_condition
	jmp    main_for_condition_10
														;%for_after
main_for_after_13:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_14
														;jump %exit
	jmp    main_exit_14
														;%exit
main_exit_14:
	leave
	ret


SECTION .data
a:
	dq 0
CONST_STRING_23:
	db "", 0
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

