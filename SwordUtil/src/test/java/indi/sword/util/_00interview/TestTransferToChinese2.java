package indi.sword.util._00interview;

import java.util.Arrays;

/**
 * 自己写的一个转换
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/10 10:39
 */
/*
    我的想法：
    1、切块 0000A 0000A 0000A 0000A
    2、块中换成 千 百 十
    3、根据index把A换成万、亿
    4、处理小数点后，全部直接换中文即可
 */
public class TestTransferToChinese2 {

    public static void main(String[] args) {

        String stri = "3455055123456789.126123166345";
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
        System.out.println("---------------");
        long[] block = splitByNum(positiveStr);
        String[] strBlock = transferBlock2Cn(block);
        transferA(strBlock);
        System.out.println("transferA ----------- BEGIN --------");
        Arrays.stream(strBlock).forEach(System.out::println);
        System.out.println("transferA ----------- END --------");
        transfer2CnArr(strBlock);
        StringBuilder result = new StringBuilder();
        for (int i = strBlock.length -1; i >= 0  ; i--) {
            result.append(strBlock[i]);
        }
        System.out.println("deal with the negative part ...");
        if(!negativeStr.trim().isEmpty()){
            result.append("点");
            result.append(transfer2Cn(negativeStr));
        }
        System.out.println("the final result is " + result.toString() );


    }

    /**
     * 转换数据
     * @Decription
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/10 10:35
     */
    private static void transfer2CnArr(String[] strBlock) {
        for (int i = 0; i < strBlock.length; i++) {
            strBlock[i] = transfer2Cn(strBlock[i]);
        }
    }

    /**
     * 把A转成相应的单位
     * @Decription
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/10 10:02
     */
    private static void transferA(String[] strBlock) {
        int length = strBlock.length;
        strBlock[0] = strBlock[0].replace("A","");
        for(int i = 1;i < strBlock.length;i++){
            if(i % 2 == 0){
                strBlock[i] = strBlock[i].replace("A","亿");
            }else{
                strBlock[i] = strBlock[i].replace("A","万");
            }
        }
    }

    private static String[] transferBlock2Cn(long[] block) {
        System.out.println("transferBlock2Cn ----------- BEGIN --------");
        String[] returnStrArr = new String[block.length];
        for (int i = 0; i < block.length; i++) {
            StringBuilder sb = new StringBuilder("");
            long num = block[i];
            System.out.print(num);
            if(num / 1000 > 0){
                long lessure = num / 1000;
                sb.append(lessure + "千");
                num %= 1000;
            }
            if(num / 100 > 0){
                long lessure = num / 100;
                sb.append(lessure + "百");
                num %= 100;
            }
            if(num / 10 > 0){
                long lessure = num / 10;
                sb.append(lessure + "十");
                num %= 10;
            }
            sb.append(num + "A");
            returnStrArr[i] = sb.toString();
            System.out.println(" " + returnStrArr[i]);
        }
        System.out.println("transferBlock2Cn ----------- END --------");
        return returnStrArr;
    }

    private static String transfer2Cn(String str){
        str = str.replace('1', '一');
        str = str.replace('2', '二');
        str = str.replace('3', '三');
        str = str.replace('4', '四');
        str = str.replace('5', '五');
        str = str.replace('6', '六');
        str = str.replace('7', '七');
        str = str.replace('8', '八');
        str = str.replace('9', '九');
        str = str.replace('0', '零');
        str = str.replace('.', '点');
        return str;
    }



    /**
     * 将数字切成块
     * @Decription
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/10 9:36
     */
    private static long[] splitByNum(String positiveStr) {
        int blockLength = (int)Math.ceil(positiveStr.length() / 4.0); // 按照0000为单位切成几块
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
        }
        block[index] = temp;

//        Arrays.stream(block).forEach(System.out::println);
        return block;
    }

}
