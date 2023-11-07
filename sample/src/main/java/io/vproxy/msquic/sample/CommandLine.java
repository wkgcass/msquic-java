package io.vproxy.msquic.sample;

import io.vproxy.msquic.MsQuicUpcall;
import io.vproxy.msquic.wrap.Configuration;
import io.vproxy.msquic.wrap.Connection;
import io.vproxy.msquic.wrap.Registration;
import io.vproxy.msquic.wrap.Stream;
import io.vproxy.pni.Allocator;
import io.vproxy.pni.PNIString;

import java.lang.foreign.MemorySegment;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static io.vproxy.msquic.MsQuicConsts.*;

public class CommandLine {
    private final boolean isClient;
    private final Registration registration;
    private final Configuration configuration;
    public final Path quicTlsSecretLogFilePath;
    private final List<Connection> connections = new ArrayList<>();
    private final List<Stream> streams = new ArrayList<>();
    private final List<ResumptionTicket> resumptionTickets = new ArrayList<>();

    private record ResumptionTicket(byte[] ticket, String remote) {
    }

    public CommandLine(boolean isClient, Registration registration, Configuration configuration, Path quicTlsSecretLogFilePath) {
        this.isClient = isClient;
        this.registration = registration;
        this.configuration = configuration;
        this.quicTlsSecretLogFilePath = quicTlsSecretLogFilePath;
    }

    public synchronized void registerConnection(Connection connection) {
        connections.add(connection);
        System.out.println("[" + (connections.size() - 1) + "] " + connection);
    }

    public synchronized void removeConnection(Connection connection) {
        connections.remove(connection);
    }

    public synchronized void registerStream(Stream stream) {
        streams.add(stream);
        System.out.println("[" + (streams.size() - 1) + "] " + stream);
    }

    public synchronized void removeStream(Stream stream) {
        streams.remove(stream);
    }

    public synchronized Connection getConnection(int index) {
        return connections.get(index);
    }

    public synchronized Stream getStream(int index) {
        return streams.get(index);
    }

    public synchronized void registerResumptionTicket(String remote, MemorySegment ticket) {
        var b = new byte[(int) ticket.byteSize()];
        MemorySegment.ofArray(b).copyFrom(ticket);
        var t = new ResumptionTicket(b, remote);
        resumptionTickets.add(t);
        System.out.println("[" + (resumptionTickets.size() - 1) + "] " + t);
    }

    public synchronized void removeResumptionTicket(int index) {
        resumptionTickets.remove(index);
    }

    private int printGradleWarn = 0;

