package com.ali.mirachannel;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.utility.MiraConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**screen to show the details of displaay items*/
public class NeoNatalCareGenSce extends FragmentActivity implements ViewPager.OnPageChangeListener {
	private ViewPager viewPager;
	private MyPageAdapter pageAdapter;

    private ImageButton back,next;
    private int size=0;
    private String screen="";
    String keyToidentify="";
    String[] subSceneNeo;
    String startTimeStamp="N/A",lastTimeStamp="N/A";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neo_natal_care_gen_sce);
		// Show the Up button in the action bar.
		setupActionBar();
startTimeStamp=timeStamp();
        (findViewById(R.id.lay_fp_dodont)).setVisibility(View.INVISIBLE);//By Fz
		viewPager = (ViewPager) findViewById(R.id.viewpager);

        if(getIntent().hasExtra("screen")) {
            screen = getIntent().getExtras().getString("screen");
        }
        keyToidentify=getIntent().getExtras().getString("keyToCheck");
        System.out.println("Screen in NeoNatalCareGenSce...."+screen+"    "+keyToidentify);
        if(screen.equalsIgnoreCase("hospital")) {
            viewPager.setBackgroundColor(getResources().getColor(R.color.bg2));
            (findViewById(R.id.neo_layout)).setBackgroundColor(getResources().getColor(R.color.header2));
        }
        else if(screen.equalsIgnoreCase("home")) {
            viewPager.setBackgroundColor(getResources().getColor(R.color.bg2));
            (findViewById(R.id.neo_layout)).setBackgroundColor(getResources().getColor(R.color.header2));
        }

        else if(screen.equalsIgnoreCase("danger")) {
            viewPager.setBackgroundColor(getResources().getColor(R.color.bg2));
            (findViewById(R.id.neo_layout)).setBackgroundColor(getResources().getColor(R.color.header2));
        }
        else if(screen.equalsIgnoreCase("feeding")) {
            viewPager.setBackgroundColor(getResources().getColor(R.color.bg6));
            (findViewById(R.id.neo_layout)).setBackgroundColor(getResources().getColor(R.color.header6));
        }

		List<Fragment> fragments = getFragments(getIntent().getExtras().getInt("index"));
		 size=fragments.size();

        if(MySharedPreference.getStringValue(this,MiraConstants.NeoNatal_Prefrences,keyToidentify).equals("0"))
        {
            saveInpref();
            System.out.println("Al ready Exist......" + "Muhammad");
        }
        else{
            System.out.println("Al ready Exist......"+"Allah");
            String s1= MySharedPreference.getStringValue(this,MiraConstants.NeoNatal_Prefrences,keyToidentify);
            if(!s1.equals("0")){
                subSceneNeo=s1.split("#");
                int a=Integer.parseInt(subSceneNeo[0]) + 1;
                System.out.println("Bismillah In NeoNatal."+a+ "  "+s1.substring(1));
                String n=String.valueOf(a)+s1.substring(1);
                System.out.println("Bismillah In NeoNatal."+n);
                MySharedPreference.saveStringInPreference(this, MiraConstants.NeoNatal_Prefrences, keyToidentify, n);

            }
        }

		pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pageAdapter);

        viewPager.setOnPageChangeListener(this);



