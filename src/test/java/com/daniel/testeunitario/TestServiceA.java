package com.daniel.testeunitario;

import org.junit.Assert;
import org.junit.Test;

import com.daniel.testeunitario.examples.ServiceA;
import com.daniel.testeunitario.examples.ServiceAImpl;

public class TestServiceA {
    
    @Test
    public void testSomar(){
        int num1 = 3;
        int num2 = 2;

        ServiceA serviceA = new ServiceAImpl();
        Assert.assertEquals(serviceA.soma(num1, num2), 5); 
    }

    /* Mockito vai ser usado quando queremos isolar uma classe de suas dependências, por exemplo o ServiceB depende do ServiceA, ou seja, o 
    ServiceA seria a dependência do ServiceB */ 
    
}
