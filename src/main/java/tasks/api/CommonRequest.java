package tasks.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class CommonRequest {

    public Response commonRequestLogin(Object modelApi, String url) {
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json").
                headers("Content-Type", ContentType.JSON).
                body(modelApi).
                when().request("POST").
                then().log().all().
                assertThat().
                statusCode(200).
                extract().
                response();
    }

    public Response commonRequest(Object modelApi, String url, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                body(modelApi).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public static Response callCommonApiTest(JSONObject body, String url) {
        HashMap<String, Object> param = new HashMap<>();
        try {
            param = (HashMap<String, Object>) new ObjectMapper().readValue(body.toString(), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");

        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;
        Response response = given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                when().
                body(param).
                post().
                then().log().all().
//                statusCode(200).
        extract().
                response();
        return response;
    }

    public static Response commonRequestFormData(String url, String fileName1) throws URISyntaxException {
        // get địa chỉ tương đối của file
        ClassLoader classLoader = CommonRequest.class.getClassLoader();
        File file1 = new File(classLoader.getResource(fileName1).toURI().getPath());
        System.out.println("file = " + file1);
        RestAssured.basePath = url;

        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        Response response = given()
                .accept("application/json, text/plain, */*")
                .contentType("multipart/form-data")
                .headers("token-type", "Bearer")
                .headers("access-token", accessToken)
                .headers("client", client)
                .headers("uid", uid)

                .formParam("draft", "true")
                .formParam("product_variant[manufacturer_address_attributes][city]", "Chicago")
                .formParam("product_variant[manufacturer_address_attributes][address_state_id]", "5")
                .formParam("product_variant[storage_shelf_life_attributes][shelf_life_condition_id]", "1")
                .formParam("product_variant[storage_shelf_life_attributes][days]", "1")
                .formParam("product_variant[retail_shelf_life_attributes][shelf_life_condition_id]", "1")
                .formParam("product_variant[retail_shelf_life_attributes][days]", "1")
                .formParam("product_variant[quality_ids]", "15")
                .formParam("product_variant[nutrition_labels_attributes][0][timeStamp]", "1651734405748")
                .formParam("product_variant[nutrition_labels_attributes][0][attachment]", file1, "image/jpeg")
                .formParam("product_variant[nutrition_labels_attributes][0][description]", "")
                .formParam("product_variant[variants_regions_attributes][0][region_id]", "26")
                .formParam("product_variant[variants_regions_attributes][0][state]", "active")
                .formParam("product_variant[variants_regions_attributes][0][availability]", "in_stock")
                .formParam("product_variant[variants_regions_attributes][0][case_price_cents]", "11000")
                .formParam("product_variant[variants_regions_attributes][0][msrp_cents]", "11000")
                .formParam("product_variant[barcode_attributes][type]", "Barcodes::Upc")
                .formParam("product_variant[master_image_attributes][attachment]", file1, "image/jpeg")
                .formParam("product_variant[barcode_attributes][image_attributes][attachment]", file1, "image/jpeg")
                .formParam("product_variant[barcode_attributes][case_image_attributes][attachment]", file1, "image/jpeg")
                .formParam("product_variant[barcode_attributes][code]", "123456789012")
                .formParam("product_variant[barcode_attributes][case_code]", "1")
                .formParam("product_variant[name]", "Autotest sku api postman1")
                .formParam("product_variant[ingredients]", "Auto Ingredients")
                .formParam("product_variant[description]", "Auto Description")
                .formParam("product_variant[lead_time]", "1")
                .formParam("product_variant[case_units]", "1")
                .formParam("product_variant[min_temperature]", "1")
                .formParam("product_variant[max_temperature]", "1")
                .post()
                .then()
//                statusCode(201).
                .extract()
                .response();
        return response;
    }

    public Response commonRequestWithParam(String url, Map<String, Object> map, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().queryParams(map).log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestWithParamMultiPart(String url, RequestSpecification requestSpec, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType(MediaType.MULTIPART_FORM_DATA.toString()).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                spec(requestSpec).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestWithParam3(String url, RequestSpecification requestSpec, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                spec(requestSpec).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestWithParam2(String url, Map<String, String> map, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().queryParams(map).log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestWithBody(String url, Map<String, Object> map, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                body(map).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestWithBody(String url, Map<String, Object> map, String method, int statusCode) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                body(map).
                when().request(method).
                then().statusCode(statusCode).log().all().
                extract().
                response();
    }

    public Response commonRequestNoBody(String url, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestNoBody(String url, String method, Integer statusCode) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                when().request(method).
                then().statusCode(statusCode).log().all().
                extract().
                response();
    }

    public Response commonRequestWithBody2(String url, Object map, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        return given().log().all().
                contentType("application/json;charset=UTF-8").
                headers("Content-Type", ContentType.JSON).
                headers("token-type", "Bearer").
                headers("access-token", accessToken).
                headers("client", client).
                headers("uid", uid).
                body(map).
                when().request(method).
                then().log().all().
                extract().
                response();
    }

    public Response commonRequestMultiPart(String url, RequestSpecification requestSpec, String method) {
        String accessToken = Serenity.sessionVariableCalled("accessToken");
        String client = Serenity.sessionVariableCalled("client");
        String uid = Serenity.sessionVariableCalled("uid");
        RestAssured.baseURI = url;
        RestAssuredConfig config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 30000));

        RequestSpecification requestHeader = new RequestSpecBuilder()
                .addHeader("token-type", "Bearer")
                .addHeader("access-token", accessToken)
                .addHeader("client", client)
                .addHeader("uid", uid)
                .build();

        Response response = given()
                .contentType(MediaType.MULTIPART_FORM_DATA.toString())
                .spec(requestHeader)
                // body
                .spec(requestSpec)
                .when()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                .config(config)
                .request(method)
                .then()
                .extract()
                .response();
        return response;
    }
}
