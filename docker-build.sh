#!/usr/bin/env bash
echo " BUILDING DOCKER IMAGE WITH TAG :: "$1
docker build -t repo.citytech.global/remitpulse-countries:$1 .
echo " BUILDING DOCKER IMAGE DONE "
echo "==================================================="
echo " PUSHING DOCKER IMAGE TO repo.citytech.global :"
docker push repo.citytech.global/remitpulse-countries:$1
