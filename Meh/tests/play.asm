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
	sub    rsp, 256
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
														;$t0 = move 1000000
	mov    r11, 1000000
	mov    qword [rbp+(-112)], r11
														;call __builtin_print $4
	mov    rdi, CONST_STRING_4
	call   __builtin_print
														;$t5 = mul $t0 2
	mov    r11, qword [rbp+(-112)]
	imul   r11, 2
	mov    qword [rbp+(-176)], r11
														;$t6 = add $t5 1
	mov    r11, qword [rbp+(-176)]
	add    r11, 1
	mov    qword [rbp+(-120)], r11
														;$t7 = call __builtin_toString $t6
	mov    rdi, qword [rbp+(-120)]
	call   __builtin_toString
	mov    qword [rbp+(-64)], rax
														;$t9 = call __builtin_string_concat $t7 $8
	mov    rdi, qword [rbp+(-64)]
	mov    rsi, CONST_STRING_8
	call   __builtin_string_concat
	mov    qword [rbp+(-80)], rax
														;$t10 = call __builtin_toString $t0
	mov    rdi, qword [rbp+(-112)]
	call   __builtin_toString
	mov    qword [rbp+(-200)], rax
														;$t11 = call __builtin_string_concat $t9 $t10
	mov    rdi, qword [rbp+(-80)]
	mov    rsi, qword [rbp+(-200)]
	call   __builtin_string_concat
	mov    qword [rbp+(-104)], rax
														;call __builtin_println $t11
	mov    rdi, qword [rbp+(-104)]
	call   __builtin_println
														;$t2 = move 1
	mov    r11, 1
	mov    qword [rbp+(-240)], r11
														;$t1 = move 0
	mov    r11, 0
	mov    qword [rbp+(-208)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_condition
main_for_condition_2:
														;$t12 = slt $t1 $t0
	mov    r11, qword [rbp+(-208)]
	cmp    r11, qword [rbp+(-112)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t12 %for_body %for_after
	cmp    qword [rbp+(-72)], 0
	jnz    main_for_body_3
	jz     main_for_after_8
														;%for_body
main_for_body_3:
														;$t13 = call __builtin_toString $t2
	mov    rdi, qword [rbp+(-240)]
	call   __builtin_toString
	mov    qword [rbp+(-88)], rax
														;$t15 = call __builtin_string_concat $t13 $14
	mov    rdi, qword [rbp+(-88)]
	mov    rsi, CONST_STRING_14
	call   __builtin_string_concat
	mov    qword [rbp+(-168)], rax
														;$t16 = add $t2 1
	mov    r11, qword [rbp+(-240)]
	add    r11, 1
	mov    qword [rbp+(-128)], r11
														;$t17 = call __builtin_toString $t16
	mov    rdi, qword [rbp+(-128)]
	call   __builtin_toString
	mov    qword [rbp+(-136)], rax
														;$t18 = call __builtin_string_concat $t15 $t17
	mov    rdi, qword [rbp+(-168)]
	mov    rsi, qword [rbp+(-136)]
	call   __builtin_string_concat
	mov    qword [rbp+(-216)], rax
														;$t20 = call __builtin_string_concat $t18 $19
	mov    rdi, qword [rbp+(-216)]
	mov    rsi, CONST_STRING_19
	call   __builtin_string_concat
	mov    qword [rbp+(-152)], rax
														;$t21 = add $t2 2
	mov    r11, qword [rbp+(-240)]
	add    r11, 2
	mov    qword [rbp+(-224)], r11
														;$t22 = neg $t21
	neg    qword [rbp+(-224)]
														;$t23 = call __builtin_toString $t22
	mov    rdi, qword [rbp+(-248)]
	call   __builtin_toString
	mov    qword [rbp+(-144)], rax
														;$t24 = call __builtin_string_concat $t20 $t23
	mov    rdi, qword [rbp+(-152)]
	mov    rsi, qword [rbp+(-144)]
	call   __builtin_string_concat
	mov    qword [rbp+(-96)], rax
														;$t3 = move $t24
	mov    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-184)], r11
														;$t25 = rem $t1 100000
	mov    r11, qword [rbp+(-208)]
	mov    rax, qword [rbp+(-208)]
	cqo
	mov    r11, 100000
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-192)], r11
														;$t26 = seq $t25 0
	mov    r11, qword [rbp+(-192)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-232)], r11
														;br $t26 %if_true %if_false
	cmp    qword [rbp+(-232)], 0
	jnz    main_if_true_4
	jz     main_if_false_5
														;%if_true
main_if_true_4:
														;call __builtin_println $t3
	mov    rdi, qword [rbp+(-184)]
	call   __builtin_println
														;jump %if_merge
	jmp    main_if_merge_6
														;%if_false
main_if_false_5:
														;jump %if_merge
	jmp    main_if_merge_6
														;%if_merge
main_if_merge_6:
														;$t27 = add $t2 2
	mov    r11, qword [rbp+(-240)]
	add    r11, 2
	mov    qword [rbp+(-160)], r11
														;$t2 = move $t27
	mov    r11, qword [rbp+(-160)]
	mov    qword [rbp+(-240)], r11
														;jump %for_loop
	jmp    main_for_loop_7
														;%for_loop
main_for_loop_7:
														;$t1 = add $t1 1
	mov    r11, qword [rbp+(-208)]
	add    r11, 1
	mov    qword [rbp+(-208)], r11
														;jump %for_condition
	jmp    main_for_condition_2
														;%for_after
main_for_after_8:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_9
														;jump %exit
	jmp    main_exit_9
														;%exit
main_exit_9:
	leave
	ret


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