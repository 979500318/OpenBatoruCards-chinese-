package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_CorgiPhantomBeast extends Card {
    
    public SIGNI_R1_CorgiPhantomBeast()
    {
        setImageSets("WXDi-P04-056");
        
        setOriginalName("幻獣　コーギー");
        setAltNames("ゲンジュウコーギー Genjyuu Koogii");
        setDescription("jp",
                "@C：あなたのターンの間、このシグニのパワーはあなたのセンタールリグのレベル１につき＋2000される。\n" +
                "@C：このシグニのパワーが8000以上であるかぎり、このシグニは@>@U：このシグニがアタックしたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。@@を得る。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Corgi, Phantom Terra Beast");
        setDescription("en",
                "@C: During your turn, this SIGNI gets +2000 power for each of your Center LRIG's levels.\n" +
                "@C: As long as this SIGNI's power is 8000 or more, it gains@>@U: Whenever this SIGNI attacks, you may discard a card. If you do, draw a card.@@" +
                "~#You may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Corgi, Phantom Beast");
        setDescription("en_fan",
                "@C: During your turn, this SIGNI gets +2000 power for each level your center LRIG has.\n" +
                "@C: As long as this SIGNI's power is 8000 or more, it gains:" +
                "@>@U: Whenever this SIGNI attacks, you may discard 1 card from your hand. If you do, draw 1 card.@@" +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "幻兽 柯基");
        setDescription("zh_simplified", 
                "@C :你的回合期间，这只精灵的力量依据你的核心分身的等级的数量，每有1级就+2000。\n" +
                "@C :这只精灵的力量在8000以上时，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，可以把手牌1张舍弃。这样做的场合，抽1张牌。@@" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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
            
            registerConstantAbility(this::onConstEff1Cond, new PowerModifier(this::onConstEff1ModGetValue));
            registerConstantAbility(this::onConstEff2Cond, new AbilityGainModifier(this::onConstEff2ModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEff1Cond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEff1ModGetValue(CardIndex cardIndex)
        {
            return 2000 * getLRIG(getOwner()).getIndexedInstance().getLevel().getValue();
        }
        
        private ConditionState onConstEff2Cond()
        {
            return getPower().getValue() >= 8000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
