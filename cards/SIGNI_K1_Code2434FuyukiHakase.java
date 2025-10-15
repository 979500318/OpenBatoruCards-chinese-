package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_Code2434FuyukiHakase extends Card {
    
    public SIGNI_K1_Code2434FuyukiHakase()
    {
        setImageSets("WXDi-P00-074");
        
        setOriginalName("コード２４３４　葉加瀬冬雪");
        setAltNames("コードニジサンジハカセフユキ Koodo Nijisanji Hakase Fuyuki");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－１０００する。"
        );
        
        setName("en", "Fuyuki Hakase, Code 2434");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --1000 power until end of turn."
        );
        
        setName("en_fan", "Code 2434 Fuyuki Hakase");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power."
        );
        
		setName("zh_simplified", "2434代号 叶加濑冬雪");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-1000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(1000);
        
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
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -1000, ChronoDuration.turnEnd());
        }
    }
}
