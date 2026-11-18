package fr.donut.fakemedia;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Optional;

public final class FakeMedia {
    public static boolean enabled = true;
    public static String prefixText = "Media";
    public static Formatting color = Formatting.RED;

    private FakeMedia() {}

    public static Text prefix() {
        return Text.literal("[" + prefixText + "] ").formatted(color, Formatting.BOLD);
    }

    public static String myName() {
        MinecraftClient mc = MinecraftClient.getInstance();
        return mc.getSession() == null ? null : mc.getSession().getUsername();
    }

    public static Text decorateName(Text original) {
        return Text.empty().append(prefix()).append(original);
    }

    public static Text decorateChat(Text message) {
        String name = myName();
        if (!enabled || name == null) return message;
        String plain = message.getString();
        if (!plain.contains(name) || plain.contains("[" + prefixText + "]")) return message;

        MutableText out = Text.empty();
        message.visit((style, str) -> {
            int from = 0;
            int i;
            while ((i = str.indexOf(name, from)) >= 0) {
                if (i > from) out.append(Text.literal(str.substring(from, i)).setStyle(style));
                out.append(prefix());
                out.append(Text.literal(name).setStyle(style));
                from = i + name.length();
            }
            if (from < str.length()) out.append(Text.literal(str.substring(from)).setStyle(style));
            return Optional.empty();
        }, Style.EMPTY);
        return out;
    }
}
