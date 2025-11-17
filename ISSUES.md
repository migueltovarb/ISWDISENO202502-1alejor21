# Issues - Sistema de Gestión de Pedidos para Cafetería

Este documento contiene la propuesta de issues para el Sistema de Gestión de Pedidos para Cafetería, organizado en EPICs, Historias de Usuario y Tareas de Wireframes.

---

## EPIC1: Gestión del Menú

**Labels:** `epic`, `backend`, `frontend`, `alta`

**Descripción:**

Como administrador del sistema, quiero tener un módulo completo de gestión del menú para poder mantener actualizada la oferta de productos disponibles en la cafetería.

**Contexto:**

Esta épica agrupa todas las funcionalidades relacionadas con la administración del catálogo de productos. Incluye la capacidad de registrar nuevos productos, actualizar información existente, eliminar productos descontinuados y permitir que los clientes visualicen el menú actualizado por categorías (bebidas calientes, bebidas frías, comidas rápidas y postres).

**Criterios de aceptación:**

- [ ] El sistema permite realizar operaciones CRUD sobre productos del menú
- [ ] Los productos se organizan por categorías
- [ ] Los clientes pueden visualizar el menú completo desde la aplicación móvil
- [ ] Los cambios en el menú se reflejan en tiempo real para todos los usuarios

**Dependencias:** Ninguna

---

## EPIC2: Gestión de Pedidos

**Labels:** `epic`, `backend`, `frontend`, `alta`

**Descripción:**

Como usuario del sistema, quiero gestionar todo el ciclo de vida de los pedidos desde su creación hasta su entrega para facilitar la operación de la cafetería.

**Contexto:**

Esta épica engloba todas las funcionalidades del flujo principal del sistema: creación de pedidos por parte de los clientes, modificación antes de la confirmación, seguimiento del estado del pedido, actualización de estados por parte del personal, visualización de tiempos estimados de entrega y generación de comprobantes. Es el núcleo funcional del sistema.

**Criterios de aceptación:**

- [ ] Los clientes pueden crear pedidos desde sus dispositivos móviles
- [ ] Los pedidos pueden ser modificados antes de su confirmación
- [ ] El sistema permite actualizar estados de pedidos (Pendiente, En preparación, Listo, Entregado)
- [ ] Se genera un número de pedido único para cada orden
- [ ] Los clientes reciben información sobre el tiempo estimado de entrega
- [ ] Se generan comprobantes para cada pedido
- [ ] El sistema controla permisos para evitar cambios indebidos una vez iniciada la preparación

**Dependencias:** Depende de EPIC1 (debe existir un menú de productos para poder crear pedidos)

---

## EPIC3: Reportes e Historial

**Labels:** `epic`, `backend`, `frontend`, `media`

**Descripción:**

Como administrador del sistema, quiero acceder a reportes y estadísticas de ventas para tomar decisiones informadas sobre el negocio.

**Contexto:**

Esta épica cubre las funcionalidades de reporting y auditoría del sistema. Incluye el historial completo de pedidos para consultas posteriores y la generación de reportes estadísticos como número de pedidos diarios, productos más vendidos, ingresos totales y ventas por turno o empleado.

**Criterios de aceptación:**

- [ ] El sistema mantiene un historial completo de todos los pedidos realizados
- [ ] Se pueden generar reportes de ventas con diferentes filtros y criterios
- [ ] Los reportes incluyen: número de pedidos diarios, productos más vendidos, ingresos totales, ventas por turno/empleado
- [ ] La información es accesible solo para usuarios con permisos de administrador o empleado
- [ ] Los reportes pueden exportarse o visualizarse en pantalla

**Dependencias:** Depende de EPIC2 (debe haber pedidos registrados para generar reportes)

---

## EPIC4: Usuarios, Roles y Acceso

**Labels:** `epic`, `backend`, `frontend`, `seguridad`, `alta`

**Descripción:**

Como administrador del sistema, quiero gestionar usuarios y controlar el acceso mediante roles para garantizar la seguridad y el uso adecuado del sistema.

**Contexto:**

Esta épica agrupa las funcionalidades de autenticación, autorización y gestión de usuarios. El sistema debe soportar diferentes roles (administrador, empleado/cajero, cliente) con permisos diferenciados. Los administradores pueden gestionar usuarios y asignar roles, mientras que cada rol tiene acceso a funcionalidades específicas del sistema.

