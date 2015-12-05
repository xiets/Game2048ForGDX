package cn.appkf.game2048.actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.base.BaseGroup;
import cn.appkf.game2048.res.Res;

/**
 * 底部演员组, 包括 游戏帮助 和 退出游戏 按钮
 *
 * @author xietansheng
 */
public class BottomGroup extends BaseGroup {

    /** 帮主按钮 */
    private Button helpButton;
    
    /** 退出按钮 */
    private Button exitButton;

	public BottomGroup(MainGame mainGame) {
		super(mainGame);
		init();
	}


	private void init() {
		/*
		 * 帮助按钮
		 */
		Button.ButtonStyle helpStyle = new Button.ButtonStyle();
		helpStyle.up = new TextureRegionDrawable(
				getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_BTN_HELP, 1)
		);
		helpStyle.down = new TextureRegionDrawable(
				getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_BTN_HELP, 2)
		);
		helpButton = new Button(helpStyle);
		helpButton.setX(15);
		// 设置按钮点击监听
		helpButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                // 显示帮助舞台
                getMainGame().getGameScreen().setShowHelpStage(true);
			}
		});
		addActor(helpButton);
		
		// 设置组的尺寸（以世界的宽度, 按钮的高度 作为组的宽高）
		setSize(getMainGame().getWorldWidth(), helpButton.getHeight());
		
		/*
		 * 退出按钮
		 */
		Button.ButtonStyle exitStyle = new Button.ButtonStyle();
		exitStyle.up = new TextureRegionDrawable(
				getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_BTN_EXIT, 1)
		);
		exitStyle.down = new TextureRegionDrawable(
				getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_BTN_EXIT, 2)
		);
		exitButton = new Button(exitStyle);
		exitButton.setX(240);
		// 设置按钮点击监听
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				getMainGame().getGameScreen().setShowExitConfirmStage(true);
			}
		});
		addActor(exitButton);
	}
    
}



















