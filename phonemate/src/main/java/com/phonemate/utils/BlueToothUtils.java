package com.phonemate.utils;

import android.bluetooth.BluetoothAdapter;

/**
 * User: RanCQ
 * Date: 2015/8/26
 * QQ  :392663986
 * TEL : 15310880724
 */
public class BlueToothUtils {


    public static boolean  isOpne(){
        boolean  isOpen=false;
        BluetoothAdapter blueAdapter=BluetoothAdapter.getDefaultAdapter();
        switch (blueAdapter.getState()){
            case BluetoothAdapter.STATE_OFF:
                isOpen=false;
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                isOpen=true;
                break;
            case BluetoothAdapter.STATE_ON:
                isOpen=true;
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                isOpen=false;
                break;
        }
        return isOpen;
    }

    public static boolean operation(boolean isOpen){
        BluetoothAdapter blueAdapter=BluetoothAdapter.getDefaultAdapter();
        if(isOpen){
           return  blueAdapter.enable();
        }else{
            return blueAdapter.disable();
        }

    }
}
