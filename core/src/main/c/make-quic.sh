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

C_GENERATED="c-generated"
if [[ "$GRAAL" == "true" ]]
then
    C_GENERATED="c-generated-graal"
    GCC_OPTS="$GCC_OPTS -DPNI_GRAAL=1"
fi

ls ../$C_GENERATED | grep '\.h$' | grep -v 'QuicRegistrationConfigEx' | \
	awk '{print "#include \""$1"\""}' > io_vproxy_msquic_MsQuic.c

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
	os="_WIN32"
	target="$target.dll"
	include_platform_dir="win32"
fi

rm -f "$target"

link="-lc -lpthread"
if [ "_WIN32" == "$os" ]
then
	link="-L $WINDIR/System32 -lucrt -lntdll"
fi

if [ "Linux" == "$os" ]
then
	link="$link -Wl,--no-as-needed"
fi
if [ "_WIN32" == "$os" ]
then
	link="$link -l:msquic.dll"
else
	link="$link -lmsquic"
fi
if [ "Linux" == "$os" ]
then
	link="$link -Wl,--as-needed"
fi

gcc -std=gnu11 -O2 \
    $GCC_OPTS \
    -I "$MSQUIC_INC" \
    -I "../$C_GENERATED" \
    -L "$MSQUIC_LD" \
    -DQUIC_ENABLE_CUSTOM_EVENT_LOOP=1 \
    $cflags \
    -shared -Werror $link -fPIC \
    io_vproxy_msquic_MsQuic.c \
    inline.c \
    ../$C_GENERATED/*.c \
    -o "$target"
