package com.tencent.sso.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础类
 */
public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String redirectTo(String url) {
		StringBuffer rto = new StringBuffer("redirect:");
		rto.append(url);
		return rto.toString();
	}
}
