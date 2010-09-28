package cn.elam.rmi.adapter;

public class RemoteConfig {

	String url = "rmi://";
	int port = -1;

	public RemoteConfig(String url, int port) {
		setUrl(url);
		setPort(port);
	}
	 
	void setUrl(String url) {
		if(url.indexOf(this.url)<0)
		   this.url += url;
		else
		   this.url = url;
	}


	public void setPort(int port) {
		this.port = port;
	}

	
	public String toRemotUrl(){
		return url.trim()+":"+port+"/";
	}
	
}
