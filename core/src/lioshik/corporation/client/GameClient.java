package lioshik.corporation.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lioshik.corporation.LANscreen.LANMenuScreen;
import lioshik.corporation.Server.GameUpdateInfo;
import lioshik.corporation.Server.PlayersCountInfo;
import lioshik.corporation.Server.StartGameInfo;
import lioshik.corporation.gameScreen.Cell;
import lioshik.corporation.gameScreen.GameRulesController;
import lioshik.corporation.gameScreen.GameScreen;

import java.io.IOException;

public class GameClient {
    public Client client;
    public LANMenuScreen screen;
    public int port;
    public int playerIndex;
    public int totalIndex;
    public GameScreen gameScreen;
    public GameRulesController gameRulesController;
    public Game game;

    public GameClient(final LANMenuScreen screen, int port, Game game) {
        this.game = game;
        this.screen = screen;
        this.port = port;
        client = new Client();
        client.start();
        Kryo kryo = client.getKryo();
        kryo.register(PlayersCountInfo.class);
        kryo.register(StartGameInfo.class);
        kryo.register(GameUpdateInfo.class);
        kryo.register(Cell.ColorState[][].class);
        kryo.register(Cell.ColorState[].class);
        kryo.register(Cell.ColorState.class);
        try {
            client.connect(2000, client.discoverHost(port, 2000).getHostAddress(), port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof PlayersCountInfo) {
                    screen.setConnectedPlayersCount(((PlayersCountInfo)(object)).totalCount);
                    playerIndex = ((PlayersCountInfo)(object)).crPosition;
                    totalIndex = ((PlayersCountInfo)(object)).totalCount;
                } else if (object instanceof StartGameInfo) {
                    startGame();
                } else if (object instanceof GameUpdateInfo) {
                    importGame((GameUpdateInfo)(object));
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

    public void startGame() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                gameScreen = new GameScreen(totalIndex, 0, game);
                gameScreen.addPlayAgainButton = false;
                game.setScreen(gameScreen);
                gameRulesController = gameScreen.rulesController;
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                        gameScreen.uiStage.touchUp(screenX, screenY, pointer, button);
                        if (gameRulesController.whichTurn == playerIndex) {
                            gameScreen.inputAdapter.touchUp(screenX, screenY, pointer, button);
                            exportGame();
                            return true;
                        }
                        Vector3 touchVector = gameScreen.cam.unproject(new Vector3(screenX, screenY, 0));
                        screenX = (int) touchVector.x;
                        screenY = (int) touchVector.y;
                        if (gameScreen.field.anyCellTouched(screenX, screenY)) {
                            int[] cord = gameScreen.field.getTouchedCellCord(screenX, screenY);
                            gameScreen.field.cellArray.get(cord[0]).get(cord[1]).startShakeAnim();
                        }
                        gameScreen.field.resetClickedCell();
                        return true;
                    }

                    @Override
                    public boolean touchDragged(int screenX, int screenY, int pointer) {
                        gameScreen.uiStage.touchDragged(screenX, screenY, pointer);
                        if (gameRulesController.whichTurn == playerIndex) {
                            gameScreen.inputAdapter.touchDragged(screenX, screenY, pointer);
                            return true;
                        }
                        Vector3 touchVector = gameScreen.cam.unproject(new Vector3(screenX, screenY, 0));
                        screenX = (int) touchVector.x;
                        screenY = (int) touchVector.y;
                        if (gameScreen.field.anyCellTouched(screenX, screenY)) {
                            int[] cord = gameScreen.field.getTouchedCellCord(screenX, screenY);
                            gameScreen.field.clickCell(cord[0], cord[1]);
                        }
                        return true;
                    }

                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        gameScreen.uiStage.touchDown(screenX, screenY, pointer, button);
                        if (gameRulesController.whichTurn == playerIndex) {
                            gameScreen.inputAdapter.touchDown(screenX, screenY, pointer, button);
                            return true;
                        }
                        Vector3 touchVector = gameScreen.cam.unproject(new Vector3(screenX, screenY, 0));
                        screenX = (int) touchVector.x;
                        screenY = (int) touchVector.y;
                        if (gameScreen.field.anyCellTouched(screenX, screenY)) {
                            int[] cord = gameScreen.field.getTouchedCellCord(screenX, screenY);
                            gameScreen.field.clickCell(cord[0], cord[1]);
                        }
                        return true;
                    }
                });
            }
        });
    }

    public void exportGame() {
        client.sendTCP(new GameUpdateInfo(gameScreen.field, gameScreen.rulesController));
    }

    public void importGame(final GameUpdateInfo info) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (info.state[i][j] != gameScreen.field.cellArray.get(i).get(j).state) {
                            gameRulesController.cellTouched(i, j);
                        }
                    }
                }
            }
        });
    }
}