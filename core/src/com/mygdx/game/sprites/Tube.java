/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 */

package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    private static final int FLUCTUATION = 130;  //range of tubes' vertical movement
    private static final int TUBE_GAP = 100;  //size of openings between the tubes
    private static final int LOWEST_OPENING = 120;  //lowest available opening for the top tube (such that the bottom tube won't go past the bottom of the screen)
    public static final int TUBE_WIDTH = 52;  //width of tube texture
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Rectangle boundsTop, boundsBot;
    private Random rand;

    public Tube(float x) {  //x denotes the coordinate on the x-axis where the tube starts
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);  //allows the tubes to move vertically between 0 and 130
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public int getTubeWidth() {
        return topTube.getWidth();
    }

    public static int getTubeGap() {
        return TUBE_GAP;
    }

    public void reposition(float x) {
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
