package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.SIGNIZonePosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.FieldData;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIGA_K2_AlfouJealousy extends Card {

    public LRIGA_K2_AlfouJealousy()
    {
        setImageSets("WXDi-P15-039");

        setOriginalName("アルフォウジェラシー");
        setAltNames("Arufou Jerashii");
        setDescription("jp",
                "@E：次の対戦相手のターンの間、対戦相手はパワー12000以上のシグニを新たに場に出せない。\n" +
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Alfou Jealousy");
        setDescription("en",
                "@E: During your opponent's next turn, your opponent cannot put SIGNI with power 12000 or more onto their field. \n@E: Put target SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Alfou Jealousy");
        setDescription("en_fan",
                "@E: During your opponent's next turn, your opponent can't newly put SIGNI with power 12000 or more onto the field.\n" +
                "@E: Target 1 SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "阿尔芙忌妒");
        setDescription("zh_simplified", 
                "@E :下一个对战对手的回合期间，对战对手不能把力量12000以上的精灵新出场。（已经在场上的精灵不受这个效果影响）\n" +
                "@E :从你的废弃区把精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ALFOU);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_NEWLY_PUT_SIGNI_ON_FIELD, TargetFilter.HINT_OWNER_OP, this::onAttachedConstEffModRuleCheck));
            attachedConst.setCondition(this::onAttachedConstEffCond);

            for(SIGNIZonePosition zonePosition : SIGNIZonePosition.values()) GFX.attachToAbility(attachedConst, new GFXZoneUnderIndicator(getOpponent(),zonePosition.getLocation(), "chain_rose", -2));
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return data.getSourceCardIndex().getIndexedInstance().getPower().getValue() >= 12000 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
