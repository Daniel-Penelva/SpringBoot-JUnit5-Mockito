package com.daniel.testeunitario;

import org.junit.Assert;
import org.junit.Test;

import com.daniel.testeunitario.service.ServiceA;
import com.daniel.testeunitario.service.ServiceAImpl;
import com.daniel.testeunitario.service.ServiceB;
import com.daniel.testeunitario.service.ServiceBImpl;

public class TestServiceB {
    
    @Test
    public void multiplicar(){
        ServiceB serviceB = new ServiceBImpl();

        Assert.assertEquals(serviceB.multiplicar(2,3), 6);
    }

    /* Vale ressaltar que esse ServiceB depende do ServiceA, logo ele tem uma relação de dependência, por exemplo, o método calculoMultiplicar() 
    se observar verá que o "return serviceA.soma(num1, num2) * multiplicador" possui o método "soma(num1, num2)" que pertence ao ServiceA, ou 
    seja, ServiceA.soma(num1, num2). Se modificassemos esse método para somar mais 1, logo o seu retorno ficaria assim,  "return num1 + num2 + 1" 
    e se testasse o teste agora daria erro, pois com esse mais 1 resultaria em 11 e não 10. Portanto, com isso podemos compreender que o ServiceB
    depende do ServiceA para gerar o teste corretamente.  */ 

    @Test
    public void testMultiplicarSomar(){
        
        // ServiceB depende do ServiceA. Percebe-se através dessa instanciação do objeto serviceA.
        ServiceA serviceA = new ServiceAImpl();

        ServiceB serviceB = new ServiceBImpl();

        serviceB.setServiceA(serviceA);

        Assert.assertEquals(serviceB.calculoMultiplicar(2, 3, 2), 10);
    }
}
