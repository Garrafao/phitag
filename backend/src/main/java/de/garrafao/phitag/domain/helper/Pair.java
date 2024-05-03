package de.garrafao.phitag.domain.helper;

import lombok.Data;

@Data
public class Pair<L, R> {
    
    private L left;
    private R right;

    public Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }
    
}
