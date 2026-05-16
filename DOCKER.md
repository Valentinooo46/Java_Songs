# Docker Setup для Project1

## Вимоги
- Docker 20.10+
- Docker Compose 2.0+

## Запуск проекту з Docker

### Опція 1: Використання docker-compose (РЕКОМЕНДУЄТЬСЯ)

```bash
# Білд та запуск всіх сервісів (база даних + додаток)
docker-compose up --build

# Запуск без перебілду
docker-compose up

# Запуск у фоні (detached mode)
docker-compose up -d

# Зупинка сервісів
docker-compose down

# Перегляд логів
docker-compose logs -f app
docker-compose logs -f postgres
```

### Опція 2: Ручний білд та запуск

#### 1. Білд Docker образу
```bash
docker build -t project1:latest .
```

#### 2. Запуск PostgreSQL (якщо ви не використовуєте docker-compose)
```bash
docker run -d \
  --name project1-db \
  -e POSTGRES_USER=neondb_owner \
  -e POSTGRES_PASSWORD=npg_hk9M4PXbDBar \
  -e POSTGRES_DB=neondb \
  -p 5432:5432 \
  postgres:16-alpine
```

#### 3. Запуск додатку
```bash
docker run -d \
  --name project1-app \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://project1-db:5432/neondb \
  -e SPRING_DATASOURCE_USERNAME=neondb_owner \
  -e SPRING_DATASOURCE_PASSWORD=npg_hk9M4PXbDBar \
  -v $(pwd)/mp3Song:/app/mp3Song \
  -v $(pwd)/user-images:/app/user-images \
  --link project1-db \
  project1:latest
```

## Доступ до додатку

Після запуску додаток буде доступний за адресою:
- **Головна сторінка**: http://localhost:8080/
- **Список пісень**: http://localhost:8080/songs
- **Health check**: http://localhost:8080/hello

## Розташування каталогів

- **MP3 файли**: `/app/mp3Song` (монтується як volume)
- **Зображення користувачів**: `/app/user-images` (монтується як volume)

## Корисні команди

```bash
# Перегляд запущених контейнерів
docker-compose ps

# Входження в контейнер додатку
docker-compose exec app sh

# Перегляд логів додатку в реальному часі
docker-compose logs -f app

# Перебілд образу без кешу
docker-compose build --no-cache

# Видалення усіх контейнерів, мереж та volumes
docker-compose down -v

# Перевірка здоров'я сервісу
docker-compose ps

# Вивантаження образу на Docker Hub
docker tag project1:latest your-username/project1:latest
docker push your-username/project1:latest
```

## Розробка та Debug

```bash
# Запуск з інтерактивним режимом для debug
docker-compose run --rm app sh

# Білд без кешу для чистого білду
docker-compose build --no-cache --progress=plain
```

## Переваги Docker для цього проекту

✓ Ізоляція середовища розробки  
✓ Легкість розгортання на серверах  
✓ Управління залежностями (Java, PostgreSQL)  
✓ Масштабованість і надійність  
✓ Одна команда для запуску всіх сервісів  

## Вирішення проблем

### Порт 8080 вже зайнятий
```bash
# Змініть порт у docker-compose.yml
# "8080:8080" -> "8081:8080"
```

### База даних не підключується
```bash
# Перевірте що postgres контейнер запущений
docker-compose ps

# Перегляньте логи
docker-compose logs postgres
```

### Немає доступу до MP3 файлів
```bash
# Переконайтеся що папка mp3Song існує в проекті
mkdir -p mp3Song
```
