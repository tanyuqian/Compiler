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

hex2int:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 440
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-400)], r13
	mov    qword [rbp + (-392)], r12
	mov    qword [rbp + (-416)], r15
	mov    qword [rbp + (-408)], r14
hex2int_enter_0:
														;jump %entry
	jmp    hex2int_entry_1
hex2int_entry_1:
														;$t2 = move 0
	mov    rax, 0
	mov    r15, rax
														;$t1 = move 0
	mov    rax, 0
	mov    r14, rax
														;jump %for_condition
	jmp    hex2int_for_condition_2
hex2int_for_condition_2:
														;$t59 = call __builtin_getStringLength $p0
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    r12, rax
														;$t60 = slt $t1 $t59
	mov    r13, r14
	cmp    r13, r12
	setl   al
	movzx    r13, al
														;br $t60 %for_body %for_after
	cmp    r13, 0
	jnz    hex2int_for_body_3
	jz     hex2int_for_after_23
hex2int_for_body_3:
														;$t61 = call __builtin_ord $p0 $t1
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rsi, r14
	call   __builtin_ord
	mov    r13, rax
														;$t3 = move $t61
	mov    r13, r13
														;$t63 = sge $t3 48
	mov    r11, 48
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t63 %logical_true %logical_false
	cmp    r12, 0
	jnz    hex2int_logical_true_4
	jz     hex2int_logical_false_5
hex2int_logical_true_4:
														;$t64 = sle $t3 57
	mov    r11, 57
	mov    r12, r13
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t62 = move $t64
	mov    r12, r12
														;jump %logical_merge
	jmp    hex2int_logical_merge_6
hex2int_logical_merge_6:
														;br $t62 %if_true %if_false
	cmp    r12, 0
	jnz    hex2int_if_true_7
	jz     hex2int_if_false_8
hex2int_if_true_7:
														;$t65 = mul $t2 16
	mov    r11, 16
	mov    r12, r15
	imul    r12, r11
														;$t66 = add $t65 $t3
	lea    r12, [r13 + r12]
														;$t67 = sub $t66 48
	mov    r11, 48
	mov    r15, r12
	sub    r15, r11
														;$t2 = move $t67
	mov    r15, r15
														;jump %if_merge
	jmp    hex2int_if_merge_21
hex2int_if_merge_21:
														;jump %for_loop
	jmp    hex2int_for_loop_22
hex2int_for_loop_22:
														;$t82 = move $t1
	mov    r12, r14
														;$t1 = add $t1 1
	mov    r11, 1
	lea    r14, [r11 + r14]
														;jump %for_condition
	jmp    hex2int_for_condition_2
hex2int_if_false_8:
														;$t69 = sge $t3 65
	mov    r11, 65
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t69 %logical_true %logical_false
	cmp    r12, 0
	jnz    hex2int_logical_true_9
	jz     hex2int_logical_false_10
hex2int_logical_true_9:
														;$t70 = sle $t3 70
	mov    r11, 70
	mov    r12, r13
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t68 = move $t70
	mov    r12, r12
														;jump %logical_merge
	jmp    hex2int_logical_merge_11
hex2int_logical_merge_11:
														;br $t68 %if_true %if_false
	cmp    r12, 0
	jnz    hex2int_if_true_12
	jz     hex2int_if_false_13
hex2int_if_true_12:
														;$t71 = mul $t2 16
	mov    r11, 16
	mov    r12, r15
	imul    r12, r11
														;$t72 = add $t71 $t3
	lea    r12, [r13 + r12]
														;$t73 = sub $t72 65
	mov    r11, 65
	sub    r12, r11
														;$t74 = add $t73 10
	mov    r11, 10
	lea    r12, [r11 + r12]
														;$t2 = move $t74
	mov    r15, r12
														;jump %if_merge
	jmp    hex2int_if_merge_20
hex2int_if_merge_20:
														;jump %if_merge
	jmp    hex2int_if_merge_21
hex2int_if_false_13:
														;$t76 = sge $t3 97
	mov    r11, 97
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t76 %logical_true %logical_false
	cmp    r12, 0
	jnz    hex2int_logical_true_14
	jz     hex2int_logical_false_15
hex2int_logical_true_14:
														;$t77 = sle $t3 102
	mov    r11, 102
	mov    r12, r13
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t75 = move $t77
	mov    r12, r12
														;jump %logical_merge
	jmp    hex2int_logical_merge_16
hex2int_logical_merge_16:
														;br $t75 %if_true %if_false
	cmp    r12, 0
	jnz    hex2int_if_true_17
	jz     hex2int_if_false_18
hex2int_if_true_17:
														;$t78 = mul $t2 16
	mov    r11, 16
	mov    r12, r15
	imul    r12, r11
														;$t79 = add $t78 $t3
	lea    r12, [r13 + r12]
														;$t80 = sub $t79 97
	mov    r11, 97
	sub    r12, r11
														;$t81 = add $t80 10
	mov    r11, 10
	lea    r12, [r11 + r12]
														;$t2 = move $t81
	mov    r15, r12
														;jump %if_merge
	jmp    hex2int_if_merge_19
hex2int_if_merge_19:
														;jump %if_merge
	jmp    hex2int_if_merge_20
hex2int_if_false_18:
														;ret 0
	mov    rax, 0
	jmp    hex2int_exit_24
														;jump %exit
	jmp    hex2int_exit_24
hex2int_logical_false_15:
														;$t75 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %logical_merge
	jmp    hex2int_logical_merge_16
hex2int_logical_false_10:
														;$t68 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %logical_merge
	jmp    hex2int_logical_merge_11
hex2int_logical_false_5:
														;$t62 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %logical_merge
	jmp    hex2int_logical_merge_6
hex2int_for_after_23:
														;ret $t2
	mov    rax, r15
	jmp    hex2int_exit_24
														;jump %exit
	jmp    hex2int_exit_24
hex2int_exit_24:
	mov    r13, qword [rbp + (-400)]
	mov    r12, qword [rbp + (-392)]
	mov    r15, qword [rbp + (-416)]
	mov    r14, qword [rbp + (-408)]
	leave
	ret
int2chr:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 272
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-232)], r13
	mov    qword [rbp + (-224)], r12
int2chr_enter_0:
														;jump %entry
	jmp    int2chr_entry_1
int2chr_entry_1:
														;$t84 = sge $p5 32
	mov    r10, qword [rbp+(-8)]
	mov    r11, 32
	mov    r12, r10
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t84 %logical_true %logical_false
	cmp    r12, 0
	jnz    int2chr_logical_true_2
	jz     int2chr_logical_false_3
int2chr_logical_true_2:
														;$t85 = sle $p5 126
	mov    r10, qword [rbp+(-8)]
	mov    r11, 126
	mov    r12, r10
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t83 = move $t85
	mov    r12, r12
														;jump %logical_merge
	jmp    int2chr_logical_merge_4
int2chr_logical_merge_4:
														;br $t83 %if_true %if_false
	cmp    r12, 0
	jnz    int2chr_if_true_5
	jz     int2chr_if_false_6
int2chr_if_true_5:
														;$t86 = sub $p5 32
	mov    r10, qword [rbp+(-8)]
	mov    r11, 32
	mov    r13, r10
	sub    r13, r11
														;$t87 = sub $p5 32
	mov    r10, qword [rbp+(-8)]
	mov    r11, 32
	mov    r12, r10
	sub    r12, r11
														;$t88 = call __builtin_getSubstring $g4(asciiTable) $t86 $t87
	mov    rax, qword [rel GV_asciiTable]
	mov    rdi, rax
	mov    rsi, r13
	mov    rdx, r12
	call   __builtin_getSubstring
	mov    r12, rax
														;ret $t88
	mov    rax, r12
	jmp    int2chr_exit_8
														;jump %exit
	jmp    int2chr_exit_8
int2chr_if_false_6:
														;jump %if_merge
	jmp    int2chr_if_merge_7
int2chr_if_merge_7:
														;ret $89
	mov    rax, CONST_STRING_89
	jmp    int2chr_exit_8
														;jump %exit
	jmp    int2chr_exit_8
int2chr_logical_false_3:
														;$t83 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %logical_merge
	jmp    int2chr_logical_merge_4
int2chr_exit_8:
	mov    r13, qword [rbp + (-232)]
	mov    r12, qword [rbp + (-224)]
	leave
	ret
toStringHex:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 344
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-304)], r13
	mov    qword [rbp + (-296)], r12
	mov    qword [rbp + (-320)], r15
	mov    qword [rbp + (-312)], r14
toStringHex_enter_0:
														;jump %entry
	jmp    toStringHex_entry_1
toStringHex_entry_1:
														;$t7 = move $90
	mov    rax, CONST_STRING_90
	mov    r14, rax
														;$t8 = move 28
	mov    rax, 28
	mov    r13, rax
														;jump %for_condition
	jmp    toStringHex_for_condition_2
toStringHex_for_condition_2:
														;$t91 = sge $t8 0
	mov    r11, 0
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t91 %for_body %for_after
	cmp    r12, 0
	jnz    toStringHex_for_body_3
	jz     toStringHex_for_after_8
toStringHex_for_body_3:
														;$t92 = shr $p6 $t8
	mov    r10, qword [rbp+(-8)]
	mov    r12, r10
	mov    rcx, r13
	sar    r12, cl
														;$t93 = and $t92 15
	mov    r11, 15
	and    r12, r11
														;$t9 = move $t93
	mov    r12, r12
														;$t94 = slt $t9 10
	mov    r11, 10
	mov    r15, r12
	cmp    r15, r11
	setl   al
	movzx    r15, al
														;br $t94 %if_true %if_false
	cmp    r15, 0
	jnz    toStringHex_if_true_4
	jz     toStringHex_if_false_5
