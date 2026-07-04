package edu.upc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.upc.models.dtos.CommentaireDto;
import edu.upc.services.ArticleService;
import edu.upc.services.AuteurService;
import edu.upc.services.CommentaireService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/commentaires")
public class CommentaireController {

	@Autowired
	private CommentaireService service;

	@Autowired
	private AuteurService auteurService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "commentaires-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new CommentaireDto());
		return "commentaires-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("form") CommentaireDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form);
			return "commentaires-form";
		}
		service.create(form, result);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.commentaire", null, LocaleContextHolder.getLocale()));
		return "redirect:/commentaires";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/commentaires";
	}

	private void prepareForm(Model model, CommentaireDto form) {
		model.addAttribute("form", form);
		model.addAttribute("auteurs", auteurService.get());
		model.addAttribute("articles", articleService.get());
	}

}
