package com.app.service;

import java.util.List;

import com.app.entity.ProductDTO;

public interface IProductClientService {
	String getWelcomeMsg();

	String saveProduct(ProductDTO productDTO);

	List<ProductDTO> getAllProducts();

	String updateProduct(ProductDTO productDTO);

	String deleteProduct(Long id);

	ProductDTO getProduct(Long id);
}
