package com.ali.mirachannel;

import com.ali.mirachannel.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class LoginDialog extends DialogFragment implements OnClickListener{
	LoginCommunicator loginCommunicator;
	Button yesButton;
	Button noButton;
	EditText txtId;
	EditText txtPass;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		loginCommunicator = (LoginCommunicator) activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View view = inflater.inflate(R.layout.login_dialog, null);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		txtId = (EditText) view.findViewById(R.id.login_txt_id);
		txtPass = (EditText) view.findViewById(R.id.login_txt_pass);
		
		yesButton = (Button) view.findViewById(R.id.login_btn_yes);
		noButton = (Button) view.findViewById(R.id.login_btn_no);
		yesButton.setOnClickListener(this);
		noButton.setOnClickListener(this);
		
		return view;
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_btn_yes:
			loginCommunicator.loginMessege("");
			dismiss();
			break;
		case R.id.login_btn_no:
			dismiss();
			break;

		default:
			break;
		}
	}
	interface LoginCommunicator{
		public void loginMessege(String string);
	}
}
