package com.wdcloud;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述：
 * Created by houzehong on 2016/7/27.
 */
public class Test {
    public static void main(String[] args) {
        String path="F:\\备份\\app-master\\src\\main\\webapp\\upload\\image\\20160726\\1469526458162027410.png";
        String rootPath="F://备份";
        path=path.replace( "\\", "/" );
        String uriPath= StringUtils.replace(path,rootPath,"");
        System.out.println(uriPath);

    }
}
