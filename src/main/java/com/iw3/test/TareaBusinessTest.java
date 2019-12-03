package com.iw3.test;

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


    private static Tarea tareaExceptionTarea = new Tarea();
    
    @BeforeClass
    public static void setup() throws BusinessException, ProyectoException, TareaException {        
        tareaExceptionTarea.setNombre("tareaExceptionTarea");
        Lista lista2 = new Lista();
        lista2.setId(500);
        tareaExceptionTarea.setLista(lista2);
    }

	
    @Test(expected = com.iw3.exeptions.TareaException.class)
    public void testLoadNotFoundException() throws  BusinessException, NotFoundException, TareaException {
        tareaBusiness.crearTarea(tareaExceptionTarea);
        expectedEx.expect(com.iw3.exeptions.TareaException.class);
        expectedEx.expectMessage("No existe la lista.");
    }

    
}
