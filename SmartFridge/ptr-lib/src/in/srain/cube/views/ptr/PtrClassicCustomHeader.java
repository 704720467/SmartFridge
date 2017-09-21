package in.srain.cube.views.ptr;

import in.srain.cube.views.ptr.indicator.PtrIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PtrClassicCustomHeader extends FrameLayout implements PtrUIHandler {

	private final static String KEY_SharedPreferences = "cube_ptr_classic_last_update";
	private static SimpleDateFormat sDataFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private int mRotateAniTime = 150;
	private RotateAnimation mFlipAnimation;
	private RotateAnimation mReverseFlipAnimation;
	private AlphaAnimation alphFlipAnimation;
	private AlphaAnimation mAlphFlipAnimation;
	private TextView mTitleTextView;
	private View mRotateView;
	private ImageView mAnimView;
	private long mLastUpdateTime = -1;
	private TextView mLastUpdateTextView;
	private String mLastUpdateTimeKey;
	private boolean mShouldShowLastUpdate;

	private LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();

	public PtrClassicCustomHeader(Context context) {
		super(context);
		initViews(null);
	}

	public PtrClassicCustomHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(attrs);
	}

	public PtrClassicCustomHeader(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initViews(attrs);
	}

	protected void initViews(AttributeSet attrs) {
		TypedArray arr = getContext().obtainStyledAttributes(attrs,
				R.styleable.PtrClassicHeader, 0, 0);
		if (arr != null) {
			mRotateAniTime = arr.getInt(
					R.styleable.PtrClassicHeader_ptr_rotate_ani_time,
					mRotateAniTime);
		}
		buildAnimation();
		View header = LayoutInflater.from(getContext()).inflate(
				R.layout.cube_ptr_classic_custom_header, this);

		mRotateView = header.findViewById(R.id.ptr_classic_header_rotate_view);

		mTitleTextView = (TextView) header
				.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
		mLastUpdateTextView = (TextView) header
				.findViewById(R.id.ptr_classic_header_rotate_view_header_last_update);
		mAnimView = (ImageView) header
				.findViewById(R.id.ptr_classic_header_rotate_view_imageview);
		resetView();
	}

	public void setRotateAniTime(int time) {
		if (time == mRotateAniTime || time == 0) {
			return;
		}
		mRotateAniTime = time;
		buildAnimation();
	}

	/**
	 * Specify the last update time by this key string
	 *
	 * @param key
	 */
	public void setLastUpdateTimeKey(String key) {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		mLastUpdateTimeKey = key;
	}

	/**
	 * Using an object to specify the last update time.
	 *
	 * @param object
	 */
	public void setLastUpdateTimeRelateObject(Object object) {
		setLastUpdateTimeKey(object.getClass().getName());
	}

	private void buildAnimation() {
		mFlipAnimation = new RotateAnimation(0, -120,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(mRotateAniTime);
		mFlipAnimation.setFillAfter(true);

		mReverseFlipAnimation = new RotateAnimation(-120, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(mRotateAniTime);
		mReverseFlipAnimation.setFillAfter(true);

		alphFlipAnimation = new AlphaAnimation(1, 0);// ����Ч��zp
		alphFlipAnimation.setInterpolator(new LinearInterpolator());
		alphFlipAnimation.setDuration(mRotateAniTime);
		alphFlipAnimation.setFillAfter(true);

		mAlphFlipAnimation = new AlphaAnimation(0, 1);// ����Ч��zp
		mAlphFlipAnimation.setInterpolator(new LinearInterpolator());
		mAlphFlipAnimation.setDuration(mRotateAniTime);
		mAlphFlipAnimation.setFillAfter(true);
	}

	private void resetView() {
		hideRotateView();
		mAnimView.setVisibility(INVISIBLE);
		AnimationDrawable drawable = (AnimationDrawable) mAnimView
				.getBackground();
		if (drawable != null) {
			drawable.stop();
		}
	}

	private void hideRotateView() {
		mRotateView.clearAnimation();
		mRotateView.setVisibility(INVISIBLE);
	}

	@Override
	public void onUIReset(PtrFrameLayout frame) {
		resetView();
		mShouldShowLastUpdate = true;
		tryUpdateLastUpdateTime();
	}

	@Override
	public void onUIRefreshPrepare(PtrFrameLayout frame) {

		mShouldShowLastUpdate = true;
		tryUpdateLastUpdateTime();
		mLastUpdateTimeUpdater.start();

		mAnimView.setVisibility(INVISIBLE);
		AnimationDrawable drawable = (AnimationDrawable) mAnimView
				.getBackground();
		if (drawable != null) {
			drawable.stop();
		}

		// mRotateView.setVisibility(VISIBLE);
		mTitleTextView.setVisibility(VISIBLE);
		if (frame.isPullToRefresh()) {
			mTitleTextView.setText(getResources().getString(
					R.string.cube_ptr_pull_down_to_refresh));
		} else {
			mTitleTextView.setText(getResources().getString(
					R.string.cube_ptr_pull_down));
		}
	}

	@Override
	public void onUIRefreshBegin(PtrFrameLayout frame) {
		mShouldShowLastUpdate = false;
		hideRotateView();
		mAnimView.setVisibility(VISIBLE);
		mAnimView.setBackgroundResource(R.drawable.refresh_anim);
		AnimationDrawable drawable = (AnimationDrawable) mAnimView
				.getBackground();
		drawable.start();

		mTitleTextView.setVisibility(VISIBLE);
		mTitleTextView.setText(R.string.cube_ptr_refreshing);

		tryUpdateLastUpdateTime();
		mLastUpdateTimeUpdater.stop();
	}

	@Override
	public void onUIRefreshComplete(PtrFrameLayout frame,boolean isHeader) {

		 if(!isHeader){
	            return;
	        }
		hideRotateView();
		mAnimView.setVisibility(View.INVISIBLE);
		AnimationDrawable drawable = (AnimationDrawable) mAnimView
				.getBackground();
		if (drawable != null) {
			drawable.stop();
		}

		mTitleTextView.setVisibility(VISIBLE);
		mTitleTextView.setText(getResources().getString(
				R.string.cube_ptr_refresh_complete));

		// update last update time
		SharedPreferences sharedPreferences = getContext()
				.getSharedPreferences(KEY_SharedPreferences, 0);
		if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
			mLastUpdateTime = new Date().getTime();
			sharedPreferences.edit()
					.putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
		}
	}

	private void tryUpdateLastUpdateTime() {
		if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
			mLastUpdateTextView.setVisibility(GONE);
		} else {
			String time = getLastUpdateTime();
			if (TextUtils.isEmpty(time)) {
				mLastUpdateTextView.setVisibility(GONE);
			} else {
				mLastUpdateTextView.setVisibility(VISIBLE);
				mLastUpdateTextView.setText(time);
			}
		}
	}

	private String getLastUpdateTime() {

		if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
			mLastUpdateTime = getContext().getSharedPreferences(
					KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1);
		}
		if (mLastUpdateTime == -1) {
			return null;
		}
		long diffTime = new Date().getTime() - mLastUpdateTime;
		int seconds = (int) (diffTime / 1000);
		if (diffTime < 0) {
			return null;
		}
		if (seconds <= 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getContext().getString(R.string.cube_ptr_last_update));

		if (seconds < 60) {
			sb.append(seconds
					+ getContext().getString(R.string.cube_ptr_seconds_ago));
		} else {
			int minutes = (seconds / 60);
			if (minutes > 60) {
				int hours = minutes / 60;
				if (hours > 24) {
					Date date = new Date(mLastUpdateTime);
					sb.append(sDataFormat.format(date));
				} else {
					sb.append(hours
							+ getContext().getString(
									R.string.cube_ptr_hours_ago));
				}

			} else {
				sb.append(minutes
						+ getContext().getString(R.string.cube_ptr_minutes_ago));
			}
		}
		return sb.toString();
	}

	@Override
	public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch,
			byte status, PtrIndicator ptrIndicator) {

		final int mOffsetToRefresh = frame.getOffsetToRefresh();
		final int currentPos = ptrIndicator.getCurrentPosY();
		final int lastPos = ptrIndicator.getLastPosY();

		if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
			if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
				crossRotateLineFromBottomUnderTouch(frame);
				if (mRotateView != null) {
					mRotateView.setVisibility(View.VISIBLE);
					mRotateView.clearAnimation();
					// ���������óɵ���
					mRotateView.startAnimation(alphFlipAnimation);
				}
			}
		} else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
			if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
				crossRotateLineFromTopUnderTouch(frame);
				if (mRotateView != null) {
					mRotateView.clearAnimation();
					// ���������óɽ���
					mRotateView.startAnimation(mAlphFlipAnimation);
				}
			}
		}
	}

	private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
		if (!frame.isPullToRefresh()) {
			mTitleTextView.setVisibility(VISIBLE);
			mTitleTextView.setText(R.string.cube_ptr_release_to_refresh);
		}
	}

	private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
		mTitleTextView.setVisibility(VISIBLE);
		if (frame.isPullToRefresh()) {
			mTitleTextView.setText(getResources().getString(
					R.string.cube_ptr_pull_down_to_refresh));
		} else {
			mTitleTextView.setText(getResources().getString(
					R.string.cube_ptr_pull_down));
		}
	}

	private class LastUpdateTimeUpdater implements Runnable {

		private boolean mRunning = false;

		private void start() {
			if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
				return;
			}
			mRunning = true;
			run();
		}

		private void stop() {
			mRunning = false;
			removeCallbacks(this);
		}

		@Override
		public void run() {
			tryUpdateLastUpdateTime();
			if (mRunning) {
				postDelayed(this, 1000);
			}
		}
	}
}
