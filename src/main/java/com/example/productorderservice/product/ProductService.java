package com.example.productorderservice.product;

/**
 * 2. productService에서 product 생성 후, productPort에게 저장하라고 요청
 */
class ProductService {
	private final ProductPort productPort;

	ProductService(ProductPort productPort) {
		this.productPort = productPort;
	}

	public void addProduct(AddProductRequest request) {
		final Product product = new Product(request.name(), request.price(), request.discountPolicy());

		productPort.save(product);
	}
}
