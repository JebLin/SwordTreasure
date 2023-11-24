package indi.sword.util.basic.dataStructure.basic._06_Hash.openAddress;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

import java.math.BigInteger;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:17 2018/2/5
 * @Modified By
 */
/*
    开放地址法：
        当冲突发生时，通过查找数组的一个空位，并将数据填入，而不再用哈希函数得到数组的下标，这种方法叫做开放地址法。
 */
public class MyHashTable_openAddress {

    private Info[] arr;

    public MyHashTable_openAddress() {
        this.arr = new Info[100];
    }

    public MyHashTable_openAddress(Info[] arr) {
        this.arr = arr;
    }

    /**
     * @Description 插入
     * @Author rd_jianbin_lin
     * @Date 19:42 2018/2/5
     * @Modified By
     */
    public void insert(Info info){
        //获得关键字
        String key = info.getKey();
        //关键字所自定的哈希数
        int hashVal = hashCode(key);
        // 如果这个索引已经被占用，而且里面是一个未被删除的数据
        while(arr[hashVal] != null && arr[hashVal].getName() != null){
            // 进行递增
            ++hashVal;
            // 循环
            hashVal %= arr.length;
        }
        arr[hashVal] = info;
    }

    /**
     * @Description 查找
     * @Author rd_jianbin_lin
     * @Date 19:42 2018/2/5
     * @Modified By
     */
    public Info find(String key){
        int hashVal = hashCode(key);
        while(arr[hashVal] != null){
            if(arr[hashVal].getKey().equals(key)){
                return arr[hashVal];
            }
            ++hashVal;
            hashVal %= arr.length;
        }
        return null;
    }

    /**
     * @Description  删除的话，把删掉的 name 置为 null,而不是把数组中的 info 置为 null.
     * @Author rd_jianbin_lin
     * @Date 20:22 2018/2/5
     * @Modified By
     */
    public Info delete(String key){
        int hashVal = hashCode(key);
        while(arr[hashVal] != null){
            if(arr[hashVal].getKey().equals(key)){
                Info temp = arr[hashVal];
                temp.setName(null);
                return temp;
            }
            ++hashVal;
            hashVal %= arr.length;
        }
        return null;
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
        System.out.println(new MyHashTable_openAddress().hashCode("ab")); // 2 + 1 * 27 = 29
    }

}
