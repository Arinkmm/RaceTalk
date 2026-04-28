# RaceTalk — Фан-сайт сообщества Formula 1

Простое веб-приложение для фанатов Формулы 1, созданное на базе **Java Servlets**, **шаблонов FreeMarker** и **PostgreSQL**.

## Возможности

- **Аутентификация пользователей** — Безопасная регистрация и вход с хешированием паролей через BCrypt
- **Управление сессиями** — Разграничение доступа и авторизация на основе ролей
- **Профили пользователей** — Просмотр и редактирование профиля с обновлением личных данных
- **Контент о Формуле 1** — Просмотр команд, гонщиков и результатов гонок
- **Личные заметки** — Создание, просмотр, редактирование и удаление заметок
- **Чат в реальном времени** — Чат на базе AJAX с мгновенной доставкой сообщений
- **Адаптивный дизайн** — Bootstrap 5 для удобного интерфейса
- **Безопасность базы данных** — JDBC с подготовленными запросами для защиты от SQL-инъекций
- **Валидация на стороне клиента** — AJAX-проверка уникальности имени пользователя и надёжности пароля при регистрации
- **Обработка ошибок** — Кастомные страницы ошибок и обработка исключений

## Требования

- Java 17+
- Apache Tomcat 10+
- PostgreSQL 13+
- Maven 3.8+
- IntelliJ IDEA 2023+ (рекомендуется)

## Архитектура проекта

### Структура пакетов
```
src/main/java/com/racetalk/
├── dao/        # Объекты доступа к данным
├── service/    # Бизнес-логика
├── dto/        # Объекты передачи данных
├── entity/     # Доменные сущности (User, Team, Driver...)
├── exception/  # Кастомные исключения
├── util/       # Утилиты (DB, Cloudinary, BCrypt...)
├── web/
│   ├── servlet/  # Контроллеры (RaceServlet, ChatServlet...)
│   ├── filter/   # AuthenticationFilter
└── └── listener/ # InitListener
```

## Шаблоны и ресурсы

```
web-app/
├── templates/
├── assets/
│   ├── css/
│   ├── js/
└── └── images/  # логотипы команд, гонщики, иконки
```

## Инструкция по установке

### 1. Клонирование репозитория

В IntelliJ IDEA:

1. Откройте IntelliJ IDEA
2. На экране приветствия нажмите кнопку **Clone Repository**
3. В появившемся окне:
   - **URL:** Вставьте `https://github.com/Arinkmm/RaceTalk.git`
   - **Directory:** Выберите папку для сохранения (или оставьте по умолчанию)
4. Нажмите **Clone**
5. Дождитесь завершения загрузки
6. Когда IntelliJ спросит «Would you like to open the cloned repository?», нажмите **Yes**

Проект откроется автоматически.

### 2. Создание базы данных PostgreSQL

Через pgAdmin:

1. Откройте pgAdmin 4 (веб-интерфейс для PostgreSQL)
2. Правой кнопкой мыши нажмите на **Databases** → **Create** → **Database**
3. Введите имя: `racetalk_db`
4. Нажмите **Save**
5. Откройте **Query Tool** (редактор SQL)

Затем выполните:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    photo VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'USER'
);

CREATE TABLE teams (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    country VARCHAR(50) NOT NULL,
    photo VARCHAR(255)
);

CREATE TABLE drivers (
    driver_number SERIAL PRIMARY KEY,
    team_id INT NOT NULL REFERENCES teams(id),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    country VARCHAR(50),
    photo VARCHAR(255)
);

CREATE TABLE past_races (
    id SERIAL PRIMARY KEY,
    session_key INT NOT NULL,
    location VARCHAR(100) NOT NULL,
    race_date DATE NOT NULL,
    is_finished BOOLEAN DEFAULT FALSE
);

CREATE TABLE race_results (
    id SERIAL PRIMARY KEY,
    race_id INT NOT NULL REFERENCES past_races(id) ON DELETE CASCADE,
    driver_number INT NOT NULL REFERENCES drivers(driver_number) ON DELETE CASCADE,
    position INT,
    points INT,
    UNIQUE (race_id, driver_number)
);

CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE upcoming_races (
    id SERIAL PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    race_date DATE NOT NULL,
    is_finished BOOLEAN DEFAULT FALSE
);
```

### 3. Настройка переменных окружения в IntelliJ IDEA

**Шаг 1:** Перейдите в Run → Edit Configurations...

**Шаг 2:** Выберите вашу конфигурацию Tomcat (или создайте новую)

**Шаг 3:** В поле Environment variables добавьте:

```
DATABASE_CONNECTION_URL=jdbc:postgresql://localhost:5432/racetalk_db;
DATABASE_CONNECTION_USERNAME=postgres;
DATABASE_CONNECTION_PASSWORD=<ваш_пароль>
CLOUDINARY_CLOUD_NAME=<ваше_cloud_name>
CLOUDINARY_API_KEY=<ваш_api_key>
CLOUDINARY_API_SECRET=<ваш_api_secret>
```

Замените `<ваш_пароль>`, `<ваше_cloud_name>`, `<ваш_api_key>` и `<ваш_api_secret>` на реальные значения.

**Шаг 4:** Нажмите **OK** и сохраните конфигурацию.

### 4. Сборка проекта

1. В правой части экрана откройте панель **Maven**
2. Разверните: **RaceTalk** → **Lifecycle**
3. Дважды щёлкните по **clean**
4. После завершения дважды щёлкните по **package**
5. Логи сборки появятся в нижней панели

В результате в папке `target/` будет создан файл `.war`.

### 5. Запуск на Tomcat

1. Перейдите в **Run → Run 'Tomcat Server'**
2. Приложение запустится и откроется в браузере по умолчанию.

## Ключевые компоненты

### Сервлеты
- `RaceServlet` — Отображение данных о гонках
- `DriverServlet` — Управление списком гонщиков
- `TeamServlet` — Отображение команд
- `NotesServlet` — CRUD-операции для заметок
- `ChatServlet` — Функциональность чата в реальном времени
- `LoginServlet` — Аутентификация пользователей
- `SignUpServlet` — Регистрация пользователей

### Фильтры
- `AuthenticationFilter` — Проверка сессии пользователя перед доступом к защищённым страницам

### Слушатели
- `InitListener` — Инициализация сервисов при запуске приложения

### Шаблоны
- Все страницы используют FreeMarker для рендеринга динамического контента
- Единое оформление: базовый CSS + CSS-файлы для отдельных страниц

## Скриншоты

### Главная страница

![Главная страница](images/index.png)

### Страница регистрации

![Страница регистрации](images/sign_up.png)

### Основная страница

![Основная страница](images/main.png)

### Страница гонщиков

![Страница гонщиков](images/drivers.png)

### Страница команд

![Страница команд](images/teams.png)

### Страница прошедших гонок

![Страница прошедших гонок](images/races.png)

### Страница результатов

![Страница результатов](images/results.png)

### Страница заметок пользователя

![Страница заметок](images/notes.png)

### Чат

![Чат](images/chat.png)

### Профиль пользователя

![Профиль](images/profile.png)

## Поддержка и контакты

Остались вопросы? Нужна помощь с настройкой? Нашли баг?

Email: **mairabeeva42@gmail.com** | Telegram: @arinkmm
