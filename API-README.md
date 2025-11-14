# Sistema de Gestión de Historias Clínicas - Backend API REST

Backend desarrollado con **Spring Boot 3.5.7** y **Spring Data JPA** para la gestión completa de historias clínicas.

## 🏗️ Arquitectura

```
flutter_backend.Ramirez/
├── entity/              # Entidades JPA
│   ├── Paciente.java
│   ├── Medico.java
│   └── HistoriaClinica.java
├── repository/          # Repositorios Spring Data JPA
│   ├── PacienteRepository.java
│   ├── MedicoRepository.java
│   └── HistoriaClinicaRepository.java
├── dto/                 # Data Transfer Objects
│   ├── HistoriaClinicaDTO.java
│   └── HistoriaClinicaResponseDTO.java
├── service/             # Lógica de negocio
│   └── HistoriaClinicaService.java
└── controller/          # Controladores REST
    ├── PacienteController.java
    ├── MedicoController.java
    └── HistoriaClinicaController.java
```

## 📊 Modelo de Datos

### Paciente
- **PAC_DNI** (PK): String, 8 dígitos
- **PAC_Nombre**: String, 2-100 caracteres
- **PAC_Apellido_Paterno**: String, 2-100 caracteres
- **PAC_Apellido_Materno**: String, opcional, max 100 caracteres
- **PAC_Direccion**: String, opcional, max 255 caracteres
- **PAC_Telefono**: String, 9-15 dígitos

### Médico
- **MED_Cmp** (PK): String, 4-10 dígitos
- **MED_Nombre**: String, 2-100 caracteres
- **MED_Apellidos**: String, 2-200 caracteres
- **ESPE_Nombre**: String, 2-100 caracteres

### Historia Clínica
- **HIST_Id** (PK): Long, auto-generado
- **PAC_DNI** (FK): Referencia a Paciente
- **MED_Cmp** (FK): Referencia a Médico
- **HIST_Fecha_Atencion**: LocalDate (formato: yyyy-MM-dd)
- **HIST_Diagnostico**: Text, 10-5000 caracteres
- **HIST_Analisis**: Text, opcional, max 5000 caracteres
- **HIST_Tratamiento**: Text, 10-5000 caracteres

## 🚀 Configuración Inicial

### Requisitos Previos
- Java 21+
- MySQL 8.0+
- Maven 3.6+

### Configuración de Base de Datos

1. Crear la base de datos en MySQL (opcional, se crea automáticamente):
```sql
CREATE DATABASE ramirez_db;
```

2. Actualizar las credenciales en `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ramirez_db
spring.datasource.username=root
spring.datasource.password=tu_password
```

### Ejecutar la Aplicación

```bash
# Con Maven Wrapper (recomendado)
./mvnw spring-boot:run

# Con Maven instalado
mvn spring-boot:run
```

La aplicación se ejecutará en: `http://localhost:8080`

## 📚 Endpoints de la API

### Pacientes

#### Listar todos los pacientes
```http
GET /api/pacientes
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Pacientes obtenidos exitosamente",
  "data": [...],
  "count": 2
}
```

#### Obtener paciente por DNI
```http
GET /api/pacientes/{dni}
```

#### Registrar nuevo paciente
```http
POST /api/pacientes
Content-Type: application/json

{
  "pacDni": "12345678",
  "pacNombre": "Juan",
  "pacApellidoPaterno": "Pérez",
  "pacApellidoMaterno": "García",
  "pacDireccion": "Av. Principal 123",
  "pacTelefono": "987654321"
}
```

#### Actualizar paciente
```http
PUT /api/pacientes/{dni}
Content-Type: application/json

{
  "pacNombre": "Juan Carlos",
  "pacApellidoPaterno": "Pérez",
  "pacApellidoMaterno": "García",
  "pacDireccion": "Av. Principal 456",
  "pacTelefono": "987654321"
}
```

#### Eliminar paciente
```http
DELETE /api/pacientes/{dni}
```

---

### Médicos

#### Listar todos los médicos
```http
GET /api/medicos
```

