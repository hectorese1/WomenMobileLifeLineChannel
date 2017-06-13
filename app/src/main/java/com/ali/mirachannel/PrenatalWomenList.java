package com.ali.mirachannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.model.CloseCaseDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.HashMap;
import java.util.Map;

/**display list of prenatla woment*/
public class PrenatalWomenList extends ListFragment{
	private String action = MiraConstants.view;
	PrenatalWomenListComm comm;
	DatabaseHelper databaseHelper;
	private TextView resultText;
	int weeknumber;
//	public static SharedPreferences sharedPreferences;
//	public static SharedPreferences.Editor editor;
//	public static HashMap<String,Integer> hashMap=null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);

		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		comm = (PrenatalWomenListComm) activity;
		databaseHelper = DatabaseHelper.newInstance(getActivity());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.v("MIRA", getTag());
		MyWomanBaseAdapter adapter = new MyWomanBaseAdapter(getActivity());
//		MySharedPreference.clearPrefernce(getActivity().getBaseContext(),MiraConstants.PreNatal_Prefrences);
//		sharedPreferences=getActivity().getSharedPreferences(MiraConstants.PreNatal_Prefrences,getActivity().getBaseContext().MODE_PRIVATE);
//		editor=sharedPreferences.edit();
//		hashMap=new HashMap<String, Integer>();
//		editor.clear();
//		editor.commit();
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		final WomenDtl womenDtl = (WomenDtl) l.getAdapter().getItem(position);
		Toast.makeText(getActivity(), "Click   "+womenDtl.getWomanId()+"   "+womenDtl.getName(), Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent();
//		intent.putExtra("womanid", womenDtl.getWomanId());
//		intent.putExtra("pregid", womenDtl.getPregnentId());
		weeknumber = MiraConstants.calculateWeekNumber(womenDtl.getLmcDate());


		System.out.println("WeekNumber In preNatalWomen Lsit " + weeknumber);
		System.out.println("weeklyInfo from PrenatalWomenList..." + womenDtl.getWomanId() + "   " + womenDtl.getName() + "  " + weeknumber);

//

		//		intent.putExtra("weeknumber", ((MyWomanBaseAdapter) l.getAdapter()).getWeekNumber());
//		intent.putExtra("weeknumber", weeknumber-1);
//		intent.putExtra("womenname", womenDtl.getName());

		intent.putExtra(WomenDtl.class.getName(), womenDtl);
		
		if (action.equals(MiraConstants.view)) {
			intent.setClass(getActivity(), WeeklyInfo.class);
			startActivity(intent);
		}
		else if (action.equals(MiraConstants.closecase)) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			String title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"बंद केस ":"Close Case";
			builder.setTitle(title);
			String eng[]={"Delivered","Aborted","Women Died","Migrated"};
			String hin[]={"प्रसव  ","गर्भपात ","महिला की मृत्यु ","पलायन "};
			String msg[]=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?hin:eng;

			ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, msg);
			builder.setAdapter(adapter, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0://Delivered
						if(weeknumber+1>20){
//							delivered(womenDtl);
							locationStatus(womenDtl,1);
						}
						else{
							String alrt=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"अलर्ट ":"ALERT";
							String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"बीसवे सप्ताह से पहले प्रसव संभव नहीं है|":"Delivery Not Possilbe Less than 20 Weeks";
							AlertDialog.Builder dialog12 = myAlertDialog(1,alrt,msg);
							dialog12.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();

								}
							});
							dialog12.show();

//							Toast.makeText(getActivity(),"Delivery Not Possilbe Less than 20 Weeks",Toast.LENGTH_SHORT).show();
						}
//						NumberOfLive(womenDtl);
						break;
					case 1:
						insertStatus(womenDtl, MiraConstants.ABORTED);
						break;
					case 2:
