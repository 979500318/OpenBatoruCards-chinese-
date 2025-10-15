package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_MusuzakuPhantomBeast extends Card {
    
    public SIGNI_G2_MusuzakuPhantomBeast()
    {
        setImageSets("WXDi-P01-076");
        
        setOriginalName("幻獣　ムスザク");
        setAltNames("ゲンジュウムスザク Genjuu Musuzaku");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Musuzaku, Phantom Terra Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Musuzaku, Phantom Beast");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "幻兽 朱雀小妹");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(3000);
        
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
            enerCharge(1);
        }
    }
}
