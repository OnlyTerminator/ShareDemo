package com.aotuman.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.aotuman.share.CommonUtils;
import com.aotuman.share.MJThirdShareManager;
import com.aotuman.share.entity.ShareChannelType;
import com.aotuman.share.entity.ShareContentConfig;
import com.aotuman.share.entity.ShareContentType;
import com.aotuman.share.entity.ShareNewConfig;
import com.aotuman.share.listener.ShareListener;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener,ShareListener {
    private MJThirdShareManager mMjThirdShareManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        mMjThirdShareManager = new MJThirdShareManager(this,this);
        ShareNewConfig.initShareKey(BuildConfig.SHARE_KEY_WEIXIN,BuildConfig.SHARE_KEY_WEIXIN_SECRET,BuildConfig.SHARE_KEY_SINA,BuildConfig.SHARE_KEY_QQ,BuildConfig.SHARE_SINA_OAUTH_URL,BuildConfig.SHARE_SINA_OAUTH_SCOPE);
        findViewById(R.id.tv_pic).setOnClickListener(this);
        findViewById(R.id.tv_music).setOnClickListener(this);
        findViewById(R.id.tv_web).setOnClickListener(this);
        findViewById(R.id.tv_text).setOnClickListener(this);
        findViewById(R.id.tv_video).setOnClickListener(this);
        findViewById(R.id.tv_other).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_music:
                Toast.makeText(MainActivity.this, "暂时还未开通", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_pic:
                showPicDialog();
                break;
            case R.id.tv_web:
                ShareContentConfig webConfig = new ShareContentConfig.Builder("Android分享Demo", "Android分享第三方hong'bao")
                        .shareUrl("https://github.com/OnlyTerminator/ShareDemo")
                        .putShareType(ShareChannelType.WX_TIMELINE, ShareContentType.WEBPAGE)
                        .putShareType(ShareChannelType.QQ, ShareContentType.WEBPAGE)
                        .build();
                new MJThirdShareManager(MainActivity.this, null).doShare(webConfig, false);
                break;
            case R.id.tv_other:
                break;
            case R.id.tv_video:
                break;
            case R.id.tv_text:
                ShareContentConfig picConfig = new ShareContentConfig.Builder("aaa", "bbb")
                        .messageContent("这个是短信分享的内容")
                        .putShareType(ShareChannelType.WX_FRIEND, ShareContentType.PIC).build();
                mMjThirdShareManager.doShare(picConfig,false);
                break;
        }
    }

    @Override
    public void onSuccess(ShareChannelType type) {

    }

    @Override
    public void onError(ShareChannelType type) {

    }

    @Override
    public void onCancel(ShareChannelType type) {

    }

    public void showPicDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.drawable.ic_launcher_background);
        normalDialog.setTitle("选择你要分享的图片来源");
        normalDialog.setPositiveButton("网络图片",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        Toast.makeText(MainActivity.this,"由于第三方只支持分享本地图片，所以我们也是，如果需要分享网络图片，请先自行下载。",Toast.LENGTH_SHORT).show();
                    }
                });
        normalDialog.setNegativeButton("本地图片",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        final String path = CommonUtils.getFilesDir(MainActivity.this, "share").getAbsolutePath() + File.separator + "ATMShareImage.png";
                        ShareContentConfig picConfig = new ShareContentConfig.Builder("图片分享", "这是一张网络风景图")
                                .messageContent("这个是短信分享的内容")
                                .localImagePath(path)
                                .putShareType(ShareChannelType.WX_FRIEND, ShareContentType.PIC).build();
                        mMjThirdShareManager.doShare(picConfig,true);
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.share_test);
                                CommonUtils.writeBitmap(new File(path),bitmap,100,true);
                                mMjThirdShareManager.prepareSuccess(true);
                            }
                        }.start();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
