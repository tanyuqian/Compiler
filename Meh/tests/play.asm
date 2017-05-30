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
	mov    qword [rbp + (-408)], r14
	mov    qword [rbp + (-416)], r15
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
														;$t58 = call __builtin_getStringLength $p0
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    r13, rax
														;$t59 = slt $t1 $t58
	mov    r12, r14
	cmp    r12, r13
	setl   al
	movzx    r12, al
														;br $t59 %for_body %for_after
	cmp    r12, 0
	jnz    hex2int_for_body_3
	jz     hex2int_for_after_23
hex2int_for_body_3:
														;$t60 = call __builtin_ord $p0 $t1
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rsi, r14
	call   __builtin_ord
	mov    r13, rax
														;$t3 = move $t60
	mov    r13, r13
														;$t62 = sge $t3 48
	mov    r11, 48
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t62 %logical_true %logical_false
	cmp    r12, 0
	jnz    hex2int_logical_true_4
	jz     hex2int_logical_false_5
hex2int_logical_true_4:
														;$t63 = sle $t3 57
	mov    r11, 57
	mov    r12, r13
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t61 = move $t63
	mov    r12, r12
														;jump %logical_merge
	jmp    hex2int_logical_merge_6
hex2int_logical_merge_6:
														;br $t61 %if_true %if_false
	cmp    r12, 0
	jnz    hex2int_if_true_7
	jz     hex2int_if_false_8
hex2int_if_true_7:
														;$t64 = mul $t2 16
	mov    r11, 16
	mov    r12, r15
	imul    r12, r11
														;$t65 = add $t64 $t3
	add    r12, r13
														;$t66 = sub $t65 48
	mov    r11, 48
	sub    r12, r11
														;$t2 = move $t66
	mov    r15, r12
														;jump %if_merge
	jmp    hex2int_if_merge_21
hex2int_if_merge_21:
														;jump %for_loop
	jmp    hex2int_for_loop_22
hex2int_for_loop_22:
														;$t81 = move $t1
	mov    r12, r14
														;$t1 = add $t1 1
	mov    r11, 1
	add    r14, r11
														;jump %for_condition
	jmp    hex2int_for_condition_2
hex2int_if_false_8:
														;$t68 = sge $t3 65
	mov    r11, 65
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t68 %logical_true %logical_false
	cmp    r12, 0
	jnz    hex2int_logical_true_9
	jz     hex2int_logical_false_10
hex2int_logical_true_9:
														;$t69 = sle $t3 70
	mov    r11, 70
	mov    r12, r13
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t67 = move $t69
	mov    r12, r12
														;jump %logical_merge
	jmp    hex2int_logical_merge_11
hex2int_logical_merge_11:
														;br $t67 %if_true %if_false
	cmp    r12, 0
	jnz    hex2int_if_true_12
	jz     hex2int_if_false_13
hex2int_if_true_12:
														;$t70 = mul $t2 16
	mov    r11, 16
	mov    r12, r15
	imul    r12, r11
														;$t71 = add $t70 $t3
	add    r12, r13
														;$t72 = sub $t71 65
	mov    r11, 65
	sub    r12, r11
														;$t73 = add $t72 10
	mov    r11, 10
	add    r12, r11
														;$t2 = move $t73
	mov    r15, r12
														;jump %if_merge
	jmp    hex2int_if_merge_20
hex2int_if_merge_20:
														;jump %if_merge
	jmp    hex2int_if_merge_21
hex2int_if_false_13:
														;$t75 = sge $t3 97
	mov    r11, 97
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t75 %logical_true %logical_false
	cmp    r12, 0
	jnz    hex2int_logical_true_14
	jz     hex2int_logical_false_15
hex2int_logical_true_14:
														;$t76 = sle $t3 102
	mov    r11, 102
	mov    r12, r13
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t74 = move $t76
	mov    r12, r12
														;jump %logical_merge
	jmp    hex2int_logical_merge_16
hex2int_logical_merge_16:
														;br $t74 %if_true %if_false
	cmp    r12, 0
	jnz    hex2int_if_true_17
	jz     hex2int_if_false_18
hex2int_if_true_17:
														;$t77 = mul $t2 16
	mov    r11, 16
	mov    r12, r15
	imul    r12, r11
														;$t78 = add $t77 $t3
	add    r12, r13
														;$t79 = sub $t78 97
	mov    r11, 97
	sub    r12, r11
														;$t80 = add $t79 10
	mov    r11, 10
	add    r12, r11
														;$t2 = move $t80
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
														;$t74 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %logical_merge
	jmp    hex2int_logical_merge_16
hex2int_logical_false_10:
														;$t67 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %logical_merge
	jmp    hex2int_logical_merge_11
hex2int_logical_false_5:
														;$t61 = move 0
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
	mov    r14, qword [rbp + (-408)]
	mov    r15, qword [rbp + (-416)]
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
														;$t83 = sge $p5 32
	mov    r10, qword [rbp+(-8)]
	mov    r11, 32
	mov    r12, r10
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t83 %logical_true %logical_false
	cmp    r12, 0
	jnz    int2chr_logical_true_2
	jz     int2chr_logical_false_3
int2chr_logical_true_2:
														;$t84 = sle $p5 126
	mov    r10, qword [rbp+(-8)]
	mov    r11, 126
	mov    r12, r10
	cmp    r12, r11
	setle   al
	movzx    r12, al
														;$t82 = move $t84
	mov    r12, r12
														;jump %logical_merge
	jmp    int2chr_logical_merge_4
int2chr_logical_merge_4:
														;br $t82 %if_true %if_false
	cmp    r12, 0
	jnz    int2chr_if_true_5
	jz     int2chr_if_false_6
int2chr_if_true_5:
														;$t85 = sub $p5 32
	mov    r10, qword [rbp+(-8)]
	mov    r11, 32
	mov    r12, r10
	sub    r12, r11
														;$t86 = sub $p5 32
	mov    r10, qword [rbp+(-8)]
	mov    r11, 32
	mov    r13, r10
	sub    r13, r11
														;$t87 = call __builtin_getSubstring $g4(asciiTable) $t85 $t86
	mov    rax, qword [rel GV_asciiTable]
	mov    rdi, rax
	mov    rsi, r12
	mov    rdx, r13
	call   __builtin_getSubstring
	mov    r12, rax
														;ret $t87
	mov    rax, r12
	jmp    int2chr_exit_8
														;jump %exit
	jmp    int2chr_exit_8
int2chr_if_false_6:
														;jump %if_merge
	jmp    int2chr_if_merge_7
int2chr_if_merge_7:
														;ret $88
	mov    rax, CONST_STRING_88
	jmp    int2chr_exit_8
														;jump %exit
	jmp    int2chr_exit_8
int2chr_logical_false_3:
														;$t82 = move 0
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
	mov    qword [rbp + (-312)], r14
	mov    qword [rbp + (-320)], r15
toStringHex_enter_0:
														;jump %entry
	jmp    toStringHex_entry_1
toStringHex_entry_1:
														;$t7 = move $89
	mov    rax, CONST_STRING_89
	mov    r14, rax
														;$t8 = move 28
	mov    rax, 28
	mov    r13, rax
														;jump %for_condition
	jmp    toStringHex_for_condition_2
toStringHex_for_condition_2:
														;$t90 = sge $t8 0
	mov    r11, 0
	mov    r12, r13
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t90 %for_body %for_after
	cmp    r12, 0
	jnz    toStringHex_for_body_3
	jz     toStringHex_for_after_8
toStringHex_for_body_3:
														;$t91 = shr $p6 $t8
	mov    r10, qword [rbp+(-8)]
	mov    r12, r10
	mov    rcx, r13
	sar    r12, cl
														;$t92 = and $t91 15
	mov    r11, 15
	and    r12, r11
														;$t9 = move $t92
	mov    r12, r12
														;$t93 = slt $t9 10
	mov    r11, 10
	mov    r15, r12
	cmp    r15, r11
	setl   al
	movzx    r15, al
														;br $t93 %if_true %if_false
	cmp    r15, 0
	jnz    toStringHex_if_true_4
	jz     toStringHex_if_false_5
toStringHex_if_true_4:
														;$t94 = add $t9 48
	mov    r11, 48
	add    r12, r11
														;$t95 = call int2chr $t94
	mov    rdi, r12
	call   int2chr
	mov    r12, rax
														;$t96 = call __builtin_string_concat $t7 $t95
	mov    rdi, r14
	mov    rsi, r12
	call   __builtin_string_concat
	mov    r14, rax
														;$t7 = move $t96
	mov    r14, r14
														;jump %if_merge
	jmp    toStringHex_if_merge_6
