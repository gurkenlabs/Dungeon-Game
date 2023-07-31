package de.dungeongame.ui;


import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Rotation;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SkinSelectionScreen extends Screen {

  private ImageComponent skinSelector;
  private ImageComponent skinSelectorBackground;
  private ImageComponent previousProfession, nextProfession;
  private ImageComponent previousSkin, nextSkin;
  private CreatureAnimationController playerAnims;

  private static BufferedImage daggerArrowDown = Resources.images()
    .get("resources/images/hud/daggerarrow.png");

  private static BufferedImage goldFrame = Resources.images()
    .get("resources/images/hud/goldframe.png");

  public SkinSelectionScreen() {
    super("skins");
  }


  @Override
  protected void initializeComponents() {
    this.playerAnims = new CreatureAnimationController<>(Player.instance(), true);

    double selectorWidth = Game.window().getResolution().getWidth() * 2 / 10d;
    double selectorHeight = Game.window().getResolution().getHeight() * 4 / 10d;

    double selectorBoxLength = Game.window().getResolution().getWidth() * 3 / 10d;

    double centerX = Game.window().getCenter().getX();
    double centerY = Game.window().getCenter().getY();
    double longButtonEdge = Game.window().getResolution().getWidth() * 1 / 20d;
    double shortButtonEdge = Game.window().getResolution().getHeight() * 1 / 20d;
    double skinButtonX = centerX - longButtonEdge / 2d;
    double professionButtonY = centerY - longButtonEdge / 2d;

    this.skinSelector = new ImageComponent(centerX - selectorWidth / 2d,
      centerY - selectorHeight / 2d, selectorWidth, selectorHeight);
    this.skinSelectorBackground = new ImageComponent(centerX - selectorBoxLength / 2d,
      centerY - selectorBoxLength / 2d, selectorBoxLength, selectorBoxLength);

    previousProfession = new ImageComponent(skinSelectorBackground.getX() - shortButtonEdge,
      professionButtonY,
      shortButtonEdge, longButtonEdge);
    nextProfession = new ImageComponent(skinSelectorBackground.getX() + selectorBoxLength, professionButtonY,
      shortButtonEdge, longButtonEdge);

    previousSkin = new ImageComponent(skinButtonX,
      centerY - skinSelectorBackground.getHeight() / 2d - shortButtonEdge,
      longButtonEdge, shortButtonEdge);
    nextSkin = new ImageComponent(skinButtonX,
      centerY + skinSelectorBackground.getHeight() / 2d,
      longButtonEdge, shortButtonEdge);

    previousProfession.onClicked(c -> previousProfession());
    nextProfession.onClicked(c -> nextProfession());

    previousSkin.onClicked(c -> previousSkin());
    nextSkin.onClicked(c -> nextSkin());

    BufferedImage scaledDaggerDown = Imaging.scale(daggerArrowDown, (int) longButtonEdge,
      (int) shortButtonEdge, true);
    BufferedImage scaledDaggerUp = Imaging.rotate(scaledDaggerDown, Rotation.ROTATE_180);
    BufferedImage scaledDaggerLeft = Imaging.scale(
      Imaging.rotate(daggerArrowDown, Rotation.ROTATE_90), (int) shortButtonEdge,
      (int) longButtonEdge);
    BufferedImage scaledDaggerRight = Imaging.rotate(scaledDaggerLeft, Rotation.ROTATE_180);

    BufferedImage scaledBG = Imaging.scale(goldFrame, (int) selectorBoxLength,
      (int) selectorBoxLength, true);
    skinSelectorBackground.setImage(scaledBG);

    previousSkin.setImage(scaledDaggerUp);
    nextSkin.setImage(scaledDaggerDown);
    previousProfession.setImage(scaledDaggerLeft);
    nextProfession.setImage(scaledDaggerRight);

    getComponents().add(skinSelectorBackground);
    getComponents().add(skinSelector);
    getComponents().add(previousProfession);
    getComponents().add(nextProfession);

    getComponents().add(previousSkin);
    getComponents().add(nextSkin);
  }

  @Override
  public void prepare() {
    super.prepare();
    Game.loop().attach(playerAnims);

    playAnim();
  }

  @Override
  public void render(final Graphics2D g) {
    super.render(g);
  }

  @Override
  public void suspend() {
    super.suspend();
  }

  private void nextProfession() {
    Player.instance().setProfession(Player.instance().getProfession().next());
    playAnim();
  }

  private void previousProfession() {
    Player.instance().setProfession(Player.instance().getProfession().previous());
    playAnim();
  }

  private void nextSkin() {
    Player.instance().setSkin(Player.instance().getSkin().next());
    playAnim();
  }

  private void previousSkin() {
    Player.instance().setSkin(Player.instance().getSkin().previous());
    playAnim();
  }

  private void playAnim() {
    if (!playerAnims.hasAnimation(getCurrentAnimationName())) {
      playerAnims.add(new Animation(Resources.spritesheets().get(getCurrentAnimationName()), true));
    }
    playerAnims.play(getCurrentAnimationName());
    playerAnims.getCurrent().onKeyFrameChanged(
      (l, c) ->
        skinSelector.setImage(playerAnims.getCurrentImage((int) skinSelector.getWidth(),
          (int) skinSelector.getHeight()))
    );
  }

  private String getCurrentAnimationName() {
    return String.format("%s-idle-down", Player.instance().getSpritesheetName());
  }
}
