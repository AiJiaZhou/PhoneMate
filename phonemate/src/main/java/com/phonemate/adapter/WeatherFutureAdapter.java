package com.phonemate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.model.WeatherModel;
import com.phonemate.utils.ResUtils;
import com.phonemate.utils.WindowUtils;
import com.rxx.fast.adapter.BaseRecyclerView;
import com.rxx.fast.adapter.BaseRecyclerViewHolder;
import com.rxx.fast.view.ViewInject;

import java.util.List;

/**
 * 项目名称：PhoneMate
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/12/1 22:56
 * 修改人：colorful
 * 修改时间：15/12/1 22:56
 * 修改备注：
 */
public class WeatherFutureAdapter extends BaseRecyclerView<WeatherModel,WeatherFutureAdapter.WeatherFutureHolder>{

    public WeatherFutureAdapter(List<WeatherModel> mList, Context mContext) {
        super(mList, mContext, R.layout.item_weather_future);
    }

    @Override
    public WeatherFutureHolder createViewHolder(View v) {
        return new WeatherFutureHolder(v);
    }

    @Override
    public void baseOnBingViewHolder(WeatherFutureHolder holder, int position, WeatherModel weatherModel) {
        ViewGroup.LayoutParams layoutParams= holder.itemView.getLayoutParams();
        layoutParams.width= WindowUtils.getWIndowWidth(mContext)/4;
        layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.mIvWeather.setImageDrawable(ResUtils.getDrawable(mContext, weatherModel.getWeatherIcon()));
        holder.mTvTemp.setText(weatherModel.getTemperatureMax() + "℃/" + weatherModel.getTemperatureMin()+"℃");
        holder.mTvTWind.setText(weatherModel.getWindPowerDay()+"级/"+weatherModel.getWindPowerNight()+"级");//风力
        holder.mTvWeather.setText(weatherModel.getWeatherDay()+"/"+weatherModel.getWeatherNight());
        holder.mTvWeek.setText(weatherModel.getWeek());
    }

    class WeatherFutureHolder extends BaseRecyclerViewHolder{

        @ViewInject(id = R.id.mLayout)
        public LinearLayout mLayout;

        @ViewInject(id = R.id.mIvWeather)
        public ImageView mIvWeather;

        @ViewInject(id = R.id.mTvWeek)
        public TextView mTvWeek;

        @ViewInject(id = R.id.mTvWeather)
        public TextView mTvWeather;

        @ViewInject(id = R.id.mTvTemp)
        public TextView mTvTemp;

        @ViewInject(id = R.id.mTvTWind)
        public TextView mTvTWind;

        public WeatherFutureHolder(View itemView) {
            super(itemView);
        }
    }
}
