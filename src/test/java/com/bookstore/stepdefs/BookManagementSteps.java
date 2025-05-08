package com.bookstore.stepdefs;

import com.bookStore.base.Book;
import com.bookStore.base.User;
import com.bookStore.service.BookService;
import com.bookStore.service.SignUpService;
import com.bookStore.service.SignInService;
import com.bookStore.utils.ExtentReportUtil;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.junit.Assert.*;

public class BookManagementSteps {

    private Response response;
    private Book book;
    private int createdBookId;
    private String accessToken;
    private final String email = "bookflow_user_" + System.currentTimeMillis() + "@mail.com";
    private final String password = "Book@123";

    @Given("a user signs up and logs in successfully")
    public void signUpAndLogin() {
        User user = new User((int)(System.currentTimeMillis() % 100000), email, password);
        Response signUpResp = SignUpService.signUp(user);
        assertEquals(200, signUpResp.getStatusCode());
        ExtentReportUtil.logValidation("User Signup", 200, signUpResp.getStatusCode(), "User created successfully", signUpResp.getBody().asString(), true);

        Response loginResp = SignInService.login(user);
        accessToken = JsonPath.from(loginResp.getBody().asString()).getString("access_token");
        assertEquals(200, loginResp.getStatusCode());
        ExtentReportUtil.logValidation("User Login", 200, loginResp.getStatusCode(), "Login successful", loginResp.getBody().asString(), true);
    }

    @Given("a book payload with name {string}, author {string}, year {int}, and summary {string} is prepared")
    public void prepareBookPayload(String name, String author, int year, String summary) {
        book = new Book(name, author, year, summary);
        ExtentReportUtil.step("INFO", "Prepared book payload: " + book);
    }

    @When("user sends a request to create a new book")
    public void createBookRequest() {
        response = BookService.createBook(book, accessToken);
        String body = response.getBody().asString();
        int status = response.getStatusCode();
        ExtentReportUtil.step("INFO", "Book creation request response:\nStatus: " + status + "\nBody: " + body);
        if (status == 200 && body != null && !body.trim().isEmpty()) {
            createdBookId = JsonPath.from(body).getInt("id");
        }
    }

    @Then("validate book creation response code is {int} and response contains book details")
    public void validateCreateBookResponse(int expectedCode) {
        int actualCode = response.getStatusCode();
        String body = response.getBody().asString();
        String name = JsonPath.from(body).getString("name");

        boolean passed = actualCode == expectedCode && name.equals(book.getName());
        ExtentReportUtil.logValidation("Book Creation", expectedCode, actualCode, book.getName(), name, passed);

        assertEquals(expectedCode, actualCode);
        assertEquals(book.getName(), name);
    }

    @When("user fetches book by valid ID")
    public void fetchBookByValidId() {
        response = BookService.getBookById(createdBookId, accessToken);
        ExtentReportUtil.step("INFO", "Fetching book by valid ID: " + createdBookId + " Response Body: " + response.getBody().asString());
    }

    @Then("verify single book fetch response code is {int} and book name is {string}")
    public void validateSingleBookResponse(int expectedCode, String expectedName) {
        int actualCode = response.getStatusCode();
        String name = JsonPath.from(response.getBody().asString()).getString("name");

        boolean passed = actualCode == expectedCode && expectedName.equals(name);
        ExtentReportUtil.logValidation("Fetch Book by ID", expectedCode, actualCode, expectedName, name, passed);

        assertEquals(expectedCode, actualCode);
        assertEquals(expectedName, name);
    }

}
