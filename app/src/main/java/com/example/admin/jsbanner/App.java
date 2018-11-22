package com.example.admin.jsbanner;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * author : zlf
 * date   : 2018/11/22
 * blog   :https://www.jianshu.com/u/281e9668a5a6
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //创建全局的配置来初始化ImageLoader
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}
