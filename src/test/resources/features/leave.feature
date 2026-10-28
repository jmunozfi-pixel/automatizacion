# language: es

@LEAVE
Característica: Búsqueda y filtros de solicitudes de vacaciones

  Antecedentes:
    Dado que se abre el navegador y se navega a "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
    Y que el usuario inicia sesión en OrangeHRM con el usuario "Admin" y la contraseña "admin123"
    Y que el usuario ingresa al módulo LEAVE

  @TLEAVE-001
  Esquema del escenario: Validar búsqueda de solicitudes con filtros combinados

    Dado que existen solicitudes de vacaciones
    Y el usuario se encuentra en "Leave List"

    Cuando ingresa la fecha inicial "<fechaInicio>"
    Y ingresa la fecha final "<fechaFin>"
    Y selecciona el estado "<estado>"
    Y ingresa el nombre del empleado "<empleado>"
    Y selecciona al empleado desde el autocompletado
    Y selecciona la unidad "<unidad>"
    Y configura empleados anteriores en "<anteriores>"
    Y presiona el botón "Search"

    Entonces se debe completar la búsqueda sin errores
    Y se debe visualizar la tabla de resultados
    Y las solicitudes deben corresponder al empleado "<empleado>"
    Y las solicitudes deben cumplir el estado "<estado>"
    Y las solicitudes deben estar dentro del período consultado
    Y las solicitudes deben corresponder a la unidad "<unidad>"
    Y los registros deben respetar el filtro de empleados anteriores
    Y no deben existir registros duplicados
    Y todas las páginas deben respetar los filtros aplicados

    Cuando el usuario presiona el botón "Reset"
    Entonces los filtros deben recuperar sus valores predeterminados
    Y no deben permanecer los criterios ingresados anteriormente

    Ejemplos:
      | fechaInicio | fechaFin   | estado           | empleado       | unidad         | anteriores |
      | 2026-01-01  | 2026-12-31 | Pending Approval | QA Employee 01 | Engineering    | No         |
      | 2026-01-01  | 2026-12-31 | Scheduled        | QA Employee 02 | Engineering    | No         |
      | 2026-01-01  | 2026-12-31 | Taken            | QA Employee 03 | Administration | Yes        |
      | 2026-01-01  | 2026-12-31 | Rejected         | QA Employee 04 | Sales          | No         |
