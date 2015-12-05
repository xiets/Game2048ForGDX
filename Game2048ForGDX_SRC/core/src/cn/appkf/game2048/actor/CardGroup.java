package cn.appkf.game2048.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cn.appkf.game2048.MainGame;
import cn.appkf.game2048.actor.base.BaseGroup;
import cn.appkf.game2048.res.Res;

/**
 * 数字卡片
 *
 * @author xietansheng
 */
public class CardGroup extends BaseGroup {
	
	/** 卡片背景 */
	private Image bgImage;
	
	/** 卡片显示的数字标签 */
	private Label numLabel;
	
	/** 卡片当前显示的数字 */
	private int num;

	public CardGroup(MainGame mainGame) {
		super(mainGame);
		init();
	}

	private void init() {
		/*
		 * 卡片背景
		 */
		bgImage = new Image(getMainGame().getAtlas().findRegion(Res.AtlasNames.GAME_ROUND_RECT));
		addActor(bgImage);
		
		// 设置组的宽高（以卡片背景的宽高作为组的宽高）
		setSize(bgImage.getWidth(), bgImage.getHeight());
		
		/*
		 * 卡片显示的数字标签
		 */
		// 创建标签样式
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getMainGame().getBitmapFont();
        style.fontColor = Color.BLACK;	// 卡片的数字颜色设置为黑色
        
        // 创建文本标签
        numLabel = new Label("0", style);
        
        // 设置字体缩放
        numLabel.setFontScale(0.48F);
        
        // 设置标签的宽高（把标签的宽高设置为文本字体的宽高, 即标签包裹文本）
        numLabel.setSize(numLabel.getPrefWidth(), numLabel.getPrefHeight());
        
        // 设置文本标签在组中居中显示
        numLabel.setX(getWidth() / 2 - numLabel.getWidth() / 2);
        numLabel.setY(getHeight() / 2 - numLabel.getHeight() / 2);
        
        addActor(numLabel);
        
        // 初始化显示
        setNum(num);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		
		if (this.num == 0) {
			// 如果是 0, 不显示文本
			numLabel.setText("");
		} else {
			numLabel.setText("" + this.num);
		}
		
		// 重新设置文本后, 文本的宽度可能被改变, 需要重新设置标签的宽度, 并重新水平居中
		numLabel.setWidth(numLabel.getPrefWidth());
		numLabel.setX(getWidth() / 2 - numLabel.getWidth() / 2);
		
		// 根据不同的数字给卡片背景设置颜色
		switch (this.num) {
			case 2: {
				bgImage.setColor(Res.CardColors.RGBA_2);
				break;
			}
			case 4: {
				bgImage.setColor(Res.CardColors.RGBA_4);
				break;
			}
			case 8: {
				bgImage.setColor(Res.CardColors.RGBA_8);
				break;
			}
			case 16: {
				bgImage.setColor(Res.CardColors.RGBA_16);
				break;
			}
			case 32: {
				bgImage.setColor(Res.CardColors.RGBA_32);
				break;
			}
			case 64: {
				bgImage.setColor(Res.CardColors.RGBA_64);
				break;
			}
			case 128: {
				bgImage.setColor(Res.CardColors.RGBA_128);
				break;
			}
			case 256: {
				bgImage.setColor(Res.CardColors.RGBA_256);
				break;
			}
			case 512: {
				bgImage.setColor(Res.CardColors.RGBA_512);
				break;
			}
			case 1024: {
				bgImage.setColor(Res.CardColors.RGBA_1024);
				break;
			}
			case 2048: {
				bgImage.setColor(Res.CardColors.RGBA_2048);
				break;
			}
			default: {
				bgImage.setColor(Res.CardColors.RGBA_0);
				break;
			}
		}
	}

}















