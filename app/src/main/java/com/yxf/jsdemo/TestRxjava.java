package com.yxf.jsdemo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.orhanobut.logger.Logger;
import com.yxf.jsdemo.api.GitHubService;
import com.yxf.jsdemo.api.bean.Repo;
import com.yxf.jsdemo.api.retrofit.RetrofitFactory;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/27 14:40
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/27 14:40
 */
public class TestRxjava {
    public static void start1() {
        //observable
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {//subscribe in other thread
                e.onNext(1);
                e.onComplete();
                //Logger.w("observable thread %s", Thread.currentThread().getName());
            }
        });

        //observer
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.w("onSubscribe");
            }

            @Override
            public void onNext(Object value) {
                Logger.w("onNext %s", value.toString());
                // Logger.w("observer onNext thread %s", Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Logger.w("onError");
            }

            @Override
            public void onComplete() {
                Logger.w("onComplete");
            }
        };

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer() {//doOnNext在onNext之前执行
                    @Override
                    public void accept(Object o) throws Exception {
                        Logger.w("observerOn 1 thread :%s", Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Logger.w("observerOn 2 thread :%s", Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Logger.w("observerOn 3 thread :%s ", Thread.currentThread().getName());
                    }
                })
                .subscribe(observer);
    }

    public static void fetchList() {
        GitHubService gitHubServiceApi = RetrofitFactory.getGitHubServiceApi();
        gitHubServiceApi.listRepos("111")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.d("onSubscribe...");
                    }

                    @Override
                    public void onNext(List<Repo> value) {
                        Logger.d("onNext...%d", value.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError...");
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete...");
                    }
                });
    }

    public static void testJust() {
        Observable.just(justFunc())
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.w("onSubscribe...");
                    }

                    @Override
                    public void onNext(String value) {
                        Logger.w("onNext value %s", value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.w("onError...");
                    }

                    @Override
                    public void onComplete() {
                        Logger.w("onComplete...");
                    }
                });
    }

    private static String justFunc() {
        Logger.w("justFunc start thread is :%s", Thread.currentThread().getName());
        return "data form justFunc";
    }

    private void testRxSync(){}

}
