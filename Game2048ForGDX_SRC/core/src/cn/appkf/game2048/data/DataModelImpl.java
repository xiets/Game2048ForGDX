package cn.appkf.game2048.data;

import java.util.Random;

/**
 * 数据模型的实现 <br/><br/>
 *
 * 该类中封装了 2048 游戏的核心数据结构与算法逻辑, 实现了数据与显示的分离。
 * 在该类中使用的基本都是基本类型的运算, 与游戏框架/引擎无关, 甚至可以说与
 * 编程语言无关, 非常方便移植。如果想将该游戏使用其他引擎实现或者直接使用平
 * 台原生的 API 实现, 那么数据模型这一部分几乎不需要修改, 不同的引擎/平台
 * 只是对数据模型的显示和操作触发（事件监听）不同。
 *
 * @author xietansheng
 */
public class DataModelImpl implements IDataModel {

    /** 数据的总行数 */
    private final int rowSum;

    /** 数据的总列数 */
    private final int colSum;

    /** 数据监听器 */
    private DataListener dataListener;

    /** 二维数组数据 */
    private final int[][] data;

    /** 得分 */
    private int currentScore;

    /** 游戏状态, 默认为游戏状态 */
    private GameState gameState = GameState.game;

    /** 随机数生成器 */
    private final Random random;

    public DataModelImpl(int rowSum, int colSum, DataListener dataListener) {
        this.rowSum = rowSum;
        this.colSum = colSum;
        this.dataListener = dataListener;
        data = new int[this.rowSum][this.colSum];
        random = new Random();
    }

