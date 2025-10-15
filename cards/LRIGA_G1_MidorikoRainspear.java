package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class LRIGA_G1_MidorikoRainspear extends Card {
    
    public LRIGA_G1_MidorikoRainspear()
    {
        setImageSets("WXDi-P06-028");
        
        setOriginalName("緑姫・雨槍");
        setAltNames("ミドリコアマヤリ Midoriko Amayari");
        setDescription("jp",
                "@E：あなたのレベル２以下のシグニ１体を対象とし、ターン終了時まで、それは【ランサー】を得る。"
        );
        
        setName("en", "Midoriko, Rainspear");
        setDescription("en",
                "@E: Target level two or less SIGNI on your field gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "Midoriko Rainspear");
        setDescription("en_fan",
                "@E: Target 1 of your level 2 or lower SIGNI, and until end of turn, it gains [[Lancer]]."
        );
        
		setName("zh_simplified", "绿姬·雨枪");
        setDescription("zh_simplified", 
                "@E :你的等级2以下的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setLevel(1);
        setUseTiming(UseTiming.MAIN);
        
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(0,2)).get();
            if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
