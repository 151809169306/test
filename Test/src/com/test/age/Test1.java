/**
 * @(#)Test.java, 2016Äê10ÔÂ14ÈÕ.
 *
 * Copyright 2016 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.test.age;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iamma
 */
public class Test1 {
    public static void main(String args[]) {

        String teString = "XDYH:1,,,";

        String ss = new Test1().mapRoleCodeFromLcToJj(teString);

        System.out.println(ss);
    }

    public String mapRoleCodeFromLcToJj(String roleCode) {

        if (roleCode == null) {
            return null;
        }
        String mapRelation = "XDYH:jjXDYH,test:jjXDYH,00000010:jj00000010,admin:jjadmin,"
            + "gzyh:jjgzyh,gzyhClear:jjgzyhClear,gzyhConuter:jjgzyhConuter,sxyh001:jjsxyh001";

        //String mapRelation = SysConfigUtil.getSysConfig().getValue("SRYH_ROLEMAP");

        Map<String, String> stringPairMap = getReplaceStringPair(mapRelation);
        if (stringPairMap == null || stringPairMap.size() == 0) {

        }

        StringBuilder resultStrBuilder = new StringBuilder();
        StringBuilder tempStrBuilder = new StringBuilder();

        boolean isJump = false;
        for (int i = 0; i < roleCode.length(); i++) {

            char charAtI = roleCode.charAt(i);

            if (isJump == true && charAtI != '~' && charAtI != ',') {
                continue;
            }

            if (charAtI == ':') {
                String strMapGet = stringPairMap.get(tempStrBuilder.toString());
                tempStrBuilder.setLength(0);
                if (strMapGet == null) {
                    isJump = true;
                    continue;
                }
                if (strMapGet.equals("jjgzyhClear")) {
                    System.out
                        .println(strMapGet + " " + charAtI + " " + isJump);
                }

                resultStrBuilder.append(strMapGet);
            }

            tempStrBuilder.append(charAtI);

            if (charAtI == '~' || charAtI == ','
                || i == roleCode.length() - 1) {
                if (isJump)
                    isJump = false;
                else
                    resultStrBuilder.append(tempStrBuilder.toString());
                tempStrBuilder.setLength(0);

            }
        }
        return resultStrBuilder.toString();
    }

    private Map<String, String> getReplaceStringPair(String replaceStr) {

        Map<String, String> result = null;
        if (replaceStr == null) {
            return null;
        }

        String[] tempStrs = replaceStr.split(",");
        if (tempStrs == null || tempStrs.length == 0) {
            return null;
        }
        result = new HashMap<>();

        for (String tempStr: tempStrs) {

            String[] tempStr1 = tempStr.split(":");
            if (tempStr1 == null || tempStr1.length != 2) {
                continue;
            }
            result.put(tempStr1[0], tempStr1[1]);
        }

        return result;
    }
}
