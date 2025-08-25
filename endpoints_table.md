# 📋 Tabla de Endpoints - Proyecto Testing APIs

## 🚀 JSONPlaceholder API (SimpleApiTest.java)

| **Método** | **Endpoint** | **Descripción** | **Código Esperado** | **Validaciones** |
|------------|--------------|-----------------|-------------------|------------------|
| `GET` | `/posts` | Obtener lista de posts | `200` | - Array no vacío<br>- Contiene campos: id, title, body |
| `POST` | `/posts` | Crear nuevo post | `201` | - Título coincide<br>- Cuerpo coincide<br>- ID generado |
| `GET` | `/users` | Obtener lista de usuarios | `200` | - Array no vacío<br>- Contiene campos: id, name, email |
| `GET` | `/posts/1` | Obtener post específico | `200` | - ID = 1<br>- Título no nulo<br>- Cuerpo no nulo |
| `PUT` | `/posts/1` | Actualizar post completo | `200` | - Título actualizado<br>- Cuerpo actualizado |
| `GET` | `/posts/999` | Test error 404 | `404` | - Error not found |
| `GET` | `/posts/999999` | Test negativo 404 | `404` | - Error esperado |
| `GET` | `/posts/999999` | Test respuesta vacía | `404` | - Respuesta vacía |

---

## 🔐 GoRest API (GoRestAuth.java)

| **Método** | **Endpoint** | **Descripción** | **Código Esperado** | **Autenticación** | **Validaciones** |
|------------|--------------|-----------------|-------------------|------------------|------------------|
| `POST` | `/public/v2/users` | Crear usuario con token válido | `201` | Bearer Token ✅ | - Nombre correcto<br>- Email único<br>- Género correcto<br>- Status activo |
| `PATCH` | `/public/v2/users/{id}` | Actualizar usuario con token válido | `200` | Bearer Token ✅ | - Nombre actualizado<br>- Status cambiado<br>- ID coincide |
| `POST` | `/public/v2/users` | Crear usuario con token inválido | `401` | Token Inválido ❌ | - Error unauthorized<br>- Mensaje de error |
| `DELETE` | `/public/v2/users/123` | Eliminar usuario sin token | `404` | Sin Token ❌ | - Error de acceso |
| `GET` | `/public/v2/users` | Acceso público sin auth | `200` | Sin Auth 🌐 | - Lista de usuarios<br>- Acceso público |

---

## 📊 Resumen de Cobertura

### **JSONPlaceholder API** 
- ✅ **6 Tests Positivos** (CRUD completo)
- ⚠️ **2 Tests Negativos** (Validación de errores)
- 🌐 **Base URL:** `https://jsonplaceholder.typicode.com`

### **GoRest API**
- ✅ **2 Tests con Autenticación Válida**
- ❌ **2 Tests con Autenticación Inválida**
- 🌐 **1 Test de Acceso Público**
- 🔐 **Base URL:** `https://gorest.co.in`

---

## 🔧 Configuración de Headers

### JSONPlaceholder
```json
{
  "Content-Type": "application/json"
}
```

### GoRest (Autenticado)
```json
{
  "Authorization": "Bearer {TOKEN}",
  "Content-Type": "application/json",
  "Accept": "application/json"
}
```

---

## 📝 Notas Importantes

- **JSONPlaceholder:** API completamente gratuita, sin límites de uso
- **GoRest:** Requiere token válido para operaciones de escritura
- **Orden de Ejecución:** Tests numerados con `@Order` para secuencia específica
- **Evidencias:** Generación automática con `AutoEvidenceGenerator` (comentado)