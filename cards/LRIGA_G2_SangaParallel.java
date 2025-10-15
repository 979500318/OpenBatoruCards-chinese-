package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_SangaParallel extends Card {
    
    public LRIGA_G2_SangaParallel()
    {
        setImageSets("WXDi-P01-032");
        
        setOriginalName("サンガ／／パラレル");
        setAltNames("サンガパラレル Sanga Parareru");
        setDescription("jp",
                "@E：あなたのエナゾーンからシグニ１枚を対象とし、それを場に出す。それの@E能力は発動しない。\n" +
                "@E %G %X %X：対戦相手のパワー１００００以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Sanga//Parallel");
        setDescription("en",
                "@E: Put target SIGNI from your Ener Zone onto your field. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@E %G %X %X: Vanish target SIGNI on your opponent's field with power 10000 or more."
        );
        
        setName("en_fan", "Sanga//Parallel");
        setDescription("en_fan",
                "@E: Target 1 SIGNI from your ener zone, and put it onto the field. Its @E abilities don't activate.\n" +
                "@E %G %X %X: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );
        
		setName("zh_simplified", "山河//平行");
        setDescription("zh_simplified", 
                "@E 从你的能量区把精灵1张作为对象，将其出场。其的@E能力不能发动。\n" +
                "@E %G%X %X:对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.SANGA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target, Enter.DONT_ACTIVATE);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
