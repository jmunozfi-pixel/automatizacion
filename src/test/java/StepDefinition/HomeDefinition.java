package StepDefinition;

import ObjectPage.HomePage;
import io.cucumber.java.en.Then;

public class HomeDefinition {
    @Then("se valida el mensaje Logged In Successfully")
    public void seValidaElMensaje() {
        String mensaje = new HomePage().capturarMensajeBienvenida();
        System.out.println("Mensaje de bienvenida: " + mensaje);
    }

}
