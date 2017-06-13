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
import android.widget.Button;
import android.widget.Toast;


/**
 * @author Sherali_ali@yahoo.com
 *
 */
/**display the dialog for ASHA */
public class MyDialog extends DialogFragment implements OnClickListener{
	
	Button buttonYes;
	Button buttonNo;
	Communicator communicator;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		communicator = (Communicator) activity;
	}
		
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View rootView = inflater.inflate(R.layout.asha_dialog, null);
		getDialog().setTitle("Are You ASHA Worker");	
		buttonNo = (Button) rootView.findViewById(R.id.dialog_btn_no);
		buttonNo.setOnClickListener(this);
		buttonYes = (Button) rootView.findViewById(R.id.dialog_btn_yes);
		buttonYes.setOnClickListener(this);
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_btn_yes:
//			Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
			communicator.onDialogMessege("Yes");
			dismiss();
			break;
		case R.id.dialog_btn_no:
//			Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
			communicator.onDialogMessege("No");
			dismiss();
			break;
		default:
			break;
		}
	}
	
	interface Communicator{
		public void onDialogMessege(String messege);
			
		
	}
}
