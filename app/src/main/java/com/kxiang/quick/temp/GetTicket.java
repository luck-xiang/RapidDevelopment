package com.kxiang.quick.temp;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/21 15:15
 */

public class GetTicket implements Runnable {
    //ticket是票数、isNotRead的设置是为了下面对于统计的输出只输出一次
    private int ticket = 2000;
    private boolean isNotRead = true;
    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;
    private int count4 = 0;

    public void run() {
        while (this.ticket > 0) {
            //临界区开始，所谓的临界区就是仅能有一个线程访问的部分
            synchronized (this) {
                if (this.ticket > 0) {
                    //对票的操作
                    this.ticket--;
                    LogUtils.toE("票" + (2000 - this.ticket) + "被"
                            + Thread.currentThread().getName() + "买走，当前票数剩余："
                            + this.ticket);
                    //Thread.currentThread().getName()取当前线程的名字
                    switch (Thread.currentThread().getName()) {
                        case "线程1":
                            this.count1++;
                            break;
                        case "线程2":
                            this.count2++;
                            break;
                        case "线程3":
                            this.count3++;
                            break;
                        case "线程4":
                            this.count4++;
                            break;
                    }
                }
                else {
                    //这4个线程无论怎么样都会经过这里的，所以为了只输出一次，必须设置一个布尔值
                    //这个isNotRead某种程度上也是一个信号量
                    if (isNotRead) {
                        LogUtils.toE("^_^票已卖光，明天请早，都各自散吧！（杀死所有进程）");
                        LogUtils.toE("=========得票统计=========");
                        LogUtils.toE("线程1：" + count1 + "张");
                        LogUtils.toE("线程2：" + count2 + "张");
                        LogUtils.toE("线程3：" + count3 + "张");
                        LogUtils.toE("线程4：" + count4 + "张");
                        isNotRead = false;
                    }
                    //这段与Thread.currentThread.stop()等价，eclipse在JDK1.7中推荐这样写
                    Thread.yield();
                }
            }
            //临界区结束
        }
    }
}
