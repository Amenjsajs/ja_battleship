package battleship;

import java.util.Arrays;

public class Ship {
    private String row;
    private String col;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    private final int length;
    private Direction direction;
    private String[] coords;
    private StringBuilder radar;
    private StringBuilder impactPoint;

    public Ship(String row, String col) {
        this.row = row;
        this.col = col;

        int tmp1 = (int) row.charAt(0) - 65;
        int tmp2 = (int) col.charAt(0) - 65;

        startX = Math.min(tmp1, tmp2);
        endX = Math.max(tmp1, tmp2);

        tmp1 = Integer.parseInt(row.substring(1));
        tmp2 = Integer.parseInt(col.substring(1));

        startY = Math.min(tmp1, tmp2) - 1;
        endY = Math.max(tmp1, tmp2) - 1;

        if (startX == endX) {
            length = Math.abs(startY - endY) + 1;
            direction = Direction.HORIZONTAL;
        } else {
            length = Math.abs(startX - endX) + 1;
            direction = Direction.VERTICAL;
        }

        initCoords((char) (startX + 65), startY + 1, length);

        impactPoint = new StringBuilder();
    }

    private void initCoords(char start, int index, int length) {
        radar = new StringBuilder();
        coords = new String[length];
        int j = 0;
        if (direction == Direction.HORIZONTAL) {
            for (int i = index; i < length + index; i++) {
                coords[j++] = start + "" + i;
                if (radar.length() == 0) {
                    radar.append(start).append(i);
                    if (i - 1 >= 1) {
                        radar.append(", ").append(start).append(i - 1);
                    }
                }
                if (start - 1 >= 'A') {
                    radar.append(", ").append((char) (start - 1)).append(i);
                }
                if (start + 1 <= 'J') {
                    radar.append(", ").append((char) (start + 1)).append(i);
                }
            }
            String last = coords[length - 1];
            int lastIndex = Integer.parseInt(last.substring(1));
            if (lastIndex + 1 <= 10) {
                radar.append(", ").append(start).append(lastIndex + 1);
            }
        } else {
            for (char c = start; c < start + length; c++) {
                coords[j++] = c + "" + index;
                if (radar.length() == 0) {
                    radar.append(c).append(index);
                    if (c - 1 >= 'A') {
                        radar.append((char) (c - 1)).append(index);
                    }
                }

                if (index - 1 >= 1) {
                    radar.append(", ").append(c).append(index - 1);
                }
                if (index + 1 <= 10) {
                    radar.append(", ").append(c).append(index + 1);
                }
            }

            String last = coords[length - 1];
            if ((char) (last.charAt(0) + 1) <= 'J') {
                radar.append(", ").append((char) (last.charAt(0) + 1)).append(last.substring(1));
            }
        }
    }

    public boolean isTouched(String point) {
        for (String coord : coords) {
            if (coord.equals(point)) {
                if (impactPoint.length() == 0) {
                    impactPoint.append(point);
                } else {
                    if (impactPoint.indexOf(point) == -1) {
                        impactPoint.append(", ").append(point);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean isDestroyed() {
        return impactPoint.toString().split(", ").length == length;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getLength() {
        return length;
    }

    public String[] getCoords() {
        return coords;
    }

    public String getRadar() {
        return radar.toString();
    }

    @Override
    public String toString() {
        return "Ship{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", length=" + length +
                ", coords= " + Arrays.toString(coords) +
                ", radar= " + radar.toString() +
                '}';
    }

    enum Direction {
        HORIZONTAL, VERTICAL
    }
}
