package de.dungeongame.core;

import de.dungeongame.entities.Player;
import de.dungeongame.entities.Rat;
import de.dungeongame.entities.StoryChar;
import de.dungeongame.entities.props.BreakableWall;
import de.dungeongame.entities.props.ExplosiveBarrel;
import de.dungeongame.entities.props.Spikes;
import de.dungeongame.logic.BossSpawner;
import de.dungeongame.logic.GameManager;
import de.dungeongame.ui.InGameScreen;
import de.dungeongame.ui.MenuScreen;
import de.dungeongame.ui.SkinSelectionScreen;
import de.dungeongame.ui.UserInput;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.configuration.DisplayMode;
import de.gurkenlabs.litiengine.environment.CreatureMapObjectLoader;
import de.gurkenlabs.litiengine.environment.PropMapObjectLoader;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.gui.GuiProperties;
import de.gurkenlabs.litiengine.resources.Resources;
import java.awt.BasicStroke;

public class Main {

  public static void main(String[] args) {

    Game.init(args);
    Game.window().setTitle("LegendofLogolas");
    Game.window().setIcon(Resources.images().get("misc/icon.png"));

    UserInput.Input();

    // load the Liti Library
    Resources.load("game_v4.litidata");
    GameManager.init();
    BossSpawner.init();
    // set the Scale of the Game: pixles * X

    Game.graphics().setBaseRenderScale(3);

    GuiProperties.getDefaultAppearance().setBorderStyle(new BasicStroke(1f));
    GuiProperties.getDefaultAppearanceHovered().setBorderStyle(new BasicStroke(2f));
    GuiProperties.getDefaultAppearanceDisabled().setBorderStyle(new BasicStroke(1f));

    // create new Screen from class InGameScreen()
    Game.screens().add(new InGameScreen());
    Game.screens().add(new MenuScreen());
    Game.screens().add(new SkinSelectionScreen());
    Camera c1 = new Camera();

    c1.getViewport();

    Game.audio().setListenerLocationCallback(e -> Player.instance().getCenter());
    Game.audio().setMaxDistance(1000);

    PropMapObjectLoader.registerCustomPropType(ExplosiveBarrel.class);
    PropMapObjectLoader.registerCustomPropType(BreakableWall.class);
    PropMapObjectLoader.registerCustomPropType(Spikes.class);
    CreatureMapObjectLoader.registerCustomCreatureType(Rat.class);
    CreatureMapObjectLoader.registerCustomCreatureType(StoryChar.class);

    // loads the inserted map path
    // file must be a tmx file, and loaded in /src
    Game.world().loadEnvironment("titlescreen");
    Game.config().graphics().setDisplayMode(DisplayMode.BORDERLESS);

    Game.screens().display("MENU");
    Game.world().camera().setFocus(Game.world().environment().getCenter());

    Game.start();
  }
}
