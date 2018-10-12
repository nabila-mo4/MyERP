package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

public abstract class ConsumerTestCase {
	
	static {
        SpringRegistry.init();
    }
	
	private static final DaoProxy DAO_PROXY = SpringRegistry.getDaoProxy();
	

    
    public ConsumerTestCase() {
    }
    
   
    public static DaoProxy getDaoProxy() {
		return DAO_PROXY;
	}
}
