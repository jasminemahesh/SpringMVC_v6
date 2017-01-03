package com.ing.training;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.training.domain.User;
import com.ing.training.service.UserManagementService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/test-servlet-context.xml" })
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    User user;
    User createdUser;
    Locale locale;

    @Before
    public void setUp() {
	user = new User();
	user.setFirstname("Rachael");
	user.setLastname("Green");
	user.setCity("Los Angeles");
	user.setEmail("rachaelg@gmail.com");

	createdUser = new User();
	createdUser.setId(4);
	createdUser.setFirstname("Rachael");
	createdUser.setLastname("Green");
	createdUser.setCity("Los Angeles");
	createdUser.setEmail("rachaelg@gmail.com");

	MockitoAnnotations.initMocks(this);

	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	locale = new Locale("en", "IN");
    }

    @Test
    public void testcreateUserWithValidInput() throws Exception {

	when(userManagementService.createUser(any(User.class))).thenReturn(createdUser);

	mockMvc.perform(post("/users/create").contentType(MediaType.APPLICATION_JSON_UTF8)
		.content(new ObjectMapper().writeValueAsBytes(user))).andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.id", equalTo(createdUser.getId())))
		.andExpect(jsonPath("$.firstname", equalTo(createdUser.getFirstname())))
		.andExpect(jsonPath("$.lastname", equalTo(createdUser.getLastname())))
		.andExpect(jsonPath("$.city", equalTo(createdUser.getCity())))
		.andExpect(jsonPath("$.email", equalTo(createdUser.getEmail())));

	verify(userManagementService).createUser(user);
	verifyNoMoreInteractions(userManagementService);

    }
}
