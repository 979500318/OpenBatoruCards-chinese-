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

public final class SIGNI_G1_PonyPhantomBeast extends Card {
    
    public SIGNI_G1_PonyPhantomBeast()
    {
        setImageSets("WXDi-P06-072");
        
        setOriginalName("幻獣　ポニー");
        setAltNames("ゲンジュウポニー Genjuu Ponii");
        setDescription("jp",
                "@U $T1：あなたのパワー8000以上のシグニが対戦相手のシグニ１体をバニッシュしたとき、%Xを支払ってもよい。そうした場合、カードを１枚引く。" +
                "~#：対戦相手のシグニ１体を対象とし、%X %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Pony, Phantom Terra Beast");
        setDescription("en",
                "@U $T1: When your SIGNI with power 8000 or more vanishes a SIGNI on your opponent's field, you may pay %X. If you do, draw a card." +
                "~#You may pay %X %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Pony, Phantom Beast");
        setDescription("en_fan",
                "@U $T1: When your SIGNI with power 8000 or more banishes an opponent's SIGNI, you may pay %X. If you do, draw 1 card." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X %X. If you do, banish it."
        );
        
		setName("zh_simplified", "幻兽 矮马");
        setDescription("zh_simplified", 
                "@U $T1 :当你的力量8000以上的精灵把对战对手的精灵1只破坏时，可以支付%X。这样做的场合，抽1张牌。" +
                "~#对战对手的精灵1只作为对象，可以支付%X %X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(3000);
        
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
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(getEvent().getSourceCardIndex()) && !isOwnCard(caller) &&
                   CardType.isSIGNI(getEvent().getSourceCardIndex().getCardReference().getType()) && getEvent().getSourceCardIndex().getIndexedInstance().getPower().getValue() >= 8000 ?
                    ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(payEner(Cost.colorless(1)))
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                banish(target);
            }
        }
    }
}
