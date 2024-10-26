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

mozna jeszcze tak:
# Instalacja OpenJDK 17 i Maven
RUN apt-get update && apt-get install -y \
openjdk-17-jdk \
maven \
&& apt-get clean \
&& rm -rf /var/lib/apt/lists/*  # Clean up to reduce image size
