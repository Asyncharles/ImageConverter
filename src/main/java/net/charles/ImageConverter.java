package net.charles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.charles.adapters.PixelBlockAdapter;
import net.charles.commands.ConvertCommand;
import net.charles.converter.PixelConverter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ImageConverter extends JavaPlugin {
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(PixelConverter.PixelBlock.class, new PixelBlockAdapter()).create();
    private static ImageConverter instance;
    private PixelConverter pixelConverter;

    @Override
    public void onEnable() {
        instance = this;
        pixelConverter = new PixelConverter(getDataFolder().getAbsolutePath() + "/blocks.json");
        Objects.requireNonNull(getCommand("convert")).setExecutor(new ConvertCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ImageConverter getInstance() {
        return instance;
    }

    public PixelConverter getPixelConverter() {
        return pixelConverter;
    }
}
