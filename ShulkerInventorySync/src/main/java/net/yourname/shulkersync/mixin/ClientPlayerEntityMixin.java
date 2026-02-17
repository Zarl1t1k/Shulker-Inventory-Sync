package net.yourname.shulkersync.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "swingHand", at = @At("HEAD"))
    private void onSwing(Hand hand, CallbackInfoReturnable<Void> cir) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(hand);

        // Если в руке остался 1 блок (сейчас мы его поставим и станет 0)
        if (!stack.isEmpty() && stack.getCount() == 1) {
            checkShulkersForReplacement(player, stack);
        }
    }

    private void checkShulkersForReplacement(ClientPlayerEntity player, ItemStack depletedStack) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack invStack = player.getInventory().getStack(i);

            // Проверяем, является ли предмет контейнером (шалкер)
            ContainerComponent container = invStack.get(DataComponentTypes.CONTAINER);
            if (container != null) {
                for (ItemStack innerStack : container.iterateContents()) {
                    if (innerStack.isOf(depletedStack.getItem())) {
                        player.sendMessage(Text.literal("§6[ShulkerSync]§f Запас найден в: " + invStack.getName().getString()), true);
                        return;
                    }
                }
            }
        }
    }
}