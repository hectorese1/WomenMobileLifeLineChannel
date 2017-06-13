package com.fz.mirachannel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

/**
 * Created by zmq161 on 8/12/15.
 */
public class MyPiccaso {
    static Context mycontext;
    static View myView;

    public static void getView(Context context,int drawable,View view){
        myView=view;
        mycontext=context;
        if(myView instanceof RelativeLayout){
            myView=(RelativeLayout)myView;
        }
        else if(myView instanceof ImageView){
            myView=(ImageView)myView;
        }
        else if(myView instanceof FrameLayout){
            myView=(FrameLayout)myView;
        }
        RequestCreator requestCreator=Picasso.with(context).load(drawable);
        requestCreator.into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                myView.setBackground(new BitmapDrawable(mycontext.getResources(), bitmap));
                System.out.println("onBitmapLoaded");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
               System.out.println("onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                System.out.println("onPrepareLoad");
            }
        });

    }

    public void showView(){
           Target target = new Target() {
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            }

        };
    }
}
