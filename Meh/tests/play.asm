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
	sub    rsp, 280
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
hex2int_enter_0:
														;jump %entry
	jmp    hex2int_entry_1
hex2int_entry_1:
														;$t2 = move 0
	mov    r11, 0
	mov    qword [rbp+(-184)], r11
														;$t1 = move 0
	mov    r11, 0
	mov    qword [rbp+(-120)], r11
														;jump %for_condition
	jmp    hex2int_for_condition_2
hex2int_for_condition_2:
														;$t59 = call __builtin_getStringLength $p0
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getStringLength
	mov    qword [rbp+(-192)], rax
														;$t60 = slt $t1 $t59
	mov    r11, qword [rbp+(-120)]
	cmp    r11, qword [rbp+(-192)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-144)], r11
														;br $t60 %for_body %for_after
	cmp    qword [rbp+(-144)], 0
	jnz    hex2int_for_body_3
	jz     hex2int_for_after_23
hex2int_for_body_3:
														;$t61 = call __builtin_ord $p0 $t1
	mov    rdi, qword [rbp+(-8)]
	mov    rsi, qword [rbp+(-120)]
	call   __builtin_ord
	mov    qword [rbp+(-200)], rax
														;$t3 = move $t61
	mov    r11, qword [rbp+(-200)]
	mov    qword [rbp+(-224)], r11
														;$t63 = sge $t3 48
	mov    r11, qword [rbp+(-224)]
	cmp    r11, 48
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t63 %logical_true %logical_false
	cmp    qword [rbp+(-64)], 0
	jnz    hex2int_logical_true_4
	jz     hex2int_logical_false_5
hex2int_logical_true_4:
														;$t64 = sle $t3 57
	mov    r11, qword [rbp+(-224)]
	cmp    r11, 57
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;$t62 = move $t64
	mov    r11, qword [rbp+(-136)]
	mov    qword [rbp+(-232)], r11
														;jump %logical_merge
	jmp    hex2int_logical_merge_6
hex2int_logical_merge_6:
														;br $t62 %if_true %if_false
	cmp    qword [rbp+(-232)], 0
	jnz    hex2int_if_true_7
	jz     hex2int_if_false_8
hex2int_if_true_7:
														;$t65 = mul $t2 16
	mov    r11, qword [rbp+(-184)]
	imul   r11, 16
	mov    qword [rbp+(-168)], r11
														;$t66 = add $t65 $t3
	mov    r11, qword [rbp+(-168)]
	add    r11, qword [rbp+(-224)]
	mov    qword [rbp+(-112)], r11
														;$t67 = sub $t66 48
	mov    r11, qword [rbp+(-112)]
	sub    r11, 48
	mov    qword [rbp+(-72)], r11
														;$t2 = move $t67
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    hex2int_if_merge_21
hex2int_if_merge_21:
														;jump %for_loop
	jmp    hex2int_for_loop_22
hex2int_for_loop_22:
														;$t82 = move $t1
	mov    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-272)], r11
														;$t1 = add $t1 1
	mov    r11, qword [rbp+(-120)]
	add    r11, 1
	mov    qword [rbp+(-120)], r11
														;jump %for_condition
	jmp    hex2int_for_condition_2
hex2int_if_false_8:
														;$t69 = sge $t3 65
	mov    r11, qword [rbp+(-224)]
	cmp    r11, 65
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-208)], r11
														;br $t69 %logical_true %logical_false
	cmp    qword [rbp+(-208)], 0
	jnz    hex2int_logical_true_9
	jz     hex2int_logical_false_10
hex2int_logical_true_9:
														;$t70 = sle $t3 70
	mov    r11, qword [rbp+(-224)]
	cmp    r11, 70
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;$t68 = move $t70
	mov    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-248)], r11
														;jump %logical_merge
	jmp    hex2int_logical_merge_11
hex2int_logical_merge_11:
														;br $t68 %if_true %if_false
	cmp    qword [rbp+(-248)], 0
	jnz    hex2int_if_true_12
	jz     hex2int_if_false_13
hex2int_if_true_12:
														;$t71 = mul $t2 16
	mov    r11, qword [rbp+(-184)]
	imul   r11, 16
	mov    qword [rbp+(-160)], r11
														;$t72 = add $t71 $t3
	mov    r11, qword [rbp+(-160)]
	add    r11, qword [rbp+(-224)]
	mov    qword [rbp+(-88)], r11
														;$t73 = sub $t72 65
	mov    r11, qword [rbp+(-88)]
	sub    r11, 65
	mov    qword [rbp+(-104)], r11
														;$t74 = add $t73 10
	mov    r11, qword [rbp+(-104)]
	add    r11, 10
	mov    qword [rbp+(-240)], r11
														;$t2 = move $t74
	mov    r11, qword [rbp+(-240)]
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    hex2int_if_merge_20
hex2int_if_merge_20:
														;jump %if_merge
	jmp    hex2int_if_merge_21
hex2int_if_false_13:
														;$t76 = sge $t3 97
	mov    r11, qword [rbp+(-224)]
	cmp    r11, 97
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-128)], r11
														;br $t76 %logical_true %logical_false
	cmp    qword [rbp+(-128)], 0
	jnz    hex2int_logical_true_14
	jz     hex2int_logical_false_15
hex2int_logical_true_14:
														;$t77 = sle $t3 102
	mov    r11, qword [rbp+(-224)]
	cmp    r11, 102
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-96)], r11
														;$t75 = move $t77
	mov    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-264)], r11
														;jump %logical_merge
	jmp    hex2int_logical_merge_16
hex2int_logical_merge_16:
														;br $t75 %if_true %if_false
	cmp    qword [rbp+(-264)], 0
	jnz    hex2int_if_true_17
	jz     hex2int_if_false_18
hex2int_if_true_17:
														;$t78 = mul $t2 16
	mov    r11, qword [rbp+(-184)]
	imul   r11, 16
	mov    qword [rbp+(-152)], r11
														;$t79 = add $t78 $t3
	mov    r11, qword [rbp+(-152)]
	add    r11, qword [rbp+(-224)]
	mov    qword [rbp+(-176)], r11
														;$t80 = sub $t79 97
	mov    r11, qword [rbp+(-176)]
	sub    r11, 97
	mov    qword [rbp+(-216)], r11
														;$t81 = add $t80 10
	mov    r11, qword [rbp+(-216)]
	add    r11, 10
	mov    qword [rbp+(-256)], r11
														;$t2 = move $t81
	mov    r11, qword [rbp+(-256)]
	mov    qword [rbp+(-184)], r11
														;jump %if_merge
	jmp    hex2int_if_merge_19
hex2int_if_merge_19:
														;jump %if_merge
	jmp    hex2int_if_merge_20
hex2int_if_false_18:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    hex2int_exit_24
hex2int_logical_false_15:
														;$t75 = move 0
	mov    r11, 0
	mov    qword [rbp+(-264)], r11
														;jump %logical_merge
	jmp    hex2int_logical_merge_16
hex2int_logical_false_10:
														;$t68 = move 0
	mov    r11, 0
	mov    qword [rbp+(-248)], r11
														;jump %logical_merge
	jmp    hex2int_logical_merge_11
hex2int_logical_false_5:
														;$t62 = move 0
	mov    r11, 0
	mov    qword [rbp+(-232)], r11
														;jump %logical_merge
	jmp    hex2int_logical_merge_6
hex2int_for_after_23:
														;ret $t2
	mov    rax, qword [rbp+(-184)]
	leave
	ret
														;jump %exit
	jmp    hex2int_exit_24
hex2int_exit_24:
	leave
	ret


int2chr:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 112
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
int2chr_enter_0:
														;jump %entry
	jmp    int2chr_entry_1
int2chr_entry_1:
														;$t84 = sge $p5 32
	mov    r11, qword [rbp+(-8)]
	cmp    r11, 32
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-96)], r11
														;br $t84 %logical_true %logical_false
	cmp    qword [rbp+(-96)], 0
	jnz    int2chr_logical_true_2
	jz     int2chr_logical_false_3
int2chr_logical_true_2:
														;$t85 = sle $p5 126
	mov    r11, qword [rbp+(-8)]
	cmp    r11, 126
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-104)], r11
														;$t83 = move $t85
	mov    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-88)], r11
														;jump %logical_merge
	jmp    int2chr_logical_merge_4
int2chr_logical_merge_4:
														;br $t83 %if_true %if_false
	cmp    qword [rbp+(-88)], 0
	jnz    int2chr_if_true_5
	jz     int2chr_if_false_6
int2chr_if_true_5:
														;$t86 = sub $p5 32
	mov    r11, qword [rbp+(-8)]
	sub    r11, 32
	mov    qword [rbp+(-64)], r11
														;$t87 = sub $p5 32
	mov    r11, qword [rbp+(-8)]
	sub    r11, 32
	mov    qword [rbp+(-80)], r11
														;$t88 = call __builtin_getSubstring $g4(asciiTable) $t86 $t87
	mov    rdi, qword [rel GV_asciiTable]
	mov    rsi, qword [rbp+(-64)]
	mov    rdx, qword [rbp+(-80)]
	call   __builtin_getSubstring
	mov    qword [rbp+(-72)], rax
														;ret $t88
	mov    rax, qword [rbp+(-72)]
	leave
	ret
														;jump %exit
	jmp    int2chr_exit_8
int2chr_if_false_6:
														;jump %if_merge
	jmp    int2chr_if_merge_7
int2chr_if_merge_7:
														;ret $89
	mov    rax, CONST_STRING_89
	leave
	ret
														;jump %exit
	jmp    int2chr_exit_8
int2chr_logical_false_3:
														;$t83 = move 0
	mov    r11, 0
	mov    qword [rbp+(-88)], r11
														;jump %logical_merge
	jmp    int2chr_logical_merge_4
int2chr_exit_8:
	leave
	ret


toStringHex:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 184
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
toStringHex_enter_0:
														;jump %entry
	jmp    toStringHex_entry_1
toStringHex_entry_1:
														;$t7 = move $90
	mov    r11, CONST_STRING_90
	mov    qword [rbp+(-88)], r11
														;$t8 = move 28
	mov    r11, 28
	mov    qword [rbp+(-136)], r11
														;jump %for_condition
	jmp    toStringHex_for_condition_2
toStringHex_for_condition_2:
														;$t91 = sge $t8 0
	mov    r11, qword [rbp+(-136)]
	cmp    r11, 0
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-168)], r11
														;br $t91 %for_body %for_after
	cmp    qword [rbp+(-168)], 0
	jnz    toStringHex_for_body_3
	jz     toStringHex_for_after_8
toStringHex_for_body_3:
														;$t92 = shr $p6 $t8
	mov    r11, qword [rbp+(-8)]
	mov    rcx, qword [rbp+(-136)]
	sar    r11, cl
	mov    qword [rbp+(-96)], r11
														;$t93 = and $t92 15
	mov    r11, qword [rbp+(-96)]
	and    r11, 15
	mov    qword [rbp+(-120)], r11
														;$t9 = move $t93
	mov    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-128)], r11
														;$t94 = slt $t9 10
	mov    r11, qword [rbp+(-128)]
	cmp    r11, 10
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t94 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    toStringHex_if_true_4
	jz     toStringHex_if_false_5
