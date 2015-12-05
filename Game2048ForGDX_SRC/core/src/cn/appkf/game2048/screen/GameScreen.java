package cn.appkf.game2048.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.res.Res;
import cn.appkf.game2048.stage.ExitConfirmStage;
import cn.appkf.game2048.stage.GameOverStage;
import cn.appkf.game2048.stage.GameStage;
import cn.appkf.game2048.stage.HelpStage;

/**
 * 主游戏场景, 包含 4 个舞台
 *
 * @author xietansheng
 */
public class GameScreen extends ScreenAdapter {

    private MainGame mainGame;

    /** 1. 主游戏舞台 */
    private GameStage gameStage;

    /** 2. 帮助舞台 */
    private HelpStage helpStage;

    /** 3. 游戏结束舞台 */
    private GameOverStage gameOverStage;

    /** 4. 退出确认舞台 */
    private ExitConfirmStage exitConfirmStage;

    public GameScreen(MainGame mainGame) {
        this.mainGame = mainGame;
        init();
    }

    private void init() {
        // 1. 创建主游戏舞台
        gameStage = new GameStage(
                mainGame,
                new StretchViewport(
                        mainGame.getWorldWidth(),
                        mainGame.getWorldHeight()
                )
        );

        // 2. 创建帮助舞台
        helpStage = new HelpStage(
                mainGame,
                new StretchViewport(
                        mainGame.getWorldWidth(),
                        mainGame.getWorldHeight()
                )
        );
        helpStage.setVisible(false);    // 除主游戏舞台外, 其他舞台先设置为不可见

        // 3. 创建游戏结束舞台
        gameOverStage = new GameOverStage(
                mainGame,
                new StretchViewport(
                        mainGame.getWorldWidth(),
                        mainGame.getWorldHeight()
                )
        );
        gameOverStage.setVisible(false);

        // 4. 创建退出确认舞台
        exitConfirmStage = new ExitConfirmStage(
                mainGame,
                new StretchViewport(
                        mainGame.getWorldWidth(),
                        mainGame.getWorldHeight()
                )
        );
        exitConfirmStage.setVisible(false);
        
        // 把输入处理设置到主游戏舞台（必须设置, 否则无法接收用户输入）
        Gdx.input.setInputProcessor(gameStage);
    }
    
    /**
     * 重新开始游戏
     */
    public void restartGame() {
        gameStage.restartGame();
    }

    /**
     * 帮助舞台 是否显示
     */
    public void setShowHelpStage(boolean showHelpStage) {
        helpStage.setVisible(showHelpStage);
        if (helpStage.isVisible()) {
            // 如果显示帮助舞台, 则把输入处理设置到帮助舞台
            Gdx.input.setInputProcessor(helpStage);
        } else {
            // 不显示帮助舞台, 把输入处理设置回主游戏舞台
            Gdx.input.setInputProcessor(gameStage);
        }
    }

    /**
     * 退出确认舞台 是否显示
     */
    public void setShowExitConfirmStage(boolean showExitConfirmStage) {
        exitConfirmStage.setVisible(showExitConfirmStage);
        if (exitConfirmStage.isVisible()) {
            Gdx.input.setInputProcessor(exitConfirmStage);
        } else {
            Gdx.input.setInputProcessor(gameStage);
        }
    }

    /**
     * 显示结束舞台（并设置结束舞台中的文本显示状态和分数）
     */
    public void showGameOverStage(boolean isWin, int score) {
        // 设置结束舞台中的文本显示状态状态和分数
        gameOverStage.setGameOverState(isWin, score);

        gameOverStage.setVisible(true);
        Gdx.input.setInputProcessor(gameOverStage);
    }

    /**
     * 隐藏结束舞台
     */
    public void hideGameOverStage() {
        gameOverStage.setVisible(false);
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render(float delta) {
        // 使用背景颜色清屏
        Gdx.gl.glClearColor(Res.BG_RGBA.r, Res.BG_RGBA.g, Res.BG_RGBA.b, Res.BG_RGBA.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
         * 更新舞台逻辑, 绘制舞台
         */

        // 1. 主游戏舞台始终都需要绘制, 并且最先绘制
        gameStage.act();
        gameStage.draw();

        // 2. 帮助舞台
        if (helpStage.isVisible()) {
            helpStage.act();
            helpStage.draw();
        }

        // 3. 游戏结束舞台
        if (gameOverStage.isVisible()) {
            gameOverStage.act();
            gameOverStage.draw();
        }

        // 4. 退出游戏确认的舞台
        if (exitConfirmStage.isVisible()) {
            exitConfirmStage.act();
            exitConfirmStage.draw();
        }
    }

    @Override
    public void dispose() {
        // 场景销毁时, 同时销毁所有舞台
        if (gameStage != null) {
            gameStage.dispose();
        }
        if (helpStage != null) {
            helpStage.dispose();
        }
        if (gameOverStage != null) {
            gameOverStage.dispose();
        }
        if (exitConfirmStage != null) {
            exitConfirmStage.dispose();
        }
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public HelpStage getHelpStage() {
        return helpStage;
    }

    public GameOverStage getGameOverStage() {
        return gameOverStage;
    }

    public ExitConfirmStage getExitConfirmStage() {
        return exitConfirmStage;
    }
}



























