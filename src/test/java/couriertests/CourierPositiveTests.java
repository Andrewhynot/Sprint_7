package couriertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import steps.courier.CourierSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierPositiveTests {

    private CourierSteps courierSteps = new CourierSteps();

    private String login;
    private String password;
    private String name;

    @Test
    @DisplayName("Успешный кейс создания курьера")
    @Description("Проверяем, что курьера можно создать при указании обязательных полей, статус запроса 201, и текст ответа OK") // описание теста
    public void courierCreationOk() {

        login = RandomStringUtils.randomAlphabetic(7);
        password = RandomStringUtils.randomAlphabetic(7);
        name = RandomStringUtils.randomAlphabetic(7);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


        courierSteps.createCourier(login, password, name)
                .then()
                .statusCode(201)
                .body("ok", is(true));


    }


    @Test
    @DisplayName("Позитивный сценарий авторизации курьера")
    @Description("Проверяем, что курьером, который существует в системе, можно авторизоваться с валидным паролем и логином. Статус 200, система возвращает ID курьера.")
    public void courierLoginOk(){

//---------------------------Создание курьера перед логином-------------------------------------------------------------

        login = RandomStringUtils.randomAlphabetic(7);
        password = RandomStringUtils.randomAlphabetic(7);
        name = RandomStringUtils.randomAlphabetic(7);


        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


        courierSteps.createCourier(login, password, name);


//---------------------------Успешный логин курьера-------------------------------------------------------------

        courierSteps.loginCourier(login, password)
                .then()
                .statusCode(200)
                .body("id", notNullValue());

        }



    @After
    public void delete() {

//---------------------------Удаляем курьера после создания-------------------------------------------------------------

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Integer courierId = courierSteps.loginCourier(login, password)
                .then()
                .extract()
                .body()
                .path("id");

        if(courierId != null){
            courierSteps.deleteCourier(courierId);
        }
    }


}


