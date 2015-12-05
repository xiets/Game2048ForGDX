package cn.appkf.game2048.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.DialogGroup;
import cn.appkf.game2048.res.Res;
import cn.appkf.game2048.stage.base.BaseStage;

/**
 * 退出确认对话框的舞台, 包含一个对话框
 *
 * @author xietansheng
 */
public class ExitConfirmStage extends BaseStage {

    /** 舞台背景颜色, 60% 黑色 */
    private final Color bgColor = new Color(0, 0, 0, 0.6F);

    /** 背景 */
    private Image bgImage;
	
	/** 确认对话框 */
	private DialogGroup dialogGroup;

    public ExitConfirmStage(MainGame mainGame, Viewport viewport) {
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
         * 创建对话框
         */
    	dialogGroup = new DialogGroup(getMainGame(), "确定退出游戏 ?");
    	// 将对话框居中到舞台
    	dialogGroup.setPosition(
    			getWidth() / 2 - dialogGroup.getWidth() / 2,
    			getHeight() / 2 - dialogGroup.getHeight() / 2
    	);
    	
    	// 给对话框的确定按钮添加监听器
    	dialogGroup.getOkButton().addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		// 点击确定按钮, 退出应用
        		Gdx.app.exit();
        	}
        });
    	
    	// 给对话框的确定按钮添加监听器
    	dialogGroup.getCancelButton().addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		// 点击取消按钮, 隐藏对话框（隐藏退出确认舞台, 返回主游戏舞台）
        		getMainGame().getGameScreen().setShowExitConfirmStage(false);
        	}
        });
    	
    	// 添加对话框到舞台
    	addActor(dialogGroup);
    	
    	/*
    	 * 添加舞台输入监听器
    	 */
    	addListener(new InputListenerImpl());
    }
    
    /**
     * 输入事件监听器
     */
    private class InputListenerImpl extends InputListener {
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (keycode == Input.Keys.BACK) {
                // 按返回键, 隐藏退出确认舞台（返回主游戏舞台）
                getMainGame().getGameScreen().setShowExitConfirmStage(false);
                return true;
            }
            return super.keyUp(event, keycode);
        }
    }

}



















