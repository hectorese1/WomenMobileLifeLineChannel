package com.ali.mirachannel;

import com.ali.mirachannel.R;
import com.ali.mirachannel.utility.MiraConstants;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**class contains the fragment of main menu items*/
public class MainMenuFragment extends Fragment{
	Context context;
	CommunicatorMainMenu comMenu;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
		comMenu = (CommunicatorMainMenu) activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View view = inflater.inflate(R.layout.mainmenu_screen, container, false);
		GridView listView = (GridView) view.findViewById(R.id.gridView);
		MyAdapter adapter = new MyAdapter(context);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				comMenu.getItemId(position);
			}
		});
		return view;
	}
	
	class MyAdapter extends BaseAdapter{
		
		String[]listItems;//=new String[21];
		int[]image = {R.drawable.house_registration,R.drawable.view_registered,R.drawable.prenatal,
				R.drawable.immunization,R.drawable.neo_natal,R.drawable.family_planning,
				R.drawable.adolecent_girl,R.drawable.demo,R.drawable.mira_3,
				R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3,
				R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3,
				R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3,
				R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3
				};
		Context context;
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
			listItems= MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?context.getResources().getStringArray(R.array.main_menu_H):context.getResources().getStringArray(R.array.main_menu);
//			listItems = context.getResources().getStringArray(R.array.main_menu);
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
				rowView = inflater.inflate(R.layout.menu_grid, parent, false);
			}else{
				rowView = convertView;
			}
			
//			LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.grid_lin_layout);
//			layout.setBackgroundResource(image[position]);
			
			TextView textView = (TextView) rowView.findViewById(R.id.grid_textView);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.grid_imageView);
			textView.setText(listItems[position]);
			imageView.setImageResource(image[position]);
			return rowView;
		}		
	}
	
	interface CommunicatorMainMenu{
		public int getItemId(int position);				
	}
}
