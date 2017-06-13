package com.ali.mirachannel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**create adapter for woman list*/
public class MyWomanBaseAdapter extends BaseAdapter{
	Context context;
	DatabaseHelper databaseHelper;
	List<WomenDtl>womenDtls,womenDtlssec;
	int s=0;
//	List<PregnantDtl>pregnantDtls;
	int weekNumber;
	public MyWomanBaseAdapter(Context context) {
		this.context = context;
		this.databaseHelper = DatabaseHelper.newInstance(context);;
//		this.womenDtls = databaseHelper.getWomenByTag("womanid!=0");
//		 String queryString ="SELECT *FROM tabwomen tw,tabhouse th WHERE  tw.womanid!=0 AND tw.houseid=th.houseid AND tw.status = '1'";
//		String queryString1="SELECT *FROM tabwomen tw,tabhouse th,pregnant pg WHERE  tw.womanid!=0 AND pg.pregid!=0 AND tw.womanid = pg.womanid AND tw.houseid=th.houseid AND tw.status = '1'";
//
		String queryString="SELECT *FROM tabwomen tw,tabhouse th,pregnant pg WHERE  tw.womanid!='0' AND pg.pregid!='0' AND tw.womanid = pg.womanid AND tw.houseid=th.houseid AND tw.status = '1'";
//		 //		 String queryString = "SELECT *FROM pregnant";
		 this.womenDtls = databaseHelper.getWomenByQuery(queryString);

		System.out.println("MyWomenBAse Adpter==2 "+ womenDtls.size());
//		 this.pregnantDtls = databaseHelper.getPregnantANC(queryString);
//

	}
	public MyWomanBaseAdapter(Context c,int s){
		this.context=c;
		this.s=s;

		this.databaseHelper=DatabaseHelper.newInstance(c);
//		String query="SELECT * FROM tabwomen LEFT OUTER JOIN pregnant ON tabwomen.womanid =pregnant.womanid";
		String query="SELECT * FROM tabwomen WHERE NOT EXISTS (SELECT * FROM pregnant WHERE tabwomen.womanid =pregnant.womanid)";
		List<WomenDtl> w=databaseHelper.getWomenByTag_F(query);
		System.out.println("Women Without pregnant ..."+w.size());
//		this.womenDtlssec=databaseHelper.getWomenByTag("womanid!=0");

//		this.womenDtls=getD(womenDtlssec);
//		List<WomenDtl>womenDtlsp = new ArrayList<WomenDtl>();
//		for(int i=0;i<3;i++){
//			int an=MiraConstants.calculateWeekNumber(womenDtlssec.get(i).getLmcDate());
//	        if(an>20){
//				womenDtlsp.add(womenDtlssec.get(i));
//			}
//		}
//		this.womenDtls = databaseHelper.getWomenByTag("womanid!=0");
		this.womenDtls=w;
		System.out.println("MyWomenBAse Adpter==1 "+womenDtls.size());
//		this.womenDtls=womenDtlsp;
	}

	class ViewHolder {
		TextView listwomanName;
		TextView listhouseNumbers;
		TextView weeknumber;

		ImageView anc1_img;
		ImageView anc2_img;
		ImageView anc3_img;
		ImageView anc4_img;
		ImageView profile_img;

		public ViewHolder(View view) {
			listwomanName = (TextView) view.findViewById(R.id.listwomanName);
			listhouseNumbers = (TextView) view.findViewById(R.id.listhouseNumbers);
			weeknumber = (TextView) view.findViewById(R.id.weeknumber);



			anc1_img = (ImageView) view.findViewById(R.id.anc1_img);
			anc2_img = (ImageView) view.findViewById(R.id.anc2_img);
			anc3_img = (ImageView) view.findViewById(R.id.anc3_img);
			anc4_img = (ImageView) view.findViewById(R.id.anc4_img);

            profile_img = (ImageView) view.findViewById(R.id.imageView5);
            //*******By Fz***************************
            if(MiraConstants.isIMU_VIEW_CHANGE){
            anc1_img.setVisibility(View.GONE);
            anc2_img.setVisibility(View.GONE);
            anc3_img.setVisibility(View.GONE);
            anc4_img.setVisibility(View.GONE);
            listhouseNumbers.setVisibility(View.GONE);

		            }
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder = null;
		if(rowView==null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.women_list_items, parent, false);
			holder = new ViewHolder(rowView);
			rowView.setTag(holder);
		}else{
//			rowView = convertView;
			holder = (ViewHolder) rowView.getTag();
		}
		WomenDtl womenDtl = womenDtls.get(position);

//		TextView hnum = (TextView)rowView.findViewById(R.id.listwomanName);
//		hnum.setText(womenDtls.get(position).getName());

		holder.listwomanName.setText(womenDtl.getName());


//		TextView week = (TextView)rowView.findViewById(R.id.listhouseNumbers);
//		week.setText(womenDtls.get(position).getHouseNumber());

