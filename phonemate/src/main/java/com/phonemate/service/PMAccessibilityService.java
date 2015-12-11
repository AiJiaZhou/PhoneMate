package com.phonemate.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.rxx.fast.utils.LUtils;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/11/20 14:37
 * 修改人：colorful
 * 修改时间：15/11/20 14:37
 * 修改备注：
 */
public class PMAccessibilityService extends AccessibilityService implements TextToSpeech.OnInitListener {

    /**
     * Tag for logging.
     */
    private static final String TAG = "PMAccessibilityService";

    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(getPackageName() + ".ACCESSIBILITY_RECEIVED_ACTION");
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ((getPackageName() + ".ACCESSIBILITY_RECEIVED_ACTION").equals(intent.getAction())) {
                if ("back".equals(intent.getStringExtra("action"))) {
                    LUtils.i(TAG,"GLOBAL_ACTION_BACK");
                    boolean isDone = performGlobalAction(GLOBAL_ACTION_BACK);
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onServiceConnected() {
        LUtils.i(TAG,"onServiceConnected");
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        accessibilityServiceInfo.flags = AccessibilityServiceInfo.CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT;
        accessibilityServiceInfo.notificationTimeout = 0;
        setServiceInfo(accessibilityServiceInfo);

        registerMessageReceiver();
    }

    /**
     * Processes an AccessibilityEvent, by traversing the View's tree and
     * putting together a message to speak to the user.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        LUtils.i(TAG,"onAccessibilityEvent");
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onInterrupt() {
        LUtils.i(TAG,"onInterrupt");
		/* do nothing */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onInit(int status) {
        LUtils.i(TAG,"onInit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LUtils.i(TAG, "onDestroy");
        if (mMessageReceiver != null) {
            unregisterReceiver(mMessageReceiver);
        }
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                Log.d("Hello", "KeyUp");
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                Log.d("Hello", "KeyDown");
            }
            return true;
        } else {
            return super.onKeyEvent(event);
        }
    }
}
