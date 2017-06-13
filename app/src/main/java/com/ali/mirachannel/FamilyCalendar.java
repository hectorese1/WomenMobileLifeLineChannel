package com.ali.mirachannel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.FamilyPlanning;
import com.ali.mirachannel.utility.MiraConstants;

public class FamilyCalendar extends Activity {
	private String MONTH[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	private int[][]DAYS = {{R.id.txt_day_0,R.id.txt_day_1,R.id.txt_day_2,R.id.txt_day_3,R.id.txt_day_4,R.id.txt_day_5,R.id.txt_day_6},
			{R.id.txt_day_7,R.id.txt_day_8,R.id.txt_day_9,R.id.txt_day_10,R.id.txt_day_11,R.id.txt_day_12,R.id.txt_day_13},
			{R.id.txt_day_14,R.id.txt_day_15,R.id.txt_day_16,R.id.txt_day_17,R.id.txt_day_18,R.id.txt_day_19,R.id.txt_day_20},
			{R.id.txt_day_21,R.id.txt_day_22,R.id.txt_day_23,R.id.txt_day_24,R.id.txt_day_25,R.id.txt_day_26,R.id.txt_day_27},
			{R.id.txt_day_28,R.id.txt_day_29,R.id.txt_day_30,R.id.txt_day_31,R.id.txt_day_32,R.id.txt_day_33,R.id.txt_day_34},
			{R.id.txt_day_35,R.id.txt_day_36,R.id.txt_day_37,R.id.txt_day_38,R.id.txt_day_39,R.id.txt_day_40,R.id.txt_day_41}};
	private FamilyPlanning planning;
	DatabaseHelper databaseHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family_calendar);
		// Show the Up button in the action bar.
		databaseHelper = DatabaseHelper.newInstance(this);
		String title= MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"परिवार नियोजन कलेंडर ":"Family Calendar";
		setTitle(title);
		Intent intent = getIntent();
		planning =(FamilyPlanning) intent.getExtras().getSerializable(FamilyPlanning.class.toString());
		findViewById(R.id.txt_month_year).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(FamilyCalendar.this);
				builder.setTitle("Set New Date");
				
				LayoutInflater inflater = (LayoutInflater) FamilyCalendar.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.dialog_familypanning, null, false);
				final EditText edt_fp_name = (EditText) view.findViewById(R.id.edt_fp_name);
				edt_fp_name.setText(planning.getName());
				final Spinner spn_fp_cycle = (Spinner) view.findViewById(R.id.spn_fp_cycle);
				int x=0;
				for (int i = 0; i < spn_fp_cycle.getAdapter().getCount(); i++) {
					if(planning.getCycleDays() == Integer.parseInt(spn_fp_cycle.getAdapter().getItem(i).toString())){
						x=i;
						break;
					}
				}
				spn_fp_cycle.setSelection(x); 
				final Button btn_fp_date = (Button) view.findViewById(R.id.btn_fp_date);
				Calendar calendarTemp = Calendar.getInstance();
				
				String s1[] = planning.getLmcDate().split("/");
