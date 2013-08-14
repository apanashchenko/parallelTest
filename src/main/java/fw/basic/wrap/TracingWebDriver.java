package fw.basic.wrap;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.BuildInfo;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TracingWebDriver extends RemoteWebDriver implements WebDriver, JavascriptExecutor,
    TakesScreenshot, WrapsDriver, HasInputDevices, HasTouchScreen {

  private static Logger log = LoggerFactory.getLogger("WebDriver");
  private static Logger LOG = LoggerFactory.getLogger(TracingWebDriver.class);
  private final WebDriver driver;
/*
    private FileDetector fileDetector = new UselessFileDetector();

    public void setFileDetector(FileDetector detector) {
        if (detector == null) {
            throw new WebDriverException("You may not set a file detector that is null");
        }
        ((RemoteWebDriver) getWrappedDriver()).setFileDetector(detector);
        fileDetector = detector;
    }
    public FileDetector getFileDetector() {
        return fileDetector;
    }*/

  public TracingWebDriver(final WebDriver driver) {
    log.info("Init tracer for {}, driver {}", new BuildInfo(), driver
        .getClass().getName());
    Class<?>[] allInterfaces = extractInterfaces(driver);

    this.driver = (WebDriver) Proxy.newProxyInstance(
        TracingWebDriver.class.getClassLoader(), allInterfaces,
        new InvocationHandler() {
          public Object invoke(Object proxy, Method method, Object[] args)
              throws Throwable {
            String m = method.getName();
            if ("getWrappedDriver".equals(m)) {
              return driver;
            }

            if (!("manage".equals(m) || "switchTo".equals(m))) {
              if (args == null || args.length == 0) {
                log.debug(m + "()");
              } else {
                if (args.length == 1) {
                  log.debug(m + "({})", args[0]);
                } else {
                  log.debug(m + "({}, ...)", args[0]);
                }
              }
            }
            try {
              Object result = method.invoke(driver, args);
              if (!("manage".equals(m))) {
                log.debug(m + "(...) = {}", result);
              }
              return result;
            } catch (InvocationTargetException e) {
              log.debug(m + "(...)", e.getTargetException().getMessage());
              throw e.getTargetException();
            }
          }
        });
  }



    private Class<?>[] extractInterfaces(Object object) {
    Set<Class<?>> allInterfaces = new HashSet<Class<?>>();
    allInterfaces.add(WrapsDriver.class);
    if (object instanceof WebElement) {
      allInterfaces.add(WrapsElement.class);
    }
    extractInterfaces(allInterfaces, object.getClass());

    return allInterfaces.toArray(new Class<?>[allInterfaces.size()]);
  }

  private void extractInterfaces(Set<Class<?>> addTo, Class<?> clazz) {
    if (Object.class.equals(clazz)) {
      return; // Done
    }

    Class<?>[] classes = clazz.getInterfaces();
    addTo.addAll(Arrays.asList(classes));
    extractInterfaces(addTo, clazz.getSuperclass());
  }

  public WebDriver getWrappedDriver() {
    return driver;
  }

  public void get(String url) {
    driver.get(url);
    LOG.info("Open URL: " + url);
  }

  public String getCurrentUrl() {
      LOG.info("Get current URL: " + driver.getCurrentUrl());
      return driver.getCurrentUrl();
  }

  public String getTitle() {
      LOG.info("Get Title: " + driver.getTitle());
      return driver.getTitle();
  }

  public List<WebElement> findElements(By by) {
    List<WebElement> temp = driver.findElements(by);
    List<WebElement> result = new ArrayList<WebElement>(temp.size());
    for (WebElement element : temp) {
      result.add(createWebElement(element));
    }
    LOG.info("Find Elements " + by + result);
    return result;
  }

  public WebElement findElement(By by) {
      LOG.info("Find Element: " + by);
    return createWebElement(driver.findElement(by));
  }

  public String getPageSource() {
      LOG.info("Page Source: " + driver.getPageSource());
    return driver.getPageSource();
  }

  public void close() {
    driver.close();
      LOG.info("Close driver");
  }

  public void quit() {
    driver.quit();
    LOG.info("Quit driver");
  }

  public Set<String> getWindowHandles() {
      LOG.info("Get Window Handles: " + driver.getWindowHandles());
    return driver.getWindowHandles();
  }

  public String getWindowHandle() {
      LOG.info("Get Window Handles " + driver.getWindowHandle());
    return driver.getWindowHandle();
  }

  public Object executeScript(String script, Object... args) {
    if (driver instanceof JavascriptExecutor) {
      Object[] usedArgs = unpackWrappedArgs(args);
        LOG.info("Execute JavaScript: " + script + "Object " + args);
      return ((JavascriptExecutor) driver).executeScript(script, usedArgs);
    }

    throw new UnsupportedOperationException(
        "Underlying driver instance does not support executing javascript");
  }

  public Object executeAsyncScript(String script, Object... args) {
    if (driver instanceof JavascriptExecutor) {
      Object[] usedArgs = unpackWrappedArgs(args);
      return ((JavascriptExecutor) driver).executeAsyncScript(script, usedArgs);
    }
      LOG.info("Underlying driver instance does not support executing javascript");
    throw new UnsupportedOperationException(
        "Underlying driver instance does not support executing javascript");
  }

  private Object[] unpackWrappedArgs(Object... args) {
    // Walk the args: the various drivers expect unpacked versions of the
    // elements
    Object[] usedArgs = new Object[args.length];
    for (int i = 0; i < args.length; i++) {
      usedArgs[i] = unpackWrappedElement(args[i]);
    }
    return usedArgs;
  }

  private Object unpackWrappedElement(Object arg) {
    if (arg instanceof List<?>) {
      List<Object> aList = (List<Object>) arg;
      List<Object> toReturn = new ArrayList<Object>();
      for (int j = 0; j < aList.size(); j++) {
        toReturn.add(unpackWrappedElement(aList.get(j)));
      }
      return toReturn;
    } else if (arg instanceof Map<?, ?>) {
      Map<Object, Object> aMap = (Map<Object, Object>) arg;
      Map<Object, Object> toReturn = new HashMap<Object, Object>();
      for (Object key : aMap.keySet()) {
        toReturn.put(key, unpackWrappedElement(aMap.get(key)));
      }
      return toReturn;
    } else if (arg instanceof TracingWebElement) {
      return ((TracingWebElement) arg).getWrappedElement();
    } else {
      return arg;
    }
  }

  public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
    if (driver instanceof TakesScreenshot) {
      return ((TakesScreenshot) driver).getScreenshotAs(target);
    }
      LOG.info("Underlying driver instance does not support taking screenshots");
    throw new UnsupportedOperationException(
        "Underlying driver instance does not support taking screenshots");
  }

  public TargetLocator switchTo() {
      LOG.info("Switch to: " + driver.switchTo());
      return new TracingTargetLocator(driver.switchTo());
  }

  public Navigation navigate() {
      LOG.info("Navigate: " + driver.navigate());
    return new TracingNavigation(driver.navigate());
  }

  public Options manage() {
    return new TracingOptions(driver.manage());
  }

  private WebElement createWebElement(WebElement from) {
    return new TracingWebElement(from);
  }

  public Keyboard getKeyboard() {
    if (driver instanceof HasInputDevices) {
      return new TracingKeyboard(driver);
    } else {
      throw new UnsupportedOperationException(
          "Underlying driver does not implement advanced"
              + " user interactions yet.");
    }
  }

  public Mouse getMouse() {
    if (driver instanceof HasInputDevices) {
      return new TracingMouse(driver);
    } else {
      throw new UnsupportedOperationException(
          "Underlying driver does not implement advanced"
              + " user interactions yet.");
    }
  }

  public TouchScreen getTouch() {
    if (driver instanceof HasTouchScreen) {
      return new TracingTouch(driver);
    } else {
      throw new UnsupportedOperationException(
          "Underlying driver does not implement advanced"
              + " user interactions yet.");
    }
  }

  private class TracingWebElement implements WebElement, WrapsElement,
      WrapsDriver, Locatable {

    private final WebElement element;
    private final WebElement underlyingElement;

    private TracingWebElement(final WebElement element) {
      this.element = (WebElement) Proxy.newProxyInstance(
          TracingWebDriver.class.getClassLoader(), extractInterfaces(element),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
              String m = method.getName();
              if ("getWrappedElement".equals(m)) {
                return element;
              }

              if (args == null || args.length == 0) {
                log.debug("[{}]." + m + "()", element);
              } else {
                if (args.length == 1) {
                  log.debug("[{}]." + m + "({})", element, args[0]);
                } else {
                  log.debug("[{}]." + m + "({}, ...)", element, args[0]);
                }
              }
              try {
                Object result = method.invoke(element, args);
                log.debug("[{}]." + m + "(...) = {}", element, result);
                return result;
              } catch (InvocationTargetException e) {
                log.debug("[{}]." + m + "(...)", element,
                    e.getTargetException());
                throw e.getTargetException();
              }
            }
          });
      this.underlyingElement = element;
    }

    public void click() {
      element.click();
        LOG.info("Click on: " + element);
    }

    public void submit() {
      element.submit();
        LOG.info("Submit: " + element);
    }

    public void sendKeys(CharSequence... keysToSend) {
      element.sendKeys(keysToSend);
        String input="";
        for(int i=0;i<keysToSend.length;i++){
            input=input.concat(keysToSend[i].toString());
        }
        LOG.info("Input data: " + input);
    }

    public void clear() {
      element.clear();
        LOG.info("Clear field:  " + element);
    }

    public String getTagName() {
        LOG.info("Get tagName: " + element.getTagName());
      return element.getTagName();
    }

    public String getAttribute(String name) {
        LOG.info("Get attribute: " + name);
      return element.getAttribute(name);
    }

    public boolean isSelected() {
        LOG.info("Element is selected: " + element.isSelected());
      return element.isSelected();
    }

    public boolean isEnabled() {
        LOG.info("Element is enabled: " + element.isEnabled());
      return element.isEnabled();
    }

    public String getText() {
        LOG.info("Get element text: " + element.getText());
      return element.getText();
    }

    public boolean isDisplayed() {
        LOG.info("Element is displayed:" + element.isDisplayed());
      return element.isDisplayed();
    }

    public Point getLocation() {
        LOG.info("Element is located at: " + element.getLocation());
      return element.getLocation();
    }

    public Dimension getSize() {
        LOG.info("Elements size = " + element.getSize());
      return element.getSize();
    }

    public String getCssValue(String propertyName) {
        LOG.info("CSS value of element: " + propertyName);
      return element.getCssValue(propertyName);
    }

    public WebElement findElement(By by) {
        LOG.info("Find Element: " + by);
      return createWebElement(element.findElement(by));
    }

    public List<WebElement> findElements(By by) {
      List<WebElement> temp = element.findElements(by);
      List<WebElement> result = new ArrayList<WebElement>(temp.size());
      for (WebElement element : temp) {
        result.add(createWebElement(element));
      }
        LOG.info("Find Elements: " + by + result);
      return result;
    }

    public WebElement getWrappedElement() {
      return underlyingElement;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof WebElement)) {
        return false;
      }

      WebElement other = (WebElement) obj;
      if (other instanceof WrapsElement) {
        other = ((WrapsElement) other).getWrappedElement();
      }

      return underlyingElement.equals(other);
    }

    @Override
    public int hashCode() {
      return underlyingElement.hashCode();
    }

    public WebDriver getWrappedDriver() {
      return driver;
    }

    /*public Point getLocationOnScreenOnceScrolledIntoView() {
      Point locationOnScreenOnceScrolledIntoView = ((Locatable) underlyingElement)
          .getLocationOnScreenOnceScrolledIntoView();
      return locationOnScreenOnceScrolledIntoView;
    }*/

    public Coordinates getCoordinates() {
      Coordinates coordinates = ((Locatable) underlyingElement)
          .getCoordinates();
      return coordinates;
    }
  }

  private class TracingNavigation implements Navigation {

    private final Navigation navigation;

    TracingNavigation(Navigation navigation) {
      this.navigation = navigation;
    }

    public void to(String url) {
      navigation.to(url);
        LOG.info("Navigation to URL: " + url);
    }

    public void to(URL url) {
      to(String.valueOf(url));
    }

    public void back() {
      navigation.back();
        LOG.info("navigationBack");
    }

    public void forward() {
      navigation.forward();
        LOG.info("navigationForward");
    }

    public void refresh() {
      navigation.refresh();
        LOG.info("refresh pages");
    }
  }

  private class TracingOptions implements Options {

    private Options options;

    private TracingOptions(Options options) {
      this.options = options;
    }

    public void addCookie(Cookie cookie) {
      options.addCookie(cookie);
        LOG.info("Added cookie: " + cookie);
    }

    public void deleteCookieNamed(String name) {
      options.deleteCookieNamed(name);
        LOG.info("Delete cookie by name: " + name);
    }

    public void deleteCookie(Cookie cookie) {
      options.deleteCookie(cookie);
        LOG.info("Delete cookie: " + cookie);
    }

    public void deleteAllCookies() {
      options.deleteAllCookies();
        LOG.info("Delete all cookies");
    }

    public Set<Cookie> getCookies() {
      return options.getCookies();
    }

    public Cookie getCookieNamed(String name) {
      return options.getCookieNamed(name);
    }

    public Timeouts timeouts() {
      return new TracingTimeouts(options.timeouts());
    }

    public ImeHandler ime() {
      throw new UnsupportedOperationException(
          "Driver does not support IME interactions");
    }

    public Logs logs() {
      return options.logs();
    }

    public Window window() {
      return options.window();
    }
  }

  private class TracingTimeouts implements Timeouts {

    private final Timeouts timeouts;

    TracingTimeouts(Timeouts timeouts) {
      this.timeouts = timeouts;
    }

    public Timeouts implicitlyWait(long time, TimeUnit unit) {
      timeouts.implicitlyWait(time, unit);
      return this;
    }

    public Timeouts setScriptTimeout(long time, TimeUnit unit) {
      timeouts.setScriptTimeout(time, unit);
        LOG.info("setScriptTimeout= " + time + "TimeUnit" +unit);
      return this;
    }

    public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
      timeouts.pageLoadTimeout(time, unit);
        LOG.info("Page Load Timeout= " + time + " " +unit);
      return null;
    }
  }

  private class TracingTargetLocator implements TargetLocator {

    private final TargetLocator targetLocator;

    private TracingTargetLocator(final TargetLocator targetLocator) {
      this.targetLocator = (TargetLocator) Proxy.newProxyInstance(
          TracingWebDriver.class.getClassLoader(),
          extractInterfaces(targetLocator), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
              String m = method.getName();

              if (args == null || args.length == 0) {
                log.debug("switchTo()." + m + "()");
              } else {
                if (args.length == 1) {
                  log.debug("switchTo()." + m + "({})", args[0]);
                } else {
                  log.debug("switchTo()." + m + "({}, ...)", args[0]);
                }
              }
              try {
                Object result = method.invoke(targetLocator, args);
                log.debug("switchTo()." + m + "(...): OK", result);
                return result;
              } catch (InvocationTargetException e) {
                log.debug("switchTo()." + m + "(...)", e.getTargetException());
                throw e.getTargetException();
              }
            }

          });
    }

    public WebDriver frame(int frameIndex) {
        LOG.info("Frame index: " + frameIndex);
      return targetLocator.frame(frameIndex);
    }

    public WebDriver frame(String frameName) {
        LOG.info("Frame name: " + frameName);
      return targetLocator.frame(frameName);
    }

    public WebDriver frame(WebElement frameElement) {
        LOG.info("Find frame: " + frameElement);
      return targetLocator.frame(frameElement);
    }

    public WebDriver window(String windowName) {
        LOG.info("Window name: " + windowName);
      return targetLocator.window(windowName);
    }

    public WebDriver defaultContent() {
        LOG.info("Default content: " + targetLocator);
      return targetLocator.defaultContent();
    }

    public WebElement activeElement() {
        LOG.info("Active element: " + targetLocator);
      return targetLocator.activeElement();
    }

    public Alert alert() {
        LOG.info("Alert");
      return targetLocator.alert();
    }
  }

  public class TracingKeyboard implements Keyboard {

    private final WebDriver driver;
    private final Keyboard keyboard;

    public TracingKeyboard(WebDriver driver) {
      this.driver = driver;
      final Keyboard kb = ((HasInputDevices) this.driver).getKeyboard();
      this.keyboard = (Keyboard) Proxy.newProxyInstance(
          TracingWebDriver.class.getClassLoader(), extractInterfaces(kb),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
              String m = method.getName();

              if (args == null || args.length == 0) {
                log.debug("keyboard:" + m + "()");
              } else {
                if (args.length == 1) {
                  log.debug("keyboard:" + m + "({})", args[0]);
                } else {
                  log.debug("keyboard:" + m + "({}, ...)", args[0]);
                }
              }
              try {
                Object result = method.invoke(kb, args);
                log.debug("keyboard:" + m + "(...): OK", result);
                return result;
              } catch (InvocationTargetException e) {
                log.debug("keyboard:" + m + "(...)", e.getTargetException());
                throw e.getTargetException();
              }
            }
          });
    }

    public void sendKeys(CharSequence... keysToSend) {
        keyboard.sendKeys(keysToSend);
        String input="";
        for(int i=0;i<keysToSend.length;i++){
        input=input.concat(keysToSend[i].toString());
        }
        //Reporter.log("<ins> SendKeys: </ins>" + input + "</br>");
        LOG.info("Input data: " + input);
    }

      @Override
      public void pressKey(CharSequence charSequence) {
            keyboard.pressKey(charSequence);
            LOG.info("PressKey: " + charSequence);
      }

      @Override
      public void releaseKey(CharSequence charSequence) {
            keyboard.releaseKey(charSequence);
            LOG.info("ReleaseKey: " + charSequence);
      }

