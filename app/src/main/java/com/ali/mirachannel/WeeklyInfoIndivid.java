package com.ali.mirachannel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WeeklyInfoIndivid extends ActionBarActivity implements OnPageChangeListener,OnClickListener{
	
	private ActionBar actionBar;
	private ViewPager viewPager;
	private Map<String, String>map;
	private int index;
	private int selctIndex;
	private LinearLayout bottom_layout;
	private WomenDtl womenDtl;
	private String compositeKey;

    private ImageButton back,next;
    private int size=0;
	String[] subScene;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekly_info_individ);
		map = (Map<String, String>) getIntent().getExtras().getBundle("bundle").getSerializable("map");
		womenDtl=(WomenDtl) getIntent().getExtras().getSerializable("womenDetails");
		compositeKey= getIntent().getStringExtra("CompositeKey");
		System.out.println("onCreate compositeKey " + compositeKey);

//		if(!MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compositeKey).equals("0")){
//			upDatePref();
//		}

		selctIndex = getIntent().getExtras().getBundle("bundle").getInt("selctIndex");
		viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottom_layout=(LinearLayout)findViewById(R.id.bottom_layout);

		List<Fragment>fragments = getFragments();
        size=fragments.size();

		viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager(), fragments));
		viewPager.setOnPageChangeListener(this);	
		index = getIntent().getExtras().getInt("index");
		setCurrentFragment(index);
		actionBar = getSupportActionBar();		
		
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);						
				
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

	}
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

	private void upDatePref(){
		System.out.println("compositeKey "+compositeKey);
		String s1= MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compositeKey);
		System.out.println("upadte S1 form Pref..."+s1);

		if(!s1.equals("0")){
			subScene=s1.split("#");
		}
		if(!MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compositeKey).equals("0")) {
			subScene[index+1]="1";
			String value = subScene[0] + "#" + subScene[1] + "#" + subScene[2] + "#" + subScene[3] + "#" + subScene[4]+"#"+subScene[5]+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(getApplicationContext(), MiraConstants.PreNatal_Prefrences, compositeKey, value);
		}
	}

	private String timeStamp(){
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String format = s.format(new Date());
		return format;
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
	public void onBackPressed() {
		super.onBackPressed();
//		String value=subScene[0]+"#"+subScene[1]+"#"+subScene[2]+"#"+subScene[3]+"#"+subScene[4];
//		MySharedPreference.saveStringInPreference(getApplicationContext(),MiraConstants.PreNatal_Prefrences,compositeKey,value);
//		System.out.println("upadte value"+value);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		selctIndex = arg0;
	}

	@Override
	public void onPageSelected(int position) {
		index = position;
//		WeeklyInfo.hashMap1.put(position+"_Screne",1);
//		WeeklyInfo.hashMap.put(String.valueOf(selctIndex),WeeklyInfo.hashMap1);
//		PrenatalWomenList.hashMap.put(womenDtl.getWomanId(),WeeklyInfo.hashMap.toString());
		System.out.println("qqqqqqqq...."+position+"     ");


		if(!MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compositeKey).equals("0")) {
			String s1= MySharedPreference.getStringValue(this,MiraConstants.PreNatal_Prefrences,compositeKey);
			System.out.println("upadte S1 form Pref..."+s1);

			if(!s1.equals("0")){
				subScene=s1.split("#");
			}
			subScene[index+1]="1";
			String value = subScene[0] + "#" + subScene[1] + "#" + subScene[2] + "#" + subScene[3] + "#" + subScene[4]+"#"+subScene[5]+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(getApplicationContext(), MiraConstants.PreNatal_Prefrences, compositeKey, value);
		}
		setCurrentFragment(position);
		if(MiraConstants.MEDIA_PLAYER!=null){
			if(MiraConstants.MEDIA_PLAYER.isPlaying()){				
				MiraConstants.MEDIA_PLAYER.stop();
			}
		}
	}

	private void setCurrentFragment(int position) {
		
		viewPager.setCurrentItem(position);
		switch (position) {

		case 0:
            bottom_layout.setBackgroundResource(R.drawable.med);
			break;
		case 1:
            bottom_layout.setBackgroundResource(R.drawable.diet);
			break;
		case 2:
            bottom_layout.setBackgroundResource(R.drawable.dos);
			break;
		case 3:
            bottom_layout.setBackgroundResource(R.drawable.donts);
			break;
		default:
			break;
		}
	}
	
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();	
		String[]stringsEng = {"MEDICALCARE","DIET","DOS","DONTS"};
        String[]stringsHnd = {"स्वास्थय देखभाल","खानपान","क्या करें","क्या न करें"};

        String[]stringsEngHnd=MiraConstants.LANGUAGE.equals(MiraConstants.ENGLISH)?stringsEng:stringsHnd;
        String []strings={"MEDICALCARE","DIET","DOS","DONTS"};
        System.out.println("Header Message "+stringsEngHnd[0]);
        int[][]imgIndex={MedicareImg,DeitImage,DosImg,DontDosImg};
		for (int i = 0; i < 4; i++) {
			WeeklyInfoIndividFrag fragment = WeeklyInfoIndividFrag.newInstance(map.get(strings[i]),stringsEngHnd[i],imgIndex[i][selctIndex],i,selctIndex);
			System.out.println("Index To Check...."+selctIndex+"  "+imgIndex[i][selctIndex]);
			fList.add(fragment);
		}
		return fList;
	}

	@Override
	public void onClick(View v) {
		int i = viewPager.getCurrentItem();
		System.out.println(i+"  "+index);
		switch (v.getId()) {
		
		case R.id.pre_madicare:
			setCurrentFragment(0);

			break;
		case R.id.pre_diet:
			setCurrentFragment(1);

			break;
		case R.id.pre_does:
			setCurrentFragment(2);

			break;
		case R.id.pre_donts:
			setCurrentFragment(3);

			break;
		}
	}

	@Override
	public void onPause() { 
		
		super.onPause();
		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}
	}
	
	int[]MedicareImg = {
			R.drawable.w01_m,
			R.drawable.w02_m,
			R.drawable.w03_m,
			R.drawable.w04_m,
			R.drawable.w05_m,
			R.drawable.w06_m,
			R.drawable.w07_m,
			R.drawable.w08_m,
			R.drawable.w07_m,
			R.drawable.w06_m,
			R.drawable.w11_m,
			R.drawable.w12_m,
			R.drawable.w13_m,
			R.drawable.w14_m,
			R.drawable.w15_m,
			R.drawable.w16_m,
			R.drawable.w07_m,
			R.drawable.w18_m,
			R.drawable.w12_m,
			R.drawable.w20_m,
			R.drawable.w11_m,
			R.drawable.w14_m,
			R.drawable.w15_m,
			R.drawable.w12_m,
			R.drawable.w07_m,
			R.drawable.w16_m,
			R.drawable.w27_m,
			R.drawable.w08_m,
			R.drawable.w18_m,
			R.drawable.w14_m,
			R.drawable.w31_m,
			R.drawable.w20_m,
			R.drawable.w07_m,
			R.drawable.w12_m,
			R.drawable.w15_m,
			R.drawable.w16_m,
			R.drawable.w18_m,
			R.drawable.w20_m,
			R.drawable.w07_m,
			R.drawable.w31_m};
	int[]DeitImage ={
			R.drawable.w01_d,
			R.drawable.w02_d,
			R.drawable.w01_d,
			R.drawable.w04_d,
			R.drawable.w05_d,
			R.drawable.w06_d,
			R.drawable.w07_d,
			R.drawable.w08_d,
			R.drawable.w09_d,
			R.drawable.w10_d,
			R.drawable.w11_d,
			R.drawable.w11_d,
			R.drawable.w06_d,
			R.drawable.w14_d,
			R.drawable.w15_d,
			R.drawable.w07_d,
			R.drawable.w06_d,
			R.drawable.w11_d,
			R.drawable.w14_d,
			R.drawable.w20_d,
			R.drawable.w07_d,
			R.drawable.w08_d,
			R.drawable.w06_d,
			R.drawable.w11_d,
			R.drawable.w07_d,
			R.drawable.w11_d,
			R.drawable.w14_d,
			R.drawable.w06_d,
			R.drawable.w07_d,
			R.drawable.w08_d,
			R.drawable.w12_d,
			R.drawable.w14_d,
			R.drawable.w11_d,
			R.drawable.w06_d,
			R.drawable.w08_d,
			R.drawable.w07_d,
			R.drawable.w07_d,
			R.drawable.w11_d,
			R.drawable.w11_d,
			R.drawable.w11_d}; 
	int[]DosImg = {
			R.drawable.w01_y,
			R.drawable.w02_y,
			R.drawable.w03_y,
			R.drawable.w04_y,
			R.drawable.w05_y,
			R.drawable.w06_y,
			R.drawable.w07_y,
			R.drawable.w03_y,
			R.drawable.w03_y,
			R.drawable.w10_y,
			R.drawable.w03_y,
			R.drawable.w12_y,
			R.drawable.w06_y,
			R.drawable.w03_y,
			R.drawable.w07_y,
			R.drawable.w06_y,
			R.drawable.w17_y,
			R.drawable.w08_n,
			R.drawable.w19_y,
			R.drawable.w12_y,
			R.drawable.w21_y,
			R.drawable.w06_y,
			R.drawable.w12_y,
			R.drawable.w03_y,
			R.drawable.w17_y,
			R.drawable.w19_y,
			R.drawable.w10_y,
			R.drawable.w07_y,
			R.drawable.w29_y,
			R.drawable.w12_y,
			R.drawable.w31_y,
			R.drawable.w26_b,
			R.drawable.w33_y,
			R.drawable.w34_y,
			R.drawable.w06_y,
			R.drawable.w36_y,
			R.drawable.w12_y,
			R.drawable.w19_y,
			R.drawable.w39_y,
			R.drawable.w40_y};
	
	int[]DontDosImg = {
			R.drawable.w01_n,
			R.drawable.w02_n,
			R.drawable.w03_n,
			R.drawable.w04_n,
			R.drawable.w05_n,
			R.drawable.w01_n,
			R.drawable.w07_n,
			R.drawable.w08_n,
			R.drawable.w05_n,
			R.drawable.w07_n,
			R.drawable.w09_n,
			R.drawable.w05_n,
			R.drawable.w13_n,
			R.drawable.w07_n,
			R.drawable.w15_n,
			R.drawable.w16_n,
			R.drawable.w05_n,
			R.drawable.w01_n,
			R.drawable.w19_n,
			R.drawable.w05_n,
			R.drawable.w21_n,
			R.drawable.w03_y,
			R.drawable.w23_n,
			R.drawable.w16_n,
			R.drawable.w25_n,
			R.drawable.w08_n,
			R.drawable.w21_n,
			R.drawable.w28_n,
			R.drawable.w08_n,
			R.drawable.w01_n,
			R.drawable.w05_n,
			R.drawable.w10_y,
			R.drawable.w08_n,
			R.drawable.w34_n,
			R.drawable.w01_n,
			R.drawable.w07_m,
			R.drawable.w10_y,
			R.drawable.w38_n,
			R.drawable.w39_n,
			R.drawable.w40_n,};
}
