import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void totalPrice() {
		
		//Verify if sum of all courses price matches with purchase amount
				int totalAmount = 0;
				
				JsonPath js = new JsonPath(Payload.Courses());
				int count = js.getInt("courses.size()");
				int purAmount = js.getInt("dashboard.purchaseAmount");
				System.out.println("Verify if sum of all courses price matches with purchase amount");
				for(int i=0; i<count; i++) 
				{
					int price = js.get("courses["+i+"].price");
					int copies = js.get("courses["+i+"].copies");
					totalAmount = totalAmount + price*copies;
					
				}
				Assert.assertEquals(totalAmount, purAmount, "Amounts are not matched");

	}

}
