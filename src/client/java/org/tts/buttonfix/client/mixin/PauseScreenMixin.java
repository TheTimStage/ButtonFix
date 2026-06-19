package org.tts.buttonfix.client.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {

    @Inject(method = "init", at = @At("RETURN"))
    private void fixPauseScreenLayout(CallbackInfo ci) {
        Screen screen = (Screen) (Object) this;

        List<AbstractWidget> smallButtons = new ArrayList<>();
        int backToGameY = -1;
        int iconRowY = -1;

        for (GuiEventListener listener : screen.children()) {
            if (listener instanceof AbstractWidget widget) {
                if (widget.getWidth() == 20 && widget.getHeight() == 20) {
                    smallButtons.add(widget);

                    if (iconRowY == -1) {
                        iconRowY = widget.getY();
                    }
                }
                if (widget.getWidth() == 204 && backToGameY == -1) {
                    backToGameY = widget.getY();
                }
            }
        }

        if (smallButtons.isEmpty() || backToGameY == -1) return;
        int targetX = screen.width / 2 + 106;
        int currentY = backToGameY;

        for (GuiEventListener listener : screen.children()) {
            if (listener instanceof AbstractWidget widget) {
                if (smallButtons.contains(widget)) {
                    widget.setPosition(targetX, currentY);
                    currentY += 24;
                }
                else if (widget.getY() > iconRowY) {
                    widget.setY(widget.getY() - 24);
                }
            }
        }
    }
}
