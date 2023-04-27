package examplemod.client.ultralight;

import com.labymedia.ultralight.UltralightJava;
import com.labymedia.ultralight.UltralightLoadException;
import com.labymedia.ultralight.UltralightPlatform;
import com.labymedia.ultralight.UltralightRenderer;
import com.labymedia.ultralight.config.FontHinting;
import com.labymedia.ultralight.config.UltralightConfig;
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil;
import com.labymedia.ultralight.os.OperatingSystem;
import examplemod.client.utils.ResourceManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class UltraLight {
    private static UltralightRenderer renderer;

    private UltraLight() {}
    public static void init() {
        try {
            ResourceManager.loadUltralight();

            String[] libs = new String[] {
                    "glib-2.0-0",
                    "gobject-2.0-0",
                    "gmodule-2.0-0",
                    "gio-2.0-0",
                    "gstreamer-full-1.0",
                    "gthread-2.0-0"
            };
            Path natives = ResourceManager.binDir.toPath();
            OperatingSystem os = OperatingSystem.get();
            for(String lib : libs) {
                System.load(natives.resolve(os.mapLibraryName(lib)).toAbsolutePath().toString());
            }
            UltralightJava.load(natives);
            UltralightGPUDriverNativeUtil.load(natives);
        } catch (URISyntaxException | UltralightLoadException | IOException e) {
            throw new RuntimeException(e);
        }



        UltralightPlatform platform = UltralightPlatform.instance();
        platform.setConfig(
                new UltralightConfig()
                        .fontHinting(FontHinting.SMOOTH)
        );
        platform.usePlatformFontLoader();
        platform.usePlatformFileSystem(ResourceManager.ultraLightDir.getAbsolutePath());
        renderer = UltralightRenderer.create();
    }

    public static UltralightRenderer getRenderer() {
        return renderer;
    }

}
