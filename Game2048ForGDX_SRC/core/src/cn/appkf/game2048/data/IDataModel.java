package cn.appkf.game2048.data;

/**
 * 数据模型接口, 提供给外部方便地访问数据模型而不需要知道实现细节的 API
 *
 * @see DataModelImpl
 *
 * @author xietansheng
 */
public interface IDataModel {

	/**
     * 数据初始化, 创建了一个数据模型实例或重新开始游戏时调用该方法
     */
	public void dataInit();
	
	/**
     * 获取当前数据（4x4 二维数组）
     */
	public int[][] getData();

    /**
     * 获取当前的分数
     */
    public int getCurrentScore();

    /**
     * 判断当前是否处于游戏胜利（过关）状态
     */
    public GameState getGameState();

    /**
     * 对数据的操作, 向上滑动
     */
	public void toTop();

    /**
     * 对数据的操作, 向下滑动
     */
	public void toBottom();

    /**
     * 对数据的操作, 向左滑动
     */
	public void toLeft();

    /**
     * 对数据的操作, 向右滑动
     */
	public void toRight();

    /**
     * 游戏状态枚举
     */
    public static enum GameState {
        /** 正在游戏状态 */
        game,
        /** 游戏胜利（过关）状态 */
        win,
        /** 游戏失败状态 */
        gameOver;
    }

    /**
     * 数据监听器, 监听到数据变化后播放相应的音效和动画
     */
	public static interface DataListener {
        /**
         * 随机生成数字时调用
         *
         * @param row 生成的数字所在行
         * @param col 生成的数字所在列
         * @param num 生成的数字
         */
        public void onGeneratorNumber(int row, int col, int num);

        /**
         * 两个数字合并时调用
         *
         * @param rowAfterMerge 合并后数字所在行
         * @param colAfterMerge 合并后数字所在列
         * @param numAfterMerge 合并后的数字
         * @param currentScoreAfterMerger 合并后的当前分数
         */
        public void onNumberMerge(int rowAfterMerge, int colAfterMerge, int numAfterMerge, int currentScoreAfterMerger);

        /**
         * 游戏结束时调用
         *
         * @param isWin 是否胜利（过关）
         */
        public void onGameOver(boolean isWin);
    }

    /**
     * 数据模型构建器
     */
    public static class Builder {
        /**
         * 创建一个指定行列数据的数据模型
         */
        public static IDataModel createDataModel(int rowSum, int colSum, IDataModel.DataListener dataListener) {
            return new DataModelImpl(rowSum, colSum, dataListener);
        }
    }

}




















