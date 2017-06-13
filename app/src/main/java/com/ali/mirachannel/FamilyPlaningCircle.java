package com.ali.mirachannel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ali.mirachannel.model.FamilyPlanning;
import com.ali.mirachannel.utility.MiraConstants;

/**class to draw family planning circles*/
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class FamilyPlaningCircle extends Activity {
	private FamilyPlanning planning;
	private CustomViewFamily customViewFamily;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family_planing_circle);
		setupActionBar();
		String title= MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"परिवार नियोजन चक्र ":"Family Planning Circle";
		setTitle(title);
		Intent intent = getIntent();
		String name=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नाम ":"Name  ";
		String date=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख ":"Date  ";
		planning =(FamilyPlanning) intent.getExtras().getSerializable(FamilyPlanning.class.toString());
		((TextView)findViewById(R.id.txt_fp_name)).setText(name+planning.getName());
		((TextView)findViewById(R.id.txt_fp_date)).setText(date+planning.getLmcDate());
		customViewFamily = (CustomViewFamily) findViewById(R.id.customViewFamily1);
		customViewFamily.setCurrrent_day(getCurrnetDay()%33);
		customViewFamily.setLmc_days(planning.getCycleDays());
		findViewById(R.id.button_calendar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(FamilyPlanning.class.toString(), planning);
				intent.setClass(v.getContext(), FamilyCalendar.class);
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * @return number of days from a given date
	 */
	private int getCurrnetDay() {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");	
		Calendar calendar = Calendar.getInstance();
		String currentDate = dateformat.format(calendar.getTime());	
		
		int days = daysBetween(planning.getLmcDate(), currentDate);
		return days;
	}
	
	
    /**
     * @param currentDate current date of the system
     * @param start_date start date of lmc
     * @return days difference between dates
     */
    private int daysBetween(String currentDate, String start_date) {
		
    	Calendar calendar1 = Calendar.getInstance(); 
		Calendar calendar2 = Calendar.getInstance();
		
		String s1[] = currentDate.split("/");
		String s2[] = start_date.split("/");
		
		Date dat1 = new Date(Integer.parseInt(s1[2]),Integer.parseInt(s1[1]),Integer.parseInt(s1[0]));
		Date dat2 = new Date(Integer.parseInt(s2[2]),Integer.parseInt(s2[1]),Integer.parseInt(s2[0]));
		
		calendar1.setTime(dat1);
		calendar2.setTime(dat2);		
		
		long millisec_1 = calendar1.getTimeInMillis(); 
		long millisec_2 = calendar2.getTimeInMillis(); 
		long diff = millisec_1 - millisec_2; 
		long diffDays = diff / (24 * 60 * 60 * 1000); 
		return (int) Math.abs(diffDays);
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
		getMenuInflater().inflate(R.menu.family_planing_circle, menu);
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
