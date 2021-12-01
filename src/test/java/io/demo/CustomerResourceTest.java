package io.demo;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import io.demo.models.CustomerRest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class CustomerResourceTest {

    @Test
    public void getAll() {
        given()
                .when().get("/api/v1/customers")
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {
        CustomerRest customer = createCustomer();
        CustomerRest saved = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().as(CustomerRest.class);
        CustomerRest got = given()
                .when().get("/api/v1/customers/{customerId}", saved.getId())
                .then()
                .statusCode(200)
                .extract().as(CustomerRest.class);
        assertThat(saved).isEqualTo(got);
    }

    @Test
    public void post() {
        CustomerRest customer = createCustomer();
        CustomerRest saved = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().as(CustomerRest.class);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void postFailNoFirstName() {
        CustomerRest customer = createCustomer();
        customer.setFirstName(null);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .post("/api/v1/customers")
                .then()
                .statusCode(400);
    }

    @Test
    public void put() {
        CustomerRest customer = createCustomer();
        CustomerRest saved = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().as(CustomerRest.class);
        saved.setFirstName("Updated");
        CustomerRest updated = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(saved)
                .put("/api/v1/customers")
                .then()
                .statusCode(200)
                .extract().as(CustomerRest.class);
        assertThat(updated.getFirstName()).isEqualTo("Updated");
    }

    @Test
    public void putFailNoLastName() {
        CustomerRest customer = createCustomer();
        CustomerRest saved = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(customer)
                .post("/api/v1/customers")
                .then()
                .statusCode(201)
                .extract().as(CustomerRest.class);
        saved.setLastName(null);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(saved)
                .put("/api/v1/customers")
                .then()
                .statusCode(400);
    }

    private CustomerRest createCustomer() {
        CustomerRest customer = new CustomerRest();
        customer.setFirstName(RandomStringUtils.randomAlphabetic(10));
        customer.setMiddleName(RandomStringUtils.randomAlphabetic(10));
        customer.setLastName(RandomStringUtils.randomAlphabetic(10));
        customer.setEmail(RandomStringUtils.randomAlphabetic(10) + "@rhenergy.dev");
        customer.setPhone(RandomStringUtils.randomNumeric(10));
        return customer;
    }


}
