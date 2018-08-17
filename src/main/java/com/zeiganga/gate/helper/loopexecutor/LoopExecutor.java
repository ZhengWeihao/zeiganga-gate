package com.zeiganga.gate.helper.loopexecutor;

import com.zeiganga.gate.logger.CustomLogger;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ZhengWeihao
 * @Date: 2018/8/17
 * @Time: 10:17
 */
public class LoopExecutor {

    private static final long DEFAULT_SLEEP_TIME = 1000;
    private static final CustomLogger logger = CustomLogger.getLogger(LoopExecutor.class);

    private LoopExecuteBusiness business;
    private boolean isStop = true;

    public LoopExecutor(LoopExecuteBusiness business) {
        this.business = business;
    }


    /**
     * 循环执行任务
     */
    public void loopExecute() {
        loopExecute(0);
    }

    /**
     * 循环执行任务
     */
    public void loopExecute(long sleepMillis) {
        if (sleepMillis <= 0) {
            sleepMillis = DEFAULT_SLEEP_TIME;
        }

        isStop = false;
        while (!isStop && !business.isStop()) {
            business.execute();

            try {
                Thread.sleep(sleepMillis);
            } catch (Exception e) {
                logger.error("暂停异常", e);
            }
        }

        // 执行完毕后初始化控制标识
        business.finallyExecute();
        isStop = true;
    }

    /**
     * 停止执行
     */
    public void stop() {
        isStop = true;
    }
}
