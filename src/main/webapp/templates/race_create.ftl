<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Добавить гонку</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-notes.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
</nav>

<main class="container my-5">
    <h1 class="section-title mb-4">Добавить новую гонку</h1>

    <form action="${contextPath}/race/create" method="post" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <div class="mb-3">
            <label for="location" class="form-label fw-bold">Место проведения</label>
            <input type="text" class="form-control" id="location" name="location" required />
        </div>

        <div class="mb-3">
            <label for="raceDate" class="form-label fw-bold">Дата гонки</label>
            <input type="date" class="form-control" id="raceDate" name="raceDate" required />
        </div>

        <button type="submit" class="btn btn-main">Добавить гонку</button>
    </form>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
