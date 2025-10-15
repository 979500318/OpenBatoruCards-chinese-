package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W1_AssistAngeLevel1Dash extends Card {
    
    public LRIGA_W1_AssistAngeLevel1Dash()
    {
        setImageSets("WXDi-P00-027");
        
        setOriginalName("【アシスト】アンジュ　レベル１’");
        setAltNames("アシストアンジュレベルイチダッシュ Ashisuto Anju Reberu Ichi Dasshu Dash Assist Ange");
        setDescription("jp",
                "@E %X：対戦相手のパワー10000以下のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "[Assist] Ange, Level 1'");
        setDescription("en",
                "@E %X: Put target SIGNI on your opponent's field with power 10000 or less into its owner's trash."
        );
        
        setName("en_fan", "[Assist] Ange Level 1'");
        setDescription("en_fan",
                "@E %X: Target 1 of your opponent's SIGNI with power 10000 or less, and put it into the trash."
        );
        
		setName("zh_simplified", "【支援】安洁 等级1'");
        setDescription("zh_simplified", 
                "@E %X:对战对手的力量10000以下的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
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
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withPower(0,10000)).get();
            trash(cardIndex);
        }
    }
}
