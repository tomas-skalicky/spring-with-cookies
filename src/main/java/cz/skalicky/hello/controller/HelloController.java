package cz.skalicky.hello.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Tomas Skalicky
 *         <p>
 *         Created on 05.08.2014
 *         <p>
 *         Copyright (c) 2014, Check24 Vergleichsportal GmbH
 */
@Controller
@RequestMapping("")
public class HelloController {

    private static final String GREETING_COUNT_COOKIE_NAME = "greetingCount";

    @RequestMapping(value = "/reset/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void resetGreeting(
            @CookieValue(value = GREETING_COUNT_COOKIE_NAME, defaultValue = "0") int greetingCount,
            final HttpServletResponse response) {

        response.addCookie(new Cookie(GREETING_COUNT_COOKIE_NAME, String.valueOf(0)));
    }

    @RequestMapping(value = "/hello/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String helloWorld(
            @CookieValue(value = GREETING_COUNT_COOKIE_NAME, defaultValue = "0") int greetingCount,
            final HttpServletResponse response) {

        greetingCount += 1;
        response.addCookie(new Cookie(GREETING_COUNT_COOKIE_NAME, String.valueOf(greetingCount)));

        return "Hello World";
    }

    @RequestMapping(value = "/goodMorning/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String goodMorningTom(
            @CookieValue(value = GREETING_COUNT_COOKIE_NAME, defaultValue = "0") int greetingCount,
            final HttpServletResponse response) {

        greetingCount += 1;
        response.addCookie(new Cookie(GREETING_COUNT_COOKIE_NAME, String.valueOf(greetingCount)));

        return "Good morning Tom (already " + greetingCount + "x in row)";
    }

}
