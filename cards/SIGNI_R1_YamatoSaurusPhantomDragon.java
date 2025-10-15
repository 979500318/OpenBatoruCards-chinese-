package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R1_YamatoSaurusPhantomDragon extends Card {

    public SIGNI_R1_YamatoSaurusPhantomDragon()
    {
        setImageSets("WX24-P2-066");

        setOriginalName("幻竜　ヤマトサウルス");
        setAltNames("ゲンリュウヤマトサウルス Genryuu Yamatosaurusu");
        setDescription("jp",
                "@E：あなたの場に他の＜龍獣＞のシグニがある場合、対戦相手のパワー2000以下のシグニ１体を対象とし、対戦相手が%Xを支払わないかぎり、それをバニッシュする。"
        );

        setName("en", "Yamayosaurus, Phantom Dragon");
        setDescription("en",
                "@E: If there is another <<Dragon Beast>> SIGNI on your field, target 1 of your opponent's SIGNI with power 2000 or less, and banish it unless your opponent pays %X."
        );

		setName("zh_simplified", "幻龙 大和龙");
        setDescription("zh_simplified", 
                "@E 你的场上有其他的<<龍獣>>精灵的场合，对战对手的力量2000以下的精灵1只作为对象，如果对战对手不把%X:支付，那么将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DRAGON_BEAST).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,2000)).get();
                if(target != null && !payEner(getOpponent(), Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
