# A simple test for the minimal standard C++ library
#
include $(CLEAR_VARS)
LOCAL_MODULE := libJSBSim
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH := $(PROJ_PATH)/libs
LOCAL_SRC_FILES := $(LIB_PATH)/libJSBSim.so
APP_STL := gnustl_shared
#LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libJSBVova
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH := $(PROJ_PATH)/libs
LOCAL_SRC_FILES := $(LIB_PATH)/libJSBVova.so
#LOCAL_LDLIBS := -lJSBSim
LOCAL_SHARED_LIBRARIES :=JSBSim
APP_STL := gnustl_shared
#LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libgdx
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH := $(PROJ_PATH)/libs
LOCAL_SRC_FILES := $(LIB_PATH)/libgdx.so
APP_STL := gnustl_shared
#LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libgnustl_shared
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH  := /home/vlabel/Downloads/android-ndk-r9/build/tools/~/tool/arm-linux-androideabi/lib
LOCAL_SRC_FILES := $(LIB_PATH)/libgnustl_shared.so
#LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libstlport_shared
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH  := /home/vlabel/Downloads/android-ndk-r9/build/tools/~/tool/arm-linux-androideabi/lib
LOCAL_SRC_FILES := $(LIB_PATH)/libstlport_shared.so
#LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libjsbj
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH := $(PROJ_PATH)/libs/armeabi/
LOCAL_MODULE := libjsbj
LOCAL_SRC_FILES := $(PROJ_PATH)/jni/jsbj.cpp
LIBMY = /home/vlabel/tool/arm-linux-androideabi/lib
LOCAL_C_INCLUDES := $(NDK_ROOT)/sources/cxx-stl/stlport/stlport/
LOCAL_LDLIBS        += -L$(LIBMY)  
LOCAL_LDLIBS        += -L$(NDK_ROOT)/sources/cxx-stl/stlport/libs/armeabi
LOCAL_LDLIBS += -lstlport_shared -lstdc++ -llog -landroid 
LOCAL_SHARED_LIBRARIES :=JSBVova JSBSim 
PROJ_PATH := /home/vlabel/download/adt-bundle-linux-x86_64-20130917/eclipse/k/flight/
LIB_PATH := $(PROJ_PATH)/libs
LD_LIBRARY_PATH := $(LIB_PATH)/
LOCAL_PATH := $(call my-dir)
APP_STL := stlport_shared
include $(BUILD_SHARED_LIBRARY)
