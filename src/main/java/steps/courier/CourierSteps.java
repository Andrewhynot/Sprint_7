package steps.courier;
import dto.courier.CourierCreateModel;
import dto.courier.CourierLoginModel;
import io.restassured.response.Response;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;


public class CourierSteps {


    public Response createCourier(String login, String password, String name){

        CourierCreateModel courier = new CourierCreateModel(login, password, name);

        return given()
                .header("Content-type", "application/json")
                .baseUri(URL)
                .body(courier)
                .when()
                .post(CREATE_COURIER);

    }


    public Response loginCourier(String login, String password){

        CourierLoginModel courier = new CourierLoginModel(login, password);

        return  given()
                .header("Content-type", "application/json")
                .baseUri(URL)
                .body(courier)
                .when()
                .post(LOGIN_COURIER);


    }

    public Response deleteCourier(Integer id){
        return given()
                .header("Content-type", "application/json")
                .baseUri(URL)
                .pathParam("id", id)
                .when()
                .delete(CREATE_COURIER + "/{id}");
    }

}
