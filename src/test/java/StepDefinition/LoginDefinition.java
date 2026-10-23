package StepDefinition;

import ObjectPage.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginDefinition {

    @And("ingresar el usuario {string}")
    public void ingresarElUsuario(String nombreUsuario) {
        new LoginPage().escribirUsersname(nombreUsuario);
    }

    @And("ingresar la pass {string}")
    public void ingresarLaPass(String password) {
        new LoginPage().escribirPassword(password);
    }

    @When("presiono el boton Submit")
    public void presionoElBotonSubmit() {
        new LoginPage().clickBtnSubmit();
    }

    @Then("se valida el mensaje Your username is invalid!")
    public void seValidaElMensajeUsuarioInvalido() {
        new LoginPage().validarMsgUsuarioInvalido();
    }

}