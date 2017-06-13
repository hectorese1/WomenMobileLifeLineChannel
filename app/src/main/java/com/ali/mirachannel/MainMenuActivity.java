package com.ali.mirachannel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.HouseRegistration.HouseRegComm;
import com.ali.mirachannel.ImmunizMenu.ImmunzMenuComm;
import com.ali.mirachannel.ImmunzRegistration.ImmunzRegistrationComm;
import com.ali.mirachannel.MainMenuFragment.CommunicatorMainMenu;
import com.ali.mirachannel.PrenatalMenu.PrenatalMenuComm;
import com.ali.mirachannel.PrenatalRegistration.PreNatalComm;
import com.ali.mirachannel.PrenatalWomenList.PrenatalWomenListComm;
import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.model.CloseCaseDtl;
import com.ali.mirachannel.model.FamilyPlanning;
import com.ali.mirachannel.model.HouseDtl;
import com.ali.mirachannel.model.PregnantDtl;
import com.ali.mirachannel.model.VaccinatedDtl;
import com.ali.mirachannel.model.VaccineDtl;
import com.ali.mirachannel.model.WeeklyDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;
import com.fz.mirachannel.AdolscentMenu;
import com.fz.mirachannel.FamilyPlanningMenu;
import com.fz.mirachannel.FamilyPlanningMenu.FamilyPlanningMenuComm;
import com.fz.mirachannel.NatalCareMenu;
import com.fz.mirachannel.NatalCareMenu.NatalCareMenuComm;
import com.fz.mirachannel.AdolscentMenu.AdolscentMenuComm;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
/**this is the main activity of application which includes many functions for app*/
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class MainMenuActivity extends ActionBarActivity implements
		CommunicatorMainMenu, PrenatalMenuComm, ImmunzMenuComm, PreNatalComm,
		HouseRegComm, PrenatalWomenListComm,ImmunzRegistrationComm,NatalCareMenuComm ,FamilyPlanningMenuComm,AdolscentMenuComm {
		
	/**listView show the list of side menu*/
	public ListView listView;
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle drawerListner;
	FragmentManager manager;
	public int indication=0;
	boolean trackException=false;
	/**instance of database helper */
	public DatabaseHelper databaseHelper;
	public static final String Default="N/A";
	List<Address> addresses;
	Geocoder gcd,gcd1;
	GPSTracker gps,gps1;
	public static double latitude=0.0;
	public static double longitude=0.0;
	JSONArray jsonArrayPre,jsonArrayImmu;
	JSONObject jsonObjectMain,jsonObjectNeo,jsonObjectFp,jsonObjectAdo;

	private Menu menu;


	public SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu_drawer);
		databaseHelper = DatabaseHelper.newInstance(getApplicationContext());
		jsonObjectMain=new JSONObject();

//		gps = new GPSTracker(MainMenuActivity.this);
//////		gcd = new Geocoder(MainActivity.this, Locale.getDefault());
////		// check if GPS enabled
//		if(gps.canGetLocation()){
//             System.out.println("Gps Is Enabled..");
////			latitude = gps.getLatitude();
////			 longitude = gps.getLongitude();
//		}else{
//			// can't get location
//			// GPS or Network is not enabled
//			// Ask user to enable GPS/network in settings
//			gps.showSettingsAlert();
//		}
//


		////////////////////////////Faisal Gps/////////////////////////
//		gps = new GPSTracker(MainMenuActivity.this);
//		gcd = new Geocoder(MainMenuActivity.this, Locale.getDefault());
//		// check if GPS enabled
//		if(gps.canGetLocation()){
//
//			 latitude = gps.getLatitude();
//			 longitude = gps.getLongitude();
//
//		}else{
////			 can't get location
////			 GPS or Network is not enabled
////			 Ask user to enable GPS/network in settings
//			gps.showSettingsAlert();
//		}
		////////////////////////////////////Faisal Gps//////////////////

		List<VaccineDtl>dtls = databaseHelper.getVaccineByTag("");
		if(dtls.size()<=0){
			MiraConstants.vaccineRules(databaseHelper);
			System.out.println("We are in the vaccineRule....");
		}
		manager = getSupportFragmentManager();		
		listView = (ListView) findViewById(R.id.drawerList);
//		MyAdapter adapter = new MyAdapter(this);
//		listView.setAdapter(adapter);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerListner = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
			@Override
			public void onDrawerClosed(View drawerView) {
//				getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
				super.onDrawerClosed(drawerView);

			}
			@Override
			public void onDrawerOpened(View drawerView) {
				
				super.onDrawerOpened(drawerView);
				checkDataBaseRecord();
				MyAdapter adapter = new MyAdapter(MainMenuActivity.this);
				listView.setAdapter(adapter);
			}
		};
		
		drawerLayout.setDrawerListener(drawerListner);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);				
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
					listView.setItemChecked(position, true);
//					Toast.makeText(getBaseContext(), "Clicked", Toast.LENGTH_SHORT).show();
//					getSupportActionBar().setTitle(getResources().getStringArray(R.array.drawerItems)[position]);
					switch (position) {
					case 0:
						drawerLayout.closeDrawers();
						AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
						String title = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"भाषा का चयन करें":"Select language";
						builder.setTitle(title);						
						builder.setItems(new String[]{"Hindi/हिन्दी","English"}, new OnClickListener() {
							
							@Override
								public void onClick(DialogInterface dialog,int which) {
									switch (which) {
									case 0:
										MiraConstants.LANGUAGE = MiraConstants.HINDI;
										MainMenuFragment fragment = new MainMenuFragment();
										FragmentTransaction transaction  = manager.beginTransaction();
										transaction.replace(R.id.mainContent, fragment, "mainMenu");
										transaction.commit();

										Locale locale = new Locale("hi");
										Locale.setDefault(locale);
										Configuration config = new Configuration();
										config.locale = locale;
										getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//										Toast.makeText(this, "Locale in English !", Toast.LENGTH_LONG).show();
										break;
									case 1:
										MiraConstants.LANGUAGE = MiraConstants.ENGLISH;
										MainMenuFragment fragment1 = new MainMenuFragment();
										FragmentTransaction transaction1  = manager.beginTransaction();
										transaction1.replace(R.id.mainContent, fragment1, "mainMenu");
										transaction1.commit();

										Locale locale1 = new Locale("en");
										Locale.setDefault(locale1);
										Configuration config1 = new Configuration();
										config1.locale = locale1;
										getBaseContext().getResources().updateConfiguration(config1, getBaseContext().getResources().getDisplayMetrics());
//										Toast.makeText(this, "Locale in English !", Toast.LENGTH_LONG).show();
										break;
									default:
										break;
									}
								}
							});
						builder.show();
						break;
					case 1:
						drawerLayout.closeDrawers();
						checkInterNet();
//						new Upload().execute();
						break;
					case 2:
						drawerLayout.closeDrawers();
						refreshWomenRecord();
						break;
					case 3:
//						MyApplication application = MyApplication.getInstance();
//						application.clearApplicationData();
//						clearApplicationData();
//						finish();
//						Intent intent = new Intent();
//						intent.setClass(MainMenuActivity.this, LoginActivity.class);
//						startActivity(intent);
						break;
						case 4:
//							drawerLayout.closeDrawers();
//
//			 SharedPreferences sharedPreferences=getSharedPreferences("User_Data", Context.MODE_PRIVATE);
//           String name=  sharedPreferences.getString("email", Default);
//           String pass= sharedPreferences.getString("pwrd", Default);
//            if(name.equals(Default)||pass.equals(Default)){
//                Toast.makeText(getBaseContext(),"No data found",Toast.LENGTH_SHORT).show();
//            }
//            else{
//              Toast.makeText(getBaseContext(),"Yes data found "+name+"  "+pass,Toast.LENGTH_SHORT).show();
//            }

					default:
						break;
					}
			}
		});	
		if(savedInstanceState==null){
			MainMenuFragment fragment = new MainMenuFragment();
			FragmentTransaction transaction  = manager.beginTransaction();		
			transaction.replace(R.id.mainContent, fragment, "mainMenu");
			transaction.commit();
		}
		makeActionOverflowMenuShown();
		getSupportActionBar().setTitle("Name-"+MiraConstants.USERNAME);//"MIRA ID-"+MiraConstants.USERID+
