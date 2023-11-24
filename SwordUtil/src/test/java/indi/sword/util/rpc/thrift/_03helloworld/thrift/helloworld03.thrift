# 命名空间的定义 注意‘java’的关键字(这个简单理解为包名称）
namespace java indi.sword.util.rpc.thrift._03helloworld.thrift

# 结构体定义
struct Request {
    1:required string paramJSON;
    2:required string serviceName;
}

# 另一个结构体定义
struct Reponse {
    1:required  RESCODE responeCode;
    2:required  string responseJSON;
}

# 异常描述定义
exception ServiceException {
    1:required EXCCODE exceptionCode;
    2:required string exceptionMess;
}

# 枚举定义
enum RESCODE {
    _200=200;
    _500=500;
    _400=400;
}

# 另一个枚举
enum EXCCODE {
    PARAMNOTFOUND = 2001;
    SERVICENOTFOUND = 2002;
}

# 服务定义
service HelloWorldService {
    Reponse send(1:Request request) throws (1:ServiceException e);
}