**Criterios de aceptación:**

- [ ] El sistema permite la autenticación de usuarios
- [ ] Se implementan al menos dos roles: administrador y empleado/cajero
- [ ] Los administradores pueden crear, editar y eliminar usuarios
- [ ] Los administradores pueden asignar y modificar roles
- [ ] El sistema controla el acceso a las funcionalidades según el rol del usuario
- [ ] Se implementan mecanismos de seguridad para proteger credenciales

**Dependencias:** Ninguna

---

## HU001: Registro y gestión del menú de productos

**Labels:** `feature`, `backend`, `frontend`, `alta`, `crud`

**Descripción:**

Como administrador, quiero registrar, editar y eliminar productos del menú para mantener la carta actualizada y disponible en la aplicación.

**Contexto:**

El sistema debe permitir al administrador gestionar el catálogo completo de productos ofrecidos por la cafetería. Cada producto debe incluir: nombre, descripción, categoría (bebidas calientes, bebidas frías, comidas rápidas, postres), precio unitario, disponibilidad y opcionalmente una imagen. Esta funcionalidad es fundamental para mantener el menú actualizado según la oferta disponible.

**Criterios de aceptación:**

- [ ] El administrador puede acceder a una interfaz de gestión de productos
- [ ] Se puede registrar un nuevo producto con todos sus datos (nombre, descripción, categoría, precio, disponibilidad)
- [ ] Se puede editar la información de productos existentes
- [ ] Se puede eliminar productos del menú
- [ ] Se puede marcar productos como no disponibles temporalmente sin eliminarlos
- [ ] Los productos se organizan automáticamente por categorías
- [ ] Se valida que los datos ingresados sean correctos (precio > 0, categoría válida, etc.)
- [ ] Los cambios se reflejan inmediatamente en el menú visible para clientes

**Dependencias:** Depende de EPIC1 y EPIC4 (requiere autenticación como administrador)

---

## HU002: Visualizar menú de productos

**Labels:** `feature`, `frontend`, `alta`, `user-experience`

**Descripción:**

Como cliente, quiero visualizar el menú completo por categorías para poder elegir productos antes de realizar un pedido.

**Contexto:**

Los clientes deben poder acceder fácilmente al catálogo de productos disponibles desde sus dispositivos móviles. El menú debe organizarse por categorías (bebidas calientes, bebidas frías, comidas rápidas, postres) para facilitar la navegación. Cada producto debe mostrar su nombre, descripción, precio e imagen (si está disponible).

**Criterios de aceptación:**

- [ ] El menú es accesible desde la aplicación móvil sin necesidad de autenticación
- [ ] Los productos se muestran organizados por categorías
- [ ] Cada producto muestra: nombre, descripción, precio e imagen
- [ ] Solo se muestran productos disponibles
- [ ] La interfaz es clara y fácil de navegar
- [ ] Se puede filtrar o buscar productos por nombre o categoría
- [ ] El menú se actualiza automáticamente cuando hay cambios

**Dependencias:** Depende de EPIC1 y HU001 (debe existir un catálogo de productos)

---

## HU003: Crear un pedido

**Labels:** `feature`, `backend`, `frontend`, `alta`, `core-functionality`

**Descripción:**

Como cliente, quiero seleccionar productos y crear un pedido para solicitar mi orden desde el celular.

**Contexto:**

Esta es la funcionalidad principal del sistema desde la perspectiva del cliente. El usuario debe poder seleccionar múltiples productos del menú, especificar cantidades, ver el total a pagar y confirmar el pedido. Cada pedido debe generar un número único de identificación y contener toda la información necesaria: productos solicitados, cantidades, precios, total, fecha/hora, método de pago y estado inicial.

**Criterios de aceptación:**

- [ ] El cliente puede agregar productos del menú a un carrito de compra
- [ ] Se puede especificar la cantidad de cada producto
- [ ] El sistema calcula automáticamente el total a pagar
- [ ] Se puede seleccionar el método de pago (en línea, efectivo, etc.)
- [ ] Al confirmar, se genera un número de pedido único
- [ ] El pedido incluye: número único, identificación del cliente, lista de productos, cantidades, precios unitarios, total, fecha/hora, método de pago, estado inicial "Pendiente"
- [ ] Se muestra confirmación con el número de pedido y tiempo estimado
- [ ] El cliente puede revisar el pedido antes de confirmar

