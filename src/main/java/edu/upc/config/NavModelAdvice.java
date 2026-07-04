package edu.upc.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NavModelAdvice {

	@ModelAttribute("navHome")
	public boolean navHome(HttpServletRequest request) {
		return "/".equals(request.getRequestURI());
	}

	@ModelAttribute("navGenres")
	public boolean navGenres(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/genres");
	}

	@ModelAttribute("navAdherents")
	public boolean navAdherents(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/adherents") && !path.startsWith("/adherents-");
	}

	@ModelAttribute("navLivres")
	public boolean navLivres(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/livres");
	}

	@ModelAttribute("navEmprunts")
	public boolean navEmprunts(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/emprunts");
	}

}
