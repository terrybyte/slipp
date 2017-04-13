package net.slipp.web;

import javax.servlet.http.HttpSession;

import net.slipp.domain.User;

public class HttpSessionUtils {
	
	public static final String USER_SESSION_KEY = "session";
	
	public static boolean isLogin(HttpSession httpSession) {
		Object session = httpSession.getAttribute(USER_SESSION_KEY);
		if (session == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession httpSession) {
		if (!isLogin(httpSession)) {
			return null;
		}
		
		return (User) httpSession.getAttribute(USER_SESSION_KEY);
	}
}
