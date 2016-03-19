package com.example.cheng.myfifthapplication.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

	private Toolbar toolbar;
	private Button login_in;
	private EditText usernameEdit;
	private EditText passwordEdit;
	private CheckBox rememberPass;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		rememberPwd();
		loginIn();
	}

	private void init(){
		toolbar = (Toolbar) findViewById(R.id.back_toolbar);
		TypedArray array = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
		toolbar.setBackgroundColor(array.getColor(0, 0xFF00FF));
		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		login_in = (Button)findViewById(R.id.sign_in_button);
		usernameEdit = (EditText) findViewById(R.id.username);
		passwordEdit = (EditText) findViewById(R.id.password);
		rememberPass = (CheckBox) findViewById(R.id.remember_pwd);
//		pref = PreferenceManager.getDefaultSharedPreferences(this);
		pref =getSharedPreferences("Daily",0);
	}

	private void rememberPwd(){
		boolean isRemember = pref.getBoolean("remember_password", false);
		if(isRemember){
			// 将账号和密码都设置到文本框中
			String account = pref.getString("account", "");
			String password = pref.getString("password", "");
			usernameEdit.setText(account);
			passwordEdit.setText(password);
			rememberPass.setChecked(true);
		}
	}

	private void loginIn() {
		login_in.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String account = usernameEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				if(checkUser() == true){
					editor = pref.edit();
					if(rememberPass.isChecked()){
						editor.putBoolean("remember_password", true);
						editor.putString("account", account);
						editor.putString("password", password);
					} else {
						editor.clear();
					}
					editor.commit();
					startActivity();
				}
				else{
					Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private boolean checkUser()
	{
		return true;
	}

	private void startActivity(){
		Intent intent =  new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}
}

