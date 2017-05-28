#include <stdio.h>
#include <string.h>

int main() {
    long long a = 5;
    long long b = 3;

    scanf("%lld%lld", &a, &b);

    long long c = (a > b);

    printf("%lld\n", c);

    return 0;
}