package com.daniel.testeunitario.examples;

public class ServiceBImpl implements ServiceB{

    private ServiceA serviceA;

    @Override
    public ServiceA getServiceA() {
      return serviceA;
    }

    @Override
    public void setServiceA(ServiceA servicioA) {
       this.serviceA = servicioA;
    }

    @Override
    public int calculoMultiplicar(int num1, int num2, int multiplicador) {
        return serviceA.soma(num1, num2) * multiplicador;

    }

    @Override
    public int multiplicar(int num1, int num2) {
        return num1 * num2;
    }
    
}
