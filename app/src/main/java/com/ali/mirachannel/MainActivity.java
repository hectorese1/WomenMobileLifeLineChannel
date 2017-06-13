package com.ali.mirachannel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ali.mirachannel.helper.MySharedPreference;
import com.ali.mirachannel.utility.MiraConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**activit which show the splash screen of application*/
@SuppressLint("NewApi") 
public class MainActivity extends FragmentActivity implements MyDialog.Communicator,LoginDialog.LoginCommunicator{
	List<Address> addresses;
	Geocoder gcd;
	GPSTracker gps;
	public static String serverDate="05/08/2016";
	JSONArray jsonArray;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy");
		String format1 = s1.format(new Date());
        serverDate=format1;
		MiraConstants.parseServerDate(format1);

//		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//		String format = s.format(new Date());
//		jsonArray=new JSONArray();
//
//		System.out.println("MainActivity timeStamp...."+format);
//		String s2="2#1#1#1#1#1#1#1#1#05/08/2016 02:55:03#05/08/2016 02:54:00";
//		System.out.println("LAst TimeStamp...."+s2.substring(s2.length()-19,s2.length()));
//		String arr[]=s2.split("#");    //(s2.substring(0,s2.length()-2)).
//		for(int a=0;a<arr.length;a++){
//			System.out.println("MainActivity splitString...."+arr[a]);
//		}
//		gps = new GPSTracker(MainActivity.this);
////		gcd = new Geocoder(MainActivity.this, Locale.getDefault());
//		// check if GPS enabled
//		if(gps.canGetLocation()){
//             System.out.println("Gps Is Enabled..");
//		}else{
//			// can't get location
//			// GPS or Network is not enabled
//			// Ask user to enable GPS/network in settings
//			gps.showSettingsAlert();
//		}


		MiraConstants.preferences = getSharedPreferences(MiraConstants.PREFERENCES, MODE_PRIVATE);
		
		MiraConstants.USERID = MiraConstants.preferences.getInt("user_id", 0);
		MiraConstants.USERNAME = MiraConstants.preferences.getString("name", "MIRA");
		
		File filex = new File(getExternalCacheDir(), "/WOMENMOBILECHANNEL/HW15_0.mp3" );
		if (filex.exists()) {
		System.out.println(filex);

		}
		SharedPreferences prefA=getSharedPreferences(MiraConstants.Adolescent_Girl_Prefrences,MODE_PRIVATE);

//		MySharedPreference.clearPrefernce(this,MiraConstants.Immuniz_Prefrences);
//		MySharedPreference.clearPrefernce(this,MiraConstants.NeoNatal_Prefrences);
//		MySharedPreference.clearPrefernce(this,MiraConstants.FP_Prefrences);
//		MySharedPreference.clearPrefernce(this,MiraConstants.Adolescent_Girl_Prefrences);
//		MySharedPreference.clearPrefernce(this,MiraConstants.PreNatal_Prefrences);

		Map<String, ?> allEntries = prefA.getAll();
		for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

			System.out.println("values from SharedPref...." + entry.getKey()+ ": " + entry.getValue().toString());

		}
	}
    
    public void onClickSplash(View view) {   
    	
    	if(MiraConstants.preferences.getInt(MiraConstants.ID, -1)!=-1){
    		Intent intent = new Intent();
        	intent.setClass(getBaseContext(), MainMenuActivity.class);
    		startActivity(intent);
    		
    	}
		else{
    		Intent intent = new Intent();
        	intent.setClass(getBaseContext(), LoginActivity.class);
    		startActivity(intent);
    	}    	    	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onDialogMessege(String messege) {
		Toast.makeText(this, messege, Toast.LENGTH_SHORT).show();
		FragmentManager manager = getSupportFragmentManager();
		LoginDialog fragment = new LoginDialog();
		fragment.show(manager, "loginDialog");
	}
    
	@Override
	public void loginMessege(String string) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(getBaseContext(), MainMenuActivity.class);
		startActivity(intent);
//		Toast.makeText(this, "On Pause", Toast.LENGTH_SHORT).show();
	}
}
//    try {
//        PackageInfo info = getPackageManager().getPackageInfo("com.example.mirachannel.MainActivity", PackageManager.GET_SIGNATURES);
//        for (Signature signature : info.signatures) {
//            MessageDigest md = MessageDigest.getInstance("SHA");
//            md.update(signature.toByteArray());
//            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//    } catch (NameNotFoundException e) {
//
//    } catch (NoSuchAlgorithmException e) {
//
//    }

