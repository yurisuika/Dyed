package dev.yurisuika.dyed;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("dyed")
public class Dyed {

    public Dyed() {
        MinecraftForge.EVENT_BUS.register(this);
    }

}