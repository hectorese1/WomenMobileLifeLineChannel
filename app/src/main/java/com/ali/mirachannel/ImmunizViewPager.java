package com.ali.mirachannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.model.VaccinatedDtl;
import com.ali.mirachannel.model.VaccineDtl;
import com.ali.mirachannel.utility.MiraConstants;
import com.ali.mirachannel.utility.ShowAlert_Msg;
import com.fz.mirachannel.DummyImmuModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**show the details of vaccine along with details*/
@SuppressLint("SimpleDateFormat")
public class ImmunizViewPager extends ActionBarActivity implements OnPageChangeListener,OnClickListener{
	
	ViewPager viewPager;	
	ImageButton vaccineBtn;
	ImageButton medicalBtn;
	ImageButton precatnBtn;
	MyPageAdapter pageAdapter;
	ArrayList<String> list;
    private int status=0;
    boolean hideMissed=false;
	String childId;
	String vaccineName;
	DatabaseHelper databaseHelper;
	HashMap<String, String>hashMap;
	Map<String, Map<String, String>>detailMap;
    boolean isdemo;

    private ImageButton back,next;
    private int size=0;
    LinearLayout layout;
   // HashMap<String,Map<String,VaccineDtl>>vaccine_dtl_map_send;
    HashMap<String,VaccineDtl>vaccine_dtl_map_send_temp;
    ChildDtl childDetail;
   // private boolean isDateDilogShow=true;
    private VaccineDtl vaccineDtl;
    View myView=null;
    String dateRangeMessage="",compositeKeyImmuz="N/A";
   // private HashMap<String ,VaccineDtl>doseTakenMap;

    private Map<String ,String>dateRange=new HashMap<String, String>();
     private String vac_name="NA",start_Date="NA",end_Date="NA";


    public static int[][] VaccineIcons = {
            {R.drawable.vac_0, R.drawable.vac_01, R.drawable.vac_02},//0 Hepatitis_B0
            {R.drawable.vac_0, R.drawable.vac_03, R.drawable.vac_04},//1 OPV_0
            {R.drawable.vac_0, R.drawable.vac_08, R.drawable.vac_06},//2 BCG
            {R.drawable.vac_1, R.drawable.vac_03, R.drawable.vac_04},//3 OPV_1

            {R.drawable.vac_1, R.drawable.vac_01, R.drawable.penta},//4 PENTA-1

            {R.drawable.vac_2, R.drawable.vac_03, R.drawable.vac_04},//5 OPV_2

            {R.drawable.vac_2, R.drawable.vac_01, R.drawable.penta},//6 PENTA-2

            {R.drawable.vac_3, R.drawable.vac_03, R.drawable.vac_04},//7 OPV_3

            {R.drawable.vac_3, R.drawable.vac_01, R.drawable.penta},//8 PENTA-3

            {R.drawable.vac_4, R.drawable.vac_05, R.drawable.vac_09},//9 Measles
            {R.drawable.vac_5, R.drawable.vac_01, R.drawable.vac_07},//10 DPT_Booster
            {R.drawable.vac_6, R.drawable.vac_03, R.drawable.vac_04},//11 OPV_Booster
            {R.drawable.vac_7, R.drawable.vac_03, R.drawable.vac_010},//12 VITAMIN-A
            {R.drawable.vac_8, R.drawable.vac_05, R.drawable.vac_09}//13 Measles_Booster

//            {R.drawable.vac_4, R.drawable.vac_05, R.drawable.vac_09},//9 Measles
//            {R.drawable.vac_5, R.drawable.vac_01, R.drawable.vac_07},//10 DPT_Booster
//            {R.drawable.vac_6, R.drawable.vac_03, R.drawable.vac_04},//11 OPV_Booster
//            {R.drawable.vac_7, R.drawable.vac_03, R.drawable.vac_010},//12 VITAMIN-A
//            {R.drawable.vac_8, R.drawable.vac_05, R.drawable.vac_09}

    };

    private void setLayoutColor(int arg0){

        if(Integer.parseInt(list.get(arg0).toString().split("#")[1])==0){
            layout.setBackgroundColor(getResources().getColor(R.color.header2));
        }
        else if(Integer.parseInt(list.get(arg0).toString().split("#")[1])==1){
            layout.setBackgroundColor(getResources().getColor(R.color.header3));
        }
        else {
            layout.setBackgroundColor(getResources().getColor(R.color.header1));
        }
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.weekly_infoimz);

        layout = (LinearLayout) findViewById(R.id.layout_bg);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		databaseHelper = DatabaseHelper.newInstance(this);
        List<VaccinatedDtl>dtls=null;

		try {

			detailMap = getEventsFromAnXML(this);


		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		list = bundle.getStringArrayList("list");

        System.out.println("Status Value..."+status);
        for(int i=0;i<list.size();i++){
            System.out.println("Status Of Vac.."+Integer.parseInt(list.get(i).toString().split("#")[1]));
        }
		isdemo=bundle.getBoolean("boolean");
		childId = bundle.getString("childid");
        System.out.println("Child id in pager "+isdemo);
        vaccine_dtl_map_send_temp= (HashMap<String,VaccineDtl>)bundle.getSerializable("vacMapRule");
        System.out.println("VacMapRule In Pager "+vaccine_dtl_map_send_temp.size());
        childDetail=(ChildDtl)bundle.getSerializable("childDetail");
        compositeKeyImmuz=bundle.getString("compositeKeyImmu");



      if(!compositeKeyImmuz.equals("N/A"))
        {
          System.out.println("From Pager vaccine_dtl_map_send not "+vaccine_dtl_map_send_temp +" "+childDetail+" "+compositeKeyImmuz);
            if(MySharedPreference.getStringValue(this,MiraConstants.Immuniz_Prefrences,compositeKeyImmuz).equals("0")){
                savePref();
            }
            else{
                updatePref();
            }
      }
        else{
          System.out.println("From Pager vaccine_dtl_map_send  "+vaccine_dtl_map_send_temp +" "+childDetail+" "+compositeKeyImmuz);

      }



		String tag_name = "SELECT *FROM tabvaccinestatus WHERE childid='"+childId+"'";
        if(!MiraConstants.IMMUNIZATION_DEMO.equalsIgnoreCase("imu_demo")){
            dtls = databaseHelper.getvaccStatusByTag(tag_name);
            System.out.println("We are not in the dummyModel111111111111");

        }
        else{
            dtls = DummyImmuModel.getVaccinatedDtl();
            System.out.println("We are in the dummyModel111111111111");

        }

		hashMap = new HashMap<String, String>();
		for (VaccinatedDtl vaccinatedDtl : dtls) {
			hashMap.put(vaccinatedDtl.getName(), vaccinatedDtl.getDate());
            System.out.println("We are in the dummyModel"+vaccinatedDtl.getName()+"   "+vaccinatedDtl.getDate());
		}
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		List<Fragment> fragments = getFragments(list);

        size=fragments.size();

		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pageAdapter);
		 index = getIntent().getIntExtra("index", 0);
        status=Integer.parseInt(list.get(index).toString().split("#")[1]);
        System.out.println("Array List....." + list.size() + " index num.." + index + " Status.." + status);
        if(status==0)
        {
          hideMissed=true;
            System.out.println("hideMissed.."+hideMissed);
        }
        else{
            System.out.println("hideMissed.."+hideMissed);
        }

		viewPager.setCurrentItem(index); 
		viewPager.setOnPageChangeListener(ImmunizViewPager.this);
		
		vaccineBtn = (ImageButton) findViewById(R.id.pre_madicare);
		vaccineBtn.setOnClickListener(this);
		medicalBtn = (ImageButton) findViewById(R.id.pre_diet);
		medicalBtn.setOnClickListener(this);
		precatnBtn = (ImageButton) findViewById(R.id.pre_does);
		precatnBtn.setOnClickListener(this);

            vaccineName = list.get(index).toString().split("#")[0];
            System.out.println("Vaccine NAme  " + vaccineName);

//		vaccineName = list.get(index).toString().split("#")[0];
//        System.out.println("Vaccine NAme  " + vaccineName);

//        if(MiraConstants.IMMUNIZATION_DEMO.equalsIgnoreCase("imu_demo")){
//            vaccineBtn.setEnabled(false);
//            medicalBtn.setEnabled(false);
//            precatnBtn.setEnabled(false);
//        }

		if(Integer.parseInt(list.get(index).toString().split("#")[1])==2 ||hashMap.containsKey(vaccineName))
		vaccineBtn.setVisibility(View.GONE);


        back= (ImageButton) findViewById(R.id.back_btn);
        next= (ImageButton) findViewById(R.id.next_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true);

            }
        });
       // doseTakenMap=new HashMap<String, VaccineDtl>();

        System.out.println("Today date "+MiraConstants.getDateTime());
        if(Integer.parseInt(list.get(index).toString().split("#")[1])!=2 ){
            initializeMap();
            chk_Vac_Rules();
        }


    }

    private String timeStamp(){
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String format = s.format(new Date());
        return format;
    }


    private void savePref(){
        String s="1#0#0"+"#"+timeStamp()+"#"+timeStamp();
        MySharedPreference.saveStringInPreference(this,MiraConstants.Immuniz_Prefrences,compositeKeyImmuz,s);
    }

    private void updatePref(){
        String s1=MySharedPreference.getStringValue(this,MiraConstants.Immuniz_Prefrences,compositeKeyImmuz);
        if(!s1.equals("0")){
            String [] s=s1.split("#");
            int a=Integer.parseInt(s[0]) + 1;
            System.out.println("Bismillah In Adolescent."+a+ "  "+s1.substring(1));
            String n=String.valueOf(a)+s1.substring(1);
            System.out.println("Bismillah In Adolescent."+n);
            MySharedPreference.saveStringInPreference(this, MiraConstants.Immuniz_Prefrences, compositeKeyImmuz, n);
        }
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {

        if(viewPager.getCurrentItem()!=0){
            back.setVisibility(View.VISIBLE);
        }
        else{
            back.setVisibility(View.INVISIBLE);
        }

        if(viewPager.getCurrentItem()==size-1){
            next.setVisibility(View.INVISIBLE);
        }
        else{
            next.setVisibility(View.VISIBLE);
        }


       // isDateDilogShow=true;
    }

    /////////////////////////////Faisal//////////////////////////////////////////////////////////////////

        private void showDialog(String title,String messege) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImmunizViewPager.this);
        builder.setTitle(title);
        builder.setMessage(messege);
            String Option1=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख अपडेट ":"Update Date";
            String Option2=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"छूटा हुआ टीका ":"Missed Date";
        builder.setPositiveButton(Option1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                showAletDatePicker(myView);
            }
        }) .setNegativeButton(Option2, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button
//                dialog.cancel();
                if(hideMissed==true) {
                    OnMissedClicked();
                    System.out.println("onMissedClicked...."+1);
                }
                else{
                    String alrt=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?" अलर्ट ":"ALERT";
                    String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"अभी यह टीका छूटी हुई खुराक नहीं माना जा सकता":"Currently this vaccine cannot be considered as missed dose";
                    AlertDialog.Builder dialog12 = myAlertDialog(1,alrt,msg);
                    dialog12.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    dialog12.show();

//                    Toast.makeText(getApplicationContext(),"You can't missed dose..",Toast.LENGTH_SHORT).show();
                }
            }

        });
        builder.show();

    }
    /////////////////////////////Faisal//////////////////////////////////////////////////////////////////

    /////////////////////////////Faisal//////////////////////////////////////////////////////////////////

    private void OnMissedClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImmunizViewPager.this);
        String title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"छूटे टीके की अपडेट":"Missed Status ";
        String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"क्या आप अवश्य  ही इसे अपडेट करना चाहते हैं?":"Are You Sure?";
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"हाँ ":"YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
                showMissPicker();
            }
        }) .setNegativeButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"ना ":"NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button
//                dialog.cancel();
                dialog.cancel();
            }

        });
        builder.show();

    }


    /////////////////////////////Faisal//////////////////////////////////////////////////////////////////

     private boolean updateMap(String date){
//         vaccine_dtl_map_send_temp.put(vaccineName,vaccineDtl);
        if(!vaccineDtl.getVaccineDepend().equals("0")){
            VaccineDtl vaccineDtl1=vaccine_dtl_map_send_temp.get(vaccineDtl.getVaccineDepend());

            if(!vaccineDtl1.getVaccineTakenDate().equals("NA")){

                String doseTakenDate=vaccineDtl1.getVaccineTakenDate();
                int diff=vaccineDtl.getDiffDay();
                String vac_date=getDate(diff,doseTakenDate);
                String end_date=getDate(vaccineDtl.getEndDay(),childDetail.getDob());
                System.out.println("dates Values in upDateMap1111...."+date+"   "+vac_date+"    "+end_date+"   "+vaccineDtl.getEndDay());

                return  checkDatesRange(date,vac_date,end_date);
            }
        }
         else{
            VaccineDtl vaccineDtl1=vaccine_dtl_map_send_temp.get(vaccineName);
            String start_date=getDate(vaccineDtl1.getStartDay(),childDetail.getDob());
            String end_date=getDate(vaccineDtl1.getEndDay(),childDetail.getDob());
            System.out.println("dates Values in upDateMap2222...."+date+"   "+start_date+"    "+end_date+"   "+vaccineDtl.getEndDay());
            return checkDatesRange(date,start_date,end_date);
        }


         System.out.println("Update Map "+vaccine_dtl_map_send_temp);
         return  false;
     }

	private void chk_Vac_Rules(){
        VaccineDtl vaccineDtl1=vaccine_dtl_map_send_temp.get(vaccineName);
        String vac_dependent=vaccineDtl1.getVaccineDepend();

        if(vac_dependent.equals("0")){
            start_Date=getActualDate(vaccineDtl1.getStartDay(),childDetail.getDob());

        }

        else{
            VaccineDtl vaccineDtl2=vaccine_dtl_map_send_temp.get(vaccineDtl1.getVaccineDepend());
            String takenDate=vaccineDtl2.getVaccineTakenDate();

            if(!takenDate.equals("NA")) {
                int daysDiff = vaccineDtl1.getDiffDay();
                String actualDate = getDate(daysDiff, takenDate);

                String testEndDate=getDate(vaccineDtl1.getEndDay(),childDetail.getDob());

               // String endDate = getDate(vaccineDtl1.getStartDay(), actualDate);
                start_Date=actualDate;
                  if((getDate(actualDate).before(getDate(testEndDate)))){

                      if(checkDatesRange(MiraConstants.getDateTime(),actualDate,testEndDate)){
                          vaccineBtn.setVisibility(View.VISIBLE);
                      }
                      else{
                          vaccineBtn.setVisibility(View.GONE);
                      }
                  }
                else{
                      vaccineBtn.setVisibility(View.GONE);
//                      if(checkDatesRange(MiraConstants.getDateTime(),actualDate,testEndDate)){
//                          vaccineBtn.setVisibility(View.VISIBLE);
//                      }
                  }


            }

            else{
                start_Date=getActualDate(vaccineDtl1.getStartDay(),childDetail.getDob());

                VaccineDtl vaccineDtl3=vaccine_dtl_map_send_temp.get(vaccineDtl1.getVaccineDepend());
                if(vaccineDtl3.getVaccineTakenDate().equals("NA")){
                    vaccineBtn.setVisibility(View.GONE);
                }
                dateRangeMessage="Please Vaccinate "+vac_dependent +" first.";
            }


        }
        String dateRange=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख का चुनाव":"Date Should be between ";
        String and=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"के बीच में ही करें ":" ";
        dateRangeMessage=dateRange+ start_Date+"--"+
                getActualDate(vaccineDtl1.getEndDay(), childDetail.getDob())+and;
    }
    private boolean checkDatesRange(String testDate,String startDate,String endDate){
        System.out.println("Todays date in checkDatesRange..."+testDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date=null,date1=null,date2=null;
        try {
            date =  formatter.parse(testDate);
            date1 = formatter.parse(startDate);

            Calendar c = Calendar.getInstance();
            c.setTime(date1); // Now use today date.
            c.add(Calendar.DATE, -1); // substracting 1 days
            date1 = formatter.parse(formatter.format(c.getTime()));

            date2 = formatter.parse(endDate);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date2); // Now use today date.
            c1.add(Calendar.DATE, 1); // Adding 1 days
            date2 = formatter.parse(formatter.format(c1.getTime()));

            System.out.println("Choosen date "+date+"  Two dates "+date1 +" "+date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Todays date in checkDatesRange..value."+(date.before(date1) && date.after(date2)));
        return date.after(date1) && date.before(date2);

    }

    private Date getDate(String Date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = formatter.parse(Date);
            return date;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  new Date();
    }

    private String getDate(int daysAdd,String baseDate){
       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       try {

           Date date = formatter.parse(baseDate);
           System.out.println(date);
           System.out.println(formatter.format(date));


           Calendar c = Calendar.getInstance();
           c.setTime(date); // Now use today date.
           c.add(Calendar.DATE, daysAdd); // Adding 5 days

           String output = formatter.format(c.getTime());
           System.out.println(output);

           return  output;
       } catch (Exception e) {
           e.printStackTrace();

       }
       return  "";
   }

    private void updateTimeStamp(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String format=sdf.format(new Date());
        System.out.println("UpdateTimeStamp in ImmunizViewPager....."+format);

    }

    private String getActualDate(int daysAdd,String baseDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {

            Date date = formatter.parse(baseDate);
            System.out.println(date);
            System.out.println(formatter.format(date));


            Calendar c = Calendar.getInstance();
            c.setTime(date); // Now use today date.
            c.add(Calendar.DATE, daysAdd); // Adding 5 days

            String output = formatter.format(c.getTime());
            System.out.println(output);

            Date formateddate=formatter.parse(output);
            Calendar c1 = Calendar.getInstance();


            String formattedDate = formatter.format(c1.getTime());
            Date selectedDate=formatter.parse(formattedDate);

            if(formateddate.after(selectedDate)){
                output=formatter.format(c1.getTime());
            }
            return  output;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return  "";
    }

    private String getActualDate_F(int daysAdd,String baseDate){
        String dt = baseDate;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, daysAdd);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String output = sdf1.format(c.getTime());
        return output;
    }

	private List<Fragment> getFragments(ArrayList<String>list) {
		List<Fragment> fList = new ArrayList<Fragment>();
        System.out.println("Size....."+list.size());
		for (int i = 0; i < list.size(); i++) {

			Bundle bundle = new Bundle();
			bundle.putString("Vaccinestatus", list.get(i));
            bundle.putInt("image", VaccineIcons[i][0]);
			bundle.putInt("selectIndex", i);
            String vaccName = list.get(i).toString().split("#")[0];


			detailMap.get(vaccName);
			bundle.putSerializable("map", (Serializable) detailMap.get(vaccName));
            System.out.println("vacname  "+vaccName);
			ImmunizViewFragment fragment = ImmunizViewFragment.newInstance(bundle);
			fList.add(fragment);
			
		}
		return fList;
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	private int index;
	@Override
	public void onPageSelected(int arg0) {
     System.out.println("onPageSelected..."+Integer.parseInt(list.get(arg0).toString().split("#")[1]));
        if(Integer.parseInt(list.get(arg0).toString().split("#")[1])==0){
            hideMissed=true;
            System.out.println("hideMissed.."+hideMissed);
        }
        else{
            hideMissed=false;
            System.out.println("hideMissed.."+hideMissed);
        }

		vaccineName = list.get(arg0).toString().split("#")[0];
//        System.out.println("onPageScrolled "+vaccineName);
        if(Integer.parseInt(list.get(arg0).toString().split("#")[1])==1){
            compositeKeyImmuz=childDetail.getChildId()+"_"+vaccineName;
            System.out.println("onPageScrolled "+vaccineName+"    "+compositeKeyImmuz);
            if(MySharedPreference.getStringValue(this,MiraConstants.Immuniz_Prefrences,compositeKeyImmuz).equals("0")){
                savePref();
            }
            else{
                updatePref();
            }
        }
		if(Integer.parseInt(list.get(arg0).toString().split("#")[1])==2||hashMap.containsKey(vaccineName) )
			vaccineBtn.setVisibility(View.GONE);
		else{
            vaccineBtn.setVisibility(View.VISIBLE);
            chk_Vac_Rules();
        }

		index = arg0;
		System.out.println("index value..."+index);

		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}



	}

    private void initializeMap(){
        vaccineDtl=vaccine_dtl_map_send_temp.get(vaccineName);
    }



	boolean isSubmit = false;
	@Override
	public void onClick(View v) {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("image1", VaccineIcons[index][1]);
		bundle.putInt("image2", VaccineIcons[index][2]);
		bundle.putInt("indexmain", index);

		bundle.putSerializable("map", (Serializable) detailMap.get(vaccineName));

		intent.putExtra("bundle", bundle);
		intent.setClass(ImmunizViewPager.this, ImmunizViewIndivd.class);
		
		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}
		
		switch (v.getId()) {
		case R.id.pre_madicare://vaccine
            myView=v;
            if(Integer.parseInt(list.get(index).toString().split("#")[1])!=2 ||!hashMap.containsKey(vaccineName)){
                initializeMap();

                ///////////////////////////Faisal/////////////////////////////
//                showDialog("",dateRangeMessage);

                String Title=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख अपडेट/ छूटे टीके की अपडेट":"Update Date/Missed Dose";
                showDialog("",Title);

                ///////////////////////////Faisal/////////////////////////////

            }

//            chk_VaccineRules(vaccineDtl.getStartDay());

			break;
		case R.id.pre_diet:
			intent.putExtra("index", 0);
            intent.putExtra("compositeKeyImmu",compositeKeyImmuz);
            startActivity(intent);
			break; 
		case R.id.pre_does:
			intent.putExtra("index", 1);
            intent.putExtra("compositeKeyImmu",compositeKeyImmuz);
			startActivity(intent);
			break;
		default:
			break;
		}
	}



    /////////////////////////////Faisal//////////////////////////////////////////////////////////////////

		private void showMissPicker(){
            String dateMiss="01/01/1900";
            Toast.makeText(getApplicationContext(),dateMiss+" " + childId + "  "+ vaccineName,Toast.LENGTH_SHORT).show();
            VaccinatedDtl dtl = new VaccinatedDtl();
            dtl.setChildId(childId);
            dtl.setName(vaccineName);
            dtl.setDate(dateMiss);
            dtl.setUpload("0");
            if(!MiraConstants.IMMUNIZATION_DEMO.equalsIgnoreCase("imu_demo")){
                databaseHelper.insetVaccineStatusTable(dtl);
            }
            hashMap.put(dtl.getName(), dtl.getDate());
            vaccineDtl.setVaccineTakenDate(dateMiss);

            //doseTakenMap.put(vaccineName,vaccineDtl);//by fz
            vaccine_dtl_map_send_temp.put(vaccineName, vaccineDtl);



            vaccineBtn.setVisibility(View.GONE);
        }

    /////////////////////////////Faisal//////////////////////////////////////////////////////////////////

	private void showAletDatePicker(View view) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
//        Date date1=new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            date1=sdf.parse(MainActivity.serverDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        cal.setTime(date1);
       DatePickerDialog pickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	        	
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(Calendar.DATE, selectedDay);
	            calendar.set(Calendar.MONTH, selectedMonth);
	            calendar.set(Calendar.YEAR, selectedYear);
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            String date = sdf.format(calendar.getTime());
                System.out.println("Selected Date Is here...."+date);

               boolean b=true;
                if(Integer.parseInt(list.get(index).toString().split("#")[1])!=-1 ||!hashMap.containsKey(vaccineName)){
                  b= updateMap(date);
                }


	        	if(!isSubmit && b){

//					if (cal.before(calendar) || (cal.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
//									&& cal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && cal
//									.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))) {
                        System.out.println("Immunization isSubmit.....");
                        Toast.makeText(getApplicationContext(),date+" " + childId + "  "+ vaccineName, Toast.LENGTH_SHORT).show();
                        VaccinatedDtl dtl = new VaccinatedDtl();
                        dtl.setChildId(childId);
						dtl.setName(vaccineName);
						dtl.setDate(date);
						dtl.setUpload("0");
                        if(!MiraConstants.IMMUNIZATION_DEMO.equalsIgnoreCase("imu_demo")){
                            databaseHelper.insetVaccineStatusTable(dtl);
                        }
                        hashMap.put(dtl.getName(), dtl.getDate());
                        vaccineDtl.setVaccineTakenDate(date);

                        //doseTakenMap.put(vaccineName,vaccineDtl);//by fz
                        vaccine_dtl_map_send_temp.put(vaccineName,vaccineDtl);



						vaccineBtn.setVisibility(View.GONE);
//					}
//    		else
//                    {
//                        String dateRange=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कृप्या  ":"Please select between ";
//                        String and=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"   के बीच में चुनाव करें ":" ";
////                        Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
//                        AlertDialog.Builder builder= ShowAlert_Msg.alert(ImmunizViewPager.this, 1, "Alert..!!",dateRange+start_Date+" - "+getActualDate(vaccineDtl.getEndDay(),childDetail.getDob())+" "+and
//                        );
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.show();
//                    }
//    			Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
    	}
               else
                    {
                        String dateRange=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कृप्या  ":"Please select between ";
                        String and=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"   के बीच में चुनाव करें ":" ";
//                        Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder= ShowAlert_Msg.alert(ImmunizViewPager.this, 1, "Alert..!!",dateRange+start_Date+" - "+getActualDate_F(vaccineDtl.getEndDay(),childDetail.getDob())+" "+and
                        );
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }

	        }
	    } ,MiraConstants.serverYearInt,
               MiraConstants.serverMonthInt-1,MiraConstants.serverDayInt);
        String negative=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कैंसिल ":"Cancel";
        String positive=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"जमा करें":"Submit";
        pickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,positive,pickerDialog);
        pickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                    //isDateDilogShow=true;
                }
            }
        });

        pickerDialog.setCancelable(false);

        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout layout=new LinearLayout(ImmunizViewPager.this);
        layout.setLayoutParams(params);
        TextView textView=new TextView(ImmunizViewPager.this);
        String dateRange=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"तारीख का चुनाव ":"Date Should be between ";
        String and=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"  के बीच में ही करें ":" ";
         dateRangeMessage=dateRange + start_Date + " -- " + getActualDate_F(vaccineDtl.getEndDay(),childDetail.getDob())+and;
        textView.setText(dateRangeMessage);
          textView.setTextSize(20);
        layout.addView(textView);

        pickerDialog.setCustomTitle(layout);
		pickerDialog.show();
	}


	private Map<String, Map<String, String>> getEventsFromAnXML(Activity activity)throws XmlPullParserException, IOException {
//		ArrayList<HashMap<String, String>> preArrayList = new ArrayList<HashMap<String,String>>();
		Map<String, Map<String, String>>detailMap = new HashMap<String, Map<String,String>>();
//		StringBuffer stringBuffer = new StringBuffer();
		Resources res = activity.getResources();
//		XmlResourceParser xpp = res.getXml(R.xml.vacc_detail_eng);
		XmlResourceParser xpp = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?res.getXml(R.xml.vacc_detail_hnd):res.getXml(R.xml.vacc_detail_eng);
		xpp.next();
		int eventType = xpp.getEventType();
		String name = "sorry";
		String text = "sorry";
		HashMap<String, String>map= new HashMap<String, String>();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				name = xpp.getName();

				if(name.equals("VaccId")){
					map= new HashMap<String, String>();
				}
			}
			
			if (eventType == XmlPullParser.TEXT) {
				text = xpp.getText();
                //System.out.println("Parsing "+name +" Value "+text);
			}
			if (eventType == XmlPullParser.END_TAG) {
				if(xpp.getName().equals("VaccId")){
//					preArrayList.add(map);
					detailMap.put(map.get("VaccName"), map);
				}				
				if (name.equals("VaccName")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("VaccMessage")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("AdministrationMSG")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("AboutMsg")) {
					map.put(name, text);
					name = "";
					text="";
				}			
			}
            System.out.println("In Xml...."+detailMap);
			eventType = xpp.next();
		}
		return detailMap;
	}

    public  AlertDialog.Builder  myAlertDialog(int cases,String title,String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ImmunizViewPager.this);
        switch (cases){
            case 1:
                dialog.setTitle(title);
                dialog.setMessage(message);
//                dialog.setIcon(R.drawable.anc_4);
                break;
            default:
                break;
        }

        return dialog;
    }

    String getVaccineHindi(String Vacname){
        String a="N/A";
        if(Vacname.equals("Hepatitis B-0")){
            a="हैपेटाइटिस बी-0";
        }
        else if(Vacname.equals("OPV-0")){
            a="ओ पि वि- 0";
        }
        else if(Vacname.equals("BCG")){
            a="बी सी जी ";
        }
        else if(Vacname.equals("OPV-1")){
            a="ओ पी वी-1";
        }
        else if(Vacname.equals("Pentavalent-1")){
            a="पेंटावेलेंट-1";
        }
        else if(Vacname.equals("OPV-2")){
            a="ओ पी वी -2";
        }
        else if(Vacname.equals("Pentavalent-2")){
            a="पेंटावेलेंट-2";
        }
        else if(Vacname.equals("OPV-3")){
            a="ओ पी वी-3";
        }else if(Vacname.equals("Pentavalent-3")){
            a="पेंटावेलेंट-3";
        }else if(Vacname.equals("Measles")){
            a="खसरा ";
        }else if(Vacname.equals("DPT Booster")){
            a="डी पी टी बूस्टर ";
        }else if(Vacname.equals("OPV Booster")){
            a="ओ पी वी बूस्टर";
        }else if(Vacname.equals("Vitamin-A")){
            a="विटामिन- ए ";
        }else if(Vacname.equals("Measles Booster")){
            a="खसरा बूस्टर";
        }

        return a;
    }
}
