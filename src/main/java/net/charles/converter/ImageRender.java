package net.charles.converter;

import net.charles.ImageConverter;
import net.charles.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRender {
    private final PixelConverter converter = ImageConverter.getInstance().getPixelConverter();
    private final Player player;
    private final Location location;
    private BufferedImage image;
    private Raster raster;
    private int[] rgb = new int[3];
    private List<Pair<Location, Material>> imageForm;

    /*
    If you are reading this, I shall say hello to you o/
     */

    public ImageRender(Player player, String image) {
        this.player = player;
        this.location = player.getLocation();
        try {
            File file = new File(ImageConverter.getInstance().getDataFolder().getAbsolutePath() + image);
            this.image = ImageIO.read(file);
            this.raster = this.image.getRaster();
        } catch (IOException exception) {
            player.sendMessage(ChatColor.RED + "Unable to find image");
            exception.printStackTrace();
        }
    }

    public void render() {
        new BukkitRunnable() {
            int index = 0;
            @Override
            public void run() {
                int i = 0;
                try {
                    while (i <= 8) {
                        Pair<Location, Material> pair = imageForm.get(index);
                        Block block = pair.getLeft().getBlock();
                        block.setType(pair.getRight());
                        i++;
                        index++;
                    }
                } catch (Exception e) {
                    cancel();
                }
            }
        }.runTaskTimer(ImageConverter.getInstance(), 1, 1);
    }

    private void convert() {
        imageForm = new ArrayList<>();
        player.sendMessage(ChatColor.GREEN + "Starting render");

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                try {
                    imageForm.add(Pair.of(location.clone().add(x, 0, y),
                            converter.getBlockFromPixelRGB(raster.getPixel(x, y, rgb))));
                } catch (ArrayIndexOutOfBoundsException exception) {
                    break;
                }
            }
        }
    }
}
