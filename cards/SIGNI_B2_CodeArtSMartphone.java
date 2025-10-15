package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_CodeArtSMartphone extends Card {

    public SIGNI_B2_CodeArtSMartphone()
    {
        setImageSets("WX24-P1-068");

        setOriginalName("コードアート　Sマフォ");
        setAltNames("コードアートエスマフォ Koodo Aato Esu Mafo");
        setDescription("jp",
                "@U:このシグニがアタックしたとき、このターンにあなたがスペルを使用していた場合、対戦相手は手札を１枚捨てる。"
        );

        setName("en", "Code Art S Martphone");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you used a spell this turn, your opponent discards 1 card from their hand."
        );

		setName("zh_simplified", "必杀代号 智能手机");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合你把魔法使用过的场合，对战对手把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(7000);

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
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                discard(getOpponent(), 1);
            }
        }
    }
}
