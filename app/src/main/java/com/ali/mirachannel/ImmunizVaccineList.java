package com.ali.mirachannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.ChildDtl;
import com.ali.mirachannel.model.VaccinatedDtl;
import com.ali.mirachannel.model.VaccineDtl;
import com.ali.mirachannel.utility.MiraConstants;
import com.fz.mirachannel.DummyImmuModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sherali_ali@yahoo.com
 *
 */
/**listfragment to display the vaccines with icon */
@SuppressLint("ValidFragment")
public class ImmunizVaccineList extends ListFragment {
	DatabaseHelper databaseHelper;
	ChildDtl childDtl;
	List<VaccineDtl>list;
	List<String>statussList;
	HashMap<String, String>hashMap;
	List<VaccinatedDtl> dtls=null;
	int i=100001;
	boolean isdemo;
	String compositeKeyImmu="";

    HashMap<String,VaccineDtl> vaccine_dtl_map_temp;
    //HashMap<String,Map<String,VaccineDtl>>vaccine_dtl_map_send;

	public ImmunizVaccineList(ChildDtl childDtl) {

		this.childDtl = childDtl;		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//        List<VaccinatedDtl> dtls=null;

        if(MiraConstants.IMMUNIZATION_DEMO.equalsIgnoreCase("imu_demo")){
               list= DummyImmuModel.getVaccineDtl();
               dtls=DummyImmuModel.getVaccinatedDtl();
			System.out.println("121212123  "+childDtl.getKeyId());
			System.out.println("we are in the dummyModel....");
            }
        else {
            databaseHelper = DatabaseHelper.newInstance(getActivity());
            list = databaseHelper.getVaccineByTag("");
            String tag_name = "SELECT *FROM tabvaccinestatus WHERE childid='" + childDtl.getChildId() + "'";
			System.out.println("121212124  "+tag_name);
            dtls = databaseHelper.getvaccStatusByTag(tag_name);


        }




		hashMap= new HashMap<String, String>();
       // vaccine_dtl_map=new HashMap<String, VaccineDtl>();
        vaccine_dtl_map_temp=new HashMap<String, VaccineDtl>();

        //vaccine_dtl_map_send=new HashMap<String, Map<String, VaccineDtl>>();
		for (VaccinatedDtl vaccinatedDtl : dtls) {
			hashMap.put(vaccinatedDtl.getName(), vaccinatedDtl.getDate());
			System.out.println("12121212  "+vaccinatedDtl.getName()+"  "+vaccinatedDtl.getDate()+"  "+vaccinatedDtl.getKeyId());

		}
//        for(VaccineDtl vaccineDtl:list){
//			vaccine_dtl_map_temp.put(vaccineDtl.getVaccineName(),vaccineDtl);
//			System.out.println("VacMap rule..."+vaccineDtl.getVaccineName());
//        }
		statussList = calculateVaccineFeature(list);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.v("MIRA", getTag());
		View view = inflater.inflate(R.layout.list_fragment_vaccine, container, false);
		TextView child_name = (TextView) view.findViewById(R.id.child_name);
		child_name.setText(childDtl.getName());
		
		TextView child_dob = (TextView) view.findViewById(R.id.child_dob);
		child_dob.setText(childDtl.getDob());
		
		TextView numberDays = (TextView) view.findViewById(R.id.numDays);
		numberDays.setText("Age " + (MiraConstants.getNumberofDays(childDtl.getDob()) + 1) + " Days");

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

			super.onListItemClick(l, v, position, id);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt("index", position);
			bundle.putString("childid", childDtl.getChildId());
			System.out.println("ChildId in List " + childDtl.getChildId());
			bundle.putStringArrayList("list", (ArrayList<String>) statussList);
		     int status = Integer.parseInt(statussList.get(position).toString().split("#")[1]);
		 if(status==1){
			 compositeKeyImmu=childDtl.getChildId()+"_"+list.get(position).getVaccineName();
			 bundle.putString("compositeKeyImmu",compositeKeyImmu);
			System.out.println("OnListItemClick Status...."+"Bismillah..."+childDtl.getChildId()+"     "+childDtl.getName()+"   "+compositeKeyImmu);
		 }
		else{
			 bundle.putString("compositeKeyImmu","N/A");
		 }


			bundle.putSerializable("vacMapRule", vaccine_dtl_map_temp);//by Fz

			bundle.putSerializable("childDetail", childDtl);//by Fz


			intent.putExtras(bundle);
			intent.setClass(getActivity(), ImmunizViewPager.class);
			startActivity(intent);


	}

