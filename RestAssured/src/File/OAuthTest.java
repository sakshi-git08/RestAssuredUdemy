package File;

//import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;



public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String[] coursesTitle = {"Selenium Webdriver Java", "Cypress", "Protactor"};
		
//		System.setProperty("webdriver.chrome.driver", "D:\\Sakshi Projects\\chromedriver_win32\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("sakshi.agg428@gmail.com");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("sak#$7812");
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(4000);
//		String url = driver.getCurrentUrl();
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWhX7Rw4BeeG23STGAQq2UmywSHDOxMi_-6FN-t3MLSCQjYUKE33rfECARLwQAxhCw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
		
		//https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWhX7Rw4BeeG23STGAQq2UmywSHDOxMi_-6FN-t3MLSCQjYUKE33rfECARLwQAxhCw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#
		
		String partialCode = url.split("code=")[1]; // it will break the url till code in two parts
		String code = partialCode.split("&scope")[0]; //it will again break the url into two till & scope
		System.out.println(code);
		
		//tagname[attribute='value']
		
		
		String accessToken = given().urlEncodingEnabled(false).queryParams("code", code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accessToken);
		String accesstoken = js.getString("access_token");
		

		String response = given().log().all().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
		
		//deserialization
		GetCourse gc = given().log().all().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println(gc.getLinkedin());
		System.out.println(gc.getInstructor());
		
		//fetch the price of courseTitle = SoapUI webservices Testing
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for(int i = 0; i < apiCourses.size(); i++) {
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices Testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//print all the courseTitle under webAutomation 
		List<String> expectedList = Arrays.asList(coursesTitle);
		
		ArrayList<String> a = new ArrayList<>();
		List<pojo.WebAutomation> w = gc.getCourses().getWebAutoamtion();
		for(int i = 0; i < w.size(); i++) {
			a.add(w.get(i).getCourseTitle());
		}
		
		Assert.assertTrue(a.equals(expectedList));
		
	}

}
