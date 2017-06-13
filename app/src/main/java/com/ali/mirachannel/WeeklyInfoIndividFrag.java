package com.ali.mirachannel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.utility.MediaManager;
import com.ali.mirachannel.utility.MiraConstants;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class WeeklyInfoIndividFrag extends Fragment {
//    RelativeLayout relativeLayout=null;

	public WeeklyInfoIndividFrag() {
	}
	
	public static final WeeklyInfoIndividFrag newInstance(String Text,String title,int image,int index,int selctIndex) {
		WeeklyInfoIndividFrag fragment = new WeeklyInfoIndividFrag();
		Bundle bundle = new Bundle();
		bundle.putInt("index", index);
		bundle.putInt("selctIndex", selctIndex);
		bundle.putString("title", title);
		bundle.putInt("image", image);
		bundle.putString("TEXT", Text);
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View view = inflater.inflate(R.layout.weekly_info_individ_frag, container, false);
		
		FrameLayout fram_preg = (FrameLayout) view.findViewById(R.id.fram_preg);
        RelativeLayout relativeLayout=(RelativeLayout)view.findViewById(R.id.relative_layout);//By Fz

		switch (getArguments().getInt("index")) {
		case 0:
			//fram_preg.setBackgroundResource(R.drawable.bg_medical);
            fram_preg.setBackgroundColor(getResources().getColor(R.color.color_medical));
            relativeLayout.setBackgroundResource(R.drawable.med);

//            MyPiccaso.getView(getActivity(),R.drawable.bg_medical,fram_preg);
//            MyPiccaso.getView(getActivity(),R.drawable.med,relativeLayout);
			break;
		case 1:
			//fram_preg.setBackgroundResource(R.drawable.bg_diet);
            fram_preg.setBackgroundColor(getResources().getColor(R.color.color_diet));
            relativeLayout.setBackgroundResource(R.drawable.diet);

            //MyPiccaso.getView(getActivity(),R.drawable.diet,relativeLayout);
			break;
		case 2:
//			fram_preg.setBackgroundResource(R.drawable.bg_dos);
            fram_preg.setBackgroundColor(getResources().getColor(R.color.color_dos));
            relativeLayout.setBackgroundResource(R.drawable.dos);

           // MyPiccaso.getView(getActivity(),R.drawable.dos,relativeLayout);
			break;
		case 3:
//			fram_preg.setBackgroundResource(R.drawable.bg_donts);
            fram_preg.setBackgroundColor(getResources().getColor(R.color.color_donts));
            relativeLayout.setBackgroundResource(R.drawable.donts);

           // MyPiccaso.getView(getActivity(),R.drawable.donts,relativeLayout);
			break;
		default:
			break;
		}
		
		TextView subtitle = (TextView) view.findViewById(R.id.pre_subtitle);
		subtitle.setText(getArguments().getString("title"));
		
		ImageView imageView = (ImageView) view.findViewById(R.id.tabhost_img);
		imageView.setImageResource(getArguments().getInt("image"));
        //MyPiccaso.getView(getActivity(),getArguments().getInt("image"),imageView);

//		pre_subtitle
		
		CustomView customView = (CustomView) view.findViewById(R.id.customView_week);
		customView.setExampleDrawable(getResources().getDrawable(getArguments().getInt("image")));
		
		customView.setExampleString(getArguments().getString("TEXT"));
		
		TextView textView = (TextView) view.findViewById(R.id.child_name);
		textView.setText(getArguments().getString("TEXT"));
		
		int selctIndex = getArguments().getInt("selctIndex");
		int index = getArguments().getInt("index");


//		if(index==0){
//			index=1;
//			System.out.println("vlaue of Index in weeklyInfoIndi  "+index);
//
//		}
//		else if(index==1){
//			index=0;
//			System.out.println("vlaue of Index in weeklyInfoIndi  "+index);
//		}

//		final String soundURL = MiraConstants.URLMIRAHN+"HW"+(selctIndex)+"_"+(index+1)+".mp3";
		final String soundURL = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"IND_HI_PN_W"+(selctIndex+1)+"_0"+(index+1)+".mp3":"EW"+selctIndex+"_"+(index+1)+".mp3";
		System.out.println("SoundUrl in WeeklyInfoIndividFrad  "+soundURL+"   "+selctIndex +"   "+index);
		ImageButton imageSpeaker = (ImageButton) view.findViewById(R.id.imageSpeakers);
		imageSpeaker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Click "+getArguments().getString("title"), Toast.LENGTH_SHORT).show();
				
				MediaManager.playUrl(soundURL,getActivity());
				
				
				
//				try {
//					MiraConstants.MEDIA_PLAYER.reset();
//					MiraConstants.MEDIA_PLAYER.setDataSource(soundURL);
//					MiraConstants.MEDIA_PLAYER.prepare();
//					MiraConstants.MEDIA_PLAYER.start();	
//					MiraConstants.MEDIA_PLAYER.setOnCompletionListener(new OnCompletionListener() {
//						
//						@Override
//						public void onCompletion(MediaPlayer mp) {
//							// TODO Auto-generated method stub
//							MiraConstants.MEDIA_PLAYER.reset();
//						}
//					});
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SecurityException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalStateException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
		});
		
		return view;
	}


}