toStringHex_if_merge_6:
														;jump %for_loop
	jmp    toStringHex_for_loop_7
toStringHex_for_loop_7:
														;$t101 = sub $t8 4
	mov    r11, 4
	sub    r13, r11
														;$t8 = move $t101
	mov    r13, r13
														;jump %for_condition
	jmp    toStringHex_for_condition_2
toStringHex_if_false_5:
														;$t97 = add $t9 65
	mov    r11, 65
	add    r12, r11
														;$t98 = sub $t97 10
	mov    r11, 10
	sub    r12, r11
														;$t99 = call int2chr $t98
	mov    rdi, r12
	call   int2chr
	mov    r12, rax
														;$t100 = call __builtin_string_concat $t7 $t99
	mov    rdi, r14
	mov    rsi, r12
	call   __builtin_string_concat
	mov    r12, rax
														;$t7 = move $t100
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
	mov    r14, qword [rbp + (-312)]
	mov    r15, qword [rbp + (-320)]
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
														;$t102 = seq $p11 1
	mov    r10, qword [rbp+(-16)]
	mov    r11, 1
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t102 %if_true %if_false
	cmp    r12, 0
	jnz    rotate_left_if_true_2
	jz     rotate_left_if_false_3
rotate_left_if_true_2:
														;$t103 = and $p10 2147483647
	mov    r10, qword [rbp+(-8)]
	mov    r11, 2147483647
	mov    r12, r10
	and    r12, r11
														;$t104 = shl $t103 1
	mov    r11, 1
	mov    rcx, r11
	sal    r12, cl
														;$t105 = shr $p10 31
	mov    r10, qword [rbp+(-8)]
	mov    r11, 31
	mov    r13, r10
	mov    rcx, r11
	sar    r13, cl
														;$t106 = and $t105 1
	mov    r11, 1
	and    r13, r11
														;$t107 = or $t104 $t106
	or     r12, r13
														;ret $t107
	mov    rax, r12
	jmp    rotate_left_exit_8
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_if_false_3:
														;jump %if_merge
	jmp    rotate_left_if_merge_4
rotate_left_if_merge_4:
														;$t108 = seq $p11 31
	mov    r10, qword [rbp+(-16)]
	mov    r11, 31
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t108 %if_true %if_false
	cmp    r12, 0
	jnz    rotate_left_if_true_5
	jz     rotate_left_if_false_6
rotate_left_if_true_5:
														;$t109 = and $p10 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	mov    r12, r10
	and    r12, r11
														;$t110 = shl $t109 31
	mov    r11, 31
	mov    rcx, r11
	sal    r12, cl
														;$t111 = shr $p10 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	mov    r13, r10
	mov    rcx, r11
	sar    r13, cl
														;$t112 = and $t111 2147483647
	mov    r11, 2147483647
	and    r13, r11
														;$t113 = or $t110 $t112
	or     r12, r13
														;ret $t113
	mov    rax, r12
	jmp    rotate_left_exit_8
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_if_false_6:
														;jump %if_merge
	jmp    rotate_left_if_merge_7
rotate_left_if_merge_7:
														;$t114 = sub 32 $p11
	mov    r10, 32
	mov    r11, qword [rbp+(-16)]
	mov    r12, r10
	sub    r12, r11
														;$t115 = shl 1 $t114
	mov    r10, 1
	mov    r13, r10
	mov    rcx, r12
	sal    r13, cl
														;$t116 = sub $t115 1
	mov    r11, 1
	mov    r12, r13
	sub    r12, r11
														;$t117 = and $p10 $t116
	mov    r10, qword [rbp+(-8)]
	mov    r13, r10
	and    r13, r12
														;$t118 = shl $t117 $p11
	mov    r11, qword [rbp+(-16)]
	mov    r14, r13
	mov    rcx, r11
	sal    r14, cl
														;$t119 = sub 32 $p11
	mov    r10, 32
	mov    r11, qword [rbp+(-16)]
	mov    r13, r10
	sub    r13, r11
														;$t120 = shr $p10 $t119
	mov    r10, qword [rbp+(-8)]
	mov    r12, r10
	mov    rcx, r13
	sar    r12, cl
														;$t121 = shl 1 $p11
	mov    r10, 1
	mov    r11, qword [rbp+(-16)]
	mov    r13, r10
	mov    rcx, r11
	sal    r13, cl
														;$t122 = sub $t121 1
	mov    r11, 1
	sub    r13, r11
														;$t123 = and $t120 $t122
	and    r12, r13
														;$t124 = or $t118 $t123
	mov    r13, r14
	or     r13, r12
														;ret $t124
	mov    rax, r13
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
	mov    qword [rbp + (-320)], r14
	mov    qword [rbp + (-328)], r15
add_enter_0:
														;jump %entry
	jmp    add_entry_1
add_entry_1:
														;$t125 = and $p12 65535
	mov    r10, qword [rbp+(-8)]
	mov    r11, 65535
	mov    r13, r10
	and    r13, r11
														;$t126 = and $p13 65535
	mov    r10, qword [rbp+(-16)]
	mov    r11, 65535
	mov    r12, r10
	and    r12, r11
														;$t127 = add $t125 $t126
	mov    r14, r13
	add    r14, r12
														;$t14 = move $t127
	mov    r14, r14
														;$t128 = shr $p12 16
	mov    r10, qword [rbp+(-8)]
	mov    r11, 16
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t129 = and $t128 65535
	mov    r11, 65535
	mov    r13, r12
	and    r13, r11
														;$t130 = shr $p13 16
	mov    r10, qword [rbp+(-16)]
	mov    r11, 16
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t131 = and $t130 65535
	mov    r11, 65535
	and    r12, r11
														;$t132 = add $t129 $t131
	mov    r15, r13
	add    r15, r12
														;$t133 = shr $t14 16
	mov    r11, 16
	mov    r13, r14
	mov    rcx, r11
	sar    r13, cl
														;$t134 = add $t132 $t133
	mov    r12, r15
	add    r12, r13
														;$t135 = and $t134 65535
	mov    r11, 65535
	and    r12, r11
														;$t15 = move $t135
	mov    r12, r12
														;$t136 = shl $t15 16
	mov    r11, 16
	mov    r13, r12
	mov    rcx, r11
	sal    r13, cl
														;$t137 = and $t14 65535
	mov    r11, 65535
	mov    r12, r14
	and    r12, r11
														;$t138 = or $t136 $t137
	or     r13, r12
														;ret $t138
	mov    rax, r13
	jmp    add_exit_2
														;jump %exit
	jmp    add_exit_2
add_exit_2:
	mov    r13, qword [rbp + (-312)]
	mov    r12, qword [rbp + (-304)]
	mov    r14, qword [rbp + (-320)]
	mov    r15, qword [rbp + (-328)]
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
														;$t139 = shl $p17 16
	mov    r10, qword [rbp+(-16)]
	mov    r11, 16
	mov    r13, r10
	mov    rcx, r11
	sal    r13, cl
														;$t140 = or $p16 $t139
	mov    r10, qword [rbp+(-8)]
	mov    r12, r10
	or     r12, r13
														;ret $t140
	mov    rax, r12
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
	mov    qword [rbp + (-1672)], r14
	mov    qword [rbp + (-1680)], r15
sha1_enter_0:
														;jump %entry
	jmp    sha1_entry_1
sha1_entry_1:
														;$t141 = add $p24 64
	mov    r10, qword [rbp+(-16)]
	mov    r11, 64
	mov    r12, r10
	add    r12, r11
														;$t142 = sub $t141 56
	mov    r11, 56
	sub    r12, r11
														;$t143 = div $t142 64
	mov    r11, 64
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t144 = add $t143 1
	mov    r11, 1
	add    r12, r11
														;$t25 = move $t144
	mov    qword [rbp+(-1312)], r12
														;$t145 = sgt $t25 $g18(MAXCHUNK)
	mov    r10, qword [rbp+(-1312)]
	mov    r11, qword [rel GV_MAXCHUNK]
	mov    r12, r10
	cmp    r12, r11
	setg   al
	movzx    r12, al
														;br $t145 %if_true %if_false
	cmp    r12, 0
	jnz    sha1_if_true_2
	jz     sha1_if_false_3
sha1_if_true_2:
														;call __builtin_println $146
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, CONST_STRING_146
	mov    rdi, rax
	call   __builtin_println
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
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
	mov    qword [rbp+(-560)], rax
														;jump %for_condition
	jmp    sha1_for_condition_5
sha1_for_condition_5:
														;$t147 = slt $t26 $t25
	mov    r10, qword [rbp+(-560)]
	mov    r11, qword [rbp+(-1312)]
	mov    r12, r10
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t147 %for_body %for_after
	cmp    r12, 0
	jnz    sha1_for_body_6
	jz     sha1_for_after_12
