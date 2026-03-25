package dev.dannytaylor.droppable_potions.mixin;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getItem();

    @Shadow
    private @Nullable EntityReference<Entity> thrower;

    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (this.onGround()) {
            ItemStack itemStack = this.getItem();
            if (itemStack.getItem() instanceof ThrowablePotionItem throwablePotionItem) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    LivingEntity throwerEntity = null;
                    if (this.thrower != null && this.level().getEntity(this.thrower.getUUID()) instanceof LivingEntity livingEntity) {
                        throwerEntity = livingEntity;
                        if (livingEntity instanceof Player player) player.awardStat(Stats.ITEM_USED.get(throwablePotionItem), itemStack.count());
                    }
                    for (int i = 0; i < itemStack.count(); i++) {
                        Projectile.ProjectileFactory<Projectile> creator = (level, entity, stack) -> throwablePotionItem.asProjectile(level, this.position(), stack, Direction.DOWN);
                        if (throwerEntity != null) Projectile.spawnProjectileFromRotation(creator, serverLevel, itemStack, throwerEntity, -20.0F, 0.5F, 1.0F);
                        else Projectile.spawnProjectileUsingShoot(throwablePotionItem.asProjectile(serverLevel, this.position(), itemStack, Direction.DOWN), serverLevel, itemStack, this.getX(), this.getY() - 1, this.getZ(), 0.5F, 1.0F);
                    }
                }
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
}
