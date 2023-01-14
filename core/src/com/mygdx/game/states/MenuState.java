/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 * Note: Certain parts changed from what had been made from the playlist.
 */

package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GrapeBop;

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;
    private Rectangle btnBounds;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, GrapeBop.WIDTH/2, GrapeBop.HEIGHT/2);
        background = new Texture("menubg.png");
        playBtn = new Texture("playBtn.png");
        btnBounds = new Rectangle(cam.position.x - playBtn.getWidth()/2, cam.position.y, playBtn.getWidth(), playBtn.getHeight());
    }

    @Override
    public void handleInput() {  //changed from what was in the playlist in order to only change states if play button itself (not the whole screen) was pressed
        if (Gdx.input.justTouched()) {
            Vector3 touch = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (btnBounds.contains(touch.x, touch.y)) {
                System.out.println("Play Button touched!");
                gsm.set(new PlayState(gsm));
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
        sb.draw(playBtn, cam.position.x - playBtn.getWidth()/2, cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}