toStringHex_if_true_4:
														;$t95 = add $t9 48
	mov    r11, 48
	lea    r12, [r11 + r12]
														;$t96 = call int2chr $t95
	mov    rdi, r12
	call   int2chr
	mov    r12, rax
														;$t97 = call __builtin_string_concat $t7 $t96
	mov    rdi, r14
	mov    rsi, r12
	call   __builtin_string_concat
	mov    r14, rax
														;$t7 = move $t97
	mov    r14, r14
														;jump %if_merge
	jmp    toStringHex_if_merge_6
toStringHex_if_merge_6:
														;jump %for_loop
	jmp    toStringHex_for_loop_7
toStringHex_for_loop_7:
														;$t102 = sub $t8 4
	mov    r11, 4
	mov    r12, r13
	sub    r12, r11
														;$t8 = move $t102
	mov    r13, r12
														;jump %for_condition
	jmp    toStringHex_for_condition_2
toStringHex_if_false_5:
														;$t98 = add $t9 65
	mov    r11, 65
	lea    r12, [r11 + r12]
														;$t99 = sub $t98 10
	mov    r11, 10
	sub    r12, r11
														;$t100 = call int2chr $t99
	mov    rdi, r12
	call   int2chr
	mov    r12, rax
														;$t101 = call __builtin_string_concat $t7 $t100
	mov    rdi, r14
	mov    rsi, r12
	call   __builtin_string_concat
	mov    r12, rax
														;$t7 = move $t101
	mov    r14, r12
														;jump %if_merge
	jmp    toStringHex_if_merge_6
toStringHex_for_after_8:
														;ret $t7
	mov    rax, r14
	jmp    toStringHex_exit_9
														;jump %exit
	jmp    toStringHex_exit_9
toStringHex_exit_9:
	mov    r13, qword [rbp + (-304)]
	mov    r12, qword [rbp + (-296)]
	mov    r15, qword [rbp + (-320)]
	mov    r14, qword [rbp + (-312)]
	leave
	ret
rotate_left:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 408
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp + (-368)], r13
	mov    qword [rbp + (-360)], r12
	mov    qword [rbp + (-376)], r14
rotate_left_enter_0:
														;jump %entry
	jmp    rotate_left_entry_1
rotate_left_entry_1:
														;$t103 = seq $p11 1
	mov    r10, qword [rbp+(-16)]
	mov    r11, 1
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t103 %if_true %if_false
	cmp    r12, 0
	jnz    rotate_left_if_true_2
	jz     rotate_left_if_false_3
rotate_left_if_true_2:
														;$t104 = and $p10 2147483647
	mov    r10, qword [rbp+(-8)]
	mov    r11, 2147483647
	mov    r12, r10
	and    r12, r11
														;$t105 = shl $t104 1
	mov    r11, 1
	mov    r14, r12
	mov    rcx, r11
	sal    r14, cl
														;$t106 = shr $p10 31
	mov    r10, qword [rbp+(-8)]
	mov    r11, 31
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t107 = and $t106 1
	mov    r11, 1
	mov    r13, r12
	and    r13, r11
														;$t108 = or $t105 $t107
	mov    r12, r14
	or     r12, r13
														;ret $t108
	mov    rax, r12
	jmp    rotate_left_exit_8
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_if_false_3:
														;jump %if_merge
	jmp    rotate_left_if_merge_4
rotate_left_if_merge_4:
														;$t109 = seq $p11 31
	mov    r10, qword [rbp+(-16)]
	mov    r11, 31
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t109 %if_true %if_false
	cmp    r12, 0
	jnz    rotate_left_if_true_5
	jz     rotate_left_if_false_6
rotate_left_if_true_5:
														;$t110 = and $p10 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	mov    r12, r10
	and    r12, r11
														;$t111 = shl $t110 31
	mov    r11, 31
	mov    rcx, r11
	sal    r12, cl
														;$t112 = shr $p10 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	mov    r13, r10
	mov    rcx, r11
	sar    r13, cl
														;$t113 = and $t112 2147483647
	mov    r11, 2147483647
	and    r13, r11
														;$t114 = or $t111 $t113
	or     r12, r13
														;ret $t114
	mov    rax, r12
	jmp    rotate_left_exit_8
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_if_false_6:
														;jump %if_merge
	jmp    rotate_left_if_merge_7
rotate_left_if_merge_7:
														;$t115 = sub 32 $p11
	mov    r10, 32
	mov    r11, qword [rbp+(-16)]
	mov    r13, r10
	sub    r13, r11
														;$t116 = shl 1 $t115
	mov    r10, 1
	mov    r12, r10
	mov    rcx, r13
	sal    r12, cl
														;$t117 = sub $t116 1
	mov    r11, 1
	sub    r12, r11
														;$t118 = and $p10 $t117
	mov    r10, qword [rbp+(-8)]
	mov    r13, r10
	and    r13, r12
														;$t119 = shl $t118 $p11
	mov    r11, qword [rbp+(-16)]
	mov    r12, r13
	mov    rcx, r11
	sal    r12, cl
														;$t120 = sub 32 $p11
	mov    r10, 32
	mov    r11, qword [rbp+(-16)]
	mov    r13, r10
	sub    r13, r11
														;$t121 = shr $p10 $t120
	mov    r10, qword [rbp+(-8)]
	mov    r14, r10
	mov    rcx, r13
	sar    r14, cl
														;$t122 = shl 1 $p11
	mov    r10, 1
	mov    r11, qword [rbp+(-16)]
	mov    r13, r10
	mov    rcx, r11
	sal    r13, cl
														;$t123 = sub $t122 1
	mov    r11, 1
	sub    r13, r11
														;$t124 = and $t121 $t123
	and    r14, r13
														;$t125 = or $t119 $t124
	or     r12, r14
														;ret $t125
	mov    rax, r12
	jmp    rotate_left_exit_8
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_exit_8:
	mov    r13, qword [rbp + (-368)]
	mov    r12, qword [rbp + (-360)]
	mov    r14, qword [rbp + (-376)]
	leave
	ret
add:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 352
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp + (-312)], r13
	mov    qword [rbp + (-304)], r12
	mov    qword [rbp + (-328)], r15
	mov    qword [rbp + (-320)], r14
add_enter_0:
														;jump %entry
	jmp    add_entry_1
add_entry_1:
														;$t126 = and $p12 65535
	mov    r10, qword [rbp+(-8)]
	mov    r11, 65535
	mov    r12, r10
	and    r12, r11
														;$t127 = and $p13 65535
	mov    r10, qword [rbp+(-16)]
	mov    r11, 65535
	mov    r13, r10
	and    r13, r11
														;$t128 = add $t126 $t127
	lea    r12, [r13 + r12]
														;$t14 = move $t128
	mov    r14, r12
														;$t129 = shr $p12 16
	mov    r10, qword [rbp+(-8)]
	mov    r11, 16
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t130 = and $t129 65535
	mov    r11, 65535
	mov    r13, r12
	and    r13, r11
														;$t131 = shr $p13 16
	mov    r10, qword [rbp+(-16)]
	mov    r11, 16
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t132 = and $t131 65535
	mov    r11, 65535
	mov    r15, r12
	and    r15, r11
														;$t133 = add $t130 $t132
	lea    r12, [r15 + r13]
														;$t134 = shr $t14 16
	mov    r11, 16
	mov    r13, r14
	mov    rcx, r11
	sar    r13, cl
														;$t135 = add $t133 $t134
	lea    r12, [r13 + r12]
														;$t136 = and $t135 65535
	mov    r11, 65535
	and    r12, r11
														;$t15 = move $t136
	mov    r12, r12
														;$t137 = shl $t15 16
	mov    r11, 16
	mov    r13, r12
	mov    rcx, r11
	sal    r13, cl
														;$t138 = and $t14 65535
	mov    r11, 65535
	mov    r12, r14
	and    r12, r11
														;$t139 = or $t137 $t138
	or     r13, r12
														;ret $t139
	mov    rax, r13
	jmp    add_exit_2
														;jump %exit
	jmp    add_exit_2
add_exit_2:
	mov    r13, qword [rbp + (-312)]
	mov    r12, qword [rbp + (-304)]
	mov    r15, qword [rbp + (-328)]
	mov    r14, qword [rbp + (-320)]
	leave
	ret
lohi:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 240
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp + (-200)], r13
	mov    qword [rbp + (-192)], r12
lohi_enter_0:
														;jump %entry
	jmp    lohi_entry_1
lohi_entry_1:
														;$t140 = shl $p17 16
	mov    r10, qword [rbp+(-16)]
	mov    r11, 16
	mov    r12, r10
	mov    rcx, r11
	sal    r12, cl
														;$t141 = or $p16 $t140
	mov    r10, qword [rbp+(-8)]
	mov    r13, r10
	or     r13, r12
														;ret $t141
	mov    rax, r13
	jmp    lohi_exit_2
														;jump %exit
	jmp    lohi_exit_2
lohi_exit_2:
	mov    r13, qword [rbp + (-200)]
	mov    r12, qword [rbp + (-192)]
	leave
	ret
sha1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 1704
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp + (-1664)], r13
	mov    qword [rbp + (-1656)], r12
	mov    qword [rbp + (-1584)], rbx
	mov    qword [rbp + (-1680)], r15
	mov    qword [rbp + (-1672)], r14
