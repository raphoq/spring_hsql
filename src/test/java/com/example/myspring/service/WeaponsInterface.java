package com.example.myspring.service;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.example.myspring.domain.Weapon;
import com.example.myspring.service.WeaponManager;
import com.example.myspring.domain.Bullet;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional


public class WeaponsInterface {

	@Autowired
	WeaponManager weaponManager;
	
	private JFrame mainFrame;
	private JTable weaponTable;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeaponsInterface window = new WeaponsInterface();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 660, 460);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new MigLayout("", "[50%][50%]", "[grow,fill]"));
		
		JScrollPane weaponTableScroll = new JScrollPane();
		mainPanel.add(weaponTableScroll, "cell 0 0,growy");
		
		weaponTable = new JTable();
		weaponTableScroll.setViewportView(weaponTable);
		
		// Load weapon Table
		
		
		
		Weapon weapon = weaponManager.getAvailableWeapons().get(0);
		System.out.println(weapon.getMake());
		
		DefaultTableModel weaponModel = new DefaultTableModel();
		
		
		
			
		//		weaponTable.setModel(dataModel)weaponManager.getAvailableWeapons().
		
		
		
		
		
		
	}
	
	public WeaponsInterface() {
		initialize();
	}

}
