package com.ali.mirachannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.WeeklyDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MediaManager;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.HashMap;
import java.util.List;

public class ViewFragment extends Fragment implements OnClickListener{
	
	Context context;
//	private static String soundURL;
	HashMap<String, String>map;
	static String mWomanid;
	static String Pregid;
	static int mWeek,currWeek;
	DatabaseHelper databaseHelper;
	List<WeeklyDtl>weeklyDtls;
	int[]imageViewid = {R.id.vaccineImgMark,R.id.img_house_imun,R.id.imageView3,R.id.imageView4,R.id.imageView5};
	ImageView[]imageViews = new ImageView[imageViewid.length];
	WomenDtl womenDtl;

   // static boolean isCalled=false;

	public static final ViewFragment newInstance(int id,int week,HashMap<String, String>map,String womanid,String _pregid,String womenName, int CurrWeek,String lmcDate) {
		ViewFragment fragment = new ViewFragment();

		mWomanid = womanid;
		Pregid = _pregid;
		currWeek = CurrWeek; 
		Bundle bundle = new Bundle();
		bundle.putSerializable("map", map);
		bundle.putInt("image", id);
		bundle.putString("womanid", womanid);	
		bundle.putString("womenname", womenName);
		bundle.putString("lmcDate",lmcDate);
		bundle.putString("week", String.valueOf(week));
		fragment.setArguments(bundle);
        System.out.println("ViewFragment Constructor called....");
		return fragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        System.out.println("ViewFragment onAttach called....");
		Log.v("MIRA", getTag());
		this.context = activity;
		databaseHelper = DatabaseHelper.newInstance(context);

       // comm = (PrenatalWomenListDemo.PrenatalWomenListDemoComm) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
         System.out.println("ViewFragment onCreateView called....");

		map = (HashMap<String, String>) getArguments().getSerializable("map");						
		View view = inflater.inflate(R.layout.adapter_element_new, container, false);
		TextView header = (TextView) view.findViewById(R.id.pre_header);
		mWeek = Integer.parseInt(getArguments().getString("week"));
		header.setText((mWeek+1)+"");
		
		TextView textView_wom_name = (TextView) view.findViewById(R.id.textView_wom_name);
		textView_wom_name.setText(getArguments().getString("womenname").toUpperCase());


		//-------------------- Faisal------------------------

       String lmc=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"एल एम सी-":"LMC-";
        TextView lmc_date = (TextView) view.findViewById(R.id.lmcDate);
        lmc_date.setText(lmc+getArguments().getString("lmcDate").toUpperCase());


		
		TextView descrp = (TextView) view.findViewById(R.id.pre_descrp);
		descrp.setText(map.get("BABYGROWTH"));
		ImageView imageView = (ImageView) view.findViewById(R.id.pre_image);
		
		CustomView customView = (CustomView) view.findViewById(R.id.customView1);
		
		customView.setExampleString(map.get("BABYGROWTH"));
		
		int img_id=getArguments().getInt("image");
		imageView.setImageResource(img_id); 
		customView.setExampleDrawable(getResources().getDrawable(img_id));
		final String soundURL = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"IND_HI_PN_W"+(mWeek+1)+"_00.mp3":"EW"+mWeek+"_0.mp3";
		System.out.println("Sound Url............"+soundURL);
		ImageButton imageWeekSpeaker = (ImageButton) view.findViewById(R.id.imageWeekSpeaker);
		imageWeekSpeaker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MediaManager.playUrl(soundURL,getActivity());						
			}
		});
                                                                
		LinearLayout question_layout = (LinearLayout) view.findViewById(R.id.question_layout);
		question_layout.setOnClickListener(this);


            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i] = (ImageView) view.findViewById(imageViewid[i]);
            }

		String tag_name = "SELECT *FROM tabweekinfo WHERE pregid='"+Pregid+"' AND weeknum="+mWeek;
		weeklyDtls = databaseHelper.getWeeklyDetailsByTag(tag_name);
		if(weeklyDtls.size()>0){
			WeeklyDtl dtl = weeklyDtls.get(0);
				imageViews[0].setImageResource(setQuestImg(dtl.getQuesOne()));//==1?R.drawable.ind_2:R.drawable.ind_1);
				imageViews[1].setImageResource(setQuestImg(dtl.getQuesTwo()));//==1?R.drawable.ind_2:R.drawable.ind_1);
				imageViews[2].setImageResource(setQuestImg(dtl.getQuesThree()));//==1?R.drawable.ind_2:R.drawable.ind_1);
				imageViews[3].setImageResource(setQuestImg(dtl.getQuesFour()));//==1?R.drawable.ind_2:R.drawable.ind_1);
				imageViews[4].setImageResource(setQuestImg(dtl.getQuesFive()));//==1?R.drawable.ind_2:R.drawable.ind_1);
		}							
		System.out.println("------  "+mWeek);
        //isCalled=true;
		return view;
	}
	
	public int setQuestImg(int index) {
		switch (index) {
		case 0:
			return R.drawable.ind_1;
		case 1:
			return R.drawable.ind_2;
		case 2:		
			return R.drawable.ind_2;
		case 3:
			return R.drawable.ind_3;
		case 4:
			return R.drawable.ind_4;
		case 5:
			return R.drawable.ind_5;
		default:
			return R.drawable.ind_0;
		}
	}
	
	@Override
	public void onClick(View v) {
		String header;
		String descrp;
		int image;		
		Intent intent = new Intent();
//		bundle.putSerializable("map", map);
		intent.putExtra("map", map);
		intent.setClass(getActivity(), WeeklyInfoIndivid.class);
		
		switch (v.getId()) { 
		case R.id.pre_madicare:
			header = "MEDICALCARE";
			descrp = map.get(header);
			image = R.drawable.pre_0;
			startActivity(intent);
			break;
			
		case R.id.pre_diet:			
			header = "DIET";
			descrp = map.get(header);
			image = R.drawable.pre_2;
			startActivity(intent);
			break;

		case R.id.pre_does:
			header = "DOS";
			descrp = map.get(header);
			image = R.drawable.pre_3;
			startActivity(intent);
			break;

		case R.id.pre_donts:
			header = "DONTS";
			descrp = map.get(header);
			image = R.drawable.pre_4;
			startActivity(intent);
			break;
		case R.id.question_layout:
			int week = Integer.parseInt(getArguments().getString("week"));
			if(weeklyDtls.size()<=0&&currWeek==week){
			ques = 0;
			showQuestionDialog();
			}
			break;
		default:
			header = "WEEK";
			descrp = "Descrp of WEEK ";
			image = R.drawable.pre_4;
			break;
		}
	}
	int ques=0;
	int ques1=1;
	final int[]ans = new int[5];
	private void showQuestionDialog() {
		 final String[] questionEng = {
				    "Do you feel weak, breathless & get tired easily?",
		            "Are you suffering from fever, severe headache, blurring of vision or swelling all over the body?",
		            "Are you suffering from continuous vaginal bleeding?",
		            "Are you suffering from convulsions as well as loss of consciousness?",
		            "Are suffering from severe abdominal pain for more than 12 hours?",
		            "Are You Sure Want to Submit Answers?",""};
		 final String[] questionHnd = {
				    "क्या आप कमजोरी या साँस लेने में कठिनाई और थकान महसूस करती हैं ?",
			        "क्या आपको बुखार, सर में दर्द, आँखों में धुंदलापन  या  पूरे शरीर में सूजन हैं?",
			        "क्या आपकी यौनि से लगातार रक्तस्राव हो रहा हैं?",
			        "क्या आपको दौरे पड़ने के साथ चक्कर भी आते हैं?",
			        "क्या आपके पेट के निचले हिस्से में दर्द होते हुए 12 घंटे से अधिक हो गया है? ",
			        "क्या आप निश्चित ही इसे जमा करना चाहते है? ",""};

		 final String[] question = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?questionHnd:questionEng;
			final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.question_dialog, null);
			final TextView quest_num = (TextView) view.findViewById(R.id.quest_num);
		if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
			quest_num.setText(" सवाल - "+(ques+1));
		}
		else{
			quest_num.setText("Question - "+(ques+1));
		}

			final TextView ques_desc = (TextView) view.findViewById(R.id.ques_desc);
			ques_desc.setText(question[ques]);		
			view.findViewById(R.id.imageSpeaker).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					System.out.println("Question    "+ques1);
					String soundURL = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"IND_HI_PN_HRP_0"+(ques+1)+".mp3":"E0"+(ques+1)+".mp3";
					MediaManager.playUrl(soundURL,getActivity());
					System.out.println("Question    "+ques1);
					System.out.println("Sound url question    "+soundURL);
				}
			});
			builder.setView(view);			
			builder.setNegativeButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नहीं ":"NO",new DialogInterface.OnClickListener() {
				
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (ques < 5) {
					ans[ques] = 0;
					imageViews[ques].setImageResource(R.drawable.ind_1);
				}
				ques += ques < question.length - 1 ? 1 : 0;
