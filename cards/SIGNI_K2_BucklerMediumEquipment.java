package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K2_BucklerMediumEquipment extends Card {
    
    public SIGNI_K2_BucklerMediumEquipment()
    {
        setImageSets("WXDi-P05-083");
        
        setOriginalName("中装　バックラー");
        setAltNames("チュウソウバックラー Chuusou Bakkuraa");
        setDescription("jp",
                "~#：対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－15000する。"
        );
        
        setName("en", "Buckler, High Armed");
        setDescription("en",
                "~#You may pay %X. If you do, target SIGNI on your opponent's field gets -15000 power until end of turn."
        );
        
        setName("en_fan", "Buckler, Medium Equipment");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI, and you may pay %X. If you do, until end of turn, it gets --15000 power."
        );
        
		setName("zh_simplified", "中装 小圆");
        setDescription("zh_simplified", 
                "~#对战对手的精灵1只作为对象，可以支付%X。这样做的场合，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                gainPower(target, -15000, ChronoDuration.turnEnd());
            }
        }
    }
}