//		getSupportActionBar().setTitle(getResources().getString(MiraConstants.preferences.getInt("user_id", 0))+" "+MiraConstants.preferences.getString("name", "MIRA"));
	}
 @Override
 protected void onResume(){
	 super.onResume();

 }

	private void makeActionOverflowMenuShown() {
		//devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			Log.d("TAg", e.getLocalizedMessage());
		}
	}

	void checkInterNet(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(wifiNetwork != null && wifiNetwork.isConnected() || mobileNetwork != null && mobileNetwork.isConnected()){
			new Upload().execute();
		}
		else{
			AlertDialog.Builder builder=new AlertDialog.Builder(MainMenuActivity.this);
			builder.setTitle("Alert...!!!!");
			builder.setIcon(R.drawable.alert_icon);
			builder.setMessage(" No internet connectivity. Please try after some time.");
			builder.setPositiveButton("Ok", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			builder.show();
		}
	}

	//*******show toast from asynTask respone*******
	void showToast(final String msg){
		MainMenuActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainMenuActivity.this, "SuccessFully Submitted " + msg + " Details",
						Toast.LENGTH_SHORT).show();
				        indication = 0;
			}
		});
	}



	protected void checkDataBaseRecord() {
		System.out.println("We are in the checkDataBaseRecord");
		final List<HouseDtl>houseDtls = databaseHelper.gethouseByTag("houseid=0");

		final List<WomenDtl>womenDtls = databaseHelper.getWomenByTag("womanid=0");

		final List<ChildDtl>childDtls = databaseHelper.getChildByTag("childid=0");
		String tag_name = "SELECT *FROM closecase WHERE uploaded = '0'";
		List<CloseCaseDtl> closeCaseDtls = databaseHelper.getCloseCaseStatusByTag(tag_name);
		String tag_name1="SELECT* FROM tabvaccinestatus WHERE uploaded='0'";
		final List<VaccinatedDtl>vaccinatedDtls = databaseHelper.getvaccStatusByTag(tag_name1);
		final HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String,String>>();
		for (VaccinatedDtl vacdtl : vaccinatedDtls) {
			if(map.containsKey(vacdtl.getChildId())){
				HashMap<String, String>hashMap = map.get(vacdtl.getChildId());
				hashMap.put(vacdtl.getName(), vacdtl.getDate());
				map.put(vacdtl.getChildId(), hashMap);
			}else{
				HashMap<String, String>hashMap = new HashMap<String, String>();
				hashMap.put(vacdtl.getName(), vacdtl.getDate());
				map.put(vacdtl.getChildId(), hashMap);
			}
		}

		String queryString0 = "SELECT *FROM tabweekinfo WHERE quesone = 0 AND questwo = 0 AND questhree = 0 AND quesfour = 0 AND quesfive = 0 AND uploaded=0";
		final List<WeeklyDtl>weeklyDtls0 = databaseHelper.getWeeklyDetailsByTag(queryString0);
		String queryString1 = "SELECT *FROM tabweekinfo WHERE (quesone != '0' OR questwo != '0' OR questhree != '0' OR quesfour != '0' OR quesfive != '0') AND uploaded='0'";
		final List<WeeklyDtl>weeklyDtls1 = databaseHelper.getWeeklyDetailsByTag(queryString1);

		if(houseDtls.size()>0||womenDtls.size()>0||childDtls.size()>0||closeCaseDtls.size()>0||map.size()>0||weeklyDtls0.size()>0||weeklyDtls1.size()>0){
			indication=1;
			System.out.println("Sizes........"+houseDtls.size()+"   "+womenDtls.size()+"    "+childDtls.size());
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerListner.syncState();
	}

		
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		this.menu=menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

//				menu.getItem(0).setTitle(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI) ? "रिफ्रेश डिवाइस " : "Refresh Device");
//		menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.a_04));
	        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawerListner.onOptionsItemSelected(item)){
			return true;
		}
		switch (item.getItemId()) {
		case R.id.setting:
			drawerListner.onOptionsItemSelected(item);
			String alrt=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"Alert!":"Alert!";
			String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"क्या आप  डिवाइस रिफ्रेश करना चाहते हैं?":"Do you want to refresh the device?";
			String yes=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"हाँ":"Yes";
			String no=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नहीं ":"No";
			AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
			builder.setTitle(alrt);
			builder.setMessage(msg);
			builder.setPositiveButton(yes, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					confirmation();
				}
			});
			builder.setNegativeButton(no, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			builder.show();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private  void confirmation(){
		AlertDialog.Builder builder= new AlertDialog.Builder(MainMenuActivity.this);
		String alrt=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"Confirmation!":"Confirmation!";
		String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"क्या आप निश्चित ही डिवाइस रिफ्रेश करना चाहते हैं?":"Are you sure??";
		String yes=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"हाँ":"Yes";
		String no=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नहीं ":"No";
		builder.setTitle(alrt);
		builder.setMessage(msg);
		builder.setPositiveButton(yes, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				clearApplicationData();
				finish();
				Intent intent = new Intent();
				intent.setClass(MainMenuActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		builder.setNegativeButton(no, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();
	}


    /**adapeter for display list items for a class*/
	class MyAdapter extends BaseAdapter{
		String[]listItems;

		int[]image = {R.drawable.a_01,R.drawable.a_02,R.drawable.a_03};   ///,R.drawable.a_04
		int []images1={R.drawable.a_01,R.drawable.a_02_red02,R.drawable.a_03}; ////,R.drawable.a_04

		Context context;
		public MyAdapter(Context context) {
			this.context = context;
			listItems = context.getResources().getStringArray(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?R.array.drawerItems_H:R.array.drawerItems);
		}
		
		@Override
		public int getCount() {
			return listItems.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = null;
			if(convertView==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.drawerlist_items, parent, false);
			}else{
				rowView = convertView;
			}

			TextView textView = (TextView) rowView.findViewById(R.id.textView);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
			
			textView.setText(listItems[position]);
			if(indication!=1) {
				imageView.setImageResource(image[position]);
			}
			else{
				imageView.setImageResource(images1[position]);

			}
			
			return rowView;
		}
	}
	/**this will attach and deattach the fragment on the basis of their selected posiotion*/
	@Override
	public int getItemId(int position) {
		FragmentTransaction transaction  = manager.beginTransaction();

		switch (position) {
		case 0:
			////////////////////////////Faisal Gps/////////////////////////
		gps1= new GPSTracker(getApplicationContext());
//		gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
		// check if GPS enabled
		if(gps1.canGetLocation()){

			 latitude = gps1.getLatitude();
			 longitude = gps1.getLongitude();

		}
// else{
////			 can't get location
////			 GPS or Network is not enabled
////			 Ask user to enable GPS/network in settings
//			gps.showSettingsAlert();
//		}
			////////////////////////////////////Faisal Gps//////////////////

			HouseRegistration houseRegistration = new HouseRegistration();
			transaction.replace(R.id.mainContent, houseRegistration, "houseRegistration");
			transaction.addToBackStack("houseRegistration");
			transaction.commit();
			break;
		case 1:

			HouseList houseList = new HouseList();			
			transaction.replace(R.id.mainContent, houseList, "houseList");
			transaction.addToBackStack("houseList");
			transaction.commit();
			break;
		case 2:
			PrenatalMenu prenatalMenu = new PrenatalMenu();
			transaction.replace(R.id.mainContent, prenatalMenu, "prenatalMenu");
			transaction.addToBackStack("prenatalMenu");
			transaction.commit();
			break;
		case 3:

			ImmunizMenu immunizMenu = new ImmunizMenu();
			transaction.replace(R.id.mainContent, immunizMenu, "immunzlMenu");
			transaction.addToBackStack("immunzlMenu");
			transaction.commit();

			break;
		case 4:
//			Intent intent = new Intent();
//			intent.setClass(MainMenuActivity.this, NeoNatalCare.class);
//			startActivity(intent);


            NatalCareMenu natalCareMenu = new NatalCareMenu();
            transaction.replace(R.id.mainContent, natalCareMenu, "natalCareMenu");
            transaction.addToBackStack("natalCareMenu");
            transaction.commit();
			break;

		case 5:// Family Planning
			
//			Intent intentFP = new Intent();
//			intentFP.putExtra("adolecent", true);
//			intentFP.setClass(MainMenuActivity.this, AdolescentGirl.class);
//			startActivity(intentFP);


            FamilyPlanningMenu familyPlanningMenu = new FamilyPlanningMenu();
            transaction.replace(R.id.mainContent, familyPlanningMenu, "familyPlanningMenu");
            transaction.addToBackStack("familyPlanningMenu");
            transaction.commit();
			break;
			
		case 6:
//			Intent intentAdolecent = new Intent();
//			intentAdolecent.setClass(MainMenuActivity.this, AdolescentGirl.class);
//			startActivity(intentAdolecent);

            AdolscentMenu adolscentMenu = new AdolscentMenu();
            transaction.replace(R.id.mainContent, adolscentMenu, "adolscentMenu");
            transaction.addToBackStack("adolscentMenu");
            transaction.commit();
			break;
		case 7:

			break;
		default:
			break;
		}
		return 0;
	}

    @Override
    public void getNatalMenuItemId(int index) {
//        String AboutHnd = "माँ का दूध बच्चे के लिए पूर्ण आहार होता है और माँ का पहला गाढ़ा दूध जिसे कोलोस्ट्रम कहते है बच्चे को इन्फेक्शन, एलर्जी और अन्य बिमारियों से बचाता है। यह ऐप आपको स्तनपान कि अवस्थायें और अटैचमेंट के चिन्ह जानने में मदद करेगी जिससे कि आप अपने बच्चे को आराम से और अच्छे से स्तनपान करा सके।";
//        String AboutEng = "Mother’s milk is considered to be a complete diet for a baby & the first thick milk called colostrum protects the baby from infections, allergy & many other diseases. This application will help you to know how proper positions and attachments can help the mother feed her baby effectively and comfortably. ";

		String[] AboutHnd ={"स्तनपान", "माँ का दूध बच्चे के लिए पूर्ण आहार होता है और माँ का पहला गाढ़ा दूध जिसे कोलोस्ट्रम कहते है बच्चे को इन्फेक्शन, एलर्जी और अन्य बिमारियों से बचाता है। यह ऐप आपको स्तनपान कि अवस्थायें और अटैचमेंट के चिन्ह जानने में मदद करेगी जिससे कि आप अपने बच्चे को आराम से और अच्छे से स्तनपान करा सके।"};
		String[] AboutEng ={"Breast Feeding", "Mother’s milk is considered to be a complete diet for a baby & the first thick milk called colostrum protects the baby from infections, allergy & many other diseases. This application will help you to know how proper positions and attachments can help the mother feed her baby effectively and comfortably. "};


        String[] DangersignsEng = {"Danger signs", "Newborn babies can develop some health problems soon after birth. It is important to identify any sign of danger in the baby so that immediate medical help can be provided. This application will help you to identify 10 most common danger signs in newborn."};
        String[] DangersignsHnd = {"खतरे के लक्षण", "नवजात शिशु को जन्म के तुरंत बाद ही कुछ स्वास्थय सम्बन्धी परेशानियाँ हो सकती है. किसी भी खतरे के चिन्ह को पहचानना आवश्यक होता है, जिससे की बच्चे को तुरंत चिकित्सा सहायता दी जा सके. यह ऐप/टूल आपको नवजात में खतरे के 10 सबसे सामान्य चिन्हों को पहचानने में मदद करेगा. "};


        String[] HomebaseddeliveryEng = {"Home based delivery", "Before giving birth at home there is a need to do extra preparations in order to protect oneself from infections. To ensure safety of both mother and child it is important to have a trained birth attendant for birth.  This application will help you in knowing the basic things required for a home birth."};
        String[] HomebaseddeliveryHnd = {"घर पर प्रसव करना", "घर पर प्रसव करवाने से पहले, संक्रमण से बचने के लिए अतिरिक्त तैयारियों की आवश्यकता होती है. जन्म के समय माता और शिशु दोनों की सुरक्षा के लिए प्रशिक्षित दाई का साथ होना अत्यावश्यक है. यह ऐप/टूल घर पर प्रसव के लिए आवश्यक तैयारी करने में आपकी सहायता करेगा."};
        String[] InstitutionaldeliveryEng = {"Institutional delivery", "Going for institutional delivery is safe for both mother and child. Before going to a hospital for delivery, you must keep some basic needs things in a bag in advance. This application provides you with a checklist of things that you should keep in your maternity bag for delivery in a hospital."};
        String[] InstitutionaldeliveryHnd = {"अस्पताल में प्रसव करना", "अस्पताल में प्रसव कराना माता तथा बच्चे दोनों के लिए सुरक्षित होता है. प्रसव के लिए जाने से पहले सभी आवश्यक चीज़ें एक बैग में पहले से तैयार रखें. यह ऐप/टूल आपको उन सभी चीज़ों की जाँच सूची देता है जो आपको अस्पताल में प्रसव पूर्व अपने बैग में रखनी चाहिए."};

        boolean language = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?true:false;
        switch (index) {
            case 0:
                    Intent intent = new Intent();
                    intent.putExtra("screen","chunaw");
                    intent.setClass(MainMenuActivity.this, NeoNatalCare.class);
                    startActivity(intent);
                break;

            case 1:
                String title = language? AboutHnd[0]:AboutEng[0];
                String messege =language?AboutHnd[1]:AboutEng[1];
                showDialog(title, messege, 2);
                break;

            case 2:
                String title1 = language? DangersignsHnd[0]:DangersignsEng[0];
                String messege1 =language?DangersignsHnd[1]:DangersignsEng[1];
                showDialog(title1, messege1, 1);
                break;
            default:
                break;
        }
    }
	String keyToidentify="";
    private void showDialog(String title,String messege,final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setTitle(title);
        builder.setMessage(messege);
        builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"आगे बढ़ें  ":"Next", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.putExtra("index", index);
                switch (index) {
                    case 0:
                        break;
                    case 1:
						keyToidentify="danger_neo";
						intent.putExtra("keyToCheck",keyToidentify);
                        intent.putExtra("screen","danger");
                        break;
                    case 2 :
						keyToidentify="feeding_neo";
						intent.putExtra("keyToCheck",keyToidentify);
                        intent.putExtra("screen","feeding");
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }

                intent.setClass(MainMenuActivity.this, NeoNatalCareGenSce.class);
                startActivity(intent);
            }
        });
        builder.show();


    }

    public void getFamilyPlanningMenuItemId(int index){
        switch (index) {
            case 0:
                keyToidentify="safe_day";
                Intent intentFP = new Intent();
                intentFP.putExtra("keyToCheck",keyToidentify);
                intentFP.putExtra("adolecent", true);
                intentFP.setClass(MainMenuActivity.this, AdolescentGirl.class);
                startActivity(intentFP);
                break;

            case 1 :
				keyToidentify="fp_method";
				String title = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"परिवार नियोजन":"Family Planning";
				String message="अनियोजित गर्भ और बहुत अधिक गर्भपात से प्रसव में मुश्किल और रक्ताल्पता हो सकता है।  इसलिए माँ के अच्छे स्वास्थय और बच्चे के पोषण व सही देखभाल के लिए, गर्भावस्था की योजना बनाना व पहले दो बच्चों में तीन साल का अंतर रखना अत्यावश्यक है।";
				String message1="Unplanned pregnancy and abortion can cause difficult childbirth and anemia. Therefore, for better healthcare of mother and proper care of child and its nutrition, family planning and proper spacing of at least 3 years between first two children is important.";
				String msg=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?message:message1;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                builder.setTitle(title);
                builder.setMessage(msg);
                builder.setPositiveButton(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI) ? "आगे बढ़ें  " : "Next", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.putExtra("keyToCheck", keyToidentify);
						intent.putExtra(FamilyPlanning.class.toString(), 1);
						intent.putExtra("FP Method", true);
						intent.setClass(MainMenuActivity.this, AdolescentGirlFrag.class);
						startActivity(intent);
					}
				});
                builder.show();


                break;

            default:
                break;
        }
    }

    public void getAdolscentMenuItemId(int index){
        switch (index) {
            case 0:
				keyToidentify="learning_girl";
                Intent intentAdolecent = new Intent();
                intentAdolecent.putExtra("gyan", true);
				intentAdolecent.putExtra("keyToCheck",keyToidentify);
                intentAdolecent.setClass(MainMenuActivity.this, AdolescentGirl.class);
                startActivity(intentAdolecent);
                break;
            case 1 :
				keyToidentify="stories_girl";
                Intent intentAdolecent1 = new Intent();
                intentAdolecent1.putExtra("kahaniyan", true);
				intentAdolecent1.putExtra("keyToCheck",keyToidentify);
                intentAdolecent1.setClass(MainMenuActivity.this, AdolescentGirl.class);
                startActivity(intentAdolecent1);

                break;
            case 2:
				keyToidentify="games_girl";
                Intent intentAdolecent2 = new Intent();
                intentAdolecent2.putExtra("khel", true);
				intentAdolecent2.putExtra("keyToCheck",keyToidentify);
                intentAdolecent2.setClass(MainMenuActivity.this, AdolescentGirl.class);
                startActivity(intentAdolecent2);
                break;

            default:
                break;
        }
    }

    /**display the house list by selecting housse menu*/
	@Override
	public void gethouseId(int id) {

		FragmentTransaction transaction  = manager.beginTransaction();		
		MainMenuFragment fragment = new MainMenuFragment();
		transaction.replace(R.id.mainContent, fragment, "mainMenu");
		transaction.commit();
	}
	/**display the prenatal list by selecting prenatal menu*/
	@Override
	public void getprenatalId(int id) {
		// TODO Auto-generated method stub
		PrenatalMenu prenatalMenu = new PrenatalMenu();	
		FragmentTransaction transaction1  = manager.beginTransaction();	
		manager.popBackStackImmediate("prenatalReg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
		manager.popBackStackImmediate("prenatalMenu", FragmentManager.POP_BACK_STACK_INCLUSIVE);

		transaction1.commit();
		
		FragmentTransaction transaction  = manager.beginTransaction();		
		transaction.replace(R.id.mainContent, prenatalMenu, "prenatalMenu");
		transaction.addToBackStack("prenatalMenu");						
		transaction.commit();
		
		switch (id) {
		case 0:
			
			break;

		default:
			break;
		}
	}

    /**display the menu list by selecting  menu*/
	@Override
	public void getMenuItemId(int index) {
		// TODO Auto-generated method stub
        MiraConstants.PRENATAL_DEMO="pre_demo12";
        MiraConstants.isIMU_VIEW_CHANGE=false;

		final FragmentTransaction transaction  = manager.beginTransaction();
		switch (index) {
		case 0:
			
			AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
			final MyHouseBaseAdapter adapter = new MyHouseBaseAdapter(MainMenuActivity.this);
			String addhouse=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नया घर पंजीकृत करें":"Add New House";
			builder.setAdapter(adapter, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					HouseDtl dtl = (HouseDtl) adapter.getItem(which);
					System.out.println(which+"   "+dtl);					
					PrenatalRegistration registration = PrenatalRegistration.newInstance(dtl,false);
					transaction.replace(R.id.mainContent, registration, "prenatalReg");
					transaction.addToBackStack("prenatalReg");
					transaction.commit();					
				}
			});
			builder.setPositiveButton(addhouse, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					addNewHouse();
				}
			});


			builder.show();
			
			break;

		case 1:

			PrenatalWomenList womenList = new PrenatalWomenList();
			womenList.setAction(MiraConstants.view);
			transaction.replace(R.id.mainContent, womenList, "womenList");
			transaction.addToBackStack("womenList");
			transaction.commit();			
			break;
		case 2:
			PrenatalWomenList womenListc = new PrenatalWomenList();	
			womenListc.setAction(MiraConstants.closecase);
			transaction.replace(R.id.mainContent, womenListc, "PrenatalwomenList");
			transaction.addToBackStack("PrenatalwomenList");
			transaction.commit();
			
			break;
		case 3:
			PrenatalCloseCaseReport caseReport = new PrenatalCloseCaseReport();
			transaction.replace(R.id.mainContent, caseReport, "caseReport");
			transaction.addToBackStack("caseReport");
			transaction.commit();
			break;

       case 4:// Prenatal Demo Tour

