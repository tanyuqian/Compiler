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

work:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 320
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
work_enter_0:
														;jump %entry
	jmp    work_entry_1
work_entry_1:
														;$t6 = load 8 $p3 8
	mov    rax, qword [rbp+(-16)]
	mov    r11, rax
	add    r11, 8
	mov    rsi, qword [r11]
														;$t7 = sle $t6 100
	mov    r11, 100
	mov    rsi, rsi
	cmp    rsi, r11
	setle   al
	movzx    rsi, al
														;br $t7 %if_true %if_false
	cmp    rsi, 0
	jnz    work_if_true_2
	jz     work_if_false_3
work_if_true_2:
														;$t9 = call __builtin_string_concat $p2 $8
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rax, CONST_STRING_8
	mov    rsi, rax
	call   __builtin_string_concat
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	mov    rsi, rax
														;$t10 = load 8 $p3 0
	mov    rax, qword [rbp+(-16)]
	mov    r11, rax
	add    r11, 0
	mov    rbx, qword [r11]
														;$t11 = call __builtin_string_concat $t9 $t10
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rdi, rsi
	mov    rsi, rbx
	call   __builtin_string_concat
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	mov    rbx, rax
														;$t13 = call __builtin_string_concat $t11 $12
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rdi, rbx
	mov    rax, CONST_STRING_12
	mov    rsi, rax
	call   __builtin_string_concat
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	mov    rbx, rax
														;call __builtin_println $t13
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rdi, rbx
	call   __builtin_println
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
														;jump %if_merge
	jmp    work_if_merge_4
work_if_merge_4:
														;$t20 = load 8 $p3 8
	mov    rax, qword [rbp+(-16)]
	mov    r11, rax
	add    r11, 8
	mov    rbx, qword [r11]
														;$t21 = add $t20 $g1(work_anger)
	mov    r11, qword [rel GV_work_anger]
	mov    rbx, rbx
	add    rbx, r11
														;store 8 $p3 $t21 8
	mov    rax, qword [rbp+(-16)]
	mov    r11, rax
	add    r11, 8
	mov    qword [r11], rbx
														;jump %exit
	jmp    work_exit_5
work_if_false_3:
														;$t15 = call __builtin_string_concat $p2 $14
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rax, CONST_STRING_14
	mov    rsi, rax
	call   __builtin_string_concat
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	mov    rsi, rax
														;$t16 = load 8 $p3 0
	mov    rax, qword [rbp+(-16)]
	mov    r11, rax
	add    r11, 0
	mov    rbx, qword [r11]
														;$t17 = call __builtin_string_concat $t15 $t16
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rdi, rsi
	mov    rsi, rbx
	call   __builtin_string_concat
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	mov    rbx, rax
														;$t19 = call __builtin_string_concat $t17 $18
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rdi, rbx
	mov    rax, CONST_STRING_18
	mov    rsi, rax
	call   __builtin_string_concat
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	mov    rbx, rax
														;call __builtin_println $t19
	mov    qword [rbp + (-192)], rbx
	mov    qword [rbp + (-216)], rsi
	mov    rdi, rbx
	call   __builtin_println
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
														;jump %if_merge
	jmp    work_if_merge_4
work_exit_5:
	mov    rbx, qword [rbp + (-192)]
	mov    rsi, qword [rbp + (-216)]
	leave
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
	mov    qword [rbp + (-168)], r8
	mov    qword [rbp + (-152)], rsi
main_enter_0:
														;$g0(init_anger) = move 100
	mov    rax, 100
	mov    qword [rel GV_init_anger], rax
														;$g1(work_anger) = move 10
	mov    rax, 10
	mov    qword [rel GV_work_anger], rax
														;jump %entry
	jmp    main_entry_1
main_entry_1:
														;$t22 = alloc 16
	mov    rax, 16
	mov    rdi, rax
	mov    qword [rbp + (-168)], r8
	mov    qword [rbp + (-152)], rsi
	call   malloc
	mov    r8, qword [rbp + (-168)]
	mov    rsi, qword [rbp + (-152)]
	mov    r8, rax
														;$t4 = move $t22
	mov    r8, r8
														;store 8 $t4 $23 0
	mov    rax, CONST_STRING_23
	mov    r11, r8
	add    r11, 0
	mov    qword [r11], rax
														;store 8 $t4 0 8
	mov    rax, 0
	mov    r11, r8
	add    r11, 8
	mov    qword [r11], rax
														;$t24 = alloc 16
	mov    rax, 16
	mov    rdi, rax
	mov    qword [rbp + (-168)], r8
	mov    qword [rbp + (-152)], rsi
	call   malloc
	mov    r8, qword [rbp + (-168)]
	mov    rsi, qword [rbp + (-152)]
	mov    rsi, rax
														;$t5 = move $t24
	mov    rsi, rsi
														;store 8 $t5 $25 0
	mov    rax, CONST_STRING_25
	mov    r11, rsi
	add    r11, 0
	mov    qword [r11], rax
														;store 8 $t5 $g0(init_anger) 8
	mov    rax, qword [rel GV_init_anger]
	mov    r11, rsi
	add    r11, 8
	mov    qword [r11], rax
														;call work $26 $t4
	mov    qword [rbp + (-168)], r8
	mov    qword [rbp + (-152)], rsi
	mov    rax, CONST_STRING_26
	mov    rdi, rax
	mov    rsi, r8
	call   work
	mov    r8, qword [rbp + (-168)]
	mov    rsi, qword [rbp + (-152)]
														;call work $27 $t5
	mov    qword [rbp + (-168)], r8
	mov    qword [rbp + (-152)], rsi
	mov    rax, CONST_STRING_27
	mov    rdi, rax
	mov    rsi, rsi
	call   work
	mov    r8, qword [rbp + (-168)]
	mov    rsi, qword [rbp + (-152)]
														;call work $28 $t5
	mov    qword [rbp + (-168)], r8
	mov    qword [rbp + (-152)], rsi
	mov    rax, CONST_STRING_28
	mov    rdi, rax
	mov    rsi, rsi
	call   work
	mov    r8, qword [rbp + (-168)]
	mov    rsi, qword [rbp + (-152)]
														;ret 0
	mov    rax, 0
	mov    rax, rax
	jmp    main_exit_2
														;jump %exit
	jmp    main_exit_2
main_exit_2:
	mov    r8, qword [rbp + (-168)]
	mov    rsi, qword [rbp + (-152)]
	leave
	ret
SECTION .data
GV_work_anger:
	dq 0
CONST_STRING_12:
	db 32, 101, 110, 106, 111, 121, 115, 32, 116, 104, 105, 115, 32, 119, 111, 114, 107, 46, 32, 88, 68, 0
GV_init_anger:
	dq 0
CONST_STRING_18:
	db 32, 119, 97, 110, 116, 115, 32, 116, 111, 32, 103, 105, 118, 101, 32, 117, 112, 33, 33, 33, 33, 33, 0
CONST_STRING_26:
	db 77, 82, 0
CONST_STRING_8:
	db 44, 32, 0
CONST_STRING_25:
	db 116, 104, 101, 32, 115, 116, 114, 105, 107, 105, 110, 103, 32, 84, 65, 0
CONST_STRING_27:
	db 77, 97, 114, 115, 0
CONST_STRING_23:
	db 116, 104, 101, 32, 108, 101, 97, 100, 105, 110, 103, 32, 84, 65, 0
CONST_STRING_14:
	db 44, 32, 0
CONST_STRING_28:
	db 77, 97, 114, 115, 0
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