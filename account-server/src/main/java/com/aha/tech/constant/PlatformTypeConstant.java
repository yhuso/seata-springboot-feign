package com.aha.tech.constant;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * @author zyb
 * @since
 */

public class PlatformTypeConstant {
    /**
     * 签约平台：1微信公众号支付签约 2微信APP支付签约 3微信h5 4微信小程序 5华为 6IOS 7微信公众号纯签约 8微信app纯签约
     */

    public static final int WECHAT_WAP_1 = 1;
    public static final int WECHAT_APP_2 = 2;
    public static final int WECHAT_H5_3 = 3;
    public static final int WECHAT_PROGRAM_4 = 4;
    public static final int HUAWEI_5 = 5;
    public static final int IOS_6 = 6;
    public static final int ONLY_WECHAT_WAP_CONTACT_7 = 7;
    public static final int ONLY_WECHAT_APP_CONTACT_8 = 8;


    /**
     * 纯签约支付渠道
     */
    public static final Set<Integer> ONLY_CONTRACT = ImmutableSet.of(HUAWEI_5, ONLY_WECHAT_WAP_CONTACT_7, ONLY_WECHAT_APP_CONTACT_8);

}
