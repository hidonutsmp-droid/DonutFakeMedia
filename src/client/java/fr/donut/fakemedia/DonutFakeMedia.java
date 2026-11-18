package fr.donut.fakemedia;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class DonutFakeMedia implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) ->
                dispatcher.register(ClientCommands.literal("fakemedia")
                        .executes(ctx -> {
                            FakeMedia.enabled = !FakeMedia.enabled;
                            ctx.getSource().sendFeedback(Component.literal(
                                    "FakeMedia : " + (FakeMedia.enabled ? "activé" : "désactivé")));
                            return 1;
                        })
                        .then(ClientCommands.literal("texte")
                                .then(ClientCommands.argument("valeur", StringArgumentType.greedyString())
                                        .executes(ctx -> {
                                            FakeMedia.prefixText = StringArgumentType.getString(ctx, "valeur");
                                            ctx.getSource().sendFeedback(Component.literal(
                                                    "Texte du grade : " + FakeMedia.prefixText));
                                            return 1;
                                        })))
                        .then(ClientCommands.literal("couleur")
                                .then(ClientCommands.argument("valeur", StringArgumentType.word())
                                        .executes(ctx -> {
                                            ChatFormatting f = ChatFormatting.getByName(
                                                    StringArgumentType.getString(ctx, "valeur"));
                                            if (f == null || !f.isColor()) {
                                                ctx.getSource().sendFeedback(Component.literal(
                                                        "Couleur invalide (ex: red, gold, aqua, green)"));
                                                return 0;
                                            }
                                            FakeMedia.color = f;
                                            ctx.getSource().sendFeedback(Component.literal("Couleur changée."));
                                            return 1;
                                        })))));
    }
}
