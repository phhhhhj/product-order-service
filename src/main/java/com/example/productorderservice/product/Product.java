package com.example.productorderservice.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int price;
	private DiscountPolicy discountPolicy;

	public Product(final String name, final int price, final DiscountPolicy discountPolicy) {
		// validation
		Assert.hasText(name, "상품명을 필수입니다.");
		Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
		Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");

		this.name = name;
		this.price = price;
		this.discountPolicy = discountPolicy;
	}
}
