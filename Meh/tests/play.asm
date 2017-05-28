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

add:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 152
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
add_enter_0:
														;jump %entry
	jmp    add_entry_1
														;%entry
add_entry_1:
														;$g1(ne) = add $g1(ne) 1
	mov    r11, qword [rel GV_ne]
	add    r11, 1
	mov    qword [rel GV_ne], r11
														;$t22 = mul $g1(ne) 8
	mov    r11, qword [rel GV_ne]
	imul   r11, 8
	mov    qword [rbp+(-64)], r11
														;$t21 = add $g0(e) $t22
	mov    r11, qword [rel GV_e]
	add    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-136)], r11
														;$t23 = load 8 $t21 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-120)], r12
														;store 8 $t23 $p8 0
	mov    r11, qword [rbp+(-120)]
	add    r11, 0
	mov    rax, qword [rbp+(-16)]
	mov    qword [r11], rax
														;$t25 = mul $g1(ne) 8
	mov    r11, qword [rel GV_ne]
	imul   r11, 8
	mov    qword [rbp+(-72)], r11
														;$t24 = add $g0(e) $t25
	mov    r11, qword [rel GV_e]
	add    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-96)], r11
														;$t26 = load 8 $t24 0
	mov    r11, qword [rbp+(-96)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-128)], r12
														;$t28 = mul $p7 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-112)], r11
														;$t27 = add $g3(head) $t28
	mov    r11, qword [rel GV_head]
	add    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-144)], r11
														;$t29 = load 8 $t27 0
	mov    r11, qword [rbp+(-144)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-104)], r12
														;store 8 $t26 $t29 8
	mov    r11, qword [rbp+(-128)]
	add    r11, 8
	mov    rax, qword [rbp+(-104)]
	mov    qword [r11], rax
														;$t31 = mul $p7 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-80)], r11
														;$t30 = add $g3(head) $t31
	mov    r11, qword [rel GV_head]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-88)], r11
														;store 8 $t30 $g1(ne) 0
	mov    r11, qword [rbp+(-88)]
	add    r11, 0
	mov    rax, qword [rel GV_ne]
	mov    qword [r11], rax
														;jump %exit
	jmp    add_exit_2
														;%exit
add_exit_2:
	leave
	ret


init:
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
init_enter_0:
														;jump %entry
	jmp    init_entry_1
														;%entry
init_entry_1:
														;$t33 = move 800
	mov    r11, 800
	mov    qword [rbp+(-96)], r11
														;$t33 = add $t33 8
	mov    r11, qword [rbp+(-96)]
	add    r11, 8
	mov    qword [rbp+(-96)], r11
														;$t32 = alloc $t33
	mov    rdi, qword [rbp+(-96)]
	call   malloc
	mov    qword [rbp+(-216)], rax
														;store 8 $t32 100 0
	mov    r11, qword [rbp+(-216)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t32 = add $t32 8
	mov    r11, qword [rbp+(-216)]
	add    r11, 8
	mov    qword [rbp+(-216)], r11
														;$t34 = move 0
	mov    r11, 0
	mov    qword [rbp+(-248)], r11
														;jump %new_condition
	jmp    init_new_condition_2
														;%new_condition
init_new_condition_2:
														;$t35 = slt $t34 100
	mov    r11, qword [rbp+(-248)]
	cmp    r11, 100
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-160)], r11
														;br $t35 %new_body %new_exit
	cmp    qword [rbp+(-160)], 0
	jnz    init_new_body_3
	jz     init_new_exit_5
														;%new_body
init_new_body_3:
														;$t36 = alloc 16
	mov    rdi, 16
	call   malloc
	mov    qword [rbp+(-240)], rax
														;$t37 = mul $t34 8
	mov    r11, qword [rbp+(-248)]
	imul   r11, 8
	mov    qword [rbp+(-224)], r11
														;$t38 = add $t32 $t37
	mov    r11, qword [rbp+(-216)]
	add    r11, qword [rbp+(-224)]
	mov    qword [rbp+(-128)], r11
														;store 8 $t38 $t36 0
	mov    r11, qword [rbp+(-128)]
	add    r11, 0
	mov    rax, qword [rbp+(-240)]
	mov    qword [r11], rax
														;jump %new_loop
	jmp    init_new_loop_4
														;%new_loop
