package indi.sword.util.rpc.thrift._02helloworld;

import org.apache.thrift.TException;

/**
 * @Decription 这个是放在Server端的实现类
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/24 18:31
 */
public class HelloServiceImpl implements Hello.Iface {
    @Override
    public boolean helloBoolean(boolean para) throws TException {
        return para;
    }
    @Override
    public int helloInt(int para) throws TException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para;
    }
    @Override
    public String helloNull() throws TException {
        return null;
    }
    @Override
    public String helloString(String para) throws TException {
        return "this is server " + para;
    }
    @Override
    public void helloVoid() throws TException {
        System.out.println("Hello World");
    }
}