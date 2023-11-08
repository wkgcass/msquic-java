module io.vproxy.msquic {
    requires io.vproxy.pni;
    requires io.vproxy.pni.graal;
    requires io.vproxy.base;
    requires org.graalvm.word;
    requires org.graalvm.nativeimage;

    exports io.vproxy.msquic;
    exports io.vproxy.msquic.wrap;
    exports io.vproxy.msquic.callback;
}
