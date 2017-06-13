package com.ali.mirachannel;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.model.FamilyPlanning;
import com.ali.mirachannel.utility.MediaManager;
import com.ali.mirachannel.utility.MiraConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */


/**class display fragments for adolecent girls items*/
public class AdolescentGirlFrag extends FragmentActivity  implements OnPageChangeListener{

	private ViewPager viewPager;
	private MyPageAdapter pageAdapter;
	private int index;
	private int indexSound = 6;

    private ImageButton back,next;
    private int size=0;
    private String screen="swatchata";
	String keyToidentify="";
	String [] subscene;
	String startTimeStamp="N/A",lastTimeStamp="N/A";
	/**method will generate the pager adapter to show the related items*/
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neo_natal_care_gen_sce);
		// Show the Up button in the action bar.

		setupActionBar();
        startTimeStamp=timeStamp();
		viewPager = (ViewPager) findViewById(R.id.viewpager); 
		Intent intent = getIntent();
		keyToidentify=intent.getStringExtra("keyToCheck");
		System.out.println("keyToCheck in AdolescentGirlFrag...."+keyToidentify);
		if(intent.hasExtra("FP Method")){
			getActionBar().setTitle("Family Planning");
		}

		List<Fragment> fragments;// = getFragments(0);
		
		(findViewById(R.id.lay_fp_dodont)).setVisibility(View.GONE);
		if(intent.hasExtra(FamilyPlanning.class.toString())){
			fragments = getFragments(1);
			(findViewById(R.id.lay_fp_dodont)).setVisibility(View.VISIBLE);
		}else{
			fragments = getFragments(0);
			(findViewById(R.id.lay_fp_dodont)).setVisibility(View.INVISIBLE);
		}
		size=fragments.size();
		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pageAdapter);
		
//		viewPager.setCurrentItem(getIntent().getExtras().getInt("weeknumber"));
		viewPager.setOnPageChangeListener(AdolescentGirlFrag.this);
//		System.out.println(preArrayList);
		if(keyToidentify.equals("fp_method")){
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

		else if(keyToidentify.equals("learning_girl")){
			if(MySharedPreference.getStringValue(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify).equals("0")){
				savePref();
			}
			else{
				String s1=MySharedPreference.getStringValue(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify);
				subscene=s1.split("#");
				int a=Integer.parseInt(subscene[0]) + 1;
				System.out.println("Bismillah In Adolescent."+a+ "  "+s1.substring(1));
				String n=String.valueOf(a)+s1.substring(1);
				System.out.println("Bismillah In Adolescent."+n);
				MySharedPreference.saveStringInPreference(this, MiraConstants.Adolescent_Girl_Prefrences, keyToidentify, n);
			}
		}




		
		final AlertDialog builder = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.dialog_fp_dodont, null, false);
		final TextView txt_fp_messege = (TextView) view.findViewById(R.id.txt_fp_messege);
		builder.setView(view);
		builder.setCancelable(false);
		builder.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(MiraConstants.MEDIA_PLAYER.isPlaying()){
					MiraConstants.MEDIA_PLAYER.stop();
				}
				builder.dismiss();
			}
		});
		(view.findViewById(R.id.imageButton_fp_speaker)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MediaManager.playUrl(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][indexSound]+".mp3":familyPlaningEng[index][indexSound]+".mp3",AdolescentGirlFrag.this);
			}
		});
		(findViewById(R.id.imageButton_do)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                builder.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"लाभ":"Do this");
//				builder.setMessage(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][3]:familyPlaningEng[index][3]);
//				builder.show();
                txt_fp_messege.setText(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][3]:familyPlaningEng[index][3]);
                indexSound = 7;
//				builder.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"यह कर":"Do this");
//				txt_fp_messege.setText(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][2]:familyPlaningEng[index][2]);
////				builder.setMessage(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][2]:familyPlaningEng[index][2]);
//				indexSound = 6;
				if(builder.isShowing()){
					builder.dismiss();
				}
				builder.show();
			}
		});
		
		(findViewById(R.id.imageButton_dont)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                builder.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"प्रयोग न करे":"Don't do this");
                txt_fp_messege.setText(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][2]:familyPlaningEng[index][2]);
//				builder.setMessage(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][2]:familyPlaningEng[index][2]);
                indexSound = 6;

