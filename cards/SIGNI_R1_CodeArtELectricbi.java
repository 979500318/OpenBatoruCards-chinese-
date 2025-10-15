package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_R1_CodeArtELectricbi extends Card {
    
    public SIGNI_R1_CodeArtELectricbi()
    {
        setImageSets("WXDi-P06-054");
        setLinkedImageSets("WXDi-P06-057");
        
        setOriginalName("コードアート　Dンドウバ");
        setAltNames("コードアートディーンドウバ Koodo Aato Dii Ndouba");
        setDescription("jp",
                "@U：このシグニが《コードアート　ララ・ルー//メモリア》にライズされたとき、対戦相手のパワー5000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。\n\n" +
                "@C：このカードの上にある赤のシグニは@>@U：このシグニがバニッシュされたとき、このシグニをエナゾーンから手札に加えてもよい。@@を得る。"
        );
        
        setName("en", "E - Lectro Bike, Code: Art");
        setDescription("en",
                "@U: Whenever this SIGNI is risen by \"Lalaru//Memoria, Code: Art\", you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 5000 or less.\n\n" +
                "@C: The red SIGNI on top of this card gains@>@U: When this SIGNI is vanished, you may add this SIGNI from the Ener Zone into its owner's hand."
        );
        
        setName("en_fan", "Code Art E Lectricbi");
        setDescription("en_fan",
                "@U: When you rise on this SIGNI with \"Code Art Lalaru//Memoria\", target 1 of your opponent's SIGNI with power 5000 or less, and you may pay %X. If you do, banish it.\n\n" +
                "@C: The red SIGNI on top of this card gains:" +
                "@>@U: When this SIGNI is banished, you may add this SIGNI from your ener zone to your hand."
        );
        
		setName("zh_simplified", "必杀代号 电动马");
        setDescription("zh_simplified", 
                "@U :当这只精灵被《コードアート　ララ・ルー//メモリア》升阶时，对战对手的力量5000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n" +
                "@C :这张牌的上面的红色的精灵得到\n" +
                "@>@U :当这只精灵被破坏时，可以把这张精灵从能量区加入手牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.RISE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withColor(CardColor.RED).over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSource().getName().getValue().contains("コードアート　ララ・ルー//メモリア") ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.BANISH, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(getAbility().getSourceCardIndex().getLocation() == CardLocation.ENER &&
               getAbility().getSourceCardIndex().getIndexedInstance().playerChoiceActivate())
            {
                getAbility().getSourceCardIndex().getIndexedInstance().addToHand(getEvent().getCallerCardIndex());
            }
        }
    }
}