//				quest_num.setText("Question - " + (ques + 1));
				if (ques < question.length - 2) {
					showQuestionDialog();
					ques1=ques+1;
				}
				else{
					alertDialog();
					dialog.dismiss();
				}
//				if (ques == question.length - 1) {
//					for (int i = 0; i < ans.length; i++) {
//						imageViews[i].setImageResource(R.drawable.ind_0);
//					}
//					dialog.dismiss();
//				}
			}
		});
			
			builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"हाँ ":"YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (ques < 5) {
						ans[ques] = 1;
						imageViews[ques].setImageResource(R.drawable.ind_2);
					}
					ques += ques<question.length-1?1:0;
					quest_num.setText("Question - "+(ques+1));
					if(ques<question.length-2){
						showQuestionDialog();
					} else{
						alertDialog();
						dialog.dismiss();
					}
//					if(ques==question.length-1){
//						setQuesAnswers(ans);
//						dialog.dismiss();
//					}
				}
			});
			builder.setCancelable(false);
			builder.show();
	}
	
	   private void alertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String title = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"प्रस्तुत करना":"Submit";
 		String message = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"क्या आप  अवश्य  ही इसे जमा करना चाहते है?":"Do you surely want to submit your answers?";
		String No = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नहीं":"No";
		String Yes = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"हाँ":"Yes";