sha1_for_body_6:
														;$t27 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %for_condition
	jmp    sha1_for_condition_7
sha1_for_condition_7:
														;$t148 = slt $t27 80
	mov    r11, 80
	mov    r13, r12
	cmp    r13, r11
	setl   al
	movzx    r13, al
														;br $t148 %for_body %for_after
	cmp    r13, 0
	jnz    sha1_for_body_8
	jz     sha1_for_after_10
sha1_for_body_8:
														;$t150 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t149 = add $g20(chunks) $t150
	mov    r10, qword [rel GV_chunks]
	mov    r14, r10
	add    r14, r13
														;$t151 = load 8 $t149 0
	mov    r11, r14
	mov    r13, qword [r11]
														;$t153 = mul $t27 8
	mov    r11, 8
	mov    r14, r12
	imul    r14, r11
														;$t152 = add $t151 $t153
	add    r13, r14
														;store 8 $t152 0 0
	mov    rax, 0
	mov    r11, r13
	mov    qword [r11], rax
														;jump %for_loop
	jmp    sha1_for_loop_9
sha1_for_loop_9:
														;$t154 = move $t27
	mov    r13, r12
														;$t27 = add $t27 1
	mov    r11, 1
	add    r12, r11
														;jump %for_condition
	jmp    sha1_for_condition_7
sha1_for_after_10:
														;jump %for_loop
	jmp    sha1_for_loop_11
sha1_for_loop_11:
														;$t155 = move $t26
	mov    rax, qword [rbp+(-560)]
	mov    r12, rax
														;$t26 = add $t26 1
	mov    r10, qword [rbp+(-560)]
	mov    r11, 1
	mov    rax, r10
	add    rax, r11
	mov    qword [rbp+(-560)], rax
														;jump %for_condition
	jmp    sha1_for_condition_5
sha1_for_after_12:
														;$t26 = move 0
	mov    rax, 0
	mov    qword [rbp+(-560)], rax
														;jump %for_condition
	jmp    sha1_for_condition_13
sha1_for_condition_13:
														;$t156 = slt $t26 $p24
	mov    r10, qword [rbp+(-560)]
	mov    r11, qword [rbp+(-16)]
	mov    r12, r10
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t156 %for_body %for_after
	cmp    r12, 0
	jnz    sha1_for_body_14
	jz     sha1_for_after_16
sha1_for_body_14:
														;$t157 = div $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t159 = mul $t157 8
	mov    r11, 8
	imul    r12, r11
														;$t158 = add $g20(chunks) $t159
	mov    r10, qword [rel GV_chunks]
	mov    r13, r10
	add    r13, r12
														;$t160 = load 8 $t158 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t161 = rem $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r13, r10
	mov    rax, r13
	cqo
	idiv   r11
	mov    r13, rdx
														;$t162 = div $t161 4
	mov    r11, 4
	mov    rax, r13
	cqo
	idiv   r11
	mov    r13, rax
														;$t164 = mul $t162 8
	mov    r11, 8
	imul    r13, r11
														;$t163 = add $t160 $t164
	mov    r14, r12
	add    r14, r13
														;$t165 = div $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t167 = mul $t165 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t166 = add $g20(chunks) $t167
	mov    r10, qword [rel GV_chunks]
	mov    r12, r10
	add    r12, r13
														;$t168 = load 8 $t166 0
	mov    r11, r12
	mov    r15, qword [r11]
														;$t169 = rem $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t170 = div $t169 4
	mov    r11, 4
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t172 = mul $t170 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t171 = add $t168 $t172
	mov    r12, r15
	add    r12, r13
														;$t173 = load 8 $t171 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t175 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r15, r10
	imul    r15, r11
														;$t174 = add $p23 $t175
	mov    r10, qword [rbp+(-8)]
	mov    r12, r10
	add    r12, r15
														;$t176 = load 8 $t174 0
	mov    r11, r12
	mov    rbx, qword [r11]
														;$t177 = rem $t26 4
	mov    r10, qword [rbp+(-560)]
	mov    r11, 4
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t178 = sub 3 $t177
	mov    r10, 3
	mov    r15, r10
	sub    r15, r12
														;$t179 = mul $t178 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t180 = shl $t176 $t179
	mov    r15, rbx
	mov    rcx, r12
	sal    r15, cl
														;$t181 = or $t173 $t180
	mov    r12, r13
	or     r12, r15
														;store 8 $t163 $t181 0
	mov    r11, r14
	mov    qword [r11], r12
														;jump %for_loop
	jmp    sha1_for_loop_15
sha1_for_loop_15:
														;$t182 = move $t26
	mov    rax, qword [rbp+(-560)]
	mov    r12, rax
														;$t26 = add $t26 1
	mov    r10, qword [rbp+(-560)]
	mov    r11, 1
	mov    rax, r10
	add    rax, r11
	mov    qword [rbp+(-560)], rax
														;jump %for_condition
	jmp    sha1_for_condition_13
sha1_for_after_16:
														;$t183 = div $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t185 = mul $t183 8
	mov    r11, 8
	imul    r12, r11
														;$t184 = add $g20(chunks) $t185
	mov    r10, qword [rel GV_chunks]
	mov    r13, r10
	add    r13, r12
														;$t186 = load 8 $t184 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t187 = rem $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r13, r10
	mov    rax, r13
	cqo
	idiv   r11
	mov    r13, rdx
														;$t188 = div $t187 4
	mov    r11, 4
	mov    rax, r13
	cqo
	idiv   r11
	mov    r13, rax
														;$t190 = mul $t188 8
	mov    r11, 8
	imul    r13, r11
														;$t189 = add $t186 $t190
	mov    r14, r12
	add    r14, r13
														;$t191 = div $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t193 = mul $t191 8
	mov    r11, 8
	imul    r12, r11
														;$t192 = add $g20(chunks) $t193
	mov    r10, qword [rel GV_chunks]
	mov    r13, r10
	add    r13, r12
														;$t194 = load 8 $t192 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t195 = rem $t26 64
	mov    r10, qword [rbp+(-560)]
	mov    r11, 64
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t196 = div $t195 4
	mov    r11, 4
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t198 = mul $t196 8
	mov    r11, 8
	imul    r12, r11
														;$t197 = add $t194 $t198
	add    r13, r12
														;$t199 = load 8 $t197 0
	mov    r11, r13
	mov    r15, qword [r11]
														;$t200 = rem $t26 4
	mov    r10, qword [rbp+(-560)]
	mov    r11, 4
	mov    r12, r10
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t201 = sub 3 $t200
	mov    r10, 3
	mov    r13, r10
	sub    r13, r12
														;$t202 = mul $t201 8
	mov    r11, 8
	mov    r12, r13
	imul    r12, r11
														;$t203 = shl 128 $t202
	mov    r10, 128
	mov    r13, r10
	mov    rcx, r12
	sal    r13, cl
														;$t204 = or $t199 $t203
	mov    r12, r15
	or     r12, r13
														;store 8 $t189 $t204 0
	mov    r11, r14
	mov    qword [r11], r12
														;$t205 = sub $t25 1
	mov    r10, qword [rbp+(-1312)]
	mov    r11, 1
	mov    r12, r10
	sub    r12, r11
														;$t207 = mul $t205 8
	mov    r11, 8
	imul    r12, r11
														;$t206 = add $g20(chunks) $t207
	mov    r10, qword [rel GV_chunks]
	mov    r13, r10
	add    r13, r12
														;$t208 = load 8 $t206 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t210 = move 120
	mov    rax, 120
	mov    r13, rax
														;$t209 = add $t208 $t210
	add    r12, r13
														;$t211 = shl $p24 3
	mov    r10, qword [rbp+(-16)]
	mov    r11, 3
	mov    r13, r10
	mov    rcx, r11
	sal    r13, cl
														;store 8 $t209 $t211 0
	mov    r11, r12
	mov    qword [r11], r13
														;$t212 = sub $t25 1
	mov    r10, qword [rbp+(-1312)]
	mov    r11, 1
	mov    r12, r10
	sub    r12, r11
														;$t214 = mul $t212 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t213 = add $g20(chunks) $t214
	mov    r10, qword [rel GV_chunks]
	mov    r12, r10
	add    r12, r13
														;$t215 = load 8 $t213 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t217 = move 112
	mov    rax, 112
	mov    r12, rax
														;$t216 = add $t215 $t217
	add    r13, r12
														;$t218 = shr $p24 29
	mov    r10, qword [rbp+(-16)]
	mov    r11, 29
	mov    r12, r10
	mov    rcx, r11
	sar    r12, cl
														;$t219 = and $t218 7
	mov    r11, 7
	and    r12, r11
														;store 8 $t216 $t219 0
	mov    r11, r13
	mov    qword [r11], r12
														;$t28 = move 1732584193
	mov    rax, 1732584193
	mov    r14, rax
														;$t220 = call lohi 43913 61389
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, 43913
	mov    rdi, rax
	mov    rax, 61389
	mov    rsi, rax
	call   lohi
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r12, rax
														;$t29 = move $t220
	mov    qword [rbp+(-1192)], r12
														;$t221 = call lohi 56574 39098
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, 56574
	mov    rdi, rax
	mov    rax, 39098
	mov    rsi, rax
	call   lohi
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r8, rax
														;$t30 = move $t221
	mov    r8, r8
														;$t31 = move 271733878
	mov    rax, 271733878
	mov    rsi, rax
														;$t222 = call lohi 57840 50130
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, 57840
	mov    rdi, rax
	mov    rax, 50130
	mov    rsi, rax
	call   lohi
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r9, rax
														;$t32 = move $t222
	mov    r9, r9
														;$t26 = move 0
	mov    rax, 0
	mov    qword [rbp+(-560)], rax
														;jump %for_condition
	jmp    sha1_for_condition_17
