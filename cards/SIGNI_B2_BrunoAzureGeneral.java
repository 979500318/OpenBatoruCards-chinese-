package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class SIGNI_B2_BrunoAzureGeneral extends Card {
    
    public SIGNI_B2_BrunoAzureGeneral()
    {
        setImageSets("WXDi-P04-066", "SPDi10-06");
        
        setOriginalName("蒼将　ブルーノ");
        setAltNames("ソウショウブルーノ Soushou Buruuno");
        setDescription("jp",
                "@C：このシグニは中央のシグニゾーンにあるかぎり、@>@U：このシグニがアタックしたとき、カードを１枚引くか対戦相手は手札を１枚捨てる。@@を得る。"
        );
        
        setName("en", "Bruno, Azure General");
        setDescription("en",
                "@C: As long as this SIGNI is in your center SIGNI Zone, it gains@>@U: Whenever this SIGNI attacks, draw a card or your opponent discards a card."
        );
        
        setName("en_fan", "Bruno, Azure General");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, it gains:" +
                "@>@U: Whenever this SIGNI attacks, draw 1 card or your opponent discards 1 card from their hand."
        );
        
		setName("zh_simplified", "苍将 布鲁诺");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，得到\n" +
                "@>@U :当这只精灵攻击时，抽1张牌或对战对手把手牌1张舍弃。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
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
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
            {
                draw(1);
            } else {
                discard(getOpponent(), 1);
            }
        }
    }
}
