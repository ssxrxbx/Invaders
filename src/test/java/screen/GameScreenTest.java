package screen;

import static org.junit.jupiter.api.Assertions.*;

import engine.GameSettings;
import engine.GameState;
import entity.Bullet;
import entity.EnemyShipFormation;
import entity.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** 테스트 클래스: GameScreenTest */
class GameScreenTest {

  /** 게임 화면의 너비 */
  private static final int WIDTH = 448;
  /** 게임 화면의 높이 */
  private static final int HEIGHT = 520;
  /** 초당 프레임 수 */
  private static final int FPS = 60;
  /** 최대 목숨 수 */
  private static final int MAX_LIVES = 3;
  /** 테스트 레벨 1의 설정 */
  private static final GameSettings SETTINGS_LEVEL_1 =
      new GameSettings(5, 4, 60, 2000);
  /** 테스트 레벨 2의 설정 */
  private static final GameSettings SETTINGS_LEVEL_2 =
      new GameSettings(10, 10, 40, 10);
  /** 테스트 레벨 3의 설정 */
  private static final GameSettings SETTINGS_LEVEL_3 =
      new GameSettings(1, 1, 30, 10000);
  /** 게임 상태 */
  private static GameState gameState;
  /** 게임 설정 리스트 */
  private static List<GameSettings> gameSettings;
  /** 테스트할 게임 화면 */
  private static GameScreen gameScreen;
  /** 현재 게임 설정 */
  private static GameSettings currentGameSettings;
  /** 보너스 목숨 여부 */
  private static boolean bonusLife;
  /** 테스트 레벨 */
  private static int testLevel;

  /** 테스트 실행 전 설정 초기화 */
  @BeforeAll
  static void setUp() {
    gameSettings = new ArrayList<GameSettings>();
    gameSettings.add(SETTINGS_LEVEL_1);
    gameSettings.add(SETTINGS_LEVEL_2);
    gameSettings.add(SETTINGS_LEVEL_3);
    testLevel = 1;
  }

  /** 각 테스트 메소드 실행 전 GameScreen 초기화 */
  @BeforeEach
  void initialize() {
    bonusLife = false;
    gameState = new GameState(testLevel, 0, MAX_LIVES, 0, 0);
    currentGameSettings = gameSettings.get(gameState.getLevel() - 1);
    gameScreen = new GameScreen(gameState, currentGameSettings, bonusLife, WIDTH, HEIGHT, FPS);
    gameScreen.initialize();
    testLevel++;
  }

  @Test
  @DisplayName("initialize 메소드 테스트: GameScreen이 객체들을 잘 생성하는지 확인")
  void testGameScreenInitialization() {

    /** initialize 후 */
    assertNotNull(gameScreen); // gameScreen이 null이면 안됨.
    assertNotNull(gameScreen.getShip()); // Ship 생성여부확인
    assertNotNull(gameScreen.getEnemyShipFormation()); // EnemyShipFormation 생성여부확인
    assertEquals(WIDTH / 2, gameScreen.getShip().getPositionX()); // Ship이 지정된 위치에 생성되었는지 확인
    assertEquals(HEIGHT - 30, gameScreen.getShip().getPositionY()); // Ship이 지정된 위치에 생성되었는지 확인
    assertFalse(gameScreen.getEnemyShipFormation().isEmpty()); // EnemyShipFormation이 비어있으면 안됨


    /** bonusLife == false인 상황*/
    assertEquals(MAX_LIVES, gameScreen.getLives()); // lives == MAX_LIVES


    /** bonusLife == true인 상황*/
    bonusLife = true;
    gameScreen = new GameScreen(gameState, currentGameSettings, bonusLife, WIDTH, HEIGHT, FPS);
    gameScreen.initialize();
    assertEquals(MAX_LIVES+1, gameScreen.getLives()); // // lives == MAX_LIVES+1
  }

  @Test
  @DisplayName("update 메소드 테스트1: EnemyShip이 다 죽어서 게임이 끝나는 경우")
  void testGameScreenUpdate() {
    /** gameScreen initialize*/
    assertFalse(gameScreen.isLevelFinished()); // 생성된 직후에는 LevelFinished 값이 False.

    /** 가상 게임 실행 (ship이 EnemyShip을 다 파괴할 때까지 반복)*/
    Set<Bullet> bullets = gameScreen.getBullets();
    Ship ship = gameScreen.getShip();
    EnemyShipFormation enemyshipFormation = gameScreen.getEnemyShipFormation();
    while (!enemyshipFormation.isEmpty()) {
      ship.shoot(bullets);
      gameScreen.update();
    }

    /** 가상 게임 종료*/
    assertTrue(gameScreen.isLevelFinished()); // 가상 게임 종료 후에는 LevelFinished 값이 True.
  }

  @Test
  @DisplayName("update 메소드 테스트2: Ship이 체력을 다 잃어서 게임이 끝나는 경우")
  void testGameScreenUpdate2() {
    /** gameScreen initialize*/
    assertFalse(gameScreen.isLevelFinished()); // 생성된 직후에는 LevelFinished 값이 False.

    /** 가상 게임 실행 (ship이 EnemyShip을 다 파괴할 때까지 반복)*/
    while (gameScreen.getLives() != 0) {
      gameScreen.update();
    }

    /** 가상 게임 종료*/
    assertTrue(gameScreen.isLevelFinished()); // 가상 게임 종료 후에는 LevelFinished 값이 True.
  }
}
