package File;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	public static void main(String t[]) {
		
		JsonPath js = new JsonPath(Payload.countCourses());
		// Print No of courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of the first course
		String titleFirstCourse = js.getString("courses[2].title");
		System.out.println(titleFirstCourse);
		
		//Print All course titles and their respective Prices
		for(int i = 0; i < count; i++) {
			String courseTitles = js.get("courses[" + i + "].title");
			System.out.println(js.getInt("courses["+ i + "].price")); 
			// sopln expects string while it is in integer we need to convert it into string using toString
			System.out.println(courseTitles);
		}
		
		//Print no of copies sold by RPA Course
		
		System.out.println("Print no of copies sold by RPA Course");
		for(int i = 0; i < count; i++) {
			String courseTitles = js.get("courses[" + i + "].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				int countCopies = js.get("courses[" + i + "].copies");
				System.out.println(countCopies);
				break;
			}
			
		}		
		
		
	}

}
