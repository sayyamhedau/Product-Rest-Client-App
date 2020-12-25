package com.app.utils;

import org.springframework.stereotype.Component;

import com.app.entity.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Converter {
	public String convertObjToJsonFormat(Object object) throws JsonProcessingException {
		String jsonvalue = "";
		if (object instanceof ProductDTO) {
			ProductDTO productDTO = (ProductDTO) object;
			jsonvalue = new ObjectMapper().writeValueAsString(productDTO);
		}
		return jsonvalue;
	}
}
