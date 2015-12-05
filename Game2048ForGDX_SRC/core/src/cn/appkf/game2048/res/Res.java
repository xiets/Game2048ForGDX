package cn.appkf.game2048.res;

import com.badlogic.gdx.graphics.Color;

/**
 * 资源常量
 *
 * @author xietansheng
 */
public interface Res {

    /** 固定世界宽度为 480, 高度根据实际屏幕比例换算 */
    public static final float FIX_WORLD_WIDTH = 480;
    
    /** 对话框的宽度 */
    public static final float DIALOG_WIDTH = 370;

    /** 纹理图集 文件路径 */
    public static final String ATLAS_PATH = "atlas/game.atlas";

    /** 位图字体 文件路径 */
    public static final String BITMAP_FONT_PATH = "font/bitmap_font.fnt";

    /** 背景颜色 */
    public static final Color BG_RGBA = new Color(0xEEE7D4FF);

    /**
     * 纹理图集的小图名称常量
     */
    public static interface AtlasNames {
        public static final String GAME_BLANK = "game_blank";
        public static final String GAME_BTN_EXIT = "game_btn_exit";
        public static final String GAME_BTN_HELP = "game_btn_help";
        public static final String GAME_HELP_BG = "game_help_bg";
        public static final String GAME_LOGO = "game_logo";
        public static final String GAME_RECT_BG = "game_rect_bg";
        public static final String GAME_ROUND_RECT = "game_round_rect";
        public static final String GAME_SCORE_BG_BEST = "game_score_bg_best";
        public static final String GAME_SCORE_BG_NOW = "game_score_bg_now";
        public static final String DIALOG_BTN_OK = "dialog_btn_ok";
        public static final String DIALOG_BTN_cancel = "dialog_btn_cancel";
        public static final String BTN_BACK = "btn_back";
        public static final String BTN_AGAIN = "btn_again";
    }
    
    /**
     * 音频资源
     */
    public static interface Audios {
    	public static final String MOVE = "audio/move.mp3";
    	public static final String MERGE = "audio/merge.mp3";
    }
    
    /**
     * 不同数字的卡片背景颜色
     */
    public static interface CardColors {
    	public static final Color RGBA_0 = new Color(0xCCC0B3FF);
    	public static final Color RGBA_2 = new Color(0xEEE4DAFF);
    	public static final Color RGBA_4 = new Color(0xEDE0C8FF);
    	public static final Color RGBA_8 = new Color(0xF2B179FF);
    	public static final Color RGBA_16 = new Color(0xF49563FF);
    	public static final Color RGBA_32 = new Color(0xF5794DFF);
    	public static final Color RGBA_64 = new Color(0xF55D37FF);
    	public static final Color RGBA_128 = new Color(0xEEE863FF);
    	public static final Color RGBA_256 = new Color(0xEDB04DFF);
    	public static final Color RGBA_512 = new Color(0xECB04DFF);
    	public static final Color RGBA_1024 = new Color(0xEB9437FF);
    	public static final Color RGBA_2048 = new Color(0xEA7821FF);
    }

    /**
     * Preferences 键值对存储的相关常量
     */
    public static interface Prefs {

        /** Preferences 键值对存储文件名称 */
        public static final String FILE_NAME = "game_preferences";

        /** 存储字段名的 key: 最佳分数 */
        public static final String KEY_BEST_SCORE = "best_score";
    }

}