sha1_for_condition_17:
														;$t223 = slt $t26 $t25
	mov    r10, qword [rbp+(-560)]
	mov    r11, qword [rbp+(-1312)]
	mov    r12, r10
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t223 %for_body %for_after
	cmp    r12, 0
	jnz    sha1_for_body_18
	jz     sha1_for_after_37
sha1_for_body_18:
														;$t27 = move 16
	mov    rax, 16
	mov    r12, rax
														;jump %for_condition
	jmp    sha1_for_condition_19
sha1_for_condition_19:
														;$t224 = slt $t27 80
	mov    r11, 80
	mov    r13, r12
	cmp    r13, r11
	setl   al
	movzx    r13, al
														;br $t224 %for_body %for_after
	cmp    r13, 0
	jnz    sha1_for_body_20
	jz     sha1_for_after_22
sha1_for_body_20:
														;$t226 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t225 = add $g20(chunks) $t226
	mov    r10, qword [rel GV_chunks]
	mov    r15, r10
	add    r15, r13
														;$t227 = load 8 $t225 0
	mov    r11, r15
	mov    r15, qword [r11]
														;$t229 = mul $t27 8
	mov    r11, 8
	mov    r13, r12
	imul    r13, r11
														;$t228 = add $t227 $t229
	add    r15, r13
														;$t231 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t230 = add $g20(chunks) $t231
	mov    r10, qword [rel GV_chunks]
	mov    rbx, r10
	add    rbx, r13
														;$t232 = load 8 $t230 0
	mov    r11, rbx
	mov    rbx, qword [r11]
														;$t233 = sub $t27 3
	mov    r11, 3
	mov    r13, r12
	sub    r13, r11
														;$t235 = mul $t233 8
	mov    r11, 8
	imul    r13, r11
														;$t234 = add $t232 $t235
	add    rbx, r13
														;$t236 = load 8 $t234 0
	mov    r11, rbx
	mov    rbx, qword [r11]
														;$t238 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t237 = add $g20(chunks) $t238
	mov    r10, qword [rel GV_chunks]
	mov    rdi, r10
	add    rdi, r13
														;$t239 = load 8 $t237 0
	mov    r11, rdi
	mov    rdi, qword [r11]
														;$t240 = sub $t27 8
	mov    r11, 8
	mov    r13, r12
	sub    r13, r11
														;$t242 = mul $t240 8
	mov    r11, 8
	imul    r13, r11
														;$t241 = add $t239 $t242
	add    rdi, r13
														;$t243 = load 8 $t241 0
	mov    r11, rdi
	mov    r13, qword [r11]
														;$t244 = xor $t236 $t243
	xor     rbx, r13
														;$t246 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r13, r10
	imul    r13, r11
														;$t245 = add $g20(chunks) $t246
	mov    r10, qword [rel GV_chunks]
	mov    rdi, r10
	add    rdi, r13
														;$t247 = load 8 $t245 0
	mov    r11, rdi
	mov    r13, qword [r11]
														;$t248 = sub $t27 14
	mov    r11, 14
	mov    rdi, r12
	sub    rdi, r11
														;$t250 = mul $t248 8
	mov    r11, 8
	imul    rdi, r11
														;$t249 = add $t247 $t250
	add    r13, rdi
														;$t251 = load 8 $t249 0
	mov    r11, r13
	mov    r13, qword [r11]
														;$t252 = xor $t244 $t251
	mov    rdi, rbx
	xor     rdi, r13
														;$t254 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    rbx, r10
	imul    rbx, r11
														;$t253 = add $g20(chunks) $t254
	mov    r10, qword [rel GV_chunks]
	mov    r13, r10
	add    r13, rbx
														;$t255 = load 8 $t253 0
	mov    r11, r13
	mov    rbx, qword [r11]
														;$t256 = sub $t27 16
	mov    r11, 16
	mov    r13, r12
	sub    r13, r11
														;$t258 = mul $t256 8
	mov    r11, 8
	imul    r13, r11
														;$t257 = add $t255 $t258
	add    rbx, r13
														;$t259 = load 8 $t257 0
	mov    r11, rbx
	mov    r13, qword [r11]
														;$t260 = xor $t252 $t259
	mov    rbx, rdi
	xor     rbx, r13
														;$t261 = call rotate_left $t260 1
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, rbx
	mov    rax, 1
	mov    rsi, rax
	call   rotate_left
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r13, rax
														;store 8 $t228 $t261 0
	mov    r11, r15
	mov    qword [r11], r13
														;jump %for_loop
	jmp    sha1_for_loop_21
sha1_for_loop_21:
														;$t262 = move $t27
	mov    r13, r12
														;$t27 = add $t27 1
	mov    r11, 1
	add    r12, r11
														;jump %for_condition
	jmp    sha1_for_condition_19
sha1_for_after_22:
														;$t33 = move $t28
	mov    qword [rbp+(-496)], r14
														;$t34 = move $t29
	mov    rax, qword [rbp+(-1192)]
	mov    r13, rax
														;$t35 = move $t30
	mov    qword [rbp+(-408)], r8
														;$t36 = move $t31
	mov    qword [rbp+(-1104)], rsi
														;$t37 = move $t32
	mov    r15, r9
														;$t27 = move 0
	mov    rax, 0
	mov    r12, rax
														;jump %for_condition
	jmp    sha1_for_condition_23
sha1_for_condition_23:
														;$t263 = slt $t27 80
	mov    r11, 80
	mov    rbx, r12
	cmp    rbx, r11
	setl   al
	movzx    rbx, al
														;br $t263 %for_body %for_after
	cmp    rbx, 0
	jnz    sha1_for_body_24
	jz     sha1_for_after_35
sha1_for_body_24:
														;$t264 = slt $t27 20
	mov    r11, 20
	mov    rbx, r12
	cmp    rbx, r11
	setl   al
	movzx    rbx, al
														;br $t264 %if_true %if_false
	cmp    rbx, 0
	jnz    sha1_if_true_25
	jz     sha1_if_false_26
sha1_if_true_25:
														;$t265 = and $t34 $t35
	mov    r11, qword [rbp+(-408)]
	mov    rbx, r13
	and    rbx, r11
														;$t266 = not $t34
	mov    rdi, r13
	not    rdi
														;$t267 = and $t266 $t36
	mov    r11, qword [rbp+(-1104)]
	and    rdi, r11
														;$t268 = or $t265 $t267
	or     rbx, rdi
														;$t38 = move $t268
	mov    qword [rbp+(-1272)], rbx
														;$t39 = move 1518500249
	mov    rax, 1518500249
	mov    rbx, rax
														;jump %if_merge
	jmp    sha1_if_merge_33
