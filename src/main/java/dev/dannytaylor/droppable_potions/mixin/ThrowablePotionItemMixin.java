package dev.dannytaylor.droppable_potions.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrowablePotionItem.class)
public abstract class ThrowablePotionItemMixin extends PotionItem {
	public ThrowablePotionItemMixin(Properties properties) {
		super(properties);
	}

	@Inject(at = @At("HEAD"), method = "use", cancellable = true)
	private void dp$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		cir.setReturnValue(super.use(level, player, hand));
	}
}