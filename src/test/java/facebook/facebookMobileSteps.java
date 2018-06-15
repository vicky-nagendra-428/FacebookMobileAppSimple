package facebook;

import com.thoughtworks.gauge.Step;
import mobilePages.FacebookLogin;

import static org.junit.Assert.assertTrue;

public class facebookMobileSteps {

    FacebookLogin facebookLogin = new FacebookLogin();

    @Step ("Given I open the facebook app")
    public void openTheFacebookApp() {

        assertTrue("Facebook app launch failed", facebookLogin.openTheFacebookApp());

    }

    @Step ("Then login to facebook app using <EMAIL> and <PASSWORD>")
    public void loginToFacebookUsing(String email, String password) {

        assertTrue("Login failed for facebook app ", facebookLogin.loginWithCredentials(email, password));

    }
}
