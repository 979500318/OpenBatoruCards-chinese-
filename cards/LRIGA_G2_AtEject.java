package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_AtEject extends Card {
    
    public LRIGA_G2_AtEject()
    {
        setImageSets("WXDi-P04-031");
        
        setOriginalName("アト＝イジェクト");
        setAltNames("アトイジェクト Ato Ijekuto");
        setDescription("jp",
                "@E:対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。\n" +
                "@E %X %X:対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。"
        );
        
        setName("en", "At =Eject=");
        setDescription("en",
                "@E: When target SIGNI on your opponent's field attacks next this turn, negate the attack.\n" +
                "@E %X %X: When target SIGNI on your opponent's field attacks next this turn, negate the attack."
        );
        
        setName("en_fan", "At-Eject");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and this turn, the next time it attacks, disable that attack.\n" +
                "@E %X %X: Target 1 of your opponent's SIGNI, and this turn, the next time it attacks, disable that attack."
        );
        
		setName("zh_simplified", "亚特=强脱");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n" +
                "@E %X %X:对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AT);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            disableNextAttack(target);
        }
    }
}
