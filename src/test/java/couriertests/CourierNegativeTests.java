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

public class CourierNegativeTests {

    private CourierSteps courierSteps = new CourierSteps();

    private String login;
    private String password;
    private String name;

    @Test
    @DisplayName("Негативный сценарий создания курьера без поля логин")
    @Description("Проверяем, что курьера невозможно создать без указания поля логин в теле запроса. 400 ошибка, Недостаточно данных для создания учетной записи")
    public void courierCreationBadRequestLogin(){

        password = RandomStringUtils.randomAlphabetic(7);
        name = RandomStringUtils.randomAlphabetic(7);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


        courierSteps.createCourier(null, password, name)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }


    @Test
    @DisplayName("Негативный сценарий создания курьера без поля пароль")
    @Description("Проверяем, что курьера невозможно создать без указания поля пароль в теле запроса. 400 ошибка, Недостаточно данных для создания учетной записи")
    public void courierCreationBadRequestPassword(){

        login = RandomStringUtils.randomAlphabetic(7);
        name = RandomStringUtils.randomAlphabetic(7);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


        courierSteps.createCourier(login, null, name)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Негативный сценарий создания курьера с одинаковым логином")
    @Description("Проверяем, что невозможно создать курьера с уже имееющимся в БД логином. 409 ошибка, Этот логин уже используется")
    public void courierCreationConflict(){

        login = "ninja";
        password = "1234";
        name = "saske";

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        courierSteps.createCourier(login, password, name)
                .then()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Негативный сценарий авторизации курьера без поля логин")
    @Description("Проверяем, что невозможно выполнить авторизацию без указания поля login. 400 ошибка, Недостаточно данных для входа")
    public void courierLoginBadRequestLogin(){

        password = RandomStringUtils.randomAlphabetic(7);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        courierSteps.loginCourier(null, password)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));

    }


    @Test
    @DisplayName("Негативный сценарий авторизации курьера без поля пароль") // имя теста
    @Description("Проверяем, что невозможно выполнить авторизацию без указания поля password. 400 ошибка, Недостаточно данных для входа")
    public void courierLoginBadRequestPassword(){
        login = RandomStringUtils.randomAlphabetic(7);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        courierSteps.loginCourier(login, null)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Негативный сценарий авторизации курьера с несуществующим в системе логином и паролем") // имя теста
    @Description("Проверяем, что невозможно выполнить авторизацию с логином и паролем, не существующим в БД. 404 ошибка, Учетная запись не найдена")
    public void courierLoginNotFound(){
        login = RandomStringUtils.randomAlphabetic(7);
        password = RandomStringUtils.randomAlphabetic(7);

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());


        courierSteps.loginCourier(login, password)
                .then()
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));

    }


}








