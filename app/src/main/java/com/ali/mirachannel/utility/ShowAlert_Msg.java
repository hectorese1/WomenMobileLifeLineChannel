package com.ali.mirachannel.utility;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by zmq153 on 26/5/16.
 */
public class ShowAlert_Msg {
    public static AlertDialog.Builder alert(Context c,int cases,String title,String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(c);

        switch (cases){
            case 1:
                dialog.setTitle(title);
                dialog.setMessage(msg);
                break;
            default:
                break;
        }
        return dialog;
    }
}
