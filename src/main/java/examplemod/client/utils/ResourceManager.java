package examplemod.client.utils;

import com.labymedia.ultralight.UltralightJava;
import com.labymedia.ultralight.UltralightLoadException;
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil;
import net.minecraft.client.Minecraft;

import examplemod.client.ExampleMod;


import java.io.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class ResourceManager {
    private ResourceManager(){}

    public static final File modDir = new File(Minecraft.getMinecraft().mcDataDir, ExampleMod.MODID);
    public static final File ultraLightDir = new File(modDir, "ultralight");
    public static final File binDir = new File(ultraLightDir, "bin");
    public static final File resourceDir = new File(ultraLightDir, "resources");

    public static void loadUltralight() throws URISyntaxException, UltralightLoadException, IOException {
        String rootPath = "/assets/" + ExampleMod.MODID;

        URI uri = ResourceManager.class.getResource(rootPath).toURI();
        try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {


            copyFolder(fileSystem.getPath(rootPath + "/public"), new File(ultraLightDir, "public").toPath());
            copyFolder(fileSystem.getPath(rootPath + "/bin"), binDir.toPath());
            copyFolder(fileSystem.getPath(rootPath + "/resources"), resourceDir.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        UltralightJava.extractNativeLibrary(binDir.toPath());
        UltralightGPUDriverNativeUtil.extractNativeLibrary(binDir.toPath());

        UltralightJava.load(binDir.toPath());
        UltralightGPUDriverNativeUtil.load(binDir.toPath());
    }


    public static void copyFolder(Path source, Path target, CopyOption... options)
            throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir).toString()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.copy(file, target.resolve(source.relativize(file).toString()), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }


}