package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_G1_OrangutanPhantomBeast extends Card {
    
    public SIGNI_G1_OrangutanPhantomBeast()
    {
        setImageSets("WXDi-P03-075");
        
        setOriginalName("幻獣　オランウータン");
        setAltNames("ゲンジュウオランウータン Genjuu Oranuutan");
        setDescription("jp",
                "@E：あなたの他のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000する。" +
                "~#：対戦相手のシグニ１体を対象とし、%G %Gを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Orangutan, Phantom Terra Beast");
        setDescription("en",
                "@E: Another target SIGNI on your field gets +5000 power until end of turn." +
                "~#You may pay %G %G. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Orangutan, Phantom Beast");
        setDescription("en_fan",
                "@E: Target 1 of your other SIGNI, and until end of turn, it gets +5000 power." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %G %G. If you do, banish it."
        );
        
		setName("zh_simplified", "幻兽 人猿");
        setDescription("zh_simplified", 
                "@E :你的其他的精灵1只作为对象，直到回合结束时为止，其的力量+5000。" +
                "~#对战对手的精灵1只作为对象，可以支付%G %G。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().except(getCardIndex())).get();
            gainPower(target, 5000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 2)))
            {
                banish(target);
            }
        }
    }
}
