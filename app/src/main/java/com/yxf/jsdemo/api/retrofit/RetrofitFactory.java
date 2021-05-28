package com.yxf.jsdemo.api.retrofit;

import com.yxf.jsdemo.api.GitHubService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/27 15:17
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/27 15:17
 */
public class RetrofitFactory {

    private static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                // 改成RxJava2CallAdapterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        return retrofit;
    }

    public static GitHubService getGitHubServiceApi() {
        Retrofit instance = getInstance();
        GitHubService gitHubService = instance.create(GitHubService.class);
        return gitHubService;
    }

}
