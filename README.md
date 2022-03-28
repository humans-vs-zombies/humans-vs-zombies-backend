# humans-vs-zombies-frontend

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

Hosted on: [Heroku](https://humans-vs-zombies-frontend.herokuapp.com/)

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
POSTGRES_DB=postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_LOCAL_PORT=5432
POSTGRES_DOCKER_PORT=5432

SPRING_LOCAL_PORT=8080
SPRING_DOCKER_PORT=8080

KEYCLOAK_USER=admin
KEYCLOAK_PASSWORD=admin
KEYCLOAK_LOCAL_PORT=28080
KEYCLOAK_DOCKER_PORT=8080
KEYCLOAK_AUTH_SERVER_URL=https://humans-vs-zombies-keycloak.herokuapp.com/auth
KEYCLOAK_AUTH_SERVER_URL_LOCAL=http://localhost:28080/auth
KEYCLOAK_CREDENTIALS_SECRET=2LWk4YSqWt1TNqevGjUoncost1I1C5G5

KEYCLOAK_POSTGRES_URL=keycloak
KEYCLOAK_POSTGRES_DB=keycloak
KEYCLOAK_POSTGRES_USER=keycloak
KEYCLOAK_POSTGRES_PASSWORD=keycloak

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
