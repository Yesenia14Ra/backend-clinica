# 🏗️ Arquitectura del Sistema

## Diagrama de Capas

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENTE (Flutter App)                   │
│                    HTTP REST Requests                        │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE CONTROLADORES                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │  Paciente    │  │    Medico    │  │   Historia   │      │
│  │ Controller   │  │  Controller  │  │   Clinica    │      │
│  │              │  │              │  │  Controller  │      │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘      │
│         │ @RestController  │                 │              │
│         │ @RequestMapping  │                 │              │
│         │ @Valid           │                 │              │
└─────────┼──────────────────┼─────────────────┼──────────────┘
          │                  │                 │
          ▼                  ▼                 ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE SERVICIOS                          │
│                    (Lógica de Negocio)                       │
│                                                               │
│              ┌─────────────────────────┐                     │
│              │  HistoriaClinicaService │                     │
│              │  @Service               │                     │
│              │  @Transactional         │                     │
│              │                         │                     │
│              │  - registrar()          │                     │
│              │  - actualizar()         │                     │
│              │  - listarTodas()        │                     │
│              │  - eliminar()           │                     │
│              │  - buscarPorPaciente()  │                     │
│              └────────┬────────────────┘                     │
└───────────────────────┼──────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────────┐
│                  CAPA DE REPOSITORIOS                        │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │  Paciente    │  │    Medico    │  │   Historia   │      │
│  │ Repository   │  │  Repository  │  │   Clinica    │      │
│  │              │  │              │  │  Repository  │      │
│  │ extends      │  │  extends     │  │  extends     │      │
│  │ JpaRepository│  │JpaRepository │  │JpaRepository │      │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘      │
└─────────┼──────────────────┼─────────────────┼──────────────┘
          │                  │                 │
          ▼                  ▼                 ▼
┌─────────────────────────────────────────────────────────────┐
│                   SPRING DATA JPA                            │
│                   (ORM - Hibernate)                          │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   BASE DE DATOS MySQL                        │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │   pacientes  │  │    medicos   │  │  historias_  │      │
│  │              │  │              │  │   clinicas   │      │
│  │  PAC_DNI (PK)│  │ MED_Cmp (PK) │  │ HIST_Id (PK) │      │
│  │  PAC_Nombre  │  │ MED_Nombre   │  │ PAC_DNI (FK) │      │
│  │  PAC_Apell.. │  │ MED_Apellid..│  │ MED_Cmp (FK) │      │
│  │  PAC_Direcc..│  │ ESPE_Nombre  │  │ HIST_Fecha.. │      │
│  │  PAC_Telef.. │  │              │  │ HIST_Diagn.. │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
```

## Diagrama Entidad-Relación

```
┌─────────────────────┐
│      PACIENTE       │
├─────────────────────┤
│ PAC_DNI (PK)        │◄────┐
│ PAC_Nombre          │     │
│ PAC_Apellido_Pat    │     │
│ PAC_Apellido_Mat    │     │
│ PAC_Direccion       │     │
│ PAC_Telefono        │     │
└─────────────────────┘     │
                             │
                             │ 1:N
                             │
                      ┌──────┴──────────────────┐
                      │  HISTORIA_CLINICA       │
                      ├─────────────────────────┤
                      │ HIST_Id (PK)            │
                      │ PAC_DNI (FK)            │
                      │ MED_Cmp (FK)            │
                      │ HIST_Fecha_Atencion     │
                      │ HIST_Diagnostico        │
                      │ HIST_Analisis           │
                      │ HIST_Tratamiento        │
                      └─────────┬───────────────┘
                                │
                                │ N:1
                                │
┌─────────────────────┐         │
│       MEDICO        │◄────────┘
├─────────────────────┤
│ MED_Cmp (PK)        │
│ MED_Nombre          │
│ MED_Apellidos       │
│ ESPE_Nombre         │
└─────────────────────┘
```

## Flujo de una Petición REST (Ejemplo: POST Historia Clínica)

```
1. Cliente Flutter
   │
   │ POST /api/historias-clinicas
   │ {
   │   "pacDni": "12345678",
   │   "medCmp": "12345",
   │   "histDiagnostico": "...",
   │   ...
   │ }
   ▼
2. HistoriaClinicaController
   │ @PostMapping
   │ registrar(@Valid @RequestBody HistoriaClinicaDTO dto)
   │
   ├─► Validación con @Valid
   │   - Verifica campos obligatorios
   │   - Valida longitudes
   │   - Valida formatos
   │
   ▼