**Dependencias:** Depende de EPIC2 y HU002 (requiere visualización del menú)

---

## HU004: Modificar o eliminar pedido antes de confirmación

**Labels:** `feature`, `backend`, `frontend`, `media`, `user-experience`

**Descripción:**

Como cliente, quiero modificar mi pedido antes de que sea confirmado para corregir productos o cantidades.

**Contexto:**

Los clientes deben tener la posibilidad de realizar cambios en su pedido mientras este aún no ha sido procesado por el sistema. Una vez que el pedido cambia a estado "En preparación", ya no debe ser posible modificarlo para evitar inconsistencias en la operación de la cafetería.

**Criterios de aceptación:**

- [ ] El cliente puede modificar productos y cantidades mientras el pedido está en estado "Pendiente"
- [ ] El cliente puede eliminar productos del pedido mientras está en estado "Pendiente"
- [ ] El cliente puede cancelar completamente el pedido mientras está en estado "Pendiente"
- [ ] No se permite modificar pedidos una vez que pasan a estado "En preparación"
- [ ] El sistema recalcula el total cuando se hacen modificaciones
- [ ] Se muestra un mensaje claro indicando si el pedido puede o no ser modificado
- [ ] Los cambios se registran en el historial del pedido

**Dependencias:** Depende de EPIC2 y HU003 (primero debe existir un pedido)

---

## HU005: Actualizar estado del pedido

**Labels:** `feature`, `backend`, `frontend`, `alta`, `workflow`

**Descripción:**

Como empleado/cajero, quiero actualizar el estado de un pedido para gestionar adecuadamente el flujo de preparación (Pendiente, En preparación, Listo, Entregado).

**Contexto:**

El personal de la cafetería necesita una herramienta para gestionar el flujo de trabajo de los pedidos. Deben poder actualizar el estado de cada pedido a medida que avanza en el proceso: de "Pendiente" a "En preparación" cuando comienzan a prepararlo, a "Listo" cuando está terminado, y finalmente a "Entregado" cuando el cliente lo recoge.

**Criterios de aceptación:**

- [ ] Los empleados/cajeros pueden acceder a una lista de pedidos activos
- [ ] Se puede actualizar el estado de un pedido siguiendo el flujo: Pendiente → En preparación → Listo → Entregado
- [ ] No se permite retroceder a estados anteriores (solo avanzar)
- [ ] El cambio de estado se registra con fecha y hora
- [ ] El cliente puede ver el estado actualizado de su pedido en tiempo real
- [ ] Solo usuarios autenticados con rol empleado/cajero o administrador pueden cambiar estados
- [ ] Se muestra el historial de cambios de estado para auditoría

**Dependencias:** Depende de EPIC2, EPIC4 y HU003 (requiere pedidos existentes y autenticación)

---

## HU006: Ver el tiempo estimado de entrega

**Labels:** `feature`, `frontend`, `media`, `user-experience`

**Descripción:**

Como cliente, quiero ver cuánto falta para que mi pedido esté listo para saber cuándo recogerlo en ventanilla.

**Contexto:**

Para mejorar la experiencia del cliente, el sistema debe mostrar un tiempo estimado de entrega basado en la cantidad de pedidos en cola y la complejidad de los productos solicitados. Esta información ayuda al cliente a planificar cuándo acudir a recoger su pedido.

**Criterios de aceptación:**

- [ ] El sistema calcula un tiempo estimado de entrega al confirmar el pedido
- [ ] El tiempo estimado se muestra en la confirmación del pedido
- [ ] El cliente puede consultar el tiempo restante en cualquier momento
- [ ] El tiempo estimado se actualiza según el estado del pedido
- [ ] Cuando el pedido está "Listo", se notifica al cliente
- [ ] El tiempo estimado es visible en la pantalla de seguimiento del pedido

**Dependencias:** Depende de EPIC2 y HU003 (requiere pedidos existentes)

---

## HU007: Historial de pedidos

**Labels:** `feature`, `backend`, `frontend`, `media`, `reporting`

**Descripción:**

Como administrador o empleado, quiero ver el historial completo de pedidos para consultas y auditorías posteriores.

**Contexto:**

El sistema debe mantener un registro histórico de todos los pedidos realizados, permitiendo consultas para resolver dudas de clientes, auditorías internas, análisis de operación y soporte a la generación de reportes. El historial debe ser accesible solo para personal autorizado.

