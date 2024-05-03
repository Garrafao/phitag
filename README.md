# PhiTag

## Introduction

PhiTag is a modular and extensible web application solution aimed at providing a simple and easy to use interface for different tagging and annotation tasks, finding new annotators, managing the annotation process and providing a platform for the evaluation of the results.

## Getting Started & Contributing

The main technology-stack of PhiTag is based on [Spring](http://spring.io/) and [Next.js](https://nextjs.org/). The application is built on top of [Spring](http://spring.io/) as a backend and [Next.js](https://nextjs.org/) as a frontend provider. For a detailed description of the architecture and the technologies used, please refer to the [wiki](https://github.com/confusedSerge/phitag/wiki) or the subdirectories README's `docker`, `backend` and `frontend`.
## Start PhiTag in docker container
To start the PhiTag application using Docker, first navigate to the Docker directory.
Use the following command:
```bash
docker-compose -f docker-compose.local.yml down --remove-orphans
```
or
```bash
podman-compose -f docker-compose.local.yml down --remove-orphans
```
## Running Version

A running version of PhiTag is available at [https://phitag.ims.uni-stuttgart.de/](https://phitag.ims.uni-stuttgart.de/). Please note, as this is a development version, it is not guaranteed to be stable and data might be lost at any time.

## License
Shield: [![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

This work is licensed under a
[Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

## Credits

