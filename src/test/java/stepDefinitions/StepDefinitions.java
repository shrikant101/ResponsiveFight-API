package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.TestDataBuild;
import utils.Utils;

import static io.restassured.RestAssured.*;
import org.junit.Assert;

public class StepDefinitions extends Utils {

	TestDataBuild testDataBuild = new TestDataBuild();
	RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(RestAssured.baseURI).build();
	ResponseSpecification responsespecification;

	Response response;

	@Given("^I have a valid token$")
	public void iHaveAValidToken() throws Throwable {
		response = given().spec(requestSpecification()).get(getGlobalValues("VERIFY_TOKEN_ENDPOINT")).then()
				.spec(responseSpecification()).extract().response();
	}

	@Then("^I should be able to <action> user <username> with value <score>$")
	public void iShouldBeAbleToActionUserUsernameWithValueScore(DataTable datatable) throws Throwable {
		String action = (String) datatable.cell(0, 0);
		String user = (String) datatable.cell(0, 1);
		int score = Integer.valueOf(datatable.cell(0, 2));

		if (action.equalsIgnoreCase("POST")) {
			response = given().spec(requestSpecification())
					.body(testDataBuild.createPayLoad(user, Integer.valueOf(score))).when()
					.post(getGlobalValues("USER_ENDPOINT")).then().spec(responseSpecification()).extract().response();
			Assert.assertEquals(201, response.statusCode());
			

		} else if (action.equalsIgnoreCase("PUT")) 
		{
			 response = given().spec(requestSpecification())
					.body(testDataBuild.createPayLoad(user, Integer.valueOf(score))).when()
					.put(getGlobalValues("USER_ENDPOINT")).then().spec(responseSpecificationNoContent()).extract()
					.response();
		Assert.assertEquals(204, response.statusCode());
		}
		}

	@And("^I should be able to <action> user <username>$")
	public void iShouldBeAbleToActionUserUsername(DataTable datatable) throws Throwable {

		String user = (String) datatable.cell(0, 1);
		response = given().spec(requestSpecification()).when().queryParam("delete-key", user)
				.delete(getGlobalValues("USER_ENDPOINT")).then().spec(responseSpecificationNoContent()).extract()
				.response();
		Assert.assertEquals(204, response.statusCode());

	}

}
