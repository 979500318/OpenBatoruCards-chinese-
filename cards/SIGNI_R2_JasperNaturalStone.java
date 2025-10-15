package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R2_JasperNaturalStone extends Card {
    
    public SIGNI_R2_JasperNaturalStone()
    {
        setImageSets("WXDi-P05-058");
        
        setOriginalName("羅石　ジャスパー");
        setAltNames("ラセキジャスパー Raseki Jasupaa");
        setDescription("jp",
                "@U：対戦相手のターンの間、このシグニがバニッシュされたとき、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Jasper, Natural Crystal");
        setDescription("en",
                "@U: During your opponent's turn, when this SIGNI is vanished, your opponent chooses a card from their Ener Zone and puts it into their trash." +
                "~#You may discard a card. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Jasper, Natural Stone");
        setDescription("en_fan",
                "@U: During your opponent's turn, when this SIGNI is banished, your opponent chooses 1 card from their ener zone, and puts it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you discard 1 card from your hand. If you do, banish it."
        );
        
		setName("zh_simplified", "罗石 碧玉");
        setDescription("zh_simplified", 
                "@U :对战对手的回合期间，当这只精灵被破坏时，对战对手从自己的能量区选1张牌放置到废弃区。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                banish(target);
            }
        }
    }
}
