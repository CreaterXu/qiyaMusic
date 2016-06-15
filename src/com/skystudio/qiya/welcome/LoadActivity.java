package com.skystudio.qiya.welcome;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skystudio.qiya.R;
import com.skystudio.qiya.fragments.QiyaActivity;
import com.skystudio.qiya.util.CurrentUser;

public class LoadActivity extends Activity {
    private TextInputLayout userTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewRegister;
    private Button buttonLogin;
    private String userName;
    private String passWord;
    private CheckBox checkBoxAuto;


    Handler tHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(LoadActivity.this, "成功", Toast.LENGTH_LONG)
                        .show();

                CurrentUser currentUser = CurrentUser.getInstance();
                currentUser.setName(userName);
                currentUser.setPwd(passWord);
                SharedPreferences sp = getSharedPreferences("user",
                        MODE_PRIVATE);
                Editor editor = sp.edit();
                editor.putString("userName", userName);
                editor.putString("password", passWord);
                editor.commit();
                // LoginOrLoginOutImAccount.login(getApplicationContext(),
                // userName);�
                QiyaActivity.lanch(LoadActivity.this);
                LoadActivity.this.finish();
            } else {

                Toast.makeText(LoadActivity.this, "错误", Toast.LENGTH_SHORT).show();
                editTextPassword.setText("");
            }
        }

        ;
    };

    public static void lanch(Context c) {
        Intent intent = new Intent(c, LoadActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_load);

        userTextInputLayout = (TextInputLayout) findViewById(R.id.userTextInputLayout);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
        editTextUsername = userTextInputLayout.getEditText();
        editTextPassword = passwordTextInputLayout.getEditText();


        // editTextPassword.addTextChangedListener(watcher);
        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        checkBoxAuto = (CheckBox) findViewById(R.id.checkboxAuto);

        checkBoxAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Log.e("xv", "checked");
                    SharedPreferences sp = getSharedPreferences("user",
                            MODE_PRIVATE);
                    Editor editor = sp.edit();
                    editor.putBoolean("autoFlag", isChecked);
                    editor.commit();
                } else {

                }
            }
        });

        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                userName = editTextUsername.getText().toString();
                passWord = editTextPassword.getText().toString();
                if (userName.equals("")|| passWord.equals("")) {
                    if (userName.equals("")&&!passWord.equals("")){
                        userTextInputLayout.setErrorEnabled(true);
                        userTextInputLayout.setError("用户名不能为空");
                        passwordTextInputLayout.setErrorEnabled(false);
                    }else if(!userName.equals("")&&passWord.equals("")){
                        passwordTextInputLayout.setErrorEnabled(true);
                        passwordTextInputLayout.setError("密码不能为空");
                        userTextInputLayout.setErrorEnabled(false);
                    }else {
                        Snackbar.make(buttonLogin,"您还是动动手指填下信息吧",Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    userTextInputLayout.setErrorEnabled(false);
                    passwordTextInputLayout.setErrorEnabled(false);
                    new CheckConnection(userName, passWord).start();
                }
            }
        });

        textViewRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                textViewRegister.setTextColor(Color.RED);
                RegisterActivity.lanch(LoadActivity.this);
            }
        });
    }

    /**
     * 检测用户名是否正确线程
     *
     * */
    class CheckConnection extends Thread {
        String responseString = "";
        private String userName;
        private String passWord;

        public CheckConnection(String userName, String passWord) {
            // TODO Auto-generated constructor stub
            this.userName = userName;
            this.passWord = passWord;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            String url = "http://192.168.0.103:8080/cardGame/userLoginServlet?Name="
                    + userName + "&Pwd=" + passWord;
            HttpClient client = new DefaultHttpClient();
            HttpGet hg = new HttpGet(url);
            try {
                HttpResponse hr = client.execute(hg);
                System.out.println("connecting");
                String temp;
                StringBuffer sb = new StringBuffer();
                BufferedReader in = new BufferedReader(new InputStreamReader(hr
                        .getEntity().getContent()));// ��ȡ������
                while ((temp = in.readLine()) != null) {
                    sb.append(temp);
                }
                responseString = sb.toString();
                check(responseString);
                in.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
            super.run();
        }

    }

    public void check(String responseString) {
        if (responseString.equals("true")) {
            Message msg = new Message();
            msg.what = 0;
            tHandler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = 1;
            tHandler.sendMessage(msg);
        }
    }
}
