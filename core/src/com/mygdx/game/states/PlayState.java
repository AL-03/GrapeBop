/**
 * Project created as a result of the following playlist: https://youtube.com/playlist?list=PLZm85UZQLd2TPXpUJfDEdWTSgszionbJy
 * Code written with reference to Brent Aureli (playlist above) (Github: https://github.com/BrentAureli/FlappyDemo)
 * Name: Alice
 * Date Modified: 01/13/2023
 * Note: Certain parts changed from/created in addition to what had been made from the playlist.
 */

package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GrapeBop;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125; //the space between tubes (not including the tube itself)
    private static final int TUBE_COUNT = 4;  //total number of tubes in the game at any given time
    private static final int GROUND_Y_OFFSET = -50;  //shifts position of ground by -30 pixels (so it's not as high up)

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    //Used to implement score system (created in addition to what had been shown in the playlist)
    private int score;
    private String scoreStr;
    private BitmapFont scoreDisplay;
    private int scoreAreaPos;
    private ArrayList<Rectangle> scoreAreas;  //used to detect whether player gets a point or not

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, GrapeBop.WIDTH/2, GrapeBop.HEIGHT/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2((cam.position.x - cam.viewportWidth/2), GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(), GROUND_Y_OFFSET);
        score = 0;
        scoreStr = "Score: 0";
        scoreDisplay = new BitmapFont();  //allows the score to be displayed on the screen
        scoreAreaPos = 1;

        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        scoreAreas = new ArrayList<Rectangle>();
        scoreAreas.add(new Rectangle((TUBE_SPACING + Tube.TUBE_WIDTH), (tubes.get(1).getPosTopTube().y - Tube.getTubeGap()), tubes.get(1).getTubeWidth(), Tube.getTubeGap()));

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        Rectangle scoreArea = scoreAreas.get(0);

        for (int i = 0; i < TUBE_COUNT; i++) {
            Tube tube = tubes.get(i);

            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                scoreArea.setX(tubes.get((i + 1) % 4).getPosTopTube().x);  //we just want to scoreArea to be on the nearest tube
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {  //goes to EndState if bird hits a tube
                State.setScore(score);
                gsm.set(new EndState(gsm));
                break;
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {  //goes to EndState if bird hits the ground
            State.setScore(score);
            gsm.set(new EndState(gsm));
        }

        if (bird.getBounds().overlaps(scoreArea)) {  //used to generate score for the game
            scoreAreas.remove(0);
            score++;
            scoreStr = "Score: " + score;
            System.out.println("Score updated: " + String.valueOf(score));
            scoreAreaPos += 1;
            if (scoreAreaPos == 5) {
                scoreAreaPos = 1;
            }
            scoreAreas.add(new Rectangle(scoreAreaPos * (TUBE_SPACING + Tube.TUBE_WIDTH), (tubes.get(1).getPosTopTube().y - Tube.getTubeGap()), tubes.get(1).getTubeWidth(), Tube.getTubeGap()));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        //Adjusted spritebatch so it knows the coordinate system we're using in relation to the camera
        //(where it needs to draw things on the screen in relation to the camera)
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        scoreDisplay.setColor(1.0f, 1.0f, 1.0f, 1.0f);  //changes text colour to white
        scoreDisplay.draw(sb, scoreStr, cam.position.x + 20, cam.position.y + 150);  //displays score
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        scoreDisplay.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play State Disposed");
    }

    private void updateGround() {  //checks if camera is past x-position of ground, and adjusts the textures' positions so the ground looks continuous
        if (cam.position.x - (cam.viewportWidth/2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth/2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
