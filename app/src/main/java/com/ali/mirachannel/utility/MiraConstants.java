package com.ali.mirachannel.utility;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.ali.mirachannel.MainActivity;
import com.ali.mirachannel.helper.DatabaseHelper;
import com.ali.mirachannel.model.VaccineDtl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class  MiraConstants {
//	public static Context context;
	public static final String URLMIRAEN=	"http://www.mirachannel.org/mirasound/en/";
	public static final String URLMIRAHN=	"http://www.mirachannel.org/mirasound/hn/";
	public static final String URL ="http://connect2mfi.org/mira/android/";
//	public static final String URL ="http://192.168.1.101:80/mira/android/";
	public static int USERID; 
	public static String USERNAME;
	public static  String PASSWORD;
	public static  String USERNAME_F;
	
	public static final String HINDI = "hindi" ;
	public static final String ENGLISH = "english" ;
	public static  String LANGUAGE = HINDI;
	
	public static final String PREFERENCES = "mira" ;
	public static SharedPreferences preferences;
	public static final String PreNatal_Prefrences="prenatal";
	public static final String Immuniz_Prefrences="immuniz";
	public static final String NeoNatal_Prefrences="neonatal";
	public static final String FP_Prefrences="family_planning";
	public static final String Adolescent_Girl_Prefrences="adolescent_girl";
	public static final String LOGINCODE = "login_code" ;
	public static final String USERDETAILS = "user_detail" ;
	public static final String NAME = "name" ;
	public static final String ID = "id" ;
	
	
	public static final String view = "view";
	public static final String closecase = "closecase";
	
	public static final int DELIVERED = 1;
	public static final int ABORTED = 2;
	public static final int DIED = 3;
	public static final int MIGRATED = 4;
	public static final int Hospital=1;
	public static final int House=2;
	public static int noOfChlidLive=0;
	public static String serverYear,serverMonth,serverDay;
	public static int serverYearInt,serverMonthInt,serverDayInt;


    // **********BY FZ**************
    public static  String PRENATAL_DEMO = "pre_demo12";
    public static  boolean isIMU_VIEW_CHANGE = false;
    public static  String IMMUNIZATION_DEMO = "imu_demo12";
    //********************************

	public static  MediaPlayer MEDIA_PLAYER = new MediaPlayer();

	public static int calculateWeekNumber(String dateString) {
		Date date = new Date();
		System.out.println("Today's Date from MiraConstt....."+date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = sdf.parse(dateString);

		} catch (ParseException e) { 
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date date1=new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date1=sdf1.parse(MainActivity.serverDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date1);
		int i = 0;
		while (calendar.before(calendar2)) {
			calendar.add(Calendar.DATE, 7);
			i++;
		}
		return i;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static int getNumberofDays(String dateString) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		calendar.setTime(date);


		Date date1=new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date1=sdf1.parse(MainActivity.serverDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}


		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date1);
		
		return  (int)( (calendar2.getTime().getTime() - calendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}
	
//	public static String[] name = new String[] {
//            "Hepatitis B0",
//            "OPV 0",
//		    "BCG",
//
//            //"Hepatitis B1",
//            "Penta 1",
//            "OPV 1",
//           // "DPT 1",
//
//           // "Hepatitis B2",
//            "Penta 2",
//            "OPV 2",
//	    	//"DPT 2",
//
//           // "Hepatitis B3",
//            "Penta 3",
//            "OPV 3",
//           // "DPT 3",
//
//            "Measles",
//		    "DPT Booster",
//            "OPV Booster",
//            "Vitamin A*",
//            "Measles Booster" };

    public static String[] name = new String[] {
            "Hepatitis B-0",
            "OPV-0",
            "BCG",
            "OPV-1",
            "Pentavalent-1",
            "OPV-2",
            "Pentavalent-2",
            "OPV-3",
            "Pentavalent-3",
            "Measles",
            "DPT Booster",
            "OPV Booster",
            "Vitamin-A",
            "Measles Booster"
    };
	
	public static String[] depdname = new String[]{
            "0",
            "0",
            "0",
            "0",
            "0",

            "OPV-1",
            "Pentavalent-1",
            "OPV-2",
            "Pentavalent-2",
            "0",
            "Pentavalent-3",
            "OPV-3",
            "0",
            "Measles"

    };

//	public static int startEndDate[][] = {
//	{0,1,0},//Hepatitis B0
//	{0,15,0},//OPV 0
//	{0,365,0},//BCG
//	{45,1825,0},//Hepatitis B1
//	{45,1825,0},//OPV 1
//	{45,1825,0},//DPT 1
//	{75,1825,30},//Hepatitis B2
//	{75,1825,30},//OPV 2
//	{75,1825,30},//DPT 2
//	{105,1825,30},//Hepatitis B3
//	{105,1825,30},//OPV 3
//	{105,1825,30},//DPT 3
//	{270,1825,0},//Measles
//	{270,1825,180},//DPT Booster
//	{270,1825,180},//OPV Booster
//	{270,1825,0},//Vitamin A*
//	{270,1825,180},//Measles Booster
//	};

    public static int startEndDate[][] = {
            {0,1,0},//Hepatitis B0
            {0,15,0},//OPV 0
            {0,365,0},//BCG
            {45,1825,0},//OPV 1
            {45,1825,0},//PENTA 1

            {75,1825,30},//OPV 2
            {75,1825,30},//PENTA 2
            {105,1825,30},//OPV 3

            {105,1825,30},//PENTA 3
            {270,1825,0},//Measles
            {480,1825,180},//DPT Booster
            {480,1825,180},//OPV Booster
            {270,1825,0},//Vitamin A*
            {480,1825,180},//Measles Booster
    };
	
	
	public static void vaccineRules(DatabaseHelper helper) {
		
		for (int i = 0; i < name.length; i++) {
			VaccineDtl dtl = new VaccineDtl();
			dtl.setVaccineName(name[i]);
			dtl.setVaccineDepend(depdname[i]);
			dtl.setStartDay(startEndDate[i][0]);
			dtl.setEndDay(startEndDate[i][1]);
			dtl.setDiffDay(startEndDate[i][2]);
            if(!MiraConstants.IMMUNIZATION_DEMO.equalsIgnoreCase("imu_demo"))helper.insetVaccineTable(dtl);
		}		
	}
	public static Map<String,String> getVacMappingRules(){
        return new HashMap<String, String>();
    }

	public static String getDateTime() {		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static boolean parseServerDate(String serverdate){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = sdf.parse(serverdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		serverYear=String.valueOf(cal.get(Calendar.YEAR));
		if(cal.get(Calendar.MONTH)<9){
			serverMonth="0"+String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		else{
			serverMonth=String.valueOf(cal.get(Calendar.MONTH)+1);
		}

		serverDay=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));


		serverYearInt=Integer.parseInt(serverYear);
		serverMonthInt=Integer.parseInt(serverMonth);
		serverDayInt=Integer.parseInt(serverDay);
		System.out.println("Today's Date from serverDate....."+serverYearInt+"   "+serverMonthInt+"    "+serverDayInt);
		return true;
	}
}
