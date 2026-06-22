# Инструкции по проверке работы приложения

## Запуск приложения

```bash
mvn spring-boot:run
```

После запуска появится приглашение `shell:>`

## Проверка работы по шагам

### 1. Посмотреть список авторов
```
shell:> author-list
```

### 2. Посмотреть список жанров
```
shell:> genre-list
```

### 3. Создать новую книгу
```
shell:> book-create -t 'Мастер и Маргарита' -a 5 -g 5
```

### 4. Проверить, что книга создалась
```
shell:> book-list
```

### 5. Получить книгу по ID
```
shell:> book-get 8
```

### 6. Обновить книгу
```
shell:> book-update 8 -t 'Мастер и Маргарита (новое издание)' -a 5 -g 5
```

### 7. Найти книги по автору
```
shell:> book-by-author 1
```

### 8. Удалить книгу
```
shell:> book-delete 8
```

## Доступные команды

**Книги (CRUD обязателен):**
- `book-list` - Список всех книг
- `book-get <id>` - Получить книгу по ID
- `book-create -t <title> -a <authorId> -g <genreId>` - Создать книгу
- `book-update <id> -t <title> -a <authorId> -g <genreId>` - Обновить книгу
- `book-delete <id>` - Удалить книгу
- `book-by-author <authorId>` - Книги автора
- `book-by-genre <genreId>` - Книги жанра

**Авторы:**
- `author-list` - Список авторов
- `author-get <id>` - Получить автора по ID
- `author-create -n <name>` - Создать автора
- `author-update <id> -n <name>` - Обновить автора
- `author-delete <id>` - Удалить автора

**Жанры:**
- `genre-list` - Список жанров
- `genre-get <id>` - Получить жанр по ID
- `genre-create -n <name>` - Создать жанр
- `genre-update <id> -n <name>` - Обновить жанр
- `genre-delete <id>` - Удалить жанр

## H2 Console (веб-интерфейс БД)

URL: http://localhost:8080/h2-console

- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **User**: `sa`
- **Password**: *(пустой)*

## Запуск тестов

```bash
mvn test
```
