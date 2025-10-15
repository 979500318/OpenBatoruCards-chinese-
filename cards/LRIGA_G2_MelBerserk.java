package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.FieldConst;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXFieldBackground;

public final class LRIGA_G2_MelBerserk extends Card {

    public LRIGA_G2_MelBerserk()
    {
        setImageSets("WX25-P1-050");

        setOriginalName("メル・バーサク");
        setAltNames("メルバーサク Meru Beeseku");
        setDescription("jp",
                "@E：次の対戦相手のターンの間、対戦相手はアーツとスペルと起能力を使用できない。\n" +
                "@E：あなたのエナゾーンからカードを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Mel Berserk");
        setDescription("en",
                "@E: During your opponent's next turn, your opponent can't use ARTS, spells or @A abilities.\n" +
                "@E: Target up to 2 cards from your ener zone, and add them to your hand."
        );

		setName("zh_simplified", "梅露·狂化");
        setDescription("zh_simplified", 
                "@E 下一个对战对手的回合期间，对战对手不能把必杀和魔法和@A能力使用。\n" +
                "@E :从你的能量区把牌2张最多作为对象，将这些加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            ConstantAbility attachedConst = new ConstantAbility(
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_SPELL, TargetFilter.HINT_OWNER_OP, data -> RuleCheckState.BLOCK),
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_ARTS, TargetFilter.HINT_OWNER_OP, data -> RuleCheckState.BLOCK),
                new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_ABILITY, TargetFilter.HINT_OWNER_OP, data -> data.getSourceAbility() instanceof ActionAbility ? RuleCheckState.BLOCK : RuleCheckState.IGNORE)
            );
            attachedConst.setCondition(() -> !isOwnTurn() ? ConditionState.OK : ConditionState.BAD);
            
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
            GFX.attachToChronoRecord(record, new GFXFieldBackground(getOpponent(), "thorns", 2730,1024, FieldConst.FIELD_CARD_WIDTH*2 + FieldConst.FIELD_ZONE_HSPACING+10,FieldConst.FIELD_ZONE_VSPACING+100).withCenterOffset());
            attachPlayerAbility(getOwner(), attachedConst, record);
        }
        
        private void onEnterEff2()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
    }
}