//      public void pressKey(CharSequence keyToPress) {
//      keyboard.pressKey(keyToPress);
//        LOG.info("PressKey: " + keyToPress);
//    }
//
//    public void releaseKey(Keys keyToRelease) {
//      keyboard.releaseKey(keyToRelease);
//        LOG.info("ReleaseKey: " + keyToRelease);
//    }
  }

  public class TracingMouse implements Mouse {
    private final WebDriver driver;
    private final Mouse mouse;

    public TracingMouse(WebDriver driver) {
      this.driver = driver;
      final Mouse ms = ((HasInputDevices) this.driver).getMouse();
      this.mouse = (Mouse) Proxy.newProxyInstance(
          TracingWebDriver.class.getClassLoader(), extractInterfaces(ms),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
              String m = method.getName();

              if (args == null || args.length == 0) {
                log.debug("mouse:" + m + "()");
              } else {
                if (args.length == 1) {
                  log.debug("mouse:" + m + "({})", args[0]);
                } else {
                  log.debug("mouse:" + m + "({}, ...)", args[0]);
                }
              }
              try {
                Object result = method.invoke(ms, args);
                log.debug("mouse:" + m + "(...): OK", result);
                return result;
              } catch (InvocationTargetException e) {
                log.debug("mouse:" + m + "(...)", e.getTargetException());
                throw e.getTargetException();
              }
            }
          });
    }

    public void click(Coordinates where) {
      mouse.click(where);
        LOG.info("Click on: " + where);
    }

    public void doubleClick(Coordinates where) {
      mouse.doubleClick(where);
        LOG.info("DoubleClick on: " + where);
    }

    public void mouseDown(Coordinates where) {
      mouse.mouseDown(where);
        LOG.info("MouseDown: " + where);
    }

    public void mouseUp(Coordinates where) {
      mouse.mouseUp(where);
        LOG.info("MouseUp: " + where);
    }

    public void mouseMove(Coordinates where) {
      mouse.mouseMove(where);
        LOG.info("MouseMove: " + where);
    }

    public void mouseMove(Coordinates where, long xOffset, long yOffset) {
      mouse.mouseMove(where, xOffset, yOffset);
        LOG.info("MouseMove: " + where + xOffset + yOffset);
    }

    public void contextClick(Coordinates where) {
      mouse.contextClick(where);
        LOG.info("ContextClick: " + where);
    }
  }

  public class TracingTouch implements TouchScreen {

    private final WebDriver driver;
    private final TouchScreen touchScreen;

    public TracingTouch(WebDriver driver) {
      this.driver = driver;
      final TouchScreen ts = ((HasTouchScreen) this.driver).getTouch();
      this.touchScreen = (TouchScreen) Proxy.newProxyInstance(
          TracingWebDriver.class.getClassLoader(), extractInterfaces(ts),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
              String m = method.getName();

              if (args == null || args.length == 0) {
                log.debug("touch:" + m + "()");
              } else {
                if (args.length == 1) {
                  log.debug("touch:" + m + "({})", args[0]);
                } else {
                  log.debug("touch:" + m + "({}, ...)", args[0]);
                }
              }
              try {
                Object result = method.invoke(ts, args);
                log.debug("touch:" + m + "(...): OK", result);
                return result;
              } catch (InvocationTargetException e) {
                log.debug("touch:" + m + "(...)", e.getTargetException());
                throw e.getTargetException();
              }
            }
          });
    }

    public void singleTap(Coordinates where) {
      touchScreen.singleTap(where);
        LOG.info("singleTap: " + where);
    }

    public void down(int x, int y) {
      touchScreen.down(x, y);
        LOG.info("down:m" + x + y);
    }

    public void up(int x, int y) {
      touchScreen.up(x, y);
        LOG.info("up:" + x + y);
    }

    public void move(int x, int y) {
      touchScreen.move(x, y);
        LOG.info("move: " + x + y);
    }

    public void scroll(Coordinates where, int xOffset, int yOffset) {
      touchScreen.scroll(where, xOffset, yOffset);
        LOG.info("Sroll: " + xOffset + yOffset);
    }

    public void doubleTap(Coordinates where) {
      touchScreen.doubleTap(where);
        LOG.info("Double Tap: " + where);
    }

    public void longPress(Coordinates where) {
      touchScreen.longPress(where);
        LOG.info("Long Press:" + where);
    }

    public void scroll(int xOffset, int yOffset) {
      touchScreen.scroll(xOffset, yOffset);
        LOG.info("Scroll to: " + xOffset + yOffset);
    }

    public void flick(int xSpeed, int ySpeed) {
      touchScreen.flick(xSpeed, ySpeed);
        LOG.info("Flick: " + xSpeed + ySpeed);
    }

    public void flick(Coordinates where, int xOffset, int yOffset, int speed) {
      touchScreen.flick(where, xOffset, yOffset, speed);
        LOG.info("Flick: " + where + xOffset + yOffset + speed);
    }
  }
}
