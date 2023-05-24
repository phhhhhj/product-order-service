package com.example.productorderservice.product;

import org.springframework.util.Assert;

/* record 타입 (java 14 버전부터 사용 가능)
 - 각 필드가 private final로 정의된 데이터 클래스, final class로 생성되므로 상속 불가
 - 모든 필드에는 초기화를 위한 RequiredAllArgument 생성자 생성
 - 각 필드의 getter는 getXXX()가 아닌, 필드명을 딴 getter가 생성됨 (ex. name(), price() )
*/
record AddProductRequest(String name, int price,
						 DiscountPolicy discountPolicy) {
	AddProductRequest {
		// validation
		Assert.hasText(name, "상품명을 필수입니다.");
		Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
		Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");
	}
}
