package cn.appkf.game2048.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.res.Res;
import cn.appkf.game2048.stage.base.BaseStage;

/**
 * 游戏结束舞台
 *
 * @author xietansheng
 */
public class GameOverStage extends BaseStage {

    /** 舞台背景颜色, 60% 黑色 */
    private final Color bgColor = new Color(0, 0, 0, 0.6F);
    
    /** 提示文本的颜色 */
    private final Color msgColor = new Color(0xFFFFFFFF);
    
    /** 背景 */
    private Image bgImage;
    
    /** 游戏结束文本提示标签 */
    private Label msgLabel;
    
    /** 返回按钮 */
    private Button backButton;
    
    /** 再来一局按钮 */
    private Button againButton;

    public GameOverStage(MainGame mainGame, Viewport viewport) {
        super(mainGame, viewport);
        init();
    }

    private void init() {
        /*
         * 背景
         */
        // Res.AtlasNames.GAME_BLANK 是一张纯白色的小图片
        bgImage = new Image(getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_BLANK));
        bgImage.setColor(bgColor);
        bgImage.setOrigin(0, 0);
        // 缩放到铺满整个舞台
        bgImage.setScale(getWidth() / bgImage.getWidth(), getHeight() / bgImage.getHeight());
        addActor(bgImage);
        
        /*
         * 游戏结束文本提示标签
         */
        Label.LabelStyle msgStyle = new Label.LabelStyle();
        msgStyle.font = getMainGame().getBitmapFont();
        msgStyle.fontColor = msgColor;
        msgLabel = new Label("", msgStyle);
        msgLabel.setFontScale(0.7F);
        addActor(msgLabel);
        
        /*
         * 返回按钮
         */
        Button.ButtonStyle backStyle = new Button.ButtonStyle();
        backStyle.up = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.BTN_BACK, 1));
        backStyle.down = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.BTN_BACK, 2));
        backButton = new Button(backStyle);
        backButton.setX(40);
        backButton.setY(140);
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		// 点击返回按钮, 隐藏结束舞台（返回主游戏舞台）
        		getMainGame().getGameScreen().hideGameOverStage();
        	}
        });
        addActor(backButton);
        
        /*
         * 再来一局按钮
         */
        Button.ButtonStyle againStyle = new Button.ButtonStyle();
        againStyle.up = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.BTN_AGAIN, 1));
        againStyle.down = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.BTN_AGAIN, 2));
        againButton = new Button(againStyle);
        againButton.setX(getWidth() - againButton.getWidth() - 40);
        againButton.setY(140);
        againButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		// 隐藏结束舞台（返回主游戏舞台）
                getMainGame().getGameScreen().hideGameOverStage();
                // 重新初始化游戏
                getMainGame().getGameScreen().restartGame();
            }
        });
        addActor(againButton);

        /*
    	 * 添加舞台输入监听器
    	 */
        addListener(new InputListenerImpl());
    }
    
    public void setGameOverState(boolean isWin, int score) {
    	if (isWin) {
            msgLabel.setText("恭喜您 , 游戏过关 !\n分数: " + score);
        } else {
            msgLabel.setText("游戏结束 !\n分数: " + score);
    	}
    	
    	/*
    	 * 设置了文本后重新设置标签的宽高以及位置
    	 */
    	// 标签包裹字体
    	msgLabel.setSize(msgLabel.getPrefWidth(), msgLabel.getPrefHeight());
    	
    	msgLabel.setX(40);
    	msgLabel.setY(getHeight() - msgLabel.getHeight() - 100);
    }

    /**
     * 输入事件监听器
     */
    private class InputListenerImpl extends InputListener {
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (keycode == Input.Keys.BACK) {
                // 按返回键, 隐藏游戏结束舞台（返回主游戏舞台）
                getMainGame().getGameScreen().hideGameOverStage();
                return true;
            }
            return super.keyUp(event, keycode);
        }
    }

}






















