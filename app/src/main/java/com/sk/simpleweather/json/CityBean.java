package com.sk.simpleweather.json;

import java.util.List;

public class CityBean {


    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : [{"cid":"CN101240301","location":"上饶","parent_city":"上饶","admin_area":"江西","cnty":"中国","lat":"28.44441986","lon":"117.97118378","tz":"+8.00","type":"city"},{"cid":"CN101020100","location":"上海","parent_city":"上海","admin_area":"上海","cnty":"中国","lat":"31.23170662","lon":"121.47264099","tz":"+8.00","type":"city"},{"cid":"CN101240703","location":"上犹","parent_city":"赣州","admin_area":"江西","cnty":"中国","lat":"25.79428482","lon":"114.54053497","tz":"+8.00","type":"city"},{"cid":"CN101240308","location":"上饶县","parent_city":"上饶","admin_area":"江西","cnty":"中国","lat":"28.45389748","lon":"117.9061203","tz":"+8.00","type":"city"},{"cid":"CN101050817","location":"上甘岭","parent_city":"伊春","admin_area":"黑龙江","cnty":"中国","lat":"47.97485733","lon":"129.02508545","tz":"+8.00","type":"city"},{"cid":"CN101240903","location":"上栗","parent_city":"萍乡","admin_area":"江西","cnty":"中国","lat":"27.87704086","lon":"113.80052185","tz":"+8.00","type":"city"},{"cid":"CN101181604","location":"上蔡","parent_city":"驻马店","admin_area":"河南","cnty":"中国","lat":"33.2647171","lon":"114.26689148","tz":"+8.00","type":"city"},{"cid":"CN101210109","location":"上城","parent_city":"杭州","admin_area":"浙江","cnty":"中国","lat":"30.25023651","lon":"120.17146301","tz":"+8.00","type":"city"},{"cid":"CN101230705","location":"上杭","parent_city":"龙岩","admin_area":"福建","cnty":"中国","lat":"25.05001831","lon":"116.42477417","tz":"+8.00","type":"city"},{"cid":"CN101240505","location":"上高","parent_city":"宜春","admin_area":"江西","cnty":"中国","lat":"28.23478889","lon":"114.93265533","tz":"+8.00","type":"city"}]
         * status : ok
         */

        private String status;
        private List<BasicBean> basic;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<BasicBean> getBasic() {
            return basic;
        }

        public void setBasic(List<BasicBean> basic) {
            this.basic = basic;
        }

        public static class BasicBean {
            /**
             * cid : CN101240301
             * location : 上饶
             * parent_city : 上饶
             * admin_area : 江西
             * cnty : 中国
             * lat : 28.44441986
             * lon : 117.97118378
             * tz : +8.00
             * type : city
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;
            private String type;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
