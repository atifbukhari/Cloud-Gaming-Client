package com.limelight.demo;

import android.content.Context;
import android.net.Uri;

import com.limelight.LimeLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Demo-only local LAN bridge. The TV serves a small controller page to a phone
 * on the same network and forwards button press/release events to the active
 * Game activity. It does not expose shell, filesystem, or arbitrary commands.
 */
public final class DemoControllerServer {
    public interface InputListener {
        void onInput(String control, boolean pressed);
    }

    private static final int PORT = 47998;
    private static final Object LOCK = new Object();
    private static DemoControllerServer instance;
    private static volatile InputListener inputListener;

    private final Context context;
    private final String token;
    private ServerSocket socket;

    private DemoControllerServer(Context context) {
        this.context = context.getApplicationContext();
        this.token = UUID.randomUUID().toString().replace("-", "");
    }

    public static DemoControllerServer ensureStarted(Context context) {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new DemoControllerServer(context);
                instance.start();
            }
            return instance;
        }
    }

    public static void setInputListener(InputListener listener) {
        inputListener = listener;
    }

    public String getPairingUrl() {
        String ip = localIpv4();
        return ip == null ? null : "http://" + ip + ":" + PORT + "/?t=" + token;
    }

    private void start() {
        new Thread(() -> {
            try {
                socket = new ServerSocket(PORT);
                while (!socket.isClosed()) {
                    Socket client = socket.accept();
                    new Thread(() -> handle(client), "DemoControllerClient").start();
                }
            } catch (IOException e) {
                LimeLog.warning("Phone controller unavailable: " + e.getMessage());
            }
        }, "DemoControllerServer").start();
    }

    private void handle(Socket client) {
        try (Socket s = client;
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8))) {

            String first = in.readLine();
            if (first == null) return;
            String[] request = first.split(" ");
            if (request.length < 2) {
                reply(out, 400, "text/plain", "Bad request");
                return;
            }

            int length = 0;
            String header;
            while ((header = in.readLine()) != null && !header.isEmpty()) {
                int colon = header.indexOf(':');
                if (colon > 0 && header.substring(0, colon).trim().equalsIgnoreCase("Content-Length")) {
                    try { length = Integer.parseInt(header.substring(colon + 1).trim()); }
                    catch (NumberFormatException ignored) {}
                }
            }

            StringBuilder body = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int c = in.read();
                if (c < 0) break;
                body.append((char)c);
            }

            String method = request[0];
            String target = request[1];
            if (!token.equals(parse(target).get("t"))) {
                reply(out, 403, "text/plain", "Pairing token rejected");
                return;
            }

            if ("GET".equals(method)) {
                DemoTelemetry.event(context, "phone_controller_opened", "local_lan");
                reply(out, 200, "text/html; charset=utf-8", html());
            }
            else if ("POST".equals(method) && target.startsWith("/input")) {
                Map<String, String> form = parse(body.toString());
                InputListener listener = inputListener;
                String control = form.get("control");
                if (listener != null && control != null) {
                    listener.onInput(control, "1".equals(form.get("pressed")));
                }
                reply(out, 204, "text/plain", "");
            }
            else {
                reply(out, 404, "text/plain", "Not found");
            }
        } catch (IOException e) {
            LimeLog.warning("Phone controller request failed: " + e.getMessage());
        }
    }

    private Map<String, String> parse(String source) {
        int q = source.indexOf('?');
        if (q >= 0) source = source.substring(q + 1);
        Map<String, String> values = new HashMap<>();
        for (String pair : source.split("&")) {
            int eq = pair.indexOf('=');
            if (eq > 0) {
                values.put(Uri.decode(pair.substring(0, eq)), Uri.decode(pair.substring(eq + 1)));
            }
        }
        return values;
    }

    private void reply(BufferedWriter out, int code, String type, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        out.write("HTTP/1.1 " + code + " " + (code == 200 ? "OK" : code == 204 ? "No Content" : "Error") + "\r\n");
        out.write("Content-Type: " + type + "\r\n");
        out.write("Content-Length: " + bytes.length + "\r\n");
        out.write("Cache-Control: no-store\r\nConnection: close\r\n\r\n");
        out.write(body);
        out.flush();
    }

    private String localIpv4() {
        try {
            for (NetworkInterface n : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!n.isUp() || n.isLoopback()) continue;
                for (InetAddress a : Collections.list(n.getInetAddresses())) {
                    if (a instanceof Inet4Address && a.isSiteLocalAddress()) return a.getHostAddress();
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    private String html() {
        return "<!doctype html><html><head><meta name='viewport' content='width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no'>" +
                "<style>body{margin:0;background:#080d18;color:#fff;font-family:system-ui;text-align:center;touch-action:none}h2{margin:24px 0 6px}" +
                ".sub{color:#9fb0ca;margin-bottom:20px}.pad{display:grid;grid-template-columns:88px 88px 88px;gap:10px;justify-content:center}" +
                "button{height:82px;border:0;border-radius:22px;background:#17233a;color:#fff;font-size:28px;font-weight:700}" +
                "button.on{background:#2c69c9;transform:scale(.96)}.blank{visibility:hidden}.actions{display:flex;gap:14px;justify-content:center;margin-top:22px}" +
                ".actions button{width:100px}.start{width:150px!important;font-size:17px}</style></head><body>" +
                "<h2>Cloud Gaming Controller</h2><div class='sub'>Connected locally to your TV</div>" +
                "<div class='pad'><span class='blank'></span><button data-c='up'>▲</button><span class='blank'></span>" +
                "<button data-c='left'>◀</button><button data-c='down'>▼</button><button data-c='right'>▶</button></div>" +
                "<div class='actions'><button data-c='b'>B</button><button class='start' data-c='start'>START</button><button data-c='a'>A</button></div>" +
                "<script>const t=new URLSearchParams(location.search).get('t');function send(c,p){fetch('/input?t='+encodeURIComponent(t),{method:'POST'," +
                "headers:{'Content-Type':'application/x-www-form-urlencoded'},body:'control='+encodeURIComponent(c)+'&pressed='+(p?1:0)}).catch(()=>{});}" +
                "document.querySelectorAll('button[data-c]').forEach(b=>{const c=b.dataset.c;let d=false;const on=e=>{e.preventDefault();if(d)return;d=true;b.classList.add('on');send(c,true)};" +
                "const off=e=>{e.preventDefault();if(!d)return;d=false;b.classList.remove('on');send(c,false)};b.onpointerdown=on;b.onpointerup=off;b.onpointercancel=off;b.onpointerleave=off});</script></body></html>";
    }
}
