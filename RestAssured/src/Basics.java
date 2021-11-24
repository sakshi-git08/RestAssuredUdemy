import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import File.Payload;
import File.ReUsableMethods;

public class Basics {
//	@Test
	public static void main(String args[]) {
		//validate if Add Place api is working as expected.
		//Add Place and then update place with the new address -> Get place to verify the updated address

		
		//given - Handles all the input details.
		//when - submit the api - resource, http method
		//Then - validates the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath jsp = new JsonPath(response); // for parsing Json takes String as input and json as output
		String placeId = jsp.getString("place_id");
		System.out.println(placeId);
		
		//Update Place
		String newAddress = "Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+ placeId + "\",\r\n"
				+ "\"address\":\"" + newAddress + "\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get API
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId).when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js.getString("address");
	
		System.out.println(actualAddress);
		
		//Cucumber Junit, Testng
		
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}
}
