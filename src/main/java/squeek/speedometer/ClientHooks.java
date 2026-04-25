package squeek.speedometer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.util.Locale;

public class ClientHooks {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final KeyBinding TOGGLE_HUD_KEY = new KeyBinding(
            "key.squeedometer.toggle_hud",
            KeyConflictContext.IN_GAME,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "key.categories.misc"
    );

    private static boolean enabled = true;

    public static void init() {
        ClientRegistry.registerKeyBinding(TOGGLE_HUD_KEY);
        MinecraftForge.EVENT_BUS.register(new ClientHooks());
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        while (TOGGLE_HUD_KEY.isPressed()) {
            enabled = !enabled;
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!enabled || event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR || MC.player == null) {
            return;
        }

        MatrixStack matrixStack = event.getMatrixStack();

        double dx = MC.player.getPosX() - MC.player.prevPosX;
        double dz = MC.player.getPosZ() - MC.player.prevPosZ;
        double speedBpt = MathHelper.sqrt(dx * dx + dz * dz);

        SpeedUnit unit = SqueedometerConfig.SPEED_UNIT.get();
        double speed = unit.convertFromBlocksPerTick(speedBpt);
        String format = "%1$." + SqueedometerConfig.PRECISION.get() + "f";
        String text = String.format(Locale.ROOT, format, speed);
        if (SqueedometerConfig.SHOW_UNITS.get()) {
            text += " " + unit.shortLabel();
        }

        int textWidth = MC.fontRenderer.getStringWidth(text);
        int textHeight = MC.fontRenderer.FONT_HEIGHT;
        int padding = SqueedometerConfig.PADDING.get();
        int margin = SqueedometerConfig.MARGIN.get();

        int x = margin + SqueedometerConfig.X_POS.get();
        int y = margin + SqueedometerConfig.Y_POS.get();

        String xAlign = SqueedometerConfig.X_ALIGN.get().toLowerCase(Locale.ROOT);
        String yAlign = SqueedometerConfig.Y_ALIGN.get().toLowerCase(Locale.ROOT);

        if ("center".equals(xAlign) || "middle".equals(xAlign)) {
            x += (MC.getMainWindow().getScaledWidth() - textWidth - padding * 2 - margin * 2) / 2;
        } else if ("right".equals(xAlign)) {
            x += MC.getMainWindow().getScaledWidth() - textWidth - padding * 2 - margin * 2;
        }

        if ("center".equals(yAlign) || "middle".equals(yAlign)) {
            y += (MC.getMainWindow().getScaledHeight() - textHeight - padding * 2 - margin * 2) / 2;
        } else if ("bottom".equals(yAlign)) {
            y += MC.getMainWindow().getScaledHeight() - textHeight - padding * 2 - margin * 2;
        }

        if (SqueedometerConfig.SHOW_BACKGROUND.get()) {
            AbstractGui.fill(matrixStack, x, y, x + textWidth + padding * 2, y + textHeight + padding * 2, 0xAA000000);
        }

        MC.fontRenderer.drawStringWithShadow(matrixStack, text, x + padding, y + padding, 0xE0E0E0);
    }
}
