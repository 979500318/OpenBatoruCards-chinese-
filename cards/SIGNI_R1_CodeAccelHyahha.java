package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
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

public final class SIGNI_R1_CodeAccelHyahha extends Card {
    
    public SIGNI_R1_CodeAccelHyahha()
    {
        setImageSets("WXDi-P05-034");
        
        setOriginalName("コードアクセル　ヒャッハー");
        setAltNames("コードアクセルヒャッハー Koodo Akuseru Hyahhaa");
        setDescription("jp",
                "@C：このシグニの下にカードがあるかぎり、このシグニのパワーは＋5000され、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、%R %Rを支払ってもよい。そうした場合、それをバニッシュする。@@を得る。\n" +
                "@E：あなたのレベル３のシグニ１体を対象とし、それをこのシグニの下に置いてもよい。"
        );
        
        setName("en", "Hyahha, Code: Accel");
        setDescription("en",
                "@C: As long as there is a card underneath this SIGNI, this SIGNI gets +5000 power and gains@>@U: Whenever this SIGNI attacks, you may pay %R %R. If you do, vanish target SIGNI on your opponent's field with power 8000 or less.@@" +
                "@E: You may put target level three SIGNI on your field under this SIGNI."
        );
        
        setName("en_fan", "Code Accel Hyahha");
        setDescription("en_fan",
                "@C: As long as this SIGNI has a card under it, this SIGNI gets +5000 power, and:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 8000 or less, and you may pay %R %R. If you do, banish it.@@" +
                "@E: Target 1 of your level 3 SIGNI, and you may put it under this SIGNI."
        );
        
		setName("zh_simplified", "加速代号 嘿哈");
        setDescription("zh_simplified", 
                "@C :这只精灵的下面有牌时，这只精灵的力量+5000，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的力量8000以下的精灵1只作为对象，可以支付%R %R。这样做的场合，将其破坏。@@\n" +
                "@E :你的等级3的精灵1只作为对象，可以将其放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000),new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardsUnderCount(CardUnderCategory.UNDER) > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 2)))
            {
                banish(target);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withLevel(3)).get();
            
            if(target != null && playerChoiceActivate())
            {
                attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
            }
        }
    }
}
