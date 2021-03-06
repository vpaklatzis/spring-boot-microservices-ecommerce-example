package com.ecommerceapp.inventoryservice;

import com.ecommerceapp.inventoryservice.model.Inventory;
import com.ecommerceapp.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone-13");
			inventory.setQuantity(100);

			Inventory inventoryNew = new Inventory();
			inventoryNew.setSkuCode("iphone-14");
			inventoryNew.setQuantity(1);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventoryNew);
		};
	}
}
