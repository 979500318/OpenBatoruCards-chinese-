package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_SlothPhantomBeast extends Card {
    
    public SIGNI_G2_SlothPhantomBeast()
    {
        setImageSets("WXDi-P00-069");
        
        setOriginalName("幻獣　ナマケモノ");
        setAltNames("ゲンジュウナマケモノ Genjuu Namakemono");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場にパワー１００００以上のシグニがある場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Sloth, Phantom Terra Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a SIGNI on your field with power 10000 or more, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Sloth, Phantom Beast");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is a SIGNI with power 10000 or more on your field, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "幻兽 树懒");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有力量10000以上的精灵的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);
        
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
            if(new TargetFilter().own().SIGNI().withPower(10000,0).getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
    }
}
