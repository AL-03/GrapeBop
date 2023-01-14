/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to classes written by Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 * Note: This was a class created in addition to what was shown in the playlist.
 */

package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GrapeBop;

public class EndState extends State{
    private static final int OFFSET_Y = 100;  //offsets gameOver and menuBtn's y-position
    private Texture background;
    private Texture gameOver;
    private Texture menuBtn;
    private Rectangle btnBounds;
    private int score;
    private String scoreStr;
    private BitmapFont scoreDisplay;

    protected EndState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, GrapeBop.WIDTH/2, GrapeBop.HEIGHT/2);
        background = new Texture("bg.png");
        gameOver = new Texture("gameover.png");
        menuBtn = new Texture("menuBtn.png");
        btnBounds = new Rectangle(cam.position.x - menuBtn.getWidth()/2, cam.position.y - OFFSET_Y, menuBtn.getWidth(), menuBtn.getHeight());
        score = State.getScore();
        scoreStr = "Score: " + String.valueOf(score);
        scoreDisplay = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (btnBounds.contains(touch.x, touch.y)) {
                System.out.println("Menu Button touched!");
                gsm.set(new MenuState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();  //always checking input to see if users have done anything
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameOver, cam.position.x - gameOver.getWidth()/2, cam.position.y + OFFSET_Y);
        sb.draw(menuBtn, cam.position.x - menuBtn.getWidth()/2, cam.position.y - OFFSET_Y);
        scoreDisplay.setColor(0.0f, 0.0f, 0.0f, 1.0f);  //changes text colour to black
        scoreDisplay.draw(sb, scoreStr, cam.position.x - 30, cam.position.y);  //displays score
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameOver.dispose();
        menuBtn.dispose();
        scoreDisplay.dispose();
        System.out.println("End State Disposed");
    }
}
