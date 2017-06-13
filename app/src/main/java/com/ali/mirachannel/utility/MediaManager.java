package com.ali.mirachannel.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MediaManager {
	private static Context context;
	static ProgressDialog dialog;// = new ProgressDialog(context);
	static String soundURL;
	public static void playUrl(String _soundURL,Context _context) {
		context = _context;
		soundURL = _soundURL;
		System.out.println("Sound URL---- PlayUrl=="+soundURL);
		new AsyncTask<String, Integer, String>(){
			
			protected void onPreExecute() {
				dialog = new ProgressDialog(context);
				dialog.setTitle("Loading...");
				String Path = Environment.getExternalStorageDirectory().getPath()+"/WOMENMOBILECHANNEL/"+soundURL;
				File file = new File(Path);
				if(!file.exists()){
					dialog.show();
				}
				
				System.out.println(Path);
			};
			
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String Path = Environment.getExternalStorageDirectory().getPath()+"/WOMENMOBILECHANNEL/"+soundURL;
				File file = new File(Path);
				if(file.exists()){
					soundURL = Path;
//					dialog.dismiss();
				}else{
					
					soundURL = MiraConstants.LANGUAGE.equals(MiraConstants.HINDI)?MiraConstants.URLMIRAHN+soundURL:MiraConstants.URLMIRAEN+soundURL;						
					downloadFile(soundURL);
					System.out.println("Sound URL---- doInBackground=="+soundURL);

				}
				try {			
					
					if(MiraConstants.MEDIA_PLAYER.isPlaying()){
						MiraConstants.MEDIA_PLAYER.stop();
						return null;
					}
					
					MiraConstants.MEDIA_PLAYER.reset();
					MiraConstants.MEDIA_PLAYER.setDataSource(soundURL);
					MiraConstants.MEDIA_PLAYER.prepare();
					MiraConstants.MEDIA_PLAYER.start();	
					MiraConstants.MEDIA_PLAYER.setOnCompletionListener(new OnCompletionListener() {			
						@Override
						public void onCompletion(MediaPlayer mp) {
							MiraConstants.MEDIA_PLAYER.reset();
						}
					});
					
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(String result) {
				dialog.dismiss();
			};
		}.execute(soundURL);
	}
	
	
	
	public static void downloadFile(String url) {
        System.out.println("Sound URL---- downloadFile=="+url);
		new AsyncTask<String, Integer, String>(){
//			ProgressDialog dialog = new ProgressDialog(context);
			protected void onPreExecute() {
//				dialog.setTitle("Loadding...");
				dialog.show();
			};
			
			@Override
			protected String doInBackground(String... params) {
				int count;
				try {
					URL url1 = new URL(params[0]);
					URLConnection conexion = url1.openConnection();
					conexion.connect();
					int lenghtOfFile = conexion.getContentLength();
                    System.out.println("Length Of File ="+lenghtOfFile);
					InputStream input = new BufferedInputStream(url1.openStream());

                    System.out.println("Response Data ="+input.toString());

					 File file = new File(Environment.getExternalStorageDirectory(), "/WOMENMOBILECHANNEL/");

					if (!file.exists()) {
						file.mkdirs();
					}
						OutputStream output = new FileOutputStream("/"+Environment.getExternalStorageDirectory()+"/WOMENMOBILECHANNEL/"+getLastBitFromUrl(url1.toString()));
						byte data[] = new byte[1024];
						long total = 0;
						while ((count = input.read(data)) != -1) {
							total += count;
							publishProgress((int)((total/(float)lenghtOfFile)*100));
							output.write(data, 0, count);
							if (isCancelled()) break;						
						}
						output.flush();
						output.close();
						input.close();
						
				} catch (Exception e) {
                    e.printStackTrace();
				}				
				return null;
			}
			
			protected void onPostExecute(String result) {
				dialog.dismiss();
			};
			
		}.execute(url);
	}
	
	public static String getLastBitFromUrl(final String url){		
		String string = url.replaceFirst(".*/([^/?]+).*", "$1");
		return string;
	}
	
	
}
