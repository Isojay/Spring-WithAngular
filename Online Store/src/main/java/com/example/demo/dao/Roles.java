package com.example.demo.dao;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Roles")
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Role_id")
	private int id;
	
	@Column(nullable = false, unique = true)
	@NotEmpty
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	List<Users> users;
	
	public Roles() {
		// TODO Auto-generated constructor stub
	}
	


	public Roles(@NotEmpty String name, List<Users> users) {
		super();
		this.name = name;
		this.users = users;
	}

	

	public List<Users> getUsers() {
		return users;
	}



	public void setUsers(List<Users> users) {
		this.users = users;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "Roles [id=" + id + ", name=" + name + ", users=" + users + "]";
	}
	
	
}
