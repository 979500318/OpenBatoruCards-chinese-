package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_SangaHit extends Card {
    
    public LRIGA_G2_SangaHit()
    {
        setImageSets("WXDi-P01-031");
        
        setOriginalName("サンガ／／ヒット");
        setAltNames("サンガヒット Sanga Hitto");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %G %X %X %X：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Sanga//Strike");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field.\n" +
                "@E %G %X %X %X: The next time you would take damage this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Sanga//Hit");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and banish it.\n" +
                "@E %G %X %X %X: This turn, the next time you would be damaged, instead you aren't damaged."
        );
        
		setName("zh_simplified", "山河//击中");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %G%X %X %X:这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            blockNextDamage();
        }
    }
}
