package com.example.productorderservice.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2. productService에서 product 생성 후, productPort에게 저장하라고 요청
 */

@RestController
@RequestMapping("/products")
class ProductService {
	private final ProductPort productPort;

	ProductService(ProductPort productPort) {
		this.productPort = productPort;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Void> addProduct(@RequestBody AddProductRequest request) {
		final Product product = new Product(request.name(), request.price(), request.discountPolicy());

		productPort.save(product);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