sha1_enter_0:
														;jump %entry
	jmp    sha1_entry_1
sha1_entry_1:
														;$t142 = add $p24 64
	mov    r10, qword [rbp+(-16)]
	mov    r11, 64
	lea    r12, [r11 + r10]
														;$t143 = sub $t142 56
	mov    r11, 56
	sub    r12, r11
														;$t144 = div $t143 64
	mov    r11, 64
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t145 = add $t144 1
	mov    r11, 1
	lea    r15, [r11 + r12]
														;$t25 = move $t145
	mov    r15, r15
														;$t146 = sgt $t25 $g18(MAXCHUNK)
	mov    r11, qword [rel GV_MAXCHUNK]
	mov    r12, r15
	cmp    r12, r11
	setg   al
	movzx    r12, al
														;br $t146 %if_true %if_false
	cmp    r12, 0
	jnz    sha1_if_true_2
	jz     sha1_if_false_3
sha1_if_true_2:
														;call __builtin_println $147
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, CONST_STRING_147
	mov    rdi, rax
	call   __builtin_println
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
														;ret 0
	mov    rax, 0
	jmp    sha1_exit_38
														;jump %exit
	jmp    sha1_exit_38
sha1_if_false_3:
														;jump %if_merge
	jmp    sha1_if_merge_4
sha1_if_merge_4:
														;$t26 = move 0
	mov    rax, 0
	mov    qword [rbp+(-816)], rax
														;jump %for_condition
	jmp    sha1_for_condition_5
sha1_for_condition_5:
														;$t148 = slt $t26 $t25
	mov    r10, qword [rbp+(-816)]
	mov    r12, r10
	cmp    r12, r15
	setl   al
	movzx    r12, al
														;br $t148 %for_body %for_after
	cmp    r12, 0
	jnz    sha1_for_body_6
	jz     sha1_for_after_12
sha1_for_body_6:
														;$t27 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %for_condition
	jmp    sha1_for_condition_7
sha1_for_condition_7:
														;$t149 = slt $t27 80
	mov    r11, 80
	mov    r12, rbx
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t149 %for_body %for_after
	cmp    r12, 0
	jnz    sha1_for_body_8
	jz     sha1_for_after_10
sha1_for_body_8:
														;$t151 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t150 = add $g20(chunks) $t151
	mov    r10, qword [rel GV_chunks]
	lea    r13, [r12 + r10]
														;$t152 = load 8 $t150 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t154 = mul $t27 8
	mov    r11, 8
	mov    r12, rbx
	imul    r12, r11
														;$t153 = add $t152 $t154
	lea    r13, [r12 + r13]
														;store 8 $t153 0 0
	mov    rax, 0
	mov    r11, r13
	mov    qword [r11], rax
														;jump %for_loop
	jmp    sha1_for_loop_9
sha1_for_loop_9:
														;$t155 = move $t27
	mov    r12, rbx
														;$t27 = add $t27 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %for_condition
	jmp    sha1_for_condition_7
sha1_for_after_10:
														;jump %for_loop
	jmp    sha1_for_loop_11
sha1_for_loop_11:
														;$t156 = move $t26
	mov    rax, qword [rbp+(-816)]
	mov    r12, rax
														;$t26 = add $t26 1
	mov    r10, qword [rbp+(-816)]
	mov    r11, 1
	lea    rax, [r11 + r10]
	mov    qword [rbp+(-816)], rax
														;jump %for_condition
	jmp    sha1_for_condition_5
sha1_for_after_12:
														;$t26 = move 0
	mov    rax, 0
	mov    qword [rbp+(-816)], rax
														;jump %for_condition
	jmp    sha1_for_condition_13
sha1_for_condition_13:
														;$t157 = slt $t26 $p24
	mov    r10, qword [rbp+(-816)]
	mov    r11, qword [rbp+(-16)]
	mov    r12, r10
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t157 %for_body %for_after
	cmp    r12, 0
	jnz    sha1_for_body_14
	jz     sha1_for_after_16
sha1_for_body_14:
														;$t158 = div $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t160 = mul $t158 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t159 = add $g20(chunks) $t160
	mov    r10, qword [rel GV_chunks]
	lea    r12, [r13 + r10]
														;$t161 = load 8 $t159 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t162 = rem $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t163 = div $t162 4
	mov    r11, 4
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t165 = mul $t163 8
	mov    r11, 8
	imul    r12, r11
														;$t164 = add $t161 $t165
	lea    r14, [r12 + r13]
														;$t166 = div $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t168 = mul $t166 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t167 = add $g20(chunks) $t168
	mov    r10, qword [rel GV_chunks]
	lea    r12, [r13 + r10]
														;$t169 = load 8 $t167 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t170 = rem $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r13, r10
	mov    rax, r13
	cqo
	idiv   r11
	mov    r13, rdx
														;$t171 = div $t170 4
	mov    r11, 4
	mov    rax, r13
	cqo
	idiv   r11
	mov    r13, rax
														;$t173 = mul $t171 8
	mov    r11, 8
	imul    r13, r11
														;$t172 = add $t169 $t173
	lea    r12, [r13 + r12]
														;$t174 = load 8 $t172 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t176 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t175 = add $p23 $t176
	mov    r10, qword [rbp+(-8)]
	lea    rbx, [r13 + r10]
														;$t177 = load 8 $t175 0
	mov    r11, rbx
	mov    r13, qword [r11]
														;$t178 = rem $t26 4
	mov    r10, qword [rbp+(-816)]
	mov    r11, 4
	mov    rbx, r10
	mov    rax, rbx
	cqo
	idiv   r11
	mov    rbx, rdx
														;$t179 = sub 3 $t178
	mov    r10, 3
	mov    rsi, r10
	sub    rsi, rbx
														;$t180 = mul $t179 8
	mov    r11, 8
	mov    rbx, rsi
	imul    rbx, r11
														;$t181 = shl $t177 $t180
	mov    rcx, rbx
	sal    r13, cl
														;$t182 = or $t174 $t181
	or     r12, r13
														;store 8 $t164 $t182 0
	mov    r11, r14
	mov    qword [r11], r12
														;jump %for_loop
	jmp    sha1_for_loop_15
sha1_for_loop_15:
														;$t183 = move $t26
	mov    rax, qword [rbp+(-816)]
	mov    r12, rax
														;$t26 = add $t26 1
	mov    r10, qword [rbp+(-816)]
	mov    r11, 1
	lea    rax, [r11 + r10]
	mov    qword [rbp+(-816)], rax
														;jump %for_condition
	jmp    sha1_for_condition_13
