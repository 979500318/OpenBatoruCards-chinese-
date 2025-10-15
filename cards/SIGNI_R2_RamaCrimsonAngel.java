package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_RamaCrimsonAngel extends Card {
    
    public SIGNI_R2_RamaCrimsonAngel()
    {
        setImageSets("WXDi-P01-057");
        
        setOriginalName("紅天　ラーマ");
        setAltNames("コウテンラーマ Kouten Raama");
        setDescription("jp",
                "@E：ターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。" +
                "~#：対戦相手のパワー１２０００以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Rama, Crimson Angel");
        setDescription("en",
                "@E: All SIGNI on your field get +3000 power until end of turn." +
                "~#You may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Rama, Crimson Angel");
        setDescription("en_fan",
                "@E: Until end of turn, all of your SIGNI get +3000 power." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "红天 罗摩");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，你的全部的精灵的力量+3000。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
