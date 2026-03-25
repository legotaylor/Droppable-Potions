# Droppable-Potions
Makes you drop potions instead of throwing them.

## Features
- Prevents Splash and Lingering Potions from being thrown.
- When Splash and Lingering Potions are dropped, and hit the ground they will either splash or linger.

### Potential Bugs
- If the Item Entity does not have a thrower, or if the thrower uuid cannot be associated to an entity, the potion may bounce before splashing.