sha1_for_after_16:
														;$t184 = div $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t186 = mul $t184 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t185 = add $g20(chunks) $t186
	mov    r10, qword [rel GV_chunks]
	lea    r12, [r13 + r10]
														;$t187 = load 8 $t185 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t188 = rem $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t189 = div $t188 4
	mov    r11, 4
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t191 = mul $t189 8
	mov    r11, 8
	imul    r12, r11
														;$t190 = add $t187 $t191
	lea    r14, [r12 + r13]
														;$t192 = div $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t194 = mul $t192 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t193 = add $g20(chunks) $t194
	mov    r10, qword [rel GV_chunks]
	lea    r12, [r13 + r10]
														;$t195 = load 8 $t193 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t196 = rem $t26 64
	mov    r10, qword [rbp+(-816)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t197 = div $t196 4
	mov    r11, 4
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t199 = mul $t197 8
	mov    r11, 8
	imul    r12, r11
														;$t198 = add $t195 $t199
	lea    r13, [r12 + r13]
														;$t200 = load 8 $t198 0
	mov    r11, r13
	mov    rbx, qword [r11]
														;$t201 = rem $t26 4
	mov    r10, qword [rbp+(-816)]
	mov    r11, 4
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t202 = sub 3 $t201
	mov    r10, 3
	mov    r13, r10
	sub    r13, r12
														;$t203 = mul $t202 8
	mov    r11, 8
	imul    r13, r11
														;$t204 = shl 128 $t203
	mov    r10, 128
	mov    r12, r10
	mov    rcx, r13
	sal    r12, cl
														;$t205 = or $t200 $t204
	mov    r13, rbx
	or     r13, r12
														;store 8 $t190 $t205 0
	mov    r11, r14
	mov    qword [r11], r13
														;$t206 = sub $t25 1
	mov    r11, 1
	mov    r12, r15
	sub    r12, r11
														;$t208 = mul $t206 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t207 = add $g20(chunks) $t208
	mov    r10, qword [rel GV_chunks]
	lea    r12, [r13 + r10]
														;$t209 = load 8 $t207 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t211 = move 120
	mov    rax, 120
	mov    r12, rax
														;$t210 = add $t209 $t211
	lea    r13, [r12 + r13]
														;$t212 = shl $p24 3
	mov    r10, qword [rbp+(-16)]
	mov    r11, 3
	mov    r12, r10
	mov    rcx, r11
	sal    r12, cl
														;store 8 $t210 $t212 0
	mov    r11, r13
	mov    qword [r11], r12
														;$t213 = sub $t25 1
	mov    r11, 1
	mov    r12, r15
	sub    r12, r11
														;$t215 = mul $t213 8
	mov    r11, 8
	imul    r12, r11
														;$t214 = add $g20(chunks) $t215
	mov    r10, qword [rel GV_chunks]
	lea    r13, [r12 + r10]
														;$t216 = load 8 $t214 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t218 = move 112
	mov    rax, 112
	mov    r14, rax
														;$t217 = add $t216 $t218
	lea    r13, [r14 + r12]
														;$t219 = shr $p24 29
	mov    r10, qword [rbp+(-16)]
	mov    r11, 29
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t220 = and $t219 7
	mov    r11, 7
	and    r12, r11
														;store 8 $t217 $t220 0
	mov    r11, r13
	mov    qword [r11], r12
														;$t28 = move 1732584193
	mov    rax, 1732584193
	mov    qword [rbp+(-728)], rax
														;$t221 = call lohi 43913 61389
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, 43913
	mov    rdi, rax
	mov    rax, 61389
	mov    rsi, rax
	call   lohi
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r13, rax
														;$t29 = move $t221
	mov    r13, r13
														;$t222 = call lohi 56574 39098
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, 56574
	mov    rdi, rax
	mov    rax, 39098
	mov    rsi, rax
	call   lohi
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r12, rax
														;$t30 = move $t222
	mov    qword [rbp+(-1496)], r12
														;$t31 = move 271733878
	mov    rax, 271733878
	mov    r14, rax
														;$t223 = call lohi 57840 50130
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, 57840
	mov    rdi, rax
	mov    rax, 50130
	mov    rsi, rax
	call   lohi
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r12, rax
														;$t32 = move $t223
	mov    r12, r12
														;$t26 = move 0
	mov    rax, 0
	mov    qword [rbp+(-816)], rax
														;jump %for_condition
	jmp    sha1_for_condition_17
sha1_for_condition_17:
														;$t224 = slt $t26 $t25
	mov    r10, qword [rbp+(-816)]
	mov    rbx, r10
	cmp    rbx, r15
	setl   al
	movzx    rbx, al
														;br $t224 %for_body %for_after
	cmp    rbx, 0
	jnz    sha1_for_body_18
	jz     sha1_for_after_37
sha1_for_body_18:
														;$t27 = move 16
	mov    rax, 16
	mov    rbx, rax
														;jump %for_condition
	jmp    sha1_for_condition_19
sha1_for_condition_19:
														;$t225 = slt $t27 80
	mov    r11, 80
	mov    rsi, rbx
	cmp    rsi, r11
	setl   al
	movzx    rsi, al
														;br $t225 %for_body %for_after
	cmp    rsi, 0
	jnz    sha1_for_body_20
	jz     sha1_for_after_22
sha1_for_body_20:
														;$t227 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    rdi, r10
	imul    rdi, r11
														;$t226 = add $g20(chunks) $t227
	mov    r10, qword [rel GV_chunks]
	lea    rsi, [rdi + r10]
														;$t228 = load 8 $t226 0
	mov    r11, rsi
	mov    rdi, qword [r11]
														;$t230 = mul $t27 8
	mov    r11, 8
	mov    rsi, rbx
	imul    rsi, r11
														;$t229 = add $t228 $t230
	lea    rdi, [rsi + rdi]
														;$t232 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    r8, r10
	imul    r8, r11
														;$t231 = add $g20(chunks) $t232
	mov    r10, qword [rel GV_chunks]
	lea    rsi, [r8 + r10]
														;$t233 = load 8 $t231 0
	mov    r11, rsi
	mov    rsi, qword [r11]
														;$t234 = sub $t27 3
	mov    r11, 3
	mov    r8, rbx
	sub    r8, r11
														;$t236 = mul $t234 8
	mov    r11, 8
	imul    r8, r11
														;$t235 = add $t233 $t236
	lea    rsi, [r8 + rsi]
														;$t237 = load 8 $t235 0
	mov    r11, rsi
	mov    r8, qword [r11]
														;$t239 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    r9, r10
	imul    r9, r11
														;$t238 = add $g20(chunks) $t239
	mov    r10, qword [rel GV_chunks]
	lea    rsi, [r9 + r10]
														;$t240 = load 8 $t238 0
	mov    r11, rsi
	mov    rsi, qword [r11]
														;$t241 = sub $t27 8
	mov    r11, 8
	mov    r9, rbx
	sub    r9, r11
														;$t243 = mul $t241 8
	mov    r11, 8
	imul    r9, r11
														;$t242 = add $t240 $t243
	lea    rsi, [r9 + rsi]
														;$t244 = load 8 $t242 0
	mov    r11, rsi
	mov    rsi, qword [r11]
														;$t245 = xor $t237 $t244
	xor     r8, rsi
														;$t247 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    rsi, r10
	imul    rsi, r11
														;$t246 = add $g20(chunks) $t247
	mov    r10, qword [rel GV_chunks]
	lea    r9, [rsi + r10]
														;$t248 = load 8 $t246 0
	mov    r11, r9
	mov    rsi, qword [r11]
														;$t249 = sub $t27 14
	mov    r11, 14
	mov    r9, rbx
	sub    r9, r11
														;$t251 = mul $t249 8
	mov    r11, 8
	imul    r9, r11
														;$t250 = add $t248 $t251
	lea    rsi, [r9 + rsi]
														;$t252 = load 8 $t250 0
	mov    r11, rsi
	mov    rsi, qword [r11]
														;$t253 = xor $t245 $t252
	xor     r8, rsi
														;$t255 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    rsi, r10
	imul    rsi, r11
														;$t254 = add $g20(chunks) $t255
	mov    r10, qword [rel GV_chunks]
	lea    r9, [rsi + r10]
														;$t256 = load 8 $t254 0
	mov    r11, r9
	mov    rsi, qword [r11]
														;$t257 = sub $t27 16
	mov    r11, 16
	mov    r9, rbx
	sub    r9, r11
														;$t259 = mul $t257 8
	mov    r11, 8
	imul    r9, r11
														;$t258 = add $t256 $t259
	lea    rsi, [r9 + rsi]
														;$t260 = load 8 $t258 0
	mov    r11, rsi
	mov    rsi, qword [r11]
														;$t261 = xor $t253 $t260
	xor     r8, rsi
														;$t262 = call rotate_left $t261 1
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, qword[rbp + (-1624)]
	mov    rax, 1
	mov    rsi, rax
	call   rotate_left
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    rsi, rax
														;store 8 $t229 $t262 0
	mov    r11, rdi
	mov    qword [r11], rsi
														;jump %for_loop
	jmp    sha1_for_loop_21
sha1_for_loop_21:
														;$t263 = move $t27
	mov    rsi, rbx
														;$t27 = add $t27 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %for_condition
	jmp    sha1_for_condition_19
sha1_for_after_22:
														;$t33 = move $t28
	mov    rax, qword [rbp+(-728)]
	mov    qword [rbp+(-1120)], rax
														;$t34 = move $t29
	mov    qword [rbp+(-1224)], r13
														;$t35 = move $t30
	mov    rax, qword [rbp+(-1496)]
	mov    rsi, rax
														;$t36 = move $t31
	mov    qword [rbp+(-400)], r14
														;$t37 = move $t32
	mov    r8, r12
														;$t27 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %for_condition
	jmp    sha1_for_condition_23
sha1_for_condition_23:
														;$t264 = slt $t27 80
	mov    r11, 80
	mov    rdi, rbx
	cmp    rdi, r11
	setl   al
	movzx    rdi, al
														;br $t264 %for_body %for_after
	cmp    rdi, 0
	jnz    sha1_for_body_24
	jz     sha1_for_after_35
sha1_for_body_24:
														;$t265 = slt $t27 20
	mov    r11, 20
	mov    rdi, rbx
	cmp    rdi, r11
	setl   al
	movzx    rdi, al
														;br $t265 %if_true %if_false
	cmp    rdi, 0
	jnz    sha1_if_true_25
	jz     sha1_if_false_26
sha1_if_true_25:
														;$t266 = and $t34 $t35
	mov    r10, qword [rbp+(-1224)]
	mov    r9, r10
	and    r9, rsi
														;$t267 = not $t34
	mov    rax, qword [rbp+(-1224)]
	mov    rdi, rax
	not    rdi
														;$t268 = and $t267 $t36
	mov    r11, qword [rbp+(-400)]
	and    rdi, r11
														;$t269 = or $t266 $t268
	or     r9, rdi
														;$t38 = move $t269
	mov    rdi, r9
														;$t39 = move 1518500249
	mov    rax, 1518500249
	mov    qword [rbp+(-96)], rax
														;jump %if_merge
	jmp    sha1_if_merge_33
sha1_if_merge_33:
														;$t283 = call rotate_left $t33 5
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, qword [rbp+(-1120)]
	mov    rdi, rax
	mov    rax, 5
	mov    rsi, rax
	call   rotate_left
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r9, rax
														;$t284 = call add $t283 $t37
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, qword[rbp + (-1632)]
	mov    rsi, qword[rbp + (-1624)]
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r8, rax
														;$t285 = call add $t38 $t39
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, qword[rbp + (-1616)]
	mov    rax, qword [rbp+(-96)]
	mov    rsi, rax
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    rdi, rax
														;$t286 = call add $t284 $t285
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, qword[rbp + (-1624)]
	mov    rsi, qword[rbp + (-1616)]
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r9, rax
														;$t288 = mul $t26 8
	mov    r10, qword [rbp+(-816)]
	mov    r11, 8
	mov    rdi, r10
	imul    rdi, r11
														;$t287 = add $g20(chunks) $t288
	mov    r10, qword [rel GV_chunks]
	lea    r8, [rdi + r10]
														;$t289 = load 8 $t287 0
	mov    r11, r8
	mov    r8, qword [r11]
														;$t291 = mul $t27 8
	mov    r11, 8
	mov    rdi, rbx
	imul    rdi, r11
														;$t290 = add $t289 $t291
	lea    r8, [rdi + r8]
														;$t292 = load 8 $t290 0
	mov    r11, r8
	mov    rdi, qword [r11]
														;$t293 = call add $t286 $t292
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, qword[rbp + (-1632)]
	mov    rsi, qword[rbp + (-1616)]
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    rdi, rax
														;$t40 = move $t293
	mov    rdi, rdi
														;$t37 = move $t36
	mov    rax, qword [rbp+(-400)]
	mov    r8, rax
														;$t36 = move $t35
	mov    qword [rbp+(-400)], rsi
														;$t294 = call rotate_left $t34 30
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, qword [rbp+(-1224)]
	mov    rdi, rax
	mov    rax, 30
	mov    rsi, rax
	call   rotate_left
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    rsi, rax
														;$t35 = move $t294
	mov    rsi, rsi
														;$t34 = move $t33
	mov    rax, qword [rbp+(-1120)]
	mov    qword [rbp+(-1224)], rax
														;$t33 = move $t40
	mov    qword [rbp+(-1120)], rdi
														;jump %for_loop
	jmp    sha1_for_loop_34
sha1_for_loop_34:
														;$t295 = move $t27
	mov    rdi, rbx
														;$t27 = add $t27 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %for_condition
	jmp    sha1_for_condition_23
sha1_if_false_26:
														;$t270 = slt $t27 40
	mov    r11, 40
	mov    rdi, rbx
	cmp    rdi, r11
	setl   al
	movzx    rdi, al
														;br $t270 %if_true %if_false
	cmp    rdi, 0
	jnz    sha1_if_true_27
	jz     sha1_if_false_28
sha1_if_true_27:
														;$t271 = xor $t34 $t35
	mov    r10, qword [rbp+(-1224)]
	mov    rdi, r10
	xor     rdi, rsi
														;$t272 = xor $t271 $t36
	mov    r11, qword [rbp+(-400)]
	xor     rdi, r11
														;$t38 = move $t272
	mov    rdi, rdi
														;$t39 = move 1859775393
	mov    rax, 1859775393
	mov    qword [rbp+(-96)], rax
														;jump %if_merge
	jmp    sha1_if_merge_32
sha1_if_merge_32:
														;jump %if_merge
	jmp    sha1_if_merge_33
sha1_if_false_28:
														;$t273 = slt $t27 60
	mov    r11, 60
	mov    rdi, rbx
	cmp    rdi, r11
	setl   al
	movzx    rdi, al
														;br $t273 %if_true %if_false
	cmp    rdi, 0
	jnz    sha1_if_true_29
	jz     sha1_if_false_30
sha1_if_true_29:
														;$t274 = and $t34 $t35
	mov    r10, qword [rbp+(-1224)]
	mov    rdi, r10
	and    rdi, rsi
														;$t275 = and $t34 $t36
	mov    r10, qword [rbp+(-1224)]
	mov    r11, qword [rbp+(-400)]
	mov    r9, r10
	and    r9, r11
														;$t276 = or $t274 $t275
	or     rdi, r9
														;$t277 = and $t35 $t36
	mov    r11, qword [rbp+(-400)]
	mov    r9, rsi
	and    r9, r11
														;$t278 = or $t276 $t277
	or     rdi, r9
														;$t38 = move $t278
	mov    rdi, rdi
														;$t279 = call lohi 48348 36635
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, 48348
	mov    rdi, rax
	mov    rax, 36635
	mov    rsi, rax
	call   lohi
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r9, rax
														;$t39 = move $t279
	mov    qword [rbp+(-96)], r9
														;jump %if_merge
	jmp    sha1_if_merge_31
sha1_if_merge_31:
														;jump %if_merge
	jmp    sha1_if_merge_32
sha1_if_false_30:
														;$t280 = xor $t34 $t35
	mov    r10, qword [rbp+(-1224)]
	mov    rdi, r10
	xor     rdi, rsi
														;$t281 = xor $t280 $t36
	mov    r11, qword [rbp+(-400)]
	xor     rdi, r11
														;$t38 = move $t281
	mov    rdi, rdi
														;$t282 = call lohi 49622 51810
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, 49622
	mov    rdi, rax
	mov    rax, 51810
	mov    rsi, rax
	call   lohi
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r9, rax
														;$t39 = move $t282
	mov    qword [rbp+(-96)], r9
														;jump %if_merge
	jmp    sha1_if_merge_31
sha1_for_after_35:
														;$t296 = call add $t28 $t33
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, qword [rbp+(-728)]
	mov    rdi, rax
	mov    rax, qword [rbp+(-1120)]
	mov    rsi, rax
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    rbx, rax
														;$t28 = move $t296
	mov    qword [rbp+(-728)], rbx
														;$t297 = call add $t29 $t34
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, r13
	mov    rax, qword [rbp+(-1224)]
	mov    rsi, rax
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r13, rax
														;$t29 = move $t297
	mov    r13, r13
														;$t298 = call add $t30 $t35
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rax, qword [rbp+(-1496)]
	mov    rdi, rax
	mov    rsi, qword[rbp + (-1608)]
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    rbx, rax
														;$t30 = move $t298
	mov    qword [rbp+(-1496)], rbx
														;$t299 = call add $t31 $t36
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, r14
	mov    rax, qword [rbp+(-400)]
	mov    rsi, rax
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r14, rax
														;$t31 = move $t299
	mov    r14, r14
														;$t300 = call add $t32 $t37
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1608)], rsi
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1632)], r9
	mov    rdi, r12
	mov    rsi, qword[rbp + (-1624)]
	call   add
	mov    r8, qword [rbp + (-1624)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, qword [rbp + (-1616)]
	mov    r9, qword [rbp + (-1632)]
	mov    r12, rax
														;$t32 = move $t300
	mov    r12, r12
														;jump %for_loop
	jmp    sha1_for_loop_36
sha1_for_loop_36:
														;$t301 = move $t26
	mov    rax, qword [rbp+(-816)]
	mov    rbx, rax
														;$t26 = add $t26 1
	mov    r10, qword [rbp+(-816)]
	mov    r11, 1
	lea    rax, [r11 + r10]
	mov    qword [rbp+(-816)], rax
														;jump %for_condition
	jmp    sha1_for_condition_17
sha1_for_after_37:
														;$t303 = move 0
	mov    rax, 0
	mov    rbx, rax
														;$t302 = add $g22(outputBuffer) $t303
	mov    r10, qword [rel GV_outputBuffer]
	lea    r15, [rbx + r10]
														;store 8 $t302 $t28 0
	mov    rax, qword [rbp+(-728)]
	mov    r11, r15
	mov    qword [r11], rax
														;$t305 = move 8
	mov    rax, 8
	mov    rbx, rax
														;$t304 = add $g22(outputBuffer) $t305
	mov    r10, qword [rel GV_outputBuffer]
	lea    r15, [rbx + r10]
														;store 8 $t304 $t29 0
	mov    r11, r15
	mov    qword [r11], r13
														;$t307 = move 16
	mov    rax, 16
	mov    r15, rax
														;$t306 = add $g22(outputBuffer) $t307
	mov    r10, qword [rel GV_outputBuffer]
	lea    r13, [r15 + r10]
														;store 8 $t306 $t30 0
	mov    rax, qword [rbp+(-1496)]
	mov    r11, r13
	mov    qword [r11], rax
														;$t309 = move 24
	mov    rax, 24
	mov    r15, rax
														;$t308 = add $g22(outputBuffer) $t309
	mov    r10, qword [rel GV_outputBuffer]
	lea    r13, [r15 + r10]
														;store 8 $t308 $t31 0
	mov    r11, r13
	mov    qword [r11], r14
														;$t311 = move 32
	mov    rax, 32
	mov    r13, rax
														;$t310 = add $g22(outputBuffer) $t311
	mov    r10, qword [rel GV_outputBuffer]
	lea    r14, [r13 + r10]
														;store 8 $t310 $t32 0
	mov    r11, r14
	mov    qword [r11], r12
														;ret $g22(outputBuffer)
	mov    rax, qword [rel GV_outputBuffer]
	jmp    sha1_exit_38
														;jump %exit
	jmp    sha1_exit_38
sha1_exit_38:
	mov    r13, qword [rbp + (-1664)]
	mov    r12, qword [rbp + (-1656)]
	mov    rbx, qword [rbp + (-1584)]
	mov    r15, qword [rbp + (-1680)]
	mov    r14, qword [rbp + (-1672)]
	leave
	ret
computeSHA1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 360
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-320)], r13
	mov    qword [rbp + (-312)], r12
	mov    qword [rbp + (-336)], r15
	mov    qword [rbp + (-328)], r14
