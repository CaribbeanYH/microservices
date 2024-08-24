package org.nio.sctpsc;
import com.sun.nio.sctp.*;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.sun.nio.sctp.AssociationChangeNotification.AssocChangeEvent.COMM_UP;

public class DaytimeClient {
    static int SERVER_PORT = 9999;
    static int US_STREAM = 0;
    static int FR_STREAM = 1;

    public static void main(String[] args) throws IOException {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost",
                SERVER_PORT);
        ByteBuffer buf = ByteBuffer.allocateDirect(60);
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charset.newDecoder();

        SctpChannel sc = SctpChannel.open(serverAddr, 0, 0);

        /* handler to keep track of association setup and termination */
        AssociationHandler assocHandler = new AssociationHandler();

        /* expect two messages and two notifications */
        MessageInfo messageInfo = null;
        do {
            messageInfo = sc.receive(buf, System.out, assocHandler);
            buf.flip();

            if (buf.remaining() > 0 &&
                    messageInfo.streamNumber() == US_STREAM) {

                System.out.println("(US) " + decoder.decode(buf).toString());
            } else if (buf.remaining() > 0 &&
                    messageInfo.streamNumber() == FR_STREAM) {

                System.out.println("(FR) " +  decoder.decode(buf).toString());
            }
            buf.clear();
        } while (messageInfo != null);

        sc.close();
    }

    static class AssociationHandler
            extends AbstractNotificationHandler<PrintStream>
    {
        public HandlerResult handleNotification(AssociationChangeNotification not,
                                                PrintStream stream) {
            if (not.event().equals(COMM_UP)) {
                int outbound = not.association().maxOutboundStreams();
                int inbound = not.association().maxInboundStreams();
                stream.printf("New association setup with %d outbound streams" +
                        ", and %d inbound streams.\n", outbound, inbound);
            }

            return HandlerResult.CONTINUE;
        }

        public HandlerResult handleNotification(ShutdownNotification not,
                                                PrintStream stream) {
            stream.printf("The association has been shutdown.\n");
            return HandlerResult.RETURN;
        }
    }
}
