package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_G2_MuluVerdantGeneral extends Card {
    
    public SIGNI_G2_MuluVerdantGeneral()
    {
        setImageSets("WXDi-P04-074");
        
        setOriginalName("翠将　ボクロク");
        setAltNames("スイショウボクロク Suishou Bokuroku");
        setDescription("jp",
                "@C：このシグニは中央のシグニゾーンにあるかぎり、@>@U：このシグニがアタックしたとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2[[エナチャージ１]]@@を得る。"
        );
        
        setName("en", "Mulu, Jade General");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, it gains" +
                "@>@U: Whenever this SIGNI attacks, choose one of the following.\n" +
                "$$1 Add target SIGNI from your Ener Zone to your hand.\n" +
                "$$2 [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Mulu, Verdant General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, it gains:" +
                "@>@U: Whenever this SIGNI attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "$$2 [[Ener Charge 1]]"
        );
        
		setName("zh_simplified", "翠将 木鹿大王");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，得到\n" +
                "@>@U :当这只精灵攻击时，从以下的2种选1种。\n" +
                "$$1 从你的能量区把精灵1张作为对象，将其加入手牌。\n" +
                "$$2 [[能量填充1]]。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
