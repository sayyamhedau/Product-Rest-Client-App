package com.app.service.imp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.entity.ProductDTO;
import com.app.exceptions.ProductNotFoundException;
import com.app.service.IProductClientService;

@Service
public class ProductClientServiceImpl implements IProductClientService {
	@Autowired
	private RestTemplate template;

	private static final Logger log = LoggerFactory.getLogger(ProductClientServiceImpl.class);
	private static final String ROOT_URI = "http://localhost:8082/rest/api/product";

	@Override
	public String getWelcomeMsg() {
		String getWelcomeMsgUrl = ROOT_URI + "/welcome";

		ResponseEntity<String> responseEntityForWelcomeMsg = template.getForEntity(getWelcomeMsgUrl, String.class);
		if (responseEntityForWelcomeMsg.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntityForWelcomeMsg.getBody();
		}
		return responseEntityForWelcomeMsg.getBody();
	}

	@Override
	public String saveProduct(ProductDTO productDTO) {
		String postSaveProductUrl = ROOT_URI + "/save";
		String response = "";

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<>(productDTO, headers);
		try {
			ResponseEntity<String> postResponseEntitySaveProduct = template.exchange(postSaveProductUrl,
					HttpMethod.POST, httpEntity, String.class);

			if (postResponseEntitySaveProduct.getStatusCode().equals(HttpStatus.CREATED)) {
				response = postResponseEntitySaveProduct.getBody();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			response = e.getMessage();
		}
		return response;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> productList = null;
		String getAllProductsUrl = ROOT_URI + "/all";

		ResponseEntity<ProductDTO[]> responseEntityForAllProducts = template.getForEntity(getAllProductsUrl,
				ProductDTO[].class);
		if (responseEntityForAllProducts.getStatusCode().equals(HttpStatus.OK)) {
			productList = Stream.of(responseEntityForAllProducts.getBody()).collect(Collectors.toList());
		}
		return productList;
	}

	@Override
	public String updateProduct(ProductDTO productDTO) {
		String putUpdateProductUrl = ROOT_URI + "/update/{id}";
		String response = "";

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<>(productDTO, headers);

		try {
			ResponseEntity<String> putResponseEntityForUpdateProduct = template.exchange(putUpdateProductUrl,
					HttpMethod.PUT, httpEntity, String.class, productDTO.getId());
			response = putResponseEntityForUpdateProduct.getBody();
		} catch (ProductNotFoundException e) {
			log.error(e.getMessage());
			response = e.getMessage();
		}
		return response;
	}

	@Override
	public String deleteProduct(Long id) {
		String deleteProductUrl = ROOT_URI + "/delete/{id}";
		String response = "";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> deleteResponseEntityForDeleteProduct = template.exchange(deleteProductUrl,
					HttpMethod.DELETE, httpEntity, String.class, id);
			if (deleteResponseEntityForDeleteProduct.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				response = deleteResponseEntityForDeleteProduct.getBody();
			}
		} catch (ProductNotFoundException e) {
			log.error(e.getMessage());
			response = e.getMessage();
		}
		return response;
	}

	@Override
	public ProductDTO getProduct(Long id) {
		ProductDTO productDTO = null;
		String getProductUrl = ROOT_URI + "/{id}";

		try {
			ResponseEntity<ProductDTO> responseEntityForGetProduct = template.getForEntity(getProductUrl,
					ProductDTO.class, id);
			if (responseEntityForGetProduct.getStatusCode().equals(HttpStatus.OK)) {
				productDTO = responseEntityForGetProduct.getBody();
			}

		} catch (ProductNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return productDTO;
	}

}
