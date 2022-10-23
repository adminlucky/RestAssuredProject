import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;
public class Basics {

	public static void main(String[] args) {
		
		//given() - all input details
		//when() - submit the api (resources and http methods)\
		//then() - validate the response
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response1 = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.body())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		//System.out.println(response);
		JsonPath js1 = ReusableMethods.rawToJson(response1);
		String placeId = js1.getString("place_id");
		
		String newAdd = "5-94/300 Summer walk, USA";
		
		given().log().all().queryParam("key", "qaclick123")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAdd+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"))
		.extract().response().asString();
				
		String response2 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2 = ReusableMethods.rawToJson(response2);
		String updatedAdd = js2.getString("address");
		//Assert.assertEquals(newAdd, updatedAdd);
		Assert.assertTrue(newAdd.equalsIgnoreCase(updatedAdd));
	}

}
