package com.sk.simpleweather.json;

import java.util.List;

public class WeatherBean {
    /**
     * time : 2019-06-29 16:22:49
     * cityInfo : {"city":"天津市","cityId":"101030100","parent":"天津","updateTime":"15:38"}
     * date : 20190629
     * message : Success !
     * status : 200
     * data : {"shidu":"20%","pm25":41,"pm10":37,"quality":"良","wendu":"34","ganmao":"极少数敏感人群应减少户外活动","yesterday":{"date":"28","sunrise":"04:47","high":"高温 31.0℃","low":"低温 24.0℃","sunset":"19:41","aqi":51,"ymd":"2019-06-28","week":"星期五","fx":"西南风","fl":"<3级","type":"阵雨","notice":"阵雨来袭，出门记得带伞"},"forecast":[{"date":"29","sunrise":"04:47","high":"高温 34.0℃","low":"低温 22.0℃","sunset":"19:41","aqi":113,"ymd":"2019-06-29","week":"星期六","fx":"西北风","fl":"4-5级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"30","sunrise":"04:48","high":"高温 31.0℃","low":"低温 22.0℃","sunset":"19:41","aqi":70,"ymd":"2019-06-30","week":"星期日","fx":"北风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"01","sunrise":"04:48","high":"高温 35.0℃","low":"低温 26.0℃","sunset":"19:41","aqi":70,"ymd":"2019-07-01","week":"星期一","fx":"南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"02","sunrise":"04:49","high":"高温 36.0℃","low":"低温 24.0℃","sunset":"19:41","aqi":92,"ymd":"2019-07-02","week":"星期二","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","sunrise":"04:49","high":"高温 33.0℃","low":"低温 23.0℃","sunset":"19:41","aqi":89,"ymd":"2019-07-03","week":"星期三","fx":"东北风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","sunrise":"04:49","high":"高温 35.0℃","low":"低温 25.0℃","sunset":"19:41","aqi":103,"ymd":"2019-07-04","week":"星期四","fx":"南风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"05","sunrise":"04:50","high":"高温 34.0℃","low":"低温 24.0℃","sunset":"19:41","ymd":"2019-07-05","week":"星期五","fx":"南风","fl":"4-5级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"06","sunrise":"04:50","high":"高温 38.0℃","low":"低温 25.0℃","sunset":"19:40","ymd":"2019-07-06","week":"星期六","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"07","sunrise":"04:51","high":"高温 39.0℃","low":"低温 26.0℃","sunset":"19:40","ymd":"2019-07-07","week":"星期日","fx":"东南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"08","sunrise":"04:52","high":"高温 38.0℃","low":"低温 25.0℃","sunset":"19:40","ymd":"2019-07-08","week":"星期一","fx":"东南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"09","sunrise":"04:52","high":"高温 38.0℃","low":"低温 25.0℃","sunset":"19:39","ymd":"2019-07-09","week":"星期二","fx":"东风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"10","sunrise":"04:53","high":"高温 39.0℃","low":"低温 25.0℃","sunset":"19:39","ymd":"2019-07-10","week":"星期三","fx":"东南风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"11","sunrise":"04:53","high":"高温 39.0℃","low":"低温 27.0℃","sunset":"19:39","ymd":"2019-07-11","week":"星期四","fx":"南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"12","sunrise":"04:54","high":"高温 39.0℃","low":"低温 26.0℃","sunset":"19:38","ymd":"2019-07-12","week":"星期五","fx":"南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"13","sunrise":"04:55","high":"高温 39.0℃","low":"低温 25.0℃","sunset":"19:38","ymd":"2019-07-13","week":"星期六","fx":"西南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"}]}
     */

