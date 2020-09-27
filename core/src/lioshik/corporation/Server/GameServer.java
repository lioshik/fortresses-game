package lioshik.corporation.Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import lioshik.corporation.LANscreen.LANMenuScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    public int port;
    public Server server;
    public int totalCount;
    public List<Connection> players;
    public LANMenuScreen screen;

    public void updateLabels(){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).sendTCP(new PlayersCountInfo(players.size(), i));
        }
        screen.setConnectedPlayersCount(players.size());
    }

    public GameServer(int port, final LANMenuScreen screen) {
        this.screen = screen;
        totalCount = 0;
        players = new ArrayList<>();
        this.port = port;
        server = new Server();
        server.start();
        try {
            server.bind(port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Kryo kryo = server.getKryo();
        kryo.register(PlayersCountInfo.class);
        server.addListener(new Listener() {
            @Override
            public void connected (Connection connection) {
                players.add(connection);
                updateLabels();
            }
            @Override
            public void disconnected (Connection connection) {
                players.remove(connection);
                updateLabels();
            }
        });
        System.out.println("Server started");
    }
}
