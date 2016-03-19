package com.example.cheng.myfifthapplication.Hleper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cheng on 16-2-9.
 */
public class PriorityThreadFactory implements ThreadFactory{
	private String mName;
	private int mPriority;
	private final AtomicInteger mNumber = new AtomicInteger();

	public PriorityThreadFactory(String name, int priority) {
		mName = name;// 线程池的名称
		mPriority = priority;//线程池的优先级
	}
	@Override
	public Thread newThread(Runnable r){
		return new Thread(r, mName +"-"+mNumber.getAndIncrement()){
			@Override
			public void run(){
				//设置线程优先级
				setPriority(mPriority);
				super.run();
			}
		};
	}
}
