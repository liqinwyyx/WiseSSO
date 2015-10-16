package com.tencent.sso.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencent.sso.util.VerifyCodeUtils;

/**
 * 验证码
 */
@Controller
public class VerifyCodeController extends BaseController {

	@RequestMapping("/verify.do")
	public void verify(HttpServletRequest request, HttpServletResponse response) {
		try {
			String checkCode = VerifyCodeUtils.outputVerifyImage(85, 35, response.getOutputStream(), 4);
			request.getSession().setAttribute("AUTH_CODE", checkCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}