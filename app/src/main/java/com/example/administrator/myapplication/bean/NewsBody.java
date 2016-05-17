package com.example.administrator.myapplication.bean;

/**
 * <Pre>
 *     版本更新
 * </Pre>
 *
 * @author 刘阳
 * @version 1.0
 *          <p/>
 *          Create by 2016/1/27 15:38
 *          {"config_update":"Yes","report_policy":1,"last_config_time":1459997879348,"ad_request":1,"online_params":{},"oc_interval":10}
 */
public class NewsBody {
    public String config_update;// 5572a109b3cdc86cf39001db,
    public String report_policy;// 国内最新,
    public String last_config_time;// 0,
    public String ad_request;// 令越来越多的上市公司因为大股东股权质押触及预警线或平仓线面临危机，截至目前已经有6家上市公司因...,
    public String oc_interval;// http://finance.qq.com/a/20160128/011948.htm,

    public String getConfig_update() {
        return config_update;
    }

    public void setConfig_update(String config_update) {
        this.config_update = config_update;
    }

    public String getReport_policy() {
        return report_policy;
    }

    public void setReport_policy(String report_policy) {
        this.report_policy = report_policy;
    }

    public String getLast_config_time() {
        return last_config_time;
    }

    public void setLast_config_time(String last_config_time) {
        this.last_config_time = last_config_time;
    }

    public String getAd_request() {
        return ad_request;
    }

    public void setAd_request(String ad_request) {
        this.ad_request = ad_request;
    }

    public String getOc_interval() {
        return oc_interval;
    }

    public void setOc_interval(String oc_interval) {
        this.oc_interval = oc_interval;
    }
}
