package examplemod.client;

import com.labymedia.ultralight.UltralightRenderer;

import examplemod.client.ultralight.UltraLight;
import org.lwjgl.input.Keyboard;



import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import examplemod.client.ultralight.HTMLGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;


@Mod(
        modid = ExampleMod.MODID,
        name = "Example Mod",
        version = ExampleMod.VERSION,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class ExampleMod {
   public static final String MODID = "examplemod";
   public static final String VERSION = "1";


   public HTMLGui HTMLGui;
   private UltralightRenderer renderer;

   @Mod.EventHandler
   public void init(FMLInitializationEvent event) {
      MinecraftForge.EVENT_BUS.register(this);
      UltraLight.init();
      renderer = UltraLight.getRenderer();


      HTMLGui = new HTMLGui(renderer, "file://ultralight/public/index.html", 1920, 1080);



   }


   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      renderer.update();
      renderer.render();

      if (isPlayerInGame()) {
         //right shift
         if (Keyboard.isKeyDown(54) && Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().thePlayer.playSound(MODID + ":sound.enable", 10, 1);
            Minecraft.getMinecraft().displayGuiScreen(HTMLGui);
         }

      }
   }
   public static boolean isPlayerInGame() {
      Minecraft mc = Minecraft.getMinecraft();
      return mc.thePlayer != null && mc.theWorld != null;
   }

}