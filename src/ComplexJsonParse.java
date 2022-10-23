

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.Courses());
		int count = js.getInt("courses.size()");
		System.out.println("Courses count:"+count);
		
		int purAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount:"+purAmount);
		
		String title = js.getString("courses[0].title");
		System.out.println("Title: "+title);
		
		//All courses titles and their respective prices
		for(int i=0; i<count; i++) {
			String title1 = js.getString("courses["+i+"].title");
			int price = js.getInt("courses["+i+"].price");
			
			System.out.println(title1+":"+price);
			
		}
		//Print RPA course sold out copies
		for(int i=0; i<count; i++) {
			String title1 = js.getString("courses["+i+"].title");
			if(title1.equalsIgnoreCase("RPA")) {
				System.out.println("RPA course sold out copies: "+js.get("courses["+i+"].copies"));
				break;
			}
		}
		
		
		
	}

}
