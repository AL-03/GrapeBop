/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 */

package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;  //TextureRegion is all the frames combined in one image
    private float maxFrameTime;  //how long we're telling a frame to stay in view before switching to the next frame
    private float currentFrameTime;  //how long the animation has been in the current frame
    private int frameCount;  //number of frames in the animation
    private int frame;  //current frame

    public Animation(TextureRegion region, int frameCount, float cycleTime) {  //cycleTime is how long it'll take to cycle through the entire animation
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;  //how long the current frame has been in view
        if (currentFrameTime >= maxFrameTime) {  //move on to the next frame once currentFrameTime reaches maxFrameTime
            frame++;
            frame = frame % frameCount;  //if frame == frameCount, frame will be reset to 0
            currentFrameTime = 0;
        }
    }

    public TextureRegion getFrame() {  //gets the current frame
        return frames.get(frame);
    }

}
