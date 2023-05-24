package com.example.productorderservice.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

	private ProductService productService;
	private ProductPort productPort;
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		productRepository = new ProductRepository();
		productPort = new ProductAdapter(productRepository);
		// productService 초기화
		productService = new ProductService(productPort);
	}

	@Test
	/*
	 * 상품 등록 flow start
	 * 1. productService에 addProduct 요청
	 */
	void 상품등록() {
		final AddProductRequest request = 상품등록요청_생성();
		productService.addProduct(request);
	}

	private AddProductRequest 상품등록요청_생성() {
		final String name = "상품명";
		final int price = 1000;
		final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
		return new AddProductRequest(name, price, discountPolicy);
	}

}