		holder.listhouseNumbers.setText(womenDtl.getHouseNumber());

		if(s!=1) {
			this.weekNumber = MiraConstants.calculateWeekNumber(womenDtls.get(position).getLmcDate());
		}
		System.out.println("WomenId in the MywomenAdapter....."+womenDtl.getPregnentId()+"     "+weekNumber);
//		TextView weeknumber = (TextView)rowView.findViewById(R.id.weeknumber);

//		this.weekNumber = MiraConstants.calculateWeekNumber(womenDtl.getLmcDate());
//		TextView weeknumber = (TextView)rowView.findViewById(R.id.weeknumber);
        if(!MiraConstants.isIMU_VIEW_CHANGE) {
            if (this.weekNumber <= 40) {
                holder.weeknumber.setText(weekNumber+1 + "");
                holder.profile_img.setImageResource(R.drawable.profile_photo);
            } else {
                holder.weeknumber.setText("40");
                holder.profile_img.setImageResource(R.drawable.ind_2);
            }
        }

      //-------------------- Fz------------------------
       if(MiraConstants.isIMU_VIEW_CHANGE) {
           holder.weeknumber.setText(womenDtl.getHouseNumber());
//           holder.profile_img.setImageResource(R.drawable.profile_photo);
       }
       // ---------------------------------------------
		if(s!=1)
		{
//		---------------- ANC_1--------------------------------
			if (womenDtl.getAnc_1() != 0) {
				if (womenDtl.getAnc_1() == 1) {
					holder.anc1_img.setImageResource(R.drawable.anc_2); // Risk Free Anc  //By Fz
				} else if (womenDtl.getAnc_1() == 2) {
					holder.anc1_img.setImageResource(R.drawable.anc_3); // Critical Anc
				}
			} else {
				if (weekNumber >= 1 && weekNumber <= 13) {
					holder.anc1_img.setImageResource(R.drawable.anc_1);// Present Anc
				} else if (weekNumber > 13) {
					holder.anc1_img.setImageResource(R.drawable.anc_5); // Missed Anc
				} else {
					holder.anc1_img.setImageResource(R.drawable.anc_0); // Future Due Anc
				}
			}
//		---------------- ANC_2--------------------------------
			if (womenDtl.getAnc_2() != 0) {
				if (womenDtl.getAnc_2() == 1) {
					holder.anc2_img.setImageResource(R.drawable.anc_2);
				} else if (womenDtl.getAnc_2() == 2) {
					holder.anc2_img.setImageResource(R.drawable.anc_3);
				}
			} else {
				if (weekNumber >= 14 && weekNumber <= 27) {
					holder.anc2_img.setImageResource(R.drawable.anc_1);
				} else if (weekNumber > 27) {
					holder.anc2_img.setImageResource(R.drawable.anc_5);
				} else {
					holder.anc2_img.setImageResource(R.drawable.anc_0);
				}
			}
//		---------------- ANC_2--------------------------------
			if (womenDtl.getAnc_3() != 0) {
				if (womenDtl.getAnc_3() == 1) {
					holder.anc3_img.setImageResource(R.drawable.anc_2);
				} else if (womenDtl.getAnc_3() == 2) {
					holder.anc3_img.setImageResource(R.drawable.anc_3);
				}
			} else {
				if (weekNumber >= 28 && weekNumber <= 35) {
					holder.anc3_img.setImageResource(R.drawable.anc_1);
				} else if (weekNumber > 35) {
					holder.anc3_img.setImageResource(R.drawable.anc_5);
				} else {
					holder.anc3_img.setImageResource(R.drawable.anc_0);
				}
			}
//		---------------- ANC_2--------------------------------
			if (womenDtl.getAnc_4() != 0) {
				if (womenDtl.getAnc_4() == 1) {
					holder.anc4_img.setImageResource(R.drawable.anc_2);
				} else if (womenDtl.getAnc_4() == 2) {
					holder.anc4_img.setImageResource(R.drawable.anc_3);
				}
			} else {
				if (weekNumber >= 36 && weekNumber <= 43) {
					holder.anc4_img.setImageResource(R.drawable.anc_1);
				} else if (weekNumber > 43) {
					holder.anc4_img.setImageResource(R.drawable.anc_5);
				} else {
					holder.anc4_img.setImageResource(R.drawable.anc_0);
				}
			}


		}

		return rowView;
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
		return womenDtls.get(position).getKeyId();
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}




	public List<WomenDtl> getD(List<WomenDtl> women){

		List<WomenDtl>womenDtlsp = new ArrayList<WomenDtl>();
		for(int i=0;i<womenDtlssec.size();i++){
			int an=MiraConstants.calculateWeekNumber(womenDtlssec.get(i).getLmcDate());
	        if(an>20){
				womenDtlsp.add(womenDtlssec.get(i));
			}
		}
		return womenDtlsp;
	}
}