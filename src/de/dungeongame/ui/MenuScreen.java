package de.dungeongame.ui;

import de.dungeongame.logic.GameManager;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuScreen extends Screen implements IUpdateable {

  public static final BufferedImage Title = Imaging.scale(Resources.images().get("Menu/Title.png"),
    6.0);
  public long lastPlayed;
  private KeyboardMenu mainMenu;

  public MenuScreen() {
    super("Menu");

  }

  private void exit() {
    System.exit(0);

  }

  @Override
  protected void initializeComponents() {
    final double centerX = Game.window().getResolution().getWidth() / 2.0;
    final double centerY = Game.window().getResolution().getHeight() * 1 / 2;
    final double buttonWidth = 450;

    this.mainMenu = new KeyboardMenu(centerX - buttonWidth / 2, centerY * 1.1, buttonWidth,
      centerY / 2, "Play", "Load", "resources/Collectables ", "Exit");
    this.getComponents().add(this.mainMenu);

    this.mainMenu.onConfirm(c -> {
      switch (c.intValue()) {
        case 0:
          this.startGame();

          break;
        case 2:
          this.loadSkinSelection();
          break;
        case 3:
          this.exit();
          break;
        default:
          break;
      }
    });
  }

  @Override
  public void prepare() {
    this.mainMenu.setEnabled(true);
    super.prepare();
    Game.loop().attach(this);

    Game.window().getRenderComponent().setBackground(Color.BLACK);
    Game.graphics().setBaseRenderScale(8f * Game.window().getResolutionScale());
    this.mainMenu.incFocus();
    Game.world().loadEnvironment("titlescreen");
  }

  @Override
  public void render(final Graphics2D g) {
    Game.world().environment().render(g);
    super.render(g);
    final double centerX = Game.window().getResolution().getWidth() / 2.0;
    final double TitleX = centerX - Title.getWidth() / 2;
    final double TitleY = 200;
    ImageRenderer.render(g, Title, TitleX, TitleY);
  }

  @Override
  public void update() {
    if (this.lastPlayed == 0) {
      this.lastPlayed = Game.loop().getTicks();
    }
  }

  private void startGame() {
    this.mainMenu.setEnabled(false);
    Game.window().getRenderComponent().fadeOut(1500);
    Game.audio().playSound(Resources.sounds().get("dragon.wav"));
    Game.loop().perform(3500, () -> {
      Game.screens().display("INGAME-SCREEN");
      Game.window().getRenderComponent().fadeIn(1500);
      Game.world().loadEnvironment("atrium");
      GameManager.spawnPlayer(Game.world().getEnvironment("atrium"), null);
      GameManager.spawnStoryChar(1);

      //beim ersten mal kommt er nicht aus einer Tür
      Game.world().camera().setFocus(Game.world().environment().getCenter());
      Game.graphics().setBaseRenderScale(3);
    });
  }

  public void loadSkinSelection() {
    this.mainMenu.setEnabled(false);
    Game.window().getRenderComponent().fadeOut(1000);
    Game.loop().perform(1500, () -> {
        Game.screens().display("skins");
        Game.window().getRenderComponent().fadeIn(1000);
      }
    );
  }

  @Override
  public void suspend() {
    super.suspend();
    Game.loop().detach(this);
  }


}

