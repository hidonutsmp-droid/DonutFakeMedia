package fr.donut.fakemedia.mixin;

import fr.donut.fakemedia.FakeMedia;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void fakemedia$displayName(CallbackInfoReturnable<Text> cir) {
        if (!FakeMedia.enabled) return;
        if ((Object) this == MinecraftClient.getInstance().player) {
            cir.setReturnValue(FakeMedia.decorateName(cir.getReturnValue()));
        }
    }
}
