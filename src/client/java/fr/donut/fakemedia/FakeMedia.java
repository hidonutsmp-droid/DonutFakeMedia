package fr.donut.fakemedia;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.Optional;

public final class FakeMedia {
    public static boolean enabled = true;
    public static String prefixText = "Media";
    public static ChatFormatting color = ChatFormatting.RED;

    private FakeMedia() {}

    public static Component prefix() {
        return Component.literal("[" + prefixText + "] ").withStyle(color, ChatFormatting.BOLD);
    }

    public static String myName() {
        Minecraft mc = Minecraft.getInstance();
        return mc.getUser() == null ? null : mc.getUser().getName();
    }

    /** Tablist / nametag : prefix + nom. */
    public static Component decorateName(Component original) {
        return Component.empty().append(prefix()).append(original);
    }

    /** Chat : insere le prefix devant chaque occurrence de ton pseudo. */
    public static Component decorateChat(Component message) {
        String name = myName();
        if (!enabled || name == null) return message;
        String plain = message.getString();
        if (!plain.contains(name) || plain.contains("[" + prefixText + "]")) return message;

        MutableComponent out = Component.empty();
        message.visit((Style style, String str) -> {
            int from = 0;
            int i;
            while ((i = str.indexOf(name, from)) >= 0) {
                if (i > from) out.append(Component.literal(str.substring(from, i)).setStyle(style));
                out.append(prefix());
                out.append(Component.literal(name).setStyle(style));
                from = i + name.length();
            }
            if (from < str.length()) out.append(Component.literal(str.substring(from)).setStyle(style));
            return Optional.empty();
        }, Style.EMPTY);
        return out;
    }
}
