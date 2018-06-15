package coreFunctions;

import com.thoughtworks.gauge.screenshot.ICustomScreenshotGrabber;
import mobilePages.MobileBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CustomScreenGrabber implements ICustomScreenshotGrabber {

    public byte[] takeScreenshot() {
        WebDriver driver = MobileBase.androidDriver;
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
