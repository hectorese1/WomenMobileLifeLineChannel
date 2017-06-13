package com.ali.mirachannel;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.HouseDtl;
import com.ali.mirachannel.model.PregnantDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**class shows the list of houses registered by users*/
public class HouseList extends ListFragment{
	DatabaseHelper databaseHelper;
	List<HouseDtl>houseDtls;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment_child, container, false);		
		Log.w("MIRA", getTag());
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		databaseHelper = DatabaseHelper.newInstance(getActivity());
		houseDtls = databaseHelper.gethouseByTag("houseid!=0");
		MyHouseBaseAdapter adapter = new MyHouseBaseAdapter(getActivity());		
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HouseDtl dtl = (HouseDtl) l.getAdapter().getItem(position);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(dtl.getFamilyHead()+"("+dtl.getHouseNumber()+")");
		String tag_name = "houseid = '"+dtl.getHouseID()+"'";
		final List<WomenDtl> womenDtls = databaseHelper.getWomenByTag(tag_name);
		
		MyAdapter adapter = new MyAdapter(getActivity(), womenDtls);
		builder.setAdapter(adapter, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				womanDetailOptions(womenDtls.get(which));
				dialog.cancel();
				System.out.println("Dialoge Is Canceled...."+which);
			}
		});
		builder.setNeutralButton("OK", null);
		builder.show();
	}	 
	
	
	/**
	 * @param womenDtl object of women dstails
	 */
	private void womanDetailOptions(final WomenDtl womenDtl) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.anc_4);
		builder.setTitle(womenDtl.getName());
		String[]strings = null;
		if(womenDtl.getPregnentId().equals("0")){
			strings = new String[]{"Send to Pregnancy","Send to Immunization","View Details"};
		}else{
			strings = new String[]{"Send to Immunization","View Weekly Details","View Details"};
		}
		builder.setItems(strings, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				switch (which) {
				case 0:
					if (womenDtl.getPregnentId().equals("0")) {
						selectDate(womenDtl);
						System.out.println("Send To Pregnancy........1");
					}
					else {
						System.out.println("Send To Immunization.........1");
					}
					break;
				case 1:
					if (womenDtl.getPregnentId().equals("0")) {
						System.out.println("Send To Immunization.........2");
					}
					else {
						System.out.println("View Weekly Details.......2");
					}
					break;
				case 2:
					if (womenDtl.getPregnentId().equals("0")) {
						System.out.println("View Details.........3.1");
					}
					else {
						System.out.println("View Details.........3.2");
					}
					
					break;
				default:
					break;
				}
			}
		});
		builder.show();
	}
	
	
	/** Show a alert dialog for select date
	 * @param womenDtl object of woman details
	 */
	public void selectDate(final WomenDtl womenDtl) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
	       

			@SuppressLint("SimpleDateFormat")
			public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

	            Calendar calendar = Calendar.getInstance();
	            calendar.set(Calendar.DATE, selectedDay);
	            calendar.set(Calendar.MONTH, selectedMonth);
	            calendar.set(Calendar.YEAR, selectedYear);
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            String date = sdf.format(calendar.getTime()); 
	    		System.out.println(date); //15/10/2013
