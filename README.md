'''html
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All in One Cheatsheet</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            color: #333;
            line-height: 1.6;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            text-align: center;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-top: 20px;
        }
        nav {
            display: flex;
            justify-content: space-between;
            background-color: #333;
            color: white;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        nav a {
            color: white;
            text-decoration: none;
            padding: 8px;
            margin: 0 10px;
        }
        nav a:hover {
            background-color: #4CAF50;
            border-radius: 4px;
        }
        h1, h2 {
            color: #4CAF50;
        }
        h2 {
            margin-top: 40px;
        }
        pre {
            background: #f4f4f4;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 8px;
        }
        footer {
            text-align: center;
            margin-top: 20px;
            padding: 10px;
            background-color: #333;
            color: white;
            border-radius: 8px;
        }
    </style>
</head>
<body>

<header>
    <h1>All in One Cheatsheet</h1>
</header>

<div class="container">
    <nav>
        <a href="#навигация-по-файловой-системе">Навигация по файловой системе</a>
        <a href="#работа-с-файлами-и-папками">Работа с файлами и папками</a>
        <a href="#git">Git</a>
        <a href="#scala-cheatsheet">Scala Cheatsheet</a>
    </nav>

    <h2 id="навигация-по-файловой-системе">Навигация по файловой системе</h2>
    <ul>
        <li><strong>pwd</strong>: Показывает текущую рабочую директорию.</li>
        <li><strong>ls</strong>: Показывает содержимое текущей директории.</li>
        <li><strong>ls -a</strong>: Показывает скрытые файлы и папки.</li>
        <li><strong>cd &lt;directory&gt;</strong>: Переходит в указанную директорию.</li>
        <li><strong>cd ..</strong>: Переходит на уровень выше.</li>
        <li><strong>cd ~</strong>: Переходит в домашнюю директорию.</li>
        <li><strong>cd /</strong>: Переходит в корневую директорию.</li>
    </ul>

    <h2 id="работа-с-файлами-и-папками">Работа с файлами и папками</h2>
    <h3 id="создание">Создание</h3>
    <ul>
        <li><strong>touch &lt;filename&gt;</strong>: Создает файл(ы).</li>
        <li><strong>mkdir &lt;directory&gt;</strong>: Создает новую папку.</li>
    </ul>

    <h3 id="копирование-и-перемещение">Копирование и перемещение</h3>
    <ul>
        <li><strong>cp &lt;source&gt; &lt;destination&gt;</strong>: Копирует файл(ы) или папку(и).</li>
        <li><strong>mv &lt;source&gt; &lt;destination&gt;</strong>: Перемещает файл(ы) или папку(и).</li>
    </ul>

    <h3 id="чтение">Чтение</h3>
    <ul>
        <li><strong>cat &lt;filename&gt;</strong>: Выводит содержимое файла.</li>
    </ul>

    <h3 id="удаление">Удаление</h3>
    <ul>
        <li><strong>rm &lt;filename&gt;</strong>: Удаляет файл(ы).</li>
        <li><strong>rmdir &lt;directory&gt;</strong>: Удаляет папку(и).</li>
        <li><strong>rm -r &lt;directory&gt;</strong>: Удаляет папку(и) и их содержимое рекурсивно.</li>
    </ul>

    <h2 id="git">Git</h2>
    <h3 id="основные-команды">Основные команды</h3>
    <ul>
        <li><strong>git init</strong>: Инициализирует Git репозиторий.</li>
        <li><strong>rm -rf .git</strong>: Удаляет Git репозиторий.</li>
        <li><strong>git status</strong>: Показывает состояние репозитория.</li>
        <li><strong>git add &lt;files&gt;</strong>: Добавляет файлы для отслеживания.</li>
        <li><strong>git commit -m "&lt;message&gt;"</strong>: Создает коммит с указанным сообщением.</li>
        <li><strong>git log</strong>: Показывает историю коммитов.</li>
    </ul>

    <h3 id="работа-с-удаленным-репозиторием">Работа с удаленным репозиторием</h3>
    <ul>
        <li><strong>git remote add &lt;name&gt; &lt;url&gt;</strong>: Связывает локальный репозиторий с удаленным.</li>
        <li><strong>git remote -v</strong>: Показывает связанные удаленные репозитории.</li>
        <li><strong>git push</strong>: Отправляет изменения на удаленный репозиторий.</li>
    </ul>

    <h3 id="дополнительные-команды-и-понятия">Дополнительные команды и понятия</h3>
    <ul>
        <li><strong>git restore --staged &lt;file&gt;</strong>: Убирает файл из staging area.</li>
        <li><strong>git commit --amend</strong>: Вносит изменения в последний коммит.</li>
        <li><strong>git reset --hard &lt;commit hash&gt;</strong>: Откатывает репозиторий к указанному коммиту.</li>
        <li><strong>git restore &lt;file&gt;</strong>: Отменяет изменения в файле, не добавленные в staging area.</li>
    </ul>

    <h3 id="ключевые-термины-git">Ключевые термины Git</h3>
    <ul>
        <li><strong>HEAD</strong>: Указывает на последний коммит в ветке.</li>
        <li><strong>untracked</strong>: Неотслеживаемые файлы.</li>
        <li><strong>staged</strong>: Файлы, добавленные в staging area.</li>
        <li><strong>tracked</strong>: Отслеживаемые файлы.</li>
        <li><strong>modified</strong>: Измененные файлы.</li>
    </ul>

    <h3 id="разрешение-конфликтов-версий">Разрешение конфликтов версий</h3>
    <ul>
        <li><strong>git fetch origin</strong>: Получает обновления из удаленного репозитория, не объединяя их.</li>
        <li><strong>git merge origin/main</strong>: Объединяет указанную ветку (основную) с текущей веткой. При наличии конфликтов их нужно решать вручную.</li>
        <li><strong>git add &lt;resolved-file&gt;</strong>: Добавляет разрешённые файлы в staging area.</li>
        <li><strong>git commit -m "Resolved merge conflicts"</strong>: Создает коммит с сообщением о разрешении конфликтов.</li>
        <li><strong>git push origin main</strong>: Отправляет локальные коммиты в удалённый репозиторий.</li>
    </ul>

    <h3 id="способы-разрешения-конфликтов">Способы разрешения конфликтов</h3>
    <ul>
        <li>Принять локальную версию:
            <ul>
                <li><strong>git checkout --ours &lt;имя файла&gt;</strong>: Принять локальную версию файла.</li>
                <li><strong>git merge --strategy-option ours</strong>: Принять локальную версию для всех конфликтующих файлов.</li>
            </ul>
        </li>
        <li>Принять удалённую версию:
            <ul>
                <li><strong>git checkout --theirs &lt;имя файла&gt;</strong>: Принять удалённую версию файла.</li>
                <li><strong>git merge --strategy-option theirs</strong>: Принять удалённую версию для всех конфликтующих файлов.</li>
            </ul>
        </li>
    </ul>

    <h3 id="работа-с-ветками">Работа с ветками</h3>
    <ul>
        <li><strong>git checkout [branch_name]</strong>: Переключение на указанную ветку.</li>
        <li><strong>git branch -m new-name</strong>: Переименование текущей ветки.</li>
        <li><strong>git branch -m old-name new-name</strong>: Переименование указанной ветки.</li>
        <li><strong>git push origin new-name</strong>: Отправка переименованной ветки в удаленный репозиторий.</li>
    </ul>

    <h3 id="прочее">Прочее</h3>
    <ul>
        <li><strong>Файлы SSH ключей</strong>:
            <ul>
                <li>Если папка <code>.ssh</code> пуста или отсутствует, ключи не создавались.</li>
                <li>Существующие ключи имеют следующие имена: <code>id_dsa.pub</code>, <code>id_ecdsa.pub</code>, <code>id_ed25519.pub</code>, <code>id_rsa.pub</code>.</li>
            </ul>
        </li>
        <li><strong>Ограничение длины сообщения коммита</strong>: Максимально 72 символа в одной строке.</li>
        <li><strong>Внесение изменений в последний коммит</strong>: <code>git commit --amend --no-edit</code>.</li>
    </ul>

    <h2 id="scala-cheatsheet">Scala Cheatsheet</h2>

    <h3>Общие команды</h3>
    <ul>
        <li><strong>scala</strong>: Запускает REPL Scala.</li>
        <li><strong>scalac</strong>: Компилирует Scala код в байт-код для JVM.</li>
        <li><strong>scala &lt;filename&gt;</strong>: Запускает Scala файл.</li>
        <li><strong>val</strong>: Создание неизменяемой переменной.</li>
        <li><strong>var</strong>: Создание изменяемой переменной.</li>
    </ul>

    <h3>Специфические команды</h3>
    <ul>
        <li><strong>def</strong>: Определяет функцию.</li>
        <li><strong>object</strong>: Определяет синглтон объект (singleton object).</li>
        <li><strong>class</strong>: Определяет класс.</li>
        <li><strong>case class</strong>: Определяет неизменяемый класс с дополнительными возможностями (например, сопоставление с образцом).</li>
        <li><strong>trait</strong>: Определяет интерфейс или абстрактный класс.</li>
        <li><strong>match</strong>: Используется для сопоставления с образцом, аналогично switch-case в других языках.</li>
        <li><strong>yield</strong>: Используется в for-композиторах для создания новых коллекций на основе существующих.</li>
    </ul>
</div>

<footer>
    <p>&copy; 2024 All in One Cheatsheet. Все права защищены.</p>
</footer>

</body>
</html>
\'''