package com.example.administrator.myapplication.bean;

import java.util.List;

/**
 * Created by drakeet on 8/9/15.
 */
public class MeizhiData  {

    public ResultsBean results;

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public  class  ResultsBean{
        public List<Meizhi> Android;

        public List<Meizhi> getAndroid() {
            return Android;
        }

        public void setAndroid(List<Meizhi> android) {
            Android = android;
        }
    }
}