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
		requestParams.add("lateByMins", "3");

		//when
		MockHttpServletResponse request = mockMvc.perform(post("/save")
				.params(requestParams)).andReturn().getResponse();
		int numOfafterAddingNewPlane = planeService.findAllPlanes().size();

		//then
		assertTrue(numOfPlanes < numOfafterAddingNewPlane);
	}
}
