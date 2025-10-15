package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_B3_SeaHorsePhantomDragonPrincess extends Card {

    public SIGNI_B3_SeaHorsePhantomDragonPrincess()
    {
        setImageSets("WX25-P1-057");

        setOriginalName("幻竜姫　シーホース");
        setAltNames("ゲンリュウメシーホース Genryuuhime Shii Hoosu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手が%X %Xを支払わないかぎり、対戦相手は手札を２枚捨てる。\n" +
                "@E：対戦相手のシグニ１体を対象とし、それを凍結する。" +
                "~#：カードを２枚引く。対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Sea Horse, Phantom Dragon Princess");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, your opponent discards 2 cards from their hand unless they pay %X %X.\n" +
                "@E: Target 1 of your opponent's SIGNI, and freeze it." +
                "~#Draw 2 cards. Your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "幻龙姬 海马");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，如果对战对手不把%X %X:支付，那么对战对手把手牌2张舍弃。\n" +
                "@E :对战对手的精灵1只作为对象，将其冻结。" +
                "~#抽2张牌。对战对手把手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(!payEner(getOpponent(), Cost.colorless(2)))
            {
                discard(getOpponent(), 2);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
        }

        private void onLifeBurstEff()
        {
            draw(2);
            discard(getOpponent(), 1);
        }
    }
}