//		builder.setIcon(android.R.drawable.ic_delete);
//		builder.setTitle(" ");
//		builder.setMessage(message);
//		builder.setNegativeButton(No, new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
////				if (ques == question.length - 1) {
//					for (int i = 0; i < ans.length; i++) {
//						imageViews[i].setImageResource(R.drawable.ind_0);
//					}
////				}
//			}
//		});
//		builder.setPositiveButton(Yes, new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				setQuesAnswers(ans);
//			}
//		});
//		builder.show();


// By Fz
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(" ");
        dialog.setMessage(message);
        dialog.setIcon(android.R.drawable.ic_delete);
        dialog.setButton2(
                No, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
//				if (ques == question.length - 1) {
					for (int i = 0; i < ans.length; i++) {
						imageViews[i].setImageResource(R.drawable.ind_0);
					}
//				}
			}
		});

        dialog.setButton(Yes, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
                if(!MiraConstants.PRENATAL_DEMO.equalsIgnoreCase("pre_demo"))setQuesAnswers(ans);
			}
		});

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position
        dialog.show();
	}
	
	private void setQuesAnswers(int[] ans) {
		WeeklyDtl weeklyDtl = new WeeklyDtl();
		weeklyDtl.setWomanId(mWomanid);
		weeklyDtl.setPregnentId(Pregid);
		weeklyDtl.setWeekNum(mWeek-1);
		weeklyDtl.setQuesOne(ans[0]);
		weeklyDtl.setQuesTwo(ans[1]);
		weeklyDtl.setQuesThree(ans[2]);
		weeklyDtl.setQuesFour(ans[3]);
		weeklyDtl.setQuesFive(ans[4]);
		databaseHelper.insetWeeklyDetails(weeklyDtl);
		weeklyDtls.add(weeklyDtl); 
	}
	class MyAdapter extends BaseAdapter{
	    private String[] questionEnglish = {"Do you feel weak, breathless & get tired easily?",
	            "Are you suffering from fever, severe headache, blurring of vision or swelling all over the body?",
	            "Are you suffering from continuous vaginal bleeding?",
	            "Are you suffering from convulsions as well as loss of consciousness?",
	            "Are suffering from severe abdominal pain for more than 12 hours?"};
	    Context context;
	    private HashMap<Integer, Boolean>map;
	    public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
	    	this.context = context;
	    	map = new HashMap<Integer, Boolean>();
		}
	    
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return questionEnglish.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return questionEnglish[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = null;
			if(convertView==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.question_adapter, parent, false);
			}else{
				view = convertView;
			}			
			TextView question = (TextView) view.findViewById(R.id.question_text);
			question.setText(questionEnglish[position]);
			ToggleButton toggleyesno = (ToggleButton) view.findViewById(R.id.toggleyesno);
			toggleyesno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					map.put(position, isChecked);
				}
			});
			return view;
		}

		public HashMap<Integer, Boolean> getMap() {
			return map;
		}
		public void setMap(HashMap<Integer, Boolean> map) {
			this.map = map;
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
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		System.out.println("-----------------");
		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
		}

         if(MiraConstants.PRENATAL_DEMO.equalsIgnoreCase("pre_demo")) {

             //comm.getprenatalListElemntDemo();
        }
	}

	
	
}
