package open.batoru.data.cards;

import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;

public final class LRIG_B4_LostCodePirulukX extends Card {

    public LRIG_B4_LostCodePirulukX()
    {
        setImageSets("WX24-P4-017", "WX24-P4-017U");

        setOriginalName("ロストコード・ピルルク　X");
        setAltNames("ロストコードピルルクエックス Rosuto Koodo Piruruku Ekkusu");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜ピルルク＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：あなたのトラッシュからスペルと青のシグニをそれぞれ１枚まで対象とし、それらを手札に加える。\n" +
                "@A $G1 @[@|アプリ|@]@ %B0：&E４枚以上@0このターン、あなたがカードを１枚引くか、対戦相手が手札を１枚捨てたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－4000する。"
        );

        setName("en", "Lost Code Piruluk X");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Piruluk>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Target up to 1 spell and up to 1 blue SIGNI from your trash, and add them to your hand.\n" +
                "@A $G1 @[@|Appli|@]@ %B0: &E4 or more@0 This turn, whenever you draw a card or your opponent discards a card from their hand, target 1 of your opponent's SIGNI, and until end of turn, it gets --4000 power."
        );

		setName("zh_simplified", "失落代号·皮璐璐可 X");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<ピルルク>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:从你的废弃区把魔法和蓝色的精灵各1张最多作为对象，将这些加入手牌。\n" +
                "@A $G1 :凝望%B0&E4张以上@0这个回合，当你抽1张牌或，把对战对手的手牌1张舍弃时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-4000。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.PIRULUK);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setLevel(4);
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

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.PIRULUK).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Appli");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().spell().fromTrash());
            data.add(playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLUE).fromTrash()).get());
            addToHand(data);
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                AutoAbility attachedAuto1 = new AutoAbility(GameEventId.DRAW, this::onAttachedAutoEff);
                attachedAuto1.setCondition(this::onAttachedAutoEff1Cond);
                AutoAbility attachedAuto2 = new AutoAbility(GameEventId.DISCARD, this::onAttachedAutoEff);
                attachedAuto2.setCondition(this::onAttachedAutoEff2Cond);
                
                attachPlayerAbility(getOwner(), attachedAuto1, ChronoDuration.turnEnd());
                attachPlayerAbility(getOwner(), attachedAuto2, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onAttachedAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -4000, ChronoDuration.turnEnd());
        }
    }
}
