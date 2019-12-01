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

import com.iw3.business.ProyectoBusiness;
import com.iw3.exeptions.BusinessException;
import com.iw3.exeptions.NotFoundException;
import com.iw3.exeptions.ProyectoException;
import com.iw3.model.Proyecto;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProyectoBusinessTest {

	
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProyectoBusiness proyectoBusiness;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private static Proyecto proy1 = new Proyecto();

    @BeforeClass
    public static void setup() throws BusinessException, ProyectoException {
        proy1.setId(500);
        proy1.setNombre("Proyecto_test");
        Date date = new GregorianCalendar(2019, Calendar.DECEMBER, 1).getTime();
        proy1.setFecha_creacion(date);

    }


    @Test
    public void testUpdateSuccess() throws  BusinessException, NotFoundException, ProyectoException  {
        proyectoBusiness.crearProyecto(proy1);
    	String testName = "test";
    	proy1.setNombre(testName);
        assertEquals(testName, proyectoBusiness.updateProyecto(proy1).getNombre());
    }
    
    @Test
    public void testUpdateFailure() throws  BusinessException, NotFoundException, ProyectoException  {
    	proyectoBusiness.crearProyecto(proy1);
    	String testName = "test";
    	proy1.setNombre(testName);
    	testName= "Not_equal_test";
        assertNotEquals(testName, proyectoBusiness.updateProyecto(proy1).getNombre());
    }
    
    @Test
    public void testInsertSuccess() throws  BusinessException, NotFoundException, ProyectoException  {
        assertEquals("Proyecto_test", proyectoBusiness.crearProyecto(proy1).getNombre());
    }

    @Test
    public void testInsertFailure() throws  BusinessException, NotFoundException, ProyectoException  {
        assertNotEquals("Proyecto_test_not_equal", proyectoBusiness.crearProyecto(proy1).getNombre());
    }


    @Test(expected = com.iw3.exeptions.NotFoundException.class)
    public void testLoadNotFoundException() throws  BusinessException, NotFoundException, ProyectoException  {
        int id = 128;
        proy1.setId(id);
        proyectoBusiness.updateProyecto(proy1);
        expectedEx.expect(com.iw3.exeptions.NotFoundException.class);
        expectedEx.expectMessage("No existe el proyecto a modificar");
    }
	
}
