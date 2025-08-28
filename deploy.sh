#!/bin/bash

# Nombre de la imagen y versión
IMAGE_NAME="ms-clients"
IMAGE_VERSION="latest"

IMAGE_NAME_2="bank-transactions"
IMAGE_VERSION_2="latest"

echo "Construyendo el servicio bank-clients"
./gradlew clean build -x test

if [ $? -ne 0 ]; then
    echo "Error"
    exit 1
fi

echo "Construyendo la imagen Docker: $IMAGE_NAME:$IMAGE_VERSION"
docker build -t $IMAGE_NAME:$IMAGE_VERSION .

if [ $? -ne 0 ]; then
    echo "Error en la construcción de la imagen"
    exit 1
fi

echo "Construyendo el servicio bank-transactions"
cd ../bank-transactions
./gradlew clean build -x test

if [ $? -ne 0 ]; then
    echo "Error"
    exit 1
fi

echo "Construyendo la imagen Docker: $IMAGE_NAME_2:$IMAGE_VERSION_2"
docker build -t $IMAGE_NAME_2:$IMAGE_VERSION_2 .

if [ $? -ne 0 ]; then
    echo "Error en la construcción de la imagen"
    exit 1
fi

echo "Despliegue completado. La aplicación está corriendo."

docker-compose up