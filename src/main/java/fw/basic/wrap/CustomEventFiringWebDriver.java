package fw.basic.wrap;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UselessFileDetector;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 03/06/13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class CustomEventFiringWebDriver extends EventFiringWebDriver {

    private FileDetector fileDetector = new UselessFileDetector();

    public CustomEventFiringWebDriver(RemoteWebDriver driver) {
        super(driver);

    }
    public void setFileDetector(FileDetector detector) {
        if (detector == null) {
            throw new WebDriverException("You may not set a file detector that is null");
        }
        //((RemoteWebDriver) getWrappedDriver()).setFileDetector(detector);
        fileDetector = detector;
    }
    public FileDetector getFileDetector() {
        return fileDetector;
    }
}
