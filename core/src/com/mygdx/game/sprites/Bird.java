/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 */

package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int GRAVITY = -15;  //represents gravity in game world (only acting on bird bc that's how the game works)
    private static final int MOVEMENT = 100;  //horizontal movement
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture birdTexture;
    private Sound flap;

    public Bird(int x, int y) {  //x and y will be its starting position
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        birdTexture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(birdTexture), 3, 0.5f);
        bounds = new Rectangle(x, y, birdTexture.getWidth() / 3, birdTexture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt) {  //sends dt to bird class so it can reset its position in game world
        birdAnimation.update(dt);
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1/dt);  //reverses what was previously scaled
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public void jump() {
        velocity.y = 250;
        flap.play(0.5f);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        birdTexture.dispose();
        flap.dispose();
    }
}