//           PrenatalWomenListDemo demowomenList = new PrenatalWomenListDemo();
//           transaction.replace(R.id.mainContent, demowomenList, "demowomenList");
//           transaction.addToBackStack("demowomenList");
//           transaction.commit();


           WomenDtl wd = new WomenDtl();
           wd.setKeyId(12);
           wd.setHouseID("1005");
           wd.setHouseNumber("A003");
           wd.setWomanId("101");
           wd.setPregnentId("105");
           wd.setName("Sarita");
           wd.setHusname("H001");
           wd.setAge(45);
           wd.setChildren(4);
           wd.setLmcDate("01/11/2015");
           wd.setUploade("0");
           wd.setCreatedAt("10/02/2011");

           wd.setAnc_1(1);
           wd.setAnc_2(1);
           wd.setAnc_3(1);
           wd.setAnc_4(1);

           MiraConstants.PRENATAL_DEMO="pre_demo";
           Intent intent = new Intent();
           intent.putExtra(WomenDtl.class.getName(), wd);
           intent.setClass(this, WeeklyInfo.class);
           startActivity(intent);
           break;
		default:
			break;
		}
	}

	@Override
	public void getprenatalListElemnt(WomenDtl womenDtl) {
		final FragmentTransaction transaction  = manager.beginTransaction();	
		ImmunzRegistration immunzRegistration = ImmunzRegistration.newInstance(womenDtl);			
		transaction.replace(R.id.mainContent, immunzRegistration, "immzReg");
		transaction.addToBackStack("immzReg");
		transaction.commit();
	}
	
	@Override
	public void getImnzItemId(int index) {
         MiraConstants.IMMUNIZATION_DEMO="imu_demo12";
         MiraConstants.isIMU_VIEW_CHANGE=true;

		final FragmentTransaction transaction  = manager.beginTransaction();	
		switch (index) {
		case 0:
			AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
			final MyWomanBaseAdapter adapter = new MyWomanBaseAdapter(MainMenuActivity.this,1);
			builder.setAdapter(adapter, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					ImmunzRegistration immunzRegistration = ImmunzRegistration.newInstance((WomenDtl) adapter.getItem(which));
					System.out.println("WomenDtls......"+which+"   "+adapter);
					transaction.replace(R.id.mainContent, immunzRegistration, "immzReg");
					transaction.addToBackStack("immzReg");
					transaction.commit();
				}
			});
			String addwomen=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नई महिला पंजीकृत करें":"Add New Women";
			builder.setPositiveButton(addwomen, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
					final MyHouseBaseAdapter adapter = new MyHouseBaseAdapter(MainMenuActivity.this);
					builder.setAdapter(adapter, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							HouseDtl dtl = (HouseDtl) adapter.getItem(which);
							System.out.println("HouseDlts......"+which+"   "+dtl);
//							addNewWomen(dtl);

							PrenatalRegistration registration = PrenatalRegistration.newInstance(dtl,true);
//							ImmunzRegistration immunzRegistration = ImmunzRegistration.newInstance((WomenDtl) adapter.getItem(which));
							transaction.replace(R.id.mainContent, registration, "prenatalReg");
							transaction.addToBackStack("prenatalReg");
							transaction.commit();
						}
					});
					String addhouse=MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नया घर पंजीकृत करें":"Add New House";
					builder.setPositiveButton(addhouse, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							addNewHouse();
						}
					});
					builder.show();
				}
			});
			builder.show();



			break;

		case 1:
			ImmunzChildList immunzChildList = new ImmunzChildList();			
			transaction.replace(R.id.mainContent, immunzChildList, "immzList");
			transaction.addToBackStack("immzList");
			transaction.commit();
			break;

         case 2:
             MiraConstants.IMMUNIZATION_DEMO="imu_demo";
             FragmentTransaction transaction1  = manager.beginTransaction();

             ChildDtl cd = new ChildDtl();
             cd.setKeyId(2);
             cd.setWomanId("40");
             cd.setWomanName("amrita");
             cd.setChildId("2");
             cd.setName("RAJA");
             cd.setSex("Male");
             cd.setDob("21/08/2014");
             cd.setStatus("0");
             cd.setUpload("1");
             cd.setCreatedAt("20/11/2015");
