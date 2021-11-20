package com.usa.ciclo4.reto1.repository.crud;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usa.ciclo4.reto1.model.User;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Integer> {

	/**
	 * Verifica si un usuario ya tiene en uso un email proporcionado
	 * @param email
	 * @return Optional<User>
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Busca un usuario por su email y contraseña
	 * @param email
	 * @param password
	 * @return boolean -> existe usuafrio o no existe
	 */
	boolean existsByEmailAndPassword(String email, String password);

}
