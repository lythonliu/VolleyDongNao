package com.example.administrator.volleydongnao.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class ThreadPoolManager {
    private static final String TAG ="ThreadPoolManager" ;
    private static  ThreadPoolManager instance=new ThreadPoolManager();

    private LinkedBlockingQueue<Future<?>> linkedBlockingQueue =new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;
    public static ThreadPoolManager getInstance() {
        return instance;
    }
    private ThreadPoolManager()
    {
        threadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        threadPoolExecutor.execute(runnable);
    }

    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                FutureTask futureTask=null;
                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.i(TAG,"等待队列     "+ linkedBlockingQueue.size());
                    futureTask= (FutureTask) linkedBlockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(futureTask!=null)
                {
                    threadPoolExecutor.execute(futureTask);
                }
                Log.i(TAG,"线程池大小      "+threadPoolExecutor.getPoolSize());
            }
        }
    };
    public <T> void execute(FutureTask<T> futureTask) throws InterruptedException {
        linkedBlockingQueue.put(futureTask);
    }

    private RejectedExecutionHandler rejectedExecutionHandler =new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                linkedBlockingQueue.put(new FutureTask<Object>(runnable,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
