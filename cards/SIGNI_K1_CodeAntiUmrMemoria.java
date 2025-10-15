package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_CodeAntiUmrMemoria extends Card {
    
    public SIGNI_K1_CodeAntiUmrMemoria()
    {
        setImageSets("WXDi-P07-089", "WXDi-P07-089P");
        
        setOriginalName("コードアンチ　ウムル//メモリア");
        setAltNames("コードアンチウムルメモリア Koodo Anchi Umuru Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにこのシグニがトラッシュから場に出ていた場合、カードを１枚引く。\n\n" +
                "@A @[手札を２枚捨てる]@：このカードをトラッシュから場に出す。"
        );
        
        setName("en", "Umr//Memoria, Code: Anti");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI entered the field from the trash this turn, draw a card.\n\n" +
                "@A @[Discard two cards]@: Put this card from your trash onto your field. "
        );
        
        setName("en_fan", "Code Anti Umr//Memoria");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if this SIGNI was put from your trash onto the field this turn, draw 1 card.\n\n" +
                "@A @[Discard 2 cards from your hand]@: Put this card from your trash onto the field."
        );
        
		setName("zh_simplified", "古兵代号 乌姆尔//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合这只精灵从废弃区出场的场合，抽1张牌。\n" +
                "@A 手牌2张舍弃:这张牌从废弃区出场。（这个能力只有在这张牌在废弃区的场合才能使用）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANCIENT_WEAPON);
        setLevel(1);
        setPower(1000);
        
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
            
            ActionAbility act = registerActionAbility(new DiscardCost(2), this::onActionEff);
            act.setActiveLocation(CardLocation.TRASH);
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.ENTER && event.getCallerCardIndex() == getCardIndex() && event.getCaller().getOldLocation() == CardLocation.TRASH) > 0)
            {
                draw(1);
            }
        }
        
        private void onActionEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH)
            {
                putOnField(getCardIndex());
            }
        }
    }
}
