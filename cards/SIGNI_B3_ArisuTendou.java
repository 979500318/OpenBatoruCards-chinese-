package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_B3_ArisuTendou extends Card {

    public SIGNI_B3_ArisuTendou()
    {
        setImageSets("WXDi-CP02-054");

        setOriginalName("天童アリス");
        setAltNames("テンドウアリス Tendou Arisu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをこのシグニの下にあるカード１枚につき－4000する。このシグニの下にあるすべてのカードをトラッシュに置く。\n" +
                "@A @[手札から＜ブルアカ＞のカードを１枚捨てる]@：あなたのトラッシュから＜ブルアカ＞のカードを２枚まで対象とし、それらをこのシグニの下に置く。" +
                "~{{U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Tendou Aris");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, target SIGNI on your opponent's field gets --4000 power for each card underneath this SIGNI until end of turn. Put all cards underneath this SIGNI into their owner's trash.\n@A @[Discard a <<Blue Archive>> card]@: Put up to two target <<Blue Archive>> cards from your trash under this SIGNI.~{{U $T1: When this SIGNI crushes one of your opponent's Life Cloth, your opponent discards a card."
        );
        
        setName("en_fan", "Aris Tendou");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --4000 power for each card under this SIGNI. Put all cards from under this SIGNI into the trash.\n" +
                "@A @[Discard 1 <<Blue Archive>> card from your hand]@: Target up to 2 <<Blue Archive>> cards from your trash, and put them under this SIGNI." +
                "~{{U $T1: When this SIGNI crushes 1 of your opponent's life cloth, your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "天童爱丽丝");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据这只精灵的下面的牌的数量，每有1张就-4000。这只精灵的下面的全部的牌放置到废弃区。\n" +
                "@A 从手牌把<<ブルアカ>>牌1张舍弃:从你的废弃区把<<ブルアカ>>牌2张最多作为对象，将这些放置到这只精灵的下面。\n" +
                "~{{U$T1 :当这只精灵把对战对手的生命护甲1张击溃时，对战对手把手牌1张舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);

            registerActionAbility(new DiscardCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)), this::onActionEff);

            AutoAbility auto2 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }
        
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -4000 * getCardsUnderCount(CardUnderCategory.UNDER), ChronoDuration.turnEnd());
            
            trash(new TargetFilter().own().under(getCardIndex()).getExportedData());
        }

        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.UNDER).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
        
        private ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            discard(getOpponent(), 1);
        }
    }
}
