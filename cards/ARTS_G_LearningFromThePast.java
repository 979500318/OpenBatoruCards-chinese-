package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.DamageBlockParams;

public final class ARTS_G_LearningFromThePast extends Card {

    public ARTS_G_LearningFromThePast()
    {
        setImageSets("WX24-P1-008", "WX24-P1-008U");

        setOriginalName("温故知新");
        setAltNames("ツターダストメモリー Tsutaadasuto Memorii Stardust Memory");
        setDescription("jp",
                "対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。\n" +
                "&E４枚以上@0追加で、このターン、次にあなたがシグニによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Learning from the Past");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 10000 or more, and banish it.\n" +
                "&E4 or more@0 Additionally, this turn, the next time you would be damaged by a SIGNI, instead you aren't damaged."
        );

        setName("zh_simplified", "温故知新");
        setDescription("zh_simplified", 
                "对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n" +
                "&E4张以上@0追加，这个回合，下一次你因为精灵受到伤害的场合，作为替代，不会受到伤害。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }
        
        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
            
            if(getAbility().isRecollectFulfilled())
            {
                blockNextDamage(DamageBlockParams.ofSIGNI());
            }
        }
    }
}

