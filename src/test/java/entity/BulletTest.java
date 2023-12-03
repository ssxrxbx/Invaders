package entity;


import engine.DrawManager.SpriteType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BulletTest {

  private static final int POSITION_X = 0;
  private static final int POSITION_Y = 0;
  private static final int[] TEST_SPEED = new int[]{-100, -1, 0, 1, 100};

  private static Bullet[] testBullets = new Bullet[]{new Bullet(POSITION_X, POSITION_Y, TEST_SPEED[0]),
      new Bullet(POSITION_X, POSITION_Y, TEST_SPEED[1]), new Bullet(POSITION_X, POSITION_Y, TEST_SPEED[2]),
      new Bullet(POSITION_X, POSITION_Y, TEST_SPEED[3]), new Bullet(POSITION_X, POSITION_Y, TEST_SPEED[4])};


  @Test
  @DisplayName("Bullet Sprite Type 지정 (Speed >= 0 : EnemyBullet, Speed < 0 : Bullet)")
  void setSprite() {
    Assertions.assertEquals(SpriteType.Bullet, testBullets[0].getSpriteType());
    Assertions.assertEquals(SpriteType.Bullet, testBullets[1].getSpriteType());
    Assertions.assertEquals(SpriteType.EnemyBullet, testBullets[2].getSpriteType());
    Assertions.assertEquals(SpriteType.EnemyBullet, testBullets[3].getSpriteType());
    Assertions.assertEquals(SpriteType.EnemyBullet, testBullets[4].getSpriteType());
  }

  @Test
  void update() {
    testBullets[0].update();
    testBullets[1].update();
    testBullets[2].update();
    testBullets[3].update();
    testBullets[4].update();
    Assertions.assertEquals(-100, testBullets[0].getSpeed());
    Assertions.assertEquals(-1, testBullets[1].getSpeed());
    Assertions.assertEquals(0, testBullets[2].getSpeed());
    Assertions.assertEquals(1, testBullets[3].getSpeed());
    Assertions.assertEquals(100, testBullets[4].getSpeed());
  }

  @Test
  void setSpeed() {
  }

  @Test
  void getSpeed() {
  }
}