package com.criticalthinking.riddlerabbitprek;

import java.io.IOException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;

public class AudioPlayPlugIn extends CordovaPlugin {

	public static final String NATIVE_ACTION_STRING_PLAY = "playAction";

	public static final String NATIVE_ACTION_STRING_STOP = "stopAction";
	// set up MediaPlayer
	private static MediaPlayer sMediaPlayer = new MediaPlayer();

	private String mAudioFileUrl;

	private ZipResourceFile mZipResFile;

	private AssetFileDescriptor mAssetFileDescriptor;
	
	

//	@Override
//	public PluginResult execute(String action, JSONArray data, String callbackId) {
//
//		Log.d("AudioPlayPlugIn",
//				"Hello, this is a native function called from PhoneGap/Cordova!");
//
//		if (NATIVE_ACTION_STRING_PLAY.equals(action)) {
//			try {
//				mAudioFileUrl = data.getString(0);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			if (null != mAudioFileUrl) {
//				try {
//					playAudio(mAudioFileUrl);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			return new PluginResult(PluginResult.Status.OK, "Playing, Success");
//		}
//
//		else if (NATIVE_ACTION_STRING_STOP.equals(action)) {
//
//			stopAudio();
//
//			// Toast.makeText(this.cordova.getActivity().getApplicationContext(),
//			// "Message received", Toast.LENGTH_LONG).show();
//
//			return new PluginResult(PluginResult.Status.OK, "Stop, Success");
//		}
//
//		return null;
//	}

	
	@Override
	public boolean execute(String action, JSONArray data,
			CallbackContext callbackContext) throws JSONException {
		
		Log.d("AudioPlayPlugIn",
				"Hello, this is a native function called from PhoneGap/Cordova!");

		if (NATIVE_ACTION_STRING_PLAY.equals(action)) {
			try {
				mAudioFileUrl = data.getString(0);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (null != mAudioFileUrl) {
				try {
					playAudio(mAudioFileUrl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return true;
		}

		else if (NATIVE_ACTION_STRING_STOP.equals(action)) {

			stopAudio();

			// Toast.makeText(this.cordova.getActivity().getApplicationContext(),
			// "Message received", Toast.LENGTH_LONG).show();

			return false;
		}

		return false;
	}
	
	
	
	private void playAudio(String audioFileUrl) throws IOException {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			if (null == mZipResFile) {
				mZipResFile = APKExpansionSupport.getAPKExpansionZipFile(
						this.cordova.getActivity().getApplicationContext(), 1,
						0);
			}

			mAssetFileDescriptor = mZipResFile
					.getAssetFileDescriptor(audioFileUrl);

			// AssetFileDescriptor afd = this.cordova.getActivity().getAssets()
			// .openFd(audioFileUrl);

			if (sMediaPlayer.isPlaying()) {
				sMediaPlayer.stop();
			}

			sMediaPlayer.reset();

			sMediaPlayer.setDataSource(
					mAssetFileDescriptor.getFileDescriptor(),
					mAssetFileDescriptor.getStartOffset(),
					mAssetFileDescriptor.getLength());

			sMediaPlayer.prepare();

			sMediaPlayer.start();
		} else {
			Toast.makeText(this.cordova.getActivity().getApplicationContext(),
					"No SD card", Toast.LENGTH_LONG).show();
		}

	}

	private void stopAudio() {

		if (sMediaPlayer.isPlaying()) {
			sMediaPlayer.stop();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (sMediaPlayer != null) {
			sMediaPlayer.release();

		}

	}
}
