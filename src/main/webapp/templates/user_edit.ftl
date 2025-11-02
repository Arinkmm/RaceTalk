<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <title>RaceTalk — Редактировать профиль</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/page-user_edit.css" rel="stylesheet" />
</head>
<body>

<nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
    <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
    <div class="collapse navbar-collapse justify-content-end">
        <div class="navbar-nav">
            <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/user/profile/${user.id}" role="button">
                &#8592;
                <span class="ms-2">Назад</span>
            </a>
        </div>
    </div>
</nav>

<div class="container-edit-profile">
    <h1 class="hero-title">Редактировать профиль</h1>

    <#if EditErrorMessage??>
        <div class="validation-error">${EditErrorMessage}</div>
    <#else>
        <div class="validation-error hidden"></div>
    </#if>

    <form method="POST" action="${contextPath}/user/profile/${user.id}" enctype="multipart/form-data" novalidate>
        <div class="mb-3">
            <label class="form-label fw-bold text-danger">Изменить фото</label>
            <input class="form-control" type="file" name="photo" accept="image/*">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold text-danger">Имя пользователя</label>
            <input type="text" class="form-control" name="username" value="${user.username! ''}" required>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold text-danger">Статус пользователя</label>
            <input type="text" class="form-control" name="status" value="${user.status! ''}">
        </div>

        <hr>

        <div class="mb-3">
            <label class="form-label fw-bold text-danger">Текущий пароль</label>
            <input type="password" class="form-control" name="currentPassword" placeholder="Введите текущий пароль для смены">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold text-danger">Новый пароль</label>
            <input type="password" class="form-control" name="newPassword" placeholder="Новый пароль">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold text-danger">Подтвердите новый пароль</label>
            <input type="password" class="form-control" name="confirmPassword" placeholder="Подтверждение пароля">
        </div>

        <button type="submit" class="btn-main">Сохранить изменения</button>
    </form>
</div>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

</body>
</html>
