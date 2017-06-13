package com.fz.mirachannel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.mirachannel.R;
import com.ali.mirachannel.utility.MiraConstants;

/**
 * Created by zmq161 on 4/12/15.
 */
public class AdolscentMenu extends Fragment {

    Context context;
    AdolscentMenuComm comMenu;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Log.v("MIRA", getTag());
        context = activity;
        comMenu = (AdolscentMenuComm) activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainmenu_screen, container, false);
        GridView listView = (GridView) view.findViewById(R.id.gridView);
        MyAdapter adapter = new MyAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
                comMenu.getAdolscentMenuItemId(position);
            }
        });
        return view;
    }

    class MyAdapter extends BaseAdapter {

        String[]listItems;//=new String[21];
        int[]image = {R.drawable.kishori_gyan,R.drawable.kishori_kahaniya,R.drawable.kishori_khel,
                R.drawable.demo_15,R.drawable.demo,R.drawable.demo_3,
                R.drawable.demo_1,R.drawable.demo_6,R.drawable.mira_3,
                R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3,
                R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3,
                R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3,
                R.drawable.mira_3,R.drawable.mira_3,R.drawable.mira_3
        };
        Context context;
        public MyAdapter(Context context) {
            String[] mainMenuEng = {"Learning for Girls", "Stories for Girls", "Games for Girls"};
            String[] mainMenuHnd = {"किशोरी ज्ञान", "किशोरी कहनियाँ", "किशोरी खेल"};

            this.context = context;
            listItems = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?mainMenuHnd:mainMenuEng;
        }

        @Override
        public int getCount() {
            return listItems.length;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = null;
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.menu_grid, parent, false);
            }else{
                rowView = convertView;
            }
            TextView textView = (TextView) rowView.findViewById(R.id.grid_textView);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.grid_imageView);
            textView.setText(listItems[position]);
            imageView.setImageResource(image[position]);
            return rowView;
        }
    }

    public interface AdolscentMenuComm{
        public void getAdolscentMenuItemId(int index);
    }
}