init_new_loop_4:
														;$t34 = add $t34 1
	mov    r11, qword [rbp+(-248)]
	add    r11, 1
	mov    qword [rbp+(-248)], r11
														;jump %new_condition
	jmp    init_new_condition_2
														;jump %new_exit
	jmp    init_new_exit_5
														;%new_exit
init_new_exit_5:
														;$g0(e) = move $t32
	mov    r11, qword [rbp+(-216)]
	mov    qword [rel GV_e], r11
														;$g1(ne) = move 0
	mov    r11, 0
	mov    qword [rel GV_ne], r11
														;$g4(ans) = move 0
	mov    r11, 0
	mov    qword [rel GV_ans], r11
														;$g5(size) = move $g2(n)
	mov    r11, qword [rel GV_n]
	mov    qword [rel GV_size], r11
														;$t40 = move 800
	mov    r11, 800
	mov    qword [rbp+(-144)], r11
														;$t40 = add $t40 8
	mov    r11, qword [rbp+(-144)]
	add    r11, 8
	mov    qword [rbp+(-144)], r11
														;$t39 = alloc $t40
	mov    rdi, qword [rbp+(-144)]
	call   malloc
	mov    qword [rbp+(-72)], rax
														;store 8 $t39 100 0
	mov    r11, qword [rbp+(-72)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t39 = add $t39 8
	mov    r11, qword [rbp+(-72)]
	add    r11, 8
	mov    qword [rbp+(-72)], r11
														;$g9(visit) = move $t39
	mov    r11, qword [rbp+(-72)]
	mov    qword [rel GV_visit], r11
														;$t42 = move 800
	mov    r11, 800
	mov    qword [rbp+(-208)], r11
														;$t42 = add $t42 8
	mov    r11, qword [rbp+(-208)]
	add    r11, 8
	mov    qword [rbp+(-208)], r11
														;$t41 = alloc $t42
	mov    rdi, qword [rbp+(-208)]
	call   malloc
	mov    qword [rbp+(-112)], rax
														;store 8 $t41 100 0
	mov    r11, qword [rbp+(-112)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t41 = add $t41 8
	mov    r11, qword [rbp+(-112)]
	add    r11, 8
	mov    qword [rbp+(-112)], r11
														;$g10(son) = move $t41
	mov    r11, qword [rbp+(-112)]
	mov    qword [rel GV_son], r11
														;$t44 = move 800
	mov    r11, 800
	mov    qword [rbp+(-64)], r11
														;$t44 = add $t44 8
	mov    r11, qword [rbp+(-64)]
	add    r11, 8
	mov    qword [rbp+(-64)], r11
														;$t43 = alloc $t44
	mov    rdi, qword [rbp+(-64)]
	call   malloc
	mov    qword [rbp+(-104)], rax
														;store 8 $t43 100 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t43 = add $t43 8
	mov    r11, qword [rbp+(-104)]
	add    r11, 8
	mov    qword [rbp+(-104)], r11
														;$g11(maxson) = move $t43
	mov    r11, qword [rbp+(-104)]
	mov    qword [rel GV_maxson], r11
														;$t46 = move 800
	mov    r11, 800
	mov    qword [rbp+(-88)], r11
														;$t46 = add $t46 8
	mov    r11, qword [rbp+(-88)]
	add    r11, 8
	mov    qword [rbp+(-88)], r11
														;$t45 = alloc $t46
	mov    rdi, qword [rbp+(-88)]
	call   malloc
	mov    qword [rbp+(-176)], rax
														;store 8 $t45 100 0
	mov    r11, qword [rbp+(-176)]
	add    r11, 0
	mov    rax, 100
	mov    qword [r11], rax
														;$t45 = add $t45 8
	mov    r11, qword [rbp+(-176)]
	add    r11, 8
	mov    qword [rbp+(-176)], r11
														;$g3(head) = move $t45
	mov    r11, qword [rbp+(-176)]
	mov    qword [rel GV_head], r11
														;$g6(i) = move 0
	mov    r11, 0
	mov    qword [rel GV_i], r11
														;jump %for_condition
	jmp    init_for_condition_6
														;%for_condition
init_for_condition_6:
														;$t47 = sle $g6(i) $g2(n)
	mov    r11, qword [rel GV_i]
	cmp    r11, qword [rel GV_n]
	setle  al
	movzx  r11, al
	mov    qword [rbp+(-184)], r11
														;br $t47 %for_body %for_after
	cmp    qword [rbp+(-184)], 0
	jnz    init_for_body_7
	jz     init_for_after_9
														;%for_body
init_for_body_7:
														;$t49 = mul $g6(i) 8
	mov    r11, qword [rel GV_i]
	imul   r11, 8
	mov    qword [rbp+(-120)], r11
														;$t48 = add $g9(visit) $t49
	mov    r11, qword [rel GV_visit]
	add    r11, qword [rbp+(-120)]
	mov    qword [rbp+(-200)], r11
														;store 8 $t48 0 0
	mov    r11, qword [rbp+(-200)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t51 = mul $g6(i) 8
	mov    r11, qword [rel GV_i]
	imul   r11, 8
	mov    qword [rbp+(-168)], r11
														;$t50 = add $g10(son) $t51
	mov    r11, qword [rel GV_son]
	add    r11, qword [rbp+(-168)]
	mov    qword [rbp+(-80)], r11
														;store 8 $t50 0 0
	mov    r11, qword [rbp+(-80)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t53 = mul $g6(i) 8
	mov    r11, qword [rel GV_i]
	imul   r11, 8
	mov    qword [rbp+(-232)], r11
														;$t52 = add $g11(maxson) $t53
	mov    r11, qword [rel GV_maxson]
	add    r11, qword [rbp+(-232)]
	mov    qword [rbp+(-136)], r11
														;store 8 $t52 0 0
	mov    r11, qword [rbp+(-136)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;$t55 = mul $g6(i) 8
	mov    r11, qword [rel GV_i]
	imul   r11, 8
	mov    qword [rbp+(-192)], r11
														;$t54 = add $g3(head) $t55
	mov    r11, qword [rel GV_head]
	add    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-152)], r11
														;store 8 $t54 0 0
	mov    r11, qword [rbp+(-152)]
	add    r11, 0
	mov    rax, 0
	mov    qword [r11], rax
														;jump %for_loop
	jmp    init_for_loop_8
														;%for_loop
init_for_loop_8:
														;$g6(i) = add $g6(i) 1
	mov    r11, qword [rel GV_i]
	add    r11, 1
	mov    qword [rel GV_i], r11
														;jump %for_condition
	jmp    init_for_condition_6
														;%for_after
init_for_after_9:
														;jump %exit
	jmp    init_exit_10
														;%exit
init_exit_10:
	leave
	ret


max:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 72
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
max_enter_0:
														;jump %entry
	jmp    max_entry_1
														;%entry
max_entry_1:
														;$t56 = sgt $p12 $p13
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rbp+(-16)]
	setg   al
	movzx  r11, al
	mov    qword [rbp+(-64)], r11
														;br $t56 %if_true %if_false
	cmp    qword [rbp+(-64)], 0
	jnz    max_if_true_2
	jz     max_if_false_3
														;%if_true
max_if_true_2:
														;ret $p12
	mov    rax, qword [rbp+(-8)]
	leave
	ret
														;jump %exit
	jmp    max_exit_5
														;jump %if_merge
	jmp    max_if_merge_4
														;%if_false
max_if_false_3:
														;ret $p13
	mov    rax, qword [rbp+(-16)]
	leave
	ret
														;jump %exit
	jmp    max_exit_5
														;jump %if_merge
	jmp    max_if_merge_4
														;%if_merge
max_if_merge_4:
														;jump %exit
	jmp    max_exit_5
														;%exit
max_exit_5:
	leave
	ret


dfs:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 440
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
														;%enter
dfs_enter_0:
														;jump %entry
	jmp    dfs_entry_1
														;%entry
dfs_entry_1:
														;$t15 = move 0
	mov    r11, 0
	mov    qword [rbp+(-304)], r11
														;$t58 = mul $p14 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-384)], r11
														;$t57 = add $g9(visit) $t58
	mov    r11, qword [rel GV_visit]
	add    r11, qword [rbp+(-384)]
	mov    qword [rbp+(-104)], r11
														;store 8 $t57 1 0
	mov    r11, qword [rbp+(-104)]
	add    r11, 0
	mov    rax, 1
	mov    qword [r11], rax
														;$t60 = mul $p14 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-264)], r11
														;$t59 = add $g3(head) $t60
	mov    r11, qword [rel GV_head]
	add    r11, qword [rbp+(-264)]
	mov    qword [rbp+(-328)], r11
														;$t61 = load 8 $t59 0
	mov    r11, qword [rbp+(-328)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-184)], r12
														;$t16 = move $t61
	mov    r11, qword [rbp+(-184)]
	mov    qword [rbp+(-232)], r11
														;jump %for_condition
	jmp    dfs_for_condition_2
														;%for_condition