3. HistoriaClinicaService
   │ registrar(HistoriaClinicaDTO dto)
   │
   ├─► Busca Paciente por DNI
   │   └─► PacienteRepository.findById()
   │
   ├─► Busca Médico por CMP
   │   └─► MedicoRepository.findById()
   │
   ├─► Crea entidad HistoriaClinica
   │   - Asigna Paciente (relación @ManyToOne)
   │   - Asigna Médico (relación @ManyToOne)
   │   - Establece campos
   │
   ├─► Guarda en base de datos
   │   └─► HistoriaClinicaRepository.save()
   │       └─► Hibernate ejecuta INSERT SQL
   │
   └─► Convierte a DTO de respuesta
       └─► HistoriaClinicaResponseDTO
   ▼
4. Respuesta al Cliente
   {
     "success": true,
     "message": "Historia clínica registrada exitosamente",
     "data": {
       "histId": 1,
       "pacNombreCompleto": "Juan Pérez García",
       "medNombreCompleto": "María González Rodríguez",
       ...
     }
   }
```

## Componentes y Tecnologías

### 📦 Dependencias Principales

| Dependencia | Versión | Uso |
|-------------|---------|-----|
| Spring Boot | 3.5.7 | Framework principal |
| Spring Data JPA | 3.5.7 | Persistencia de datos |
| Spring Validation | 3.5.7 | Validación de datos |
| MySQL Connector | Runtime | Driver de base de datos |
| Lombok | Latest | Reducción de código boilerplate |

### 🔧 Anotaciones Clave Utilizadas

**Entidades JPA:**
- `@Entity` - Marca una clase como entidad JPA
- `@Table` - Define el nombre de la tabla
- `@Id` - Define la clave primaria
- `@GeneratedValue` - Auto-generación de IDs
- `@Column` - Mapeo de columnas
- `@ManyToOne` - Relación muchos a uno
- `@OneToMany` - Relación uno a muchos
- `@JoinColumn` - Define columna de clave foránea

**Validación:**
- `@NotNull` - Campo no nulo
- `@NotBlank` - String no vacío
- `@Size` - Longitud de string
- `@Pattern` - Validación con regex
- `@PastOrPresent` - Fecha pasada o presente
- `@Valid` - Activa validación en cascade

**Spring MVC:**
- `@RestController` - Marca controlador REST
- `@RequestMapping` - Mapeo de rutas
- `@GetMapping` - HTTP GET
- `@PostMapping` - HTTP POST
- `@PutMapping` - HTTP PUT
- `@DeleteMapping` - HTTP DELETE
- `@PathVariable` - Variable de ruta
- `@RequestBody` - Cuerpo de petición
- `@RequestParam` - Parámetro de query

**Spring Data:**
- `@Repository` - Marca repositorio
- `@Query` - Consultas JPQL personalizadas
- `@Param` - Parámetro de query

**Servicio:**
- `@Service` - Marca servicio
- `@Transactional` - Gestión de transacciones

**Lombok:**
- `@Data` - Genera getters, setters, toString, equals, hashCode
- `@NoArgsConstructor` - Constructor sin argumentos
- `@AllArgsConstructor` - Constructor con todos los argumentos
- `@RequiredArgsConstructor` - Constructor con campos final/required

## 🔐 Seguridad y Mejores Prácticas Implementadas

✅ Validación de datos en todos los endpoints  
✅ DTOs para separar capas  
✅ Manejo centralizado de errores  
✅ Transacciones en operaciones de base de datos  
✅ Relaciones JPA correctamente mapeadas  
✅ CORS habilitado para integración frontend  
✅ Respuestas JSON consistentes  
✅ Logging de SQL para debugging  

## 📊 Patrones de Diseño Utilizados

1. **Repository Pattern** - Abstracción de acceso a datos
2. **DTO Pattern** - Transferencia de datos entre capas
3. **Service Layer Pattern** - Lógica de negocio centralizada
4. **MVC Pattern** - Separación de responsabilidades
5. **Dependency Injection** - Inyección de dependencias con Spring

---

**Esta arquitectura garantiza:**
- ✅ Escalabilidad
- ✅ Mantenibilidad
- ✅ Testabilidad
- ✅ Separación de responsabilidades
- ✅ Reutilización de código
