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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class WeaponManagerTest {

	@Autowired
	WeaponManager weaponManager;

	private final Integer[] pin = {1,2,3,4};
	private final String[] bullet_names = {"7.62 x 51mm NATO", "5,56 × 45 mm", "9 x 19 mm Parabellum", "12,7 mm NATO"}; 
	
	
	@Test
	public void findByIdCheck() {
		
		Bullet bulletToFind = new Bullet();
		bulletToFind.setName(bullet_names[3]);
		bulletToFind.setPin(pin[3]);
		
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
		bulletToAdd.setPin(pin[0]);
		
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
		bulletToDelete.setPin(pin[1]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToDelete.getPin()) {
				inDb = true;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToDelete);
		}
		
		List<Bullet> retrievedBeforeDelete = weaponManager.getAllBullets();
		
		weaponManager.deleteBullet(bulletToDelete);
		
		List<Bullet> retrievedAfterDelete = weaponManager.getAllBullets();
		
//		assertEquals(weaponManager.findBulletByPin(bulletToDelete.getPin()), weaponManager.deleteBullet(bulletToDelete));
		
		assertNull(weaponManager.findBulletByPin(bulletToDelete.getPin()));
		assertEquals(retrievedBeforeDelete.size() - 1, retrievedAfterDelete.size());
	}
	
	@Test
	public void updBulletCheck() {
		Bullet bulletToUpdate = new Bullet();
		bulletToUpdate.setName(bullet_names[2]);
		bulletToUpdate.setPin(pin[2]);
		
		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();
		
		Boolean inDb = false;
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == bulletToUpdate.getPin()) {
				inDb = true;
			}
		}
		if (inDb == false) {
			weaponManager.addBullet(bulletToUpdate);
		}
		
		List<Bullet> retrievedBeforeUpdate = weaponManager.getAllBullets();
		
		bulletToUpdate.setName("Update Work");
		weaponManager.updateBullet(bulletToUpdate);
		
		List<Bullet> retrievedAfterUpdate = weaponManager.getAllBullets();
		
		for (int i = 0; i < retrievedBeforeUpdate.size(); i++) {
			if (retrievedAfterUpdate.get(i).getPin().equals(bulletToUpdate.getPin())) {
				assertEquals(retrievedAfterUpdate.get(i).getName(), bulletToUpdate.getName());
			} else {
				assertTrue(retrievedAfterUpdate.contains(retrievedBeforeUpdate.get(i)));
			}
		}
		
		if (inDb == false) {
			weaponManager.deleteBullet(bulletToUpdate);
		}
	}

}