    @Override
    public void dataInit() {
        // 数据清零
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                data[row][col] = 0;
            }
        }

        // 重置状态
        currentScore = 0;
        gameState = GameState.game;

        // 随机生成两个数字
        randomGeneratorNumber();
        randomGeneratorNumber();
    }

    @Override
    public int[][] getData() {
        return data;
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void toTop() {
        /*
         * 移动和合并数字, 整个 2048 游戏中最核心的算法部分, 也是最难的部分
         */

        // 非正在游戏状态时调用, 忽略
        if (gameState != GameState.game) {
            return;
        }

        // 是否有卡片移动或合并的标记
        boolean hasMove = false;

        // 竖直方向移动, 依次遍历每一列
        for (int col = 0; col < colSum; col++) {
            // 向上移动, 从第 0 行开始依次向下遍历每一行
            for (int row = 0; row < rowSum; row++) {
                // 找出当前遍历行 row 下面的首个非空卡片, 将该非空卡片移动到当前 row 行位置
                for (int tmpRow = row + 1; tmpRow < rowSum; tmpRow++) {
                    if (data[tmpRow][col] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        // 如果当前 row 行位置是空的, 则直接移动卡片
                        data[row][col] = data[tmpRow][col];
                        hasMove = true;
                        // 数字移动后原位置清零
                        data[tmpRow][col] = 0;
                        // 当前 row 行位置是空的, 卡片移动到当前 row 行位置后需要在下一循环中重新遍历该 row 行（移动后
                        // 的 row 行位置卡片和 row 下面下一次需要移动的卡片数字如果相同依然要相加, 没有重新校验会被忽略）
                        row--;
                    } else if (data[row][col] == data[tmpRow][col]) {
                        // 如果当前 row 行位置和找到的 row 下面首个非空卡片的数字相同, 则合并数字
                        data[row][col] += data[tmpRow][col];
                        hasMove = true;
                        // 增加分数
                        currentScore += data[row][col];
                        // 回调监听
                        if (dataListener != null) {
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        // 合并后原位置清零
                        data[tmpRow][col] = 0;
                    }
                    break;
                }
            }
        }

        // 滑动一次, 只有卡片有移动过时, 才需要检测是否游戏结束和生成新数字
        if (hasMove) {
        	// 校验游戏是否结束（过关或失败）
            checkGameFinish();
        	// 移动完一次后, 随机生成一个数字
            randomGeneratorNumber();
            // 生成数字后还需要再校验一次游戏是否结束（失败）, 防止生成数字后就是不可再移动状态
            checkGameFinish();
        }

        /* 下移, 左移, 右移 的原理相同, 不再注释 */
    }

    @Override
    public void toBottom() {
        if (gameState != GameState.game) {
            return;
        }

        boolean hasMove = false;

        for (int col = 0; col < colSum; col++) {
            for (int row = rowSum - 1; row >= 0; row--) {
                for (int tmpRow = row - 1; tmpRow >= 0; tmpRow--) {
                    if (data[tmpRow][col] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        hasMove = true;
                        data[row][col] = data[tmpRow][col];
                        data[tmpRow][col] = 0;
                        row++;
                    } else if (data[row][col] == data[tmpRow][col]) {
                        data[row][col] += data[tmpRow][col];
                        hasMove = true;
                        currentScore += data[row][col];
                        if (dataListener != null) {
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        data[tmpRow][col] = 0;
                    }
                    break;
                }
            }
        }

        if (hasMove) {
        	checkGameFinish();
            randomGeneratorNumber();
            checkGameFinish();
        }
    }

    @Override
    public void toLeft() {
        if (gameState != GameState.game) {
            return;
        }

        boolean hasMove = false;

    	for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                for (int tmpCol = col + 1; tmpCol < colSum; tmpCol++) {
                    if (data[row][tmpCol] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[row][tmpCol];
                        hasMove = true;
                        data[row][tmpCol] = 0;
                        col--;
                    } else if (data[row][col] == data[row][tmpCol]) {
                        data[row][col] += data[row][tmpCol];
                        hasMove = true;
                        currentScore += data[row][col];
                        if (dataListener != null) {
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        data[row][tmpCol] = 0;
                    }
                    break;
                }
            }
        }

    	if (hasMove) {
        	checkGameFinish();
            randomGeneratorNumber();
            checkGameFinish();
        }
    }

    @Override
    public void toRight() {
        if (gameState != GameState.game) {
            return;
        }

        boolean hasMove = false;

    	for (int row = 0; row < rowSum; row++) {
            for (int col = colSum - 1; col >= 0; col--) {
                for (int tmpCol = col - 1; tmpCol >= 0; tmpCol--) {
                    if (data[row][tmpCol] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[row][tmpCol];
                        hasMove = true;
                        data[row][tmpCol] = 0;
                        col++;
                    } else if (data[row][col] == data[row][tmpCol]) {
                        data[row][col] += data[row][tmpCol];
                        hasMove = true;
                        currentScore += data[row][col];
                        if (dataListener != null) {
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        data[row][tmpCol] = 0;
                    }
                    break;
                }
            }
        }

        if (hasMove) {
        	checkGameFinish();
            randomGeneratorNumber();
            checkGameFinish();
        }
    }

    /**
     * 校验游戏是否结束（过关或失败）
     */
    private void checkGameFinish() {
        // 判断是否游戏胜利（过关）
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                if (data[row][col] == 2048) {
                    gameState = GameState.win;          // 只要有一个卡片拼凑出 2048, 游戏即胜利（过关）
                    if (dataListener != null) {
                        dataListener.onGameOver(true);  // 监听回调
                    }
                    return;
                }
            }
        }

        // 游戏还没有胜利, 则判断是否还可移动
        if (!isMoveable()) {
            gameState = GameState.gameOver;             // 如果游戏没有胜利, 卡片又不可再移动, 则游戏失败
            if (dataListener != null) {
                dataListener.onGameOver(false);         // 监听回调
            }
            return;
        }
    }

    /**
     * 判断是否还可移动
     * @return
     */
    private boolean isMoveable() {
        // 校验水平方向上是否可移动
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum - 1; col++) {
                // 有空位或连续的两个卡片位置数字相等, 则可移动
                if (data[row][col] == 0 || data[row][col + 1] == 0 || data[row][col] == data[row][col + 1]) {
                    return true;
                }
            }
        }

        // 校验竖直方向上是否可移动
        for (int col = 0; col < colSum; col++) {
            for (int row = 0; row < rowSum - 1; row++) {
                if (data[row][col] == 0 || data[row + 1][col] == 0 || data[row][col] == data[row + 1][col]) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * 随机在指定的行列生成一个数字（2或4）, 整个 2048 游戏中的核心算法之一
     */
    private void randomGeneratorNumber() {
        // 计算出空卡片的数量（数字为 0 的卡片）
        int emptyCardsCount = 0;
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                if (data[row][col] == 0) {
                    emptyCardsCount++;
                }
            }
        }

        // 如果没有空卡片, 则游戏结束
        if (emptyCardsCount == 0) {
            /*
             * 有卡片移动时才会调用该方法, 所以程序不会到达这里,
             * 但为了逻辑严谨, 这里还是要做一下是否有空卡片的判断。
             */
            gameState = GameState.gameOver;         // 游戏失败（没有过关）
            if (dataListener != null) {
                dataListener.onGameOver(false);     // 监听回调
            }
            return;
        }

        // 有空卡片, 则在这些空卡片中随机挑一个位置生成数字
        int newNumOnEmptyCardsPosition = random.nextInt(emptyCardsCount);   // 在第 newNumOnEmptyCardsPosition 个空卡片位置生成数字
        int newNum = random.nextFloat() < 0.2F ? 4 : 2;     // 20% 的概率生成 4

        // 把生成的数字（newNum）放到指定的空卡片中（第 newNumOnEmptyCardsPosition 个空卡片）
        int emptyCardPosition = 0;
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                // 忽略非空卡片
                if (data[row][col] != 0) {
                    continue;
                }
                // 如果找到指定位置的空卡片, 则放入数字
                if (emptyCardPosition == newNumOnEmptyCardsPosition) {
                    data[row][col] = newNum;
                    // 有数字生成, 回调监听
                    if (dataListener != null) {
                        dataListener.onGeneratorNumber(row, col, newNum);
                    }
                    return;
                }
                // 还没有遍历到指定位置的空卡片, 继续遍历
                emptyCardPosition++;
            }
        }
    }

}





















