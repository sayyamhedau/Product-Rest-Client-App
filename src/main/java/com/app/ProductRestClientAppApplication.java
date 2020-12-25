package com.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.app.entity.ProductDTO;
import com.app.service.IProductClientService;

@SpringBootApplication
public class ProductRestClientAppApplication {
	@Autowired
	private IProductClientService productClientService;

	private static final Logger log = LoggerFactory.getLogger(ProductRestClientAppApplication.class);

	private static ProductDTO productDTO = ProductDTO.builder().productCode("P005").productCost(600.0)
			.productDiscount(11.0).productGst(2.0).productName("keyboard R1").productQuantity(12)
			.productType("Laptop/Computer Accessories").build();

	public static void main(String[] args) {
		SpringApplication.run(ProductRestClientAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner1() {
		return args -> log.info(productClientService.getWelcomeMsg());
	}

	@Profile(value = "!test")
	@Bean
	public CommandLineRunner commandLineRunner2() {
		return args -> productClientService.getAllProducts().forEach(product -> log.info("{}", product));
	}

	@Profile(value = "!test")
	@Bean
	public CommandLineRunner commandLineRunner3() {
		return args -> {
			String saveProductResponse = productClientService.saveProduct(productDTO);
			log.info("{}", saveProductResponse);
		};
	}

	@Profile(value = "!test")
	@Bean
	public CommandLineRunner commandLineRunner4() {
		return args -> {
			ProductDTO product = productClientService.getProduct(3L);
			log.info("{}", product);
		};
	}

	@Profile(value = "!test")
	@Bean
	public CommandLineRunner commandLineRunner5() {
		return args -> {
			productDTO.setId(5L);
			productDTO.setProductCost(700.0);

			String updateProductResponse = productClientService.updateProduct(productDTO);
			log.info(updateProductResponse);
		};
	}

	@Profile(value = "test")
	@Bean
	public CommandLineRunner commandLineRunner6() {
		return args -> {
			String deleteProductResponse = productClientService.deleteProduct(34L);
			log.info(deleteProductResponse);
		};
	}
}
