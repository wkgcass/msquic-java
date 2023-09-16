#!/bin/bash

error=0

if [[ -z "$MSQUIC_INC" ]]; then
  echo "You must set MSQUIC_INC properly, where the msquic include files are located"
	error=1
fi

if [[ -z "$MSQUIC_LD" ]]; then
  echo "You must set MSQUIC_LD properly, where the msquic shared library is located"
  error=1
fi

if [[ "$error" == "1" ]]; then
  exit 1
fi

ls ../c-generated | grep '\.h$' | awk '{print "#include \""$1"\""}' > io_vproxy_msquic_MsQuic.c

echo "" >> io_vproxy_msquic_MsQuic.c
./gen-strerror.py >> io_vproxy_msquic_MsQuic.c

os=`uname`

target="msquic-java"
include_platform_dir=""
cflags=""

if [[ "Linux" == "$os" ]]
then
	target="lib$target.so"
	include_platform_dir="linux"
	cflags="-DCX_PLATFORM_LINUX=1"
elif [[ "Darwin" == "$os" ]]
then
	target="lib$target.dylib"
	include_platform_dir="darwin"
	cflags="-DCX_PLATFORM_DARWIN=1"
else
	echo "unsupported platform $os"
	exit 1
fi

rm -f "$target"

NO_AS_NEEDED=""
AS_NEEDED=""
if [[ "Linux" == "$os" ]]
then
	NO_AS_NEEDED="-Wl,--no-as-needed"
	AS_NEEDED="-Wl,--as-needed"
fi

gcc -std=gnu11 -O2 \
    $GCC_OPTS \
    -I ./dep/ae \
    -I "$MSQUIC_INC" \
    -I "../../../../misc" \
    -I "../c-generated" \
    -L "$MSQUIC_LD" \
    -DQUIC_ENABLE_CUSTOM_EVENT_LOOP=1 \
    $cflags \
    -shared -Werror -lc -lpthread $NO_AS_NEEDED "-lmsquic" $AS_NEEDED -fPIC \
    io_vproxy_msquic_MsQuic.c \
    inline.c \
    ../c-generated/io_vproxy_msquic_MsQuicUpcall.c \
    -o "$target"
