package cz.skalicky.hello.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Tomas Skalicky
 *         <p>
 *         Created on 05.08.2014
 *         <p>
 *         Copyright (c) 2014, Check24 Vergleichsportal GmbH
 */
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class HelloIT extends AbstractTestNGSpringContextTests {

    private static final String GREETING_COUNT_COOKIE_NAME = "greetingCount";

    @Inject
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Configuration
    static class TestConfig {

        @Bean
        public HelloController helloController() {
            return new HelloController();
        }
    }

    @BeforeMethod
    public void initMockMvc() {

        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void resetShouldReturn0GreetingCount() throws Exception {

        // @formatter:off
        final MvcResult result = mockMvc.perform(
                    get("/reset/")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        // @formatter:on

        assertThat(result.getResponse().getCookie(GREETING_COUNT_COOKIE_NAME).getValue(), is("0"));
    }

    @Test
    public void helloShouldReturn1GreetingCount() throws Exception {

        // @formatter:off
        final MvcResult result = mockMvc.perform(
                    get("/goodMorning/")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        // @formatter:on

        assertThat(result.getResponse().getCookie(GREETING_COUNT_COOKIE_NAME).getValue(), is("1"));
    }

    @Test
    public void goodMorningShouldReturn1GreetingCount() throws Exception {

        // @formatter:off
        final MvcResult result = mockMvc.perform(
                    get("/goodMorning/")
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        // @formatter:on

        assertThat(result.getResponse().getCookie(GREETING_COUNT_COOKIE_NAME).getValue(), is("1"));
    }

    @Test
    public void complexTest() throws Exception {

        // @formatter:off
        Cookie cookie = mockMvc.perform(
                get("/goodMorning/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getCookie(GREETING_COUNT_COOKIE_NAME);
        cookie = mockMvc.perform(
                get("/reset/")
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn()
                .getResponse()
                .getCookie(GREETING_COUNT_COOKIE_NAME);
        cookie = mockMvc.perform(
                get("/goodMorning/")
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn()
                .getResponse()
                .getCookie(GREETING_COUNT_COOKIE_NAME);
        cookie = mockMvc.perform(
                get("/hello/")
                .accept(MediaType.APPLICATION_JSON)
                .cookie(cookie))
                .andReturn()
                .getResponse()
                .getCookie(GREETING_COUNT_COOKIE_NAME);
        // @formatter:on

        assertThat(cookie.getValue(), is("2"));
    }

}
