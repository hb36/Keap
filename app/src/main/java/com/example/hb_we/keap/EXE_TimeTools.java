package com.example.hb_we.keap;

import android.graphics.Paint;

/**
 * Created by Cxy on 2017/5/11.
 */

public class EXE_TimeTools {
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int second = 0;

        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }
}
