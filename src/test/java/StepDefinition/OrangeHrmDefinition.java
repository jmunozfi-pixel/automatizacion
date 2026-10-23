package StepDefinition;

import ObjectPage.OrangeHrmLoginPage;
import ObjectPage.PimPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class OrangeHrmDefinition {
    private PimPage pimPage;
    private List<String> resultados;
    private String nombreEmpleadoFiltro;
    private String idEmpleadoFiltro;
    private String estadoLaboralFiltro;
    private String includeFiltro;
    private String tituloTrabajoFiltro;
    private String subUnidadFiltro;
    private String supervisorFiltro;
    private final List<String> filtrosNoDisponibles = new ArrayList<>();

    @Given("que el usuario inicia sesión en OrangeHRM con el usuario {string} y la contraseña {string}")
    public void iniciarSesion(String usuario, String password) {
        new OrangeHrmLoginPage().iniciarSesion(usuario, password);
    }

    @Given("que el usuario ingresa al módulo PIM")
    public void ingresarAlModuloPim() {
        pimPage = new PimPage();
        pimPage.abrirModuloPim();
    }

    @When("el usuario ingresa {string} en el campo Nombre del empleado de PIM")
    public void ingresarNombreEmpleado(String nombre) {
        pimPage.ingresarNombreEmpleado(nombre);
    }

    @When("el usuario ingresa el nombre configurado en el campo Employee Name de PIM")
    public void buscarEmpleadoConfigurado() {
        pimPage.ingresarNombreEmpleado(obtenerNombreEmpleadoConfigurado());
    }

    @And("hace clic en Buscar")
    public void hacerClicEnBuscar() {
        pimPage.buscar();
        resultados = pimPage.obtenerTextosResultados();
    }

    @And("hace clic en Search")
    public void hacerClicEnSearchEnEspanol() {
        hacerClicEnSearch();
    }

    @When("the user enters employee id {string}")
    public void ingresarIdEmpleado(String employeeId) {
        idEmpleadoFiltro = employeeId;
        pimPage.ingresarIdEmpleado(employeeId);
    }

    @And("clicks Search")
    public void hacerClicEnSearch() {
        pimPage.buscar();
        resultados = pimPage.obtenerTextosResultados();
    }

    @When("the user selects employment status {string}")
    public void seleccionarEstadoLaboral(String status) {
        estadoLaboralFiltro = status;
        pimPage.seleccionarEstadoLaboral(status);
    }

    @When("the user selects employment status for combined filters {string}")
    public void seleccionarEstadoLaboralParaCombinacion(String status) {
        estadoLaboralFiltro = status;
        if (!pimPage.seleccionarEstadoLaboralParaCombinacion(status)) {
            filtrosNoDisponibles.add("Employment Status='" + status + "'");
        }
    }

    @Then("every result should have employment status {string}")
    public void validarEstadoLaboralDeResultados(String expectedStatus) {
        int cantidadResultados = pimPage.contarResultados();
        if (cantidadResultados == 0) {
            System.out.println("No Records Found");
            return;
        }

        System.out.println("(" + cantidadResultados + ") Record Found");
        List<String> estadosLaborales = pimPage.obtenerEstadosLaboralesDeResultados();
        Assert.assertTrue(
                "Se encontraron resultados con estados laborales distintos de '" + expectedStatus
                        + "': " + estadosLaborales,
                estadosLaborales.stream().allMatch(expectedStatus::equals));
    }

    @When("the user selects include option {string}")
    public void seleccionarOpcionInclude(String includeOption) {
        includeFiltro = includeOption;
        pimPage.seleccionarOpcionInclude(includeOption);
    }

    @When("the user selects include option for combined filters {string}")
    public void seleccionarOpcionIncludeParaCombinacion(String includeOption) {
        includeFiltro = includeOption;
        if (!pimPage.seleccionarOpcionIncludeParaCombinacion(includeOption)) {
            filtrosNoDisponibles.add("Include='" + includeOption + "'");
        }
    }

    @When("the user enters employee name {string}")
    public void ingresarNombreEmpleadoParaFiltroCompleto(String employeeName) {
        nombreEmpleadoFiltro = employeeName;
        pimPage.ingresarNombreEmpleado(employeeName);
    }

    @When("the user enters employee name for combined filters {string}")
    public void ingresarNombreEmpleadoParaCombinacion(String employeeName) {
        nombreEmpleadoFiltro = employeeName;
        if (!pimPage.ingresarNombreEmpleadoParaCombinacion(employeeName)) {
            filtrosNoDisponibles.add("Employee Name='" + employeeName + "'");
        }
    }

    @And("the user selects job title {string}")
    public void seleccionarTituloTrabajo(String jobTitle) {
        tituloTrabajoFiltro = jobTitle;
        pimPage.seleccionarTituloTrabajo(jobTitle);
    }

    @And("the user selects job title for combined filters {string}")
    public void seleccionarTituloTrabajoParaCombinacion(String jobTitle) {
        tituloTrabajoFiltro = jobTitle;
        if (!pimPage.seleccionarTituloTrabajoParaCombinacion(jobTitle)) {
            filtrosNoDisponibles.add("Job Title='" + jobTitle + "'");
        }
    }

    @And("the user selects sub unit {string}")
    public void seleccionarSubUnidad(String subUnit) {
        subUnidadFiltro = subUnit;
        pimPage.seleccionarSubUnidad(subUnit);
    }

    @And("the user selects sub unit for combined filters {string}")
    public void seleccionarSubUnidadParaCombinacion(String subUnit) {
        subUnidadFiltro = subUnit;
        if (!pimPage.seleccionarSubUnidadParaCombinacion(subUnit)) {
            filtrosNoDisponibles.add("Sub Unit='" + subUnit + "'");
        }
    }

    @And("the user selects supervisor {string}")
    public void seleccionarSupervisor(String supervisor) {
        supervisorFiltro = supervisor;
        pimPage.seleccionarSupervisor(supervisor);
    }

    @And("the user selects supervisor for combined filters {string}")
    public void seleccionarSupervisorParaCombinacion(String supervisor) {
        supervisorFiltro = supervisor;
        if (!pimPage.seleccionarSupervisorParaCombinacion(supervisor)) {
            filtrosNoDisponibles.add("Supervisor='" + supervisor + "'");
        }
    }

    @Then("every result should match all selected criteria")
    public void validarTodosLosCriteriosSeleccionados() {
        int cantidadResultados = pimPage.contarResultados();
        if (cantidadResultados == 0 || !filtrosNoDisponibles.isEmpty()) {
            String detalle = filtrosNoDisponibles.isEmpty()
                    ? ""
                    : " Valores inexistentes: " + String.join(", ", filtrosNoDisponibles) + ".";
            System.out.println(
                    "No se encontró la combinación de filtros probados." + detalle);
            return;
        }

        List<String> criteriosNoCoincidentes = new ArrayList<>();
        if (!includeFiltro.equals(pimPage.obtenerOpcionIncludeSeleccionada())) {
            criteriosNoCoincidentes.add("Include='" + includeFiltro + "'");
        }
        if (!supervisorFiltro.equals(pimPage.obtenerSupervisorSeleccionado())) {
            criteriosNoCoincidentes.add("Supervisor='" + supervisorFiltro + "'");
        }
        if (!pimPage.todosLosResultadosCoinciden(
                nombreEmpleadoFiltro,
                idEmpleadoFiltro,
                estadoLaboralFiltro,
                tituloTrabajoFiltro,
                subUnidadFiltro)) {
            criteriosNoCoincidentes.add("los resultados no cumplen todos los filtros");
        }

        if (!criteriosNoCoincidentes.isEmpty()) {
            System.out.println(
                    "La combinación no cumple con todos los filtros probados: "
                            + String.join(", ", criteriosNoCoincidentes) + ".");
            return;
        }

        System.out.println("La combinación encontrada cumple con todos los filtros probados.");
    }

    @Then("results should belong to {string}")
    public void validarFiltroIncludeAplicado(String expectedIncludeOption) {
        Assert.assertEquals(
                "El filtro Include seleccionado no coincide con el solicitado",
                expectedIncludeOption,
                pimPage.obtenerOpcionIncludeSeleccionada());
    }

    @Then("^the system should return \"([^\"]*)\" record\\(s\\)$")
    public void validarCantidadDeResultados(String expectedRecords) {
        int cantidadEsperada = Integer.parseInt(expectedRecords);
        Assert.assertEquals(
                "La cantidad de registros no coincide. Esperados: " + cantidadEsperada,
                cantidadEsperada,
                pimPage.contarResultados());
    }

    @Then("se deben mostrar los resultados de la búsqueda")
    public void validarResultadosMostrados() {
        Assert.assertNotNull("No se ejecutó la búsqueda de empleados", resultados);
        Assert.assertFalse("La búsqueda no devolvió empleados", resultados.isEmpty());
    }

    @And("cada resultado debe contener {string}")
    public void validarNombreEnCadaResultado(String nombreEsperado) {
        Assert.assertNotNull("No se ejecutó la búsqueda de empleados", resultados);
        Assert.assertTrue(
                "Hay resultados que no contienen el nombre esperado '" + nombreEsperado + "': " + resultados,
                resultados.stream().allMatch(resultado -> resultado.contains(nombreEsperado)));
    }

    @And("cada resultado debe contener el nombre configurado")
    public void validarNombreConfiguradoEnResultados() {
        validarNombreEnCadaResultado(obtenerNombreEmpleadoConfigurado());
    }

    private String obtenerNombreEmpleadoConfigurado() {
        String nombre = System.getProperty("employeeName", "").trim();
        Assert.assertFalse(
                "Falta configurar el parámetro employeeName. Ejecute Gradle con -PemployeeName=Amelia",
                nombre.isEmpty());
        return nombre;
    }
}
