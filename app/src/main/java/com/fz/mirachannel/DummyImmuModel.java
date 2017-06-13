package com.fz.mirachannel;

import com.ali.mirachannel.model.VaccinatedDtl;
import com.ali.mirachannel.model.VaccineDtl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmq161 on 24/11/15.
 */
public class DummyImmuModel {
   static String[]s={"टीकाकरण","ओ.पी.वी.-0","बी.सी.जी.","हैपेटाईटिस बी-1","ओ.पी.वी.-1",
           "डी.पी.टी.-1","हैपेटाईटिस बी-2","ओ.पी.वी.-2 ","डी.पी.टी-2","हैपेटाईटिस बी-3","ओ.पी.वी.-3","डी.पी.टी-3",
           "खसरा","डी.पी.टी. बूस्टर","ओ.पी.वी. बूस्टर","विटामिन-ए","खसरा-बूस्टर"};






    private static String [][] vaccine1={
            {"Hepatitis B0","0","0","1","0"},
            {"OPV 0","0","0","15","0"},
            {"BCG","0","0","365","0"},
            {"Penta","0","45","1825","0"},
            {"OPV 1","0","45","1825","0"},
            {"DPT 1","0","45","1825","0"},
            {"Hepatitis B2","Hepatitis B1","75","1825","30"},
            {"OPV 2","OPV 1","75","1825","30"},
            {"DPT 2","DPT 1","75","1825","30"},
            {"Hepatitis B3","Hepatitis B2","105","1825","30"},
            {"OPV 3","OPV 2 ","105","1825","30"},
            {"DPT 3","DPT 2","105","1825","30"},
            {"Measles","Measles","270","1825","0"},
            {"DPT Booster","DPT 3","270","1825","180"},
            {"OPV Booster","OPV 3","270","1825","180"},
            {"Vitamin A*","Vitamin A*","270","1825","0"},
            {"Measles Booster","Measles","270","1825","180"}
    };

    private static String [][] vaccinated1={
            {"1","2","OPV 0","17/11/2014","1","20/11/2015"},
            {"2","2","BCG","21/01/2015","1","20/11/2015"},
            {"3","2","Hepatitis B0","06/07/2015","1","20/11/2015"},
            {"4","2","Hepatitis B1","06/07/2015","1","20/11/2015"},
            {"5","2","Hepatitis B2","06/07/2015","1","20/11/2015"},
            {"6","2","Hepatitis B3","06/07/2015","1","20/11/2015"},
            {"7","2","DPT 2","06/07/2015","1","20/11/2015"},
            {"8","2","OPV 2","06/07/2015","1","20/11/2015"},
            {"9","2","Vitamin A*","06/07/2015","1","20/11/2015"},
            {"10","2","DPT Booster","06/07/2015","1","20/11/2015"},
            {"11","2","Measles Booster","06/07/2015","1","20/11/2015"},
            {"12","2","OPV Booster","06/07/2015","1","20/11/2015"},
            {"13","2","OPV 2","06/07/2015","1","20/11/2015"},
            {"14","2","Vitamin A*","06/07/2015","1","20/11/2015"},
//            {"15","2","DPT Booster","06/07/2015","1","20/11/2015"},
//            {"16","2","Measles Booster","06/07/2015","1","20/11/2015"},
//            {"17","2","OPV Booster","06/07/2015","1","20/11/2015"}
    };
    private static String [][] vaccine={
            {"Hepatitis B-0","0","0","1","0"},
            {"OPV-0","0","0","15","0"},
            {"BCG","0","0","365","0"},
            {"OPV-1","0","45","1825","0"},
            {"Pentavalent-1","0","45","1825","0"},
            {"OPV-2","OPV-1","75","1825","30"},
            {"Pentavalent-2","Pentavalent-1","75","1825","30"},
            {"OPV-3","OPV-2","105","1825","30"},
            {"Pentavalent-3","Pentavalent-2","105","1825","30"},
            {"Measles","0","270","1825","0"},
            {"DPT Booster","Pentavalent-3","480","1825","180"},
            {"OPV Booster","OPV-3","480","1825","180"},
            {"Vitamin-A","0","270","1825","0"},
            {"Measles Booster","Measles","480","1825","180"},
//            {"OPV Booster","OPV 3","270","1825","180"},
//            {"Vitamin A*","Vitamin A*","270","1825","0"},
//            {"Measles Booster","Measles","270","1825","180"}
    };

    private static String [][] vaccinated={
            {"1","2","Hepatitis B-0","01/01/1900","1","20/11/2015"},
            {"2","2","OPV-0","21/01/2015","1","20/11/2015"},
            {"3","2","BCG","06/07/2015","1","20/11/2015"},
            {"4","2","OPV-1","06/07/2015","1","20/11/2015"},
            {"5","2","Pentavalent-1","06/07/2015","1","20/11/2015"},
            {"6","2","OPV-2","06/07/2015","1","20/11/2015"},
            {"7","2","Pentavalent-2","06/07/2015","1","20/11/2015"},
            {"8","2","OPV-3","06/07/2015","1","20/11/2015"},
            {"9","2","Pentavalent-3","06/07/2015","1","20/11/2015"},
            {"10","2","Measles","06/07/2015","1","20/11/2015"},
            {"11","2","DPT Booster","06/07/2015","1","20/11/2015"},
            {"12","2","OPV Booster","06/07/2015","1","20/11/2015"},
            {"13","2","Vitamin-A","06/07/2015","1","20/11/2015"},
            {"14","2","Measles Booster","06/07/2015","1","20/11/2015"},
//            {"15","2","DPT Booster","06/07/2015","1","20/11/2015"},
//            {"16","2","Measles Booster","06/07/2015","1","20/11/2015"},
//            {"17","2","OPV Booster","06/07/2015","1","20/11/2015"}
    };

    public static List<VaccineDtl> getVaccineDtl(){
        List<VaccineDtl> list=new ArrayList<VaccineDtl>();
        System.out.println("List Size in model"+vaccine.length);
             for(int i=0;i<vaccine.length;i++){
                 VaccineDtl vd=new VaccineDtl();
                 vd.setVaccineName(vaccine[i][0]);
                 vd.setVaccineDepend(vaccine[i][1]);
                 vd.setStartDay(Integer.parseInt(vaccine[i][2]));
                 vd.setEndDay(Integer.parseInt(vaccine[i][3]));
                 vd.setDiffDay(Integer.parseInt(vaccine[i][4]));
                 System.out.println("aaaaaa  "+vd.getVaccineName());
                 list.add(vd);
             }

        return list;

    }
    public static List<VaccinatedDtl> getVaccinatedDtl(){
        List<VaccinatedDtl> list=new ArrayList<VaccinatedDtl>();

        for(int i=0;i<vaccinated.length;i++){
            VaccinatedDtl vd = new VaccinatedDtl();

            vd.setKeyId(Integer.parseInt(vaccinated[i][0]));
            vd.setChildId(vaccinated[i][1]);
            vd.setName(vaccinated[i][2]);
            vd.setDate(vaccinated[i][3]);
            vd.setUpload(vaccinated[i][4]);
            vd.setCreatedAt(vaccinated[i][5]);
            System.out.println("aaaaaa  " + vd.getName());
            list.add(vd);
        }

        return list;
    }

//    public static String setVaccineListInHindi(int index){
//       return s[index];
//    }
}
