package indi.sword.util.rpc.thrift._01helloworld;

import org.apache.thrift.TException;

public class QueryImp implements TestQry.Iface{
    @Override
    public QryResult qryTest(int qryCode) throws TException {
        QryResult result = new QryResult();
        if(qryCode==1){
            result.code = 1;
            result.msg = "success";
        }else{
            result.code = 0;
            result.msg = "fail";
        }
        return result;
    }
}
