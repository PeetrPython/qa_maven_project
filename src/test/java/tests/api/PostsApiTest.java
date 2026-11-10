package tests.api;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class PostsApiTest {

    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    private static RequestSpecification apiRequest() {
        return given().baseUri(BASE_URI);
    }

    @Test
    void getsPostById() {
        apiRequest()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void returnsNotFoundForMissingPost() {
        apiRequest()
                .when()
                .get("/posts/999999")
                .then()
                .statusCode(404);
    }


    @Test
    void returnsCommentsForRetrievedPost() {
        int postId = apiRequest()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .extract()
                .path("id");
        apiRequest()
                .when()
                .get("/posts/{id}/comments", postId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(postId)));
    }

    @Test
    void filtersCommentsByPostId() {
        int requestedPostId = 8;

        apiRequest()
                .queryParam("postId", requestedPostId)
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("postId", everyItem(equalTo(requestedPostId)));
    }

    @Test
    void updatesPostTitle() {
        int postId = 1;

        apiRequest()
                .contentType("application/json")
                .body("""
                                {
                          "title": "Updated title"
                        }
                        """)
                .when()
                .patch("/posts/{id}", postId)
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated title"))
                .body("id", equalTo(postId));
    }

    @Test
    void deletesPostById() {
        int postId = 1;

        apiRequest()
                .contentType("application/json")
                .when()
                .delete("/posts/{id}", postId)
                .then()
                .statusCode(200);
    }

    @Test
    void returnsCreatedPostMatchingSchema() {
        apiRequest()
                .contentType("application/json")
                .body(new File("src/test/resources/create-post.json"))
                .post("/posts")
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("create-post-response.json"))
                .body("userId", equalTo(1))
                .body("title", equalTo("My first API test"));
    }

    @Test
    void returnsReplacementPostAfterPut() {
        int postId = 1;

        apiRequest()
                .contentType("application/json")
                .body(new File("src/test/resources/replace-post.json"))
                .put("/posts/{id}", postId)
                .then()
                .statusCode(200)
                .body("id", equalTo(postId))
                .body("title", equalTo("Replacement title"))
                .body("body", equalTo("Replacement body"))
                .body("userId", equalTo(2));
    }
}