LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE            := botan_crypto
LOCAL_SRC_FILES         := botan_crypto.cpp
LOCAL_SHARED_LIBRARIES  := botan
LOCAL_LDLIBS            := -llog

include $(BUILD_SHARED_LIBRARY)