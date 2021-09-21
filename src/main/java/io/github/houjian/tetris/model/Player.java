package io.github.houjian.tetris.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 玩家得分记录信息
 *
 * @author houjian
 */
@Data
@Builder
public class Player implements Comparable<Player>, Serializable {

    /**
     * 得分
     */
    private final int score;
    /**
     * 记录时间
     */
    private final long time;

    @Override
    public int compareTo(Player other) {
        int compare = Integer.compare(other.score, this.score);
        if (compare == 0) {
            return Long.compare(other.time, this.time);
        }
        return compare;
    }
}