computeSHA1_enter_0:
														;jump %entry
	jmp    computeSHA1_entry_1
computeSHA1_entry_1:
														;$t42 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    computeSHA1_for_condition_2
computeSHA1_for_condition_2:
														;$t312 = call __builtin_getStringLength $p41
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    r13, rax
														;$t313 = slt $t42 $t312
	mov    r12, r15
	cmp    r12, r13
	setl   al
	movzx    r12, al
														;br $t313 %for_body %for_after
	cmp    r12, 0
	jnz    computeSHA1_for_body_3
	jz     computeSHA1_for_after_5
computeSHA1_for_body_3:
														;$t315 = mul $t42 8
	mov    r11, 8
	mov    r13, r15
	imul    r13, r11
														;$t314 = add $g21(inputBuffer) $t315
	mov    r10, qword [rel GV_inputBuffer]
	lea    r12, [r13 + r10]
														;$t316 = call __builtin_ord $p41 $t42
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rsi, r15
	call   __builtin_ord
	mov    r13, rax
														;store 8 $t314 $t316 0
	mov    r11, r12
	mov    qword [r11], r13
														;jump %for_loop
	jmp    computeSHA1_for_loop_4
computeSHA1_for_loop_4:
														;$t317 = move $t42
	mov    r12, r15
														;$t42 = add $t42 1
	mov    r11, 1
	lea    r15, [r11 + r15]
														;jump %for_condition
	jmp    computeSHA1_for_condition_2
