package api;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
public class ReqrestTest {

    private final static String url = "https://reqres.in/";

    @Test()
    public void acheckAvatarIdTest() {
        List<UserData> users = given()
                .when().contentType(ContentType.JSON)
                .get(url+"api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
      // первое сравнение - что в аватаре ест айди
        for (int i = 0; i <users.size() ; i++) {
            System.out.println(users.get(i).getFirst_name());

        Assert.assertTrue(users.get(i).getAvatar().contains(users.get(i).getId().toString()));
        }
        // через стримы
        users.stream().forEach(x->Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        //проверка что все поля почты заканчиваються на @reqres.in
        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().contains("@reqres.in")));
        users.stream().forEach(x->Assert.assertTrue(x.getEmail().contains("@reqres.in"))); // my
       //Достать только аватарки и загнать их в лист
        List<String> avatars = new ArrayList<>();
            for (UserData user : users) {
                avatars.add(user.getAvatar());
            }
            System.out.println(avatars.size() + " avatars size");
            System.out.println(avatars + " avatars");
      //через стимы сравнение - что в аватаре ест айди
      List<String>avatarsListStream = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
      List<String> ids = users.stream().map(x->x.getAvatar().toString()).collect(Collectors.toList());
        for (int i = 0; i <avatarsListStream.size() ; i++) {
             Assert.assertTrue(avatarsListStream.get(i).contains(ids.get(i)));
             Assert.assertTrue(avatarsListStream.get(i).contains(ids.get(i)));
        }
    }
    @Ignore
    @DisplayName("2 test")
    @Test()
    public void checkAvatarIdTestUseSpec () {
        //вызываем методы класса Specifications !!! Magic here!!!
        Specification.installSpec(Specification.requestSpec(url),Specification.responseSpecOk200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        Assert.assertTrue(users.stream().allMatch(x->x.getAvatar().contains(x.getId().toString())));

    }

//    @org.junit.jupiter.api.Test
//    @DisplayName("Аватары содержат айди пользователей")
//    public void checkAvatarContainsIdTest(){
//       // Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
//        //1 способ сравнивать значения напрямую из экземпляров класса
//        List<UserData> users = given()
//                .when()
//                .get("api/users?page=2")
//                .then()
//                .log().all()
//                .extract().body().jsonPath().getList("data", UserData.class);
//
//        //проверка аватар содержит айди
//        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
//        //проверка почты оканчиваются на reqres.in
//        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
//
//        //2 способ сравнивать значения через получения списков
//        //список с аватарками
//        List<String> realPeopleAvatars  = users.stream()
//                .map(UserData::getAvatar)
//                .collect(Collectors.toList());
//        //список с айди
//        List<String> realPeopleIds = users.stream()
//                .map(x->x.getId().toString())
//                .collect(Collectors.toList());
//        //проверка через сравнение двух списков
//        for (int i = 0; i<realPeopleAvatars.size(); i++){
//            Assertions.assertTrue(realPeopleAvatars.get(i).contains(realPeopleIds.get(i)));
//        }
//    }


}
