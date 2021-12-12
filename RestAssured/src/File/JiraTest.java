package File;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Inorder to parse json we can use jsonPath while other way for keeping session intact is using session filter class and can use it's variable
		//wherever required. Doing this same via JsonPath is a long way 
		
		SessionFilter session = new SessionFilter();
		
		RestAssured.baseURI = "http://localhost:8082/";
		//Login scenario
		String response = given().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"username\":\"sakshi.agg428\",\r\n"
				+ "    \"password\":\"Sakshi@12412\"\r\n"
				+ "}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		String expectedMessage = "Hi! How are you?";
		
		
		//Add Comment
		String commentIssue = given().pathParam("key", "10106").log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = new JsonPath(commentIssue);
		String commentId = js.get("id");
		
		//Add Attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10106")
		.header("Content-Type", "multipart/form-data")
		.multiPart("file", new File("C:\\Users\\Sakshi Aggarwal\\git\\repository\\RestAssured\\src\\File\\jira.txt")).when()
		.post("rest/api/2/issue/{key}/attachments").then().log().all().statusCode(200);
		
		//whenever making use of multipart method do not use content type as application/json 
		//Make use of "Content-Type", "multipart/form-data" in case of multipart
		
		//Get Issue
		String issueDetails = given().filter(session).pathParam("key", "10106") // locates you to the sub-resource
				.queryParam("fields", "comment") //filtering the response (drill down)
				.log().all().when().get("/rest/api/2/issue/{key}").then()
		.log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		//according to commentId matching the body
		
		JsonPath jsp = new JsonPath(issueDetails);
		int commentCount = jsp.getInt("fields.comment.comments.size()");
		for(int i = 0; i < commentCount; i++) {
			String commentIdIssue = jsp.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId)) {
				String message = jsp.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
			}
		}
		
		//If you are using https protocol then it might require any certifications to be validated in order to hit the api.
		//So, if you do not have certificate and want to bypass it. You can do it in the below way: 
		//given().relaxedHTTPSValidation()..... - relaxedHTTPSValidation() this will take care of it in case of no proper certifications
		
		
	}

	//{key} --> This dynamically picks the value at run time replacing in the resource section of the api call. We could have directly 
	//pasted the key but just to know that it can be dynamically captured we did this way
}
