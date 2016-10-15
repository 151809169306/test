/**
 * @(#)Test.java, 2016Äê10ÔÂ14ÈÕ.
 *
 * Copyright 2016 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.test.age;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author iamma
 */
public class Test {
    public static void main(String args[]) {

        String teString = "XDYH:1~gzyhClear:1,666:2,gzyhClear:1";

        String ss = new Test().mapRoleCodeFromLcToJj(teString);

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

        StringBuilder result = new StringBuilder();
        String[] sourceStrs = roleCode.split(",");
        List<StringBuilder> listTempStr = new ArrayList<>();
        for (int j = 0; j < sourceStrs.length; j++) {
            String sourceStr = sourceStrs[j];
            String[] sourceStr1s = sourceStr.split("~");

            StringBuilder tempStrBuilder = new StringBuilder();
            List<String[]> listTemp = new ArrayList<>();

            for (int i = 0; i < sourceStr1s.length; i++) {
                String sourceStr1 = sourceStr1s[i];
                String[] sourceStr2s = sourceStr1.split(":");

                String temp = "";
                if (sourceStr2s.length != 2) {
                    continue;
                }

                if ((temp = stringPairMap.get(sourceStr2s[0])) != null) {
                    sourceStr2s[0] = temp;
                    listTemp.add(sourceStr2s);
                }
            }
            if (listTemp.size() == 0) {
                continue;
            }
            for (int ii = 0; ii < listTemp.size(); ii++) {
                String[] tempStra = listTemp.get(ii);
                tempStrBuilder.append(tempStra[0] + ":" + tempStra[1]);
                if (ii != listTemp.size() - 1) {
                    tempStrBuilder.append("~");
                }
            }

            listTempStr.add(tempStrBuilder);
        }
        for (int ii = 0; ii < listTempStr.size(); ii++) {
            StringBuilder tempStra = listTempStr.get(ii);
            result.append(tempStra.toString());
            if (ii != listTempStr.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
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
