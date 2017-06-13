package com.ali.mirachannel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.model.HouseDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**display immunization form for immunizastion registration*/
@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class ImmunzRegistration extends Fragment implements OnClickListener{
	FragmentManager manager;
	   private String date;
	Button cancel;
	Button submit;
	Button dateOfBirth;
	EditText imz_child_name;
	EditText imz_mother_name;
	EditText imz_father_name;
	TextView numOfCounter;
	Spinner imz_sex;
	DatabaseHelper databaseHelper;
	static WomenDtl womenDtlmain;
	static HouseDtl houseDtl;
	ImmunzRegistrationComm comm;
	static int counter=1;
	int  year,month,day;
	
	public static final ImmunzRegistration newInstance(WomenDtl womenDtl) {
		ImmunzRegistration immunzRegistration = new ImmunzRegistration();

		womenDtlmain = womenDtl;
		return immunzRegistration;
	}
	public static final ImmunzRegistration newInstance(HouseDtl houseDtl) {
		ImmunzRegistration immunzRegistration = new ImmunzRegistration();

		houseDtl = houseDtl;
		return immunzRegistration;
	}
		
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		comm = (ImmunzRegistrationComm) activity;

		databaseHelper = DatabaseHelper.newInstance(getActivity());;
		date = MiraConstants.getDateTime();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
//		manager = getSupportFragmentManager();
		View view = inflater.inflate(R.layout.immunz_registration, container, false);
		((TextView)view.findViewById(R.id.imzhouseNumber)).setText(womenDtlmain.getHouseNumber());
		imz_child_name = (EditText) view.findViewById(R.id.imz_child_name);
		imz_mother_name = (EditText) view.findViewById(R.id.imz_mother_name);
		imz_mother_name.setText(womenDtlmain.getName());
		System.out.println("Women Lmc Date...." + womenDtlmain.getLmcDate());
		imz_father_name = (EditText) view.findViewById(R.id.imz_father_name);
		imz_father_name.setText(womenDtlmain.getHusname());
		imz_sex = (Spinner) view.findViewById(R.id.imz_sex);
//		numOfCounter= (TextView) view.findViewById(R.id.numOfChild);
//		numOfCounter.setText(String.valueOf(counter));

		cancel = (Button) view.findViewById(R.id.pr_lmc_date);
		submit = (Button) view.findViewById(R.id.imz_submit);
		dateOfBirth = (Button) view.findViewById(R.id.imz_dob);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
//		dateOfBirth.setText(sdf.format(calendar.getTime()).toString());

		dateOfBirth.setText(MainActivity.serverDate);

//		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH);
		day=calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("Current Date year And Month..."+year+"   "+month+"   "+day);

		dateOfBirth.setOnClickListener(this);
		cancel.setOnClickListener(this);
		submit.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imz_dob:
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

		        public void onDateSet(DatePicker view, int selectedYear,
		                int selectedMonth, int selectedDay) {
		        	
		            Calendar calendar = Calendar.getInstance();
//		            calendar.set(Calendar.DATE, selectedDay);//&&selectedDay<=MiraConstants.serverDayInt
					if(selectedYear==MiraConstants.serverYearInt&&selectedMonth+1<=MiraConstants.serverMonthInt){
						calendar.set(Calendar.DATE, selectedDay);
						calendar.set(Calendar.MONTH, selectedMonth);
						calendar.set(Calendar.YEAR, selectedYear);

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						date = sdf.format(calendar.getTime());

						dateOfBirth.setText(date);

					}
					else if(selectedYear>MiraConstants.serverYearInt-5&&(selectedYear<MiraConstants.serverYearInt)){
						calendar.set(Calendar.DATE, selectedDay);
						calendar.set(Calendar.MONTH, selectedMonth);
						calendar.set(Calendar.YEAR, selectedYear);

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						date = sdf.format(calendar.getTime());
						dateOfBirth.setText(date);
					}

					else {
						String alrt=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"अलर्ट ":"ALERT";
						String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कृपया पिछले पाँच वर्ष के अंदर ही तारीख चुनें":"Please select date within last 5 years ";
						AlertDialog.Builder dialog12 = myAlertDialog(1,alrt,msg);
						dialog12.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();

							}
						});
						dialog12.show();

//						Toast.makeText(getActivity(), "Can't take date...", Toast.LENGTH_SHORT).show();
					}

		        }
		    } , MiraConstants.serverYearInt,
                    MiraConstants.serverMonthInt-1,MiraConstants.serverDayInt);


			pickerDialog.setCancelable(false);
			String negative=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कैंसिल ":"Cancel";
			String positive=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जमा करें":"Submit";

			pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,positive,pickerDialog);
			pickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, pickerDialog);
			String dateString = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख का चयन करें":"Select the date";
			pickerDialog.setTitle(dateString);
			pickerDialog.show();
			break;
		case R.id.imz_submit:
			if(imz_child_name.getText().toString().trim().length()<=0){
				Toast.makeText(getActivity(), "Enter Child Name", Toast.LENGTH_SHORT).show();
			}else{
				ChildDtl childDtl = new ChildDtl();
//				childDtl.setHouseID(womenDtlmain.getHouseID());
				childDtl.setWomanId(womenDtlmain.getWomanId());
				childDtl.setChildId("0");
				childDtl.setName(imz_child_name.getText().toString().toUpperCase());
				childDtl.setSex(imz_sex.getSelectedItemPosition() == 0 ? "Male" : "Female");
				childDtl.setDob(date);
				childDtl.setStatus("0");
				childDtl.setUpload("0");
				databaseHelper.insertChildRec(childDtl);
				Toast.makeText(getActivity(), "Record Submitted", Toast.LENGTH_SHORT).show();

				if(counter<MiraConstants.noOfChlidLive){
					    counter++;
						comm.getprenatalListElemnt(womenDtlmain);
				}

				else {
					counter=1;
					MiraConstants.noOfChlidLive=0;
					comm.immunReg();
				}
			}

			break;
		default:
			break;
			
		}
//		comm.immunReg();
	}

	public  AlertDialog.Builder  myAlertDialog(int cases,String title,String message) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

		switch (cases){

			case 1:
				dialog.setTitle(title);
				dialog.setMessage(message);
				//  dialog.setIcon(R.drawable.ic_dialog_alert);
				break;

			default:
				break;
		}

		return dialog;
	}
		
	interface ImmunzRegistrationComm{
		public void immunReg();
		public void getprenatalListElemnt(WomenDtl womenDtl);
	}


}
