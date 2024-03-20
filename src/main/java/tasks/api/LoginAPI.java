package tasks.api;

//import cucumber.config.ReportPortal;
import models.User;
import io.restassured.response.Response;

public class LoginAPI {

    public Response callSignIn(User user, String basePath) {
        CommonRequest commonRequest = new CommonRequest();
        Response response = commonRequest.commonRequestLogin(user, basePath);
//        Serenity.recordReportData().asEvidence().withTitle("RESPONSE SIGN IN:  ").andContents(response.prettyPrint());
        return response;
    }
}
