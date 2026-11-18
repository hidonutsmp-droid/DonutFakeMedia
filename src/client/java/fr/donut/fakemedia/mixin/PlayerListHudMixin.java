package fr.donut.fakemedia.mixin;

import fr.donut.fakemedia.FakeMedia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerTabOverlay.class)
public class PlayerListHudMixin {
    @Inject(method = "getNameForDisplay", at = @At("RETURN"), cancellable = true)
    private void fakemedia$tabName(PlayerInfo info, CallbackInfoReturnable<Component> cir) {
        if (!FakeMedia.enabled) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) return;
        if (mc.getConnection().getPlayerInfo(mc.player.getUUID()) == info) {
            cir.setReturnValue(FakeMedia.decorateName(cir.getReturnValue()));
        }
    }
}
