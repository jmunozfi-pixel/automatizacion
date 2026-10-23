package StepDefinition;

import Constant.Navegador;
import Control.DriverContext;
import Control.NavSelector;
import io.cucumber.java.en.Given;
import org.junit.Assert;

public class NavDefinition {

    @Given("que se abre el navegador y se navega a {string}")
    public void abrirNavegadorParaOrangeHrm(String url) throws InterruptedException {
        abrirNavegador(url);
    }

    @Given("abrir el navegador en la url {string}")
    public void abrirNavegador(String url) throws InterruptedException {

        String nav = System.getProperty("nav", "default");
        Navegador navegador = NavSelector.seleccionNavegador(nav);
        DriverContext.setUp(navegador, url);
        Thread.sleep(5000);
        String urlActual = DriverContext.getDriver().getCurrentUrl();
        System.out.println("URL Actual: "+ urlActual);
        Assert.assertEquals(urlActual,url);

    }
}
