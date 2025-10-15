package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_VJWOLFMirage extends Card {
    
    public LRIGA_G2_VJWOLFMirage()
    {
        setImageSets("WXDi-P01-020");
        
        setOriginalName("VJ.WOLF-MIRAGE");
        setAltNames("ブイジェーウルフミラージュ Buijee Urufu Miraaju");
        setDescription("jp",
                "@E：ターン終了時まで、あなたのすべてのシグニのパワーを＋5000する。\n" +
                "@E %G %X %X：あなたのいずれかのシグニよりパワーの低い対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X %X %X %X：対戦相手のシグニ１体を対象とし、このターン、次にそれがアタックしたとき、そのアタックを無効にする。"
        );
        
        setName("en", "VJ WOLF - MIRAGE");
        setDescription("en",
                "@E: All SIGNI on your field get +5000 power until end of turn.\n" +
                "@E %G %X %X: Vanish target SIGNI on your opponent's field with power less than a SIGNI on your field.\n" +
                "@E %X %X %X %X: When target SIGNI on your opponent's field attacks next this turn, negate the attack."
        );
        
        setName("en_fan", "VJ.WOLF - MIRAGE");
        setDescription("en_fan",
                "@E: Until end of turn, all of your SIGNI get +5000 power.\n" +
                "@E %G %X %X: Target 1 of your opponent's SIGNI with power less than any of your SIGNI, and banish it.\n" +
                "@E %X %X %X %X: Target 1 of your opponent's SIGNI, and during this turn, the next time it attacks, disable that attack."
        );
        
		setName("zh_simplified", "VJ.WOLF-MIRAGE");
        setDescription("zh_simplified", 
                "@E :直到回合结束时为止，你的全部的精灵的力量+5000。\n" +
                "@E %G%X %X:比你的任一只的精灵的力量低的对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E %X %X %X %X:对战对手的精灵1只作为对象，这个回合，当下一次其攻击时，那次攻击无效。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
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
            registerEnterAbility(new EnerCost(Cost.colorless(4)), this::onEnterEff3);
        }
        
        private void onEnterEff1()
        {
            gainPower(getSIGNIOnField(getOwner()), 5000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            double maxPower = new TargetFilter().own().SIGNI().getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).max().orElse(0);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,maxPower-1)).get();
            banish(target);
        }
        
        private void onEnterEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            disableNextAttack(target);
        }
    }
}
