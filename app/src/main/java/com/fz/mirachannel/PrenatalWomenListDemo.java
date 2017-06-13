package com.fz.mirachannel;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.mirachannel.WeeklyInfo;
import com.ali.mirachannel.model.WomenDtl;
import com.ali.mirachannel.utility.MiraConstants;
/**display list of prenatla woment*/
public class PrenatalWomenListDemo extends ListFragment{
    private String action = MiraConstants.view;
//    PrenatalWomenListDemoComm comm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.list_fragment, container, false);

        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // comm = (PrenatalWomenListDemoComm) activity;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.v("MIRA", getTag());
//        MyWomanBaseAdapter adapter = new MyWomanBaseAdapter(getActivity());
//        setListAdapter(adapter);

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
        intent.setClass(getActivity(), WeeklyInfo.class);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
      // comm.getprenatalListElemntDemo();
    }



}
