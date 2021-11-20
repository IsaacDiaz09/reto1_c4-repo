package com.usa.ciclo4.reto1.rest_controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usa.ciclo4.reto1.model.User;
import com.usa.ciclo4.reto1.repository.UserRepository;
import com.usa.ciclo4.reto1.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserRestController {

	@Autowired
	private UserServiceImpl service;

	@Autowired
	private UserRepository repo;

	@GetMapping("/all")
	private List<User> traerUsuarios() {
		log.info("Entregando lista de usuarios por peticion rest...");
		return service.listUsers();
	}

	@PostMapping("/new")
	@ResponseStatus(HttpStatus.CREATED)
	private void guardarIsuario(@RequestBody User user) {
		log.info("Guardando un nuevo usuario en la DB");
		repo.guardarUsuario(user);
	}

	@GetMapping("/{email}")
	private boolean validaEmail(@PathVariable("email") String email) {
		log.info("Verificando usuario con el email:" + email + " ...");
		return repo.buscarPorEmail(email).isPresent();
	}

	@GetMapping("/{email}/{password}")
	private Map<String, Object> verificarUsuarioExiste(@PathVariable("email") String email,
			@PathVariable("password") String password) {
		log.info("Verificando usuario con el email: " + email + " y contraseña: " + password);
		return service.verificaUsuario(email, password);
	}

}
