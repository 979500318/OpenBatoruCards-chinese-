package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class ARTS_K_DarkBloom extends Card {

    public ARTS_K_DarkBloom()
    {
        setImageSets("WX24-P3-009", "WX24-P3-009U");

        setOriginalName("ダーク・ブルーム");
        setAltNames("ダークブルーム Daaku Buruumu");
        setDescription("jp",
                "あなたのレベル２以上のセンタールリグ１体を対象とし、次のあなたのエナフェイズ終了時まで、それのリミットを＋１し、それは以下の能力を得る。" +
                "@>@C：あなたのライフクロスがリフレッシュによってトラッシュに移動する場合、代わりにこのルリグはこの能力を失う。\n" +
                "@A $T1 #D：あなたのデッキの上からカードを５枚トラッシュに置く。その後、あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Dark Bloom");
        setDescription("en",
                "Target your level 2 or higher center LRIG, and until the end of your next ener phase, it gets +1 limit, and it gains:" +
                "@>@C: If your life cloth would be moved to your trash by refreshing, this LRIG loses this ability instead.\n" +
                "@A $T1 #D: Put the top 5 cards of your deck into the trash. Then, target 1 SIGNI from your trash, and add it to your hand."
        );

        setName("zh_simplified", "黑暗·盛开");
        setDescription("zh_simplified", 
                "你的等级2以上的核心分身1只作为对象，直到下一个你的充能阶段结束时为止，其的界限+1，其得到以下的能力。" +
                "@>@C :你的生命护甲因为重构往废弃区移动的场合，作为替代，这只分身的这个能力失去。\n" +
                "@A $T1 #D:从你的牌组上面把5张牌放置到废弃区。然后，从你的废弃区把精灵1张作为对象，将其加入手牌。@@"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setCondition(this::onARTSEffCond);
        }
        
        private ConditionState onARTSEffCond()
        {
            return new TargetFilter().own().LRIG().withLevel(2,0).getValidTargetsCount() == 0 ? ConditionState.WARN : ConditionState.OK;
        }
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().LRIG().withLevel(2,0)).get();
            if(target == null) return;
            
            gainValue(target, target.getIndexedInstance().getLimit(),1d, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.MUST_TRASH_LC_ON_REFRESH, TargetFilter.HINT_OWNER_OWN, this::onAttachedConstEffModRuleCheck));
            attachAbility(target, attachedConst, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            
            ActionAbility attachedAct = new ActionAbility(new DownCost(), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            attachedAct.setNestedDescriptionOffset(1);
            attachAbility(target, attachedAct, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            data.getSourceAbilityRC().disable();
            return RuleCheckState.BLOCK;
        }
        private void onAttachedActionEff()
        {
            millDeck(5);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
