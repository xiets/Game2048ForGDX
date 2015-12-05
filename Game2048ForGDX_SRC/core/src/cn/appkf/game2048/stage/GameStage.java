package cn.appkf.game2048.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.BottomGroup;
import cn.appkf.game2048.actor.MiddleGroup;
import cn.appkf.game2048.actor.TopGroup;
import cn.appkf.game2048.res.Res;
import cn.appkf.game2048.stage.base.BaseStage;

/**
 * 主游戏舞台, 包含 上中下 三个演员组
 *
 * @author xietansheng
 */
public class GameStage extends BaseStage {

    /** 顶部演员组（包括 LOGO, 分数显示） */
    private TopGroup topGroup;

    /** 中间部分演员组（2048 数字卡片展示区域, 主要游戏逻辑区域） */
    private MiddleGroup middleGroup;

    /** 底部演员组（包括 游戏帮助 和 退出游戏 按钮） */
    private BottomGroup bottomGroup;

    public GameStage(MainGame mainGame, Viewport viewport) {
        super(mainGame, viewport);
        init();
    }

    private void init() {
        /*
         * 中间部分演员组（先创建中间组作为位置参考）
         */
        middleGroup = new MiddleGroup(getMainGame());
        // 设置到舞台中心
        middleGroup.setPosition(
                getWidth() / 2 - middleGroup.getWidth() / 2,
                getHeight() / 2 - middleGroup.getHeight() / 2
        );
        addActor(middleGroup);
        
        /*
         * 顶部演员组
         */
        topGroup = new TopGroup(getMainGame());
        topGroup.setX(getWidth() / 2 - topGroup.getWidth() / 2);	// 水平居中
        float middleGroupTopY = middleGroup.getY() + middleGroup.getHeight();	// 中间组的顶部Y坐标
        float topSurplusHeight = getHeight() - middleGroupTopY;		// 被中间组占去后顶部剩余的高度
        topGroup.setY(middleGroupTopY + (topSurplusHeight / 2 - topGroup.getHeight() / 2));	// 顶部竖直居中
        addActor(topGroup);

        /*
         * 底部演员组
         */
        bottomGroup = new BottomGroup(getMainGame());
        bottomGroup.setX(getWidth() / 2 - bottomGroup.getWidth() / 2);				// 水平居中
        bottomGroup.setY(middleGroup.getY() / 2 - bottomGroup.getHeight() / 2);		// 底部竖直居中
        addActor(bottomGroup);

        /*
         * 初始化分数显示
         */
        // 当前分数清零
        topGroup.getCurrScoreGroup().setScore(0);
        // 读取本地最佳分数
        Preferences prefs = Gdx.app.getPreferences(Res.Prefs.FILE_NAME);
        int bestScore = prefs.getInteger(Res.Prefs.KEY_BEST_SCORE, 0);
        // 设置最佳分数
        topGroup.getBestScoreGroup().setScore(bestScore);

        /*
         * 添加舞台监听器
         */
        addListener(new InputListenerImpl());
    }

    /**
     * 重新开始游戏
     */
    public void restartGame() {
        middleGroup.restartGame();
        // 当前分数清零
        topGroup.getCurrScoreGroup().setScore(0);
    }

    /**
     * 增加当前分数
     */
    public void addCurrScore(int scoreStep) {
        // 增加分数
        topGroup.getCurrScoreGroup().addScore(scoreStep);
        // 如果当前分数大于最佳分数, 则更新最佳分数
        int currSore = topGroup.getCurrScoreGroup().getScore();
        int bestSore = topGroup.getBestScoreGroup().getScore();
        if (currSore > bestSore) {
            topGroup.getBestScoreGroup().setScore(currSore);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        // 舞台销毁时保存最佳分数
        Preferences prefs = Gdx.app.getPreferences(Res.Prefs.FILE_NAME);
        prefs.putInteger(Res.Prefs.KEY_BEST_SCORE, topGroup.getBestScoreGroup().getScore());
        prefs.flush();
    }

    /**
     * 输入事件监听器
     */
    private class InputListenerImpl extends InputListener {
    	
    	@Override
        public boolean keyDown(InputEvent event, int keycode) {
        	/*
        	 * 对于 PC 平台, 可同时通过按键控制游戏,
        	 * 监听方向键的按下, 根据方向键移动卡片
        	 */
        	switch (keycode) {
				case Input.Keys.UP: {
					middleGroup.toTop();
					return true;
				}
				case Input.Keys.DOWN: {
					middleGroup.toBottom();
					return true;
				}
				case Input.Keys.LEFT: {
					middleGroup.toLeft();
					return true;
				}
				case Input.Keys.RIGHT: {
					middleGroup.toRight();
					return true;
				}
			}
        	
        	return super.keyDown(event, keycode);
        }
    	
        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            if (keycode == Input.Keys.BACK) {
                // 在主游戏舞台界面按下返回键并弹起后, 提示是否退出游戏（显示退出确认舞台）
                getMainGame().getGameScreen().setShowExitConfirmStage(true);
                return true;
            }
            return super.keyUp(event, keycode);
        }
    }

}
















