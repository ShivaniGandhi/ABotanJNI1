package com.erdk.ABotanJNI1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    TextView editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (TextView) findViewById(R.id.main_textview);
        pbkdf2_native(null);
    }

    public void pbkdf2_native(View view) {

        char[] password = Constants.password;
        byte[] salt = new byte[8];
        int iterations = 4096;
        int keyLength = 32;
        long start = System.currentTimeMillis();
        String key = NativeCrypto.pbkdf2Demo(password, salt, iterations, keyLength / 8);
        long end = System.currentTimeMillis();

        String text = (String) editText.getText();
        text += "\nNative:" + key + "\nComputed in " + String.valueOf(end - start) + " miliseconds";
        editText.setText(text);
    }

    public void pbkdf2_dalvik(View view) {
        char[] password = "testowe_haslo".toCharArray();
        byte[] salt = new byte[8];
        int iterations = 4096;
        int keyLength = 32;
        long start = System.currentTimeMillis();
        String key = JavaCrypto.pbkdf2Demo(password, salt, iterations, keyLength / 8);
        long end = System.currentTimeMillis();

        String text = (String) editText.getText();
        text += "\nDalvik:" + key + "\nComputed in " + String.valueOf(end - start) + " miliseconds";
        editText.setText(text);
    }
}
