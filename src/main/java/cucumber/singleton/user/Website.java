package cucumber.singleton.user;

import cucumber.models.User;

public class Website {

    public static User getUser() {
        return new User("admin@beta.co", "Abc@12345678");
    }
}