toStringHex_if_true_4:
														;$t95 = add $t9 48
	mov    r11, qword [rbp+(-128)]
	add    r11, 48
	mov    qword [rbp+(-80)], r11
														;$t96 = call int2chr $t95
	mov    rdi, qword [rbp+(-80)]
	call   int2chr
	mov    qword [rbp+(-112)], rax
														;$t97 = call __builtin_string_concat $t7 $t96
	mov    rdi, qword [rbp+(-88)]
	mov    rsi, qword [rbp+(-112)]
	call   __builtin_string_concat
	mov    qword [rbp+(-72)], rax
														;$t7 = move $t97
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-88)], r11
														;jump %if_merge
	jmp    toStringHex_if_merge_6
toStringHex_if_merge_6:
														;jump %for_loop
	jmp    toStringHex_for_loop_7
toStringHex_for_loop_7:
														;$t102 = sub $t8 4
	mov    r11, qword [rbp+(-136)]
	sub    r11, 4
	mov    qword [rbp+(-144)], r11
														;$t8 = move $t102
	mov    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-136)], r11
														;jump %for_condition
	jmp    toStringHex_for_condition_2
toStringHex_if_false_5:
														;$t98 = add $t9 65
	mov    r11, qword [rbp+(-128)]
	add    r11, 65
	mov    qword [rbp+(-104)], r11
														;$t99 = sub $t98 10
	mov    r11, qword [rbp+(-104)]
	sub    r11, 10
	mov    qword [rbp+(-176)], r11
														;$t100 = call int2chr $t99
	mov    rdi, qword [rbp+(-176)]
	call   int2chr
	mov    qword [rbp+(-160)], rax
														;$t101 = call __builtin_string_concat $t7 $t100
	mov    rdi, qword [rbp+(-88)]
	mov    rsi, qword [rbp+(-160)]
	call   __builtin_string_concat
	mov    qword [rbp+(-152)], rax
														;$t7 = move $t101
	mov    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-88)], r11
														;jump %if_merge
	jmp    toStringHex_if_merge_6
toStringHex_for_after_8:
														;ret $t7
	mov    rax, qword [rbp+(-88)]
	leave
	ret
														;jump %exit
	jmp    toStringHex_exit_9
toStringHex_exit_9:
	leave
	ret


rotate_left:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 248
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
rotate_left_enter_0:
														;jump %entry
	jmp    rotate_left_entry_1
rotate_left_entry_1:
														;$t103 = seq $p11 1
	mov    r11, qword [rbp+(-16)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-160)], r11
														;br $t103 %if_true %if_false
	cmp    qword [rbp+(-160)], 0
	jnz    rotate_left_if_true_2
	jz     rotate_left_if_false_3
rotate_left_if_true_2:
														;$t104 = and $p10 2147483647
	mov    r11, qword [rbp+(-8)]
	and    r11, 2147483647
	mov    qword [rbp+(-208)], r11
														;$t105 = shl $t104 1
	mov    r11, qword [rbp+(-208)]
	mov    rcx, 1
	sal    r11, cl
	mov    qword [rbp+(-128)], r11
														;$t106 = shr $p10 31
	mov    r11, qword [rbp+(-8)]
	mov    rcx, 31
	sar    r11, cl
	mov    qword [rbp+(-192)], r11
														;$t107 = and $t106 1
	mov    r11, qword [rbp+(-192)]
	and    r11, 1
	mov    qword [rbp+(-200)], r11
														;$t108 = or $t105 $t107
	mov    r11, qword [rbp+(-128)]
	or    r11, qword [rbp+(-200)]
	mov    qword [rbp+(-224)], r11
														;ret $t108
	mov    rax, qword [rbp+(-224)]
	leave
	ret
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_if_false_3:
														;jump %if_merge
	jmp    rotate_left_if_merge_4
rotate_left_if_merge_4:
														;$t109 = seq $p11 31
	mov    r11, qword [rbp+(-16)]
	cmp    r11, 31
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t109 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    rotate_left_if_true_5
	jz     rotate_left_if_false_6
rotate_left_if_true_5:
														;$t110 = and $p10 1
	mov    r11, qword [rbp+(-8)]
	and    r11, 1
	mov    qword [rbp+(-72)], r11
														;$t111 = shl $t110 31
	mov    r11, qword [rbp+(-72)]
	mov    rcx, 31
	sal    r11, cl
	mov    qword [rbp+(-176)], r11
														;$t112 = shr $p10 1
	mov    r11, qword [rbp+(-8)]
	mov    rcx, 1
	sar    r11, cl
	mov    qword [rbp+(-88)], r11
														;$t113 = and $t112 2147483647
	mov    r11, qword [rbp+(-88)]
	and    r11, 2147483647
	mov    qword [rbp+(-144)], r11
														;$t114 = or $t111 $t113
	mov    r11, qword [rbp+(-176)]
	or    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-232)], r11
														;ret $t114
	mov    rax, qword [rbp+(-232)]
	leave
	ret
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_if_false_6:
														;jump %if_merge
	jmp    rotate_left_if_merge_7
rotate_left_if_merge_7:
														;$t115 = sub 32 $p11
	mov    r11, 32
	sub    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-112)], r11
														;$t116 = shl 1 $t115
	mov    r11, 1
	mov    rcx, qword [rbp+(-112)]
	sal    r11, cl
	mov    qword [rbp+(-136)], r11
														;$t117 = sub $t116 1
	mov    r11, qword [rbp+(-136)]
	sub    r11, 1
	mov    qword [rbp+(-120)], r11
														;$t118 = and $p10 $t117
	mov    r11, qword [rbp+(-8)]
	and    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-80)], r11
														;$t119 = shl $t118 $p11
	mov    r11, qword [rbp+(-80)]
	mov    rcx, qword [rbp+(-16)]
	sal    r11, cl
	mov    qword [rbp+(-240)], r11
														;$t120 = sub 32 $p11
	mov    r11, 32
	sub    r11, qword [rbp+(-16)]
	mov    qword [rbp+(-168)], r11
														;$t121 = shr $p10 $t120
	mov    r11, qword [rbp+(-8)]
	mov    rcx, qword [rbp+(-168)]
	sar    r11, cl
	mov    qword [rbp+(-104)], r11
														;$t122 = shl 1 $p11
	mov    r11, 1
	mov    rcx, qword [rbp+(-16)]
	sal    r11, cl
	mov    qword [rbp+(-216)], r11
														;$t123 = sub $t122 1
	mov    r11, qword [rbp+(-216)]
	sub    r11, 1
	mov    qword [rbp+(-184)], r11
														;$t124 = and $t121 $t123
	mov    r11, qword [rbp+(-104)]
	and    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-96)], r11
														;$t125 = or $t119 $t124
	mov    r11, qword [rbp+(-240)]
	or    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-152)], r11
														;ret $t125
	mov    rax, qword [rbp+(-152)]
	leave
	ret
														;jump %exit
	jmp    rotate_left_exit_8
rotate_left_exit_8:
	leave
	ret


add:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 192
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
add_enter_0:
														;jump %entry
	jmp    add_entry_1
add_entry_1:
														;$t126 = and $p12 65535
	mov    r11, qword [rbp+(-8)]
	and    r11, 65535
	mov    qword [rbp+(-168)], r11
														;$t127 = and $p13 65535
	mov    r11, qword [rbp+(-16)]
	and    r11, 65535
	mov    qword [rbp+(-136)], r11
														;$t128 = add $t126 $t127
	mov    r11, qword [rbp+(-168)]
	add    r11, qword [rbp+(-136)]
	mov    qword [rbp+(-104)], r11
														;$t14 = move $t128
	mov    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-80)], r11
														;$t129 = shr $p12 16
	mov    r11, qword [rbp+(-8)]
	mov    rcx, 16
	sar    r11, cl
	mov    qword [rbp+(-112)], r11
														;$t130 = and $t129 65535
	mov    r11, qword [rbp+(-112)]
	and    r11, 65535
	mov    qword [rbp+(-176)], r11
														;$t131 = shr $p13 16
	mov    r11, qword [rbp+(-16)]
	mov    rcx, 16
	sar    r11, cl
	mov    qword [rbp+(-184)], r11
														;$t132 = and $t131 65535
	mov    r11, qword [rbp+(-184)]
	and    r11, 65535
	mov    qword [rbp+(-64)], r11
														;$t133 = add $t130 $t132
	mov    r11, qword [rbp+(-176)]
	add    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-96)], r11
														;$t134 = shr $t14 16
	mov    r11, qword [rbp+(-80)]
	mov    rcx, 16
	sar    r11, cl
	mov    qword [rbp+(-128)], r11
														;$t135 = add $t133 $t134
	mov    r11, qword [rbp+(-96)]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-160)], r11
														;$t136 = and $t135 65535
	mov    r11, qword [rbp+(-160)]
	and    r11, 65535
	mov    qword [rbp+(-72)], r11
														;$t15 = move $t136
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-144)], r11
														;$t137 = shl $t15 16
	mov    r11, qword [rbp+(-144)]
	mov    rcx, 16
	sal    r11, cl
	mov    qword [rbp+(-120)], r11
														;$t138 = and $t14 65535
	mov    r11, qword [rbp+(-80)]
	and    r11, 65535
	mov    qword [rbp+(-152)], r11
														;$t139 = or $t137 $t138
	mov    r11, qword [rbp+(-120)]
	or    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-88)], r11
														;ret $t139
	mov    rax, qword [rbp+(-88)]
	leave
	ret
														;jump %exit
	jmp    add_exit_2
add_exit_2:
	leave
	ret


lohi:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 80
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
lohi_enter_0:
														;jump %entry
	jmp    lohi_entry_1
lohi_entry_1:
														;$t140 = shl $p17 16
	mov    r11, qword [rbp+(-16)]
	mov    rcx, 16
	sal    r11, cl
	mov    qword [rbp+(-72)], r11
														;$t141 = or $p16 $t140
	mov    r11, qword [rbp+(-8)]
	or    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-64)], r11
														;ret $t141
	mov    rax, qword [rbp+(-64)]
	leave
	ret
														;jump %exit
	jmp    lohi_exit_2
lohi_exit_2:
	leave
	ret


sha1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 1544
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
sha1_enter_0:
														;jump %entry
	jmp    sha1_entry_1
sha1_entry_1:
														;$t142 = add $p24 64
	mov    r11, qword [rbp+(-16)]
	add    r11, 64
	mov    qword [rbp+(-1280)], r11
														;$t143 = sub $t142 56
	mov    r11, qword [rbp+(-1280)]
	sub    r11, 56
	mov    qword [rbp+(-688)], r11
														;$t144 = div $t143 64
	mov    r11, qword [rbp+(-688)]
	mov    rax, qword [rbp+(-688)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-1256)], r11
														;$t145 = add $t144 1
	mov    r11, qword [rbp+(-1256)]
	add    r11, 1
	mov    qword [rbp+(-72)], r11
														;$t25 = move $t145
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-376)], r11
														;$t146 = sgt $t25 $g18(MAXCHUNK)
	mov    r11, qword [rbp+(-376)]
	cmp    r11, qword [rel GV_MAXCHUNK]
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-1216)], r11
														;br $t146 %if_true %if_false
	cmp    qword [rbp+(-1216)], 0
	jnz    sha1_if_true_2
	jz     sha1_if_false_3
