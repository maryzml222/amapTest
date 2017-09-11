package com.example.mylibrary.component.thread;

public class Blocker {

	private volatile boolean mCondition;

	public Blocker() {

		mCondition = true;
	}

	/**
	 * 阻塞
	 */
	public void block() {

		synchronized (this) {
			while (mCondition) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * true: 正常阻塞直到解阻塞
	 * false: 超时异常后借阻塞
	 *
	 * @param timeout
	 * @return
	 */
	public boolean block(long timeout) {

		if (timeout != 0) {
			synchronized (this) {
				long now = System.currentTimeMillis();
				long end = now + timeout;
				while (mCondition && now < end) {
					try {
						this.wait(end - now);
					} catch (InterruptedException e) {
					}
					now = System.currentTimeMillis();
				}
				return !mCondition;
			}
		} else {
			this.block();
			return true;
		}
	}

	/**
	 * 重置当前对象，然后可以执行 bloc/unblock
	 */
	public void reset() {

		synchronized (this) {
			mCondition = true;
		}
	}

	/**
	 * 解阻塞
	 */
	public void unblock() {

		synchronized (this) {
			boolean old = mCondition;
			mCondition = false;
			if (old) {
				this.notifyAll();
			}
		}
	}
}
