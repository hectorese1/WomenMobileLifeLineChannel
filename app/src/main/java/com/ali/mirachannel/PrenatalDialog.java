package com.ali.mirachannel;

import com.ali.mirachannel.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
/**dialog to show the details of prenatal reports for child menu of bottom*/
public class PrenatalDialog extends DialogFragment{

	public static final PrenatalDialog newInstance(String header,String descrp,int image) {
		PrenatalDialog dialog = new PrenatalDialog();
		Bundle bundle = new Bundle();
		bundle.putString("header", header);
		bundle.putString("descrp", descrp);
		bundle.putInt("image", image);
		dialog.setArguments(bundle);
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View view = inflater.inflate(R.layout.pre_dialog, null);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		TextView header = (TextView) view.findViewById(R.id.txt_header);
		header.setText(getArguments().getString("header"));
		TextView descrp = (TextView) view.findViewById(R.id.txt_descrp);
		descrp.setText(getArguments().getString("descrp"));//, "No value"));
		
		ImageView imageView = (ImageView) view.findViewById(R.id.img_image);
		imageView.setImageResource(getArguments().getInt("image"));
		return view;
	}
	
}
