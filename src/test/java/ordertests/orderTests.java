package ordertests;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class orderTests {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;
    private List<String> color;

    public orderTests(String firstName, String lastName, String address,
                     String metroStation, String phone, String rentTime,
                     String deliveryDate, String comment, List <String> color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;

    }

    @Parameterized.Parameters
    public static Object[][] orders(){
        return new Object[][]{
                {"Петр", "Петров", "Москва, кремль", "4", "89000000000", "5", "2024-06-06", "Везите быстрее", List.of("BLACK")},
                {"Иван", "Иванов", "Какая-то улица", "5", "89111111111", "6", "2024-07-06", "Просто комментарий", List.of("GRAY")},
                {"Александр", "Александров", "Питер", "6", "89222222222", "7", "2024-08-06", " ", List.of("BLACK", "GRAY")},
                {"Семен", "Семенов", "Питер", "6", "89222222222", "7", "2024-08-06", " ", null}
        };
    }

    @Test
    @DisplayName("Позитивный сценарий создания заказа")
    @Description("Проверяем, что возможно создать заказ с вариациями содержимого в поле Color")
    public void createNewOrderOk(){

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        given()
                .header("Content-type", "application/json")
                .baseUri(URL)
                .body("{\n" +
                        "    \"firstName\": \"" + firstName + "\",\n" +
                        "    \"lastName\": \"" + lastName + "\",\n" +
                        "    \"address\": \"" + address + "\",\n" +
                        "    \"metroStation\": " + metroStation + ",\n" +
                        "    \"phone\": \"" + phone + "\",\n" +
                        "    \"rentTime\": " + rentTime + ",\n" +
                        "    \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                        "    \"comment\": \"" + comment + "\",\n" +
                        "    \"color\": [\n" +
                        "        \"" + color + "\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .post(ORDER)
                .then()
                .statusCode(201)
                .body("track", notNullValue());

    }


}


