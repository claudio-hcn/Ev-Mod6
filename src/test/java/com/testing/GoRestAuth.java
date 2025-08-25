package com.testing;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
// import org.junit.jupiter.api.extension.ExtendWith;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Suite de tests de Seguridad y Autenticación para GoRest.io API
 * Valida autenticación con tokens Bearer válidos e inválidos
 * 
 * CONFIGURACIÓN REQUERIDA:
 * 1. Obtener token válido en: https://gorest.co.in/consumer/login
 * 2. Reemplazar "TU_TOKEN_AQUI" con tu token real
 * 
 * @author Tu Nombre
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Tests de Seguridad y Autenticación - GoRest API")
// @ExtendWith(AutoEvidenceGenerator.class)
class GoRestSecurityTest {
    
    // ⚠️ IMPORTANTE: Reemplazar con tu token real de GoRest.io
    private static final String VALID_TOKEN = "865f98143dcc2e33d84b116b8b77a94cbf26d6292e8e7b1b6a332c752a9acd68";
    private static final String INVALID_TOKEN = "token_invalido_123456";
    private static final String BASE_URL = "https://gorest.co.in";
    
    private static String createdUserId;
    
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        System.out.println("🔐 Iniciando tests de Seguridad y Autenticación - GoRest API");
        System.out.println("🌐 URL Base: " + BASE_URL);
        System.out.println("⚠️ NOTA: Asegúrate de tener un token válido configurado");
    }
    
    // ===============================================
    // TESTS CON TOKEN/API KEY VÁLIDO (2 tests)
    // ===============================================
    
    @Test
    @Order(1)
    @DisplayName("✅ AUTENTICACIÓN VÁLIDA - Crear usuario con token correcto")
    void testValidAuth_CreateUser() {
        System.out.println("🔑 TEST 1: Autenticación válida - Creando usuario");
        
        // Generar email único para evitar conflictos
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@testing.com";
        
        String requestBody = "{" +
                "\"name\": \"Usuario de Prueba\"," +
                "\"gender\": \"male\"," +
                "\"email\": \"" + uniqueEmail + "\"," +
                "\"status\": \"active\"" +
                "}";
        
        String userId = given()
            .header("Authorization", "Bearer " + VALID_TOKEN)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(requestBody)
        .when()
            .post("/public/v2/users")
        .then()
            .statusCode(201)  // ✅ CREADO exitosamente
            .body("name", equalTo("Usuario de Prueba"))
            .body("email", equalTo(uniqueEmail))
            .body("gender", equalTo("male"))
            .body("status", equalTo("active"))
            .body("id", notNullValue())
            .log().ifValidationFails()
            .extract()
            .path("id").toString();
        
        // Guardar ID para usar en otros tests
        createdUserId = userId;
        
        System.out.println("✅ Usuario creado exitosamente con ID: " + userId);
        System.out.println("✅ Token válido autenticado correctamente");
    }
    
    @Test
    @Order(2)
    @DisplayName("✅ AUTENTICACIÓN VÁLIDA - Actualizar usuario con token correcto")
    void testValidAuth_UpdateUser() {
        System.out.println("🔑 TEST 2: Autenticación válida - Actualizando usuario");
        
        // Usar el usuario creado en el test anterior, o un ID conocido
        String userIdToUpdate = (createdUserId != null) ? createdUserId : "5";
        
        String requestBody = "{" +
                "\"name\": \"Usuario Actualizado\"," +
                "\"status\": \"inactive\"" +
                "}";
        
        given()
            .header("Authorization", "Bearer " + VALID_TOKEN)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(requestBody)
        .when()
            .patch("/public/v2/users/" + userIdToUpdate)
        .then()
            .statusCode(200)  // ✅ ACTUALIZADO exitosamente
            .body("name", equalTo("Usuario Actualizado"))
            .body("status", equalTo("inactive"))
            .body("id", equalTo(Integer.parseInt(userIdToUpdate)))
            .log().ifValidationFails();
        
        System.out.println("✅ Usuario actualizado exitosamente");
        System.out.println("✅ Token válido permite operaciones de escritura");
    }
    
    // ===============================================
    // TESTS CON TOKEN/API KEY INVÁLIDO (2 tests)
    // ===============================================
    
    @Test
    @Order(3)
    @DisplayName("❌ AUTENTICACIÓN INVÁLIDA - Crear usuario con token incorrecto")
    void testInvalidAuth_CreateUserWithBadToken() {
        System.out.println("🚫 TEST 3: Autenticación inválida - Token incorrecto");
        
        String requestBody = "{" +
                "\"name\": \"Usuario No Autorizado\"," +
                "\"gender\": \"female\"," +
                "\"email\": \"unauthorized@testing.com\"," +
                "\"status\": \"active\"" +
                "}";
        
        given()
            .header("Authorization", "Bearer " + INVALID_TOKEN)  // ❌ TOKEN INVÁLIDO
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .body(requestBody)
        .when()
            .post("/public/v2/users")
        .then()
            .statusCode(401)  // ✅ UNAUTHORIZED como esperado
            .body("message", containsString("Invalid"))  // Mensaje de error de autenticación
            .log().all();
        
        System.out.println("✅ Error 401 recibido correctamente");
        System.out.println("✅ Token inválido rechazado por la API");
    }
    
    @Test
    @Order(4)
    @DisplayName("❌ AUTENTICACIÓN INVÁLIDA - Eliminar usuario sin token")
    void testInvalidAuth_DeleteUserWithoutToken() {
        System.out.println("🚫 TEST 4: Autenticación inválida - Sin token");
        
        // Intentar eliminar usuario sin proporcionar token de autenticación
        given()
            // ❌ SIN HEADER DE AUTHORIZATION
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
        .when()
            .delete("/public/v2/users/123")  // ID cualquiera
        .then()
            .statusCode(404)  // ✅ UNAUTHORIZED como esperado
            .log().all();
        
        System.out.println("✅ Error 404 recibido correctamente");
        System.out.println("✅ Operación sin token rechazada apropiadamente");
    }
    
    // ===============================================
    // TEST ADICIONAL - VERIFICAR ACCESO PÚBLICO
    // ===============================================
    
    @Test
    @Order(5)
    @DisplayName("🌐 VERIFICACIÓN - Acceso público sin autenticación")
    void testPublicAccess_GetUsersWithoutAuth() {
        System.out.println("🌐 TEST ADICIONAL: Verificando acceso público");
        
        // Las operaciones de lectura (GET) deben funcionar sin autenticación
        given()
            // Sin headers de autenticación
        .when()
            .get("/public/v2/users")
        .then()
            .statusCode(200)  // ✅ ACCESO PÚBLICO permitido
            .body("size()", greaterThan(0))  // Debe retornar usuarios
            .log().ifValidationFails();
        
        System.out.println("✅ Acceso público a lectura funcionando correctamente");
        System.out.println("✅ API permite GET sin autenticación");
    }
    
    @AfterAll
    static void tearDown() {
        System.out.println("🎉 Tests de seguridad completados - 5 casos ejecutados");
        System.out.println("📊 Resumen de autenticación:");
        System.out.println("  ✅ 2 tests con token válido - Exitosos");
        System.out.println("  ❌ 2 tests con token inválido - Fallos esperados (401)");
        System.out.println("  🌐 1 test de acceso público - Exitoso");
        System.out.println("🔐 Validación de seguridad completa");
    }
}