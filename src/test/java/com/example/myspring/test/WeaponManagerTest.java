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

import com.example.myspring.domain.Bullet;
import com.example.myspring.service.WeaponManager;

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
	public void addBulletCheck() {

		List<Bullet> retrievedAllBullets = weaponManager.getAllBullets();

		// If there is a client with PIN_1 delete it
		for (Bullet bullet : retrievedAllBullets) {
			if (bullet.getPin() == pin[0]) {
				weaponManager.deleteBullet(bullet);
			}
		}

		
		Bullet bulletNew = new Bullet();
		bulletNew.setName(bullet_names[0]);
		bulletNew.setPin(pin[0]);

		// Pin is Unique
		weaponManager.addBullet(bulletNew);

		Bullet retrievedBullets = weaponManager.findBulletByPin(pin[0]);

		assertEquals(pin[0], retrievedBullets.getPin());
		assertEquals(bullet_names[0], retrievedBullets.getName());
		// ... check other properties here
	}

}
