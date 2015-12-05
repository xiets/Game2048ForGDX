package cn.appkf.game2048.actor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.base.BaseGroup;
import cn.appkf.game2048.data.IDataModel;
import cn.appkf.game2048.res.Res;

/**
 * 中间部分演员组, 2048 数字卡片展示区域, 滑动事件捕获区域
 *
 * @author xietansheng
 */
public class MiddleGroup extends BaseGroup {
	
	// 卡片的行列数
	private static final int CARD_ROW_SUM = 4;
	private static final int CARD_COL_SUM = 4;

    /** 滑动生效的最小距离 */
    private static final float SLIDE_MIN_DIFF = 20;

    /** 中间区域背景 */
    private Image bgImage;
    
    /** 所有数字卡片 */
    private final CardGroup[][] allCards = new CardGroup[CARD_ROW_SUM][CARD_COL_SUM];
    
    /** 移动操作的音效 */
    private Sound moveSound;
    
    /** 合并数据的音效 */
    private Sound mergeSound;

    /** 数据模型（封装了核心的数据与逻辑） */
    private IDataModel dataModel;
    
    public MiddleGroup(MainGame mainGame) {
        super(mainGame);
        init();
    }

    private void init() {
        /*
         * 背景
         */
        bgImage = new Image(getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_RECT_BG));
        addActor(bgImage);
        
        // 设置组的宽高（以组的背景宽高作为组的宽高）
        setSize(bgImage.getWidth(), bgImage.getHeight());
        
        /*
         * 创建所有的数字卡片
         */
        // 创建所有卡片并添加到组中
        for (int row = 0; row < CARD_ROW_SUM; row++) {
			for (int col = 0; col < CARD_COL_SUM; col++) {
				allCards[row][col] = new CardGroup(getMainGame());
				allCards[row][col].setOrigin(Align.center);		// 缩放和旋转支点设置到演员的中心
				addActor(allCards[row][col]);
			}
		}
        
        // 获取卡片的宽高
        float cardWidth = allCards[0][0].getWidth();
        float cardHeight = allCards[0][0].getHeight();
        
        // 计算所有卡片按指定的行列排列到组中后卡片间的水平和竖直间隙大小
        float horizontalInterval = (getWidth() - CARD_COL_SUM * cardWidth) / (CARD_COL_SUM + 1);
        float verticalInterval = (getHeight() - CARD_ROW_SUM * cardHeight) / (CARD_ROW_SUM + 1);
        
        // 均匀地排版卡片在组中的位置
        float cardY = 0;
        for (int row = 0; row < CARD_ROW_SUM; row++) {
        	cardY = getHeight() - (verticalInterval + cardHeight) * (row + 1);
			for (int col = 0; col < CARD_COL_SUM; col++) {
				allCards[row][col].setX(horizontalInterval + (cardWidth + horizontalInterval) * col);
				allCards[row][col].setY(cardY);
			}
		}
        
        /*
         * 获取音效
         */
        moveSound = getMainGame().getAssetManager().get(Res.Audios.MOVE, Sound.class);
        mergeSound = getMainGame().getAssetManager().get(Res.Audios.MERGE, Sound.class);

        /*
         * 添加输入监听器（用于监听手势的滑动）
         */
        addListener(new InputListenerImpl());

        /*
         * 数据模型
         */
        // 创建一个指定行列的数据模型实例, 并指定数据监听器
        dataModel = IDataModel.Builder.createDataModel(CARD_ROW_SUM, CARD_COL_SUM, new DataListenerImpl());
        // 初始化数据模型
        dataModel.dataInit();

        // 数据模型初始化后同步到演员数组
        syncDataToCardGroups();
    }

    /**
     * 重新开始游戏
     */
    public void restartGame() {
        dataModel.dataInit();
        syncDataToCardGroups();
    }

    /**
     * 同步 数据模型中的数据 到 卡片演员数组
     */
    private void syncDataToCardGroups() {
        int[][] data = dataModel.getData();
        for (int row = 0; row < CARD_ROW_SUM; row++) {
            for (int col = 0; col < CARD_COL_SUM; col++) {
                allCards[row][col].setNum(data[row][col]);
            }
        }
    }

    public void toTop() {
        // 操作数据模型中的数据
        dataModel.toTop();
        // 操作完数据模型中的数据后, 需要同步到卡片演员数组
        syncDataToCardGroups();
        // 播放移动操作的音效
        moveSound.play();
    }

    public void toBottom() {
        dataModel.toBottom();
        syncDataToCardGroups();
        moveSound.play();
    }

    public void toLeft() {
        dataModel.toLeft();
        syncDataToCardGroups();
        moveSound.play();
    }

    public void toRight() {
        dataModel.toRight();
        syncDataToCardGroups();
        moveSound.play();
    }

    /**
     * 输入监听器的实现
     */
    private class InputListenerImpl extends InputListener {

        private float downX;
        private float downY;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            downX = x;
            downY = y;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            float diffX = x - downX;
            float diffY = y - downY;

            if (Math.abs(diffX) >= SLIDE_MIN_DIFF && Math.abs(diffX) * 0.5F > Math.abs(diffY)) {
                // 左右滑动
                if (diffX > 0) {
                    toRight();
                } else {
                    toLeft();
                }
            } else if (Math.abs(diffY) >= SLIDE_MIN_DIFF && Math.abs(diffY) * 0.5F > Math.abs(diffX)) {
                // 上下滑动
                if (diffY > 0) {
                    toTop();
                } else {
                    toBottom();
                }
            }
        }
    }

    /**
     * 数据监听器实现
     */
    private class DataListenerImpl implements IDataModel.DataListener {

        @Override
        public void onGeneratorNumber(int row, int col, int num) {
        	// 有数字生成新, 该数字所在位置的卡片附加一个动画效果, 0.2 秒内缩放值从 0.2 到 1.0
        	allCards[row][col].setScale(0.2F);
        	ScaleToAction scaleTo = Actions.scaleTo(1.0F, 1.0F, 0.2F);
            allCards[row][col].addAction(scaleTo);
        }

        @Override
        public void onNumberMerge(int rowAfterMerge, int colAfterMerge, int numAfterMerge, int currentScoreAfterMerger) {
        	// 有卡片合成, 在合成位置附加动画效果, 缩放值从 0.8 到 1.2, 再到 1.0
        	allCards[rowAfterMerge][colAfterMerge].setScale(0.8F);
        	SequenceAction sequence = Actions.sequence(
        			Actions.scaleTo(1.2F, 1.2F, 0.1F),
        			Actions.scaleTo(1.0F, 1.0F, 0.1F)
        	);
        	allCards[rowAfterMerge][colAfterMerge].addAction(sequence);
        	// 播放数字合成的音效
        	mergeSound.play();
            // 增加当前分数
            getMainGame().getGameScreen().getGameStage().addCurrScore(numAfterMerge);
        }

        @Override
        public void onGameOver(boolean isWin) {
            // 游戏结束, 展示结束舞台
            getMainGame().getGameScreen().showGameOverStage(isWin, dataModel.getCurrentScore());
        }
    }

}






















