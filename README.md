# MsQuic Java Binding

The original [README.md](https://github.com/wkgcass/msquic-java/blob/java/MSQUIC_README.md).

## Platform

Only Linux is supported.

## Build

### 1. build msquic

Please follow the instructions in [BUILD.md](https://github.com/wkgcass/msquic-java/blob/java/docs/BUILD.md).

Note that the library should be built with `-Config Release`:

```shell
./scripts/build.ps1 -Config Release
```

### 2. build msquicjava

```shell
cd java/msquic-java/src/main/c
./build.sh
```

## Run

### sample server

#### 1. sample server in java

```shell
cd java
./gradlew SampleServer
```

#### 2. test it with sample client in c

```shell
cd artifacts/bin/linux/x64_Release_openssl
./quicsample -client -target:127.0.0.1 -unsecure
```

## sample client

#### 1. start the sample server in c

```shell
cd artifacts/bin/linux/x64_Release_openssl
./quicsample -server -cert_file:"$cert_file_path" -key_file:"$key_file_path"
```

#### 2. sample client in java

```shell
cd java
./gradlew SampleClient
```
