<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>Список гонщиков — RaceTalk</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/RaceTalk_war/assets/css/page-drivers.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3" aria-label="Главное меню">
    <a class="navbar-brand" href="/RaceTalk_war/">RaceTalk</a>
</nav>

<section class="hero d-flex justify-content-start align-items-center">
    <div class="hero-content">
        <h1 class="hero-title">Гонщики сезона</h1>
        <p class="hero-desc">
            Перейдите на страницу гонщика для подробной информации
        </p>

        <div class="table-responsive">
            <table class="table table-hover align-middle shadow-sm">
                <thead class="table-danger">
                <tr>
                    <th scope="col">Гоночный номер</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Фамилия</th>
                    <th scope="col">Страна</th>
                    <th scope="col">Дата рождения</th>
                    <th scope="col">Команда</th>
                </tr>
                </thead>
                <tbody>
                <#list drivers as driver>
                    <tr style="cursor:pointer;" onclick="window.location.href='/RaceTalk_war/driver/${driver.driverNumber}';">
                        <td>${driver.driverNumber}</td>
                        <td>${driver.firstName}</td>
                        <td>${driver.lastName}</td>
                        <td>${driver.country}</td>
                        <#assign dateOfBirthAsDate = driver.dateOfBirth?date("yyyy-MM-dd")>
                        <td>${dateOfBirthAsDate?string("dd.MM.yyyy")}</td>
                        <td>${driver.team.name}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</section>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