dfs_for_condition_2:
														;$t62 = sne $t16 0
	mov    r11, qword [rbp+(-232)]
	cmp    r11, 0
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-368)], r11
														;br $t62 %for_body %for_after
	cmp    qword [rbp+(-368)], 0
	jnz    dfs_for_body_3
	jz     dfs_for_after_8
														;%for_body
dfs_for_body_3:
														;$t64 = mul $t16 8
	mov    r11, qword [rbp+(-232)]
	imul   r11, 8
	mov    qword [rbp+(-136)], r11
														;$t63 = add $g0(e) $t64
	mov    r11, qword [rel GV_e]
	add    r11, qword [rbp+(-136)]
	mov    qword [rbp+(-120)], r11
														;$t65 = load 8 $t63 0
	mov    r11, qword [rbp+(-120)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-312)], r12
														;$t66 = load 8 $t65 0
	mov    r11, qword [rbp+(-312)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-152)], r12
														;$t17 = move $t66
	mov    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-376)], r11
														;$t68 = mul $t17 8
	mov    r11, qword [rbp+(-376)]
	imul   r11, 8
	mov    qword [rbp+(-416)], r11
														;$t67 = add $g9(visit) $t68
	mov    r11, qword [rel GV_visit]
	add    r11, qword [rbp+(-416)]
	mov    qword [rbp+(-144)], r11
														;$t69 = load 8 $t67 0
	mov    r11, qword [rbp+(-144)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-96)], r12
														;$t70 = seq $t69 0
	mov    r11, qword [rbp+(-96)]
	cmp    r11, 0
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-336)], r11
														;br $t70 %if_true %if_false
	cmp    qword [rbp+(-336)], 0
	jnz    dfs_if_true_4
	jz     dfs_if_false_5
														;%if_true
