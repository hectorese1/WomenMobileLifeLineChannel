package com.ali.mirachannel;

import com.ali.mirachannel.R;
import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.HouseDtl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Sherali_ali@yahoo.com
 *
 */
/**class show house registration form fragment*/
public class HouseRegistration extends Fragment implements OnClickListener {

	Button cancel;
	Button submit;	
	HouseRegComm comm;
	
	EditText hs_house_number;
	EditText he_family_head;	
	EditText hs_address;
	
	Spinner hs_family_members;
	Spinner hs_married_women;
	Spinner hs_unmarried_women;
	Spinner hs_adolecence_girls;
	Spinner hs_child_less_five;
	TextView textlati;
	TextView textlongi;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		comm = (HouseRegComm) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.house_registration, container,
				false);
		Log.v("MIRA", getTag());
		hs_house_number = (EditText) view.findViewById(R.id.hs_house_number);
		hs_house_number.requestFocus();
		he_family_head = (EditText) view.findViewById(R.id.he_family_head);
		hs_address = (EditText) view.findViewById(R.id.hs_address);
		
		hs_family_members = (Spinner) view.findViewById(R.id.hs_family_members);
		hs_married_women = (Spinner) view.findViewById(R.id.hs_married_women);
		hs_unmarried_women = (Spinner) view.findViewById(R.id.hs_unmarried_women);
		hs_adolecence_girls = (Spinner) view.findViewById(R.id.hs_adolecence_girls);
		hs_child_less_five = (Spinner) view.findViewById(R.id.hs_child_less_five);


		cancel = (Button) view.findViewById(R.id.hs_cancel);
		submit = (Button) view.findViewById(R.id.hs_submit);
		cancel.setOnClickListener(this);
		submit.setOnClickListener(this);
		Toast.makeText(getActivity(),"Lati="+MainMenuActivity.latitude+" Longi="+MainMenuActivity.longitude,Toast.LENGTH_SHORT).show();
		return view;
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.hs_cancel:
			comm.gethouseId(0);			
			break;
		case R.id.hs_submit:
			if(hs_house_number.getText().toString().trim().length()<=0){
				hs_house_number.requestFocus();
				Toast.makeText(getActivity(), "Enter House Number", Toast.LENGTH_SHORT).show();
			}else if(he_family_head.getText().toString().trim().length()<=0){
				he_family_head.requestFocus();
				Toast.makeText(getActivity(), "Enter Name of Family Head", Toast.LENGTH_SHORT).show();
			}else if(hs_address.getText().toString().trim().length()<=0){
				
				hs_address.setText("No Land Mark");
			}else{
				comm.gethouseId(1);			
				HouseDtl houseDtl = new HouseDtl();
				houseDtl.setHouseID("0");
				houseDtl.setHouseNumber(hs_house_number.getText().toString());
				houseDtl.setFamilyHead(he_family_head.getText().toString());
				houseDtl.setLandMark(hs_address.getText().toString());
				
				houseDtl.setFamilyMembers(hs_family_members.getSelectedItemPosition());
				houseDtl.setMarriedWomen(hs_married_women.getSelectedItemPosition());
				houseDtl.setUnmarriedWomen(hs_unmarried_women.getSelectedItemPosition());
				houseDtl.setAdolecenceGorls(hs_adolecence_girls.getSelectedItemPosition());
				houseDtl.setChildrens(hs_child_less_five.getSelectedItemPosition());
				
				DatabaseHelper helper = DatabaseHelper.newInstance(getActivity());;
				helper.insertintoHouse(houseDtl);
				helper.insertintoUserLog("Registered a House");
			}
			
			
			break;
		default:
			break;
		}

	}

	interface HouseRegComm {
		public void gethouseId(int id);
	}
}
