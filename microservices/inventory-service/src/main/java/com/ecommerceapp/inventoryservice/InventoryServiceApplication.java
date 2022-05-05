package com.ecommerceapp.inventoryservice;

import com.ecommerceapp.inventoryservice.model.Inventory;
import com.ecommerceapp.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("Iphone_13");
			inventory.setQuantity(100);

			Inventory inventoryNew = new Inventory();
			inventoryNew.setSkuCode("Iphone_14");
			inventoryNew.setQuantity(20);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventoryNew);
		};
	}
}
