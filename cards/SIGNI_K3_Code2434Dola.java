package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K3_Code2434Dola extends Card {
    
    public SIGNI_K3_Code2434Dola()
    {
        setImageSets("WXDi-P00-079");
        
        setOriginalName("コード２４３４　ドーラ");
        setAltNames("コードニジサンジドーラ Koodo Nijisanji Doora");
        setDescription("jp",
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );
        
        setName("en", "Dola, Code 2434");
        setDescription("en",
                "~#Target SIGNI on your opponent's field gets --10000 power until end of turn."
        );
        
        setName("en_fan", "Code 2434 Dola");
        setDescription("en_fan",
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );
        
		setName("zh_simplified", "2434代号 多拉");
        setDescription("zh_simplified", 
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(13000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(cardIndex, -10000, ChronoDuration.turnEnd());
        }
    }
}
