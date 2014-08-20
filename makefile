PREFIX=/usr/local
EXE_NAME=velocli

TARGET=target/velocli-jar-with-dependencies.jar

all:
	mvn clean package
install: $(TARGET) src/main/bash/velocli
	mkdir -p $(PREFIX)/bin
	mkdir -p $(PREFIX)/lib
	cp src/main/bash/velocli $(PREFIX)/bin/$(EXE_NAME)
	chmod +x $(PREFIX)/bin/$(EXE_NAME)
	cp $(TARGET) $(PREFIX)/lib/velocli-jar-with-dependencies.jar
clean:
	mvn clean
