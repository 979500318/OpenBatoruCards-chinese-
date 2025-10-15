package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.core.gameplay.rulechecks.player.RuleCheckMustSkipPhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class KEY_W_DonaCHEER extends Card {

    public KEY_W_DonaCHEER()
    {
        setImageSets("WXK01-007");

        setOriginalName("ドーナ　ＣＨＥＥＲ");
        setAltNames("ドーナチアー Doona Chia");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -A @[エクシード４]@：このターン、シグニアタックステップをスキップする。\n" +
                "@A -A @[エクシード４]@：このターン、ルリグアタックステップをスキップする。@@" +
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それは@>@C：バニッシュされない。@@を得る。\n" +
                "@A @[このキーを場からルリグトラッシュに置く]@：あなたのデッキからシグニ１枚を探して公開し手札に加え、デッキをシャッフルする。"
        );

        setName("en", "Dona CHEER");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@A -A @[Exceed 4]@: Skip the SIGNI attack step of this turn.\n" +
                "@A -A @[Exceed 4]@: Skip the LRIG attack step of this turn.@@" +
                "@E: Target 1 of your SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't be banished.@@" +
                "@A @[Put this key from the field into the LRIG trash]@: Search your deck for 1 SIGNI, reveal it, and add it to your hand, and shuffle your deck."
        );

		setName("zh_simplified", "多娜 CHEER");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@A 攻击阶段@[超越 4]@:这个回合，精灵攻击步骤跳过。@@\n" +
                "@A 攻击阶段@[超越 4]@:\n" +
                "这个回合，分身攻击步骤跳过。\n" +
                "@E :你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不会被破坏。@@\n" +
                "@A 这张钥匙从场上放置到分身废弃区:从你的牌组找精灵1张公开加入手牌，牌组洗切。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.WHITE);
        setCost(Cost.coin(2));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffMod1GetSample),new AbilityGainModifier(this::onConstEffMod2GetSample));
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new TrashCost(), this::onActionEff);
        }

        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct1 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff1);
            attachedAct1.setActiveUseTiming(UseTiming.ATTACK);
            return attachedAct1;
        }
        private void onAttachedActionEff1()
        {
            addPlayerRuleCheck(PlayerRuleCheckType.MUST_SKIP_PHASE, getTurnPlayer(), ChronoDuration.turnEnd(), data ->
                RuleCheckMustSkipPhase.getDataGamePhase(data) == GamePhase.ATTACK_SIGNI ? RuleCheck.RuleCheckState.OK : RuleCheck.RuleCheckState.IGNORE
            );
        }

        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct2 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff2);
            attachedAct2.setActiveUseTiming(UseTiming.ATTACK);
            attachedAct2.setNestedDescriptionOffset(1);
            return attachedAct2;
        }
        private void onAttachedActionEff2()
        {
            addPlayerRuleCheck(PlayerRuleCheckType.MUST_SKIP_PHASE, getTurnPlayer(), ChronoDuration.turnEnd(), data ->
                RuleCheckMustSkipPhase.getDataGamePhase(data) == GamePhase.ATTACK_LRIG ? RuleCheck.RuleCheckState.OK : RuleCheck.RuleCheckState.IGNORE
            );
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_BANISHED, data -> RuleCheckState.BLOCK));
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = searchDeck(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            shuffleDeck();
        }
    }
}