sha1_if_true_2:
														;call __builtin_println $147
	mov    rdi, CONST_STRING_147
	call   __builtin_println
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    sha1_exit_38
sha1_if_false_3:
														;jump %if_merge
	jmp    sha1_if_merge_4
sha1_if_merge_4:
														;$t26 = move 0
	mov    r11, 0
	mov    qword [rbp+(-816)], r11
														;jump %for_condition
	jmp    sha1_for_condition_5
sha1_for_condition_5:
														;$t148 = slt $t26 $t25
	mov    r11, qword [rbp+(-816)]
	cmp    r11, qword [rbp+(-376)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t148 %for_body %for_after
	cmp    qword [rbp+(-80)], 0
	jnz    sha1_for_body_6
	jz     sha1_for_after_12
sha1_for_body_6:
														;$t27 = move 0
	mov    r11, 0
	mov    qword [rbp+(-264)], r11
														;jump %for_condition
	jmp    sha1_for_condition_7
sha1_for_condition_7:
														;$t149 = slt $t27 80
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 80
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-568)], r11
														;br $t149 %for_body %for_after
	cmp    qword [rbp+(-568)], 0
	jnz    sha1_for_body_8
	jz     sha1_for_after_10
sha1_for_body_8:
														;$t151 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-1152)], r11
														;$t150 = add $g20(chunks) $t151
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-1152)]
	mov    qword [rbp+(-1184)], r11
														;$t152 = load 8 $t150 0
	mov    r11, qword [rbp+(-1184)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-168)], rax
														;$t154 = mul $t27 8
	mov    r11, qword [rbp+(-264)]
	imul   r11, 8
	mov    qword [rbp+(-1504)], r11
														;$t153 = add $t152 $t154
	mov    r11, qword [rbp+(-168)]
	add    r11, qword [rbp+(-1504)]
	mov    qword [rbp+(-344)], r11
														;store 8 $t153 0 0
	mov    r11, qword [rbp+(-344)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;jump %for_loop
	jmp    sha1_for_loop_9
sha1_for_loop_9:
														;$t155 = move $t27
	mov    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-696)], r11
														;$t27 = add $t27 1
	mov    r11, qword [rbp+(-264)]
	add    r11, 1
	mov    qword [rbp+(-264)], r11
														;jump %for_condition
	jmp    sha1_for_condition_7
sha1_for_after_10:
														;jump %for_loop
	jmp    sha1_for_loop_11
sha1_for_loop_11:
														;$t156 = move $t26
	mov    r11, qword [rbp+(-816)]
	mov    qword [rbp+(-1472)], r11
														;$t26 = add $t26 1
	mov    r11, qword [rbp+(-816)]
	add    r11, 1
	mov    qword [rbp+(-816)], r11
														;jump %for_condition
	jmp    sha1_for_condition_5
sha1_for_after_12:
														;$t26 = move 0
	mov    r11, 0
	mov    qword [rbp+(-816)], r11
														;jump %for_condition
	jmp    sha1_for_condition_13
sha1_for_condition_13:
														;$t157 = slt $t26 $p24
	mov    r11, qword [rbp+(-816)]
	cmp    r11, qword [rbp+(-16)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-576)], r11
														;br $t157 %for_body %for_after
	cmp    qword [rbp+(-576)], 0
	jnz    sha1_for_body_14
	jz     sha1_for_after_16
sha1_for_body_14:
														;$t158 = div $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-528)], r11
														;$t160 = mul $t158 8
	mov    r11, qword [rbp+(-528)]
	imul   r11, 8
	mov    qword [rbp+(-664)], r11
														;$t159 = add $g20(chunks) $t160
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-664)]
	mov    qword [rbp+(-1040)], r11
														;$t161 = load 8 $t159 0
	mov    r11, qword [rbp+(-1040)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-104)], rax
														;$t162 = rem $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-152)], r11
														;$t163 = div $t162 4
	mov    r11, qword [rbp+(-152)]
	mov    rax, qword [rbp+(-152)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-896)], r11
														;$t165 = mul $t163 8
	mov    r11, qword [rbp+(-896)]
	imul   r11, 8
	mov    qword [rbp+(-1264)], r11
														;$t164 = add $t161 $t165
	mov    r11, qword [rbp+(-104)]
	add    r11, qword [rbp+(-1264)]
	mov    qword [rbp+(-112)], r11
														;$t166 = div $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-632)], r11
														;$t168 = mul $t166 8
	mov    r11, qword [rbp+(-632)]
	imul   r11, 8
	mov    qword [rbp+(-448)], r11
														;$t167 = add $g20(chunks) $t168
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-448)]
	mov    qword [rbp+(-1016)], r11
														;$t169 = load 8 $t167 0
	mov    r11, qword [rbp+(-1016)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1480)], rax
														;$t170 = rem $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-992)], r11
														;$t171 = div $t170 4
	mov    r11, qword [rbp+(-992)]
	mov    rax, qword [rbp+(-992)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-248)], r11
														;$t173 = mul $t171 8
	mov    r11, qword [rbp+(-248)]
	imul   r11, 8
	mov    qword [rbp+(-840)], r11
														;$t172 = add $t169 $t173
	mov    r11, qword [rbp+(-1480)]
	add    r11, qword [rbp+(-840)]
	mov    qword [rbp+(-256)], r11
														;$t174 = load 8 $t172 0
	mov    r11, qword [rbp+(-256)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1400)], rax
														;$t176 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-1304)], r11
														;$t175 = add $p23 $t176
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-1304)]
	mov    qword [rbp+(-416)], r11
														;$t177 = load 8 $t175 0
	mov    r11, qword [rbp+(-416)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1392)], rax
														;$t178 = rem $t26 4
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-936)], r11
														;$t179 = sub 3 $t178
	mov    r11, 3
	sub    r11, qword [rbp+(-936)]
	mov    qword [rbp+(-288)], r11
														;$t180 = mul $t179 8
	mov    r11, qword [rbp+(-288)]
	imul   r11, 8
	mov    qword [rbp+(-464)], r11
														;$t181 = shl $t177 $t180
	mov    r11, qword [rbp+(-1392)]
	mov    rcx, qword [rbp+(-464)]
	sal    r11, cl
	mov    qword [rbp+(-1064)], r11
														;$t182 = or $t174 $t181
	mov    r11, qword [rbp+(-1400)]
	or    r11, qword [rbp+(-1064)]
	mov    qword [rbp+(-312)], r11
														;store 8 $t164 $t182 0
	mov    r11, qword [rbp+(-112)]
	add    r11, 0
	mov    rax, qword [rbp+(-312)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    sha1_for_loop_15
sha1_for_loop_15:
														;$t183 = move $t26
	mov    r11, qword [rbp+(-816)]
	mov    qword [rbp+(-1344)], r11
														;$t26 = add $t26 1
	mov    r11, qword [rbp+(-816)]
	add    r11, 1
	mov    qword [rbp+(-816)], r11
														;jump %for_condition
	jmp    sha1_for_condition_13
sha1_for_after_16:
														;$t184 = div $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-1408)], r11
														;$t186 = mul $t184 8
	mov    r11, qword [rbp+(-1408)]
	imul   r11, 8
	mov    qword [rbp+(-496)], r11
														;$t185 = add $g20(chunks) $t186
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-496)]
	mov    qword [rbp+(-968)], r11
														;$t187 = load 8 $t185 0
	mov    r11, qword [rbp+(-968)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-392)], rax
														;$t188 = rem $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-1328)], r11
														;$t189 = div $t188 4
	mov    r11, qword [rbp+(-1328)]
	mov    rax, qword [rbp+(-1328)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-136)], r11
														;$t191 = mul $t189 8
	mov    r11, qword [rbp+(-136)]
	imul   r11, 8
	mov    qword [rbp+(-432)], r11
														;$t190 = add $t187 $t191
	mov    r11, qword [rbp+(-392)]
	add    r11, qword [rbp+(-432)]
	mov    qword [rbp+(-640)], r11
														;$t192 = div $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-760)], r11
														;$t194 = mul $t192 8
	mov    r11, qword [rbp+(-760)]
	imul   r11, 8
	mov    qword [rbp+(-472)], r11
														;$t193 = add $g20(chunks) $t194
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-472)]
	mov    qword [rbp+(-888)], r11
														;$t195 = load 8 $t193 0
	mov    r11, qword [rbp+(-888)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1048)], rax
														;$t196 = rem $t26 64
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 64
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-456)], r11
														;$t197 = div $t196 4
	mov    r11, qword [rbp+(-456)]
	mov    rax, qword [rbp+(-456)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-584)], r11
														;$t199 = mul $t197 8
	mov    r11, qword [rbp+(-584)]
	imul   r11, 8
	mov    qword [rbp+(-144)], r11
														;$t198 = add $t195 $t199
	mov    r11, qword [rbp+(-1048)]
	add    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-1528)], r11
														;$t200 = load 8 $t198 0
	mov    r11, qword [rbp+(-1528)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-352)], rax
														;$t201 = rem $t26 4
	mov    r11, qword [rbp+(-816)]
	mov    rax, qword [rbp+(-816)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-120)], r11
														;$t202 = sub 3 $t201
	mov    r11, 3
	sub    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-520)], r11
														;$t203 = mul $t202 8
	mov    r11, qword [rbp+(-520)]
	imul   r11, 8
	mov    qword [rbp+(-360)], r11
														;$t204 = shl 128 $t203
	mov    r11, 128
	mov    rcx, qword [rbp+(-360)]
	sal    r11, cl
	mov    qword [rbp+(-872)], r11
														;$t205 = or $t200 $t204
	mov    r11, qword [rbp+(-352)]
	or    r11, qword [rbp+(-872)]
	mov    qword [rbp+(-1384)], r11
														;store 8 $t190 $t205 0
	mov    r11, qword [rbp+(-640)]
	add    r11, 0
	mov    rax, qword [rbp+(-1384)]
	mov    qword [r11], rax
														;$t206 = sub $t25 1
	mov    r11, qword [rbp+(-376)]
	sub    r11, 1
	mov    qword [rbp+(-1288)], r11
														;$t208 = mul $t206 8
	mov    r11, qword [rbp+(-1288)]
	imul   r11, 8
	mov    qword [rbp+(-784)], r11
														;$t207 = add $g20(chunks) $t208
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-784)]
	mov    qword [rbp+(-424)], r11
														;$t209 = load 8 $t207 0
	mov    r11, qword [rbp+(-424)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-672)], rax
														;$t211 = move 120
	mov    r11, 120
	mov    qword [rbp+(-1336)], r11
														;$t210 = add $t209 $t211
	mov    r11, qword [rbp+(-672)]
	add    r11, qword [rbp+(-1336)]
	mov    qword [rbp+(-88)], r11
														;$t212 = shl $p24 3
	mov    r11, qword [rbp+(-16)]
	mov    rcx, 3
	sal    r11, cl
	mov    qword [rbp+(-200)], r11
														;store 8 $t210 $t212 0
	mov    r11, qword [rbp+(-88)]
	add    r11, 0
	mov    rax, qword [rbp+(-200)]
	mov    qword [r11], rax
														;$t213 = sub $t25 1
	mov    r11, qword [rbp+(-376)]
	sub    r11, 1
	mov    qword [rbp+(-608)], r11
														;$t215 = mul $t213 8
	mov    r11, qword [rbp+(-608)]
	imul   r11, 8
	mov    qword [rbp+(-208)], r11
														;$t214 = add $g20(chunks) $t215
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-208)]
	mov    qword [rbp+(-720)], r11
														;$t216 = load 8 $t214 0
	mov    r11, qword [rbp+(-720)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-296)], rax
														;$t218 = move 112
	mov    r11, 112
	mov    qword [rbp+(-160)], r11
														;$t217 = add $t216 $t218
	mov    r11, qword [rbp+(-296)]
	add    r11, qword [rbp+(-160)]
	mov    qword [rbp+(-616)], r11
														;$t219 = shr $p24 29
	mov    r11, qword [rbp+(-16)]
	mov    rcx, 29
	sar    r11, cl
	mov    qword [rbp+(-1192)], r11
														;$t220 = and $t219 7
	mov    r11, qword [rbp+(-1192)]
	and    r11, 7
	mov    qword [rbp+(-1440)], r11
														;store 8 $t217 $t220 0
	mov    r11, qword [rbp+(-616)]
	add    r11, 0
	mov    rax, qword [rbp+(-1440)]
	mov    qword [r11], rax
														;$t28 = move 1732584193
	mov    r11, 1732584193
	mov    qword [rbp+(-728)], r11
														;$t221 = call lohi 43913 61389
	mov    rdi, 43913
	mov    rsi, 61389
	call   lohi
	mov    qword [rbp+(-1272)], rax
														;$t29 = move $t221
	mov    r11, qword [rbp+(-1272)]
	mov    qword [rbp+(-744)], r11
														;$t222 = call lohi 56574 39098
	mov    rdi, 56574
	mov    rsi, 39098
	call   lohi
	mov    qword [rbp+(-848)], rax
														;$t30 = move $t222
	mov    r11, qword [rbp+(-848)]
	mov    qword [rbp+(-1496)], r11
														;$t31 = move 271733878
	mov    r11, 271733878
	mov    qword [rbp+(-504)], r11
														;$t223 = call lohi 57840 50130
	mov    rdi, 57840
	mov    rsi, 50130
	call   lohi
	mov    qword [rbp+(-440)], rax
														;$t32 = move $t223
	mov    r11, qword [rbp+(-440)]
	mov    qword [rbp+(-1160)], r11
														;$t26 = move 0
	mov    r11, 0
	mov    qword [rbp+(-816)], r11
														;jump %for_condition
	jmp    sha1_for_condition_17
