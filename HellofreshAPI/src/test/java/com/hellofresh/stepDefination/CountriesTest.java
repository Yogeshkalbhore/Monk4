package com.hellofresh.stepDefination;

import java.net.URI;
import java.util.List;

import com.hellofresh.configuration.Config;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBodyData;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class CountriesTest {

	String post_url, get_url;
	Response response;
	Response PostResponse;
	private String payload = "{\n" + "\n" + "\"name\": \"Test Country\",\n" + "\n" + "\"alpha2_code\": \"TC\",\n" + "\n"
			+ "\"alpha3_code\": \"TCY\"\n" + "\n" + "}";

	@When("^We make http get call request for all Countries\\.$")
	public void we_make_http_get_call_request_for_all_Countries() throws Throwable {
		get_url = Config.baseURI + "/get/all";
		response = RestAssured.given().accept(ContentType.JSON).when().get(new URI(get_url));
	}

	@Then("^Response code for this should be \"([^\"]*)\" OK for all Countries\\.$")
	public void response_code_for_this_should_be_OK_for_all_Countries(int expected_response_code) throws Throwable {
		try {
			Assert.assertTrue("200OK", expected_response_code == response.getStatusCode());
		} catch (AssertionError e) {
			throw new AssertionError("Message ");
		}
	}

	@Then("^In returned Response these Countries code US, DE and GB should be present\\.$")
	public void in_returned_Response_these_Countries_code_US_DE_and_GB_should_be_present() throws Throwable {
		JsonPath jp = new JsonPath(response.asString());
		List<String> countries = response.path("RestResponse.result.alpha2_code");
		// check that they are all 'open'
		for (String alpha2_code : countries) {
			if (alpha2_code.equals("US")) {
				Assert.assertEquals("reason", "US", alpha2_code);

			} else if (alpha2_code.equals("GB")) {
				Assert.assertEquals("reason", "GB", alpha2_code);

			} else if (alpha2_code.equals("DE")) {

				Assert.assertEquals("reason", "DE", alpha2_code);
			} else {
				System.out.println("Oops other countries");
			}

		}

	}

	@When("^We make http get call request with individually Countries \"([^\"]*)\"\\.$")
	public void we_make_http_get_call_request_with_individually_Countries(String ISOcode) throws Throwable {
		get_url = Config.baseISOURI + ISOcode;
		response = RestAssured.given().accept(ContentType.JSON).when().get(new URI(get_url));

	}

	@Then("^Response code for this should be \"([^\"]*)\" OK for individually Countries\\.$")
	public void response_code_for_this_should_be_OK_for_individually_Countries(int expected_response_code)
			throws Throwable {
		try {
			Assert.assertTrue("200OK", expected_response_code == response.getStatusCode());
		} catch (AssertionError e) {
			throw new AssertionError("Message ");
		}
	}

	@Then("^In returned Response these Countries elements should be present\\.$")
	public void in_returned_Response_these_Countries_elements_should_be_present() throws Throwable {

		JsonPath jp = new JsonPath(response.asString());

		String alpha2_code = response.path("RestResponse.result.alpha2_code");

		if (alpha2_code.equals("US")) {
			Assert.assertEquals("US", jp.get("RestResponse.result.alpha2_code"));
			Assert.assertEquals("United States of America", jp.get("RestResponse.result.name"));
			Assert.assertEquals("USA", jp.get("RestResponse.result.alpha3_code"));

		} else if (alpha2_code.equals("GB")) {
			Assert.assertEquals("GB", jp.get("RestResponse.result.alpha2_code"));
			Assert.assertEquals("United Kingdom of Great Britain and Northern Ireland",
					jp.get("RestResponse.result.name"));
			Assert.assertEquals("GBR", jp.get("RestResponse.result.alpha3_code"));

		} else if (alpha2_code.equals("DE")) {

			Assert.assertEquals("DE", jp.get("RestResponse.result.alpha2_code"));
			Assert.assertEquals("Germany", jp.get("RestResponse.result.name"));
			Assert.assertEquals("DEU", jp.get("RestResponse.result.alpha3_code"));
		} else {
			System.out.println("Oops other countries");
		}

	}

	@When("^We make http get call request for in-existent individually Countries \"([^\"]*)\"\\.$")
	public void we_make_http_get_call_request_for_in_existent_individually_Countries(String ISOcode) throws Throwable {
		get_url = Config.baseISOURI + ISOcode;
		response = RestAssured.given().accept(ContentType.JSON).when().get(new URI(get_url));
	}

	@Then("^Response code for this should be \"([^\"]*)\" OK for in-existent individually Countries\\.$")
	public void response_code_for_this_should_be_OK_for_in_existent_individually_Countries(int expected_response_code)
			throws Throwable {
		try {
			Assert.assertTrue("200OK", expected_response_code == response.getStatusCode());
		} catch (AssertionError e) {
			throw new AssertionError("Message ");
		}
	}

	@Then("^In returned Response these Countries elements should not be present\\.$")
	public void in_returned_Response_these_Countries_elements_should_not_be_present() throws Throwable {
		JsonPath jp = new JsonPath(response.asString());

		try {
			String alpha2_code = response.path("RestResponse.result.alpha2_code");
			Assert.assertNull(response.path("RestResponse.result.alpha2_code"));
		} catch (Exception e) {

		}

	}

	@When("^We make http POST call request for Specific Countries with Request\\.$")
	public void we_make_http_POST_call_request_for_Specific_Countries_with_Request() throws Throwable {
		post_url = Config.basePOSTURI;
		PostResponse = RestAssured.given().accept(ContentType.JSON).body(payload).when().post(new URI(get_url));
	}

	@Then("^Response code for this should be \"([^\"]*)\" OK for Any POST Request for Countries\\.$")
	public void response_code_for_this_should_be_OK_for_Any_POST_Request_for_Countries(int expected_response_code)
			throws Throwable {
		try {
			Assert.assertTrue("400OK", expected_response_code == response.getStatusCode());
		} catch (AssertionError e) {
			throw new AssertionError("Message ");
		}
	}

	@Then("^In returned Response these Countries should give (\\d+) Not Found\\.$")
	public void in_returned_Response_these_Countries_should_give_Not_Found(int arg1) throws Throwable {
		JsonPath jp = new JsonPath(PostResponse.asString());

		Assert.assertNotEquals("TCY", jp.get("RestResponse.result.alpha2_code"));

	}

}
