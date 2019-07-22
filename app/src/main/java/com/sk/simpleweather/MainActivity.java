package com.sk.simpleweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.simpleweather.event.CityMessageEvent;
import com.sk.simpleweather.json.WeatherBean;
import com.sk.simpleweather.serv.WeatherService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_CITY_ID = "101020100";

    MyCurveView mybarCharView;

    ArrayList<MyCurveView.WeatherData> dataArrayList = new ArrayList<>();

    @BindView(R.id.city_text)
    TextView city_text;
    @BindView(R.id.cur_text)
    TextView cur_text;
    @BindView(R.id.type_text)
    TextView type_text;
    @BindView(R.id.high_text)
    TextView high_text;
    @BindView(R.id.low_text)
    TextView low_text;
    @BindView(R.id.fx_fl_text)
    TextView fx_fl_text;
    @BindView(R.id.aqi_text)
    TextView aqi_text;
    @BindView(R.id.aqi_val_text)
    TextView aqi_val_text;
    @BindView(R.id.humidity_text)
    TextView humidity_text;
    @BindView(R.id.humidity_val_text)
    TextView humidity_val_text;
    @BindView(R.id.up_text)
    TextView up_text;
    @BindView(R.id.up_val_text)
    TextView up_val_text;
    @BindView(R.id.down_text)
    TextView down_text;
    @BindView(R.id.down_val_text)
    TextView down_val_text;
    @BindView(R.id.pm2_5_text)
    TextView pm2_5_text;
    @BindView(R.id.pm2_5_val_text)
    TextView pm2_5_val_text;
    @BindView(R.id.quality_text)
    TextView quality_text;
    @BindView(R.id.quality_val_text)
    TextView quality_val_text;
    @BindView(R.id.type_image)
    ImageView type_image;

    int totalHigh = 0;
    int totalLow = 0;

    int averageHigh = 0;
    int averageLow = 0;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        saveTypePictureId(preferences);

        ButterKnife.bind(this);
        mybarCharView = findViewById(R.id.mybarCharView);
        requestWeather(DEFAULT_CITY_ID);
    }

    @SuppressLint("CheckResult")
    private void requestWeather(String cityId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://t.weather.sojson.com/api/weather/city/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        final WeatherService weatherService = retrofit.create(WeatherService.class);

        weatherService.getCall(cityId)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<WeatherBean, Observable<WeatherBean>>) weatherBean -> {
                    dataArrayList.clear();
                    totalHigh = Integer.parseInt(weatherBean.getData().getForecast().get(0).getHigh().replace("高温 ", "").replace(".0℃", ""));
                    totalLow = Integer.parseInt(weatherBean.getData().getForecast().get(0).getLow().replace("低温 ", "").replace(".0℃", ""));
                    for (WeatherBean.DataBean.ForecastBean forecastBean : weatherBean.getData().getForecast()) {
                        String highStr = forecastBean.getHigh();
                        String lowStr = forecastBean.getLow();
                        highStr = highStr.replace("高温 ", "")
                                .replace(".0℃", "");
                        lowStr = lowStr.replace("低温 ", "")
                                .replace(".0℃", "");
                        int high = Integer.parseInt(highStr);
                        totalHigh = Math.max(totalHigh, high);

                        int low = Integer.parseInt(lowStr);
                        totalLow = Math.min(totalLow, low);

                        averageHigh = averageHigh + high;
                        averageLow = averageLow + low;
                        String type = forecastBean.getType();
                        int date = Integer.parseInt(forecastBean.getDate());

                        String picture_id = preferences.getString(forecastBean.getType(), "未知");
                        weatherService.getPicture(picture_id)
                                .subscribe(new Observer<ResponseBody>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ResponseBody responseBody) {
                                        Bitmap bitmap;
                                        InputStream inputStream = responseBody.byteStream();
                                        bitmap = BitmapFactory.decodeStream(inputStream);
                                        dataArrayList.add(new MyCurveView.WeatherData(low, high, date, type, bitmap));
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                    averageHigh = averageHigh / weatherBean.getData().getForecast().size();
                    averageLow = averageLow / weatherBean.getData().getForecast().size();
                    return Observable.fromArray(weatherBean);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        high_text.setText(weatherBean.getData().getForecast().get(0).getHigh().replace("高温 ", "").replace(".0℃", ""));
                        low_text.setText(weatherBean.getData().getForecast().get(0).getLow().replace("低温 ", "").replace(".0℃", ""));
                        fx_fl_text.setText(weatherBean.getData().getForecast().get(0).getFx() + " / " + weatherBean.getData().getForecast().get(0).getFl());
                        city_text.setText(weatherBean.getCityInfo().getCity());
                        cur_text.setText(weatherBean.getData().getWendu());
                        type_text.setText(weatherBean.getData().getForecast().get(0).getType());
                        humidity_val_text.setText(weatherBean.getData().getShidu());
                        aqi_val_text.setText(Integer.toString(weatherBean.getData().getForecast().get(0).getAqi()));
                        up_val_text.setText(weatherBean.getData().getForecast().get(0).getSunrise());
                        down_val_text.setText(weatherBean.getData().getForecast().get(0).getSunset());
                        pm2_5_val_text.setText(Integer.toString(weatherBean.getData().getPm25()));
                        quality_val_text.setText(weatherBean.getData().getQuality());
                        mybarCharView.setProgress(averageHigh, averageLow, totalLow, totalHigh, dataArrayList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CommitPrefEdits")
    private void saveTypePictureId(SharedPreferences preferences) {
        if (!preferences.getBoolean("isAdd", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("晴", "100.png");
            editor.putString("多云", "101.png");
            editor.putString("少云", "102.png");
            editor.putString("晴间多云", "103.png");
            editor.putString("阴", "104.png");
            editor.putString("有风", "200.png");
            editor.putString("平静", "201.png");
            editor.putString("微风", "202.png");
            editor.putString("和风", "203.png");
            editor.putString("清风", "204.png");
            editor.putString("强风/劲风", "205.png");
            editor.putString("疾风", "206.png");
            editor.putString("大风", "207.png");
            editor.putString("烈风", "208.png");
            editor.putString("风暴", "209.png");
            editor.putString("狂爆风", "210.png");
            editor.putString("飓风", "211.png");
            editor.putString("龙卷风", "212.png");
            editor.putString("热带风暴", "213.png");
            editor.putString("阵雨", "300.png");
            editor.putString("强阵雨", "301.png");
            editor.putString("雷阵雨", "302.png");
            editor.putString("强雷阵雨", "303.png");
            editor.putString("雷阵雨伴有冰雹", "304.png");
            editor.putString("小雨", "305.png");
            editor.putString("中雨", "306.png");
            editor.putString("大雨", "307.png");
            editor.putString("极端降雨", "308.png");
            editor.putString("毛毛雨/细雨", "309.png");
            editor.putString("暴雨", "310.png");
            editor.putString("大暴雨", "311.png");
            editor.putString("特大暴雨", "312.png");
            editor.putString("冻雨", "313.png");
            editor.putString("小到中雨", "314.png");
            editor.putString("中到大雨", "315.png");
            editor.putString("大到暴雨", "316.png");
            editor.putString("暴雨到大暴雨", "317.png");
            editor.putString("大暴雨到特大暴雨", "318.png");
            editor.putString("雨", "399.png");
            editor.putString("小雪", "400.png");
            editor.putString("中雪", "401.png");
            editor.putString("大雪", "402.png");
            editor.putString("暴雪", "403.png");
            editor.putString("雨夹雪", "404.png");
            editor.putString("雨雪天气", "405.png");
            editor.putString("阵雨夹雪", "406.png");
            editor.putString("阵雪", "407.png");
            editor.putString("小到中雪", "408.png");
            editor.putString("中到大雪", "409.png");
            editor.putString("大到暴雪", "410.png");
            editor.putString("雪", "499.png");
            editor.putString("薄雾", "500.png");
            editor.putString("雾", "501.png");
            editor.putString("扬沙", "502.png");
            editor.putString("浮尘", "503.png");
            editor.putString("沙尘暴", "504.png");
            editor.putString("强沙尘暴", "507.png");
            editor.putString("浓雾", "508.png");
            editor.putString("浓雾", "509.png");
            editor.putString("强浓雾", "510.png");
            editor.putString("中度霾", "511.png");
            editor.putString("重度霾", "512.png");
            editor.putString("严重霾", "513.png");
            editor.putString("大雾", "514.png");
            editor.putString("特强浓雾", "515.png");
            editor.putString("热", "900.png");
            editor.putString("冷", "901.png");
            editor.putString("未知", "999.png");
            editor.putBoolean("isAdd", true);
            editor.apply();
        }
    }

    @OnClick(R.id.city_text)
    public void chooseCity() {
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(CityMessageEvent messageEvent) {
        requestWeather(messageEvent.getCityId());
    }

}
