#include <cerrno>
#include <cstddef>

#include <jni.h>
#include "JSBSim.h"
#include "JSBVova.h"
#include <android/log.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>

#define APPNAME "Flight"

extern AAssetManager *GlobalManager;
extern "C" {
JNIEXPORT void JNICALL Java_com_example_flight_BaseApp_setA(JNIEnv * env, jobject obj,jobject assetmgr);
JNIEXPORT void JNICALL Java_com_example_flight_BaseApp_setA(JNIEnv * env, jobject obj,jobject assetmgr)
{
	GlobalManager = AAssetManager_fromJava(env, assetmgr);
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "AFRET MAIN");
}


JNIEXPORT int JNICALL Java_com_example_flight_JSBMotionProvider_createPlane(JNIEnv * env, jobject obj,jstring name)
{
	const char *nativeString = env->GetStringUTFChars( name, JNI_FALSE);
	std::string ss(nativeString);
	int  id = JSBProvidersManager::instance().createProvider(ss);
	__android_log_print(ANDROID_LOG_VERBOSE, APPNAME, "createPlane 22");
	return id;
}


};
