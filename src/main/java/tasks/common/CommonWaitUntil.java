package tasks.common;

import singleton.GVs;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.CoreMatchers.*;

public class CommonWaitUntil {
    public static Interaction isVisible(Target target) {
        return WaitUntil.the(target, WebElementStateMatchers.isVisible()).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();
    }

    public static Interaction isCurrentVisible(String cssOrXpath) {
        return WaitUntil.the(cssOrXpath, WebElementStateMatchers.isCurrentlyVisible()).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();
    }

    public static Interaction isClickable(Target target) {
        return WaitUntil.the(target, WebElementStateMatchers.isClickable()).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();
    }

    public static Interaction isEnabled(Target target) {
        return WaitUntil.the(target, WebElementStateMatchers.isCurrentlyEnabled()).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();
    }

    public static Interaction isPresent(Target target) {
        return WaitUntil.the(target, WebElementStateMatchers.isPresent()).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();

    }

    public static Interaction isNotPresent(Target target) {
        return WaitUntil.the(target, WebElementStateMatchers.isNotPresent()).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();
    }

    public static Interaction isNotVisible(Target target) {//được sử dụng kiểm tra sự có mặt của phần tử trong cây DOM
        return WaitUntil.the(target, not(WebElementStateMatchers.isCurrentlyVisible())).forNoMoreThan(GVs.HTTP_TIMEOUT).seconds();
    }

    public static Interaction isNotVisible(Target target, int timeOut) {//được sử dụng kiểm tra sự có mặt của phần tử trong cây DOM
        return WaitUntil.the(target, not(WebElementStateMatchers.isCurrentlyVisible())).forNoMoreThan(timeOut).seconds();
    }

    public static Interaction isVisible(Target target, int timeOut) {//được sử dụng kiểm tra sự có mặt của phần tử trong cây DOM
        return WaitUntil.the(target, WebElementStateMatchers.isCurrentlyVisible()).forNoMoreThan(timeOut).seconds();
    }


    public static Interaction waitToLoadingNewWindow2(String pageTitle) {
        return WaitUntil.the(ExpectedConditions.titleIs(pageTitle));
    }

}
