package coreFunctions;

import com.thoughtworks.gauge.AfterScenario;
import mobilePages.MobileBase;

public class BeforeAfter {

    @AfterScenario
    public void endTheBrowser() {

        if (MobileBase.androidDriver != null) {
            MobileBase.androidDriver.quit();
        }
    }

}
