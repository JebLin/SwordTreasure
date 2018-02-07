package indi.sword.util.basic.dataStructure.basic._06_Hash.Linked;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

import java.math.BigInteger;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 16:40 2018/2/7
 * @Modified By
 */
public class MyHashTable_linked {
    private MyLinkList[] arr;

    // 默认的构造方法
    public MyHashTable_linked() {
        arr = new MyLinkList[100];
    }

    // 指定大小
    public MyHashTable_linked(int maxsize){
        arr = new MyLinkList[maxsize];
    }

    // 插入数据
    public void insert(Info info){

        // 获得关键字
        String key = info.getKey();

        // 关键字所自定的哈希数
        int hashVal = hashCode(key);
        if(arr[hashVal] == null){
            arr[hashVal] = new MyLinkList();
        }
        arr[hashVal].insertFirst(info);
    }

    // 查找数据
    public Info find(String key){
        int hashVal = hashCode(key);
        return arr[hashVal].find(key).info;
    }

    // 删除数据
    public Info delete(String key){
        int hashVal = hashCode(key);
        return arr[hashVal].delete(key).info;
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

}