	public List<String> calculateVaccineFeature(List<VaccineDtl> list) {
		List<String>arrList = new ArrayList<String>();
		int days = MiraConstants.getNumberofDays(childDtl.getDob())+1;
		for (int i = 0; i < list.size(); i++) {
			String namePstatus="";
			VaccineDtl vaccineDtl = list.get(i);

			namePstatus+=vaccineDtl.getVaccineName();
			if (vaccineDtl.getEndDay() < days) {
				System.out.println("PAST K " + vaccineDtl.getVaccineName());
				namePstatus+="#0";
                vaccine_dtl_map_temp.put(vaccineDtl.getVaccineName(),vaccineDtl);
				System.out.println("Past "+vaccineDtl.getVaccineName());

			} else if (vaccineDtl.getStartDay() <= days	&& days<=vaccineDtl.getEndDay()) {
				namePstatus+="#1";
                vaccine_dtl_map_temp.put(vaccineDtl.getVaccineName(),vaccineDtl);
				System.out.println("PRESENT  " + vaccineDtl.getVaccineName());

			} else {
				namePstatus+="#2";
				System.out.println("FUTURE  " + vaccineDtl.getVaccineName());
			}
//******************************************************************************
//            if(!getVaccineStatus(vaccineDtl)){
//                namePstatus+="#"+vaccineDtl.getVaccineName();//hide Vaccine Button
//            }
//            else{
//                namePstatus+="#4";//show Vaccine Button
//            }

//            if(hashMap.containsKey(vaccineDtl.getVaccineName())){
//                vaccineDtl.setVaccineTakenDate(hashMap.get(vaccineDtl.getVaccineName()));
//            }
//
//            if(!vaccineDtl.getVaccineDepend().equalsIgnoreCase("0")){
//
//                Map<String,VaccineDtl>map=new HashMap<String, VaccineDtl>();
//                map.put(vaccineDtl.getVaccineName(), vaccineDtl);
//                map.put(vaccine_dtl_map.get(vaccineDtl.getVaccineDepend()).getVaccineName(),vaccine_dtl_map.get(vaccineDtl.getVaccineDepend()));
//
//               if(calculateVaccineFeatures(map,vaccineDtl).equals("4")){
//                   vaccine_dtl_map_send.put(vaccineDtl.getVaccineName(),map);
//                  //namePstatus+="#due";//due
//               }
//               else{
//                  // namePstatus+="#miss";//missed
//               }
//
//
//            }
//
//            else{
//                Map<String,VaccineDtl>map=new HashMap<String, VaccineDtl>();
//                map.put(vaccineDtl.getVaccineName(),vaccineDtl);
//                vaccine_dtl_map_send.put(vaccineDtl.getVaccineName(),map);
//                //namePstatus+="#due";
//            }
//***************************************************************************************************************
			arrList.add(namePstatus);

            System.out.println("Vaccine Name " + vaccineDtl.getVaccineName() +" Vac_Dep "+vaccineDtl.getVaccineDepend()+"  Vac_Diff "+vaccineDtl.getDiffDay());
		}
       // System.out.println("vaccine_dtl_map_send "+vaccine_dtl_map_send);
		return arrList;
	}
    private boolean checkDatesRange(String testDate,String startDate,String endDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date=null,date1=null,date2=null;
        try {
            date =  formatter.parse(testDate);
            date1 = formatter.parse(startDate);

            Calendar c = Calendar.getInstance();
            c.setTime(date1); // Now use today date.
            c.add(Calendar.DATE, -1); // substracting 1 days
            date1 = formatter.parse(formatter.format(c.getTime()));

            date2 = formatter.parse(endDate);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date2); // Now use today date.
            c1.add(Calendar.DATE, 1); // Adding 1 days
            date2 = formatter.parse(formatter.format(c1.getTime()));

            System.out.println("Choosen date "+date+"  Two dates "+date1 +" "+date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.after(date1) && date.before(date2);

    }

	public String calculateVaccineFeatures(Map<String,VaccineDtl> map,VaccineDtl vaccineDtl) {
        VaccineDtl vaccineDtlDepend=map.get(vaccineDtl.getVaccineDepend());

        if(!vaccineDtlDepend.getVaccineTakenDate().equals("NA")){
           String takenDate=vaccineDtlDepend.getVaccineTakenDate();
           String actualDate=getActualDate(vaccineDtl.getDiffDay(),takenDate);
           String doseDate=getActualDate(vaccineDtl.getEndDay(),childDtl.getDob());

           if(getDate(actualDate).after(getDate(doseDate))){
               return "3";//Vaccine missed
           }


        }
        return  "4";//Vaccine due
	}
    private Date getDate(String Date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = formatter.parse(Date);
            return date;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  new Date();
    }

    private String getActualDate(int daysAdd,String dateToBeAdded){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Date date = formatter.parse(dateToBeAdded);
            System.out.println(date);
            System.out.println(formatter.format(date));


            Calendar c = Calendar.getInstance();
            c.setTime(date); // Now use today date.
            c.add(Calendar.DATE, daysAdd); // Adding 5 days

            String output = formatter.format(c.getTime());
            System.out.println(output);

            Date formateddate=formatter.parse(output);
            Calendar c1 = Calendar.getInstance();
            String formattedDate = formatter.format(c1.getTime());
            Date selectedDate=formatter.parse(formattedDate);

            if(formateddate.after(selectedDate)){
                output=formatter.format(c1.getTime());
            }
            return  output;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return  "";
    }
	/**class for crate adapter for list items*/
	class MyAdapter extends BaseAdapter {
		private Context context;
		
		public MyAdapter(Context context) {
			this.context = context;			
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		class ViewHolder{
			RelativeLayout layout;
			ImageView imageView;
			TextView vaccName;
			TextView vaccDate;
			TextView vaccDiff;
            TextView vaccStatus;

			public ViewHolder(View view) {
				layout = (RelativeLayout) view.findViewById(R.id.vaccineLayout);
				imageView = (ImageView) view.findViewById(R.id.vaccineImgMark);
				vaccName = (TextView) view.findViewById(R.id.vaccineName);
				vaccDate = (TextView) view.findViewById(R.id.vaccineStatus);
				vaccDiff = (TextView) view.findViewById(R.id.vaccineDiff);
                vaccStatus = (TextView) view.findViewById(R.id.vaccineStatus);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder=null;
			VaccineDtl vaccineDtl = list.get(position);
			if(view==null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.adapter_vaccine, parent, false);
				holder = new ViewHolder(view);
				view.setTag(holder);
			}else{
				holder = (ViewHolder) view.getTag();
			}

//            if(MiraConstants.LANGUAGE.equalsIgnoreCase(MiraConstants.HINDI)){
//               vaccineDtl.setVaccineName(DummyImmuModel.setVaccineListInHindi(position));
//            }
          if(MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)){
			  holder.vaccName.setText(getVaccineHindi(vaccineDtl.getVaccineName()));
		  }
			else{
			  holder.vaccName.setText(vaccineDtl.getVaccineName());
		  }
//                holder.vaccName.setText(vaccineDtl.getVaccineName());


            int status = Integer.parseInt(statussList.get(position).toString().split("#")[1]);

            if(status==0){
                holder.layout.setBackgroundColor(getResources().getColor(R.color.red_color_imu));

//                if(!getVaccineStatus(vaccineDtl) && !hashMap.containsKey(vaccineDtl.getVaccineName())){
//                  holder.vaccStatus.setText("missed");
//                }
//                else{
//                    holder.vaccStatus.setText("Small Text");
//                }
            }
            else if(status==1){
                holder.layout.setBackgroundColor(getResources().getColor(R.color.green_color_imu));
            }
            else{
                holder.layout.setBackgroundColor(getResources().getColor(R.color.blue_color_imu));
            }

			if(hashMap.containsKey(vaccineDtl.getVaccineName())){
				System.out.println("Bismillah...." + hashMap.get(vaccineDtl.getVaccineName()));
//				holder.vaccDiff.setText(hashMap.get(vaccineDtl.getVaccineName()));
////					//holder.imageView.setImageResource(R.drawable.injection_green);
//					holder.layout.setBackgroundColor(getResources().getColor(R.color.yellow_color_imu));

				/////////////////////////FAisal//////////////////////////////////////////////
				if(!hashMap.get(vaccineDtl.getVaccineName()).equals("01/01/1900")) {
					holder.vaccDiff.setText(hashMap.get(vaccineDtl.getVaccineName()));
					//holder.imageView.setImageResource(R.drawable.injection_green);
					holder.layout.setBackgroundColor(getResources().getColor(R.color.yellow_color_imu));
				}
				else if(hashMap.get(vaccineDtl.getVaccineName()).equals("01/01/1900"))
				{
					holder.vaccDiff.setText("Missed");
					//holder.imageView.setImageResource(R.drawable.injection_green);
					holder.layout.setBackgroundColor(getResources().getColor(R.color.yellow_color_imu));
				}
				/////////////////////////FAisal//////////////////////////////////////////////


            }else{

				holder.vaccDiff.setText("[" + vaccineDtl.getStartDay() + " - " + vaccineDtl.getEndDay() + "]");

                // holder.vaccDiff.setText("");
               // holder.imageView.setImageResource(R.drawable.injection_small_icon);
			}

			return view;
		}

        private int getWeeks(int days){

            return days/7;
        }

	}
    private boolean getVaccineStatus(VaccineDtl vaccineDtl){
          int days=MiraConstants.getNumberofDays(childDtl.getDob());

        if(days>vaccineDtl.getEndDay()){
            return  false;
        }
        return  true;
    }



   String getVaccineHindi(String Vacname){
      String a="N/A";
		if(Vacname.equals("Hepatitis B-0")){
         a="हैपेटाइटिस बी-0";
		}
	   else if(Vacname.equals("OPV-0")){
			a="ओ पी वी-0";
		}
		else if(Vacname.equals("BCG")){
			a="बी सी जी ";
		}
		else if(Vacname.equals("OPV-1")){
			a="ओ पी वी-1";
		}
		else if(Vacname.equals("Pentavalent-1")){
			a="पेंटावेलेंट-1";
		}
		else if(Vacname.equals("OPV-2")){
			a="ओ पी वी -2";
		}
		else if(Vacname.equals("Pentavalent-2")){
			a="पेंटावेलेंट-2";
		}
		else if(Vacname.equals("OPV-3")){
			a="ओ पी वी-3";
		}else if(Vacname.equals("Pentavalent-3")){
			a="पेंटावेलेंट-3";
		}else if(Vacname.equals("Measles")){
			a="खसरा ";
		}else if(Vacname.equals("DPT Booster")){
			a="डी पी टी बूस्टर ";
		}else if(Vacname.equals("OPV Booster")){
			a="ओ पी वी बूस्टर";
		}else if(Vacname.equals("Vitamin-A")){
			a="विटामिन- ए ";
		}else if(Vacname.equals("Measles Booster")){
			a="खसरा बूस्टर";
		}

	   return a;
	}

}
