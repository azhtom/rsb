package com.azhtom.rsb;

import java.util.Map;

import com.al.rtmp.client.RtmpClient;
import com.al.rtmp.client.RtmpStream;
import com.al.rtmp.client.RtmpStreamFactory;
import com.al.rtmp.client.data.RTMPData;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.MediaController.MediaPlayerControl;

public class CopyOfRSBActivity extends Activity implements RtmpClient, OnTouchListener, MediaPlayerControl {
	private Button btnConnect;
	private RtmpStream stream = null;
	private String server = "rtmp://216.69.156.237/live/bolsa/";
	private Button btnPlay;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnConnect = ((Button)findViewById(R.id.btnConnect));
        btnPlay = ((Button)findViewById(R.id.btnPlay));
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
				//stream.setPlayName("live.flv");
				play();
			}
		});
    }

    private void connect() {
    	
    	if(stream != null) {
			stream.close();
			stream = null;
		}
    	
    	stream = RtmpStreamFactory.getRtmpStream();
		stream.setClient(this);
		stream.connect(server);	
		
		Log.i(this.getClass().getName(), " Connected !!! on " + server);
		
		
    }
    
    public void play() {
		if(stream != null) {
			Log.e("Client", " Resuming Thread Now...");
			
			stream.play("RSBlive", "/mnt/sdcard/RSBlive.flv");
			/*synchronized(playingThread)
			{
				playingThread.notify();
			}*/
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
		Log.i(this.getClass().getName(), data.toString());
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