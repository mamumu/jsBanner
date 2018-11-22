package com.example.admin.jsbanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : zlf
 * date   : 2018/11/22
 * blog   :https://www.jianshu.com/u/281e9668a5a6
 */
public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;
    @BindView(R.id.cb_test1)
    ConvenientBanner cbTest1;
    @BindView(R.id.cb_test2)
    ConvenientBanner cbTest2;

    // TODO: 2018/11/22 是否自动轮播,控制如果是一张图片，不能滑动
    private boolean mCanLoop = true;

    private ArrayList<String> arrayList;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initView();
        initBanner1();
        initBanner2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始执行轮播，并设置轮播时长
        cbTest1.startTurning(4000);
        cbTest2.startTurning(2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止轮播
        cbTest1.stopTurning();
        cbTest2.stopTurning();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 初始化
     * 添加三张展示照片，网上随便找的，正常形式是调用接口从自己的后台服务器拿取
     */
    private void initView() {
        arrayList = new ArrayList<>();
        arrayList.add("http://img2.imgtn.bdimg.com/it/u=1447362014,2103397884&fm=200&gp=0.jpg");
        arrayList.add("http://img1.imgtn.bdimg.com/it/u=111342610,3492888501&fm=26&gp=0.jpg");
        arrayList.add("http://imgsrc.baidu.com/imgad/pic/item/77094b36acaf2eddc8c37dc7861001e9390193e9.jpg");
    }

    /**
     * 初始化轮播图1
     * setPageIndicator 设置指示器样式
     * setPageIndicatorAlign 设置指示器位置
     * setPointViewVisible 设置指示器是否显示
     * setCanLoop 设置是否轮播
     * setOnItemClickListener 设置每一张图片的点击事件
     */
    private void initBanner1() {

        // TODO: 2018/11/22 控制如果只有一张网络图片，不能滑动，不能轮播
        if(arrayList.size()<=1){
            mCanLoop=false;
        }

        cbTest1.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new NetImageHolderView1(itemView);
            }

            @Override
            public int getLayoutId() {
                //设置加载哪个布局
                return R.layout.item_banner1;
            }
        }, arrayList)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(mCanLoop)
                .setCanLoop(mCanLoop)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(MainActivity.this, "你点击了cbTest1的第" + position + "张图片", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 初始化轮播图2
     */
    private void initBanner2() {
        cbTest2.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new NetImageHolderView2(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner2;
            }
        }, arrayList)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(mCanLoop)
                .setCanLoop(mCanLoop)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(MainActivity.this, "你点击了cbTest2的第" + position + "张图片", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 轮播图1 对应的holder
     */
    public class NetImageHolderView1 extends Holder<String> {
        private ImageView mImageView;

        //构造器
        public NetImageHolderView1(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            //找到对应展示图片的imageview
            mImageView = itemView.findViewById(R.id.iv_banner1);
            //设置图片加载模式为铺满，具体请搜索 ImageView.ScaleType.FIT_XY
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //初始化options，可以加载不同情况下的默认图片
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)//设置加载图片时候的图片
                    .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.ic_launcher)//设置获取图片失败的默认图片
                    .cacheInMemory(true)//设置内存缓存
                    .cacheOnDisk(true)//设置外存缓存
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }

        @Override
        public void updateUI(String data) {
            //使用ImageLoader加载图片
            ImageLoader.getInstance().displayImage(data, mImageView, options);
        }
    }

    /**
     * 轮播图2 对应的holder
     */
    public class NetImageHolderView2 extends Holder<String> {
        private ImageView mImageView;

        //构造器
        public NetImageHolderView2(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            //找到对应展示图片的imageview
            mImageView = itemView.findViewById(R.id.iv_banner2);
            //设置图片加载模式为铺满，具体请搜索 ImageView.ScaleType.FIT_XY
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void updateUI(String data) {
            //使用glide加载更新图片
            Glide.with(MainActivity.this).load(data).into(mImageView);
        }
    }
}
