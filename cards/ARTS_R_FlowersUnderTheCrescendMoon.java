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
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.LifeBurst;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class ARTS_R_FlowersUnderTheCrescendMoon extends Card {

    public ARTS_R_FlowersUnderTheCrescendMoon()
    {
        setImageSets("WX24-P1-003", "WX24-P1-003U");

        setOriginalName("鏡花炎月");
        setAltNames("キョウカエンゲツ Kyouka Engetsu");
        setDescription("jp",
                "以下の３つから２つまで選ぶ。\n" +
                "$$1あなたのシグニ１体を対象とし、ターン終了時まで、それは【アサシン】を得る。\n" +
                "$$2手札をすべて捨て、カードを４枚引く。\n" +
                "$$3手札を３枚捨ててもよい。そうした場合、対戦相手のライフクロス１枚をクラッシュする。そのカードのライフバーストは発動しない。"
        );

        setName("en", "Flowers Under the Crescend Moon");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Target 1 of your SIGNI, and until end of turn, it gains [[Assassin]].\n" +
                "$$2 Discard all cards from your hand, and draw 4 cards.\n" +
                "$$3 You may discard 3 cards from your hand. If you do, crush 1 of your opponent's life cloth. That card's Life Burst does not activate."
        );

		setName("zh_simplified", "镜花炎月");
        setDescription("zh_simplified", 
                "从以下的3种选2种最多。\n" +
                "$$1 你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。\n" +
                "$$2 手牌全部舍弃，抽4张牌。\n" +
                "$$3 可以把手牌3张舍弃。这样做的场合，对战对手的生命护甲1张击溃。那张牌的生命迸发不能发动。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

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
            arts.setModeChoice(0,2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
            if((modes & 1<<1) != 0)
            {
                discard(getCardsInHand(getOwner()));
                draw(4);
            }
            if((modes & 1<<2) != 0)
            {
                if(discard(0,3, ChoiceLogic.BOOLEAN).size() == 3)
                {
                    crush(getOpponent(), LifeBurst.IGNORE);
                }
            }
        }
    }
}

