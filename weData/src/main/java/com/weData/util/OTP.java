package com.weData.util;

import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OTP {

    private static final long DISTANCE = 30000L; // 30sec
    private static final String ALGORITHM = "HmacSHA1";
    private static final byte[] SECRETKEY = "Q1H#2WHSA4D)23()".getBytes();

    private static long create(long time) throws Exception{
        byte[] data = new byte[8];
        long value = time;
        for(int i = 8; i-- > 0; value>>>=8) {
            data[i] = (byte) value;
        }

        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(SECRETKEY, ALGORITHM));
        byte[] hash = mac.doFinal(data);
        int offset = hash[20-1] & 0xF;
        
        long truncatedHash = 0;
        for(int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset+i] & 0xFF;
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;

        return truncatedHash;
    }

    public static String create() throws Exception {
        return String.format("%06d", create(new Date().getTime() / DISTANCE));
    }

    public static boolean verify(String code) throws Exception{
        return create().equals(code);
    }
}
