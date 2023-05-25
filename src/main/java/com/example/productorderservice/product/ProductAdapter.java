package com.example.productorderservice.product;

import org.springframework.stereotype.Component;

/**
 * 3. productPort의 구현체인 Adapter에서 실제 메모리에 저장
 */

@Component
class ProductAdapter implements ProductPort {
	private final ProductRepository productRepository;

	ProductAdapter(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);
	}
}
