package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_ZmeiPhantomDragon extends Card {

    public SIGNI_R2_ZmeiPhantomDragon()
    {
        setImageSets("WX24-P2-070");

        setOriginalName("幻竜　ズメイ");
        setAltNames("ゲンリュウズメイ Genryuu Zumei");
        setDescription("jp",
                "@E %R：あなたの場に他の＜龍獣＞のシグニがある場合、対戦相手のパワー8000以下のシグニ１体を対象とし、対戦相手が%X %Xを支払わないかぎり、それをバニッシュする。"
        );

        setName("en", "Zmei, Phantom Dragon");
        setDescription("en",
                "@E %R: If there is another <<Dragon Beast>> SIGNI on your field, target 1 of your opponent's SIGNI with power 8000 or less, and banish it unless your opponent pays %X %X."
        );

		setName("zh_simplified", "幻龙 斯拉夫龙");
        setDescription("zh_simplified", 
                "@E %R你的场上有其他的<<龍獣>>精灵的场合，对战对手的力量8000以下的精灵1只作为对象，如果对战对手不把%X %X:支付，那么将其破坏。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                if(target != null && !payEner(getOpponent(), Cost.colorless(2)))
                {
                    banish(target);
                }
            }
        }
    }
}
