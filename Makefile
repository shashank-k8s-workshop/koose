
.DEFAULT_GOAL := build
.PHONY: gradlew build

PORT ?= 8082
GOOSE_URL ?= "http://host.docker.internal:8083"

start-local: gradlew
	SERVICE_GOOSE_URL=$(GOOSE_URL) SERVER_PORT=$(PORT) ./gradlew bootRun

build:
	docker build -t k8s-koose .

start:
	docker run -d -p $(PORT):$(PORT) --name=k8s-koose -e SERVICE_GOOSE_URL=$(GOOSE_URL) -e SERVER_PORT=${PORT} k8s-koose

stop:
	docker stop k8s-koose

remove:
	docker rm k8s-koose

logs:
	docker logs -f k8s-koose

cluster-up:
	docker-compose up -d

cluster-down:
	docker-compose down

cluster-start:
	docker-compose start

cluster-stop:
	docker-compose stop

cluster-logs:
	docker-compose logs -f koose goose

cluster-logs-koose:
	@docker-compose logs -f koose

cluster-logs-goose:
	@docker-compose logs -f goose