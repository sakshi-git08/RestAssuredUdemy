package File;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;


public class SerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AddPlace add = new AddPlace();
		add.setAccuracy(50);
		add.setAddress("638, Old Faridabad");
		add.setLanguage("English");
		add.setPhone_number("6666600000");
		add.setName("Sakshi");
		add.setWebsite("https://www.google.com");
		
		List<String> myList = new ArrayList<>();
		myList.add("shoe park");
		myList.add("shop");
		add.setTypes(myList);
		
		Location l = new Location();
		l.setLat(-38.364783);
		l.setLng(367.4839932);
		
		add.setLocation(l);
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response res =  given().log().all().queryParam("key", "qaclick123").body(add)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		
		String responseString = res.asString();
		System.out.println(responseString);
	}

}
