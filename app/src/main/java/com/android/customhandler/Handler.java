package com.android.customhandler;

/**
 * @ProjectName: CustomHandler
 * @Package: com.android.customhandler
 * @ClassName: Handler
 * @Author: 1984629668@qq.com
 * @CreateDate: 2021/3/22 10:47
 */
public class Handler {
    private MessageQueue mQueue;
    private Looper mLooper;

    public Handler() {
        mLooper = Looper.getLooper();
        mQueue = mLooper.mQueue;
    }

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    public void sendMessage(Message msg) {
        //消息信息持有handler对象，消息蕴含发送者信息
        msg.mTarget = this;
        //把消息添加到消息队列
        mQueue.enqueueMessage(msg);
    }

    /**
     * 分发消息
     *
     * @param msg 消息
     */
    public void dispatchMessage(Message msg) {
        handlerMessage(msg);
    }

    public void handlerMessage(Message msg) {

    }
}
