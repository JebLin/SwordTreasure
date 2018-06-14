
#include "CXServerLib.h"
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int CXServerLibraryVersion(const char * inputPath) {
	printf("c output %s\n", inputPath);
    return 1;
}

// 原生方法
JNIEXPORT jstring JNICALL Java_CXServer_sayHi(JNIEnv *env, jclass jc, jstring name)
{
    return name;
}

// 调用内部C++方法，名称规范 Java_包路径_类名_对应类中方法名
JNIEXPORT jint JNICALL Java_CXServer_CXServerLibraryVersion(JNIEnv *env, jclass obj, jstring javaString){

	const char *nativeString = env->GetStringUTFChars(javaString, 0);
	int v = CXServerLibraryVersion(nativeString);
    env->ReleaseStringUTFChars(javaString, nativeString);
    return v;
}


