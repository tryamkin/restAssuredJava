package pai.ItFriendlyHomeWork;
import api.Specification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.junit.runners.MethodSorters;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;


/* Ссылка на сваггер для тестирования https://petstore.swagger.io/
Создать питомца
Найти созданного питомца
Отредактировать созданного питомца
Придумать не менее 3-х негативных кейсов для тестирования API
*/

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetstoreHomeWorkTest {
    private final static String url = "https://petstore.swagger.io/v2/";


    @Test
    @Order(1)
    public void aopenTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        given().get("store/inventory")
                .then()
                .log().all();
    }

    @Test()
    @Order(2)
    public void createPetTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Map<String, String> user = new HashMap<>();
        user.put("id", "123456789");
        user.put("name", "Pug");
        user.put("status", "ThisIsMyPug");
        given().body(user)
                .post(url + "pet")
                .then().log().all()
                .body("name", Matchers.equalTo(user.get("name")));

    }

    @Nested
    @Order(3)
    @Test()
    public void findPetByIdTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        String petId = "pet/123456789";
        Response response = given()
                .get(url + petId)
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        System.out.println(jsonPath.get("name").toString());
    }

    @Test
    public void updatgExistingPetTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Map<String, String> newPet = new HashMap();
        newPet.put("id", "987654321");
        newPet.put("name", "NewPug");
        newPet.put("status", "ThisIsMyNEWPug");
        Response response = given()
                .body(newPet)
                .put(url + "pet")
                .then().log().all()
                .extract().response();

    }
    @Test
    public void updatWrongExistingPetTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpec405());
        Map<String, String> newPet = new HashMap();
        newPet.put("id", "987654321");
        newPet.put("name", "NewPug");
        newPet.put("status", "ThisIsMyNEWPug");
        Response response = given()
                .body(newPet)
                .put(url + "pet/123456789")
                .then().log().all()
                .extract().response();
    }
    @Test
    public void WrongSearchPetByStatusTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpecOk200());
        Response response =    given()
                .get(url +"pet/findByStatus?status=WrongSmt")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertFalse(jsonPath.get("status").toString().contains("status"));
        System.out.println(jsonPath.get("status").toString());
    }
    @Test
    public void WrongSearchPetByIdTest() {
        Specification.installSpec(Specification.requestSpec(url), Specification.responseSpec404());
        String wrongId = "pet/123456789987654321";
        Response response = given()
                .get(url +wrongId)
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        String msg = jsonPath.get("message");
        System.out.println(jsonPath.get("message") + " msg");
        Assert.assertEquals("Pet not found",msg);
        Assert.assertEquals(response.getStatusCode(),404);
        System.out.println(response.getStatusCode() + " status code");
    }
}
