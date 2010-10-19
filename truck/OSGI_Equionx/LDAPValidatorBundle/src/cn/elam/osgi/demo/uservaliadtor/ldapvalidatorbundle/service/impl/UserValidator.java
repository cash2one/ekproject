package cn.elam.osgi.demo.uservaliadtor.ldapvalidatorbundle.service.impl;

import cn.elam.osgi.demo.service.user.Validator;

public class UserValidator implements Validator {

	@Override
	public boolean validator(String userName, String password) {
		System.out.println("ldapvalidatorbundle.validator()....!");
		return true;
	}

}
