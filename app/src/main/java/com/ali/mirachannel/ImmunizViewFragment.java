package com.ali.mirachannel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.mirachannel.utility.MediaManager;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.HashMap;

/**
 * @author fragment to show the dtails fragment for immunization
 *
 */
public class ImmunizViewFragment extends Fragment{
	Context context;


	
	
	/**
	 * @param bundle get the information passed by child class
	 * @return fragment
	 */
	public static final ImmunizViewFragment newInstance(Bundle bundle) {
		ImmunizViewFragment immunizViewFragment = new ImmunizViewFragment();		
		immunizViewFragment.setArguments(bundle);
		return immunizViewFragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		Log.v("MIRA", getTag());
		String[] string = getArguments().getString("Vaccinestatus").split("#");
		View view = inflater.inflate(R.layout.adapter_element_imz, container, false);
		//RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.adapter_elm_imz_layout);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.adapter_elm_imz_layout);

        LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.imu_layout_bg);
//		ImageView imageView = (ImageView) view.findViewById(R.id.pre_image);
//		imageView.setImageResource(getArguments().getInt("image"));

		CustomView customView = (CustomView) view.findViewById(R.id.customView_imz);
		customView.setExampleDrawable(getResources().getDrawable(getArguments().getInt("image")));
		TextView pre_descrp = (TextView) view.findViewById(R.id.pre_descrp);
		HashMap<String, String>map = (HashMap<String, String>) getArguments().getSerializable("map");
		pre_descrp.setText(map.get("VaccMessage"));

//		System.out.println();
		customView.setExampleString(map.get("VaccMessage"));
		
//		if(Integer.parseInt(string[1])==0){
//			layout.setBackgroundColor(getResources().getColor(R.color.header2));//red_color
//            layout2.setBackgroundColor(getResources().getColor(R.color.bg2));
//		}else if(Integer.parseInt(string[1])==1){
//			layout.setBackgroundColor(getResources().getColor(R.color.header3));//green_color
//            layout2.setBackgroundColor(getResources().getColor(R.color.bg3));
//		}else{
//			layout.setBackgroundColor(getResources().getColor(R.color.header1));//blue_color
//            layout2.setBackgroundColor(getResources().getColor(R.color.bg1));
//		}

		TextView header = (TextView) view.findViewById(R.id.pre_header);

		if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
			header.setText(getVaccineHindi(string[0]));
		}
		else{
			header.setText(string[0]);
		}

//		header.setText(string[0]);
		int index = getArguments().getInt("selectIndex");
//		final String soundURL = MiraConstants.URLMIRAHN+"HI"+index+"_0.mp3";
		final String soundURL = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"IND_HI_IM"+(index+1)+"_00.mp3":"EI"+index+"_0.mp3";
		System.out.println("Immunize SoundUrl ..."+soundURL);

		ImageButton imageButton_sound = (ImageButton) view.findViewById(R.id.imageButton_sound);
		imageButton_sound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				MediaManager.playUrl(soundURL,getActivity());
			}
		});

		return view;
	}
	
	@Override
	public void onPause() { 
		
		super.onPause();
		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		System.out.println("-----------------");
		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}


	}
	String getVaccineHindi(String Vacname){
		String a="N/A";
		if(Vacname.equals("Hepatitis B-0")){
			a="हैपेटाइटिस बी-0";
		}
		else if(Vacname.equals("OPV-0")){
			a="ओ पी वि- 0";
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
