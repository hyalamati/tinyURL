package com.encodeURLApp.URLFormatterService.utility;

import java.math.BigInteger;

public class EncodeURL {

        private static final BigInteger BASE = BigInteger.valueOf(62);
        private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        static String encode(BigInteger number) {
            if (number.compareTo(BigInteger.ZERO) < 0) {
                throwIllegalArgumentException("number must not be negative");
            }
            StringBuilder result = new StringBuilder();
            while (number.compareTo(BigInteger.ZERO) > 0) {
                BigInteger[] divmod = number.divideAndRemainder(BASE);
                number = divmod[0];
                int digit = divmod[1].intValue();
                result.insert(0, DIGITS.charAt(digit));
            }
            if (result.length() >= 11) {
                return result.toString().substring(0,10);
            }
            else if(result.length() == 0) {
                return DIGITS.substring(0, 1);
            }else
            return result.toString();
        }

        private static BigInteger throwIllegalArgumentException(String format, Object... args) {
            throw new IllegalArgumentException(String.format(format, args));
        }

}
