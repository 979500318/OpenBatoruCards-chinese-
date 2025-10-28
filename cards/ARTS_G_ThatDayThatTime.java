package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilitySLancer;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class ARTS_G_ThatDayThatTime extends Card {

    public ARTS_G_ThatDayThatTime()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-005", Mask.VERTICAL+"WX25-CP1-005U");

        setOriginalName("あの日……あの時を……");
        setAltNames("アノヒアノトキヲ Ano Hi Ano Toki wo");
        setDescription("jp",
                "あなたのエナゾーンから＜ブルアカ＞のシグニを３枚まで対象とし、それらを場に出す。次の対戦相手のターン終了時まで、あなたのすべての＜ブルアカ＞のシグニのパワーを＋5000し、あなたのすべての＜ブルアカ＞のシグニは[[シャドウ（レベル３以上のシグニ）]]を得る。\n" +
                "&E４枚以上@0その後、追加であなたのシグニ１体を対象とし、ターン終了時まで、それは【Ｓランサー】を得る。"
        );

        setName("en", "That day... That time...");
        setDescription("en",
                "Target up to 3 <<Blue Archive>> SIGNI from your ener zone, and put them onto the field. Until the end of your opponent's next turn, all of your <<Blue Archive>> SIGNI get +5000 power, and they gain [[Shadow (level 3 or higher SIGNI)]]\n" +
                "&E4 or more@0 Then, additionally target 1 of your <<Blue Archive>> SIGNI, and until end of turn, it gains [[S Lancer]]."
        );

        setName("zh_simplified", "那日……那时……");
        setDescription("zh_simplified", 
                "从你的能量区把<<蔚蓝档案>>精灵3张最多作为对象，将这些出场。直到下一个对战对手的回合结束时为止，你的全部的<<蔚蓝档案>>精灵的力量+5000，你的全部的<<蔚蓝档案>>精灵得到[[暗影（等级3以上的精灵）]]。\n" +
                "&E4张以上@0然后，追加你的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner().playable());
            putOnField(data);
            
            forEachSIGNIOnField(getOwner(), cardIndex -> {
                if(cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE))
                {
                    gainPower(cardIndex, 5000, ChronoDuration.nextTurnEnd(getOpponent()));
                    attachAbility(cardIndex, new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
                }
            });
            
            if(getAbility().isRecollectFulfilled())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get();
                attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
