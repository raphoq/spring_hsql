package com.example.myspring.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.myspring.domain.Bullet;
import com.example.myspring.domain.Weapon;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class WeaponManagerTest {

	@Autowired
	WeaponManager weaponManager;

	private final Integer[] bullet_pins = {1,2,3,4,5,6};
	private final String[] bullet_names = {"7.62 x 51mm NATO", "5,56 × 45 mm", "9 x 19 mm Parabellum", "12,7 mm NATO", ".408 CheyTac", "5,7 x 28 mm FN-P90"}; 
	private final Integer[] weapon_pins = {1,2,3,4,5,6};
	private final String[] weapon_names = {"G3", "M4A1", "MP5", "M107", "M200 Intervention", "Five-seveN"};
	private final String[] weapon_makes = {"Heckler und Koch GmbH", "Colt's Manufacturing Company", "Heckler und Koch GmbH", "Barrett Firearms Manufacturing", "CheyTac LLC", "Fabrique Nationale de Herstal"};
	
	@Test
	public void findByIdCheck() {
		
		Bullet bulletToFind = new Bullet();
		bulletToFind.setName(bullet_names[3]);
		bulletToFind.setPin(bullet_pins[3]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToFind.getPin()) {
				inDb = true;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToFind);
		}
		
		Long newId = weaponManager.findBulletByPin(bulletToFind.getPin()).getId();
		
		assertEquals(weaponManager.findBulletByPin(bulletToFind.getPin()), weaponManager.findBulletById(newId));
		
		if (inDb == false ) {
			weaponManager.deleteBullet(bulletToFind);
		}
	}
	
	@Test
	public void addBulletCheck() {
		Bullet bulletToAdd = new Bullet();
		bulletToAdd.setName(bullet_names[0]);
		bulletToAdd.setPin(bullet_pins[0]);
		
		List<Bullet> retrievedBullets = weaponManager.getAllBullets();

		// If there is a client with PIN_1 delete it
		for (Bullet bullet : retrievedBullets) {
			if (bullet.getPin().equals(bulletToAdd.getPin())) {
				weaponManager.deleteBullet(bullet);
			}
		}

		weaponManager.addBullet(bulletToAdd);

		Bullet checkBullet = weaponManager.findBulletByPin(bulletToAdd.getPin());

		assertEquals(bulletToAdd.getName(), checkBullet.getName());
		assertEquals(bulletToAdd.getPin(), checkBullet.getPin());
	}
	
	@Test
	public void delBulletCheck() {
		
		Bullet bulletToDelete = new Bullet();
		bulletToDelete.setName(bullet_names[1]);
		bulletToDelete.setPin(bullet_pins[1]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Bullet retrievedBullet = null;
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToDelete.getPin()) {
				inDb = true;
				retrievedBullet = bullet;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToDelete);
			retrievedBullet = weaponManager.findBulletByPin(bulletToDelete.getPin());
		}
		
		List<Bullet> retrievedBeforeDelete = weaponManager.getAllBullets();
		
		weaponManager.deleteBullet(retrievedBullet);
		
		List<Bullet> retrievedAfterDelete = weaponManager.getAllBullets();
		
