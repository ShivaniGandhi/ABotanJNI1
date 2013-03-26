package com.erdk.ABotanJNI1;

import java.security.Key;

/**
 * Created with IntelliJ IDEA.
 * User: erdk
 * Date: 3/25/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class NativeCrypto {

    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("botan");
        System.loadLibrary("botan_crypto");
    }

    public static native String pbkdf2Demo(char[] password, byte[] salt, int iterations, int keyLength);
}
