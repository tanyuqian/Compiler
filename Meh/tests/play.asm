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


    mov     r12, qword[rbp-8]
    mov     r13, qword[rbp-16]
    mov     r14, qword[rbp-24]
    mov     r15, qword[rbp-32]

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
	sub    rsp, 192
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
														;$t0 = move 5
	mov    r11, 5
	mov    qword [rbp+(-144)], r11
														;$t1 = move 0
	mov    r11, 0
	mov    qword [rbp+(-160)], r11
														;$t4 = sne $t1 0
	mov    r11, qword [rbp+(-160)]
	cmp    r11, 0
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t4 %logical_true %logical_false
	cmp    qword [rbp+(-136)], 0
	jnz    main_logical_true_2
	jz     main_logical_false_3
														;%logical_true
main_logical_true_2:
														;$t5 = div $t0 $t1
	mov    r11, qword [rbp+(-144)]
	mov    rax, qword [rbp+(-144)]
	cqo
	mov    r11, qword [rbp+(-160)]
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-80)], r11
														;$t6 = sne $t5 1
	mov    r11, qword [rbp+(-80)]
	cmp    r11, 1
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-168)], r11
														;$t3 = move $t6
	mov    r11, qword [rbp+(-168)]
	mov    qword [rbp+(-176)], r11
														;jump %logical_merge
	jmp    main_logical_merge_4
														;%logical_false
main_logical_false_3:
														;$t3 = move 0
	mov    r11, 0
	mov    qword [rbp+(-176)], r11
														;jump %logical_merge
	jmp    main_logical_merge_4
														;%logical_merge
main_logical_merge_4:
														;br $t3 %if_true %if_false
	cmp    qword [rbp+(-176)], 0
	jnz    main_if_true_5
	jz     main_if_false_6
														;%if_true
main_if_true_5:
														;$t2 = move 10
	mov    r11, 10
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    main_if_merge_7
														;%if_false
main_if_false_6:
														;$t2 = move 20
	mov    r11, 20
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    main_if_merge_7
														;%if_merge
main_if_merge_7:
														;$t9 = seq $t2 10
	mov    r11, qword [rbp+(-184)]
	cmp    r11, 10
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t9 %logical_true %logical_false
	cmp    qword [rbp+(-72)], 0
	jnz    main_logical_true_8
	jz     main_logical_false_9
														;%logical_true
main_logical_true_8:
														;$t10 = div $t0 $t1
	mov    r11, qword [rbp+(-144)]
	mov    rax, qword [rbp+(-144)]
	cqo
	mov    r11, qword [rbp+(-160)]
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-88)], r11
														;$t11 = seq $t10 0
	mov    r11, qword [rbp+(-88)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-112)], r11
														;$t8 = move $t11
	mov    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-152)], r11
														;jump %logical_merge
	jmp    main_logical_merge_10
														;%logical_false
main_logical_false_9:
														;$t8 = move 0
	mov    r11, 0
	mov    qword [rbp+(-152)], r11
														;jump %logical_merge
	jmp    main_logical_merge_10
														;%logical_merge
main_logical_merge_10:
														;br $t8 %logical_true %logical_false
	cmp    qword [rbp+(-152)], 0
	jnz    main_logical_true_11
	jz     main_logical_false_12
														;%logical_true
main_logical_true_11:
														;$t12 = seq $t0 5
	mov    r11, qword [rbp+(-144)]
	cmp    r11, 5
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;$t7 = move $t12
	mov    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-96)], r11
														;jump %logical_merge
	jmp    main_logical_merge_13
														;%logical_false
main_logical_false_12:
														;$t7 = move 0
	mov    r11, 0
	mov    qword [rbp+(-96)], r11
														;jump %logical_merge
	jmp    main_logical_merge_13
														;%logical_merge
main_logical_merge_13:
														;$t13 = not $t7
	not    qword [rbp+(-96)]
														;br $t13 %if_true %if_false
	cmp    qword [rbp+(-104)], 0
	jnz    main_if_true_14
	jz     main_if_false_15
														;%if_true
main_if_true_14:
														;$t2 = move 30
	mov    r11, 30
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    main_if_merge_16
														;%if_false
main_if_false_15:
														;jump %if_merge
	jmp    main_if_merge_16
														;%if_merge
main_if_merge_16:
														;$t14 = call main
	call   main
	mov    qword [rbp+(-128)], rax
														;$t15 = call __builtin_toString $t14
	mov    rdi, qword [rbp+(-128)]
	call   __builtin_toString
	mov    qword [rbp+(-120)], rax
														;call __builtin_println $t15
	mov    rdi, qword [rbp+(-120)]
	call   __builtin_println
														;ret $t2
	mov    rax, qword [rbp+(-184)]
	leave
	ret
														;jump %exit
	jmp    main_exit_17
														;jump %exit
	jmp    main_exit_17
														;%exit
main_exit_17:
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