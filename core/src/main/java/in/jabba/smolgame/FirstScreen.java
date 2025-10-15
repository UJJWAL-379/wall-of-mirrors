package in.jabba.smolgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3; 
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private SpriteBatch batch;
    private Texture playerTexture;
    private TextureRegion playerFrame;

    private Vector2 playerPosition;
    private float playerSpeed = 100f; // 100 pixels per second

    private TiledMapTileLayer collisionLayer;


    @Override
    public void show() {
        // Prepare your screen here.
        map= new TmxMapLoader().load("test2.tmx");
        mapRenderer=new OrthogonalTiledMapRenderer(map);

        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");

        playerTexture=new Texture("player.png");
        batch= new SpriteBatch();
        playerFrame = new TextureRegion(playerTexture, 0, 0, 16, 16);
        playerPosition = new Vector2(300, 220);

        camera = new OrthographicCamera();
        viewport= new FitViewport(320, 240, camera);
    }

     private void handleInput(float delta) {
        float oldX = playerPosition.x;
        float oldY = playerPosition.y;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerPosition.x -= playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerPosition.x += playerSpeed * delta;
        }
        if (isCellBlocked(playerPosition.x, playerPosition.y) || isCellBlocked(playerPosition.x, playerPosition.y + playerFrame.getRegionHeight() - 1)) {
            playerPosition.x = oldX;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerPosition.y += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerPosition.y -= playerSpeed * delta;
        }
        if (isCellBlocked(playerPosition.x, playerPosition.y) || isCellBlocked(playerPosition.x + playerFrame.getRegionWidth() - 1, playerPosition.y)) {
            playerPosition.y = oldY;
        }
    }

     private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
            (int) (x / collisionLayer.getTileWidth()), 
            (int) (y / collisionLayer.getTileHeight())
        );
        return cell != null && cell.getTile() != null;
    }


    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        ScreenUtils.clear(0,0,0,1);
        handleInput(delta);

        collisionLayer.setVisible(false);

        //camera updation
        camera.position.set(playerPosition.x,playerPosition.y,0);
        camera.update();
        mapRenderer.setView(camera);

        //rendering
        mapRenderer.render();

        //batchrender
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(playerFrame, playerPosition.x, playerPosition.y,32,32); 
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        viewport.update(width, height,true);
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        map.dispose();
        mapRenderer.dispose();
        playerTexture.dispose();
        batch.dispose();
    }
}