package cn.appkf.game2048.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.base.BaseGroup;
import cn.appkf.game2048.res.Res;

/**
 * 顶部演员组, 包括 LOGO, 分数显示
 *
 * @author xietansheng
 */
public class TopGroup extends BaseGroup {

    /** 2048 LOGO */
    private Image logoImage;

    /** 最佳分数 */
    private ScoreGroup bestScoreGroup;

    /** 当前分数 */
    private ScoreGroup currScoreGroup;

    public TopGroup(MainGame mainGame) {
        super(mainGame);
        init();
    }

    private void init() {
        /*
         * 2048 LOGO
         */
        logoImage = new Image(getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_LOGO));
        logoImage.setX(20);
        addActor(logoImage);
        
        // 设置组的宽高（以世界的宽度, LOGO 的高度 作为组的宽高）
        setSize(getMainGame().getWorldWidth(), logoImage.getHeight());
        
        /*
         * 当前分数
         */
        currScoreGroup = new ScoreGroup(getMainGame(), getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_SCORE_BG_NOW));
        currScoreGroup.setX(186);
        currScoreGroup.setY(getHeight() - currScoreGroup.getHeight());	// 设置到组的顶部
        addActor(currScoreGroup);

        /*
         * 最佳分数
         */
        bestScoreGroup = new ScoreGroup(getMainGame(), getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_SCORE_BG_BEST));
        bestScoreGroup.setX(334);
        bestScoreGroup.setY(getHeight() - bestScoreGroup.getHeight());      
        addActor(bestScoreGroup);
    }

    public ScoreGroup getBestScoreGroup() {
        return bestScoreGroup;
    }

    public ScoreGroup getCurrScoreGroup() {
        return currScoreGroup;
    }

}



















