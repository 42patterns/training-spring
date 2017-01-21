package com.example.web;

import com.github.mjeanroy.junit.servers.rules.TomcatServerRule;
import com.github.mjeanroy.junit.servers.tomcat.EmbeddedTomcat;
import com.github.mjeanroy.junit.servers.tomcat.EmbeddedTomcatConfiguration;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WordControllerApplicationTest {

    @ClassRule
    public static TomcatServerRule server = new TomcatServerRule(
            new EmbeddedTomcat(EmbeddedTomcatConfiguration.builder()
                    .withBaseDir("./target/tomcat-work")
                    .withWebapp("./target/war/")
                    .build()));

    @Test
    public void test_application() throws IOException, InterruptedException {
        final String location = "http://localhost:" + server.getPort() + "/search/dom.html";

        WebDriver driver = new HtmlUnitDriver();
        driver.get(location);
        assertEquals("Home", driver.getTitle());

        List<WebElement> li = driver.findElements(By.tagName("li"));
        assertEquals(24, li.size());
        assertEquals("dom home", li.get(0).getText());
        assertEquals("home", li.get(0).findElement(By.tagName("blockquote")).getText());
    }

}