    private String time;
    private CityInfoBean cityInfo;
    private String date;
    private String message;
    private int status;
    private DataBean data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class CityInfoBean {
        /**
         * city : 天津市
         * cityId : 101030100
         * parent : 天津
         * updateTime : 15:38
         */

        private String city;
        private String cityId;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class DataBean {
        /**
         * shidu : 20%
         * pm25 : 41
         * pm10 : 37
         * quality : 良
         * wendu : 34
         * ganmao : 极少数敏感人群应减少户外活动
         * yesterday : {"date":"28","sunrise":"04:47","high":"高温 31.0℃","low":"低温 24.0℃","sunset":"19:41","aqi":51,"ymd":"2019-06-28","week":"星期五","fx":"西南风","fl":"<3级","type":"阵雨","notice":"阵雨来袭，出门记得带伞"}
         * forecast : [{"date":"29","sunrise":"04:47","high":"高温 34.0℃","low":"低温 22.0℃","sunset":"19:41","aqi":113,"ymd":"2019-06-29","week":"星期六","fx":"西北风","fl":"4-5级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"30","sunrise":"04:48","high":"高温 31.0℃","low":"低温 22.0℃","sunset":"19:41","aqi":70,"ymd":"2019-06-30","week":"星期日","fx":"北风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"01","sunrise":"04:48","high":"高温 35.0℃","low":"低温 26.0℃","sunset":"19:41","aqi":70,"ymd":"2019-07-01","week":"星期一","fx":"南风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"02","sunrise":"04:49","high":"高温 36.0℃","low":"低温 24.0℃","sunset":"19:41","aqi":92,"ymd":"2019-07-02","week":"星期二","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","sunrise":"04:49","high":"高温 33.0℃","low":"低温 23.0℃","sunset":"19:41","aqi":89,"ymd":"2019-07-03","week":"星期三","fx":"东北风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"04","sunrise":"04:49","high":"高温 35.0℃","low":"低温 25.0℃","sunset":"19:41","aqi":103,"ymd":"2019-07-04","week":"星期四","fx":"南风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"05","sunrise":"04:50","high":"高温 34.0℃","low":"低温 24.0℃","sunset":"19:41","ymd":"2019-07-05","week":"星期五","fx":"南风","fl":"4-5级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"06","sunrise":"04:50","high":"高温 38.0℃","low":"低温 25.0℃","sunset":"19:40","ymd":"2019-07-06","week":"星期六","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"07","sunrise":"04:51","high":"高温 39.0℃","low":"低温 26.0℃","sunset":"19:40","ymd":"2019-07-07","week":"星期日","fx":"东南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"08","sunrise":"04:52","high":"高温 38.0℃","low":"低温 25.0℃","sunset":"19:40","ymd":"2019-07-08","week":"星期一","fx":"东南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"09","sunrise":"04:52","high":"高温 38.0℃","low":"低温 25.0℃","sunset":"19:39","ymd":"2019-07-09","week":"星期二","fx":"东风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"10","sunrise":"04:53","high":"高温 39.0℃","low":"低温 25.0℃","sunset":"19:39","ymd":"2019-07-10","week":"星期三","fx":"东南风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"11","sunrise":"04:53","high":"高温 39.0℃","low":"低温 27.0℃","sunset":"19:39","ymd":"2019-07-11","week":"星期四","fx":"南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"12","sunrise":"04:54","high":"高温 39.0℃","low":"低温 26.0℃","sunset":"19:38","ymd":"2019-07-12","week":"星期五","fx":"南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"13","sunrise":"04:55","high":"高温 39.0℃","low":"低温 25.0℃","sunset":"19:38","ymd":"2019-07-13","week":"星期六","fx":"西南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"}]
         */

        private String shidu;
        private int pm25;
        private int pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 28
             * sunrise : 04:47
             * high : 高温 31.0℃
             * low : 低温 24.0℃
             * sunset : 19:41
             * aqi : 51
             * ymd : 2019-06-28
             * week : 星期五
             * fx : 西南风
             * fl : <3级
             * type : 阵雨
             * notice : 阵雨来袭，出门记得带伞
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private int aqi;
            private String ymd;
            private String week;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 29
             * sunrise : 04:47
             * high : 高温 34.0℃
             * low : 低温 22.0℃
             * sunset : 19:41
             * aqi : 113
             * ymd : 2019-06-29
             * week : 星期六
             * fx : 西北风
             * fl : 4-5级
             * type : 多云
             * notice : 阴晴之间，谨防紫外线侵扰
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private int aqi;
            private String ymd;
            private String week;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
