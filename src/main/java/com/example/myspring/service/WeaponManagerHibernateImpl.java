package com.example.myspring.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.myspring.domain.Weapon;

@Component
@Transactional
public class WeaponManagerHibernateImpl implements WeaponManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addWeapon(Weapon weapon) {
		sessionFactory.getCurrentSession().persist(weapon);
	}
	
	@Override
	public void deleteWeapon(Weapon weapon) {
		weapon = (Weapon) sessionFactory.getCurrentSession().get(Weapon.class, weapon.getId());
		
		sessionFactory.getCurrentSession().delete(weapon);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Weapon> getAllWeapons() {
		return sessionFactory.getCurrentSession().getNamedQuery("weapon.all").list();
	}

	@Override
	public Weapon findWeaponById(Integer id) {
		return (Weapon) sessionFactory.getCurrentSession().getNamedQuery("weapon.byId").setInteger("id", id).uniqueResult();
	}


}
