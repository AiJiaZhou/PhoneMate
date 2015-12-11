package com.phonemate.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.adapter.WeatherFutureAdapter;
import com.phonemate.base.BaseActivity;
import com.phonemate.model.WeatherModel;
import com.phonemate.utils.DateUtil;
import com.phonemate.utils.ResUtils;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/1 18:00
 * 修改人：colorful
 * 修改时间：15/12/1 18:00
 * 修改备注：
 */
public class WeatherDetailActivity extends BaseActivity implements View.OnClickListener {


    @ViewInject(id = R.id.mTvLeft, click = true)
    private TextView mTvLeft;
    @ViewInject(id = R.id.mTvRight, click = true)
    private TextView mTvRight;
    @ViewInject(id = R.id.mTvCentre, click = true)
    private TextView mTvCentre;

    @ViewInject(id = R.id.mLayoutTitleMenu)
    RelativeLayout mLayoutTitleMenu;

    @ViewInject(id = R.id.recyclerview)
    private RecyclerView recyclerview;

    @ViewInject(id = R.id.mTvWeatherTemp)
    private TextView mTvWeather;

    @ViewInject(id = R.id.mTvWeather)
    private TextView mTvWeatherName;

    @ViewInject(id = R.id.mTvTime)
    private TextView mTvTime;

    @ViewInject(id = R.id.mIvWeather)
    private ImageView mIvWeather;

    private WeatherFutureAdapter mAdapter;

    private List<WeatherModel> mWeatherList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
    }

    @Override
    public void bingViewFinish() {
        mTitleColor = R.color.color_light_green;
        mLayoutTitleMenu.setBackgroundColor(ResUtils.getColor(mActivity, R.color.color_light_green));
        mTvCentre.setText("天气");
        mTvLeft.setText("返回");
        mTvRight.setText("");

        mWeatherList=mApplication.fastDB.queryAll(WeatherModel.class);
        if(mWeatherList==null || mWeatherList.size()==0){

        }else{
            mTvWeatherName.setText(mWeatherList.get(0).getWeather());
            mTvTime.setText(DateUtil.longToString(System.currentTimeMillis(),"yyyy-MM-dd"));
            mTvWeather.setText(mWeatherList.get(0).getTemperature()+"°");
            mIvWeather.setImageDrawable(ResUtils.getDrawable(mActivity, mWeatherList.get(0).getWeatherIcon()));
        }
        mAdapter = new WeatherFutureAdapter(mWeatherList, mActivity);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());


    }


    @Override
    public void onClick(View v) {
        if (v == mTvLeft) {
            this.finish();
        }
    }

}

