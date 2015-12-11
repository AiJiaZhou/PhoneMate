package com.phonemate.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.MediaStore;

import java.util.List;

public class CameraUtils {

	/**
	 * 是否安全的启动相机
	 * 
	 * @param context
	 * @param secure
	 */
	public static void launchCamera(Context context, boolean secure) {
//		Light.getInstance(context.getApplicationContext()).release();
		if (secure) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
					&& isIntentAvailable(context,
							MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE)) {
				try {
					context.startActivity(getSecureCameraIntent());
				} catch (Exception e) {
					try {
						context.startActivity(getNormalCameraIntent());
					} catch (Exception e2) {
						MessageUtils.alertMessageCENTER("无法启动相机");
					}
				}
			} else {
				try {
					context.startActivity(getNormalCameraIntent());
				} catch (Exception e) {
					MessageUtils.alertMessageCENTER("无法启动相机");
				}
			}
		} else {
			try {
				context.startActivity(getNormalCameraIntent());
			} catch (Exception e) {
				MessageUtils.alertMessageCENTER("无法启动相机");
			}
		}
	}

	private static Intent getSecureCameraIntent() {
		Intent intent = new Intent(
				MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return intent;
	}

	private static Intent getNormalCameraIntent() {
		Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return intent;
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

}
