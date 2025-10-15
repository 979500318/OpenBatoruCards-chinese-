package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.stock.*;

public final class ARTS_W_FortunaDefense extends Card {

    public ARTS_W_FortunaDefense()
    {
        setImageSets("WX24-P3-032");

        setOriginalName("フォルトナ・ディフェンス");
        setAltNames("フォルトナディフェンス Forutona Difensu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "$$2【アサシン】か【ランサー】か【Ｓランサー】か【ダブルクラッシュ】を持つ対戦相手のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Fortuna Defense");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "$$2 Target 1 of your opponent's SIGNI with [[Assassin]], [[Lancer]], [[S Lancer]], or [[Double Crush]], and return to their hand."
        );

		setName("zh_simplified", "福尔图娜·防御");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "$$2 持有[[暗杀]]或[[枪兵]]或[[S枪兵]]或[[双重击溃]]的对战对手的精灵1只作为对象，将其返回手牌。（[[枪兵（条件）]]包含在[[枪兵]]中）\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setUseTiming(UseTiming.ATTACK);

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
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withStockAbility(
                    StockAbilityAssassin.class, StockAbilityLancer.class, StockAbilitySLancer.class, StockAbilityDoubleCrush.class
                )).get();
                addToHand(target);
            }
        }
    }
}

