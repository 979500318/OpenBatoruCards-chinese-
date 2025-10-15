package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_R2_CarnivalFessoneNaturalStar extends Card {

    public SIGNI_R2_CarnivalFessoneNaturalStar()
    {
        setImageSets("WXDi-P14-056");

        setOriginalName("羅星　カーニバル//フェゾーネ");
        setAltNames("ラセイカーニバルフェゾーネ Raseiki Kaanibaru Fezoone");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、手札を１枚捨ててもよい。そうした場合、以下の３つから１つを選ぶ。\n" +
                "$$1対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。\n" +
                "$$3【エナチャージ１】\n" +
                "@E #C #C #C #C #C：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Carnival//Fesonne, Natural Planet");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may discard a card. If you do, choose one of the following.\n$$1Vanish target SIGNI on your opponent's field with power 3000 or less.\n$$2Draw a card.\n$$3[[Ener Charge 1]].\n@A #C #C #C #C #C: Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Carnival//Fessone, Natural Star");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may discard 1 card from your hand. If you do, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI with power 3000 or less, and banish it.\n" +
                "$$2 Draw 1 card.\n" +
                "$$3 [[Ener Charge 1]]\n" +
                "@E #C #C #C #C #C: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "罗星 嘉年华//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以把手牌1张舍弃。这样做的场合，从以下的3种选1种。\n" +
                "$$1 对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n" +
                "$$3 [[能量填充1]]\n" +
                "@A #C #C #C #C #C:对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new CoinCost(5), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(discard(0,1).get() != null)
            {
                switch(playerChoiceMode())
                {
                    case 1<<0 -> {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
                        banish(target);
                    }
                    case 1<<1 -> draw(1);
                    case 1<<2 -> enerCharge(1);
                }
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
