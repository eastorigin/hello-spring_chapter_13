package com.ktdsuniversity.edu.hello_spring.common.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ktdsuniversity.edu.hello_spring.common.exceptions.AlreadyUseException;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.FileNotExistsException;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.MakeXlsxFileException;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.PageNotFoundException;
import com.ktdsuniversity.edu.hello_spring.common.exceptions.UserIdentifyNotMatchException;

@ControllerAdvice // Spring Application에서 예외를 일괄처리한다
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(PageNotFoundException.class)
	public String viewPageNotFoundPage() {
		if(logger.isDebugEnabled()) {
			logger.debug("Page를 찾을 수 없습니다");
		}
		
		return "error/404";
	}
	
	@ExceptionHandler(UserIdentifyNotMatchException.class)
	public String viewLoginErrorPage(Model model, UserIdentifyNotMatchException uinme) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("아이디 또는 비밀번호가 일치하지 않습니다");
		}
		
		model.addAttribute("message", uinme.getMessage());
		model.addAttribute("loginMemberVO", uinme.getLoginMemberVO());
		return "member/memberlogin";
	}
	
	@ExceptionHandler({FileNotExistsException.class, MakeXlsxFileException.class})
	public String viewFileErrorPage(Model model, RuntimeException re) {
		if(re instanceof FileNotExistsException) {
			FileNotExistsException fnee = (FileNotExistsException) re;
			model.addAttribute("message", fnee.getMessage());
		}
		else if(re instanceof MakeXlsxFileException) {
			MakeXlsxFileException mxfe = (MakeXlsxFileException) re;
			model.addAttribute("message", mxfe.getMessage());
		}
		return "error/500";
	}
	
	@ExceptionHandler(AlreadyUseException.class)
	public String viewMemberRegisterErrorPage(Model model, AlreadyUseException aue) {
		model.addAttribute("message", aue.getMessage());
		model.addAttribute("memberRegistVO", aue.getMemberRegistVO());
		return "member/memberregist";
	}
}
