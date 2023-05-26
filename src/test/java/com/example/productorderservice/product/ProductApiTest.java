package com.example.productorderservice.product;

import com.example.productorderservice.ApiTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class ProductApiTest extends ApiTest {

	/*
	 * 상품 등록 flow start
	 * 1. productService에 addProduct 요청
	 */
	@Test
	void 상품등록() {
		final AddProductRequest request = 상품등록요청_생성();

		// api 요청
		// given().log().all(): 요청 보내는 로그 전체를 남기겠다
		final ExtractableResponse<Response> response = RestAssured.given().log().all()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.when()
				.post("/products")
				.then()
				.log().all().extract();

		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	private AddProductRequest 상품등록요청_생성() {
		final String name = "상품명";
		final int price = 1000;
		final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
		return new AddProductRequest(name, price, discountPolicy);
	}

}
