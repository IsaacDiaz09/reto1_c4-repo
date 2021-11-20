package com.usa.ciclo4.reto1.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.usa.ciclo4.reto1.model.User;
import com.usa.ciclo4.reto1.repository.crud.UserCrudRepository;

@Repository
public class UserRepository {
	@Autowired
	private UserCrudRepository repo;

	@Transactional(readOnly = true)
	public List<User> traerUsuarios() {
		return (List<User>) repo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<User> buscarPorEmail(String email) {
		return repo.findByEmail(email);
	}

	@Transactional(readOnly = true)
	public boolean usuarioExiste(String email, String password) {
		return repo.existsByEmailAndPassword(email, password);
	}

	@Transactional
	public void guardarUsuario(User user) {
		if (Objects.isNull(user.getConfirmPassword())) {
			user.setConfirmPassword(user.getPassword());
		}
		repo.save(user);
	}
}
