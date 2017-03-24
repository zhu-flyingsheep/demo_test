package com.utsoft.myapplication.Interface;

import com.utsoft.myapplication.model.ResultVdieotags;
import com.utsoft.myapplication.model.VideoTag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hyong on 2017/3/7.
 * func:
 */

public interface IvideoTag {


    @GET("livideo")
    Call<ResultVdieotags> getVdieoTags(@Query("cmd") String sort);
}
