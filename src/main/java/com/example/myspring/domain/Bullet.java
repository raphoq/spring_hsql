package com.example.myspring.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.example.myspring.domain.Weapon;

@Entity
@NamedQueries({
		@NamedQuery(name = "bullet.all", query = "Select b from Bullet b"),
		@NamedQuery(name = "bullet.byPin", query = "Select b from Bullet b where b.pin = :pin"),
		@NamedQuery(name = "bullet.byId", query = "Select b from Bullet b where b.id = :id")
		//SELECT model FROM Weapon WHERE id IN (SELECT weapons_id FROM BULLET_WEAPON WHERE bullet_id = 31)
})
public class Bullet {

	private Long id;
	private String name;
	private Integer pin;
	
	private List<Weapon> weapons = new ArrayList<Weapon>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	// Be careful here, both with lazy and eager fetch type
		@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		public List<Weapon> getWeapons() {
			return weapons;
		}
		public void setWeapons(List<Weapon> weapons) {
			this.weapons = weapons;
		}

}
