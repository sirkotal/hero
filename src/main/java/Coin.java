import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class Coin extends Element{
    public Coin(int x, int y) {
        super(x,y);
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "*");
    }
}
