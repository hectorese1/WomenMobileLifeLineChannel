package com.ali.mirachannel;

import android.annotation.SuppressLint;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.model.FamilyPlanning;
import com.ali.mirachannel.utility.MiraConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * 
 * @author sherali_ali@yahooo.com
 *
 */
/**class display the messages for adolecent girls*/
public class AdolescentGirl extends Activity {
		
	/**
	 * mainMenuEng -- main menu items array
	 * storyEng -- main menu items array
	 * gamesEng -- main menu items array
	 * learnEng -- main menu items array
	 * familyPlanningMenuEng -- main menu items array
	 * mainMenuEng -- main menu items array
	 */
	String[] mainMenuEng = {"Learning for Girls", "Stories for Girls", "Games for Girls"};
    String[] mainMenuHnd = {"किशोरी ज्ञान", "किशोरी कहनियाँ", "किशोरी खेल"}; 
	
    String[] storyEng = {"Family Choices"};
    String[] storyHnd = {"फैमिली चोइसिस"};
    String[] gamesEng = {"Worm Attack", "9 minutes", "Good touch - Bad touch"};
    String[] gamesHnd = {"वर्म अटैक", "9 मिनट्स", "गुड टच - बैड टच"};
    String[] learnEng = {"Menstrual hygiene"};
    String[] learnHnd = {"मासिक धर्म स्वच्छता"};
    
    String[] mainMenuFPEng = {"Know Your Safe Days", "FP Methods"};
    String[] mainMenuFPHnd = {"Know Your Safe Days", "FP Methods"};
    
    public String[] familyPlanningMenuEng = {"Register", "View Record", "Demo Tour"};
    public String[] familyPlanningMenuHindi = {"पंजीकरण", "रिकॉर्ड देखें", "नमूना देखें"};
    public int[]familyPlanningMenuIconId={R.drawable.registration_small,R.drawable.view_record_small,R.drawable.demo_tour_small};
    ListView listView;
    String[] strings;
    MyAdapter adapter;
    DatabaseHelper databaseHelper;
	String keyToidentify="";
	String [] subscene;
	String startTimeStamp="N/A",lastTimeStamp="N/A";
    
	/**method will create list for items and on click method will change the display content\
	 * final item will show related item description*/

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActionBar().setTitle("Allah");
		setContentView(R.layout.activity_adolescent_girl);
		databaseHelper = DatabaseHelper.newInstance(this);

		startTimeStamp=timeStamp();

		listView = (ListView) findViewById(R.id.listView_adolecent);
		Intent intent = getIntent();
		keyToidentify=intent.getStringExtra("keyToCheck");
        System.out.println("keyToCheck in Adolescent...."+keyToidentify);
		if(intent.hasExtra("adolecent")){
			getActionBar().setTitle("Family Planning");
			strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlanningMenuHindi:familyPlanningMenuEng;
		}else if(intent.hasExtra("gyan")){
			getActionBar().setTitle("Adolescent Girl Health");
			strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?learnHnd:learnEng;
            familyPlanningMenuIconId[0]=R.drawable.masik_dharam_swachhta;
		}
        else if(intent.hasExtra("kahaniyan")){
			getActionBar().setTitle("Adolescent Girl Health");
            strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?storyHnd:storyEng;
        }
        else if(intent.hasExtra("khel")){
			getActionBar().setTitle("Adolescent Girl Health");
            strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?gamesHnd:gamesEng;
        }
		if(keyToidentify.equals("safe_day")){
			if(MySharedPreference.getStringValue(this,MiraConstants.FP_Prefrences,keyToidentify).equals("0")){
               savePref();
			}
			else{
				String s1=MySharedPreference.getStringValue(this,MiraConstants.FP_Prefrences,keyToidentify);
				subscene=s1.split("#");
				int a=Integer.parseInt(subscene[0]) + 1;
				System.out.println("Bismillah In Adolescent."+a+ "  "+s1.substring(1));
				String n=String.valueOf(a)+s1.substring(1);
				System.out.println("Bismillah In Adolescent."+n);
				MySharedPreference.saveStringInPreference(this, MiraConstants.FP_Prefrences, keyToidentify, n);
			}
		}
		else if(keyToidentify.equals("learning_girl"))
		{
			if(MySharedPreference.getStringValue(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify).equals("0")){
//				savePref();
			}
		}
		else if(keyToidentify.equals("stories_girl"))
		{
			if(MySharedPreference.getStringValue(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify).equals("0")){
				savePref();
			}
		}
		else if(keyToidentify.equals("games_girl"))
		{
			if(MySharedPreference.getStringValue(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify).equals("0")){
				savePref();
			}
		}

		adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
   //--------------------------Not In used-------------------------------------------------------------
				if(strings==(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuHnd:mainMenuEng)){
					switch (position) {
					case 0:
						strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?learnHnd:learnEng;
						break;
					case 1:
						strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?storyHnd:storyEng;
						break;
					case 2:
						strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?gamesHnd:gamesEng;
						break;
					default:
						break;
					}
				}
                else if(strings==(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?learnHnd:learnEng)){
//					------------- Start Koshori Giyaan --------------------------------

					String title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"मासिक धर्म स्वच्छता":"Menstrual Hygiene";
					String message="मासिक धर्म के दौरान साफ़ और कीटाणु रहित रहने के लिए बहुत ज़रूरी है कि लड़कियाँ कुछ ज़रूरी स्वच्छता व्यवहारों को अपनायें।";
					String message1="To remain clean and germ free during menstruation, it is important for girls to adopt some important hygiene practices.";
					String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?message:message1;
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdolescentGirl.this);
                    builder.setTitle(title);
                    builder.setMessage(msg);
                    builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"आगे बढ़ें  ":"Next", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
							intent.putExtra("keyToCheck",keyToidentify);
                            intent.setClass(AdolescentGirl.this, AdolescentGirlFrag.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();


				}
  //------------------------------------Not In Used--------------------------------------------------------------------

  //----------------------------------Menu (Know your safe days & FP Methods------------------------------------------------------------
                 if(strings == (MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuFPHnd:mainMenuFPEng)){
					switch (position) {
					case 0:
						strings = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlanningMenuHindi:familyPlanningMenuEng;
						break;
					case 1:
						Intent intent = new Intent();
						intent.putExtra(FamilyPlanning.class.toString(), 1);
						intent.setClass(AdolescentGirl.this, AdolescentGirlFrag.class);
						startActivity(intent);
						break;

					default:
						break;
					}
				}

  //----------------------------- Registration Menu (After clicked on know your safe days)--------------------------------------------------------
                else if(strings ==( MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlanningMenuHindi:familyPlanningMenuEng)){
					switch (position) {
					case 0:
						AlertDialog.Builder builder = new AlertDialog.Builder(AdolescentGirl.this);
						builder.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"पंजीकरण":"Registration");
						LayoutInflater inflater = (LayoutInflater) AdolescentGirl.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						View view = inflater.inflate(R.layout.dialog_familypanning, null, false);
						final EditText edt_fp_name = (EditText) view.findViewById(R.id.edt_fp_name);
						final Spinner spn_fp_cycle = (Spinner) view.findViewById(R.id.spn_fp_cycle);
						final Button btn_fp_date = (Button) view.findViewById(R.id.btn_fp_date);
						Calendar calendarTemp = Calendar.getInstance();
						final String dateString = calendarTemp.get(Calendar.DAY_OF_MONTH)+"/"+(calendarTemp.get(Calendar.MONTH)+1)+"/"+calendarTemp.get(Calendar.YEAR);
						btn_fp_date.setText(MainActivity.serverDate);
						btn_fp_date.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Calendar cal = Calendar.getInstance(TimeZone.getDefault());
								DatePickerDialog pickerDialog = new DatePickerDialog(AdolescentGirl.this, new DatePickerDialog.OnDateSetListener() {

							        public void onDateSet(DatePicker view, int selectedYear,
							                int selectedMonth, int selectedDay) {
							        	
							            Calendar calendar = Calendar.getInstance();
							            calendar.set(Calendar.DATE, selectedDay);
							            calendar.set(Calendar.MONTH, selectedMonth);
							            calendar.set(Calendar.YEAR, selectedYear);							            
							            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							            btn_fp_date.setText(sdf.format(calendar.getTime()));
							        }
							    } , MiraConstants.serverYearInt,
					                    MiraConstants.serverMonthInt-1,MiraConstants.serverDayInt);
								pickerDialog.setCancelable(false);
								String dateString = MiraConstants.HINDI.equals(MiraConstants.HINDI)?"तारीख का चयन करें":"Select the date";
								pickerDialog.setTitle(dateString);
								String negative=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कैंसिल ":"Cancel";
								String positive=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जमा करें":"Submit";
								pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,positive,pickerDialog);
								pickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, pickerDialog);
								pickerDialog.show();
							}
						});
						builder.setView(view);
						builder.setNegativeButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कैंसिल":"Cancel", null);
						builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जमा करें ":"Submit", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								FamilyPlanning planning = new FamilyPlanning();
								planning.setName(edt_fp_name.getText().toString());
								String string = spn_fp_cycle.getSelectedItem().toString();
								planning.setCycleDays(Integer.parseInt(spn_fp_cycle.getSelectedItem().toString()));
								planning.setLmcDate(btn_fp_date.getText().toString());
								databaseHelper.insertFamiyPlanning(planning);
							}
						});
						builder.show();
						updatePref(1);
						break;
					case 1:
						final List<FamilyPlanning>list = databaseHelper.getFamilyPlanning("SELECT *FROM tabfamplan");
						AlertDialog.Builder builderView = new AlertDialog.Builder(AdolescentGirl.this);
						builderView.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"पंजीकरण":"View Records");
						final List<String>arr = new ArrayList<String>();
						for (int i = 0; i < list.size(); i++) {
							arr.add(list.get(i).getName());
						}
						ArrayAdapter<String>adapter = new ArrayAdapter<String>(AdolescentGirl.this, android.R.layout.simple_list_item_1, arr);
						builderView.setAdapter(adapter, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent();
								intent.putExtra(FamilyPlanning.class.toString(), list.get(which));
								intent.setClass(AdolescentGirl.this, FamilyPlaningCircle.class);
								startActivity(intent);
								Toast.makeText(AdolescentGirl.this, "Clic.. "+arr.get(which), Toast.LENGTH_SHORT).show();
							}													
						});

						builderView.show();
						updatePref(2);
						break;
					case 2:
						updatePref(3);
                        FamilyPlanning vd = new FamilyPlanning();
                        vd.setKeyId(12);
                        vd.setCycleDays(30);
                        vd.setName("Radha");
                        vd.setLmcDate("20/01/2012");

                        Intent intent = new Intent();
                        intent.putExtra(FamilyPlanning.class.toString(), vd);
                        intent.setClass(AdolescentGirl.this, FamilyPlaningCircle.class);
                        startActivity(intent);
                        break;
					default:
						break;
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		setupActionBar();
	}

	private String timeStamp(){
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String format = s.format(new Date());
		return format;
	}
	
	private void savePref(){
		if(keyToidentify.equals("safe_day")){
			String s="1#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(this,MiraConstants.FP_Prefrences,keyToidentify,s);
		}
//		if(keyToidentify.equals("learning_girl")){
//			String s="1#0#0#0";
//			MySharedPreference.saveStringInPreference(this,MiraConstants.FP_Prefrences,keyToidentify,s);
//		}
		if(keyToidentify.equals("stories_girl")){
			String s="1#0"+"#"+timeStamp()+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify,s);
		}
		if(keyToidentify.equals("games_girl")){
			String s="1#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify,s);
		}
	}


	private String updatePref(int position){
		String sfinal="N/A";
		String s1= MySharedPreference.getStringValue(this,MiraConstants.FP_Prefrences,keyToidentify);
		if(!s1.equals("0")){
			subscene=s1.split("#");
			String firstPart=s1.substring(0, position * 2);
			String secondPart=s1.substring(position * 2,s1.length()-19);
//              String secondPart=s1.substring(position * 2);
			String lastPart=s1.substring(s1.length()-19,s1.length());

			System.out.println("Lo ji cutt gayi Main string...."+firstPart+"    "+secondPart+"    "+lastPart);
			String [] qp=secondPart.split("#");

			int a=Integer.parseInt(qp[0]) + 1;
			lastPart=timeStamp();
			String n=String.valueOf(a)+secondPart.substring(1);
			System.out.println("Bismillah In NeoNatal."+n);
			sfinal=firstPart+n+lastPart;

			System.out.println("In the Upadte ...."  + sfinal);

			MySharedPreference.saveStringInPreference(this,MiraConstants.FP_Prefrences,keyToidentify,sfinal);
		}

		return sfinal;
	}

	private void updateFirstValue(String keyToidentify){

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
		getMenuInflater().inflate(R.menu.adolescent_girl, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 * @author Sher Ali
	 *
	 */
	class MyAdapter extends BaseAdapter{
		Context context;
		public MyAdapter(Context context) {
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return strings.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		class ViewHolder{
			ImageView imageView_adolecent;
			TextView txt_adolecent;
			public ViewHolder(View view) {
				imageView_adolecent = (ImageView) view.findViewById(R.id.imageView_adolecent);
				txt_adolecent = (TextView) view.findViewById(R.id.txt_adolecent);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = null;
			if(view==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.list_item, parent, false);
				holder = new ViewHolder(view);
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
            holder.imageView_adolecent.setImageResource(familyPlanningMenuIconId[position]);
			holder.txt_adolecent.setText(strings[position]);
			return view;
		}		
	}
	
	@Override
	public void onBackPressed() {
//		if(strings==(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuHnd:mainMenuEng)||
//				strings == (MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuFPHnd:mainMenuFPEng)){
//			super.onBackPressed();
//		}else if(strings == (MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlanningMenuHindi:familyPlanningMenuEng)){
//			strings=(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuFPHnd:mainMenuFPEng);
//			adapter.notifyDataSetChanged();
//		}else{
//			strings=(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuHnd:mainMenuEng);
//			adapter.notifyDataSetChanged();
//		}

        super.onBackPressed();
	}
}
