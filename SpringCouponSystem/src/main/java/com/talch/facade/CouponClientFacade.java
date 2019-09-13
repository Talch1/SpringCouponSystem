package com.talch.facade;

import com.talch.beans.ClientType;
import com.talch.exeption.LogginEx;

public interface CouponClientFacade {
	public CouponClientFacade login(String name, String password, ClientType type) throws LogginEx;
}
