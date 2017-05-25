#include <stdio.h>

int func(int x, int y) {
    return 10 * x + y;
}

int main() {
    int a = 5;
    int b = 3;

    int c = func(a, b);

    printf("%d\n", c);

    return 0;
}