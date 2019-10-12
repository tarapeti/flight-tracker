package com.project.fligthtracker;

import com.project.fligthtracker.model.Planes;
import com.project.fligthtracker.repositroy.PlaneRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FligthtrackerApplicationTests {

	@Autowired
	private PlaneRepository planeRepository;

	@Test
	public void whenSaved_thenFindsByName() {
		//given
		Planes plane = new Planes("QUATAR", "Miskolc", "Pest", 0, 1 ,0);

		planeRepository.save(plane);

		//when
		Planes foundPlane = planeRepository.findByCompanyName(plane.getCompanyName());

		//then
		assertEquals(plane.getCompanyName(), foundPlane.getCompanyName());
	}

	@Test public void whenSavedMultiple_thenFindsThemAll(){
		Planes plane1 = new Planes("QUATAR", "Miskolc", "Pest", 0, 1 ,0);
		Planes plane2 = new Planes("UNITED", "Pécs", "Szeged", 0, 1 ,0);

		List<Planes> planes = new ArrayList<>();
		planes.add(plane1);
		planes.add(plane2);

		List<Planes> foundPlanes = planeRepository.findAll();

		assertEquals(foundPlanes.get(0).getCompanyName(), plane1.getCompanyName());
		assertEquals(foundPlanes.get(1).getCompanyName(), plane2.getCompanyName());
	}


}
