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
	sub    rsp, 264
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
														;$t3 = alloc 8
	mov    rdi, 8
	call   malloc
	mov    qword [rbp+(-160)], rax
														;$t0 = move $t3
	mov    r11, qword [rbp+(-160)]
	mov    qword [rbp+(-184)], r11
														;$t4 = alloc 8
	mov    rdi, 8
	call   malloc
	mov    qword [rbp+(-168)], rax
														;$t1 = move $t4
	mov    r11, qword [rbp+(-168)]
	mov    qword [rbp+(-128)], r11
														;$t5 = alloc 16
	mov    rdi, 16
	call   malloc
	mov    qword [rbp+(-176)], rax
														;$t2 = move $t5
	mov    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-64)], r11
														;store 8 $t0 $t2 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, qword [rbp+(-64)]
	mov    qword [r11], rax
														;store 8 $t1 $t2 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    rax, qword [rbp+(-64)]
	mov    qword [r11], rax
														;store 8 $t2 0 0
	mov    r11, qword [rbp+(-64)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t6 = call __builtin_getString
	call   __builtin_getString
	mov    qword [rbp+(-112)], rax
														;store 8 $t2 $t6 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    rax, qword [rbp+(-112)]
	mov    qword [r11], rax
														;$t7 = load 8 $t0 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-216)], r12
														;$t8 = load 8 $t7 8
	mov    r11, qword [rbp+(-216)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-200)], r12
														;$t9 = load 8 $t1 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-72)], r12
														;$t10 = load 8 $t9 8
	mov    r11, qword [rbp+(-72)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-136)], r12
														;$t11 = call __builtin_string_concat $t8 $t10
	mov    rdi, qword [rbp+(-200)]
	mov    rsi, qword [rbp+(-136)]
	call   __builtin_string_concat
	mov    qword [rbp+(-152)], rax
														;$t12 = load 8 $t2 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-88)], r12
														;$t13 = load 8 $t2 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-224)], r12
														;$t14 = call __builtin_string_concat $t12 $t13
	mov    rdi, qword [rbp+(-88)]
	mov    rsi, qword [rbp+(-224)]
	call   __builtin_string_concat
	mov    qword [rbp+(-240)], rax
														;$t15 = call __builtin_string_equalTo $t11 $t14
	mov    rdi, qword [rbp+(-152)]
	mov    rsi, qword [rbp+(-240)]
	call   __builtin_string_equalTo
	mov    qword [rbp+(-208)], rax
														;br $t15 %if_true %if_false
	cmp    qword [rbp+(-208)], 0
	jnz    main_if_true_2
	jz     main_if_false_3
														;%if_true
main_if_true_2:
														;$t16 = load 8 $t2 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-192)], r12
														;$t17 = load 8 $t2 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-120)], r12
														;$t18 = call __builtin_string_concat $t16 $t17
	mov    rdi, qword [rbp+(-192)]
	mov    rsi, qword [rbp+(-120)]
	call   __builtin_string_concat
	mov    qword [rbp+(-144)], rax
														;store 8 $t2 $t18 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    rax, qword [rbp+(-144)]
	mov    qword [r11], rax
														;jump %if_merge
	jmp    main_if_merge_4
														;%if_false
main_if_false_3:
														;jump %if_merge
	jmp    main_if_merge_4
														;%if_merge
main_if_merge_4:
														;$t20 = call __builtin_parseInt $19
	mov    rdi, CONST_STRING_19
	call   __builtin_parseInt
	mov    qword [rbp+(-256)], rax
														;$t21 = call __builtin_toString $t20
	mov    rdi, qword [rbp+(-256)]
	call   __builtin_toString
	mov    qword [rbp+(-232)], rax
														;store 8 $t2 $t21 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    rax, qword [rbp+(-232)]
	mov    qword [r11], rax
														;$t22 = load 8 $t2 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-80)], r12
														;$t23 = call __builtin_toString 123
	mov    rdi, 123
	call   __builtin_toString
	mov    qword [rbp+(-96)], rax
														;$t24 = call __builtin_string_equalTo $t22 $t23
	mov    rdi, qword [rbp+(-80)]
	mov    rsi, qword [rbp+(-96)]
	call   __builtin_string_equalTo
	mov    qword [rbp+(-104)], rax
														;br $t24 %if_true %if_false
	cmp    qword [rbp+(-104)], 0
	jnz    main_if_true_5
	jz     main_if_false_6
														;%if_true
main_if_true_5:
														;$t25 = load 8 $t2 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-248)], r12
														;call __builtin_println $t25
	mov    rdi, qword [rbp+(-248)]
	call   __builtin_println
														;jump %if_merge
	jmp    main_if_merge_7
														;%if_false
main_if_false_6:
														;jump %if_merge
	jmp    main_if_merge_7
														;%if_merge
main_if_merge_7:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_8
														;jump %exit
	jmp    main_exit_8
														;%exit
main_exit_8:
	leave
	ret


SECTION .data
CONST_STRING_19:
	db "123.456", 0
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