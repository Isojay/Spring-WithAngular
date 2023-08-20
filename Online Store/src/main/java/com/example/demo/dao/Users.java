package com.example.demo.dao;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_id")
	private int id;
	
	@NotEmpty
	@Column(nullable = false)
	
	private String firstname;
	private String lastname;
	
	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email(message = "{error.invalid_email}")
	private String email;
	
	@Column(nullable = false)
	@NotEmpty	
	private String password;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name ="user_role",
			joinColumns = {@JoinColumn(name="User_id" ,referencedColumnName = "User_id")},
			inverseJoinColumns = {@JoinColumn(name="Role_id" ,referencedColumnName = "Role_id")}			
			)
	List<Roles> roles;
	

	public Users() {
		super();
	}


	public Users(@NotEmpty String firstname, String lastname,
			@NotEmpty @Email(message = "{error.invalid_email}") String email, @NotEmpty String password,
			List<Roles> roles) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	
	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Roles> getRoles() {
		return roles;
	}


	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	

	@Override
	public String toString() {
		return "Users [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password="
				+ password + ", roles=" + roles + "]";
	}


	public Users(Users users) {
		super();
		this.firstname = users.getFirstname();
		this.lastname = users.getLastname();
		this.email = users.getEmail();
		this.password = users.getPassword();
		this.roles = users.getRoles();
	}
	
	
	
	
	
}