**Criterios de aceptación:**

- [ ] El sistema almacena todos los pedidos realizados sin importar su estado final
- [ ] Se puede acceder al historial con permisos de administrador o empleado
- [ ] Se pueden filtrar pedidos por: fecha, estado, cliente, productos, empleado
- [ ] Se muestra información completa de cada pedido (número, cliente, productos, total, fecha/hora, estado, método de pago)
- [ ] El historial está paginado para facilitar la navegación
- [ ] Se puede buscar un pedido específico por su número
- [ ] Se puede ver el detalle completo de cualquier pedido histórico

**Dependencias:** Depende de EPIC3 y HU003 (requiere pedidos registrados)

---

## HU008: Generar reporte de ventas

**Labels:** `feature`, `backend`, `frontend`, `media`, `reporting`, `analytics`

**Descripción:**

Como administrador, quiero ver estadísticas y reportes (número de pedidos diarios, productos más vendidos, ingresos, ventas por turno/empleado) para tomar decisiones.

**Contexto:**

Los reportes de ventas son fundamentales para la gestión del negocio. El administrador necesita información sobre el desempeño de la cafetería para tomar decisiones sobre inventario, personal, promociones y estrategia general. Los reportes deben ser precisos, actualizados y presentados de forma clara.

**Criterios de aceptación:**

- [ ] El administrador puede acceder a un módulo de reportes
- [ ] Se puede generar reporte de número de pedidos diarios
- [ ] Se muestra un ranking de productos más vendidos
- [ ] Se calcula y muestra el total de ingresos por período seleccionado
- [ ] Se pueden ver ventas desglosadas por turno (mañana, tarde)
- [ ] Se pueden ver ventas desglosadas por empleado
- [ ] Los reportes se pueden filtrar por rango de fechas
- [ ] La información se presenta en gráficos y tablas fáciles de interpretar
- [ ] Se pueden exportar los reportes en formato PDF o Excel

**Dependencias:** Depende de EPIC3, HU003 y HU007 (requiere historial de pedidos)

---

## HU009: Gestión de roles (admin y empleado)

**Labels:** `feature`, `backend`, `frontend`, `alta`, `seguridad`, `user-management`

**Descripción:**

Como administrador, quiero gestionar usuarios y roles del sistema para controlar accesos y permisos.

**Contexto:**

El sistema requiere diferentes niveles de acceso según el tipo de usuario. Los administradores necesitan control total, los empleados/cajeros necesitan acceso a funciones operativas, y los clientes solo a funciones de consulta y compra. El administrador debe poder crear usuarios, asignar roles y modificar permisos.

**Criterios de aceptación:**

- [ ] El administrador puede crear nuevos usuarios del sistema
- [ ] Se pueden asignar roles: administrador, empleado/cajero
- [ ] El administrador puede editar información de usuarios existentes
- [ ] El administrador puede desactivar o eliminar usuarios
- [ ] Cada rol tiene permisos específicos claramente definidos
- [ ] Los usuarios solo pueden acceder a funcionalidades permitidas para su rol
- [ ] Se valida el rol en cada operación sensible
- [ ] Se mantiene un registro de cambios de roles y permisos para auditoría

**Dependencias:** Depende de EPIC4

---

## HU010: Generar comprobante del pedido

**Labels:** `feature`, `backend`, `frontend`, `media`, `user-experience`

**Descripción:**

Como cliente, quiero recibir un comprobante con los datos del pedido para verificar mi orden y facilitar la entrega en ventanilla.

**Contexto:**

Al confirmar un pedido, el cliente debe recibir un comprobante digital que incluya toda la información relevante: número de pedido, productos solicitados con cantidades y precios, total pagado, fecha/hora, método de pago. Este comprobante sirve como confirmación y como referencia al momento de recoger el pedido.

**Criterios de aceptación:**

- [ ] Se genera automáticamente un comprobante al confirmar un pedido
- [ ] El comprobante incluye: número de pedido, fecha/hora, nombre/identificación del cliente, lista detallada de productos con cantidades y precios, subtotal, impuestos (si aplica), total pagado, método de pago
- [ ] El comprobante se muestra en pantalla inmediatamente después de confirmar
- [ ] El cliente puede descargar o guardar el comprobante en formato PDF
- [ ] El cliente puede volver a acceder al comprobante desde el historial de sus pedidos
- [ ] El comprobante incluye un código QR o número visible para facilitar la identificación en ventanilla
- [ ] El diseño del comprobante es claro y profesional

