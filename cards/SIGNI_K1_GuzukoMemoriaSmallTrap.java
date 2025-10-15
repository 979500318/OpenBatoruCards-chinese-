package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_GuzukoMemoriaSmallTrap extends Card {

    public SIGNI_K1_GuzukoMemoriaSmallTrap()
    {
        setImageSets("WXDi-P10-073", "WXDi-P10-073P");

        setOriginalName("小罠　グズ子//メモリア");
        setAltNames("ショウビングズコメモリア Shoubin Guzuko Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。その後、そのカードがレベルが偶数のシグニの場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Guzuko//Memoria, Small Trickster");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a SIGNI with an even level, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Guzuko//Memoria, Small Trap");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. Then, if that card is a SIGNI of even level, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "小罠 迟钝子//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的牌组最上面公开。然后，那张牌是等级在偶数的精灵的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(1);
        setPower(3000);

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
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) && cardIndex.getIndexedInstance().getLevelByRef() % 2 == 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -2000, ChronoDuration.turnEnd());
            }
            returnToDeck(cardIndex, DeckPosition.TOP);
        }
    }
}
