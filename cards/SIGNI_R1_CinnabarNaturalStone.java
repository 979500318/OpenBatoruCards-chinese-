package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_R1_CinnabarNaturalStone extends Card {

    public SIGNI_R1_CinnabarNaturalStone()
    {
        setImageSets("WX24-P4-062");

        setOriginalName("羅石　シンシャ");
        setAltNames("ラセキシンシャ Raseki Shinsha");
        setDescription("jp",
                "@E @[手札から赤のカードを１枚捨てる]@：【エナチャージ１】" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、対戦相手が%X %X %Xを支払わないかぎり、それをバニッシュする。"
        );

        setName("en", "Cinnabar, Natural Stone");
        setDescription("en",
                "@E @[Discard 1 red card from your hand]@: [[Ener Charge 1]]" +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it unless your opponent pays %X %X %X."
        );

		setName("zh_simplified", "罗石 辰砂");
        setDescription("zh_simplified", 
                "@E 从手牌把红色的牌1张舍弃:[[能量填充1]]" +
                "~#对战对手的力量10000以下的精灵1只作为对象，如果对战对手不把%X %X %X:支付，那么将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new DiscardCost(new TargetFilter().withColor(CardColor.RED)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            enerCharge(1);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().withPower(0,10000).SIGNI()).get();
            
            if(target != null && !payEner(getOpponent(), Cost.colorless(3)))
            {
                banish(target);
            }
        }
    }
}