//						insertStatus(womenDtl, MiraConstants.DIED);
//						WomenDied_Rep(womenDtl);
						locationStatus(womenDtl, 2);
						break;					
					case 3:
						insertStatus(womenDtl, MiraConstants.MIGRATED);
					default:
						break;
					}
				}
			});
			builder.show();
		}
	}

	private HashMap<String,Integer> iniliazeVisitHashMap(int weeknumber){

		HashMap<String,Integer> hash = new HashMap<String, Integer>();
		hash.put("00_Screne", 1);
		hash.put("0_Screne", 0);
		hash.put("1_Screne",0);
		hash.put("2_Screne", 0);
		hash.put("3_Screne", 0);
		return hash;

	}



	///----------------------Faisal ----------------------------vvvvvvvvvvvvvvvvvvvv
	public void NumberOfLive(final WomenDtl womenDtl){

		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setView(promptView);
		final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
		String ok=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जमा करें ":"OK";
		String cancel=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कैंसिल":"Cancel";

		AlertDialog.Builder builder = alertDialogBuilder.setCancelable(false)


				.setPositiveButton(ok, new OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						editText.setText(editText.getText());
						String value = editText.getText().toString();

						if (Integer.parseInt(value) > 5) {
							System.out.println("Total number of child...." + value);
							Toast.makeText(getActivity(), "Insert Number Of Child less than 6", Toast.LENGTH_SHORT).show();
							NumberOfLive(womenDtl);
						} else {
							MiraConstants.noOfChlidLive = Integer.parseInt(value);
							System.out.println("No Of Live Child is ====" + MiraConstants.noOfChlidLive);
							insertStatus(womenDtl, MiraConstants.DELIVERED);
							comm.getprenatalListElemnt(womenDtl);
						}
//						MiraConstants.noOfChlidLive = resultText;
					}
				})
				.setNegativeButton(cancel,
						 new OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();

	}


	///----------------------Faisal ---------------------------^^^^^^^^^^^^^^^^^^^^^^^^^^^

	public void WomenDied_Rep(final WomenDtl womenDtl){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जीवित व मृत जन्म ":"Mixed Birth";
		builder.setTitle(title);
		final int weeknumber = MiraConstants.calculateWeekNumber(womenDtl.getLmcDate());
		String option[]=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?new String[]{"मृत जन्म"," जीवित जन्म"}:new String[]{" Still"," Birth"};
		ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, option);
		builder.setAdapter(adapter, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case 0://Delivered
//						MiraConstants.noOfChlidLive=1;
//						System.out.println("No Of Live Child is ===="+MiraConstants.noOfChlidLive);
						insertStatus(womenDtl, MiraConstants.DIED);
						break;
					case 1:
//						MiraConstants.noOfChlidLive=2;
//						System.out.println("No Of Live Child is ===="+MiraConstants.noOfChlidLive);
						if(weeknumber>20){
							insertStatus(womenDtl, MiraConstants.DIED);
							NumberOfLive(womenDtl);
						}
						else{
							AlertDialog.Builder dialog12 = myAlertDialog(1,"ALERT","Delivery Not Possilbe Less than 20 Weeks");
							dialog12.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();

								}
							});
							dialog12.show();

//							Toast.makeText(getActivity(),"Delivery Not Possilbe Less than 20 Weeks",Toast.LENGTH_SHORT).show();

						}

//						insertStatus(womenDtl, MiraConstants.DELIVERED);
//						comm.getprenatalListElemnt(womenDtl);
						break;

					default:
						break;
				}
			}
		});
		builder.show();
	}

	public void locationStatus(final  WomenDtl womenDtl,final int a){
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		String title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"Location":"Location";
		builder.setTitle(title);
		String eng[]={"At Hospital","At Home"};
		String hin[]={"At Hospital","At Home"};
		String msg[]=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?hin:eng;
		ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,msg);
		builder.setAdapter(adapter, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
					case 0:
						break;
					case 1:
						break;
				}
				if(a==1){
					delivered(womenDtl);
				}
				else{
					WomenDied_Rep(womenDtl);
				}
			}
		});
		builder.show();
	}
	 
	public void delivered(final WomenDtl womenDtl) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"प्रसव  ":"Delivered";
		builder.setTitle(title);
		String eng[]={"Live","Mixed","Still"};
		String hin[]={"जीवित जन्म ","जीवित व मृत जन्म ","मृत जन्म"};
		String msg[]=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?hin:eng;

		ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, msg);
		builder.setAdapter(adapter, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0://Delivered
//					insertStatus(womenDtl, MiraConstants.DELIVERED);
//					comm.getprenatalListElemnt(womenDtl);
					NumberOfLive(womenDtl);
					System.out.println("We are in the Live Click......");
					break;
				case 1:
//					insertStatus(womenDtl, MiraConstants.ABORTED);
					WomenDied_Rep(womenDtl);
					System.out.println("We are in the Mixed Click......");
					break;
				case 2:
					insertStatus(womenDtl, MiraConstants.DIED);
					System.out.println("We are in the Still Click......");
					break;
				case 3:
					insertStatus(womenDtl, MiraConstants.MIGRATED);
					break;
				default:
					break;
				}
			}
		});
		builder.show();
	}

	
	private void insertStatus(WomenDtl womenDtl,int status) {
		CloseCaseDtl closeCaseDtl = new CloseCaseDtl();
//		closeCaseDtl.setPregnentId(womenDtl.getPregnentId());
		closeCaseDtl.setPregnentId(womenDtl.getPregnentId());
		closeCaseDtl.setStatus(status);
		closeCaseDtl.setWomenName(womenDtl.getName());
		closeCaseDtl.setUploade("0");
//		closeCaseDtl.setLocation("1");
		closeCaseDtl.setCreatedAt(MiraConstants.getDateTime());
		databaseHelper.insertCloseCase(closeCaseDtl);
		String query_string = "status = '0' ,pregid = '0' WHERE womanid='"+womenDtl.getWomanId()+"'";
		
		databaseHelper.updateWomenDetails(query_string);
		
		databaseHelper.deletePregnantWoman(womenDtl.getKeyId());
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
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	interface PrenatalWomenListComm{
		public void getprenatalListElemnt(WomenDtl womenDtl); 
	}
}
