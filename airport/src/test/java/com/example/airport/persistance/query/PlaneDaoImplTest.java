package com.example.airport.persistance.query;


import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.enumeration.DocumentType;
import com.example.airport.domain.to.ClientDto;
import com.example.airport.domain.to.PlaneDto;
import com.example.airport.persistance.repository.FlightScheduleRepository;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.PlaneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("hsql")
@TestPropertySource(properties = "custom.var.earlie.days=0")
public class PlaneDaoImplTest {

    @Autowired
    private PlaneService service;
    @Autowired
    private PlaneRepository repository;

    @Test
    public void getPlanesToRemoveShouldReturnElements() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        service.add(planeDto1);
        service.add(planeDto2);
        //when
        service.setToRemove(1L);
        service.setToRemove(2L);
        service.getAll();
        List<Plane> seat = repository.getPlanesToRemove();
        //then
        assertEquals(2,seat.size());
    }
    @Test
    public void getPlanesToRemoveShouldReturnEmptyListWhenNotFoundElement() {
        //given
        PlaneDto planeDto1 = this.createPlaneDto(2L,"123123123","Test.Sp.Zoo");
        PlaneDto planeDto2 = this.createPlaneDto(2L,"32131231","Test2.Sp.Zoo");
        service.add(planeDto1);
        service.add(planeDto2);
        //when
        service.getAll();
        List<Plane> seat = repository.getPlanesToRemove();
        //then
        assertEquals(0,seat.size());
    }

    @Test
    public void getPlanesToRemoveShouldReturnEmptyListWhenDbIsEmpty() {
        //given
        //when
        List<Plane> seat = repository.getPlanesToRemove();
        //then
        assertEquals(0,seat.size());
        //TestPropertySource
    }
    private PlaneDto createPlaneDto(Long id, String serialNumber, String nameCarrier) {
        return PlaneDto.builder().withId(id).withSerialNumber(serialNumber).withNameCarrier(nameCarrier).build();
    }
}
