package lioshik.corporation.gameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class TextureContainer {
    public static Texture titleTexture = new Texture(Gdx.files.internal("title_texture.png"));
    public static Texture buttonMenuUp = new Texture(Gdx.files.internal("button_home_default.png"));
    public static Texture buttonMenuDown = new Texture(Gdx.files.internal("button_home_pressed.png"));
    public static Texture buttonAgainUp = new Texture(Gdx.files.internal("button_again_default.png"));
    public static Texture buttonAgainDown = new Texture(Gdx.files.internal("button_again_pressed.png"));
    public static Texture buttonBackUp = new Texture(Gdx.files.internal("button_back_default.png"));
    public static Texture buttonBackDown = new Texture(Gdx.files.internal("button_back_pressed.png"));
    public static Texture buttonSinglePlayerUp = new Texture(Gdx.files.internal("button_one_player_default.png"));
    public static Texture buttonSinglePlayerDown = new Texture(Gdx.files.internal("button_one_player_pressed.png"));
    public static Texture buttonMultiPlayerUp = new Texture(Gdx.files.internal("button_multiplayer_default.png"));
    public static Texture buttonMultiPlayerDown = new Texture(Gdx.files.internal("button_multiplayer_pressed.png"));
    public static Texture buttonExitUp = new Texture(Gdx.files.internal("button_exit_default.png"));
    public static Texture buttonExitDown = new Texture(Gdx.files.internal("button_exit_pressed.png"));
    public static Texture buttonVs1Up = new Texture(Gdx.files.internal("button_vs1_default.png"));
    public static Texture buttonVs1Down = new Texture(Gdx.files.internal("button_vs1_pressed.png"));
    public static Texture buttonVs2Up = new Texture(Gdx.files.internal("button_vs2_default.png"));
    public static Texture buttonVs2Down = new Texture(Gdx.files.internal("button_vs2_pressed.png"));
    public static Texture buttonPlayers2Up = new Texture(Gdx.files.internal("button_players2_default.png"));
    public static Texture buttonPlayers2Down = new Texture(Gdx.files.internal("button_players2_pressed.png"));
    public static Texture buttonPlayers3Up = new Texture(Gdx.files.internal("button_players3_default.png"));
    public static Texture buttonPlayers3Down = new Texture(Gdx.files.internal("button_players3_pressed.png"));
    public static Texture cellEmpty = new Texture(Gdx.files.internal("texture_empty_cell.png"));
    public static Texture cellColor1 = new Texture(Gdx.files.internal("texture_color1_cell.png"));
    public static Texture cellColor2 = new Texture(Gdx.files.internal("texture_color2_cell.png"));
    public static Texture cellColor3 = new Texture(Gdx.files.internal("texture_color3_cell.png"));
    public static Texture cellColor1locked = new Texture(Gdx.files.internal("texture_color1_locked_cell.png"));
    public static Texture cellColor2locked = new Texture(Gdx.files.internal("texture_color2_locked_cell.png"));
    public static Texture cellColor3locked = new Texture(Gdx.files.internal("texture_color3_locked_cell.png"));
    public static final Color color1 = new Color(50 / 255f, 215 / 255f, 76 / 255f, 255 / 255f);
    public static final Color color1locked = new Color(40 / 255f, 150 / 255f, 68 / 255f, 255 / 255f);
    public static final Color color2 = new Color(255 / 255f, 204 / 255f, 0, 255);
    public static final Color color2locked = new Color(227 / 255f, 155 / 255f, 0, 255);
    public static final Color color3 = new Color(10 / 255f, 132 / 255f, 255 / 255f, 255 / 255f);
    public static final Color color3locked = new Color(21 / 255f, 110 / 255f, 193 / 255f, 255 / 255f);
    public static final Texture titleColor1lost = new Texture(Gdx.files.internal("title_green_lost.png"));
    public static final Texture titleColor2lost = new Texture(Gdx.files.internal("title_yellow_lost.png"));
    public static final Texture titleColor3lost = new Texture(Gdx.files.internal("title_blue_lost.png"));

}