computeSHA1_for_after_5:
														;$t318 = call __builtin_getStringLength $p41
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    r12, rax
														;$t319 = call sha1 $g21(inputBuffer) $t318
	mov    rax, qword [rel GV_inputBuffer]
	mov    rdi, rax
	mov    rsi, r12
	call   sha1
	mov    r14, rax
														;$t43 = move $t319
	mov    r14, r14
														;$t42 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    computeSHA1_for_condition_6
computeSHA1_for_condition_6:
														;$t320 = call __builtin_getArraySize $t43
	mov    rdi, r14
	call   __builtin_getArraySize
	mov    r13, rax
														;$t321 = slt $t42 $t320
	mov    r12, r15
	cmp    r12, r13
	setl   al
	movzx    r12, al
														;br $t321 %for_body %for_after
	cmp    r12, 0
	jnz    computeSHA1_for_body_7
	jz     computeSHA1_for_after_9
computeSHA1_for_body_7:
														;$t323 = mul $t42 8
	mov    r11, 8
	mov    r13, r15
	imul    r13, r11
														;$t322 = add $t43 $t323
	lea    r12, [r13 + r14]
														;$t324 = load 8 $t322 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t325 = call toStringHex $t324
	mov    rdi, r12
	call   toStringHex
	mov    r12, rax
														;call __builtin_print $t325
	mov    rdi, r12
	call   __builtin_print
														;jump %for_loop
	jmp    computeSHA1_for_loop_8
computeSHA1_for_loop_8:
														;$t326 = move $t42
	mov    r12, r15
														;$t42 = add $t42 1
	mov    r11, 1
	lea    r15, [r11 + r15]
														;jump %for_condition
	jmp    computeSHA1_for_condition_6
computeSHA1_for_after_9:
														;call __builtin_println $327
	mov    rax, CONST_STRING_327
	mov    rdi, rax
	call   __builtin_println
														;jump %exit
	jmp    computeSHA1_exit_10
computeSHA1_exit_10:
	mov    r13, qword [rbp + (-320)]
	mov    r12, qword [rbp + (-312)]
	mov    r15, qword [rbp + (-336)]
	mov    r14, qword [rbp + (-328)]
	leave
	ret
nextLetter:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 256
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-208)], r12
nextLetter_enter_0:
														;jump %entry
	jmp    nextLetter_entry_1
nextLetter_entry_1:
														;$t328 = seq $p44 122
	mov    r10, qword [rbp+(-8)]
	mov    r11, 122
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t328 %if_true %if_false
	cmp    r12, 0
	jnz    nextLetter_if_true_2
	jz     nextLetter_if_false_3
nextLetter_if_true_2:
														;ret -1
	mov    rax, -1
	jmp    nextLetter_exit_11
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_if_false_3:
														;jump %if_merge
	jmp    nextLetter_if_merge_4
nextLetter_if_merge_4:
														;$t329 = seq $p44 90
	mov    r10, qword [rbp+(-8)]
	mov    r11, 90
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t329 %if_true %if_false
	cmp    r12, 0
	jnz    nextLetter_if_true_5
	jz     nextLetter_if_false_6
nextLetter_if_true_5:
														;ret 97
	mov    rax, 97
	jmp    nextLetter_exit_11
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_if_false_6:
														;jump %if_merge
	jmp    nextLetter_if_merge_7
nextLetter_if_merge_7:
														;$t330 = seq $p44 57
	mov    r10, qword [rbp+(-8)]
	mov    r11, 57
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t330 %if_true %if_false
	cmp    r12, 0
	jnz    nextLetter_if_true_8
	jz     nextLetter_if_false_9
nextLetter_if_true_8:
														;ret 65
	mov    rax, 65
	jmp    nextLetter_exit_11
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_if_false_9:
														;jump %if_merge
	jmp    nextLetter_if_merge_10
nextLetter_if_merge_10:
														;$t331 = add $p44 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	lea    r12, [r11 + r10]
														;ret $t331
	mov    rax, r12
	jmp    nextLetter_exit_11
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_exit_11:
	mov    r12, qword [rbp + (-208)]
	leave
	ret
nextText:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 352
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp + (-312)], r13
	mov    qword [rbp + (-304)], r12
	mov    qword [rbp + (-328)], r15
	mov    qword [rbp + (-320)], r14
nextText_enter_0:
														;jump %entry
	jmp    nextText_entry_1
nextText_entry_1:
														;$t332 = sub $p46 1
	mov    r10, qword [rbp+(-16)]
	mov    r11, 1
	mov    r12, r10
	sub    r12, r11
														;$t47 = move $t332
	mov    r14, r12
														;jump %for_condition
	jmp    nextText_for_condition_2
nextText_for_condition_2:
														;$t333 = sge $t47 0
	mov    r11, 0
	mov    r12, r14
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t333 %for_body %for_after
	cmp    r12, 0
	jnz    nextText_for_body_3
	jz     nextText_for_after_8
nextText_for_body_3:
														;$t335 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t334 = add $p45 $t335
	mov    r10, qword [rbp+(-8)]
	lea    r15, [r12 + r10]
														;$t337 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t336 = add $p45 $t337
	mov    r10, qword [rbp+(-8)]
	lea    r13, [r12 + r10]
														;$t338 = load 8 $t336 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t339 = call nextLetter $t338
	mov    rdi, r12
	call   nextLetter
	mov    r12, rax
														;store 8 $t334 $t339 0
	mov    r11, r15
	mov    qword [r11], r12
														;$t341 = mul $t47 8
	mov    r11, 8
	mov    r13, r14
	imul    r13, r11
														;$t340 = add $p45 $t341
	mov    r10, qword [rbp+(-8)]
	lea    r12, [r13 + r10]
														;$t342 = load 8 $t340 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t343 = seq $t342 -1
	mov    r11, -1
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t343 %if_true %if_false
	cmp    r12, 0
	jnz    nextText_if_true_4
	jz     nextText_if_false_5
nextText_if_true_4:
														;$t345 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t344 = add $p45 $t345
	mov    r10, qword [rbp+(-8)]
	lea    r13, [r12 + r10]
														;store 8 $t344 48 0
	mov    rax, 48
	mov    r11, r13
	mov    qword [r11], rax
														;jump %if_merge
	jmp    nextText_if_merge_6
nextText_if_merge_6:
														;jump %for_loop
	jmp    nextText_for_loop_7
nextText_for_loop_7:
														;$t346 = move $t47
	mov    r12, r14
														;$t47 = sub $t47 1
	mov    r11, 1
	sub    r14, r11
														;jump %for_condition
	jmp    nextText_for_condition_2
nextText_if_false_5:
														;ret 1
	mov    rax, 1
	jmp    nextText_exit_9
														;jump %exit
	jmp    nextText_exit_9
nextText_for_after_8:
														;ret 0
	mov    rax, 0
	jmp    nextText_exit_9
														;jump %exit
	jmp    nextText_exit_9
nextText_exit_9:
	mov    r13, qword [rbp + (-312)]
	mov    r12, qword [rbp + (-304)]
	mov    r15, qword [rbp + (-328)]
	mov    r14, qword [rbp + (-320)]
	leave
	ret
array_equal:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 336
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp + (-296)], r13
	mov    qword [rbp + (-288)], r12
	mov    qword [rbp + (-312)], r15
	mov    qword [rbp + (-304)], r14
array_equal_enter_0:
														;jump %entry
	jmp    array_equal_entry_1
array_equal_entry_1:
														;$t347 = call __builtin_getArraySize $p48
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getArraySize
	mov    r13, rax
														;$t348 = call __builtin_getArraySize $p49
	mov    rax, qword [rbp+(-16)]
	mov    rdi, rax
	call   __builtin_getArraySize
	mov    r12, rax
														;$t349 = sne $t347 $t348
	cmp    r13, r12
	setne   al
	movzx    r13, al
														;br $t349 %if_true %if_false
	cmp    r13, 0
	jnz    array_equal_if_true_2
	jz     array_equal_if_false_3
array_equal_if_true_2:
														;ret 0
	mov    rax, 0
	jmp    array_equal_exit_12
														;jump %exit
	jmp    array_equal_exit_12
array_equal_if_false_3:
														;jump %if_merge
	jmp    array_equal_if_merge_4
array_equal_if_merge_4:
														;$t50 = move 0
	mov    rax, 0
	mov    r13, rax
														;jump %for_condition
	jmp    array_equal_for_condition_5
array_equal_for_condition_5:
														;$t350 = call __builtin_getArraySize $p48
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getArraySize
	mov    r12, rax
														;$t351 = slt $t50 $t350
	mov    r14, r13
	cmp    r14, r12
	setl   al
	movzx    r14, al
														;br $t351 %for_body %for_after
	cmp    r14, 0
	jnz    array_equal_for_body_6
	jz     array_equal_for_after_11