**Dependencias:** Depende de EPIC2 y HU003 (requiere un pedido confirmado)

---

## WF001: Wireframe pantalla de menú (listado de productos)

**Labels:** `wireframe`, `documentation`, `frontend`, `design`, `alta`

**Descripción:**

Como diseñador/desarrollador del proyecto, quiero documentar el wireframe de la pantalla de menú para establecer la estructura visual del listado de productos por categorías.

**Contexto:**

Esta tarea consiste en crear y documentar el wireframe de la pantalla principal donde los clientes visualizarán el menú de productos. Debe mostrar cómo se organizarán las categorías (bebidas calientes, bebidas frías, comidas rápidas, postres) y cómo se presentará cada producto con su información (nombre, descripción, precio, imagen).

**Criterios de aceptación:**

- [ ] Crear wireframe de la pantalla de menú mostrando la organización por categorías
- [ ] Incluir elementos visuales: navegación entre categorías, tarjetas de productos, precios, imágenes
- [ ] Mostrar cómo se visualiza el botón para agregar productos al carrito
- [ ] Incluir filtros o búsqueda de productos (si aplica)
- [ ] El wireframe debe ser para vista móvil (responsive)
- [ ] Subir captura de pantalla del wireframe como attachment al issue
- [ ] Documentar las interacciones principales del usuario en esta pantalla

**Dependencias:** Relacionado con HU002

---

## WF002: Wireframe pantalla creación de pedido

**Labels:** `wireframe`, `documentation`, `frontend`, `design`, `alta`

**Descripción:**

Como diseñador/desarrollador del proyecto, quiero documentar el wireframe de la pantalla de creación de pedido para establecer el flujo de compra y confirmación del pedido.

**Contexto:**

Esta tarea consiste en crear y documentar el wireframe del proceso de creación de pedido, incluyendo el carrito de compras, la selección de cantidades, el resumen del pedido, la selección del método de pago y la confirmación final. Es fundamental mostrar cómo el cliente verá el total a pagar y los detalles antes de confirmar.

**Criterios de aceptación:**

- [ ] Crear wireframe mostrando el carrito de compras con productos seleccionados
- [ ] Incluir controles para modificar cantidades de productos
- [ ] Mostrar el cálculo del total a pagar de forma clara
- [ ] Incluir sección de selección de método de pago
- [ ] Mostrar pantalla de confirmación con resumen del pedido
- [ ] Incluir visualización del número de pedido generado y tiempo estimado
- [ ] El wireframe debe ser para vista móvil (responsive)
- [ ] Subir captura de pantalla del wireframe como attachment al issue
- [ ] Documentar el flujo paso a paso de la creación del pedido

**Dependencias:** Relacionado con HU003, HU004 y HU010

---

## WF003: Wireframe pantalla seguimiento de pedido (estados y tiempo estimado)

**Labels:** `wireframe`, `documentation`, `frontend`, `design`, `media`

**Descripción:**

Como diseñador/desarrollador del proyecto, quiero documentar el wireframe de la pantalla de seguimiento de pedido para mostrar cómo los clientes verán el estado actual y el tiempo estimado de entrega.

**Contexto:**

Esta tarea consiste en crear y documentar el wireframe de la pantalla donde los clientes pueden hacer seguimiento de su pedido. Debe mostrar claramente el estado actual (Pendiente, En preparación, Listo, Entregado), el tiempo estimado restante, el número de pedido y un resumen de los productos solicitados.

**Criterios de aceptación:**

- [ ] Crear wireframe mostrando los diferentes estados del pedido de forma visual
- [ ] Incluir indicador de progreso o timeline del estado del pedido
- [ ] Mostrar el tiempo estimado de entrega de forma prominente
- [ ] Incluir el número de pedido y resumen de productos
- [ ] Mostrar notificación clara cuando el pedido esté "Listo"
- [ ] El wireframe debe ser para vista móvil (responsive)
- [ ] Subir captura de pantalla del wireframe como attachment al issue
- [ ] Documentar las actualizaciones en tiempo real del estado

**Dependencias:** Relacionado con HU005 y HU006

---

