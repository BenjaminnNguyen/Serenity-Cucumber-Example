package tasks.common;

import models.User;
import singleton.GVs;
import singleton.user.Website;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isCurrentlyVisible;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.valueOf;

public class CommonTask {

    public static User setUser(String email, String pass) {
        return new User(email, pass);
    }

    public static User getUser(String name) {
        User user = null;
        GVs.AccountType actor = GVs.AccountType.valueOf(name.toUpperCase());
        switch (actor) {
            case ADMIN:
                user = Website.getUser();
                break;
        }
        return user;
    }


    public static Task chooseItemInDropdown(Target parentTarget, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown",
                CommonWaitUntil.isClickable(parentTarget),
                Scroll.to(parentTarget),
                Click.on(parentTarget),
                CommonWaitUntil.isVisible(childTarget),
                Scroll.to(childTarget),
                Click.on(childTarget)
        );
    }

    public static Task chooseItemInDropdown1(Target parentTarget, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown",
                CommonWaitUntil.isClickable(parentTarget),
                Click.on(parentTarget),
                CommonWaitUntil.isVisible(childTarget),
                Click.on(childTarget).afterWaitingUntilEnabled()
        );
    }

    public static Task chooseItemInDropdown2(Target parentTarget, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown",
                CommonWaitUntil.isClickable(parentTarget),
                Click.on(parentTarget),
                Check.whether(valueOf(childTarget), isCurrentlyVisible())
                        .andIfSo(Click.on(childTarget))
                        .otherwise(JavaScriptClick.on(childTarget))
        );
    }

    public static Task clearFieldByKeyboard(Target parentTarget) {
        return Task.where("Chọn giá trị trong dropdown",
                CommonWaitUntil.isClickable(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(Keys.chord(Keys.COMMAND, "a")).into(parentTarget).thenHit(Keys.DELETE)


        );
    }

    public static Performable clearFieldByJavaScript(Target target) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                BrowseTheWeb.as(t).evaluateJavascript("arguments[0].value = \"\";", target.resolveFor(t));

            }
        };
    }


    public static Performable clearFieldByEnterKey(Target target) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                t.attemptsTo(
                        Clear.field(target),
                        Enter.theValue("1").into(target).thenHit(Keys.BACK_SPACE),
                        Click.on(target)
                );
            }
        };
    }

    public static Task chooseItemInDropdown3(Target parentTarget, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown",
                CommonWaitUntil.isClickable(parentTarget),
                Click.on(parentTarget),
                WindowTask.threadSleep(500),
                JavaScriptClick.on(childTarget)
        );
    }



    public static Task chooseItemInDropdownWithValueInput(Target parentTarget, String value, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown với giá trị nhập vào",
                Scroll.to(parentTarget),
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(value).into(parentTarget),
                WindowTask.threadSleep(500),
                CommonWaitUntil.isVisible(childTarget),
                Scroll.to(childTarget),
                Click.on(childTarget).afterWaitingUntilEnabled()
        );
    }

    public static Task chooseItemInDropdownWithValueInput2(Target parentTarget, String value, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown với giá trị nhập vào",
                Scroll.to(parentTarget),
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.keyValues(value).into(parentTarget).then(
                        WindowTask.threadSleep(500)
                ),
                CommonWaitUntil.isVisible(childTarget),
                Click.on(childTarget)
        );
    }

    public static Task chooseItemInDropdownWithValueInput3(Target parentTarget, String value, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown với giá trị nhập vào",
                Scroll.to(parentTarget),
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(value).into(parentTarget),
                WindowTask.threadSleep(500),
                CommonWaitUntil.isVisible(childTarget),
                Scroll.to(childTarget),
                JavaScriptClick.on(childTarget)
        );
    }

    public static Task chooseItemInDropdownWithValueInput1(Target parentTarget, String value, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown với giá trị nhập vào",
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(value).into(parentTarget),
                CommonWaitUntil.isVisible(childTarget),
                WindowTask.threadSleep(500),
                Click.on(childTarget)
        );
    }

    public static Task chooseItemInDropdownWithValueInput4(Target parentTarget, String value, Target childTarget) {
        return Task.where("Chọn giá trị trong dropdown với giá trị nhập vào",
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(value).into(parentTarget),
                CommonWaitUntil.isVisible(childTarget),
                WindowTask.threadSleep(1000),
                Click.on(childTarget)
        );
    }

    public static Task ChooseValueFromSuggestions(String value) {
        String xpath = "(//ul/li//*[text()='%s'])[last()]";
        Target target = Target.the("").locatedBy(String.format(xpath, value));
        return Task.where("Choose Value From Suggestions",
                CommonWaitUntil.isVisible(target),
                Scroll.to(target),
                Click.on(target)
        );
    }

    public static Task ChooseValueFromSuggestionsWithJS(String value) {
        String xpath = "(//ul/li//*[text()='%s'])[last()]";
        return Task.where("Choose Value From Suggestions",
                CommonWaitUntil.isCurrentVisible(String.format(xpath, value)),
                JavaScriptClick.on(String.format(xpath, value))
        );
    }

    public static String getDateTimeString() {
        //khai báo đối tượng current thuộc class LocalDateTime
        LocalDateTime current = LocalDateTime.now();
        //sử dụng class DateTimeFormatter để định dạng ngày giờ theo kiểu pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        //sử dụng phương thức format() để định dạng ngày giờ hiện tại rồi gán cho chuỗi formatted
        String formatted = current.format(formatter);
        //hiển thị chuỗi formatted ra màn hình
        System.out.println("Ngày giờ hiện tại: " + formatted);
        return formatted;
    }

    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
        String alphaUpperCase = alpha.toUpperCase(); // A-Z
        String digits = "0123456789"; // 0-9
        String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
        Random generator = new Random();

        for (int i = 0; i < numberOfCharactor; i++) {
            char ch = ALPHA_NUMERIC.charAt(generator.nextInt((ALPHA_NUMERIC.length() - 1) - 0 + 1) + 0);
            sb.append(ch);
        }

        return sb.toString();
    }

    public static HashMap<String, String> setValueRandom(Map<String, String> list, String key, String value) {
        HashMap<String, String> info = new HashMap<>(list);
        if (info.get(key).contains("random")) {
            info.replace(key, info.get(key), value);
        }
        System.out.println("Value = " + value);
        return info;
    }

    public static HashMap<String, Object> setValueRandom1(Map<String, Object> list, String key, String value) {
        HashMap<String, Object> info = new HashMap<>(list);
        if (info.get(key).toString().contains("random")) {
            info.replace(key, "random", value);
        }
        System.out.println("Value = " + value);
        return info;
    }

    public static HashMap<String, String> setValueRandom2(Map<String, String> list, String key, String oldValue, String newValue) {
        HashMap<String, String> info = new HashMap<>(list);
        if (info.get(key).contains("random")) {
            info.replace(key, oldValue, newValue);
        }
        System.out.println("Value = " + newValue);
        return info;
    }

    public static HashMap<String, String> setValue(Map<String, String> list, String key, String oldValue, String newValue, String condition) {
        HashMap<String, String> info = new HashMap<>(list);
        if (info.get(key).equals(condition)) {
            info.replace(key, oldValue, newValue);
        }
        System.out.println("Value = " + newValue);
        return info;
    }

    public static HashMap<String, Object> setValue1(Map<String, Object> list, String key, Object oldValue, String newValue, String condition) {
        HashMap<String, Object> info = new HashMap<>(list);
        if (info.get(key).equals(condition)) {
            info.replace(key, oldValue, newValue);
        }
        System.out.println("Value = " + newValue);
        return info;
    }

    public static HashMap<String, String> setValue2(Map<String, String> list, String key, String oldValue, String newValue, String condition) {
        HashMap<String, String> info = new HashMap<>(list);
        if (info.get(key).toString().contains(condition)) {
            info.replace(key, oldValue, newValue);
        }
        System.out.println("Value = " + newValue);
        return info;
    }

    public static Performable pressESC() {
        return new DriverTask(driver -> {
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            robot.setAutoDelay(1000);
            //native key strokes for CTRL, V and ENTER keys
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        });
    }

    public static Performable moveSlider() {
        return new DriverTask(driver -> {
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.ARROW_DOWN).build().perform();
        });
    }

    public static Performable setSessionVariable(String varName, String value) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                Serenity.setSessionVariable(varName).to(value);
            }
        };
    }


    public static Performable loop(int numCircle, Performable performable) {
        return new Performable() {
            @Override
            public <T extends Actor> void performAs(T t) {
                for (int i = 0; i < numCircle; i++) {
                    Task.where("", performable);
                }
            }
        };
    }

    /**
     * Dung trong re turn Task
     *
     * @param varName
     * @param value
     * @return
     */
    public static Interaction setSessionVariableInteraction(String varName, String value) {
        return Interaction.where("set variable", setSessionVariable(varName, value));
    }


    public static Performable scroll() {
        return Task.where("{0} sroll",
                actor -> {
                    BrowseTheWeb.as(actor).evaluateJavascript("window.scrollBy(0,document.body.scrollHeight)", "");
                });
    }

    public static Task chooseMultiItemInDropdown(Target parentTarget, String value, Target childTarget) {
        return Task.where("Choose multi item in dropdown",
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(value).into(parentTarget),
                CommonWaitUntil.isVisible(childTarget),
                WindowTask.threadSleep(500),
                Click.on(childTarget),
                Click.on(parentTarget)
        );
    }

    public static Task chooseMultiWithOneItemInDropdown(Target parentTarget, String value, Target childTarget) {
        return Task.where("Choose multi with one item in dropdown",
                CommonWaitUntil.isEnabled(parentTarget),
                Click.on(parentTarget),
                Enter.theValue(value).into(parentTarget),
                CommonWaitUntil.isVisible(childTarget),
                WindowTask.threadSleep(500),
                Click.on(childTarget),
                WindowTask.threadSleep(500),
                JavaScriptClick.on(parentTarget)
        );
    }

    public static Task swipeTarget(Target fromTarget, Target toTarget) {
        return Task.where("swipe Target from " + fromTarget.getName() + " to " + toTarget.getName(),
                CommonWaitUntil.isVisible(fromTarget),
                Drag.from(fromTarget).to(toTarget)
        );
    }


    public static Task clearTextbox(Target target) {
        return Task.where("Choose multi item in dropdown",
                Enter.theValue("a").into(target),
                Hit.the(Keys.BACK_SPACE).into(target)
        );
    }

    public static Performable clearTextboxMultiDropdown(Target target) {
        return Task.where("Clear textbox multi dropdown",
                actor -> {
                    List<WebElementFacade> elements = target.resolveAllFor(theActorInTheSpotlight());
                    for (WebElementFacade element : elements) {
                        actor.attemptsTo(
                                Click.on(element).afterWaitingUntilEnabled(),
                                WindowTask.threadSleep(1000)
                        );
                    }
                }
        );
    }

    public CommonTask performWithContainKey(Map<String, String> map, String key, Performable... performables) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Check.whether(map.get(key).isEmpty()).otherwise(
                            performables
                    )
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }



}