array_equal_for_body_6:
														;$t353 = mul $t50 8
	mov    r11, 8
	mov    r14, r13
	imul    r14, r11
														;$t352 = add $p48 $t353
	mov    r10, qword [rbp+(-8)]
	lea    r12, [r14 + r10]
														;$t354 = load 8 $t352 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t356 = mul $t50 8
	mov    r11, 8
	mov    r15, r13
	imul    r15, r11
														;$t355 = add $p49 $t356
	mov    r10, qword [rbp+(-16)]
	lea    r14, [r15 + r10]
														;$t357 = load 8 $t355 0
	mov    r11, r14
	mov    r14, qword [r11]
														;$t358 = sne $t354 $t357
	cmp    r12, r14
	setne   al
	movzx    r12, al
														;br $t358 %if_true %if_false
	cmp    r12, 0
	jnz    array_equal_if_true_7
	jz     array_equal_if_false_8
array_equal_if_true_7:
														;ret 0
	mov    rax, 0
	jmp    array_equal_exit_12
														;jump %exit
	jmp    array_equal_exit_12
array_equal_if_false_8:
														;jump %if_merge
	jmp    array_equal_if_merge_9
array_equal_if_merge_9:
														;jump %for_loop
	jmp    array_equal_for_loop_10
array_equal_for_loop_10:
														;$t359 = move $t50
	mov    r12, r13
														;$t50 = add $t50 1
	mov    r11, 1
	lea    r13, [r11 + r13]
														;jump %for_condition
	jmp    array_equal_for_condition_5
array_equal_for_after_11:
														;ret 1
	mov    rax, 1
	jmp    array_equal_exit_12
														;jump %exit
	jmp    array_equal_exit_12
array_equal_exit_12:
	mov    r13, qword [rbp + (-296)]
	mov    r12, qword [rbp + (-288)]
	mov    r15, qword [rbp + (-312)]
	mov    r14, qword [rbp + (-304)]
	leave
	ret
crackSHA1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 600
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-560)], r13
	mov    qword [rbp + (-552)], r12
	mov    qword [rbp + (-480)], rbx
	mov    qword [rbp + (-576)], r15
	mov    qword [rbp + (-568)], r14
crackSHA1_enter_0:
														;jump %entry
	jmp    crackSHA1_entry_1
crackSHA1_entry_1:
														;$t361 = move 40
	mov    rax, 40
	mov    r12, rax
														;$t361 = add $t361 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$t360 = alloc $t361
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rdi, r12
	call   malloc
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r13, rax
														;store 8 $t360 5 0
	mov    rax, 5
	mov    r11, r13
	mov    qword [r11], rax
														;$t360 = add $t360 8
	mov    r11, 8
	lea    r13, [r11 + r13]
														;$t52 = move $t360
	mov    r13, r13
														;$t362 = call __builtin_getStringLength $p51
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;$t363 = sne $t362 40
	mov    r11, 40
	cmp    r12, r11
	setne   al
	movzx    r12, al
														;br $t363 %if_true %if_false
	cmp    r12, 0
	jnz    crackSHA1_if_true_2
	jz     crackSHA1_if_false_3
crackSHA1_if_true_2:
														;call __builtin_println $364
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, CONST_STRING_364
	mov    rdi, rax
	call   __builtin_println
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_if_false_3:
														;jump %if_merge
	jmp    crackSHA1_if_merge_4
crackSHA1_if_merge_4:
														;$t53 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_5
crackSHA1_for_condition_5:
														;$t365 = slt $t53 5
	mov    r11, 5
	mov    r12, rbx
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t365 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_6
	jz     crackSHA1_for_after_8
crackSHA1_for_body_6:
														;$t367 = mul $t53 8
	mov    r11, 8
	mov    r12, rbx
	imul    r12, r11
														;$t366 = add $t52 $t367
	lea    r14, [r12 + r13]
														;store 8 $t366 0 0
	mov    rax, 0
	mov    r11, r14
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_7
crackSHA1_for_loop_7:
														;$t368 = move $t53
	mov    r12, rbx
														;$t53 = add $t53 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %for_condition
	jmp    crackSHA1_for_condition_5
crackSHA1_for_after_8:
														;$t53 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_9
crackSHA1_for_condition_9:
														;$t369 = slt $t53 40
	mov    r11, 40
	mov    r12, rbx
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t369 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_10
	jz     crackSHA1_for_after_12
crackSHA1_for_body_10:
														;$t370 = div $t53 8
	mov    r11, 8
	mov    r12, rbx
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t372 = mul $t370 8
	mov    r11, 8
	imul    r12, r11
														;$t371 = add $t52 $t372
	lea    r15, [r12 + r13]
														;$t373 = div $t53 8
	mov    r11, 8
	mov    r12, rbx
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t375 = mul $t373 8
	mov    r11, 8
	imul    r12, r11
														;$t374 = add $t52 $t375
	lea    r14, [r12 + r13]
														;$t376 = load 8 $t374 0
	mov    r11, r14
	mov    rsi, qword [r11]
														;$t377 = add $t53 3
	mov    r11, 3
	lea    r12, [r11 + rbx]
														;$t378 = call __builtin_getSubstring $p51 $t53 $t377
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rsi, rbx
	mov    rdx, r12
	call   __builtin_getSubstring
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;$t379 = call hex2int $t378
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rdi, r12
	call   hex2int
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;$t380 = div $t53 4
	mov    r11, 4
	mov    r14, rbx
	mov    rax, r14
	cqo
	idiv   r11
	mov    r14, rax
														;$t381 = rem $t380 2
	mov    r11, 2
	mov    rdi, r14
	mov    rax, rdi
	cqo
	idiv   r11
	mov    rdi, rdx
														;$t382 = sub 1 $t381
	mov    r10, 1
	mov    r14, r10
	sub    r14, rdi
														;$t383 = mul $t382 16
	mov    r11, 16
	imul    r14, r11
														;$t384 = shl $t379 $t383
	mov    rcx, r14
	sal    r12, cl
														;$t385 = or $t376 $t384
	mov    r14, rsi
	or     r14, r12
														;store 8 $t371 $t385 0
	mov    r11, r15
	mov    qword [r11], r14
														;jump %for_loop
	jmp    crackSHA1_for_loop_11
crackSHA1_for_loop_11:
														;$t386 = add $t53 4
	mov    r11, 4
	lea    r12, [r11 + rbx]
														;$t53 = move $t386
	mov    rbx, r12
														;jump %for_condition
	jmp    crackSHA1_for_condition_9
crackSHA1_for_after_12:
														;$t54 = move 4
	mov    rax, 4
	mov    r14, rax
														;$t55 = move 1
	mov    rax, 1
	mov    r15, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_13
crackSHA1_for_condition_13:
														;$t387 = sle $t55 $t54
	mov    r12, r15
	cmp    r12, r14
	setle   al
	movzx    r12, al
														;br $t387 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_14
	jz     crackSHA1_for_after_33
crackSHA1_for_body_14:
														;$t53 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_15
crackSHA1_for_condition_15:
														;$t388 = slt $t53 $t55
	mov    r12, rbx
	cmp    r12, r15
	setl   al
	movzx    r12, al
														;br $t388 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_16
	jz     crackSHA1_for_after_18
crackSHA1_for_body_16:
														;$t390 = mul $t53 8
	mov    r11, 8
	mov    rsi, rbx
	imul    rsi, r11
														;$t389 = add $g21(inputBuffer) $t390
	mov    r10, qword [rel GV_inputBuffer]
	lea    r12, [rsi + r10]
														;store 8 $t389 48 0
	mov    rax, 48
	mov    r11, r12
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_17
crackSHA1_for_loop_17:
														;$t391 = move $t53
	mov    r12, rbx
														;$t53 = add $t53 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %for_condition
	jmp    crackSHA1_for_condition_15
crackSHA1_for_after_18:
														;jump %while_loop
	jmp    crackSHA1_while_loop_19
crackSHA1_while_loop_19:
														;jump %while_body
	jmp    crackSHA1_while_body_20
crackSHA1_while_body_20:
														;$t392 = call sha1 $g21(inputBuffer) $t55
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, qword [rel GV_inputBuffer]
	mov    rdi, rax
	mov    rsi, r15
	call   sha1
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;$t56 = move $t392
	mov    r12, r12
														;$t393 = call array_equal $t56 $t52
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rdi, r12
	mov    rsi, r13
	call   array_equal
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;br $t393 %if_true %if_false
	cmp    r12, 0
	jnz    crackSHA1_if_true_21
	jz     crackSHA1_if_false_26
crackSHA1_if_true_21:
														;$t53 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_22
crackSHA1_for_condition_22:
														;$t394 = slt $t53 $t55
	mov    r12, rbx
	cmp    r12, r15
	setl   al
	movzx    r12, al
														;br $t394 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_23
	jz     crackSHA1_for_after_25
crackSHA1_for_body_23:
														;$t396 = mul $t53 8
	mov    r11, 8
	mov    r13, rbx
	imul    r13, r11
														;$t395 = add $g21(inputBuffer) $t396
	mov    r10, qword [rel GV_inputBuffer]
	lea    r12, [r13 + r10]
														;$t397 = load 8 $t395 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t398 = call int2chr $t397
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rdi, r12
	call   int2chr
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;call __builtin_print $t398
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rdi, r12
	call   __builtin_print
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
														;jump %for_loop
	jmp    crackSHA1_for_loop_24
crackSHA1_for_loop_24:
														;$t399 = move $t53
	mov    r12, rbx
														;$t53 = add $t53 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %for_condition
	jmp    crackSHA1_for_condition_22
