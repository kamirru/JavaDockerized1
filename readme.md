budowaie z customową nazwą pliku:

docker build -t basicweb1 -f 01-Dockerfile-ubuntu-build .
docker run -d -p 9001:8999 basicweb1

docker build -t basicweb2 -f 02-Dockerfile-ubuntu-build-multistage .
docker run -d -p 9002:8999 basicweb2

docker build -t basicweb3 -f 03-Dockerfile-ubuntu-local-jar .
docker run -d -p 9003:8999 basicweb3

docker build -t basicweb4 -f 04-Dockerfile-build-maven .
docker run -d -p 9004:8999 basicweb4

docker build -t basicweb5 -f 05-Dockerfile-jdk-copy-local .
docker run -d -p 9005:8999 basicweb5

docker build -t basicweb6 -f 06-Dockerfile-jdk-copy-local-distroless .
docker run -d -p 9006:8999 basicweb6
docker run --rm -p 9006:8999 basicweb6

docker build -t basicweb7 -f 07-Dockerfile-jdk-copy-local-distroless-user .
docker run -d -p 9007:8999 basicweb7
docker run --rm -p 9007:8999 basicweb7

docker build -t basicweb8 -f 08-Dockerfile-ubuntu-build-user .
docker run -d -p 9008:8999 basicweb8
docker run --rm -p 9008:8999 basicweb8

mozna jeszcze tak:
# Instalacja OpenJDK 17 i Maven
RUN apt-get update && apt-get install -y \
openjdk-17-jdk \
maven \
&& apt-get clean \
&& rm -rf /var/lib/apt/lists/*  # Clean up to reduce image size

Kubernetes (dla 08-Dockerfile-ubuntu-build-user):

Docker Hub - krok po kroku (po zbudowaniu image):
wszedzie zamiast YOUR_DOCKERHUB_USERNAME camil1985
1. Zaloguj się do Docker Hub z terminala:

docker login

2. (Jednorazowo) utwórz repozytorium `basicweb` na Docker Hub (przez UI).

3. Zataguj lokalny obraz w formacie Docker Huba:

docker tag basicweb8 camil1985/basicweb:08-ubuntu-build-user

4. Wypchnij obraz:

docker push camil1985/basicweb:08-ubuntu-build-user

5. Zweryfikuj, że tag jest widoczny:

docker pull camil1985/basicweb:08-ubuntu-build-user

Kubernetes:

1. Zbuduj i wypchnij obraz do DockerHuba (alternatywnie w jednym kroku):

docker build -t camil1985/basicweb:08-ubuntu-build-user -f 08-Dockerfile-ubuntu-build-user .
docker push camil1985/basicweb:08-ubuntu-build-user

2. Ustaw swój DockerHub username w pliku:

k8s/01-ubuntu-build/deployment.yaml

podmień:

docker.io/camil1985/basicweb:08-ubuntu-build-user

3. Wdróż na Kubernetes:

kubectl apply -k k8s/01-ubuntu-build
microk8s kubectl apply -k k8s/01-ubuntu-build

4. Szybki test lokalny:

kubectl -n basicweb port-forward svc/basicweb 8080:80
curl http://127.0.0.1:8080/

w microk8s cos nie dziala port forward
microk8s kubectl proxy --port=8080
http://127.0.0.1:8080/api/v1/namespaces/basicweb/services/http:basicweb:80/proxy/