//	    	    lmcDate.setText(date);
	    	    
	    	    if (view.isShown()) {
	    	    	System.out.println("View Shown ----  "+date+" "+womenDtl.getStatus()+" "+womenDtl.getWomanId()+"  "+MiraConstants.USERID); //15/10/2013
//	    	    	setRecord(String.valueOf(1));
	    	    	updatePregnantWoman(womenDtl, date);
	    	    }
	        }
	    } , cal.get(Calendar.YEAR), 
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
		pickerDialog.setCancelable(true);
		String dateString = MiraConstants.HINDI.equals(MiraConstants.HINDI)?"तारीख का चयन करें":"Select the date  ";
		pickerDialog.setTitle(dateString);
				
		pickerDialog.show();			
		Toast.makeText(getActivity(), "Toast", Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 * @param womenDtl women detail object
	 * @param date date of modification
	 */
	private void updatePregnantWoman(final WomenDtl womenDtl,String date){
		womenDtl.setLmcDate(date);
		womenDtl.setStatus("1");
		databaseHelper.updateWomanRec(womenDtl);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				updtaeWomanPregnant(womenDtl);
			}
		}).start();
		
	}
	
	
	/**
	 * @param womenDtl women detail object
	 */
	public void updtaeWomanPregnant(WomenDtl womenDtl) {
		InputStream inputStream = null;
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			String url = MiraConstants.URL + "updateWomenPregnant";
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", MiraConstants.USERID);
			JSONArray array = new JSONArray();
			JSONObject object = new JSONObject();
			object.put("women_id", womenDtl.getWomanId());
			object.put("anmc_date", womenDtl.getLmcDate());
			object.put("pregnant_status", womenDtl.getStatus());
			array.put(object);
			jsonObject.put("women_update_detail", array);

			JSONArray jsonarr = new JSONArray();
			jsonarr.put(jsonObject);
			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("json", json);
			httpPost.getParams().setParameter("jsonpost", jsonarr);
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null) {
				result = MainMenuActivity.convertInputStreamToString(inputStream);
				JSONObject objectResult = new JSONObject(result);
				JSONArray jsonArray = objectResult.getJSONArray("update_women_pregnant");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject objectpt = jsonArray.getJSONObject(i);
					int women_id= objectpt.getInt("women_id");
					int pregnant_id= objectpt.getInt("pregnant_id");
					womenDtl.setPregnentId(String.valueOf(pregnant_id));
					
					PregnantDtl pregnantDtl= new PregnantDtl();
					pregnantDtl.setWomanId(women_id);
					pregnantDtl.setPregId(pregnant_id);
					pregnantDtl.setLmcDate(womenDtl.getLmcDate());
					databaseHelper.insertPregAnc(pregnantDtl);
					
					databaseHelper.updateWomanRec(womenDtl);
					
				}
				Log.d("MIRA", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		// return result;
	}
	
	/**class to create adapter for house list	 
	 */
	class MyAdapter extends BaseAdapter{
		
		private Context context;
		List<WomenDtl> womenDtls;
		
		public MyAdapter(Context context, List<WomenDtl> womenDtls) {
			this.context = context;
			this.womenDtls = womenDtls;
		}
		
		@Override
		public int getCount() {
			return womenDtls.size();
		}

		@Override
		public Object getItem(int position) {
			return womenDtls.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		class ViewHolder {
			TextView text_house_name;
//			ImageView img_house_preg;
			ImageView img_house_imun;

			public ViewHolder(View view) {
				text_house_name = (TextView) view.findViewById(R.id.text_house_name);
//				img_house_preg = (ImageView) view.findViewById(R.id.img_house_preg);
				img_house_imun = (ImageView) view.findViewById(R.id.img_house_imun);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = null;
			if(view==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.list_item_house, parent, false);
				holder = new ViewHolder(view);
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
			WomenDtl womenDtl = womenDtls.get(position);
			holder.text_house_name.setText(womenDtl.getName());
//			if(!womenDtl.getPregnentId().equals("0")&&womenDtl.getLmcDate().equals("01/01/1900")){
////				System.out.println("Bismillah..."+womenDtl.getLmcDate()+"  "+womenDtl.getPregnentId());
//				holder.img_house_imun.setImageResource(R.drawable.pregnant_icon);
//			}

			///womenDtl.getPregnentId().equals("0")&&
		if(womenDtl.getLmcDate().equals("01/01/1900"))
			{
//				System.out.println("Bismillah..."+womenDtl.getLmcDate()+"  "+womenDtl.getPregnentId());
				holder.img_house_imun.setImageResource(R.drawable.injection_icon);
//				holder.img_house_imun.setImageResource(R.drawable.ques_ind);
			}
			else{
				System.out.println("Bismillah..."+womenDtl.getLmcDate()+"  "+womenDtl.getPregnentId()+"  "+womenDtl.getName());
				holder.img_house_imun.setImageResource(R.drawable.pregnant_icon);
			}
			return view;
		}
		
	}
}
