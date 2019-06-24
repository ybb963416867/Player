//
// Created by administered on 2019/2/21.
//

#ifndef DAY11_JAVACALLHELPER_H
#define DAY11_JAVACALLHELPER_H

#include <jni.h>

class  JavaCallHelper{
public:
    JavaCallHelper(JavaVM *vm,JNIEnv *env,jobject  instance);
    ~JavaCallHelper();
    void onError(int thread, int errorCode);
    void onPrepare(int thread);
    void setFrameData(int thread, int width, int height, uint8_t *fy, uint8_t *fu, uint8_t *fv);

private:
    JavaVM *vm;
    JNIEnv  *env;
    jobject  instance;
    jmethodID   onErrorId;
    jmethodID  onPrepareId;
    jmethodID   setFrameMethodId;
};



#endif //DAY11_JAVACALLHELPER_H
