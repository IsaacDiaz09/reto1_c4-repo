package com.usa.ciclo4.reto1.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usa.ciclo4.reto1.model.User;
import com.usa.ciclo4.reto1.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	@Override
	public boolean passwordMatch(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Las contraseñas no coinciden");
		}
		return true;
	}

	@Override
	public boolean isEmailNotInUse(User user) throws Exception {
		Optional<User> userByEmail = repo.buscarPorEmail(user.getEmail());
		if (userByEmail.isPresent()) {
			throw new Exception("El email proporcionado ya se encuentra en uso");
		}

		return true;
	}

	@Override
	public void createUser(User user) throws Exception {
		if (isValidEmail(user) && isValidPassword(user)) {
			if (passwordMatch(user) && isEmailNotInUse(user)) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
				String encryptedPassword = encoder.encode(user.getPassword());
				user.setPassword(encryptedPassword);
				user.setConfirmPassword(user.getPassword());

				repo.guardarUsuario(user);
			}
		}
	}

	@Override
	public boolean isValidEmail(User user) {
		return (!user.getEmail().trim().equals("") && !Objects.isNull(user.getEmail()));
	}

	@Override
	public boolean isValidPassword(User user) {
		return (!user.getPassword().trim().equals("") && !Objects.isNull(user.getPassword()));
	}

	@Override
	public List<User> listUsers() {
		return repo.traerUsuarios();
	}

	@Override
	public Map<String, Object> verificaUsuario(String email, String password) {
		Optional<User> user = repo.buscarPorEmail(email);
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if (user.isPresent()) {
			map.put("id", user.get().getId());
			map.put("email", email);
			map.put("password", password);
			map.put("name", user.get().getName());
		} else {
			map.put("id", null);
			map.put("email", email);
			map.put("password", password);
			map.put("name", "NO DEFINIDO");
		}

		return map;
	}

}
