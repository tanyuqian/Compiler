public class Main {
    public static void fff(CCC x) {
        x.c();
    }

    public static void main(String[] args) {
        BBB b = new BBB();
        CCC c = new CCC();

        b.son = c;
        c.son = b;

        AAA a = b.son;

        DDD d = new DDD();

        System.out.println(String.valueOf(true));

    }
}