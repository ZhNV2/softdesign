package ru.spbau.zhidkov.view.drawer;

import java.util.Arrays;

public class TestDrawer {

    final char[][] field;

    public TestDrawer(int n, int m) {
        field = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                set(i, j, ' ');
            }
        }
    }

    public void set(int i, int j, char c) {
        field[i][j] = c;
    }

    public void print() {
        for (int i = 0; i < field.length; i++) {
            for (int j =0 ; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDrawer that = (TestDrawer) o;

        return Arrays.deepEquals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(field);
    }
}
