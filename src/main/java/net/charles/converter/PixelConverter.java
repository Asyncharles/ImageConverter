package net.charles.converter;

import net.charles.ImageConverter;
import net.charles.Pair;
import org.bukkit.Material;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class PixelConverter {
    private PixelList pixelList;

    public PixelConverter(String pathToBlockFile) {
        try (InputStream stream = new FileInputStream(pathToBlockFile)) {
            String result = new BufferedReader(new InputStreamReader(stream))
                    .lines().collect(Collectors.joining("\n"));
            pixelList = ImageConverter.GSON.fromJson(result, PixelList.class);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot file block file. I cannot function properly! Shutting down.");
            ImageConverter.getInstance().getPluginLoader().disablePlugin(ImageConverter.getInstance());
        }
    }

    public Material getBlockFromPixelRGB(int[] rgb) {
        final int r = rgb[0], g = rgb[1], b = rgb[2];

        PixelBlock closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;

        for (PixelBlock pixelBlock : pixelList.pixelBlockList) {
            mse = pixelBlock.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = pixelBlock;
            }
        }

        return closestMatch != null ? closestMatch.getMaterial() : Material.WHITE_TERRACOTTA;
    }

    public static class PixelList {
        private final List<PixelBlock> pixelBlockList;

        public PixelList(List<PixelBlock> pixelBlockList) {
            this.pixelBlockList = pixelBlockList;
        }
    }

    public static class PixelBlock {
        private Material material;
        private int r, g, b;

        public PixelBlock() {}

        public PixelBlock(Material material, int r, int g, int b) {
            this.material = material;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int computeMSE(int pixR, int pixG, int pixB) {
            return (int) (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + (pixB - b)
                    * (pixB - b)) / 3);
        }

        public Material getMaterial() {
            return material;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }

        public void setMaterial(String material) {
            this.material = Material.valueOf(material);
        }

        public void setR(int r) {
            this.r = r;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setB(int b) {
            this.b = b;
        }
    }
}
