package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StartMenuScreen implements Screen {
    private final MyGdxGame game;
    private Stage stage;
    private OrthographicCamera camera;
    private Texture backgroundSky, backgroundGrass, cloud1, cloud2, tree, cart;
    private BitmapFont font;

    public StartMenuScreen(final MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load textures (replace with your own assets)
        backgroundSky = new Texture("sky.png");      // light blue
        backgroundGrass = new Texture("grass.png");  // green
        cloud1 = new Texture("cloud1.png");
        cloud2 = new Texture("cloud2.png");
        tree = new Texture("tree.png");
        cart = new Texture("cart.png");

        font = new BitmapFont(); // Default pixel-style font can be replaced
        font.getData().setScale(2f);

        createMenuUI();
    }

    private void createMenuUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        style.up = new Image(new Texture("button_up.png")).getDrawable();   // wood-like texture
        style.down = new Image(new Texture("button_down.png")).getDrawable();

        String[] buttons = {"PLAY", "CONTINUE", "OPTIONS", "CREDITS", "EXIT"};

        for (String text : buttons) {
            TextButton button = new TextButton(text, style);
            button.pad(10);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleButton(text);
                }
            });
            table.add(button).width(200).height(60).pad(10);
            table.row();
        }

        stage.addActor(table);
    }
   private void handleButton(String action) {
       switch (action) {
           case "PLAY":
               System.out.println("Starting new game...");
               game.setScreen(new GameScreen(game)); // Assuming you have a GameScreen class
               break;
           case "CONTINUE":
               System.out.println("Loading saved game...");
               // Implement loading logic here
               break;
           case "OPTIONS":
               System.out.println("Opening options...");
               // Implement options screen logic here
               break;
           case "CREDITS":
               System.out.println("Showing credits...");
               // Implement credits screen logic here
               break;
           case "EXIT":
               Gdx.app.exit();
               break;
       }
   }
   
@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1); // sky blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // Draw background manually
        game.batch.draw(backgroundSky, 0, 300, 800, 300);
        game.batch.draw(backgroundGrass, 0, 0, 800, 300);

        // Draw decorative objects
        game.batch.draw(tree, 50, 100, 80, 150);
        game.batch.draw(tree, 120, 120, 80, 150);
        game.batch.draw(cloud1, 200, 420, 100, 60);
        game.batch.draw(cloud2, 550, 460, 80, 50);
        game.batch.draw(cart, 650, 70, 100, 80);

        game.batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void dispose() {
        stage.dispose();
        backgroundSky.dispose();
        backgroundGrass.dispose();
        tree.dispose();
        cloud1.dispose();
        cloud2.dispose();
        cart.dispose();
        font.dispose();
    }
}
package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
    @Override
    public void create() {
        setScreen(new StartMenuScreen(this));
    }
}
