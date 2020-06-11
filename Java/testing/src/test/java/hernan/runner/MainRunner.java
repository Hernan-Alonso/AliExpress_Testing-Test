package hernan.runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
@RunWith(Cucumber.class)
/***
 * @CucumberOptions
 * @feature Object{}
 * @glue Object{}
 * @monochrome Boolean
 * @plugin Object{}
 */
@CucumberOptions(
    features = {"src/test/java/hernan/featureFiles/"},
    glue = {"hernan.stepFiles"},
    monochrome = true,
    plugin = {"pretty", "html:target/cucumber",
              "json:target/cucumber.json",
              "com.cucumber.listener.ExtentCucumberFormatter:target/report.html"
    }
)
public class MainRunner {
    
}