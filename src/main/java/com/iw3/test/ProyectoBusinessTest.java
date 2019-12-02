package com.iw3.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private static Proyecto proyTestUpdateFailure = new Proyecto();
    private static Proyecto proyTestInsert = new Proyecto();;
    private static Proyecto proyTestInsertFailure = new Proyecto();;
    private static Proyecto proyTestUpdate = new Proyecto();
    private static Proyecto proyTestNotFound= new Proyecto();
    
    private  boolean proy1Created = false;

    @BeforeClass
    public static void setup() throws BusinessException, ProyectoException {
        proyTestUpdateFailure.setNombre("Proyecto_test_update_failure");
        proyTestInsert.setNombre("Proyecto_test_insert");
        proyTestInsertFailure.setNombre("Proyecto_test_insert_failure");
        proyTestUpdate.setNombre("Proyecto_test_update");
    }


    @Test
    public void testUpdateSuccess() throws  BusinessException, NotFoundException, ProyectoException  {
    	
    	proyTestUpdate = proyectoBusiness.crearProyecto(proyTestUpdate);
    	String testName = "test";
    	proyTestUpdate.setNombre(testName);
        assertEquals(testName, proyectoBusiness.updateProyecto(proyTestUpdate).getNombre());
    }
 
    @Test
    public void testUpdateFailure() throws  BusinessException, NotFoundException, ProyectoException  {
    	String testName = "test2";
    	proyTestUpdateFailure = proyectoBusiness.crearProyecto(proyTestUpdateFailure);
    	proyTestUpdateFailure.setNombre(testName);
    	testName= "Not_equal_test_name";
        assertNotEquals(testName, proyectoBusiness.updateProyecto(proyTestUpdateFailure).getNombre());
    }
    
    @Test
    public void testInsertSuccess() throws  BusinessException, NotFoundException, ProyectoException  {
        assertEquals("Proyecto_test_insert", proyectoBusiness.crearProyecto(proyTestInsert).getNombre());
    }
    
    @Test
    public void testInsertFailure() throws  BusinessException, NotFoundException, ProyectoException  {
        assertNotEquals("Proyecto_test_not_equal", proyectoBusiness.crearProyecto(proyTestInsertFailure).getNombre());
    }

    
    @Test(expected = com.iw3.exeptions.NotFoundException.class)
    public void testLoadNotFoundException() throws  BusinessException, NotFoundException, ProyectoException  {
        int id = 128;
        proyTestNotFound.setId(id);
        proyectoBusiness.updateProyecto(proyTestNotFound);
        expectedEx.expect(com.iw3.exeptions.NotFoundException.class);
        expectedEx.expectMessage("No existe el proyecto a modificar");
    }
	
}