sha1_if_merge_33:
														;$t282 = call rotate_left $t33 5
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, qword [rbp+(-496)]
	mov    rdi, rax
	mov    rax, 5
	mov    rsi, rax
	call   rotate_left
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, rax
														;$t283 = call add $t282 $t37
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, qword[rbp + (-1616)]
	mov    rsi, r15
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r15, rax
														;$t284 = call add $t38 $t39
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, qword [rbp+(-1272)]
	mov    rdi, rax
	mov    rsi, rbx
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rbx, rax
														;$t285 = call add $t283 $t284
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, r15
	mov    rsi, rbx
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rdi, rax
														;$t287 = mul $t26 8
	mov    r10, qword [rbp+(-560)]
	mov    r11, 8
	mov    r15, r10
	imul    r15, r11
														;$t286 = add $g20(chunks) $t287
	mov    r10, qword [rel GV_chunks]
	mov    rbx, r10
	add    rbx, r15
														;$t288 = load 8 $t286 0
	mov    r11, rbx
	mov    r15, qword [r11]
														;$t290 = mul $t27 8
	mov    r11, 8
	mov    rbx, r12
	imul    rbx, r11
														;$t289 = add $t288 $t290
	add    r15, rbx
														;$t291 = load 8 $t289 0
	mov    r11, r15
	mov    r15, qword [r11]
														;$t292 = call add $t285 $t291
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, qword[rbp + (-1616)]
	mov    rsi, r15
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rbx, rax
														;$t40 = move $t292
	mov    rbx, rbx
														;$t37 = move $t36
	mov    rax, qword [rbp+(-1104)]
	mov    r15, rax
														;$t36 = move $t35
	mov    rax, qword [rbp+(-408)]
	mov    qword [rbp+(-1104)], rax
														;$t293 = call rotate_left $t34 30
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, r13
	mov    rax, 30
	mov    rsi, rax
	call   rotate_left
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r13, rax
														;$t35 = move $t293
	mov    qword [rbp+(-408)], r13
														;$t34 = move $t33
	mov    rax, qword [rbp+(-496)]
	mov    r13, rax
														;$t33 = move $t40
	mov    qword [rbp+(-496)], rbx
														;jump %for_loop
	jmp    sha1_for_loop_34
sha1_for_loop_34:
														;$t294 = move $t27
	mov    rbx, r12
														;$t27 = add $t27 1
	mov    r11, 1
	add    r12, r11
														;jump %for_condition
	jmp    sha1_for_condition_23
sha1_if_false_26:
														;$t269 = slt $t27 40
	mov    r11, 40
	mov    rbx, r12
	cmp    rbx, r11
	setl   al
	movzx    rbx, al
														;br $t269 %if_true %if_false
	cmp    rbx, 0
	jnz    sha1_if_true_27
	jz     sha1_if_false_28
sha1_if_true_27:
														;$t270 = xor $t34 $t35
	mov    r11, qword [rbp+(-408)]
	mov    rbx, r13
	xor     rbx, r11
														;$t271 = xor $t270 $t36
	mov    r11, qword [rbp+(-1104)]
	xor     rbx, r11
														;$t38 = move $t271
	mov    qword [rbp+(-1272)], rbx
														;$t39 = move 1859775393
	mov    rax, 1859775393
	mov    rbx, rax
														;jump %if_merge
	jmp    sha1_if_merge_32
sha1_if_merge_32:
														;jump %if_merge
	jmp    sha1_if_merge_33
sha1_if_false_28:
														;$t272 = slt $t27 60
	mov    r11, 60
	mov    rbx, r12
	cmp    rbx, r11
	setl   al
	movzx    rbx, al
														;br $t272 %if_true %if_false
	cmp    rbx, 0
	jnz    sha1_if_true_29
	jz     sha1_if_false_30
sha1_if_true_29:
														;$t273 = and $t34 $t35
	mov    r11, qword [rbp+(-408)]
	mov    rbx, r13
	and    rbx, r11
														;$t274 = and $t34 $t36
	mov    r11, qword [rbp+(-1104)]
	mov    rdi, r13
	and    rdi, r11
														;$t275 = or $t273 $t274
	or     rbx, rdi
														;$t276 = and $t35 $t36
	mov    r10, qword [rbp+(-408)]
	mov    r11, qword [rbp+(-1104)]
	mov    rdi, r10
	and    rdi, r11
														;$t277 = or $t275 $t276
	or     rbx, rdi
														;$t38 = move $t277
	mov    qword [rbp+(-1272)], rbx
														;$t278 = call lohi 48348 36635
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, 48348
	mov    rdi, rax
	mov    rax, 36635
	mov    rsi, rax
	call   lohi
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rbx, rax
														;$t39 = move $t278
	mov    rbx, rbx
														;jump %if_merge
	jmp    sha1_if_merge_31
sha1_if_merge_31:
														;jump %if_merge
	jmp    sha1_if_merge_32
sha1_if_false_30:
														;$t279 = xor $t34 $t35
	mov    r11, qword [rbp+(-408)]
	mov    rbx, r13
	xor     rbx, r11
														;$t280 = xor $t279 $t36
	mov    r11, qword [rbp+(-1104)]
	xor     rbx, r11
														;$t38 = move $t280
	mov    qword [rbp+(-1272)], rbx
														;$t281 = call lohi 49622 51810
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, 49622
	mov    rdi, rax
	mov    rax, 51810
	mov    rsi, rax
	call   lohi
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rbx, rax
														;$t39 = move $t281
	mov    rbx, rbx
														;jump %if_merge
	jmp    sha1_if_merge_31
sha1_for_after_35:
														;$t295 = call add $t28 $t33
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, r14
	mov    rax, qword [rbp+(-496)]
	mov    rsi, rax
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r14, rax
														;$t28 = move $t295
	mov    r14, r14
														;$t296 = call add $t29 $t34
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rax, qword [rbp+(-1192)]
	mov    rdi, rax
	mov    rsi, r13
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r12, rax
														;$t29 = move $t296
	mov    qword [rbp+(-1192)], r12
														;$t297 = call add $t30 $t35
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, qword[rbp + (-1624)]
	mov    rax, qword [rbp+(-408)]
	mov    rsi, rax
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r8, rax
														;$t30 = move $t297
	mov    r8, r8
														;$t298 = call add $t31 $t36
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, qword[rbp + (-1608)]
	mov    rax, qword [rbp+(-1104)]
	mov    rsi, rax
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    rsi, rax
														;$t31 = move $t298
	mov    rsi, rsi
														;$t299 = call add $t32 $t37
	mov    qword [rbp + (-1632)], r9
	mov    qword [rbp + (-1624)], r8
	mov    qword [rbp + (-1616)], rdi
	mov    qword [rbp + (-1608)], rsi
	mov    rdi, qword[rbp + (-1632)]
	mov    rsi, r15
	call   add
	mov    r9, qword [rbp + (-1632)]
	mov    r8, qword [rbp + (-1624)]
	mov    rdi, qword [rbp + (-1616)]
	mov    rsi, qword [rbp + (-1608)]
	mov    r9, rax
														;$t32 = move $t299
	mov    r9, r9
														;jump %for_loop
	jmp    sha1_for_loop_36
sha1_for_loop_36:
														;$t300 = move $t26
	mov    rax, qword [rbp+(-560)]
	mov    r12, rax
														;$t26 = add $t26 1
	mov    r10, qword [rbp+(-560)]
	mov    r11, 1
	mov    rax, r10
	add    rax, r11
	mov    qword [rbp+(-560)], rax
														;jump %for_condition
	jmp    sha1_for_condition_17
sha1_for_after_37:
														;$t302 = move 0
	mov    rax, 0
	mov    r12, rax
														;$t301 = add $g22(outputBuffer) $t302
	mov    r10, qword [rel GV_outputBuffer]
	mov    r13, r10
	add    r13, r12
														;store 8 $t301 $t28 0
	mov    r11, r13
	mov    qword [r11], r14
														;$t304 = move 8
	mov    rax, 8
	mov    r12, rax
														;$t303 = add $g22(outputBuffer) $t304
	mov    r10, qword [rel GV_outputBuffer]
	mov    r13, r10
	add    r13, r12
														;store 8 $t303 $t29 0
	mov    rax, qword [rbp+(-1192)]
	mov    r11, r13
	mov    qword [r11], rax
														;$t306 = move 16
	mov    rax, 16
	mov    r12, rax
														;$t305 = add $g22(outputBuffer) $t306
	mov    r10, qword [rel GV_outputBuffer]
	mov    r13, r10
	add    r13, r12
														;store 8 $t305 $t30 0
	mov    r11, r13
	mov    qword [r11], r8
														;$t308 = move 24
	mov    rax, 24
	mov    r13, rax
														;$t307 = add $g22(outputBuffer) $t308
	mov    r10, qword [rel GV_outputBuffer]
	mov    r12, r10
	add    r12, r13
														;store 8 $t307 $t31 0
	mov    r11, r12
	mov    qword [r11], rsi
														;$t310 = move 32
	mov    rax, 32
	mov    r13, rax
														;$t309 = add $g22(outputBuffer) $t310
	mov    r10, qword [rel GV_outputBuffer]
	mov    r12, r10
	add    r12, r13
														;store 8 $t309 $t32 0
	mov    r11, r12
	mov    qword [r11], r9
														;ret $g22(outputBuffer)
	mov    rax, qword [rel GV_outputBuffer]
	jmp    sha1_exit_38
														;jump %exit
	jmp    sha1_exit_38
