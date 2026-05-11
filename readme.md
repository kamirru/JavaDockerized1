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

1. Zbuduj i wypchnij obraz do DockerHuba:

docker build -t YOUR_DOCKERHUB_USERNAME/basicweb:08-ubuntu-build-user -f 08-Dockerfile-ubuntu-build-user .
docker push YOUR_DOCKERHUB_USERNAME/basicweb:08-ubuntu-build-user

2. Ustaw swój DockerHub username w pliku:

k8s/01-ubuntu-build/deployment.yaml

podmień:

docker.io/YOUR_DOCKERHUB_USERNAME/basicweb:08-ubuntu-build-user

3. Wdróż na Kubernetes:

kubectl apply -k k8s/01-ubuntu-build

4. Szybki test lokalny:

kubectl -n basicweb port-forward svc/basicweb 8080:80
curl http://127.0.0.1:8080/