    public void run() {
        System.out.println("Command line is ready, enter 'help' to show available commands");
        var scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            var line = scanner.nextLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equals("exit") || line.equals("quit")) {
                System.exit(0);
            } else if (line.endsWith("!")) {
                continue;
            }
            if (printGradleWarn % 5 == 0) {
                printGradleWarn = 0;
                System.out.println("Note: if you are running from Gradle, the output may have 1000 milliseconds delay, this is a JDK+Gradle bug");
            }
            ++printGradleWarn;
            try {
                handle(line);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private static final String HELP_STR = """
        exit|quit                                 terminate
        list <conn|stream|ticket>                 show current connections or streams
        new conn <host> <port>                    create a connection
                [resume=<ticket-idx>]
                [data=<0-rtt-data-string>]
        new stream <conn-idx>                     create a stream on the connection
                [immediate]
        send <stream-idx> <string>                send data through a stream
        shutdown <conn|stream> <idx> <code>       shutdown connection or stream
                [r|w]*
        fin <stream-idx>                          send fin through a stream
        close <conn|stream> <idx>                 close connection or stream
        sendResumptionTicket <conn-idx>           send resumption ticket via connection
        deleteResumptionTicket <ticket-idx>       remove a resumption ticket
                
        if the command ends with !, the command is ignored
        """;

    private void handle(String line) {
        var ls = Arrays.stream(line.split(" ")).filter(s -> !s.isBlank()).map(String::trim).toList();
        var cmd = ls.get(0);
        ls = ls.subList(1, ls.size());
        switch (cmd) {
            case "help", "?", "/help", "/?" -> System.out.println(HELP_STR);
            case "list" -> {
                if (ls.size() != 1 || !List.of("conn", "stream", "ticket").contains(ls.get(0))) {
                    throw new RuntimeException("invalid arguments");
                }
                if (ls.get(0).equals("conn")) {
                    listConnections();
                } else if (ls.get(0).equals("stream")) {
                    listStreams();
                } else {
                    assert ls.get(0).equals("ticket");
                    listTickets();
                }
            }
            case "new" -> {
                if (ls.isEmpty() || !List.of("conn", "stream").contains(ls.get(0))) {
                    throw new RuntimeException("invalid arguments");
                }
                if (ls.get(0).equals("conn")) {
                    if (!isClient) {
                        throw new RuntimeException("not client");
                    }
                    if (ls.size() < 3) {
                        throw new RuntimeException("invalid arguments");
                    }
                    ResumptionTicket ticket = null;
                    StringBuilder zrttData = null;
                    int state = 0; // 0:normal, 1:data
                    for (int i = 3; i < ls.size(); i++) {
                        var arg = ls.get(i);
                        if (arg.startsWith("resume=")) {
                            var n = Integer.parseInt(arg.substring("resume=".length()).trim());
                            ticket = resumptionTickets.get(n);
                        } else if (arg.startsWith("data=")) {
                            var s = arg.substring("data=".length());
                            zrttData = new StringBuilder(s);
                            state = 1;
                        } else {
                            if (state == 0) {
                                throw new RuntimeException("unknown arg " + arg);
                            }
                            zrttData.append(" ").append(arg);
                        }
                    }
                    var host = ls.get(1);
                    var port = Integer.parseInt(ls.get(2));
                    newConn(host, port, ticket, (zrttData == null ? null : zrttData.toString()));
                } else {
                    if (ls.size() < 2) {
                        throw new RuntimeException("invalid arguments");
                    }
                    boolean immediate = false;
                    for (int i = 2; i < ls.size(); i++) {
                        var arg = ls.get(i);
                        if (arg.equals("immediate")) {
                            immediate = true;
                        } else {
                            throw new RuntimeException("unknown arg " + arg);
                        }
                    }
                    int index = Integer.parseInt(ls.get(1));
                    assert ls.get(0).equals("stream");
                    newStream(getConnection(index), immediate, false);
                }
            }
            case "send" -> {
                if (ls.size() < 2) {
                    throw new RuntimeException("invalid arguments");
                }
                int streamIndex = Integer.parseInt(ls.get(0));
                var str = String.join(" ", ls.subList(1, ls.size()));
                var stream = getStream(streamIndex);
                var allocator = Allocator.ofUnsafe();
                var pStr = new PNIString(allocator, str);
                stream.send(allocator, pStr.MEMORY
                    // remove the last '\0'
                    .reinterpret(pStr.MEMORY.byteSize() - 1));
            }
            case "fin" -> {
                if (ls.size() != 1) {
                    throw new RuntimeException("invalid arguments");
                }
                int streamIndex = Integer.parseInt(ls.get(0));
                var stream = getStream(streamIndex);
                stream.sendFin();
            }
            case "close" -> {
                if (ls.size() != 2 || !List.of("conn", "stream").contains(ls.get(0))) {
                    throw new RuntimeException("invalid arguments");
                }
                int idx = Integer.parseInt(ls.get(1));
                if (ls.get(0).equals("conn")) {
                    closeConn(getConnection(idx));
                } else {
                    closeStream(getStream(idx));
                }
            }
            case "shutdown" -> {
                if (ls.size() < 3 || !List.of("conn", "stream").contains(ls.get(0))) {
                    throw new RuntimeException("invalid arguments");
                }
                int idx = Integer.parseInt(ls.get(1));
                long errorCode = Long.parseLong(ls.get(2));

                boolean r = false;
                boolean w = false;
                for (int i = 3; i < ls.size(); i++) {
                    var arg = ls.get(i);
                    if (arg.equals("r")) {
                        r = true;
                    } else if (arg.equals("w")) {
                        w = true;
                    } else {
                        throw new RuntimeException("unknown arg " + arg);
                    }
                }

                if (ls.get(0).equals("conn")) {
                    shutdownConn(getConnection(idx), errorCode);
                } else {
                    shutdownStream(getStream(idx), errorCode, r, w);
                }
            }
            case "sendResumptionTicket" -> {
                if (isClient) {
                    throw new RuntimeException("not server");
                }
                if (ls.size() != 1) {
                    throw new RuntimeException("invalid arguments");
                }
                int idx = Integer.parseInt(ls.get(0));
                sendResumptionTicket(getConnection(idx));
            }
            case "deleteResumptionTicket" -> {
                if (!isClient) {
                    throw new RuntimeException("not client");
                }
                if (ls.size() != 1) {
                    throw new RuntimeException("invalid arguments");
                }
                int idx = Integer.parseInt(ls.get(0));
                deleteResumptionTicket(idx);
            }
            default -> throw new RuntimeException("unknown command: " + cmd + " " + ls);
        }
    }

    private void newConn(String host, int port, ResumptionTicket ticket, String zrttData) {
        var allocator = Allocator.ofUnsafe();
        var conn = new SampleConnection(this, new Connection.Options(registration, allocator, ref ->
            registration.opts.registrationQ.openConnection(MsQuicUpcall.connectionCallback, ref.MEMORY, null, allocator)));
        if (conn.connectionQ == null) {
            conn.close();
            throw new RuntimeException("ConnectionOpen failed");
        }
        var err = conn.enableTlsSecretDebug();
        if (err != 0) {
            System.out.println("failed to set QuicTlsSecret for debugging");
        }
        if (ticket != null) {
            var t = allocator.allocate(ticket.ticket.length);
            t.copyFrom(MemorySegment.ofArray(ticket.ticket));
            err = conn.opts.apiTableQ.setParam(conn.connectionQ.getConn(), QUIC_PARAM_CONN_RESUMPTION_TICKET,
                ticket.ticket.length, t);
            if (err != 0) {
                conn.close();
                throw new RuntimeException("failed to set resumption ticket");
            }
        }
        if (zrttData != null) {
            var stream = newStream(conn, false, true);
            var alloc = Allocator.ofUnsafe();
            var str = new PNIString(alloc, zrttData);
            stream.send(QUIC_SEND_FLAG_ALLOW_0_RTT, alloc,
                str.MEMORY.reinterpret(str.MEMORY.byteSize() - 1));
        }
        var host_ = new PNIString(allocator, host);
        err = conn.connectionQ.start(configuration.opts.configurationQ, QUIC_ADDRESS_FAMILY_INET, host_, port);
        if (err != 0) {
            conn.close();
            throw new RuntimeException("ConnectionStart failed");
        }
    }

    private Stream newStream(Connection conn, boolean immediate, boolean zrtt) {
        var allocator = Allocator.ofUnsafe();
        var stream = new SampleStream(this, new Stream.Options(conn, allocator, ref ->
            conn.connectionQ.openStream(QUIC_STREAM_OPEN_FLAG_NONE, MsQuicUpcall.streamCallback, ref.MEMORY, null, allocator)));
        if (stream.streamQ == null) {
            stream.close();
            throw new RuntimeException("StreamOpen failed");
        }
        int flags = QUIC_STREAM_OPEN_FLAG_NONE;
        if (immediate) {
            flags |= QUIC_STREAM_START_FLAG_IMMEDIATE;
        }
        if (zrtt) {
            flags |= QUIC_STREAM_OPEN_FLAG_0_RTT;
        }
        var err = stream.streamQ.start(flags);
        if (err != 0) {
            stream.close();
            throw new RuntimeException("StreamStart failed");
        }
        return stream;
    }

    private synchronized void listConnections() {
        if (connections.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int i = 0; i < connections.size(); i++) {
            var conn = connections.get(i);
            System.out.println("[" + i + "] " + conn);
        }
    }

    private synchronized void listStreams() {
        if (streams.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int i = 0; i < streams.size(); ++i) {
            var stream = streams.get(i);
            System.out.println("[" + i + "] " + stream);
        }
    }

    private void listTickets() {
        if (resumptionTickets.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int i = 0; i < resumptionTickets.size(); ++i) {
            var ticket = resumptionTickets.get(i);
            System.out.println("[" + i + "] " + ticket);
        }
    }

    private void closeConn(Connection conn) {
        conn.closeConnection();
    }

    private void closeStream(Stream stream) {
        stream.closeStream();
    }

    private void shutdownConn(Connection connection, long errorCode) {
        connection.connectionQ.shutdown(QUIC_CONNECTION_SHUTDOWN_FLAG_NONE, errorCode);
    }

    private void shutdownStream(Stream stream, long errorCode, boolean r, boolean w) {
        int flags = QUIC_STREAM_SHUTDOWN_FLAG_NONE;
        if (r) {
            flags |= QUIC_STREAM_SHUTDOWN_FLAG_ABORT_RECEIVE;
        }
        if (w) {
            flags |= QUIC_STREAM_SHUTDOWN_FLAG_ABORT_SEND;
        }
        stream.streamQ.shutdown(flags, errorCode);
    }

    private void sendResumptionTicket(Connection connection) {
        int err = connection.connectionQ.sendResumptionTicket(
            QUIC_SEND_RESUMPTION_FLAG_NONE,
            0, null);
        if (err != 0) {
            throw new RuntimeException("ConnectionSendResumptionTicket failed");
        }
    }

    private void deleteResumptionTicket(int idx) {
        removeResumptionTicket(idx);
    }
}
