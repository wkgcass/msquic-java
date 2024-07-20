.DEFAULT_GOAL := build

.PHONY: clean
clean:
	cd core/src/main/c && rm -rf *.so
	cd core/src/main/c && rm -rf *.dylib
	cd core/src/main/c && rm -rf *.dll

.PHONY: build
build: build-native

.PHONY: build-native
build-native:
	cd core/src/main/c && ./make-quic.sh
