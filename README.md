# RaceTalk — Simple Formula 1 Fan Website

## What is this?
RaceTalk is a simple web app for Formula 1 fans. It’s made with Java Servlets, JSP, and JDBC without any big frameworks. The site lets users register, log in, view teams and drivers, write notes, and chat with others.

## Main Features
- User registration and login
- View F1 teams and drivers
- Create, read, update, delete (CRUD) your notes
- Join a shared chat
- Secure password storage and basic authorization
- Uses PostgreSQL and JDBC with prepared statements to keep data safe
- UI is made with Bootstrap for easy responsive design
- Uses MVC structure: separate data, logic, and web pages
- Simple JavaScript for validation and better user experience

## Technologies Used
- Java Servlets and JSP
- JDBC for database access
- PostgreSQL as the database
- Maven for building the project
- Bootstrap 5 for styling
- SLF4J for logging

## How to Run
1. Download or clone this project
2. Set your PostgreSQL connection settings inside the `DatabaseConnectionUtil.java` file by editing the URL, username, and password fields
3. Run SQL script to create tables in your database
4. Build the project using Maven
5. Deploy the `.war` file on Tomcat server
6. Open your browser at `http://localhost:8080/racetalk`

## Project Structure
- `com.racetalk.entity` — Entity classes
- `com.racetalk.dao` — Database access classes
- `com.racetalk.service` — Business logic
- `com.racetalk.web` — Servlets, Listeners, Filters
- `com.racetalk.util` — Utility classes such as database connection
- `webapp` — FreeMarker templates (.ftl) with CSS and JavaScript