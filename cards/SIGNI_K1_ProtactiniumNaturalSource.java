package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_ProtactiniumNaturalSource extends Card {
    
    public SIGNI_K1_ProtactiniumNaturalSource()
    {
        setImageSets("WXDi-P03-082");
        
        setOriginalName("羅原　Ｐａ");
        setAltNames("ラゲンプロトアクチニウム Ragen Purotoakuchiniumu Pa");
        setDescription("jp",
                "@E @[手札から＜原子＞のシグニを２枚捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "Pa, Natural Element");
        setDescription("en",
                "@E @[Discard two <<Atom>> SIGNI]@: Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Protactinium, Natural Source");
        setDescription("en_fan",
                "@E @[Discard 2 <<Atom>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "罗原 Pa");
        setDescription("zh_simplified", 
                "@E 从手牌把<<原子>>精灵2张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ATOM)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}
