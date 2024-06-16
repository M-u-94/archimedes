package la.archimedes.jse.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件 {@link java.util.concurrent.locks.Condition},用于实现等待和通知模式
 * <p>
 * 作用：
 * <li>
 * 1、将一个信号量和一个锁实例关联，信号量的休眠和唤醒都需要获取这个锁（默认Object的wait需要获取的是对象的Monitor，要用synchronized才能成为对象monitor的owner）
 * <li>
 * 2、把锁和竞争线程的同步对象（condition）关联起来
 * <p>
 * 参考：<a href="https://www.cnblogs.com/jiading/articles/12590588.html">Condition详解</a>
 */
@Slf4j
public class Condition01 {
    //可重入锁：公平锁
    //entrant是enter的名词
    ReentrantLock lock = new ReentrantLock(true);

    /**
     * 线程等待指定的时间
     */
    @Test
    public void testThreadWait() throws InterruptedException {
        Condition condition = lock.newCondition();
        //需要先获取信号关联的lock，因为调用这个方法时awaitNanos会自动释放lock
        if (lock.tryLock()) {
            try {
                log.info("线程{}开始休眠", Thread.currentThread().getName());
                //方法返回还需要一个条件，就是能重新获取到关联的这个lock
                //因此最后还是需要解锁
                //如果在休眠期间被提前唤醒，返回值会是正数。看api示例，会在try内部使用循环
                condition.awaitNanos(TimeUnit.SECONDS.toNanos(3));
                log.info("休眠结束");
                log.info("当前线程还持有锁吗？{}", lock.isHeldByCurrentThread());
            } finally {
                lock.unlock();
            }
        }
        log.info("当前线程还持有锁吗？{}", lock.isHeldByCurrentThread());
    }

    //多个线程交替打印正整数
    volatile int cnt = 0;
    volatile boolean terminated = false;
    //任务数量
    final int taskNbr = 3;

    @Test
    public void multiThreadPrintInSequence() throws InterruptedException {
        Condition condition = lock.newCondition();
        CountDownLatch latch = new CountDownLatch(3);
        PrintTask task0 = new PrintTask("任务0", 0, condition, latch);
        PrintTask task1 = new PrintTask("任务1", 1, condition, latch);
        PrintTask task2 = new PrintTask("任务2", 2, condition, latch);
        new Thread(task0).start();
        new Thread(task1).start();
        new Thread(task2).start();
        log.info("所有线程已经启动，等待结果");
        latch.await();
        log.info("执行结束，当前的计数器是{}，终止状态是:{}",cnt,terminated);
    }

    @Data
    @AllArgsConstructor
    class PrintTask implements Runnable {
        private String name;
        //任务编号
        private int nbr;
        private Condition condition;
        CountDownLatch latch;

        @Override
        public void run() {
            lock.lock();
            try {
                for (; ; ) {
                    checkTerminateCondition();
                    if (terminated) {
                        return;
                    }
                    if (cnt % taskNbr == nbr) {
                        log.info("线程{}打印数字:{}", name, cnt);
                        cnt++;
                    }else{
                        log.warn("线程{}不能打印数字{}，需要休眠",name,cnt);
                    }
                    //唤醒其他线程，但是需要自己释放了lock其他线程才能真正起来（竞争到lock的那个线程）
                    condition.signalAll();
                    try {
                        //唤醒所有线程，因为是公平锁，应该会按照等待锁的顺序持有锁把？？？
                        //休眠并释放锁
                        //被唤醒后语句返回，需要获取锁
                        condition.await();
                    } catch (InterruptedException e) {
                        log.warn("线程{}被中断，退出...", name);
                        Thread.currentThread().interrupt();
                        return;
                    }

                }
            } finally {
                log.info("任务{}已结束",this.nbr);
                //需要唤醒最后那个任务
                condition.signalAll();
                lock.unlock();
                latch.countDown();
            }
        }


        private boolean checkTerminateCondition() {
            if (cnt >= 20) {
                terminated = true;
            }
            return terminated;
        }

    }


}
