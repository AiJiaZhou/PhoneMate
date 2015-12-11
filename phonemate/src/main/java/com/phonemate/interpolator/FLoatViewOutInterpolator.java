package com.phonemate.interpolator;

import android.view.animation.Interpolator;

public class FLoatViewOutInterpolator implements Interpolator {

	@Override
	public float getInterpolation(float p) {

		// FLoatViewOutInterpolator
		float f = (1 - p);
		return 1 - (float) (f * f * f - f * Math.sin(f * Math.PI));

	}

}
