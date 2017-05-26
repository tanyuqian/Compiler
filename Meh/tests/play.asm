	global main
	extern malloc
	extern printf
	extern puts

SECTION .text
AAA:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 64
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
							;	%enter
AAA_enter_0:
							;	jump %entry
	jmp    AAA_entry_1
							;	%entry
AAA_entry_1:
							;	store 8 $p0 5 0
	mov    r11, qword [rbp+(-8)]
	add    r11, 0
	mov    rax, 5
	mov    qword [r11], rax
							;	store 8 $p0 3 8
	mov    r11, qword [rbp+(-8)]
	add    r11, 8
	mov    rax, 3
	mov    qword [r11], rax
							;	jump %exit
	jmp    AAA_exit_2
							;	%exit
AAA_exit_2:
	leave
	ret


AAA.func:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 96
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
							;	%enter
AAA.func_enter_0:
							;	jump %entry
	jmp    AAA.func_entry_1
							;	%entry
AAA.func_entry_1:
							;	$t5 = load 8 $p1 0
	mov    r11, qword [rbp+(-8)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-72)], rax
							;	$t6 = mul $t5 10
	mov    r11, qword [rbp+(-72)]
	imul   r11, 10
	mov    qword [rbp+(-64)], r11
							;	$t7 = load 8 $p1 8
	mov    r11, qword [rbp+(-8)]
	add    r11, 8
	mov    rax, qword [r11]
	mov    qword [rbp+(-80)], rax
							;	$t8 = add $t6 $t7
	mov    r11, qword [rbp+(-64)]
	add    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-88)], r11
							;	ret $t8
	mov    rax, qword [rbp+(-88)]
	leave
	ret
							;	jump %exit
	jmp    AAA.func_exit_2
							;	jump %exit
	jmp    AAA.func_exit_2
							;	%exit
AAA.func_exit_2:
	leave
	ret


main:
	push   rbp
	mov    rbp, rsp
	sub    rsp, 112
	mov    qword [rbp-8], rdi
	mov    qword [rbp-16], rsi
	mov    qword [rbp-24], rdx
	mov    qword [rbp-32], rcx
	mov    qword [rbp-40], r8
	mov    qword [rbp-48], r9
							;	%enter
main_enter_0:
							;	jump %entry
	jmp    main_entry_1
							;	%entry
main_entry_1:
							;	$t9 = alloc 16
	mov    rdi, 16
	call   malloc
	mov    qword [rbp+(-64)], rax
							;	call AAA $t9
	mov    rdi, qword [rbp+(-64)]
	call   AAA
							;	$t2 = move $t9
	mov    r11, qword [rbp+(-64)]
	mov    qword [rbp+(-72)], r11
							;	$t10 = load 8 $t2 0
	mov    r11, qword [rbp+(-72)]
	add    r11, 0
	mov    rax, qword [r11]
	mov    qword [rbp+(-80)], rax
							;	$t3 = move $t10
	mov    r11, qword [rbp+(-80)]
	mov    qword [rbp+(-96)], r11

	mov     rax, qword [rbp-96]
            mov     rsi, rax
            mov     rdi, L_001
            mov     rax, 0
            call    printf

							;	$t11 = call AAA.func $t2
	mov    rdi, qword [rbp+(-72)]
	call   AAA.func
	mov    qword [rbp+(-88)], rax
							;	$t4 = move $t11
	mov    r11, qword [rbp+(-88)]
	mov    qword [rbp+(-104)], r11
							;	ret 0
	mov    rax, 0
	leave
	ret
							;	jump %exit
	jmp    main_exit_2
							;	jump %exit
	jmp    main_exit_2
							;	%exit
main_exit_2:
	leave
	ret


SECTION .data

L_001:
        db 25H, 64H, 0AH, 00H

