package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_K3_ShunSunoharaWhereAreTheTroublesomeChildren extends Card {

    public LRIG_K3_ShunSunoharaWhereAreTheTroublesomeChildren()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WX25-CP1-024", "WX25-CP1-024U");

        setOriginalName("春原シュン[悪い子はどこですか？]");
        setAltNames("スノハラシュンワルイコハドコデスカ Sunohara Shun Warui Ko wa Doko desu ka");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、対戦相手は自分のデッキの上からカードを７枚トラッシュに置いてもよい。そうしなかった場合、そのアタックの間、対戦相手は【ガード】ができない。\n" +
                "@A $G1 %K0：あなたのトラッシュから#Gを持たないカードを３枚まで対象とし、それらをエナゾーンに置く。" +
                "~{{A $G1 %K0：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Sunohara Shun [Where are the troublesome children?]");

        setName("en_fan", "Shun Sunohara [Where are the troublesome children?]");
        setDescription("en",
                "@U: Whenever this LRIG attacks, your opponent may choose to put the top 7 cards of their deck into the trash. If they don't, your opponent can't [[Guard]] during that attack.\n" +
                "@A $G1 %K0: Target up to 3 cards without #G @[Guard]@ from your trash, and put them into the ener zone." +
            "~{{A $G1 %K0: Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand."
        );

		setName("zh_simplified", "春原瞬[坏孩子在哪呢？]");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，对战对手可以从自己的牌组上面把7张牌放置到废弃区。不这样做的场合，那次攻击期间，对战对手不能[[防御]]。\n" +
                "@A $G1 %K0从你的废弃区把不持有#G的牌3张最多作为对象，将这些放置到能量区。\n" +
                "~{{A$G1 %K0从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.SHUN);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.GAME, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onAutoEff()
        {
            if(getDeckCount(getOpponent()) >= 7 && playerChoiceActivate(getOpponent()))
            {
                millDeck(getOpponent(), 7);
            } else {
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
                addPlayerRuleCheck(PlayerRuleCheckType.CAN_GUARD, getOpponent(), record, data -> {
                    record.forceExpire();
                    return RuleCheckState.BLOCK;
                });
            }
        }

        private void onActionEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.ENER).own().not(new TargetFilter().guard()).fromTrash());
            putInEner(data);
        }

        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().guard()).fromTrash()).get();
            addToHand(target);
        }
    }
}
