#include <stdio.h>
#include <string.h>

int main() {
    char *a = new char [100];
    char *b = new char [100];

    b[0] = 'b';
    b[1] = '\0';
    strcpy(a, b);

    //puts(a);

    return 0;
}