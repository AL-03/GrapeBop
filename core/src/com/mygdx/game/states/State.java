/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 * Note: Certain parts changed from/created in addition to what had been made from the playlist (specifically getScore and setScore).
 */

package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;  //a way to manage the states
    protected static int score;  //a way to compute score of player

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected static int getScore() {
        return score;
    }

    protected static void setScore(int newScore) {
        score = newScore;
    }

    protected abstract void handleInput();
    public abstract void update(float dt); //takes a delta time (difference between one frame rendered and the next frame rendered)
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
