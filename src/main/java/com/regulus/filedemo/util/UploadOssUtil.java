package com.regulus.filedemo.util;

import com.qiniu.util.Auth;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-09-21
 */
public class UploadOssUtil {
    public static final String DOMAIN = "http://s1bbdov13.hd-bkt.clouddn.com/";

    public static void uploadImage(){
        String accessKey = "b2aUW5hXA_7XMaMA_z-OxooBtSKpjdPJu9qQeL3B";
        String secretKey = "RvUW9JlTiLTdQSYSMYL0mTlzdS9eZUX8JJz9U0Au";
        String bucket = "regulus-teamwork";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);

    }

}
