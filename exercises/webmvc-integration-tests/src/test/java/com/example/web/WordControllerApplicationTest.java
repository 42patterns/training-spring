package com.example.web;

import com.example.TestAppRunner;
import com.example.AppConfiguration;
import com.example.DispatcherConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestAppRunner.class})
@WebIntegrationTest(randomPort = true)
@EnableAutoConfiguration
public class WordControllerApplicationTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void test_application() throws IOException {
        WebDriver driver = new HtmlUnitDriver();
        driver.get("http://localhost:" + this.port + "/search/dom.html");
        assertEquals("Home", driver.getTitle());

        List<WebElement> li = driver.findElements(By.tagName("li"));
        assertEquals(24, li.size());
        assertEquals("dom home", li.get(0).getText());
        assertEquals("home", li.get(0).findElement(By.tagName("blockquote")).getText());
    }

}
