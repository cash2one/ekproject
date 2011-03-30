package cn.elam.util.services;

import java.io.File;

import cn.elam.util.monitor.FileWatchdog;

/**
 * 
 * 用例 当文件有修改时，执行重新加载
 * @author Ethan.Lam  2011-3-30
 *
 */
public class BlacklistService {
	private File configFile = new File("d:/blacklist.txt");

	public void init() throws Exception {
		loadConfig();
		ConfigWatchDog dog = new ConfigWatchDog();
		dog.setName("daemon_demo_config_watchdog");// a
		dog.addFile(configFile);// b
		dog.start();// c
	}

	public void loadConfig() {
		try {
			Thread.sleep(1 * 1000);// d
			System.out.println("加载黑名单");
		} catch (InterruptedException ex) {
			System.out.println("加载配置文件失败！");
		}
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	
	
	/**
	 * 
	 * 内嵌类
	 * @author Ethan.Lam  2011-3-30
	 *
	 */
	private class ConfigWatchDog extends FileWatchdog {
		@Override
		protected void doOnChange(File file) {
			System.out.println("文件" + file.getName() + "发生改变，重新加载");
			loadConfig();
		}

	}

	public static void main(String[] args) throws Exception {
		BlacklistService service = new BlacklistService();
		service.init();

		Thread.sleep(60 * 60 * 1000);// e
	}
}
