package com.phonemate.model;

import com.phonemate.R;
import com.rxx.fast.db.table.Property;
/**
 * User: RanCQ
 * Date: 2015/9/24
 */
public class WeatherModel {
    @Property(isSave = false)
    public String []WEEK={"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
    @Property(isSave = false)
    public final String [] WEATHER_TYPE={
            "晴","多云","阴","雾" ,
            "阵雨","雷阵雨", "雷阵雨并伴有冰雹", "冻雨",
            "小雨","中雨", "大雨","暴雨","大暴雨",  "特大暴雨",
            "小雨-中雨", "中雨-大雨", "大雨-暴雨" , "暴雨-大暴雨", "大暴雨-特大暴雨",
            "雨夹雪","阵雪","小雪", "中雪", "大雪", "暴雪" ,
            "小雪-中雪", "中雪-大雪", "大雪-暴雪","弱高吹雪",
            "沙尘暴","浮尘","扬沙", "强沙尘暴", "飑", "龙卷风", "轻霾", "霾"
    };
    @Property(isSave = false)
    public final int [] WEATHER_ICON={
            //基本4个
            R.mipmap.weather_sunny, R.mipmap.weather_cloudy, R.mipmap.weather_overcast, R.mipmap.weather_foggy,
            //雨 15个
            R.mipmap.weather_shower, R.mipmap.weather_shower, R.mipmap.weather_shower, R.mipmap.weather_rain, R.mipmap.weather_rain,
            R.mipmap.weather_rain, R.mipmap.weather_rain, R.mipmap.weather_rain, R.mipmap.weather_rain, R.mipmap.weather_rain,
            R.mipmap.weather_rain, R.mipmap.weather_rain, R.mipmap.weather_rain, R.mipmap.weather_rain, R.mipmap.weather_rain,

            //雪10个
            R.mipmap.weather_snowy, R.mipmap.weather_snowy, R.mipmap.weather_snowy, R.mipmap.weather_snowy, R.mipmap.weather_snowy,
            R.mipmap.weather_snowy, R.mipmap.weather_snowy, R.mipmap.weather_snowy, R.mipmap.weather_snowy, R.mipmap.weather_snowy,

            //其他 8 个
            R.mipmap.weather_sandy, R.mipmap.weather_sandy, R.mipmap.weather_sandy, R.mipmap.weather_sandy,
            R.mipmap.weather_sandy, R.mipmap.weather_sandy, R.mipmap.weather_haze, R.mipmap.weather_haze
    };
    @Property(isSave = false)
    public final int WEATHE_RDEFAULT_ICON= R.mipmap.icon_error_small;

    private String id;

    private String city;//城市
    private String temperatureMin;
    private String temperatureMax;
    private String temperature;
    private String weather;//天气情况
    private String weatherDay;//天气情况
    private String weatherNight;//天气情况
    private String windDir;//风向
    private String windPower;//风力

    private String windDirDay;//风向
    private String windPowerDay;//风力

    private String windDirNight;//风向
    private String windPowerNight;//风力
    private String humidity;//空气湿度
    private String reportTime;//数据发布时间
    private long updateTime;//更新时间
    private String week;//更新时间

    public String getWindDirDay() {
        return windDirDay;
    }

    public void setWindDirDay(String windDirDay) {
        this.windDirDay = windDirDay;
    }

    public String getWindPowerDay() {
        return windPowerDay;
    }

    public void setWindPowerDay(String windPowerDay) {
        this.windPowerDay = windPowerDay;
    }

    public String getWindDirNight() {
        return windDirNight;
    }

    public void setWindDirNight(String windDirNight) {
        this.windDirNight = windDirNight;
    }

    public String getWindPowerNight() {
        return windPowerNight;
    }

    public void setWindPowerNight(String windPowerNight) {
        this.windPowerNight = windPowerNight;
    }

    public String getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(String weatherDay) {
        this.weatherDay = weatherDay;
    }

    public String getWeatherNight() {
        return weatherNight;
    }

    public void setWeatherNight(String weatherNight) {
        this.weatherNight = weatherNight;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        try {
            this.week = WEEK[Integer.parseInt(week)-1];
        }catch(Exception e){
            this.week=week;
        }
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "temperature='" + temperature + '\'' +
                ", city='" + city + '\'' +
                ", weather='" + weather + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windPower='" + windPower + '\'' +
                ", humidity='" + humidity + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
    public WeatherModel() {
    }


    public int getWeatherIcon(){
        int  iconPosition=-1;
        for(int i=0;i<WEATHER_ICON.length;i++){
            if(WEATHER_TYPE[i].equals(weather)){
                iconPosition=i;
                break;
            }
        }

        if(iconPosition<0){
            for(int i=0;i<WEATHER_ICON.length;i++){
                if(WEATHER_TYPE[i].equals(weatherDay)){
                    iconPosition=i;
                    break;
                }
            }
        }


        if(iconPosition<0){
            return WEATHE_RDEFAULT_ICON;
        }
        if(iconPosition>(WEATHER_ICON.length-1)){
            return WEATHE_RDEFAULT_ICON;
        }
        return WEATHER_ICON[iconPosition];
    }

}
