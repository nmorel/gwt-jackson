//@formatter:off
/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.utils;
/**
 * Custom Base64 encode/decode implementation suitable for use in
 * GWT applications (uses only translatable classes).
 */
public class Base64 {

    private static final String etab =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    private static byte[] dtab = {
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,
        -1,-1,-1,-1,-1,-1,-1,-1,-1,62,-1,-1,-1,63,52,53,54,
        55,56,57,58,59,60,61,-1,-1,-1,64,-1,-1,-1, 0, 1, 2,
         3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,
        20,21,22,23,24,25,-1,-1,-1,-1,-1,-1,26,27,28,29,30,
        31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,
        48,49,50,51,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
    };

    public static String decode(String data) {
        StringBuilder out = new StringBuilder();

        // length must be multiple of 4 (with padding)
        if (data.length() % 4 != 0)
            return "";

        for (int i = 0; i < data.length();) {
            byte e0 = dtab[data.charAt(i++) & 0x7f];
            byte e1 = dtab[data.charAt(i++) & 0x7f];
            byte e2 = dtab[data.charAt(i++) & 0x7f];
            byte e3 = dtab[data.charAt(i++) & 0x7f];

            // Invalid characters in input
            if (e0 == -1 || e1 == -1 || e2 == -1 || e3 == -1)
                return "";

            byte d0 = (byte) ((e0 << 2) + ((e1 >>> 4) & 0x03));
            byte d1 = (byte) ((e1 << 4) + ((e2 >>> 2) & 0x0f));
            byte d2 = (byte) ((e2 << 6) + (e3 & 0x3f));

            out.append(Character.toString((char) d0));
            if (e2 != 64)
                out.append(Character.toString((char) d1));
            if (e3 != 64)
                out.append(Character.toString((char) d2));
        }
        return out.toString();
    }

    public static String encode(String data) {
        StringBuilder out = new StringBuilder();

        int i = 0;
        int r = data.length();
        while (r > 0) {
            byte d0, d1, d2;
            byte e0, e1, e2, e3;

            d0 = (byte) data.charAt(i++); --r;
            e0 = (byte) (d0 >>> 2);
            e1 = (byte) ((d0 & 0x03) << 4);

            if (r > 0) {
                d1 = (byte) data.charAt(i++); --r;
                e1 += (byte) (d1 >>> 4);
                e2 = (byte) ((d1 & 0x0f) << 2);
            }
            else {
                e2 = 64;
            }

            if (r > 0) {
                d2 = (byte) data.charAt(i++); --r;
                e2 += (byte) (d2 >>> 6);
                e3 = (byte) (d2 & 0x3f);
            }
            else {
                e3 = 64;
            }
            out.append(etab.charAt(e0));
            out.append(etab.charAt(e1));
            out.append(etab.charAt(e2));
            out.append(etab.charAt(e3));
        }

        return out.toString();
    }
}
//@formatter:on