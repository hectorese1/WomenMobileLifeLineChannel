package com.ali.mirachannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.HouseDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**prenatla registration fragment*/
public class PrenatalRegistration extends Fragment implements OnClickListener{
	
	Button cancel;
	Button submit;
	Button lmcDate;
	EditText pr_women_name;
	EditText pr_husband_name;
	Spinner pr_women_age;
	Spinner pr_num_child;
	TextView textLmc;

	int  year,month,day;
	PreNatalComm comm;
	DatabaseHelper databaseHelper;
	static HouseDtl houseDtlmain;
	static boolean ischild;
	public static final PrenatalRegistration newInstance(HouseDtl houseDtl,boolean is) {
		PrenatalRegistration prenatalRegistration = new PrenatalRegistration();
		houseDtlmain = houseDtl;
		ischild=is;
		return prenatalRegistration;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v("MIRA1111", getTag());
		comm = (PreNatalComm) activity; 
		databaseHelper = DatabaseHelper.newInstance(getActivity());
	}
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.prenatal_registration, container, false);
		((TextView)view.findViewById(R.id.pr_house_number)).setText(houseDtlmain.getHouseNumber());
		pr_women_name = (EditText) view.findViewById(R.id.pr_women_name);
		pr_husband_name = (EditText) view.findViewById(R.id.pr_husband_name);
		Integer[]ageGroupe = {14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
		ArrayAdapter<Integer>adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, ageGroupe);
		pr_women_age = (Spinner) view.findViewById(R.id.pr_women_age);
		pr_women_age.setAdapter(adapter);
		pr_women_age.setSelection(4);
		pr_num_child = (Spinner) view.findViewById(R.id.pr_num_child);
		
		cancel = (Button) view.findViewById(R.id.pr_cancel);
		submit = (Button) view.findViewById(R.id.pr_submit);
		lmcDate = (Button) view.findViewById(R.id.pr_lmc_date);

		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	    year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH);
		day=cal.get(Calendar.DAY_OF_MONTH);

		if(ischild==true){
			textLmc=(TextView)view.findViewById(R.id.lmcDate);
			lmcDate.setEnabled(false);
		    textLmc.setEnabled(false);
			date="01/01/1900";
			lmcDate.setText("01/01/1900");
		}
//
//
  else if(ischild==false){
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(new Date());
	  date = sdf.format(calendar.getTime());
	  System.out.println(date); //15/10/2013
			System.out.println("Calendar* "+calendar.get(Calendar.YEAR)+"  "+calendar.get((Calendar.MONTH)));
	  lmcDate.setText(MainActivity.serverDate);
  }
//		else{
//
//
//  }



		
		lmcDate.setOnClickListener(this);
		cancel.setOnClickListener(this);
		submit.setOnClickListener(this);
		return view;
	}
	 String date;
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.pr_lmc_date:
			selectDate();
//			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//			DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//
//
//				public void onDateSet(DatePicker view, int selectedYear,
//		                int selectedMonth, int selectedDay) {
//
//		            Calendar calendar = Calendar.getInstance();
//		            calendar.set(Calendar.DATE, selectedDay);
//		            calendar.set(Calendar.MONTH, selectedMonth);
//		            calendar.set(Calendar.YEAR, selectedYear);
//
//		            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		            date = sdf.format(calendar.getTime());
//		    		System.out.println(date); //15/10/2013
//		    	    lmcDate.setText(date);
//		        }
//		    } , cal.get(Calendar.YEAR),
//                    cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
//			pickerDialog.setCancelable(false);
//			pickerDialog.setTitle("Select the date");
//			pickerDialog.show();
//			Toast.makeText(getActivity(), "Toast", Toast.LENGTH_SHORT).show();
			break;
			case R.id.pr_submit:
				if(pr_women_name.getText().toString().isEmpty()){
					Toast.makeText(getActivity(), "Enter Woman Name", Toast.LENGTH_SHORT).show();
				}else if(pr_husband_name.getText().toString().isEmpty()){
					Toast.makeText(getActivity(), "Enter Husban Name", Toast.LENGTH_SHORT).show();
				}
                else {

//					WomenDtl womenDtl = new WomenDtl();
//					womenDtl.setHouseID(houseDtlmain.getHouseID());
//					womenDtl.setWomanId("0");
//					womenDtl.setPregnentId("0");
//					womenDtl.setStatus("1");
//					womenDtl.setUploade("0");
//					womenDtl.setName(pr_women_name.getText().toString());
//					womenDtl.setHusname(pr_husband_name.getText().toString());
//					womenDtl.setAge(pr_women_age.getSelectedItemPosition());
//					womenDtl.setChildren(pr_num_child.getSelectedItemPosition());
//					womenDtl.setLmcDate(date);	
//				    databaseHelper.insertWomenRec(womenDtl);
//				    comm.getprenatalId(0);
					if (ischild == false) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						String string = MiraConstants.HINDI.equals(MiraConstants.HINDI) ? "क्या महिला गर्भवती है?" : "is woman pregnant?";
						builder.setTitle(string);
						String no = MiraConstants.HINDI.equals(MiraConstants.HINDI) ? "नही" : "No";
						builder.setNegativeButton(no, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								setRecord(String.valueOf(0));
							}
						});
						String yes = MiraConstants.HINDI.equals(MiraConstants.HINDI) ? "हाँ" : "Yes";
						builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								//selectDate();
								setRecord(String.valueOf(1));
							}
						});
						builder.show();
					}
					else if(ischild==true){
						setRecord(String.valueOf(0));
					}
				}

				break;
		default:
			break;
		}
	}
	/**method to select date for registrion*/
	public void selectDate() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//		Date date = new Date();
