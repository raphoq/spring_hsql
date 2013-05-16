package com.example.myspring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "bullet.all", query = "Select b from Bullet b"),
		@NamedQuery(name = "bullet.byPin", query = "Select b from Bullet b where b.pin = :pin")
})
public class Bullet {

	private Integer id;
	private String name;
	private Integer pin;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = true, nullable = false)
	public Integer getPin() {
		return pin;
	}
	
	public void setPin(Integer pin) {
		this.pin = pin;
	}

}
