package com.example.myspring.service;

import java.util.List;

import com.example.myspring.domain.Bullet;

public interface WeaponManager {
	
	void addBullet(Bullet bullet);
	List<Bullet> getAllBullets();
	void deleteBullet(Bullet bullet);
	Bullet findBulletByPin(Integer id);
}
