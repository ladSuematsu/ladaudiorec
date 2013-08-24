package com.ladsoft.ladaudiorec;

//import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
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
	private AudioRecord audioRecorder;
	//private int soundID;
	boolean loaded = false;
	boolean recording = false;
	
	private Button buttonPlay;
	private Button buttonRecord;
	
	
	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//set the hardware buttons to control the music
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		mediaPlayer = new MediaPlayer();
		
		audioRecorder = new AudioRecord(
				MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
				AudioRecord.getMinBufferSize(44100 , AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT));
		
		
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonPlay.setOnClickListener(new Button.OnClickListener() {
					
			@Override
			public void onClick(View v) {	
				playback();
			}
				
		});
		
		buttonRecord = (Button) findViewById(R.id.buttonRecord);
		buttonRecord.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v)
			{
				record();
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
		audioRecorder.release();
		audioRecorder = null;
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
	
	//Record audio
	public void record()
	{
		
		try{
			if(audioRecorder.getState() == AudioRecord.RECORDSTATE_STOPPED){
				audioRecorder.startRecording();
				buttonRecord.setText(R.string.stopRecord);
			}
			else if(audioRecorder.getState() == AudioRecord.RECORDSTATE_RECORDING){
				audioRecorder.stop();
				buttonRecord.setText(R.string.record);
			}	
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