dfs_if_true_4:
														;call dfs $t17
	mov    rdi, qword [rbp+(-376)]
	call   dfs
														;$t72 = mul $p14 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-168)], r11
														;$t71 = add $g10(son) $t72
	mov    r11, qword [rel GV_son]
	add    r11, qword [rbp+(-168)]
	mov    qword [rbp+(-224)], r11
														;$t74 = mul $p14 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-128)], r11
														;$t73 = add $g10(son) $t74
	mov    r11, qword [rel GV_son]
	add    r11, qword [rbp+(-128)]
	mov    qword [rbp+(-408)], r11
														;$t75 = load 8 $t73 0
	mov    r11, qword [rbp+(-408)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-240)], r12
														;$t77 = mul $t17 8
	mov    r11, qword [rbp+(-376)]
	imul   r11, 8
	mov    qword [rbp+(-280)], r11
														;$t76 = add $g10(son) $t77
	mov    r11, qword [rel GV_son]
	add    r11, qword [rbp+(-280)]
	mov    qword [rbp+(-216)], r11
														;$t78 = load 8 $t76 0
	mov    r11, qword [rbp+(-216)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-160)], r12
														;$t79 = add $t78 1
	mov    r11, qword [rbp+(-160)]
	add    r11, 1
	mov    qword [rbp+(-392)], r11
														;$t80 = add $t75 $t79
	mov    r11, qword [rbp+(-240)]
	add    r11, qword [rbp+(-392)]
	mov    qword [rbp+(-176)], r11
														;store 8 $t71 $t80 0
	mov    r11, qword [rbp+(-224)]
	add    r11, 0
	mov    rax, qword [rbp+(-176)]
	mov    qword [r11], rax
														;$t82 = mul $t17 8
	mov    r11, qword [rbp+(-376)]
	imul   r11, 8
	mov    qword [rbp+(-360)], r11
														;$t81 = add $g10(son) $t82
	mov    r11, qword [rel GV_son]
	add    r11, qword [rbp+(-360)]
	mov    qword [rbp+(-256)], r11
														;$t83 = load 8 $t81 0
	mov    r11, qword [rbp+(-256)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-64)], r12
														;$t84 = add $t83 1
	mov    r11, qword [rbp+(-64)]
	add    r11, 1
	mov    qword [rbp+(-248)], r11
														;$t85 = call max $t15 $t84
	mov    rdi, qword [rbp+(-304)]
	mov    rsi, qword [rbp+(-248)]
	call   max
	mov    qword [rbp+(-344)], rax
														;$t15 = move $t85
	mov    r11, qword [rbp+(-344)]
	mov    qword [rbp+(-304)], r11
														;jump %if_merge
	jmp    dfs_if_merge_6
														;%if_false
