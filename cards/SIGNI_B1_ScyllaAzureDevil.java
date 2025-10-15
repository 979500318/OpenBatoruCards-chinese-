package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B1_ScyllaAzureDevil extends Card {
    
    public SIGNI_B1_ScyllaAzureDevil()
    {
        setImageSets("WXDi-P07-069");
        
        setOriginalName("蒼魔　スキュラ");
        setAltNames("ソウマスキュラ Souma Sukyura");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手は手札を１枚捨てる。\n" +
                "@E：あなたは手札を１枚捨てる。"
        );
        
        setName("en", "Scylla, Azure Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, your opponent discards a card.\n" +
                "@E: Discard a card."
        );
        
        setName("en_fan", "Scylla, Azure Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, your opponent discards 1 card from their hand.\n" +
                "@E: Discard 1 card from your hand."
        );
        
		setName("zh_simplified", "苍魔 斯库拉");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手把手牌1张舍弃。\n" +
                "@E :你把手牌1张舍弃。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            discard(getOpponent(), 1);
        }
        
        private void onEnterEff()
        {
            discard(1);
        }
    }
}
