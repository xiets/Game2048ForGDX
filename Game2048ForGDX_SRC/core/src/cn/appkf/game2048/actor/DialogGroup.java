package cn.appkf.game2048.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.base.BaseGroup;
import cn.appkf.game2048.res.Res;

/**
 * 自定义对话框, 由一个文本标签和两个按钮组成
 *
 * @author xietansheng
 */
public class DialogGroup extends BaseGroup {
	
	/** 背景颜色 */
    private final Color bgColor = new Color(0xECECEC00 | (int) (255 * 0.95F));
    
    /** 文本标签的字体颜色 */
    private final Color msgTextColor = new Color(0x777777FF);
    
    /** 按钮透明度 */
    private final float btnAlpha = 0.95F;

    /** 背景图片 */
	private Image bgImage;

    /** 对话框文本提示标签 */
    private Label msgLabel;

    /** 确认按钮 */
    private Button okButton;

    /** 取消按钮 */
    private Button cancelButton;

    public DialogGroup(MainGame mainGame, String message) {
        super(mainGame);
        setWidth(Res.DIALOG_WIDTH);
        init(message);
    }

    private void init(String message) {
        /*
         * 背景
         */
        // Res.AtlasNames.GAME_BLANK 是一张纯白色的小图片
        bgImage = new Image(getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_BLANK));
        bgImage.setColor(bgColor);
        bgImage.setOrigin(0, 0);
        // 水平方向先缩放到铺满对话框宽度
        bgImage.setScaleX(getWidth() / bgImage.getWidth());
        addActor(bgImage);

        /*
         * 确定按钮
         */
        Button.ButtonStyle okBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        okBtnStyle.up = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.DIALOG_BTN_OK, 1));
        okBtnStyle.down = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.DIALOG_BTN_OK, 2));
        okButton = new Button(okBtnStyle);
        okButton.setPosition(getWidth() - okButton.getWidth() - 10, 10);
        okButton.getColor().a = btnAlpha;
        addActor(okButton);
        
        /*
         * 取消按钮
         */
        Button.ButtonStyle cancelBtnStyle = new ImageTextButton.ImageTextButtonStyle();
        cancelBtnStyle.up = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.DIALOG_BTN_cancel, 1));
        cancelBtnStyle.down = new TextureRegionDrawable(getMainGame().getAtlas().findRegion(Res.AtlasNames.DIALOG_BTN_cancel, 2));
        cancelButton = new Button(cancelBtnStyle);
        cancelButton.setPosition(10, 10);
        cancelButton.getColor().a = btnAlpha;
        addActor(cancelButton);
        
        /*
         * 对话框文本提示标签
         */
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = getMainGame().getBitmapFont();
        labelStyle.fontColor = msgTextColor;
        msgLabel = new Label(message, labelStyle);
        // 设置字体大小
        msgLabel.setFontScale(0.5F);
        // 标签包裹字体
        msgLabel.setSize(msgLabel.getPrefWidth(), msgLabel.getPrefHeight());
        msgLabel.setX(getWidth() / 2 - msgLabel.getWidth() / 2);
        msgLabel.setY(okButton.getY() + okButton.getHeight() + 50);
        addActor(msgLabel);
        
        /*
         * 根据对话框中的控件计算对话框高度
         */
        setHeight(msgLabel.getY() + msgLabel.getHeight() + 50);
        
        // 已知对话框高度后, 将背景竖直方向缩放到铺满对话框高度
        bgImage.setScaleY(getHeight() / bgImage.getHeight());
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

}














