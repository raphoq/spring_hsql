package com.example.myspring.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.myspring.domain.Bullet;
import com.example.myspring.domain.Weapon;

@Component
@Transactional
public class WeaponManagerHibernateImpl implements WeaponManager {

	@Autowired
	public SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addBullet(Bullet bullet) {
		bullet.setId(null);
		sessionFactory.getCurrentSession().persist(bullet);
	}
	
	@Override
	public Bullet deleteBullet(Bullet bullet) {
		bullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class, bullet.getId());
		sessionFactory.getCurrentSession().delete(bullet);
		sessionFactory.getCurrentSession().flush();
		return bullet;
	}
	
	@Override
	@Cascade(CascadeType.DELETE)
	public void deleteBulletCascade(Bullet bullet) {
		bullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class, bullet.getId());
		
//		List<Weapon> weapons = getLoadedWeapons(bullet);
//		
//		for (int i = 0; i < weapons.size(); i++) {
//			unloadBullet(bullet, weapons.get(i));
//			deleteWeapon(weapons.get(i));
//		}
		
		sessionFactory.getCurrentSession().delete(bullet);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void repairWeapons(Bullet bullet) {
		bullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class, bullet.getId());
		
		List<Weapon> weapons = getLoadedWeapons(bullet);
		
		for (int i = 0; i < weapons.size(); i++) {
			Weapon one_weapon = weapons.get(i);
			one_weapon.setModel("REPEAIRED " + one_weapon.getModel());
			sessionFactory.getCurrentSession().update(one_weapon);
		}
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<Bullet> getAllBullets() {
		return sessionFactory.getCurrentSession().getNamedQuery("bullet.all").list();
	}

	@Override
	public Bullet findBulletByPin(Integer pin) {
		return (Bullet) sessionFactory.getCurrentSession().getNamedQuery("bullet.byPin").setInteger("pin", pin).uniqueResult();
	}

	@Override
	public void updateBullet(Bullet bullet) {
		Bullet newBullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class, bullet.getId());
		newBullet.setName(bullet.getName());
		sessionFactory.getCurrentSession().update(newBullet);
	}

	@Override
	public Bullet findBulletById(Long id) {
		return (Bullet) sessionFactory.getCurrentSession().getNamedQuery("bullet.byId").setLong("id", id).uniqueResult();
	}

	@Override
	public Long addWeapon(Weapon weapon) {
		weapon.setId(null);
		sessionFactory.getCurrentSession().persist(weapon);
		return weapon.getId();
	}
	
	@Override
	public void updateWeapon(Weapon weapon) {
		Weapon newWeapon = (Weapon) sessionFactory.getCurrentSession().get(Weapon.class, weapon.getId());
		newWeapon.setModel(weapon.getModel());
		newWeapon.setMake(weapon.getMake());
		sessionFactory.getCurrentSession().update(newWeapon);
	}
	
	@Override
	public Weapon deleteWeapon(Weapon weapon) {
		weapon = (Weapon) sessionFactory.getCurrentSession().get(Weapon.class, weapon.getId());
		sessionFactory.getCurrentSession().delete(weapon);
		sessionFactory.getCurrentSession().flush();
		return weapon;
	}

	@Override
	public List<Weapon> getAvailableWeapons() {
		return sessionFactory.getCurrentSession().getNamedQuery("weapon.all").list();
	}

	@Override
	public Weapon findWeaponById(Long id) {
		return (Weapon) sessionFactory.getCurrentSession().getNamedQuery("weapon.byId").setLong("id", id).uniqueResult();
	  	
	}

	@Override
	public Weapon findWeaponByPin(Integer pin) {
		return (Weapon) sessionFactory.getCurrentSession().getNamedQuery("weapon.byPin").setInteger("pin", pin).uniqueResult();
	}
	
	@Override
	public List<Weapon> getLoadedWeapons(Bullet bullet) {
		bullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class, bullet.getId());
		
		// lazy loading here - try this code without (shallow) copying
		List<Weapon> weapons = new ArrayList<Weapon>(bullet.getWeapons());
		return weapons;
	}
	
	@Override
	public void loadBullet(Long bulletId, Long weaponId) {
		Bullet bullet = (Bullet) sessionFactory.getCurrentSession().get(
				Bullet.class, bulletId);
		Weapon weapon = (Weapon) sessionFactory.getCurrentSession()
				.get(Weapon.class, weaponId);
		bullet.getWeapons().add(weapon);
	}
	
	@Override
	public void unloadBullet(Bullet bullet, Weapon weapon) {
		bullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class,
				bullet.getId());
		weapon = (Weapon) sessionFactory.getCurrentSession().get(Weapon.class,
				weapon.getId());

		Weapon toRemove = null;
		
		// lazy loading here (person.getCars)
		for (Weapon aWeapon : bullet.getWeapons())
			if (aWeapon.getId().compareTo(weapon.getId()) == 0) {
				toRemove = aWeapon;
				break;
			}

		if (toRemove != null)
			bullet.getWeapons().remove(toRemove);
		
	}



}
