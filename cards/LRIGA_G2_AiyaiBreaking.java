package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_AiyaiBreaking extends Card {

    public LRIGA_G2_AiyaiBreaking()
    {
        setImageSets("WXDi-P12-043");

        setOriginalName("アイヤイ　ブレイキング");
        setAltNames("アイヤイブレイキング Aiyai Bureikingu");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E %G %X %X：対戦相手のシグニ１体と対戦相手のエナゾーンからシグニ１枚を対象とし、それらの場所を入れ替える。この方法で場に出たシグニの@E能力は発動しない。"
        );

        setName("en", "Aiyai, Breaking");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead you do not take that damage.\n@E %G %X %X: Swap the position of target SIGNI on your opponent's field with target SIGNI in their Ener Zone. The @E abilities of SIGNI put onto their field this way do not activate."
        );
        
        setName("en_fan", "Aiyai Breaking");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E %G %X %X: Exchange the positions of 1 of your opponent's SIGNI with 1 SIGNI in your opponent's ener zone. Its @E abilities don't activate."
        );

		setName("zh_simplified", "艾娅伊 霹雳");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E %G%X %X对战对手的精灵1只和从对战对手的能量区把精灵1张作为对象，将这些的场所交换。这个方法出场的精灵的@E能力不能发动。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AIYAI);
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            blockNextDamage();
        }
        
        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI()).get();
            
            if(cardIndex != null)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI().fromEner().playableAs(cardIndex)).get();
                
                if(target != null)
                {
                    putInEner(cardIndex);
                    putOnField(target, cardIndex.getPreTransientLocation(), Enter.DONT_ACTIVATE);
                }
            }
        }
    }
}
