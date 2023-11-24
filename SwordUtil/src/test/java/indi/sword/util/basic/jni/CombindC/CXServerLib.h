
// 记得引用 Jni.H
#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif
    extern int CXServerLibraryVersion(void);

    // 调用内部C++方法，名称规范 Java_包路径_类名_对应类中方法名
    JNIEXPORT jint JNICALL Java_CXServer_CXServerLibraryVersion(JNIEnv *, jclass, jstring);

    JNIEXPORT jstring JNICALL Java_CXServer_sayHi(JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif