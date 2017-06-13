package com.ali.mirachannel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyInfo extends ActionBarActivity implements OnPageChangeListener,OnClickListener {
	ViewPager viewPager;
	MyPageAdapter pageAdapter;
//	String womanid;
//	String pregid;
//	String womenName;
	DatabaseHelper databaseHelper;
	int selctIndex,weeknumber;
	WomenDtl womenDtl;
    FragmentManager manager;
    private ImageButton back,next;
    private int size=0;
	String compostieKey="";
	String startTimeStamp="N/A",lastTimeStamp="N/A";


//	public static HashMap<String,HashMap<String,Integer>> hashMap;
//	public static HashMap<String,Integer>hashMap1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.weekly_infonew);
		
		womenDtl = (WomenDtl) getIntent().getExtras().getSerializable(WomenDtl.class.getName());
		startTimeStamp=timeStamp();
//        hashMap=new HashMap<String, HashMap<String, Integer>>();
//		hashMap1=new HashMap<String, Integer>();
//		hashMap1.put("00_Screne",1);
//		hashMap1.put("0_Screne",0);
//		hashMap1.put("1_Screne",0);
//		hashMap1.put("2_Screne", 0);
//		hashMap1.put("3_Screne", 0);


		
//		int weeknumber = MiraConstants.calculateWeekNumber(womenDtl.getLmcDate());
		
//		womanid = getIntent().getExtras().getString("womanid");
//		pregid = getIntent().getExtras().getString("pregid");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
//	    womenName = getIntent().getExtras().getString("womenname");
	    
//	    womenDtl = (WomenDtl) getIntent().getExtras().getSerializable(WomenDtl.class.getName());
	    
		viewPager = (ViewPager) findViewById(R.id.viewpager);		
		
		ImageButton button_medicare = (ImageButton) findViewById(R.id.pre_madicare);
		button_medicare.setOnClickListener(this);
		ImageButton button_diet = (ImageButton) findViewById(R.id.pre_diet);
		button_diet.setOnClickListener(this);
		ImageButton button_does = (ImageButton) findViewById(R.id.pre_does);
		button_does.setOnClickListener(this);
		ImageButton button_donts = (ImageButton) findViewById(R.id.pre_donts);
		button_donts.setOnClickListener(this);

        back= (ImageButton) findViewById(R.id.back_btn);
        next= (ImageButton) findViewById(R.id.next_btn);


		try {
			preArrayList = getEventsFromAnXML(this);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 weeknumber = MiraConstants.calculateWeekNumber(womenDtl.getLmcDate());
//		hashMap.put(String.valueOf(weeknumber),hashMap1);
//		PrenatalWomenList.hashMap.put(womenDtl.getWomanId(),hashMap);
//		PrenatalWomenList.editor.putString(womenDtl.getWomanId(), hashMap.toString());
//		PrenatalWomenList.editor.commit();


		List<Fragment> fragments = getFragments(weeknumber);
        size=fragments.size();

		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pageAdapter);
		selctIndex = weeknumber;
		viewPager.setCurrentItem(weeknumber);
		compostieKey=womenDtl.getWomanId()+"_"+(weeknumber+1);
		System.out.println("weeklyInfo in weeklyInfo...."+selctIndex+"  "+weeknumber);

//		editor=sharedPreferences.edit();

		viewPager.setOnPageChangeListener(WeeklyInfo.this);
		System.out.println("viewPager.getCurrentItem() " + viewPager.getCurrentItem() + " Size " + size);

       // manager = getSupportFragmentManager();
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


		if(MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compostieKey).equals("0")&&(selctIndex==(weeknumber))){
           saveInpref();
		}
//		else{
//			updateInPref();
//		}
    }
    private int getItem(int i) {
		System.out.println("Weekly Info getItem......"+i);
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
    }
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);  
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	private List<Fragment> getFragments(int CurrWeek) {
		List<Fragment> fList = new ArrayList<Fragment>();

		int[] images = { R.drawable.w01_b, R.drawable.w02_b, R.drawable.w03_b,
				R.drawable.w04_b, R.drawable.w05_b, R.drawable.w06_b,
				R.drawable.w07_b, R.drawable.w08_b, R.drawable.w09_b,
				R.drawable.w10_b, R.drawable.w11_b, R.drawable.w12_b,
				R.drawable.w13_b, R.drawable.w14_b, R.drawable.w15_b,
				R.drawable.w16_b, R.drawable.w17_b, R.drawable.w18_b,
				R.drawable.w19_b, R.drawable.w20_b, R.drawable.w21_b,
				R.drawable.w22_b, R.drawable.w23_b, R.drawable.w24_b,
				R.drawable.w25_b, R.drawable.w26_b, R.drawable.w27_b,
				R.drawable.w28_b, R.drawable.w29_b, R.drawable.w30_b,
				R.drawable.w31_b, R.drawable.w32_b, R.drawable.w33_b,
				R.drawable.w34_b, R.drawable.w35_b, R.drawable.w36_b,
				R.drawable.w37_b, R.drawable.w38_b, R.drawable.w39_b,
				R.drawable.w40_b};
		for (int i = 0; i < 40; i++) {
			ViewFragment fragment = ViewFragment.newInstance(images[i%images.length],i,preArrayList.get(i),womenDtl.getWomanId(),womenDtl.getPregnentId(),womenDtl.getName(),CurrWeek,womenDtl.getLmcDate());
			fList.add(fragment);
		}
		return fList;
	}


             private void saveInpref(){
				 String s="1#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
                 MySharedPreference.saveStringInPreference(this,MiraConstants.PreNatal_Prefrences,compostieKey,s);

			 }
	private String timeStamp(){
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String format = s.format(new Date());
		return format;
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		selctIndex = arg0;
	}

	@Override
	public void onPageSelected(int arg0) {
		Log.v("MIRA", "onPageSelected   id -    " + arg0);
		System.out.println("pppppppp......." + arg0);
		selctIndex  = arg0;
		compostieKey=womenDtl.getWomanId()+"_"+(weeknumber+1);
//		if(weeknumber!=selctIndex){
///			MySharedPreference.clearPrefernce(this,MiraConstants.PreNatal_Prefrences);
//		}
			if(MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compostieKey).equals("0"))
			{
				if((selctIndex==(weeknumber))){
				saveInpref();
			}

		}
		
		if(MiraConstants.MEDIA_PLAYER!=null){
			if(MiraConstants.MEDIA_PLAYER.isPlaying())
			MiraConstants.MEDIA_PLAYER.stop();
		}
	}
