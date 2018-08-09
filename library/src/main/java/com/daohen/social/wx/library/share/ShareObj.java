package com.daohen.social.wx.library.share;

import android.graphics.Bitmap;

import com.daohen.personal.toolbox.library.util.Strings;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.io.ByteArrayOutputStream;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATA : 2017/07/12 15:47
 */
public class ShareObj {

    private static final int MAX_KB = 32 * 1024;

    private SendMessageToWX.Req req;

    public ShareObj(SendMessageToWX.Req req){
        this.req = req;
    }

    protected static String buildTransation(String type){
        return Strings.isNull(type) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public SendMessageToWX.Req getReq(){
        return req;
    }

    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);

        int options = 100;
        while (output.toByteArray().length > MAX_KB && options != 10) {
            output.reset(); //清空output
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }

        byte[] result = output.toByteArray();

        try {
            output.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        } /*finally {
            if (needRecycle) {//交给外部回收
                if (bmp != null)
                    bmp.recycle();
            }
        }*/

        return result;
    }

}
