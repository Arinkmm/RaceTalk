<#include "/templates/base.ftl">

<#macro title>RaceTalk — Редактировать заметку</#macro>

<#macro navbar>
    <nav class="navbar navbar-expand-md sticky-top shadow-sm px-3">
        <a class="navbar-brand" href="${contextPath}/main">RaceTalk</a>
        <div class="collapse navbar-collapse justify-content-end">
            <div class="navbar-nav">
                <a class="nav-link text-danger fw-bold d-flex align-items-center" href="${contextPath}/notes" role="button">
                    &#8592;
                    <span class="ms-2">Назад</span>
                </a>
            </div>
        </div>
    </nav>
</#macro>

<#macro content>
    <main class="container my-5">
        <h1 class="section-title mb-4">Редактировать заметку</h1>
        <form method="POST" action="${contextPath}/notes/edit/${note.id}" class="mb-5 p-4 border rounded shadow-sm bg-light">
            <div class="mb-3">
                <label class="form-label fw-bold">Заголовок</label>
                <input type="text" name="title" class="form-control" value="${note.title}" required />
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Содержание</label>
                <textarea name="content" class="form-control" rows="4">${note.content}</textarea>
            </div>

            <button type="submit" class="btn btn-main">Сохранить изменения</button>
        </form>
    </main>
</#macro>