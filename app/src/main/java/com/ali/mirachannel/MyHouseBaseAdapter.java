package com.ali.mirachannel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.HouseDtl;

import java.util.List;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**create adapter for house togenerate list of houses*/
public class MyHouseBaseAdapter extends BaseAdapter{
	Context context;
	DatabaseHelper databaseHelper;
	List<HouseDtl>houseDtls;
	public MyHouseBaseAdapter(Context context) {
		this.context = context;
		databaseHelper = DatabaseHelper.newInstance(context);;
		houseDtls = databaseHelper.gethouseByTag("houseid!=0");
		System.out.println(houseDtls);
	}

	class ViewHolder{
		TextView hnum;
		TextView familyHead;
		TextView landmark;
		public ViewHolder(View view) {
			hnum = (TextView)view.findViewById(R.id.child_name);
			familyHead = (TextView)view.findViewById(R.id.child_dob);
			landmark = (TextView)view.findViewById(R.id.mother_name);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		ViewHolder holder = null;
		if(rowView==null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.house_list_item, parent, false);
			holder = new ViewHolder(rowView);
			rowView.setTag(holder);
		}else{
			holder = (ViewHolder) rowView.getTag();
		}

//		TextView hnum = (TextView)rowView.findViewById(R.id.child_name);
		holder.hnum.setText(houseDtls.get(position).getHouseNumber());

//		TextView familyHead = (TextView)rowView.findViewById(R.id.child_dob);
		holder.familyHead.setText(houseDtls.get(position).getFamilyHead());

//		TextView landmark = (TextView)rowView.findViewById(R.id.mother_name);
		holder.landmark.setText(houseDtls.get(position).getLandMark());
		return rowView;
	}
	@Override
	public int getCount() {
		return houseDtls.size();
	}

	@Override
	public Object getItem(int arg0) {
		return houseDtls.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

}
