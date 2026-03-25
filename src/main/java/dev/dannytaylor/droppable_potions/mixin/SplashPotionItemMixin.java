package dev.dannytaylor.droppable_potions.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({SplashPotionItem.class, LingeringPotionItem.class})
public abstract class SplashPotionItemMixin {
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), method = "use")
	private void dp$use(Level instance, Entity except, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch) {
	}
}