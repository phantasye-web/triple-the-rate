package game.content.phantasye.minigame.pirate;

import core.GameType;
import game.entity.combat_strategy.EntityCombatStrategy;
import game.entity.combat_strategy.impl.NpcCombatStrategy;
import game.npc.CustomNpcComponent;
import game.npc.Npc;
import game.npc.impl.barrelchest.Barrelchest;
import game.type.GameTypeIdentity;

@CustomNpcComponent(identities = @GameTypeIdentity(type= GameType.OSRS, identity = 5426))
public class PirateBoss extends Npc {

    private final NpcCombatStrategy strategy = new PirateBossCombatStrategy();

    public PirateBoss(int npcId, int npcType) {
        super(npcId, npcType);
    }

    /**
     * Creates a new instance of this npc with the given index.
     *
     * @param index the new index of this npc.
     * @return the new instance.
     */
    @Override
    public Npc copy(int index) {
        return new Barrelchest(index, npcType);
    }

    /**
     * The combat strategy for the entity, or null if there is no combat strategy to be used.
     *
     * @return the strategy used against other entities.
     */
    @Override
    public EntityCombatStrategy getCombatStrategyOrNull() {
        return strategy;
    }
}
