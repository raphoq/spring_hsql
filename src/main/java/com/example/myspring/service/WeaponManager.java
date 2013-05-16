package com.example.myspring.service;

import java.util.List;

import com.example.myspring.domain.Weapon;

public interface WeaponManager {
	
	void addWeapon(Weapon weapon);
	List<Weapon> getAllWeapons();
	void deleteWeapon(Weapon weapon);
	Weapon findWeaponById(Integer id);
}