//		System.out.println("Today's Date from MiraConstt....."+date);
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		try {
//			date = sdf.parse("12/12/1989");
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		//		int  year=cal.get(Calendar.YEAR);
//		System.out.println("SimpleDateFromat "+ cal.get(Calendar.MONTH));
		DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {


			@SuppressLint("SimpleDateFormat")
			public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

	            Calendar calendar = Calendar.getInstance();

//	            calendar.set(Calendar.DATE, selectedDay);

//	            calendar.set(Calendar.MONTH, selectedMonth);
//				calendar.set(Calendar.YEAR, selectedYear);
				if(selectedYear==MiraConstants.serverYearInt-1&&(selectedMonth+1!=MiraConstants.serverMonthInt)&&(selectedMonth+1!=MiraConstants.serverMonthInt+1)&&(selectedMonth+1!=MiraConstants.serverMonthInt+2))
				{
					calendar.set(Calendar.DATE, selectedDay);
					calendar.set(Calendar.MONTH, selectedMonth);
					calendar.set(Calendar.YEAR, selectedYear);

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					date = sdf.format(calendar.getTime());
					System.out.println(date); //15/10/2013
					lmcDate.setText(date);
				}
				else if(selectedYear==MiraConstants.serverYearInt&&selectedMonth+1<=MiraConstants.serverMonthInt){
					calendar.set(Calendar.DATE, selectedDay);
					calendar.set(Calendar.MONTH, selectedMonth);
					calendar.set(Calendar.YEAR, selectedYear);
					System.out.println("Hello ji..." + selectedMonth);

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					date = sdf.format(calendar.getTime());
					System.out.println(date); //15/10/2013
					lmcDate.setText(date);
				}

//				else if(selectedYear==MiraConstants.serverYearInt&&selectedMonth<=MiraConstants.serverMonthInt&&selectedDay<=MiraConstants.serverDayInt){
//					calendar.set(Calendar.DATE, selectedDay);
//					calendar.set(Calendar.MONTH, selectedMonth);
//					calendar.set(Calendar.YEAR, selectedYear);
//					System.out.println("Hello ji..." + selectedMonth);
//
//					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//					date = sdf.format(calendar.getTime());
//					System.out.println(date); //15/10/2013
//					lmcDate.setText(date);
//				}


				else{
					String alrt=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"अलर्ट":"ALERT";
					String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कृपया पिछले 1 वर्ष के अंतर्गत ही तारीख चुनें ":"Please select date within last 1 year";
					AlertDialog.Builder dialog = myAlertDialog(1,alrt,msg);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();

						}
					});
					dialog.show();


//					Toast.makeText(getActivity(),"You Can't select Date Before One year From Current Date",Toast.LENGTH_SHORT).show();
				}


	            

	    	    
	    	    if (view.isShown()) {
	    	    	System.out.println("View Shown ----  "+date); //15/10/2013
					Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
	    	    	//setRecord(String.valueOf(1));
	    	    }
	        }
	    } , MiraConstants.serverYearInt,
                MiraConstants.serverMonthInt-1,MiraConstants.serverDayInt);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
//			long d=(9*31556952L / 12)*1000;
//			pickerDialog.getDatePicker().setMinDate(d);
			//datePicker.getDatePicker().setMaxDate(1546210800000L);

		}
		pickerDialog.setCancelable(true);
		String negative=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कैंसिल ":"Cancel";
		String positive=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जमा करें":"Submit";

		pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,positive,pickerDialog);
		pickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,negative,pickerDialog);
		String dateString = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख का चयन करें":"Select the date";
		pickerDialog.setTitle(dateString);

				
		pickerDialog.show();			
		Toast.makeText(getActivity(), "Toast", Toast.LENGTH_SHORT).show();
	}
	/**set records of women in database*/
	private void setRecord(String string) {

		WomenDtl womenDtl = new WomenDtl();
		womenDtl.setHouseID(houseDtlmain.getHouseID());
		womenDtl.setWomanId("0");
		womenDtl.setPregnentId("0");
		womenDtl.setStatus(string);
		womenDtl.setUploade("0");
		womenDtl.setName(pr_women_name.getText().toString());
		womenDtl.setHusname(pr_husband_name.getText().toString());
		womenDtl.setAge(Integer.parseInt(pr_women_age.getSelectedItem().toString()));
		womenDtl.setChildren(pr_num_child.getSelectedItemPosition());
		womenDtl.setLmcDate(date);	
	    databaseHelper.insertWomenRec(womenDtl);
	    comm.getprenatalId(0);
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
	
	interface PreNatalComm{
		public void getprenatalId(int id);
	}
}
