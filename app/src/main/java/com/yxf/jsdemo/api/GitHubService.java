package com.yxf.jsdemo.api;

import com.yxf.jsdemo.api.bean.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/27 15:14
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/27 15:14
 */
public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);//此处的observable类似于promise，用来处理异步
}