crackSHA1_for_after_25:
														;call __builtin_println $400
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, CONST_STRING_400
	mov    rdi, rax
	call   __builtin_println
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_if_false_26:
														;jump %if_merge
	jmp    crackSHA1_if_merge_27
crackSHA1_if_merge_27:
														;$t401 = call nextText $g21(inputBuffer) $t55
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, qword [rel GV_inputBuffer]
	mov    rdi, rax
	mov    rsi, r15
	call   nextText
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
	mov    r12, rax
														;$t402 = xor $t401 1
	mov    r11, 1
	xor     r12, r11
														;br $t402 %if_true %if_false
	cmp    r12, 0
	jnz    crackSHA1_if_true_28
	jz     crackSHA1_if_false_29
crackSHA1_if_true_28:
														;jump %while_after
	jmp    crackSHA1_while_after_31
crackSHA1_while_after_31:
														;jump %for_loop
	jmp    crackSHA1_for_loop_32
crackSHA1_for_loop_32:
														;$t403 = move $t55
	mov    r12, r15
														;$t55 = add $t55 1
	mov    r11, 1
	lea    r15, [r11 + r15]
														;jump %for_condition
	jmp    crackSHA1_for_condition_13
crackSHA1_if_false_29:
														;jump %if_merge
	jmp    crackSHA1_if_merge_30
crackSHA1_if_merge_30:
														;jump %while_loop
	jmp    crackSHA1_while_loop_19
crackSHA1_for_after_33:
														;call __builtin_println $404
	mov    qword [rbp + (-504)], rsi
	mov    qword [rbp + (-512)], rdi
	mov    rax, CONST_STRING_404
	mov    rdi, rax
	call   __builtin_println
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, qword [rbp + (-512)]
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_exit_34:
	mov    r13, qword [rbp + (-560)]
	mov    r12, qword [rbp + (-552)]
	mov    rbx, qword [rbp + (-480)]
	mov    r15, qword [rbp + (-576)]
	mov    r14, qword [rbp + (-568)]
	leave
	ret
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 408
	mov    qword [rbp + (-368)], r13
	mov    qword [rbp + (-360)], r12
	mov    qword [rbp + (-288)], rbx
	mov    qword [rbp + (-384)], r15
	mov    qword [rbp + (-376)], r14
main_enter_0:
														;$g4(asciiTable) = move $405
	mov    rax, CONST_STRING_405
	mov    qword [rel GV_asciiTable], rax
														;$g18(MAXCHUNK) = move 100
	mov    rax, 100
	mov    qword [rel GV_MAXCHUNK], rax
														;$t406 = sub $g18(MAXCHUNK) 1
	mov    r10, qword [rel GV_MAXCHUNK]
	mov    r11, 1
	mov    r12, r10
	sub    r12, r11
														;$t407 = mul $t406 64
	mov    r11, 64
	imul    r12, r11
														;$t408 = sub $t407 16
	mov    r11, 16
	sub    r12, r11
														;$g19(MAXLENGTH) = move $t408
	mov    qword [rel GV_MAXLENGTH], r12
														;$t410 = mul $g18(MAXCHUNK) 8
	mov    r10, qword [rel GV_MAXCHUNK]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t410 = add $t410 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$t409 = alloc $t410
	mov    rdi, r12
	call   malloc
	mov    r14, rax
														;store 8 $t409 $g18(MAXCHUNK) 0
	mov    rax, qword [rel GV_MAXCHUNK]
	mov    r11, r14
	mov    qword [r11], rax
														;$t409 = add $t409 8
	mov    r11, 8
	lea    r14, [r11 + r14]
														;$t411 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %new_condition
	jmp    main_new_condition_1
main_new_condition_1:
														;$t412 = slt $t411 $g18(MAXCHUNK)
	mov    r11, qword [rel GV_MAXCHUNK]
	mov    r12, rbx
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t412 %new_body %new_exit
	cmp    r12, 0
	jnz    main_new_body_2
	jz     main_new_exit_4
main_new_body_2:
														;$t414 = move 640
	mov    rax, 640
	mov    r12, rax
														;$t414 = add $t414 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$t413 = alloc $t414
	mov    rdi, r12
	call   malloc
	mov    r12, rax
														;store 8 $t413 80 0
	mov    rax, 80
	mov    r11, r12
	mov    qword [r11], rax
														;$t413 = add $t413 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$t415 = mul $t411 8
	mov    r11, 8
	mov    r13, rbx
	imul    r13, r11
														;$t416 = add $t409 $t415
	lea    r15, [r13 + r14]
														;store 8 $t416 $t413 0
	mov    r11, r15
	mov    qword [r11], r12
														;jump %new_loop
	jmp    main_new_loop_3
main_new_loop_3:
														;$t411 = add $t411 1
	mov    r11, 1
	lea    rbx, [r11 + rbx]
														;jump %new_condition
	jmp    main_new_condition_1
main_new_exit_4:
														;$g20(chunks) = move $t409
	mov    qword [rel GV_chunks], r14
														;$t418 = mul $g19(MAXLENGTH) 8
	mov    r10, qword [rel GV_MAXLENGTH]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t418 = add $t418 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$t417 = alloc $t418
	mov    rdi, r12
	call   malloc
	mov    r12, rax
														;store 8 $t417 $g19(MAXLENGTH) 0
	mov    rax, qword [rel GV_MAXLENGTH]
	mov    r11, r12
	mov    qword [r11], rax
														;$t417 = add $t417 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$g21(inputBuffer) = move $t417
	mov    qword [rel GV_inputBuffer], r12
														;$t420 = move 40
	mov    rax, 40
	mov    r12, rax
														;$t420 = add $t420 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$t419 = alloc $t420
	mov    rdi, r12
	call   malloc
	mov    r12, rax
														;store 8 $t419 5 0
	mov    rax, 5
	mov    r11, r12
	mov    qword [r11], rax
														;$t419 = add $t419 8
	mov    r11, 8
	lea    r12, [r11 + r12]
														;$g22(outputBuffer) = move $t419
	mov    qword [rel GV_outputBuffer], r12
														;jump %entry
	jmp    main_entry_5
main_entry_5:
														;jump %while_loop
	jmp    main_while_loop_6
main_while_loop_6:
														;jump %while_body
	jmp    main_while_body_7
main_while_body_7:
														;$t421 = call __builtin_getInt
	call   __builtin_getInt
	mov    r12, rax
														;$t57 = move $t421
	mov    r13, r12
														;$t422 = seq $t57 0
	mov    r11, 0
	mov    r12, r13
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t422 %if_true %if_false
	cmp    r12, 0
	jnz    main_if_true_8
	jz     main_if_false_9
main_if_true_8:
														;jump %while_after
	jmp    main_while_after_17
main_while_after_17:
														;ret 0
	mov    rax, 0
	jmp    main_exit_18
														;jump %exit
	jmp    main_exit_18
main_if_false_9:
														;jump %if_merge
	jmp    main_if_merge_10
main_if_merge_10:
														;$t423 = seq $t57 1
	mov    r11, 1
	mov    r12, r13
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t423 %if_true %if_false
	cmp    r12, 0
	jnz    main_if_true_11
	jz     main_if_false_12
main_if_true_11:
														;$t424 = call __builtin_getString
	call   __builtin_getString
	mov    r12, rax
														;$t58 = move $t424
	mov    r12, r12
														;call computeSHA1 $t58
	mov    rdi, r12
	call   computeSHA1
														;jump %if_merge
	jmp    main_if_merge_16
main_if_merge_16:
														;jump %while_loop
	jmp    main_while_loop_6
main_if_false_12:
														;$t425 = seq $t57 2
	mov    r11, 2
	mov    r12, r13
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t425 %if_true %if_false
	cmp    r12, 0
	jnz    main_if_true_13
	jz     main_if_false_14
main_if_true_13:
														;$t426 = call __builtin_getString
	call   __builtin_getString
	mov    r12, rax
														;$t58 = move $t426
	mov    r12, r12
														;call crackSHA1 $t58
	mov    rdi, r12
	call   crackSHA1
														;jump %if_merge
	jmp    main_if_merge_15
main_if_merge_15:
														;jump %if_merge
	jmp    main_if_merge_16
main_if_false_14:
														;jump %if_merge
	jmp    main_if_merge_15
main_exit_18:
	mov    r13, qword [rbp + (-368)]
	mov    r12, qword [rbp + (-360)]
	mov    rbx, qword [rbp + (-288)]
	mov    r15, qword [rbp + (-384)]
	mov    r14, qword [rbp + (-376)]
	leave
	ret
SECTION .data
CONST_STRING_404:
	db 78, 111, 116, 32, 70, 111, 117, 110, 100, 33, 0
GV_MAXLENGTH:
	dq 0
CONST_STRING_89:
	db 0
GV_inputBuffer:
	dq 0
CONST_STRING_400:
	db 0
GV_MAXCHUNK:
	dq 0
CONST_STRING_364:
	db 73, 110, 118, 97, 108, 105, 100, 32, 105, 110, 112, 117, 116, 0
CONST_STRING_147:
	db 110, 67, 104, 117, 110, 107, 32, 62, 32, 77, 65, 88, 67, 72, 85, 78, 75, 33, 0
CONST_STRING_405:
	db 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 0
CONST_STRING_90:
	db 0
GV_asciiTable:
	dq 0
GV_outputBuffer:
	dq 0
GV_chunks:
	dq 0
CONST_STRING_327:
	db 0
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

