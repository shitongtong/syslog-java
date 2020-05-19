package cn.stt.syslog;

import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.server.SyslogServer;
import org.graylog2.syslog4j.server.SyslogServerConfigIF;
import org.graylog2.syslog4j.server.SyslogServerEventIF;
import org.graylog2.syslog4j.server.SyslogServerIF;
import org.graylog2.syslog4j.server.SyslogServerSessionEventHandlerIF;

import java.net.SocketAddress;

/**
 * 接收端
 *
 * @Author shitt7
 * @Date 2020/5/19 8:41
 */
public class MySyslogReceive {
    //无意义
    private final Object lock = new Object();

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 514;

    private void receiveSyslogMessage() throws InterruptedException {
        SyslogServerIF server = SyslogServer.getInstance(SyslogConstants.UDP);
        SyslogServerConfigIF config = server.getConfig();
        config.setHost(HOST);
        config.setPort(PORT);
        config.addEventHandler(new SyslogServerSessionEventHandlerIF() {
            @Override
            public Object sessionOpened(SyslogServerIF syslogServerIF, SocketAddress socketAddress) {
                return null;
            }

            @Override
            public void event(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress,
                              SyslogServerEventIF syslogServerEventIF) {
                System.out.println("receive from:" + socketAddress + "\tmessage" + syslogServerEventIF.getMessage());
            }

            @Override
            public void exception(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress, Exception e) {

            }

            @Override
            public void sessionClosed(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress, boolean b) {

            }

            @Override
            public void initialize(SyslogServerIF syslogServerIF) {

            }

            @Override
            public void destroy(SyslogServerIF syslogServerIF) {

            }
        });
        SyslogServer.getThreadedInstance(SyslogConstants.UDP);
//        Thread.sleep(100000);
        synchronized (lock) {
            lock.wait();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MySyslogReceive mySyslogServer = new MySyslogReceive();
//        while (true){
        mySyslogServer.receiveSyslogMessage();
//        }
    }
}
