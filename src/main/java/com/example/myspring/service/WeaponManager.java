package com.example.myspring.service;

import java.util.List;

import com.example.myspring.domain.Bullet;
import com.example.myspring.domain.Weapon;

public interface WeaponManager {
	
	void addBullet(Bullet bullet);
	List<Bullet> getAllBullets();
	Bullet deleteBullet(Bullet bullet);
	void updateBullet(Bullet bullet);
	Bullet findBulletByPin(Integer pin);
	Bullet findBulletById(Long id);

	Long addWeapon(Weapon weapon);
	List<Weapon> getAvailableWeapons();
	Weapon findWeaponById(Long id);
	
	void unloadBullet(Bullet bullet, Weapon weapon);
	List<Weapon> getLoadedWeapons(Bullet bullet);
	void loadBullet(Long bulletId, Long carId);
	
}
