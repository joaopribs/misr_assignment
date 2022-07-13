package br.com.jpribeiro.misr_assignment.controller;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.AreaType;
import br.com.jpribeiro.misr_assignment.service.AreaTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AreaTypeControllerTest {

    @InjectMocks
    AreaTypeController areaTypeController = new AreaTypeController();

    @Mock
    AreaTypeService areaTypeService;

    @Test
    public void testCreate() throws NotFoundException {
        AreaType areaTypeToCreate = new AreaType();
        AreaType areaTypeSaved = new AreaType();

        when(areaTypeService.save(areaTypeToCreate)).thenReturn(areaTypeSaved);

        AreaType areaTypeThatWasCreated = areaTypeController.create(areaTypeToCreate);
        assertEquals(areaTypeThatWasCreated, areaTypeSaved);
    }

    @Test
    public void testGetById() throws NotFoundException {
        AreaType areaType1 = new AreaType();
        AreaType areaType2 = new AreaType();

        when(areaTypeService.get(1)).thenReturn(areaType1);
        when(areaTypeService.get(2)).thenReturn(areaType2);

        AreaType areaTypeFromController1 = areaTypeController.get(1);
        assertEquals(areaTypeFromController1, areaType1);

        AreaType areaTypeFromController2 = areaTypeController.get(2);
        assertEquals(areaTypeFromController2, areaType2);
    }

    @Test
    public void testListAll() {
        List<AreaType> areaTypeList = new ArrayList<>();
        areaTypeList.add(new AreaType());
        areaTypeList.add(new AreaType());

        when(areaTypeService.listAll()).thenReturn(areaTypeList);

        List<AreaType> areaTypeListFromController = areaTypeController.listAll();
        assertEquals(areaTypeListFromController, areaTypeList);
    }

    @Test
    public void testEdit() throws NotFoundException {
        AreaType areaTypeToEdit = new AreaType();

        when(areaTypeService.save(areaTypeToEdit)).thenReturn(areaTypeToEdit);

        AreaType areaTypeFromController = areaTypeController.edit(1, areaTypeToEdit);
        assertEquals(areaTypeFromController, areaTypeToEdit);
    }

    @Test
    public void testDelete() throws NotFoundException {
        doNothing().when(areaTypeService).delete(1);
        doThrow(new NotFoundException()).when(areaTypeService).delete(2);

        areaTypeController.delete(1);

        try {
            areaTypeController.delete(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException e) {
            // Expected exception
        }
    }

    @Test
    public void testPredictRatio() throws NotFoundException {
        when(areaTypeService.predictRatio(1)).thenReturn(1.23);

        double resultFromController = areaTypeController.predictRatio(1);
        assertEquals(resultFromController, 1.23);
    }
}
