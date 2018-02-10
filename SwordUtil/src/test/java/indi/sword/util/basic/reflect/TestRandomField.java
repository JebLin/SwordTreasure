//package indi.sword.util.basic.reflect;
//
//import indi.sword.util.base.bean.Result;
//
//public class TestRandomField {
//
//
//
//    public static void main(String[] args) {
//
//        TestRandomField java_field=new TestRandomField();
//        int number=java_field.get(1);
//        System.out.println("number"+number);
//
//        String str=java_field.get("1");
//        System.out.println("str:"+str);
//
//        double aDouble=java_field.get(1.32d);
//        System.out.println("aDouble:"+aDouble);
//
//        float aFloat=java_field.get(1.2f);
//        System.out.println("aFloat:"+aFloat);
//    }
//
//    <V> V get(Object obj){
//        return (V)obj;
//    }
//
//    /**
//     * 通过回传的response返回Result对象
//     * @Descrption
//     * @author rd_jianbin_lin
//     * @param clazz
//     * @Date Oct 20, 2017 8:32:43 PM
//     */
//    private <clazz> clazz getResultByResponse(String response, Class<?> clazz) {
//        Result result = new Result();
//
//        return (clazz)result;
//    }
//
//}
//
