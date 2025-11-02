<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Добавить новую команду</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-notes.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-user_edit.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/teams" role="button">
                &#8592;
                <span class="ms-2">Назад</span>
            </a>
        </div>
    </div>
</nav>

<main class="container my-5">
    <h1 class="section-title mb-4">Добавить новую команду</h1>

    <form action="${contextPath}/team/create" method="post" enctype="multipart/form-data" class="mb-5 p-4 border rounded shadow-sm bg-light">
        <div class="mb-3">
            <label for="photo" class="form-label fw-bold">Логотип команды</label>
            <input type="file" id="photo" name="photo" class="form-control" accept="image/*" />
        </div>
        <div class="mb-3">
            <label for="name" class="form-label fw-bold">Название команды</label>
            <input type="text" id="name" name="name" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="country" class="form-label fw-bold">Страна</label>
            <input type="text" id="country" name="country" class="form-control" required />
        </div>

        <button type="submit" class="btn btn-main">Добавить команду</button>
    </form>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
