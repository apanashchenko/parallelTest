package fw.basic.helpers;

import fw.basic.ApplicationManager;
import fw.basic.data.BaseDataProvider;
import fw.basic.data.WebDriverBaseDataProviders;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import ru.yandex.qatools.htmlelements.element.*;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: alpa
 * Date: 31/05/13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */

/**
 * Parent class for general methods with WebDriver and HTML Elements.
 */
public class BaseWebDriverHelper implements WebDriverBaseDataProviders, BaseDataProvider {

    protected ApplicationManager manager;
    private WebDriver driver;
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(BaseWebDriverHelper.class);

    public BaseWebDriverHelper(ApplicationManager manager) {
        this.manager = manager;
        driver = manager.getWebDriverHelper().getDriver();
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
        System.out.println("!!!!!!!!!!!!!!!!!!BaseWebDriverHelper " + Thread.currentThread().getId());
    }

    /**
     * Get WebDriver instance
     *
     * @return current WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Check visibility of element
     *
     * @param locator element locator
     * @return boolean
     */
    protected boolean isElementVisible(By locator) {
        try {
            driver.findElement(locator);
            LOG.info("Element is visible");
            return true;
        } catch (NoSuchElementException e) {
            LOG.info("Element is not visible" + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     *
     * @param element element
     * @return boolean
     */
    protected boolean isElementVisible(WebElement element) {
        try {
            element.isDisplayed();
            LOG.info("Element is visible");
            return true;
        } catch (NoSuchElementException e) {
            LOG.info("Element is not visible" + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     *
     * @param textInput TextInput element
     * @return boolean
     */
    protected boolean isElementVisible(TextInput textInput) {
        try {
            textInput.isDisplayed();
            LOG.info("Text field is visible");
            return true;
        } catch (NoSuchElementException e) {
            LOG.info("Text field is not visible " + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     *
     * @param button Button element
     * @return boolean
     */
    protected boolean isElementVisible(Button button) {
        try {
            button.isDisplayed();
            LOG.info("Button is visible");
            return true;
        } catch (NoSuchElementException e) {
            LOG.info("Button is not visible" + e);
            return false;
        }
    }

    /**
     * Check visibility of element
     *
     * @param link Link element
     * @return boolean
     */
    protected boolean isElementVisible(Link link) {
        try {
            link.isDisplayed();
            LOG.info("Text field is visible");
            return true;
        } catch (NoSuchElementException e) {
            LOG.info("Text field is not visible " + e);
            return false;
        }
    }

    /**
     * Check alert present
     *
     * @return boolean
     */
    protected boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
                LOG.info("Alert is not present");
                return false;
            } else {
                LOG.info("Alert is present");
                return true;
            }
        } catch (Exception e) {
            LOG.info("Alert is not present");
        }
        return false;
    }

    /**
     * Wait for alert present
     */
    private Alert waitForAlert(WebDriver driver, int seconds) {
        Wait<WebDriver> wait = new WebDriverWait(driver, seconds).ignoring(NullPointerException.class);
        Alert alert = wait.until(new ExpectedCondition<Alert>() {
            @Override
            public Alert apply(WebDriver driver) {
                Alert alert = driver.switchTo().alert();
                alert.getText();
                return alert;
            }
        });
        return alert;
    }

    /**
     * Get alert for action
     *
     * @return Alert element
     */
    protected Alert getAlert() {
        return waitForAlert(driver, 10);
    }

    /**
     * Type data into element
     *
     * @param locator element locator
     * @param text    text
     */
    protected void inputText(By locator, String text) {
        findElement(locator).clear();
        findElement(locator).sendKeys(text);
        LOG.info("Type info: " + text + " to element found " + locator);
    }

    /**
     * Get TextInput Properties
     *
     * @param element TextInput element
     */
    protected void inputText(TextInput element, String text) {
        element.clear();
        element.sendKeys(text);
        LOG.info("Input text " + text + ", in " + element.getName() + " field");
        reporterLog("Input text " + text + ", in " + element.getName() + " field");
    }

    /**
     * Get TextInput Properties
     *
     * @param element TextInput element
     */
    protected void inputText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        LOG.info("Input text " + text + ", in " + element + " field");
    }

    /**
     * Input data character by character
     */
    public void waitElementDuringSendKeys(String el, WebElement element) {
        String elLowerCase = el.toLowerCase().trim();
        for (int i = 0; i < el.length(); i++) {
            element.sendKeys(Character.toString(elLowerCase.charAt(i)));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // element.sendKeys(" " + Keys.BACK_SPACE);
        element.sendKeys(" ".trim());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver.findElement(By.linkText(el)).click();
    }

    /**
     * Input data character by character
     */
    public void waitElementDuringSendKeys(String el, TextInput element) {
        String elLowerCase = el.toLowerCase().trim();
        for (int i = 0; i < el.length(); i++) {
            element.sendKeys(Character.toString(elLowerCase.charAt(i)));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        element.sendKeys(" ".trim());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill form
     *
     * @param form Form element
     * @param data data and object for input
     */
    protected void fillForm(Form form, Map<String, Object> data) {
        form.fill(data);
        LOG.info("Fill " + form.getName());
    }

    /**
     * Find element
     *
     * @param locator element locator
     * @return WebElement
     */
    protected WebElement findElement(By locator) {
        LOG.info("Find element: " + locator);
        return driver.findElement(locator);
    }

    /**
     * Find List of elements
     *
     * @param locator element locator
     * @return List of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        LOG.info("Find elements: " + locator);
        return driver.findElements(locator);
    }

    /**
     * Click on element
     *
     * @param locator element locator
     */
    protected void clickOnButton(By locator) {
        LOG.info("Click on element: " + locator);
        driver.findElement(locator).click();
    }

    /**
     * Click on element
     *
     * @param element WebElement locator
     */
    protected void clickOnButton(WebElement element) {
        LOG.info("Click on element: " + element);
        element.click();
    }

    /**
     * Click on button
     *
     * @param btn Button element
     */
    protected void clickOnButton(Button btn) {
        btn.click();
        LOG.info("Click on button " + btn.getName());
        reporterLog("Click on " + btn.getName());
    }

    /**
     * Open URL in browser
     *
     * @param url URL
     */
    protected void openUrl(String url) {
        driver.get(url);
        LOG.info("Open URL: " + url);
    }

    /**
     * Get current URL from browser
     *
     * @return String current URL
     */
    protected String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        LOG.info("Get current url: " + currentUrl);
        return currentUrl;
    }

    /**
     * Refresh current pages
     */
    public void refreshPage() {
        driver.navigate().refresh();
        reporterLog("Refresh pages");
    }

    /**
     * Implicitly Wait
     *
     * @param i number in second
     */
    protected void implicitlyWait(int i) {
        driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
    }

    /**
     * Thread sleep
     *
     * @param seconds input seconds
     */
    protected void waitABit(int seconds) {
        int i = seconds * 1000;
        try {
            Thread.currentThread().sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait for complete ajax request
     */
    protected ExpectedCondition<Boolean> ajaxComplete() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                long counter = (Long) ((JavascriptExecutor) d).executeScript(
                        "return window.jQuery.active", new Object[]{});
                return counter == 0;
            }
        };
    }

    /**
     * Wait for element present
     *
     * @param locator element locator
     */
    protected void waitElementPresent(By locator) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.presenceOfElementLocated((locator)));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element present
     *
     * @param element element locator
     */
    protected void waitElementPresent(WebElement element) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOf(element));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element present
     *
     * @param locator element locator
     */
    protected void waitElementPresent(By locator, int seconds) {
        new WebDriverWait(driver, seconds).until(
                ExpectedConditions.presenceOfElementLocated((locator)));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element visibility
     *
     * @param locator element locator
     */
    protected void waitElementVisibility(By locator) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOfElementLocated((locator)));
        LOG.info("Wait for element present");
    }

    /**
     * Wait for element invisibility
     *
     * @param locator element locator
     */
    protected void waitElementInvisibility(By locator) {
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.invisibilityOfElementLocated((locator)));
        LOG.info("Wait for element invisibility");
    }

    /**
     * Get pages title
     *
     * @return String page title from
     *         <head>
     *         <title>TITLE_VALUE</title>
     *         </head>
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        LOG.info("Get title: " + title);
        return title;
    }

    /**
     * Set value for attribute
     *
     * @param locator   element locator
     * @param attribute name attribute
     * @param value     value attribute
     */
    public void setAttribute(By locator, String attribute, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('" + attribute
                + "',arguments[1]);",
                findElement(locator),
                value);
        LOG.info("Set attribute: " + attribute + " Value: " + value);
    }

    /**
     * Set value for attribute
     *
     * @param element   WebElement element
     * @param attribute name attribute
     * @param value     value attribute
     */
    public void setAttribute(WebElement element, String attribute, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('" + attribute
                + "',arguments[1]);", element, value);
        LOG.info("Set attribute: " + attribute + " Value: " + value);
    }

    /**
     * Get browser type for fix errors concerning cross-browsers
     *
     * @return String browser type
     */
    public String getBrowser() {
        String browser = ApplicationManager.getBrowserType();
        LOG.info("Browser type: " + browser);
        return browser;
    }

    /**
     * Added log message in report
     *
     * @param message text message
     */
    public void reporterLog(String message) {
        Reporter.log(message + " </br>");
    }

    /**
     * Get element text
     *
     * @param locator element locator
     * @return String WebElement text
     */
    protected String getElementText(By locator) {
        WebElement element = findElement(locator);
        String elementText = element.getText().trim();
        LOG.info("Get element text: " + elementText);
        return elementText;
    }

    /**
     * Get element text
     *
     * @param element element locator
     * @return String WebElement text
     */
    protected String getElementText(TextInput element) {
        String elementText = element.getText().trim();
        LOG.info("Get element text: " + elementText);
        return element.getText().trim();
    }

    /**
     * Get element text
     *
     * @param locator   element locator
     * @param attribute element attribute
     * @return String WebElement attribute
     */
    protected String getElementAttribute(By locator, String attribute) {
        WebElement element = findElement(locator);
        String elementAttribute = element.getAttribute(attribute).trim();
        LOG.info("Get element attribute: " + elementAttribute);
        return elementAttribute;
    }

    /**
     * Get element attribute
     *
     * @param button    Button button
     * @param attribute element attribute
     * @return String Button attribute value
     */
    protected String getElementAttribute(Button button, String attribute) {
        return button.getWrappedElement().getAttribute(attribute).trim();
    }

    /**
     * Get element text
     *
     * @param button element locator
     * @return String Button text
     */
    protected String getElementText(Button button) {
        String elementText = button.getWrappedElement().getText().trim();
        LOG.info("Get element text: " + elementText);
        return button.getWrappedElement().getText().trim();
    }

    /**
     * Get element text
     *
     * @param checkBox element locator
     * @return String CheckBox text
     */
    protected String getElementText(CheckBox checkBox) {
        String elementText = checkBox.getText().trim();
        LOG.info("Get element text: " + elementText);
        return checkBox.getText().trim();
    }

    /**
     * Get element text
     *
     * @param element element locator
     */
    protected String getElementText(WebElement element) {
        return element.getText().trim();
    }

    /**                               //strong[contains(text(),'Rep Number:')]/following-sibling::span
     * Get TextInput element
     *
     * @param element TextInput element
     * @return TextInput element
     */
    protected TextInput getElementOptions(TextInput element) {
        return element;
    }

    /**
     * Get Button properties
     *
     * @param element Button element
     * @return Button element
     */
    protected Button getElementOptions(Button element) {
        return element;
    }

    /**
     * Get CheckBox properties
     *
     * @param checkBox CheckBox element
     * @return CheckBox element
     */
    protected CheckBox getElementOptions(CheckBox checkBox) {
        return checkBox;
    }

    /**
     * Get Select properties
     *
     * @param select CheckBox element
     * @return Select element
     */
    protected Select getElementOptions(Select select) {
        return select;
    }

    /**
     * Get table options
     *
     * @param table Table element
     * @return Table element
     */
    protected Table getElementOptions(Table table) {
        return table;
    }

    /**
     * Get link options
     *
     * @param link Select element
     * @return Link element
     */
    protected Link getElementOptions(Link link) {
        return link;
    }

    /**
     * Get Form options
     *
     * @param form Form element
     * @return Form element
     */
    protected Form getElementOptions(Form form) {
        return form;
    }

    /**
     * Check checkBox
     *
     * @param checkBox CheckBox element
     */
    protected void checkCheckbox(CheckBox checkBox) {
        checkBox.select();
        LOG.info("Check " + checkBox.getName());
    }

    /**
     * Uncheck checkBox
     *
     * @param checkBox CheckBox element
     */
    protected void unCheckbox(CheckBox checkBox) {
        checkBox.deselect();
        LOG.info("Uncheck " + checkBox.getName());
    }

    /**
     * Select by visible text options
     *
     * @param select Select element
     */
    protected void selectByVisibleText(Select select, String itemName) {
        select.selectByVisibleText(itemName);
        LOG.info("Select item by visible text: " + itemName + ", from " + select.getName());
    }

    /**
     * Select by index options
     *
     * @param select Select element
     */
    protected void selectByIndex(Select select, int indexNumber) {
        select.selectByIndex(indexNumber);
        LOG.info("Select item by index: " + indexNumber + ", from " + select.getName());
    }

    /**
     * Select by index options
     *
     * @param select Select element
     */
    protected void selectByValue(Select select, String valueName) {
        select.selectByValue(valueName);
        LOG.info("Select item by value: " + valueName + ", from " + select.getName());
    }

    /**
     * Get link
     *
     * @param link Link link
     */
    protected void getLink(Link link) {
        link.getReference();
    }

    /**
     * Get link
     *
     * @param link Link link
     * @return String link text
     */
    protected String getLinkText(Link link) {
        String linkText = link.getText().trim();
        LOG.info("Get link text: " + linkText);
        return linkText;
    }


    /**
     * Get WebElement from HTML element
     *
     * @param element TextInput element
     * @return TextInput webElement
     */
    protected WebElement getWrappedElement(TextInput element) {
        return element.getWrappedElement();
    }

    /**
     * Get WebElement from HTML element
     *
     * @param element Button element
     * @return Button webElement
     */
    protected WebElement getWrappedElement(Button element) {
        return element.getWrappedElement();
    }

    /**
     * Get WebElement from HTML element
     *
     * @param element CheckBox element
     * @return CheckBox webElement
     */
    protected WebElement getWrappedElement(CheckBox element) {
        return element.getWrappedElement();
    }

    /**
     * Get WebElement from HTML element
     *
     * @param element Select element
     * @return Select webElement
     */
    protected WebElement getWrappedElement(Select element) {
        return element.getWrappedElement();
    }

    /**
     * Get WebElement from HTML element
     *
     * @param table Table element
     * @return Table webElement
     */
    protected WebElement getWrappedElement(Table table) {
        return table.getWrappedElement();
    }

    /**
     * Get WebElement from HTML element
     *
     * @param link Link element
     * @return Link webElement
     */
    protected WebElement getWrappedElement(Link link) {
        return link.getWrappedElement();
    }

    /**
     * Get WebElement from HTML element
     *
     * @param form Form element
     * @return Form webElement
     */
    protected WebElement getWrappedElement(Form form) {
        return form.getWrappedElement();
    }

    /**
     * Assert visibility of web element
     *
     * @param textInput TextInput element
     * @param message   String text message
     */
    protected void assertVisibility(TextInput textInput, String message) {
        assertTrue(isElementVisible(textInput), message);
    }

    /**
     * Assert visibility of web element
     *
     * @param button  Button element
     * @param message String text message
     */
    protected void assertVisibility(Button button, String message) {
        assertTrue(isElementVisible(button), message);
    }

    /**
     * Assert visibility of web element
     *
     * @param locator Web element locator
     * @param message String text message
     */
    protected void assertVisibility(By locator, String message) {
        assertTrue(isElementVisible(locator), message);
    }

    /**
     * Assert visibility of web element
     *
     * @param link    Link element
     * @param message String text message
     */
    protected void assertVisibility(Link link, String message) {
        assertTrue(isElementVisible(link), message);
    }

    /**
     * Assert visibility of web element
     *
     * @param element WebElement element
     * @param message String text message
     */
    protected void assertVisibility(WebElement element, String message) {
        assertTrue(isElementVisible(element), message);
    }

    /**
     * Assert that element not visible
     *
     * @param locator Element locator
     * @param message String text message
     */
    protected void assertNotVisibility(By locator, String message) {
        assertFalse(isElementVisible(locator), message);
    }

    /**
     * Assert that element not visible
     *
     * @param element WebElement element
     * @param message String text message
     */
    protected void assertNotVisibility(WebElement element, String message) {
        assertFalse(isElementVisible(element), message);
    }

    /**
     * Assert String value is equals
     *
     * @param actualValue   actual String value
     * @param expectedValue expected String value
     * @param message       String text message
     */
    protected void isStringsEquals(String expectedValue, String actualValue, String message) {
        assertTrue(expectedValue.equals(actualValue), message);
    }

    /**
     * Assert that String contains value
     *
     * @param actualValue   actual String value
     * @param expectedValue expected String value
     * @param message       String text message
     */
    protected void isStringContains(String expectedValue, String actualValue, String message) {
        assertTrue(actualValue.contains(expectedValue), message);
    }

    /**
     * Assert that String value not empty
     *
     * @param expectedValue expected String value
     * @param message       String text message
     */
    protected void isStringNotEmpty(String expectedValue, String message) {
        assertFalse(expectedValue.isEmpty(), message);
    }

    /**
     * Assert that int value equals
     *
     * @param actualValue   actual int value
     * @param expectedValue int String value
     * @param message       String text message
     */
    protected void isIntEquals(int expectedValue, int actualValue, String message) {
        assertTrue(expectedValue == actualValue, message);
    }

    /**
     * Assert that int value not equals
     *
     * @param actualValue   actual int value
     * @param expectedValue int String value
     * @param message       String text message
     */
    protected void isIntNotEquals(int expectedValue, int actualValue, String message) {
        assertFalse(expectedValue == actualValue, message);
    }

    /**
     * Assert that int value equals not equals 0
     *
     * @param expectedValue int String value
     * @param message       String text message
     */
    protected void isIntNotZero(int expectedValue, String message) {
        assertTrue(expectedValue != 0, message);
    }

    /**
     * Get size of web element
     *
     * @param xpathExpression xpath expression
     * @return int element numbers
     */
    protected int getSize(String xpathExpression) {
        List<WebElement> webElementList = driver.findElements(By.xpath(xpathExpression));
        int size = webElementList.size();
        LOG.info("Get size: " + size);
        return size;
    }

    /**
     * Return current window handle
     *
     * @return String window handle
     */
    public String getCurrentWindow() {
        String windowHandle = driver.getWindowHandle();
        LOG.info("Get current window handle : " + windowHandle);
        return windowHandle;
    }

    /**
     * Switch on active window or popup
     */
    public void switchWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        LOG.info("Switch on active window handle");
    }

    /**
     * Switch on active window or popup
     *
     * @param winHandleBefore window handle
     */
    public void switchWindow(String winHandleBefore) {
        driver.switchTo().window(winHandleBefore);
    }

    /**
     * Close active window or popup
     */
    public void closeCurrentWindow() {
        driver.close();
    }

    /**
     * Get time for page load
     *
     * @return String time value millisecond
     */
    public String getTimePageLoad() {
        String time = ((JavascriptExecutor) getDriver()).executeScript("return ( window.performance.timing.loadEventEnd - window.performance.timing.navigationStart )").toString();
        return time;
    }
}
