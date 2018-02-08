package indi.sword.util.basic.algorithm;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 16:42 2018/2/8
 * @Modified By
 */
public class TestNumTransfer {
    public static void main(String[] args) {
        System.out.println(transfer(16,50));
    }


    // 将十进制转换成二进制或者八进制，16进制
    /**
     * @Description
     * @Author rd_jianbin_lin
     * @Date 18:37 2018/2/8
     * @Modified By
     * @param mode 2 8 16 进制
     * @param number 要转换的数字
     */

    public static String transfer(int mode,int number){

        StringBuilder sb = new StringBuilder();

        // 二进制模式
        if(mode == 2){
            while(number != 0){
                sb.append(number & 0x01); // 与运算，获取最后一个二进制数字
                number >>= 1; // 右移一位
            }
        }else { // 八进制模式 或者 十六进制
            int lessure = 0;
            int divisionResult = number;
            while(divisionResult != 0){
                lessure = divisionResult % mode;
                sb.append(trasferLessure(mode,lessure));
                divisionResult /= mode;
            }
        }
        return sb.reverse().toString();
    }

    private static String trasferLessure(int mode, int lessure) {
        if(mode == 8){
            return String.valueOf(lessure);
        }else if(mode == 16){
            switch (lessure){
                case 10: return "A";
                case 11: return "B";
                case 12: return "C";
                case 13: return "D";
                case 14: return "E";
                case 15: return "F";
                default:
                    return String.valueOf(lessure);
            }
        }else {

        }
        return null;

    }
}
