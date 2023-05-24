package com.example.productorderservice.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

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
		final String name = "상품명";
		final int price = 1000;
		final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
		final AddProductRequest request = new AddProductRequest(name, price, discountPolicy);
		productService.addProduct(request);
	}

	/* record 타입 (java 14 버전부터 사용 가능)
	 - 각 필드가 private final로 정의된 데이터 클래스, final class로 생성되므로 상속 불가
	 - 모든 필드에는 초기화를 위한 RequiredAllArgument 생성자 생성
	 - 각 필드의 getter는 getXXX()가 아닌, 필드명을 딴 getter가 생성됨 (ex. name(), price() )
	*/
	private record AddProductRequest(String name, int price,
									 DiscountPolicy discountPolicy) {
		private AddProductRequest {
			// validation
			Assert.hasText(name, "상품명을 필수입니다.");
			Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
			Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");
		}
	}

	private enum DiscountPolicy {
		NONE
	}

	/**
	 * 2. productService에서 product 생성 후, productPort에게 저장하라고 요청
	 */
	private class ProductService {
		private final ProductPort productPort;

		private ProductService(ProductPort productPort) {
			this.productPort = productPort;
		}

		public void addProduct(AddProductRequest request) {
			final Product product = new Product(request.name(), request.price(), request.discountPolicy());

			productPort.save(product);
		}
	}

	private class Product {
		private final String name;
		private final int price;
		private final DiscountPolicy discountPolicy;
		private Long id;

		public Product(final String name, final int price, final DiscountPolicy discountPolicy) {
			// validation
			Assert.hasText(name, "상품명을 필수입니다.");
			Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
			Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");

			this.name = name;
			this.price = price;
			this.discountPolicy = discountPolicy;
		}

		public void assignId(final Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}
	}

	private interface ProductPort {
		void save(final Product product);
	}

	/**
	 * 3. productPort의 구현체인 Adapter에서 실제 메모리에 저장
	 */
	private class ProductAdapter implements ProductPort {
		private final ProductRepository productRepository;

		private ProductAdapter(ProductRepository productRepository) {
			this.productRepository = productRepository;
		}

		@Override
		public void save(Product product) {
			productRepository.save(product);
		}
	}

	private class ProductRepository {
		private Long sequence = 0L;
		private Map<Long, Product> persistence = new HashMap<>();

		public void save(final Product product) {
			product.assignId(++sequence);  // id를 저장
			persistence.put(product.getId(), product);
		}
	}
}
