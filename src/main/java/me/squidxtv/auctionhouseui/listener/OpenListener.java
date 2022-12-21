package me.squidxtv.auctionhouseui.listener;

import me.squidxtv.auctionhouseui.Assets;
import me.squidxtv.auctionhouseui.AuctionHouseUI;
import me.squidxtv.frameui.core.Screen;
import me.squidxtv.frameui.core.ScreenManager;
import me.squidxtv.frameui.core.content.Alignment;
import me.squidxtv.frameui.core.content.Component;
import me.squidxtv.frameui.core.content.Text;
import me.squidxtv.frameui.util.Direction;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.*;
import java.util.List;

public class OpenListener implements Listener {

    private static final int DISTANCE = 2;

    private static final Font FONT = Font.getFont("Roboto");

    private final @NotNull AuctionHouseUI plugin;
    private final @NotNull ScreenManager screenManager;

    public OpenListener(@NotNull AuctionHouseUI plugin) {
        this.plugin = plugin;
        this.screenManager = ScreenManager.getInstance();
    }

    @EventHandler
    public void openAuctionHouse(@NotNull PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        for (Screen screen : screenManager.getByPlayer(plugin, player)) {
            screenManager.unregister(plugin, screen);
        }

        Screen screen = createAuctionHouse(player);
        if (screen == null) {
            // TODO: 19.12.2022 logging
        }

    }

    private @Nullable Screen createAuctionHouse(@NotNull Player player) {
        Direction direction = getDirection(player);
        if (direction == null) {
            return null;
        }
        Location topLeft = getLocation(player, direction);
        BufferedImage background = Assets.BACKGROUND.getImage();
        World world = player.getWorld();

        Screen screen = new Screen.Builder(topLeft, background, world, List.of(player))
                .direction(direction)
                .width(5)
                .height(3).build();

        initializeAuctionBrowser(screen);

        return screen;
    }

    private void initializeAuctionBrowser(@NotNull Screen screen) {
        screen.clearContent();

        Component browseWindow = new Component(Assets.WINDOW_SELECTED.getImage(), 235, 19, 115, 28);
        browseWindow.add(new Text("BROWSE", FONT, Alignment.CENTER));

        Component createWindow = new Component(Assets.WINDOW_UNSELECTED.getImage(), 353, 19, 115, 28);
        createWindow.add(new Text("CREATE", FONT, Alignment.CENTER));
        createWindow.setClickAction((component, clickX, clickY) -> initializeAuctionCreator(screen));

        Component manageWindow = new Component(Assets.WINDOW_UNSELECTED.getImage(), 471, 19, 115, 28);
        manageWindow.add(new Text("MANAGE", FONT, Alignment.CENTER));
        manageWindow.setClickAction((component, clickX, clickY) -> initializeAuctionManager(screen));

        Component closeButton = new Component(Assets.CLOSE_BUTTON.getImage(), 596, 30, 17, 17);
        closeButton.setClickAction((component, i, i1) -> screen.close());

        Component categoryAll = new Component(Assets.CATEGORY_SELECTED.getImage(), 30, 171, 114, 19);
        categoryAll.add(new Text("ALL", FONT, Alignment.CENTER));

        Component categoryWeapons = new Component(Assets.CATEGORY_UNSELECTED.getImage(), x, y, 114, 19);
        categoryAll.add(new Text("", FONT, Alignment.CENTER));

        Component categoryTools = new Component(Assets.CATEGORY_UNSELECTED.getImage(), x, y, 114, 19);
        categoryAll.add(new Text("", FONT, Alignment.CENTER));

        Component categoryArmor = new Component(Assets.CATEGORY_UNSELECTED.getImage(), x, y, 114, 19);
        categoryAll.add(new Text("", FONT, Alignment.CENTER));

        Component categoryConsumables = new Component(Assets.CATEGORY_UNSELECTED.getImage(), x, y, 114, 19);
        categoryAll.add(new Text("", FONT, Alignment.CENTER));

        Component categoryBlocks = new Component(Assets.CATEGORY_UNSELECTED.getImage(), x, y, 114, 19);
        categoryAll.add(new Text("", FONT, Alignment.CENTER));

        Component categoryMisc = new Component(Assets.CATEGORY_UNSELECTED.getImage(), x, y, 114, 19);
        categoryAll.add(new Text("", FONT, Alignment.CENTER));

        screen.render();
    }

    private void initializeAuctionCreator(@NotNull Screen screen) {
        screen.clearContent();
        // TODO: 19.12.2022 implement
        screen.render();
    }

    private void initializeAuctionManager(@NotNull Screen screen) {
        screen.clearContent();
        // TODO: 19.12.2022 implement
        screen.render();
    }

    private @NotNull Location getLocation(@NotNull Player player, @NotNull Direction direction) {
        Location location = player.getLocation();
        int x = (direction.getMultiplierX() != 0 ? 1 : 0);
        int z = (direction.getMultiplierX() == 0 ? 1 : 0);
        if (direction == Direction.SOUTH) {
            x *= -1;
        }
        if (direction == Direction.WEST) {
            z *= -1;
        }
        Location block = location.add(direction.getNormal().multiply(-DISTANCE));
        block.add(x * 2.0, 0, z * 2.0);
        block.add(0, 2, 0);
        return block;
    }

    private @Nullable Direction getDirection(@NotNull Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0) {
            rotation += 360;
        }

        if ((225 <= rotation) && (rotation <= 315)) {
            return Direction.NORTH;
        }
        if ((315 <= rotation) || (rotation <= 45)) {
            return Direction.EAST;
        }
        if ((45 <= rotation) && (rotation <= 135)) {
            return Direction.SOUTH;
        }
        if (135 <= rotation) {
            return Direction.WEST;
        }

        return null;
    }

}
