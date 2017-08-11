package com.daohen.social.wx.library;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.daohen.personal.toolbox.library.Singleton;
import com.daohen.personal.toolbox.library.util.Logs;
import com.daohen.social.wx.library.login.LoginListener;
import com.daohen.social.wx.library.login.LoginObj;
import com.daohen.social.wx.library.share.ShareBitmapObj;
import com.daohen.social.wx.library.share.ShareMiniProgramObj;
import com.daohen.social.wx.library.share.ShareMusicObj;
import com.daohen.social.wx.library.share.ShareTextObj;
import com.daohen.social.wx.library.share.ShareVideoObj;
import com.daohen.social.wx.library.share.ShareWebpageObj;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATA : 2017/07/10 23:56
 */
public class WxProvider {

    private String appid;
    private String appSecret;

    private static IWXAPI iwxapi;

    public static final WxProvider get(){
        return gDefault.get();
    }

    public void registerWx(Context context, String appid, String appSecret){
        this.appid = appid;
        this.appSecret = appSecret;
        iwxapi = WXAPIFactory.createWXAPI(context, appid, true);
        iwxapi.registerApp(appid);
    }

    public String getAppid(){
        return appid;
    }

    public String getAppSecret(){
        return appSecret;
    }

    public void shareText(String content, boolean isTimeline){
        shareText(content, null, isTimeline);
    }

    /**
     * 分享文本
     * @param content
     * @param desc
     * @param isTimeline
     */
    public void shareText(String content, String desc, boolean isTimeline){
        checkNull();

        ShareTextObj textObj = new ShareTextObj.Builder()
                .content(content)
                .desc(desc)
                .isTimeline(isTimeline)
                .build();
        iwxapi.sendReq(textObj.getReq());
    }

    /**
     * 分享图片
     * @param bitmap
     * @param isTimeline
     */
    public void shareBitmap(Bitmap bitmap, boolean isTimeline){
        checkNull();

        ShareBitmapObj bitmapObj = new ShareBitmapObj.Builder()
                .bitmap(bitmap)
                .isTimeline(isTimeline)
                .build();
        iwxapi.sendReq(bitmapObj.getReq());
    }

    public void shareMusic(String url, String title, Bitmap thumb, boolean isTimeline){
        shareMusic(url, title, null, thumb, isTimeline);
    }

    /**
     * 分享音乐
     * @param url
     * @param title
     * @param desc
     * @param thumb
     */
    public void shareMusic(String url, String title, String desc, Bitmap thumb, boolean isTimeline){
        checkNull();

        ShareMusicObj musicObj = new ShareMusicObj.Builder()
                .url(url)
                .title(title)
                .desc(desc)
                .thumb(thumb)
                .isTimeline(isTimeline)
                .build();
        iwxapi.sendReq(musicObj.getReq());

    }

    public void shareVideo(String url, String title, Bitmap thumb, boolean isTimeline){
        shareVideo(url, title, null, thumb, isTimeline);
    }

    /**
     * 分享视频
     * @param url
     * @param title
     * @param desc
     * @param thumb
     * @param isTimeline
     */
    public void shareVideo(String url, String title, String desc, Bitmap thumb, boolean isTimeline){
        checkNull();

        ShareVideoObj videoObj = new ShareVideoObj.Builder()
                .url(url)
                .title(title)
                .desc(desc)
                .thumb(thumb)
                .isTimeline(isTimeline)
                .build();
        iwxapi.sendReq(videoObj.getReq());
    }

    public void shareWebpage(String url, String title, boolean isTimeline){
        shareWebpage(url, title, null, null, isTimeline);
    }

    /**
     * 分享网页
     * @param url
     * @param title
     * @param desc
     * @param thumb
     * @param isTimeline
     */
    public void shareWebpage(String url, String title, String desc, Bitmap thumb, boolean isTimeline){
        checkNull();

        ShareWebpageObj webpageObj = new ShareWebpageObj.Builder()
                .url(url)
                .title(title)
                .desc(desc)
                .thumb(thumb)
                .isTimeline(isTimeline)
                .build();
        iwxapi.sendReq(webpageObj.getReq());
    }

    /**
     * 分享小程序
     * @param url 低版本微信将打开url
     * @param username 跳转的小程序原始id
     * @param path 小程序path
     * @param title
     * @param desc
     * @param thumb
     */
    public void shareMiniProgram(String url, String username, String path, String title, String desc, Bitmap thumb){
        checkNull();

        ShareMiniProgramObj miniProgramObj = new ShareMiniProgramObj.Builder()
                .url(url)
                .username(username)
                .path(path)
                .title(title)
                .desc(desc)
                .thumb(thumb)
                .build();
        iwxapi.sendReq(miniProgramObj.getReq());
    }

    public void handleIntent(Intent intent, IWXAPIEventHandler iwxapiEventHandler){
        checkNull();

        iwxapi.handleIntent(intent, iwxapiEventHandler);
    }

    /**
     * 微信登录
     * @param listener
     */
    public void login(LoginListener listener){
        checkNull();

        iwxapi.sendReq(LoginObj.get().getSendAuthReq(listener));
    }

    private WxProvider(){
        Logs.e("TestTest");
    }

    private static final Singleton<WxProvider> gDefault = new Singleton<WxProvider>() {
        @Override
        protected WxProvider create() {
            return new WxProvider();
        }
    };

    private void checkNull(){
        if (iwxapi == null)
            throw new NullPointerException("需要先注册到微信才能使用，请先调用registerWx方法");
    }

}