## WF004: Wireframe pantalla de reportes

**Labels:** `wireframe`, `documentation`, `frontend`, `design`, `media`

**Descripción:**

Como diseñador/desarrollador del proyecto, quiero documentar el wireframe de la pantalla de reportes para establecer cómo se presentarán las estadísticas y análisis de ventas.

**Contexto:**

Esta tarea consiste en crear y documentar el wireframe de la interfaz de reportes para administradores. Debe mostrar cómo se visualizarán las estadísticas (número de pedidos, productos más vendidos, ingresos), los filtros disponibles (por fecha, turno, empleado) y la presentación de datos mediante gráficos y tablas.

**Criterios de aceptación:**

- [ ] Crear wireframe del dashboard de reportes con métricas principales
- [ ] Incluir visualización de gráficos (barras, líneas, pastel) para diferentes estadísticas
- [ ] Mostrar filtros de fecha, turno y empleado
- [ ] Incluir secciones para: pedidos diarios, productos más vendidos, ingresos totales
- [ ] Mostrar opciones de exportación de reportes
- [ ] El wireframe puede ser para vista desktop o tablet
- [ ] Subir captura de pantalla del wireframe como attachment al issue
- [ ] Documentar los diferentes tipos de reportes disponibles

**Dependencias:** Relacionado con HU007 y HU008

---

## WF005: Wireframe pantalla de login/roles

**Labels:** `wireframe`, `documentation`, `frontend`, `design`, `seguridad`, `media`

**Descripción:**

Como diseñador/desarrollador del proyecto, quiero documentar el wireframe de la pantalla de login y gestión de roles para establecer el flujo de autenticación y administración de usuarios.

**Contexto:**

Esta tarea consiste en crear y documentar los wireframes relacionados con la autenticación y gestión de usuarios. Incluye la pantalla de login, la pantalla de gestión de usuarios (para administradores) donde se pueden crear, editar y asignar roles, y potencialmente una pantalla de perfil de usuario.

**Criterios de aceptación:**

- [ ] Crear wireframe de la pantalla de login con campos de usuario y contraseña
- [ ] Incluir opciones de recuperación de contraseña (si aplica)
- [ ] Crear wireframe de la pantalla de gestión de usuarios mostrando lista de usuarios
- [ ] Incluir formulario para crear/editar usuarios y asignar roles
- [ ] Mostrar claramente los diferentes roles (administrador, empleado/cajero)
- [ ] El wireframe debe considerar tanto vista móvil como desktop según corresponda
- [ ] Subir captura de pantalla del wireframe como attachment al issue
- [ ] Documentar el flujo de autenticación y navegación según rol

**Dependencias:** Relacionado con HU009 y EPIC4

---

## Resumen de Issues

**Total de issues:** 19

**EPICs (4):**
- EPIC1: Gestión del Menú
- EPIC2: Gestión de Pedidos
- EPIC3: Reportes e Historial
- EPIC4: Usuarios, Roles y Acceso

**Historias de Usuario (10):**
- HU001: Registro y gestión del menú de productos
- HU002: Visualizar menú de productos
- HU003: Crear un pedido
- HU004: Modificar o eliminar pedido antes de confirmación
- HU005: Actualizar estado del pedido
- HU006: Ver el tiempo estimado de entrega
- HU007: Historial de pedidos
- HU008: Generar reporte de ventas
- HU009: Gestión de roles (admin y empleado)
- HU010: Generar comprobante del pedido

**Wireframes (5):**
- WF001: Wireframe pantalla de menú (listado de productos)
- WF002: Wireframe pantalla creación de pedido
- WF003: Wireframe pantalla seguimiento de pedido (estados y tiempo estimado)
- WF004: Wireframe pantalla de reportes
- WF005: Wireframe pantalla de login/roles

---

## Notas de Implementación

1. Los EPICs deben crearse primero en el sistema de issues de GitHub.
2. Las Historias de Usuario deben vincularse a su EPIC correspondiente mediante referencias o labels.
3. Los Wireframes son tareas de diseño que deben completarse antes de implementar las interfaces correspondientes.
4. Las dependencias indicadas son lógicas y deben respetarse en el orden de implementación.
5. Se recomienda asignar prioridades: las issues marcadas con label `alta` deben implementarse primero.
6. Las labels sugeridas pueden ajustarse según las convenciones del equipo de desarrollo.
