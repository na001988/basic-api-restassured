package com.studioAPI;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {

	// basic set-up
	// verify if testng plu-in is installed on eclipse
	// add maven dependencies in pom, for testng, restassured and gson
	// run as testng class
	// schema validation, add maven dependency for resassured json validation
	// create a folder and add a json file, add the converted response
	// import in the class the schema validation from matchesJsonSchema
	// use it in the assert

	@Test
	public void configAPI() {

		String url = "https://api-staging-builder.engineer.ai";

		String base_path = System.getProperty("user.dir") + "/schema-validation/file.json";

		RestAssured.baseURI = url;

		// System.out.println(base_path);

		File myFile = new File(base_path);

		RestAssured.given().accept(ContentType.JSON).when().get("/configurations").then().statusCode(200);

		RestAssured.given().log().all().accept(ContentType.JSON).when().get("/configurations").then().statusCode(200)
				.log().all();

		RestAssured.given().accept(ContentType.JSON).when().get("/configurations").then().statusCode(200).assertThat()
				.body("country_code", equalTo("I"));

		RestAssured.given().accept(ContentType.JSON).when().get("/configurations").then().statusCode(200).assertThat()
				.header("Connection", equalTo("keep-alive"));

		RestAssured.given().log().all().accept(ContentType.JSON).when().get("/configurations").then().statusCode(200)
				.assertThat().header("Connection", equalTo("keep-alive")).body(matchesJsonSchema(myFile))
				.body("country_code", equalTo("US")).log().all();

		RestAssured.given().log().all().accept(ContentType.JSON).when().get("/configurations").then().statusCode(200)
				.assertThat().header("Connection", equalTo("keep-alive")).body(matchesJsonSchema(myFile))
				.body("country_code", equalTo("US")).time(lessThan(200L));

	}
}
