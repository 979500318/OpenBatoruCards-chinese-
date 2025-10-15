package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_R2_HamsterPhantomBeast extends Card {
    
    public SIGNI_R2_HamsterPhantomBeast()
    {
        setImageSets("WXDi-P02-061", "SPDi38-08");
        
        setOriginalName("幻獣　ハムスター");
        setAltNames("ゲンジュウハムスター Genjuu Hamusutaa");
        setDescription("jp",
                "@U：：このシグニがアタックしたとき、このシグニのパワーが8000以上の場合、対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。このシグニのパワーが12000以上の場合、代わりに対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Hamster, Phantom Terra Beast");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 8000 or more, vanish target SIGNI on your opponent's field with power 3000 or less. If this SIGNI's power is 12000 or more, instead vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Hamster, Phantom Beast");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if this SIGNI's power is 8000 or more, target 1 of your opponent's SIGNI with power 3000 or less, and banish it. If this SIGNI's power is 12000 or more, instead target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );
        
		setName("zh_simplified", "幻兽 仓鼠");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量在8000以上的场合，对战对手的力量3000以下的精灵1只作为对象，将其破坏。这只精灵的力量在12000以上的场合，作为替代，对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(2);
        setPower(5000);
        
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
            int powerMax = 0;
            if(getCardIndex().getIndexedInstance().getPower().getValue() >= 8000) powerMax = 3000;
            if(getCardIndex().getIndexedInstance().getPower().getValue() >= 12000) powerMax = 8000;
            
            if(powerMax != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,powerMax)).get();
                banish(target);
            }
        }
    }
}
