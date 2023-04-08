package ricardo.cn.juc;

public class TestException {

    public static void main(String[] args) {
        try {
            badMethod();
            System.out.println("A");
        }catch (RuntimeException e){
            System.out.println("B");
        }catch (Exception e){
            System.out.println("c");
        }finally {
            System.out.println("D");
        }
    }

    public static void badMethod(){
        throw new RuntimeException();
    }
}
