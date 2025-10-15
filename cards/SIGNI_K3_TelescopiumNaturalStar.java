package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_TelescopiumNaturalStar extends Card {
    
    public SIGNI_K3_TelescopiumNaturalStar()
    {
        setImageSets("WXDi-P03-088");
        
        setOriginalName("羅星　テレスコピウム");
        setAltNames("ラセイテレスコピウム Rasei Teresukopiumu");
        setDescription("jp",
                "@E %X：対戦相手のシグニ１体を対象とし、あなたのトラッシュにレベル１のシグニが５枚以上ある場合、ターン終了時まで、それのパワーを－5000する。１０枚以上ある場合、代わりにターン終了時まで、それのパワーを－10000する。"
        );
        
        setName("en", "Telescopium, Natural Planet");
        setDescription("en",
                "@E %X: If there are five or more level one SIGNI in your trash, target SIGNI on your opponent's field gets --5000 power until end of turn. If there are ten or more in your trash, it gets --10000 power until end of turn instead."
        );
        
        setName("en_fan", "Telescopium, Natural Star");
        setDescription("en_fan",
                "@E %X: Target 1 of your opponent's SIGNI, and if there are 5 or more level 1 SIGNI in your trash, until end of turn, it gets --5000 power. If there are 10 or more, instead until end of turn, it gets --10000 power."
        );
        
		setName("zh_simplified", "罗星 望远镜座");
        setDescription("zh_simplified", 
                "@E %X:对战对手的精灵1只作为对象，你的废弃区的等级1的精灵在5张以上的场合，直到回合结束时为止，其的力量-5000。10张以上的场合，作为替代，直到回合结束时为止，其的力量-10000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            int count = new TargetFilter().own().SIGNI().withLevel(1).fromTrash().getValidTargetsCount();
            if(count >= 5)
            {
                gainPower(target, count < 10 ? -5000 : -10000, ChronoDuration.turnEnd());
            }
        }
    }
}
