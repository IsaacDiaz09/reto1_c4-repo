package com.usa.ciclo4.reto1.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties("confirmPassword")
@Table(name = "users")
public class User implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7233856453730961959L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Email(message = "El email proporcionado no es valido")
	@NotBlank(message = "El email es requerido")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message = "La contraseña es requerida")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	@Column(length = 60)
	private String password;
	
	@NotBlank(message = "El nombre es requerido")
	@Size(min = 3, max = 60, message = "Su nombre debe tener entre 3 y 80 caracteres")
	@Column(length = 80)
	private String name;

	@NotBlank(message = "Debe confirmar su contraseña")
	@Column(length = 60)
	private String confirmPassword;

	/**
	 * Constructor
	 * 
	 * @param email
	 * @param name
	 * @param password
	 */
	public User(String email, String password, String name, String confirmPassword) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

}
