package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class ARTS_B_AlabasterCallingCard extends Card {

    public ARTS_B_AlabasterCallingCard()
    {
        setImageSets(Mask.VERTICAL+"WX25-CP1-004", Mask.VERTICAL+"WX25-CP1-004U");

        setOriginalName("白亜の予告状");
        setAltNames("ハクアノヨコクジョウ Hakua no Yokokujou");
        setDescription("jp",
                "以下の４つから２つまで選ぶ。\n" +
                "&E４枚以上@0 代わりに３つまで選ぶ。@0" +
                "$$1カードを３枚引く。\n" +
                "$$2対戦相手のルリグ１体を対象とし、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、それをダウンする。\n" +
                "$$3対戦相手のシグニ１体を対象とし、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、それをダウンする。\n" +
                "$$4対戦相手のシグニ１体を対象とし、手札を４枚捨てる。そうした場合、それをバニッシュする。"
        );

        setName("en", "Alabaster Calling Card");
        setDescription("en",
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "&E4 or more@0 Instead, @[@|choose up to 3 of the following:|@]@ @0" +
                "$$1 Draw 3 cards.\n" +
                "$$2 Target 1 of your opponent's LRIG, and you may discard 1 <<Blue Archive>> card from your hand. If you do, down it.\n" +
                "$$3 Target 1 of your opponent's SIGNI, and you may discard 1 <<Blue Archive>> card from your hand. If you do, down it.\n" +
                "$$4 Target 1 of your opponent's SIGNI, and you may discard 4 cards from your hand. If you do, banish it."
        );

		setName("zh_simplified", "纯白的预告信");
        setDescription("zh_simplified", 
                "从以下的4种选2种最多。&E4张以上@0作为替代，选3种最多。（先选全部的选择项和对象）\n" +
                "$$1 抽3张牌。\n" +
                "$$2 对战对手的分身1只作为对象，可以从手牌把<<ブルアカ>>牌1张舍弃。这样做的场合，将其#D。\n" +
                "$$3 对战对手的精灵1只作为对象，可以从手牌把<<ブルアカ>>牌1张舍弃。这样做的场合，将其#D。\n" +
                "$$4 对战对手的精灵1只作为对象，手牌4张舍弃。这样做的场合，将其破坏。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(2));
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
            arts.setOnModesChosenPre(this::onARTSEffPreModeChoice);
            arts.setModeChoice(0,2);
            arts.setRecollect(4);
        }

        private void onARTSEffPreModeChoice()
        {
            arts.setModeChoice(0, arts.isRecollectFulfilled() ? 3 : 2);
        }

        private void onARTSEff()
        {
            int modes = arts.getChosenModes();

            if((modes & 1<<0) != 0)
            {
                draw(3);
            }
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
                
                if(target != null && discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
                {
                    down(target);
                }
            }
            if((modes & 1<<2) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                
                if(target != null && discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
                {
                    down(target);
                }
            }
            if((modes & 1<<3) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null && discard(0,4, ChoiceLogic.BOOLEAN).get() != null)
                {
                    banish(target);
                }
            }
        }
    }
}
