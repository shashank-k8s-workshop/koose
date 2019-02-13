
.DEFAULT_GOAL := run

.PHONY: gradlew

run: gradlew
	./gradlew bootRun