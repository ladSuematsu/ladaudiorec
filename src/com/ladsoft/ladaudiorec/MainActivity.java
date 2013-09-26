package com.ladsoft.ladaudiorec;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity{

	private static final String LOG_TAG = "AudioRecordTest";
	private static String mediaFileName = null;
	
	private MediaPlayer mediaPlayer;
	private MediaRecorder mediaRecorder;
	
	
	boolean loaded = false;
	boolean recording = false;
	
	private Button buttonPlay;
	private Button recordButton;
	
	
	public MainActivity()
	{
		mediaFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mediaFileName += "/audioRecorded.3gpp";
	}
	
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
		
		recordButton = (Button) findViewById(R.id.buttonRecord);
		recordButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				onRecord(recording);
				recording = !recording;
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
//			Uri uri = Uri.parse("android.resource://com.ladsoft.ladaudiorec/" + R.raw.sound1);
			
			File file = new File(mediaFileName);
			FileInputStream fileDescriptor = new FileInputStream(file);
			
			mediaPlayer.reset();
			mediaPlayer.setDataSource(fileDescriptor.getFD());
			fileDescriptor.close();
			//mediaPlayer.setDataSource(this, uri);
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch(Exception e)
		{
            Log.v(getString(R.string.app_name), e.getMessage());
		}
	}
	
	

	/*Recording elements*/
	public class RecordButton extends Button
	{
		boolean recording = false;
		
		OnClickListener clickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				onRecord(recording);
				recording = !recording;
			}
		};
		
		
		public RecordButton(Context context)
		{
			super(context);
		}
		
	};

	private void onRecord(boolean recording)
	{
		if(!recording)
		{
			startRecording();
		}
		else
		{
			stopRecording();
		}
	}
	
	private void startRecording()
	{
		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaRecorder.setOutputFile(mediaFileName);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		try{
			mediaRecorder.prepare();
			mediaRecorder.start();
		}
		catch(IOException e){
			Log.e(LOG_TAG, "MediaRecorder.prepare() failed");
		}
	}
	
	private void stopRecording()
	{
		try{
			mediaRecorder.stop();
			mediaRecorder.reset();
		}
		catch(IllegalStateException e){
			Log.e(LOG_TAG, "MediaRecorder.stop() failed");
		}
		finally{
			mediaRecorder.release();
			mediaRecorder = null;
		}
	}
}
