﻿linux/Ubuntu 下使用 java 调用 so 动态链接库详细步骤


动态链接库（Dynamic Link Library 或者 Dynamic-link Library，缩写为 DLL），是微软公司在微软Windows操作系统中，实现共享函数库概念的一种方式。这些库函数的扩展名是 ”.dll"、".ocx"（包含ActiveX控制的库）或者 ".drv"（旧式的系统驱动程序）。

例如，您有一个大型网络游戏，如果把整个数百MB甚至数GB的游戏的代码都放在一个应用程序里，日后的修改工作将会十分费时，而如果把不同功能的代码分别放在数个动态链接库中，您无需重新生成或安装整个程序就可以应用更新。
动态链接提供了一种方法，使进程可以调用不属于其可执行代码的函数。函数的可执行代码位于一个 DLL 文件中，该 DLL 包含一个或多个已被编译、链接并与使用它们的进程分开存储的函数。DLL 还有助于共享数据和资源。多个应用程序可同时访问内存中单个 DLL 副本的内容。
使用动态链接库可以更为容易地将更新应用于各个模块，而不会影响该程序的其他部分。


Windows 中，DLL 多数情况下是带有 ".dll" 扩展名的文件，但也可能是 ".ocx"或其他扩展名；Linux系统中常常是 ".so" 的文件。它们向运行于 Windows操作系统下的程序提供代码、数据或函数。程序可根据 DLL 文件中的指令打开、启用、查询、禁用和关闭驱动程序。

### 操作步骤
- 编写 java 类 HelloJNI.java 
- 编译 java 类 就生成在同一个目录就行javac (注意目录)
```
momo@MOMOdeMacBook-Pro:~/Documents/code/individualGithubProject/swordTreasure/SwordTreasure/SwordUtil/src/test/java$ 
javac indi/sword/util/basic/jni/HelloJNI.java
```
- 使用 class 文件生成 .h 头文件 javah (注意目录)
```
momo@MOMOdeMacBook-Pro:~/Documents/code/individualGithubProject/swordTreasure/SwordTreasure/SwordUtil/src/test/java$ 
javah indi.sword.util.basic.jni.HelloJNI
﻿会在最外边的目录 /util 目录下生成 com_hongquan_jni_HelloJNI.h 的头文件,移进来一起放着
```
- 编写 hello.c 源文件 (注意目录)
```$xslt
#include "stdio.h"  
#include "indi_sword_util_basic_jni_HelloJNI.h"  
  
  
JNIEXPORT jstring JNICALL Java_indi_sword_util_basic_jni_HelloJNI_sayHi(JNIEnv *env, jclass jc, jstring name)  
{  
    return name;  
}  
```
> 引入第 3 步中生成的头文件，并实现其中声明的函数
函数内容很简单，直接将传入的 name 返回

- ﻿使用如下命令生成 .o 文件 (注意目录)
```$xslt 
momo@MOMOdeMacBook-Pro:~/Documents/code/individualGithubProject/swordTreasure/SwordTreasure/SwordUtil/src/test/java/indi/sword/util/basic/jni$ 
gcc -fPIC -D_REENTRANT -I/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers -I/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/include -c hello.c
﻿﻿
  
```
﻿需要注意的是
/usr/local/java/jdk1.7.0_40/include
/usr/local/java/jdk1.7.0_40/include/linux
是 jdk 中的 jni.h 文件的所在目录，这两个目录需要大家替换为自己的对应路径
```$xslt
sudo find / -name jni.h
结果：
/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers/jni.h
/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home/include/jni.h
```

成功的话会在 执行 目录下生成 hello.o 文件

- ﻿最后输出 main.so  (注意目录)
```$xslt
gcc filename.c -o output_filename
确定输出文件的名称为output_filename，同时这个名称不能和源文件同名。如果不给出这个选项，gcc就给出预设的可执行文件a.out。
```
``` 
momo@MOMOdeMacBook-Pro:~/Documents/code/individualGithubProject/swordTreasure/SwordTreasure/SwordUtil/src/test/java/indi/sword/util/basic/jni$ 
gcc hello.o -o main.so -shared

```
成功的话会在 执行 目录下生成 main.so 文件

- ﻿接下来我们来试试是否能够调用成功
```$xslt
momo@MOMOdeMacBook-Pro:~/Documents/code/individualGithubProject/swordTreasure/SwordTreasure/SwordUtil/src/test/java$
java indi.sword.util.basic.jni.HelloJNI
结果：
It's a result return by main.so file!! 
```


﻿linux 下使用 java 调用 so 动态链接库就成功了！Goodluck every one!!

---


 android 中调用 so 动态链接库 <https://blog.csdn.net/hongquan1991/article/details/12449381>