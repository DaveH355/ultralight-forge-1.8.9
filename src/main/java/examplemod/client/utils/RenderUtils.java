package examplemod.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import java.awt.*;
import java.util.Random;

public class RenderUtils {
    private RenderUtils() {}

    /**
     * Allows you to easily draw textures to the screen.
     * If you are drawing without a gui screen active make sure to fix your
     * x & y coordinates using the ScaledResolution class
     * @param resourceLocation The resource location of the texture
     * @param x
     * @param y
     * @param width The width used to draw the texture
     * @param height The height used to draw the texture
     * @param rgbaCombined Color as combined RGBA to tint the texture with
     */
    public static void drawTexture(ResourceLocation resourceLocation, double x, double y, int width, int height, int rgbaCombined) {
        if (resourceLocation == null) return;
        float red = (rgbaCombined >> 16 & 255) / 255f;
        float green = (rgbaCombined >> 8 & 255) / 255f;
        float blue = (rgbaCombined & 255) / 255f;
        float alpha = (rgbaCombined >> 24 & 255) / 255f;

        //tint
        GL11.glColor4f(red, green, blue, alpha);


        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);

        //the most valuable line of code here, anti-aliasing
        Minecraft.getMinecraft().renderEngine.getTexture(resourceLocation).setBlurMipmap(true, true);


        //enable transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        draw(x, y, 0, 0, width, height, width, height);

        GL11.glDisable(GL11.GL_BLEND);

    }

    public static void draw(double xPos, double yPos, float p_drawModalRectWithCustomSizedTexture_2_, float p_drawModalRectWithCustomSizedTexture_3_, int p_drawModalRectWithCustomSizedTexture_4_, int p_drawModalRectWithCustomSizedTexture_5_, float p_drawModalRectWithCustomSizedTexture_6_, float p_drawModalRectWithCustomSizedTexture_7_) {
        float lvt_8_1_ = 1.0F / p_drawModalRectWithCustomSizedTexture_6_;
        float lvt_9_1_ = 1.0F / p_drawModalRectWithCustomSizedTexture_7_;
        Tessellator lvt_10_1_ = Tessellator.getInstance();
        WorldRenderer lvt_11_1_ = lvt_10_1_.getWorldRenderer();
        lvt_11_1_.begin(7, DefaultVertexFormats.POSITION_TEX);
        lvt_11_1_.pos((double)xPos, (double)(yPos + p_drawModalRectWithCustomSizedTexture_5_), 0.0).tex((double)(p_drawModalRectWithCustomSizedTexture_2_ * lvt_8_1_), (double)((p_drawModalRectWithCustomSizedTexture_3_ + (float)p_drawModalRectWithCustomSizedTexture_5_) * lvt_9_1_)).endVertex();
        lvt_11_1_.pos((double)(xPos + p_drawModalRectWithCustomSizedTexture_4_), (double)(yPos + p_drawModalRectWithCustomSizedTexture_5_), 0.0).tex((double)((p_drawModalRectWithCustomSizedTexture_2_ + (float)p_drawModalRectWithCustomSizedTexture_4_) * lvt_8_1_), (double)((p_drawModalRectWithCustomSizedTexture_3_ + (float)p_drawModalRectWithCustomSizedTexture_5_) * lvt_9_1_)).endVertex();
        lvt_11_1_.pos((double)(xPos + p_drawModalRectWithCustomSizedTexture_4_), (double)yPos, 0.0).tex((double)((p_drawModalRectWithCustomSizedTexture_2_ + (float)p_drawModalRectWithCustomSizedTexture_4_) * lvt_8_1_), (double)(p_drawModalRectWithCustomSizedTexture_3_ * lvt_9_1_)).endVertex();
        lvt_11_1_.pos((double)xPos, (double)yPos, 0.0).tex((double)(p_drawModalRectWithCustomSizedTexture_2_ * lvt_8_1_), (double)(p_drawModalRectWithCustomSizedTexture_3_ * lvt_9_1_)).endVertex();
        lvt_10_1_.draw();
    }


    public static void drawRegularPolygon(double x, double y, int radius, int sides, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        double twicePI = Math.PI*2;
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());


        //enable transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        worldRenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for(int i = 0; i <= sides ;i++)
        {
            double angle = (twicePI * i / sides) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }
        tessellator.draw();

    }

}
