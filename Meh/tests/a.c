#include <stdio.h>

int main() {
    int *a = new int[100];

    a[99] = 5;

    printf("%d\n", a[99]);

    return 0;
}