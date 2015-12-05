package cn.appkf.game2048;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import cn.appkf.game2048.res.Res;
import cn.appkf.game2048.screen.GameScreen;

/**
 * 游戏主程序入口类
 *
 * @author xietansheng
 */
public class MainGame extends Game {

    public static final String TAG = "Game2048";

    /** 世界宽度 */
    private float worldWidth;
    /** 世界高度 */
    private float worldHeight;

    /** 资源管理器 */
    private AssetManager assetManager;

    /** 纹理图集 */
    private TextureAtlas atlas;

    /** 位图字体 */
    private BitmapFont bitmapFont;

    /** 主游戏场景 */
    private GameScreen gameScreen;

    @Override
    public void create() {
        // 设置 log 输出级别
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // 为了不压扁或拉长图片, 按实际屏幕比例计算世界宽高
        worldWidth = Res.FIX_WORLD_WIDTH;
        worldHeight = Gdx.graphics.getHeight() * worldWidth / Gdx.graphics.getWidth();

        Gdx.app.log(TAG, "World Size: " + worldWidth + " * " + worldHeight);

        // 创建资源管理器
        assetManager = new AssetManager();

        // 加载资源
        assetManager.load(Res.ATLAS_PATH, TextureAtlas.class);
        assetManager.load(Res.BITMAP_FONT_PATH, BitmapFont.class);
        assetManager.load(Res.Audios.MOVE, Sound.class);
        assetManager.load(Res.Audios.MERGE, Sound.class);

        // 等待资源加载完毕
        assetManager.finishLoading();

        // 获取资源
        atlas = assetManager.get(Res.ATLAS_PATH, TextureAtlas.class);
        bitmapFont = assetManager.get(Res.BITMAP_FONT_PATH, BitmapFont.class);

        // 创建主游戏场景
        gameScreen = new GameScreen(this);

        // 设置当前场景
        setScreen(gameScreen);

        // 捕获返回键, 手动处理应用的退出（防止“弹出”帮助界面或对话框时按返回键退出应用）
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        // 应用退出时, 需要手动销毁场景
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        // 应用退出时释放资源
        if (assetManager != null) {
            assetManager.dispose();
        }
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }
    
    public AssetManager getAssetManager() {
		return assetManager;
	}

	public TextureAtlas getAtlas() {
        return atlas;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

}
















