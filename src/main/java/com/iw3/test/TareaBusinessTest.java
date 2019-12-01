package com.iw3.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iw3.business.TareaBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.ProyectoException;
import com.iw3.exeptions.TareaException;
import com.iw3.model.Lista;
import com.iw3.model.Tarea;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TareaBusinessTest {

	
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TareaBusiness tareaBusiness;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private static Tarea tarea1 = new Tarea();
    private static Tarea tarea2 = new Tarea();

    @BeforeClass
    public static void setup() throws BusinessException, ProyectoException, TareaException {
        tarea1.setId(500);
        tarea1.setEstimacion(5.0);
        tarea1.setNombre("Test_Tarea_Name");
        Date date = new GregorianCalendar(2019, Calendar.DECEMBER, 1).getTime();
        tarea1.setFechaCreacion(date);
        tarea1.setPrioridad("Alta");
        Lista lista = new Lista();
        lista.setNombre("backlog");
        tarea1.setLista(lista);
        
        tarea2.setId(501);
        tarea2.setEstimacion(5.0);
        tarea2.setNombre("Test_Tarea_Name");
        Date date2 = new GregorianCalendar(2019, Calendar.DECEMBER, 1).getTime();
        tarea2.setFechaCreacion(date2);
        tarea2.setPrioridad("Alta");
        tarea2.setLista(lista);
    }

	
	
    @Test
    public void testUpdateFailure() throws  BusinessException, NotFoundException, TareaException  {
        tareaBusiness.crearTarea(tarea1);
    	String testName = "test";
    	tarea1.setNombre(testName);
    	testName= "Not_equal_test";
        assertNotEquals(testName, tareaBusiness.updateTarea(tarea1).getNombre());
    }
    
    @Test
    public void testLoadSuccess() throws  BusinessException, NotFoundException  {
        assertEquals("Test_Tarea_Name", tareaBusiness.obtenerTarea(501).getNombre());
    }

    @Test(expected = com.iw3.exeptions.TareaException.class)
    public void testLoadNotFoundException() throws  BusinessException, NotFoundException, TareaException  {
        tareaBusiness.crearTarea(tarea2);
        expectedEx.expect(com.iw3.exeptions.TareaException.class);
        expectedEx.expectMessage("No tiene una lista asignada");
    }
}
