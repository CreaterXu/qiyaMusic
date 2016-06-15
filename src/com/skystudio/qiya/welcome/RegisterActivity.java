package com.skystudio.qiya.welcome;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skystudio.qiya.R;
import com.skystudio.qiya.fragments.QiyaActivity;
import com.skystudio.qiya.util.CurrentUser;
import com.skystudio.qiya.util.ImageUploadUtil;

@SuppressLint("NewApi")
public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private EditText editTextNickname;
    private EditText editTextAutograph;
    private TextInputLayout userNameTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private TextInputLayout passwordConfirmTextInputLayout;
    private TextInputLayout nicknameTextInputLayout;
    private TextInputLayout autographTextInputLayout;
    private Button buttonRegister;
    private View pv;

    private TextView textViewUploadHead;
    private String userName;
    private String passWord;
    private String passWordConfirm;
    private String nickname;
    private String autograph;
    private ImageView headImageView;

    private PopupWindow popupWindow = null;
    private static final int TAKE_PICTURE = 0x000001;
    private static final int CUT_PHOTO = 0x000002;
    Bitmap newb = null;//默认头像
    File temp = new File(Environment.getExternalStorageDirectory() + "/qiya");
    File t = null;

    public static void lanch(Context c) {
        Intent intent = new Intent(c, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        c.startActivity(intent);
    }

    Handler tHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "错误",
                        Toast.LENGTH_SHORT).show();
                CurrentUser cu = CurrentUser.getInstance();
                cu.setName(userName);
                cu.setPwd(passWord);
                QiyaActivity.lanch(RegisterActivity.this);
                //LoadActivity.this.finish();
                RegisterActivity.this.finish();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "错误",
                        Toast.LENGTH_SHORT).show();
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pv = getLayoutInflater().inflate(R.layout.activity_register,
                null);
        setContentView(pv);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassWord);
        editTextPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        editTextAutograph = (EditText) findViewById(R.id.editTextAutoGraph);
        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
        userNameTextInputLayout=(TextInputLayout)findViewById(R.id.userTextInputLayout);
        passwordTextInputLayout=(TextInputLayout)findViewById(R.id.passwordTextInputLayout);
        passwordConfirmTextInputLayout=(TextInputLayout)findViewById(R.id.confirmPasswordTextInputLayout);
        nicknameTextInputLayout=(TextInputLayout)findViewById(R.id.nicknameTextInputLayout);
        autographTextInputLayout=(TextInputLayout)findViewById(R.id.autographTextInputLayout);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewUploadHead = (TextView) findViewById(R.id.textViewHead);
        headImageView = (ImageView) findViewById(R.id.imageViewHead);

        newb = BitmapFactory.decodeResource(getResources(),
                R.drawable.icon_user);
        buttonRegister.setOnClickListener(this);
        headImageView.setOnClickListener(this);
    }

    public void saveAndUploadHead(String userName, Bitmap head) {
        // TODO Auto-generated method stub
        String path = "/head" + userName + ".jpg";
        FileOutputStream fos = null;
        try {
            if (!temp.exists()) {
                temp.mkdirs();
            }
            t = new File(Environment.getExternalStorageDirectory() + "/qiya"
                    + path);
            if (!t.exists()) {
                try {
                    t.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            fos = new FileOutputStream(t);
            head.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        ImageUploadUtil iu = new ImageUploadUtil(getApplicationContext());
        iu.execute(t);

    }

    public void showPopupWindow(View pv) {
        // TODO Auto-generated method stub
        View view = getLayoutInflater().inflate(R.layout.choiceimage_lineview,
                null);
        Dialog dialog=new Dialog(this);
        dialog.setContentView(view);
        dialog.show();
        RelativeLayout parent = (RelativeLayout) view
                .findViewById(R.id.registerLayout);
        popupWindow = new PopupWindow(RegisterActivity.this);
        System.out.println(popupWindow);
        popupWindow.setWidth(LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(pv, 0, 0,
                pv.getHeight() - popupWindow.getHeight());
        Button cancelButton=(Button)view.findViewById(R.id.buttonCancel);
        Button takePhotoButton=(Button)view.findViewById(R.id.buttonTakePhoto);
        Button alburmButton=(Button)view.findViewById(R.id.buttonFromAlbum);
        cancelButton.setOnClickListener(this);
        takePhotoButton.setOnClickListener(this);
        alburmButton.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE) {
            File temp = new File(Environment.getExternalStorageDirectory() + "/qiya"
                    + "/xvTakePhoto");// ���ļ�����ʽ����ü�����
            cutPhoto(Uri.fromFile(temp));

        } else if (requestCode == CUT_PHOTO) {
            newb = data.getParcelableExtra("data");
            headImageView.setImageBitmap(newb);// ��ʾͷ����ڵ�¼֮ǰ����ͷ���ļ�
        }
    }

    /*
     * 裁剪照片
     *
     * @params: 图片存储地址
     */
    public void cutPhoto(Uri uri) {
        // TODO Auto-generated method stub
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// uri���Ѿ�ѡ���ͼƬUri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// �ü������
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);// ���ͼƬ��С
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        RegisterActivity.this.startActivityForResult(intent, CUT_PHOTO);

    }

    //统一处理点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister:
                if (CheckEditText()) ;
                //上传信息
                break;
            case R.id.imageViewHead:
                if(pv==null){
                    Log.e("xv","pv is null");
                }

                showPopupWindow(pv);
                textViewUploadHead.setVisibility(View.GONE);
                break;
            case R.id.buttonTakePhoto:
                Intent photoin = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoin.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                        .fromFile(new File(Environment
                                .getExternalStorageDirectory() + "/qiya", "xvTakePhoto")));
                startActivityForResult(photoin, TAKE_PICTURE);
                break;
            case R.id.buttonCancel:
                popupWindow.dismiss();
                break;
            case R.id.buttonFromAlbum://从相册中
                Intent intent = new Intent();
                intent.setType("image/*");  /* 开启Pictures画面Type设定为image */
                intent.setAction(Intent.ACTION_GET_CONTENT); /* 使用Intent.ACTION_GET_CONTENT这个Action */
                startActivityForResult(intent, 1);/* 取得相片后返回本画面 */
                break;
        }

    }

    //点击注册，先检查各输入框
    private boolean CheckEditText() {
        userName = editTextUserName.getText() + "";
        passWord = editTextPassword.getText() + "";
        nickname = editTextNickname.getText() + "";
        passWordConfirm = editTextPasswordConfirm.getText() + "";
        autograph = editTextAutograph.getText() + "";
        if (userName.equals("")) {
            userNameTextInputLayout.setErrorEnabled(true);
            userNameTextInputLayout.setError("请输入你的唯一账号");
            passwordTextInputLayout.setErrorEnabled(false);
            passwordConfirmTextInputLayout.setErrorEnabled(false);
            nicknameTextInputLayout.setErrorEnabled(false);
            autographTextInputLayout.setErrorEnabled(false);
            return false;
        } else if (passWord.equals("")) {
            passwordTextInputLayout.setErrorEnabled(true);
            passwordTextInputLayout.setError("为了安全，还是设置个密码");
            userNameTextInputLayout.setErrorEnabled(false);
            passwordConfirmTextInputLayout.setErrorEnabled(false);
            nicknameTextInputLayout.setErrorEnabled(false);
            autographTextInputLayout.setErrorEnabled(false);
            return false;
        } else if (passWordConfirm.equals("")) {
            passwordConfirmTextInputLayout.setErrorEnabled(true);
            passwordConfirmTextInputLayout.setError("请再次密码");
            passwordTextInputLayout.setErrorEnabled(false);
            userNameTextInputLayout.setErrorEnabled(false);
            nicknameTextInputLayout.setErrorEnabled(false);
            autographTextInputLayout.setErrorEnabled(false);
            return false;
        } else if (!passWordConfirm.equals(passWord)) {
            passwordConfirmTextInputLayout.setErrorEnabled(true);
            passwordConfirmTextInputLayout.setError("密码有错，请确认密码");
            passwordTextInputLayout.setErrorEnabled(false);
            userNameTextInputLayout.setErrorEnabled(false);
            nicknameTextInputLayout.setErrorEnabled(false);
            autographTextInputLayout.setErrorEnabled(false);

            return false;
        } else if (nickname.equals("")) {
            nicknameTextInputLayout.setErrorEnabled(true);
            passwordConfirmTextInputLayout.setErrorEnabled(false);
            nicknameTextInputLayout.setError("取个好听名字");
            passwordTextInputLayout.setErrorEnabled(false);
            userNameTextInputLayout.setErrorEnabled(false);
            autographTextInputLayout.setErrorEnabled(false);
            return false;
        } else if (autograph.equals("")) {
            autographTextInputLayout.setErrorEnabled(true);
            autographTextInputLayout.setError("能不能个性点");
            passwordTextInputLayout.setErrorEnabled(false);
            userNameTextInputLayout.setErrorEnabled(false);
            passwordConfirmTextInputLayout.setErrorEnabled(false);
            nicknameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        return true;
    }

    /*
     * ʵ����������������ڲ��ಢ����ע�����
     *
     * @author:Creater Xv
     */
    class connectionThread extends Thread {
        String responseString = "";

        @Override
        public void run() {
//			try {
//				this.wait();
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
            // TODO Auto-generated method stub
            String url = "http://192.168.43.216:8080/cardGame/userRegisterServlet?Name="
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
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Toast.makeText(getApplicationContext(), "����������������",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(getApplicationContext(), "����������������",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            super.run();
        }

    }

    /*
     * ��֤���ص�ֵ������ͬ����Ϣ��Ӧ ;
     *
     * params:responseString return:void
     */
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
