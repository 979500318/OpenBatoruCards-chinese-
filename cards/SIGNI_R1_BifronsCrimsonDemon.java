package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_BifronsCrimsonDemon extends Card {
    
    public SIGNI_R1_BifronsCrimsonDemon()
    {
        setImageSets("WXDi-P03-055");
        
        setOriginalName("紅魔　ビフロンス");
        setAltNames("コウマビフロンス Kouma Bifuronsu");
        setDescription("jp",
                "@U $T1：あなたのシグニ１体がコストか効果によって場からトラッシュに置かれたとき、カードを１枚引き、手札を１枚捨てる。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、%Rを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Bifrons, Crimson Evil");
        setDescription("en",
                "@U $T1: When a SIGNI on your field is put into the trash by a cost or an effect, draw a card and discard a card." +
                "~#You may pay %R. If you do, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Bifrons, Crimson Devil");
        setDescription("en_fan",
                "@U $T1: When 1 of your SIGNI is put into the trash by a cost or effect, draw 1 card, and discard 1 card from your hand." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %R. If you do, banish it."
        );
        
		setName("zh_simplified", "红魔 毕弗隆斯");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵1只因为费用或效果从场上放置到废弃区时，抽1张牌，手牌1张舍弃。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，可以支付%R。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && getEvent().getSourceAbility() != null && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
            discard(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1)))
            {
                banish(target);
            }
        }
    }
}
