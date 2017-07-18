package com.codedrinker.task;

import com.codedrinker.utils.LogUtils;

import java.util.Date;

/**
 * Created by codedrinker on 18/07/2017.
 */
public class DaemonTask {
    public void schedule() {
        LogUtils.log("schedule : ", new Date());
    }
}