//		viewPager.setCurrentItem(getIntent().getExtras().getInt("weeknumber"));
//		viewPager.setOnPageChangeListener(WeeklyInfo.this);
//		System.out.println(preArrayList);

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

    private void saveInpref(){
        if(keyToidentify.equals("delivery_house")){
            String s="1#0#0#0#0#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
            MySharedPreference.saveStringInPreference(this,MiraConstants.NeoNatal_Prefrences,keyToidentify,s);
        }
        else if(keyToidentify.equals("delivery_hospital"))
        {
            String s="1#0#0#0#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
            MySharedPreference.saveStringInPreference(this,MiraConstants.NeoNatal_Prefrences,keyToidentify,s);
        }
        else if(keyToidentify.equals("feeding_neo")){
            String s="1#0#0#0#0#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
            MySharedPreference.saveStringInPreference(this,MiraConstants.NeoNatal_Prefrences,keyToidentify,s);
        }
        else if(keyToidentify.equals("danger_neo")){
            String s="1#0#0#0#0#0#0#0#0#0#0#0"+"#"+timeStamp()+"#"+timeStamp();
            MySharedPreference.saveStringInPreference(this,MiraConstants.NeoNatal_Prefrences,keyToidentify,s);
        }
    }


      private String updatePref(int position){
          String sfinal="N/A";
          String s1= MySharedPreference.getStringValue(this,MiraConstants.NeoNatal_Prefrences,keyToidentify);
          if(!s1.equals("0")){
              subSceneNeo=s1.split("#");
//              int a=Integer.parseInt(subSceneNeo[position]) + 1;
              String firstPart=s1.substring(0, position * 2);
              String secondPart=s1.substring(position * 2,s1.length()-19);
//              String secondPart=s1.substring(position * 2);
              String lastPart=s1.substring(s1.length()-19,s1.length());

              System.out.println("Lo ji cutt gayi...."+firstPart+"    "+secondPart+"    "+lastPart);

              String [] qp=secondPart.split("#");
//
              int a=Integer.parseInt(qp[0]) + 1;
                  lastPart=timeStamp();

//              System.out.println("Bismillah In NeoNatal."+a+ "  "+secondPart.substring(1));
              String n=String.valueOf(a)+secondPart.substring(1);
              System.out.println("Bismillah In NeoNatal."+n);
              sfinal=firstPart+n+lastPart;
//
              System.out.println("In the Upadte ...."  + sfinal);
              //+ firstPart + "   " + secondPart + "  " + n + "   "+lastPart+"  "
              MySharedPreference.saveStringInPreference(this,MiraConstants.NeoNatal_Prefrences,keyToidentify,sfinal);
          }

          return sfinal;
     }


    private int getItem(int i)
    {
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        String s=updatePref(position);

        System.out.println("OnPage Scrolled...."+position+"   "+s);

    }

	private List<Fragment> getFragments(int index) {
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
				R.drawable.w40_b };

		int[]breastfeed = {R.drawable.breast01,R.drawable.breast02,R.drawable.breast03,
				R.drawable.breast04,R.drawable.breast05,R.drawable.breast06,
				R.drawable.breast07,R.drawable.breast08,R.drawable.breast09};

		int[]dangerSign = {R.drawable.dang00,R.drawable.dang01,R.drawable.dang02,
				R.drawable.dang03,R.drawable.dang04,R.drawable.dang05,
				R.drawable.dang06,R.drawable.dang07,R.drawable.dang08,
				R.drawable.dang09,R.drawable.dang10,R.drawable.dang11};

		int[]homeBased = {R.drawable.home00,R.drawable.home01,
				R.drawable.home02,R.drawable.home03,R.drawable.home04,
				R.drawable.home05,R.drawable.home06,R.drawable.home07,
				R.drawable.checklist,R.drawable.checklist};

		int[]intstDelv = {R.drawable.inst00,R.drawable.inst01,
				R.drawable.inst02,R.drawable.inst03,R.drawable.inst04,
				R.drawable.inst05,R.drawable.inst06,R.drawable.checklist,R.drawable.checklist};

		boolean language = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?true:false;
		String[][]arr = language?HBPNCChecklistHnd:HBPNCChecklistEng;
		switch (index) {
		case 1:
			arr = language?DangerSignsHnd:DangerSignsEng;
			images = dangerSign;
			break;
		case 2:
			images = breastfeed;
			arr = language?BreastFeedingHnd:BreastFeedingEng;
			break;
		case 3:
			images = homeBased;
			arr = language?HBPNCChecklistHnd:HBPNCChecklistEng;
			break;
		case 4:
			images = intstDelv;
			arr = language?InstitutionalChecklistHnd:InstitutionalChecklistEng;
			break;
		default:
			break;
		}

		for (int i = 0; i < arr.length; i++) {
			Bundle bundle = new Bundle();
            bundle.putString("screen",screen);
			bundle.putInt("image", images[i]);
			bundle.putString("title", arr[i][1]);
			bundle.putString("messege", arr[i][2]);
			bundle.putString("sound", arr[i][3]);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.neo_natal_care_gen_sce, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	String[] SLID1Eng = {"Newborn child care", "Choice of delivery", "Danger signs", "Breast Feeding"};
    String[] SLID1Hnd = {"नवजात शिशु देखभाल", "प्रसव का चुनाव", "खतरे के लक्षण", "स्तनपान"};
    String[] SLID2Eng = {"Choice of delivery", "Where do you want to deliver your child?", "Please select", "At home", "At hospital"};
    String[] SLID2Hnd = {"प्रसव का चुनाव", "आप अपना प्रसव कहाँ करवाना चाहती है?", "Please select", "घर में", "अस्पताल में"};
    String[] RemCheckListEng = {"Do you remember all the things now? ", "See the checklist on next page", "Have you taken care of the things given below? If yes please tick"};
    String[] RemCheckListHnd = {"Do you remember all the things now? ", "See the checklist on next page", "Have you taken care of the things given below? If yes please tick"};
//    String[] DangersignsEng = {"Danger signs", "Newborn babies can develop some health problems soon after birth. It is important to identify any sign of danger in the baby so that immediate medical help can be provided. This application will help you to identify 10 most common danger signs in newborn."};
//    String[] DangersignsHnd = {"खतरे के लक्षण", "नवजात शिशु को जन्म के तुरंत बाद ही कुछ स्वास्थय सम्बन्धी परेशानियाँ हो सकती है. किसी भी खतरे के चिन्ह को पहचानना आवश्यक होता है, जिससे की बच्चे को तुरंत चिकित्सा सहायता दी जा सके. यह ऐप/टूल आपको नवजात में खतरे के 10 सबसे सामान्य चिन्हों को पहचानने में मदद करेगा. "};
//    String[] HomebaseddeliveryEng = {"Home based delivery", "Before giving birth at home there is a need to do extra preparations in order to protect oneself from infections. To ensure safety of both mother and child it is important to have a trained birth attendant for birth.  This application will help you in knowing the basic things required for a home birth."};
//    String[] HomebaseddeliveryHnd = {"घर पर प्रसव करना", "घर पर प्रसव करवाने से पहले, संक्रमण से बचने के लिए अतिरिक्त तैयारियों की आवश्यकता होती है. जन्म के समय माता और शिशु दोनों की सुरक्षा के लिए प्रशिक्षित दाई का साथ होना अत्यावश्यक है. यह ऐप/टूल घर पर प्रसव के लिए आवश्यक तैयारी करने में आपकी सहायता करेगा."};
//    String[] InstitutionaldeliveryEng = {"Institutional delivery", "Going for institutional delivery is safe for both mother and child. Before going to a hospital for delivery, you must keep some basic needs things in a bag in advance. This application provides you with a checklist of things that you should keep in your maternity bag for delivery in a hospital."};
//    String[] InstitutionaldeliveryHnd = {"अस्पताल में प्रसव करना", "अस्पताल में प्रसव कराना माता तथा बच्चे दोनों के लिए सुरक्षित होता है. प्रसव के लिए जाने से पहले सभी आवश्यक चीज़ें एक बैग में पहले से तैयार रखें. यह ऐप/टूल आपको उन सभी चीज़ों की जाँच सूची देता है जो आपको अस्पताल में प्रसव पूर्व अपने बैग में रखनी चाहिए."};
//
    String[][] HBPNCChecklistEng = {{"home00", "Safe Home Birth", "Prepare for a  safe delivery at home", "homeEng01"},//0
        {"home01", "Clean room", "Prepare a clean room for delivery", "homeEng02"},//1
        {"home02", "Clean bed sheet ", "Keep a clean bed sheet to cover the mother while delivering baby ", "homeEng03"},//2
        {"home03", "Clean plastic sheet ", "To wrap the placenta keep a clean plastic sheet ready", "homeEng04"},//3
        {"home04", "Plastic Bucket of water", "Keep a bucket full of disinfected boiled water with a mug ", "homeEng05"},//4
        {"home05", "2 bowls", "Keep 2 clean bowls ready- one for washing hands and one for keeping the placenta", "homeEng06"},//5
        {"home06", "Clean blade ", "Keep a new clean blade ready for cutting the umbilical cord of the baby", "homeEng07"},//6
        {"home07", "2-3 threads ", "Get 2-3 threads each 20 cm long to tie the umbilical cord", "homeEng08"},//7
        {"CHECK_LIST", "Check List.", "Things required for a safe home birth", "homeEng10"},//7
    //        Things required for a safe home birth
    };
    String[][] HBPNCChecklistHnd = {{"home00", "सुरक्षित जन्म ", "घर पर सुरक्षित प्रसव के लिए तैयारी करें", "IND_HI_NC_HM_00"},//0
        {"home01", "साफ़ कमरा ", "प्रसव के लिए एक साफ़ कमरा तैयार करें ", "IND_HI_NC_HM_01"},//1
        {"home02", "साफ चादर ", "बच्चा पैदा करते समय ढकने के लिए साफ़ चादर का इंतज़ाम करें ", "IND_HI_NC_HM_02"},//2
        {"home03", "साफ़ प्लास्टिक शीट ", "आंवल लपेटने के लिए एक साफ़ प्लास्टिक शीट पास रखें ", "IND_HI_NC_HM_03"},//3
        {"home04", "बाल्टी और साफ़ पानी ", "हाथ धोने के लिए एक बाल्टी में साफ़ उबला पानी और एक डब्बे का प्रबंध  करें ", "IND_HI_NC_HM_04"},//4
        {"home05", "2 कटोरी ", "2 साफ़ कटोरियाँ पास रखें- एक हाथ धोने के लिए और एक आंवल रखने के लिए ", "IND_HI_NC_HM_05"},//5
        {"home06", "साफ़ ब्लेड  ", "जन्म के बाद शिशु की नाल काटने के लिए एक नया व साफ़ ब्लेड रखें ", "IND_HI_NC_HM_06"},//6
        {"home07", "2-3 लम्बे धागे  ", "काटने के बाद नाल बांधने के लिए 20 सेंटीमीटर लम्बे 2-3 धागों का प्रबंध करें ", "IND_HI_NC_HM_07"},//7
        {"CHECK_LIST", "चेक लिस्ट.", "क्या आपको बताई गई सभी चीज़ें याद हैं? ", "IND_HI_NC_HM_08"},//7
    };
    String[][] InstitutionalChecklistEng = {{"Inst00", "Delivery in Hospital/ aspatal mein prasav", "Prepare for a safe Institutional delivery ", "InstEng01"},//0
        {"Inst01", "Money", "Keep some money in cash with you so that you are prepared for any emergency situation", "InstEng02"},//1
        {"Inst02", "Arranging transport", "Arrange for a vehicle for taking the pregnant to the hospital when labor pain starts.", "InstEng03"},//2
        {"Inst03", "Woolen clothes", "Newborn baby catches cold very fast in order to wrap them properly, keep socks, cap, sweater, small blanket etc.", "InstEng04"},//3
        {"Inst04", "Cotton nappies", "Keep at least 6-8 cotton nappies for the baby as they urinate frequently", "InstEng05"},//4
        {"Inst05", "Mother’s clothing", "Keep comfortable clothes for yourself like nightgown or salwar-suit", "InstEng06"},//5
        {"Inst06", "A bag ", "Keep a soap, comb,toothbrush and sanitary pads with you for  basic hygiene and cleanliness", "InstEng07"},//6
        {"CHECK_LIST", "Check List", "Things required for a safe delivery at hospital-", "InstEng09"},//6
    };
    String[][] InstitutionalChecklistHnd = {{"Inst00", "अस्पताल में प्रसव", "अस्पताल में सुरक्षित प्रसव के लिए तैयारियाँ करें", "IND_HI_NC_HO_00"},//0
        {"Inst01", "पैसे", "अपने पास कुछ पैसे अवश्य रखें ताकि आप किसी भी आपातकालीन स्थिति के लिए तैयार रहें.", "IND_HI_NC_HO_01"},//1
        {"Inst02", "परिवहन की व्यवस्था", "प्रसव के समय गर्भवती औरत को अस्पताल ले जाने के लिए परिवहन की व्यवस्था रखें", "IND_HI_NC_HO_02"},//2
        {"Inst03", "गर्म कपड़े", "नवजात शिशु कोसर्दी-ज़ुखाम जल्दी हो जाता है इसलिए उसे लपेटकर रखने के लिए मोज़े, टोपी, स्वेटर, छोटा कंबल आदि का प्रबंध करें.", "IND_HI_NC_HO_03"},//3
        {"Inst04", "सूती कपड़े की लंगोट", "नवजात शिशुबार-बार  पेशाब करतें है इसलिए कम से कम 6-8 सूती लंगोट रखें.", "IND_HI_NC_HO_04"},//4
        {"Inst05", "महिला के कपड़े", "अपने लिए आरामदायक कपड़े रखें जैसे कि सलवार-कमीज़", "IND_HI_NC_HO_05"},//5
        {"Inst06", "एक बैग ", "अपनी स्वच्छता बनाये रखने के लिए साबुन, कंघी, टूथब्रश और सेनेटरी पैड साथ रखें.", "IND_HI_NC_HO_06"},//6
        {"CHECK_LIST", "चेक लिस्ट", "क्या आपको सब बातें याद है?", "IND_HI_NC_HO_07"},//6
    };
    String[][] DangerSignsEng = {{"Dang00", "Newborn Health", "Identify most common Danger Signs in Newborn Babies and refer them to doctor. ", "DangEng01"},//0
        {"Dang01", "Convulsions", "If you observe any abnormal activity or stiffness in limbs or eyes of the baby, immediately seek doctor’s advice ", "DangEng02"},//1
        {"Dang02", "Not sucking milk", "If the baby is not able to suck milk from mother’s breast seek doctor’s advice", "DangEng03"},//2
        {"Dang03", "Fast breathing ", "If the baby’s breathing rate is more than 60 per minute it is a danger sign. You can check this by counting the falling and rising of chest of baby in 1 minute. One falling and rising makes one breath. Count twice to be sure.", "DangEng04"},//3
        {"Dang04", "Chest in drawing", "Chest in-drawing indicates breathing problem in the baby. Look at the baby’s chest, if the chest goes inside when he breathes and a pit forms it is a danger sign. See a doctor immediately ", "DangEng05"},//4
        {"Dang05", "Skin pustules ", "If you see many pustules or one large boil on baby’s skin see a doctor. ", "DangEng06"},//5
        {"Dang06", "Body temperature", "Check the body temperature of the baby by using a thermometer. If the body temperature of baby is above 37.5 degrees C or less than 35.4 degrees C immediately see a doctor", "DangEng07"},//6
        {"Dang07", "Poor Activity", "If the baby appears to be inactive- not kicking or moving hands it can be a danger sign. Take it to the doctor immediately ", "DangEng08"},//7
        {"Dang08", "Yellow palms/soles", "See the palms and soles of the baby if they are yellow. Press with a thumb and release, if the skin color remains yellow even after releasing it is a danger sign indicating jaundice ", "DangEng09"},//8
        {"Dang09", "Less Weight", "Weigh your baby, if its weight is less than 2 kg it is a danger sign", "DangEng10"},//9
        {"Dang10", "Blue lips/tongue", "If the baby’s lips or tongue is bluish in color take it to the doctor immediately ", "DangEng11"},//10
        {"Dang11", " ", "Refer to Doctor if any of the above signs are seen", "DangEng12"},//11
    //        {"", ""}//12
    };
    String[][] DangerSignsHnd = {{"Dang00", "नवजात शिशु स्वास्थय", "नवजात शिशुओं में कुछ सामान्य खतरे के चिन्ह पहचाने और उन्हें तुरंत डॉक्टर को दिखायें", "IND_HI_NC_DS_00"},//0
        {"Dang01", "ऐंठन", "यदि बच्चे में कोई असामान्य गतिविधि या अंगो या आँखों में अकड़न दिखाई दे, तो तुरंत डॉक्टर की सलाह लें  ", "IND_HI_NC_DS_01"},//1
        {"Dang02", "बच्चे का दूध न पीना", "यदि बच्चा माँ के स्तन से दूध नहीं पी रहा है तो डॉक्टर की सलाह लें", "IND_HI_NC_DS_02"},//2
        {"Dang03", "साँस तेज़ चलना ", "यदि बच्चे का श्वास दर 60 प्रति मिनट से ज्यादा है तो डॉक्टर की सलाह लें", "IND_HI_NC_DS_03"},//3
        {"Dang04", "पसलियाँ चलना", "पसलियों का चलना बच्चों में साँस की दिक्कत का संकेत देता है। बच्चे की छाती यदि साँस लेते हुए अन्दर जाकर गड्ढा सा बनाती है तो तुरंत डॉक्टर को दिखायें ", "IND_HI_NC_DS_04"},//4
        {"Dang05", "त्वचा पर फुंसियाँ (मवाद या छाले)", "यदि आप बच्चे की त्वचा पर कई फुंसियाँ या एक बड़ा फोड़ा देखें तो डॉक्टर को दिखायें ", "IND_HI_NC_DS_05"},//5
        {"Dang06", "बच्चे का तापमान ", "थर्मामीटर की मदद से बच्चे का तापमान जांचे। यदि शरीर का तापमान 37.5 डिग्री सेल्सिउस से ज्यादा या 35.4 डिग्री सेल्सिउस से कम है तो तुरंत डॉक्टर को दिखायें ", "IND_HI_NC_DS_06"},//6
        {"Dang07", "सुस्त होना ", "यदि बच्चा सुस्त दिखता है और ज़्यादा हाथ पैर नहीं हिला रहा तो ये खतरे का चिन्ह हो सकता है। उसे डॉक्टर के पास ले जायें", "IND_HI_NC_DS_07"},//7
        {"Dang08", "पीले हथेली व पैर", "बच्चे की हथेली व तलवों के पीलेपन की जाँच करें। अंगूठे से दबाएँ और छोड़े, यदि त्वचा छोड़ने के बाद भी पीली रहती है तो यह पीलिये का संकेत है", "IND_HI_NC_DS_08"},//8
        {"Dang09", "कम वज़न ", "अपने बच्चे का वज़न जाँचें, यदि वज़न 2 किलोग्राम से कम है तो यह खतरे का चिन्ह है ", "IND_HI_NC_DS_09"},//9
        {"Dang10", "नीले होंठ व जीभ ", "यदि बच्चे के होंठ व जीभ नीली हो तो बच्चे को तुरंत डॉक्टर के पास ले जायें", "IND_HI_NC_DS_10"},//10
        {"Dang11", "", "यदि ऊपर दिए गए चिन्ह दिखें तो डॉक्टर के पास जायें", "IND_HI_NC_DS_11"},//11
    };
//    Heading,Content,Image,Sound
    String[][] BreastFeedingEng = {{"breast01","Positions of breastfeeding & signs of attachment", "Basic information on breastfeeding", "BE_0"},
        {"breast01","A low sitting position ", "While sitting and feeding, the mother should sit in a low position with her back well supported and baby positioned in a straight line", "BE_1"},//1
        {"breast01","Support with a pillow ", "To provide support to the baby mother can use a pillow below baby’s head", "BE_2"},//2
        {"breast01","Give correct guidance", "To ensure comfortable breastfeeding mother should use one hand to hold other hand to support ", "BE_3"},//3
        {"breast01","Lying sideways ", "While lying and breastfeeding the mother should be positioned sideways to support the head of the baby.", "BE_4"},//4
        {"breast01","Chin touching the breast", "To ensure proper breastfeeding to your baby, check that baby’s chin should be touching the breast ",  "BE_5"},//6
        {"breast01","Mouth widely open", "Check that baby’s mouth is widely open  before you put your nipple inside its mouth",  "BE_6"},//6
        {"breast01","Lower lip turned outside", "If the baby’s lower lip is turned towards outside while suckling your milk, it shows proper attachment",  "BE_7"},//7
        {"breast01","More part of areola visible above ", "While breastfeeding check that more part of areola or dark part around nipple should be visible above baby’s mouth than below.",  "BE_8"}//8
    };
    String[][] BreastFeedingHnd = {{"breast01","स्तनपान कि अवस्था और लगाव के चिन्ह", "स्तनपान के बारे में कुछ मूल बातें",  "IND_HI_NC_BF_00"},
        {"breast01","नीचे होकर बैठे", "बैठे हुए स्तनपान  कराते  समय माँ थोड़ा नीचे होके  बैठें जिससे कि माँ की पीठ को सहारा मिले और बच्चा सीधी अवस्था में रहे.",  "IND_HI_NC_BF_01"},//1
        {"breast01","तकिये से सहारा दे", "बच्चे को सहारा देने के लिए उसके सिर के नीचे तकिया  लगायें या हाथ से सहारा दें",  "IND_HI_NC_BF_02"},//2
        {"breast01","सही  मार्गदर्शन देना", "आरामदायक स्तनपान कराने के लिए माँ  एक हाथ से बच्चे को पकड़ें और दूसरे से स्तन को सहारा देकर स्तनपान करायें",  "IND_HI_NC_BF_03"},//3
        {"breast01","टेढ़ा होकर लेटना", "लेट कर स्तनपान कराते समय माँ  टेढ़ी हो कर लेटे जिससे कि बच्चे के  सिर को सहारा मिले",  "IND_HI_NC_BF_04"},//4
        {"breast01","ठोड़ी स्तन को  छुए", "सही स्तनपान के लिए देखें कि बच्चे की ठोड़ी माँ के स्तन को छू  रही है",  "IND_HI_NC_BF_05"},//6
        {"breast01","मुंह अच्छे से खुला हुआ है", "ध्यान दे कि बच्चे के मुंह में निप्पल देते हुए उसका मुंह अच्छे से खुला हुआ हैै", "IND_HI_NC_BF_06"},//6
        {"breast01","होंठ बाहर को मुड़ा हुआ", "स्तनपान कराते समय बच्चे का निचला होंठ बाहर की तरफ मुड़ा होना चाहिए",  "IND_HI_NC_BF_07"},//7
        {"breast01","एरिओला मुंह के ऊपर ज़्यादा दिखे", "बच्चे को दूध पिलाते हुए ध्यान रखें कि एरिओला या भूरा घेरा बच्चे के मुंह के नीचे से ज़्यादा ऊपर दिखना चाहिए", "IND_HI_NC_BF_08"}//8
    };


}
