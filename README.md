# Lecano · Petagram

Aplicación Android en **Kotlin** que implementa una lista de mascotas con `RecyclerView`, sistema de rating y una pantalla de las últimas 5 mascotas favoritas.

## Parte 1 · Lista de mascotas

La pantalla principal utiliza un `RecyclerView` para mostrar un DataSet de mascotas. Cada item incluye:

- Identidad visual de la mascota
- Nombre
- Rating actual
- Hueso amarillo que representa el rating acumulado
- Hueso de acción para dar un voto a la mascota

Cada mascota puede recibir un voto por sesión desde el hueso de acción. Al votar, el contador aumenta y el hueso cambia de estado.

## Parte 2 · Action View de favoritos

La barra superior contiene un **Action View en forma de estrella** con el número 5.

Al seleccionarlo se abre `FavoritesActivity`, que:

- Muestra exactamente **5 mascotas hardcodeadas**
- Utiliza el mismo `RecyclerView`
- Permite regresar al Activity padre mediante el botón de navegación

La pantalla principal también contiene un **FloatingActionButton** para subir rápidamente al inicio de la lista.

## Arquitectura solicitada

```text
app/src/main/java/com/example/lecano/
├── Mascota.kt              # Entidad
├── MascotaData.kt          # DataSet
├── MascotaAdapter.kt       # Adapter + ViewHolder
├── MainActivity.kt         # Lista principal
└── FavoritesActivity.kt    # Últimas 5 favoritas
```

Layouts principales:

```text
app/src/main/res/layout/
├── activity_main.xml
├── activity_favorites.xml
├── item_mascota.xml
└── view_action_favorites.xml
```

## Criterios de evaluación cubiertos

- ✅ Proyecto ejecutable
- ✅ DataSet
- ✅ Adapter
- ✅ ViewHolder
- ✅ Layout de item para el RecyclerView
- ✅ RecyclerView principal
- ✅ Action View de estrella
- ✅ Acción para abrir favoritos
- ✅ RecyclerView con 5 mascotas
- ✅ Botón para subir al inicio
- ✅ Rating mediante icono de hueso
- ✅ Navegación de regreso al Activity padre

## Flujo

```text
MainActivity
Lista de mascotas
      │
      ├── Hueso → aumenta rating
      │
      ├── FAB ↑ → vuelve al inicio
      │
      └── ⭐ 5
           │
           ▼
FavoritesActivity
5 mascotas hardcodeadas
           │
           └── ← Regresar
```

## Ejecutar

Abre el proyecto en Android Studio, sincroniza Gradle y ejecuta en un emulador o dispositivo Android.

## Descargar los últimos cambios

```bash
git switch main
git pull origin main
```

## Evidencias

Guarda los pantallazos finales de esta actividad dentro de:

```text
docs/screenshots/
```

## Repositorio

**CristianCanoIng/Lecano**