//			 databaseHelper.insertChildRec(cd);

             ImmunizVaccineList immunizVaccineList = new ImmunizVaccineList(cd);
			 Toast.makeText(getApplicationContext(), "Click  "+cd.getName(), Toast.LENGTH_SHORT).show();
			 transaction1.replace(R.id.mainContent, immunizVaccineList, "immunzList");
             transaction1.addToBackStack("immunzList");
             transaction1.commit();
                break;
		default:
			break;
		}
	}

    /**display the registrationform for house*/
	public void addNewHouse() {
		FragmentTransaction transaction  = manager.beginTransaction();	
		HouseRegistration houseRegistration = new HouseRegistration();
		transaction.replace(R.id.mainContent, houseRegistration, "houseRegistration");
		transaction.addToBackStack("houseRegistration");
		transaction.commit();
	}
	
	/**display the immunization registration form for house*/
	@Override
	public void immunReg() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction  = manager.beginTransaction();
		ImmunizMenu immunizMenu = new ImmunizMenu();
		transaction.replace(R.id.mainContent, immunizMenu, "immunzlMenu");
		transaction.addToBackStack("immunzlMenu");
		transaction.commit();
	}

	public void addNewWomen(HouseDtl dtl){
		final FragmentTransaction transaction  = manager.beginTransaction();
		ImmunzRegistration immunzRegistration =ImmunzRegistration.newInstance(dtl);
		transaction.replace(R.id.mainContent, immunzRegistration, "prenatalReg");
		transaction.addToBackStack("prenatalReg");
		transaction.commit();
	}
	/**upload the overall records by selecting their ids from database*/
	public boolean uploadRecord1() {
		final List<HouseDtl>houseDtls = databaseHelper.gethouseByTag("houseid=0");
		
		final List<WomenDtl>womenDtls = databaseHelper.getWomenByTag("womanid=0");
		
		final List<ChildDtl>childDtls = databaseHelper.getChildByTag("childid=0");
		
		String tag_name="SELECT* FROM tabvaccinestatus WHERE uploaded='0'";
		final List<VaccinatedDtl>vaccinatedDtls = databaseHelper.getvaccStatusByTag(tag_name);
		final HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String,String>>();
		for (VaccinatedDtl vacdtl : vaccinatedDtls) {
			if(map.containsKey(vacdtl.getChildId())){
				HashMap<String, String>hashMap = map.get(vacdtl.getChildId());
				hashMap.put(vacdtl.getName(), vacdtl.getDate());
				map.put(vacdtl.getChildId(), hashMap);
			}else{
				HashMap<String, String>hashMap = new HashMap<String, String>();
				hashMap.put(vacdtl.getName(), vacdtl.getDate());
				map.put(vacdtl.getChildId(), hashMap);
			}
		}
		
		String queryString0 = "SELECT *FROM tabweekinfo WHERE quesone = 0 AND questwo = 0 AND questhree = 0 AND quesfour = 0 AND quesfive = 0 AND uploaded='0'";
		final List<WeeklyDtl>vaccinatedDtls0 = databaseHelper.getWeeklyDetailsByTag(queryString0);
		String queryString1 = "SELECT *FROM tabweekinfo WHERE (quesone != '0' OR questwo != '0' OR questhree != '0' OR quesfour != '0' OR quesfive != '0') AND uploaded='0'";
		final List<WeeklyDtl>vaccinatedDtls1 = databaseHelper.getWeeklyDetailsByTag(queryString1);
		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				
				
				postChil("", childDtls);
				postCloseCase();
				postChildImmunz(map);
				postWomenQueAns(map);
				postHouse("", houseDtls);
				postWomen("", womenDtls);
//				postChil("", childDtls);
			}
		}).start();
		
		return true;
	}
	
	class Upload extends AsyncTask<Void, Integer, String>{
		ProgressDialog progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainMenuActivity.this);
			progressDialog.setTitle("uploading....");
			progressDialog.setMessage("Please Wait data uploading...");
			progressDialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			final List<HouseDtl>houseDtls = databaseHelper.gethouseByTag("houseid=0");
			final List<WomenDtl>womenDtls = databaseHelper.getWomenByTag("womanid=0");
			final List<ChildDtl>childDtls = databaseHelper.getChildByTag("childid=0");
			
			String tag_name="SELECT* FROM tabvaccinestatus WHERE uploaded='0'";

			SharedPreferences prefPre=getSharedPreferences(MiraConstants.PreNatal_Prefrences,MODE_PRIVATE);
			SharedPreferences prefImmu=getSharedPreferences(MiraConstants.Immuniz_Prefrences,MODE_PRIVATE);
			SharedPreferences prefNe0=getSharedPreferences(MiraConstants.NeoNatal_Prefrences,MODE_PRIVATE);
			SharedPreferences prefFp=getSharedPreferences(MiraConstants.FP_Prefrences,MODE_PRIVATE);
			SharedPreferences prefAdo=getSharedPreferences(MiraConstants.Adolescent_Girl_Prefrences,MODE_PRIVATE);

			if(prefPre.getAll().size()>0){
				Map<String, ?> allEntries =prefPre.getAll();
				jsonArrayPre=new JSONArray();
				JSONArray jsonArray=makePreNatal(allEntries);
				try {
					jsonObjectMain.put("PreNatalTracking",jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			if(prefImmu.getAll().size()>0){
				Map<String, ?> allEntries =prefImmu.getAll();
				jsonArrayImmu=new JSONArray();
				JSONArray jsonArray=makeImmu(allEntries);
				try {
					jsonObjectMain.put("ImmunizationTracking",jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(prefNe0.getAll().size()>0){
				Map<String, ?> allEntries =prefNe0.getAll();
				jsonObjectNeo=new JSONObject();
				JSONObject object=makeNeoNatal(allEntries);
				try {
					jsonObjectMain.put("NeoNatalCareTracking",object);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(prefFp.getAll().size()>0){
				Map<String, ?> allEntries =prefFp.getAll();
				jsonObjectFp=new JSONObject();
				JSONObject object=makeFamilyPlanning(allEntries);
				try {
					jsonObjectMain.put("FamilyPlanningTracking",object);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(prefAdo.getAll().size()>0){
				Map<String, ?> allEntries =prefAdo.getAll();
				jsonObjectAdo=new JSONObject();
				JSONObject object=makeAdolescentGirlHealth(allEntries);
				try {
					jsonObjectMain.put("AdolescentTracking",object);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Yes There is something....." + jsonObjectMain.toString());


			final List<VaccinatedDtl>vaccinatedDtls = databaseHelper.getvaccStatusByTag(tag_name);
			final HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String,String>>();
			for (VaccinatedDtl vacdtl : vaccinatedDtls) {
				if(map.containsKey(vacdtl.getChildId())){
					HashMap<String, String>hashMap = map.get(vacdtl.getChildId());
					hashMap.put(vacdtl.getName(), vacdtl.getDate());
					map.put(vacdtl.getChildId(), hashMap);
				}else{
					HashMap<String, String>hashMap = new HashMap<String, String>();
					hashMap.put(vacdtl.getName(), vacdtl.getDate());
					map.put(vacdtl.getChildId(), hashMap);
				}
			}
			
//			String queryString0 = "SELECT *FROM tabweekinfo WHERE quesone = 0 AND questwo = 0 AND questhree = 0 AND quesfour = 0 AND quesfive = 0 AND uploaded='0'";
//			final List<WeeklyDtl>vaccinatedDtls0 = databaseHelper.getWeeklyDetailsByTag(queryString0);
//			String queryString1 = "SELECT *FROM tabweekinfo WHERE (quesone != '0' OR questwo != '0' OR questhree != '0' OR quesfour != '0' OR quesfive != '0') AND uploaded='0'";
//			final List<WeeklyDtl>vaccinatedDtls1 = databaseHelper.getWeeklyDetailsByTag(queryString1);
			postHouse("", houseDtls);
			postWomen("", womenDtls);
			postChil("", childDtls);
			postCloseCase();
			postChildImmunz(map);
			postWomenQueAns(map);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.cancel();
			if(trackException==false){
				indication=0;
			}

		}		
	}

	public JSONArray makePreNatal(Map<String, ?> prefPre){
		for (Map.Entry<String, ?> entry : prefPre.entrySet()) {
			JSONObject jsonObject=new JSONObject();
			String arr1[]=entry.getKey().split("_");
			String arr2[]=entry.getValue().toString().split("#");
			try {
				jsonObject.put("womenId",arr1[0]);
				jsonObject.put("WeekNo", arr1[1]);
				for(int i=0;i<arr2.length-2;i++){
					jsonObject.put("Screen_"+i,arr2[i]);
				}
				jsonObject.put("startTimeStamp",arr2[arr2.length-2]);
				jsonObject.put("lastTimeStamp",arr2[arr2.length-1]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonArrayPre.put(jsonObject);
			System.out.println("values from SharedPref...." + arr1[0]+"  "+arr1[1] + ": " + entry.getValue().toString());
		}
		return jsonArrayPre;
	}

	public JSONArray makeImmu(Map<String, ?> preImmu){
			for (Map.Entry<String, ?> entry : preImmu.entrySet()) {
			JSONObject jsonObject=new JSONObject();
			String arr1[]=entry.getKey().split("_");
			String arr2[]=entry.getValue().toString().split("#");
			try {
				jsonObject.put("childID",arr1[0]);
				jsonObject.put("vaccName", arr1[1]);
				for(int i=0;i<arr2.length-2;i++){
					jsonObject.put("Screen_"+i,arr2[i]);
				}
				jsonObject.put("startTimeStamp",arr2[arr2.length-2]);
				jsonObject.put("lastTimeStamp",arr2[arr2.length-1]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonArrayImmu.put(jsonObject);
		}
		System.out.println("values from SharedPref...."+jsonArrayImmu.toString());
		return jsonArrayImmu;
	}

	public JSONObject makeNeoNatal(Map<String,?>preNeo){
		for(Map.Entry<String,?>entry:preNeo.entrySet()){
			JSONObject object=new JSONObject();
			String arr[]=entry.getValue().toString().split("#");
			try {
				for (int i = 0; i < arr.length - 2; i++) {
					object.put("Screen_" + i, arr[i]);
				}
				object.put("startTimeStamp",arr[arr.length-2]);
				object.put("lastTimeStamp",arr[arr.length-1]);

				if(entry.getKey().equals("feeding_neo"))
				{
					jsonObjectNeo.put("BreastFeedingTracking",object);
				}
				else if(entry.getKey().equals("danger_neo"))
				{
					jsonObjectNeo.put("DangerSignTracking",object);
				}
				else if(entry.getKey().equals("delivery_hospital"))
				{
					jsonObjectNeo.put("DeliveryHospitalTracking",object);
				}
				else if(entry.getKey().equals("delivery_house"))
				{
					jsonObjectNeo.put("DeliveryHouseTracking",object);
				}
				else if(entry.getKey().equals("fp_method"))
				{
					jsonObjectFp.put("FamilyPlanningTracking",object);
				}
				else if(entry.getKey().equals("safe_day"))
				{
                 jsonObjectFp.put("SafeDaysTracking",object);
				}
			}catch (JSONException j){
				j.printStackTrace();
			}
		}
		return jsonObjectNeo;
	}
	public JSONObject makeFamilyPlanning(Map<String,?>preFp){
		for(Map.Entry<String,?>entry:preFp.entrySet()){
			JSONObject object=new JSONObject();
			String arr[]=entry.getValue().toString().split("#");
			try {
				for (int i = 0; i < arr.length - 2; i++) {
					object.put("Screen_" + i, arr[i]);
				}
				object.put("startTimeStamp",arr[arr.length-2]);
				object.put("lastTimeStamp",arr[arr.length-1]);

				if(entry.getKey().equals("fp_method"))
				{
					jsonObjectFp.put("FamilyPlanningTracking",object);
				}
				else if(entry.getKey().equals("safe_day"))
				{
					jsonObjectFp.put("SafeDaysTracking",object);
				}
			}catch (JSONException j){
				j.printStackTrace();
			}
		}
		return jsonObjectFp;
		}


	public JSONObject makeAdolescentGirlHealth(Map<String,?>preAdo){
		for(Map.Entry<String,?>entry:preAdo.entrySet()){
			JSONObject object=new JSONObject();
			String arr[]=entry.getValue().toString().split("#");
			try {
				for (int i = 0; i < arr.length - 2; i++) {
					object.put("Screen_" + i, arr[i]);
				}
				object.put("startTimeStamp",arr[arr.length-2]);
				object.put("lastTimeStamp",arr[arr.length-1]);

				if(entry.getKey().equals("stories_girl"))
				{
					jsonObjectAdo.put("StoriesGirlTracking",object);
				}
				else if(entry.getKey().equals("games_girl"))
				{
					jsonObjectAdo.put("GamesGirlTracking",object);
				}
				else if(entry.getKey().equals("learning_girl"))
				{
					jsonObjectAdo.put("LearingGirlTracking",object);
				}
			}catch (JSONException j){
				j.printStackTrace();
			}
		}
		return jsonObjectAdo;
	}

	
	public  String postHouse(String url, List<HouseDtl>houseDtls){
		InputStream inputStream = null;
		String result = "";
		if(houseDtls.size()>0){
		try {	
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();			
			// 2. make POST request to the given URL
			url = MiraConstants.URL+"houseRegistration";
		    HttpPost httpPost = new HttpPost(url);
		    String json = "";
			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", MiraConstants.USERID);
			JSONArray array = new JSONArray();
			for (int i = 0; i < houseDtls.size(); i++) {
				JSONObject object = new JSONObject();
				HouseDtl dtl = houseDtls.get(i);
				object.put("family_head", dtl.getFamilyHead());
				object.put("house_number", dtl.getHouseNumber());
				object.put("land_mark", dtl.getLandMark());
				object.put("latitude", dtl.getLatitude());
				object.put("longitude", dtl.getLongitude());
				object.put("family_members", dtl.getFamilyMembers());
				object.put("no_of_married", dtl.getMarriedWomen());
				object.put("no_of_unmarried", dtl.getUnmarriedWomen());
				object.put("no_of_adolecence", dtl.getAdolecenceGorls());
				object.put("no_of_child", dtl.getChildrens());
				array.put(object);
			}
			jsonObject.put("houseDetails", array);
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(jsonObject);
		    json = jsonObject.toString();
		    StringEntity se = new StringEntity(json);		     
		    // 6. set httpPost Entity
		    httpPost.setEntity(se);		    
		    // 7. Set some headers to inform server about the type of the content   
		    httpPost.setHeader("json", json);
//		    httpPost.setHeader("Content-type", "application/json");		    
		    httpPost.getParams().setParameter("jsonpost",jsonarr);		    
			// 8. Execute POST request to the given MiraConstants.URL
 			HttpResponse httpResponse = httpclient.execute(httpPost);			
			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();			
			// 10. convert inputstream to string  
			if(inputStream != null){
				result = convertInputStreamToString(inputStream);
				System.out.println("Return Result....."+result);
				JSONObject object = new JSONObject(result);
				JSONArray newHousearray = null;
				JSONArray existHousearray =null;
				if(!object.getString("newHouse").equals("null")){
					newHousearray = object.getJSONArray("newHouse");
					for (int i = 0; i < newHousearray.length(); i++) {
						JSONObject jsonobj = newHousearray.getJSONObject(i);
						String house_id = jsonobj.getString("house_id");
						String house_number = jsonobj.getString("house_number");						
						HouseDtl dtl = databaseHelper.gethouseByTag("housenumber="+"'"+house_number+"'").get(0);
						dtl.setHouseID(house_id);
						databaseHelper.updateHouseId(dtl);
					}				
				}				 
				if(!object.getString("existHouse").equals("null")){
					existHousearray =object.getJSONArray("existHouse");					
					for (int i = 0; i < existHousearray.length(); i++) {
						JSONObject jsonobj = existHousearray.getJSONObject(i);
						String house_id = jsonobj.getString("house_id");
						String house_number = jsonobj.getString("house_number");						
						HouseDtl dtl = databaseHelper.gethouseByTag("housenumber=" + "'" + house_number + "'").get(0);
						dtl.setHouseID(house_id);
						databaseHelper.updateHouseId(dtl);
					}
					}
				showToast("House");
				} else {
					result = "Did not work!";
					Toast.makeText(MainMenuActivity.this, "Not Submitted",
							Toast.LENGTH_SHORT).show();

				}		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
			//faisal//////
			trackException=true;
		}		
		}
		// 11. return result		
		return result;
	}
	
	public  String postWomen(String url, List<WomenDtl>womenDtls){
		InputStream inputStream = null;
		String result = "";
		if(womenDtls.size()>0){
		try {			
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();			
			// 2. make POST request to the given MiraConstants.URL
			url = MiraConstants.URL+"womenRegistration";
		    HttpPost httpPost = new HttpPost(url);
		    String json = "";
			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", MiraConstants.USERID);
			JSONArray array = new JSONArray();
			for (int i = 0; i < womenDtls.size(); i++) {
				JSONObject object = new JSONObject();
				WomenDtl dtl = womenDtls.get(i);
				object.put("house_id", dtl.getHouseID());
				object.put("women_name", dtl.getName());
				object.put("husband", dtl.getHusname());
				object.put("pregnant_status", dtl.getStatus());
				object.put("almc_date", dtl.getLmcDate());
				object.put("number_of_child", dtl.getChildren());
				object.put("women_age", dtl.getAge());				
				array.put(object);
			}
			jsonObject.put("women_new_detail", array);
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(jsonObject);
		    json = jsonObject.toString();
		    StringEntity se = new StringEntity(json);		    
		    // 6. set httpPost Entity
		    httpPost.setEntity(se);		    
		    // 7. Set some headers to inform server about the type of the content   
		    httpPost.setHeader("json", json);
//		    httpPost.setHeader("Content-type", "application/json");		    
		    httpPost.getParams().setParameter("jsonpost",jsonarr);		    
			// 8. Execute POST request to the given MiraConstants.URL
 			HttpResponse httpResponse = httpclient.execute(httpPost);			
			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();			
			// 10. convert inputstream to string  
			if(inputStream != null){
				result = convertInputStreamToString(inputStream);
				System.out.println("Return Result....."+result);
				JSONObject object = new JSONObject(result);
				JSONArray pregnantarray = null;//object.getJSONArray("newHouse");
				JSONArray notPregnantarray =null;// object.getJSONArray("existHouse");
				if(!object.getString("new_women_pregnant").equals("null")){
					pregnantarray = object.getJSONArray("new_women_pregnant");
					for (int i = 0; i < pregnantarray.length(); i++) {
			 			JSONObject jsonobj = pregnantarray.getJSONObject(i);
						String house_id = jsonobj.getString("house_id");
						String women_id = jsonobj.getString("women_id");
						String pregnant_id = jsonobj.getString("pregnant_id");
						String women_name = jsonobj.getString("women_name");
						String husband = jsonobj.getString("husband");		
						String select = "houseid='"+house_id+"' AND womanname='"+women_name+"' AND husbandname='"+husband+"'";						
						WomenDtl dtl = databaseHelper.getWomenByTag(select).get(0);
						dtl.setWomanId(women_id);
						dtl.setPregnentId(pregnant_id);
						
						PregnantDtl pregnantDtl= new PregnantDtl();
						pregnantDtl.setWomanId(Integer.parseInt(women_id));
						pregnantDtl.setPregId(Integer.parseInt(pregnant_id));
						pregnantDtl.setLmcDate(dtl.getLmcDate());
						databaseHelper.insertPregAnc(pregnantDtl);
						
						databaseHelper.updateWomanRec(dtl);
					}
				}
				if(!object.getString("new_women_not_pregnant").equals("null")){
					notPregnantarray =object.getJSONArray("new_women_not_pregnant");					
					for (int i = 0; i < notPregnantarray.length(); i++) {
						JSONObject jsonobj = notPregnantarray.getJSONObject(i);
						String house_id = jsonobj.getString("house_id");
						String women_id = jsonobj.getString("women_id");
						String women_name = jsonobj.getString("women_name");
						String husband = jsonobj.getString("husband");						
						String select = "houseid='"+house_id+"' AND womanname='"+women_name+"' AND husbandname='"+husband+"'";						
						WomenDtl dtl = databaseHelper.getWomenByTag(select).get(0);
						dtl.setWomanId(women_id);
						databaseHelper.updateWomanRec(dtl);
					}
				}
				showToast("Women");
			}	 else {
				result = "Did not work!";
				Toast.makeText(MainMenuActivity.this, "Not Submitted",
						Toast.LENGTH_SHORT).show();

			}		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
			//faisal//////
			trackException=true;
		}		
		// 11. return result
		}
		return result;
	}
/**------------------- SEND CHILD DATA TO SERVER -----------------------*/
	public String postChil(String url, List<ChildDtl>childDtls){
		InputStream inputStream = null;
		String result = "";
		if(childDtls.size()>0){
		try {
			HttpClient httpclient = new DefaultHttpClient();
			url = MiraConstants.URL+"newChildRegistration";
		    HttpPost httpPost = new HttpPost(url);
		    String json = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", MiraConstants.USERID);
			JSONArray array = new JSONArray();
			for (int i = 0; i < childDtls.size(); i++) {
				JSONObject object = new JSONObject();
				ChildDtl dtl = childDtls.get(i);
				object.put("women_id", dtl.getWomanId());
				object.put("dob", dtl.getDob());
				object.put("gender", dtl.getSex());
				object.put("child_name", dtl.getName());			
				array.put(object);
			}			
			jsonObject.put("child_new_detail", array);
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(jsonObject);
		    json = jsonObject.toString();
		    StringEntity se = new StringEntity(json);		    
		    httpPost.setEntity(se);
		    httpPost.setHeader("json", json);
		    httpPost.getParams().setParameter("jsonpost",jsonarr);
 			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if(inputStream != null){
				result = convertInputStreamToString(inputStream);
				System.out.println("Return Result....."+result);
				JSONObject object = new JSONObject(result);
				JSONArray pregnantarray = null;
				if(!object.getString("child_data").equals("null")){
					pregnantarray = object.getJSONArray("child_data");
					for (int i = 0; i < pregnantarray.length(); i++) {
			 			JSONObject jsonobj = pregnantarray.getJSONObject(i);
						String women_id = jsonobj.getString("women_id");
						String child_id = jsonobj.getString("child_id");
						String child_name = jsonobj.getString("child_name");						
						String select = "name='"+child_name+"' AND womanid='"+women_id+"'";						
						ChildDtl dtl = databaseHelper.getChildByTag(select).get(0);
						dtl.setChildId(child_id);
						databaseHelper.updateChildRec(dtl);
					}				
				}
				showToast("Child");
			}else result = "Did not work!";		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
			//faisal//////
			trackException=true;
		}		
		}
		return result;
	}
	
	/**post woman record on server
	 * @param map get the details of quetion answers 
	 * @return ok reslt of posted record
	 */
	public String postWomenQueAns(HashMap<String, HashMap<String, String>> map) {
		
		String queryString0 = "SELECT *FROM tabweekinfo WHERE quesone = 0 AND questwo = 0 AND questhree = 0 AND quesfour = 0 AND quesfive = 0 AND uploaded=0";
		final List<WeeklyDtl>weeklyDtls0 = databaseHelper.getWeeklyDetailsByTag(queryString0);
		String queryString1 = "SELECT *FROM tabweekinfo WHERE (quesone != '0' OR questwo != '0' OR questhree != '0' OR quesfour != '0' OR quesfive != '0') AND uploaded='0'";
		final List<WeeklyDtl>weeklyDtls1 = databaseHelper.getWeeklyDetailsByTag(queryString1);
		
		JSONObject jsonObjectmain = new JSONObject();
		
		try {
			jsonObjectmain.put("alert0", null);
			jsonObjectmain.put("alert1", null);

		
		if (weeklyDtls0.size() > 0){
			try {
				JSONObject jsonObject0 = new JSONObject();
				
				JSONArray array = new JSONArray();
				for (int i = 0; i < weeklyDtls0.size(); i++) {
					WeeklyDtl dtl = weeklyDtls0.get(i);
					JSONObject object = new JSONObject();
					object.put("pregnant_id", dtl.getPregnentId());
					object.put("week_id", dtl.getWeekNum());
					array.put(object);
				}
				jsonObjectmain.put("alert0", array);
				
				System.out.println(jsonObject0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (weeklyDtls1.size() > 0) {

			try {
				JSONObject jsonObject1 = new JSONObject();
				
				JSONArray array = new JSONArray();
				for (int i = 0; i < weeklyDtls1.size(); i++) {
					WeeklyDtl dtl = weeklyDtls1.get(i);
					JSONObject object = new JSONObject();
					object.put("pregnant_id", dtl.getPregnentId());
					object.put("week_id", dtl.getWeekNum());
					int ques[] = {dtl.getQuesOne(),dtl.getQuesTwo(),dtl.getQuesThree(),dtl.getQuesFour(),dtl.getQuesFive()};
					JSONArray array2 = new JSONArray();
					for (int j = 0; j < 5; j++) {
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("q_id", j);
						ques[j] = ques[j]==1?2:ques[j];
						ques[j] = ques[j]==0?1:ques[j];						
						jsonObject2.put("ans", ques[j]);
						array2.put(jsonObject2);
					}
					object.put("qus_id_ans", array2);
					array.put(object);
				}
				jsonObjectmain.put("alert1", array);
				
				System.out.println(jsonObject1);
			} catch (JSONException e) {
				e.printStackTrace();
			}				
		}

	} catch (JSONException e1) {
		e1.printStackTrace();
	}
		
		InputStream inputStream = null;
 		String result = "";
//		if (map.size() > 0) {
			if (weeklyDtls0.size()>0||weeklyDtls1.size()>0) {
				try {
					HttpClient httpclient = new DefaultHttpClient();
					String url = MiraConstants.URL+"updateAshaQusAns";
//					String url = MiraConstants.URL+"test";
				    HttpPost httpPost = new HttpPost(url);
				    String json = "";
					JSONArray jsonarr = new JSONArray();
					jsonarr.put(jsonObjectmain);
				    json = jsonObjectmain.toString();
				    StringEntity se = new StringEntity(json);		    
				    httpPost.setEntity(se);
				    httpPost.setHeader("json", json);
				    httpPost.getParams().setParameter("jsonpost",jsonarr);
		 			HttpResponse httpResponse = httpclient.execute(httpPost);
					inputStream = httpResponse.getEntity().getContent();
					if(inputStream!=null){
						result = convertInputStreamToString(inputStream);
						System.out.println("Return Result....."+result);
						String update_query = "UPDATE tabweekinfo SET uploaded = '1'";
						databaseHelper.updateWeeklyInfo(update_query);
						Log.d("MIRA", result);
						showToast("WeeklyQuestion");
					}
				} catch (Exception e) {
					e.printStackTrace();
					//faisal//////
					trackException=true;
				}
			}
		return result;
	}
	
	/** post immunized child record 
	 * @param map contains child details
	 * @return result  
	 */
	public String postChildImmunz(HashMap<String, HashMap<String, String>> map) {
		InputStream inputStream = null;
		String result = "";
		if (map.size() > 0) {			
			try {
				HttpClient httpclient = new DefaultHttpClient();
				String url = MiraConstants.URL+"immunization";
			    HttpPost httpPost = new HttpPost(url);
			    String json = "";
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("user_id", MiraConstants.USERID);
				JSONArray array = new JSONArray();
				Set<String>set = map.keySet();			
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					String childid = (String) iterator.next();
					JSONObject object = new JSONObject();
					object.put("child_id", childid);
					HashMap<String, String>hashMap = map.get(childid);
					Set<String>set2 = hashMap.keySet();
					JSONArray array2 = new JSONArray();
					for (Iterator iterator2 = set2.iterator(); iterator2.hasNext();) {
						String vacname = (String) iterator2.next();
						JSONObject object2 = new JSONObject();
						object2.put("vaccine_date",hashMap.get(vacname));
						System.out.println("Allah...."+hashMap.get(vacname)+"   "+vacname);
						object2.put("vaccine_id", vacname);
						array2.put(object2);
					}
					object.put("child_detail", array2);
					array.put(object);
				}
				jsonObject.put("immunization", array);
				
				JSONArray jsonarr = new JSONArray();
				jsonarr.put(jsonObject);
			    json = jsonObject.toString();
			    StringEntity se = new StringEntity(json);		    
			    httpPost.setEntity(se);
			    httpPost.setHeader("json", json);
			    httpPost.getParams().setParameter("jsonpost",jsonarr);
	 			HttpResponse httpResponse = httpclient.execute(httpPost);
				inputStream = httpResponse.getEntity().getContent();
				if(inputStream!=null){
					result = convertInputStreamToString(inputStream);
					System.out.println("Return Result....."+result);
					databaseHelper.updateVaccineTable("");
					Log.d("MIRA", result);
					showToast("Immunization");
				}
			} catch (Exception e) {
				e.printStackTrace();
				//faisal//////
				trackException=true;
			}
		}
		return result;
	}
	
	/**post close case report
	 * @return result of server operation
	 */
	public String postCloseCase() {
		InputStream inputStream = null;
		String result = "";
		
		String tag_name = "SELECT *FROM closecase WHERE uploaded = '0'";
		List<CloseCaseDtl> closeCaseDtls = databaseHelper.getCloseCaseStatusByTag(tag_name);
		
		if(closeCaseDtls.size()>0){
		try {
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();			
			// 2. make POST request to the given MiraConstants.URL
			String url = MiraConstants.URL+"updatePregnantStatus";
		    HttpPost httpPost = new HttpPost(url);
		    String json = "";
			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", MiraConstants.USERID);
			JSONArray array = new JSONArray();
			for (CloseCaseDtl closeCaseDtl : closeCaseDtls) {
				JSONObject object = new JSONObject();
				object.put("pregnant_id", closeCaseDtl.getPregnentId());
				object.put("close_status", closeCaseDtl.getStatus());
				object.put("closing_date", closeCaseDtl.getCreatedAt());
				array.put(object);
			}
			jsonObject.put("pregnant_update", array);
			JSONArray jsonarr = new JSONArray();
			jsonarr.put(jsonObject);
		    json = jsonObject.toString();
		    StringEntity se = new StringEntity(json);		    
		    // 6. set httpPost Entity
		    httpPost.setEntity(se);		    
		    // 7. Set some headers to inform server about the type of the content   
		    httpPost.setHeader("json", json);
//		    httpPost.setHeader("Content-type", "application/json");		    
		    httpPost.getParams().setParameter("jsonpost",jsonarr);		    
			// 8. Execute POST request to the given MiraConstants.URL
 			HttpResponse httpResponse = httpclient.execute(httpPost);			
			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();			
			// 10. convert inputstream to string  
			if(inputStream != null){
				result = convertInputStreamToString(inputStream);
				System.out.println("Return Result....."+result);
				System.out.println(result);
				
				databaseHelper.updateCloseCase(tag_name);
				showToast("CloseCase");
			} else {
				result = "Did not work!";
				Toast.makeText(MainMenuActivity.this, "Not Submitted",
						Toast.LENGTH_SHORT).show();

			}		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
			//faisal//////
			trackException=true;
		}		
		// 11. return result
		}
		
		return result;
	}
	
	/**
	 * referesh woman record for new changes
	 */
	private void refreshWomenRecord() {
		new AsyncTask<String, Integer, Void>(){
			ProgressDialog dialog = new ProgressDialog(MainMenuActivity.this);
			protected void onPreExecute() {
				String title = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"नवीनतम":"Update";
				String msg = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?"कृपया प्रतीक्षा करें":"Please Wait";
				dialog.setTitle(title);
				dialog.setMessage(msg);
				dialog.show();
			};
			
			@Override
			protected Void doInBackground(String... params) {
				InputStream inputStream = null;
				String result = "";
				HttpClient httpclient = new DefaultHttpClient();
				String url = MiraConstants.URL+"anmanswer";
			    HttpPost httpPost = new HttpPost(url);
			    String json = "";
				JSONObject jsonObject = new JSONObject();
				try { 
					jsonObject.put("asha_id", MiraConstants.USERID);
					JSONArray jsonarr = new JSONArray();
					jsonarr.put(jsonObject);
				    json = jsonObject.toString();
				    StringEntity se = new StringEntity(json);		    
				    httpPost.setEntity(se);
				    httpPost.setHeader("json", json); 
				    httpPost.getParams().setParameter("jsonpost",jsonarr);		    
		 			HttpResponse httpResponse = httpclient.execute(httpPost);			
					inputStream = httpResponse.getEntity().getContent();			
					if(inputStream != null){
						result = convertInputStreamToString(inputStream);

						JSONObject jsonObjectMain = new JSONObject(result);
						System.out.println("JsonObject.."+jsonObjectMain.toString());
						if(!jsonObjectMain.isNull("anm_answer")){
						
						JSONArray jsonArray = jsonObjectMain.getJSONArray("anm_answer");
	
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject object = jsonArray.getJSONObject(i);
							String pregnant_id = object.getString("pregnant_id");
							int week_id = object.getInt("week_id");
							
							WeeklyDtl dtl = new WeeklyDtl();
							dtl.setPregnentId(pregnant_id);
							dtl.setWeekNum(week_id);
							
							JSONArray arrQuestAns = object.getJSONArray("qus_id_ans");
							for (int j = 0; j < arrQuestAns.length(); j++) {
								JSONObject questAns = arrQuestAns.getJSONObject(j);
								int q_id = questAns.getInt("q_id");
								int ans = questAns.getInt("ans");
								
								switch (q_id) {
								case 0:
									dtl.setQuesOne(ans);
									break;
								case 1:
									dtl.setQuesTwo(ans);
									break;
								case 2:
									dtl.setQuesThree(ans);
									break;
								case 3:
									dtl.setQuesFour(ans);
									break;
								case 4:
									dtl.setQuesFive(ans);
									break;
								default:
									break;
								}				
								databaseHelper.updateWeeklyinfoDetails(dtl);
							}
						}
					}
						JSONArray anc_status = jsonObjectMain.getJSONArray("anc_status");
						for (int i = 0; i < anc_status.length(); i++) {
							JSONArray array = anc_status.getJSONArray(i);
							for (int j = 0; j < array.length(); j++) {
								JSONObject object = array.getJSONObject(j);
								int pregnant_id = object.getInt("pregnant_id");
								int anc_code = object.getInt("anc_code");
								int critical_status = object.getInt("critical_status");
								
								critical_status = critical_status==1?2:critical_status;
								critical_status = critical_status==0?1:critical_status;
								
								
								System.out.println(anc_code+" - "+critical_status+" - "+pregnant_id);
								String update_query = "UPDATE pregnant SET anc1 = '"+critical_status+"' WHERE pregid = '"+pregnant_id+"'";
								switch (anc_code) {
								case 1:
									update_query = "UPDATE pregnant SET anc1 = '"+critical_status+"' WHERE pregid = '"+pregnant_id+"'";
									break;
								case 2:
									update_query = "UPDATE pregnant SET anc2 = '"+critical_status+"' WHERE pregid = '"+pregnant_id+"'";
									break;
								case 3:
									update_query = "UPDATE pregnant SET anc3 = '"+critical_status+"' WHERE pregid = '"+pregnant_id+"'";
									break;								
								case 4:
									update_query = "UPDATE pregnant SET anc4 = '"+critical_status+"' WHERE pregid = '"+pregnant_id+"'";
									 break;
								default:
									break;
								}						
								databaseHelper.updatePregnantANC(update_query);
							}
					
						}
						
						System.out.println(result);
					} else {
						result = "Did not work!";
						Toast.makeText(MainMenuActivity.this, "Not Submitted",
								Toast.LENGTH_SHORT).show();
					}	
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				dialog.cancel();
			};
			
		}.execute("");
	}
	
	/**convert input stream into string format
	 * @param inputStream
	 * @return string of inputStream
	 * @throws IOException
	 */
	public static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;        
        inputStream.close();
        return result;        
    }



	public void clearApplicationData() {
		File cache = getCacheDir();
		File appDir = new File(cache.getParent());
		if(appDir.exists()){
			String[] children = appDir.list();
			for(String s : children){
				if(!s.equals("lib")){
					deleteDir(new File(appDir, s));
					Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED");
				}
			}
		}
	}
	
	public static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    return dir.delete();
	}



}
