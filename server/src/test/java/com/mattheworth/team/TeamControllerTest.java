package com.mattheworth.team;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.mattheworth.server.Team;
import com.mattheworth.server.TeamRepository;

/**
 * @author Josh Long
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class TeamControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Team team;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.team = new Team();
 	   	this.team.setId(1);
 	    this.team.setName("awayTeam");
 	    this.team.setOffensiveRating();
 	    this.team.setDefensiveRating();
        this.team = teamRepository.save(this.team); 
    }

    @Test
    public void teamNotFound() throws Exception {
        mockMvc.perform(get("/api/teams/" + (team.getId()+1) + "/")
                .content(this.json(new Team()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void teamFound() throws Exception {
        mockMvc.perform(get("/api/teams/" + team.getId() + "/")
                .content(this.json(this.team))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void readTeams() throws Exception {
        mockMvc.perform(get("/api/teams/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.team.getId())))
                .andExpect(jsonPath("$[0].name", is(this.team.getName())))
                .andExpect(jsonPath("$[1].id", is(this.team.getId())))
                .andExpect(jsonPath("$[1].name", is(this.team.getName())));
    }

    @Test
    public void createTeam() throws Exception {
        String teamJson = json(this.team);

        this.mockMvc.perform(post("/api/teams/" + this.team.getId() + "/")
                .contentType(contentType)
                .content(teamJson))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void updateTeam() throws Exception {
        String teamJson = json(this.team);

        this.mockMvc.perform(put("/api/teams/" + this.team.getId() + "/")
                .contentType(contentType)
                .content(teamJson))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void deleteTeam() throws Exception {
        String teamJson = json(this.team);

        this.mockMvc.perform(delete("/api/teams/" + this.team.getId() + "/")
                .contentType(contentType)
                .content(teamJson))
                .andExpect(status().isNoContent());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