sha1_for_condition_17:
														;$t224 = slt $t26 $t25
	mov    r11, qword [rbp+(-816)]
	cmp    r11, qword [rbp+(-376)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-976)], r11
														;br $t224 %for_body %for_after
	cmp    qword [rbp+(-976)], 0
	jnz    sha1_for_body_18
	jz     sha1_for_after_37
sha1_for_body_18:
														;$t27 = move 16
	mov    r11, 16
	mov    qword [rbp+(-264)], r11
														;jump %for_condition
	jmp    sha1_for_condition_19
sha1_for_condition_19:
														;$t225 = slt $t27 80
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 80
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-1512)], r11
														;br $t225 %for_body %for_after
	cmp    qword [rbp+(-1512)], 0
	jnz    sha1_for_body_20
	jz     sha1_for_after_22
sha1_for_body_20:
														;$t227 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-904)], r11
														;$t226 = add $g20(chunks) $t227
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-904)]
	mov    qword [rbp+(-1080)], r11
														;$t228 = load 8 $t226 0
	mov    r11, qword [rbp+(-1080)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-736)], rax
														;$t230 = mul $t27 8
	mov    r11, qword [rbp+(-264)]
	imul   r11, 8
	mov    qword [rbp+(-1536)], r11
														;$t229 = add $t228 $t230
	mov    r11, qword [rbp+(-736)]
	add    r11, qword [rbp+(-1536)]
	mov    qword [rbp+(-280)], r11
														;$t232 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-320)], r11
														;$t231 = add $g20(chunks) $t232
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-320)]
	mov    qword [rbp+(-920)], r11
														;$t233 = load 8 $t231 0
	mov    r11, qword [rbp+(-920)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1296)], rax
														;$t234 = sub $t27 3
	mov    r11, qword [rbp+(-264)]
	sub    r11, 3
	mov    qword [rbp+(-776)], r11
														;$t236 = mul $t234 8
	mov    r11, qword [rbp+(-776)]
	imul   r11, 8
	mov    qword [rbp+(-1112)], r11
														;$t235 = add $t233 $t236
	mov    r11, qword [rbp+(-1296)]
	add    r11, qword [rbp+(-1112)]
	mov    qword [rbp+(-856)], r11
														;$t237 = load 8 $t235 0
	mov    r11, qword [rbp+(-856)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1376)], rax
														;$t239 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-1352)], r11
														;$t238 = add $g20(chunks) $t239
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-1352)]
	mov    qword [rbp+(-232)], r11
														;$t240 = load 8 $t238 0
	mov    r11, qword [rbp+(-232)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-648)], rax
														;$t241 = sub $t27 8
	mov    r11, qword [rbp+(-264)]
	sub    r11, 8
	mov    qword [rbp+(-864)], r11
														;$t243 = mul $t241 8
	mov    r11, qword [rbp+(-864)]
	imul   r11, 8
	mov    qword [rbp+(-216)], r11
														;$t242 = add $t240 $t243
	mov    r11, qword [rbp+(-648)]
	add    r11, qword [rbp+(-216)]
	mov    qword [rbp+(-1464)], r11
														;$t244 = load 8 $t242 0
	mov    r11, qword [rbp+(-1464)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-960)], rax
														;$t245 = xor $t237 $t244
	mov    r11, qword [rbp+(-1376)]
	xor    r11, qword [rbp+(-960)]
	mov    qword [rbp+(-1056)], r11
														;$t247 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-880)], r11
														;$t246 = add $g20(chunks) $t247
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-880)]
	mov    qword [rbp+(-536)], r11
														;$t248 = load 8 $t246 0
	mov    r11, qword [rbp+(-536)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1008)], rax
														;$t249 = sub $t27 14
	mov    r11, qword [rbp+(-264)]
	sub    r11, 14
	mov    qword [rbp+(-1200)], r11
														;$t251 = mul $t249 8
	mov    r11, qword [rbp+(-1200)]
	imul   r11, 8
	mov    qword [rbp+(-752)], r11
														;$t250 = add $t248 $t251
	mov    r11, qword [rbp+(-1008)]
	add    r11, qword [rbp+(-752)]
	mov    qword [rbp+(-1520)], r11
														;$t252 = load 8 $t250 0
	mov    r11, qword [rbp+(-1520)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1424)], rax
														;$t253 = xor $t245 $t252
	mov    r11, qword [rbp+(-1056)]
	xor    r11, qword [rbp+(-1424)]
	mov    qword [rbp+(-176)], r11
														;$t255 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-808)], r11
														;$t254 = add $g20(chunks) $t255
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-808)]
	mov    qword [rbp+(-1024)], r11
														;$t256 = load 8 $t254 0
	mov    r11, qword [rbp+(-1024)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-824)], rax
														;$t257 = sub $t27 16
	mov    r11, qword [rbp+(-264)]
	sub    r11, 16
	mov    qword [rbp+(-192)], r11
														;$t259 = mul $t257 8
	mov    r11, qword [rbp+(-192)]
	imul   r11, 8
	mov    qword [rbp+(-1000)], r11
														;$t258 = add $t256 $t259
	mov    r11, qword [rbp+(-824)]
	add    r11, qword [rbp+(-1000)]
	mov    qword [rbp+(-1136)], r11
														;$t260 = load 8 $t258 0
	mov    r11, qword [rbp+(-1136)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1072)], rax
														;$t261 = xor $t253 $t260
	mov    r11, qword [rbp+(-176)]
	xor    r11, qword [rbp+(-1072)]
	mov    qword [rbp+(-272)], r11
														;$t262 = call rotate_left $t261 1
	mov    rdi, qword [rbp+(-272)]
	mov    rsi, 1
	call   rotate_left
	mov    qword [rbp+(-336)], rax
														;store 8 $t229 $t262 0
	mov    r11, qword [rbp+(-280)]
	add    r11, 0
	mov    rax, qword [rbp+(-336)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    sha1_for_loop_21
sha1_for_loop_21:
														;$t263 = move $t27
	mov    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-128)], r11
														;$t27 = add $t27 1
	mov    r11, qword [rbp+(-264)]
	add    r11, 1
	mov    qword [rbp+(-264)], r11
														;jump %for_condition
	jmp    sha1_for_condition_19
sha1_for_after_22:
														;$t33 = move $t28
	mov    r11, qword [rbp+(-728)]
	mov    qword [rbp+(-1120)], r11
														;$t34 = move $t29
	mov    r11, qword [rbp+(-744)]
	mov    qword [rbp+(-1224)], r11
														;$t35 = move $t30
	mov    r11, qword [rbp+(-1496)]
	mov    qword [rbp+(-1360)], r11
														;$t36 = move $t31
	mov    r11, qword [rbp+(-504)]
	mov    qword [rbp+(-400)], r11
														;$t37 = move $t32
	mov    r11, qword [rbp+(-1160)]
	mov    qword [rbp+(-1144)], r11
														;$t27 = move 0
	mov    r11, 0
	mov    qword [rbp+(-264)], r11
														;jump %for_condition
	jmp    sha1_for_condition_23
sha1_for_condition_23:
														;$t264 = slt $t27 80
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 80
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-656)], r11
														;br $t264 %for_body %for_after
	cmp    qword [rbp+(-656)], 0
	jnz    sha1_for_body_24
	jz     sha1_for_after_35
sha1_for_body_24:
														;$t265 = slt $t27 20
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 20
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-984)], r11
														;br $t265 %if_true %if_false
	cmp    qword [rbp+(-984)], 0
	jnz    sha1_if_true_25
	jz     sha1_if_false_26
sha1_if_true_25:
														;$t266 = and $t34 $t35
	mov    r11, qword [rbp+(-1224)]
	and    r11, qword [rbp+(-1360)]
	mov    qword [rbp+(-408)], r11
														;$t267 = not $t34
	mov    r11, qword [rbp+(-1224)]
	not    r11
	mov    qword [rbp+(-704)], r11
														;$t268 = and $t267 $t36
	mov    r11, qword [rbp+(-704)]
	and    r11, qword [rbp+(-400)]
	mov    qword [rbp+(-384)], r11
														;$t269 = or $t266 $t268
	mov    r11, qword [rbp+(-408)]
	or    r11, qword [rbp+(-384)]
	mov    qword [rbp+(-1032)], r11
														;$t38 = move $t269
	mov    r11, qword [rbp+(-1032)]
	mov    qword [rbp+(-1208)], r11
														;$t39 = move 1518500249
	mov    r11, 1518500249
	mov    qword [rbp+(-96)], r11
														;jump %if_merge
	jmp    sha1_if_merge_33
