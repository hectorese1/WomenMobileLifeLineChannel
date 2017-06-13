package com.ali.mirachannel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.mirachannel.utility.MediaManager;
import com.ali.mirachannel.utility.MiraConstants;
/**fragment to display records of individuals*/
public class ImmunizViewIndivdFrag extends Fragment{
	
	public static final ImmunizViewIndivdFrag newInstance(Bundle bundle) {
		ImmunizViewIndivdFrag frag = new ImmunizViewIndivdFrag();
		frag.setArguments(bundle);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.weekly_info_individ_frag, container, false);
		
		TextView subtitle = (TextView) view.findViewById(R.id.pre_subtitle);
		if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
			subtitle.setText(getTitleHindi(getArguments().getString("title")));
		}
		else{
			subtitle.setText(getArguments().getString("title"));
		}

		
		ImageView imageView = (ImageView) view.findViewById(R.id.tabhost_img);
		imageView.setImageResource(getArguments().getInt("image"));
		
		CustomView customView = (CustomView) view.findViewById(R.id.customView_week);
		customView.setExampleDrawable(getResources().getDrawable(getArguments().getInt("image")));
		
		customView.setExampleString(getArguments().getString("messege"));
		
		TextView textView = (TextView) view.findViewById(R.id.child_name);
		textView.setText(getArguments().getString("messege"));
		ImageButton imageSpeakers = (ImageButton) view.findViewById(R.id.imageSpeakers);
		
		int selectIndex = getArguments().getInt("selectIndex");
//		final String soundURL = MiraConstants.URLMIRAHN+"HI"+getArguments().getInt("indexmain")+"_"+selectIndex+".mp3";
		final String soundURL =MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"IND_HI_IM"+(getArguments().getInt("indexmain")+1)+"_0"+selectIndex+".mp3":"EI"+getArguments().getInt("indexmain")+"_"+selectIndex+".mp3";
System.out.println("Immu view indi...."+soundURL);
        RelativeLayout relativeLayout=(RelativeLayout)view.findViewById(R.id.relative_layout);
        if(subtitle.getText().toString().equalsIgnoreCase("Administration")){
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.header1));
        }else{
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.header5));
        }
		System.out.println(soundURL);
		imageSpeakers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MediaManager.playUrl(soundURL,getActivity());
			}
		});
		return view;
	}
	String getTitleHindi(String s){
		String rtrn="N/A";
		if(s.equals("Administration")){
			rtrn="लगाने का तरीका ";

		}
		else if(s.equals("About")){
			rtrn="बीमारी के बारें में";
		}
		return rtrn;
	}
}
