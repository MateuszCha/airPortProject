package com.example.airport.persistance.service;

import com.example.airport.components.aspect.TimeCounterAspect;
import com.example.airport.components.configuration.AspectConfig;
import com.example.airport.domain.entity.Booked;
import com.example.airport.domain.entity.Plane;
import com.example.airport.domain.entity.Seat;
import com.example.airport.domain.enumeration.BookedState;
import com.example.airport.domain.enumeration.CategoryType;
import com.example.airport.domain.enumeration.SeatPosition;
import com.example.airport.domain.enumeration.SoldType;
import com.example.airport.domain.to.PlaneDto;
import com.example.airport.domain.to.SeatDto;
import com.example.airport.persistance.exception.DifferentVersion;
import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.mapper.SeatMapper;
import com.example.airport.persistance.mapper.impl.SeatMapperImpl;
import com.example.airport.persistance.repository.BookedRepository;
import com.example.airport.persistance.repository.PlaneRepository;
import com.example.airport.persistance.repository.SeatRepository;
import com.example.airport.persistance.service.crud.SeatService;
import com.example.airport.persistance.service.crud.impl.SeatServiceImpl;
import com.example.airport.persistance.validation.SeatValidator;
import com.example.airport.web.config.WebConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("hsql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SeatServiceImplTest {

    @Autowired
    private SeatService service;
    @Autowired
    private PlaneRepository planeRepository;
    @Autowired
    private BookedRepository bookedRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private AspectConfig config;
    @Autowired
    private EntityManagerFactory emf;

    @Before
    public void setUp(){
        planeRepository.save(new Plane(null,"111111","Name1",null,null));
        planeRepository.save(new Plane(null,"222222","Name2",null,null));
        bookedRepository.save(new Booked(null,123123L,7012d, SoldType.SOLD_AIRPORT, BookedState.SOLD, LocalDateTime.now(),null,null));
        bookedRepository.save(new Booked(null,22222L,7012d, SoldType.SOLD_AIRPORT, BookedState.SOLD, LocalDateTime.now(),null,null));
    }

    @Test
    public void addShouldAddElement() {
        //given
        SeatDto expect = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        List<Plane> plane = planeRepository.findAll();
        List<Booked> bookeds = bookedRepository.findAll();
        //when
        SeatDto result = service.add(expect,1L);
        //then
        assertNotNull(result);
        this.compareSeatDto(expect,result);
        Assertions.assertEquals(expect.getVersion(), result.getVersion());
    }
    @Test
    public void addShouldAddElementWhenElementOnIndexExistOnDb() {
        //given
        SeatDto dto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto expect = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        List<SeatDto> beforeAdd = service.getAll();
        service.add(dto1,1L);
        SeatDto result = service.add(expect,1L);
        List<SeatDto> afterAdd = service.getAll();
        //then
        assertEquals(0,beforeAdd.size());
        assertEquals(2,afterAdd.size());
        assertNotNull(result);
        Assertions.assertEquals(expect.getVersion(), result.getVersion());
    }
    @Test(expected = IllegalArgumentException.class)
    public void addShouldThrowExceptionWhenElementIsNull() {
        //given
        SeatDto dto1 = null;
        //when
        service.add(dto1,1L);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void addShouldThrowExceptionWhenIndexPlaneIsNoExist() {
        //given
        SeatDto seatDto = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        Long planeIndex = Long.MAX_VALUE;
        //when
        service.add(seatDto,planeIndex);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void addShouldThrowExceptionWhenIndexPlaneIsNull() {
        //given
        SeatDto seatDto = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        Long planeIndex = null;
        //when
        service.add(seatDto,planeIndex);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void addShouldThrowExceptionWhenIndexPlaneIsLessThanOne() {
        //given
        SeatDto seatDto = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        Long planeIndex = 0L;
        //when
        service.add(seatDto,planeIndex);
        //then
    }


    @Test
    public void getShouldReturnElementOnIndex() {
        //given
        Long index = 2L;
        SeatDto dto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto expect = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        service.add(dto1,1L);
        service.add(expect,2L);
        SeatDto result = service.get(index);
        //then
        this.checkAllParametersInDtoAreNotNull(result);
        assertEquals(index, result.getId());
        this.compareSeatDto(expect,result);
        Assertions.assertEquals(expect.getVersion(), result.getVersion());
    }

    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenNotFindElement() {
        //given
        Long index = 14L;
        SeatDto seatDto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        service.add(seatDto,2L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long index = 0L;
        SeatDto seatDto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        service.add(seatDto,1L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long index = Long.MIN_VALUE;
        SeatDto seatDto =this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        service.add(seatDto,2L);
        service.get(index);
        //then
    }
    @Test(expected = IllegalIndexEntity.class)
    public void getShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long index = null;
        SeatDto seatDto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        //when
        service.add(seatDto,1L);
        service.get(index);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void getShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long index = 1L;
        //when
        service.get(index);
        //then
    }

    @Test
    public void getAllShouldReturnAllElement() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
         //when
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        List<SeatDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(2,resultList.size());
        assertNotNull(resultList.get(0));
        assertNotNull(resultList.get(1));
        this.compareSeatDto(seatDto1,resultList.get(0));
        this.compareSeatDto(seatDto2,resultList.get(1));
        Assertions.assertEquals(seatDto1.getVersion(), resultList.get(0).getVersion());
        Assertions.assertEquals(seatDto2.getVersion(), resultList.get(0).getVersion());
    }
    @Test
    public void getAllShouldReturnZeroWhenDBisEmpty() {
        //given
        // when
        List<SeatDto> resultList = service.getAll();
        //then
        assertNotNull(resultList);
        assertEquals(0,resultList.size());
    }

    @Test
    public void removeShouldRemoveElement() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        Long removeIndex = 2L;
        //when
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        List<SeatDto> beforeRemove = service.getAll();
        SeatDto result = service.remove(removeIndex);
        List<SeatDto> afterRemove = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(2,beforeRemove.size());
        assertEquals(1,afterRemove.size());
        this.compareSeatDto(beforeRemove.get(0),afterRemove.get(0));
        this.compareSeatDto(beforeRemove.get(1),result);
        Assertions.assertEquals(seatDto2.getVersion(), result.getVersion());
    }

    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenIndexNotExist() {
        //given
        SeatDto seatDto = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        Long removeIndex = 4L;
        //when
        service.add(seatDto,1L);
        SeatDto result = service.remove(removeIndex);
        //then
    }
    @Test (expected = NoFoundEntity.class)
    public void removeShouldThrowExceptionWhenDBisEmpty() {
        //given
        Long removeIndex = 4L;
        //when
        SeatDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        Long removeIndex = 0L;
        //when
        SeatDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        Long removeIndex = Long.MIN_VALUE;
        //when
        SeatDto result = service.remove(removeIndex);
        //then
    }

    @Test (expected = IllegalIndexEntity.class)
    public void removeShouldThrowExceptionWhenIndexIsNull() {
        //given
        Long removeIndex = null;
        //when
        SeatDto result = service.remove(removeIndex);
        //then
    }
    @Test
    public void updateShouldUpdateElement() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        service.add(seatDto1,1L);
        SeatDto expect = service.add(seatDto2,2L);
        expect.setCategoryType(CategoryType.OTHER);
        expect.setColumn(17);
        expect.setRow(1);
        expect.setPosition(SeatPosition.RIGHT);
        List<SeatDto> beforeUpdate = service.getAll();
        SeatDto result = service.update(expect,null);
        List<SeatDto> afterUpdate = service.getAll();
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.compareSeatDto(beforeUpdate.get(0),afterUpdate.get(0));
        this.compareSeatDto(afterUpdate.get(1),result);
        Assertions.assertEquals(expect.getVersion(), result.getVersion());
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenIdNoExist() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        service.add(seatDto1,1L);
        SeatDto expect = service.add(seatDto2,2L);
        expect.setId(5L);
        expect.setCategoryType(CategoryType.OTHER);
        expect.setColumn(17);
        expect.setRow(1);
        service.update(expect,1L);
        //then
    }
    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowExceptionWhenElementIsNull() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);

        //when
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        service.update(null,1L);
        //then
    }
    @Test(expected = NoFoundEntity.class)
    public void updateShouldThrowExceptionWhenPlaneIndexIsNoExist() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        Long planeIndex = Long.MAX_VALUE;
        //when
        SeatDto expect = service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        expect.setColumn(1782);
        service.update(expect,planeIndex);
        //then
    }
    @Test
    public void updateShouldUpdateElementWhenPlaneInexIsSet() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        SeatDto expect = service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        expect.setColumn(1782);
        SeatDto result = service.update(expect,1L);
        //then
        assertNotNull(result);
        this.compareSeatDto(expect,result);
        Assertions.assertEquals(expect.getVersion(), result.getVersion());
    }

    @Test
    public void updateShouldChangeVersionWhenUpdateExistObject() {
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        service.add(seatDto1,1L);
        SeatDto expect = service.add(seatDto2,2L);
        expect.setCategoryType(CategoryType.OTHER);
        expect.setColumn(17);
        expect.setRow(1);
        expect.setPosition(SeatPosition.RIGHT);
        List<SeatDto> beforeUpdate = service.getAll();
        service.update(expect,null);
        List<SeatDto> afterUpdate = service.getAll();
        SeatDto result = service.get(expect.getId());
        //then
        assertNotNull(result);
        assertEquals(beforeUpdate.size(), afterUpdate.size());
        this.compareSeatDto(beforeUpdate.get(0),afterUpdate.get(0));
        this.compareSeatDto(afterUpdate.get(1),result);
        Assertions.assertEquals(expect.getVersion() + 1, result.getVersion());
    }
    @Test(expected = DifferentVersion.class)
    public void updateShouldThrowExceptionWhenVersionIsDifferent() {
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        //when
        service.add(seatDto1,1L);
        SeatDto expect = service.add(seatDto2,2L);
        expect.setCategoryType(CategoryType.OTHER);
        expect.setColumn(17);
        expect.setRow(1);
        expect.setPosition(SeatPosition.RIGHT);
        List<SeatDto> beforeUpdate = service.getAll();
        SeatDto result = service.update(expect,null);
        List<SeatDto> afterUpdate = service.getAll();
        SeatDto test = service.get(expect.getId());
        expect.setCategoryType(CategoryType.PREMIUM);
        expect.setColumn(1789654);
        result = service.update(expect,null);
    }
    @Test
    public void setToRemoveShouldSetElementToRemove() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        //when
        service.setToRemove(2L);
        List<SeatDto> expect = service.getAll();
        //then
        assertEquals(2,expect.size());
        assertEquals(false,expect.get(0).isRemove());
        assertEquals(true,expect.get(1).isRemove());
    }
    @Test(expected = IllegalIndexEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIndexIsLessThanOne() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        //when
        service.setToRemove(0L);

    }
    @Test(expected = IllegalIndexEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIndexIsLessThanZero() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        //when
        service.setToRemove(-1L);

    }
    @Test(expected = NoFoundEntity.class)
    public void setToRemoveShouldThrowExceptionWhenIEntityNotFound() {
        //given
        SeatDto seatDto1 = this.createSeatDto(1L,1,1,CategoryType.BUSINESS,SeatPosition.LEFT,true);
        SeatDto seatDto2 = this.createSeatDto(2L,0,0,CategoryType.ECONOMIC,SeatPosition.RIGHT,false);
        service.add(seatDto1,1L);
        service.add(seatDto2,2L);
        //when
        service.setToRemove(3L);
    }
    private void checkAllParametersInDtoAreNotNull(SeatDto dto){
        assertNotNull(dto);
        assertNotNull(dto.getId());
        assertNotNull(dto.getRow());
        assertNotNull(dto.getColumn());
        assertNotNull(dto.getCategoryType());
        assertNotNull(dto.getPosition());
        assertNotNull(dto.getEnable());
    }
    private void checkAllParametersInDtoAreNull(SeatDto dto){
        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getRow());
        assertNull(dto.getColumn());
        assertNull(dto.getCategoryType());
        assertNull(dto.getPosition());
        assertNull(dto.getEnable());
    }
    private void compareSeatDto(SeatDto expect, SeatDto result){
        Assertions.assertEquals(expect.getRow(), result.getRow());
        Assertions.assertEquals(expect.getColumn(), result.getColumn());
        Assertions.assertEquals(expect.getCategoryType(), result.getCategoryType());
        Assertions.assertEquals(expect.getPosition(), result.getPosition());
        Assertions.assertEquals(expect.getEnable(), result.getEnable());
    }

    private SeatDto createSeatDto(Long id, Integer row, Integer column, CategoryType categoryType, SeatPosition position, Boolean enable) {
        return SeatDto.builder().withId(id).withRowNumber(row).withColumnNumber(column).withCategoryType(categoryType).withSeatPosition(position).withEnableSeat(enable).build();
    }
}
