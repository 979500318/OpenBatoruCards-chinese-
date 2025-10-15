package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_PaimonWickedDevil extends Card {
    
    public SIGNI_K2_PaimonWickedDevil()
    {
        setImageSets("WXDi-P01-083", "SPDi01-63");
        
        setOriginalName("凶魔　パイモン");
        setAltNames("キョウマパイモン Kyouma Paimon");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、あなたのシグニ１体を場からトラッシュに置いてもよい。そうした場合、ターン終了時まで、それのパワーを－5000する。"
        );
        
        setName("en", "Paimon, Doomed Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put a SIGNI on your field into its owner's trash. If you do, target SIGNI on your opponent's field gets --5000 power until end of turn."
        );
        
        setName("en_fan", "Paimon, Wicked Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may put 1 of your SIGNI from the field into the trash. If you do, until end of turn, it gets --5000 power."
        );
        
		setName("zh_simplified", "凶魔 派蒙");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以把你的精灵1只从场上放置到废弃区。这样做的场合，直到回合结束时为止，其的力量-5000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI()).get();
                
                if(cardIndex != null && trash(cardIndex))
                {
                    gainPower(target, -5000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
