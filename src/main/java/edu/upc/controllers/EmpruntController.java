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

import edu.upc.models.dtos.EmpruntDto;
import edu.upc.services.LivreService;
import edu.upc.services.AdherentService;
import edu.upc.services.EmpruntService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {

	@Autowired
	private EmpruntService service;

	@Autowired
	private AdherentService adherentService;

	@Autowired
	private LivreService livreService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "emprunts-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new EmpruntDto());
		return "emprunts-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("form") EmpruntDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form);
			return "emprunts-form";
		}
		service.create(form, result);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.emprunt", null, LocaleContextHolder.getLocale()));
		return "redirect:/emprunts";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/emprunts";
	}

	private void prepareForm(Model model, EmpruntDto form) {
		model.addAttribute("form", form);
		model.addAttribute("adherents", adherentService.get());
		model.addAttribute("livres", livreService.get());
	}

}
