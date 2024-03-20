package steps.stepdefinitions;

import singleton.GVs;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class ParameterDefinitions {

    @ParameterType(".*")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);
    }

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        String enviroment = null;
        if (System.getProperty("environment") == null) {
            enviroment = "default";
        } else {
            enviroment = System.getProperty("environment");
        }
        GVs.ENVIRONMENT = enviroment;
    }
}
