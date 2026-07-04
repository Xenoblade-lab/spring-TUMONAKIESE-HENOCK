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

import edu.upc.models.dtos.AdherentDto;
import edu.upc.services.GenreService;
import edu.upc.services.AdherentService;
import edu.upc.utils.Mapper;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adherents")
public class AdherentController {

	@Autowired
	private AdherentService service;

	@Autowired
	private GenreService genreService;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
		model.addAttribute("rows", service.search(keyword));
		model.addAttribute("keyword", keyword == null ? "" : keyword);
		return "adherents-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new AdherentDto(), null);
		return "adherents-form";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable("id") long id, Model model) {
		prepareForm(model, mapper.toAdherentDto(service.getById(id)), id);
		return "adherents-form";
	}

	@PostMapping("/save")
	public String save(@RequestParam(value = "id", required = false) Long id,
			@Valid @ModelAttribute("form") AdherentDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form, id);
			return "adherents-form";
		}
		if (id == null) {
			service.create(form, result);
		} else {
			service.update(id, form, result);
		}
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.saved", null, LocaleContextHolder.getLocale()));
		return "redirect:/adherents";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/adherents";
	}

	private void prepareForm(Model model, AdherentDto form, Long id) {
		model.addAttribute("form", form);
		model.addAttribute("id", id);
		model.addAttribute("genres", genreService.get());
	}

}
