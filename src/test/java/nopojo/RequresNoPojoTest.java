package nopojo;


import api.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/*
*
Получить список пользователей со второй страница на сайте https://reqres.in/
Убедиться что id пользователей содержаться в их avatar;
Убедиться, что еmail пользователей имеет окончание regres.in;
* */


public class RequresNoPojoTest {

    private final static String url = "https://reqres.in/";

    @Test
    public void checkAvatar() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Response response = given()
                .when()
                .get("api/users?page=2")
                .then()
                //.log().all()
                .body("page", equalTo(2))
                .body("data.id", Matchers.notNullValue())
                .body("data.email", Matchers.notNullValue())
                .body("data.first_name", Matchers.notNullValue())
                .body("data.last_name", Matchers.notNullValue())
                .body("data.avatar", Matchers.notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");


        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }

        Assert.assertTrue(emails.stream().allMatch(x -> x.endsWith("@reqres.in")));

        /*
        Вывод всех строк с емайлами
        System.out.println(response.jsonPath().getList("data.email"));
        * */
    }

    @Test ()
    @DisplayName("POST request register user")
        /* {"id": 4,
            "token": "QpwL5tke4Pnpja7X4"
           } */
    public void successUserRegistrationTest () {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", Matchers.equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));

    }

    @Test ()
    @DisplayName("POST request register user")
    public void successUserRegistrationVer2Test () {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Map<String, String> user = new HashMap<>();
        user.put ("email", "eve.holt@reqres.in");
        user.put ("password","pistol");
        Response response =
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");
        System.out.println(id + " ----  " + token);
        Assert.assertEquals(4,id);
        Assert.assertEquals("QpwL5tke4Pnpja7X4",token);

    }


    @Test
    public void checkUserTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Response response1 = given()
                .when()
                .get("api/users/2")
                .then()
                .log().all()
                // todo smtng whith body
                .extract().response();
    }

    @Test
    public void unsuccessUserRegistration(){
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpec400());
        Map<String, String> user = new HashMap<>();
        user.put("email","sydney@fife");
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("error",equalTo("Missing password"));
    }
    @Test
    public void unsuccessUserRegistrationWhithResponse(){
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpec400());
        Map<String, String> user = new HashMap<>();
        user.put("email","sydney@fife");
        Response response =
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        System.out.println(" ' " +  jsonPath.get("error")+ " ' " + " - sout jsonPath");
        String error = jsonPath.get("error");
        Assert.assertEquals("Missing password",error);
    }
}