sha1_exit_38:
	mov    r13, qword [rbp + (-1664)]
	mov    r12, qword [rbp + (-1656)]
	mov    rbx, qword [rbp + (-1584)]
	mov    r14, qword [rbp + (-1672)]
	mov    r15, qword [rbp + (-1680)]
	leave
	ret
computeSHA1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 360
	mov    qword [rbp-8], rdi
	mov    qword [rbp + (-320)], r13
	mov    qword [rbp + (-312)], r12
	mov    qword [rbp + (-328)], r14
	mov    qword [rbp + (-336)], r15
computeSHA1_enter_0:
														;jump %entry
	jmp    computeSHA1_entry_1
computeSHA1_entry_1:
														;$t42 = move 0
	mov    rax, 0
	mov    r14, rax
														;jump %for_condition
	jmp    computeSHA1_for_condition_2
computeSHA1_for_condition_2:
														;$t311 = call __builtin_getStringLength $p41
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    r13, rax
														;$t312 = slt $t42 $t311
	mov    r12, r14
	cmp    r12, r13
	setl   al
	movzx    r12, al
														;br $t312 %for_body %for_after
	cmp    r12, 0
	jnz    computeSHA1_for_body_3
	jz     computeSHA1_for_after_5
computeSHA1_for_body_3:
														;$t314 = mul $t42 8
	mov    r11, 8
	mov    r13, r14
	imul    r13, r11
														;$t313 = add $g21(inputBuffer) $t314
	mov    r10, qword [rel GV_inputBuffer]
	mov    r12, r10
	add    r12, r13
														;$t315 = call __builtin_ord $p41 $t42
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rsi, r14
	call   __builtin_ord
	mov    r13, rax
														;store 8 $t313 $t315 0
	mov    r11, r12
	mov    qword [r11], r13
														;jump %for_loop
	jmp    computeSHA1_for_loop_4
computeSHA1_for_loop_4:
														;$t316 = move $t42
	mov    r12, r14
														;$t42 = add $t42 1
	mov    r11, 1
	add    r14, r11
														;jump %for_condition
	jmp    computeSHA1_for_condition_2
computeSHA1_for_after_5:
														;$t317 = call __builtin_getStringLength $p41
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    r12, rax
														;$t318 = call sha1 $g21(inputBuffer) $t317
	mov    rax, qword [rel GV_inputBuffer]
	mov    rdi, rax
	mov    rsi, r12
	call   sha1
	mov    r12, rax
														;$t43 = move $t318
	mov    r13, r12
														;$t42 = move 0
	mov    rax, 0
	mov    r14, rax
														;jump %for_condition
	jmp    computeSHA1_for_condition_6
computeSHA1_for_condition_6:
														;$t319 = call __builtin_getArraySize $t43
	mov    rdi, r13
	call   __builtin_getArraySize
	mov    r15, rax
														;$t320 = slt $t42 $t319
	mov    r12, r14
	cmp    r12, r15
	setl   al
	movzx    r12, al
														;br $t320 %for_body %for_after
	cmp    r12, 0
	jnz    computeSHA1_for_body_7
	jz     computeSHA1_for_after_9
computeSHA1_for_body_7:
														;$t322 = mul $t42 8
	mov    r11, 8
	mov    r15, r14
	imul    r15, r11
														;$t321 = add $t43 $t322
	mov    r12, r13
	add    r12, r15
														;$t323 = load 8 $t321 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t324 = call toStringHex $t323
	mov    rdi, r12
	call   toStringHex
	mov    r12, rax
														;call __builtin_print $t324
	mov    rdi, r12
	call   __builtin_print
														;jump %for_loop
	jmp    computeSHA1_for_loop_8
computeSHA1_for_loop_8:
														;$t325 = move $t42
	mov    r12, r14
														;$t42 = add $t42 1
	mov    r11, 1
	add    r14, r11
														;jump %for_condition
	jmp    computeSHA1_for_condition_6
computeSHA1_for_after_9:
														;call __builtin_println $326
	mov    rax, CONST_STRING_326
	mov    rdi, rax
	call   __builtin_println
														;jump %exit
	jmp    computeSHA1_exit_10
computeSHA1_exit_10:
	mov    r13, qword [rbp + (-320)]
	mov    r12, qword [rbp + (-312)]
	mov    r14, qword [rbp + (-328)]
	mov    r15, qword [rbp + (-336)]
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
														;$t327 = seq $p44 122
	mov    r10, qword [rbp+(-8)]
	mov    r11, 122
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t327 %if_true %if_false
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
														;$t328 = seq $p44 90
	mov    r10, qword [rbp+(-8)]
	mov    r11, 90
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t328 %if_true %if_false
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
														;$t329 = seq $p44 57
	mov    r10, qword [rbp+(-8)]
	mov    r11, 57
	mov    r12, r10
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t329 %if_true %if_false
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
														;$t330 = add $p44 1
	mov    r10, qword [rbp+(-8)]
	mov    r11, 1
	mov    r12, r10
	add    r12, r11
														;ret $t330
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
	mov    qword [rbp + (-320)], r14
	mov    qword [rbp + (-328)], r15
nextText_enter_0:
														;jump %entry
	jmp    nextText_entry_1
nextText_entry_1:
														;$t331 = sub $p46 1
	mov    r10, qword [rbp+(-16)]
	mov    r11, 1
	mov    r12, r10
	sub    r12, r11
														;$t47 = move $t331
	mov    r14, r12
														;jump %for_condition
	jmp    nextText_for_condition_2
nextText_for_condition_2:
														;$t332 = sge $t47 0
	mov    r11, 0
	mov    r12, r14
	cmp    r12, r11
	setge   al
	movzx    r12, al
														;br $t332 %for_body %for_after
	cmp    r12, 0
	jnz    nextText_for_body_3
	jz     nextText_for_after_8
nextText_for_body_3:
														;$t334 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t333 = add $p45 $t334
	mov    r10, qword [rbp+(-8)]
	mov    r13, r10
	add    r13, r12
														;$t336 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t335 = add $p45 $t336
	mov    r10, qword [rbp+(-8)]
	mov    r15, r10
	add    r15, r12
														;$t337 = load 8 $t335 0
	mov    r11, r15
	mov    r12, qword [r11]
														;$t338 = call nextLetter $t337
	mov    rdi, r12
	call   nextLetter
	mov    r12, rax
														;store 8 $t333 $t338 0
	mov    r11, r13
	mov    qword [r11], r12
														;$t340 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t339 = add $p45 $t340
	mov    r10, qword [rbp+(-8)]
	mov    r13, r10
	add    r13, r12
														;$t341 = load 8 $t339 0
	mov    r11, r13
	mov    r12, qword [r11]
														;$t342 = seq $t341 -1
	mov    r11, -1
	cmp    r12, r11
	sete   al
	movzx    r12, al
														;br $t342 %if_true %if_false
	cmp    r12, 0
	jnz    nextText_if_true_4
	jz     nextText_if_false_5
nextText_if_true_4:
														;$t344 = mul $t47 8
	mov    r11, 8
	mov    r12, r14
	imul    r12, r11
														;$t343 = add $p45 $t344
	mov    r10, qword [rbp+(-8)]
	mov    r13, r10
	add    r13, r12
														;store 8 $t343 48 0
	mov    rax, 48
	mov    r11, r13
	mov    qword [r11], rax
														;jump %if_merge
	jmp    nextText_if_merge_6
nextText_if_merge_6:
														;jump %for_loop
	jmp    nextText_for_loop_7
nextText_for_loop_7:
														;$t345 = move $t47
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
	mov    r14, qword [rbp + (-320)]
	mov    r15, qword [rbp + (-328)]
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
	mov    qword [rbp + (-304)], r14
	mov    qword [rbp + (-312)], r15
array_equal_enter_0:
														;jump %entry
	jmp    array_equal_entry_1
array_equal_entry_1:
														;$t346 = call __builtin_getArraySize $p48
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getArraySize
	mov    r12, rax
														;$t347 = call __builtin_getArraySize $p49
	mov    rax, qword [rbp+(-16)]
	mov    rdi, rax
	call   __builtin_getArraySize
	mov    r13, rax
														;$t348 = sne $t346 $t347
	cmp    r12, r13
	setne   al
	movzx    r12, al
														;br $t348 %if_true %if_false
	cmp    r12, 0
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
	mov    r14, rax
														;jump %for_condition
	jmp    array_equal_for_condition_5
