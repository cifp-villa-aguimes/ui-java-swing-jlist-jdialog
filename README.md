# UT5.2 - Ejemplo Didactico de Swing con `JList` y `JDialog`

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-orange)
![Nivel](https://img.shields.io/badge/Nivel-1%C2%BA%20DAM%20%7C%201%C2%BA%20DAW-success)
![Tipo](https://img.shields.io/badge/Proyecto-Educativo-informational)
![Estado](https://img.shields.io/badge/Estado-Listo%20para%20clase-brightgreen)

Proyecto guiado para introducir Java Swing en un entorno educativo.
El objetivo es que el alumnado vea, en un ejemplo pequeno y claro, como se conectan datos, interfaz y eventos.

## Que vas a aprender

- Crear una ventana con `JFrame`, paneles y componentes basicos.
- Mostrar objetos (`Persona`) en una `JList`.
- Trabajar con `DefaultListModel` para agregar y eliminar elementos.
- Gestionar eventos de seleccion, doble clic y botones.
- Abrir un `JDialog` modal desde la ventana principal.
- Aplicar estilo basico con `UIManager` y un renderer personalizado.
- Ejecutar Swing correctamente en el EDT con `SwingUtilities.invokeLater(...)`.

## Competencias didacticas (UT5.2)

- Separar responsabilidades: modelo, vista y control de eventos.
- Leer codigo de interfaz sin perderse en detalles.
- Razonar el flujo de la aplicacion a partir de acciones de usuario.
- Modificar una app existente sin romper comportamiento base.

## Estructura del proyecto

```text
src/
  app/
    EjemploJListDialog.java   # Ventana principal, listeners y dialogo
  model/
    Persona.java              # Entidad de dominio (POO)
  config/
    UIConfig.java             # Look & Feel y ajustes visuales
bin/                          # Clases compiladas
lib/                          # Dependencias (si se usan)
```

## Ejecucion

### Opcion 1: VS Code

1. Abre la carpeta del proyecto.
2. Ejecuta `EjemploJListDialog.java` con "Run Java".

### Opcion 2: Terminal

Compilar:

```bash
javac -d bin src/config/UIConfig.java src/model/Persona.java src/app/EjemploJListDialog.java
```

Ejecutar:

```bash
java -cp bin app.EjemploJListDialog
```

## Recorrido sugerido para clase (30-45 min)

1. Ejecutar la app e identificar zonas: lista, detalle y acciones.
2. Seleccionar personas y observar como cambia la ficha de datos.
3. Hacer doble clic para abrir el `JDialog` modal.
4. Probar botones:
   - `Agregar ejemplo`
   - `Eliminar seleccion`
   - `Limpiar seleccion`
5. Localizar en codigo:
   - donde se crea el modelo (`DefaultListModel`)
   - donde se enlazan listeners
   - donde se actualiza la vista de detalle

## Retos para el alumnado

- Nivel basico: anadir una persona fija desde un nuevo boton.
- Nivel medio: crear formulario con `JTextField` y validaciones simples.
- Nivel avanzado: guardar y cargar datos desde fichero.
- Extra: filtrar por grupo (`1 DAM`, `1 DAW`) y comparar UX.

## Criterios de autoevaluacion rapida

- Entiendo que diferencia hay entre `JList` y `DefaultListModel`.
- Soy capaz de explicar que hace cada listener principal.
- Puedo anadir funcionalidad sin romper la seleccion ni el dialogo.
- Se ejecutar el proyecto tanto en IDE como en terminal.

## Nota para docentes

El ejemplo esta pensado para explicar en pizarra y practicar en parejas.
Incluye complejidad suficiente para trabajar eventos reales, pero mantiene una base pequena y legible.
