#!/bin/bash

if [[ -z "$JAVA_HOME" ]]
then
	echo "You need to set JAVA_HOME in env"
	exit 1
fi
#  main/src/java/msquic/
MSQUIC_INC="../../../../src/inc"
MSQUIC_LIB="../../../../artifacts/bin/linux/x64_Release_openssl"

os=`uname`
target="msquicjava"
if [[ "Linux" == "$os" ]]
then
	target="lib$target.so"
	include_platform_dir="linux"
elif [[ "Darwin" == "$os" ]]
then
	target="lib$target.dylib"
	include_platform_dir="darwin"
else
	echo "unsupported platform $os"
	exit 1
fi

rm -f "$target"

gcc -std=gnu99 \
    -I "$JAVA_HOME/include" \
    -I "$JAVA_HOME/include/$include_platform_dir" \
    -I "$MSQUIC_INC" \
    -L "$MSQUIC_LIB" -Wl,--no-as-needed,--whole-archive,-lmsquic,--no-whole-archive,--as-needed \
    -shared -Werror -lc -fPIC \
    -O2 \
    msquic_internal_Native.c msquic_internal_NativeValues.c \
    -o "$target"