array_equal_for_condition_5:
														;$t349 = call __builtin_getArraySize $p48
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getArraySize
	mov    r12, rax
														;$t350 = slt $t50 $t349
	mov    r13, r14
	cmp    r13, r12
	setl   al
	movzx    r13, al
														;br $t350 %for_body %for_after
	cmp    r13, 0
	jnz    array_equal_for_body_6
	jz     array_equal_for_after_11
array_equal_for_body_6:
														;$t352 = mul $t50 8
	mov    r11, 8
	mov    r13, r14
	imul    r13, r11
														;$t351 = add $p48 $t352
	mov    r10, qword [rbp+(-8)]
	mov    r12, r10
	add    r12, r13
														;$t353 = load 8 $t351 0
	mov    r11, r12
	mov    r15, qword [r11]
														;$t355 = mul $t50 8
	mov    r11, 8
	mov    r13, r14
	imul    r13, r11
														;$t354 = add $p49 $t355
	mov    r10, qword [rbp+(-16)]
	mov    r12, r10
	add    r12, r13
														;$t356 = load 8 $t354 0
	mov    r11, r12
	mov    r13, qword [r11]
														;$t357 = sne $t353 $t356
	mov    r12, r15
	cmp    r12, r13
	setne   al
	movzx    r12, al
														;br $t357 %if_true %if_false
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
														;$t358 = move $t50
	mov    r12, r14
														;$t50 = add $t50 1
	mov    r11, 1
	add    r14, r11
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
	mov    r14, qword [rbp + (-304)]
	mov    r15, qword [rbp + (-312)]
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
	mov    qword [rbp + (-568)], r14
	mov    qword [rbp + (-576)], r15
crackSHA1_enter_0:
														;jump %entry
	jmp    crackSHA1_entry_1
crackSHA1_entry_1:
														;$t360 = move 40
	mov    rax, 40
	mov    r12, rax
														;$t360 = add $t360 8
	mov    r11, 8
	add    r12, r11
														;$t359 = alloc $t360
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rdi, r12
	call   malloc
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r14, rax
														;store 8 $t359 5 0
	mov    rax, 5
	mov    r11, r14
	mov    qword [r11], rax
														;$t359 = add $t359 8
	mov    r11, 8
	add    r14, r11
														;$t52 = move $t359
	mov    r14, r14
														;$t361 = call __builtin_getStringLength $p51
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	call   __builtin_getStringLength
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r12, rax
														;$t362 = sne $t361 40
	mov    r11, 40
	cmp    r12, r11
	setne   al
	movzx    r12, al
														;br $t362 %if_true %if_false
	cmp    r12, 0
	jnz    crackSHA1_if_true_2
	jz     crackSHA1_if_false_3
crackSHA1_if_true_2:
														;call __builtin_println $363
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, CONST_STRING_363
	mov    rdi, rax
	call   __builtin_println
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_if_false_3:
														;jump %if_merge
	jmp    crackSHA1_if_merge_4
crackSHA1_if_merge_4:
														;$t53 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_5
crackSHA1_for_condition_5:
														;$t364 = slt $t53 5
	mov    r11, 5
	mov    r12, r15
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t364 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_6
	jz     crackSHA1_for_after_8
crackSHA1_for_body_6:
														;$t366 = mul $t53 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t365 = add $t52 $t366
	mov    r13, r14
	add    r13, r12
														;store 8 $t365 0 0
	mov    rax, 0
	mov    r11, r13
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_7
crackSHA1_for_loop_7:
														;$t367 = move $t53
	mov    r12, r15
														;$t53 = add $t53 1
	mov    r11, 1
	add    r15, r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_5
crackSHA1_for_after_8:
														;$t53 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_9
crackSHA1_for_condition_9:
														;$t368 = slt $t53 40
	mov    r11, 40
	mov    r12, r15
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t368 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_10
	jz     crackSHA1_for_after_12
crackSHA1_for_body_10:
														;$t369 = div $t53 8
	mov    r11, 8
	mov    r12, r15
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t371 = mul $t369 8
	mov    r11, 8
	imul    r12, r11
														;$t370 = add $t52 $t371
	mov    rbx, r14
	add    rbx, r12
														;$t372 = div $t53 8
	mov    r11, 8
	mov    r12, r15
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t374 = mul $t372 8
	mov    r11, 8
	imul    r12, r11
														;$t373 = add $t52 $t374
	mov    r13, r14
	add    r13, r12
														;$t375 = load 8 $t373 0
	mov    r11, r13
	mov    rsi, qword [r11]
														;$t376 = add $t53 3
	mov    r11, 3
	mov    r12, r15
	add    r12, r11
														;$t377 = call __builtin_getSubstring $p51 $t53 $t376
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, qword [rbp+(-8)]
	mov    rdi, rax
	mov    rsi, r15
	mov    rdx, r12
	call   __builtin_getSubstring
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r12, rax
														;$t378 = call hex2int $t377
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rdi, r12
	call   hex2int
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    rdi, rax
														;$t379 = div $t53 4
	mov    r11, 4
	mov    r12, r15
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rax
														;$t380 = rem $t379 2
	mov    r11, 2
	mov    rax, r12
	cqo
	idiv   r11
	mov    r12, rdx
														;$t381 = sub 1 $t380
	mov    r10, 1
	mov    r13, r10
	sub    r13, r12
														;$t382 = mul $t381 16
	mov    r11, 16
	imul    r13, r11
														;$t383 = shl $t378 $t382
	mov    r12, rdi
	mov    rcx, r13
	sal    r12, cl
														;$t384 = or $t375 $t383
	mov    r13, rsi
	or     r13, r12
														;store 8 $t370 $t384 0
	mov    r11, rbx
	mov    qword [r11], r13
														;jump %for_loop
	jmp    crackSHA1_for_loop_11
crackSHA1_for_loop_11:
														;$t385 = add $t53 4
	mov    r11, 4
	add    r15, r11
														;$t53 = move $t385
	mov    r15, r15
														;jump %for_condition
	jmp    crackSHA1_for_condition_9
crackSHA1_for_after_12:
														;$t54 = move 4
	mov    rax, 4
	mov    rbx, rax
														;$t55 = move 1
	mov    rax, 1
	mov    r13, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_13
crackSHA1_for_condition_13:
														;$t386 = sle $t55 $t54
	mov    r12, r13
	cmp    r12, rbx
	setle   al
	movzx    r12, al
														;br $t386 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_14
	jz     crackSHA1_for_after_33
crackSHA1_for_body_14:
														;$t53 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_15
crackSHA1_for_condition_15:
														;$t387 = slt $t53 $t55
	mov    r12, r15
	cmp    r12, r13
	setl   al
	movzx    r12, al
														;br $t387 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_16
	jz     crackSHA1_for_after_18
crackSHA1_for_body_16:
														;$t389 = mul $t53 8
	mov    r11, 8
	mov    r12, r15
	imul    r12, r11
														;$t388 = add $g21(inputBuffer) $t389
	mov    r10, qword [rel GV_inputBuffer]
	mov    rsi, r10
	add    rsi, r12
														;store 8 $t388 48 0
	mov    rax, 48
	mov    r11, rsi
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_17
crackSHA1_for_loop_17:
														;$t390 = move $t53
	mov    r12, r15
														;$t53 = add $t53 1
	mov    r11, 1
	add    r15, r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_15
crackSHA1_for_after_18:
														;jump %while_loop
	jmp    crackSHA1_while_loop_19
crackSHA1_while_loop_19:
														;jump %while_body
	jmp    crackSHA1_while_body_20
crackSHA1_while_body_20:
														;$t391 = call sha1 $g21(inputBuffer) $t55
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, qword [rel GV_inputBuffer]
	mov    rdi, rax
	mov    rsi, r13
	call   sha1
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r12, rax
														;$t56 = move $t391
	mov    r12, r12
														;$t392 = call array_equal $t56 $t52
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rdi, r12
	mov    rsi, r14
	call   array_equal
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r12, rax
														;br $t392 %if_true %if_false
	cmp    r12, 0
	jnz    crackSHA1_if_true_21
	jz     crackSHA1_if_false_26
crackSHA1_if_true_21:
														;$t53 = move 0
	mov    rax, 0
	mov    r15, rax
														;jump %for_condition
	jmp    crackSHA1_for_condition_22
crackSHA1_for_condition_22:
														;$t393 = slt $t53 $t55
	mov    r12, r15
	cmp    r12, r13
	setl   al
	movzx    r12, al
														;br $t393 %for_body %for_after
	cmp    r12, 0
	jnz    crackSHA1_for_body_23
	jz     crackSHA1_for_after_25
