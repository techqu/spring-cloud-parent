package com.quguang.springcloudfeign.nio;

/**
 * Created by 瞿广 on 2020/4/17 0017.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class NIOServer {

    private static CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
    private static CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    private static ByteBuffer readBuffer;
    private static Selector selector;

    public static void main(String[] args) {
        init();
        listen();
    }

    private static void init(){
        readBuffer = ByteBuffer.allocate(1024);
        ServerSocketChannel servSocketChannel = null;

        try {
            servSocketChannel = ServerSocketChannel.open();
            servSocketChannel.configureBlocking(false);
            servSocketChannel.socket().bind(new InetSocketAddress(9000), 100);
            selector = Selector.open();
            servSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listen() {
        while(true){
            try{
                selector.select();
                Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();

                while(keysIterator.hasNext()){
                    SelectionKey key = (SelectionKey) keysIterator.next();
                    keysIterator.remove();
                    handleKey(key);
                }
            }
            catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

    private static void handleKey(SelectionKey key)
            throws IOException, ClosedChannelException {
        SocketChannel channel = null;

        try{
            if(key.isAcceptable()){
                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                channel = serverChannel.accept();
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            }
            else if(key.isReadable()){
                channel = (SocketChannel) key.channel();
                readBuffer.clear();
                int count = channel.read(readBuffer);

                if(count > 0){
                    readBuffer.flip();
                    CharBuffer charBuffer = decoder.decode(readBuffer);
                    String request = charBuffer.toString();
                    System.out.println(request);
                    String response = "";
                    channel.write(encoder.encode(CharBuffer.wrap(response)));
                }
                else{
                    channel.close();
                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
            if(channel != null){
                channel.close();
            }
        }
    }

}