//				Calendar calendar = Calendar.getInstance();
				calendarTemp.set(Integer.parseInt(s1[2]), Integer.parseInt(s1[1])-1, Integer.parseInt(s1[0]));
				
				final String dateString = calendarTemp.get(Calendar.DAY_OF_MONTH)+"/"+(calendarTemp.get(Calendar.MONTH)+1)+"/"+calendarTemp.get(Calendar.YEAR);
				btn_fp_date.setText(dateString);
				btn_fp_date.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Calendar cal = Calendar.getInstance(TimeZone.getDefault());
						
						String s1[] = planning.getLmcDate().split("/");
			    		cal = Calendar.getInstance();
			    		cal.set(Integer.parseInt(s1[2]), Integer.parseInt(s1[1])-1, Integer.parseInt(s1[0]));
						
						DatePickerDialog pickerDialog = new DatePickerDialog(FamilyCalendar.this, new DatePickerDialog.OnDateSetListener() {

					        public void onDateSet(DatePicker view, int selectedYear,
					                int selectedMonth, int selectedDay) {
					        	
					            Calendar calendar = Calendar.getInstance();
					            calendar.set(Calendar.DATE, selectedDay);
					            calendar.set(Calendar.MONTH, selectedMonth);
					            calendar.set(Calendar.YEAR, selectedYear);							            
					            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					            btn_fp_date.setText(sdf.format(calendar.getTime()));
					        }
					    } , cal.get(Calendar.YEAR), 
			                    cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
						pickerDialog.setCancelable(false);
						pickerDialog.setTitle("Select the date");
						pickerDialog.show();
					}
				});
				builder.setView(view);
				builder.setNegativeButton("Cancel", null);
				builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String query_string = "UPDATE tabfamplan SET cycledays = '"+spn_fp_cycle.getSelectedItem().toString()+"',lmcdate = '"+btn_fp_date.getText().toString()+"' WHERE id = "+planning.getKeyId();
						databaseHelper.updateFamilyPlanning(query_string);
						
						planning = databaseHelper.getFamilyPlanningIndivid(planning.getKeyId()).get(0);
						getdate("");
					}
				});
				builder.show();
			}
		});
		getdate("");
		setupActionBar();
	}

	private void getdate(String date){
		String s1[] = planning.getLmcDate().split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(s1[2]), Integer.parseInt(s1[1])-1, Integer.parseInt(s1[0]));
		int startDay = calendar.get(Calendar.DAY_OF_MONTH);
		int startDayName = calendar.get(Calendar.DAY_OF_WEEK);
		int startMonth = calendar.get(Calendar.MONTH);
		int startYear = calendar.get(Calendar.YEAR);
		
		Calendar calendarEnd = calendar.getInstance();
		calendarEnd.set(Integer.parseInt(s1[2]), Integer.parseInt(s1[1])-1, Integer.parseInt(s1[0]));
		calendarEnd.add(Calendar.DAY_OF_MONTH, planning.getCycleDays());
		
		int endDay = calendarEnd.get(Calendar.DAY_OF_MONTH);
		int endDayName = calendarEnd.get(Calendar.DAY_OF_WEEK);
		int endMonth = calendarEnd.get(Calendar.MONTH);
		int endYear = calendarEnd.get(Calendar.YEAR);

		String month = (startMonth!=endMonth)?(MONTH[startMonth]+"-"+MONTH[endMonth]):(MONTH[endMonth]);
		String year = (startYear!=endYear)?(startYear+"-"+String.valueOf(endYear).substring(2)):endYear+"";
		((TextView)findViewById(R.id.txt_month_year)).setText(month+"/"+year);

		int dayCount = 0;
		boolean isFirst = true;
		for (int i = 0; i < DAYS.length; i++) {
			for (int j = 0; j < DAYS[i].length; j++) {
				TextView textView = (TextView) findViewById(DAYS[i][j]);
				if (i == 0 && j < startDayName - 1) {
					textView.setText("");
					textView.setBackgroundColor(getResources().getColor(R.color.TRANSPARENT));	
				} else {
					if (calendar.before(calendarEnd)) {
						textView.setBackgroundColor(getResources().getColor(R.color.green_color));		
						textView.setBackgroundResource(R.drawable.fp_safe);
						if(dayCount>=7&&dayCount<=18){
							textView.setBackgroundColor(getResources().getColor(R.color.red_color));
							textView.setBackgroundResource(R.drawable.fp_unsafe);
						}
						if(i==0&&calendar.get(calendar.DAY_OF_MONTH)==startDay){
							textView.setBackgroundColor(getResources().getColor(R.color.color_yellow));
							textView.setBackgroundResource(R.drawable.fp_firstday);
						}
						if(calendar.get(calendar.DAY_OF_MONTH)==endDay-1){
							textView.setBackgroundColor(getResources().getColor(R.color.color_darkgreen));
							textView.setBackgroundResource(R.drawable.fp_lastday);
						}
						if(calendar.get(calendar.DAY_OF_MONTH)==Calendar.getInstance().get(Calendar.DAY_OF_MONTH)&&isFirst){
							textView.setBackgroundColor(getResources().getColor(R.color.color_today));
							textView.setBackgroundResource(R.drawable.fp_today);
							isFirst=false;
						}
						
						textView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
					} else {
						textView.setText("");
						textView.setBackgroundColor(getResources().getColor(R.color.TRANSPARENT));	
					}
					dayCount++;
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}							
			}
		}
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.family_calendar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
