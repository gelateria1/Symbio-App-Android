package com.mobileapp.symbio.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mobileapp.symbio.R;
import com.mobileapp.symbio.SymbioApp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    public static final String CONFIG_FILE_NAME = "userdata.config";

    private EditText mEditTextURL;
    private EditText mEditTextLogin;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private List<String> mConfig;
    private File mConfigFile;

    private int CONFIG_URL      = 0;
    private int CONFIG_USERNAME = 1;
    private int CONFIG_PASSWORD = 2;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mEditTextURL = (EditText) findViewById(R.id.loginEditTextURL);
        mEditTextLogin = (EditText) findViewById(R.id.loginEditTextEMail);
        mEditTextPassword = (EditText) findViewById(R.id.loginEditTextPassword);


        mConfigFile = new File(getFilesDir(), CONFIG_FILE_NAME);

        checkForConfigFile();

        mEditTextURL.setText(mConfig.get(CONFIG_URL));
        mEditTextLogin.setText(mConfig.get(CONFIG_USERNAME));
        mEditTextPassword.setText(mConfig.get(CONFIG_PASSWORD));

        mButtonLogin = (Button) findViewById(R.id.loginButton);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if login data is correct and display error message or start intent
                String writeToConfig = mEditTextURL.getText() + "\n" +
                        mEditTextLogin.getText() + "\n" +
                        mEditTextPassword.getText();

                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(mConfigFile);
                    out.write(writeToConfig.getBytes());
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                SymbioApp instance = (SymbioApp) getApplication();
                instance.setUrl(mEditTextURL.getText().toString());
                instance.setUsername(mEditTextLogin.getText().toString());
                instance.setPassword(mEditTextPassword.getText().toString());

                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });
    }

    public void checkForConfigFile() {
        mConfig = new ArrayList<String>();
        if (mConfigFile.exists()) {
            // configfile exists

            readConfigFile();

        } else {
            // No configfile, copy it from assets to sd card
            AssetManager assetManager = getAssets();
            InputStream input = null;
            try {
                input = assetManager.open(CONFIG_FILE_NAME);
                copy(input, mConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            readConfigFile();
        }
    }

    public void copy(InputStream src, File dst) throws IOException {
        InputStream in = src;
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public void readConfigFile() {
        try {
            BufferedReader buff_reader = new BufferedReader(new FileReader(mConfigFile));
            String line;
            while ((line = buff_reader.readLine()) != null) {
                mConfig.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
