# 🚀 Guía de Inicio Rápido

## Sistema de Gestión de Historias Clínicas - Backend

### ⚡ Pasos para Ejecutar el Proyecto

#### 1. Configurar MySQL

**Opción A: Crear la base de datos manualmente**
```sql
CREATE DATABASE ramirez_db;
```

**Opción B: Dejar que Spring Boot la cree automáticamente**  
El proyecto está configurado para crear la base de datos automáticamente si no existe.

#### 2. Configurar Credenciales de Base de Datos

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=tu_password_mysql
```

#### 3. Ejecutar la Aplicación

**Con Maven Wrapper (recomendado):**
```bash
./mvnw spring-boot:run
```

**Con Maven instalado:**
```bash
mvn spring-boot:run
```

**Desde tu IDE:**
- Ejecuta la clase `RamirezApplication.java`

#### 4. Verificar que la Aplicación Esté Corriendo

La aplicación estará disponible en: **http://localhost:8080**

Prueba este endpoint en tu navegador o con curl:
```bash
curl http://localhost:8080/api/pacientes
```

Deberías ver una respuesta JSON similar a:
```json
{
  "success": true,
  "message": "Pacientes obtenidos exitosamente",
  "data": [],
  "count": 0
}
```

---

## 📊 Cargar Datos de Prueba

Para facilitar las pruebas, puedes cargar datos de ejemplo:

```bash
mysql -u root -p ramirez_db < datos-prueba.sql
```

O desde MySQL Workbench/phpMyAdmin, ejecuta el archivo `datos-prueba.sql`

Esto insertará:
- 5 pacientes de prueba
- 6 médicos de prueba  
- 7 historias clínicas de prueba

---

## 🧪 Probar los Endpoints

### Opción 1: Usar Postman

1. Abre Postman
2. Importa el archivo `postman-collection.json`
3. La colección incluye todos los endpoints ya configurados

### Opción 2: Usar cURL

**Listar historias clínicas:**
```bash
curl http://localhost:8080/api/historias-clinicas
```

**Registrar un paciente:**
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "pacDni": "99999999",
    "pacNombre": "Nuevo",
    "pacApellidoPaterno": "Paciente",
    "pacApellidoMaterno": "Test",
    "pacDireccion": "Av. Test 123",
    "pacTelefono": "999888777"
  }'
```

**Registrar un médico:**
```bash
curl -X POST http://localhost:8080/api/medicos \
  -H "Content-Type: application/json" \
  -d '{
    "medCmp": "99999",
    "medNombre": "Nuevo",
    "medApellidos": "Médico Test",
    "espeNombre": "Medicina General"
  }'
```

**Registrar una historia clínica:**
```bash
curl -X POST http://localhost:8080/api/historias-clinicas \
  -H "Content-Type: application/json" \
  -d '{
    "pacDni": "12345678",
    "medCmp": "12345",
    "histFechaAtencion": "2025-11-13",
    "histDiagnostico": "Diagnóstico de prueba con más de diez caracteres necesarios",
    "histAnalisis": "Análisis detallado del caso",
    "histTratamiento": "Tratamiento recomendado con más de diez caracteres"
  }'
```

### Opción 3: Usar el Navegador

Para endpoints GET, simplemente abre tu navegador:

- Todos los pacientes: http://localhost:8080/api/pacientes
- Todos los médicos: http://localhost:8080/api/medicos  
- Todas las historias: http://localhost:8080/api/historias-clinicas
- Historia por ID: http://localhost:8080/api/historias-clinicas/1
- Historias de un paciente: http://localhost:8080/api/historias-clinicas/paciente/12345678

---

## 📋 Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/historias-clinicas` | Listar todas las historias |
| GET | `/api/historias-clinicas/{id}` | Obtener historia por ID |
| POST | `/api/historias-clinicas` | Registrar nueva historia |
| PUT | `/api/historias-clinicas/{id}` | Actualizar historia |
| DELETE | `/api/historias-clinicas/{id}` | Eliminar historia |
| GET | `/api/historias-clinicas/paciente/{dni}` | Historias de un paciente |
| GET | `/api/historias-clinicas/medico/{cmp}` | Historias de un médico |
| GET | `/api/historias-clinicas/fechas?inicio=...&fin=...` | Historias por rango de fechas |

Para más detalles, consulta el archivo **API-README.md**

---

## ✅ Checklist de Verificación

- [ ] MySQL está instalado y ejecutándose
- [ ] Base de datos `ramirez_db` creada o configurada para creación automática
- [ ] Credenciales de MySQL actualizadas en `application.properties`
- [ ] Aplicación ejecutándose sin errores en puerto 8080
- [ ] Endpoint de prueba responde correctamente
- [ ] (Opcional) Datos de prueba cargados desde `datos-prueba.sql`
- [ ] (Opcional) Colección de Postman importada

---

## 🛠️ Solución de Problemas Comunes

### Error: "Access denied for user 'root'@'localhost'"
**Solución:** Verifica que el usuario y contraseña en `application.properties` sean correctos.

### Error: "Table doesn't exist"
**Solución:** Asegúrate de que `spring.jpa.hibernate.ddl-auto=update` esté configurado. Spring Boot creará las tablas automáticamente.

### Error: Puerto 8080 en uso
**Solución:** Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

### Error: Cannot find MySQL Driver
**Solución:** Ejecuta:
```bash
./mvnw clean install
```

---

## 📚 Documentación Completa

- **API-README.md**: Documentación detallada de todos los endpoints
- **datos-prueba.sql**: Scripts SQL con datos de ejemplo
- **postman-collection.json**: Colección de Postman para pruebas

---

## 🎯 Próximos Pasos

1. ✅ Probar todos los endpoints CRUD
2. ✅ Verificar validaciones de datos
3. ✅ Probar relaciones entre entidades
4. 🔄 Integrar con tu aplicación Flutter
5. 🔄 Implementar autenticación/seguridad (opcional)
6. 🔄 Agregar pruebas unitarias (opcional)

---

**¡El backend está listo para usar! 🎉**

Para consultas técnicas, revisa el código fuente o la documentación en API-README.md
