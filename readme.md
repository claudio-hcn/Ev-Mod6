# 🚀 Proyecto Testing APIs - Módulo 6

## 📖 Descripción
Proyecto de automatización de pruebas para APIs REST utilizando Java, JUnit 5 y RestAssured. Incluye tests de autenticación, validación de endpoints y casos negativos sobre dos APIs diferentes.

## 🏗️ Estructura del Proyecto
```
└── 📁Ev-Mod6
    └── 📁src
        └── 📁test
            └── 📁java
                └── 📁com
                    └── 📁testing
                        ├── GoRestAuth.java      # Tests de autenticación GoRest API
                        ├── SimpleApiTest.java   # Tests JSONPlaceholder API
    └── 📁target                                # Archivos compilados
    └── pom.xml                                 # Configuración Maven
```

## 🎯 APIs Probadas

### 1. **JSONPlaceholder API** 🌐
- **URL Base:** `https://jsonplaceholder.typicode.com`
- **Tipo:** API pública gratuita
- **Autenticación:** No requiere
- **Tests:** 8 casos (6 positivos + 2 negativos)

### 2. **GoRest API** 🔐
- **URL Base:** `https://gorest.co.in`
- **Tipo:** API con autenticación Bearer Token
- **Autenticación:** Requerida para operaciones de escritura
- **Tests:** 5 casos (autenticación válida/inválida)

## 🧪 Casos de Prueba

### **SimpleApiTest.java** (JSONPlaceholder)
| Test | Método | Endpoint | Validación |
|------|--------|----------|------------|
| GET Posts | `GET` | `/posts` | Lista no vacía, estructura correcta |
| POST Post | `POST` | `/posts` | Creación exitosa, datos correctos |
| GET Users | `GET` | `/users` | Lista de usuarios válida |
| GET Post específico | `GET` | `/posts/1` | Datos del post ID 1 |
| PUT Post | `PUT` | `/posts/1` | Actualización exitosa |
| GET 404 | `GET` | `/posts/999` | Error 404 esperado |
| Test Negativo 404 | `GET` | `/posts/999999` | Validación error 404 |
| Test Respuesta Vacía | `GET` | `/posts/999999` | Respuesta vacía |

### **GoRestAuth.java** (GoRest)
| Test | Método | Endpoint | Validación |
|------|--------|----------|------------|
| Auth Válida - Crear | `POST` | `/public/v2/users` | Usuario creado con token válido |
| Auth Válida - Actualizar | `PATCH` | `/public/v2/users/{id}` | Usuario actualizado con token válido |
| Auth Inválida - Crear | `POST` | `/public/v2/users` | Error 401 con token inválido |
| Sin Auth - Eliminar | `DELETE` | `/public/v2/users/123` | Error sin token |
| Acceso Público | `GET` | `/public/v2/users` | Lectura sin autenticación |

## ⚙️ Configuración

### **Prerrequisitos**
- Java 11 o superior
- Maven 3.6+
- IntelliJ IDEA / Eclipse
- Conexión a Internet

### **Dependencias Principales** (pom.xml)
```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
    
    <!-- RestAssured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.3.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Hamcrest Matchers -->
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### **Token GoRest API** 🔑
1. Registrarse en: https://gorest.co.in/consumer/login
2. Obtener token personal
3. Reemplazar `VALID_TOKEN` en `GoRestAuth.java`:
```java
private static final String VALID_TOKEN = "TU_TOKEN_AQUI";
```

## 🚀 Ejecución

### **Ejecutar todos los tests**
```bash
mvn test
```

### **Ejecutar clase específica**
```bash
mvn test -Dtest=SimpleAPITest
mvn test -Dtest=GoRestSecurityTest
```

### **Ejecutar con logs detallados**
```bash
mvn test -Dtest.verbose=true
```

## 📋 Resultados Esperados

### **Consola de Salida**
```
🚀 Iniciando tests de JSONPlaceholder API
✅ GET Posts - EXITOSO
✅ POST Post - EXITOSO
✅ GET Users - EXITOSO
✅ GET Post específico - EXITOSO
✅ PUT Post - EXITOSO
✅ Error 404 - EXITOSO
✅ Test negativo exitoso - Error 404 validado correctamente
✅ Test negativo exitoso - Respuesta vacía validada correctamente
🎉 Tests completados - 8 casos ejecutados

🔒 Iniciando tests de Seguridad y Autenticación - GoRest API
✅ Usuario creado exitosamente con ID: 12345
✅ Token válido autenticado correctamente
✅ Usuario actualizado exitosamente
✅ Error 401 recibido correctamente
✅ Error 404 recibido correctamente
🎉 Tests de seguridad completados - 5 casos ejecutados
```

## 📊 Características Técnicas

### **Frameworks y Librerías**
- **JUnit 5** - Framework de testing
- **RestAssured** - Testing de APIs REST
- **Hamcrest** - Matchers para validaciones
- **Maven** - Gestión de dependencias

### **Patrones Implementados**
- **Given-When-Then** - Estructura BDD
- **Test Ordering** - Ejecución secuencial con `@Order`
- **Test Display Names** - Nombres descriptivos con emojis
- **Setup/Teardown** - Configuración con `@BeforeAll/@AfterAll`

### **Validaciones Incluidas**
- ✅ Status codes HTTP
- ✅ Estructura de respuesta JSON
- ✅ Valores específicos de campos
- ✅ Autenticación válida/inválida
- ✅ Casos negativos y de error

## 🎯 Objetivos del Proyecto

1. **Validar funcionalidad** de APIs REST
2. **Probar autenticación** Bearer Token
3. **Verificar manejo de errores** (404, 401)
4. **Implementar tests negativos**
5. **Documentar resultados** con logs claros

## 📝 Notas Importantes

- **JSONPlaceholder** es una API de prueba que simula respuestas pero no persiste datos
- **GoRest** requiere token válido y tiene límites de uso
- Los tests están diseñados para ser **determinísticos** y **repetibles**
- Se incluye generación automática de emails únicos para evitar conflictos

## 🤝 Contribuciones

Para contribuir al proyecto:
1. Fork del repositorio
2. Crear branch para nueva feature
3. Implementar tests siguiendo los patrones existentes
4. Enviar Pull Request con descripción detallada

## 📄 Licencia

Este proyecto es de uso educativo para el Módulo 6 de Testing de APIs.

---
**Autor:** Tu Nombre  
**Versión:** 1.0  
**Fecha:** Agosto 2025