dfs_if_false_5:
														;jump %if_merge
	jmp    dfs_if_merge_6
														;%if_merge
dfs_if_merge_6:
														;jump %for_loop
	jmp    dfs_for_loop_7
														;%for_loop
dfs_for_loop_7:
														;$t87 = mul $t16 8
	mov    r11, qword [rbp+(-232)]
	imul   r11, 8
	mov    qword [rbp+(-432)], r11
														;$t86 = add $g0(e) $t87
	mov    r11, qword [rel GV_e]
	add    r11, qword [rbp+(-432)]
	mov    qword [rbp+(-200)], r11
														;$t88 = load 8 $t86 0
	mov    r11, qword [rbp+(-200)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-400)], r12
														;$t89 = load 8 $t88 8
	mov    r11, qword [rbp+(-400)]
	add    r11, 8
	mov    r12, qword [r11]
	mov    qword [rbp+(-352)], r12
														;$t16 = move $t89
	mov    r11, qword [rbp+(-352)]
	mov    qword [rbp+(-232)], r11
														;jump %for_condition
	jmp    dfs_for_condition_2
														;%for_after
dfs_for_after_8:
														;$t91 = mul $p14 8
	mov    r11, qword [rbp+(-8)]
	imul   r11, 8
	mov    qword [rbp+(-192)], r11
														;$t90 = add $g10(son) $t91
	mov    r11, qword [rel GV_son]
	add    r11, qword [rbp+(-192)]
	mov    qword [rbp+(-272)], r11
														;$t92 = load 8 $t90 0
	mov    r11, qword [rbp+(-272)]
	add    r11, 0
	mov    r12, qword [r11]
	mov    qword [rbp+(-112)], r12
														;$t93 = sub $g2(n) $t92
	mov    r11, qword [rel GV_n]
	sub    r11, qword [rbp+(-112)]
	mov    qword [rbp+(-88)], r11
														;$t94 = sub $t93 1
	mov    r11, qword [rbp+(-88)]
	sub    r11, 1
	mov    qword [rbp+(-424)], r11
														;$t95 = call max $t15 $t94
	mov    rdi, qword [rbp+(-304)]
	mov    rsi, qword [rbp+(-424)]
	call   max
	mov    qword [rbp+(-72)], rax
														;$t15 = move $t95
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-304)], r11
														;$t97 = slt $t15 $g5(size)
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rel GV_size]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-208)], r11
														;br $t97 %logical_true %logical_false
	cmp    qword [rbp+(-208)], 0
	jnz    dfs_logical_true_13
	jz     dfs_logical_false_9
														;%logical_false
