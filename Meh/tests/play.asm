	global main


main:
	push   rbp
	mov    rbp, rsp
	;sub    rsp, 224									;$g0(a) = move 10
	mov    rax, 10
	mov    qword [rel GV_a], rax										;ret 0
	;mov    rax, 0
	leave
	ret

SECTION .data
GV_a:
	dq 0
