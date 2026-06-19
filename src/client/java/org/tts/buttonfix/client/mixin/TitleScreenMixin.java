package org.tts.buttonfix.client.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.FriendsButton;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(method = "init", at = @At("RETURN"))
    private void fixTitleScreenLayout(CallbackInfo ci) {
        Screen screen = (Screen) (Object) this;
        int smallRowY = -1;

        for (GuiEventListener listener : screen.children()) {
            if (listener instanceof AbstractWidget widget && widget.getWidth() == 20) {
                smallRowY = widget.getY();
                break;
            }
        }

        if (smallRowY == -1) return;

        for (GuiEventListener listener : screen.children()) {
            if (listener instanceof AbstractWidget widget) {

                if (widget instanceof FriendsButton) {
                    widget.setX(screen.width / 2 + 104);
                    widget.setY(smallRowY - 24);
                }
                else if (widget.getWidth() == 20 && !(widget instanceof FriendsButton)) {
                    if (widget.getX() < screen.width / 2) {
                        widget.setX(screen.width / 2 - 124);
                    } else {
                        widget.setX(screen.width / 2 + 104);
                    }

                    widget.setY(widget.getY() + 14);
                }
                else if (widget.getWidth() == 98) {
                    widget.setY(widget.getY() - 10);
                }
            }
        }
    }
}
