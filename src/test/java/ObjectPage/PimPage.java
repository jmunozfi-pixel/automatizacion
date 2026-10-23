package ObjectPage;

import Control.DriverContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class PimPage {
    private final WebDriver driver = DriverContext.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    private final By employeeNameInput = By.xpath(
            "//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By employeeIdInput = By.xpath(
            "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By employmentStatusDropdown = By.xpath(
            "//label[normalize-space()='Employment Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    private final By includeDropdown = By.xpath(
            "//label[normalize-space()='Include']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    private final By supervisorInput = By.xpath(
            "//label[contains(normalize-space(),'Supervisor')]/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By autocompleteOptions = By.cssSelector(".oxd-autocomplete-option");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By resultRows = By.cssSelector(".oxd-table-body .oxd-table-card");
    private final By tableHeaders = By.cssSelector(".oxd-table-header-cell");
    private final By tableCells = By.cssSelector(".oxd-table-cell");

    public void abrirModuloPim() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='PIM']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
    }

    public void ingresarNombreEmpleado(String nombre) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        seleccionarAutocompletado(input, nombre, "empleado");
    }

    public boolean ingresarNombreEmpleadoParaCombinacion(String nombre) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        return seleccionarAutocompletadoSiExiste(input, nombre);
    }

    public void ingresarIdEmpleado(String employeeId) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdInput));
        input.clear();
        input.sendKeys(employeeId);
    }

    public void seleccionarEstadoLaboral(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(employmentStatusDropdown)).click();
        By option = By.xpath("//div[contains(@class,'oxd-select-option') and normalize-space(.)='"
                + status + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public boolean seleccionarEstadoLaboralParaCombinacion(String status) {
        return seleccionarOpcionDropdownSiExiste(employmentStatusDropdown, status);
    }

    public void seleccionarOpcionInclude(String includeOption) {
        seleccionarOpcionDropdown("Include", includeOption);
    }

    public boolean seleccionarOpcionIncludeParaCombinacion(String includeOption) {
        return seleccionarOpcionDropdownSiExiste(obtenerDropdown("Include"), includeOption);
    }

    public String obtenerOpcionIncludeSeleccionada() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(includeDropdown)).getText().trim();
    }

    public void seleccionarTituloTrabajo(String jobTitle) {
        seleccionarOpcionDropdown("Job Title", jobTitle);
    }

    public boolean seleccionarTituloTrabajoParaCombinacion(String jobTitle) {
        return seleccionarOpcionDropdownSiExiste(obtenerDropdown("Job Title"), jobTitle);
    }

    public void seleccionarSubUnidad(String subUnit) {
        seleccionarOpcionDropdown("Sub Unit", subUnit);
    }

    public boolean seleccionarSubUnidadParaCombinacion(String subUnit) {
        return seleccionarOpcionDropdownSiExiste(obtenerDropdown("Sub Unit"), subUnit);
    }

    public void seleccionarSupervisor(String supervisor) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(supervisorInput));
        seleccionarAutocompletado(input, supervisor, "supervisor");
    }

    public boolean seleccionarSupervisorParaCombinacion(String supervisor) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(supervisorInput));
        return seleccionarAutocompletadoSiExiste(input, supervisor);
    }

    private boolean seleccionarAutocompletadoSiExiste(WebElement input, String valor) {
        input.clear();
        input.sendKeys(valor);

        WebElement option;
        try {
            option = wait.until(webDriver -> webDriver.findElements(autocompleteOptions).stream()
                    .filter(WebElement::isDisplayed)
                    .filter(element -> {
                        String texto = normalizarTexto(element.getText());
                        return texto.contains("no records found")
                                || texto.contains(normalizarTexto(valor));
                    })
                    .findFirst()
                    .orElse(null));
        } catch (TimeoutException exception) {
            input.clear();
            return false;
        }

        if (normalizarTexto(option.getText()).contains("no records found")) {
            input.clear();
            return false;
        }
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        return true;
    }

    private void seleccionarAutocompletado(WebElement input, String valor, String tipoRegistro) {
        input.clear();
        input.sendKeys(valor);

        WebElement option;
        try {
            option = wait.until(webDriver -> webDriver.findElements(autocompleteOptions).stream()
                    .filter(WebElement::isDisplayed)
                    .filter(element -> {
                        String texto = normalizarTexto(element.getText());
                        return texto.contains("no records found")
                                || texto.contains(normalizarTexto(valor));
                    })
                    .findFirst()
                    .orElse(null));
        } catch (TimeoutException exception) {
            throw new NoSuchElementException(
                    "No existe un registro de " + tipoRegistro + " con el valor '" + valor + "'.",
                    exception);
        }

        if (normalizarTexto(option.getText()).contains("no records found")) {
            throw new NoSuchElementException(
                    "No existe un registro de " + tipoRegistro + " con el valor '" + valor + "'.");
        }
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private String normalizarTexto(String texto) {
        return texto.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }

    public String obtenerSupervisorSeleccionado() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(supervisorInput))
                .getAttribute("value");
    }

    public boolean todosLosResultadosCoinciden(
            String employeeName,
            String employeeId,
            String status,
            String jobTitle,
            String subUnit) {
        List<WebElement> rows = driver.findElements(resultRows);
        if (rows.isEmpty()) {
            System.out.println("No se encontró la combinación de filtros probados.");
            return true;
        }

        System.out.println("(" + rows.size() + ") Record Found");
        int idColumn = obtenerIndiceColumna("Id");
        int firstNameColumn = obtenerIndiceColumna("First (& Middle) Name");
        int lastNameColumn = obtenerIndiceColumna("Last Name");
        int jobTitleColumn = obtenerIndiceColumna("Job Title");
        int statusColumn = obtenerIndiceColumna("Employment Status");
        int subUnitColumn = obtenerIndiceColumna("Sub Unit");

        return rows.stream().allMatch(row -> {
            List<WebElement> cells = row.findElements(tableCells);
            String fullName = cells.get(firstNameColumn).getText() + " "
                    + cells.get(lastNameColumn).getText();
            return fullName.contains(employeeName)
                    && cells.get(idColumn).getText().trim().equals(employeeId)
                    && cells.get(jobTitleColumn).getText().trim().equals(jobTitle)
                    && cells.get(statusColumn).getText().trim().equals(status)
                    && cells.get(subUnitColumn).getText().trim().equals(subUnit);
        });
    }

    private void seleccionarOpcionDropdown(String label, String value) {
        By dropdown = obtenerDropdown(label);
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        By option = By.xpath("//div[contains(@class,'oxd-select-option') and normalize-space(.)='"
                + value + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private By obtenerDropdown(String label) {
        return By.xpath("//label[normalize-space()='" + label
                + "']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");
    }

    private boolean seleccionarOpcionDropdownSiExiste(By dropdown, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-select-dropdown")));
        By option = By.xpath("//div[contains(@class,'oxd-select-option') and normalize-space(.)='"
                + value + "']");
        List<WebElement> options = driver.findElements(option);
        if (options.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
            return false;
        }
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        return true;
    }

    private int obtenerIndiceColumna(String columnName) {
        List<WebElement> headers = driver.findElements(tableHeaders);
        for (int index = 0; index < headers.size(); index++) {
            if (headers.get(index).getText().trim().equals(columnName)) {
                return index;
            }
        }
        throw new NoSuchElementException("No se encontró la columna " + columnName + " en la tabla");
    }

    public void buscar() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-body")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".oxd-loading-spinner")));
    }

    public List<String> obtenerTextosResultados() {
        return driver.findElements(resultRows).stream()
                .map(WebElement::getText)
                .toList();
    }

    public int contarResultados() {
        return driver.findElements(resultRows).size();
    }

    public List<String> obtenerEstadosLaboralesDeResultados() {
        List<WebElement> headers = driver.findElements(tableHeaders);
        int statusColumnIndex = -1;
        for (int index = 0; index < headers.size(); index++) {
            if (headers.get(index).getText().trim().equals("Employment Status")) {
                statusColumnIndex = index;
                break;
            }
        }
        if (statusColumnIndex < 0) {
            throw new NoSuchElementException("No se encontró la columna Employment Status en la tabla");
        }

        final int columnIndex = statusColumnIndex;
        return driver.findElements(resultRows).stream()
                .map(row -> row.findElements(tableCells).get(columnIndex).getText().trim())
                .toList();
    }
}