//		assertEquals(weaponManager.findBulletByPin(bulletToDelete.getPin()), weaponManager.deleteBullet(bulletToDelete));
		
		assertNull(weaponManager.findBulletByPin(retrievedBullet.getPin()));
		assertEquals(retrievedBeforeDelete.size() - 1, retrievedAfterDelete.size());
	}
	
	@Test
	public void updBulletCheck() {
		Bullet bulletToUpdate = new Bullet();
		bulletToUpdate.setName(bullet_names[2]);
		bulletToUpdate.setPin(bullet_pins[2]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Bullet retrievedBullet = null;
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToUpdate.getPin()) {
				inDb = true;
				retrievedBullet = bullet;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToUpdate);
			retrievedBullet = weaponManager.findBulletByPin(bulletToUpdate.getPin());
		}
		
		List<Bullet> retrievedBeforeUpdate = weaponManager.getAllBullets();
		
		bulletToUpdate.setName("Update Work");
		weaponManager.updateBullet(retrievedBullet);
		
		List<Bullet> retrievedAfterUpdate = weaponManager.getAllBullets();
		
		for (int i = 0; i < retrievedBeforeUpdate.size(); i++) {
			if (retrievedAfterUpdate.get(i).getPin().equals(retrievedBullet.getPin())) {
				assertEquals(retrievedAfterUpdate.get(i).getName(), retrievedBullet.getName());
			} else {
				assertTrue(retrievedAfterUpdate.contains(retrievedBeforeUpdate.get(i)));
			}
		}
		
		if (inDb == false) {
			weaponManager.deleteBullet(retrievedBullet);
		}
	}
	
	
	/////////////////////////////////////
	/*     RELATIONS TEST BOTTOM	   */
	/////////////////////////////////////
	@Test
	public void addWeaponCheck() {
		Weapon weaponToAdd = new Weapon();
		weaponToAdd.setModel(weapon_names[0]);
		weaponToAdd.setMake(weapon_makes[0]);
		weaponToAdd.setPin(weapon_pins[0]);

		List<Weapon> retrievedWeaponsBefore = weaponManager.getAvailableWeapons();

		for (Weapon weapon : retrievedWeaponsBefore) {
			if (weapon.getPin().equals(weaponToAdd.getPin())) {
				weaponManager.deleteWeapon(weapon);
			}
		}

		Long weaponId = weaponManager.addWeapon(weaponToAdd);

		Weapon retrievedWeaponAfter = weaponManager.findWeaponById(weaponId);
		
		assertEquals(weaponToAdd.getModel(), retrievedWeaponAfter.getModel());
		assertEquals(weaponToAdd.getMake(), retrievedWeaponAfter.getMake());
		
	}
	
	@Test
	public void loadBulletCheck() {

		Bullet bulletToLoad = new Bullet();
		bulletToLoad.setName(bullet_names[4]);
		bulletToLoad.setPin(bullet_pins[4]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Bullet retrievedBullet = null;
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToLoad.getPin()) {
				inDb = true;
				retrievedBullet = bullet;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToLoad);
			weaponManager.findBulletByPin(bulletToLoad.getPin());
		}
		
		Weapon weaponToLoad = new Weapon();
		weaponToLoad.setModel(weapon_names[4]);
		weaponToLoad.setMake(weapon_makes[4]);
		weaponToLoad.setPin(weapon_pins[4]);
		
		List<Weapon> retrievedAllWeapons = weaponManager.getAvailableWeapons();
		Long weaponId = null;
		
		inDb = false;
		for (Weapon weapon : retrievedAllWeapons) {
			if (weapon.getPin() == weaponToLoad.getPin()) {
				inDb = true;
				weaponId = weapon.getId();
			}
		}
		if (inDb == false) {
			weaponId = weaponManager.addWeapon(weaponToLoad);
		}

		inDb = false;
		for (Weapon weapon : weaponManager.getLoadedWeapons(retrievedBullet)) {
			if (weapon.getPin() == weaponToLoad.getPin()) {
				inDb = true;
			}
		}
		
		if (inDb == false ) {
			weaponManager.loadBullet(retrievedBullet.getId(), weaponId);
		}
		
		List<Weapon> loadedWeapons = weaponManager.getLoadedWeapons(retrievedBullet);

		Weapon loadedWeapon = weaponManager.findWeaponByPin(weaponToLoad.getPin());
		
		assertTrue(loadedWeapons.contains(loadedWeapon));
		
	}

	@Test
	public void unloadBulletCheck() {
		Bullet bulletToLoad = new Bullet();
		bulletToLoad.setName(bullet_names[5]);
		bulletToLoad.setPin(bullet_pins[5]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Bullet retrievedBullet = null;
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToLoad.getPin()) {
				inDb = true;
				retrievedBullet = bullet;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToLoad);
			retrievedBullet = weaponManager.findBulletByPin(bulletToLoad.getPin());
		}
		
		Weapon weaponToLoad = new Weapon();
		weaponToLoad.setModel(weapon_names[5]);
		weaponToLoad.setMake(weapon_makes[5]);
		weaponToLoad.setPin(weapon_pins[5]);
		
		List<Weapon> retrievedAllWeapons = weaponManager.getAvailableWeapons();
		Long weaponId = null;
		
		inDb = false;
		for (Weapon weapon : retrievedAllWeapons) {
			if (weapon.getPin() == weaponToLoad.getPin()) {
				inDb = true;
				weaponId = weapon.getId();
			}
		}
		if (inDb == false) {
			weaponId = weaponManager.addWeapon(weaponToLoad);
		}

		inDb = false;
		for (Weapon weapon : weaponManager.getLoadedWeapons(retrievedBullet)) {
			if (weapon.getPin() == weaponToLoad.getPin()) {
				inDb = true;
			}
		}
		
		if (inDb == false ) {
			weaponManager.loadBullet(retrievedBullet.getId(), weaponId);
		}
	
		Weapon retrievedWeapon = weaponManager.findWeaponById(weaponId);
		
		
		List<Weapon> retrievedBeforeUnload = weaponManager.getLoadedWeapons(retrievedBullet);
		
		weaponManager.unloadBullet(retrievedBullet, retrievedWeapon);
		
		List<Weapon> retrievedAfterUnload = weaponManager.getLoadedWeapons(retrievedBullet);
		
		assertFalse(retrievedAfterUnload.contains(retrievedWeapon));
		assertEquals(retrievedBeforeUnload.size() - 1, retrievedAfterUnload.size());
		
	}
	

}
