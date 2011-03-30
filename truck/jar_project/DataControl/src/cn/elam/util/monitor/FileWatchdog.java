package cn.elam.util.monitor;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * 守护进程
 * 当检测到文件发生修改后，触发对应的事件
 * @author Ethan.Lam  2011-3-30
 *
 */
public abstract class FileWatchdog extends Thread{
	static final public long DEFAULT_DELAY = 20 * 1000;

	protected HashMap fileList;

	protected long delay = DEFAULT_DELAY;

	boolean warnedAlready = false;

	boolean interrupted = false;

	public static class Entity {
		File file;
		long lastModify;

		Entity(File file, long lastModify) {
			this.file = file;
			this.lastModify = lastModify;
		}
	}

	protected FileWatchdog() {
		fileList = new HashMap();
		setDaemon(true);
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void addFile(File file) {
		fileList.put(file.getAbsolutePath(), new Entity(file, file
				.lastModified()));
	}

	public boolean contains(File file) {
		if (fileList.get(file.getAbsolutePath()) != null)
			return true;
		else
			return false;
	}

	abstract protected void doOnChange(File file);

	
	protected void checkAndConfigure() {
		HashMap map = (HashMap) fileList.clone();
		Iterator it = map.values().iterator();

		while (it.hasNext()) {

			Entity entity = (Entity) it.next();

			boolean fileExists;
			try {
				fileExists = entity.file.exists();
			} catch (SecurityException e) {
				System.err.println("Was not allowed to read check file existance, file:["
								+ entity.file.getAbsolutePath() + "].");
				interrupted = true;
				return;
			}

			if (fileExists) {

				long l = entity.file.lastModified(); // this can also throw a
														// SecurityException
				if (l > entity.lastModify) { // however, if we reached this
												// point this
					entity.lastModify = l; // is very unlikely.
					newThread(entity.file);
				}
			} else {
				System.err.println("[" + entity.file.getAbsolutePath()
						+ "] does not exist.");
			}
		}
	}

	private void newThread(File file) {
		
		class MyThread extends Thread {
			File f;

			public MyThread(File f) {
				this.f = f;
			}

			public void run() {
				doOnChange(f);
			}
		}

		MyThread mt = new MyThread(file);
		mt.start();
	}

	public void run() {
		while (!interrupted) {
			try {
				Thread.currentThread().sleep(delay);
			} catch (InterruptedException e) {
				// no interruption expected
			}
			checkAndConfigure();
		}
	}
}
