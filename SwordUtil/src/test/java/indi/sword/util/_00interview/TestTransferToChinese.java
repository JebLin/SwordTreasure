package indi.sword.util._00interview;


/**
 *
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/7 18:23
 */
/*
    将一个键盘输入的数字转化成中文输出
    (例如:输入:1234567 输出:一百二拾三万四千五百六拾七)
    用java语言实现,,请编一段程序实现!
 */
public class TestTransferToChinese {
    private String strNum;

    private String strNumChFormat;

    private String strNumTemp;

    private int intNumLen;

    private String strBegin;

    public TestTransferToChinese(String strNum) {
        this.strNum = strNum;
    }

    public boolean check(String strNum) {
        boolean valid = false;
        while (strNum.substring(0, 1).equals("0")) {
            strNum = strNum.substring(1);
        }
        this.strNum = strNum;
        try {
            new Double(strNum);
            valid = true;
        } catch (NumberFormatException ex) {
            System.out.println("Bad number format!");
        }
        return valid;
    }

    public void init() {
        strNumChFormat = "";
        intNumLen = strNum.length();
        strNumTemp = strNum;
        strNumTemp = strNumTemp.replace('1', '一');
        strNumTemp = strNumTemp.replace('2', '二');
        strNumTemp = strNumTemp.replace('3', '三');
        strNumTemp = strNumTemp.replace('4', '四');
        strNumTemp = strNumTemp.replace('5', '五');
        strNumTemp = strNumTemp.replace('6', '六');
        strNumTemp = strNumTemp.replace('7', '七');
        strNumTemp = strNumTemp.replace('8', '八');
        strNumTemp = strNumTemp.replace('9', '九');
        strNumTemp = strNumTemp.replace('0', '零');
        strNumTemp = strNumTemp.replace('.', '点');
        strBegin = strNumTemp.substring(0, 1);
    }

    public String readNum() {
        if (check(strNum)) {
            init();
            try {
                for (int i = 1, j = 1, k = 1; i < intNumLen; i++) {
                    if (strNumTemp.charAt(intNumLen - 1) == '零' && i == 1) {
                        strNumChFormat = "位";
                    } else if (strNumTemp.charAt(intNumLen - i) == '零' && j == 1) {
                        strNumChFormat = "位" + strNumChFormat;
                    } else if (strNumTemp.charAt(intNumLen - i) == '点') {
                        j = 1;
                        k = 1;
                        strNumChFormat = strNumTemp.charAt(intNumLen - i) + strNumChFormat;
                        continue;
                    } else {
                        strNumChFormat = strNumTemp.charAt(intNumLen - i) + strNumChFormat;
                    }
                    if (strNumTemp.charAt(intNumLen - i - 1) != '位' && strNumTemp.charAt(intNumLen - i - 1) != '零') {
                        if (j == 1 && i < intNumLen) {
                            strNumChFormat = '拾' + strNumChFormat;
                        } else if (j == 2 && i < intNumLen) {
                            strNumChFormat = '百' + strNumChFormat;
                        } else if (j == 3 && i < intNumLen) {
                            strNumChFormat = '千' + strNumChFormat;
                        }
                    }
                    if (j == 4 && i < intNumLen) {
                        j = 0;
                    }
                    if (k == 4 && i < intNumLen) {
                        strNumChFormat = '万' + strNumChFormat;
                    } else if (k == 8 && i < intNumLen) {
                        k = 0;
                        strNumChFormat = '亿' + strNumChFormat;
                    }
                    j++;
                    k++;
                }
                while (strNumChFormat.indexOf("位") != -1) {
                    strNumChFormat = strNumChFormat.replaceAll("位", " ");
                }
                if (strNumChFormat.substring(0, 2) == "一拾") {
                    strNumChFormat = strNumChFormat.substring(1, strNumChFormat.length());
                }
                if (strNumChFormat.indexOf("点") >= 0) {
                    String rebegin = strNumChFormat.substring(0, strNumChFormat.indexOf("点"));
                    String relast = strNumChFormat.substring(strNumChFormat.indexOf("点"), strNumChFormat.length());
                    for (int i = 1; i <= relast.length(); i++) {
                        relast = relast.replaceAll("拾", "");
                        relast = relast.replaceAll("百", "");
                        relast = relast.replaceAll("千", "");
                        relast = relast.replaceAll("万", "");
                        relast = relast.replaceAll("亿", "");
                    }
                    strNumChFormat = rebegin + relast;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            int off = strNumChFormat.indexOf("点");
            strNumChFormat = strBegin + strNumChFormat.substring(0);
        } else {
            strNumChFormat = "";
        }
        return strNumChFormat;
    }

    public static void main(String args[]) {
        try {
            String number = "1234560001234234567890.55544";
            System.out.println("The number is: " + number);
            TestTransferToChinese reader = new TestTransferToChinese(number);
            System.out.println("Output String: " + reader.readNum());
        } catch (Exception ex) {
            System.out.println("Please input like that: javac Reader ");
        }
    }
}
