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

    // private Position tile;

    private List<Wall> walls;
    private List<Coin> coins;

    private List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        hero = new Hero(width/2, height/2);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    /*public boolean nheroTile(int x, int y) {
        Position heroic = hero.getPosition();
        if ((heroic.getX() == x) && (heroic.getY() == y)) {
            return false;
        }
        return true;
    }*/

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
        verifyMonsterCollisions();
        moveMonsters();
        verifyMonsterCollisions();
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

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        }
        return monsters;
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position monsterPosition = monster.move();
            if (canHeroMove(monsterPosition))
                monster.setPosition(monsterPosition);
        }
    }


    private void verifyMonsterCollisions() {
        for (Monster monster : monsters)
            if (hero.getPosition().equals(monster.getPosition())) {
                System.out.println("Fatality!");
                System.exit(0);
            }
    }

    private List<Coin> createCoins() {
        int on = 0;
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Position> full = new ArrayList<>();
        full.add(hero.getPosition());
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            for (int j = 0; j < full.size(); j++) {
                if ((x == full.get(j).getX()) && (y == full.get(j).getY())) {
                    on = 1;
                }
                else {
                    continue;
                }
            }
            if (on == 0) {
                coins.add(new Coin(x, y));
            }
            else {
                i--;
            }
        }
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

        for (Monster monster : monsters) {
            monster.draw(graphics);
        }
    }
}
