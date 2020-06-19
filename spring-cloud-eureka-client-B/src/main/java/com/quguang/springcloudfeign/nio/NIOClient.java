package com.quguang.springcloudfeign.nio;

/**
 * Created by 瞿广 on 2020/4/17 0017.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class NIOClient {

    private static CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
    private static CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new Worker().start();
        }
    }

    static class Worker extends Thread {

        @Override
        public void run() {
            SocketChannel channel = null;
            Selector selector = null;
            try {
                channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(new InetSocketAddress("localhost", 9000));

                selector = Selector.open();
                channel.register(selector, SelectionKey.OP_CONNECT);

                while(true){
                    selector.select();

                    Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
                    while(keysIterator.hasNext()){
                        SelectionKey key = (SelectionKey) keysIterator.next();
                        keysIterator.remove();

                        if(key.isConnectable()){
                            if(channel.isConnectionPending()){
                                if(channel.finishConnect()){
                                    key.interestOps(SelectionKey.OP_READ);
                                    channel.write(encoder.encode(CharBuffer.wrap("你好")));
                                }
                                else{
                                    key.cancel();
                                }
                            }
                        }
                        else if(key.isReadable()){
                            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                            channel.read(byteBuffer);
                            byteBuffer.flip();
                            CharBuffer charBuffer = decoder.decode(byteBuffer);
                            String response = charBuffer.toString();
                            System.out.println("[" + Thread.currentThread().getName()
                                    + "]收到响应：" + response);

                            channel.write(encoder.encode(CharBuffer.wrap("你好")));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(channel != null){
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(selector != null){
                    try {
                        selector.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}

