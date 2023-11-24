package indi.sword.util.basic.reflect;



public class TestRandomField {

    public static void main(String[] args) {

        TestRandomField java_field=new TestRandomField();
        int number=java_field.get(1);
        System.out.println("number"+number);

        String str=java_field.get("1");
        System.out.println("str:"+str);

        double aDouble=java_field.get(1.32d);
        System.out.println("aDouble:"+aDouble);

        float aFloat=java_field.get(1.2f);
        System.out.println("aFloat:"+aFloat);
    }

    <V> V get(Object obj){
        return (V)obj;
    }


}


