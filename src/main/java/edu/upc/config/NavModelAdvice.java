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

	@ModelAttribute("navCategories")
	public boolean navCategories(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/categories");
	}

	@ModelAttribute("navAuteurs")
	public boolean navAuteurs(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/auteurs") && !path.startsWith("/auteurs-");
	}

	@ModelAttribute("navArticles")
	public boolean navArticles(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/articles");
	}

	@ModelAttribute("navCommentaires")
	public boolean navCommentaires(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/commentaires");
	}

}
