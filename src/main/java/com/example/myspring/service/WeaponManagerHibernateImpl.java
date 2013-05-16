package com.example.myspring.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.myspring.domain.Bullet;

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
	public void addBullet(Bullet bullet) {
		bullet.setId(null);
		sessionFactory.getCurrentSession().persist(bullet);
	}
	
	@Override
	public void deleteBullet(Bullet bullet) {
		bullet = (Bullet) sessionFactory.getCurrentSession().get(Bullet.class, bullet.getId());
		sessionFactory.getCurrentSession().delete(bullet);
		sessionFactory.getCurrentSession().flush();
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


}
