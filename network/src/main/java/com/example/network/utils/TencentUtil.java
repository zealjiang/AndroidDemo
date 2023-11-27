package com.example.network.utils;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class TencentUtil {

    public static final String secretId = "AKIDjxlr38cez45xt6911qbiMSwPf95c3i1kw1ge";
    public static final String secretKey = "cr05ks0w1pGtgiztpIfk44ln8el4n3zzm1orv8";
    private static final String CONTENT_CHARSET = "UTF-8";
    private static final String HMAC_ALGORITHM = "HmacSHA1";

    private TencentUtil() {}

    private static String sign(String secret, String timeStr) {
        String signStr = "date: " + timeStr + "\n" + "source: " + "source";
        try {
            Mac mac1 = Mac.getInstance(HMAC_ALGORITHM);

            byte[] hash;
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac1.getAlgorithm());
            mac1.init(secretKey);
            hash = mac1.doFinal(signStr.getBytes(CONTENT_CHARSET));
            String sig = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                sig = new String(Base64.getEncoder().encode(hash));
            } else {
                //sig = new String(kotlin.io.encoding.Base64.Default.encode(hash, 0, hash.length));
                sig = "";
            }
            System.out.println("signValue--->"+sig);
            return sig;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getTimeStr() {
        return "";
    }

    public static String getAuthorization(String sign) {
        return "";
    }
}
