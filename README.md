# msquic-java

## prerequists

1. At least JDK 21
2. Follow the instructions [modify-gradle-compiler-args](https://github.com/vproxy-tools/modify-gradle-compiler-args) to install the java-agent for Gradle
3. Clone and compile the [modified msquic](https://github.com/wkgcass/msquic/blob/modified/README_MODIFIED.md)

## tasks

* `pniGenerate`: generate files
* `pniCompile`: compile the c shim lib
* `buildSampleNativeImage`: build native image

## scripts

You may add two scripts at the root of this project, for running the sample server and client.

**my-server.sh**

```shell
mode=""
task="runSampleServer"
GRAAL=`cat is-graal-build`
if [[ "$GRAAL" == "true" ]]
then
	mode="server"
	task="runSampleNativeImage"
fi

args="$mode cert=${PATH_TO_YOUR_TLS_CERT} key=${PATH_TO_YOUR_TLS_KEY}"

if [[ "$GRAAL" == "true" ]]
then
	./gradlew $task --console=plain -DARGS="$args"
else
	./gradlew $task --console=plain --args="$args"
fi
```

**my-client.sh**

```shell
mode=""
task="runSampleClient"
GRAAL=`cat is-graal-build`
if [[ "$GRAAL" == "true" ]]
then
	mode="client"
	task="runSampleNativeImage"
fi

args="$mode host=127.0.0.1 port=443"
SSLKEYLOGFILE="${PATH_TO_STORE_SSL_KEY_LOG_FILE}"

if [[ "$GRAAL" == "true" ]]
then
	SSLKEYLOGFILE="$SSLKEYLOGFILE" ./gradlew $task --console=plain -DARGS="$args"
else
	SSLKEYLOGFILE="$SSLKEYLOGFILE" ./gradlew $task --console=plain --args="$args"
fi
```

## graal

To compile the graal native-image version code, change content of file `./is-graal-build` to `true`.