#### Obtener médico por CMP
```http
GET /api/medicos/{cmp}
```

#### Buscar médicos por especialidad
```http
GET /api/medicos/especialidad/{especialidad}
```

Ejemplo: `GET /api/medicos/especialidad/Cardiología`

#### Registrar nuevo médico
```http
POST /api/medicos
Content-Type: application/json

{
  "medCmp": "12345",
  "medNombre": "María",
  "medApellidos": "González Rodríguez",
  "espeNombre": "Cardiología"
}
```

#### Actualizar médico
```http
PUT /api/medicos/{cmp}
Content-Type: application/json

{
  "medNombre": "María Elena",
  "medApellidos": "González Rodríguez",
  "espeNombre": "Cardiología"
}
```

#### Eliminar médico
```http
DELETE /api/medicos/{cmp}
```

---

### Historias Clínicas (CRUD Principal)

#### Listar todas las historias clínicas
```http
GET /api/historias-clinicas
```

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Historias clínicas obtenidas exitosamente",
  "data": [
    {
      "histId": 1,
      "pacDni": "12345678",
      "pacNombreCompleto": "Juan Pérez García",
      "pacTelefono": "987654321",
      "medCmp": "12345",
      "medNombreCompleto": "María González Rodríguez",
      "medEspecialidad": "Cardiología",
      "histFechaAtencion": "2025-11-13",
      "histDiagnostico": "Hipertensión arterial grado 1...",
      "histAnalisis": "Presión arterial elevada...",
      "histTratamiento": "Enalapril 10mg cada 12 horas..."
    }
  ],
  "count": 1
}
```

#### Obtener historia clínica por ID
```http
GET /api/historias-clinicas/{id}
```

#### Registrar nueva historia clínica
```http
POST /api/historias-clinicas
Content-Type: application/json

{
  "pacDni": "12345678",
  "medCmp": "12345",
  "histFechaAtencion": "2025-11-13",
  "histDiagnostico": "Hipertensión arterial grado 1. Paciente presenta presión arterial elevada de manera constante durante los últimos 3 meses.",
  "histAnalisis": "Presión arterial: 145/95 mmHg. Frecuencia cardíaca: 82 lpm. Sin alteraciones en electrocardiograma.",
  "histTratamiento": "Enalapril 10mg cada 12 horas por vía oral. Dieta hiposódica. Control en 30 días. Realizar actividad física moderada 30 minutos diarios."
}
```

**Respuesta exitosa (201):**
```json
{
  "success": true,
  "message": "Historia clínica registrada exitosamente",
  "data": {
    "histId": 1,
    "pacDni": "12345678",
    "pacNombreCompleto": "Juan Pérez García",
    ...
  }
}
```

**Respuesta con errores de validación (400):**
```json
{
  "success": false,
  "message": "Error de validación",
  "errors": [
    "histDiagnostico: El diagnóstico debe tener entre 10 y 5000 caracteres",
    "pacDni: El DNI del paciente es obligatorio"
  ]
}
```

#### Actualizar historia clínica
```http
PUT /api/historias-clinicas/{id}
Content-Type: application/json

