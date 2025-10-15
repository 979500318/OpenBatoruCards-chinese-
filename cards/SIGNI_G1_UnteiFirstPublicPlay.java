package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_UnteiFirstPublicPlay extends Card {
    
    public SIGNI_G1_UnteiFirstPublicPlay()
    {
        setImageSets("WXDi-P06-070");
        
        setOriginalName("壱ノ公遊　ウンテイ");
        setAltNames("イチノコウユウウンテイ Ichi no Kouyuu Untei");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにこのシグニがエナゾーンから場に出ていた場合、【エナチャージ１】をする。"
        );
        
        setName("en", "Monkey Bars, First Playground");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI entered the field from your Ener Zone this turn, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Untei, First Public Play");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if this SIGNI was put from the ener zone onto the field this turn, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "壹之公游 云梯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合这只精灵从能量区出场的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event ->
                event.getId() == GameEventId.ENTER && event.getCallerCardIndex() == getCardIndex() && event.getCaller().getOldLocation() == CardLocation.ENER) > 0)
            {
                enerCharge(1);
            }
        }
    }
}