//    @Override
//    public void getprenatalListElemntDemo() {
//        PrenatalMenu prenatalMenu = new PrenatalMenu();
//        FragmentTransaction transaction1  = manager.beginTransaction();
//        manager.popBackStackImmediate("demowomenList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        manager.popBackStackImmediate("prenatalMenu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//        transaction1.commit();
//
//        FragmentTransaction transaction  = manager.beginTransaction();
//        transaction.replace(R.id.mainContent, prenatalMenu, "prenatalMenu");
//        transaction.addToBackStack("prenatalMenu");
//        transaction.commit();
//    }
	@Override
	public void onClick(View v) {
		String header;
		String descrp;
		int image;
		
		Intent intent = new Intent();
		compostieKey=womenDtl.getWomanId()+"_"+(selctIndex+1);
		Map<String, String>map = preArrayList.get(this.selctIndex);
		ArrayList<String>arrayList = new ArrayList<String>(map.values());
		Bundle bundle= new Bundle();
		intent.putStringArrayListExtra("list", arrayList);
		intent.putExtra("womenDetails", womenDtl);
		intent.putExtra("CompositeKey", compostieKey);
		bundle.putSerializable("map", (Serializable) map);
		bundle.putInt("selctIndex", selctIndex);
		System.out.println("SelectIndex In WeeklyInfo...."+selctIndex);
		intent.putExtra("bundle", bundle);
		intent.setClass(WeeklyInfo.this, WeeklyInfoIndivid.class);
		if(MiraConstants.MEDIA_PLAYER!=null){
			if(MiraConstants.MEDIA_PLAYER.isPlaying())
			MiraConstants.MEDIA_PLAYER.stop();
		}
		
		switch (v.getId()) {
		
		case R.id.pre_madicare:
			header = "MEDICALCARE";
			image = R.drawable.pre_0;
			;
			intent.putExtra("index", 0);
//			hashMap1.put("0_Screne", 1);
//			hashMap.put(String.valueOf(weeknumber),hashMap1);
//			PrenatalWomenList.hashMap.put(womenDtl.getWomanId(),hashMap.toString());
			startActivity(intent);
			break;
			
		case R.id.pre_diet:			
			header = "DIET";
			intent.putExtra("index", 1);
			image = R.drawable.pre_2;
//			intent.putExtra("womenDetails", womenDtl);
//			hashMap1.put("1_Screne",1);
//			hashMap.put(String.valueOf(weeknumber),hashMap1);
//			PrenatalWomenList.hashMap.put(womenDtl.getWomanId(),hashMap.toString());
			startActivity(intent);
			break;

		case R.id.pre_does:
			header = "DOS";
			intent.putExtra("index", 2);
			image = R.drawable.pre_3;
//			intent.putExtra("womenDetails", womenDtl);
//			hashMap1.put("2_Screne",1);
//			hashMap.put(String.valueOf(weeknumber),hashMap1);
//			PrenatalWomenList.hashMap.put(womenDtl.getWomanId(),hashMap.toString());
			startActivity(intent);
			break;

		case R.id.pre_donts:
			header = "DONTS";
			intent.putExtra("index", 3);
			image = R.drawable.pre_4;
//			intent.putExtra("womenDetails", womenDtl);
//			hashMap1.put("3_Screne",1);
//			hashMap.put(String.valueOf(weeknumber),hashMap1);
//			PrenatalWomenList.hashMap.put(womenDtl.getWomanId(),hashMap.toString());
			startActivity(intent);
			break;

//        case R.id.back_btn:
//             viewPager.setCurrentItem(getItem(-1), true);
//             break;
//
//        case R.id.next_btn:
//             if(viewPager.getCurrentItem()<(size-1)){
//                 //viewPager.setCurrentItem(getItem(+1), true);
//             }
//            break;

		default:
			header = "WEEK";
			descrp = "Descrp of WEEK ";
			image = R.drawable.pre_4;
			break;		
		}


	}



    ArrayList<HashMap<String, String>>preArrayList;
	private ArrayList<HashMap<String, String>> getEventsFromAnXML(Activity activity)throws XmlPullParserException, IOException {
		ArrayList<HashMap<String, String>> preArrayList = new ArrayList<HashMap<String,String>>();
		Resources res = activity.getResources();
		XmlResourceParser xpp = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?res.getXml(R.xml.wmc_msg_hnd):res.getXml(R.xml.wmc_msg_eng);
		xpp.next();
		int eventType = xpp.getEventType();
		String name = "sorry";
		String text = "sorry";
		HashMap<String, String>map= new HashMap<String, String>();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				name = xpp.getName();
				if(name.equals("WEEK")){
					map= new HashMap<String, String>();
				}
			}
			if (eventType == XmlPullParser.TEXT) {
				text = xpp.getText();
			}
			if (eventType == XmlPullParser.END_TAG) {
				if(xpp.getName().equals("WEEK")){
					preArrayList.add(map);
				}				
				if (name.equals("BABYGROWTH")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("MEDICALCARE")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("DIET")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("DOS")) {
					map.put(name, text);
					name = "";
					text="";
				}
				if (name.equals("DONTS")) {
					map.put(name, text);
					name = "";
					text="";
				}
			}
			eventType = xpp.next();
		}
		return preArrayList;
	}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("OnBackPressed....."+MiraConstants.PRENATAL_DEMO);
//        if(MiraConstants.PRENATAL_DEMO.equalsIgnoreCase("pre_demo")) {
//
//            Intent intent = new Intent();
//            intent.setClass(this, MainMenuActivity.class);
//            startActivity(intent);
//        }
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy WeeklyInfo....." );
	}
}