dfs_logical_false_9:
														;$t99 = seq $t15 $g5(size)
	mov    r11, qword [rbp+(-304)]
	cmp    r11, qword [rel GV_size]
	sete   al
	movzx  r11, al
	mov    qword [rbp+(-320)], r11
														;br $t99 %logical_true %logical_false
	cmp    qword [rbp+(-320)], 0
	jnz    dfs_logical_true_10
	jz     dfs_logical_false_11
														;%logical_true
dfs_logical_true_10:
														;$t100 = slt $p14 $g4(ans)
	mov    r11, qword [rbp+(-8)]
	cmp    r11, qword [rel GV_ans]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-288)], r11
														;$t98 = move $t100
	mov    r11, qword [rbp+(-288)]
	mov    qword [rbp+(-296)], r11
														;jump %logical_merge
	jmp    dfs_logical_merge_12
														;%logical_false
dfs_logical_false_11:
														;$t98 = move 0
	mov    r11, 0
	mov    qword [rbp+(-296)], r11
														;jump %logical_merge
	jmp    dfs_logical_merge_12
														;%logical_merge
dfs_logical_merge_12:
														;$t96 = move $t98
	mov    r11, qword [rbp+(-296)]
	mov    qword [rbp+(-80)], r11
														;jump %logical_merge
	jmp    dfs_logical_merge_14
														;%logical_true
dfs_logical_true_13:
														;$t96 = move 1
	mov    r11, 1
	mov    qword [rbp+(-80)], r11
														;jump %logical_merge
	jmp    dfs_logical_merge_14
														;%logical_merge
dfs_logical_merge_14:
														;br $t96 %if_true %if_false
	cmp    qword [rbp+(-80)], 0
	jnz    dfs_if_true_15
	jz     dfs_if_false_16
														;%if_true
dfs_if_true_15:
														;$g4(ans) = move $p14
	mov    r11, qword [rbp+(-8)]
	mov    qword [rel GV_ans], r11
														;$g5(size) = move $t15
	mov    r11, qword [rbp+(-304)]
	mov    qword [rel GV_size], r11
														;jump %if_merge
	jmp    dfs_if_merge_17
														;%if_false
dfs_if_false_16:
														;jump %if_merge
	jmp    dfs_if_merge_17
														;%if_merge
dfs_if_merge_17:
														;jump %exit
	jmp    dfs_exit_18
														;%exit
dfs_exit_18:
	leave
	ret


main:
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
main_enter_0:
														;$g1(ne) = move 0
	mov    r11, 0
	mov    qword [rel GV_ne], r11
														;$g2(n) = move 0
	mov    r11, 0
	mov    qword [rel GV_n], r11
														;$g4(ans) = move 0
	mov    r11, 0
	mov    qword [rel GV_ans], r11
														;$g5(size) = move 0
	mov    r11, 0
	mov    qword [rel GV_size], r11
														;$g6(i) = move 0
	mov    r11, 0
	mov    qword [rel GV_i], r11
														;jump %entry
	jmp    main_entry_1
														;%entry
