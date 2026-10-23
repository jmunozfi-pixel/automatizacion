# language: es
@PIM
Característica: Búsqueda y filtros de la lista de empleados

  Antecedentes:
    Dado que se abre el navegador y se navega a "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
    Y que el usuario inicia sesión en OrangeHRM con el usuario "Admin" y la contraseña "admin123"
    Y que el usuario ingresa al módulo PIM

  @TC-001
    Esquema del escenario: Buscar empleado por nombre
      Cuando el usuario ingresa "<employeeName>" en el campo Nombre del empleado de PIM
      Y hace clic en Search
      Entonces se deben mostrar los resultados de la búsqueda
      Y cada resultado debe contener "<expectedName>"

      Ejemplos:
        | employeeName | expectedName |
        | Amelia       | Amelia       |


  @TC-002
  Esquema del escenario: Buscar empleado por id employee
    Cuando the user enters employee id "<employeeId>"
    Y clicks Search
    Entonces the system should return "<expectedRecords>" record(s)

    Ejemplos:
      | employeeId | expectedRecords |
      | 0001       | 1               |
      | 999999     | 0               |

  @TC-003
  Esquema del escenario: Buscar empleado por status employment
    Cuando the user selects employment status "<status>"
    Y clicks Search
    Entonces every result should have employment status "<status>"

    Ejemplos:
      | status               |
      | Freelance            |
      | Full-Time Contract   |
      | Full-Time Permanent  |
      | Full-Time Probation  |
      | Part-Time Contract   |

  @TC-004
  Esquema del escenario: Buscar empleado por filtro actual, ambos y pasado
    Cuando the user selects include option "<includeOption>"
    Y clicks Search
    Entonces results should belong to "<includeOption>"

    Ejemplos:
      | includeOption              |
      | Current Employees Only     |
      | Current and Past Employees |
      | Past Employees Only        |

    @TC-005
    Esquema del escenario: Search employees using all available PIM filters
      Cuando the user enters employee name for combined filters "<employeeName>"
      Y the user enters employee id "<employeeId>"
      Y the user selects employment status for combined filters "<status>"
      Y the user selects include option for combined filters "<includeOption>"
      Y the user selects job title for combined filters "<jobTitle>"
      Y the user selects sub unit for combined filters "<subUnit>"
      Y the user selects supervisor for combined filters "<supervisor>"
      Y clicks Search
      Entonces every result should match all selected criteria

      Ejemplos:
        | employeeName | employeeId | status               | includeOption              | jobTitle                 | subUnit        | supervisor     |
        | Amelia       | 0001       | Full-Time Permanent  | Current Employees Only     | Account Assistant        | Administration | John Smith     |
        | James        | 0002       | Full-Time Contract   | Current and Past Employees | Automation Engineer L000 | Engineering    | Peter Anderson |
        | Linda        | 0003       | Part-Time Contract   | Past Employees Only        | Automation Tester        | Development    | Linda Brown    |
