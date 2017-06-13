package com.ali.mirachannel;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.mirachannel.utility.MediaManager;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
/**neonatal care fragmet for display details*/
public class NeoNatalCareFrag extends Fragment {
	String titleString="";
	public static final NeoNatalCareFrag newInstance(Bundle bundle) {
		NeoNatalCareFrag careFrag = new NeoNatalCareFrag();
		careFrag.setArguments(bundle);
		return careFrag;
	}
	
	public NeoNatalCareFrag() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_neo_natal_care, container,false);
		Bundle bundle = getArguments();
		titleString = bundle.getString("title");
		((TextView)view.findViewById(R.id.txt_neo_title)).setText(bundle.getString("title"));
		((TextView)view.findViewById(R.id.txt_neo_descp)).setText(bundle.getString("messege"));
		((ImageView)view.findViewById(R.id.img_neo_img)).setImageResource(bundle.getInt("image"));
		CustomView customView = (CustomView)view.findViewById(R.id.customView_neo);
		customView.setExampleDrawable(getResources().getDrawable(bundle.getInt("image")));
		
		customView.setExampleString(bundle.getString("messege"));
        System.out.println("NeoNatalCareFrag Screen...."+bundle.getString("screen"));


        if(bundle.getString("screen").equalsIgnoreCase("hospital")) {
            customView.setBackgroundColor(getResources().getColor(R.color.bg2));
            (view.findViewById(R.id.neo_natalcare)).setBackgroundColor(getResources().getColor(R.color.header2));
        }
        else if(bundle.getString("screen").equalsIgnoreCase("home")) {
            customView.setBackgroundColor(getResources().getColor(R.color.bg2));
            (view.findViewById(R.id.neo_natalcare)).setBackgroundColor(getResources().getColor(R.color.header2));
        }
        else if(bundle.getString("screen").equalsIgnoreCase("danger")) {
            customView.setBackgroundColor(getResources().getColor(R.color.bg2));
            (view.findViewById(R.id.neo_natalcare)).setBackgroundColor(getResources().getColor(R.color.header2));
        }
        else if(bundle.getString("screen").equalsIgnoreCase("feeding")) {
            customView.setBackgroundColor(getResources().getColor(R.color.bg6));
            (view.findViewById(R.id.neo_natalcare)).setBackgroundColor(getResources().getColor(R.color.header6));
        }
        else if(bundle.getString("screen").equalsIgnoreCase("swatchata")) {
            customView.setBackgroundColor(getResources().getColor(R.color.bg6));
            (view.findViewById(R.id.neo_natalcare)).setBackgroundColor(getResources().getColor(R.color.header6));
        }
        else if(bundle.getString("screen").equalsIgnoreCase("FPMethod")) {
            customView.setBackgroundColor(getResources().getColor(R.color.bg3));
            (view.findViewById(R.id.neo_natalcare)).setBackgroundColor(getResources().getColor(R.color.header3));
        }

		final String soundURL = bundle.getString("sound");
		view.findViewById(R.id.img_btn_neo_speaker).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				MediaManager.playUrl(soundURL+".mp3",getActivity());

			}
		});
		
		view.findViewById(R.id.customView_neo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(titleString.equals("Check List")||titleString.equals("Check List.")||titleString.equals("चेक लिस्ट.")||titleString.equals("चेक लिस्ट")){
					List<ListApp> listApps = generateList(InstitutionalChecklistHnd,images_HBPNC);
					if(titleString.equals("Check List.")||titleString.equals("चेक लिस्ट.")){
//						listApps = generateList(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?InstitutionalChecklistHnd:InstitutionalChecklistEng,images_Institutional);
						listApps = generateList(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?HBPNCChecklistHnd:HBPNCChecklistEng,images_Institutional);
					}else{
//						listApps = generateList(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?HBPNCChecklistHnd:HBPNCChecklistEng,images_HBPNC);
						listApps = generateList(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?InstitutionalChecklistHnd:InstitutionalChecklistEng,images_HBPNC);
//						listApps = generateList(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?InstitutionalChecklistHnd:InstitutionalChecklistEng,images_Institutional);
					}
					
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
										
					builder.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"Check List":"चेक लिस्ट");
					builder.setAdapter(new MyAdapter(getActivity(),listApps), null);
					builder.setPositiveButton("OK", null);
					builder.show();
				}				
			}

			private List<ListApp> generateList(String[][]strings, int[]images) {
				List<ListApp>listApps = new ArrayList<NeoNatalCareFrag.ListApp>();
				for (int i = 0; i < images.length; i++) {
					ListApp app = new ListApp(strings[i][1], images[i]);
					listApps.add(app);
				}				
				return listApps;
			}			
		});
		return view;
	}
	class ListApp{
		String string;
		int image;
		public ListApp(String string, int image) {
			this.string = string;
			this.image = image;
		}				
	}
	
	class MyAdapter extends BaseAdapter{
		Context context;
		List<ListApp>listApps;
		public MyAdapter(Context context, List<ListApp>listApps) {
			this.context = context;
			this.listApps = listApps;
		}

		@Override
		public int getCount() {
			return listApps.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listApps.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		
		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.adapter_element_checklist, arg2, false);
			((ImageView)view.findViewById(R.id.chklist_imageView)).setImageResource(listApps.get(position).image);
			((TextView)view.findViewById(R.id.chklist_textView)).setText(listApps.get(position).string);
			return view;
		}
		
	}
	
		
	 String[][] HBPNCChecklistEng = 
		 		{//0
		        {"home01", "Clean room", "Prepare a clean room for delivery", "homeEng02"},//1
		        {"home02", "Clean bed sheet ", "Keep a clean bed sheet to cover the mother while delivering baby ", "homeEng03"},//2
		        {"home03", "Clean plastic sheet ", "To wrap the placenta keep a clean plastic sheet ready", "homeEng04"},//3
		        {"home04", "Plastic Bucket of water", "Keep a bucket full of disinfected boiled water with a mug ", "homeEng05"},//4
		        {"home05", "2 bowls", "Keep 2 clean bowls ready- one for washing hands and one for keeping the placenta", "homeEng06"},//5
		        {"home06", "Clean blade ", "Keep a new clean blade ready for cutting the umbilical cord of the baby", "homeEng07"},//6
		        {"home07", "2-3 threads ", "Get 2-3 threads each 20 cm long to tie the umbilical cord", "homeEng08"},//7        
		        {"CHECK_LIST", "???", "Things required for a safe home birth", "homeEng10"},//7        
		    //        Things required for a safe home birth
		    };
		    String[][] HBPNCChecklistHnd = 
		    	{//0
		        {"home01", "साफ़ कमरा ", "प्रसव के लिए एक साफ़ कमरा तैयार करें ", "homeHnd02"},//1
		        {"home02", "साफ चादर ", "बच्चा पैदा करते समय ढकने के लिए साफ़ चादर का इंतेज़ाम करें ", "homeHnd03"},//2
		        {"home03", "साफ़ प्लास्टिक शीट ", "आंवल लपेटने के लिए एक साफ़ प्लास्टिक शीट पास रखें ", "homeHnd04"},//3
		        {"home04", "एक बाल्टी उबला हुआ साफ़ पानी", "हाथ धोने के लिए एक बाल्टी में साफ़ उबला पानी और एक डब्बे का प्रबंध  करें ", "homeHnd05"},//4
		        {"home05", "2 कटोरी ", "2 साफ़ कटोरियाँ पास रखें- एक हाथ धोने के लिए और एक आंवल रखने के लिए ", "homeHnd06"},//5
		        {"home06", "साफ़ ब्लेड  ", "जन्म के बाद शिशु की नाल काटने के लिए एक नया व साफ़ ब्लेड रखें ", "homeHnd07"},//6
		        {"home07", "2-3 लम्बे धागे  ", "काटने के बाद नाल बांधने के लिए 20 सेंटीमीटर लम्बे 2-3 धागों का प्रबंध करें ", "homeHnd08"},//7 
		        {"CHECK_LIST", "???", "घर पर प्रसव के लिए आवश्यक वस्तुएं:", "homeHnd10"}//7
		    };
		    
		    String[][] InstitutionalChecklistEng = {
                   //0

                    {"Inst03", "Woolen clothes", "Newborn baby catches cold very fast in order to wrap them properly, keep socks, cap, sweater, small blanket etc.", "InstEng04"},//3
		            {"Inst04", "Cotton nappies", "Keep at least 6-8 cotton nappies for the baby as they urinate frequently", "InstEng05"},//4
		            {"Inst05", "Mother’s clothing", "Keep comfortable clothes for yourself like nightgown or salwar-suit", "InstEng06"},//5
		            {"Inst06", "A bag ", "Keep a soap, comb,toothbrush and sanitary pads with you for  basic hygiene and cleanliness", "InstEng07"},//6
                    {"Inst01", "Money", "Keep some money in cash with you so that you are prepared for any emergency situation", "InstEng02"},//1
                    {"Inst02", "Arranging transport", "Arrange for a vehicle for taking the pregnant to the hospital when labor pain starts.", "InstEng03"},//2

                    {"CHECK_LIST", "???", "Things required for a safe delivery at hospital-", "InstEng09"},//6
		        };
		        String[][] InstitutionalChecklistHnd = {
                    {"Inst03", "गर्म कपड़े", "नवजात शिशु को सर्दी ज़ुखाम जल्दी हो जाता है इसलिए उसे लपेटकर रखने के लिए मोज़े, टोपी, स्वेटर, छोटा कंबल आदि का प्रबंध करें.", "InstHnd04"},
                    {"Inst04", "सूती कपड़े की लंगोट", "नवजात शिशु बार बार पेशाब करतें है इसलिए कम से कम 6-8 सूती लंगोट रखें.", "InstHnd05"},
                    {"Inst05", "महिला के लिए  कपड़े", "अपने लिए आरामदायक कपड़े रखें जैसे कि सलवार-कमीज़", "InstHnd06"},
                    {"Inst06", "एक बैग ", "अपनी स्वछता बनाये रखने के लिए साबुन, कंघी, टूथब्रश और सेनेटरी पैड साथ रखें.", "InstHnd07"},
                    {"Inst01", "पैसे", "अपने पास कुछ पैसे अवश्य रखें ताकि आप किसी भी आपातकालीन स्थिति के लिए तैयार रहें.", "InstHnd02"},
                    {"Inst02", "परिवहन की व्यवस्था", "प्रसव के समय गर्भवती औरत को अस्पताल ले जाने के लिए परिवहन की व्यवस्था रखें", "InstHnd03"},

		            {"CHECK_LIST", "???", "सुरक्षित प्रसव के लिए ज़रूरी चीज़ें", "InstHnd09"},//6
		        };
		        
		        int[]images_HBPNC = {R.drawable.inst03,R.drawable.inst04,R.drawable.inst05,R.drawable.inst06,
                                     R.drawable.inst01,R.drawable.inst02};
		        
		        int[]images_Institutional = {R.drawable.home01,R.drawable.home02,R.drawable.home03,
		        		R.drawable.home04,R.drawable.home05,R.drawable.home06,R.drawable.home06};
		        
}
