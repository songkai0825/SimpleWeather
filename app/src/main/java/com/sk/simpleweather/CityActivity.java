package com.sk.simpleweather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;

import com.sk.simpleweather.databinding.ActivityCityBinding;
import com.sk.simpleweather.event.CityMessageEvent;
import com.sk.simpleweather.json.CityBean;
import com.sk.simpleweather.serv.CityService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityActivity extends AppCompatActivity implements CitysAdapter.OnItemClick {

    private static final String KEY = "584179a027404868afac4eb153ce970";

    private ArrayList<String> cityList = new ArrayList<>();

    private CitysAdapter adapter;

    private CityBean mCityBean;

    private CityMessageEvent messageEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCityBinding activityCityBinding = DataBindingUtil.setContentView(this, R.layout.activity_city);
        activityCityBinding.setCityViewHolder(new CityViewHolder());

        messageEvent = new CityMessageEvent();

        adapter = new CitysAdapter(cityList, this);
        adapter.setOnItemClick(this);
        activityCityBinding.cityList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        activityCityBinding.cityList.setAdapter(adapter);
    }

    @Override
    public void onClick(String city, int position) {
        if (mCityBean != null) {
            messageEvent.setName(city);
            messageEvent.setCityId(mCityBean.getHeWeather6().get(0).getBasic().get(position).getCid().replace("CN", ""));
            EventBus.getDefault().postSticky(messageEvent);
        }
        finish();
    }

    public class CityViewHolder {

        public void afterTextChanged(Editable s) {
            cityList.clear();
            if (s.toString().equals("")) {
                return;
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://search.heweather.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            CityService cityService = retrofit.create(CityService.class);
            cityService.getCall(s.toString(), KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CityBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(CityBean cityBean) {
                            mCityBean = cityBean;
                            for (CityBean.HeWeather6Bean.BasicBean basicBean : cityBean.getHeWeather6().get(0).getBasic()) {
                                cityList.add(basicBean.getLocation());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            adapter.notifyDataSetChanged();
                        }
                    });

        }
    }

}
