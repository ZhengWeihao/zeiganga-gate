package com.zeiganga.gate.helper.loopexecutor;

/**
 * 重复执行的业务
 *
 * @Author: ZhengWeihao
 * @Date: 2018/8/17
 * @Time: 10:21
 */
public interface LoopExecuteBusiness {

    /**
     * 执行函数
     */
    void execute();

    /**
     * 停止判断
     */
    boolean isStop();

    /**
     * 最终执行
     */
    void finallyExecute();
}
