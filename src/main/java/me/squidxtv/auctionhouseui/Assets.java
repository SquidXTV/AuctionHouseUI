package me.squidxtv.auctionhouseui;

import me.squidxtv.frameui.util.BufferedImageUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum Assets {
    // auction_browse
    ARROW_DOWN(),
    ARROW_UP(),
    CATEGORY_SELECTED(),
    CATEGORY_UNSELECTED(),
    ITEM_SELECTED(),
    ITEM_UNSELECTED(),
    REFRESH_BUTTON(),
    RESET(),
    SCROLL(),
    SEARCH(),
    // auction_create
    // auction_manage
    // all
    BACKGROUND(),
    CLOSE_BUTTON(),
    WINDOW_SELECTED(),
    WINDOW_UNSELECTED();

    private @Nullable BufferedImage image;

    Assets() {
        Logger logger = JavaPlugin.getPlugin(AuctionHouseUI.class).getLogger();

        try(InputStream in = getClass().getResourceAsStream(name().toLowerCase() + ".png")) {
            if (in == null) {
                logger.warning("Failed to load the texture for " + name());
                image = null;
                return;
            }
            image = ImageIO.read(in);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to load the texture for " + name(), e);
            image = null;
        }
    }

    public @Nullable BufferedImage getImage() {
        if (image == null) {
            return null;
        }
        return BufferedImageUtil.deepCopy(image);
    }
}
