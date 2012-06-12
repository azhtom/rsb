package com.azhtom.rsb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.MediaController.MediaPlayerControl;

import com.al.rtmp.client.RtmpClient;
import com.al.rtmp.client.RtmpStream;
import com.al.rtmp.client.RtmpStreamFactory;
import com.al.rtmp.client.data.MetaData;
import com.al.rtmp.client.data.RTMPData;
import com.al.rtmp.message.MessageType;

public class RSBActivity extends Activity implements RtmpClient,
		OnTouchListener, MediaPlayerControl {
	private Button btnPlay;
	private Button btnConnect;
	private MediaPlayer mMediaPlayer;
	private File temp;
	private FileOutputStream ff;

	private RtmpStream stream = null;
	private String server = "rtmp://96.126.113.46:1935/vod/live/";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		

		btnConnect = ((Button) findViewById(R.id.btnConnect));
		btnPlay = ((Button) findViewById(R.id.btnPlay));
		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				connect();
			}
		});
		
		btnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("play", "Click to playing!");
				stream.setPlayName("azhtom");
				try {
					temp = File.createTempFile("temp", ".dat");
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				play();				
				
			}
		});
	}

	private void connect() {

		if (stream != null) {
			stream.close();
			stream = null;
		}

		stream = RtmpStreamFactory.getRtmpStream();
		stream.setClient(this);
		stream.connect(server);

		Log.i(this.getClass().getName(), " Connected !!! on " + server);

	}

	public void play() {
		if (stream != null) {
			Log.e("Client", " Resuming Thread Now...");

			stream.play();
			/*
			 * synchronized(playingThread) { playingThread.notify(); }
			 */
			return;
		}
	}

	@Override
	public byte[] getWriteData(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(String arg0, Object... arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDataReceived(RTMPData data) {
		// TODO Auto-generated method stub
		if (data.getMessageType() != MessageType.AUDIO)
			return;
		MetaData metaData = data.getMetaData();
		
		try {			
			String tempPath = temp.getAbsolutePath();
			ff = new FileOutputStream(tempPath);
			ff.write(data.getData());
			ff.flush();
			ff.close();
			
			testMediaBuffer();
		} catch (FileNotFoundException e) {
			Log.e(this.getClass().getName(), e.getMessage());
		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.getMessage());
		}
		//AudioCodec audioCodec = metaData.getAudioCodec();		
	}
	
	private void testMediaBuffer() {
		try {
			Runnable r = new Runnable() {
				public void run() {
					mMediaPlayer = new MediaPlayer();
					mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mMediaPlayer.setDataSource(ff.getFD());									
					mMediaPlayer.prepare();
				}

			};

			new Thread(r).start();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void startMediaPlayer() {
		
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(temp.getAbsolutePath());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fireDataPreloadComplete();

	}
	
	private void fireDataPreloadComplete() {
		Runnable updater = new Runnable() {
			public void run() {
				mMediaPlayer.start();		
			};
		};
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMetaDataReceived(Map arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResult(String arg0, Object... arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatus(String status) {
		Log.i(this.getClass().getName(), "------ STATUS -----");
		Log.i(this.getClass().getName(), status);
		Log.i(this.getClass().getName(), "------ STATUS -----");
	}

	@Override
	public void streamCreated() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void seekTo(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
}