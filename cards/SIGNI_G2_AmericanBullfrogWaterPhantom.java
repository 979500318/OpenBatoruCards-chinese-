package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_AmericanBullfrogWaterPhantom extends Card {
    
    public SIGNI_G2_AmericanBullfrogWaterPhantom()
    {
        setImageSets("WXDi-P04-076");
        
        setOriginalName("幻水　ウシガエル");
        setAltNames("ゲンスイウシガエル Gensui Ushigaeru");
        setDescription("jp",
                "@C：あなたの手札が５枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：このシグニがアタックしたとき、%Gを支払ってもよい。そうした場合、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、%G %Gを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Bullfrog, Phantom Aquatic Beast");
        setDescription("en",
                "@C: As long as you have five or more cards in your hand, this SIGNI gets +5000 power.\n" +
                "@U: Whenever this SIGNI attacks, you may pay %G. If you do, draw a card." +
                "~#You may pay %G %G. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "American Bullfrog, Water Phantom");
        setDescription("en_fan",
                "@C: As long as there are 5 or more cards in your hand, this SIGNI gets +5000 power.\n" +
                "@U: Whenever this SIGNI attacks, you may pay %G. If you do, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %G %G. If you do, banish it."
        );
        
		setName("zh_simplified", "幻水 牛蛙");
        setDescription("zh_simplified", 
                "@C :你的手牌在5张以上时，这只精灵的力量+5000。\n" +
                "@U :当这只精灵攻击时，可以支付%G。这样做的场合，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，可以支付%G %G。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 5 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff()
        {
            if(payEner(Cost.color(CardColor.GREEN, 1)))
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.GREEN, 2)))
            {
                banish(target);
            }
        }
    }
}