//				builder.setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"यह यह मत करो":"Don't do this");
////				builder.setMessage(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][3]:familyPlaningEng[index][3]);
////				builder.show();
//				txt_fp_messege.setText(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?familyPlaningHnd[index][3]:familyPlaningEng[index][3]);
//				 indexSound = 7;
				if(builder.isShowing()){
					builder.dismiss();
				}
				builder.show();
			}
		});

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

        LinearLayout layout=(LinearLayout)findViewById(R.id.neo_layout);

        if(screen.equalsIgnoreCase("swatchata")) {
            layout.setBackgroundColor(getResources().getColor(R.color.header6));

        }
        else if(screen.equalsIgnoreCase("FPMethod")) {
            layout.setBackgroundColor(getResources().getColor(R.color.header3));

        }
    }

	private void savePref(){
		if(keyToidentify.equals("fp_method")){
			String s="1#0#0#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(this,MiraConstants.FP_Prefrences,keyToidentify,s);
		}
		if(keyToidentify.equals("learning_girl")){
			String s="1#0#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
			MySharedPreference.saveStringInPreference(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify,s);
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
    }
	
	private List<Fragment> getFragments(int index) {

		List<Fragment> fList = new ArrayList<Fragment>();

		int[] images = { R.drawable.b0001,R.drawable.b0001, R.drawable.b0002, R.drawable.b0003,
				R.drawable.b0004, R.drawable.b0005, R.drawable.w06_b };		
		
		boolean language = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?true:false;
		String[][]arr = language?menstrualHygieneHnd:menstrualHygieneEng;
		
		if(index==1){
			int[] imagesFp = { R.drawable.c0001, R.drawable.c0002, R.drawable.c0003,
					R.drawable.c0004, R.drawable.c0005, R.drawable.c0006,R.drawable.c0007 };	
			images = imagesFp;
			arr = language?familyPlaningHnd:familyPlaningEng;
            screen="FPMethod";
		}
		
		for (int i = 0; i < arr.length; i++) {
			Bundle bundle = new Bundle();
            bundle.putString("screen",screen);
			bundle.putInt("image", images[i]);
			bundle.putString("title", arr[i][0]);
			bundle.putString("messege", arr[i][1]);
			if(index==0)bundle.putString("sound", arr[i][3]);
			else bundle.putString("sound", arr[i][5]);
			NeoNatalCareFrag fragment = NeoNatalCareFrag.newInstance(bundle);
			fList.add(fragment);
		}
		
		return fList;
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

    private String[][] menstrualHygieneHnd = {{"मासिक धर्म स्वच्छता", "शारीरिक स्वच्छता के 5  चरण", "B0001", "IND_HI_AH_MH_01"},
            {"रोज़ नहायें", "रोज़ नहायें और खुजली व कीटाणुओं से बचने के लिए अपने सभी अंगो को साफ़ रखें", "B0001", "IND_HI_AH_MH_02"},
            {"नैपकिन का इस्तेमाल", "सेनेटरी नैपकिन का इस्तेमाल करना एक साफ़ और तनाव रहित विधि है और यह मासिक स्राव को सोखने में अधिक सक्षम होता है", "B0002", "IND_HI_AH_MH_03"},
            {"नैपकिन बदलना", "मासिक धर्म के दौरान स्राव को देखते हुए नैपकिन बदलना चाहिये", "B0003", "IND_HI_AH_MH_04"},
            {"नैपकिन को फेंकना", "वातावरण को स्वच्छ रखने के लिए इस्तेमाल के बाद नैपकिन को या तो जला दें या कागज़ में लपेट कर फेंक  दें", "B0004", "IND_HI_AH_MH_05"},
            {"साबुन से हाथ धोना", "कीटाणुओं से बचे रहने के लिए हर बार नैपकिन बदल कर हाथ धोना चाहिये ", "B0005", "IND_HI_AH_MH_06"}};
        private String[][] menstrualHygieneEng = {{"Personal hygiene", "5 steps to personal hygiene.", "B0001", "ME_0"},
            {"Bathe daily ", "Bathe  everyday & keep all the body parts clean to avoid itching & infections", "B0001", "ME_1"},
            {"Use of napkin", "Use of sanitary napkin is a clean & tension free method & helps absorb the menstrual flow in a better manner. ", "B0002", "ME_2"},
            {"Change napkin", "During menstruation sanitary napkin should be changed according to the menstrual flow.", "B0003", "ME_3"},
            {"Disposal of napkin", "To keep the environment clean sanitary napkin should be disposed off either by burning or throwing away wrapped in a paper.", "B0004", "ME_4"},
            {"Wash hands with soap", "To prevent infections wash hands every time you change a sanitary napkin or clothe. ", "B0005", "ME_5"}};
       
        
	String[][] familyPlaningEng = {
			{
					"Lactation Amenorrhea Method (LAM)",
					"Initiate breast feeding within 1 hour of delivery & practice exclusive breast feeding day & night.",
					"Can be used for 6 months but discontinue if menstrual cycle starts",
					"1. Is a natural & reversible method\n2. Protects from pregnancy & baby gets good nutrition.\n3Less bleeding after delivery.",
					"C0001", "FE_11", "FE_12", "FE_13" },
			{
					"Safe days method",
					"Know your menstrual cycle and count the safe days ( 7 days before the menstrual periods and 9 days after the menstrual periods) and avoid having intercourse in other unsafe days  ",
					"If your menstrual cycle is irregular.", "No side effects",
					"C0002", "FE_21", "FE_22", "FE_23" },
			{
					"Combined Oral Contraceptives (OCPs)",
					"Take one pill everyday at the same time ",
					"1. If breastfeeding with 6 months postpartum.\n2. Suffering from heart disease, high B. P, Liver disease or diabetes",
					"1. Feeling of control over contraception as there is immediate return of fertility.\n2. Decreases the risk women related cancers.3. May decrease menstrual bleeding, pelvic pain etc.",
					"C0003", "FE_31", "FE_32", "FE_33" },
			{
					"Emergency Contraception Pills",
					"To be taken within 72 hours of an unprotected intercourse.",
					"Preferably not to be taken after 5 days of an unprotected intercourse.",
					"No harm to ongoing pregnancy or does not cause abortion.",
					"C0004", "FE_41", "FE_42", "FE_43" },
			{
					"Male &female condoms",
					"Used to shield the private parts before an intercourse ",
					"It causes allergy to very less people & consensus of both the partners is important. ",
					"1. Protection from unwanted pregnancy, HIV and STDs.\n2. Are  available free at PHCs",
					"C0005", "FE_51", "FE_52", "FE_53" },
			{
					"IUDs",
					"IUDs like Copper-T are inserted into vaginal tract by expert doctor",
					"When you do not want to delay the next pregnancy.",
					"1. Long term yet reversible.\n2. On removal there is immediate return of fertility.",
					"C0006", "FE_61", "FE_62", "FE_63" },
			{
					"Female sterilization- Tubectomy & Male sterilization –Vasectomy",
					"A small operation is carried out by expert doctors & it protects from AIDs & STDs. ",
					"It is a irreversible method & should be adopted after thinking over it. ",
					"Highly effective & permanent", "C0007", "FE_71", "FE_72",
					"FE_73" } };
	String[][] familyPlaningHnd = {
			{
					"लेक्टेशन अमोनोहरिया विधि (स्तनपान करवाने की विधि)",
					"स्तनपान प्रसव के बाद पहले 1 घंटे में शुरू करें और सुबह शाम केवल स्तनपान करायें।",
					"यह विधि 6 महीने तक इस्तेमाल की जा सकती है लेकिन मासिक धर्म शुरू होते ही इसका इस्तेमाल रोक देना चाहिए ",
					"1. यह एक प्राकृतिक और अस्थायी विधि है।\n2. यह गर्भधारण से बचाता है और इससे बच्चे को सही पोषण मिलता है। \n3. इससे प्रसव के बाद रक्तस्राव कम होता है।",
					"C0001", "IND_HI_FP_AM_00", "IND_HI_FP_AM_01", "IND_HI_FP_AM_02" },
			{
					"मानक दिवस विधि ",
					"अपने मासिक धर्म को जाने और सुरक्षित दिन गिने (मासिक धर्म से सात दिन पहले और सात  दिन बाद )  और बाकी बचे असुरक्षित दिनों में सम्भोग करने से बचे",
					"यदि आपका मासिक धर्म अनियमित है तो इसका इस्तेमाल न करें",
					"इसके कोई दुष्परिणाम नहीं होते", "C0002", "IND_HI_FP_SD_00", "IND_HI_FP_SD_01",
					"IND_HI_FP_SD_02" },
			{
					"कंबाइंड ओरल कॉन्ट्रासेप्टिव्स  (गर्भ निरोध की गोली)",
					"हर रोज़ एक गोली एक ही समय पर",
					"1.यदि आप 6 महीने के प्रसवोत्तर के साथ स्तनपान करा र ही है ै\n2. यदि आप दिल की बीमारी, उच्च रक्तचाप, फेफड़ो की बीमारी या मधुमेह से पीड़ित है ",
					"1. इससे महिलायें गर्भनिरोध पर अपना नियंत्रण बनाये रख सकती है और छोड़ते ही प्रजननशीलता लौट आती हैै\n2. इससे महिलायों में होने वाले कैंसर का खतरा कम हो जाता है\n3. इसके सेवन से मासिक स्राव, पेडू में दर्द आदि कम हो सकती है ",
					"C0003", "IND_HI_FP_OC_00", "IND_HI_FP_OC_01", "IND_HI_FP_OC_02" },
			{
					"आपातकालीन गर्भनिरोधक गोलियाँ",
					"इसे असुरक्षित यौन संबंध के 72  घंटो के अन्दर लेना चाहिए ",
					"इसे असुरक्षित यौन संबंध के 5 दिन बाद नहीं लेना चाहिए",
					"इससे ठहरे हुए गर्भ को कोई नुकसान नही होता और इससे गर्भपात भी नही होता  ",
					"C0004", "IND_HI_FP_EC_00", "IND_HI_FP_EC_01", "IND_HI_FP_EC_02" },
			{
					"पुरुष या महिला कंडोम",
					"इसे यौन संबंध के दौरान अपने गुप्तांगो का बचाव करने के लिए इस्तेमाल करते है ",
					"इससे कम ही लोगो को एलर्जी होती है और इसके लिए आपसी सहमति ज़रूरी होती है ",
					"1. इससे यौन संचारित रोगो, अनचाहे  गर्भधारण और एड्स से बचाव होता है\n3. यह पी.एच.सी पर मुफ्त में मिलता है",
					"C0005", "IND_HI_FP_CO_00", "IND_HI_FP_CO_01", "IND_HI_FP_CO_02" },
			{
					"आई.यू.डी.",
					"आई.यू.डी. जैसे कि कॉपर-टी को डॉक्टर द्वारा बच्चेदानी में लगाया जाता है",
					"जब आप कुछ समय में अपने परिवार को बढ़ाने का सोच रहें हो",
					"1. यह लम्बी अवधि का अस्थायी तरीका है\n3. इसे निकलवाने पर तुरंत प्रजननशीलता की वापसी हो जाती है",
					"C0006", "IND_HI_FP_ID_00", "IND_HI_FP_ID_01", "IND_HI_FP_ID_02" },
			{
					"पुरुष या महिला नसबंदी",
					"इसके लिए डॉक्टर द्वारा एक छोटा ऑपरेशन किया जाता है और यह एड्स और यौन संचारित रोगों से बचाता है ",
					"यह एक स्थायी तरीका है और इसे सोच समझकर अपनाना चाहिए ",
					"यह एक स्थायी और प्रभावी विधि है ", "C0007", "IND_HI_FP_ST_00",
					"IND_HI_FP_ST_01", "IND_HI_FP_ST_02" } };


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		index=arg0;
		updatePref(arg0);

		if(MiraConstants.MEDIA_PLAYER.isPlaying()){
			MiraConstants.MEDIA_PLAYER.stop();
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

	private String timeStamp(){
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String format = s.format(new Date());
		return format;
	}

	private String updatePref(int position){
		String sfinal="N/A";
		String s1="N/A";
		if(keyToidentify.equals("fp_method")){
			s1= MySharedPreference.getStringValue(this,MiraConstants.FP_Prefrences,keyToidentify);
		}
		else if(keyToidentify.equals("learning_girl"))
		{
			s1= MySharedPreference.getStringValue(this,MiraConstants.Adolescent_Girl_Prefrences,keyToidentify);
		}

		if(!s1.equals("0")){
			subscene=s1.split("#");
			String firstPart=s1.substring(0, position * 2);
			String secondPart=s1.substring(position * 2,s1.length()-19);
//              String secondPart=s1.substring(position * 2);
			String lastPart=s1.substring(s1.length()-19,s1.length());



			String [] qp=secondPart.split("#");

			int a=Integer.parseInt(qp[0]) + 1;
			lastPart=timeStamp();
			String n=String.valueOf(a)+secondPart.substring(1);
			System.out.println("Bismillah In NeoNatal."+n);
			sfinal=firstPart+n+lastPart;
//
			System.out.println("In the Upadte ...."  + sfinal);

			System.out.println("In the Upadte ....." + firstPart + "    " + secondPart + "   " + n + "  " + sfinal);
			if(keyToidentify.equals("fp_method")){
				MySharedPreference.saveStringInPreference(this, MiraConstants.FP_Prefrences, keyToidentify, sfinal);
			}
			else if(keyToidentify.equals("learning_girl"))
			{
				MySharedPreference.saveStringInPreference(this, MiraConstants.Adolescent_Girl_Prefrences, keyToidentify, sfinal);
			}

		}
		return sfinal;
	}
}
