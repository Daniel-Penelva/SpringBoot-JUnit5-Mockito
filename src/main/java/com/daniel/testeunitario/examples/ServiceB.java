package com.daniel.testeunitario.examples;

public interface ServiceB {
    
    public ServiceA getServiceA();

    public void setServiceA(ServiceA servicioA);

    public int calculoMultiplicar(int num1, int num2, int multiplicador);
    public int multiplicar(int num1, int num2);

    // OBS. O ServiceB vai depender do ServiceA
}