sha1_if_merge_33:
														;$t283 = call rotate_left $t33 5
	mov    rdi, qword [rbp+(-1120)]
	mov    rsi, 5
	call   rotate_left
	mov    qword [rbp+(-488)], rax
														;$t284 = call add $t283 $t37
	mov    rdi, qword [rbp+(-488)]
	mov    rsi, qword [rbp+(-1144)]
	call   add
	mov    qword [rbp+(-832)], rax
														;$t285 = call add $t38 $t39
	mov    rdi, qword [rbp+(-1208)]
	mov    rsi, qword [rbp+(-96)]
	call   add
	mov    qword [rbp+(-1088)], rax
														;$t286 = call add $t284 $t285
	mov    rdi, qword [rbp+(-832)]
	mov    rsi, qword [rbp+(-1088)]
	call   add
	mov    qword [rbp+(-912)], rax
														;$t288 = mul $t26 8
	mov    r11, qword [rbp+(-816)]
	imul   r11, 8
	mov    qword [rbp+(-184)], r11
														;$t287 = add $g20(chunks) $t288
	mov    r11, qword [rel GV_chunks]
	add    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-1248)], r11
														;$t289 = load 8 $t287 0
	mov    r11, qword [rbp+(-1248)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-224)], rax
														;$t291 = mul $t27 8
	mov    r11, qword [rbp+(-264)]
	imul   r11, 8
	mov    qword [rbp+(-1232)], r11
														;$t290 = add $t289 $t291
	mov    r11, qword [rbp+(-224)]
	add    r11, qword [rbp+(-1232)]
	mov    qword [rbp+(-1320)], r11
														;$t292 = load 8 $t290 0
	mov    r11, qword [rbp+(-1320)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-1168)], rax
														;$t293 = call add $t286 $t292
	mov    rdi, qword [rbp+(-912)]
	mov    rsi, qword [rbp+(-1168)]
	call   add
	mov    qword [rbp+(-1488)], rax
														;$t40 = move $t293
	mov    r11, qword [rbp+(-1488)]
	mov    qword [rbp+(-1456)], r11
														;$t37 = move $t36
	mov    r11, qword [rbp+(-400)]
	mov    qword [rbp+(-1144)], r11
														;$t36 = move $t35
	mov    r11, qword [rbp+(-1360)]
	mov    qword [rbp+(-400)], r11
														;$t294 = call rotate_left $t34 30
	mov    rdi, qword [rbp+(-1224)]
	mov    rsi, 30
	call   rotate_left
	mov    qword [rbp+(-1240)], rax
														;$t35 = move $t294
	mov    r11, qword [rbp+(-1240)]
	mov    qword [rbp+(-1360)], r11
														;$t34 = move $t33
	mov    r11, qword [rbp+(-1120)]
	mov    qword [rbp+(-1224)], r11
														;$t33 = move $t40
	mov    r11, qword [rbp+(-1456)]
	mov    qword [rbp+(-1120)], r11
														;jump %for_loop
	jmp    sha1_for_loop_34
sha1_for_loop_34:
														;$t295 = move $t27
	mov    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-944)], r11
														;$t27 = add $t27 1
	mov    r11, qword [rbp+(-264)]
	add    r11, 1
	mov    qword [rbp+(-264)], r11
														;jump %for_condition
	jmp    sha1_for_condition_23
sha1_if_false_26:
														;$t270 = slt $t27 40
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 40
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t270 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    sha1_if_true_27
	jz     sha1_if_false_28
sha1_if_true_27:
														;$t271 = xor $t34 $t35
	mov    r11, qword [rbp+(-1224)]
	xor    r11, qword [rbp+(-1360)]
	mov    qword [rbp+(-680)], r11
														;$t272 = xor $t271 $t36
	mov    r11, qword [rbp+(-680)]
	xor    r11, qword [rbp+(-400)]
	mov    qword [rbp+(-1312)], r11
														;$t38 = move $t272
	mov    r11, qword [rbp+(-1312)]
	mov    qword [rbp+(-1208)], r11
														;$t39 = move 1859775393
	mov    r11, 1859775393
	mov    qword [rbp+(-96)], r11
														;jump %if_merge
	jmp    sha1_if_merge_32
sha1_if_merge_32:
														;jump %if_merge
	jmp    sha1_if_merge_33
sha1_if_false_28:
														;$t273 = slt $t27 60
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 60
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-560)], r11
														;br $t273 %if_true %if_false
	cmp    qword [rbp+(-560)], 0
	jnz    sha1_if_true_29
	jz     sha1_if_false_30
sha1_if_true_29:
														;$t274 = and $t34 $t35
	mov    r11, qword [rbp+(-1224)]
	and    r11, qword [rbp+(-1360)]
	mov    qword [rbp+(-792)], r11
														;$t275 = and $t34 $t36
	mov    r11, qword [rbp+(-1224)]
	and    r11, qword [rbp+(-400)]
	mov    qword [rbp+(-928)], r11
														;$t276 = or $t274 $t275
	mov    r11, qword [rbp+(-792)]
	or    r11, qword [rbp+(-928)]
	mov    qword [rbp+(-512)], r11
														;$t277 = and $t35 $t36
	mov    r11, qword [rbp+(-1360)]
	and    r11, qword [rbp+(-400)]
	mov    qword [rbp+(-552)], r11
														;$t278 = or $t276 $t277
	mov    r11, qword [rbp+(-512)]
	or    r11, qword [rbp+(-552)]
	mov    qword [rbp+(-1432)], r11
														;$t38 = move $t278
	mov    r11, qword [rbp+(-1432)]
	mov    qword [rbp+(-1208)], r11
														;$t279 = call lohi 48348 36635
	mov    rdi, 48348
	mov    rsi, 36635
	call   lohi
	mov    qword [rbp+(-1128)], rax
														;$t39 = move $t279
	mov    r11, qword [rbp+(-1128)]
	mov    qword [rbp+(-96)], r11
														;jump %if_merge
	jmp    sha1_if_merge_31
sha1_if_merge_31:
														;jump %if_merge
	jmp    sha1_if_merge_32
sha1_if_false_30:
														;$t280 = xor $t34 $t35
	mov    r11, qword [rbp+(-1224)]
	xor    r11, qword [rbp+(-1360)]
	mov    qword [rbp+(-624)], r11
														;$t281 = xor $t280 $t36
	mov    r11, qword [rbp+(-624)]
	xor    r11, qword [rbp+(-400)]
	mov    qword [rbp+(-368)], r11
														;$t38 = move $t281
	mov    r11, qword [rbp+(-368)]
	mov    qword [rbp+(-1208)], r11
														;$t282 = call lohi 49622 51810
	mov    rdi, 49622
	mov    rsi, 51810
	call   lohi
	mov    qword [rbp+(-1448)], rax
														;$t39 = move $t282
	mov    r11, qword [rbp+(-1448)]
	mov    qword [rbp+(-96)], r11
														;jump %if_merge
	jmp    sha1_if_merge_31
sha1_for_after_35:
														;$t296 = call add $t28 $t33
	mov    rdi, qword [rbp+(-728)]
	mov    rsi, qword [rbp+(-1120)]
	call   add
	mov    qword [rbp+(-952)], rax
														;$t28 = move $t296
	mov    r11, qword [rbp+(-952)]
	mov    qword [rbp+(-728)], r11
														;$t297 = call add $t29 $t34
	mov    rdi, qword [rbp+(-744)]
	mov    rsi, qword [rbp+(-1224)]
	call   add
	mov    qword [rbp+(-1368)], rax
														;$t29 = move $t297
	mov    r11, qword [rbp+(-1368)]
	mov    qword [rbp+(-744)], r11
														;$t298 = call add $t30 $t35
	mov    rdi, qword [rbp+(-1496)]
	mov    rsi, qword [rbp+(-1360)]
	call   add
	mov    qword [rbp+(-768)], rax
														;$t30 = move $t298
	mov    r11, qword [rbp+(-768)]
	mov    qword [rbp+(-1496)], r11
														;$t299 = call add $t31 $t36
	mov    rdi, qword [rbp+(-504)]
	mov    rsi, qword [rbp+(-400)]
	call   add
	mov    qword [rbp+(-600)], rax
														;$t31 = move $t299
	mov    r11, qword [rbp+(-600)]
	mov    qword [rbp+(-504)], r11
														;$t300 = call add $t32 $t37
	mov    rdi, qword [rbp+(-1160)]
	mov    rsi, qword [rbp+(-1144)]
	call   add
	mov    qword [rbp+(-712)], rax
														;$t32 = move $t300
	mov    r11, qword [rbp+(-712)]
	mov    qword [rbp+(-1160)], r11
														;jump %for_loop
	jmp    sha1_for_loop_36
sha1_for_loop_36:
														;$t301 = move $t26
	mov    r11, qword [rbp+(-816)]
	mov    qword [rbp+(-544)], r11
														;$t26 = add $t26 1
	mov    r11, qword [rbp+(-816)]
	add    r11, 1
	mov    qword [rbp+(-816)], r11
														;jump %for_condition
	jmp    sha1_for_condition_17
sha1_for_after_37:
														;$t303 = move 0
	mov    r11, 0
	mov    qword [rbp+(-800)], r11
														;$t302 = add $g22(outputBuffer) $t303
	mov    r11, qword [rel GV_outputBuffer]
	add    r11, qword [rbp+(-800)]
	mov    qword [rbp+(-1416)], r11
														;store 8 $t302 $t28 0
	mov    r11, qword [rbp+(-1416)]
	add    r11, 0
	mov    rax, qword [rbp+(-728)]
	mov    qword [r11], rax
														;$t305 = move 8
	mov    r11, 8
	mov    qword [rbp+(-1176)], r11
														;$t304 = add $g22(outputBuffer) $t305
	mov    r11, qword [rel GV_outputBuffer]
	add    r11, qword [rbp+(-1176)]
	mov    qword [rbp+(-480)], r11
														;store 8 $t304 $t29 0
	mov    r11, qword [rbp+(-480)]
	add    r11, 0
	mov    rax, qword [rbp+(-744)]
	mov    qword [r11], rax
														;$t307 = move 16
	mov    r11, 16
	mov    qword [rbp+(-1096)], r11
														;$t306 = add $g22(outputBuffer) $t307
	mov    r11, qword [rel GV_outputBuffer]
	add    r11, qword [rbp+(-1096)]
	mov    qword [rbp+(-592)], r11
														;store 8 $t306 $t30 0
	mov    r11, qword [rbp+(-592)]
	add    r11, 0
	mov    rax, qword [rbp+(-1496)]
	mov    qword [r11], rax
														;$t309 = move 24
	mov    r11, 24
	mov    qword [rbp+(-240)], r11
														;$t308 = add $g22(outputBuffer) $t309
	mov    r11, qword [rel GV_outputBuffer]
	add    r11, qword [rbp+(-240)]
	mov    qword [rbp+(-328)], r11
														;store 8 $t308 $t31 0
	mov    r11, qword [rbp+(-328)]
	add    r11, 0
	mov    rax, qword [rbp+(-504)]
	mov    qword [r11], rax
														;$t311 = move 32
	mov    r11, 32
	mov    qword [rbp+(-1104)], r11
														;$t310 = add $g22(outputBuffer) $t311
	mov    r11, qword [rel GV_outputBuffer]
	add    r11, qword [rbp+(-1104)]
	mov    qword [rbp+(-304)], r11
														;store 8 $t310 $t32 0
	mov    r11, qword [rbp+(-304)]
	add    r11, 0
	mov    rax, qword [rbp+(-1160)]
	mov    qword [r11], rax
														;ret $g22(outputBuffer)
	mov    rax, qword [rel GV_outputBuffer]
	leave
	ret
														;jump %exit
	jmp    sha1_exit_38
