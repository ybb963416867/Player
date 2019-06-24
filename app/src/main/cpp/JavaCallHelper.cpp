//
// Created by administered on 2019/2/21.
//

#include "JavaCallHelper.h"
#include "macro.h"

JavaCallHelper::JavaCallHelper(JavaVM *vm, JNIEnv *env, jobject instance) {
    this->vm = vm;
    this->instance = env->NewGlobalRef(instance);
    this->env = env;
    jclass clazz = env->GetObjectClass(instance);
    onErrorId = env->GetMethodID(clazz, "onError", "(I)V");
    onPrepareId = env->GetMethodID(clazz, "onPrepare", "()V");
    setFrameMethodId = env->GetMethodID(clazz, "setFrameData", "(II[B[B[B)V");
}

JavaCallHelper::~JavaCallHelper() {
    env->DeleteGlobalRef(instance);
}

void JavaCallHelper::onError(int thread, int errorCode) {
    //如果在主线程
    if (thread == THREAD_MAIN) {
        env->CallVoidMethod(instance, onErrorId, errorCode);
    } else {
        JNIEnv *env;
        vm->AttachCurrentThread(&env, 0);
        env->CallVoidMethod(instance, onErrorId, errorCode);
        vm->DetachCurrentThread();
    }
}

void JavaCallHelper::onPrepare(int thread) {
    //如果在主线程
    if (thread == THREAD_MAIN) {
        env->CallVoidMethod(instance, onPrepareId);
    } else {
        JNIEnv *env;
        vm->AttachCurrentThread(&env, 0);
        env->CallVoidMethod(instance, onPrepareId);
        vm->DetachCurrentThread();
    }

}

void JavaCallHelper::setFrameData(int thread, int width, int height, uint8_t *fy, uint8_t *fu,
                                  uint8_t *fv) {
    LOGE("调用了jni:setFrameData");
    if(thread == THREAD_CHILD)
    {
        JNIEnv *jniEnv;
        if(vm->AttachCurrentThread(&jniEnv, 0) != JNI_OK)
        {
//            LOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
            return;
        }

        jbyteArray y = jniEnv->NewByteArray(width * height);
        jniEnv->SetByteArrayRegion(y, 0, width * height, (jbyte*)fy);

        jbyteArray u = jniEnv->NewByteArray(width * height / 4);
        jniEnv->SetByteArrayRegion(u, 0, width * height / 4, (jbyte*)fu);

        jbyteArray v = jniEnv->NewByteArray(width * height / 4);
        jniEnv->SetByteArrayRegion(v, 0, width * height / 4, (jbyte*)fv);

        jniEnv->CallVoidMethod(instance, setFrameMethodId, width, height, y, u, v);
        jniEnv->DeleteLocalRef(y);
        jniEnv->DeleteLocalRef(u);
        jniEnv->DeleteLocalRef(v);
//
//        vm->DetachCurrentThread();
    }
    else
    {
        jbyteArray y = env->NewByteArray(width * height);
        env->SetByteArrayRegion(y, 0, width * height, (jbyte*)fy);

        jbyteArray u = env->NewByteArray(width * height / 4);
        env->SetByteArrayRegion(u, 0, width * height / 4, (jbyte*)fu);

        jbyteArray v = env->NewByteArray(width * height / 4);
        env->SetByteArrayRegion(v, 0, width * height / 4, (jbyte*)fv);

        env->CallVoidMethod(instance, setFrameMethodId, width, height, y, u, v);
        env->DeleteLocalRef(y);
        env->DeleteLocalRef(u);
        env->DeleteLocalRef(v);
    }

}

