package indi.sword.util._00interview;

public class TestTransferToChinese2 {

    public static void main(String[] args) {

        String stri = "0005055123456789.12666345";
        System.out.println(stri);
        while(stri.substring(0,1).equals("0")){
            stri = stri.substring(1);
        }

        String positiveStr = "";
        String negativeStr = "";
        if(stri.contains(".")){
            positiveStr = stri.substring(0,stri.indexOf('.'));
            negativeStr = stri.substring(stri.indexOf('.') + 1,stri.length());
        }
        System.out.println("positiveStr -> " + positiveStr);
        System.out.println("negativeStr -> " + negativeStr);
        int blockLength = (int)Math.ceil(positiveStr.length() / 4.0);
        System.out.println("positiveStr.length() -> " + positiveStr.length());
        System.out.println("blockLength -> " + blockLength);
        long[] block = new long[blockLength];

        long positive = Long.parseLong(positiveStr);
        long temp = positive;
        int index = 0;
        long lessure = 0;
        while(temp > 10000){
            lessure = temp % 10000;
            temp /= 10000;
            block[index] = lessure;
            index++;
            System.out.println(lessure);
        }
        System.out.println(temp);

//        for(int i = 0;i< block.length;i++){
//            System.out.println(block[i]);
//        }



    }
}
