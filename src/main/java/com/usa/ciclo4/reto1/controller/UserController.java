package com.usa.ciclo4.reto1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.usa.ciclo4.reto1.model.User;
import com.usa.ciclo4.reto1.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	/**
	 * Inyeccion de dependencia requerida
	 */
	@Autowired
	private UserServiceImpl userService;

	/**
	 * Ruta de inicio, principal
	 * 
	 * @param model
	 * @return String path
	 */
	@GetMapping({ "/", "/login" })
	public String formLogin(Model model) {
		/**
		 * Verifica que el usuario no haya inicado sesion para que le envie al
		 * login,sino, directamente dentro a la aplicacion
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		log.info("El usuario ya esta autenticado, redirigiendo a login_success...");
		return "redirect:/login_success";

	}

	/**
	 * Presenta el formulario de registro de usuario
	 * 
	 * @param model
	 * @return String path
	 */
	@GetMapping("/register")
	public String formRegistro(Model model) {
		model.addAttribute("user", new User());
		log.info("Mostrando la pagina de registro de nuevo usuario");
		return "register";
	}

	/**
	 * Recibe un usuario a registrar del formulario y lo valida, si es exitoso lo
	 * guarda
	 * 
	 * @param user
	 * @param result
	 * @param model
	 * @return String path
	 */
	@PostMapping("/register")
	public String login(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			log.info("No se pudo registrar el usuario, número de errores de validacion: " + result.getErrorCount());
			return "register";
		} else {
			try {
				log.info("Se ha registrado un nuevo usuario exitosamente");
				userService.createUser(user);
			} catch (Exception ex) {
				model.addAttribute("user", user);
				model.addAttribute("validationError", ex.getMessage());
				log.info("No se pudo registrar el usuario, el email ya está en uso o las contraseñas no coinciden");
				return "register";
			}
		}
		return "redirect:/register_success";
	}

	/**
	 * Regresa la vista de registro exitoso
	 * 
	 * @return String path
	 */
	@GetMapping("/register_success")
	public String registroExitoso() {
		log.info("Se muestra la vista de registro exitoso");
		return "register_success";
	}

	/**
	 * Regresa la vista de login exitoso
	 * 
	 * @return String path
	 */
	@GetMapping("/login_success")
	public String loginExitoso() {
		log.info("Se muestra la vista de login exitoso");
		return "login_success";
	}
}
