<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8" />
    <title>
        <#if .namespace.title??>
            <@title />
        <#else>
            RaceTalk
        </#if>
    </title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/style.css" rel="stylesheet" />
    <#if .namespace.additionalCss??>
        <@additionalCss />
    </#if>
</head>

<body>

<header>
    <#if .namespace.navbar??>
        <@navbar />
    </#if>
</header>

<#if .namespace.hero??>
    <@hero />
</#if>

<#if .namespace.linksSection??>
    <@linksSection />
</#if>

<main class="container my-5">
    <#if .namespace.content??>
        <@content />
    </#if>
</main>

<footer>
    &copy; 2025 RaceTalk. Все права защищены.
</footer>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<#if .namespace.additionalScripts??>
    <@additionalScripts />
</#if>

</body>

</html>