sha1_exit_38:
	leave
	ret


computeSHA1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 200
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
computeSHA1_enter_0:
														;jump %entry
	jmp    computeSHA1_entry_1
computeSHA1_entry_1:
														;$t42 = move 0
	mov    r11, 0
	mov    qword [rbp+(-184)], r11
														;jump %for_condition
	jmp    computeSHA1_for_condition_2
computeSHA1_for_condition_2:
														;$t312 = call __builtin_getStringLength $p41
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getStringLength
	mov    qword [rbp+(-152)], rax
														;$t313 = slt $t42 $t312
	mov    r11, qword [rbp+(-184)]
	cmp    r11, qword [rbp+(-152)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-112)], r11
														;br $t313 %for_body %for_after
	cmp    qword [rbp+(-112)], 0
	jnz    computeSHA1_for_body_3
	jz     computeSHA1_for_after_5
computeSHA1_for_body_3:
														;$t315 = mul $t42 8
	mov    r11, qword [rbp+(-184)]
	imul   r11, 8
	mov    qword [rbp+(-128)], r11
														;$t314 = add $g21(inputBuffer) $t315
	mov    r11, qword [rel GV_inputBuffer]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-120)], r11
														;$t316 = call __builtin_ord $p41 $t42
	mov    rdi, qword [rbp+(-8)]
	mov    rsi, qword [rbp+(-184)]
	call   __builtin_ord
	mov    qword [rbp+(-160)], rax
														;store 8 $t314 $t316 0
	mov    r11, qword [rbp+(-120)]
	add    r11, 0
	mov    rax, qword [rbp+(-160)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    computeSHA1_for_loop_4
computeSHA1_for_loop_4:
														;$t317 = move $t42
	mov    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-64)], r11
														;$t42 = add $t42 1
	mov    r11, qword [rbp+(-184)]
	add    r11, 1
	mov    qword [rbp+(-184)], r11
														;jump %for_condition
	jmp    computeSHA1_for_condition_2
computeSHA1_for_after_5:
														;$t318 = call __builtin_getStringLength $p41
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getStringLength
	mov    qword [rbp+(-88)], rax
														;$t319 = call sha1 $g21(inputBuffer) $t318
	mov    rdi, qword [rel GV_inputBuffer]
	mov    rsi, qword [rbp+(-88)]
	call   sha1
	mov    qword [rbp+(-144)], rax
														;$t43 = move $t319
	mov    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-104)], r11
														;$t42 = move 0
	mov    r11, 0
	mov    qword [rbp+(-184)], r11
														;jump %for_condition
	jmp    computeSHA1_for_condition_6
computeSHA1_for_condition_6:
														;$t320 = call __builtin_getArraySize $t43
	mov    rdi, qword [rbp+(-104)]
	call   __builtin_getArraySize
	mov    qword [rbp+(-176)], rax
														;$t321 = slt $t42 $t320
	mov    r11, qword [rbp+(-184)]
	cmp    r11, qword [rbp+(-176)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-192)], r11
														;br $t321 %for_body %for_after
	cmp    qword [rbp+(-192)], 0
	jnz    computeSHA1_for_body_7
	jz     computeSHA1_for_after_9
computeSHA1_for_body_7:
														;$t323 = mul $t42 8
	mov    r11, qword [rbp+(-184)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t322 = add $t43 $t323
	mov    r11, qword [rbp+(-104)]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-136)], r11
														;$t324 = load 8 $t322 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-96)], rax
														;$t325 = call toStringHex $t324
	mov    rdi, qword [rbp+(-96)]
	call   toStringHex
	mov    qword [rbp+(-72)], rax
														;call __builtin_print $t325
	mov    rdi, qword [rbp+(-72)]
	call   __builtin_print
														;jump %for_loop
	jmp    computeSHA1_for_loop_8
computeSHA1_for_loop_8:
														;$t326 = move $t42
	mov    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-168)], r11
														;$t42 = add $t42 1
	mov    r11, qword [rbp+(-184)]
	add    r11, 1
	mov    qword [rbp+(-184)], r11
														;jump %for_condition
	jmp    computeSHA1_for_condition_6
computeSHA1_for_after_9:
														;call __builtin_println $327
	mov    rdi, CONST_STRING_327
	call   __builtin_println
														;jump %exit
	jmp    computeSHA1_exit_10
computeSHA1_exit_10:
	leave
	ret


nextLetter:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 96
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
nextLetter_enter_0:
														;jump %entry
	jmp    nextLetter_entry_1
nextLetter_entry_1:
														;$t328 = seq $p44 122
	mov    r11, qword [rbp+(-8)]
	cmp    r11, 122
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t328 %if_true %if_false
	cmp    qword [rbp+(-80)], 0
	jnz    nextLetter_if_true_2
	jz     nextLetter_if_false_3
nextLetter_if_true_2:
														;ret -1
	mov    rax, -1
	leave
	ret
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_if_false_3:
														;jump %if_merge
	jmp    nextLetter_if_merge_4
nextLetter_if_merge_4:
														;$t329 = seq $p44 90
	mov    r11, qword [rbp+(-8)]
	cmp    r11, 90
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t329 %if_true %if_false
	cmp    qword [rbp+(-72)], 0
	jnz    nextLetter_if_true_5
	jz     nextLetter_if_false_6
nextLetter_if_true_5:
														;ret 97
	mov    rax, 97
	leave
	ret
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_if_false_6:
														;jump %if_merge
	jmp    nextLetter_if_merge_7
nextLetter_if_merge_7:
														;$t330 = seq $p44 57
	mov    r11, qword [rbp+(-8)]
	cmp    r11, 57
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t330 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    nextLetter_if_true_8
	jz     nextLetter_if_false_9
nextLetter_if_true_8:
														;ret 65
	mov    rax, 65
	leave
	ret
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_if_false_9:
														;jump %if_merge
	jmp    nextLetter_if_merge_10
nextLetter_if_merge_10:
														;$t331 = add $p44 1
	mov    r11, qword [rbp+(-8)]
	add    r11, 1
	mov    qword [rbp+(-88)], r11
														;ret $t331
	mov    rax, qword [rbp+(-88)]
	leave
	ret
														;jump %exit
	jmp    nextLetter_exit_11
nextLetter_exit_11:
	leave
	ret


nextText:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 192
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
nextText_enter_0:
														;jump %entry
	jmp    nextText_entry_1
nextText_entry_1:
														;$t332 = sub $p46 1
	mov    r11, qword [rbp+(-16)]
	sub    r11, 1
	mov    qword [rbp+(-152)], r11
														;$t47 = move $t332
	mov    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-72)], r11
														;jump %for_condition
	jmp    nextText_for_condition_2
nextText_for_condition_2:
														;$t333 = sge $t47 0
	mov    r11, qword [rbp+(-72)]
	cmp    r11, 0
	setge   al
	movzx  r11, al
	mov    qword [rbp+(-80)], r11
														;br $t333 %for_body %for_after
	cmp    qword [rbp+(-80)], 0
	jnz    nextText_for_body_3
	jz     nextText_for_after_8
nextText_for_body_3:
														;$t335 = mul $t47 8
	mov    r11, qword [rbp+(-72)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t334 = add $p45 $t335
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-64)], r11
														;$t337 = mul $t47 8
	mov    r11, qword [rbp+(-72)]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t336 = add $p45 $t337
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-176)], r11
														;$t338 = load 8 $t336 0
	mov    r11, qword [rbp+(-176)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-96)], rax
														;$t339 = call nextLetter $t338
	mov    rdi, qword [rbp+(-96)]
	call   nextLetter
	mov    qword [rbp+(-168)], rax
														;store 8 $t334 $t339 0
	mov    r11, qword [rbp+(-64)]
	add    r11, 0
	mov    rax, qword [rbp+(-168)]
	mov    qword [r11], rax
														;$t341 = mul $t47 8
	mov    r11, qword [rbp+(-72)]
	imul   r11, 8
	mov    qword [rbp+(-104)], r11
														;$t340 = add $p45 $t341
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-160)], r11
														;$t342 = load 8 $t340 0
	mov    r11, qword [rbp+(-160)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-128)], rax
														;$t343 = seq $t342 -1
	mov    r11, qword [rbp+(-128)]
	cmp    r11, -1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-136)], r11
														;br $t343 %if_true %if_false
	cmp    qword [rbp+(-136)], 0
	jnz    nextText_if_true_4
	jz     nextText_if_false_5
nextText_if_true_4:
														;$t345 = mul $t47 8
	mov    r11, qword [rbp+(-72)]
	imul   r11, 8
	mov    qword [rbp+(-144)], r11
														;$t344 = add $p45 $t345
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-184)], r11
														;store 8 $t344 48 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, 48
	mov    qword [r11], rax
														;jump %if_merge
	jmp    nextText_if_merge_6
nextText_if_merge_6:
														;jump %for_loop
	jmp    nextText_for_loop_7
nextText_for_loop_7:
														;$t346 = move $t47
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-112)], r11
														;$t47 = sub $t47 1
	mov    r11, qword [rbp+(-72)]
	sub    r11, 1
	mov    qword [rbp+(-72)], r11
														;jump %for_condition
	jmp    nextText_for_condition_2
nextText_if_false_5:
														;ret 1
	mov    rax, 1
	leave
	ret
														;jump %exit
	jmp    nextText_exit_9
nextText_for_after_8:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    nextText_exit_9
nextText_exit_9:
	leave
	ret


array_equal:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 176
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
array_equal_enter_0:
														;jump %entry
	jmp    array_equal_entry_1
array_equal_entry_1:
														;$t347 = call __builtin_getArraySize $p48
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getArraySize
	mov    qword [rbp+(-160)], rax
														;$t348 = call __builtin_getArraySize $p49
	mov    rdi, qword [rbp+(-16)]
	call   __builtin_getArraySize
	mov    qword [rbp+(-64)], rax
														;$t349 = sne $t347 $t348
	mov    r11, qword [rbp+(-160)]
	cmp    r11, qword [rbp+(-64)]
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-152)], r11
														;br $t349 %if_true %if_false
	cmp    qword [rbp+(-152)], 0
	jnz    array_equal_if_true_2
	jz     array_equal_if_false_3
array_equal_if_true_2:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    array_equal_exit_12
array_equal_if_false_3:
														;jump %if_merge
	jmp    array_equal_if_merge_4
array_equal_if_merge_4:
														;$t50 = move 0
	mov    r11, 0
	mov    qword [rbp+(-144)], r11
														;jump %for_condition
	jmp    array_equal_for_condition_5
array_equal_for_condition_5:
														;$t350 = call __builtin_getArraySize $p48
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getArraySize
	mov    qword [rbp+(-120)], rax
														;$t351 = slt $t50 $t350
	mov    r11, qword [rbp+(-144)]
	cmp    r11, qword [rbp+(-120)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-168)], r11
														;br $t351 %for_body %for_after
	cmp    qword [rbp+(-168)], 0
	jnz    array_equal_for_body_6
	jz     array_equal_for_after_11
