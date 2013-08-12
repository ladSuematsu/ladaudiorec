package com.ladsoft.ladaudiorec;

import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity{

	//private SoundPool soundPool;
	private MediaPlayer mediaPlayer;
	//private int soundID;
	boolean loaded = false;
	
	private Button buttonPlay;
	
	
	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//set the hardware buttons to control the music
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		mediaPlayer = new MediaPlayer();
		
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonPlay.setOnClickListener(new Button.OnClickListener() {
					
			@Override
			public void onClick(View v) {	
				playback();
			}
				
		});
			
	}
	
	/*Release resources used by soundPool*/
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
		mediaPlayer.release();
		mediaPlayer = null;
	}
	
	//Sets the MediaPlayer instance, sets the data source and plays the sound
	public void playback()
	{
		try{
			Uri uri = Uri.parse("android.resource://com.ladsoft.ladaudiorec/" + R.raw.sound1);
			
			mediaPlayer.reset();
			mediaPlayer.setDataSource(this, uri);
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch(Exception e)
		{
            Log.v(getString(R.string.app_name), e.getMessage());
		}
	}
	
//	@Override
//	public void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		//Set the hardware buttons to control the music
//		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//		
//		//Load the sound
//		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//			@Override
//			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//				loaded = true;
//				
//			}
//		});
//		
//		
//		soundID = soundPool.load(this, R.raw.sound1 , 1);
//		
//		buttonPlay= (Button) findViewById(R.id.buttonPlay);
//		buttonPlay.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//					playback();
//				}
//		});
//		
//	}
	
//	/*Release resources used by soundPool*/
//	@Override
//	public void onDestroy()
//	{
//		super.onDestroy();
//		soundPool.release();
//	}
//	
//	
//	public void playback()
//	{
//		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//		float actualVolume = (float) audioManager
//				.getStreamVolume(AudioManager.STREAM_MUSIC);
//		float maxVolume = (float) audioManager
//				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//		float volume = actualVolume / maxVolume;
//		
//		//plays the sound if it was already loaded
//		if(loaded)
//		{
//			soundPool.play(soundID, volume, volume, 1, 0, 1f);
//		}
//	}
	
}





//public class MainActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    
//}
