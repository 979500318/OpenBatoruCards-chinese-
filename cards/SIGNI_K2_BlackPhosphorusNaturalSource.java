package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_BlackPhosphorusNaturalSource extends Card {
    
    public SIGNI_K2_BlackPhosphorusNaturalSource()
    {
        setImageSets("WXDi-P05-084");
        
        setOriginalName("羅原　ＢＰ");
        setAltNames("ラゲンブラックファスファラス Ragen Burakku Fasufarasu BP");
        setDescription("jp",
                "@E @[エナゾーンから＜原子＞のシグニ１枚をトラッシュに置き、手札から＜原子＞のシグニを１枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );
        
        setName("en", "BP, Natural Element");
        setDescription("en",
                "@E @[Put an <<Atom>> SIGNI from your Ener Zone into your trash and discard an <<Atom>> SIGNI]@: Target SIGNI on your opponent's field gets --10000 power until end of turn."
        );
        
        setName("en_fan", "Black Phosphorus, Natural Source");
        setDescription("en_fan",
                "@E @[Put 1 <<Atom>> SIGNI from your ener zone into the trash, and discard 1 <<Atom>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );
        
		setName("zh_simplified", "罗原 BP");
        setDescription("zh_simplified", 
                "@E 从能量区把<<原子>>精灵1张放置到废弃区，从手牌把<<原子>>精灵1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new AbilityCostList(
                new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ATOM).fromEner()),
                new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ATOM))
            ), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
