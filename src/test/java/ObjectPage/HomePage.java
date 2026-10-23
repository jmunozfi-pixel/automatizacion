package ObjectPage;

import Control.BaseController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.fail;

public class HomePage extends BaseController {
    private WebDriver driver;
    
    @FindBy(xpath = "//*[normalize-space() = 'Logged In Successfully']")
    public WebElement msgBienvenida;


    public String capturarMensajeBienvenida(){
        try {
            if(!this.msgBienvenida.isDisplayed()){
                fail("No se visualiza el mensaje Logged In Successfully");
            }
            return this.msgBienvenida.getText();
        }catch(Exception e){
            fail("Error al detectar el mensaje de bienvenida: "+e.getMessage());
        }
        throw new AssertionError("No se pudo capturar el mensaje de bienvenida");
    }

}
