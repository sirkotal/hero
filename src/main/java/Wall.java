import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Wall extends Element {
    public Wall(int x, int y) {
        super(x,y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#D3D3D3"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), " ");
    }
}
