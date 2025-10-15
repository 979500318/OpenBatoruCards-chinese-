package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;

public final class ARTS_B_AntiSpellX extends Card {

    public ARTS_B_AntiSpellX()
    {
        setImageSets("WX24-P3-036");

        setOriginalName("アンチ・スペル・バツ");
        setAltNames("アンチスペルバツ Anchi Superu Batsu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "$$2スペル１つを対象とし、それのコストの合計１につき%Xを支払ってもよい。そうした場合、それの効果を打ち消す。このターンにあなたが《アンチ・スペル・バツ》を使用したのが一度目の場合、このカードをルリグデッキに戻してもよい。"
        );

        setName("en", "Anti Spell X");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and down it.\n" +
                "$$2 Target 1 spell, and you may pay %X for each of its total cost. If you do, cancel it. If this is the first time you used \"Anti Spell X\" this turn, you may return this card to the LRIG deck."
        );

		setName("zh_simplified", "反制·魔法·叉叉");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其#D。\n" +
                "$$2 魔法1张作为对象，可以依据其的费用的合计的数量，每有1点就把%X支付。这样做的场合，将其的效果取消。这个回合你把《アンチ・スペル・バツ》的使用是第一次的场合，可以把这张牌返回分身牌组。（费用的合计是，牌的左上的能量费用的数字的合计。例费用是%W%X %X:的场合，费用的合计是3）\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
        setUseTiming(UseTiming.ATTACK | UseTiming.SPELLCUTIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final ARTSAbility arts;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            arts = registerARTSAbility(this::onARTSEff);
            arts.setModeChoice(1);
        }

        private void onARTSEff()
        {
            if(arts.getChosenModes() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                down(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.CANCEL).spell()).get();
                
                if(target != null && payEner(Cost.colorless(Cost.getOriginalCostAsNumber(target.getCardReference()))))
                {
                    cancel(target);
                }
                
                if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_ARTS && isOwnCard(event.getCaller()) && event.getCaller().getName().getValue().contains("アンチ・スペル・バツ")) == 0 &&
                   playerChoiceActivate())
                {
                    returnToDeck(getCardIndex(), DeckPosition.TOP);
                }
            }
        }
    }
}
