package fr.donut.fakemedia.mixin;

import fr.donut.fakemedia.FakeMedia;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
    private void fakemedia$tabName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        if (!FakeMedia.enabled) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.getNetworkHandler() == null) return;
        if (mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()) == entry) {
            cir.setReturnValue(FakeMedia.decorateName(cir.getReturnValue()));
        }
    }
}
