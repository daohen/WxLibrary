package com.daohen.social.wx.library.login;

import com.daohen.personal.toolbox.library.Singleton;
import com.daohen.social.wx.library.WxProvider;
import com.daohen.social.wx.library.bean.AccessTokenResponse;
import com.daohen.social.wx.library.bean.WxUserInfoResponse;
import com.daohen.thirdparty.library.retrofit.RetrofitFactory;
import com.daohen.thirdparty.library.rxjava.SingleDefaultObserver;
import com.daohen.thirdparty.library.rxjava.SingleSchedulerTransformer;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import io.reactivex.annotations.NonNull;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATA : 2017/07/12 22:58
 */
public class LoginObj {

    public static LoginObj get(){
        return gDefault.get();
    }

    public SendAuth.Req getSendAuthReq(LoginListener listener){
        this.loginListener = listener;

        if (loginListener == null)
            throw new NullPointerException("LoginListener is null");

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_library_from_daohen";
        return req;
    }

    public void login(String code){
        loginService.getAccessToken(WxProvider.get().getAppid(), WxProvider.get().getAppSecret(), code, "authorization_code")
                .compose(new SingleSchedulerTransformer<AccessTokenResponse>())
                .subscribe(new SingleDefaultObserver<AccessTokenResponse>() {
                    @Override
                    public void onSuccess(@NonNull AccessTokenResponse accessTokenResponse) {
                        getUserInfo(accessTokenResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loginListener.onFail(e);
                    }
                });
    }

    public void getUserInfo(AccessTokenResponse response){
        loginService.getUserInfo(response.getAccessToken(), response.getOpenid())
                .compose(new SingleSchedulerTransformer<WxUserInfoResponse>())
                .subscribe(new SingleDefaultObserver<WxUserInfoResponse>() {
                    @Override
                    public void onSuccess(@NonNull WxUserInfoResponse wxUserInfoResponse) {
                        loginListener.onSuccess(wxUserInfoResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        loginListener.onFail(e);
                    }
                });
    }


    private static final Singleton<LoginObj> gDefault = new Singleton<LoginObj>() {
        @Override
        protected LoginObj create() {
            return new LoginObj();
        }
    };

    private LoginListener loginListener;
    private WxLoginService loginService;
    private LoginObj(){
        loginService = RetrofitFactory.getDefault("https://api.weixin.qq.com/")
                .create(WxLoginService.class);
    }
}
