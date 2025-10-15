package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R3_DiabolosCrimsonDevil extends Card {

    public SIGNI_R3_DiabolosCrimsonDevil()
    {
        setImageSets("WXDi-P09-061");

        setOriginalName("紅魔　ディアボロス");
        setAltNames("コウマディアボロス Kouma Diaborosu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のパワー10000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@E：あなたは手札をすべて捨てる。"
        );

        setName("en", "Diabolos, Crimson Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may pay %R. If you do, vanish target SIGNI on your opponent's field with power 10000 or less.\n" +
                "@E: Discard your hand."
        );
        
        setName("en_fan", "Diabolos, Crimson Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 10000 or less, and you may pay %R. If you do, banish it.\n" +
                "@E: Discard all cards from your hand."
        );

		setName("zh_simplified", "红魔 迪亚波罗斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的力量10000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n" +
                "@E :你把手牌全部舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

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

            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                trash(target);
            }
        }
        
        private void onEnterEff()
        {
            discard(getCardsInHand(getOwner()));
        }
    }
}
