
# Telegram bot: gestor de control de ingresos y egresos de un usuario

Esto es un proyecto para poner en práctica los recién adquiridos conocimientos en Java y SQL, de un bot al que un usuario de telegram pueda acceder teniendo conexión a internet y gestionar sus ingresos y gastos personales.



## Descripción del proyecto

El Gestor de Control de Ingresos y Egresos de un Usuario es un proyecto diseñado para poner en práctica los conocimientos adquiridos en Java y SQL. Este bot de Telegram permite a los usuarios gestionar de manera eficiente sus finanzas personales a través de un interfaz de chat accesible desde cualquier dispositivo con conexión a Internet.
Características Principales

* Registro de Movimientos: Los usuarios pueden registrar ingresos y egresos de manera sencilla. Cada movimiento se almacena en una base de datos MySQL para su posterior consulta y análisis.

* Consulta de Historial: Permite a los usuarios consultar el historial de sus movimientos financieros, brindándoles una visión clara y organizada de sus transacciones pasadas.

* Gestión de Ahorros: Los usuarios pueden agregar y consultar registros de sus ahorros, lo que facilita el seguimiento de sus metas financieras y el control de sus ahorros acumulados.

* Interacción Directa en Telegram: La interfaz basada en Telegram permite a los usuarios interactuar con el bot mediante comandos simples, eliminando la necesidad de interfaces complejas y facilitando el acceso desde cualquier lugar.

## Stack utilizado
![image](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![image](https://img.shields.io/badge/Java-1FBED6?style=for-the-badge&logo=java&logoColor=white)
![image](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![image](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)
![image](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

* MySQL: Sistema de gestión de bases de datos relacional utilizado para almacenar y gestionar los datos financieros del usuario. [Doc](https://www.mysql.com/)

* Java: Lenguaje de programación principal para el desarrollo del bot, aprovechando su robustez y facilidad para trabajar con bases de datos y redes. [Doc](https://www.java.com/es/)

* Apache Maven: Herramienta de gestión y construcción de proyectos utilizada para gestionar las dependencias y construir el proyecto de manera eficiente. [Doc](https://maven.apache.org/)

* Telegram API: Plataforma de mensajería utilizada para la interacción con el bot, proporcionando una forma intuitiva y accesible para que los usuarios gestionen sus finanzas. [Doc](https://core.telegram.org/bots/tutorial)

## Objetivo

El objetivo de este proyecto es ofrecer una solución práctica y accesible para el seguimiento de las finanzas personales, utilizando tecnologías modernas y ampliamente utilizadas en el desarrollo de software. A través de este bot, los usuarios podrán tener un control más efectivo de sus ingresos, egresos y ahorros, contribuyendo a una mejor gestión de su economía personal.

## Clonar el repositorio

```
git clone https://github.com/borgesmj/telegram_app_bot.git
```

o [Abrir con project IDX](https://idx.google.com/import?url=https://github.com/borgesmj/telegram_app_bot) 

## Configuración del entorno de desarrollo
1. Crea el archivo en la ruta `.vscode/settings.json` y pega lo siguiente
```json
{
    "files.autoSave": "off",
    "editor.wordWrap": "on",
    "java.configuration.updateBuildConfiguration": "interactive"
}
```

2. Configuramos nuestro entorno para trabajar con MySQL 8.0
En nuestro archivo `.idx/dev.nix` colocamos este código justo debajo de `env = {}`

```
services.mysql = {
    enable = true;
    package = pkgs.mysql80;
  };
``` 

Con esto podemos crear nuestra base de datos

### Estructura del proyecto
```tree
├── pom.xml
├── README.md
├── src
│   └── main
│       └── java
│           └── com
│               └── saburo
│                   └── telegrambot
│                       ├── bot
│                       │   ├── CommandHandler.java
│                       │   ├── ErrorsHandler.java
│                       │   ├── MessageHandler.java
│                       │   ├── MessageListener.java
│                       │   ├── MessageSender.java
│                       │   ├── TelegramBotContent.java
│                       │   └── TelegramBot.java
│                       ├── config
│                       │   └── EnvConfig.java
│                       ├── database
│                       │   ├── CreateTablesCommands.java
│                       │   ├── DatabaseCommands.java
│                       │   └── DatabaseConnection.java
│                       ├── Main.java
│                       └── user
│                           ├── UserProfile.java
│                           ├── UserReports.java
│                           └── UserStatus.java

```



### Telegram Bot username y Bot token
Los datos necesarios para la autenticacion de nuestra app los obtenemos creando un bot nuevo en el [Bot Father](https://web.telegram.org/k/#@BotFather)


![](https://mermaid.ink/img/pako:eNplkUtOwzAQhq8y8gqk5gJBArVNW7qgLFpWSRdDMqQWsR35gYSiHIkzIMHFmLxQELZk-fH9__y2G5GbgkQsSov1BU7JTaaB2_Iq3WuZSwMROI_Wn68him5h1Xx9PrmAlk90oDdz146KFZ_D8fujx9asLi05hDDChw4-z9mD6dEkfTDOW7SgSAeordS5rLGa2GFc9-wmXVYKc9JM99Un-wne9NiWQ54kaQJt1LMlKKgDLUqOm-kB3c7z7liRkCOEkv0Ktndh8u4kv4Ix9H16NBU_j_8XZLLf9fTjQM-2por72VWUdOqvQ9fFQiiyCmXB39N0FpnwF1KUiZinHPI1E5lumcPgzfFd5yL2NtBCWBPKi4hfsHK8CnWBnhKJ_Mdq3G1_AOi6o_g?type=png)](https://mermaid.live/edit#pako:eNplkUtOwzAQhq8y8gqk5gJBArVNW7qgLFpWSRdDMqQWsR35gYSiHIkzIMHFmLxQELZk-fH9__y2G5GbgkQsSov1BU7JTaaB2_Iq3WuZSwMROI_Wn68him5h1Xx9PrmAlk90oDdz146KFZ_D8fujx9asLi05hDDChw4-z9mD6dEkfTDOW7SgSAeordS5rLGa2GFc9-wmXVYKc9JM99Un-wne9NiWQ54kaQJt1LMlKKgDLUqOm-kB3c7z7liRkCOEkv0Ktndh8u4kv4Ix9H16NBU_j_8XZLLf9fTjQM-2por72VWUdOqvQ9fFQiiyCmXB39N0FpnwF1KUiZinHPI1E5lumcPgzfFd5yL2NtBCWBPKi4hfsHK8CnWBnhKJ_Mdq3G1_AOi6o_g)

