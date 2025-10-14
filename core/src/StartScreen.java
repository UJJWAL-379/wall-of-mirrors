
package yourpackage;  // Replace with your actual package name, e.g., com.sandglitch.wallofmirrors

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class StartScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private YourMainGameClass game;  // Replace YourMainGameClass with your main game class name, e.g., MyGdxGame

    public StartScreen(YourMainGameClass game) {  // Pass the main game instance
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();  // Simple font for rendering text
    }

    @Override
    public void show() {
        // Set up input handling
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.NUM_1) {
                    // Start the game: Switch to your main game screen
                    game.setScreen(new YourGameScreen(game));  // Replace YourGameScreen with your actual game screen class
                    return true;
                } else if (keycode == Keys.NUM_2) {
                    // Exit the game
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Wall of Mirrors", 100, 300);  // Game title
        font.draw(batch, "1. Start Game", 100, 200);    // Start option
        font.draw(batch, "2. Exit", 100, 100);          // Exit option
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Optional: Handle window resizing
    }

    @Override
    public void pause() {
        // Optional: Handle pause
    }

    @Override
    public void resume() {
        // Optional: Handle resume
    }

    @Override
    public void hide() {
        // Clean up when screen is hidden
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
