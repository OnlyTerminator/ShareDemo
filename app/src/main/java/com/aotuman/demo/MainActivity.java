package com.aotuman.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aotuman.share.MJThirdShareManager;
import com.aotuman.share.entity.ShareChannelType;
import com.aotuman.share.entity.ShareContentConfig;
import com.aotuman.share.entity.ShareContentType;
import com.aotuman.share.entity.ShareNewConfig;
import com.aotuman.share.listener.ShareListener;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareNewConfig.initShareKey(BuildConfig.SHARE_KEY_WEIXIN,BuildConfig.SHARE_KEY_WEIXIN_SECRET,BuildConfig.SHARE_KEY_SINA,BuildConfig.SHARE_KEY_QQ,BuildConfig.SHARE_SINA_OAUTH_URL,BuildConfig.SHARE_SINA_OAUTH_SCOPE);
        setContentView(R.layout.activity_main_test);
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
                ShareContentConfig picConfig = new ShareContentConfig.Builder("aaa", "bbb")
                        .messageContent("这个是短信分享的内容")
                        .putShareType(ShareChannelType.WX_FRIEND, ShareContentType.PIC).build();
                new MJThirdShareManager(MainActivity.this, new ShareListener() {
                    @Override
                    public void onSuccess(ShareChannelType type) {

                    }

                    @Override
                    public void onError(ShareChannelType type) {

                    }

                    @Override
                    public void onCancel(ShareChannelType type) {

                    }
                }).doShare(picConfig, false);
                break;
            case R.id.tv_web:
                ShareContentConfig webConfig = new ShareContentConfig.Builder("aaa", "bbb")
                        .shareUrl("http://sj.qq.com/myapp/detail.htm?apkName=com.aotuman.notepad")
                        .putShareType(ShareChannelType.WX_TIMELINE, ShareContentType.WEBPAGE)
                        .putShareType(ShareChannelType.QQ, ShareContentType.WEBPAGE)
                        .build();
                new MJThirdShareManager(MainActivity.this, null).doShare(webConfig, false);
                break;
        }
    }
}
