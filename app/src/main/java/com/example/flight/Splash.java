package com.example.flight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class Splash  implements Screen{

    private SpriteBatch batch;
    private StartGame myGame;
    private Texture texture;
    private OrthographicCamera camera;
    private long startTime;
    private int rendCount;

    public Splash(StartGame g) // ** constructor called initially **//
    {
        Gdx.app.log("my Spash Screen", "constructor called");
        myGame = g; // ** get Game parameter **//
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 512);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        rendCount++;
        if (TimeUtils.millis()>(startTime+5000)) myGame.setScreen(new GamePlay(myGame));

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.app.log("my Splash Screen", "show called");
        texture = new Texture(Gdx.files.internal("data/su27.png")); //** texture is now the splash image **//
        startTime = TimeUtils.millis();
    }

    @Override
    public void hide() {
        Gdx.app.log("my Splash Screen", "hide called");
        Gdx.app.log("my Splash Screen", "rendered " + rendCount + " times.");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        texture.dispose();
        batch.dispose();
    }

}