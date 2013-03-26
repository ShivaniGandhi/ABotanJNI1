#include <native.h>
#include <botan_all.h>
#include <log.h>
#include <string>

#define LOG_TAG "pl.task.nds2droid"

using namespace std;
using namespace Botan;

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_erdk_ABotanJNI1_NativeCrypto
 * Method:    pbkdf2Demo
 * Signature: ([C[BII)Ljava/security/Key;
 */
JNIEXPORT jobject JNICALL Java_com_erdk_ABotanJNI1_NativeCrypto_pbkdf2Demo
    (JNIEnv *env, jclass thiz, jcharArray jPassword, jbyteArray jSalt, jint iterations, jint keyLength) {

    string password;
    unsigned char* salt;

    try {
        jchar* ptr;
        jbyte* ptr2;

        // move password Java -> C++
        int size = env->GetArrayLength(jPassword);
        char* tmp_password = (char*) malloc((size + 1) * sizeof(char));
        ptr = env->GetCharArrayElements(jPassword, 0);
        for (int i = 0; i < 128; i++) {
            tmp_password[i] = ptr[i];
        }
        //tmp_password[size] = '\0';
        password = string(tmp_password, 128);
        free(tmp_password);

        // move salt Java -> C++
        size = env->GetArrayLength(jSalt);
        salt = (unsigned char*) malloc(size * sizeof(unsigned char));
        ptr2 = env->GetByteArrayElements(jSalt, 0);
        for (int i = 0; i < size; i++) {
            salt[i] = ptr2[i];
        }
        env->ReleaseCharArrayElements(jPassword, ptr, 0);
        env->ReleaseByteArrayElements(jSalt, ptr2, 0);

        // Generate pbkdf(HmacWithSHA1)
        PBKDF* pbkdf = get_pbkdf("PBKDF2(SHA-160)");

        OctetString aes256_key = pbkdf->derive_key(
                keyLength, password, salt, size, iterations);

//        free(password);
//        password = NULL;
        free(salt);
        salt = NULL;

        return env->NewStringUTF(aes256_key.as_string().c_str());

    } catch (Exception& e) {
        LOGE(LOG_TAG, "In exception... %s:%d", __FILE__, __LINE__);
//        if (password != NULL) {
//            free(password);
//        }

        if (salt != NULL) {
            free(salt);
        }

        std::string empty_str = "";
        return env->NewStringUTF(empty_str.c_str());
    }
}

#ifdef __cplusplus
}
#endif