import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) {
        super(x, y);
    }

    public Position move() {
        switch (new Random().nextInt(6)) {
            case 0:
                return new Position(position.getX() + 1, position.getY());
            case 1:
                return new Position(position.getX(), position.getY() + 1);
            case 2:
                return new Position(position.getX() - 1, position.getY());
            case 3:
                return new Position(position.getX(), position.getY() - 1);
            case 4:
                return new Position(position.getX() - 1, position.getY() - 1);
            case 5:
                return new Position(position.getX() + 1, position.getY() + 1);
        }
        return new Position(position.getX(), position.getY());
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "A");
    }
}
