

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;


public class SpecBuilderTest {

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
		
		RequestSpecification request = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();	
		
		RequestSpecification res =  given().spec(request).body(add);
		
		Response response =	res.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}

}
