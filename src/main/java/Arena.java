import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;


import java.io.IOException;

public class Arena {
    private int width;
    private int height;

    private Hero hero;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        Position position = new Position(width/2, height/2);
        hero = new Hero(position);
    }

    public void processKey(KeyStroke key) throws IOException {
        System.out.println(key);
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'w')
            moveHero(hero.moveUp());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 's')
            moveHero(hero.moveDown());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'd')
            moveHero(hero.moveRight());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'a')
            moveHero(hero.moveLeft());
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
    }

    private boolean canHeroMove(Position position) {
        if (position.getX() < 0) return false;
        if (position.getY() < 0) return false;
        if (position.getX() > width - 1) return false;
        if (position.getY() > height - 1) return false;

        return true;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));   // #336699
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
    }
}
