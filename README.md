# MsQuic Java Binding

The original [README.md](https://github.com/wkgcass/msquic-java/blob/java/MSQUIC_README.md).

## Platform

Only Linux is supported.

## Build

### 1. build msquic

Please follow the instructions in [BUILD.md](https://github.com/wkgcass/msquic-java/blob/java/docs/BUILD.md).

Note that the library should be built with `-Config Release`:

```
./scripts/build.ps1 -Config Release
```

### 2. build msquicjava

```
cd java/src/main/c
./build.sh
```

## Run

### sample server

#### 1. sample server in java

```
cd java
./gradlew SampleServer
```

#### 2. test it with sample client in c

```
cd artifacts/bin/linux/x64_Release_openssl
./quicsample -client -target:127.0.0.1 -unsecure
```