{
  "pacDni": "12345678",
  "medCmp": "12345",
  "histFechaAtencion": "2025-11-13",
  "histDiagnostico": "Hipertensión arterial grado 2 (actualizado)...",
  "histAnalisis": "Presión arterial: 155/100 mmHg...",
  "histTratamiento": "Ajuste de medicación: Enalapril 20mg..."
}
```

#### Eliminar historia clínica
```http
DELETE /api/historias-clinicas/{id}
```

#### Buscar historias por paciente
```http
GET /api/historias-clinicas/paciente/{pacDni}
```

Ejemplo: `GET /api/historias-clinicas/paciente/12345678`

#### Buscar historias por médico
```http
GET /api/historias-clinicas/medico/{medCmp}
```

Ejemplo: `GET /api/historias-clinicas/medico/12345`

#### Buscar historias por rango de fechas
```http
GET /api/historias-clinicas/fechas?inicio=2025-01-01&fin=2025-12-31
```

---

## ✅ Validaciones Implementadas

### Validaciones en Paciente:
- DNI: Obligatorio, 8 dígitos numéricos
- Nombre: Obligatorio, 2-100 caracteres
- Apellido Paterno: Obligatorio, 2-100 caracteres
- Teléfono: 9-15 dígitos numéricos

### Validaciones en Médico:
- CMP: Obligatorio, 4-10 dígitos numéricos
- Nombre: Obligatorio, 2-100 caracteres
- Apellidos: Obligatorio, 2-200 caracteres
- Especialidad: Obligatorio, 2-100 caracteres

### Validaciones en Historia Clínica:
- Paciente y Médico: Obligatorios y deben existir en la BD
- Fecha de Atención: Obligatoria, no puede ser futura
- Diagnóstico: Obligatorio, 10-5000 caracteres
- Tratamiento: Obligatorio, 10-5000 caracteres
- Análisis: Opcional, máximo 5000 caracteres

## 🔧 Características Técnicas

✅ **Spring Boot 3.5.7** con Java 21  
✅ **Spring Data JPA** para persistencia  
✅ **Validación de datos** con `@Valid` y Bean Validation  
✅ **Relaciones JPA**: `@ManyToOne` correctamente mapeadas  
✅ **DTOs** para separar capa de presentación de entidades  
✅ **Manejo de errores** con respuestas JSON consistentes  
✅ **CORS** habilitado para integración con frontend  
✅ **Transacciones** gestionadas con `@Transactional`  
✅ **Consultas personalizadas** con `@Query`  
✅ **Lombok** para reducir código boilerplate  

## 🧪 Ejemplos de Prueba con cURL

### 1. Registrar un paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "pacDni": "12345678",
    "pacNombre": "Juan",
    "pacApellidoPaterno": "Pérez",
    "pacApellidoMaterno": "García",
    "pacDireccion": "Av. Principal 123",
    "pacTelefono": "987654321"
  }'
```

### 2. Registrar un médico
```bash
curl -X POST http://localhost:8080/api/medicos \
  -H "Content-Type: application/json" \
  -d '{
    "medCmp": "12345",
    "medNombre": "María",
    "medApellidos": "González Rodríguez",
    "espeNombre": "Cardiología"
  }'
```

### 3. Registrar una historia clínica
```bash
curl -X POST http://localhost:8080/api/historias-clinicas \
  -H "Content-Type: application/json" \
  -d '{
    "pacDni": "12345678",
    "medCmp": "12345",
    "histFechaAtencion": "2025-11-13",
    "histDiagnostico": "Hipertensión arterial grado 1. Paciente presenta presión arterial elevada de manera constante durante los últimos 3 meses.",
    "histAnalisis": "Presión arterial: 145/95 mmHg. Frecuencia cardíaca: 82 lpm. Sin alteraciones en electrocardiograma.",
    "histTratamiento": "Enalapril 10mg cada 12 horas por vía oral. Dieta hiposódica. Control en 30 días."
  }'
```

### 4. Listar todas las historias clínicas
```bash
curl http://localhost:8080/api/historias-clinicas
```

## 📝 Notas Importantes

1. **Base de Datos**: La aplicación está configurada para crear automáticamente la base de datos y las tablas al iniciar (`spring.jpa.hibernate.ddl-auto=update`)

2. **Relaciones**: Las historias clínicas están relacionadas con pacientes y médicos mediante claves foráneas. No se puede crear una historia sin que existan previamente el paciente y el médico.

3. **JSON de Respuesta**: Todos los endpoints devuelven un formato consistente:
   ```json
   {
     "success": true/false,
     "message": "Descripción del resultado",
     "data": {...} o [...],
     "count": número (opcional)
   }
   ```

4. **Manejo de Errores**: Los errores de validación y excepciones se manejan adecuadamente con mensajes descriptivos.

## 📧 Contacto y Soporte

Para consultas o reportes de problemas, por favor crea un issue en el repositorio.

---

**Desarrollado con ❤️ usando Spring Boot 3.x y Spring Data JPA**
