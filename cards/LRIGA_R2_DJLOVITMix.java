package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class LRIGA_R2_DJLOVITMix extends Card {
    
    public LRIGA_R2_DJLOVITMix()
    {
        setImageSets("WXDi-D04-007");
        
        setOriginalName("DJ.LOVIT-MIX");
        setAltNames("ディージェーラビットミックス Diijee Rabitto Mikkusu");
        setDescription("jp",
                "@E：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：ターン終了時まで、対象のあなたのシグニ１体は[[アサシン]]を得、対象のあなたのシグニ１体は[[ランサー]]を得る。"
        );
        
        setName("en", "DJ LOVIT - MIX");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 8000 or less.\n" +
                "@E: Target SIGNI on your field gains [[Assassin]] until end of turn. Target SIGNI on your field gains [[Lancer]] until end of turn."
        );
        
        setName("en_fan", "DJ.LOVIT - MIX");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "@E: Until end of turn, 1 of your target SIGNI gains [[Assassin]], and 1 of your target SIGNI gains [[Lancer]]."
        );
        
		setName("zh_simplified", "DJ.LOVIT-MIX");
        setDescription("zh_simplified", 
                "@E :对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "@E :直到回合结束时为止，对象的你的精灵1只得到[[暗杀]]，对象的你的精灵1只得到[[枪兵]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.colorless(3));
        setLevel(2);
        setLimit(+1);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
            
            target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
    }
}
