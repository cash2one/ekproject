package cn.qtone.xxt.csop.services.impl;

public class TestService {

	public String test(String name) {
		System.out.println(name);
		return "test.... " + name;
	}

	public String test(String...params){
		String t ="";
		for(String param:params)
		  t += param;
		return "test Params: "+t;
	}
	
}
