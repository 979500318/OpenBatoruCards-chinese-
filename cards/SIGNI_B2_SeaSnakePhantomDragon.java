package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_B2_SeaSnakePhantomDragon extends Card {

    public SIGNI_B2_SeaSnakePhantomDragon()
    {
        setImageSets("WX24-P2-079");

        setOriginalName("幻竜　ウミヘビ");
        setAltNames("ゲンリュウウミヘビ Genryuu Umihebi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手が%Xを支払わないかぎり、対戦相手は手札を１枚捨てる。" +
                "~#対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Sea Snake, Phantom Dragon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, your opponent discards 1 card from their hand unless they pay %X." +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "幻龙 海蛇");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，如果对战对手不把%X:支付，那么对战对手把手牌1张舍弃。" +
                "~#对战对手的分身1只作为对象，将其横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff1()
        {
            if(!payEner(getOpponent(), Cost.colorless(1)))
            {
                discard(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
