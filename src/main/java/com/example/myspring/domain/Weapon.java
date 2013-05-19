package com.example.myspring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "weapon.all", query = "Select w from Weapon w"),
		@NamedQuery(name = "weapon.byId", query = "Select w from Weapon w where w.id = :id"),
		@NamedQuery(name = "weapon.byPin", query = "Select w from Weapon w where w.pin = :pin")
})
public class Weapon {

	private Long id;
	private String model;
	private String make;
	private Integer pin;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

}
