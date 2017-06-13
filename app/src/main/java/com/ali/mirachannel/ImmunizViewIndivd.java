package com.ali.mirachannel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.utility.MiraConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**display individual records for child*/
public class ImmunizViewIndivd extends ActionBarActivity implements OnPageChangeListener,OnClickListener{
	ActionBar actionBar;
	ViewPager viewPager;
	Bundle bundle;// = new Bundle();
	HashMap<String, String>map;
	private int index;
	private int indexmain;
    private ImageButton back,next;
    private int size=0;
    LinearLayout layout;
	String compositeKeyImmu="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weekly_infoimz_individ);
		layout=(LinearLayout)findViewById(R.id.imu_layout);

		bundle = getIntent().getBundleExtra("bundle");
		map = (HashMap<String, String>) bundle.getSerializable("map");
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		index = getIntent().getExtras().getInt("index");

		compositeKeyImmu=getIntent().getExtras().getString("compositeKeyImmu");
		System.out.println("compositeKeyImmu in ImmunizViewIndivid "+compositeKeyImmu);

		if(!compositeKeyImmu.equals("N/A")&&(index==0)){
			if(!MySharedPreference.getStringValue(this, MiraConstants.Immuniz_Prefrences,compositeKeyImmu).equals("0"))
			{
				updatePref(index);
			}
		}



		indexmain = bundle.getInt("indexmain");
		System.out.println(indexmain);
		List<Fragment>fragments = getFragments();
        size=fragments.size();
		viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager(), fragments));
		viewPager.setOnPageChangeListener(this);	
		
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		
		actionBar.setTitle(map.get("VaccName"));
		actionBar.setDisplayShowCustomEnabled(true);
		setCurrentFragment(index);
		ImageButton button_medicare = (ImageButton) findViewById(R.id.pre_madicare);
		button_medicare.setOnClickListener(this); 
		ImageButton button_diet = (ImageButton) findViewById(R.id.pre_diet);
		button_diet.setOnClickListener(this);

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

	private String timeStamp(){
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String format = s.format(new Date());
		return format;
	}

	private String updatePref(int position){
		String sfinal="N/A";
		String s1= MySharedPreference.getStringValue(this,MiraConstants.Immuniz_Prefrences,compositeKeyImmu);
		if(!s1.equals("0")){
//
			String firstPart=s1.substring(0, (position * 2)+2);
			String secondPart=s1.substring(((position * 2) + 2),s1.length()-19);
			String lastPart=s1.substring(s1.length()-19,s1.length());
			String [] qp=secondPart.split("#");
			System.out.println("Lo ji cutt gayi...."+firstPart+"    "+secondPart+"    "+lastPart);

			int a=Integer.parseInt(qp[0]) + 1;
			lastPart=timeStamp();

			String n=String.valueOf(a)+secondPart.substring(1);

			sfinal=firstPart+n+lastPart;

			System.out.println("In the Upadte ImmunizViewIndivd ....." + firstPart + "    " + secondPart + "  " + sfinal);
			MySharedPreference.saveStringInPreference(this, MiraConstants.Immuniz_Prefrences, compositeKeyImmu, sfinal);
		}

		return sfinal;
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
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
		
		return super.onOptionsItemSelected(item);
	}
	
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();	
		Bundle bundle1 = new Bundle();
		bundle1.putInt("image", this.bundle.getInt("image1"));
		bundle1.putString("messege", map.get("AdministrationMSG"));
		bundle1.putString("title", "Administration");
		bundle1.putInt("selectIndex", 1);
		bundle1.putInt("indexmain", indexmain);
		ImmunizViewIndivdFrag frag1 = ImmunizViewIndivdFrag.newInstance(bundle1);
		fList.add(frag1);
		
		Bundle bundle2 = new Bundle();
		bundle2.putInt("image", this.bundle.getInt("image2"));
		bundle2.putString("messege", map.get("AboutMsg"));
		bundle2.putString("title", "About");
		bundle2.putInt("selectIndex", 2);
		bundle2.putInt("indexmain", indexmain);
		ImmunizViewIndivdFrag frag2 = ImmunizViewIndivdFrag.newInstance(bundle2);
		fList.add(frag2);
		
		return fList;
	}
	


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
//		Inner View
		setCurrentFragment(arg0);
		if(!compositeKeyImmu.equals("N/A")){
			if(!MySharedPreference.getStringValue(this, MiraConstants.Immuniz_Prefrences,compositeKeyImmu).equals("0"))
			{
				updatePref(arg0);
			}
		}
		System.out.println("onPageSelected in ImmunizViewIndivd "+arg0);
	}
	
	private void setCurrentFragment(int position) {
		
		viewPager.setCurrentItem(position);
		switch (position) {
		case 0:
			viewPager.setBackgroundColor(getResources().getColor(R.color.bg1));
            layout.setBackgroundColor(getResources().getColor(R.color.header1));
			break;
		case 1:
			viewPager.setBackgroundColor(getResources().getColor(R.color.bg5));
            layout.setBackgroundColor(getResources().getColor(R.color.header5));
			break;
		case 2:
			viewPager.setBackgroundColor(getResources().getColor(R.color.color_dos));
			break;
		case 3:
			viewPager.setBackgroundColor(getResources().getColor(R.color.color_donts));
			break;
		default:
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
}
