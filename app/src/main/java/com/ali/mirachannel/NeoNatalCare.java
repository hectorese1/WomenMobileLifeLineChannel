package com.ali.mirachannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ali.mirachannel.utility.MiraConstants;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**activity for neonatal which contains menu for navigation on screens*/
public class NeoNatalCare extends Activity implements OnClickListener{

	private Button btn_neo_sc_0_0;
	private Button btn_neo_sc_0_1;
	private Button btn_neo_sc_0_2;
	
	private Button btn_neo_sc_1_0;
	private Button btn_neo_sc_1_1;
	
	LinearLayout lay_neo_sce_0;
	LinearLayout lay_neo_sce_1;
	String keyToidentify="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_neo_natal_care);	
		lay_neo_sce_0 = (LinearLayout) findViewById(R.id.lay_neo_sce_0);
		
		btn_neo_sc_0_0 = (Button) findViewById(R.id.btn_neo_sc_0_0);
		btn_neo_sc_0_0.setOnClickListener(this);
		btn_neo_sc_0_1 = (Button) findViewById(R.id.btn_neo_sc_0_1);
		btn_neo_sc_0_1.setOnClickListener(this);
		btn_neo_sc_0_2 = (Button) findViewById(R.id.btn_neo_sc_0_2);
		btn_neo_sc_0_2.setOnClickListener(this);
		
		lay_neo_sce_1 = (LinearLayout) findViewById(R.id.lay_neo_sce_1);
		
		btn_neo_sc_1_0 = (Button) findViewById(R.id.btn_neo_sc_1_0);
		btn_neo_sc_1_0.setOnClickListener(this);
		btn_neo_sc_1_1 = (Button) findViewById(R.id.btn_neo_sc_1_1);
		btn_neo_sc_1_1.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.neo_natal_care, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		boolean language = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?true:false;
		
		switch (v.getId()) {
		case R.id.btn_neo_sc_0_0:
			lay_neo_sce_0.setVisibility(View.GONE);
			lay_neo_sce_1.setVisibility(View.VISIBLE);
			break;
			
		case R.id.btn_neo_sc_0_1:
			String title = language? HomebaseddeliveryHnd[0]:HomebaseddeliveryEng[0];
			String messege =language?AboutHnd:AboutEng;
			showDialog(title, messege, 2);
			break;
		case R.id.btn_neo_sc_0_2:
			String title1 = language? DangersignsHnd[0]:DangersignsEng[0];
			String messege1 =language?DangersignsHnd[1]:DangersignsEng[1];
			showDialog(title1, messege1, 1);
			break;


      //******************************************************************************
		case R.id.btn_neo_sc_1_0:
			keyToidentify="delivery_house";
			String title12 = language? HomebaseddeliveryHnd[0]:HomebaseddeliveryEng[0];
			String messege2 =language?HomebaseddeliveryHnd[1]:HomebaseddeliveryEng[1];
			showDialog(title12, messege2, 3);
			break;
		case R.id.btn_neo_sc_1_1:
			keyToidentify="delivery_hospital";
			String title13 = language? InstitutionaldeliveryHnd[0]:InstitutionaldeliveryEng[0];
			String messege3 =language?InstitutionaldeliveryHnd[1]:InstitutionaldeliveryEng[1];
			showDialog(title13, messege3, 4);			
			break;
		default:
			break;
		}
	}
	
	private void showDialog(String title,String messege,final int index) {
		AlertDialog.Builder builder = new AlertDialog.Builder(NeoNatalCare.this);
		builder.setTitle(title);
		builder.setMessage(messege);
		builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"आगे बढ़ें  ":"Next", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();	
				intent.putExtra("index", index);
				switch (index) {
				case 1:
					break;
				case 2 :
					break;
				case 3:
					intent.putExtra("keyToCheck",keyToidentify);
                    intent.putExtra("screen","home");
					break;
				case 4:
					intent.putExtra("keyToCheck",keyToidentify);
                    intent.putExtra("screen","hospital");
					break;
				default:
					break;
				}
				intent.setClass(NeoNatalCare.this, NeoNatalCareGenSce.class);
				startActivity(intent);
			}
		});
		builder.show();
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		
//		if(lay_neo_sce_1.getVisibility()==View.VISIBLE){
//			lay_neo_sce_1.setVisibility(View.GONE);
//			lay_neo_sce_0.setVisibility(View.VISIBLE);
//		}else{
//			super.onBackPressed();
//		}
        super.onBackPressed();
	}
	
	String AboutHnd = "माँ का दूध बच्चे के लिए पूर्ण आहार होता है और माँ का पहला गाढ़ा दूध जिसे कोलोस्ट्रम कहते है बच्चे को इन्फेक्शन, एलर्जी और अन्य बिमारियों से बचाता है। यह ऐप आपको स्तनपान कि अवस्थाएं और अटैचमेंट के चिन्ह जानने में मदद करेगी जिससे कि आप अपने बच्चे को आराम से और अच्छे से स्तनपान करा सके.";
    String AboutEng = "Mother’s milk is considered to be a complete diet for a baby & the first thick milk called colostrum protects the baby from infections, allergy & many other diseases. This application will help you to know how proper positions and attachments can help the mother feed her baby effectively and comfortably. ";

    String[] DangersignsEng = {"Danger signs", "Newborn babies can develop some health problems soon after birth. It is important to identify any sign of danger in the baby so that immediate medical help can be provided. This application will help you to identify 10 most common danger signs in newborn."};
    String[] DangersignsHnd = {"खतरे के लक्षण", "नवजात शिशु को जन्म के तुरंत बाद ही कुछ स्वास्थय सम्बन्धी परेशानियाँ हो सकती है। किसी भी खतरे के चिन्ह को पहचानना आवश्यक होता है, जिससे कि बच्चे को तुरंत चिकित्सा सहायता दी जा सके। यह ऐप/टूल आपको नवजात में खतरे के 10 सबसे सामान्य चिन्हों को पहचानने में मदद करेगा। "};

	
	String[] HomebaseddeliveryEng = {"Home based delivery", "Before giving birth at home there is a need to do extra preparations in order to protect oneself from infections. To ensure safety of both mother and child it is important to have a trained birth attendant for birth.  This application will help you in knowing the basic things required for a home birth."};
    String[] HomebaseddeliveryHnd = {"घर पर प्रसव करना", "घर पर प्रसव करवाने से पहले, संक्रमण से बचने के लिए अतिरिक्त तैयारियों की आवश्यकता होती है। जन्म के समय माता और शिशु दोनों की सुरक्षा के लिए प्रशिक्षित दाई का साथ होना अत्यावश्यक है। यह ऐप/टूल घर पर प्रसव के लिए आवश्यक तैयारी करने में आपकी सहायता करेगा।"};
    String[] InstitutionaldeliveryEng = {"Institutional delivery", "Going for institutional delivery is safe for both mother and child. Before going to a hospital for delivery, you must keep some basic needs things in a bag in advance. This application provides you with a checklist of things that you should keep in your maternity bag for delivery in a hospital."};
    String[] InstitutionaldeliveryHnd = {"अस्पताल में प्रसव करना", "अस्पताल में प्रसव कराना माता तथा बच्चे दोनों के लिए सुरक्षित होता है। प्रसव के लिए जाने से पहले सभी आवश्यक चीज़ें एक बैग में पहले से तैयार रखें। यह ऐप/टूल आपको उन सभी चीज़ों की जाँच सूची देता है जो आपको अस्पताल में प्रसव पूर्व अपने बैग में रखनी चाहिए।"};
  
}
