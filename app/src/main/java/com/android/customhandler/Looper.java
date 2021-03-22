package com.android.customhandler;

/**
 * @ProjectName: CustomHandler
 * @Package: com.android.customhandler
 * @ClassName: Looper
 * @Author: 1984629668@qq.com
 * @CreateDate: 2021/3/22 10:49
 */
public class Looper {
    private static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    public MessageQueue mQueue;

    private Looper() {//一个Looper 有一个队列
        mQueue = new MessageQueue();
    }

    /**
     * 初始化Looper
     */
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("一个线程只能有一个Looper");
        }
        sThreadLocal.set(new Looper());
    }

    public static Looper getLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        Looper looper = getLooper();
        MessageQueue queue = looper.mQueue;
        for (; ; ) {//死循环获取队列中的数据
            Message msg = queue.next();
            if (msg == null) {
                continue;
            }
            //消息持有发送
            msg.mTarget.dispatchMessage(msg);
        }
    }
}
