package tasks.common;

import singleton.GVs;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.DriverTask;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class WindowTask {

    public static Performable switchWindow() {
        return new DriverTask(driver -> {
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            driver.manage().window().maximize();
        });
    }

    public static Performable switchWindow(String winHandle) {
        return new DriverTask(driver -> {
            driver.switchTo().window(winHandle);
        });
    }

    public static Performable switchFrame(String locator) {
        WebElement element = getDriver().findElement(By.xpath(locator));
        return new DriverTask(driver -> {
            driver.switchTo().frame(element);
        });
    }

    public static Performable switchToChildWindowsByTitle(String title) {
        return new DriverTask(driver -> {
            Set<String> allWindows = driver.getWindowHandles();
            for (String runWindow : allWindows) {
                driver.switchTo().window(runWindow);
                String currentWin = driver.getTitle();
                System.out.println("Current title " + currentWin);
                if (currentWin.contains(title)) {
                    System.out.println("Đang ở title " + currentWin);
                    break;
                }
            }
        });
    }

    public static Performable switchToChildWindowsByTitle1(String title) {
        return new DriverTask(driver -> {
            Set<String> allWindows = driver.getWindowHandles();
            for (String runWindow : allWindows) {
                driver.switchTo().window(runWindow);
                String currentWin = driver.getTitle();
                System.out.println("Current title " + currentWin);
                if (currentWin.equals(title)) {
                    break;
                }
            }
        });
    }

    public static Performable switchToDefaultWindow() {
        return new DriverTask(driver -> {
            driver.switchTo().defaultContent();
        });
    }

    public static Performable switchToChildWindowsByIndex(int index) {
        return new DriverTask(driver -> {
            Set<String> allWindows = driver.getWindowHandles();
            List<String> list = new ArrayList<>(allWindows);
            driver.switchTo().window(list.get(index));
            driver.manage().window().maximize();
        });
    }

    public static Performable closeAllWindowsWithoutParent(String parentID) {
        return new DriverTask(driver -> {
            Set<String> allWindows = driver.getWindowHandles();
            for (String runWindows : allWindows) {
                if (!runWindows.contains(parentID)) {
                    driver.switchTo().window(runWindows);
                    driver.close();
                }
            }
            driver.switchTo().window(parentID);
        });
    }

    public static Performable closeCurrentAndSwitchParentWindowByURL(String parentUrl, String childUrl) {
        return new DriverTask(driver -> {
            Set<String> allWindows = driver.getWindowHandles();
            for (String runWindow : allWindows) {
                driver.switchTo().window(runWindow);
                String currentWin = driver.getCurrentUrl();
                System.out.println("Current Url " + currentWin);
                if (currentWin.contains(childUrl)) {
                    driver.close();
                }
            }
            for (String runWindow : allWindows) {
                driver.switchTo().window(runWindow);
                String currentWin = driver.getCurrentUrl();
                System.out.println("Current title " + currentWin);
                if (currentWin.contains(parentUrl)) {
                    break;
                }
            }
        });
    }

    public static Performable getWindowhandle() {
        return new DriverTask(driver -> {
            GVs.WINDOW_HANDLE = driver.getWindowHandle();
        });
    }

    public static Performable refeshBrowser() {
        return new DriverTask(driver -> {
            driver.navigate().refresh();
        });
    }

    public static Performable closeWindow() {
        return new DriverTask(driver -> {
            driver.close();
        });
    }

    public static Performable quit() {
        return new DriverTask(driver -> {
            driver.quit();
        });
    }

    public static Performable forward() {
        return new DriverTask(driver -> {
            driver.navigate().forward();
        });
    }

    public static Performable newTab(String url) {
        return new DriverTask(driver -> {
            ((JavascriptExecutor) driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.get(url);
        });
    }

    public static Performable threadSleep(int milisecons) {
        return new DriverTask(driver -> {
            try {
                Thread.sleep(milisecons);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Performable addTextToCodeMirror(String value, Target target) {
        return new DriverTask(driver -> {
            List<WebElementFacade> codeMirrorWebElement = target.resolveAllFor(theActorInTheSpotlight());
            for(WebElementFacade item:codeMirrorWebElement) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].CodeMirror.setValue( '"+ value + "');", item);
            }
        });
    }
}
