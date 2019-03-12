package com.devisv.javalin.demo00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Bootstrap {

    private static final int PORT = 9000;

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool(10);

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);

        while (true) {
            Socket socket = server.accept();

            forkJoinPool.submit(() -> {

                System.out.println("Start new connection");

                boolean keepAlive = false;

                try {
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(in));

                    do {
                        List<String> headers = new ArrayList<>();

                        while (true) {
                            String line = bf.readLine();

                            if (line.startsWith("Connection:")) {
                                keepAlive = line.substring(11)
                                                .trim()
                                                .equalsIgnoreCase("keep-alive");
                            }

                            if (line == null || line.trim().length() == 0) {
                                break;
                            }

                            headers.add(line);
                        }

                        System.out.println(headers);

                        String response;
                        String connection;

                        if (keepAlive) {
                            connection = "Connection: keep-alive\r\n";
                        } else {
                            connection = "Connection: close\r\n";
                        }

                        String[] options = headers.get(0).split(" ");

                        if (options[1].equalsIgnoreCase("/status")) {

                            String json = "{ \"status\": \"ok\" }";

                            response = "HTTP/1.1 200 OK\r\n" +
                                    "Server: Socket Server\r\n" +
                                    "Content-Type: application/json\r\n" +
                                    connection +
                                    "Content-Length: " + json.length() + "\r\n\r\n";

                            response += json;
                        } else {
                            String json = "{ \"status\": \"error\" }";

                            response = "HTTP/1.1 405 Method Not Allowed\r\n" +
                                    "Server: Socket Server\r\n" +
                                    "Content-Type: application/json\r\n" +
                                    connection +
                                    "Content-Length: " + json.length() + "\r\n\r\n";

                            response += json;
                        }

                        out.write(response.getBytes());
                        out.flush();

                    } while (keepAlive);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Connection is close");

                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

}
