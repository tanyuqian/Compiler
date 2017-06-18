	.text
____builtin____print:
	li $v0, 4
	syscall
	jr $ra

____builtin____print_int:
	li $v0, 1
	syscall
	jr $ra

____builtin____println:
	li $v0, 4
	syscall
	la $a0, ____string_newline
	syscall
	jr $ra

____builtin____get_string:
	la $a0, ____string_buffer
	li $a1, 1024
	li $v0, 8
	syscall
	move $v1, $a0
	li $a1, 0
____label_2_condition:
	lb $v0, ($a0)
	beqz $v0, ____label_2_string_space
	add $a1, $a1, 1
	add $a0, $a0, 1
	j ____label_2_condition
____label_2_string_space:
	add $a0, $a1, 5
	li $v0, 9
	syscall
	sw $a1, ($v0)
	add $v0, $v0, 4
	move $a0, $v0
	la $a1 ____string_buffer
____label_2_copy_condition:
	lb $v1, ($a1)
	beqz $v1, ____label_2_end
	sb $v1, ($a0)
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_2_copy_condition
____label_2_end:
	sb $0, ($a0)
	jr $ra

____builtin____get_int:
	li $v0, 5
	syscall
	jr $ra

____builtin____to_string:
	move $a1, $a0
	li $a0, 16
	li $v0, 9
	syscall
	bnez $a1, ____label_12_not_zero
	lb $0, 5($v0)
	li $a0, 48
	sb $a0, 4($v0)
	li $a0, 1
	sb $a0, ($v0)
	j ____label_12_end_to_string
____label_12_not_zero:
	slt $v1, $a1, 0
	add $a3, $v0, 15
	sb $0, ($a3)
____label_12_condition:
	beqz $a1, ____label_12_end_loop
	rem $a0, $a1, 10
	div $a1, $a1, 10
	beqz $v1, ____label_12_load_char
	ble $a0, 0, ____label_12_to_negative
	add $a0, $a0, -10
____label_12_to_negative:
	neg $a0, $a0
____label_12_load_char:
	add $a0, $a0, 48
	add $a3, $a3, -1
	sb $a0, ($a3)
	j ____label_12_condition
____label_12_end_loop:
	beqz $v1, ____label_12_Copy
	li $a0, 45
	add $a3, $a3, -1
	sb $a0, ($a3)
____label_12_Copy:
	sub $v1, $v0, $a3
	add $v1, $v1, 15
	sw $v1, ($v0)
	add $v1, $v0, 4
____label_12_copy_condition:
	lb $a0, ($a3)
	sb $a0, ($v1)
	add $a3, $a3, 1
	add $v1, $v1, 1
	bnez $a0 ____label_12_copy_condition
____label_12_end_to_string:
	add $v0, $v0, 4
	jr $ra

____builtin_array____size:
	lw $v0, -4($a0)
	jr $ra

____builtin_string____length:
	lw $v0, -4($a0)
	jr $ra

____builtin_string____ord:
	addu $a0, $a0, $a1
	lb $v0, 0($a0)
	jr $ra

____builtin_string____substring:
	add $a2, $a2, 1
	add $a1, $a0, $a1
	add $a2, $a0, $a2
	sub $v1, $a2, $a1
	add $a0, $v1, 5
	li $v0, 9
	syscall
	sw $v1, ($v0)
	add $v0, $v0, 4
	move $a0, $v0
____label_1_loop:
	beq $a1, $a2, ____label_1_end
	lb $v1, ($a1)
	sb $v1, ($a0)
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_1_loop
____label_1_end:
	sb $0, ($a0)
	jr $ra

____builtin_string____parse_int:
	li $v0, 0
	lb $a3, ($a0)
	seq $v1, $a3, 45
	add $a0, $a0, $v1
____label_0_condition:
	lb $a3, ($a0)
	blt $a3, 48, ____label_0_Next
	bgt $a3, 57, ____label_0_Next
	add $a3, $a3, -48
	beqz $v1 ____label_0_not_neg
	neg $a3, $a3
____label_0_not_neg:
	mul $v0, $v0, 10
	add $v0, $v0, $a3
	add $a0, $a0, 1
	j ____label_0_condition
____label_0_Next:
	jr $ra

____builtin_string____concatenate:
	move $v1, $a0
	lw $v0, -4($a0)
	lw $a0, -4($a1)
	add $a0, $a0, $v0
	move $a2, $a0
	add $a0, $a0, 5
	li $v0, 9
	syscall
	sw $a2, ($v0)
	add $v0, $v0, 4
	move $a0, $v0
____label_5_copy_first:
	lb $a2, ($v1)
	beqz $a2, ____label_5_copy_second
	sb $a2, ($a0)
	add $a0, $a0, 1
	add $v1, $v1, 1
	j ____label_5_copy_first
____label_5_copy_second:
	lb $v1, ($a1)
	beqz $v1, ____label_5_end
	sb $v1, ($a0)
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_5_copy_second
____label_5_end:
	sb $0, ($a0)
	jr $ra

____builtin_string____equal_to:
____label_6_begin:
	lb $v0, ($a0)
	lb $v1, ($a1)
	beqz $v0, ____label_6_end
	beqz $v1, ____label_6_end
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_6_begin
____label_6_end:
	seq $v0, $v0, $v1
	jr $ra

____builtin_string____greater_than:
____label_7_begin:
	lb $v0, ($a0)
	lb $v1, ($a1)
	beqz $v0, ____label_7_end
	beqz $v1, ____label_7_end
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_7_begin
____label_7_end:
	sgt $v0, $v0, $v1
	jr $ra

____builtin_string____greater_than_or_equal_to:
____label_8_begin:
	lb $v0, ($a0)
	lb $v1, ($a1)
	beqz $v0, ____label_8_end
	beqz $v1, ____label_8_end
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_8_begin
____label_8_end:
	sge $v0, $v0, $v1
	jr $ra

____builtin_string____less_than:
____label_9_begin:
	lb $v0, ($a0)
	lb $v1, ($a1)
	beqz $v0, ____label_9_end
	beqz $v1, ____label_9_end
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_9_begin
____label_9_end:
	slt $v0, $v0, $v1
	jr $ra

____builtin_string____less_than_or_equal_to:
____label_10_begin:
	lb $v0, ($a0)
	lb $v1, ($a1)
	beqz $v0, ____label_10_end
	beqz $v1, ____label_10_end
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_10_begin
____label_10_end:
	sle $v0, $v0, $v1
	jr $ra

____builtin_string____not_equal_to:
____label_11_begin:
	lb $v0, ($a0)
	lb $v1, ($a1)
	beqz $v0, ____label_11_end
	beqz $v1, ____label_11_end
	add $a0, $a0, 1
	add $a1, $a1, 1
	j ____label_11_begin
____label_11_end:
	sne $v0, $v0, $v1
	jr $ra

	.data
____string_buffer:
	.space 1024
	.align 2
____string_newline:
	.asciiz "\n"
	.align 2