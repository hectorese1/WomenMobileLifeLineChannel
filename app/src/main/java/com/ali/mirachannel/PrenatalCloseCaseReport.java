package com.ali.mirachannel;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.CloseCaseDtl;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;
/**list fragment will display the list of closed woman*/
public class PrenatalCloseCaseReport extends ListFragment{
	DatabaseHelper databaseHelper;
	List<CloseCaseDtl>closeCaseDtls;
	List<WomenDtl>wmndtls;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		String tag_name = "SELECT *FROM closecase";
		String tag_name = "SELECT * FROM tabwomen tw,closecase cc WHERE tw.womanid =cc.pregid";
		String queryString="SELECT * FROM tabwomen INNER JOIN closecase ON tabwomen.womanid =closecase.pregid";
		databaseHelper = DatabaseHelper.newInstance(getActivity());
		closeCaseDtls = databaseHelper.getCloseCaseStatusByTag(tag_name);
		wmndtls=databaseHelper.getWomenByTag_F(queryString);
//        System.out.println("bhahuuu...."+wmndtls.get(2).getName());

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		MyAdapter adapter = new MyAdapter(getActivity()); 
		setListAdapter(adapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_closecase, container, false);
	}
	
	class MyAdapter extends BaseAdapter{
		Context context;
		
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.context =context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return closeCaseDtls.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return closeCaseDtls.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder{
			TextView closetextViewName;
			TextView closetextViewStatus;
			TextView locationStatus;
			public ViewHolder(View view) {
				closetextViewName = (TextView) view.findViewById(R.id.closetextViewName);
				closetextViewStatus = (TextView) view.findViewById(R.id.closetextViewStatus);
//				locationStatus= (TextView) view.findViewById(R.id.closelocation);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder = null;
			if(view==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.list_closecase_item, parent, false);
				holder = new ViewHolder(view);
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}
//            if(closeCaseDtls.get(position).getPregnentId()== wmndtls.get(position).getPregnentId()) {
			//+"("+closeCaseDtls.get(position).getPregnentId()+")"
				holder.closetextViewName.setText(wmndtls.get(position).getName());
//			holder.closetextViewName.setText();
			System.out.println("Women Record Find 1......"+closeCaseDtls.get(position).getPregnentId()+" "+wmndtls.get(position).getName());
//			}
//			holder.closetextViewName.setText(wmndtls.get(position).getName()+" "+wmndtls.get(position).getPregnentId());

			switch (closeCaseDtls.get(position).getStatus()) {
			case MiraConstants.DELIVERED:
				if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
					holder.closetextViewStatus.setText("प्रसव ");
				}
				else{
					holder.closetextViewStatus.setText("Delivered");
				}

				break;
			case MiraConstants.ABORTED:
				if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
					holder.closetextViewStatus.setText("गर्भपात");
				}
				else {
					holder.closetextViewStatus.setText("Aborted");
				}
				break;
			case MiraConstants.DIED:
				if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
					holder.closetextViewStatus.setText("महिला की मृत्यु ");
				}
				else {
					holder.closetextViewStatus.setText("Died");
//					holder.locationStatus.setText(closeCaseDtls.get(position).getLocation());

				}
				break;
			case MiraConstants.MIGRATED:
				if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
					holder.closetextViewStatus.setText("पलायन ");
				}
				else {
					holder.closetextViewStatus.setText("Migrated");
				}
				break;
			default:
				if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
					holder.closetextViewStatus.setText("प्रसव ");
				}
				else {
					holder.closetextViewStatus.setText("Delivered");
				}
				break;
			}			
			return view;
		}
		
	}
}
