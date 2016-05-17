package com.example.administrator.myapplication.factory;

import com.example.administrator.myapplication.tools.DebugUtil;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import java.io.IOException;

final class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    JsonResponseBodyConverter() {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonObj;
        try {
            jsonObj = new String(value.string());
            DebugUtil.i("gxj",jsonObj);
            return (T) jsonObj;
        } catch(Exception e) {
            return null;
        }
    }
}
