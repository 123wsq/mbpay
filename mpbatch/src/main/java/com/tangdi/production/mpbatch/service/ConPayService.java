package com.tangdi.production.mpbatch.service;

/**
 * 
 * 根据代付状态0未确认， 进行确认代付
 * 
 * @author youdd
 */
public interface ConPayService {
	/**
	 * 根据代付状态进行确认代付
	 */
	public void process() throws Exception;

}
