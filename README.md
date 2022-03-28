# humans-vs-zombies-backend

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

Noroff case: Humans vs. Zombies \
Backend component of the application \
Other components
- [Frontend](https://github.com/humans-vs-zombies/humans-vs-zombies-frontend)
- [Keycloak](https://github.com/humans-vs-zombies/humans-vs-zombies-keycloak)

Built using:
  - Java
  - Hibernate
  - Gradle
  - PostgreSQL
  - Docker
  - Docker compose
  - Keycloak
  - Swagger

Hosted on: [Heroku](https://humans-vs-zombies-backend.herokuapp.com/)

## Table of Contents

- [humans-vs-zombies-frontend](#humans-vs-zombies-frontend)
  - [Table of Contents](#table-of-contents)
  - [Install](#install)
  - [Usage](#usage)
  - [Maintainers](#maintainers)
  - [Contributing](#contributing)
  - [License](#license)

## Install

```
git clone https://github.com/humans-vs-zombies/humans-vs-zombies-backend.git
cd humans-vs-zombies-backend
```

## Usage

### Environment variables

```
POSTGRES_DB
POSTGRES_USER
POSTGRES_PASSWORD
POSTGRES_LOCAL_PORT
POSTGRES_DOCKER_PORT

SPRING_LOCAL_PORT
SPRING_DOCKER_PORT

KEYCLOAK_USER
KEYCLOAK_PASSWORD
KEYCLOAK_LOCAL_PORT
KEYCLOAK_DOCKER_PORT
KEYCLOAK_AUTH_SERVER_URL
KEYCLOAK_AUTH_SERVER_URL_LOCAL
KEYCLOAK_CREDENTIALS_SECRET

KEYCLOAK_POSTGRES_URL
KEYCLOAK_POSTGRES_DB
KEYCLOAK_POSTGRES_USER
KEYCLOAK_POSTGRES_PASSWORD

HIBERNATE_DDL_AUTO
```

```
docker-compose up (-d)
```

## Maintainers

[Jonas Bergkvist Melå @Pucko321](https://github.com/Pucko321) \
[Jonas Kristoffersen @jonaskris](https://github.com/jonaskris) \
[Jørgen Saanum @Jorgsaa](https://github.com/Jorgsaa)

## Contributing

PRs accepted.

Small note: If editing the README, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

MIT © 2022 Jonas Bergkvist Melå, Jonas Kristoffersen, Jørgen Saanum
