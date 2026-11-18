package fr.donut.fakemedia;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DonutFakeMedia implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("fakemedia")
                        .executes(ctx -> {
                            FakeMedia.enabled = !FakeMedia.enabled;
                            ctx.getSource().sendFeedback(Text.literal(
                                    "FakeMedia : " + (FakeMedia.enabled ? "activé" : "désactivé")));
                            return 1;
                        })
                        .then(ClientCommandManager.literal("texte")
                                .then(ClientCommandManager.argument("valeur", StringArgumentType.greedyString())
                                        .executes(ctx -> {
                                            FakeMedia.prefixText = StringArgumentType.getString(ctx, "valeur");
                                            ctx.getSource().sendFeedback(Text.literal(
                                                    "Texte du grade : " + FakeMedia.prefixText));
                                            return 1;
                                        })))
                        .then(ClientCommandManager.literal("couleur")
                                .then(ClientCommandManager.argument("valeur", StringArgumentType.word())
                                        .executes(ctx -> {
                                            Formatting f = Formatting.byName(
                                                    StringArgumentType.getString(ctx, "valeur"));
                                            if (f == null || !f.isColor()) {
                                                ctx.getSource().sendFeedback(Text.literal(
                                                        "Couleur invalide (ex: red, gold, aqua, green)"));
                                                return 0;
                                            }
                                            FakeMedia.color = f;
                                            ctx.getSource().sendFeedback(Text.literal("Couleur changée."));
                                            return 1;
                                        })))));
    }
}
