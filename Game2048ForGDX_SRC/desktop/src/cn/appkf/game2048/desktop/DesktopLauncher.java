package cn.appkf.game2048.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cn.appkf.game2048.MainGame;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
        final float pixWidth = 320;				// 窗口宽度参考值
        final float ratio = 320.0F / 480.0F;	// 窗口宽高比（适当调节宽高比可以查看在不同屏幕上的效果）
        final float scale = 1.0F;				// 适当改变缩放比以适应自己的电脑屏幕
        
        config.width = (int) (pixWidth * scale);         	// 窗口宽度
        config.height = (int) ((pixWidth / ratio) * scale);	// 窗口高度
        
        config.resizable = false;				// 窗口设置为大小不可改变
        
        new LwjglApplication(new MainGame(), config);
	}

}

























