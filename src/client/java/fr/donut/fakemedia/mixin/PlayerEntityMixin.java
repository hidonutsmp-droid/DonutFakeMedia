package fr.donut.fakemedia.mixin;

import fr.donut.fakemedia.FakeMedia;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin {
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void fakemedia$displayName(CallbackInfoReturnable<Component> cir) {
        if (!FakeMedia.enabled) return;
        if ((Object) this == Minecraft.getInstance().player) {
            cir.setReturnValue(FakeMedia.decorateName(cir.getReturnValue()));
        }
    }
}
