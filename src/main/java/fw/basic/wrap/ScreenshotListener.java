package fw.basic.wrap;

import com.google.common.base.Throwables;
import fw.basic.ApplicationManager;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 20.03.13
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class ScreenshotListener implements IInvokedMethodListener2 {

    protected org.slf4j.Logger LOG = LoggerFactory.getLogger("TEST");

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod m, ITestResult res, ITestContext iTestContext) {
        if (m.isTestMethod()) {
            if (!res.isSuccess()) {
                if (res.getThrowable() == null) {
                    ApplicationManager.getThreadAppManager().get().getWebDriverHelper().stop();
                    Reporter.log("<big> <b style=\"color:red\">Setup method i failed</big></b><br/>");
                } else {
                    String errorMessage = Throwables.getStackTraceAsString(res.getThrowable());
                    if (errorMessage.contains("NoSuchElementException")) {
                        res.setStatus(3);
                        LOG.error("Error in test: " + m.getTestMethod().getMethodName());
                        LOG.error("Message: " + errorMessage);
                        Reporter.log("<b style=\"color:#FF8C00\">-------------------------------------------------------</b><br/>");
                        Reporter.log("<big> <b style=\"color:#FF8C00\">Error in test:</big> " + createTestMethodName(m) + "</b><br/>");
                    } else {
                        LOG.error("FAILED: " + m.getTestMethod().getMethodName());
                        Reporter.log("<b style=\"color:red\">-------------------------------------------------------</b><br/>");
                        Reporter.log("<big> <b style=\"color:red\">Test is failed:</big> " + createTestMethodName(m) + "</b><br/>");
                        if (ApplicationManager.getSaucelabsLogin() == null) {
                            ApplicationManager app = (ApplicationManager) iTestContext.getAttribute("application");
                            try {
                                app.getThreadAppManager().get().getWebDriverHelper().getScreenshot(res);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                LOG.info("PASSED: " + m.getTestMethod().getMethodName());
                Reporter.log("<b style=\"color:green\">-------------------------------------------------------</b><br/>");
                Reporter.log("<big> <b style=\"color:green\">Test completed successfully:</big> " + createTestMethodName(m) + "</b><br/>");
                Reporter.log("<b>-------------------------------------------------------</b><br/>");
            }
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod m, ITestResult res, ITestContext iTestContext) {
        if (m.isTestMethod()) {
            LOG.info("Test started: " + m.getTestMethod().getMethodName());
            Reporter.log("<big><b>Test started:</big> " + createTestMethodName(m) + "</b><br/>");
            Reporter.log("<b>-------------------------------------------------------</b><br/>");
        }
    }

    private String createTestMethodName(IInvokedMethod m) {
        String methodName = m.getTestMethod().getMethodName();
        String result = methodName.replace("_", " ");
       /* String[] r = methodName.split("(?=\\p{Upper})");
        String result="";
        for(int i=0; i<r.length; i++){
            result = result.concat(" ").concat(r[i]);
        }*/
        //TODO need solution for writing test methods name with space
        return result;
    }
}
