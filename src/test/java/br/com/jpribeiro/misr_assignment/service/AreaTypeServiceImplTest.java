package br.com.jpribeiro.misr_assignment.service;

import br.com.jpribeiro.misr_assignment.exceptions.NotFoundException;
import br.com.jpribeiro.misr_assignment.model.AreaType;
import br.com.jpribeiro.misr_assignment.repository.AreaTypeRepository;
import br.com.jpribeiro.misr_assignment.repository.PlotOfLandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AreaTypeServiceImplTest {

    @InjectMocks
    AreaTypeServiceImpl areaTypeServiceImpl = new AreaTypeServiceImpl();

    @Mock
    private AreaTypeRepository areaTypeRepository;

    @Mock
    private PlotOfLandRepository plotOfLandRepository;

    @Test
    public void testListAll() {
        List<AreaType> areaTypeList = new ArrayList<>();
        areaTypeList.add(new AreaType());
        areaTypeList.add(new AreaType());

        when(areaTypeRepository.findAll()).thenReturn(areaTypeList);

        List<AreaType> areaTypeListFromService = areaTypeServiceImpl.listAll();
        assertEquals(areaTypeListFromService, areaTypeList);
    }

    @Test
    public void testSave() throws NotFoundException {
        AreaType areaType1 = new AreaType();
        areaType1.setId(1);

        AreaType areaType2 = new AreaType();
        areaType2.setId(2);

        when(areaTypeRepository.findById(1)).thenReturn(Optional.of(areaType1));
        when(areaTypeRepository.findById(2)).thenReturn(Optional.empty());

        when(areaTypeRepository.save(areaType1)).thenReturn(areaType1);

        AreaType areaType1FromService = areaTypeServiceImpl.save(areaType1);
        assertEquals(areaType1FromService, areaType1);

        try {
            areaTypeServiceImpl.save(areaType2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

    @Test
    public void testGet() throws NotFoundException {
        AreaType areaType1 = new AreaType();
        areaType1.setId(1);

        when(areaTypeRepository.findById(1)).thenReturn(Optional.of(areaType1));
        when(areaTypeRepository.findById(2)).thenReturn(Optional.empty());

        AreaType areaType1FromService = areaTypeServiceImpl.get(1);
        assertEquals(areaType1FromService, areaType1);

        try {
            areaTypeServiceImpl.get(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

    @Test
    public void testDelete() throws NotFoundException {
        AreaType areaType1 = new AreaType();
        areaType1.setId(1);

        when(areaTypeRepository.findById(1)).thenReturn(Optional.of(areaType1));
        when(areaTypeRepository.findById(2)).thenReturn(Optional.empty());

        doNothing().when(areaTypeRepository).deleteById(1);

        areaTypeServiceImpl.delete(1);

        try {
            areaTypeServiceImpl.delete(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

    @Test
    public void testPredictRatio() throws NotFoundException {
        AreaType areaType1 = new AreaType();
        areaType1.setId(1);

        when(areaTypeRepository.findById(1)).thenReturn(Optional.of(areaType1));
        when(areaTypeRepository.findById(2)).thenReturn(Optional.empty());

        when(plotOfLandRepository.predictRatio(1)).thenReturn(1.23);

        double ratioFromService = areaTypeServiceImpl.predictRatio(1);
        assertEquals(ratioFromService, 1.23, 0);

        try {
            areaTypeServiceImpl.predictRatio(2);
            fail("Should have thrown exception");
        }
        catch (NotFoundException exception) {
            // Expected exception
        }
    }

}
