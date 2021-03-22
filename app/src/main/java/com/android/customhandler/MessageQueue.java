package com.android.customhandler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: CustomHandler
 * @Package: com.android.customhandler
 * @ClassName: MessageQueue
 * @Author: 1984629668@qq.com
 * @CreateDate: 2021/3/22 10:50
 */
public class MessageQueue {
    //互斥锁
    private Lock mLock;
    private Condition mNotEmpty;
    private Condition mNotFull;

    private Message[] mItems;
    int putIndex = 0;
    int takeIndex = 0;
    int count = 0;

    public MessageQueue() {
        mItems = new Message[50];
        mLock = new ReentrantLock();
        mNotEmpty = mLock.newCondition();
        mNotFull = mLock.newCondition();
    }


    /**
     * 入队 生产者
     *
     * @param msg 消息
     */
    public void enqueueMessage(Message msg) {
        try {
            mLock.lock();
            //如果满了，锁死不让进入
            while (count == mItems.length) {//为了多个子线程唤醒，多个子线程可能存在堵塞
                try {
                    //等待唤醒
                    mNotEmpty.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mItems[putIndex] = msg;
            putIndex = (++putIndex == mItems.length) ? 0 : putIndex;
            count++;
            //加入消息唤醒出队
            mNotFull.signalAll();
        } finally {
            mLock.unlock();
        }
    }


    /**
     * 出队 消费者
     *
     * @return
     */
    public Message next() {
        try {
            mLock.lock();
            while (count == 0) {
                try {
                    mNotFull.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Message msg = mItems[takeIndex];
            takeIndex = (++takeIndex == mItems.length) ? 0 : takeIndex;
            count--;
            //出去消息唤醒入队
            mNotEmpty.signalAll();
            return msg;
        } finally {
            mLock.unlock();
        }
    }

}
