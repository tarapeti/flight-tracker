package com.project.fligthtracker;

import com.project.fligthtracker.model.Planes;
import com.project.fligthtracker.repositroy.PlaneRepository;
import com.project.fligthtracker.service.PlaneService;
import com.project.fligthtracker.service.simple.SimplePlaneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FligthtrackerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PlaneRepository planeRepository;

	@TestConfiguration
	static class PlaneServiceImplTestContextConfiguration {

		@Bean
		public PlaneService planeService() {
			return new SimplePlaneService();
		}
	}

	@Autowired
	private PlaneService planeService;


	@Test
	public void whenSaved_thenFindsByName() {
		//given
		Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, 0);

		planeRepository.save(plane);

		//when
		Planes foundPlane = planeRepository.findByCompanyName(plane.getCompanyName());

		//then
		assertEquals(plane.getCompanyName(), foundPlane.getCompanyName());
	}

	@Test
	public void whenSavedMultiple_thenFindsThemAll() {
		//given
		Planes plane1 = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, 0);
		Planes plane2 = new Planes("UNITED", "Pécs", "Szeged", 0, 1, 0);

		List<Planes> planes = new ArrayList<>();
		planes.add(plane1);
		planes.add(plane2);

		//when
		List<Planes> foundPlanes = planeRepository.findAll();

		//then
		assertEquals(foundPlanes.get(0).getCompanyName(), plane1.getCompanyName());
		assertEquals(foundPlanes.get(1).getCompanyName(), plane2.getCompanyName());
	}

	@Test
	public void whenDeleted_doesntFind() {
		//given
		Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, 0);
		planeRepository.save(plane);

		//when
		planeRepository.delete(plane);

		//then
		assertNull(planeRepository.findByCompanyName(plane.getCompanyName()));
	}

	@Test
	public void whenUpdated_thenChanges() {
		//given
		Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, 0);
		planeRepository.save(plane);
		String newDeparturePlace = "Pécs";
		plane.setDeparturePlace(newDeparturePlace);


		//when
		planeService.updatePlane(plane);

		//then
		assertEquals(planeRepository.findByCompanyName(plane.getCompanyName()).getDeparturePlace(), newDeparturePlace);

	}

	@Test
	public void isGetMethodValid() throws Exception {
		Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, 0);
		planeService.savePlane(plane);

		// when
		MockHttpServletResponse response = mockMvc.perform(
				get("/getAll")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertFalse(response.getContentAsString().length() < 1);
	}

	@Test
	public void isPostMethodWorking() throws Exception {
	//given
		int numOfPlanes = planeService.findAllPlanes().size();

		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("companyName", "TestCompany");
		requestParams.add("departurePlace", "TestPlace");
		requestParams.add("landingPlace", "TestPlace");
		requestParams.add("departureTime", "1111-11-11T11:11");
		requestParams.add("landingTime", "0002-12-12T12:12");
		requestParams.add("delay", "3");

		//when
		MockHttpServletResponse request = mockMvc.perform(post("/save")
				.params(requestParams)).andReturn().getResponse();
		int numOfAfterAddingNewPlane = planeService.findAllPlanes().size();

		//then
		assertTrue(numOfPlanes < numOfAfterAddingNewPlane);
	}

	@Test
    public void isDeleteMethodWorking() throws Exception {
        //given
        int numOfPlanes = planeService.findAllPlanes().size();

        //when
        MockHttpServletResponse request = mockMvc.perform(post("/remove")
                .param("planeId", "1")).andReturn().getResponse();
        int numOfAfterRemove = planeService.findAllPlanes().size();

        //then
        assertTrue(numOfPlanes > numOfAfterRemove);

    }

	@Test
	public void isUpdateMethodWorking() throws Exception {
		//given
		Planes plane = planeService.findById(1);

		//sending necessary requestparams
		MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");
		requestParams.add("companyName", "TestCompanyUPDATE");
		requestParams.add("departurePlace", "TestPlace");
		requestParams.add("landingPlace", "TestPlace");
		requestParams.add("departureTime", "1111-11-11T11:11");
		requestParams.add("landingTime", "0002-12-12T12:12");
		requestParams.add("delay", "3");

		//when
		MockHttpServletResponse request = mockMvc.perform(post("/update")
				.params(requestParams)).andReturn().getResponse();
		Planes planeAfterUpdate = planeService.findById(1);


		//then
		assertFalse(plane.getCompanyName().equals(planeAfterUpdate.getCompanyName()));
	}
	@Test
    public void doesFilteringWorks() throws Exception {
	    //given
        Planes plane = planeService.findById(1);

        //when
        MockHttpServletResponse request = mockMvc.perform(post("/filter")
                .param("departureTime", "0002-12-12T12:12")).andReturn().getResponse();

        //then
        assertEquals(request.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void isBusinessLogicWorking_maxDelay(){
	    //given
        int delay = 100;
        Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, delay);
        planeService.savePlane(plane);
        List<Planes> allPlanes = planeService.findAllPlanes();

        //when
        Planes mostDelayedPlane = planeService.getMostDelayedPlane(allPlanes);

        //then
        assertEquals(mostDelayedPlane.getDelay(), delay);
    }

    @Test
    public void isBusinessLogicWorking_avgDelay(){
        //given
        int delay1 = 100;
        int delay2 = 200;
        int avgDelay = 150;
        Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, delay1);
        Planes plane2 = new Planes("QUATAR", "Miskolc", "Pest", 0, 1, delay2);
        List<Planes> allPlanes = new ArrayList<>();
        allPlanes.add(plane);
        allPlanes.add(plane2);

        //when
        OptionalDouble calculatedDelay = planeService.getAvgDelay(allPlanes);


        //then
        assertTrue(avgDelay == calculatedDelay.getAsDouble());
    }


}
