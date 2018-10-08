package com.bridgelabz.sabre;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.sabre.controller.FlightController;

/**
 * To test the CheapFlightController class
 * 
 
 * @since 05-10-2018
 * @version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SabreProjApplicationTests {

    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    /**
     * 
     * Junit Test cases (run with help of internet)
     */
    @Test
    public void getTokenTest() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc.perform(post("/flights/get")).andExpect(status().isOk());
    }

    @Test
    public void getFlightInfoTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc.perform(get("/flights/getendpoint")).andExpect(status().isOk());
    }

    /**
     * Mock test cases(run without internet)
     *
     */
    @InjectMocks
    private FlightController flightController;

    @Test
    public void mockGetToken() throws Exception {
    	flightController = mock(FlightController.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        mockMvc.perform(post("/flights/get")).andExpect(status().isOk());

    }

    @Test
    public void getFlightInfo() throws Exception {
    	flightController = mock(FlightController.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        mockMvc.perform(get("/flights/getendpoint")).andExpect(status().isOk());
    }
}


/****************************************************************************/

/*the following code is to test code with json test cases*/

/*import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SabreProjApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	private ObjectMapper mapper = new ObjectMapper();

	private Resource casesFile;

	private Map<String, Json> cases;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		casesFile = new ClassPathResource("cases.json");

		cases = mapper.readValue(casesFile.getInputStream(), new TypeReference<Map<String, Json>>() {
		});
	}

	@Test
	public void connecturlTest() throws Exception {
		Json json = cases.get("geturl");
		System.out.println(json.getRequest());
		System.out.println(json.getRequest().getBody());
		test(json);
	}
	
	@Test
	public void accessendpointTest() throws Exception {
		Json json = cases.get("getendpoint");
		System.out.println(json.getRequest());
		System.out.println(json.getRequest().getBody());
		test(json);
	}
	

	private void test(Json json) throws Exception {
		ResultActions actions = mockMvc
				.perform(getMethod(json).headers(json.getRequest().getHeaders()).contentType(MediaType.APPLICATION_JSON)
						.content(getRequestBody(json)).accept(MediaType.APPLICATION_JSON));

		actions.andExpect(status().is(json.getResponse().getStatus().value()));

		MockHttpServletResponse response = actions.andReturn().getResponse();

		for (String key : json.getResponse().getHeaders().keySet()) {
			assertEquals(json.getResponse().getHeaders().get(key), response.getHeader(key));
		}
		assertEquals(getResponseBody(json), response.getContentAsString());
	}

	private MockHttpServletRequestBuilder getMethod(Json json) {
		return MockMvcRequestBuilders.request(HttpMethod.resolve(json.getRequest().getMethod()),
				json.getRequest().getUrl());
	}

	private String getRequestBody(Json json) throws JsonProcessingException {
		return mapper.writeValueAsString(json.getRequest().getBody());
	}

	private String getResponseBody(Json json) throws JsonProcessingException {
		return mapper.writeValueAsString(json.getResponse().getBody());
	}

}
*/