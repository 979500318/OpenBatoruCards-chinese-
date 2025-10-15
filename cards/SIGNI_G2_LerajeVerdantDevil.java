package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_LerajeVerdantDevil extends Card {
    
    public SIGNI_G2_LerajeVerdantDevil()
    {
        setImageSets("WXDi-P04-075");
        
        setOriginalName("翠魔　レラジェ");
        setAltNames("スイマレラジェ Suima Reraje");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それの基本パワーを10000にする。"
        );
        
        setName("en", "Leraje, Jade Evil");
        setDescription("en",
                "@E: The base power of target SIGNI on your opponent's field becomes 10000 until end of turn."
        );
        
        setName("en_fan", "Leraje, Verdant Devil");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, its base power becomes 10000."
        );
        
		setName("zh_simplified", "翠魔 列拉金");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的基本力量变为10000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);
        
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
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            setBasePower(target, 10000, ChronoDuration.turnEnd());
        }
    }
}
