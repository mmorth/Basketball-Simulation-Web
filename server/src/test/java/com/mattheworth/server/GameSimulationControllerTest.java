package com.mattheworth.server;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

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

@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class GameSimulationControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Team team;

    @Autowired
    private TeamRepository teamRepository;
    
    private GameSimulation gameSimulation;
    
    @Autowired
    private GameSimulationRepository gameSimulationRepository;

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
 	    this.team.setOffensiveRating(100);
 	    this.team.setDefensiveRating(100);
        this.team = teamRepository.save(this.team); 
        
 	   Team awayTeam = new Team();
 	   awayTeam.setId(1);
 	   awayTeam.setName("awayTeam");
 	   awayTeam.setOffensiveRating(100);
 	   awayTeam.setDefensiveRating(100);
 	   
 	   Team homeTeam = new Team();
 	   homeTeam.setId(2);
 	   homeTeam.setName("homeTeam");
 	   homeTeam.setOffensiveRating(100);
 	   homeTeam.setDefensiveRating(100);
 	   
 	   gameSimulation = new GameSimulation(awayTeam, homeTeam);
    }

    @Test
    public void gameSimulationNotFound() throws Exception {
        mockMvc.perform(get("/api/game-simulation/" + (gameSimulation.getId()+1) + "/")
                .content(this.json(new GameSimulation()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void gameSimulationFound() throws Exception {
        mockMvc.perform(get("/api/game-simulation/" + gameSimulation.getId() + "/")
                .content(this.json(this.gameSimulation))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void createGameSimulation() throws Exception {
        String gameSimulationJson = json(this.gameSimulation);

        this.mockMvc.perform(post("/api/game-simulation/" + this.gameSimulation.getId() + "/")
                .contentType(contentType)
                .content(gameSimulationJson))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void updateGameSimulation() throws Exception {
        String gameSimulationJson = json(this.gameSimulation);

        this.mockMvc.perform(put("/api/game-simulation/" + this.gameSimulation.getId() + "/")
                .contentType(contentType)
                .content(gameSimulationJson));        
    }
    
    @Test
    public void deleteTeam() throws Exception {
        String teamJson = json(this.team);

        this.mockMvc.perform(delete("/api/teams/" + this.team.getId() + "/")
                .contentType(contentType)
                .content(teamJson))
                .andExpect(status().isNoContent());
    }
    
    // Logic tests
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}