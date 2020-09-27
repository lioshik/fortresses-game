package lioshik.corporation.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lioshik.corporation.LANscreen.LANMenuScreen;
import lioshik.corporation.Server.PlayersCountInfo;

import java.io.IOException;

public class GameClient {
    public Client client;
    public LANMenuScreen screen;
    public int port;

    public GameClient(final LANMenuScreen screen, int port) {
        this.screen = screen;
        this.port = port;
        client = new Client();
        client.start();
        Kryo kryo = client.getKryo();
        kryo.register(PlayersCountInfo.class);
        try {
            client.connect(2000, client.discoverHost(port, 2000).getHostAddress(), port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof PlayersCountInfo) {
                    screen.setConnectedPlayersCount(((PlayersCountInfo)(object)).totalCount);
                }
            }
            public void disconnected (Connection connection) {
                if (screen.clientDialogStage != null) {
                    screen.clientDialogStage.remove();
                    screen.crState = LANMenuScreen.State.MAIN;
                }
            }
        });
        System.out.println("client started");
    }
}