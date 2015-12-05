package cn.appkf.game2048.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.res.Res;
import cn.appkf.game2048.stage.base.BaseStage;

/**
 * 帮助舞台, 使用一张带文字的现成图片来展示说明
 *
 * @author xietansheng
 */
public class HelpStage extends BaseStage {

    /** 舞台背景颜色, 60% 黑色 */
    private final Color bgColor = new Color(0, 0, 0, 0.6F);

    /** 背景 */
    private Image bgImage;

    /** 帮助内容图片 */
    private Image helpContentImage;

    public HelpStage(MainGame mainGame, Viewport viewport) {
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
         * 帮助内容的图片
         */
        helpContentImage = new Image(getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_HELP_BG));
        // 水平居中
        helpContentImage.setX(getWidth() / 2 - helpContentImage.getWidth() / 2);
        // 设置到舞台顶部
        helpContentImage.setY(getHeight() - helpContentImage.getHeight());
        addActor(helpContentImage);

        /*
         * 添加舞台监听器（点击屏幕或按返回键返回主游戏舞台）
         */
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // 隐藏帮助舞台返回主游戏舞台）
                getMainGame().getGameScreen().setShowHelpStage(false);
            }
        });
        addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    // 按返回键, 隐藏帮助舞台（返回主游戏舞台）
                    getMainGame().getGameScreen().setShowHelpStage(false);
                }
                return true;
            }
        });
    }

}
















