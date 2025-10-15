package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G1_HyakkokoPhantomBeast extends Card {
    
    public SIGNI_G1_HyakkokoPhantomBeast()
    {
        setImageSets("WXDi-P01-072", "SPDi01-68","SPDi38-07");
        
        setOriginalName("幻獣　ヒャッココ");
        setAltNames("ゲンジュウヒャッココ Genjuu Hyakkoko");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワーが5000以上の場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Hyakkoko, Phantom Terra Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if its power is 5000 or more, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Hyakkoko, Phantom Beast");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 5000 or more, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "幻兽 白虎虎");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在5000以上的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            if(getPower().getValue() >= 5000)
            {
                enerCharge(1);
            }
        }
    }
}
