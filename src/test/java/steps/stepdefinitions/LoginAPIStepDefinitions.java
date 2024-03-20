package steps.stepdefinitions;

import models.User;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import singleton.GVs;
import singleton.UrlWebsiteAPI;

import tasks.api.CommonHandle;
import tasks.api.LoginAPI;
import tasks.common.CommonTask;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class LoginAPIStepDefinitions {

    public static EnvironmentVariables env;
    LoginAPI loginAPI = new LoginAPI();
    CommonHandle commonHandle = new CommonHandle();

    @Before
    public void set_the_stage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String listOfStringListsType(String cell) {
        return cell;
    }

    @Given("{word} login to web with role {string} with API")
    public void signInAPI(String name, String role) {
        User user = CommonTask.getUser(name);
        if (role.equals("buyer")) {
            GVs.ENVIRONMENT_BASE_URI = EnvironmentSpecificConfiguration.from(env).getProperty("environments.default.apiBuyer");
        }
        String basePath = UrlWebsiteAPI.SIGNIN;
        System.out.println("basePath " + GVs.ENVIRONMENT_BASE_URI + UrlWebsiteAPI.SIGNIN);

        Response response = loginAPI.callSignIn(user, basePath);
        commonHandle.handleSignInResponse(response);

    }

    /**
     * APIIIIIIIIIIIIIIIIIIIIIII
     */
    @Given("{word} login web admin by api")
    public void login_to_app_by_API(String name, User user) {
        String basePath = UrlWebsiteAPI.SIGNIN;
        Response response = loginAPI.callSignIn(user, basePath);
        // lấy uid cho api
        Serenity.setSessionVariable("uid").to(user.getEmail());
        // lấy access-token và clienID từ response Signin
        commonHandle.getInfoFromHeaderSign(response);
    }

}
