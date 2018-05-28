package com.example.print;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This is the main Activity that displays the current session.
 */
public class BTPrinterDemo extends Activity {
	// Debugging
	private static final String TAG = "BTPrinter";
	private static final boolean D = true;

	// Message types sent from the BluetoothService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// Layout Views
	private TextView mTitle;
	private EditText mOutEditText;
	private Button mSendButton;
	private Button mPrintButton;
	private Button mClearButton;

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the services
	private BluetoothService mService = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		// Set up the window layout
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		setContentView(R.layout.main);


		// Set up the custom title
//		mTitle = (TextView) findViewById(R.id.title_left_text);
//		mTitle.setText(R.string.app_title);
//		mTitle = (TextView) findViewById(R.id.title_right_text);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
		}
		
		findViewById(R.id.button_connect).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent serverIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the session
		} else {
			if (mService == null)
				init();
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mService.getState() == BluetoothService.STATE_NONE) {
				// Start the Bluetooth services
				mService.start();
			}
		}
	}

	private void init() {
		Log.d(TAG, "setupChat()");

		// Initialize the compose field with a listener for the return key
		mOutEditText = (EditText) findViewById(R.id.edit_text_out);

		// Initialize the send button with a listener that for click events
		mSendButton = (Button) findViewById(R.id.button_send);
		mSendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Send a message using content of the edit text widget
//				TextView fontGrayView = (TextView) findViewById(R.id.fontGrayscaleValue);
//				String message = mOutEditText.getText().toString();
				String message = "??????????1234567890abcdefghijklmnopqestvvwsxyz\n";
				sendMessage(message);
			}
		});
		// Initialize the print button with a listener that for click events
		mPrintButton = (Button) findViewById(R.id.button_print);
		mPrintButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TextView fontGrayView = (TextView) findViewById(R.id.fontGrayscaleValue);
				String message = mOutEditText.getText().toString();
				sendMessage(message);
//				byte [] sends = new byte[2];
////				sends[0] = 27;
////				sends[1] = 64;
////				sends[2] = 10;
//				sends[0] = 0x1b;
//				sends[1] = 0x40;
////				sends[2] = 10;
//				mService.write(sends);

			}
		});

		// Initialize the print button with a listener that for click events
		mClearButton = (Button) findViewById(R.id.button_clear);
		mClearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mOutEditText.setText("");
			}
		});

		// Initialize the BluetoothService to perform bluetooth connections
		mService = new BluetoothService(this, mHandler);
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth services
		if (mService != null)
			mService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	// private void ensureDiscoverable() {
	// if(D) Log.d(TAG, "ensure discoverable");
	// if (mBluetoothAdapter.getScanMode() !=
	// BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
	// Intent discoverableIntent = new
	// Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	// discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
	// 300);
	// startActivity(discoverableIntent);
	// }
	// }

//	/**
//	 * Set font gray scale
//	 */
//	private void fontGrayscaleSet(int ucFontGrayscale) {
//		// Check that we're actually connected before trying anything
//		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
//			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
//					.show();
//			return;
//		}
//		if (ucFontGrayscale < 1)
//			ucFontGrayscale = 4;
//		if (ucFontGrayscale > 8)
//			ucFontGrayscale = 8;
//		byte[] send = new byte[3];// ESC m n
//		send[0] = 0x1B;
//		send[1] = 0x6D;
//		send[2] = (byte) ucFontGrayscale;
//		mService.write(send);
//	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 * 
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothService to write
			byte [] send ;
			try {
				send = message.getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				send = message.getBytes();
//				//去除/n;
//				int length = send.length;
//				for(int i=0; i<length;i++){
//					if(send[i]==10){
//						for(int j =i;j<length-1;j++){
//							send[j]=send[j+1];
//						}
//					}
//				}
			}

				mService.write(send);
				//
				// // Reset out string buffer to zero and clear the edit text field
				// mOutStringBuffer.setLength(0);
				// mOutEditText.setText(mOutStringBuffer);
		}
	}

	// // The action listener for the EditText widget, to listen for the return
	// key
	// private TextView.OnEditorActionListener mWriteListener =
	// new TextView.OnEditorActionListener() {
	// public boolean onEditorAction(TextView view, int actionId, KeyEvent
	// event) {
	// // If the action is a key-up event on the return key, send the message
	// if (actionId == EditorInfo.IME_NULL && event.getAction() ==
	// KeyEvent.ACTION_UP) {
	// String message = view.getText().toString();
	// sendMessage(message);
	// }
	// if(D) Log.i(TAG, "END onEditorAction");
	// return true;
	// }
	// };

	// The Handler that gets information back from the BluetoothService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothService.STATE_CONNECTED:
//					mTitle.setText(R.string.title_connected_to);
//					mTitle.append(mConnectedDeviceName);
					break;
				case BluetoothService.STATE_CONNECTING:
//					mTitle.setText(R.string.title_connecting);
					break;
				case BluetoothService.STATE_LISTEN:
				case BluetoothService.STATE_NONE:
//					mTitle.setText(R.string.title_not_connected);
					break;
				}
				break;
			case MESSAGE_WRITE:
				// byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				// String writeMessage = new String(writeBuf);
				break;
			case MESSAGE_READ:
				// byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				// String readMessage = new String(readBuf, 0, msg.arg1);
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				if (BluetoothAdapter.checkBluetoothAddress(address)) {
					BluetoothDevice device = mBluetoothAdapter
							.getRemoteDevice(address);
					// Attempt to connect to the device
					mService.connect(device);
				}
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a session
				init();
			} else {
				// User did not enable Bluetooth or an error occured
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scan:
			// Launch the DeviceListActivity to see devices and do scan
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return true;
		case R.id.disconnect:
			// disconnect
			mService.stop();
			return true;
		}
		return false;
	}

}