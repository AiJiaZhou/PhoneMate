package com.phonemate.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * User: RanCQ
 * Date: 2015/8/20
 * QQ  :392663986
 * TEL : 15310880724
 */
public class RingerUtils {


    /**
     * 是否是静音
     */
    public static boolean isSilent(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch (mAudioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                return false;
            case AudioManager.RINGER_MODE_VIBRATE:
                return true;
            case AudioManager.RINGER_MODE_SILENT:
                return true;
            default:
                return true;
        }
    }

    /**
     * 是否震动
     */
    public static boolean isVibarte(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch (mAudioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                boolean isVibarte=false;
               switch(mAudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER)){
                   case AudioManager.VIBRATE_SETTING_OFF:
                       isVibarte= false;
                       break;
                   default:
                       isVibarte= true;
                       break;
                }
                return isVibarte;
            case AudioManager.RINGER_MODE_VIBRATE:
                return true;
            case AudioManager.RINGER_MODE_SILENT:
                return false;
            default:
                return false;
        }
    }

    ;

    public static void setRingerMode(boolean silent, boolean vibrate, Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (silent) {
            mAudioManager.setRingerMode(vibrate ? AudioManager.RINGER_MODE_VIBRATE :
                    AudioManager.RINGER_MODE_SILENT);
        } else {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION,
                    vibrate ? AudioManager.VIBRATE_SETTING_ON : AudioManager.VIBRATE_SETTING_OFF);
            mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER,
                    vibrate ? AudioManager.VIBRATE_SETTING_ON : AudioManager.VIBRATE_SETTING_OFF);
        }
    }

}
