package cucumber.tasks.common;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;

import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class CommonVerify {

    /***
     * Kiểm tra target text của element
     * @param actor
     * @param target
     * @param value
     * @return
     */
    public CommonVerify verifyTargetTextEqual(Actor actor, Target target, String value) {
        actor.attemptsTo(
                CommonWaitUntil.isVisible(target),
                Ensure.that(target).text().isEqualTo(value)
        );
        return this;
    }

    public CommonVerify verifyTargetTextEqual(Target target, String value) {
        theActorInTheSpotlight().attemptsTo(
                CommonWaitUntil.isVisible(target),
                Ensure.that(target).text().isEqualTo(value)
        );
        return this;
    }

    public CommonVerify verifyTargetTextContain(Actor actor, Target target, String value) {
        actor.attemptsTo(
                CommonWaitUntil.isVisible(target),
                Ensure.that(target).text().containsIgnoringCase(value)
        );
        return this;
    }

    public CommonVerify verifyTargetTextContain(Target target, String value) {
        theActorInTheSpotlight().attemptsTo(
                CommonWaitUntil.isVisible(target),
                Ensure.that(target).text().containsIgnoringCase(value)
        );
        return this;
    }

    public CommonVerify verifyTargetTextContainNotBlank(Target target, String value) {
        theActorInTheSpotlight().attemptsTo(
                Check.whether(value.isEmpty()).otherwise(
                        CommonWaitUntil.isVisible(target),
                        Ensure.that(target).text().containsIgnoringCase(value))
        );
        return this;
    }

    public CommonVerify verifyTargetTextContainNotBlank(Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Check.whether(map.get(key).isEmpty()).otherwise(
                            CommonWaitUntil.isVisible(target),
                            Ensure.that(target).text().containsIgnoringCase(map.get(key))
                    )
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextContain(Actor actor, Target target, String attribute, String value) {
        actor.attemptsTo(
                Ensure.that(target).attribute(attribute).containsIgnoringCase(value)
        );
        return this;
    }

    public CommonVerify verifyTargetAttributeTextContain(Target target, String attribute, String value) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(target).attribute(attribute).containsIgnoringCase(value)
        );
        return this;
    }

    public CommonVerify verifyTargetAttributeTextContain(Target target, String attribute, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Ensure.that(target).attribute(attribute).containsIgnoringCase(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextContain(Actor actor, Target target, String attribute, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            actor.attemptsTo(
                    Ensure.that(target).attribute(attribute).containsIgnoringCase(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextContainNotBlank(Target target, String attribute, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Check.whether(map.get(key).isEmpty()).otherwise(
                            Ensure.that(target).attribute(attribute).containsIgnoringCase(map.get(key)))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextContain(Target target, String attribute, Map<String, String> map, String key, String value) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Ensure.that(target).attribute(attribute).containsIgnoringCase(value)
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextEqual(Target target, String attribute, String value) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(target).attribute(attribute).isEqualTo(value)
        );
        return this;
    }

    public CommonVerify verifyTargetTextEqualNotBlank(Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Check.whether(map.get(key).isEmpty()).otherwise(
                            CommonWaitUntil.isVisible(target),
                            Ensure.that(target).text().isEqualTo(map.get(key))
                    )
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextEqual(Target target, String attribute, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Ensure.that(target).attribute(attribute).isEqualTo(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextEqual(Target target, String attribute, Map<String, String> map, String key, String value) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    Ensure.that(target).attribute(attribute).isEqualTo(value)
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetAttributeTextEqual(Actor actor, Target target, String attribute, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            actor.attemptsTo(
                    Ensure.that(target).attribute(attribute).isEqualTo(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    /***
     * Kiểm tra target text của element với table chứa key hay không
     * @param actor
     * @param target
     * @param map
     * @param key
     * @return
     */
    public CommonVerify verifyTargetTextEqual(Actor actor, Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            actor.attemptsTo(
                    CommonWaitUntil.isVisible(target),
                    Ensure.that(target).text().isEqualTo(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetTextEqual(Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    CommonWaitUntil.isVisible(target),
                    Ensure.that(target).text().isEqualTo(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetTextContain(Actor actor, Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            actor.attemptsTo(
                    CommonWaitUntil.isVisible(target),
                    Ensure.that(target).text().containsIgnoringCase(map.get(key))
            );
        } else System.out.println("Datatable does not contain the key: " + key);
        return this;
    }

    public CommonVerify verifyTargetTextContain(Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    CommonWaitUntil.isVisible(target),
                    Ensure.that(target).text().containsIgnoringCase(map.get(key))
            );
        } else System.out.println("Data table does not contain key: " + key);
        return this;
    }

    public CommonVerify verifyTargetTextContain(Target target, Map<String, String> map, String key, String value) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    CommonWaitUntil.isVisible(target),
                    Ensure.that(target).text().containsIgnoringCase(value)
            );
        } else System.out.println("Data table does not contain key: " + key);
        return this;
    }

    public CommonVerify verifyTargetIsDisplay(Target target, Map<String, String> map, String key) {
        if (map.containsKey(key)) {
            theActorInTheSpotlight().attemptsTo(
                    CommonWaitUntil.isVisible(target)
            );
        } else System.out.println("Data table does not contain key: " + key);
        return this;
    }

}
