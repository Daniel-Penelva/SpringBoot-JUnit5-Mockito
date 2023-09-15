package com.daniel.testeunitario.examples;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.daniel.testeunitario.examples.ServiceA;
import com.daniel.testeunitario.examples.ServiceB;
import com.daniel.testeunitario.examples.ServiceBImpl;

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
    public void testMultiplicarSomarMockito(){
        ServiceA serviceA = Mockito.mock(ServiceA.class);

        Mockito.when(serviceA.soma(2, 3)).thenReturn(5);

        ServiceB serviceB = new ServiceBImpl();
        serviceB.setServiceA(serviceA);
        Assert.assertEquals(serviceB.calculoMultiplicar(2, 3, 2), 10);
    }

    /* Ao Utilizar o Mockito eliminamos a dependência do ServiceB em relação ao ServiceA, e a principal razão pela qual o Mockito pode eliminar 
    dependências entre métodos é a capacidade de substituir objetos reais por objetos simulados (mocks - simula uma class). Isso é conhecido como 
    "mocking". Quando você está testando um componente específico, deseja isolar esse componente de suas dependências externas para que o teste 
    se concentre apenas no comportamento desse componente. Exemplificando: [ServiceB] <Elimina a dependencia> [Mock ServiceA] */


}
