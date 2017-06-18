import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        BBB a = new BBB();
        a.a = 1;

        BBB b = a;
        b.a = 2;

        System.out.println(a.a);
    }
}