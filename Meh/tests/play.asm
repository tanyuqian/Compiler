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

a:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 176
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
a_enter_0:
														;jump %entry
	jmp    a_entry_1
														;%entry
a_entry_1:
														;$t15 = add $p0 $p1
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-88)], r11
														;$t16 = add $t15 $p2
	mov    r11, qword [rbp+(-88)]
	add    r11, qword [rbp+(-24)]
	mov    qword [rbp+(-112)], r11
														;$t17 = add $t16 $p3
	mov    r11, qword [rbp+(-112)]
	add    r11, qword [rbp+(-32)]
	mov    qword [rbp+(-72)], r11
														;$t18 = add $t17 $p4
	mov    r11, qword [rbp+(-72)]
	add    r11, qword [rbp+(-40)]
	mov    qword [rbp+(-160)], r11
														;$t19 = add $t18 $p5
	mov    r11, qword [rbp+(-160)]
	add    r11, qword [rbp+(-48)]
	mov    qword [rbp+(-120)], r11
														;$t20 = add $t19 $p6
	mov    r11, qword [rbp+(-120)]
	add    r11, qword [rbp+(16)]
	mov    qword [rbp+(-168)], r11
														;$t21 = add $t20 $p7
	mov    r11, qword [rbp+(-168)]
	add    r11, qword [rbp+(24)]
	mov    qword [rbp+(-128)], r11
														;$t22 = add $t21 $p8
	mov    r11, qword [rbp+(-128)]
	add    r11, qword [rbp+(32)]
	mov    qword [rbp+(-104)], r11
														;$t23 = add $t22 $p9
	mov    r11, qword [rbp+(-104)]
	add    r11, qword [rbp+(40)]
	mov    qword [rbp+(-64)], r11
														;$t24 = add $t23 $p10
	mov    r11, qword [rbp+(-64)]
	add    r11, qword [rbp+(48)]
	mov    qword [rbp+(-144)], r11
														;$t25 = add $t24 $p11
	mov    r11, qword [rbp+(-144)]
	add    r11, qword [rbp+(56)]
	mov    qword [rbp+(-96)], r11
														;$t26 = add $t25 $p12
	mov    r11, qword [rbp+(-96)]
	add    r11, qword [rbp+(64)]
	mov    qword [rbp+(-152)], r11
														;$t27 = add $t26 $p13
	mov    r11, qword [rbp+(-152)]
	add    r11, qword [rbp+(72)]
	mov    qword [rbp+(-136)], r11
														;$t28 = add $t27 $p14
	mov    r11, qword [rbp+(-136)]
	add    r11, qword [rbp+(80)]
	mov    qword [rbp+(-80)], r11
														;ret $t28
	mov    rax, qword [rbp+(-80)]
	leave
	ret
														;jump %exit
	jmp    a_exit_2
														;jump %exit
	jmp    a_exit_2
														;%exit
a_exit_2:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 80
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
														;$t29 = call a 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
	mov    rdi, 1
	mov    rsi, 2
	mov    rdx, 3
	mov    rcx, 4
	mov    r8, 5
	mov    r9, 6
	push   15
	push   14
	push   13
	push   12
	push   11
	push   10
	push   9
	push   8
	push   7
	call   a
	mov    qword [rbp+(-72)], rax
														;$t30 = call __builtin_toString $t29
	mov    rdi, qword [rbp+(-72)]
	call   __builtin_toString
	mov    qword [rbp+(-64)], rax
														;call __builtin_println $t30
	mov    rdi, qword [rbp+(-64)]
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