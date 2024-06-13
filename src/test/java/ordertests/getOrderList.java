package ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;

import static constants.Constants.ORDER;
import static constants.Constants.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class getOrderList {


    @Test
    @DisplayName("Позитивный сценарий получения списка заказов")
    @Description("Проверяем, что при запросе приходит непустой список доступных к взятию курьером заказов")
    public void getOrderListOk(){


        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        given()
                .header("Content-type", "application/json")
                .baseUri(URL)
                .when()
                .get(ORDER)
                .then()
                .statusCode(200)
                .body("orders", notNullValue());

    }
}
