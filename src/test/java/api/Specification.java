package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Specification {
    public static RequestSpecification requestSpec(String url){
        return new  RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpecOk200 (){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
    public static ResponseSpecification responseSpec400 (){
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }  public static ResponseSpecification responseSpec404 (){
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
    public static ResponseSpecification responseSpec405 (){
        return new ResponseSpecBuilder()
                .expectStatusCode(405)
                .build();
    }

    public static void installSpec (RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static void installSpecification(RequestSpecification requestSpec){
        RestAssured.requestSpecification =requestSpec;
    }
    public static void installSpecification(ResponseSpecification responseSpec){
        RestAssured.responseSpecification =responseSpec;
    }
}
