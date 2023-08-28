#!/usr/bin/env python3

import os

INC = os.environ.get("MSQUIC_INC")

f = open(os.path.join(INC, 'msquic_posix.h'))
content = f.read()
f.close()

lines = content.split('\n')
keys = []
for line in lines:
    line = line.strip()
    if not line.startswith("#define") or "QUIC_STATUS_" not in line:
        continue
    sp = line.split(' ')
    if len(sp) < 2:
        continue
    k = sp[1]
    if "(" in k:
        continue
    keys.append(k)

print ("JNIEXPORT char * JNICALL JavaCritical_io_vproxy_msquic_MsQuicValues_QuicStatusString(int32_t status) {")
print ("    switch (status) {")
for k in keys:
    print ("        case " + k + ": return \"" + k + "\";")
print ("        default: return \"UNKNOWN\";")
print ("    }")
print ("}")