array_equal_for_body_6:
														;$t353 = mul $t50 8
	mov    r11, qword [rbp+(-144)]
	imul   r11, 8
	mov    qword [rbp+(-128)], r11
														;$t352 = add $p48 $t353
	mov    r11, qword [rbp+(-8)]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-136)], r11
														;$t354 = load 8 $t352 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-88)], rax
														;$t356 = mul $t50 8
	mov    r11, qword [rbp+(-144)]
	imul   r11, 8
	mov    qword [rbp+(-104)], r11
														;$t355 = add $p49 $t356
	mov    r11, qword [rbp+(-16)]
	add    r11, qword [rbp+(-104)]
	mov    qword [rbp+(-80)], r11
														;$t357 = load 8 $t355 0
	mov    r11, qword [rbp+(-80)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-112)], rax
														;$t358 = sne $t354 $t357
	mov    r11, qword [rbp+(-88)]
	cmp    r11, qword [rbp+(-112)]
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t358 %if_true %if_false
	cmp    qword [rbp+(-72)], 0
	jnz    array_equal_if_true_7
	jz     array_equal_if_false_8
array_equal_if_true_7:
														;ret 0
	mov    rax, 0
	leave
	ret
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
	mov    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-96)], r11
														;$t50 = add $t50 1
	mov    r11, qword [rbp+(-144)]
	add    r11, 1
	mov    qword [rbp+(-144)], r11
														;jump %for_condition
	jmp    array_equal_for_condition_5
array_equal_for_after_11:
														;ret 1
	mov    rax, 1
	leave
	ret
														;jump %exit
	jmp    array_equal_exit_12
array_equal_exit_12:
	leave
	ret


crackSHA1:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 440
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
crackSHA1_enter_0:
														;jump %entry
	jmp    crackSHA1_entry_1
crackSHA1_entry_1:
														;$t361 = move 40
	mov    r11, 40
	mov    qword [rbp+(-304)], r11
														;$t361 = add $t361 8
	mov    r11, qword [rbp+(-304)]
	add    r11, 8
	mov    qword [rbp+(-304)], r11
														;$t360 = alloc $t361
	mov    rdi, qword [rbp+(-304)]
	call   malloc
	mov    qword [rbp+(-248)], rax
														;store 8 $t360 5 0
	mov    r11, qword [rbp+(-248)]
	add    r11, 0
	mov    rax, 5
	mov    qword [r11], rax
														;$t360 = add $t360 8
	mov    r11, qword [rbp+(-248)]
	add    r11, 8
	mov    qword [rbp+(-248)], r11
														;$t52 = move $t360
	mov    r11, qword [rbp+(-248)]
	mov    qword [rbp+(-256)], r11
														;$t362 = call __builtin_getStringLength $p51
	mov    rdi, qword [rbp+(-8)]
	call   __builtin_getStringLength
	mov    qword [rbp+(-264)], rax
														;$t363 = sne $t362 40
	mov    r11, qword [rbp+(-264)]
	cmp    r11, 40
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-416)], r11
														;br $t363 %if_true %if_false
	cmp    qword [rbp+(-416)], 0
	jnz    crackSHA1_if_true_2
	jz     crackSHA1_if_false_3
crackSHA1_if_true_2:
														;call __builtin_println $364
	mov    rdi, CONST_STRING_364
	call   __builtin_println
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_if_false_3:
														;jump %if_merge
	jmp    crackSHA1_if_merge_4
crackSHA1_if_merge_4:
														;$t53 = move 0
	mov    r11, 0
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_5
crackSHA1_for_condition_5:
														;$t365 = slt $t53 5
	mov    r11, qword [rbp+(-336)]
	cmp    r11, 5
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-400)], r11
														;br $t365 %for_body %for_after
	cmp    qword [rbp+(-400)], 0
	jnz    crackSHA1_for_body_6
	jz     crackSHA1_for_after_8
crackSHA1_for_body_6:
														;$t367 = mul $t53 8
	mov    r11, qword [rbp+(-336)]
	imul   r11, 8
	mov    qword [rbp+(-144)], r11
														;$t366 = add $t52 $t367
	mov    r11, qword [rbp+(-256)]
	add    r11, qword [rbp+(-144)]
	mov    qword [rbp+(-424)], r11
														;store 8 $t366 0 0
	mov    r11, qword [rbp+(-424)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_7
crackSHA1_for_loop_7:
														;$t368 = move $t53
	mov    r11, qword [rbp+(-336)]
	mov    qword [rbp+(-64)], r11
														;$t53 = add $t53 1
	mov    r11, qword [rbp+(-336)]
	add    r11, 1
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_5
crackSHA1_for_after_8:
														;$t53 = move 0
	mov    r11, 0
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_9
crackSHA1_for_condition_9:
														;$t369 = slt $t53 40
	mov    r11, qword [rbp+(-336)]
	cmp    r11, 40
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-152)], r11
														;br $t369 %for_body %for_after
	cmp    qword [rbp+(-152)], 0
	jnz    crackSHA1_for_body_10
	jz     crackSHA1_for_after_12
crackSHA1_for_body_10:
														;$t370 = div $t53 8
	mov    r11, qword [rbp+(-336)]
	mov    rax, qword [rbp+(-336)]
	cqo
	mov    r11, 8
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-112)], r11
														;$t372 = mul $t370 8
	mov    r11, qword [rbp+(-112)]
	imul   r11, 8
	mov    qword [rbp+(-232)], r11
														;$t371 = add $t52 $t372
	mov    r11, qword [rbp+(-256)]
	add    r11, qword [rbp+(-232)]
	mov    qword [rbp+(-192)], r11
														;$t373 = div $t53 8
	mov    r11, qword [rbp+(-336)]
	mov    rax, qword [rbp+(-336)]
	cqo
	mov    r11, 8
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-344)], r11
														;$t375 = mul $t373 8
	mov    r11, qword [rbp+(-344)]
	imul   r11, 8
	mov    qword [rbp+(-96)], r11
														;$t374 = add $t52 $t375
	mov    r11, qword [rbp+(-256)]
	add    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-368)], r11
														;$t376 = load 8 $t374 0
	mov    r11, qword [rbp+(-368)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-224)], rax
														;$t377 = add $t53 3
	mov    r11, qword [rbp+(-336)]
	add    r11, 3
	mov    qword [rbp+(-376)], r11
														;$t378 = call __builtin_getSubstring $p51 $t53 $t377
	mov    rdi, qword [rbp+(-8)]
	mov    rsi, qword [rbp+(-336)]
	mov    rdx, qword [rbp+(-376)]
	call   __builtin_getSubstring
	mov    qword [rbp+(-312)], rax
														;$t379 = call hex2int $t378
	mov    rdi, qword [rbp+(-312)]
	call   hex2int
	mov    qword [rbp+(-104)], rax
														;$t380 = div $t53 4
	mov    r11, qword [rbp+(-336)]
	mov    rax, qword [rbp+(-336)]
	cqo
	mov    r11, 4
	idiv   r11
	mov    r11, rax
	mov    qword [rbp+(-328)], r11
														;$t381 = rem $t380 2
	mov    r11, qword [rbp+(-328)]
	mov    rax, qword [rbp+(-328)]
	cqo
	mov    r11, 2
	idiv   r11
	mov    r11, rdx
	mov    qword [rbp+(-128)], r11
														;$t382 = sub 1 $t381
	mov    r11, 1
	sub    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-208)], r11
														;$t383 = mul $t382 16
	mov    r11, qword [rbp+(-208)]
	imul   r11, 16
	mov    qword [rbp+(-200)], r11
														;$t384 = shl $t379 $t383
	mov    r11, qword [rbp+(-104)]
	mov    rcx, qword [rbp+(-200)]
	sal    r11, cl
	mov    qword [rbp+(-120)], r11
														;$t385 = or $t376 $t384
	mov    r11, qword [rbp+(-224)]
	or    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-392)], r11
														;store 8 $t371 $t385 0
	mov    r11, qword [rbp+(-192)]
	add    r11, 0
	mov    rax, qword [rbp+(-392)]
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_11
crackSHA1_for_loop_11:
														;$t386 = add $t53 4
	mov    r11, qword [rbp+(-336)]
	add    r11, 4
	mov    qword [rbp+(-216)], r11
														;$t53 = move $t386
	mov    r11, qword [rbp+(-216)]
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_9
crackSHA1_for_after_12:
														;$t54 = move 4
	mov    r11, 4
	mov    qword [rbp+(-168)], r11
														;$t55 = move 1
	mov    r11, 1
	mov    qword [rbp+(-176)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_13
crackSHA1_for_condition_13:
														;$t387 = sle $t55 $t54
	mov    r11, qword [rbp+(-176)]
	cmp    r11, qword [rbp+(-168)]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-72)], r11
														;br $t387 %for_body %for_after
	cmp    qword [rbp+(-72)], 0
	jnz    crackSHA1_for_body_14
	jz     crackSHA1_for_after_33
crackSHA1_for_body_14:
														;$t53 = move 0
	mov    r11, 0
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_15
crackSHA1_for_condition_15:
														;$t388 = slt $t53 $t55
	mov    r11, qword [rbp+(-336)]
	cmp    r11, qword [rbp+(-176)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-352)], r11
														;br $t388 %for_body %for_after
	cmp    qword [rbp+(-352)], 0
	jnz    crackSHA1_for_body_16
	jz     crackSHA1_for_after_18
crackSHA1_for_body_16:
														;$t390 = mul $t53 8
	mov    r11, qword [rbp+(-336)]
	imul   r11, 8
	mov    qword [rbp+(-272)], r11
														;$t389 = add $g21(inputBuffer) $t390
	mov    r11, qword [rel GV_inputBuffer]
	add    r11, qword [rbp+(-272)]
	mov    qword [rbp+(-240)], r11
														;store 8 $t389 48 0
	mov    r11, qword [rbp+(-240)]
	add    r11, 0
	mov    rax, 48
	mov    qword [r11], rax
														;jump %for_loop
	jmp    crackSHA1_for_loop_17
crackSHA1_for_loop_17:
														;$t391 = move $t53
	mov    r11, qword [rbp+(-336)]
	mov    qword [rbp+(-384)], r11
														;$t53 = add $t53 1
	mov    r11, qword [rbp+(-336)]
	add    r11, 1
	mov    qword [rbp+(-336)], r11
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
	mov    rdi, qword [rel GV_inputBuffer]
	mov    rsi, qword [rbp+(-176)]
	call   sha1
	mov    qword [rbp+(-296)], rax
														;$t56 = move $t392
	mov    r11, qword [rbp+(-296)]
	mov    qword [rbp+(-280)], r11
														;$t393 = call array_equal $t56 $t52
	mov    rdi, qword [rbp+(-280)]
	mov    rsi, qword [rbp+(-256)]
	call   array_equal
	mov    qword [rbp+(-136)], rax
														;br $t393 %if_true %if_false
	cmp    qword [rbp+(-136)], 0
	jnz    crackSHA1_if_true_21
	jz     crackSHA1_if_false_26
