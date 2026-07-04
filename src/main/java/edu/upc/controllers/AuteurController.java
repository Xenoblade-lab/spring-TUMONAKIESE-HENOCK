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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.upc.models.dtos.AuteurDto;
import edu.upc.services.CategorieService;
import edu.upc.services.AuteurService;
import edu.upc.utils.Mapper;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auteurs")
public class AuteurController {

	@Autowired
	private AuteurService service;

	@Autowired
	private CategorieService categorieService;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
		model.addAttribute("rows", service.search(keyword));
		model.addAttribute("keyword", keyword == null ? "" : keyword);
		return "auteurs-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new AuteurDto(), null);
		return "auteurs-form";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable("id") long id, Model model) {
		prepareForm(model, mapper.toAuteurDto(service.getById(id)), id);
		return "auteurs-form";
	}

	@PostMapping("/save")
	public String save(@RequestParam(value = "id", required = false) Long id,
			@Valid @ModelAttribute("form") AuteurDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form, id);
			return "auteurs-form";
		}
		if (id == null) {
			service.create(form, result);
		} else {
			service.update(id, form, result);
		}
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.saved", null, LocaleContextHolder.getLocale()));
		return "redirect:/auteurs";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/auteurs";
	}

	private void prepareForm(Model model, AuteurDto form, Long id) {
		model.addAttribute("form", form);
		model.addAttribute("id", id);
		model.addAttribute("categories", categorieService.get());
	}

}
