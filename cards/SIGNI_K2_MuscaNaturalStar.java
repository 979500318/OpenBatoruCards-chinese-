package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K2_MuscaNaturalStar extends Card {
    
    public SIGNI_K2_MuscaNaturalStar()
    {
        setImageSets("WXDi-D07-016");
        
        setOriginalName("羅星　マースカ");
        setAltNames("ラセイマースカ Rasei Maasuka");
        setDescription("jp",
                "@E @[手札からレベル１のシグニを２枚捨てる]@：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Musca, Natural Planet");
        setDescription("en",
                "@E @[Discard two level one SIGNI]@: Add target SIGNI without a #G from your trash to your hand." +
                "~#You may pay %K %X. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Musca, Natural Star");
        setDescription("en_fan",
                "@E @[Discard 2 level 1 SIGNI from your hand]@: Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "罗星 苍蝇座");
        setDescription("zh_simplified", 
                "@E :从手牌把等级1的精灵2张舍弃从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new DiscardCost(2, new TargetFilter().SIGNI().withLevel(1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            addToHand(target);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
