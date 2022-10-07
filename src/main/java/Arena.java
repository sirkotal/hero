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

import java.util.ArrayList;
import java.util.List;

import java.util.Random;


import java.io.IOException;

public class Arena {
    private int width;
    private int height;

    private Hero hero;

    private List<Wall> walls;
    private List<Coin> coins;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        hero = new Hero(width/2, height/2);
        this.walls = createWalls();
        this.coins = createCoins();
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

        retrieveCoins();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c,0));
            walls.add(new Wall(c,height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0,r));
            walls.add(new Wall(width - 1,r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }

    private void retrieveCoins() {
        for (Coin coin : coins)
            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                break;
            }
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

        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));   // #336699
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls) {
            wall.draw(graphics);
        }
        for (Coin coin : coins) {
            coin.draw(graphics);
        }
    }
}
