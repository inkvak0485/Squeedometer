package squeek.speedometer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.api.distmarker.Dist;

@Mod(ModSpeedometer.MODID)
public class ModSpeedometer {
    public static final String MODID = "squeedometer";

    public ModSpeedometer() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SqueedometerConfig.SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientHooks.init();
        }
    }
}