crackSHA1_for_body_23:
														;$t395 = mul $t53 8
	mov    r11, 8
	mov    r14, r15
	imul    r14, r11
														;$t394 = add $g21(inputBuffer) $t395
	mov    r10, qword [rel GV_inputBuffer]
	mov    r12, r10
	add    r12, r14
														;$t396 = load 8 $t394 0
	mov    r11, r12
	mov    r12, qword [r11]
														;$t397 = call int2chr $t396
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rdi, r12
	call   int2chr
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r12, rax
														;call __builtin_print $t397
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rdi, r12
	call   __builtin_print
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
														;jump %for_loop
	jmp    crackSHA1_for_loop_24
crackSHA1_for_loop_24:
														;$t398 = move $t53
	mov    r12, r15
														;$t53 = add $t53 1
	mov    r11, 1
	add    r15, r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_22
crackSHA1_for_after_25:
														;call __builtin_println $399
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, CONST_STRING_399
	mov    rdi, rax
	call   __builtin_println
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_if_false_26:
														;jump %if_merge
	jmp    crackSHA1_if_merge_27
crackSHA1_if_merge_27:
														;$t400 = call nextText $g21(inputBuffer) $t55
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, qword [rel GV_inputBuffer]
	mov    rdi, rax
	mov    rsi, r13
	call   nextText
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
	mov    r12, rax
														;$t401 = xor $t400 1
	mov    r11, 1
	xor     r12, r11
														;br $t401 %if_true %if_false
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
														;$t402 = move $t55
	mov    r12, r13
														;$t55 = add $t55 1
	mov    r11, 1
	add    r13, r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_13
crackSHA1_if_false_29:
														;jump %if_merge
	jmp    crackSHA1_if_merge_30
crackSHA1_if_merge_30:
														;jump %while_loop
	jmp    crackSHA1_while_loop_19
crackSHA1_for_after_33:
														;call __builtin_println $403
	mov    qword [rbp + (-512)], rdi
	mov    qword [rbp + (-504)], rsi
	mov    rax, CONST_STRING_403
	mov    rdi, rax
	call   __builtin_println
	mov    rdi, qword [rbp + (-512)]
	mov    rsi, qword [rbp + (-504)]
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_exit_34:
	mov    r13, qword [rbp + (-560)]
	mov    r12, qword [rbp + (-552)]
	mov    rbx, qword [rbp + (-480)]
	mov    r14, qword [rbp + (-568)]
	mov    r15, qword [rbp + (-576)]
	leave
	ret
main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 352
	mov    qword [rbp + (-312)], r13
	mov    qword [rbp + (-232)], rbx
	mov    qword [rbp + (-304)], r12
	mov    qword [rbp + (-320)], r14
	mov    qword [rbp + (-328)], r15
main_enter_0:
														;$g4(asciiTable) = move $404
	mov    rax, CONST_STRING_404
	mov    qword [rel GV_asciiTable], rax
														;$g18(MAXCHUNK) = move 100
	mov    rax, 100
	mov    qword [rel GV_MAXCHUNK], rax
														;$t405 = sub $g18(MAXCHUNK) 1
	mov    r10, qword [rel GV_MAXCHUNK]
	mov    r11, 1
	mov    r12, r10
	sub    r12, r11
														;$t406 = mul $t405 64
	mov    r11, 64
	imul    r12, r11
														;$t407 = sub $t406 16
	mov    r11, 16
	sub    r12, r11
														;$g19(MAXLENGTH) = move $t407
	mov    qword [rel GV_MAXLENGTH], r12
														;$t409 = mul $g18(MAXCHUNK) 8
	mov    r10, qword [rel GV_MAXCHUNK]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t409 = add $t409 8
	mov    r11, 8
	add    r12, r11
														;$t408 = alloc $t409
	mov    rdi, r12
	call   malloc
	mov    r13, rax
														;store 8 $t408 $g18(MAXCHUNK) 0
	mov    rax, qword [rel GV_MAXCHUNK]
	mov    r11, r13
	mov    qword [r11], rax
														;$t408 = add $t408 8
	mov    r11, 8
	add    r13, r11
														;$t410 = move 0
	mov    rax, 0
	mov    rbx, rax
														;jump %new_condition
	jmp    main_new_condition_1
main_new_condition_1:
														;$t411 = slt $t410 $g18(MAXCHUNK)
	mov    r11, qword [rel GV_MAXCHUNK]
	mov    r12, rbx
	cmp    r12, r11
	setl   al
	movzx    r12, al
														;br $t411 %new_body %new_exit
	cmp    r12, 0
	jnz    main_new_body_2
	jz     main_new_exit_4
main_new_body_2:
														;$t413 = move 640
	mov    rax, 640
	mov    r12, rax
														;$t413 = add $t413 8
	mov    r11, 8
	add    r12, r11
														;$t412 = alloc $t413
	mov    rdi, r12
	call   malloc
	mov    r14, rax
														;store 8 $t412 80 0
	mov    rax, 80
	mov    r11, r14
	mov    qword [r11], rax
														;$t412 = add $t412 8
	mov    r11, 8
	add    r14, r11
														;$t414 = mul $t410 8
	mov    r11, 8
	mov    r15, rbx
	imul    r15, r11
														;$t415 = add $t408 $t414
	mov    r12, r13
	add    r12, r15
														;store 8 $t415 $t412 0
	mov    r11, r12
	mov    qword [r11], r14
														;jump %new_loop
	jmp    main_new_loop_3
main_new_loop_3:
														;$t410 = add $t410 1
	mov    r11, 1
	add    rbx, r11
														;jump %new_condition
	jmp    main_new_condition_1
main_new_exit_4:
														;$g20(chunks) = move $t408
	mov    qword [rel GV_chunks], r13
														;$t417 = mul $g19(MAXLENGTH) 8
	mov    r10, qword [rel GV_MAXLENGTH]
	mov    r11, 8
	mov    r12, r10
	imul    r12, r11
														;$t417 = add $t417 8
	mov    r11, 8
	add    r12, r11
														;$t416 = alloc $t417
	mov    rdi, r12
	call   malloc
	mov    r12, rax
														;store 8 $t416 $g19(MAXLENGTH) 0
	mov    rax, qword [rel GV_MAXLENGTH]
	mov    r11, r12
	mov    qword [r11], rax
														;$t416 = add $t416 8
	mov    r11, 8
	add    r12, r11
														;$g21(inputBuffer) = move $t416
	mov    qword [rel GV_inputBuffer], r12
														;$t419 = move 40
	mov    rax, 40
	mov    r12, rax
														;$t419 = add $t419 8
	mov    r11, 8
	add    r12, r11
														;$t418 = alloc $t419
	mov    rdi, r12
	call   malloc
	mov    r12, rax
														;store 8 $t418 5 0
	mov    rax, 5
	mov    r11, r12
	mov    qword [r11], rax
														;$t418 = add $t418 8
	mov    r11, 8
	add    r12, r11
														;$g22(outputBuffer) = move $t418
	mov    qword [rel GV_outputBuffer], r12
														;jump %entry
	jmp    main_entry_5
main_entry_5:
														;$t57 = move $420
	mov    rax, CONST_STRING_420
	mov    r12, rax
														;call crackSHA1 $t57
	mov    rdi, r12
	call   crackSHA1
														;ret 0
	mov    rax, 0
	jmp    main_exit_6
														;jump %exit
	jmp    main_exit_6
main_exit_6:
	mov    r13, qword [rbp + (-312)]
	mov    rbx, qword [rbp + (-232)]
	mov    r12, qword [rbp + (-304)]
	mov    r14, qword [rbp + (-320)]
	mov    r15, qword [rbp + (-328)]
	leave
	ret
SECTION .data
CONST_STRING_89:
	db 0
CONST_STRING_403:
	db 78, 111, 116, 32, 70, 111, 117, 110, 100, 33, 0
GV_MAXCHUNK:
	dq 0
GV_MAXLENGTH:
	dq 0
CONST_STRING_146:
	db 110, 67, 104, 117, 110, 107, 32, 62, 32, 77, 65, 88, 67, 72, 85, 78, 75, 33, 0
GV_outputBuffer:
	dq 0
CONST_STRING_404:
	db 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 0
GV_inputBuffer:
	dq 0
CONST_STRING_88:
	db 0
CONST_STRING_363:
	db 73, 110, 118, 97, 108, 105, 100, 32, 105, 110, 112, 117, 116, 0
GV_chunks:
	dq 0
GV_asciiTable:
	dq 0
CONST_STRING_420:
	db 48, 52, 69, 56, 54, 57, 54, 69, 54, 52, 50, 52, 67, 50, 49, 68, 55, 49, 55, 69, 52, 54, 48, 48, 56, 55, 56, 48, 53, 48, 53, 68, 53, 57, 56, 69, 66, 53, 57, 65, 0
CONST_STRING_399:
	db 0
CONST_STRING_326:
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

