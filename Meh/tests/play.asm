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

cd:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 168
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
cd_enter_0:
														;jump %entry
	jmp    cd_entry_1
														;%entry
cd_entry_1:
														;$t10 = seq $p0 1
	mov    r11, qword [rbp+(-8)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-88)], r11
														;br $t10 %if_true %if_false
	cmp    qword [rbp+(-88)], 0
	jnz    cd_if_true_2
	jz     cd_if_false_3
														;%if_true
cd_if_true_2:
														;$t12 = call __builtin_string_concat $11 $p1
	mov    rdi, CONST_STRING_11
	mov    rsi, qword [rbp+(-16)]
	call   __builtin_string_concat
	mov    qword [rbp+(-64)], rax
														;$t14 = call __builtin_string_concat $t12 $13
	mov    rdi, qword [rbp+(-64)]
	mov    rsi, CONST_STRING_13
	call   __builtin_string_concat
	mov    qword [rbp+(-136)], rax
														;$t15 = call __builtin_string_concat $t14 $p3
	mov    rdi, qword [rbp+(-136)]
	mov    rsi, qword [rbp+(-32)]
	call   __builtin_string_concat
	mov    qword [rbp+(-128)], rax
														;call __builtin_println $t15
	mov    rdi, qword [rbp+(-128)]
	call   __builtin_println
														;$t16 = move $p4
	mov    r11, qword [rbp+(-40)]
	mov    qword [rbp+(-96)], r11
														;$p4 = add $p4 1
	mov    r11, qword [rbp+(-40)]
	add    r11, 1
	mov    qword [rbp+(-40)], r11
														;jump %if_merge
	jmp    cd_if_merge_4
														;%if_false
cd_if_false_3:
														;$t17 = sub $p0 1
	mov    r11, qword [rbp+(-8)]
	sub    r11, 1
	mov    qword [rbp+(-152)], r11
														;$t18 = call cd $t17 $p1 $p3 $p2 $p4
	mov    rdi, qword [rbp+(-152)]
	mov    rsi, qword [rbp+(-16)]
	mov    rdx, qword [rbp+(-32)]
	mov    rcx, qword [rbp+(-24)]
	mov    r8, qword [rbp+(-40)]
	call   cd
	mov    qword [rbp+(-112)], rax
														;$p4 = move $t18
	mov    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-40)], r11
														;$t20 = call __builtin_string_concat $19 $p1
	mov    rdi, CONST_STRING_19
	mov    rsi, qword [rbp+(-16)]
	call   __builtin_string_concat
	mov    qword [rbp+(-80)], rax
														;$t22 = call __builtin_string_concat $t20 $21
	mov    rdi, qword [rbp+(-80)]
	mov    rsi, CONST_STRING_21
	call   __builtin_string_concat
	mov    qword [rbp+(-144)], rax
														;$t23 = call __builtin_string_concat $t22 $p3
	mov    rdi, qword [rbp+(-144)]
	mov    rsi, qword [rbp+(-32)]
	call   __builtin_string_concat
	mov    qword [rbp+(-120)], rax
														;call __builtin_println $t23
	mov    rdi, qword [rbp+(-120)]
	call   __builtin_println
														;$t24 = sub $p0 1
	mov    r11, qword [rbp+(-8)]
	sub    r11, 1
	mov    qword [rbp+(-72)], r11
														;$t25 = call cd $t24 $p2 $p1 $p3 $p4
	mov    rdi, qword [rbp+(-72)]
	mov    rsi, qword [rbp+(-24)]
	mov    rdx, qword [rbp+(-16)]
	mov    rcx, qword [rbp+(-32)]
	mov    r8, qword [rbp+(-40)]
	call   cd
	mov    qword [rbp+(-160)], rax
														;$p4 = move $t25
	mov    r11, qword [rbp+(-160)]
	mov    qword [rbp+(-40)], r11
														;$t26 = move $p4
	mov    r11, qword [rbp+(-40)]
	mov    qword [rbp+(-104)], r11
														;$p4 = add $p4 1
	mov    r11, qword [rbp+(-40)]
	add    r11, 1
	mov    qword [rbp+(-40)], r11
														;jump %if_merge
	jmp    cd_if_merge_4
														;%if_merge
cd_if_merge_4:
														;ret $p4
	mov    rax, qword [rbp+(-40)]
	leave
	ret
														;jump %exit
	jmp    cd_exit_5
														;jump %exit
	jmp    cd_exit_5
														;%exit
cd_exit_5:
	leave
	ret


main:
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
main_enter_0:
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t5 = move $27
	mov    r11, CONST_STRING_27
	mov    qword [rbp+(-64)], r11
														;$t6 = move $28
	mov    r11, CONST_STRING_28
	mov    qword [rbp+(-80)], r11
														;$t7 = move $29
	mov    r11, CONST_STRING_29
	mov    qword [rbp+(-112)], r11
														;$t30 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-120)], rax
														;$t8 = move $t30
	mov    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-104)], r11
														;$t31 = call cd $t8 $t5 $t6 $t7 0
	mov    rdi, qword [rbp+(-104)]
	mov    rsi, qword [rbp+(-64)]
	mov    rdx, qword [rbp+(-80)]
	mov    rcx, qword [rbp+(-112)]
	mov    r8, 0
	call   cd
	mov    qword [rbp+(-88)], rax
														;$t9 = move $t31
	mov    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-96)], r11
														;$t32 = call __builtin_toString $t9
	mov    rdi, qword [rbp+(-96)]
	call   __builtin_toString
	mov    qword [rbp+(-72)], rax
														;call __builtin_println $t32
	mov    rdi, qword [rbp+(-72)]
	call   __builtin_println
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_2
														;jump %exit
	jmp    main_exit_2
														;%exit
main_exit_2:
	leave
	ret


SECTION .data
CONST_STRING_28:
	db 66, 0
CONST_STRING_29:
	db 67, 0
CONST_STRING_11:
	db 109, 111, 118, 101, 32, 0
CONST_STRING_13:
	db 32, 45, 45, 62, 32, 0
CONST_STRING_21:
	db 32, 45, 45, 62, 32, 0
CONST_STRING_27:
	db 65, 0
CONST_STRING_19:
	db 109, 111, 118, 101, 32, 0
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
