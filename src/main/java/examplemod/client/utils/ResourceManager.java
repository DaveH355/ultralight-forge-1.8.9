package examplemod.client.utils;

import com.labymedia.ultralight.UltralightJava;
import com.labymedia.ultralight.UltralightLoadException;
import com.labymedia.ultralight.gpu.UltralightGPUDriverNativeUtil;
import net.minecraft.client.Minecraft;

import examplemod.client.ExampleMod;
import net.minecraftforge.fml.common.ProgressManager;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.utils.CountingInputStream;
import org.apache.commons.io.FileUtils;


import java.io.*;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class ResourceManager {
    private ResourceManager(){}

    private static final String  LIBRARY_VERSION = "b8daecd";

    public static final File ultraLightDir = new File(Minecraft.getMinecraft().mcDataDir, "ultralight");
    public static final File binDir = new File(ultraLightDir, "bin");
    public static final File resourceDir = new File(ultraLightDir, "resources");

    public static void loadUltralight() throws URISyntaxException, UltralightLoadException, IOException {
        try {
            File VERSION = new File(ultraLightDir, "VERSION");
            if(VERSION.exists() && VERSION.isFile()){
                String version = new String(java.nio.file.Files.readAllBytes(VERSION.toPath()));
                if(version.equals(LIBRARY_VERSION)){
                    return;
                }
            }
            if(binDir.exists() && binDir.isDirectory()){
                FileUtils.deleteDirectory(binDir);
            }

            binDir.mkdirs();

            if(resourceDir.exists() && resourceDir.isDirectory()){
                FileUtils.deleteDirectory(resourceDir);
            }

            resourceDir.mkdirs();

            //get the os name, win for windows, linux for linux, mac for mac
            String os;
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                os = "win";
            } else if (osName.contains("mac")) {
                os = "mac";
            } else {
                os = "linux";
            }
            URL url = new URL(String.format("https://ultralight-sdk.sfo2.cdn.digitaloceanspaces.com/ultralight-sdk-%s-%s-x64.7z", LIBRARY_VERSION, os));

            File file = new File(ultraLightDir, "ultralight-sdk.7z");
            System.out.println(file.getAbsolutePath());
            System.out.println(url.toString());
            if(file.exists() && file.isFile()){
                file.delete();
            }
            if(file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.createNewFile();
            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();
            try(InputStream inputStream = url.openStream();
                FileOutputStream fos = new FileOutputStream(file);
                CountingInputStream cis = new CountingInputStream(inputStream)){
                ProgressManager.ProgressBar bar = ProgressManager.push("Downloading Ultralight", (int) (completeFileSize / 4096) + 1);

                byte[] buffer = new byte[4096];
                int len;
                while ((len = cis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);

                    int percent = (int) (cis.getBytesRead() * 100 / completeFileSize);
                    bar.step(percent + "%");
                    System.out.println("Download:  " + percent + "%");
                }

                ProgressManager.pop(bar);
            }
            ProgressManager.ProgressBar bar = ProgressManager.push("Extracting Ultralight", 15);
            // Extract the 7z file
            try(SevenZFile sevenZFile = new SevenZFile(file)){
                SevenZArchiveEntry entry;
                while ((entry = sevenZFile.getNextEntry()) != null){
                    if(entry.getName().startsWith("bin/")){
                        File dest = new File(binDir, entry.getName().substring(4));
                        if(dest.exists() && dest.isFile()){
                            dest.delete();
                        }
                        if(dest.getParentFile().exists())
                            dest.getParentFile().mkdirs();
                        dest.createNewFile();
                        bar.step(dest.getName());
                        try(FileOutputStream fos = new FileOutputStream(dest)){
                            byte[] buffer = new byte[4096];
                            int len;
                            while ((len = sevenZFile.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                        }

                    }

                    if (entry.getName().startsWith("resources/")) {
                        File dest = new File(resourceDir, entry.getName().substring(10));
                        if(dest.exists() && dest.isFile()){
                            dest.delete();
                        }
                        if(dest.getParentFile().exists())
                            dest.getParentFile().mkdirs();
                        dest.createNewFile();
                        bar.step(dest.getName());
                        try(FileOutputStream fos = new FileOutputStream(dest)){
                            byte[] buffer = new byte[4096];
                            int len;
                            while ((len = sevenZFile.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                        }
                    }
                }
            }
            ProgressManager.pop(bar);
            file.delete();
            UltralightJava.extractNativeLibrary(binDir.toPath());
            UltralightGPUDriverNativeUtil.extractNativeLibrary(binDir.toPath());

            String rootPath = "/assets/" + ExampleMod.MODID;

            URI uri = ResourceManager.class.getResource(rootPath).toURI();
            try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {


                copyFolder(fileSystem.getPath(rootPath + "/public"), new File(ultraLightDir, "public").toPath());

            } catch (IOException e) {
                e.printStackTrace();
            }

            Files.write(VERSION.toPath(), LIBRARY_VERSION.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        }catch (Exception e) {
            e.printStackTrace();
        }
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