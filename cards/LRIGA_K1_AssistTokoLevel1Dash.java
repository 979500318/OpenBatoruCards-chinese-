package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIGA_K1_AssistTokoLevel1Dash extends Card {
    
    public LRIGA_K1_AssistTokoLevel1Dash()
    {
        setImageSets("WXDi-P00-030");
        
        setOriginalName("【アシスト】とこ　レベル１’");
        setAltNames("アシストとこレベルイチダッシュ Ashisuto Toko Reberu Ichi Dasshu Dash Assist Toko");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "[Assist] Toko, Level 1'");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "[Assist] Toko Level 1'");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, that SIGNI gets --8000 power."
        );
        
		setName("zh_simplified", "【支援】床 等级1'");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，将其的力量-8000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.TOKO);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.BLACK);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(cardIndex, -8000, ChronoDuration.turnEnd());
        }
    }
}
