import java.util.InputMismatchException;

public class Main {
    public static void fff(CCC x) {
        x.c();
    }

    class Int{
        int a;
    }

    public static void change(Int aa) {
        aa.a = 3;
    }

    public static void main(String[] args) {
        int aaa = 1;
        Integer tmp = new Integer(aaa);
        change(tmp);
        aaa = tmp;
        System.out.println(aaa);
    }
}