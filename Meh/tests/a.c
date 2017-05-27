#include <stdio.h>
#include <string.h>

int main() {
    char a[100];

    char cc;
    int i = 0;
    while (true) {
        cc = getchar();
        a[i] = cc;
        i = i + 1;
        if (cc == '\n') {
            break;
        }
    }


    return 0;
}