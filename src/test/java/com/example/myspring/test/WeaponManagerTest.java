package com.example.myspring.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.myspring.domain.Weapon;
import com.example.myspring.service.WeaponManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class WeaponManagerTest {

	@Autowired
	WeaponManager weaponManager;

	private final Integer[] ids = {1,2,3,4};
	private final String[] names = {"MP5", "Ak 47", "Desert Eagle"};
	private final String[] producer = {"Germany", "ZSSR", "USA"};

	
	
	@Test
	public void addWeaponCheck() {

		List<Weapon> retrievedAllWeapons = weaponManager.getAllWeapons();

		// If there is a client with PIN_1 delete it
		for (Weapon weapon : retrievedAllWeapons) {
			if (weapon.getId() == ids[0]) {
				weaponManager.deleteWeapon(weapon);
			}
		}

		Weapon weapon = new Weapon();
		weapon.setId(ids[0]);
		weapon.setName(names[0]);
		weapon.setProducer(producer[0]);

		// Pin is Unique
		weaponManager.addWeapon(weapon);

		Weapon retrievedWeapons = weaponManager.findWeaponById(ids[0]);

		assertEquals(ids[0], retrievedWeapons.getId());
		assertEquals(names[0], retrievedWeapons.getName());
		// ... check other properties here
	}

}