main_entry_1:
														;$t101 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-64)], rax
														;$t18 = move $t101
	mov    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-88)], r11
														;jump %while_loop
	jmp    main_while_loop_2
														;%while_loop
main_while_loop_2:
														;$t102 = move $t18
	mov    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-96)], r11
														;$t18 = sub $t18 1
	mov    r11, qword [rbp+(-88)]
	sub    r11, 1
	mov    qword [rbp+(-88)], r11
														;$t103 = sne $t102 0
	mov    r11, qword [rbp+(-96)]
	cmp    r11, 0
	setne  al
	movzx  r11, al
	mov    qword [rbp+(-128)], r11
														;br $t103 %while_body %while_after
	cmp    qword [rbp+(-128)], 0
	jnz    main_while_body_3
	jz     main_while_after_8
														;%while_body
main_while_body_3:
														;$t104 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-160)], rax
														;$g2(n) = move $t104
	mov    r11, qword [rbp+(-160)]
	mov    qword [rel GV_n], r11
														;call init
	call   init
														;$g6(i) = move 0
	mov    r11, 0
	mov    qword [rel GV_i], r11
														;jump %for_condition
	jmp    main_for_condition_4
														;%for_condition
main_for_condition_4:
														;$t105 = sub $g2(n) 1
	mov    r11, qword [rel GV_n]
	sub    r11, 1
	mov    qword [rbp+(-112)], r11
														;$t106 = slt $g6(i) $t105
	mov    r11, qword [rel GV_i]
	cmp    r11, qword [rbp+(-112)]
	setl   al
	movzx  r11, al
	mov    qword [rbp+(-120)], r11
														;br $t106 %for_body %for_after
	cmp    qword [rbp+(-120)], 0
	jnz    main_for_body_5
	jz     main_for_after_7
														;%for_body
main_for_body_5:
														;$t107 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-152)], rax
														;$t19 = move $t107
	mov    r11, qword [rbp+(-152)]
	mov    qword [rbp+(-80)], r11
														;$t108 = call __builtin_getInt
	call   __builtin_getInt
	mov    qword [rbp+(-72)], rax
														;$t20 = move $t108
	mov    r11, qword [rbp+(-72)]
	mov    qword [rbp+(-104)], r11
														;call add $t19 $t20
	mov    rdi, qword [rbp+(-80)]
	mov    rsi, qword [rbp+(-104)]
	call   add
														;call add $t20 $t19
	mov    rdi, qword [rbp+(-104)]
	mov    rsi, qword [rbp+(-80)]
	call   add
														;jump %for_loop
	jmp    main_for_loop_6
														;%for_loop
main_for_loop_6:
														;$g6(i) = add $g6(i) 1
	mov    r11, qword [rel GV_i]
	add    r11, 1
	mov    qword [rel GV_i], r11
														;jump %for_condition
	jmp    main_for_condition_4
														;%for_after
main_for_after_7:
														;call dfs 1
	mov    rdi, 1
	call   dfs
														;$t109 = call __builtin_toString $g4(ans)
	mov    rdi, qword [rel GV_ans]
	call   __builtin_toString
	mov    qword [rbp+(-144)], rax
														;call __builtin_println $t109
	mov    rdi, qword [rbp+(-144)]
	call   __builtin_println
														;$t110 = call __builtin_toString $g5(size)
	mov    rdi, qword [rel GV_size]
	call   __builtin_toString
	mov    qword [rbp+(-136)], rax
														;call __builtin_println $t110
	mov    rdi, qword [rbp+(-136)]
	call   __builtin_println
														;jump %while_loop
	jmp    main_while_loop_2
														;%while_after
main_while_after_8:
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
GV_i:
	dq 0
GV_size:
	dq 0
GV_son:
	dq 0
GV_visit:
	dq 0
GV_ne:
	dq 0
GV_e:
	dq 0
GV_ans:
	dq 0
GV_n:
	dq 0
GV_head:
	dq 0
GV_maxson:
	dq 0
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

