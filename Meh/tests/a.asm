        global  main
        extern  puts
        extern  printf
        extern malloc

        section .text
main:                                   ; This is called by the C library startup code
        mov     rdi, format       ; First integer (or pointer) argument in rdi
        mov     rsi, 20
        mov     rdx, 10
        call    malloc                  ; puts(message)
        mov     dword [rax + 4], 3
        mov     dword [rax + 8], 5
        mov     r8d, dword[rax + 8]
        add     dword [rax + 4], r8d
        mov     esi, dword [rax + 4]
        mov     edi, format
        mov     rax, 0
        call    printf

        ret                             ; Return from main back into C library wrapper
message:
        db      "Hola, mundo", 0        ; Note strings must be terminated with 0 in C
format:
        db      "%d", 10, 0
