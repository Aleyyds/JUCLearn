package ricardo.cn.ImmutableObject;

public class StringTest {
    public static void main(String[] args) {
        String str = "Hello world";
        String s = str.substring(0, 6);
        System.out.println(str.hashCode());
        System.out.println(s.hashCode());

    }
}
