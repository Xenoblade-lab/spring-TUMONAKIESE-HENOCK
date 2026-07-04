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

import edu.upc.models.dtos.GenreDto;
import edu.upc.services.GenreService;
import edu.upc.utils.Mapper;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/genres")
public class GenreController {

	@Autowired
	private GenreService service;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "genres-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("form", new GenreDto());
		return "genres-form";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("form", mapper.toGenreDto(service.getById(id)));
		model.addAttribute("id", id);
		return "genres-form";
	}

	@PostMapping("/save")
	public String save(@RequestParam(value = "id", required = false) Long id,
			@Valid @ModelAttribute("form") GenreDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "genres-form";
		}
		if (id == null) {
			service.create(form, result);
		} else {
			service.update(id, form, result);
		}
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.saved", null, LocaleContextHolder.getLocale()));
		return "redirect:/genres";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/genres";
	}

}
