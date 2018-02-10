package indi.sword.util.basic.dataStructure.basic._06_Hash.common;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

import java.math.BigInteger;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:17 2018/2/5
 * @Modified By
 */
public class MyHashTable {

    private Info[] arr;

    public MyHashTable() {
        this.arr = new Info[100];
    }

    public MyHashTable(Info[] arr) {
        this.arr = arr;
    }

    /**
     * @Description 插入
     * @Author rd_jianbin_lin
     * @Date 19:42 2018/2/5
     * @Modified By
     */
    public void insert(Info info){
        arr[hashCode(info.getKey())] = info;
    }

    /**
     * @Description 查找
     * @Author rd_jianbin_lin
     * @Date 19:42 2018/2/5
     * @Modified By
     */
    public Info find(String key){
        return arr[hashCode(key)];
    }


    /**
     *
     * 幂运算 --> 取模
     * ASCII码 'a' -> 97
     * @Description
     *
     *      "1234" ->
     *              4 * 27 ^ 0 +
     *              3 * 27 ^ 1 +
     *              2 * 27 ^ 2 +
     *              1 * 27 ^ 3
     * @Author rd_jianbin_lin
     * @Date 19:29 2018/2/5
     * @Modified By
     */
    public int hashCode(String key){
        BigInteger hashVal = new BigInteger("0");
        BigInteger pow27 = new BigInteger("1");

        BigInteger bigInteger_27 =  new BigInteger(String.valueOf(27));
        BigInteger bigInteger_length =  new BigInteger(String.valueOf(arr.length));

        for (int i = key.length() - 1; i >= 0 ; i--) {
            int letter = key.charAt(i) - 96;
            BigInteger letterB = new BigInteger(String.valueOf(letter));
            hashVal = hashVal.add(letterB.multiply(pow27));
            pow27 = pow27.multiply(bigInteger_27);
        }
//        System.out.println("key -> " + key + "---> hashVal " + hashVal);
        return hashVal.mod(bigInteger_length).intValue();
    }

    public static void main(String[] args) {
        System.out.println(new MyHashTable().hashCode("ab")); // 2 + 1 * 27 = 29
    }

}
