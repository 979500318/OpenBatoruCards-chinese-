package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R1_RilDissonaCrimsonGeneral extends Card {

    public SIGNI_R1_RilDissonaCrimsonGeneral()
    {
        setImageSets("WXDi-P12-063", "SPDi01-85");

        setOriginalName("紅将　リル//ディソナ");
        setAltNames("コウショウリルディソナ Koushou Riru Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の#Sのシグニがある場合、対戦相手のパワー8000以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Ril//Dissona, Crimson General");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another #S SIGNI on your field, you may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Ril//Dissona, Crimson General");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is another #S @[Dissona]@ SIGNI on your field, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %R %X. If you do, banish it."
        );

		setName("zh_simplified", "红将 莉露//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上有其他的#S的精灵的场合，对战对手的力量8000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().dissona().except(getCardIndex()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                
                if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
