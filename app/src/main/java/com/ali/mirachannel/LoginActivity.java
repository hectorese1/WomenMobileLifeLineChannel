package com.ali.mirachannel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.model.CloseCaseDtl;
import com.ali.mirachannel.model.HouseDtl;
import com.ali.mirachannel.model.PregnantDtl;
import com.ali.mirachannel.model.VaccinatedDtl;
import com.ali.mirachannel.model.WeeklyDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };
	
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private DatabaseHelper databaseHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		databaseHelper = DatabaseHelper.newInstance(this);
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.

		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 3) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		
		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} 
//		else if (!mEmail.contains("@")) {
//			mEmailView.setError(getString(R.string.error_invalid_email));
//			focusView = mEmailView;
//			cancel = true;
//		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {

			return postUserLogin();

		}
		
		@Override
		protected void onPostExecute(final Integer success) {
			mAuthTask = null;
			showProgress(false);
			
			if (success>=7) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainMenuActivity.class);
				startActivity(intent);
				finish();
			} else if(success==5){
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}else if(success==0){
				mEmailView.setError(getString(R.string.error_field_required));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
	
	public Integer postUserLogin() {
		InputStream inputStream = null;
		String result = "";
		int login_code = 0;
		try {
			HttpClient httpclient = new DefaultHttpClient();			
			String url = MiraConstants.URL+"userLogin";
		    HttpPost httpPost = new HttpPost(url);
		    String json = "";

			JSONObject jsonObject = new JSONObject();
			JSONArray array = new JSONArray();
				jsonObject.put("id", mEmailView.getText().toString().trim());
				jsonObject.put("pass", mPasswordView.getText().toString().trim());
//			System.out.println("Login Credantials.....  " + jsonObject.toString());
//			System.out.println("Login Credantials.....  "+jsonObject.toString());
                 System.out.println("Login Credantials.....  "+jsonObject.toString());
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
				System.out.println(result);
				if(result!=null){
					JSONObject object = new JSONObject(result);
					if(!object.isNull("login_code")){
						login_code = object.getInt("login_code");
						if(login_code<7){
							return login_code;
						}else{
							parseUserData(result);
							
						}
					}					
				}
			} else {
				result = "Did not work!";
				Toast.makeText(LoginActivity.this, "Not Submitted",
						Toast.LENGTH_SHORT).show();

			}		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}		
		return login_code;
	}
	
	private void parseUserData(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			int login_code = jsonObject.getInt("login_code");			
			JSONObject user_detail = jsonObject.getJSONObject("user_detail");
			String name = user_detail.getString("name");
			MiraConstants.PASSWORD=mPassword;
			MiraConstants.USERNAME_F=mEmail;
			int id = user_detail.getInt("id");
			MiraConstants.USERID=id;
			MiraConstants.USERNAME = name;
			SharedPreferences.Editor editor1 = MiraConstants.preferences.edit();
			editor1.putInt("user_id", id);
			editor1.putString("name", name);
			editor1.commit();

			SharedPreferences sharedPreferences=getSharedPreferences("User_Data",Context.MODE_PRIVATE);
			SharedPreferences.Editor editor2= sharedPreferences.edit();
			editor2.putString("email",mEmail);
			editor2.putString("pwrd",mPassword);
			editor2.putInt("userId",id);
			editor2.putString("userName",name);
			editor2.commit();

			if(!jsonObject.isNull("total_data")){
			JSONObject total_data = jsonObject.getJSONObject("total_data");
//			if(total_data.getJSONArray("house_data")!=null){
			
			if(!total_data.isNull("house_data")){
			JSONArray house_data = total_data.getJSONArray("house_data");
			house_data(house_data);
			}
			if(!total_data.isNull("women_data")){
			JSONArray women_data = total_data.getJSONArray("women_data");
			women_data(women_data);
			}
			if(!total_data.isNull("child_data")){
			JSONArray child_data = total_data.getJSONArray("child_data");
			child_data(child_data);
			}
			if(!total_data.isNull("women_pregnant_data")){
			JSONArray women_pregnant_data = total_data.getJSONArray("women_pregnant_data");
			women_pregnant_data(women_pregnant_data);
			}
			if(!total_data.isNull("pregnant_track_data")){
				JSONArray pregnant_track_data = total_data.getJSONArray("pregnant_track_data");
				pregnant_track_data(pregnant_track_data);
			}
			if(!total_data.isNull("immunization_data")){
			JSONArray immunization_data = total_data.getJSONArray("immunization_data");
			immunization_data(immunization_data);
			}			
			SharedPreferences.Editor editor = MiraConstants.preferences.edit();
			editor.putInt(MiraConstants.ID, id);
			editor.putInt(MiraConstants.LOGINCODE, login_code);
			editor.putString(MiraConstants.NAME, name);
			editor.commit();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;        
        inputStream.close();
        return result;        
    }
	
	private void house_data(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject object = data.getJSONObject(i);				
				HouseDtl dtl = new HouseDtl();
				dtl.setHouseID(object.getString("house_id"));
				dtl.setFamilyHead(object.getString("family_head"));
				dtl.setHouseNumber(object.getString("house_number"));
				dtl.setLandMark(object.getString("land_mark"));
				
				dtl.setLatitude(object.getLong("latitude"));
				dtl.setLongitude(object.getLong("longitude"));
				dtl.setFamilyMembers(object.getInt("family_members"));
				dtl.setMarriedWomen(object.getInt("no_of_married"));
				dtl.setUnmarriedWomen(object.getInt("no_of_unmarried"));
				dtl.setAdolecenceGorls(object.getInt("no_of_adolecence"));
				dtl.setChildrens(object.getInt("no_of_child"));
				dtl.setUploaded("1");
				dtl.setCreatedAt(MiraConstants.getDateTime());
				databaseHelper.insertintoHouse(dtl);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void women_data(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {
			try {	
				if(data.getJSONArray(i)!=null){
					JSONArray data2 = data.getJSONArray(i);
					for (int j = 0; j < data2.length(); j++) {
						if(data2.getJSONObject(j)!=null){
							JSONObject object = data2.getJSONObject(j);
							WomenDtl dtl = new WomenDtl();					
							dtl.setHouseID(object.getString("house_id"));
							dtl.setWomanId(object.getString("women_id"));				
							dtl.setName(object.getString("women_name"));
							dtl.setHusname(object.getString("husband"));
							dtl.setChildren(object.getInt("number_of_child"));
							dtl.setAge(object.getInt("women_age"));						
							dtl.setPregnentId("0");
							dtl.setStatus("0");
							dtl.setLmcDate("01/01/1900");
							dtl.setUploade("1");
							dtl.setCreatedAt(MiraConstants.getDateTime());					
							databaseHelper.insertWomenRec(dtl);
						}												
					}
				}		
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void child_data(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONArray array = data.getJSONArray(i);
				for (int j = 0; j < array.length(); j++) {
					if(!array.isNull(j)){
						JSONObject object = array.getJSONObject(j);
						ChildDtl dtl = new ChildDtl();
						dtl.setWomanId(object.getString("women_id"));
						dtl.setChildId(object.getString("child_id"));
						dtl.setName(object.getString("child_name"));
						dtl.setDob(object.getString("dob"));
						dtl.setSex(object.getString("gender"));
						dtl.setStatus("0");
						dtl.setUpload("1");
						dtl.setCreatedAt(MiraConstants.getDateTime());
						databaseHelper.insertChildRec(dtl);	
					}				
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void women_pregnant_data(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONArray array = data.getJSONArray(i);
				for (int j = 0; j < array.length(); j++) {
					if(!array.isNull(j)){
						JSONObject object = array.getJSONObject(j);
						
						String women_id = object.getString("women_id");
						String anmc_date = object.getString("anmc_date");
						String pregnant_status = object.getString("pregnant_status");
						String pregnant_id = object.getString("pregnant_id");
						String closing_date = object.getString("closing_date");
						int close_status = object.getInt("close_status");
						String query_string = "UPDATE tabwomen SET lmcdate = '"+anmc_date+"',status = '"+pregnant_status+"',pregid = '"+pregnant_id+"' WHERE womanid = '"+women_id+"'";
						databaseHelper.updateWomanRec(query_string);
						
						PregnantDtl pregnantDtl = new PregnantDtl();
						pregnantDtl.setWomanId(Integer.parseInt(women_id));
						pregnantDtl.setPregId(Integer.parseInt(pregnant_id));
						pregnantDtl.setLmcDate(anmc_date);
						databaseHelper.insertPregAnc(pregnantDtl);
						
						CloseCaseDtl dtl = new CloseCaseDtl();
//						dtl.setPregnentId(pregnant_id);
						dtl.setPregnentId(women_id);
						dtl.setStatus(close_status);
						dtl.setUploade("1");
						dtl.setCreatedAt(closing_date);					
						databaseHelper.insertCloseCase(dtl);							
					}				
				}				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void pregnant_track_data(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject object = data.getJSONObject(i);
				if(!object.isNull("alert0")){
					JSONArray alert0 = object.getJSONArray("alert0");
					for (int j = 0; j < alert0.length(); j++) {
						if(!alert0.isNull(j)){
							JSONObject jsonObject = alert0.getJSONObject(j);
							String pregnant_id = jsonObject.getString("pregnant_id");
							int week_id = jsonObject.getInt("week_id");
							WeeklyDtl dtl = new WeeklyDtl();
							dtl.setPregnentId(pregnant_id);
							dtl.setWeekNum(week_id);
							dtl.setQuesOne(0);
							dtl.setQuesTwo(0);
							dtl.setQuesThree(0);
							dtl.setQuesFour(0);
							dtl.setQuesFive(0);
							dtl.setUploaded("1");
							dtl.setCreatedAt(MiraConstants.getDateTime());
							dtl.setWomanId("0");
							databaseHelper.insetWeeklyDetails(dtl);
						}
						
					}
				}
				
				if(!object.isNull("alert1")){
					JSONArray alert1 = object.getJSONArray("alert1");
					for (int j = 0; j < alert1.length(); j++) {
						
						if(!alert1.isNull(j)){
							JSONObject jsonObject = alert1.getJSONObject(j);
							String pregnant_id = jsonObject.getString("pregnant_id");
							int week_id = jsonObject.getInt("week_id");
							JSONArray qus_id_ans = jsonObject.getJSONArray("qus_id_ans");
							
							WeeklyDtl dtl = new WeeklyDtl();
							dtl.setPregnentId(pregnant_id);
							dtl.setWeekNum(week_id);
							dtl.setQuesOne(0);
							dtl.setQuesTwo(0);
							dtl.setQuesThree(0);
							dtl.setQuesFour(0);
							dtl.setQuesFive(0);
							for (int k = 0; k < qus_id_ans.length(); k++) {							
								JSONObject jsonObject2 = qus_id_ans.getJSONObject(k);
								int q_id = jsonObject2.getInt("q_id");
								int ans = jsonObject2.getInt("ans");
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
							}							
							dtl.setUploaded("1");
							dtl.setCreatedAt(MiraConstants.getDateTime());
							dtl.setWomanId("0");
							databaseHelper.insetWeeklyDetails(dtl);
						}						
					}
				}			
			} catch (JSONException e) {
				e.printStackTrace();
			}
//			alert0
		}
	}

	private void immunization_data(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONArray array = data.getJSONArray(i);
				for (int j = 0; j < array.length(); j++) {
					if(!array.isNull(j)){
						JSONObject jsonObject = array.getJSONObject(j);
						String vaccine_date = jsonObject.getString("vaccine_date");
                        String vaccine_name = jsonObject.getString("vaccine_id");
						System.out.println("Vaccine NAme In LOginActivity...."+vaccine_name);
						String child_id = jsonObject.getString("child_id");
						VaccinatedDtl dtl = new VaccinatedDtl();
						dtl.setChildId(child_id);
						dtl.setName(vaccine_name);
						dtl.setDate(vaccine_date);
						System.out.println("Faiz....." + vaccine_date);
						dtl.setUpload("1");
						dtl.setCreatedAt(MiraConstants.getDateTime());

						//////////////Faisal//////////////////////////////////////////
						databaseHelper.insetVaccineStatusTable(dtl);
						///////////////////////////Faisal//////////////////////////
						System.out.println(child_id +"  Vaccine Name "+vaccine_name);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}		
}
