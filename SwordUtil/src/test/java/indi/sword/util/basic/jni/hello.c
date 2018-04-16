#include "stdio.h"  
#include "indi_sword_util_basic_jni_HelloJNI.h"  
  
  
JNIEXPORT jstring JNICALL Java_indi_sword_util_basic_jni_HelloJNI_sayHi(JNIEnv *env, jclass jc, jstring name)  
{  
    return name;  
}  