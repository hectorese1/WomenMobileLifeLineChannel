package com.ali.mirachannel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.List;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**show details of vaccine for child immunized*/
public class ImmunzChildList extends ListFragment{
	DatabaseHelper databaseHelper;
	List<ChildDtl>childDtls;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View view = inflater.inflate(R.layout.list_fragment_child, container, false);		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		MyAdapter adapter = new MyAdapter(getActivity());
		setListAdapter(adapter);
	}
	
		
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		ChildDtl childDtl = (ChildDtl) l.getAdapter().getItem(position);
        System.out.println("ChildDtl "+childDtl);
		Toast.makeText(getActivity(), "Click  "+childDtl.getName(), Toast.LENGTH_SHORT).show();		
		
		FragmentTransaction transaction  = getFragmentManager().beginTransaction();				
		ImmunizVaccineList immunizVaccineList = new ImmunizVaccineList(childDtl);
		transaction.replace(R.id.mainContent, immunizVaccineList, "immunzList");
		transaction.addToBackStack("immunzList");
		transaction.commit();
	}



	class MyAdapter extends BaseAdapter{
		public MyAdapter(Context context) {
			this.context = context;
			databaseHelper = DatabaseHelper.newInstance(getActivity());;			
			String queryString ="SELECT *FROM tabchild tc,tabwomen tw WHERE  tc.childid!=0 AND tc.womanid=tw.womanid";
//			String queryString ="SELECT *FROM tabchild tc,tabwomen tw WHERE  tc.childid==0 AND tc.womanid=tw.womanid";
			childDtls = databaseHelper.getChildByQuery(queryString);
		}
		
		Context context;
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = null;
			ChildDtl childDtl = childDtls.get(position);

			if(convertView==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.child_list_item, parent, false);
			}else{
				rowView = convertView;
			}
			
//			LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.child_list_layout);
//			if(position%2==0){
//			
//			layout.setBackgroundColor(getResources().getColor(R.color.childcell_color));
//			}else{
//				layout.setBackgroundColor(getResources().getColor(R.color.whit_color));
//			}
			TextView child_name = (TextView)rowView.findViewById(R.id.child_name);
			child_name.setText(childDtl.getName());
			TextView mother_name = (TextView)rowView.findViewById(R.id.mother_name);
			mother_name.setText(childDtl.getWomanName());
			
			TextView child_dob = (TextView)rowView.findViewById(R.id.child_dob);
			child_dob.setText(childDtl.getDob());
//			
			TextView numberofdays = (TextView)rowView.findViewById(R.id.numberofdays);
			numberofdays.setText(MiraConstants.getNumberofDays(childDtl.getDob())+" days");
			return rowView;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return childDtls.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return childDtls.get(arg0);
		}
		
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return childDtls.get(arg0).getKeyId();
		}		
	}
}
