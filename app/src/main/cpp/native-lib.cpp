#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bd_blacksky_utils_Keys_apiKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "aa2df23d347d91a01f286584e35f2b7e";
    return env->NewStringUTF(api_key.c_str());
}