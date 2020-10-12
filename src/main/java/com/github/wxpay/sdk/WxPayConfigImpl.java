package com.github.wxpay.sdk;

import java.io.InputStream;

public class WxPayConfigImpl extends WXPayConfig {

    //APPID
    public String getAppID() {
        return "wx927ce75bbe52a272";
    }

    //MCH_ID 商户号
    public String getMchID() {
        return "1587390201";
    }

    //KEY API密钥
    public String getKey() {
        return "7b01963b0b8224e899165b7ab94723bb";
    }

    public InputStream getCertStream() {
        return null;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }


}
