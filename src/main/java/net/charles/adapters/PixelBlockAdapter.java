package net.charles.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.charles.converter.PixelConverter;

import java.io.IOException;

public class PixelBlockAdapter extends TypeAdapter<PixelConverter.PixelBlock> {
    @Override
    public void write(JsonWriter jsonWriter, PixelConverter.PixelBlock pixelBlock) throws IOException {

    }

    @Override
    public PixelConverter.PixelBlock read(JsonReader reader) throws IOException {
        PixelConverter.PixelBlock pixelBlock = new PixelConverter.PixelBlock();
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "material":
                    pixelBlock.setMaterial(reader.nextName());
                    break;
                case "r":
                    pixelBlock.setR(reader.nextInt());
                    break;
                case "g":
                    pixelBlock.setG(reader.nextInt());
                    break;
                case "b":
                    pixelBlock.setB(reader.nextInt());
                    break;
            }
        }
        reader.endObject();
        return pixelBlock;
    }
}
