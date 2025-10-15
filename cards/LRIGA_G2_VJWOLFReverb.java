package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_VJWOLFReverb extends Card {
    
    public LRIGA_G2_VJWOLFReverb()
    {
        setImageSets("WXDi-P03-020");
        
        setOriginalName("VJ.WOLF-REVERB");
        setAltNames("ブイジェーウルフリバーブ Bui Jee Urufu Ribaabu");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E %G %X %X %X：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "VJ WOLF - REVERB");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead you do not take that damage.\n" +
                "@E %G %X %X %X: Vanish target SIGNI on your opponent's field with power 10000 or more."
        );
        
        setName("en_fan", "VJ.WOLF - REVERB");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E %G %X %X %X: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );
        
		setName("zh_simplified", "VJ.WOLF-REVERB");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E %G%X %X %X:对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(3)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            blockNextDamage();
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