crackSHA1_if_true_21:
														;$t53 = move 0
	mov    r11, 0
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_22
crackSHA1_for_condition_22:
														;$t394 = slt $t53 $t55
	mov    r11, qword [rbp+(-336)]
	cmp    r11, qword [rbp+(-176)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-288)], r11
														;br $t394 %for_body %for_after
	cmp    qword [rbp+(-288)], 0
	jnz    crackSHA1_for_body_23
	jz     crackSHA1_for_after_25
crackSHA1_for_body_23:
														;$t396 = mul $t53 8
	mov    r11, qword [rbp+(-336)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t395 = add $g21(inputBuffer) $t396
	mov    r11, qword [rel GV_inputBuffer]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-184)], r11
														;$t397 = load 8 $t395 0
	mov    r11, qword [rbp+(-184)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-432)], rax
														;$t398 = call int2chr $t397
	mov    rdi, qword [rbp+(-432)]
	call   int2chr
	mov    qword [rbp+(-88)], rax
														;call __builtin_print $t398
	mov    rdi, qword [rbp+(-88)]
	call   __builtin_print
														;jump %for_loop
	jmp    crackSHA1_for_loop_24
crackSHA1_for_loop_24:
														;$t399 = move $t53
	mov    r11, qword [rbp+(-336)]
	mov    qword [rbp+(-320)], r11
														;$t53 = add $t53 1
	mov    r11, qword [rbp+(-336)]
	add    r11, 1
	mov    qword [rbp+(-336)], r11
														;jump %for_condition
	jmp    crackSHA1_for_condition_22
crackSHA1_for_after_25:
														;call __builtin_println $400
	mov    rdi, CONST_STRING_400
	call   __builtin_println
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_if_false_26:
														;jump %if_merge
	jmp    crackSHA1_if_merge_27
crackSHA1_if_merge_27:
														;$t401 = call nextText $g21(inputBuffer) $t55
	mov    rdi, qword [rel GV_inputBuffer]
	mov    rsi, qword [rbp+(-176)]
	call   nextText
	mov    qword [rbp+(-360)], rax
														;$t402 = xor $t401 1
	mov    r11, qword [rbp+(-360)]
	xor    r11, 1
	mov    qword [rbp+(-160)], r11
														;br $t402 %if_true %if_false
	cmp    qword [rbp+(-160)], 0
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
	mov    r11, qword [rbp+(-176)]
	mov    qword [rbp+(-408)], r11
														;$t55 = add $t55 1
	mov    r11, qword [rbp+(-176)]
	add    r11, 1
	mov    qword [rbp+(-176)], r11
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
	mov    rdi, CONST_STRING_404
	call   __builtin_println
														;jump %exit
	jmp    crackSHA1_exit_34
crackSHA1_exit_34:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 248
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
main_enter_0:
														;$g4(asciiTable) = move $405
	mov    r11, CONST_STRING_405
	mov    qword [rel GV_asciiTable], r11
														;$g18(MAXCHUNK) = move 100
	mov    r11, 100
	mov    qword [rel GV_MAXCHUNK], r11
														;$t406 = sub $g18(MAXCHUNK) 1
	mov    r11, qword [rel GV_MAXCHUNK]
	sub    r11, 1
	mov    qword [rbp+(-208)], r11
														;$t407 = mul $t406 64
	mov    r11, qword [rbp+(-208)]
	imul   r11, 64
	mov    qword [rbp+(-80)], r11
														;$t408 = sub $t407 16
	mov    r11, qword [rbp+(-80)]
	sub    r11, 16
	mov    qword [rbp+(-152)], r11
														;$g19(MAXLENGTH) = move $t408
	mov    r11, qword [rbp+(-152)]
	mov    qword [rel GV_MAXLENGTH], r11
														;$t410 = mul $g18(MAXCHUNK) 8
	mov    r11, qword [rel GV_MAXCHUNK]
	imul   r11, 8
	mov    qword [rbp+(-64)], r11
														;$t410 = add $t410 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    qword [rbp+(-64)], r11
														;$t409 = alloc $t410
	mov    rdi, qword [rbp+(-64)]
	call   malloc
	mov    qword [rbp+(-176)], rax
														;store 8 $t409 $g18(MAXCHUNK) 0
	mov    r11, qword [rbp+(-176)]
	add    r11, 0
	mov    rax, qword [rel GV_MAXCHUNK]
	mov    qword [r11], rax
														;$t409 = add $t409 8
	mov    r11, qword [rbp+(-176)]
	add    r11, 8
	mov    qword [rbp+(-176)], r11
														;$t411 = move 0
	mov    r11, 0
	mov    qword [rbp+(-168)], r11
														;jump %new_condition
	jmp    main_new_condition_1
main_new_condition_1:
														;$t412 = slt $t411 $g18(MAXCHUNK)
	mov    r11, qword [rbp+(-168)]
	cmp    r11, qword [rel GV_MAXCHUNK]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-128)], r11
														;br $t412 %new_body %new_exit
	cmp    qword [rbp+(-128)], 0
	jnz    main_new_body_2
	jz     main_new_exit_4
main_new_body_2:
														;$t414 = move 640
	mov    r11, 640
	mov    qword [rbp+(-104)], r11
														;$t414 = add $t414 8
	mov    r11, qword [rbp+(-104)]
	add    r11, 8
	mov    qword [rbp+(-104)], r11
														;$t413 = alloc $t414
	mov    rdi, qword [rbp+(-104)]
	call   malloc
	mov    qword [rbp+(-136)], rax
														;store 8 $t413 80 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    rax, 80
	mov    qword [r11], rax
														;$t413 = add $t413 8
	mov    r11, qword [rbp+(-136)]
	add    r11, 8
	mov    qword [rbp+(-136)], r11
														;$t415 = mul $t411 8
	mov    r11, qword [rbp+(-168)]
	imul   r11, 8
	mov    qword [rbp+(-88)], r11
														;$t416 = add $t409 $t415
	mov    r11, qword [rbp+(-176)]
	add    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-240)], r11
														;store 8 $t416 $t413 0
	mov    r11, qword [rbp+(-240)]
	add    r11, 0
	mov    rax, qword [rbp+(-136)]
	mov    qword [r11], rax
														;jump %new_loop
	jmp    main_new_loop_3
main_new_loop_3:
														;$t411 = add $t411 1
	mov    r11, qword [rbp+(-168)]
	add    r11, 1
	mov    qword [rbp+(-168)], r11
														;jump %new_condition
	jmp    main_new_condition_1
main_new_exit_4:
														;$g20(chunks) = move $t409
	mov    r11, qword [rbp+(-176)]
	mov    qword [rel GV_chunks], r11
														;$t418 = mul $g19(MAXLENGTH) 8
	mov    r11, qword [rel GV_MAXLENGTH]
	imul   r11, 8
	mov    qword [rbp+(-224)], r11
														;$t418 = add $t418 8
	mov    r11, qword [rbp+(-224)]
	add    r11, 8
	mov    qword [rbp+(-224)], r11
														;$t417 = alloc $t418
	mov    rdi, qword [rbp+(-224)]
	call   malloc
	mov    qword [rbp+(-144)], rax
														;store 8 $t417 $g19(MAXLENGTH) 0
	mov    r11, qword [rbp+(-144)]
	add    r11, 0
	mov    rax, qword [rel GV_MAXLENGTH]
	mov    qword [r11], rax
														;$t417 = add $t417 8
	mov    r11, qword [rbp+(-144)]
	add    r11, 8
	mov    qword [rbp+(-144)], r11
														;$g21(inputBuffer) = move $t417
	mov    r11, qword [rbp+(-144)]
	mov    qword [rel GV_inputBuffer], r11
														;$t420 = move 40
	mov    r11, 40
	mov    qword [rbp+(-200)], r11
														;$t420 = add $t420 8
	mov    r11, qword [rbp+(-200)]
	add    r11, 8
	mov    qword [rbp+(-200)], r11
														;$t419 = alloc $t420
	mov    rdi, qword [rbp+(-200)]
	call   malloc
	mov    qword [rbp+(-232)], rax
														;store 8 $t419 5 0
	mov    r11, qword [rbp+(-232)]
	add    r11, 0
	mov    rax, 5
	mov    qword [r11], rax
														;$t419 = add $t419 8
	mov    r11, qword [rbp+(-232)]
	add    r11, 8
	mov    qword [rbp+(-232)], r11
														;$g22(outputBuffer) = move $t419
	mov    r11, qword [rbp+(-232)]
	mov    qword [rel GV_outputBuffer], r11
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
	mov    qword [rbp+(-120)], rax
														;$t57 = move $t421
	mov    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-112)], r11
														;$t422 = seq $t57 0
	mov    r11, qword [rbp+(-112)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-216)], r11
														;br $t422 %if_true %if_false
	cmp    qword [rbp+(-216)], 0
	jnz    main_if_true_8
	jz     main_if_false_9
main_if_true_8:
														;jump %while_after
	jmp    main_while_after_17
main_while_after_17:
														;ret 0
	mov    rax, 0
	leave
	ret
														;jump %exit
	jmp    main_exit_18
main_if_false_9:
														;jump %if_merge
	jmp    main_if_merge_10
main_if_merge_10:
														;$t423 = seq $t57 1
	mov    r11, qword [rbp+(-112)]
	cmp    r11, 1
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-192)], r11
														;br $t423 %if_true %if_false
	cmp    qword [rbp+(-192)], 0
	jnz    main_if_true_11
	jz     main_if_false_12
main_if_true_11:
														;$t424 = call __builtin_getString
	call   __builtin_getString
	mov    qword [rbp+(-72)], rax
														;$t58 = move $t424
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-184)], r11
														;call computeSHA1 $t58
	mov    rdi, qword [rbp+(-184)]
	call   computeSHA1
														;jump %if_merge
	jmp    main_if_merge_16
main_if_merge_16:
														;jump %while_loop
	jmp    main_while_loop_6
main_if_false_12:
														;$t425 = seq $t57 2
	mov    r11, qword [rbp+(-112)]
	cmp    r11, 2
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-160)], r11
														;br $t425 %if_true %if_false
	cmp    qword [rbp+(-160)], 0
	jnz    main_if_true_13
	jz     main_if_false_14
main_if_true_13:
														;$t426 = call __builtin_getString
	call   __builtin_getString
	mov    qword [rbp+(-96)], rax
														;$t58 = move $t426
	mov    r11, qword [rbp+(-96)]
	mov    qword [rbp+(-184)], r11
														;call crackSHA1 $t58
	mov    rdi, qword [rbp+(-184)]
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
	leave
	ret


SECTION .data
GV_MAXLENGTH:
	dq 0
CONST_STRING_89:
	db 0
GV_inputBuffer:
	dq 0
GV_MAXCHUNK:
	dq 0
CONST_STRING_404:
	db 78, 111, 116, 32, 70, 111, 117, 110, 100, 33, 0
CONST_STRING_364:
	db 73, 110, 118, 97, 108, 105, 100, 32, 105, 110, 112, 117, 116, 0
CONST_STRING_147:
	db 110, 67, 104, 117, 110, 107, 32, 62, 32, 77, 65, 88, 67, 72, 85, 78, 75, 33, 0
CONST_STRING_90:
	db 0
CONST_STRING_400:
	db 0
GV_asciiTable:
	dq 0
CONST_STRING_405:
	db 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 0
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

